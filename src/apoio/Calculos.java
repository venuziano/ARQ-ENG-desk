package apoio;

import dao.ComodosProjetosDAO;
import dao.FacesDAO;
import dao.MateriaisDAO;
import dao.MateriaisFaceDAO;
import dao.exceptions.NonexistentEntityException;
import entidade.ComodosProjetos;
import entidade.Faces;
import entidade.Materiais;
import entidade.MateriaisFace;
import entidade.MateriaisFacePK;
import java.io.IOException;
import java.util.Collection;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author rrs
 */
public class Calculos {

    EntityManagerFactory factory = Persistence.createEntityManagerFactory("ARQ_ENGPU");
    
    //construtor dos controllers
    MateriaisDAO dao = new MateriaisDAO(factory);
    MateriaisFaceDAO daoMf = new MateriaisFaceDAO(factory);
    FacesDAO daoF = new FacesDAO(factory);
    ComodosProjetosDAO daoC = new ComodosProjetosDAO(factory);
    
    ComodosProjetos comodo = new ComodosProjetos();

    //tipo da face
    static Double PAREDE = 0.13;
    static Double COBERTURA = 0.10;
    static Double LAJEINFERIOR = 0.17;

    //variaveis para o calculo da resistencia total da face
    Double somaResistenciaTotal = 0.0;
    Double Rse = 0.04;
    Double resistenciaAtualizada = 0.0;
    Double transmitanciaFinal = 0.0;
    Double fluxoOpaco = 0.0;
    Double fluxoTransparente = 0.0;
    Double fatorSolar = 0.0;
    Double cargaFluxoOpaco = 0.0;
    Double cargaFluxoTransparente = 0.0;
    Double cargaTermicaT = 0.0;

    //log
    GerenciarLog log = new GerenciarLog();

    //metodo para calcular a resistencia total após um material ser inserido na face.
    public void calcularRtFaceAddMaterial(Faces face) throws Exception {
        Double Rsi = 0.0;
        somaResistenciaTotal = 0.0;
        try {
            //carrega os materiais da face.
            face.setMateriaisFaceCollection(daoMf.buscaMaterialPorFace(face.getId()));
            for (MateriaisFace mf : face.getMateriaisFaceCollection()) {
                somaResistenciaTotal += mf.getResistencia();
            }
        } catch (Exception e) {
            log.get_log(e.toString());
        }
        try {
            if (face.getTipo() == 0) {
                Rsi = PAREDE;
            }
            if (face.getTipo() == 1) {
                Rsi = COBERTURA;
            }
            if (face.getTipo() == 2) {
                Rsi = LAJEINFERIOR;
            }
        } catch (Exception e) {
            log.get_log(e.toString());
        }
        try {
            //sempre calcula a resistencia total da face de acordo com seus materiais, substituindo o valor atual.
            resistenciaAtualizada = Rse + somaResistenciaTotal + Rsi;
            face.setResistenciaTotal(resistenciaAtualizada);
            calcularTransmitancia();
            face.setTransmitancia(transmitanciaFinal);
        } catch (Exception e) {
            log.get_log(e.toString());
        }
        daoF.atualizar(face);
    }

    public void calcularTransmitancia() {
        transmitanciaFinal = 1 / resistenciaAtualizada;
    }

    //metodo para calcular a resistencia total após um material ser excluído da face.
    public void calcularRtFaceExcluiMaterial(Faces face) throws IOException, NonexistentEntityException, Exception {
        Double Rsi = 0.0;
        somaResistenciaTotal = 0.0;
        try {
            face.setMateriaisFaceCollection(daoMf.buscaMaterialPorFace(face.getId()));
            for (MateriaisFace mf : face.getMateriaisFaceCollection()) {
                somaResistenciaTotal += mf.getResistencia();
            }
        } catch (Exception e) {
            log.get_log(e.toString());
        }
        try {
            if (face.getTipo() == 0) {
                Rsi = PAREDE;
            }
            if (face.getTipo() == 1) {
                Rsi = COBERTURA;
            }
            if (face.getTipo() == 2) {
                Rsi = LAJEINFERIOR;
            }
        } catch (Exception e) {
            log.get_log(e.toString());
        }
        try {
            //confere se há materiais vinculados a lista, se não há, seta resistencia total e transmitancia da fase manipulada para 0.
            //se houver materiais na lista, cálculo a resistencia total com a soma dos rt's realizada no for acima.
            if (daoMf.buscaMaterialPorFace(face.getId()).size() > 0) {
                resistenciaAtualizada = Rse + somaResistenciaTotal + Rsi;
                face.setResistenciaTotal(resistenciaAtualizada);
                calcularTransmitancia();
                face.setTransmitancia(transmitanciaFinal);
            } else {
                resistenciaAtualizada = 0.0;
                transmitanciaFinal = 0.0;
                face.setResistenciaTotal(resistenciaAtualizada);
                face.setTransmitancia(transmitanciaFinal);
            }
        } catch (Exception e) {
            log.get_log(e.toString());
        }
        dao.atualizar(face);
    }
    
    public void calcularCargaTermicaComodo(ComodosProjetos comodo, Double temperatura, int estacao) throws Exception {
        this.comodo = comodo;
        for (Faces f : this.comodo.getFacesCollection()) {
                if (f.getFatorSolar() != 0) {
                   fluxoTransparente = f.getTransmitancia() * (temperatura - 23);
                   fatorSolar = f.getFatorSolar() * f.getRadiacaoSolarIns();
                   cargaFluxoTransparente = (fluxoTransparente + fatorSolar) * f.getArea();
                   f.setFluxoTransparente(fluxoTransparente);
                   f.setCargaFluxoTransparente(cargaFluxoTransparente);
                   dao.atualizar(f);
                   cargaTermicaT += cargaFluxoTransparente;
                } else {
                    //CÁLCULO INVERNO
                    if (estacao == 1) {
                        fluxoOpaco = f.getTransmitancia() * (temperatura - 18);
                        cargaFluxoOpaco = fluxoOpaco * f.getArea();
                        f.setFluxoOpaco(fluxoOpaco);
                        f.setCargaFluxoOpaco(cargaFluxoOpaco);
                        dao.atualizar(f);
                        cargaTermicaT += cargaFluxoOpaco;
                    //CÁLCULO VERÃO
                    } else if (estacao == 2) {
                        fluxoOpaco = f.getTransmitancia() * ((f.getAbsorvidadeTinta() * f.getRadiacaoSolarIns() * 0.04) + (temperatura - 23));
                        cargaFluxoOpaco = fluxoOpaco * f.getArea();
                        f.setFluxoOpaco(fluxoOpaco);
                        f.setCargaFluxoOpaco(cargaFluxoOpaco);
                        dao.atualizar(f);
                        cargaTermicaT += cargaFluxoOpaco;
                    }
                }
            this.comodo.setCargaTermicaT(cargaTermicaT);
            daoC.atualizar(this.comodo);
        }
    }
}
