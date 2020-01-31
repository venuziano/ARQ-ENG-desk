/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import apoio.Formatacao;
import apoio.GerenciarLog;
import apoio.UsuarioLogado;
import dao.AnexosDAO;
import entidade.Anexos;
import entidade.Projetos;
import entidade.Usuarios;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.NonWritableChannelException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author rrs
 */
public class DlgAnexosView extends javax.swing.JDialog {

    EntityManagerFactory factory = Persistence.createEntityManagerFactory("ARQ_ENGPU");
    DlgProjetosView dlgProjetos;
    Projetos projetos;
    UsuarioLogado usuarioLogado;
    Anexos anexos = new Anexos();

    AnexosDAO dao = new AnexosDAO(factory);

    GerenciarLog log = new GerenciarLog();
    Formatacao formatacao = new Formatacao();

    public DlgAnexosView(java.awt.Frame parent, boolean modal, Projetos projetos, DlgProjetosView dlgProjetos) throws IOException {
        super(parent, modal);
        initComponents();
        this.dlgProjetos = dlgProjetos;
        this.projetos = projetos;
        renderizarTabela();
        //desabilita as actions caso o projeto selecionado estiver finalizado.
        if (this.projetos.getSituacao() == 2) {
            btNovo.setEnabled(false);
            btExcluir.setEnabled(false);
            btRenomear.setEnabled(false);
        }
    }

    public void salvarDiretorio() throws IOException {
        JFileChooser seletor = new JFileChooser();
        seletor.setDialogTitle("Procurar Arquivo");
        seletor.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int opcao = seletor.showOpenDialog(this);

        try {
            if (opcao == JFileChooser.APPROVE_OPTION) {
                //cria o diretorio de anexos para cada ID de projeto caso ainda não existir.
                File diretorio = new File("src/anexos/projetos/" + projetos.getId());
                if (!diretorio.exists()) {
                    diretorio.mkdir();
                }
                File arquivo = seletor.getSelectedFile();
                File destino = new File("src/anexos/projetos/" + projetos.getId() + "/" + arquivo.getName());
                //tratamento que permite inserir apenas uma única vez um arquivo de mesmo nome.
                if (destino.exists()) {
                    JOptionPane.showMessageDialog(this, "Anexo já inserido!\n\nRenomeie o arquivo e tente inseri-lo novamente.");
                    renderizarTabela();
                } else {
                    //salva o arquivo no diretorio especificado acima.
                    copiarArquivo(arquivo.getAbsoluteFile(), destino);
                    //salva o arquivo no banco de dados, tabela "anexos".
                    salvarBD(arquivo, destino);
                    renderizarTabela();
                }
            }
        } catch (Exception e) {
            log.get_log(e.toString());
        }
    }

    public static void copiarArquivo(File source, File destination) throws IOException {
        FileChannel sourceChannel = null;
        FileChannel destinationChannel = null;
        try {
            sourceChannel = new FileInputStream(source).getChannel();
            destinationChannel = new FileOutputStream(destination).getChannel();
            sourceChannel.transferTo(0, sourceChannel.size(), destinationChannel);
        } finally {
            if (sourceChannel != null && sourceChannel.isOpen()) {
                sourceChannel.close();
            }
            if (destinationChannel != null && destinationChannel.isOpen()) {
                destinationChannel.close();
            }
        }
    }
    
    public static void descarregarArquivo(File source, File destination) throws IOException, NonWritableChannelException {
        FileChannel sourceChannel = null;
        FileChannel destinationChannel = null;
        try {
            sourceChannel = new FileInputStream(source).getChannel();
            destinationChannel = new FileOutputStream(destination + "\\" + source.getName()).getChannel();
            sourceChannel.transferTo(0, sourceChannel.size(), destinationChannel);
            sourceChannel.close();
            destinationChannel.close();
        } catch (NonWritableChannelException e) {
            e.printStackTrace();
        } 
        finally {
            if (sourceChannel != null && sourceChannel.isOpen()) {
                sourceChannel.close();
            }
            if (destinationChannel != null && destinationChannel.isOpen()) {
                destinationChannel.close();
            }
        }
    }

