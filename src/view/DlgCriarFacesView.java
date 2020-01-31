/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import apoio.GerenciarLog;
import dao.ComodosProjetosDAO;
import dao.FacesDAO;
import entidade.ComodosProjetos;
import entidade.Faces;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;

/**
 *
 * @author mf
 */
public class DlgCriarFacesView extends javax.swing.JDialog {

    EntityManagerFactory factory = Persistence.createEntityManagerFactory("ARQ_ENGPU");
    FacesDAO daoF = new FacesDAO(factory);
    ComodosProjetosDAO daoCp = new ComodosProjetosDAO(factory);
    
    DlgFacesView dlgFaces;
    ComodosProjetos comodo = new ComodosProjetos();
    Faces face = new Faces();
    Integer tratamento;
    
    GerenciarLog log = new GerenciarLog();
    
    public DlgCriarFacesView(java.awt.Frame parent, boolean modal, DlgFacesView dlgFaces, ComodosProjetos comodo) throws IOException {
        super(parent, modal);
        initComponents();
        
        this.dlgFaces = dlgFaces;
        this.comodo = comodo;
        this.tratamento = 0;
    }
    
    public DlgCriarFacesView(java.awt.Frame parent, boolean modal, DlgFacesView dlgFaces, ComodosProjetos comodo, Faces face) throws IOException {
        super(parent, modal);
        initComponents();
        
        this.dlgFaces = dlgFaces;
        this.comodo = comodo;
        this.face = face;
        this.tratamento = 1;
        
        tfDescricao.setText(this.face.getDescricao());
        switch (this.face.getTipo()) {
            case 0:
                rbParede.setSelected(true);
                break;
            case 1:
                rbCobertura.setSelected(true);
                break;
            case 2:
                rbLajeInf.setSelected(true);
                break;
            default:
                break;
        }
        cbOrientacao.setSelectedItem(this.face.getOrientacaoSolar());
        tfRadiacaoSolar.setText(String.valueOf(this.face.getRadiacaoSolarIns()));
        tfArea.setText(String.valueOf(this.face.getArea()));
        tfAbsorvidadeTinta.setText(String.valueOf(this.face.getAbsorvidadeTinta()));
    }
    

    private void salvar() throws Exception {
        try{
            Faces f = new Faces();
            f.setRefComodo(this.comodo);
            f.setDescricao(tfDescricao.getText());
            f.setTipo(parseInt(btg.getSelection().getActionCommand()));
            f.setOrientacaoSolar((String) cbOrientacao.getSelectedItem());
            f.setRadiacaoSolarIns(Double.parseDouble(tfRadiacaoSolar.getText()));
            f.setArea(Double.parseDouble(tfArea.getText()));
            f.setAbsorvidadeTinta(Double.parseDouble(tfAbsorvidadeTinta.getText()));
            f.setFatorSolar(0.0);
            f.setFluxoOpaco(0.0);
            f.setFluxoTransparente(0.0);
            f.setTransmitancia(0.0);
            f.setResistenciaTotal(0.0);
            f.setCargaFluxoOpaco(0.0);
            f.setCargaFluxoTransparente(0.0);
            
            this.comodo.getFacesCollection().add(f);
            
            daoF.salvar(f);
            daoCp.atualizar(this.comodo);
            
            JOptionPane.showMessageDialog(this, "Cadastro efetuado com sucesso!");
            
        } catch (Exception e) {
            log.get_log(e.toString());
        }
    }
    
    private void atualizar() throws Exception {
        try{
            this.face.setDescricao(tfDescricao.getText());
            this.face.setTipo(parseInt(btg.getSelection().getActionCommand()));
            this.face.setOrientacaoSolar((String) cbOrientacao.getSelectedItem());
            this.face.setRadiacaoSolarIns(Double.parseDouble(tfRadiacaoSolar.getText()));
            this.face.setArea(Double.parseDouble(tfArea.getText()));
            this.face.setAbsorvidadeTinta(Double.parseDouble(tfAbsorvidadeTinta.getText()));
            
            daoF.atualizar(this.face);
            
            JOptionPane.showMessageDialog(this, "Informações atualizadas com sucesso!");
            
        } catch (Exception e) {
            log.get_log(e.toString());
        }
    }
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btg = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tfDescricao = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        rbParede = new javax.swing.JRadioButton();
        rbCobertura = new javax.swing.JRadioButton();
        rbLajeInf = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cbOrientacao = new javax.swing.JComboBox<>();
        tfRadiacaoSolar = new javax.swing.JTextField();
        tfArea = new javax.swing.JTextField();
        tfAbsorvidadeTinta = new javax.swing.JTextField();
        btFechar = new javax.swing.JButton();
        btOk = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel1.setText("Nova Face");

