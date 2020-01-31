/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apoio;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

/**
 *
 * @author User
 */
public class EnviarEmail
{
    public void enviarComAnexo(String destinatario, String assunto, String conteudo, String caminho, String descicaoArquivo, String nomeArquivo){
        MultiPartEmail email =  new MultiPartEmail();
        email.setHostName("smtp.gmail.com");
	email.setSmtpPort(465);
        email.setSSL(true);
        email.setStartTLSEnabled(true);
        email.setStartTLSRequired(true);
        email.setSSLOnConnect(true);
        
        email.setAuthenticator(new DefaultAuthenticator("integrador.arqeng@gmail.com","arqeng1234"));
        
        try {
            //Anexa o Arquivo
            EmailAttachment anexo = new EmailAttachment();
            anexo.setPath(caminho);
            anexo.setName(nomeArquivo);
            anexo.setDescription(descicaoArquivo);
            anexo.setDisposition(EmailAttachment.ATTACHMENT);
            
            email.setFrom("integrador.arqeng@gmail.com");
            email.setSubject(assunto);
            email.setMsg(conteudo);
            email.addTo(destinatario);
            email.attach(anexo);
            
            email.send();
                
        } catch (EmailException e) {
            Logger.getLogger(EnviarEmail.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public void enviarSemAnexo(String destinatario, String assunto, String conteudo){
        MultiPartEmail email =  new MultiPartEmail();
        email.setHostName("smtp.gmail.com");
	email.setSmtpPort(465);
        email.setStartTLSRequired(true);
        email.setStartTLSEnabled(true);
        email.setSSLOnConnect(true);
        
        email.setAuthenticator(new DefaultAuthenticator("integrador.arqeng@gmail.com","arqeng1234"));
        
        try {
            
            email.setFrom("integrador.arqeng@gmail.com");
            email.setSubject(assunto);
            email.setMsg(conteudo);
            email.addTo(destinatario);
            
            email.send();
                
        } catch (EmailException e) {
            Logger.getLogger(EnviarEmail.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
