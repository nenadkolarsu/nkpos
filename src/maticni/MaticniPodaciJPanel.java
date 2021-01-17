/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maticni;

import forms_pos.Main;
import forms_pos.Prijava;
import glavni.Glavni;
import glavni.Nkpos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
//import static maticni.MaticniPodaci.logger;
import org.apache.log4j.PropertyConfigurator;
import org.asoft.library.AsoftTaskData;
import org.asoft.library.entiteti.AsoftTabele;
import org.asoft.library.entiteti.Postanski_brojTable;
import org.asoft.library.entiteti.ZemljaTable;

/**
 *
 * @author Pionir SU
 */
public class MaticniPodaciJPanel extends javax.swing.JPanel {

    private Connection conn;
    private String tabela;
    private int rekordaUslektu;
    // public Glavni parent;
    private Nkpos parent;
    private AsoftTaskData td;
    private ArrayList myList = new ArrayList();

    /**
     * Creates new form MaticniPodaciJPanel
     */
    public MaticniPodaciJPanel() {
        initComponents();
    }

    public MaticniPodaciJPanel(Nkpos parent, final AsoftTaskData td, final String tabela) {
        this.td = td;
        this.parent = parent;
        this.conn = td.getConn();
        this.tabela = tabela;

        String log4jConfigFile = System.getProperty("user.dir")
                + File.separator + "log4j.properties";
        PropertyConfigurator.configure(log4jConfigFile);

        initComponents();

        punjenjeJtableIPrikazModelaSaUslovom(filterTxt.getText());
        /* if (!Prijava.getKosam()) {
            UnosBtn.setEnabled(false);
            IzmenaBtn.setEnabled(false);
            BrisanjeBtn.setEnabled(false);
            PregledBtn.setEnabled(false);
        }
         */

        // Button to show the Next user from the List
        NextBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String mmId = mId.getText();
                Integer pos = Integer.valueOf(mmId);

                pos++;

                ShowPosInfo(pos, " order by id asc LIMIT 1");

                DefaultTableModel model = (DefaultTableModel) tblSifre.getModel();
                String id = model.getValueAt(tblSifre.getSelectedRow(), 4).toString();
                int foo = Integer.parseInt(id);
                if (foo < rekordaUslektu - 1) {
                    foo++;
                }
                tblSifre.changeSelection(foo, 0, false, false);
            }

            private void ShowPosInfo(Integer pos, String orderilimit) {

                try {
                    Statement stmt = conn.createStatement();

                    /*
                    String sqlQuery = " select * from " + tabela + " where id >= "
                            + pos.toString() + orderilimit;
                     */
                    String sqlQuery = String.format("SELECT * FROM %s." + tabela + " WHERE ID >= "
                            + pos.toString() + orderilimit,
                            td.getSchema(AsoftTabele.SCHEMA.ZEMLJA));

                    ResultSet rs = stmt.executeQuery(sqlQuery);

                    while (rs.next()) {
                        mId.setText(rs.getString(1));
                        mSifra.setText(rs.getString(2));
                        mNaziv.setText(rs.getString(3));
                        int mmAktivan = rs.getInt("aktivan");
                        if (mmAktivan == 1) {
                            mAktivan.setSelected(true);
                        } else {
                            mAktivan.setSelected(false);
                        }
                        recordLbl.setText(rs.getString(1));
                    }
                } catch (SQLException ex) {
                    Main.track.info("this is a information log message" + ex);
                }
            }
        }
        );

        PrevBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String mmId = mId.getText();
                Integer pos = Integer.valueOf(mmId);
                Integer pozicija;

                pos--;
                pozicija = ShowPosInfo(pos, " order by id desc LIMIT 1");
                DefaultTableModel model = (DefaultTableModel) tblSifre.getModel();
                String id = model.getValueAt(tblSifre.getSelectedRow(), 4).toString();

                int foo = Integer.parseInt(id);
                if (foo > 0) {
                    foo--;
                }
                tblSifre.changeSelection(foo, 0, false, false);

            }

            private Integer ShowPosInfo(Integer pos, String orderilimit) {

                Integer vracam = 0;
//                recordLbl.setText(pos.toString());

                try {
                    Statement stmt = conn.createStatement();

                    /*
                    String sqlQuery = " select * from " + tabela + " where id <= "
                            + pos.toString() + orderilimit;
                     */
                    String sqlQuery = String.format("SELECT * FROM %s." + tabela + " WHERE ID <= "
                            + pos.toString() + orderilimit,
                            td.getSchema(AsoftTabele.SCHEMA.ZEMLJA));

//                    System.out.println(sqlQuery);
                    ResultSet rs = stmt.executeQuery(sqlQuery);

                    while (rs.next()) {
                        vracam = rs.getInt(1);
                        mId.setText(rs.getString(1));
                        mSifra.setText(rs.getString(2));
                        mNaziv.setText(rs.getString(3));
//                        mDrzava.setSelectedItem(rs.getString("drzava"));
                        int mmAktivan = rs.getInt("aktivan");
                        if (mmAktivan == 1) {
                            mAktivan.setSelected(true);
                        } else {
                            mAktivan.setSelected(false);
                        }
                        recordLbl.setText(rs.getString(1));
                    }
                } catch (SQLException ex) {

                    Main.track.info("this is a information log message" + ex);
                }
                return vracam;
            }
        }
        );

        // Button to show the Next user from the List
        FirstBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String mmId = mId.getText();
                Integer pos = Integer.valueOf(mmId);

