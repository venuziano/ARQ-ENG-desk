/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apoio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author rrs
 */
public class GerenciarAuditoria {

    // 0 = ativa
    // 1 = inativa
    private static String state;
    
    //utilizada como auxílio para gravar o projeto antigo quando atualizar.
    private static String info;

    public static void setSituacao(String state) throws FileNotFoundException, IOException {

        GerenciarAuditoria.state = state;

        Properties prop = new Properties();

        prop.load(new FileInputStream("src/properties/global.properties"));
        prop.setProperty("aud.state", state);
        prop.store(new FileOutputStream("src/properties/global.properties"), null);

    }

    public static String getSituacao() throws FileNotFoundException, IOException {

        Properties prop = new Properties();

        prop.load(new FileInputStream("src/properties/global.properties"));

        GerenciarAuditoria.state = prop.getProperty("aud.state");

        return state;
    }

    public static void setInfo(String info) {
        GerenciarAuditoria.info = info;
    }

    public static String getInfo() {
        return info;
    }

}
