/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apoio;

import entidade.Usuarios;

/**
 *
 * @author mf
 */
public class UsuarioLogado {
    
    private static Usuarios usuario;
    
    public static void setUsuario(Usuarios usuario){
     UsuarioLogado.usuario = usuario;
    }
    
    public static Usuarios getUsuario(){
     return usuario;
    }
}
