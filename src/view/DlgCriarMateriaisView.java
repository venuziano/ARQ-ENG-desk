/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import apoio.GerenciarLog;
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
public class DlgCriarMateriaisView extends javax.swing.JDialog {

    EntityManagerFactory factory = Persistence.createEntityManagerFactory("ARQ_ENGPU");
    
    MateriaisDAO dao = new MateriaisDAO(factory);
    
    DlgMateriaisView dlgMateriais;
    
    GerenciarLog log = new GerenciarLog();
    
    /** Creates new form DlgCriarEquipeView */
    public DlgCriarMateriaisView(java.awt.Frame parent, boolean modal, DlgMateriaisView dlgMateriais) throws IOException {
        super(parent, modal);
        initComponents();
        
        this.dlgMateriais = dlgMateriais;
        
        tfCondutividade.setEnabled(false);
        
    }

    private void salvar() throws Exception {
        try {
                Materiais materiais = new Materiais();
                materiais.setNome(tfNome.getText());
                materiais.setInfo(tfInfo.getText());
                materiais.setSituacao(1);
                if(rbTransparente.isSelected())
                {
                    materiais.setTipo(0);
                }   else {
                    materiais.setTipo(1);
                    materiais.setCondutividade(Double.parseDouble(tfCondutividade.getText()));
                }
                dao.salvar(materiais);
                JOptionPane.showMessageDialog(this, "Cadastro efetuado com sucesso!");
        } catch (Exception e) {
            log.get_log(e.toString());
        }

    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        btCancelar = new javax.swing.JButton();
        btOk = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        tfNome = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tfInfo = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        tfCondutividade = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        rbOpaco = new javax.swing.JRadioButton();
        rbTransparente = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Editor de cadastro de material");

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel1.setText("Novo Material");

        btCancelar.setText("Cancelar");
        btCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelarActionPerformed(evt);
            }
        });

        btOk.setText("Ok");
        btOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btOkActionPerformed(evt);
            }
        });

        jLabel2.setText("Nome:*");

        tfNome.setToolTipText("");

        tfInfo.setColumns(20);
        tfInfo.setRows(5);
        jScrollPane3.setViewportView(tfInfo);

        jLabel5.setText("Informações gerais:");

        jLabel3.setText("Condutividade:*");

        tfCondutividade.setToolTipText("");

        jLabel10.setText("Tipo:*");

        rbOpaco.setText("Opaco");
        rbOpaco.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rbOpacoMouseClicked(evt);
            }
        });

        rbTransparente.setText("Transparente");
        rbTransparente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbTransparenteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(308, 308, 308)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel3))
                                .addGap(31, 31, 31)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tfCondutividade, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(rbOpaco)
                                        .addGap(18, 18, 18)
                                        .addComponent(rbTransparente))
                                    .addComponent(tfNome, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE))))
                        .addGap(0, 324, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btOk, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btCancelar)
                .addGap(13, 13, 13))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tfNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(rbOpaco)
                    .addComponent(rbTransparente))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(tfCondutividade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btOk)
                    .addComponent(btCancelar))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelarActionPerformed
        this.dispose();
        try {
            dlgMateriais.renderizarTabela();
        } catch (IOException ex) {
            Logger.getLogger(DlgCriarMateriaisView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btCancelarActionPerformed

    private void btOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btOkActionPerformed
        try {
            if (tfNome.getText().length() != 0) {
                salvar();
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Preencha os campos corretamente!");
            }
        } catch (Exception ex) {
            Logger.getLogger(DlgMateriaisView.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            dlgMateriais.renderizarTabela();
        } catch (IOException ex) {
            Logger.getLogger(DlgCriarMateriaisView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btOkActionPerformed

    private void rbOpacoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rbOpacoMouseClicked
        rbTransparente.setSelected(false);
        tfCondutividade.setEnabled(true);
    }//GEN-LAST:event_rbOpacoMouseClicked

    private void rbTransparenteActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_rbTransparenteActionPerformed
    {//GEN-HEADEREND:event_rbTransparenteActionPerformed
        rbOpaco.setSelected(false);
        tfCondutividade.setEnabled(false);
    }//GEN-LAST:event_rbTransparenteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCancelar;
    private javax.swing.JButton btOk;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JRadioButton rbOpaco;
    private javax.swing.JRadioButton rbTransparente;
    private javax.swing.JTextField tfCondutividade;
    private javax.swing.JTextArea tfInfo;
    private javax.swing.JTextField tfNome;
    // End of variables declaration//GEN-END:variables

}
