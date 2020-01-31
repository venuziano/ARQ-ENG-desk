/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import apoio.GerenciarAuditoria;
import apoio.GerenciarLog;
import apoio.UsuarioLogado;
import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import entidade.Anexos;
import entidade.Auditoria;
import entidade.ComodosProjetos;
import entidade.Equipes;
import entidade.Faces;
import entidade.Materiais;
import entidade.MateriaisFace;
import entidade.Projetos;
import entidade.Usuarios;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author rrs
 */
public class GerenciadorDAO implements Serializable {

    public GerenciadorDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    GerenciarAuditoria ga = new GerenciarAuditoria();

    UsuarioLogado ul;

    Auditoria a = new Auditoria();

    GerenciarLog log = new GerenciarLog();

    String infoAtual;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void salvar(Object o) throws IOException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(o);
            em.getTransaction().commit();

            //salva auditoria
            a.setTipo("SALVAR");
            setNewEntidade(o);
            salvarAuditoria(o);
        } finally {
            if (em != null) {
                em.close();
            }
        }

    }

    public void atualizar(Object o) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            o = em.merge(o);
            em.getTransaction().commit();

            a.setTipo("ATUALIZAR");

            //primeiro salva a oldEntidade em uma string local
            infoAtual = GerenciarAuditoria.getInfo() + " || NOVO REGISTRO:  ";

            setNewEntidade(o);

            //concatena com o string "infoAtual" com o a.setInfo definido na newEntidade
            infoAtual = (infoAtual + a.getInfo());
            a.setInfo(infoAtual);

            //salva auditoria
            salvarAuditoria(o);
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void excluir(Object o, Integer id) throws IllegalOrphanException, NonexistentEntityException, IOException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Object c = em.getReference(o.getClass(), id);
            em.remove(c);
            em.getTransaction().commit();

            //salva auditoria
            a.setTipo("EXCLUIR");
            setNewEntidade(o);
            salvarAuditoria(o);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void excluirPk(Object o) throws IllegalOrphanException, NonexistentEntityException, IOException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
