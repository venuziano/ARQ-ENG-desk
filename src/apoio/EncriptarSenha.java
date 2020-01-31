/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apoio;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mf
 */
public class EncriptarSenha {
    
    private MessageDigest md;
    private byte messageDigest[];
    private StringBuilder sb;
    private String senhaHex;

    public void setSenhaHex(String senha)
    {
        sb = new StringBuilder();
        
        try {
            md = MessageDigest.getInstance("SHA-256");
            messageDigest = md.digest(senha.getBytes("UTF-8"));
            
            for( byte b : messageDigest){
                sb.append(String.format("%02X", 0xFF & b));
            }
            
            senhaHex = sb.toString();
            
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex)
        {
            Logger.getLogger(EncriptarSenha.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public String getSenhaHex(){
        return this.senhaHex;
    }
}
