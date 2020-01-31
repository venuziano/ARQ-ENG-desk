/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import BackupRestore.Restore;
import apoio.ConexaoDB;
import dashboards.dashboards;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import view.DlgEquipesView;
import view.DlgFacesView;
import view.DlgLoginView;
import view.DlgMateriaisView;
import view.DlgProjetosView;
import view.FrmTelaPrincipal;
import view.UsuariosView;

/**
 *
 * @author rafael.silva
 */
public class iniciarProjeto {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {

        File file = new File("restore.txt");
        if (file.exists()) {
            JFileChooser seletor = new JFileChooser();
            seletor.setDialogTitle("Procurar Arquivo");
            seletor.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int opcao = seletor.showOpenDialog(null);

            File arquivo = seletor.getSelectedFile();

            Restore restore = new Restore();
            //quando chamo aqui ele trava o banco, preciso fazer um comando para substituir 

            System.out.println("Iniciando restore - Reiniciando servico postgres");
            String retorno = "";
            retorno = restore.cmd("cd C:/Program Files/PostgreSQL/12/bin && pg_ctl.exe -D \"C:/Program Files/PostgreSQL/12/data\" restart");
            System.out.println(retorno);

            System.out.println("Deletando banco de dados");

            retorno = restore.cmd("cd C:/Program Files/PostgreSQL/12/bin && psql.exe --host localhost --port 5432 --username postgres -c \"DROP DATABASE arq_eng\"");
            System.out.println(retorno);
            System.out.println("Criando novo banco de dados");
            retorno = restore.cmd("cd C:/Program Files/PostgreSQL/12/bin && psql.exe --host localhost --port 5432 --username postgres -c \"CREATE DATABASE arq_eng\"");
            System.out.println(retorno);
            System.out.println("Restaurando backup");
            System.out.println("cd C:/Program Files/PostgreSQL/12/bin && psql.exe --host localhost --port 5432 --username postgres --dbname arq_eng \"" + arquivo + "\"");
            retorno = restore.cmd("cd C:/Program Files/PostgreSQL/12/bin && pg_restore.exe --host localhost --port 5432 --username postgres --dbname arq_eng \"" + arquivo + "\"");
            System.out.println(retorno);
            System.out.println("Finalizado");
            file.delete();
            JOptionPane.showMessageDialog(null, "Restore realizado com sucesso.");

        } else if (ConexaoDB.getInstance().getConnection() != null) {
//            DlgMateriaisView uv = new DlgMateriaisView( null, true );
//            uv.setVisible( true );

//            UsuariosView uv = new UsuariosView( null, true );
//            uv.setVisible( true );
//            
            System.out.println("bd");
            DlgLoginView uv = new DlgLoginView(null, true);
            uv.setVisible(true);

//            FrmTelaPrincipal uv = new FrmTelaPrincipal();
//            uv.setVisible( true );
//            DlgEquipesView uv = new DlgEquipesView( null, true );
//            uv.setVisible( true );
//            DlgLoginView uv = new DlgLoginView( null, true );
//            uv.setVisible( true );
        } else {
            JOptionPane.showMessageDialog(null, "Erro ao abrir conexão!");
        }
    }

}
