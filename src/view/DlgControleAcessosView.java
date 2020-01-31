/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import apoio.GerenciarLog;
import dao.PermissoesDAO;
import dao.UsuariosDAO;
import entidade.Permissoes;
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
public class DlgControleAcessosView extends javax.swing.JDialog {

    EntityManagerFactory factory = Persistence.createEntityManagerFactory("ARQ_ENGPU");
    UsuariosDAO daou = new UsuariosDAO(factory);
    PermissoesDAO daop = new PermissoesDAO(factory);
    Usuarios usuario;
    GerenciarLog log = new GerenciarLog();
    
    public DlgControleAcessosView(java.awt.Frame parent, boolean modal, Usuarios usuario) throws IOException {
        super(parent, modal);
        this.usuario = usuario;
        this.usuario.setPermissoesCollection(daop.buscaPermissoesUsuario(this.usuario.getId()));
        initComponents();
        
        carregaTelas();
        lbNomeUsuario.setText(usuario.getNome());
    }

    public void carregaTelas() throws IOException {
        
        try {
            for (String s : daop.findTelas()) {

                cbTelas.addItem(s);
            }
        } catch (Exception e) {
            log.get_log(e.toString());
        }
    }
    
    void renderizarTabela() throws IOException {
        DefaultTableModel tabela = (DefaultTableModel) tbPermissoes.getModel();

        tabela.setNumRows(0);

        tbPermissoes.setRowSorter(new TableRowSorter(tabela));
        tbPermissoes.setDefaultEditor(Object.class, null);

        montaTabela();
    }
    
    private void montaTabela() throws IOException {
        DefaultTableModel tabela = (DefaultTableModel) tbPermissoes.getModel();

        tabela.setNumRows(0);

        try {
            for (Permissoes p : this.usuario.getPermissoesCollection()) {
                
                if( p.getOrigem().equals((String)cbTelas.getSelectedItem())) {
                
                tabela.addRow(new Object[]{
                    p.getId(),
                    p.getAcao(),
                    p.getStatus()
                });
                }
            }
        } catch (Exception e) {
            log.get_log(e.toString());
        }
    }
    
    private void permitirUsuario() throws  Exception {
        try {
            if (tbPermissoes.getSelectedRow() != 1) {
                for (Permissoes p : this.usuario.getPermissoesCollection()){
                    if (p.getId() == (int) tbPermissoes.getValueAt(tbPermissoes.getSelectedRow(), 0)){
                        p.setStatus(1);
                        break;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um item da lista!");
            }
            
            JOptionPane.showMessageDialog(this, "Permissão efetuada");
            
        } catch (Exception e) {
            log.get_log(e.toString());
        }
    }
    
    private void negarUsuario() throws  Exception {
        try {
            if (tbPermissoes.getSelectedRow() != 1) {
                for (Permissoes p : this.usuario.getPermissoesCollection()){
                    if (p.getId() == (int) tbPermissoes.getValueAt(tbPermissoes.getSelectedRow(), 0)){
                        p.setStatus(0);
                        break;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um item da lista!");
            }
            
            JOptionPane.showMessageDialog(this, "Permissão negada");
            
        } catch (Exception e) {
            log.get_log(e.toString());
        }
    }
    
    private void atualizarUsuario() throws Exception {
        daou.atualizar(this.usuario);
        JOptionPane.showMessageDialog(this, "Permissões do usuário foram atualizadas");
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        lbNomeUsuario = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbPermissoes = new javax.swing.JTable();
        cbTelas = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btFechar = new javax.swing.JButton();
        brSalvar = new javax.swing.JButton();
        btPermitir = new javax.swing.JButton();
        btNegar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Controle de Acesso");

        jLabel1.setText("Usuário:");

        tbPermissoes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Cód.", "Descrição", "Status"
            }
        ));
        jScrollPane1.setViewportView(tbPermissoes);

        cbTelas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione" }));
        cbTelas.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbTelasItemStateChanged(evt);
            }
        });

        jLabel2.setText("Telas:");

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel3.setText("Controle de Acesso do Usuário");
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        btFechar.setText("Fechar");
        btFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btFecharActionPerformed(evt);
            }
        });

        brSalvar.setText("Salvar");
        brSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                brSalvarActionPerformed(evt);
            }
        });

        btPermitir.setText("Permitir");
        btPermitir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btPermitirActionPerformed(evt);
            }
        });

        btNegar.setText("Negar");
        btNegar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btNegarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbNomeUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                        .addGap(653, 653, 653))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbTelas, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btPermitir)
                            .addComponent(btNegar))
                        .addGap(64, 64, 64))))
            .addGroup(layout.createSequentialGroup()
                .addGap(306, 306, 306)
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(303, 303, 303))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(brSalvar)
                .addGap(18, 18, 18)
                .addComponent(btFechar)
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btNegar, btPermitir});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(lbNomeUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cbTelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btFechar)
                            .addComponent(brSalvar)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btPermitir)
                        .addGap(18, 18, 18)
                        .addComponent(btNegar)))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cbTelasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbTelasItemStateChanged
        try {
            renderizarTabela();
        } catch (IOException ex) {
            Logger.getLogger(DlgControleAcessosView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_cbTelasItemStateChanged

    private void btPermitirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btPermitirActionPerformed
        try {
            permitirUsuario();
            renderizarTabela();
        } catch (Exception ex) {
            Logger.getLogger(DlgControleAcessosView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btPermitirActionPerformed

    private void btNegarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btNegarActionPerformed
        try {
            negarUsuario();
            renderizarTabela();
        } catch (Exception ex) {
            Logger.getLogger(DlgControleAcessosView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btNegarActionPerformed

    private void btFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFecharActionPerformed
        this.dispose();
    }//GEN-LAST:event_btFecharActionPerformed

    private void brSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_brSalvarActionPerformed
        try {
            atualizarUsuario();
        } catch (Exception ex) {
            Logger.getLogger(DlgControleAcessosView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_brSalvarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton brSalvar;
    private javax.swing.JButton btFechar;
    private javax.swing.JButton btNegar;
    private javax.swing.JButton btPermitir;
    private javax.swing.JComboBox<String> cbTelas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbNomeUsuario;
    private javax.swing.JTable tbPermissoes;
    // End of variables declaration//GEN-END:variables
}
