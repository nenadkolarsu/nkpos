/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maticni;

import forms_pos.Main;
import forms_pos.Prijava;
import glavni.Glavni;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
//import static maticni.MaticniPodaci.logger;
import service.puniComboMaticni;

/**
 *
 * @author Pionir SU
 */
public class PttBrojeviJPanel extends javax.swing.JPanel {

    private Connection conn;
    private String tabela;
    private int rekordaUslektu;
    public Glavni parent;

    /**
     * Creates new form PttBrojeviJPanel
     */
    public PttBrojeviJPanel() {
        initComponents();
    }

    public PttBrojeviJPanel(Glavni parent, final Connection conn, final String tabela) {

        this.parent = parent;
        this.conn = conn;
        this.tabela = tabela;
        initComponents();
//        tblSifre.setDefaultRenderer(Object.class, new MyCellRenderer());
        List<String> listDrzave = puniComboMaticni.puni_maticne(conn, "drzave");
        mDrzava.setModel(new DefaultComboBoxModel(listDrzave.toArray()));
//        setLocationRelativeTo(null);
//        punjenjeJtable(); 
        punjenjeJtableIPrikazModelaSaUslovom(filterTxt.getText());
        if (!Prijava.getKosam()) {
            UnosBtn.setEnabled(false);
            IzmenaBtn.setEnabled(false);
            BrisanjeBtn.setEnabled(false);
//            PregledBtn.setEnabled(false);
        }
        // Button to show the Next user from the List
        NextBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String mmId = mId.getText();
                Integer pos = Integer.valueOf(mmId);

                pos++;
//                if (pos < ListSifre("").size()) {
//                    ShowPosInfo(pos, " order by id asc LIMIT 1");
//                } else {
//                    pos = ListSifre("").size() - 1;
//                    ShowPosInfo(pos, " order by id asc LIMIT 1");
//                    JOptionPane.showMessageDialog(null, " THE END ");
//                }

                ShowPosInfo(pos, " order by id asc LIMIT 1");

//                System.out.println(pos);                
//                tblSifre.clearSelection();
//                tblSifre.changeSelection(pos, 1, false, false);
                DefaultTableModel model = (DefaultTableModel) tblSifre.getModel();
                String id = model.getValueAt(tblSifre.getSelectedRow(), 5).toString();
//                int rows = rekordaUslektu; // tblSifre.getRowCount()-1;
//                int cols = tblSifre.getColumnCount();

//                tblSifre.clearSelection();
//                System.out.println(id);   
                int foo = Integer.parseInt(id);
                if (foo < rekordaUslektu - 1) {
                    foo++;
                }
                tblSifre.changeSelection(foo, 0, false, false);
            }

