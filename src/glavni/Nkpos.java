/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glavni;

import JTree.JTreeMenu;
import forms_pos.Pos;
import forms_pos.PosJPanel;
import forms_pos.Prijava;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.WindowConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import konekcija.konekcija;
import maticni.MaticniPodaciJPanel;
import maticni.PttBrojeviJPanel;
import org.asoft.library.AsoftTaskData;
import terminal_pos.TerminalJPanel;

/**
 *
 * @author Pionir SU
 */
public class Nkpos extends javax.swing.JFrame {

    /**
     * Creates new form Glavni
     */
    Nkpos parent = this;
    public Connection conn;
    public AsoftTaskData td;

    public Nkpos() {

                initComponents();
    }
    
    public Nkpos(AsoftTaskData td) {

        this.td = td;
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        initComponents();
        this.setTitle("ASOFT-POS");
        
        conn = td.getConn();
        // konekcija n = new konekcija();
        setLocationRelativeTo(null);
        ucitajMenu();
        
        // jSplitPane1.setDividerLocation(.2f);
        // jSplitPane1.setDividerSize(20);
        // jSplitPane1.setResizeWeight(0.5);
                
        PosJPanel mpjp = new PosJPanel();

        jTabbedPane2.add(mpjp);

        int aa = jTabbedPane2.indexOfComponent(mpjp);

        /* jTabbedPane2.setTabComponentAt(jTabbedPane2.indexOfComponent(mpjp), 
                    getTitlePanel(jTabbedPane2, mpjp, "Ptt brojevi"));
         */
        int selectedIndex = jTabbedPane2.getSelectedIndex();
        jTabbedPane2.setSelectedIndex(jTabbedPane2.getTabCount() - 1);
        selectedIndex = jTabbedPane2.getSelectedIndex();

//                DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) jTree1.getCellRenderer();
//                
//                Icon closedIcon = new ImageIcon("pictures/fileopen.png");
//                Icon openIcon = new ImageIcon("pictures/filefind.png");
//                Icon leafIcon = new ImageIcon("pictures/fileopen.png");
//                
//                renderer.setClosedIcon(closedIcon);
//                renderer.setOpenIcon(openIcon);
//                renderer.setLeafIcon(leafIcon);
//                
//                this.repaint();
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {  // handler
                if (ke.getKeyCode() == ke.VK_ESCAPE) {
                    // System.out.println("escaped ?");
                    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // trying to close
                    System.exit(0);
                } else {
                    // System.out.println("not escaped");
                }
            }
        });

            setCurrentTime();
            setFlags();
        
    }




    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        try {
            jXTaskPaneBeanInfo1 = new org.jdesktop.swingx.JXTaskPaneBeanInfo();
        } catch (java.beans.IntrospectionException e1) {
            e1.printStackTrace();
        }
        try {
            jXTaskPaneBeanInfo2 = new org.jdesktop.swingx.JXTaskPaneBeanInfo();
        } catch (java.beans.IntrospectionException e1) {
            e1.printStackTrace();
        }
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jPanel1 = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jLabel2 = new javax.swing.JLabel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jXTaskPaneContainer1 = new org.jdesktop.swingx.JXTaskPaneContainer();
        jXTaskPane1 = new org.jdesktop.swingx.JXTaskPane();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jXTaskPane4 = new org.jdesktop.swingx.JXTaskPane();
        jPanel4 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jXTaskPane2 = new org.jdesktop.swingx.JXTaskPane();
        jXTaskPane3 = new org.jdesktop.swingx.JXTaskPane();
        jXTaskPane5 = new org.jdesktop.swingx.JXTaskPane();
        jXTaskPane6 = new org.jdesktop.swingx.JXTaskPane();
        jLabelDateTime = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("JFrame Title");

        jDesktopPane1.setAutoscrolls(true);

        jSplitPane1.setDividerLocation(250);
        jSplitPane1.setDividerSize(8);
        jSplitPane1.setAutoscrolls(true);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/Naslovna1636x1089-44a.jpg"))); // NOI18N
        jLabel2.setToolTipText("Best POS solution in Serbia");
        jLabel2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel2.setAlignmentY(0.0F);
        jLabel2.setIconTextGap(0);
        jLabel2.setMaximumSize(new java.awt.Dimension(1600, 513));
        jLabel2.setMinimumSize(new java.awt.Dimension(1600, 513));
        jLabel2.setName(""); // NOI18N
        jLabel2.setPreferredSize(new java.awt.Dimension(1570, 413));
        jTabbedPane2.addTab("Welcome", jLabel2);

        jSplitPane1.setRightComponent(jTabbedPane2);

        jTabbedPane3.setBackground(new java.awt.Color(204, 204, 204));
        jTabbedPane3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTabbedPane3.setForeground(new java.awt.Color(51, 204, 0));
        jTabbedPane3.setToolTipText("Current date & time");
        jTabbedPane3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jXTaskPaneContainer1.setToolTipText("Pregled poslova");

        jXTaskPane1.setTitle("Menu");

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jButton1.setText("jButton1");

        jButton2.setText("jButton2");

        jLabel1.setForeground(new java.awt.Color(0, 0, 255));
        jLabel1.setText("jLabel1");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jXTaskPane1.getContentPane().add(jPanel3);

        jXTaskPane4.setTitle("Menu");

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jButton3.setText("jButton1");

        jButton4.setText("jButton2");

        jLabel5.setForeground(new java.awt.Color(0, 0, 255));
        jLabel5.setText("jLabel1");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jXTaskPane4.getContentPane().add(jPanel4);

        jXTaskPane1.getContentPane().add(jXTaskPane4);

        jXTaskPaneContainer1.add(jXTaskPane1);

        jXTaskPane2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Floppy_Drive_Small.png"))); // NOI18N
        jXTaskPane2.setTitle("POS - SMARTPOS ");
        jXTaskPane2.setToolTipText("Main menu");
        jXTaskPane2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jXTaskPaneContainer1.add(jXTaskPane2);

        jXTaskPane3.setTitle("Koristi se u ...");
        jXTaskPane3.setToolTipText("");
        jXTaskPaneContainer1.add(jXTaskPane3);

        jXTaskPane5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Floppy_Drive_Small.png"))); // NOI18N
        jXTaskPane5.setTitle("POS - SMARTPOS ");
        jXTaskPane5.setToolTipText("Main menu");
        jXTaskPane5.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jXTaskPaneContainer1.add(jXTaskPane5);

        jXTaskPane6.setTitle("Koristi se u ...");
        jXTaskPane6.setToolTipText("");

        jLabelDateTime.setForeground(new java.awt.Color(0, 0, 255));
        jLabelDateTime.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jXTaskPane6.getContentPane().add(jLabelDateTime);

        jXTaskPaneContainer1.add(jXTaskPane6);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jXTaskPaneContainer1, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jXTaskPaneContainer1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(120, 120, 120))
        );

        jScrollPane1.setViewportView(jPanel2);

        jTabbedPane3.addTab("tab2", jScrollPane1);

        jSplitPane1.setLeftComponent(jTabbedPane3);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1881, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jSplitPane1)
                    .addContainerGap()))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 678, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jSplitPane1)
                    .addContainerGap()))
        );

        jDesktopPane1.setLayer(jPanel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1881, Short.MAX_VALUE)
            .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jDesktopPane1Layout.createSequentialGroup()
                    .addGap(0, 0, 0)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(0, 0, 0)))
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 678, Short.MAX_VALUE)
            .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jDesktopPane1Layout.createSequentialGroup()
                    .addGap(0, 0, 0)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(0, 0, 0)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jDesktopPane1)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jDesktopPane1)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(Nkpos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Nkpos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Nkpos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Nkpos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                Nkpos gt = new Nkpos();
                // new Glavni_test().setVisible(true);
                gt.setVisible(true);
                gt.setExtendedState(MAXIMIZED_BOTH);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabelDateTime;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private org.jdesktop.swingx.JXTaskPane jXTaskPane1;
    private org.jdesktop.swingx.JXTaskPane jXTaskPane2;
    private org.jdesktop.swingx.JXTaskPane jXTaskPane3;
    private org.jdesktop.swingx.JXTaskPane jXTaskPane4;
    private org.jdesktop.swingx.JXTaskPane jXTaskPane5;
    private org.jdesktop.swingx.JXTaskPane jXTaskPane6;
    private org.jdesktop.swingx.JXTaskPaneBeanInfo jXTaskPaneBeanInfo1;
    private org.jdesktop.swingx.JXTaskPaneBeanInfo jXTaskPaneBeanInfo2;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer1;
    // End of variables declaration//GEN-END:variables

    private void ucitajMenu() {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        DefaultMutableTreeNode JRoot = new DefaultMutableTreeNode("Smart POS");
        DefaultMutableTreeNode JMaticniPodaci = new DefaultMutableTreeNode("Matični podaci");
        DefaultMutableTreeNode JProdaja = new DefaultMutableTreeNode("Prodaja");
        JRoot.add(JMaticniPodaci);
        JRoot.add(JProdaja);
        DefaultMutableTreeNode red = new DefaultMutableTreeNode("Države");
        DefaultMutableTreeNode blue = new DefaultMutableTreeNode("Ptt brojevi");
        DefaultMutableTreeNode black = new DefaultMutableTreeNode("Terminal");
        DefaultMutableTreeNode green = new DefaultMutableTreeNode("green");
        JMaticniPodaci.add(red);
        JMaticniPodaci.add(blue);
        JProdaja.add(black);
        JMaticniPodaci.add(green);
        
        JButton button = new JButton();
        try {
            Image img = ImageIO.read(getClass().getResource("pictures/Naslovna.png"));
            button.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        jTabbedPane2.add(button);
        
        // novi menu
        JTreeMenu jTreeMenu = new JTreeMenu();
        jXTaskPane4.add(jTreeMenu);
        
        final JTree jtreeMenu1 = new JTree(JRoot);
        Font font1 = jtreeMenu1.getFont();
        int size = font1.getSize();
        final Font currentFont = jtreeMenu1.getFont();
        final Font bigFont = new Font(currentFont.getName(), currentFont.getStyle(), currentFont.getSize() + 2);
        
                ImageIcon imageIcon = new ImageIcon(JTreeMenu.class.getResource("/icone_nove/leaf.jpg"));
        DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();        
        renderer.setLeafIcon(imageIcon);
        
        jtreeMenu1.setCellRenderer(renderer);
        
        //final Font bigFont = new Font("Tahoma", Font.PLAIN, 15);
        /*
        final Font bigFont = new Font("Arial", Font.PLAIN, 12);
        final Font bigFont = new Font(currentFont.getName(), currentFont.getStyle(), currentFont.getSize() + 2);
        jtreeMenu1.setFont(bigFont);
        */
        expandTree(jtreeMenu1, true);
//    jt.expandPath(style);
        jXTaskPane2.add(jtreeMenu1);
//    f.setSize(200,200);
//    f.setVisible(true);
//        JTextField jtf = new JTextField("", 20);
//        add(jtf, BorderLayout.SOUTH)

//        JButton button = new JButton();
//        try {
//
//            button.setIcon(new ImageIcon(ImageIO.read(Thread.currentThread().getContextClassLoader().getResourceAsStream("/pictures/Naslovna.jpeg"))));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        JLabel jl = new JLabel(new ImageIcon("pictures/Naslovna.jpeg"));
//        jTabbedPane2.add(jl);


        jtreeMenu1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                doMouseClicked(me);
            }

            void doMouseClicked(MouseEvent me) {

                TreePath tp = jtreeMenu1.getPathForLocation(me.getX(), me.getY());
                if (tp != null) {
                    jLabel1.setText(tp.toString());
                } else {
                    jLabel1.setText("");
                }

                System.out.print(tp.toString());

                if (tp.toString().equals("[Smart POS, Matični podaci, Države]")) {

                    MaticniPodaciJPanel mpjp = new MaticniPodaciJPanel(parent, td, "zemlja");
                    mpjp.setOpaque(false);
                    jTabbedPane2.add(mpjp);

                    int aa = jTabbedPane2.indexOfComponent(mpjp);
                    jTabbedPane2.setTabComponentAt(jTabbedPane2.indexOfComponent(mpjp), getTitlePanel(jTabbedPane2, mpjp, "Države"));

//                    jTabbedPane2.grabFocus();
//                    jTabbedPane2.requestFocus();
// get the currently selected index for this tabbedpane
                    int selectedIndex = jTabbedPane2.getSelectedIndex();

                    System.out.println("Default Index:" + selectedIndex);

                    // select the last tab
                    jTabbedPane2.setSelectedIndex(jTabbedPane2.getTabCount() - 1);

                    selectedIndex = jTabbedPane2.getSelectedIndex();

                    System.out.println("New Index:" + selectedIndex);

                } else if (tp.toString().equals("[Smart POS, Matični podaci, Ptt brojevi]")) {
                    
                    PttBrojeviJPanel mpjp = new PttBrojeviJPanel(parent, td, "ptt_brojevi");
                    mpjp.setOpaque(false);
                    jTabbedPane2.add(mpjp);
                    int aa = jTabbedPane2.indexOfComponent(mpjp);
                    jTabbedPane2.setTabComponentAt(jTabbedPane2.indexOfComponent(mpjp), getTitlePanel(jTabbedPane2, mpjp, "Ptt brojevi"));
                    int selectedIndex = jTabbedPane2.getSelectedIndex();
                    jTabbedPane2.setSelectedIndex(jTabbedPane2.getTabCount() - 1);
                    selectedIndex = jTabbedPane2.getSelectedIndex();
                    
                } else if (tp.toString().equals("[Smart POS, Prodaja, Terminal]")) {
                    
                    TerminalJPanel mpjp = new TerminalJPanel(parent, conn);
                    mpjp.setOpaque(false);
                    jTabbedPane2.add(mpjp);
                    int aa = jTabbedPane2.indexOfComponent(mpjp);
                    jTabbedPane2.setTabComponentAt(jTabbedPane2.indexOfComponent(mpjp), getTitlePanel(jTabbedPane2, mpjp, "Terminal"));
                    int selectedIndex = jTabbedPane2.getSelectedIndex();
                    jTabbedPane2.setSelectedIndex(jTabbedPane2.getTabCount() - 1);
                    selectedIndex = jTabbedPane2.getSelectedIndex();
                     
                }

            }

//        private Component getTitlePanel(JTabbedPane jTabbedPane2, JPanel panel, String tab1) {
//            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        }
            private JPanel getTitlePanel(final JTabbedPane tabbedPane, final JPanel panel, String title) {
                JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
                titlePanel.setOpaque(false);
                JLabel titleLbl = new JLabel(title);
                titleLbl.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
                titlePanel.add(titleLbl);
                JButton closeButton = new JButton("x");
                closeButton.setFont(bigFont);
                closeButton.setForeground(Color.red); // .setBackground(Color.RED);
                closeButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        tabbedPane.remove(panel);
                    }
                });
                titlePanel.add(closeButton);
                return titlePanel;
            }

        });
        
