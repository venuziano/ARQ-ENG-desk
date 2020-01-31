/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apoio;

import entidade.Projetos;
import entidade.Usuarios;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mf
 */
public class ConsultaProjetoAuditoria {

    public static ArrayList<Object> arrayProjetos = new ArrayList();

    public static void setArrayProjeto(Object o) {
        ConsultaProjetoAuditoria.arrayProjetos.add(o);
    }

    public static ArrayList getArrayProjeto() {
            return arrayProjetos;
    }

}
