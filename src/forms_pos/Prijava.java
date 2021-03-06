package forms_pos;

import glavni.Glavni;
import glavni.Nkpos;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import konekcija.konekcija;
import org.asoft.library.AsoftAutorizacija;
import org.asoft.library.AsoftConstants;
import org.asoft.library.AsoftGroupInfo;
import org.asoft.library.AsoftServerGroupInfo;
import org.asoft.library.AsoftTaskData;
import org.asoft.library.entiteti.AsoftTabele;
import org.asoft.library.entiteti.Korisnik_pravaTable;

/**
 *
 * @author ms
 */
public class Prijava extends javax.swing.JFrame {

    public Connection conn;
    public static boolean kosam;
    public AsoftTaskData td;

    /**
     * Creates new form Prijava
     */
    public Prijava() {
        initComponents();
        setLocationRelativeTo(null);

        mPassword.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    bPrijava.doClick();
                }
            }

        });

        mKorisnik.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    mPassword.grabFocus();
                }
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
            }

        });

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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        bPrijava = new javax.swing.JButton();
        mKorisnik = new javax.swing.JTextField();
        mPassword = new javax.swing.JPasswordField();
        KorisnikName = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        bIzlaz = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SmartPOS");
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel1.setText("Prijava");

        jLabel2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel2.setText("Korisnik:");

        jLabel3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel3.setText("Lozinka:");

        bPrijava.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        bPrijava.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/Windows-Log-Off-icon.png"))); // NOI18N
        bPrijava.setText("Prijava");
        bPrijava.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                bPrijavaMouseReleased(evt);
            }
        });
        bPrijava.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bPrijavaActionPerformed(evt);
            }
        });

        mKorisnik.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        mKorisnik.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mKorisnikActionPerformed(evt);
            }
        });

        mPassword.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N

        KorisnikName.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/Login-icon.png"))); // NOI18N

        bIzlaz.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        bIzlaz.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/Cute-Ball-Shutdown-icon.png"))); // NOI18N
        bIzlaz.setText("Izlaz");
        bIzlaz.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                bIzlazMouseReleased(evt);
            }
        });
        bIzlaz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bIzlazActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel", "com.sun.java.swing.plaf.motif.MotifLookAndFeel", "com.sun.java.swing.plaf.gtk.GTKLookAndFeel", "com.sun.java.swing.plaf.windows.WindowsLookAndFeel", "net.sourceforge.napkinlaf.NapkinLookAndFeel", " " }));
        jComboBox1.setToolTipText("Izbor teme za prikaz");
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel5.setText("Look and feel:");
        jLabel5.setToolTipText("");

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/look-n-feel-icon.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(1, 1, 1)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(mPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
                                    .addComponent(mKorisnik)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(17, 17, 17)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(KorisnikName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(bPrijava, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(bIzlaz, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(90, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(233, 233, 233)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(27, 27, 27)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(mKorisnik, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(mPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel2))))
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bIzlaz, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bPrijava))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(KorisnikName, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(13, 13, 13))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mKorisnikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mKorisnikActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mKorisnikActionPerformed

    private void bPrijavaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bPrijavaMouseReleased
        // TODO add your handling code here:
//        prijava();

    }//GEN-LAST:event_bPrijavaMouseReleased

    private void bPrijavaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bPrijavaActionPerformed
        try {
            // TODO add your handling code here:
            prijava();
        } catch (Exception ex) {
            Logger.getLogger(Prijava.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bPrijavaActionPerformed

    private void bIzlazMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bIzlazMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_bIzlazMouseReleased

    private void bIzlazActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bIzlazActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_bIzlazActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:

        String lookAndFeel = jComboBox1.getItemAt(jComboBox1.getSelectedIndex());
//            lookAndFeel = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
//            lookAndFeel = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
//            lookAndFeel = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
//            lookAndFeel = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
// napkin.NapkinLookAndFeel
// net.sourceforge.napkinlaf.NapkinLookAndFeel

        try {
            UIManager.setLookAndFeel(lookAndFeel);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Prijava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Prijava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Prijava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Prijava.class.getName()).log(Level.SEVERE, null, ex);
        }
        Prijava p = new Prijava();
        p.setVisible(true);
        this.repaint();

    }//GEN-LAST:event_jComboBox1ActionPerformed

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
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//              lookAndFeel = javax.swing.UIManager.getSystemLookAndFeelClassName();
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Prijava.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Prijava.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Prijava.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Prijava.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                new Prijava().setVisible(true);
            }
        });

//            b.addActionListener(new ActionListener() {  
//                    public void actionPerformed(ActionEvent e) {       
//            String data = "Programming language Selected: "   
//               + cb.getItemAt(cb.getSelectedIndex());  
//            label.setText(data);  
//            }  
//            });           
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel KorisnikName;
    private javax.swing.JButton bIzlaz;
    private javax.swing.JButton bPrijava;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField mKorisnik;
    private javax.swing.JPasswordField mPassword;
    // End of variables declaration//GEN-END:variables

    private void prijava() throws Exception {

        if (conn == null) {
            konekcija n = new konekcija();
            conn = n.konekcija();
        }

        try {
            makeTD();
        } catch (Exception e) {
            throw e;
        }

        // da li ima privilegiju
        // *********************
        boolean ima_pravo = false;
        Korisnik_pravaTable kpt = new Korisnik_pravaTable(conn, td.getSchema(AsoftTabele.SCHEMA.KORISNIK_PRAVA));

        if (!td.getAsoftGroupInfo().getImeKorisnika().equals(AsoftConstants.ASOFT_ADMINISTRATOR)) {
            kpt.getKorisnikForIme(td.getAsoftGroupInfo().getImeKorisnika());
            ima_pravo = kpt.korisnikImaPravo(AsoftAutorizacija.Maloprodaja_Razduzenje_MP);
        }
        int privilegija = Korisnik_pravaTable.NEMA_PRAVO;
        
        if (ima_pravo) {
            privilegija = kpt.getKorisnikPravo(AsoftAutorizacija.Maloprodaja_Razduzenje_MP);
        }
        
            Nkpos gt = new Nkpos(td);
            gt.setVisible(true);
            this.dispose();

            /*
        if (privilegija > 0 || td.getAsoftGroupInfo().getImeKorisnika().equals(AsoftConstants.ASOFT_ADMINISTRATOR)) {

            KorisnikName.setText("you are succefully logged in.."); //0 = new JLabel("you are succefully logged in..");
            KorisnikName.setForeground(Color.blue);
            KorisnikName.setFont(new Font("Serif", Font.BOLD, 30));
            KorisnikName.setBounds(60, 50, 400, 30);

            
            Glavni_test gt = new Glavni_test();
            gt.setVisible(true);
            this.dispose();

        } else {
            JOptionPane.showMessageDialog(null, "Neispravna prijava", "Obaveštenje", JOptionPane.ERROR_MESSAGE);
        }
        */
    }

    public static boolean getKosam() {
        return kosam;
    }

    void makeTD() throws Exception {
        String curfirma = "Pionir 2019";
        AsoftTabele asofttabele;
        AsoftServerGroupInfo sgi;
        asofttabele = new AsoftTabele(conn, curfirma, null);
        AsoftGroupInfo currentgroup = new AsoftGroupInfo(asofttabele, (short) 2021, new ArrayList<String>());
        td = AsoftTaskData.nextTask(AsoftAutorizacija.dummy_entry, conn, currentgroup);
        sgi = AsoftServerGroupInfo.getOrCreateServerGroupInfo("default");
        sgi.setImeKorisnika(mKorisnik.getText());
        currentgroup.setServerGroupInfo(sgi);
    }

}