    public void salvarBD(File arquivo, File destino) throws IOException {
        try {
            String dataFormatada = formatacao.getDataAtual();
            Anexos anexos = new Anexos();
            anexos.setNome(arquivo.getName());
            anexos.setFamily(0);
            anexos.setDtCriacao(dataFormatada);
            anexos.setPath(destino.toString());
            anexos.setRefSource(this.projetos.getId());
            //remover DEPOISSSSSSSS remover DEPOISSSSSSSS remover DEPOISSSSSSSS remover DEPOISSSSSSSS remover DEPOISSSSSSSS remover DEPOISSSSSSSS 
            Usuarios u = new Usuarios();
            u.setId(9);
            anexos.setRefUsuario(u);
            //manter o abaixo
//          anexos.setRefUsuario(UsuarioLogado.getUsuario());
            dao.salvar(anexos);
            JOptionPane.showMessageDialog(this, "Anexo inserido com sucesso!");
        } catch (Exception e) {
            log.get_log(e.toString());
        }
    }

    public void abrirAnexo() throws IOException {
        try {
            if (tbAnexos.getSelectedRow() != -1) {
                java.awt.Desktop.getDesktop().open(new File(this.anexos.getPath()));
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um anexo da tabela!");
            }
        } catch (Exception e) {
            log.get_log(e.toString());
        }
    }

    public void baixarAnexo() throws IOException {
        JFileChooser seletor = new JFileChooser();
        int opcao = 0;
        if (tbAnexos.getSelectedRow() != -1) {
            seletor.setDialogTitle("Selecione o local para inserir o arquivo baixado");
            seletor.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            opcao = seletor.showOpenDialog(this);
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um anexo da tabela!");
        }
        try {
            if (opcao == JFileChooser.APPROVE_OPTION) {
                //pega o path do anexo selecionado.
                File arquivo = new File(this.anexos.getPath());
                //pega o caminho escolhido pelo usuario para inserir o anexo baixado.
                File destino = seletor.getSelectedFile();
                descarregarArquivo(arquivo, destino);
                JOptionPane.showMessageDialog(this, "Anexo baixado com sucesso!");
            }
        } catch (Exception e) {
            log.get_log(e.toString());
        }
    }

    public void excluirDiretorio() throws IOException {
        try {
            if (tbAnexos.getSelectedRow() != -1) {
                int confirm = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir o anexo vinculado?");
                if (confirm == JOptionPane.YES_OPTION) {
                    File arquivo = new File(anexos.getPath());
                    //exclui o arquivo do diretorio.
                    arquivo.delete();
                    //exclui o arquivo do banco de dados, tabela "anexos".
                    excluirBD();
                    JOptionPane.showMessageDialog(this, "Anexo excluido com sucesso!");
                    renderizarTabela();
                }
                if (confirm == JOptionPane.NO_OPTION) {
                    renderizarTabela();
                }
                if (confirm == JOptionPane.CANCEL_OPTION) {
                    renderizarTabela();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um anexo da tabela!");
            }
        } catch (Exception e) {
            log.get_log(e.toString());
        }
    }

    public void excluirBD() throws Exception {
        try {
            dao.excluir(anexos, anexos.getId());
        } catch (Exception e) {
            log.get_log(e.toString());
        }
    }

    public void popularEntidade() throws IOException {
        try {
            if (tbAnexos.getSelectedRow() != -1) {

                int id = (int) tbAnexos.getValueAt(tbAnexos.getSelectedRow(), 0);
                anexos = (Anexos) dao.findAnexos(id);
                dao.setOldEntidade(anexos);
            }
        } catch (Exception e) {
            log.get_log(e.toString());
        }
    }

    void renderizarTabela() throws IOException {
        DefaultTableModel tabela = (DefaultTableModel) tbAnexos.getModel();
        tabela.setNumRows(0);
        tbAnexos.setRowSorter(new TableRowSorter(tabela));
        tbAnexos.setDefaultEditor(Object.class, null);
        montaTabela();
    }

    private void montaTabela() throws IOException {
        DefaultTableModel tabela = (DefaultTableModel) tbAnexos.getModel();
        tabela.setNumRows(0);
        try {
            for (Anexos a : dao.buscaAnexosProjeto(this.projetos.getId())) {
                tabela.addRow(new Object[]{
                    a.getId(),
                    a.getNome(),
                    a.getRefUsuario().getNome(),
                    a.getDtCriacao()
                });

            }
        } catch (Exception e) {
            log.get_log(e.toString());
        }
    }

    private void pesquisar(String desc) throws IOException {
        DefaultTableModel tabela = (DefaultTableModel) tbAnexos.getModel();
        tabela.setNumRows(0);
        try {
            for (Anexos a : dao.buscaAnexos(desc, this.projetos.getId())) {
                tabela.addRow(new Object[]{
                    a.getId(),
                    a.getNome(),
                    a.getRefUsuario().getNome(),
                    a.getDtCriacao()
                });
            }
        } catch (Exception e) {
            log.get_log(e.toString());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbAnexos = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        btSair = new javax.swing.JButton();
        btNovo = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        tfPesquisar = new javax.swing.JTextField();
        btPesquisar = new javax.swing.JButton();
        btExcluir = new javax.swing.JButton();
        btRenomear = new javax.swing.JButton();
        btVisualizar = new javax.swing.JButton();
        btBaixar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Editor de Anexos");

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel8.setText("Anexos:");

        tbAnexos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Nome", "Responsável", "Data Registro"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbAnexos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbAnexosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbAnexos);

        jLabel1.setText("Lista de Anexos:");

        btSair.setText("Sair");
        btSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSairActionPerformed(evt);
            }
        });

