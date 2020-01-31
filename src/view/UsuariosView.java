/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import apoio.EncriptarSenha;
import apoio.GerenciarAuditoria;
import apoio.GerenciarLog;
import com.sun.org.apache.xerces.internal.impl.dv.xs.DecimalDV;
import dao.UsuariosDAO;
import dao.UsuariosJpaController;
import entidade.Auditoria;
import entidade.Usuarios;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author rrs
 */
public class UsuariosView extends javax.swing.JDialog {

    EntityManagerFactory factory = Persistence.createEntityManagerFactory("ARQ_ENGPU");
    UsuariosDAO dao = new UsuariosDAO(factory);
    GerenciarLog log = new GerenciarLog();

    public UsuariosView(java.awt.Frame parent, boolean modal) throws IOException {
        super(parent, modal);
        initComponents();
        renderizarTabela();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    private void salvar() throws Exception {
        try {
            if (tfNome.getText().length() != 0) {
                Usuarios usuarios = new Usuarios();
                EncriptarSenha senha = new EncriptarSenha();
                senha.setSenhaHex(Arrays.toString(tfSenha.getPassword()));

                usuarios.setNome(tfNome.getText());
                usuarios.setLogin(tfLogin.getText());
                usuarios.setSenha(senha.getSenhaHex());

                dao.salvar(usuarios);

                JOptionPane.showMessageDialog(this, "Cadastro efetuado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(this, "Preencha os campos corretamente!");
            }
        } catch (Exception e) {
            log.get_log(e.toString());
        }

    }

    private void atualizar() throws Exception {
        try {
            if (tbUsuarios.getSelectedRow() != -1) {

                Usuarios usuarios = new Usuarios();
                EncriptarSenha senha = new EncriptarSenha();
                senha.setSenhaHex(Arrays.toString(tfSenha.getPassword()));

                usuarios.setId((int) tbUsuarios.getValueAt(tbUsuarios.getSelectedRow(), 0));
                usuarios.setNome(tfNome.getText());
                usuarios.setLogin(tfLogin.getText());
                usuarios.setSenha(senha.getSenhaHex());

                dao.atualizar(usuarios);

                JOptionPane.showMessageDialog(this, "Atualização efetuada com sucesso!");
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um item da lista!");
            }
        } catch (Exception e) {
            log.get_log(e.toString());
        }
    }

    private void excluir() throws Exception {
        try {
            if (tbUsuarios.getSelectedRow() != -1) {
                int confirm = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir?");
                if (confirm == JOptionPane.YES_OPTION) {

                    Usuarios usuarios = new Usuarios();

                    EncriptarSenha senha = new EncriptarSenha();
                    senha.setSenhaHex(Arrays.toString(tfSenha.getPassword()));

                    usuarios.setId((int) tbUsuarios.getValueAt(tbUsuarios.getSelectedRow(), 0));
                    usuarios.setNome(tfNome.getText());
                    usuarios.setLogin(tfLogin.getText());
                    usuarios.setSenha(senha.getSenhaHex());

                    int id = ((int) tbUsuarios.getValueAt(tbUsuarios.getSelectedRow(), 0));
                    dao.excluir(usuarios, id);

                    JOptionPane.showMessageDialog(this, "Cadastro excluido com sucesso!");

                    limpaCampos();

                }

                if (confirm == JOptionPane.NO_OPTION) {

                    limpaCampos();
                }

                if (confirm == JOptionPane.CANCEL_OPTION) {

                    limpaCampos();

                }

            } else {
                JOptionPane.showMessageDialog(this, "Selecione um item da tabela!");

            }
        } catch (Exception e) {
            log.get_log(e.toString());
        }
    }

    private void renderizarTabela() throws IOException {
        DefaultTableModel tabela = (DefaultTableModel) tbUsuarios.getModel();

        tabela.setNumRows(0);

        tbUsuarios.setRowSorter(new TableRowSorter(tabela));
        tbUsuarios.setDefaultEditor(Object.class, null);

        montaTabela();
    }

    private void montaTabela() throws IOException {
        DefaultTableModel tabela = (DefaultTableModel) tbUsuarios.getModel();

        tabela.setNumRows(0);

        UsuariosJpaController dao = new UsuariosJpaController(factory);

        try {
            for (Usuarios b : dao.findUsuariosEntities()) {
                tabela.addRow(new Object[]{
                    b.getId(),
                    b.getNome(),
                    b.getLogin()

                });
            }
        } catch (Exception e) {
            log.get_log(e.toString());
        }
    }

    private void pesquisar(String desc) throws IOException {
        DefaultTableModel tabela = (DefaultTableModel) tbUsuarios.getModel();

        tabela.setNumRows(0);

        UsuariosJpaController dao = new UsuariosJpaController(factory);

        try {
            for (Usuarios c : dao.buscaUsuarios(desc)) {
                tabela.addRow(new Object[]{
                    c.getId(),
                    c.getNome(),
                    c.getLogin()

                });
            }
        } catch (Exception e) {
            log.get_log(e.toString());
        }

    }

    private void populaInputs() {
        if (tbUsuarios.getSelectedRow() != -1) {
            tfNome.setText(tbUsuarios.getValueAt(tbUsuarios.getSelectedRow(), 1).toString());
            tfLogin.setText(tbUsuarios.getValueAt(tbUsuarios.getSelectedRow(), 2).toString());
        }
    }

    public void limpaCampos() throws IOException {

        tfNome.setText("");
        tfLogin.setText("");
        tfSenha.setText("");

        montaTabela();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tfNome = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btSalvar = new javax.swing.JButton();
        btPesquisar = new javax.swing.JButton();
        btAtualizar = new javax.swing.JButton();
        btExcluir = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbUsuarios = new javax.swing.JTable();
        btSair = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        tfPesquisar = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        tfLogin = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        tfSenha = new javax.swing.JPasswordField();
        btControleAcessos = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tfNome.setToolTipText("Informe o nome da cidade");

        jLabel2.setText("Nome: *");

        btSalvar.setText("Novo");
        btSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSalvarActionPerformed(evt);
            }
        });