//            Object c = em.getReference(o.getClass(), id1);
//            em.remove(o);

            o = em.merge(o);
            em.remove(o);

            em.getTransaction().commit();

            //salva auditoria
            a.setTipo("EXCLUIR");
            setNewEntidade(o);
            salvarAuditoria(o);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void salvarAuditoria(Object o) throws IOException {

        EntityManager em = null;

        try {
            if (ga.getSituacao().equalsIgnoreCase("0")) {
                a.setIdAutor(UsuarioLogado.getUsuario().getId());
                a.setAutor(UsuarioLogado.getUsuario().getNome());
                Date data = new Date(System.currentTimeMillis());
                a.setData(data);

            }

            //salva a auditoria
            em = getEntityManager();
            em.getTransaction().begin();

            //reseta a auditoria, para não dar exceção de detached
            a.setId(null);

            em.persist(a);
            em.getTransaction().commit();

        } catch (Exception e) {
            log.get_log(e.toString());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void setNewEntidade(Object o) throws IOException {

        try {

            if (o instanceof Projetos) {
                Projetos projetos = (Projetos) o;

                a.setInfo(" ID: " + projetos.getId() + " || EQUIPE: " + projetos.getRefEquipe().getNome() + " || NOME: " + projetos.getNome() + "|| SITUACAO: " + projetos.getSituacao() + " || INFO: " + projetos.getInfo()
                        + " || RESPONSAVEL: " + projetos.getResponsavel().getNome() + " || DATA CRIACAO: " + projetos.getDataCriacao());
            }

            if (o instanceof Usuarios) {
                Usuarios usuarios = (Usuarios) o;

                a.setInfo(" ID: " + usuarios.getId() + " || NOME: " + usuarios.getNome() + " || LOGIN: " + usuarios.getLogin() + " || SENHA: " + usuarios.getSenha());
            }

            if (o instanceof Anexos) {
                Anexos anexos = (Anexos) o;

                a.setInfo(" ID: " + anexos.getId() + " || NOME: " + anexos.getNome() + " || ID SOURCE: " + anexos.getRefSource() + " || RESPONSAVEL: " + anexos.getRefUsuario().getNome()
                        + " || DATA CRIACAO: " + anexos.getDtCriacao() + " || PATH: " + anexos.getPath() + " || FAMILY: " + anexos.getFamily());
            }

            if (o instanceof Equipes) {
                Equipes esquipes = (Equipes) o;

                a.setInfo(" ID: " + esquipes.getId() + " || NOME: " + esquipes.getNome() + " || INFO: " + esquipes.getInfo());
            }

            if (o instanceof ComodosProjetos) {
                ComodosProjetos comodos = (ComodosProjetos) o;

                a.setInfo(" ID: " + comodos.getId() + " || NOME: " + comodos.getNome() + " || Q: " + comodos.getCargaTermicaT() + " || Projeto: " + comodos.getRefProjeto().getNome());
            }

            if (o instanceof Materiais) {
                Materiais materiais = (Materiais) o;

                a.setInfo(" ID: " + materiais.getId() + " || NOME: " + materiais.getNome() + " || CONDUTIVIDADE: " + materiais.getCondutividade() + " || TIPO: "
                        + materiais.getTipo() + " || INFO: " + materiais.getInfo() + " || SITUAÇÃO: " + materiais.getSituacao());
            }

            if (o instanceof Faces) {
                Faces faces = (Faces) o;

                a.setInfo(" ID: " + faces.getId() + " || NOME: " + faces.getDescricao() + " || TIPO: " + faces.getTipo() + " || ORIENTAÇÃO SOLAR: "
                        + faces.getOrientacaoSolar() + " || RESISTÊNCIA TOTAL: " + faces.getResistenciaTotal() + " || TRANSMITANCIA: " + faces.getTransmitancia() + " || FATOR SOLAR:" + faces.getFatorSolar() + " || FLUXO OPACO: "
                        + faces.getFluxoOpaco() + " || FLUXO TRANSPARENTE: " + faces.getFluxoTransparente() + " || AREA: " + faces.getArea() + " || CARGA FLUXO OPACO: "
                        + faces.getCargaFluxoOpaco() + " || CARGA FLUXO TRANSPARENTE: " + faces.getCargaFluxoTransparente() + " || COMODO: " + faces.getRefComodo().getNome() + " || ABSORVIDADE TINTA: "
                        + faces.getAbsorvidadeTinta() + " || RADIAÇÃO SOLAR: " + faces.getRadiacaoSolarIns());
            }

            if (o instanceof MateriaisFace) {
                MateriaisFace materiaisFace = (MateriaisFace) o;

                a.setInfo(" FACE: " + materiaisFace.getFaces().getDescricao() + " || MATERIAL: " + materiaisFace.getMateriais().getNome()
                        + " || RESISTENCIA: " + materiaisFace.getResistencia() + " || ESPESSURA: " + materiaisFace.getEspessura());
            }

        } catch (Exception e) {
            log.get_log(e.toString());
        }
    }

    public void setOldEntidade(Object o) throws IOException {

        //complementa o registro sinalizando qual é o antigo
        String complemento = "REGISTRO ANTIGO:  ";

        try {

            if (o instanceof Projetos) {
                Projetos projetos = (Projetos) o;

                GerenciarAuditoria.setInfo(complemento + " ID: " + projetos.getId() + " || EQUIPE: " + projetos.getRefEquipe().getNome() + " || NOME: "
                        + projetos.getNome() + "|| SITUACAO: " + projetos.getSituacao() + " || INFO: " + projetos.getInfo() + " || RESPONSAVEL: " + projetos.getResponsavel().getNome()
                        + " || DATA CRIACAO: " + projetos.getDataCriacao());
            }

            if (o instanceof Usuarios) {
                Usuarios usuarios = (Usuarios) o;

                GerenciarAuditoria.setInfo(complemento + " ID: " + usuarios.getId() + " || NOME: " + usuarios.getNome() + " || LOGIN: " + usuarios.getLogin()
                        + " || SENHA: " + usuarios.getSenha());

            }

            if (o instanceof Anexos) {
                Anexos anexos = (Anexos) o;

                a.setInfo(" ID: " + anexos.getId() + " || NOME: " + anexos.getNome() + " || ID SOURCE: " + anexos.getRefSource() + " || RESPONSAVEL: " + anexos.getRefUsuario().getNome()
                        + " || DATA CRIACAO: " + anexos.getDtCriacao() + " || PATH: " + anexos.getPath() + " || FAMILY: " + anexos.getFamily());
            }

            if (o instanceof Equipes) {
                Equipes esquipes = (Equipes) o;

                GerenciarAuditoria.setInfo(complemento + " ID: " + esquipes.getId() + " || NOME: " + esquipes.getNome() + " || INFO: " + esquipes.getInfo());
            }

            if (o instanceof ComodosProjetos) {
                ComodosProjetos comodos = (ComodosProjetos) o;

                GerenciarAuditoria.setInfo(complemento + " ID: " + comodos.getId() + " || NOME: " + comodos.getNome() + " || Q: " + comodos.getCargaTermicaT()
                        + " || Projeto: " + comodos.getRefProjeto().getNome());
            }

            if (o instanceof Materiais) {
                Materiais materiais = (Materiais) o;

                GerenciarAuditoria.setInfo(complemento + " ID: " + materiais.getId() + " || NOME: " + materiais.getNome() + " || CONDUTIVIDADE: " + materiais.getCondutividade()
                        + " || TIPO: " + materiais.getTipo() + " || INFO: " + materiais.getInfo() + " || SITUAÇÃO: " + materiais.getSituacao());
            }

            if (o instanceof Faces) {
                Faces faces = (Faces) o;

                a.setInfo(" ID: " + faces.getId() + " || NOME: " + faces.getDescricao() + " || TIPO: " + faces.getTipo() + " || ORIENTAÇÃO SOLAR: "
                        + faces.getOrientacaoSolar() + " || RESISTÊNCIA TOTAL: " + faces.getResistenciaTotal() + " || TRANSMITANCIA: " + faces.getTransmitancia() + " || FATOR SOLAR:" + faces.getFatorSolar() + " || FLUXO OPACO: "
                        + faces.getFluxoOpaco() + " || FLUXO TRANSPARENTE: " + faces.getFluxoTransparente() + " || AREA: " + faces.getArea() + " || CARGA FLUXO OPACO: "
                        + faces.getCargaFluxoOpaco() + " || CARGA FLUXO TRANSPARENTE: " + faces.getCargaFluxoTransparente() + " || COMODO: " + faces.getRefComodo().getNome() + " || ABSORVIDADE TINTA: "
                        + faces.getAbsorvidadeTinta() + " || RADIAÇÃO SOLAR: " + faces.getRadiacaoSolarIns());
            }

            if (o instanceof MateriaisFace) {
                MateriaisFace materiaisFace = (MateriaisFace) o;

                a.setInfo(" FACE: " + materiaisFace.getFaces().getDescricao() + " || MATERIAL: " + materiaisFace.getMateriais().getNome()
                        + " || RESISTENCIA: " + materiaisFace.getResistencia() + " || ESPESSURA: " + materiaisFace.getEspessura());
            }

        } catch (Exception e) {
            log.get_log(e.toString());
        }
    }

}