//                pos++;
                if (pos < ListSifre("").size()) {
                    ShowPosInfo(pos, " order by id desc ");
                } else {
                    pos = ListSifre("").size() - 1;
                    ShowPosInfo(pos, " order by id desc ");
//                    JOptionPane.showMessageDialog(null, " START ");
                }

                tblSifre.changeSelection(0, 1, false, false);

            }

            private void ShowPosInfo(Integer pos, String orderilimit) {

                try {
                    Statement stmt = conn.createStatement();

                    /*
                    String sqlQuery = " select * from " + tabela + " where id < "
                            + pos.toString() + orderilimit;
                     */
                    String sqlQuery = String.format("SELECT * FROM %s." + tabela + " WHERE ID < "
                            + pos.toString() + orderilimit,
                            td.getSchema(AsoftTabele.SCHEMA.ZEMLJA));

//                    System.out.println(sqlQuery);
                    ResultSet rs = stmt.executeQuery(sqlQuery);

                    while (rs.next()) {
                        mId.setText(rs.getString(1));
                        mSifra.setText(rs.getString(2));
                        mNaziv.setText(rs.getString(3));
//                        mDrzava.setSelectedItem(rs.getString("drzava"));
                        int mmAktivan = rs.getInt("aktivan");
                        if (mmAktivan == 1) {
                            mAktivan.setSelected(true);
                        } else {
                            mAktivan.setSelected(false);
                        }
                        recordLbl.setText(rs.getString(1));
                    }
                } catch (SQLException ex) {
                    Main.track.info("First button " + ex);
                }
            }

        }
        );

        LastBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String mmId = mId.getText();
                Integer pos = Integer.valueOf(mmId);

                if (pos < ListSifre("").size()) {
                    ShowPosInfo(pos, " order by id asc ");
                } else {
                    pos = ListSifre("").size() - 1;
                    ShowPosInfo(pos, " order by id asc ");
                }

                int rows = tblSifre.getRowCount() - 1;
                tblSifre.changeSelection(rows, 0, false, false);
            }

            private void ShowPosInfo(Integer pos, String orderilimit) {

                recordLbl.setText(pos.toString());

                try {
                    Statement stmt = conn.createStatement();

                    /*
                    String sqlQuery = String.format("SELECT * FROM %s." + tabela + " WHERE ID > " 
                            + pos.toString() + orderilimit ,
                     */
                    String sqlQuery = String.format("SELECT * FROM %s." + tabela + " WHERE ID > "
                            + pos.toString() + orderilimit,
                            td.getSchema(AsoftTabele.SCHEMA.ZEMLJA));

                    ResultSet rs = stmt.executeQuery(sqlQuery);

                    while (rs.next()) {
                        mId.setText(rs.getString(1));
                        mSifra.setText(rs.getString(2));
                        mNaziv.setText(rs.getString(3));
                        int mmAktivan = rs.getInt("aktivan");
                        if (mmAktivan == 1) {
                            mAktivan.setSelected(true);
                        } else {
                            mAktivan.setSelected(false);
                        }
                        recordLbl.setText(rs.getString(1));
                    }
                } catch (SQLException ex) {
                    Main.track.info("this is a information log message" + ex);
                }
            }
        }
        );

        FirstBtn.doClick();
                
    }

