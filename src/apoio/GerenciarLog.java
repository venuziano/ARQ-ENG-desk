package apoio;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author RRS
 */
public class GerenciarLog {
    
        public Logger logger;
        FileHandler fh;
    
    public String get_log (String log) throws IOException{

            File file = new File("src/log/log.txt");
            
            Date data = new Date(System.currentTimeMillis());

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("HORA: " + data + " | ERRO: " + log);
            bw.newLine();
            bw.close();
            
        return log;
        
    }

    public void get_log(Exception e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