        jLabel2.setText("Descrição:");

        jLabel3.setText("Tipo:");

        btg.add(rbParede);
        rbParede.setText("Parede");
        rbParede.setActionCommand("0");

        btg.add(rbCobertura);
        rbCobertura.setText("Cobertura");
        rbCobertura.setActionCommand("1");

        btg.add(rbLajeInf);
        rbLajeInf.setText("Laje Inferior");
        rbLajeInf.setActionCommand("2");

        jLabel4.setText("Orientação Solar:");

        jLabel5.setText("Radiação Solar Incidente:");

        jLabel6.setText("Área:");

        jLabel7.setText("Absorvidade da Tinta:");

        cbOrientacao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione", "S", "SE", "E", "NE", "N", "NW", "W", "SW" }));

        btFechar.setText("Fechar");
        btFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btFecharActionPerformed(evt);
            }
        });

        btOk.setText("Ok");
        btOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btOkActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(254, 254, 254)
                                .addComponent(jLabel1))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel7)
                                            .addComponent(jLabel4))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(tfArea, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                                            .addComponent(cbOrientacao, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(tfRadiacaoSolar)
                                            .addComponent(tfAbsorvidadeTinta)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel3))
                                        .addGap(88, 88, 88)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(rbParede)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(rbCobertura)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(rbLajeInf))
                                            .addComponent(tfDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addGap(0, 213, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btOk, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btFechar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tfDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbParede)
                    .addComponent(rbCobertura)
                    .addComponent(rbLajeInf)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cbOrientacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(tfRadiacaoSolar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(tfArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(tfAbsorvidadeTinta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btFechar)
                    .addComponent(btOk))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFecharActionPerformed
        this.dispose();
    }//GEN-LAST:event_btFecharActionPerformed

    private void btOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btOkActionPerformed
        try {
            if(!tfDescricao.getText().isEmpty()){
                if (tratamento == 0){
                salvar();
                dlgFaces.atualizaComodo();
                dlgFaces.renderizarTabelaFaces();
                this.dispose();
                } else if (tratamento == 1) {
                    atualizar();
                    dlgFaces.atualizaComodo();
                    dlgFaces.renderizarTabelaFaces();
                    this.dispose();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Preencha os campos corretamente!");
            }
        } catch (Exception e) {
            Logger.getLogger(DlgMateriaisView.class.getName()).log(Level.SEVERE, null, e);
        }
    }//GEN-LAST:event_btOkActionPerformed

//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(DlgCriarFacesView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(DlgCriarFacesView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(DlgCriarFacesView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(DlgCriarFacesView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the dialog */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                DlgCriarFacesView dialog = new DlgCriarFacesView(new javax.swing.JFrame(), true);
//                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//                    @Override
//                    public void windowClosing(java.awt.event.WindowEvent e) {
//                        System.exit(0);
//                    }
//                });
//                dialog.setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btFechar;
    private javax.swing.JButton btOk;
    private javax.swing.ButtonGroup btg;
    private javax.swing.JComboBox<String> cbOrientacao;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JRadioButton rbCobertura;
    private javax.swing.JRadioButton rbLajeInf;
    private javax.swing.JRadioButton rbParede;
    private javax.swing.JTextField tfAbsorvidadeTinta;
    private javax.swing.JTextField tfArea;
    private javax.swing.JTextField tfDescricao;
    private javax.swing.JTextField tfRadiacaoSolar;
    // End of variables declaration//GEN-END:variables
}
