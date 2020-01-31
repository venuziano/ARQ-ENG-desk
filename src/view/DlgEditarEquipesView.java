/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import apoio.GerenciarLog;
import dao.EquipesDAO;
import dao.UsuariosDAO;
import entidade.Equipes;
import entidade.Usuarios;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author mf
 */
public class DlgEditarEquipesView extends javax.swing.JDialog {

    EntityManagerFactory factory = Persistence.createEntityManagerFactory("ARQ_ENGPU");
    EquipesDAO daoe = new EquipesDAO(factory);
    UsuariosDAO daou = new UsuariosDAO(factory);
    Equipes e;
    GerenciarLog log = new GerenciarLog();
    
    public DlgEditarEquipesView(java.awt.Frame parent, boolean modal, Equipes equipe) throws IOException {
        super(parent, modal);
        initComponents();
        this.e = equipe;
        tfdNomeEquipe.setText(e.getNome());
        taInfo.setText(e.getInfo());
        renderizarTabelaParticipantes();
        renderizarTabelaUsuarios();
        
        System.out.println("QUALÈQUIÉ PARCEROO: " + e.getUsuariosCollection());
        
    }
    
    private void atualizar() throws Exception {
        try {
            e.setNome(tfdNomeEquipe.getText());
            e.setInfo(taInfo.getText());
            daoe.atualizar(e);
            JOptionPane.showMessageDialog(this, "Atualização efetuada com sucesso!");
            
        } catch (Exception e){
            log.get_log(e.toString());
        }
        
    }
    
    private void montaTabelaParticipantes() throws IOException {
        DefaultTableModel tabela = (DefaultTableModel) tbParticipantes.getModel();

        tabela.setNumRows(0);

        try {
            for (Usuarios u : e.getUsuariosCollection()) {
                tabela.addRow(new Object[]{
                    u.getId(),
                    u.getNome(),
                });
            }
        } catch (Exception e) {
            log.get_log(e.toString());
        }
    }
    
    public void renderizarTabelaParticipantes() throws IOException {
        DefaultTableModel tabela = (DefaultTableModel) tbParticipantes.getModel();

        tabela.setNumRows(0);

        tbParticipantes.setRowSorter(new TableRowSorter(tabela));
        tbParticipantes.setDefaultEditor(Object.class, null);

        montaTabelaParticipantes();
    }
    
    private void montaTabelaUsuarios() throws IOException {
        DefaultTableModel tabela = (DefaultTableModel) tbUsuarios.getModel();

        tabela.setNumRows(0);

        try {
            for (Usuarios u : daou.findUsuariosEntities()) {
                tabela.addRow(new Object[]{
                    u.getId(),
                    u.getNome(),
                    
                });
            }
        } catch (Exception e) {
            log.get_log(e.toString());
        }
    }
    
    public void renderizarTabelaUsuarios() throws IOException {
        DefaultTableModel tabela = (DefaultTableModel) tbUsuarios.getModel();

        tabela.setNumRows(0);

        tbUsuarios.setRowSorter(new TableRowSorter(tabela));
        tbUsuarios.setDefaultEditor(Object.class, null);

        montaTabelaUsuarios();
    }
    
    private void pesquisar(String desc) throws IOException {
        DefaultTableModel tabela = (DefaultTableModel) tbUsuarios.getModel();

        tabela.setNumRows(0);

        try {
            for (Usuarios c : daou.buscaUsuarios(desc)) {
                tabela.addRow(new Object[]{
                    c.getId(),
                    c.getNome(),
                });
            }
        } catch (Exception e) {
            log.get_log(e.toString());
        }

    }
    
    private void adicionarUsuarioEquipe() throws IOException {
        int idUsuario = (int) tbUsuarios.getValueAt(tbUsuarios.getSelectedRow(), 0);
        Usuarios u = daou.findUsuario(idUsuario);
        try {
            for (Usuarios ue : e.getUsuariosCollection()){
                if (ue.getId().equals(u.getId())){
                    JOptionPane.showMessageDialog(this, "Usuário selecionado já pertence a equipe");
                    return;
                }
            } 
            
            e.getUsuariosCollection().add(u);
            
        } catch(Exception e) {
            System.out.println(e);
        }
    }
    