            private void ShowPosInfo(Integer pos, String orderilimit) {

//                recordLbl.setText(pos.toString());
                try {
                    Statement stmt = conn.createStatement();

                    String sqlQuery = " select * from " + tabela + " where id >= "
                            + pos.toString() + orderilimit;
                    ResultSet rs = stmt.executeQuery(sqlQuery);

                    while (rs.next()) {
                        mId.setText(rs.getString(1));
                        mSifra.setText(rs.getString(2));
                        mNaziv.setText(rs.getString(3));
                        mDrzava.setSelectedItem(rs.getString("drzava"));
                        int mmAktivan = rs.getInt("aktivan");
                        if (mmAktivan == 1) {
                            mAktivan.setSelected(true);
                        } else {
                            mAktivan.setSelected(false);
                        }
                        recordLbl.setText(rs.getString(1));
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(PttBrojevi.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        );

        // Button to show the Next user from the List
        PrevBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String mmId = mId.getText();
                Integer pos = Integer.valueOf(mmId);
                Integer pozicija;

                pos--;
                pozicija = ShowPosInfo(pos, " order by id desc LIMIT 1");
//                if (pos < ListSifre("").size()) {
//                    pozicija = ShowPosInfo(pos, " order by id desc LIMIT 1");
//                } else {
//                    pos = ListSifre("").size() - 1;
//                    pozicija = ShowPosInfo(pos, " order by id desc LIMIT 1");
//                    JOptionPane.showMessageDialog(null, " START ");
//                }

//                pozicija = ShowPosInfo(pos, " order by id desc LIMIT 1");             
//                DefaultTableModel model = (DefaultTableModel) tblSifre.getModel();
//                String id = model.getValueAt(tblSifre.getSelectedRow(), 5).toString(); // ovde treba da je za 1 manje
////
////                tblSifre.clearSelection();
////                System.out.println(id);   
//                int foo = Integer.parseInt(id);
//                foo--;
//                tblSifre.changeSelection(foo, 0, false, false);               
////                tblSifre.changeSelection(pozicija, 0, false, false);   
                DefaultTableModel model = (DefaultTableModel) tblSifre.getModel();
                String id = model.getValueAt(tblSifre.getSelectedRow(), 5).toString();
//                int rows = tblSifre.getRowCount()-1;
//                int cols = tblSifre.getColumnCount();

//                tblSifre.clearSelection();
//                System.out.println(id);   
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

                    String sqlQuery = " select * from " + tabela + " where id <= "
                            + pos.toString() + orderilimit;

//                    System.out.println(sqlQuery);
                    ResultSet rs = stmt.executeQuery(sqlQuery);

                    while (rs.next()) {
                        vracam = rs.getInt(1);
                        mId.setText(rs.getString(1));
                        mSifra.setText(rs.getString(2));
                        mNaziv.setText(rs.getString(3));
                        mDrzava.setSelectedItem(rs.getString("drzava"));
                        int mmAktivan = rs.getInt("aktivan");
                        if (mmAktivan == 1) {
                            mAktivan.setSelected(true);
                        } else {
                            mAktivan.setSelected(false);
                        }
                        recordLbl.setText(rs.getString(1));
                    }
                } catch (SQLException ex) {
//            Logger.getLogger(SearchWithTab.class.getName()).log(Level.SEVERE, null, ex);
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

//                System.out.println(pos);                                
//                tblSifre.clearSelection();
//                tblSifre.changeSelection(pos, 1, false, false);        
//                DefaultTableModel model = (DefaultTableModel) tblSifre.getModel();
//                String id = model.getValueAt(tblSifre.getSelectedRow(), 5).toString();
//
//                tblSifre.clearSelection();
//                System.out.println(id);   
//                int foo = Integer.parseInt(id);
                tblSifre.changeSelection(0, 1, false, false);

            }

            private void ShowPosInfo(Integer pos, String orderilimit) {

                try {
                    Statement stmt = conn.createStatement();

                    String sqlQuery = " select * from " + tabela + " where id < "
                            + pos.toString() + orderilimit;

//                    System.out.println(sqlQuery);
                    ResultSet rs = stmt.executeQuery(sqlQuery);

                    while (rs.next()) {
                        mId.setText(rs.getString(1));
                        mSifra.setText(rs.getString(2));
                        mNaziv.setText(rs.getString(3));
                        mDrzava.setSelectedItem(rs.getString("drzava"));
                        int mmAktivan = rs.getInt("aktivan");
                        if (mmAktivan == 1) {
                            mAktivan.setSelected(true);
                        } else {
                            mAktivan.setSelected(false);
                        }
                        recordLbl.setText(rs.getString(1));
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(PttBrojevi.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        );

        LastBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String mmId = mId.getText();
                Integer pos = Integer.valueOf(mmId);

//                pos++;
                if (pos < ListSifre("").size()) {
                    ShowPosInfo(pos, " order by id asc ");
                } else {
                    pos = ListSifre("").size() - 1;
                    ShowPosInfo(pos, " order by id asc ");
//                    JOptionPane.showMessageDialog(null, " END ");
                }

//                DefaultTableModel model = (DefaultTableModel) tblSifre.getModel();
//                String id = model.getValueAt(tblSifre.getSelectedRow(), 5).toString();
                int rows = tblSifre.getRowCount() - 1;
//                int cols = tblSifre.getColumnCount();

//                tblSifre.clearSelection();
//                System.out.println(id);   
//                int foo = Integer.parseInt(id);
//                if (foo<rows)
//                foo++;
                tblSifre.changeSelection(rows, 0, false, false);
            }

            private void ShowPosInfo(Integer pos, String orderilimit) {

                recordLbl.setText(pos.toString());

                try {
                    Statement stmt = conn.createStatement();

                    String sqlQuery = " select * from " + tabela + " where id > "
                            + pos.toString() + orderilimit;

//                    System.out.println(sqlQuery);
                    ResultSet rs = stmt.executeQuery(sqlQuery);

                    while (rs.next()) {
                        mId.setText(rs.getString(1));
                        mSifra.setText(rs.getString(2));
                        mNaziv.setText(rs.getString(3));
                        mDrzava.setSelectedItem(rs.getString("drzava"));
                        int mmAktivan = rs.getInt("aktivan");
                        if (mmAktivan == 1) {
                            mAktivan.setSelected(true);
                        } else {
                            mAktivan.setSelected(false);
                        }
                        recordLbl.setText(rs.getString(1));
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(PttBrojevi.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        );
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        mId = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        mSifra = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        mNaziv = new javax.swing.JTextField();
        mDrzava = new javax.swing.JComboBox();
        jLabel32 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        mAktivan = new javax.swing.JCheckBox();
        lTabela = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        UnosBtn = new javax.swing.JButton();
        IzmenaBtn = new javax.swing.JButton();
        BrisanjeBtn = new javax.swing.JButton();
        IzlazBtn = new javax.swing.JButton();
        filterTxt = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        FirstBtn = new javax.swing.JButton();
        PrevBtn = new javax.swing.JButton();
        NextBtn = new javax.swing.JButton();
        LastBtn = new javax.swing.JButton();
        recordLbl = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        iMessage = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSifre = new javax.swing.JTable();

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel1.setText("Id:");

        mId.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        mId.setEnabled(false);
        mId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mIdActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel2.setText("Šifra:");

        mSifra.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        mSifra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mSifraActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel3.setText("Naziv:");

        mNaziv.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N

        mDrzava.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N

        jLabel32.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel32.setText("Država:");

        jLabel4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel4.setText("Aktivan:");

        mAktivan.setSelected(true);
        mAktivan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mAktivanActionPerformed(evt);
            }
        });

        lTabela.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lTabela.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel32, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(36, 36, 36)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(mId, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(mSifra, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lTabela, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(mDrzava, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(mNaziv))
                        .addGap(44, 44, 44))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(mAktivan)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lTabela, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(mId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(mSifra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(mNaziv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(mDrzava, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mAktivan)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18))
        );

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

        recordLbl.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        recordLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        recordLbl.setText("0");
        recordLbl.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        recordLbl.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(UnosBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel5)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(IzmenaBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(BrisanjeBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(IzlazBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(FirstBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(PrevBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(recordLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(NextBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LastBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(filterTxt))
                .addGap(7, 7, 7))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(FirstBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PrevBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(UnosBtn)
                        .addComponent(IzmenaBtn)
                        .addComponent(BrisanjeBtn)
                        .addComponent(IzlazBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(NextBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LastBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(recordLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(filterTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(10, 10, 10))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {BrisanjeBtn, IzlazBtn, IzmenaBtn, UnosBtn});

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        iMessage.setBackground(new java.awt.Color(153, 204, 255));
        iMessage.setForeground(new java.awt.Color(255, 0, 0));
        iMessage.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        tblSifre.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tblSifre.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        tblSifre.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Sifra", "Naziv", "Drzava", "Aktivan", "Rbr"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class
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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(iMessage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(iMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(13, 13, 13))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void mIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mIdActionPerformed

    private void mSifraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mSifraActionPerformed
        // TODO add your handling code here:
        mNaziv.requestFocus();
    }//GEN-LAST:event_mSifraActionPerformed

    private void mAktivanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mAktivanActionPerformed
        System.out.println(mAktivan.isSelected());
        if (mAktivan.isSelected()) {
            mAktivan.setSelected(true);
        } else {
            mAktivan.setSelected(false);
        }
    }//GEN-LAST:event_mAktivanActionPerformed

    private void UnosBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UnosBtnActionPerformed
        iMessage.setText("");
        DefaultTableModel model = (DefaultTableModel) tblSifre.getModel();

        if (!mSifra.getText().trim().equals("")) {
            try {

                Statement stmt = conn.createStatement();

                //                int mmAktivan = mAktivan.isSelected() ? 1 : 0;
                //                vratiJedanIliNula(mAktivan.isSelected());
                String sqlQuery = " insert into " + tabela + "(sifra, naziv, drzava, aktivan "
                        + ") "
                        + "values ('" + mSifra.getText() + "', '"
                        + mNaziv.getText() + "', '" + mDrzava.getSelectedItem().toString() + "', '"
                        + vratiJedanIliNula(mAktivan.isSelected())
                        + "')";

                //                System.out.println(sqlQuery);
                stmt.executeUpdate(sqlQuery);
                //    conn.commit();

                //                JOptionPane.showMessageDialog(null, "Upisano:");
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
            model.addRow(new Object[]{mId.getText(), mSifra.getText(), mNaziv.getText(), mDrzava.getSelectedItem().toString(), mAktivan.isSelected()});
            mId.setText("");
            mSifra.setText("");
            mNaziv.setText("");
            mDrzava.setSelectedItem("");
            mAktivan.setSelected(true);
        } else {
            iMessage.setText("Šifra nemože biti prazna ...");
        }

        //        punjenjeJtable();
        filterTxt.setText("");
        punjenjeJtableIPrikazModelaSaUslovom(filterTxt.getText());
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
            model.setValueAt(mDrzava.getSelectedItem().toString(), tblSifre.getSelectedRow(), 3);
            model.setValueAt(mAktivan.isSelected(), tblSifre.getSelectedRow(), 4);
            Statement stmt = null;
            try {
                stmt = conn.createStatement();

                String sqlQuery = null;

                sqlQuery = " UPDATE " + tabela + " SET sifra='" + mSifra.getText()
                        + "', naziv='" + mNaziv.getText() + "', drzava='"
                        + mDrzava.getSelectedItem().toString()
                        + "', aktivan='" + vratiJedanIliNula(mAktivan.isSelected())
                        + "' WHERE id = '" + mId.getText()
                        + "'";

                //                System.out.println(sqlQuery);
                stmt.execute(sqlQuery);
                // FillList();

            } catch (SQLException ex) {
                //                Logger.getLogger(SearchWithTab.class.getName()).log(Level.SEVERE, null, ex);
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
                System.out.println(sqlQuery);
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
                //                Logger.getLogger(SearchWithTab.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_BrisanjeBtnActionPerformed

    private void IzlazBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IzlazBtnActionPerformed
//        this.dispose();
    }//GEN-LAST:event_IzlazBtnActionPerformed

    private void filterTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_filterTxtKeyReleased
        // TODO add your handling code here:
        //        String query = filterTxt.getText();
        //        filter(query);
        //        findUsers();
        punjenjeJtableIPrikazModelaSaUslovom(filterTxt.getText());
    }//GEN-LAST:event_filterTxtKeyReleased

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

    private void tblSifreMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSifreMouseClicked

        int index = tblSifre.getSelectedRow();
        TableModel model = tblSifre.getModel();
        String id = model.getValueAt(index, 0).toString();

        try {
            Statement stmt = conn.createStatement();
            String sqlQuery = " select * from " + tabela + " where id like '" + id
                    + "'";
            ResultSet rs = stmt.executeQuery(sqlQuery);

            while (rs.next()) {
                mId.setText(rs.getString(1));
                mSifra.setText(rs.getString(2));
                mNaziv.setText(rs.getString(3));
                mDrzava.setSelectedItem(rs.getString("drzava"));
                int mmAktivan = rs.getInt("aktivan");
                if (mmAktivan == 1) {
                    mAktivan.setSelected(true);
                } else {
                    mAktivan.setSelected(false);
                }
                recordLbl.setText(mId.getText());
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
        }

        // ovo punjenje nevraca kada je filter ukljucen
        //        DefaultTableModel model = (DefaultTableModel) tblSifre.getModel();
        //        mId.setText(model.getValueAt(tblSifre.getSelectedRow(), 0).toString());
        //        mSifra.setText(model.getValueAt(tblSifre.getSelectedRow(), 1).toString());
        //        mNaziv.setText(model.getValueAt(tblSifre.getSelectedRow(), 2).toString());
        //        mAktivan.setSelected((Boolean) model.getValueAt(tblSifre.getSelectedRow(), 3));
        //        kraj ovo punjenje nevraca kada je filter ukljucen
    }//GEN-LAST:event_tblSifreMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BrisanjeBtn;
    private javax.swing.JButton FirstBtn;
    private javax.swing.JButton IzlazBtn;
    private javax.swing.JButton IzmenaBtn;
    private javax.swing.JButton LastBtn;
    private javax.swing.JButton NextBtn;
    private javax.swing.JButton PrevBtn;
    private javax.swing.JButton UnosBtn;
    private javax.swing.JTextField filterTxt;
    private javax.swing.JLabel iMessage;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lTabela;
    private javax.swing.JCheckBox mAktivan;
    private javax.swing.JComboBox mDrzava;
    private javax.swing.JTextField mId;
    private javax.swing.JTextField mNaziv;
    private javax.swing.JTextField mSifra;
    private javax.swing.JLabel recordLbl;
    private javax.swing.JTable tblSifre;
    // End of variables declaration//GEN-END:variables

    private void punjenjeJtableIPrikazModelaSaUslovom(String ValToSearch) {

        lTabela.setText(" " + tabela);
        DefaultTableModel model = (DefaultTableModel) tblSifre.getModel();
        TableColumnModel tcm = tblSifre.getColumnModel();
        tcm.getColumn(0).setPreferredWidth(20);     //Name
        tcm.getColumn(1).setPreferredWidth(40);    //Title
        tcm.getColumn(2).setPreferredWidth(150);    //Surname
        tcm.getColumn(3).setPreferredWidth(150);    //Surname
        tcm.getColumn(4).setPreferredWidth(10);    //ID
        tcm.getColumn(5).setPreferredWidth(10);    //rbr       
//        tblSifre.removeAll();
        if (model.getRowCount() > 0) {
            for (int i = model.getRowCount() - 1; i > -1; i--) {
                model.removeRow(i);
            }
        }
//        model.setValueAt(mId.getText(), tblSifre.getSelectedRow(), 0);
//        model.setValueAt(mSifra.getText(), tblSifre.getSelectedRow(), 1);
//        model.setValueAt(mNaziv.getText(), tblSifre.getSelectedRow(), 2);
        try {
            Statement stmt = conn.createStatement();
            // String[] array = yourString.split(wordSeparator);
//            String aa = (String) List1.getSelectedValue();
//            String[] niz = aa.split(AsoftComboBox.COMBOBOX_ITEM_SEPARATOR);
//            LOWER(something)
            String searchQuery = "SELECT * FROM " + tabela + " WHERE "
                    + "LOWER(CONCAT(`id`, `sifra`, `naziv`)) LIKE LOWER('%" + ValToSearch + "%') order by id";

//            String searchQuery = "SELECT * FROM " + tabela + " WHERE "
//                    + "CONCAT(`id`, `sifra`, `naziv`) LIKE '%" + ValToSearch + "%' order by id";
            ResultSet rs = stmt.executeQuery(searchQuery);
            rekordaUslektu = 0;

            while (rs.next()) {

                mId.setText(rs.getString(1));
                mSifra.setText(rs.getString(2));
                mNaziv.setText(rs.getString(3));
                mDrzava.setSelectedItem(rs.getString("drzava"));
//                    int cetvrti = rs.getInt(5);
                boolean mmAktivan = (rs.getInt("aktivan") == 1) ? true : false;
                mAktivan.setSelected(mmAktivan);

//                    if (rs.getInt(4))
                model.addRow(new Object[]{mId.getText(), mSifra.getText(), mNaziv.getText(),
                    mDrzava.getSelectedItem().toString(), mmAktivan, rekordaUslektu});
                rekordaUslektu++;
            }

            mId.setText("");
            mSifra.setText("");
            mNaziv.setText("");
            mDrzava.setSelectedItem("");
            mAktivan.setSelected(true);

        } catch (SQLException ex) {
//            Logger.getLogger(SearchWithTab.class.getName()).log(Level.SEVERE, null, ex);
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
