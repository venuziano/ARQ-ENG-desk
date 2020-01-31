/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackupRestore;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import javax.mail.Session;
import javax.swing.text.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultElement;

/**
 *
 * @author GUSTAVO
 */
public class Restore {

    public static String cmd(String command) throws IOException {
        ProcessBuilder builder = new ProcessBuilder(
                "cmd.exe", "/c", command);
        builder.redirectErrorStream(true);
        Process p = builder.start();
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        line = r.readLine();

        System.out.println("comando finalizado");
        return line;
    }

    /**
     * Restore database.
     *
     * @warning for this to succeed the constraints must allow null
     * @param path backup folder
     */
    public synchronized static String execCommand(final String commandLine) throws IOException {

        boolean success = false;
        String result;

        Process p;
        BufferedReader input;
        StringBuffer cmdOut = new StringBuffer();
        String lineOut = null;
        int numberOfOutline = 0;

        try {

            p = Runtime.getRuntime().exec(commandLine);

            input = new BufferedReader(new InputStreamReader(p.getInputStream()));

            while ((lineOut = input.readLine()) != null) {
                if (numberOfOutline > 0) {
                    cmdOut.append("\n");
                }
                cmdOut.append(lineOut);
                numberOfOutline++;
            }

            result = cmdOut.toString();

            success = true;

            input.close();

        } catch (IOException e) {
            e.printStackTrace();
            result = String.format("Falha ao executar comando %s. Erro: %s", commandLine, e.toString());
        }

        // Se não executou com sucesso, lança a falha
        if (!success) {
            throw new IOException(result);
        }

        return result;

    }
}
