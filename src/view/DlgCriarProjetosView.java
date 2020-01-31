/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import apoio.Formatacao;
import apoio.GerenciarLog;
import dao.EquipesDAO;
import dao.ProjetosDAO;
import dao.UsuariosDAO;
import entidade.Equipes;
import entidade.Projetos;
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
public class DlgCriarProjetosView extends javax.swing.JDialog {

    EntityManagerFactory factory = Persistence.createEntityManagerFactory("ARQ_ENGPU");
    
    ProjetosDAO dao = new ProjetosDAO(factory);
    Formatacao formatacao = new Formatacao();
    
    DlgProjetosView dlgProjetos;
    
    GerenciarLog log = new GerenciarLog();
    
    /** Creates new form DlgCriarEquipeView */
    public DlgCriarProjetosView(java.awt.Frame parent, boolean modal, DlgProjetosView dlgProjetos) throws IOException {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        this.dlgProjetos = dlgProjetos;
        
        carregaUsuarios();
        carregaEquipes();
        
        jfTdata.setEnabled(false);
        String data = formatacao.getDataAtual();
        formatacao.reformatarData(jfTdata);
        jfTdata.setText(data);
    }

    private void salvar() throws Exception {
        try {
                Projetos projetos = new Projetos();

                Usuarios u = (Usuarios) cbUsuarios.getSelectedItem();
                Equipes e = (Equipes) cbEquipes.getSelectedItem();

                projetos.setNome(tfNome.getText());
                projetos.setResponsavel(u);
                projetos.setRefEquipe(e);
                projetos.setInfo(tfInfo.getText());
                projetos.setSituacao(1);
                projetos.setDataCriacao(jfTdata.getText());

                dao.salvar(projetos);

                JOptionPane.showMessageDialog(this, "Cadastro efetuado com sucesso!");

        } catch (Exception e) {
            log.get_log(e.toString());
        }

    }
    
    public void carregaUsuarios() throws IOException {

        UsuariosDAO dao = new UsuariosDAO(factory);

        try {
            for (Usuarios u : dao.findUsuariosEntities()) {

                Usuarios usuarios = new Usuarios();
                usuarios.setId(u.getId());
                usuarios.setNome(u.getNome());

                cbUsuarios.addItem(usuarios);
            }
        } catch (Exception e) {
            log.get_log(e.toString());
        }

    }

    public void carregaEquipes() throws IOException {

        EquipesDAO dao = new EquipesDAO(factory);

        try {
            for (Equipes e : dao.findEquipesEntities()) {

                Equipes equipes = new Equipes();
                equipes.setId(e.getId());
                equipes.setNome(e.getNome());

                cbEquipes.addItem(equipes);
            }
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
        cbUsuarios = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cbEquipes = new javax.swing.JComboBox<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        tfInfo = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jfTdata = new javax.swing.JFormattedTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Editor de cadastro de projeto");

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel1.setText("Novo Projeto");

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

        jLabel2.setText("Nome*");

        tfNome.setToolTipText("");

        cbUsuarios.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione" }));
        cbUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbUsuariosActionPerformed(evt);
            }
        });

        jLabel4.setText("Reponsável*:");

        jLabel7.setText("Equipe*:");

        cbEquipes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione" }));
        cbEquipes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbEquipesActionPerformed(evt);
            }
        });

        tfInfo.setColumns(20);
        tfInfo.setRows(5);
        jScrollPane3.setViewportView(tfInfo);

        jLabel5.setText("Informações gerais:");

        jLabel3.setText("Data Criação*:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(529, Short.MAX_VALUE)
                        .addComponent(btOk, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btCancelar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(30, 30, 30)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cbUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(tfNome, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(jfTdata, javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(cbEquipes, javax.swing.GroupLayout.Alignment.LEADING, 0, 108, Short.MAX_VALUE)))))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(308, 308, 308)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tfNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cbUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cbEquipes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jfTdata, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btCancelar)
                    .addComponent(btOk))
                .addGap(21, 21, 21))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelarActionPerformed
        this.dispose();
        try {
            dlgProjetos.renderizarTabela();
//            dlgProjetos.renderizarTabelaComdos();
        } catch (IOException ex) {
            Logger.getLogger(DlgCriarProjetosView.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(DlgProjetosView.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            dlgProjetos.renderizarTabela();
            dlgProjetos.limpaTabelaComodos();
        } catch (IOException ex) {
            Logger.getLogger(DlgCriarProjetosView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btOkActionPerformed

    private void cbUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbUsuariosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbUsuariosActionPerformed

    private void cbEquipesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbEquipesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbEquipesActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCancelar;
    private javax.swing.JButton btOk;
    private javax.swing.JComboBox<Object> cbEquipes;
    private javax.swing.JComboBox<Object> cbUsuarios;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JFormattedTextField jfTdata;
    private javax.swing.JTextArea tfInfo;
    private javax.swing.JTextField tfNome;
    // End of variables declaration//GEN-END:variables

}