        btPesquisar.setText("pesquisar");
        btPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btPesquisarActionPerformed(evt);
            }
        });

        btAtualizar.setText("Atualizar");
        btAtualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAtualizarActionPerformed(evt);
            }
        });

        btExcluir.setText("Excluir");
        btExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btExcluirActionPerformed(evt);
            }
        });

        tbUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Id", "Nome", "Login", "Senha"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbUsuariosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbUsuarios);

        btSair.setText("Sair");
        btSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSairActionPerformed(evt);
            }
        });

        jLabel1.setText("Buscar:");

        jLabel3.setText("Login: *");

        jLabel5.setText("Senha: *");

        btControleAcessos.setText("Controle de acesso do usuário");
        btControleAcessos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btControleAcessosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(btAtualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(btExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btSair, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 951, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(1, 1, 1)
                                        .addComponent(jLabel2))
                                    .addComponent(jLabel3))
                                .addGap(29, 29, 29)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tfSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tfLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tfNome, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btControleAcessos))
                                .addGap(0, 513, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(502, 502, 502)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(tfPesquisar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(16, 16, 16)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tfNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(tfLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(tfSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btControleAcessos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(btPesquisar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btExcluir)
                    .addComponent(btAtualizar)
                    .addComponent(btSalvar)
                    .addComponent(btSair))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSalvarActionPerformed
        try {
            salvar();
        } catch (Exception ex) {
            Logger.getLogger(UsuariosView.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            limpaCampos();
        } catch (IOException ex) {
            Logger.getLogger(UsuariosView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btSalvarActionPerformed

    private void btPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btPesquisarActionPerformed
        try {
            pesquisar(tfPesquisar.getText());
        } catch (IOException ex) {
            Logger.getLogger(UsuariosView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btPesquisarActionPerformed

    private void btAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAtualizarActionPerformed
        try {
            atualizar();
        } catch (Exception ex) {
            Logger.getLogger(UsuariosView.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            limpaCampos();
        } catch (IOException ex) {
            Logger.getLogger(UsuariosView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btAtualizarActionPerformed

    private void btExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btExcluirActionPerformed
        try {
            excluir();
        } catch (Exception ex) {
            Logger.getLogger(UsuariosView.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            limpaCampos();
        } catch (IOException ex) {
            Logger.getLogger(UsuariosView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btExcluirActionPerformed

    private void tbUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbUsuariosMouseClicked
        populaInputs();
    }//GEN-LAST:event_tbUsuariosMouseClicked

    private void btSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSairActionPerformed
        this.dispose();
    }//GEN-LAST:event_btSairActionPerformed

    private void btControleAcessosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btControleAcessosActionPerformed
        int id = (int) tbUsuarios.getValueAt(tbUsuarios.getSelectedRow(), 0);
        
        Usuarios usuario = dao.findUsuario(id);
        
        DlgControleAcessosView dlg;
        try {
            dlg = new DlgControleAcessosView (null, true, usuario);
            dlg.setVisible(true);
        } catch (IOException ex) {
            Logger.getLogger(UsuariosView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btControleAcessosActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UsuariosView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UsuariosView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UsuariosView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UsuariosView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                UsuariosView dialog = null;
                try {
                    dialog = new UsuariosView(new javax.swing.JFrame(), true);
                } catch (IOException ex) {
                    Logger.getLogger(UsuariosView.class.getName()).log(Level.SEVERE, null, ex);
                }
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAtualizar;
    private javax.swing.JButton btControleAcessos;
    private javax.swing.JButton btExcluir;
    private javax.swing.JButton btPesquisar;
    private javax.swing.JButton btSair;
    private javax.swing.JButton btSalvar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbUsuarios;
    private javax.swing.JTextField tfLogin;
    private javax.swing.JTextField tfNome;
    private javax.swing.JTextField tfPesquisar;
    private javax.swing.JPasswordField tfSenha;
    // End of variables declaration//GEN-END:variables
}