    private void removerUsuarioEquipe() throws IOException {
        int idUsuario = (int) tbParticipantes.getValueAt(tbParticipantes.getSelectedRow(), 0);
        Usuarios u = daou.findUsuario(idUsuario);
        e.getUsuariosCollection().remove(u);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tbParticipantes = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btAdicionar = new javax.swing.JButton();
        btRemover = new javax.swing.JButton();
        btFechar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbUsuarios = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        btPesquisar = new javax.swing.JButton();
        tfdNome = new javax.swing.JTextField();
        btSalvar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        taInfo = new javax.swing.JTextArea();
        tfdNomeEquipe = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gerenciar Equipe");

        tbParticipantes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Código", "Nome"
            }
        ));
        jScrollPane1.setViewportView(tbParticipantes);

        jLabel1.setText("Participantes:");

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setText("Editar Equipe");

        jLabel3.setText("Nome da Equipe:*");

        btAdicionar.setText("Adicionar");
        btAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAdicionarActionPerformed(evt);
            }
        });

        btRemover.setText("Remover");
        btRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRemoverActionPerformed(evt);
            }
        });

        btFechar.setText("Fechar");
        btFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btFecharActionPerformed(evt);
            }
        });

        tbUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Código", "Nome"
            }
        ));
        jScrollPane2.setViewportView(tbUsuarios);

        jLabel5.setText("Lista de Usuários:");

        btPesquisar.setText("Pesquisar");
        btPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btPesquisarActionPerformed(evt);
            }
        });

        btSalvar.setText("Salvar");
        btSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSalvarActionPerformed(evt);
            }
        });

        jLabel4.setText("Descrição:");

        taInfo.setColumns(20);
        taInfo.setRows(5);
        jScrollPane3.setViewportView(taInfo);

        jLabel6.setText("Buscar usuários:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(528, 528, 528)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btSalvar)
                                .addGap(18, 18, 18)
                                .addComponent(btFechar))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jLabel4)
                                                    .addComponent(jLabel3))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jScrollPane3)
                                                    .addComponent(tfdNomeEquipe))))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(btAdicionar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(btRemover, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel5)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(jLabel6)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(tfdNome, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btPesquisar)))))))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(tfdNomeEquipe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btPesquisar)
                                    .addComponent(tfdNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6))
                                .addGap(18, 18, 18)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel5))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(93, 93, 93)
                        .addComponent(btAdicionar)
                        .addGap(18, 18, 18)
                        .addComponent(btRemover)))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btFechar)
                    .addComponent(btSalvar))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btPesquisarActionPerformed
        try {
            pesquisar(tfdNome.getText());
        } catch (IOException ex) {
            Logger.getLogger(UsuariosView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btPesquisarActionPerformed

    private void btFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFecharActionPerformed
        this.dispose();
    }//GEN-LAST:event_btFecharActionPerformed

    private void btAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAdicionarActionPerformed
        try {
            adicionarUsuarioEquipe();
            renderizarTabelaParticipantes();
        } catch (IOException ex) {
            Logger.getLogger(DlgEditarEquipesView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btAdicionarActionPerformed

    private void btRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRemoverActionPerformed
        try {
            removerUsuarioEquipe();
            renderizarTabelaParticipantes();
        } catch (IOException ex) {
            Logger.getLogger(DlgEditarEquipesView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btRemoverActionPerformed

    private void btSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSalvarActionPerformed
        if (!tfdNomeEquipe.getText().isEmpty()){
            try {
                atualizar();
            } catch (Exception ex) {
                Logger.getLogger(DlgEditarEquipesView.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Obrigatório inserir um nome para a equipe");
            tfdNomeEquipe.requestFocus();
        }
    }//GEN-LAST:event_btSalvarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAdicionar;
    private javax.swing.JButton btFechar;
    private javax.swing.JButton btPesquisar;
    private javax.swing.JButton btRemover;
    private javax.swing.JButton btSalvar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea taInfo;
    private javax.swing.JTable tbParticipantes;
    private javax.swing.JTable tbUsuarios;
    private javax.swing.JTextField tfdNome;
    private javax.swing.JTextField tfdNomeEquipe;
    // End of variables declaration//GEN-END:variables
}
