/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms_pos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Random;
import javax.swing.ImageIcon;

/**
 *
 * @author nenadk
 */
public class PosJPanel extends javax.swing.JPanel {

    /**
     * Creates new form PosJPanel
     */
    public PosJPanel() {
        initComponents();
        /*
        */
       
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        DodavanjeBtn = new javax.swing.JButton();
        TrazenjePersoneBtn = new javax.swing.JButton();
        PartnersBtn = new javax.swing.JButton();
        PrintBtn1 = new javax.swing.JButton();
        IzlazBtn = new javax.swing.JButton();
        DodavanjeBtn1 = new javax.swing.JButton();
        ArtikliBtn2 = new javax.swing.JButton();
        TrazenjePersoneBtn1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        DodavanjeBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/cashbox-icon.png"))); // NOI18N
        DodavanjeBtn.setText("SmartPOS");
        DodavanjeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DodavanjeBtnActionPerformed(evt);
            }
        });

        TrazenjePersoneBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/Barcode-icon-48.png"))); // NOI18N
        TrazenjePersoneBtn.setText("Pakovanja");
        TrazenjePersoneBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TrazenjePersoneBtnActionPerformed(evt);
            }
        });

        PartnersBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove1/partners-50.jpg"))); // NOI18N
        PartnersBtn.setText("Partneri");
        PartnersBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PartnersBtnActionPerformed(evt);
            }
        });

        PrintBtn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/price-tag-icon-48.png"))); // NOI18N
        PrintBtn1.setText("Cenovnik");
        PrintBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrintBtn1ActionPerformed(evt);
            }
        });

        IzlazBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/Button-Close-icon.png"))); // NOI18N
        IzlazBtn.setText("Izlaz");
        IzlazBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IzlazBtnActionPerformed(evt);
            }
        });

        DodavanjeBtn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/search-good-icon-48.png"))); // NOI18N
        DodavanjeBtn1.setText("Artikli");
        DodavanjeBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DodavanjeBtn1ActionPerformed(evt);
            }
        });

        ArtikliBtn2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/EVE-icon-48.png"))); // NOI18N
        ArtikliBtn2.setText("Artikli");
        ArtikliBtn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ArtikliBtn2ActionPerformed(evt);
            }
        });

        TrazenjePersoneBtn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/sale-icon-48.png"))); // NOI18N
        TrazenjePersoneBtn1.setText("Akcije");
        TrazenjePersoneBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TrazenjePersoneBtn1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(PartnersBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(DodavanjeBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(DodavanjeBtn1, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE))
                    .addComponent(ArtikliBtn2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(IzlazBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(PrintBtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(TrazenjePersoneBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(TrazenjePersoneBtn1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DodavanjeBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DodavanjeBtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PrintBtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ArtikliBtn2, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TrazenjePersoneBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(TrazenjePersoneBtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(IzlazBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PartnersBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(462, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/pos-small.jpg"))); // NOI18N

        jLabel5.setText("*");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(313, 313, 313))
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 419, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(27, 27, 27)
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/smartPOS3.jpg"))); // NOI18N
        jLabel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 204));
        jLabel4.setText("jLabel4");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(498, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(485, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(35, 35, 35)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(472, 472, 472)
                            .addComponent(jLabel4)))
                    .addContainerGap(393, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel4)
                    .addGap(385, 385, 385)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void DodavanjeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DodavanjeBtnActionPerformed
        /*
        Thread t2 = new Thread(new Runnable() {
            public void run() {
                TerminalPanel ap = new TerminalPanel(conn, "UNOS", "");
                ap.setVisible(true);
            }
        });
        
        t2.start();
        */
        //        TerminalPanel ap = new TerminalPanel(conn, "UNOS", "");
        //        ap.setVisible(true);
    }//GEN-LAST:event_DodavanjeBtnActionPerformed

    private void TrazenjePersoneBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TrazenjePersoneBtnActionPerformed
        // TODO add your handling code here:
        /* Pakovanja sr = new Pakovanja(conn, "artikli_pakovanja", "");
        sr.setVisible(true);
        */
        //        TrazenjePersone tp = new TrazenjePersone();
        //        tp.setVisible(true);
    }//GEN-LAST:event_TrazenjePersoneBtnActionPerformed

    private void PartnersBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PartnersBtnActionPerformed
        /* TrazenjePartnera sr = new TrazenjePartnera(conn, "artikli");
        sr.setVisible(true);
        */
        //        Thread t1 = new Thread(new Runnable() {
            //            public void run() {
                //                try {
                    //                    String reportPath = "C:\\Users\\ms\\Documents\\NetBeansProjects\\gui\\src\\report\\pregledPersonalaReport1.jrxml";
                    //                    JasperReport jr = JasperCompileManager.compileReport(reportPath);
                    //                    JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);
                    //                    JasperViewer.viewReport(jp, false);
                    //                } catch (JRException ex) {
                    //                    Logger.getLogger(Pos.class.getName()).log(Level.SEVERE, null, ex);
                    //                }
                //            }
            //        });
    //        t1.start();
    }//GEN-LAST:event_PartnersBtnActionPerformed

    private void PrintBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PrintBtn1ActionPerformed
        // TODO add your handling code here:
        /* Cenovnik sr = new Cenovnik(conn, "cenovnik", "");
        sr.setVisible(true);
        */
        //              Thread t1 = new Thread(new Runnable() {
            //            public void run() {
                //                try {
                    //                    String reportPath = "C:\\Users\\ms\\Documents\\NetBeansProjects\\gui\\src\\report\\karton.jrxml";
                    //                    JasperReport jr = JasperCompileManager.compileReport(reportPath);
                    //                    JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);
                    //                    JasperViewer.viewReport(jp, false);
                    //                } catch (JRException ex) {
                    //                    Logger.getLogger(Pos.class.getName()).log(Level.SEVERE, null, ex);
                    //                }
                //            }
            //        });
    //        t1.start();
    }//GEN-LAST:event_PrintBtn1ActionPerformed

    private void IzlazBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IzlazBtnActionPerformed
        System.exit(0);
    }//GEN-LAST:event_IzlazBtnActionPerformed

    private void DodavanjeBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DodavanjeBtn1ActionPerformed
        // TODO add your handling code here:
        /* TrazenjeArtikala sr = new TrazenjeArtikala(conn, "artikli");
        sr.setVisible(true);
        */
    }//GEN-LAST:event_DodavanjeBtn1ActionPerformed

    private void ArtikliBtn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ArtikliBtn2ActionPerformed
        // TODO add your handling code here:
        /* TrazenjeArtikala sr = new TrazenjeArtikala(conn, "artikli");
        sr.setVisible(true);
        */
    }//GEN-LAST:event_ArtikliBtn2ActionPerformed

    private void TrazenjePersoneBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TrazenjePersoneBtn1ActionPerformed
        // TODO add your handling code here:
        /* Akcije sr = new Akcije(conn, "akcije", "");
        sr.setVisible(true);
        */
    }//GEN-LAST:event_TrazenjePersoneBtn1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ArtikliBtn2;
    private javax.swing.JButton DodavanjeBtn;
    private javax.swing.JButton DodavanjeBtn1;
    private javax.swing.JButton IzlazBtn;
    private javax.swing.JButton PartnersBtn;
    private javax.swing.JButton PrintBtn1;
    private javax.swing.JButton TrazenjePersoneBtn;
    private javax.swing.JButton TrazenjePersoneBtn1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables


}
