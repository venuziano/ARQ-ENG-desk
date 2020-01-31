/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import apoio.GerenciarLog;
import dao.AuditoriaDAO;
import dao.ComodosProjetosDAO;
import dao.ProjetosDAO;
import dashboards.dashboards;
import entidade.ComodosProjetos;
import entidade.Projetos;
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
 * @author rrs
 */
public class DlgProjetosView extends javax.swing.JFrame {

    EntityManagerFactory factory = Persistence.createEntityManagerFactory("ARQ_ENGPU");
    ProjetosDAO dao = new ProjetosDAO(factory);
    AuditoriaDAO daoA = new AuditoriaDAO(factory);
    ComodosProjetosDAO daoCp =  new ComodosProjetosDAO(factory);
    
    Usuarios usuarios = new Usuarios();
    Projetos projetos = new Projetos();
    ComodosProjetos comodoProjetos = new ComodosProjetos();
    
    GerenciarLog log = new GerenciarLog();
    
    public DlgProjetosView(java.awt.Frame parent, boolean modal) throws IOException {
        initComponents();
        this.setLocationRelativeTo(null);
        renderizarTabela();
        
        btAdicionarComodo.setEnabled(false);
        btRemoverComodo.setEnabled(false);
        btEditarComodo.setEnabled(false);
        btReabrir.setVisible(false);
        btFinalizar.setVisible(false);
        btAnexos.setVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    private void excluirProjeto() throws Exception {
        try {
            if (tbProjetos.getSelectedRow() != -1) {
               
                int confirm = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir?");
                
                if (confirm == JOptionPane.YES_OPTION) {
                    dao.excluir(projetos, projetos.getId());
                    JOptionPane.showMessageDialog(this, "Cadastro excluido com sucesso!");
                }
                if (confirm == JOptionPane.NO_OPTION) {
                    renderizarTabela();
                    renderizarTabelaComdos();
                }
                if (confirm == JOptionPane.CANCEL_OPTION) {
                    renderizarTabela();
                    renderizarTabelaComdos();
                }
                
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um item da tabela!");

            }
        } catch (Exception e) {
            log.get_log(e.toString());
        }
    }
    
    private void excluirComodo() throws Exception {
        try {
            if (tbComodos.getSelectedRow() != -1) {
                int confirm = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir?");
                if (confirm == JOptionPane.YES_OPTION) {
                    int id = (int) tbComodos.getValueAt(tbComodos.getSelectedRow(), 0);
                    comodoProjetos = daoCp.findComodos(id);
                    comodoProjetos.setRefProjeto(projetos);
                    daoCp.excluir(comodoProjetos, comodoProjetos.getId());
                    JOptionPane.showMessageDialog(this, "Cadastro excluido com sucesso!");
                }
                if (confirm == JOptionPane.NO_OPTION) {
                    renderizarTabela();
                    renderizarTabelaComdos();
                }
                if (confirm == JOptionPane.CANCEL_OPTION) {
                    renderizarTabela();
                    renderizarTabelaComdos();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um Comodo da tabela!");
            }
        } catch (Exception e) {
            log.get_log(e.toString());
        }
    }
    
    private void finalizarProjeto() throws Exception {
        int confirm = JOptionPane.showConfirmDialog(this, "Você está finalizando o projeto!\n" + "Projetos finalizados não podem ser gerenciados.\n" + "Deseja realmente finalizar?");
        try {
            if (confirm == JOptionPane.YES_OPTION) {
                    projetos.setSituacao(2);
                    dao.atualizar(projetos);
                    JOptionPane.showMessageDialog(this, "Projeto finalizado com sucesso!");
                    btFinalizar.setVisible(false);
                    renderizarTabela();
                    renderizarTabelaComdos();
                }
                if (confirm == JOptionPane.NO_OPTION) {
                    renderizarTabela();
                    renderizarTabelaComdos();
                    btFinalizar.setVisible(false);
                }
                if (confirm == JOptionPane.CANCEL_OPTION) {
                    renderizarTabela();
                    renderizarTabelaComdos();
                    btFinalizar.setVisible(false);
                }
        } catch (Exception e) {
            log.get_log(e.toString());
        }
    }
    
    private void reabrirProjeto() throws Exception {
        int confirm = JOptionPane.showConfirmDialog(this, "Deseja reabrir o projeto?");
        try {
            if (confirm == JOptionPane.YES_OPTION) {
                    projetos.setSituacao(1);
                    dao.atualizar(projetos);
                    JOptionPane.showMessageDialog(this, "Projeto reaberto com sucesso!");
                    btReabrir.setVisible(false);
                    renderizarTabela();
                    renderizarTabelaComdos();
                }
                if (confirm == JOptionPane.NO_OPTION) {
                    renderizarTabela();
                    renderizarTabelaComdos();
                    btReabrir.setVisible(false);
                }
                if (confirm == JOptionPane.CANCEL_OPTION) {
                    renderizarTabela();
                    renderizarTabelaComdos();
                    btReabrir.setVisible(false);
                }
        } catch (Exception e) {
            log.get_log(e.toString());
        }
    }
    
    void renderizarTabela() throws IOException {
        DefaultTableModel tabela = (DefaultTableModel) tbProjetos.getModel();

        tabela.setNumRows(0);

        tbProjetos.setRowSorter(new TableRowSorter(tabela));
        tbProjetos.setDefaultEditor(Object.class, null);

        montaTabela();
    }

    private void montaTabela() throws IOException {
        DefaultTableModel tabela = (DefaultTableModel) tbProjetos.getModel();
        tabela.setNumRows(0);

        ProjetosDAO dao = new ProjetosDAO(factory);
        String situacao = "";
        try {
            for (Projetos p : dao.findProjetosEntities()) {
                if(p.getSituacao() == 1)
                    {
                        situacao = "Em Andamento";
                    }else{
                         situacao = "Finalizado";
                         }
                tabela.addRow(new Object[]{
                    p.getId(),
                    p.getNome(),
                    p.getResponsavel(),
                    p.getRefEquipe(),
                    p.getInfo(),
                    situacao,
                    p.getDataCriacao()
                });

            }
        } catch (Exception e) {
            log.get_log(e.toString());
        }
    }
    
     private void montaTabelaComodos() throws IOException {
        DefaultTableModel tabela = (DefaultTableModel) tbComodos.getModel();
        tabela.setNumRows(0);

        this.projetos.setComodosProjetosCollection(daoCp.buscaComodosPorProjetos(projetos.getId()));
            
        try{
            for (ComodosProjetos c : projetos.getComodosProjetosCollection()) {
                tabela.addRow(new Object[]{
                    c.getId(),
                    c.getNome(),
                    c.getCargaTermicaT(),
                    c.getBtus()
                });
            }
        } catch (Exception e) {
            log.get_log(e.toString());
        }

    }
     
     void renderizarTabelaComdos() throws IOException {
        DefaultTableModel tabela = (DefaultTableModel) tbComodos.getModel();
        tabela.setNumRows(0);
        tbComodos.setRowSorter(new TableRowSorter(tabela));
        tbComodos.setDefaultEditor(Object.class, null);
        montaTabelaComodos();
    }
     
    void limpaTabelaComodos(){
        DefaultTableModel tabela = (DefaultTableModel) tbComodos.getModel();
        tabela.setNumRows(0);
        tbComodos.setRowSorter(new TableRowSorter(tabela));
        tbComodos.setDefaultEditor(Object.class, null);
    }

    private void pesquisar(String desc) throws IOException {
        DefaultTableModel tabela = (DefaultTableModel) tbProjetos.getModel();
        tabela.setNumRows(0);
        String situacao = "";
        try {
            for (Projetos p : dao.buscaProjetos(desc)) {
                if(p.getSituacao() == 1)
                    {
                        situacao = "Em Andamento";
                    }else{
                         situacao = "Finalizado";
                         }
                tabela.addRow(new Object[]{
                    p.getId(),
                    p.getNome(),              
                    p.getResponsavel(),
                    p.getRefEquipe(),
                    p.getInfo(),
                    situacao,
                    p.getDataCriacao()
                });
            }
        } catch (Exception e) {
            log.get_log(e.toString());
        }
    }
    
    private Projetos findProjetos(int id){
        return dao.findProjetos(id);
    }

    //usado para pegar o registro antigo antes de atualizar. Salvar auditoria.
    private void populaOldEntidade() throws IOException {
        try{
        if (tbProjetos.getSelectedRow() != -1) {

            int id = (int) tbProjetos.getValueAt(tbProjetos.getSelectedRow(), 0);
            projetos = dao.findProjetos(id);
            dao.setOldEntidade(projetos);
            btAdicionarComodo.setEnabled(true);
            btRemoverComodo.setEnabled(true);
            btEditarProjeto.setEnabled(true);
            btEditarComodo.setEnabled(true);
            btFinalizar.setVisible(true);
            btReabrir.setVisible(false);
            btAnexos.setVisible(true);
            }
        if(projetos.getSituacao() == 2)
        {
        btEditarProjeto.setEnabled(false);
        btAdicionarComodo.setEnabled(false);
        btRemoverComodo.setEnabled(false);
        btReabrir.setVisible(true);
        btFinalizar.setVisible(false);
        }
        }catch (Exception e){
            log.get_log(e.toString());
        }
            
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        btNovoProjeto = new javax.swing.JButton();
        btPesquisar = new javax.swing.JButton();
        btEditarProjeto = new javax.swing.JButton();
        btExcluirProjeto = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbProjetos = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        tfPesquisar = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        btRemoverComodo = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbComodos = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btAdicionarComodo = new javax.swing.JButton();
        btEditarComodo = new javax.swing.JButton();
        btDashboard = new javax.swing.JButton();
        btReabrir = new javax.swing.JButton();
        btFinalizar = new javax.swing.JButton();
        btAnexos = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(jList1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Editor de Projetos");

        btNovoProjeto.setText("Novo");
        btNovoProjeto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btNovoProjetoActionPerformed(evt);
            }
        });

        btPesquisar.setText("Pesquisar");
        btPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btPesquisarActionPerformed(evt);
            }
        });

        btEditarProjeto.setText("Editar");
        btEditarProjeto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEditarProjetoActionPerformed(evt);
            }
        });

        btExcluirProjeto.setText("Excluir");
        btExcluirProjeto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btExcluirProjetoActionPerformed(evt);
            }
        });

        tbProjetos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nome", "Responsável", "Equipe", "Informações", "Situação", "Data Criação"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbProjetos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbProjetosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbProjetos);

        jButton1.setText("Sair");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Buscar:");

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel8.setText("Lista de Projetos:");

        btRemoverComodo.setText("Remover");
        btRemoverComodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRemoverComodoActionPerformed(evt);
            }
        });

        tbComodos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Id", "Nome", "Carga Térmica", "BTU / H"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbComodos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbComodosMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbComodos);
        if (tbComodos.getColumnModel().getColumnCount() > 0) {
            tbComodos.getColumnModel().getColumn(0).setMinWidth(45);
            tbComodos.getColumnModel().getColumn(0).setPreferredWidth(45);
            tbComodos.getColumnModel().getColumn(0).setMaxWidth(45);
        }

        jLabel2.setText("Lista de Projetos:");

        jLabel3.setText("Comodos do Projeto:");

        btAdicionarComodo.setText("Adicionar");
        btAdicionarComodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAdicionarComodoActionPerformed(evt);
            }
        });

        btEditarComodo.setText("Editar");
        btEditarComodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEditarComodoActionPerformed(evt);
            }
        });

        btDashboard.setText("Dashboard");
        btDashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDashboardActionPerformed(evt);
            }
        });

        btReabrir.setText("Reabrir Projeto");
        btReabrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btReabrirActionPerformed(evt);
            }
        });

        btFinalizar.setText("Finalizar");
        btFinalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btFinalizarActionPerformed(evt);
            }
        });

        btAnexos.setText("Anexos");
        btAnexos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAnexosActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel9.setText("Lista de Comodos do Projeto:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(375, 375, 375)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(101, 101, 101))
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btReabrir, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btFinalizar, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btAnexos, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel1)
                                    .addGap(18, 18, 18)
                                    .addComponent(tfPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(btPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 992, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 91, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btNovoProjeto, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(btEditarProjeto)
                        .addGap(18, 18, 18)
                        .addComponent(btExcluirProjeto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(100, 100, 100)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(btAdicionarComodo)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btEditarComodo)
                            .addGap(18, 18, 18)
                            .addComponent(btRemoverComodo)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel3))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addGap(60, 60, 60)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btPesquisar)
                        .addComponent(jLabel3))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tfPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1)))
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btReabrir)
                    .addComponent(btFinalizar)
                    .addComponent(btAnexos))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btNovoProjeto)
                    .addComponent(btEditarProjeto)
                    .addComponent(btExcluirProjeto)
                    .addComponent(jButton1)
                    .addComponent(btRemoverComodo)
                    .addComponent(btAdicionarComodo)
                    .addComponent(btEditarComodo)
                    .addComponent(btDashboard))
                .addGap(24, 24, 24))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btNovoProjetoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btNovoProjetoActionPerformed
        DlgCriarProjetosView dlg;
        try {
            dlg = new DlgCriarProjetosView(null, true, this);
            dlg.setVisible(true);
        } catch (IOException ex) {
            Logger.getLogger(DlgProjetosView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btNovoProjetoActionPerformed

    private void btPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btPesquisarActionPerformed
        try {
            pesquisar(tfPesquisar.getText());
        } catch (IOException ex) {
            Logger.getLogger(DlgProjetosView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btPesquisarActionPerformed

    private void btEditarProjetoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEditarProjetoActionPerformed
        if (tbProjetos.getSelectedRow() != -1) {
        
            int id = (int) tbProjetos.getValueAt(tbProjetos.getSelectedRow(), 0);
            projetos = findProjetos(id);
            DlgEditarProjetosView dlg;
            
        try {
            dlg = new DlgEditarProjetosView(null, true, projetos, this);
            dlg.setVisible(true);
        } catch (IOException ex) {
            Logger.getLogger(DlgProjetosView.class.getName()).log(Level.SEVERE, null, ex);
        }
        }else {
                JOptionPane.showMessageDialog(this, "Selecione um item da tabela!");

            }
    }//GEN-LAST:event_btEditarProjetoActionPerformed

    private void btExcluirProjetoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btExcluirProjetoActionPerformed
        try {
            excluirProjeto();
        } catch (Exception ex) {
            Logger.getLogger(DlgProjetosView.class.getName()).log(Level.SEVERE, null, ex);
        }try {
            renderizarTabela();
            limpaTabelaComodos();
        } catch (IOException ex) {
            Logger.getLogger(DlgEquipesView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btExcluirProjetoActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tbComodosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbComodosMouseClicked
        
    }//GEN-LAST:event_tbComodosMouseClicked

    private void btRemoverComodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRemoverComodoActionPerformed
        try {
            excluirComodo();
            renderizarTabelaComdos();
        } catch (Exception ex) {
            Logger.getLogger(DlgProjetosView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btRemoverComodoActionPerformed

    private void btAdicionarComodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAdicionarComodoActionPerformed
        try {
            populaOldEntidade();
        } catch (IOException ex) {
            Logger.getLogger(DlgProjetosView.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        int id = (int) tbProjetos.getValueAt(tbProjetos.getSelectedRow(), 0);
        projetos = dao.findProjetos(id);
        
        DlgCriarComodosView dlg;
        dlg = new DlgCriarComodosView(null, true, projetos, this);
        dlg.setVisible(true); 
    }//GEN-LAST:event_btAdicionarComodoActionPerformed

    private void btEditarComodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEditarComodoActionPerformed
        if (tbComodos.getSelectedRow() != -1) {
        int id = (int) tbComodos.getValueAt(tbComodos.getSelectedRow(), 0);
        ComodosProjetos comodo = daoCp.findComodos(id);

        DlgFacesView dlg;
        try {            
            dlg = new DlgFacesView(null, true, comodo);
            dlg.setVisible(true);
        } catch (IOException ex) {
            Logger.getLogger(DlgProjetosView.class.getName()).log(Level.SEVERE, null, ex);
        }
        }else{
            JOptionPane.showMessageDialog(this, "Selecione um Comodo da tabela!");
        }
    }//GEN-LAST:event_btEditarComodoActionPerformed

    private void btDashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDashboardActionPerformed
        try {
            dashboards ds = new dashboards(null, true);
            ds.setVisible(true);
        } catch (Exception ex) {
            Logger.getLogger(DlgProjetosView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btDashboardActionPerformed

    private void btFinalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFinalizarActionPerformed
        try {
            finalizarProjeto();
        } catch (Exception ex) {
            Logger.getLogger(DlgProjetosView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btFinalizarActionPerformed

    private void btReabrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btReabrirActionPerformed
        try {
            reabrirProjeto();
        } catch (Exception ex) {
            Logger.getLogger(DlgProjetosView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btReabrirActionPerformed

    private void tbProjetosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbProjetosMouseClicked
        try {
            populaOldEntidade();

        } catch (IOException ex) {
            Logger.getLogger(DlgProjetosView.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            renderizarTabelaComdos();
        } catch (IOException ex) {
            Logger.getLogger(DlgProjetosView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tbProjetosMouseClicked

    private void btAnexosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAnexosActionPerformed
        int id = (int) tbProjetos.getValueAt(tbProjetos.getSelectedRow(), 0);
        projetos = dao.findProjetos(id);

        DlgAnexosView dlg;
        try {
            dlg = new DlgAnexosView(null, true, projetos, this);
            dlg.setVisible(true);
        } catch (IOException ex) {
            Logger.getLogger(DlgProjetosView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btAnexosActionPerformed

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
            java.util.logging.Logger.getLogger(DlgProjetosView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DlgProjetosView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DlgProjetosView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DlgProjetosView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
                DlgProjetosView dialog = null;
                try {
                    dialog = new DlgProjetosView(new javax.swing.JFrame(), true);
                } catch (IOException ex) {
                    Logger.getLogger(DlgProjetosView.class.getName()).log(Level.SEVERE, null, ex);
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
    private javax.swing.JButton btAdicionarComodo;
    private javax.swing.JButton btAnexos;
    private javax.swing.JButton btDashboard;
    private javax.swing.JButton btEditarComodo;
    private javax.swing.JButton btEditarProjeto;
    private javax.swing.JButton btExcluirProjeto;
    private javax.swing.JButton btFinalizar;
    private javax.swing.JButton btNovoProjeto;
    private javax.swing.JButton btPesquisar;
    private javax.swing.JButton btReabrir;
    private javax.swing.JButton btRemoverComodo;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tbComodos;
    private javax.swing.JTable tbProjetos;
    private javax.swing.JTextField tfPesquisar;
    // End of variables declaration//GEN-END:variables
}