// jxtask5        
        
        
    }

    private void expandTree(JTree tree, boolean expand) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        TreeNode root = (TreeNode) tree.getModel().getRoot();
        expandAll(tree, new TreePath(root), expand);
    }

    private void expandAll(JTree tree, TreePath path, boolean expand) {
        TreeNode node = (TreeNode) path.getLastPathComponent();

        if (node.getChildCount() >= 0) {
            Enumeration enumeration = node.children();
            while (enumeration.hasMoreElements()) {
                TreeNode n = (TreeNode) enumeration.nextElement();
                TreePath p = path.pathByAddingChild(n);

                expandAll(tree, p, expand);
            }
        }

        if (expand) {
            tree.expandPath(path);
        } else {
            tree.collapsePath(path);
        }
    }

    public void setCurrentTime() {
        ActionListener actiondate = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                java.util.Date mydate = new java.util.Date();
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
                String date = sdf.format(new java.util.Date());
                // jTabbedPane3.setBackground(Color.yellow);
                // jTabbedPane3.repaint();
                jTabbedPane3.setBackgroundAt(0, Color.RED);
                jTabbedPane3.setTitleAt(0, date);
                // jLabelDateTime.setText(date);
                if (mydate.getSeconds() > 10 && mydate.getSeconds() < 20) {
//                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
//                String date = sdf.format(new java.util.Date());
//                System.out.println(date); //15/10/2013

                    // jLabel3.setIcon(new ImageIcon(getClass().getResource("/icone_nove1/wallie.gif")));
//                    jLabel3.setText(date);

                } else if (mydate.getSeconds() > 20 && mydate.getSeconds() < 30) {
                    // jLabel3.setIcon(new ImageIcon(getClass().getResource("/icone_nove1/smartpos3.jpg")));
                } else if (mydate.getSeconds() > 30 && mydate.getSeconds() < 40) {
                    // jLabel3.setIcon(new ImageIcon(getClass().getResource("/icone_nove1/EVE-icon-512.png")));
                } else if (mydate.getSeconds() > 40 && mydate.getSeconds() < 50) {
                    // jLabel3.setIcon(new ImageIcon(getClass().getResource("/icone_nove1/Wall-E-icon-512.png")));
                } else if (mydate.getSeconds() > 50 && mydate.getSeconds() < 60) {
                    // jLabel3.setIcon(new ImageIcon(getClass().getResource("/icone_nove1/smartpos3.jpg")));
                }

            }

        };
        new javax.swing.Timer(1000, actiondate).start();
    }

    public void setFlags() {

        final String[] allSubFiles = {"/flags/Macedonia-Flag-icon-48.png", 
            "/flags/Bosnian-Flag-icon-48.png", "/flags/Croatian-Flag-icon-48.png", 
            "/flags/Ex-Yugoslavia-Flag-icon-48.png", "/flags/Montenegro-Flag-icon-48.png", 
            "/flags/Serbia-Flag-icon-48.png", "/flags/Slovenia-Flag--48.png", "/images-pionir/pionir-logo.png",
            "/images-pionir/pionir.jpg", "/flags/United-Kingdom-flag-icon-48.png"};        
        ActionListener actiondate = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Random rn = new Random();
                int range = allSubFiles.length - 1 + 1;
                int randomNum = rn.nextInt(range); // + 1;
                jLabelDateTime.setIcon(new ImageIcon(getClass().getResource(allSubFiles[randomNum])));
//                java.util.Date mydate = new java.util.Date();
   
//                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
//                String date = sdf.format(new java.util.Date());
//                jLabel4.setText(date);
//                if (mydate.getSeconds() > 10 && mydate.getSeconds() < 20) {
////                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
////                String date = sdf.format(new java.util.Date());
////                System.out.println(date); //15/10/2013
//                    jLabel5.setIcon(new ImageIcon(getClass().getResource("/flags/Macedonia-icon-64.png")));
////                    jLabel5.setText(date);
//                } else if (mydate.getSeconds() > 20 && mydate.getSeconds() < 30) {
//                    jLabel5.setIcon(new ImageIcon(getClass().getResource("/flags/Croatia-flat-icon-64.png")));
//                } else if (mydate.getSeconds() > 30 && mydate.getSeconds() < 40) {
//                    jLabel5.setIcon(new ImageIcon(getClass().getResource("/flags/Serbia-Flag-icon-64.png")));
//                } else if (mydate.getSeconds() > 40 && mydate.getSeconds() < 50) {
//                    jLabel5.setIcon(new ImageIcon(getClass().getResource("/flags/Montenegro-icon-64.png")));
//                } else if (mydate.getSeconds() > 50 && mydate.getSeconds() < 60) {
//                    jLabel5.setIcon(new ImageIcon(getClass().getResource("/flags/Bosnian-icon-64.png")));
//                } else if (mydate.getSeconds() > 0 && mydate.getSeconds() < 10) {
//                    jLabel5.setIcon(new ImageIcon(getClass().getResource("/flags/Kosovo-icon-64.png")));
//                }
//                jLabel5.setIcon(new ImageIcon(getClass().getResource("/icone_nove1/wallie.gif")));
            }

        };
        new javax.swing.Timer(1000, actiondate).start();
    }
}