        btNovo.setText("Novo");
        btNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btNovoActionPerformed(evt);
            }
        });

        jLabel2.setText("Buscar:");

        btPesquisar.setText("Pesquisar");
        btPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btPesquisarActionPerformed(evt);
            }
        });

        btExcluir.setText("Excluir");
        btExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btExcluirActionPerformed(evt);
            }
        });

        btRenomear.setText("Renomear");
        btRenomear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRenomearActionPerformed(evt);
            }
        });

        btVisualizar.setText("Visualizar");
        btVisualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btVisualizarActionPerformed(evt);
            }
        });

        btBaixar.setText("Baixar");
        btBaixar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btBaixarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(366, 366, 366)
                        .addComponent(jLabel8)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(tfPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 728, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 34, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btSair, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btNovo, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                            .addComponent(btExcluir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btRenomear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btVisualizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btBaixar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btPesquisar, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tfPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btVisualizar)
                        .addGap(40, 40, 40)
                        .addComponent(btNovo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btRenomear)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btBaixar)
                        .addGap(173, 173, 173)
                        .addComponent(btSair))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btNovoActionPerformed
        try {
            salvarDiretorio();
        } catch (IOException ex) {
            Logger.getLogger(DlgAnexosView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btNovoActionPerformed

    private void btSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSairActionPerformed
        this.dispose();
    }//GEN-LAST:event_btSairActionPerformed

    private void btPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btPesquisarActionPerformed
        try {
            pesquisar(tfPesquisar.getText());
        } catch (IOException ex) {
            Logger.getLogger(DlgProjetosView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btPesquisarActionPerformed

    private void tbAnexosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbAnexosMouseClicked
        try {
            popularEntidade();
        } catch (IOException ex) {
            Logger.getLogger(DlgAnexosView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tbAnexosMouseClicked

    private void btExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btExcluirActionPerformed
        try {
            excluirDiretorio();
        } catch (IOException ex) {
            Logger.getLogger(DlgAnexosView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btExcluirActionPerformed

    private void btRenomearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRenomearActionPerformed
        if (tbAnexos.getSelectedRow() != -1) {
            int id = (int) tbAnexos.getValueAt(tbAnexos.getSelectedRow(), 0);
            anexos = dao.findAnexos(id);

            DlgRenomearAnexosView dlg;
            dlg = new DlgRenomearAnexosView(null, true, anexos, this);
            dlg.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um anexo da tabela!");
        }
    }//GEN-LAST:event_btRenomearActionPerformed

    private void btVisualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btVisualizarActionPerformed
        try {
            abrirAnexo();
        } catch (IOException ex) {
            Logger.getLogger(DlgAnexosView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btVisualizarActionPerformed

    private void btBaixarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btBaixarActionPerformed
        try {
            baixarAnexo();
        } catch (IOException ex) {
            Logger.getLogger(DlgAnexosView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btBaixarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btBaixar;
    private javax.swing.JButton btExcluir;
    private javax.swing.JButton btNovo;
    private javax.swing.JButton btPesquisar;
    private javax.swing.JButton btRenomear;
    private javax.swing.JButton btSair;
    private javax.swing.JButton btVisualizar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbAnexos;
    private javax.swing.JTextField tfPesquisar;
    // End of variables declaration//GEN-END:variables
}
