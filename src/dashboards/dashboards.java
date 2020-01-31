/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dashboards;

import apoio.ConexaoDB;
import java.awt.BorderLayout;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import view.DlgProjetosView;

/**
 *
 * @author rrs
 */
public class dashboards extends javax.swing.JFrame {

    public dashboards(java.awt.Frame parent, boolean modal) throws Exception {
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    public void situacaoProjeto() throws Exception {

        PreparedStatement ps = null;
        ResultSet rs = null;
        String situacao = "";
        DateFormat dfInicio = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat dfFim = new SimpleDateFormat("dd/MM/yyyy");
       
        String sql = "select situacao as situacao, count(*) as num from projetos p where dt_criacao between ? and ? group by p.situacao";
        ps = ConexaoDB.getInstance().getConnection().prepareStatement(sql);
        ps.setString(1, dfInicio.format(dtInicio.getDate()));
        ps.setString(2, dfFim.format(dtFim.getDate()));
        rs = ps.executeQuery();
        
        DefaultCategoryDataset barra = new DefaultCategoryDataset();
        try {
            while (rs.next()) {
                if (rs.getInt("situacao") == 1) {
                    situacao = "Em Andamento";
                } else {
                    situacao = "Finalizado";
                }
                barra.setValue(rs.getInt("num"), situacao, "");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DlgProjetosView.class.getName()).log(Level.SEVERE, null, ex);
        }

        JFreeChart grafico = ChartFactory.createBarChart("Situação x Projeto", "Situação", "Quantidade", barra, PlotOrientation.VERTICAL, true, true, false);
        ChartPanel cp = new ChartPanel(grafico);
        situacaoProjeto.setLayout(new java.awt.BorderLayout());
        situacaoProjeto.setSize(350, 350);
        situacaoProjeto.setLocation(300, 50);
        situacaoProjeto.add(cp, BorderLayout.EAST);
        situacaoProjeto.add(cp);
        situacaoProjeto.validate();
    }

    public void qtdMaterialProjeto() throws Exception {

        PreparedStatement ps = null;
        ResultSet rs = null;
        String situacao = "";
        DateFormat dfInicio = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat dfFim = new SimpleDateFormat("dd/MM/yyyy");
        
        String sql = "select m.nome as nome,count(mf.*) as num from projetos p,comodos_projetos cp,faces f,materiais_face mf,materiais m"
                + " where p.id = cp.ref_projeto and cp.id = f.ref_comodo and f.id = mf.faces_id and m.id = mf.materiais_id and p.dt_criacao between ? and ? group by m.nome";
        ps = ConexaoDB.getInstance().getConnection().prepareStatement(sql);
        ps.setString(1, dfInicio.format(dtInicio.getDate()));
        ps.setString(2, dfFim.format(dtFim.getDate()));
        rs = ps.executeQuery();

        DefaultPieDataset pizza = new DefaultPieDataset();
        try {
            while (rs.next()) {
                pizza.setValue(rs.getString("nome"), rs.getInt("num"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DlgProjetosView.class.getName()).log(Level.SEVERE, null, ex);
        }

        JFreeChart grafico = ChartFactory.createPieChart("Quantidade x Materiais Utilizados", pizza, true, true, false);
        ChartPanel cp = new ChartPanel(grafico);
        this.qtdMateriais.setLayout(new java.awt.BorderLayout());
        this.qtdMateriais.setSize(350, 350);
        this.qtdMateriais.setLocation(750, 50);
        this.qtdMateriais.add(cp, BorderLayout.NORTH);
        this.qtdMateriais.add(cp);
        this.qtdMateriais.validate();
    }

    public void qtdResponsavelProjeto() throws Exception {

        PreparedStatement ps = null;
        ResultSet rs = null;
        String situacao = "";
        DateFormat dfInicio = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat dfFim = new SimpleDateFormat("dd/MM/yyyy");
        
        String sql = "select u.nome as responsavel, count(p.*) as num from usuarios u, projetos p where u.id = p.responsavel and "
                     + "p.dt_criacao between ? and ? group by u.nome";
        ps = ConexaoDB.getInstance().getConnection().prepareStatement(sql);
        ps.setString(1, dfInicio.format(dtInicio.getDate()));
        ps.setString(2, dfFim.format(dtFim.getDate()));
        rs = ps.executeQuery();

        DefaultCategoryDataset barra = new DefaultCategoryDataset();
        try {
            while (rs.next()) {
                barra.setValue(rs.getInt("num"), rs.getString("responsavel"), "");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DlgProjetosView.class.getName()).log(Level.SEVERE, null, ex);
        }

        JFreeChart grafico = ChartFactory.createBarChart("Quantidade Responsável x Projetos", "Responsável", "Quantidade", barra, PlotOrientation.HORIZONTAL, true, true, false);
        ChartPanel cp = new ChartPanel(grafico);
        this.qtdResponsavel.setLayout(new java.awt.BorderLayout());
        this.qtdResponsavel.setSize(700, 350);
        this.qtdResponsavel.setLocation(350, 425);
        this.qtdResponsavel.add(cp, BorderLayout.PAGE_END);
        this.qtdResponsavel.add(cp);
        this.qtdResponsavel.validate();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        qtdMateriais = new javax.swing.JPanel();
        qtdResponsavel = new javax.swing.JPanel();
        btSair = new javax.swing.JButton();
        btGerar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        dtInicio = new com.toedter.calendar.JDateChooser();
        dtFim = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        situacaoProjeto = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Dashboards");
        setBackground(new java.awt.Color(204, 0, 0));
        setPreferredSize(new java.awt.Dimension(900, 700));

        javax.swing.GroupLayout qtdMateriaisLayout = new javax.swing.GroupLayout(qtdMateriais);
        qtdMateriais.setLayout(qtdMateriaisLayout);
        qtdMateriaisLayout.setHorizontalGroup(
            qtdMateriaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 458, Short.MAX_VALUE)
        );
        qtdMateriaisLayout.setVerticalGroup(
            qtdMateriaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 340, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout qtdResponsavelLayout = new javax.swing.GroupLayout(qtdResponsavel);
        qtdResponsavel.setLayout(qtdResponsavelLayout);
        qtdResponsavelLayout.setHorizontalGroup(
            qtdResponsavelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        qtdResponsavelLayout.setVerticalGroup(
            qtdResponsavelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 316, Short.MAX_VALUE)
        );

        btSair.setText("Voltar");
        btSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSairActionPerformed(evt);
            }
        });

        btGerar.setText("Gerar");
        btGerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btGerarActionPerformed(evt);
            }
        });

        jLabel1.setText("Insira datas para gerar o dashboard!");

        jLabel2.setText("Busca pela Data de Criação do projeto:");

        jLabel3.setText("Gráficos gerados:");

        jLabel4.setText("* Situação dos projetos");

        jLabel5.setText("* Quantidade e materiais utilizados");

        jLabel6.setText("* Relação de responsáveis dos projetos");

        jLabel7.setText("De*:");

        jLabel8.setText("Até:");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/sharp_clear_all_black_18dp.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/sharp_clear_all_black_18dp.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout situacaoProjetoLayout = new javax.swing.GroupLayout(situacaoProjeto);
        situacaoProjeto.setLayout(situacaoProjetoLayout);
        situacaoProjetoLayout.setHorizontalGroup(
            situacaoProjetoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 518, Short.MAX_VALUE)
        );
        situacaoProjetoLayout.setVerticalGroup(
            situacaoProjetoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 336, Short.MAX_VALUE)
        );

        jLabel9.setText("            DASHBOARD PROJETO");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel7)
                                    .addGap(18, 18, 18)
                                    .addComponent(dtInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btGerar, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btSair, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addGap(18, 18, 18)
                                        .addComponent(dtFim, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(situacaoProjeto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(qtdMateriais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(qtdResponsavel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(27, 27, 27))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(jLabel2)
                                .addGap(36, 36, 36)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(dtInicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                .addGap(21, 21, 21)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(dtFim, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btGerar)
                                    .addComponent(btSair))
                                .addGap(65, 65, 65)
                                .addComponent(jLabel3)
                                .addGap(11, 11, 11)
                                .addComponent(jLabel4)
                                .addGap(11, 11, 11)
                                .addComponent(jLabel5)
                                .addGap(11, 11, 11)
                                .addComponent(jLabel6))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(situacaoProjeto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(qtdMateriais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addComponent(qtdResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSairActionPerformed
        this.dispose();
    }//GEN-LAST:event_btSairActionPerformed

    private void btGerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btGerarActionPerformed
        if (dtInicio.getDate() != null && dtFim.getDate() != null) {
            try {
                situacaoProjeto();
                qtdMaterialProjeto();
                qtdResponsavelProjeto();
            } catch (Exception ex) {
                Logger.getLogger(dashboards.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Obrigatório informar datas para o campo 'De' e 'Até'!");
        }
    }//GEN-LAST:event_btGerarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dtInicio.setCalendar(null);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        dtFim.setCalendar(null);
    }//GEN-LAST:event_jButton2ActionPerformed

//    /**
//     * @param args the command line arguments
//     */
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
            java.util.logging.Logger.getLogger(dashboards.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(dashboards.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(dashboards.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(dashboards.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                dashboards dialog = null;
                try {
                    dialog = new dashboards(new javax.swing.JFrame(), true);
                } catch (Exception ex) {
                    Logger.getLogger(dashboards.class.getName()).log(Level.SEVERE, null, ex);
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
    private javax.swing.JButton btGerar;
    private javax.swing.JButton btSair;
    private com.toedter.calendar.JDateChooser dtFim;
    private com.toedter.calendar.JDateChooser dtInicio;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel qtdMateriais;
    private javax.swing.JPanel qtdResponsavel;
    private javax.swing.JPanel situacaoProjeto;
    // End of variables declaration//GEN-END:variables
}
