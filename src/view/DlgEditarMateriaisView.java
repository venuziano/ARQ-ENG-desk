/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import apoio.GerenciarLog;
import dao.AuditoriaDAO;
import dao.EquipesDAO;
import dao.MateriaisDAO;
import dao.UsuariosDAO;
import entidade.Equipes;
import entidade.Materiais;
import entidade.Usuarios;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;

/**
 *
 * @author mf
 */
public class DlgEditarMateriaisView extends javax.swing.JDialog {

    EntityManagerFactory factory = Persistence.createEntityManagerFactory("ARQ_ENGPU");
//    EquipesDAO daoe = new EquipesDAO(factory);
//    UsuariosDAO daou = new UsuariosDAO(factory);
//    Equipes e;
    GerenciarLog log = new GerenciarLog();

    MateriaisDAO dao = new MateriaisDAO(factory);

//    AuditoriaDAO daoA = new AuditoriaDAO(factory);

    Materiais materiais;
    
    DlgMateriaisView dlgMateriais ;
    
    public DlgEditarMateriaisView(java.awt.Frame parent, boolean modal, Materiais materiais, DlgMateriaisView viwe) throws IOException {
        super(parent, modal);
        initComponents();
        this.dlgMateriais = viwe;
        this.materiais = materiais;
        tfNome.setText(materiais.getNome());
        tfCondutividade.setText(String.valueOf(materiais.getCondutividade()));
        tfInfo.setText(materiais.getInfo());
        if(materiais.getTipo() == 1)
        {
            rbOpaco.setSelected(true);
        }else
        {
            rbTransparente.setSelected(true);
        }
    }

    private void atualizar() throws Exception {
        try {
                Materiais m = new Materiais();
                m.setId(this.materiais.getId());
                m.setNome(tfNome.getText());
                m.setCondutividade(Double.parseDouble(tfCondutividade.getText()));
                m.setInfo(tfInfo.getText());
                m.setSituacao(1);
                if(rbOpaco.isSelected())
                {
                    m.setTipo(1);
                }else
                {
                    m.setTipo(0);
                    materiais.setCondutividade(Double.parseDouble(tfCondutividade.getText()));
                }
                dao.atualizar(m);
                JOptionPane.showMessageDialog(this, "Atualização efetuada com sucesso!");
            }
         catch (Exception e) {
    }
}
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        btFechar = new javax.swing.JButton();
        btSalvar = new javax.swing.JButton();
        tfNome = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tfInfo = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        tfCondutividade = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        rbOpaco = new javax.swing.JRadioButton();
        rbTransparente = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Editor de Projetos");

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setText("Editar Material");

        btFechar.setText("Fechar");
        btFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btFecharActionPerformed(evt);
            }
        });

        btSalvar.setText("Salvar");
        btSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSalvarActionPerformed(evt);
            }
        });

        tfNome.setToolTipText("");

        jLabel8.setText("Nome: *");

        tfInfo.setColumns(20);
        tfInfo.setRows(5);
        jScrollPane3.setViewportView(tfInfo);

        jLabel5.setText("Informações gerais:");

        jLabel9.setText("Condutividade: *");

        tfCondutividade.setToolTipText("");

        jLabel10.setText("Tipo* :");

        rbOpaco.setText("Opaco");
        rbOpaco.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rbOpacoMouseClicked(evt);
            }
        });

        rbTransparente.setText("Transparente");
        rbTransparente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rbTransparenteMouseClicked(evt);
            }
        });
        rbTransparente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbTransparenteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btSalvar)
                        .addGap(18, 18, 18)
                        .addComponent(btFechar))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addGap(63, 63, 63)
                                        .addComponent(tfNome, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel5)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addGap(22, 22, 22)
                                        .addComponent(tfCondutividade, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel10)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(100, 100, 100)
                                        .addComponent(rbOpaco)
                                        .addGap(18, 18, 18)
                                        .addComponent(rbTransparente)))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 313, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(307, 307, 307))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tfNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rbOpaco)
                        .addComponent(rbTransparente)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tfCondutividade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btSalvar)
                    .addComponent(btFechar))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFecharActionPerformed
        this.dispose();
        try {
            dlgMateriais.renderizarTabela();
        } catch (IOException ex) {
            Logger.getLogger(DlgCriarMateriaisView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btFecharActionPerformed

    private void btSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSalvarActionPerformed
        if (!tfNome.getText().isEmpty()){
            try {
                atualizar();
            } catch (Exception ex) {
                Logger.getLogger(DlgEditarMateriaisView.class.getName()).log(Level.SEVERE, null, ex);
                try {
                    log.get_log(ex.toString());
                } catch (IOException ex1) {
                    Logger.getLogger(DlgEditarMateriaisView.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Obrigatório inserir um nome");
            tfNome.requestFocus();
        }
        try {
            dlgMateriais.renderizarTabela();
        } catch (IOException ex) {
            Logger.getLogger(DlgEditarMateriaisView.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dispose();
    }//GEN-LAST:event_btSalvarActionPerformed

    private void rbTransparenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbTransparenteActionPerformed
    }//GEN-LAST:event_rbTransparenteActionPerformed

    private void rbTransparenteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rbTransparenteMouseClicked
        rbOpaco.setSelected(false);
        tfCondutividade.setEnabled(false);
    }//GEN-LAST:event_rbTransparenteMouseClicked

    private void rbOpacoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rbOpacoMouseClicked
        rbTransparente.setSelected(false);
        tfCondutividade.setEnabled(true);
    }//GEN-LAST:event_rbOpacoMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btFechar;
    private javax.swing.JButton btSalvar;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JRadioButton rbOpaco;
    private javax.swing.JRadioButton rbTransparente;
    private javax.swing.JTextField tfCondutividade;
    private javax.swing.JTextArea tfInfo;
    private javax.swing.JTextField tfNome;
    // End of variables declaration//GEN-END:variables
}
