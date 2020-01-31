/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import apoio.GerenciarLog;
import dao.AuditoriaDAO;
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
public class DlgEditarProjetosView extends javax.swing.JDialog {

    EntityManagerFactory factory = Persistence.createEntityManagerFactory("ARQ_ENGPU");
    EquipesDAO daoe = new EquipesDAO(factory);
    UsuariosDAO daou = new UsuariosDAO(factory);
    Equipes e;
    GerenciarLog log = new GerenciarLog();
    

    ProjetosDAO dao = new ProjetosDAO(factory);

    AuditoriaDAO daoA = new AuditoriaDAO(factory);

    Usuarios usuarios;
    
    Projetos projetos;
    
    DlgProjetosView dlgProjetos ;
    
    public DlgEditarProjetosView(java.awt.Frame parent, boolean modal, Projetos projetos, DlgProjetosView viwe) throws IOException {
        super(parent, modal);
        initComponents();
        
        this.dlgProjetos = viwe;
        this.projetos = projetos;
        
        carregaEquipes();
        carregaUsuarios();
        
        //carrega as cb's com o devido valor do objeto projetos e viwe
        cbUsuarios.setSelectedItem( projetos.getResponsavel() );
        cbEquipes.setSelectedItem( projetos.getRefEquipe() );
        
        tfNome.setText(projetos.getNome());
        tfInfo.setText(projetos.getInfo());
        jfTdata.setText(projetos.getDataCriacao());
        
    }

    private void atualizar() throws Exception {
        try {

                Projetos projetos = new Projetos();

                Usuarios u = (Usuarios) cbUsuarios.getSelectedItem();
                Equipes e = (Equipes) cbEquipes.getSelectedItem();

                projetos.setId(this.projetos.getId());
                projetos.setNome(tfNome.getText());
                projetos.setInfo(tfInfo.getText());
                projetos.setResponsavel(u);
                projetos.setRefEquipe(e);
                projetos.setSituacao(1);
                projetos.setDataCriacao(this.projetos.getDataCriacao());

                dao.atualizar(projetos);

                JOptionPane.showMessageDialog(this, "Atualização efetuada com sucesso!");

            }
         catch (Exception e) {
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

        jLabel2 = new javax.swing.JLabel();
        btFechar = new javax.swing.JButton();
        btSalvar = new javax.swing.JButton();
        cbEquipes = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cbUsuarios = new javax.swing.JComboBox<>();
        tfNome = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tfInfo = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        jfTdata = new javax.swing.JFormattedTextField();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Editor de Projetos");

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setText("Editar Projeto");

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

        cbEquipes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione" }));
        cbEquipes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbEquipesActionPerformed(evt);
            }
        });

        jLabel7.setText("Equipe:");

        jLabel4.setText("Reponsável:");

        cbUsuarios.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione" }));
        cbUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbUsuariosActionPerformed(evt);
            }
        });

        tfNome.setToolTipText("");

        jLabel8.setText("Nome *");

        tfInfo.setColumns(20);
        tfInfo.setRows(5);
        jScrollPane3.setViewportView(tfInfo);

        jLabel5.setText("Informações gerais:");

        jfTdata.setEditable(false);

        jLabel3.setText("Data Criação*:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(183, 183, 183)
                .addComponent(jLabel2)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btSalvar)
                        .addGap(18, 18, 18)
                        .addComponent(btFechar))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(28, 28, 28)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tfNome, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jfTdata, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(cbEquipes, javax.swing.GroupLayout.Alignment.LEADING, 0, 108, Short.MAX_VALUE))))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 447, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(tfNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cbUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cbEquipes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jfTdata, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btSalvar)
                            .addComponent(btFechar))
                        .addContainerGap())))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFecharActionPerformed
        this.dispose();
        try {
            dlgProjetos.renderizarTabela();
            dlgProjetos.renderizarTabelaComdos();
        } catch (IOException ex) {
            Logger.getLogger(DlgCriarProjetosView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btFecharActionPerformed

    private void btSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSalvarActionPerformed
        if (!tfNome.getText().isEmpty()){
            try {
                atualizar();
            } catch (Exception ex) {
                Logger.getLogger(DlgEditarProjetosView.class.getName()).log(Level.SEVERE, null, ex);
                try {
                    log.get_log(ex.toString());
                } catch (IOException ex1) {
                    Logger.getLogger(DlgEditarProjetosView.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Obrigatório inserir um nome");
            tfNome.requestFocus();
        }
        try {
            dlgProjetos.renderizarTabela();
            dlgProjetos.limpaTabelaComodos();
        } catch (IOException ex) {
            Logger.getLogger(DlgEditarProjetosView.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dispose();
    }//GEN-LAST:event_btSalvarActionPerformed

    private void cbEquipesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbEquipesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbEquipesActionPerformed

    private void cbUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbUsuariosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbUsuariosActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btFechar;
    private javax.swing.JButton btSalvar;
    private javax.swing.JComboBox<Object> cbEquipes;
    private javax.swing.JComboBox<Object> cbUsuarios;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JFormattedTextField jfTdata;
    private javax.swing.JTextArea tfInfo;
    private javax.swing.JTextField tfNome;
    // End of variables declaration//GEN-END:variables
}