//    public MaticniPodaciJPanel(Glavni parent) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        UnosBtn = new javax.swing.JButton();
        IzmenaBtn = new javax.swing.JButton();
        BrisanjeBtn = new javax.swing.JButton();
        IzlazBtn = new javax.swing.JButton();
        filterTxt = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        PregledBtn = new javax.swing.JButton();
        FirstBtn = new javax.swing.JButton();
        PrevBtn = new javax.swing.JButton();
        recordLbl = new javax.swing.JLabel();
        NextBtn = new javax.swing.JButton();
        LastBtn = new javax.swing.JButton();
        prikazAktivnih = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lTabela = new javax.swing.JLabel();
        mAktivan = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        mNaziv = new javax.swing.JTextField();
        mSifra = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        mId = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSifre = new javax.swing.JTable();
        iMessage = new javax.swing.JLabel();

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        UnosBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/new.png"))); // NOI18N
        UnosBtn.setText("Unos");
        UnosBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UnosBtnActionPerformed(evt);
            }
        });

        IzmenaBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/edit.png"))); // NOI18N
        IzmenaBtn.setText("Izmena");
        IzmenaBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IzmenaBtnActionPerformed(evt);
            }
        });

        BrisanjeBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/delete_1.png"))); // NOI18N
        BrisanjeBtn.setText("Brisanje");
        BrisanjeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BrisanjeBtnActionPerformed(evt);
            }
        });

        IzlazBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/exit.png"))); // NOI18N
        IzlazBtn.setText("Izlaz");
        IzlazBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IzlazBtnActionPerformed(evt);
            }
        });

        filterTxt.setToolTipText("Pretraga");
        filterTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                filterTxtKeyReleased(evt);
            }
        });

        jLabel5.setText("Pretraga:");

        PregledBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/fileprint.png"))); // NOI18N
        PregledBtn.setText("Pregled");
        PregledBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PregledBtnActionPerformed(evt);
            }
        });

        FirstBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/c.png"))); // NOI18N
        FirstBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FirstBtnActionPerformed(evt);
            }
        });

        PrevBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/go-previous.png"))); // NOI18N
        PrevBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrevBtnActionPerformed(evt);
            }
        });

        recordLbl.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        recordLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        recordLbl.setText("0");
        recordLbl.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        recordLbl.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        NextBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/go-next.png"))); // NOI18N
        NextBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NextBtnActionPerformed(evt);
            }
        });

        LastBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/d.png"))); // NOI18N
        LastBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LastBtnActionPerformed(evt);
            }
        });

        prikazAktivnih.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prikazAktivnihActionPerformed(evt);
            }
        });

        jLabel6.setText("Prikaz neaktivnih:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(UnosBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(IzmenaBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(BrisanjeBtn)
                        .addGap(12, 12, 12)
                        .addComponent(PregledBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(IzlazBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(filterTxt))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(FirstBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(PrevBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(recordLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(NextBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LastBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(44, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(prikazAktivnih)
                        .addGap(37, 37, 37))))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {BrisanjeBtn, IzlazBtn, IzmenaBtn, PregledBtn, UnosBtn});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(FirstBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PrevBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(NextBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LastBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(recordLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(UnosBtn)
                        .addComponent(IzmenaBtn)
                        .addComponent(BrisanjeBtn)
                        .addComponent(IzlazBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(PregledBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(filterTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5)
                        .addComponent(jLabel6))
                    .addComponent(prikazAktivnih))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {BrisanjeBtn, IzlazBtn, IzmenaBtn, PregledBtn, UnosBtn});

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lTabela.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        lTabela.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        mAktivan.setSelected(true);
        mAktivan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mAktivanActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel4.setText("Aktivan:");

        jLabel3.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel3.setText("Naziv:");

        mNaziv.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N

        mSifra.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        mSifra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mSifraActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel2.setText("Šifra:");

        jLabel1.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel1.setText("Id:");

        mId.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        mId.setEnabled(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(mAktivan)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(mNaziv)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(mSifra, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(mId, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lTabela, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(22, 22, 22))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 13, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(mId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(mSifra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lTabela, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mNaziv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mAktivan)
                    .addComponent(jLabel4)))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tblSifre.setAutoCreateRowSorter(true);
        tblSifre.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        tblSifre.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        tblSifre.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Sifra", "Naziv", "Aktivan", "Rbr"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblSifre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSifreMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSifre);

        iMessage.setBackground(new java.awt.Color(153, 204, 255));
        iMessage.setForeground(new java.awt.Color(255, 0, 0));
        iMessage.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(iMessage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(iMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void UnosBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UnosBtnActionPerformed
        try {
            iMessage.setText("");
            DefaultTableModel model = (DefaultTableModel) tblSifre.getModel();
            ZemljaTable zemlja = new ZemljaTable(conn, td.getSchema(AsoftTabele.SCHEMA.ZEMLJA));
//
            if (!mSifra.getText().trim().equals("")) {
                
                zemlja.data.sifra = mSifra.getText();
                zemlja.data.naziv = mNaziv.getText();

                long insertData = zemlja.insertData();
            } else {
                iMessage.setText("Šifra nemože biti prazna ...");
            }

            /*
            if (!mSifra.getText().trim().equals("")) {
                
                try {
                    
                    Statement stmt = conn.createStatement();
                    
                    String sqlQuery = " insert into " + tabela + "(sifra, naziv, aktivan "
                            + ") "
                            + "values ('" + mSifra.getText() + "', '"
                            + mNaziv.getText() + "', '" + vratiJedanIliNula(mAktivan.isSelected())
                            + "')";
                    
                    stmt.executeUpdate(sqlQuery);
                    
                } catch (Exception e) {
                    try {
                        conn.rollback();
                    } catch (SQLException ex) {
                        //          Logger.getLogger(putniNaloziZ.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    e.printStackTrace();
                } finally {
                    //      if (rset != null) { try { rset.close();  rset = null; } catch(Exception e){e.printStackTrace();} }
                    //      if (stmt != null) { try { stmt.close();  stmt = null; } catch(Exception e){e.printStackTrace();} }
                }
                model.addRow(new Object[]{mId.getText(), mSifra.getText(), mNaziv.getText(), mAktivan.isSelected()});
                mId.setText("");
                mSifra.setText("");
                mNaziv.setText("");
                mAktivan.setSelected(true);
            } else {
                iMessage.setText("Šifra nemože biti prazna ...");
            }
             */
            //        punjenjeJtable();
            filterTxt.setText("");
            punjenjeJtableIPrikazModelaSaUslovom(filterTxt.getText());
        } catch (SQLException ex) {
            Logger.getLogger(MaticniPodaciJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_UnosBtnActionPerformed

    private void IzmenaBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IzmenaBtnActionPerformed
        iMessage.setText("");
        DefaultTableModel model = (DefaultTableModel) tblSifre.getModel();
        if (tblSifre.getSelectedRow() == -1) {
            if (tblSifre.getRowCount() == 0) {
                iMessage.setText("Tabela je prazna");
            } else {
                iMessage.setText("Morate izabrati promenu");
            }
        } else {
            model.setValueAt(mId.getText(), tblSifre.getSelectedRow(), 0);
            model.setValueAt(mSifra.getText(), tblSifre.getSelectedRow(), 1);
            model.setValueAt(mNaziv.getText(), tblSifre.getSelectedRow(), 2);
            model.setValueAt(mAktivan.isSelected(), tblSifre.getSelectedRow(), 3);
            Statement stmt = null;
            try {
                stmt = conn.createStatement();

                String sqlQuery = null;

                sqlQuery = " UPDATE " + tabela + " SET sifra='" + mSifra.getText()
                        + "', naziv='" + mNaziv.getText()
                        + "', aktivan='" + vratiJedanIliNula(mAktivan.isSelected())
                        + "' WHERE id = '" + mId.getText()
                        + "'";

                System.out.println(sqlQuery);
                stmt.execute(sqlQuery);
                // FillList();

            } catch (SQLException ex) {
                //                Logger.getLogger(SearchWithTab.class.getName()).log(Level.SEVERE, null, ex);
                //                   obj.runMe("Exception update " + ex);
                Main.track.info("Update table SQLException " + ex);
            }
        }
    }//GEN-LAST:event_IzmenaBtnActionPerformed

    private void BrisanjeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BrisanjeBtnActionPerformed
        iMessage.setText("");
        DefaultTableModel model = (DefaultTableModel) tblSifre.getModel();
        if (tblSifre.getSelectedRow() == -1) {
            if (tblSifre.getRowCount() == 0) {
                iMessage.setText("Tabela je prazna");
            } else {
                iMessage.setText("Morate izabrati promenu");
            }
        } else {
            //            model.removeRow(tblSifre.getSelectedRow());

            //  model.setValueAt(mSifra.getText(),tblSifre.getSelectedRow(), 1);
            //  model.setValueAt(mNaziv.getText(),tblSifre.getSelectedRow(), 2);
            Statement stmt = null;
            try {
                stmt = conn.createStatement();

                String sqlQuery = null;

                sqlQuery = " DELETE FROM " + tabela
                        + " WHERE id = '" + mId.getText()
                        + "'";
                sqlQuery = " UPDATE " + tabela
                        + " SET aktivan=0 WHERE id = '" + mId.getText()
                        + "'";
                //                System.out.println(sqlQuery);
                // display the showOptionDialog
                int choice = JOptionPane.showOptionDialog(this,
                        "Da li ste sigurni u brisanje?",
                        "Brisanje?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null, null, null);

                // interpret the user's choice
                if (choice == JOptionPane.YES_OPTION) {
                    stmt.execute(sqlQuery);
                    model.removeRow(tblSifre.getSelectedRow());
                    // FillList();
                }

            } catch (SQLException ex) {
                Main.track.info("Brisanje " + tabela + " SQLException " + ex);
                //                Logger.getLogger(SearchWithTab.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_BrisanjeBtnActionPerformed

    private void IzlazBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IzlazBtnActionPerformed
//        parent.
//        this.dispose();
    }//GEN-LAST:event_IzlazBtnActionPerformed

    private void filterTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_filterTxtKeyReleased
        // TODO add your handling code here:
        //        String query = filterTxt.getText();
        //        filter(query);
        //        findUsers();
        punjenjeJtableIPrikazModelaSaUslovom(filterTxt.getText());
    }//GEN-LAST:event_filterTxtKeyReleased

    private void PregledBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PregledBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PregledBtnActionPerformed

    private void FirstBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FirstBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FirstBtnActionPerformed

    private void PrevBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PrevBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PrevBtnActionPerformed

    private void NextBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NextBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NextBtnActionPerformed

    private void LastBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LastBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LastBtnActionPerformed

    private void mAktivanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mAktivanActionPerformed
        System.out.println(mAktivan.isSelected());
        if (mAktivan.isSelected()) {
            mAktivan.setSelected(true);
        } else {
            mAktivan.setSelected(false);
        }
    }//GEN-LAST:event_mAktivanActionPerformed

    private void mSifraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mSifraActionPerformed
        // TODO add your handling code here:
        mNaziv.requestFocus();
    }//GEN-LAST:event_mSifraActionPerformed

    private void tblSifreMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSifreMouseClicked

        int index = tblSifre.getSelectedRow();

        TableModel model = tblSifre.getModel();
        String sqlQuery = "";

        String id = model.getValueAt(index, 0).toString();

        try {
            Statement stmt = conn.createStatement();
            sqlQuery = String.format("SELECT * FROM %s.zemlja WHERE ID = " + id,
                    td.getSchema(AsoftTabele.SCHEMA.ZEMLJA));

            /*
            String sqlQuery = " select * from " + '%s.' + tabela + " where id like '" + id
            + "'";
             */
            ResultSet rs = stmt.executeQuery(sqlQuery);

            while (rs.next()) {
                mId.setText(rs.getString("id"));
                mSifra.setText(rs.getString("sifra"));
                mNaziv.setText(rs.getString("naziv"));
                boolean mmAktivan = rs.getBoolean("aktivan");
                if (mmAktivan) {
                    mAktivan.setSelected(true);
                } else {
                    mAktivan.setSelected(false);
                }

                //                mPrezime.setText(rs.getString(4));
                //                mImeOca.setText(rs.getString(5));
                //                mImeMajke.setText(rs.getString(6));
                //                Date a = rs.getDate(9);
                //                mDatumRodj.setDate(a);
                //                try {
                //                    mDatumRodj.setDate(rs.getDate(9));
                //                } catch (ParseException ex) {
                //                    Logger.getLogger(SearchWithJTable.class.getName()).log(Level.SEVERE, null, ex);
                //                }
                //                String ddd = rs.getString("mestorodj");
                //                mMestoRodj.setSelectedItem(rs.getString("mestorodj"));
                //                mDrzavljanstvo.setSelectedItem(rs.getString("drzavljanstvo"));
                //                mNacionalnost.setSelectedItem(rs.getString("nacionalnost"));
                //                mTelefon.setText(rs.getString("telefon"));
                //                mEmail.setText(rs.getString("e_mail"));
            }
        } catch (Exception ex) {
            //            Logger.getLogger(this.
            //            class
            //
            //        .getName()).log(Level.SEVERE, null, ex);
            Main.track.info("Mouse click on table Exception " + ex);
        }

        // ovo punjenje nevraca kada je filter ukljucen
        //        DefaultTableModel model = (DefaultTableModel) tblSifre.getModel();
        //        mId.setText(model.getValueAt(tblSifre.getSelectedRow(), 0).toString());
        //        mSifra.setText(model.getValueAt(tblSifre.getSelectedRow(), 1).toString());
        //        mNaziv.setText(model.getValueAt(tblSifre.getSelectedRow(), 2).toString());
        //        mAktivan.setSelected((Boolean) model.getValueAt(tblSifre.getSelectedRow(), 3));
        // kraj ovo punjenje nevraca kada je filter ukljucen
    }//GEN-LAST:event_tblSifreMouseClicked

    private void prikazAktivnihActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prikazAktivnihActionPerformed
        // TODO add your handling code here:
        punjenjeJtableIPrikazModelaSaUslovom(filterTxt.getText());
    }//GEN-LAST:event_prikazAktivnihActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BrisanjeBtn;
    private javax.swing.JButton FirstBtn;
    private javax.swing.JButton IzlazBtn;
    private javax.swing.JButton IzmenaBtn;
    private javax.swing.JButton LastBtn;
    private javax.swing.JButton NextBtn;
    private javax.swing.JButton PregledBtn;
    private javax.swing.JButton PrevBtn;
    private javax.swing.JButton UnosBtn;
    private javax.swing.JTextField filterTxt;
    private javax.swing.JLabel iMessage;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lTabela;
    private javax.swing.JCheckBox mAktivan;
    private javax.swing.JTextField mId;
    private javax.swing.JTextField mNaziv;
    private javax.swing.JTextField mSifra;
    private javax.swing.JCheckBox prikazAktivnih;
    private javax.swing.JLabel recordLbl;
    private javax.swing.JTable tblSifre;
    // End of variables declaration//GEN-END:variables

    private void punjenjeJtableIPrikazModelaSaUslovom(String ValToSearch) {

        lTabela.setText(" " + tabela);
        DefaultTableModel model = (DefaultTableModel) tblSifre.getModel();
        TableColumnModel tcm = tblSifre.getColumnModel();

//        tblSifre.getColumnModel().getColumn(0).setPreferredWidth(10);
//        tblSifre.getColumnModel().getColumn(1).setPreferredWidth(10);
//        tblSifre.getColumnModel().getColumn(2).setPreferredWidth(170);
//        tblSifre.getColumnModel().getColumn(3).setPreferredWidth(10);
        tcm.getColumn(0).setPreferredWidth(10);     //Name
        tcm.getColumn(1).setPreferredWidth(10);    //Title
        tcm.getColumn(2).setPreferredWidth(170);    //Surname
        tcm.getColumn(3).setPreferredWidth(10);    //ID
        tcm.getColumn(4).setPreferredWidth(10);    //ID
//        tblSifre.getColumnModel().getColumn(0).setPreferredWidth(10);
//        tblSifre.getColumnModel().getColumn(1).setPreferredWidth(10);
//        tblSifre.getColumnModel().getColumn(2).setPreferredWidth(170);
//        tblSifre.getColumnModel().getColumn(3).setPreferredWidth(10);
//        tblSifre.removeAll();
        if (model.getRowCount() > 0) {
            for (int i = model.getRowCount() - 1; i > -1; i--) {
                model.removeRow(i);
            }
        }
//        model.setValueAt(mId.getText(), tblSifre.getSelectedRow(), 0);
//        model.setValueAt(mSifra.getText(), tblSifre.getSelectedRow(), 1);
//        model.setValueAt(mNaziv.getText(), tblSifre.getSelectedRow(), 2);

        myList.clear();
        Connection conn = td.getConn();

        ZemljaTable zemlja = new ZemljaTable(conn, td.getSchema(AsoftTabele.SCHEMA.ZEMLJA));
        Postanski_brojTable posta = new Postanski_brojTable(conn, td.getSchema(AsoftTabele.SCHEMA.POSTANSKI_BROJ));

        String aktivan="false";
        ResultSet rs = null;
        Statement stmt = null;
                
        try {

            stmt = conn.createStatement();
            // String[] array = yourString.split(wordSeparator);
//            String aa = (String) List1.getSelectedValue();
//            String[] niz = aa.split(AsoftComboBox.COMBOBOX_ITEM_SEPARATOR);
//            LOWER(something)

//            String searchQuery = String.format("SELECT * FROM %s." + tabela + " WHERE "
//                    + "LOWER(CONCAT(`id`, `sifra`, `naziv`)) LIKE LOWER('%" + ValToSearch + "%') order by id"
//                    + "LOWER(CONCAT(id, sifra, naziv)) LIKE LOWER('%" + ValToSearch + "%') order by id"                    
//                    ,
//                    td.getSchema(AsoftTabele.SCHEMA.ZEMLJA));

        aktivan="false";
        
        if (prikazAktivnih.isSelected()) {
                aktivan="true";
            } else {
                aktivan="false";
            }
        

            ValToSearch = "%" + ValToSearch + "%";
            String searchQuery = String.format("SELECT * FROM %s." + tabela + " WHERE "
                    // + "LOWER(CONCAT(id, sifra, naziv)) LIKE LOWER('%" + ValToSearch + "%') order by id"
                    + "LOWER(CONCAT(id, sifra, naziv)) LIKE LOWER('%s') and aktivan = '%s' order by id",
                     td.getSchema(AsoftTabele.SCHEMA.ZEMLJA), ValToSearch, aktivan);

            System.out.print(searchQuery);
            /*            
        String sqlQuery1 = String.format("SELECT * FROM %s.zemlja WHERE aktivan and vazeci",
                td.getSchema(AsoftTabele.SCHEMA.ZEMLJA));                    
             */
//            String searchQuery = "SELECT * FROM " + tabela + " WHERE "
//                    + "CONCAT(`id`, `sifra`, `naziv`) LIKE '%" + ValToSearch + "%' order by id";
           rs = stmt.executeQuery(searchQuery);
            rekordaUslektu = 0;
            while (rs.next()) {
                mId.setText(rs.getString("id"));
                mSifra.setText(rs.getString("sifra"));
                mNaziv.setText(rs.getString("naziv"));
                boolean mmAktivan = rs.getBoolean("aktivan");
//                    int cetvrti = rs.getInt(5);
                // boolean mmAktivan = (rs.getInt(5) == 1) ? true : false;

                mAktivan.setSelected(mmAktivan);
//                    if (rs.getInt(4))
                model.addRow(new Object[]{mId.getText(), mSifra.getText(), mNaziv.getText(), mmAktivan, rekordaUslektu});
                rekordaUslektu++;
            }
            /*
            mId.setText("");
            mSifra.setText("");
            mNaziv.setText("");
            mAktivan.setSelected(true);
             */
        } catch (SQLException ex) {
            Main.track.info("punjenjeJtableIPrikazModelaSaUslovom " + tabela + " SQLException " + ex);
        } 
        finally {
            if (rs != null) {
                try {
                    rs.close();
                    rs = null;
                } catch (Exception e) {
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                    stmt = null;
                } catch (Exception e) {
                }
            }
        }

    }

    private ArrayList<model.MaticniPodaci> ListSifre(String ValToSearch) {
        ArrayList<model.MaticniPodaci> usersList = new ArrayList<model.MaticniPodaci>();
        Statement st;
        ResultSet rs;
        try {
            // Connection con = getConnection();
            st = conn.createStatement();
            String searchQuery = "SELECT * FROM " + tabela + " WHERE "
                    + "CONCAT(`id`, `sifra`, `naziv`) LIKE '%" + ValToSearch + "%'";
            rs = st.executeQuery(searchQuery);
            model.MaticniPodaci pttbrojevi;
            while (rs.next()) {
                pttbrojevi = new model.MaticniPodaci(rs.getInt("id"), rs.getString("sifra"), rs.getString("naziv"));
                usersList.add(pttbrojevi);
            }
        } catch (Exception ex) {
            // System.out.println(ex.getMessage());
            Main.track.info("Kreiranje ListSifre " + ex);
        }
        return usersList;
    }

    private int vratiJedanIliNula(boolean selected) {
        if (selected) {
            return 1;
        } else {
            return 0;
        }
    }

}
