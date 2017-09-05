package maticni.artikli;

import maticni.*;
import forms_pos.Main;
import forms_pos.Prijava;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
//import java.util.logging.Level;
//import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import konekcija.konekcija;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import model.ArtikliPakovanja;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import org.asoft.library.AsoftDFP;
import static org.asoft.library.AsoftDFP.NULA;
import org.asoft.library.AsoftDate;
import service.MyLogger;
import static service.OperacijeSaSifrom.daLiPostojiDuplaSifra;
import static service.id_false.id_false;
import service.puniComboMaticni;
import static service.uzmiParametar.uzmiParametar;
import static service.vrati_id_za_naziv.vrati_id_za_naziv;
//import static service.vrati_id_za_naziv.vrati_id_za_naziv_artikla;
import static service.vrati_id_za_sifru.vrati_id_za_sifru;
//import static service.vrati_id_za_sifru_artikla.vrati_id_za_sifru_artikla;

/**
 *
 * @author ms
 */
public class Pakovanja extends javax.swing.JFrame {

    ArrayList<ArtikliPakovanja> pakovanjaArray = new ArrayList<ArtikliPakovanja>();
    Connection conn;
    String tabela, sifraArtikla = "";
    int rekordaUslektu = 0;
    MyLogger ml = new MyLogger();

    public Pakovanja() {

        initComponents();
        MyLogger.logger.info("Pakovanja");
        this.setTitle(tabela);
        setLocationRelativeTo(null);
        konekcija n = new konekcija();
        conn = n.konekcija();
        tabela = "artikli_pakovanja";
        nuliranjeForme();
        PripremaTabelePakovanja(sifraArtikla);

    }

    public Pakovanja(final Connection conn, final String tabela, String sifraArtikla) {

        this.conn = conn;
        this.tabela = tabela;
        this.sifraArtikla = sifraArtikla;

        initComponents();
        MyLogger.logger.info("Pakovanja");
        this.setTitle(tabela);

        setLocationRelativeTo(null);
        nuliranjeFormeBezSifre();
        PripremaTabelePakovanja(sifraArtikla);
        mSifra.setText(sifraArtikla); 
        filterTxt.setText(sifraArtikla); 
        
        if (!Prijava.getKosam()) {
            UnosBtn.setEnabled(false);
            IzmenaBtn.setEnabled(false);
            BrisanjeBtn.setEnabled(false);
            PregledBtn.setEnabled(false);
        }

        // Button to show the Next user from the List
        NextBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String mmId = mId.getText();
                Integer pos = Integer.valueOf(mmId);

                pos++;

                ShowPosInfo(pos, " and aktivan order by id asc LIMIT 1");
                DefaultTableModel model = (DefaultTableModel) tblSifre.getModel();
                String id = model.getValueAt(tblSifre.getSelectedRow(), 10).toString();
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
                    MyLogger.logger.info(ex.toString());
                    iMessage.setText(ex.toString());

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
                pozicija = ShowPosInfo(pos, " and aktivan order by id desc LIMIT 1");
                DefaultTableModel model = (DefaultTableModel) tblSifre.getModel();
                String id = model.getValueAt(tblSifre.getSelectedRow(), 10).toString();
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
                    MyLogger.logger.info(ex.toString());
                    iMessage.setText(ex.toString());
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

                    String sqlQuery = " select * from " + tabela + " where id < "
                            + pos.toString() + orderilimit;

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
                    MyLogger.logger.info(ex.toString());
                    iMessage.setText(ex.toString());
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
//                String id = model.getValueAt(tblSifre.getSelectedRow(), 4).toString();
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
                    MyLogger.logger.info(ex.toString());
                    iMessage.setText(ex.toString());
                }
            }
        }
        );

//        nuliranjeForme();

    }

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
        RefreshBtn = new javax.swing.JButton();
        filterTxt = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        PregledBtn = new javax.swing.JButton();
        FirstBtn = new javax.swing.JButton();
        PrevBtn = new javax.swing.JButton();
        recordLbl = new javax.swing.JLabel();
        NextBtn = new javax.swing.JButton();
        LastBtn = new javax.swing.JButton();
        IzlazBtn = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        mId = new javax.swing.JTextField();
        mSifra = new javax.swing.JTextField();
        mNaziv = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        mAktivan = new javax.swing.JCheckBox();
        jPanel20 = new javax.swing.JPanel();
        mEAN = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        mVrstaPakovanjaArtikala = new javax.swing.JComboBox<String>();
        jPanel19 = new javax.swing.JPanel();
        mTezina = new org.asoft.library.AsoftNumericField();
        jLabel31 = new javax.swing.JLabel();
        mAmbalaza = new javax.swing.JComboBox<String>();
        jLabel36 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        mNetoUJm = new org.asoft.library.AsoftNumericField();
        mBrutoUJm = new org.asoft.library.AsoftNumericField();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        mKomada = new org.asoft.library.AsoftNumericField();
        jPanel17 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        mSirina = new org.asoft.library.AsoftNumericField();
        jLabel29 = new javax.swing.JLabel();
        mDuzina = new org.asoft.library.AsoftNumericField();
        jLabel30 = new javax.swing.JLabel();
        mVisina = new org.asoft.library.AsoftNumericField();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSifre = new javax.swing.JTable();
        iMessage = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        UnosBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/Files-New-File-icon.png"))); // NOI18N
        UnosBtn.setText("Unos");
        UnosBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UnosBtnActionPerformed(evt);
            }
        });

        IzmenaBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/Actions-document-edit-icon.png"))); // NOI18N
        IzmenaBtn.setText("Izmena");
        IzmenaBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IzmenaBtnActionPerformed(evt);
            }
        });

        BrisanjeBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/delete-file-icon.png"))); // NOI18N
        BrisanjeBtn.setText("Brisanje");
        BrisanjeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BrisanjeBtnActionPerformed(evt);
            }
        });

        RefreshBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/Refresh-icon.png"))); // NOI18N
        RefreshBtn.setText("Osveži");
        RefreshBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefreshBtnActionPerformed(evt);
            }
        });

        filterTxt.setToolTipText("Pretraga");
        filterTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                filterTxtKeyReleased(evt);
            }
        });

        jLabel5.setText("Pretraga:");

        PregledBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/print-icon.png"))); // NOI18N
        PregledBtn.setText("Pregled");
        PregledBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PregledBtnActionPerformed(evt);
            }
        });

        FirstBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/Actions-go-first-view-icon-24.png"))); // NOI18N
        FirstBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FirstBtnActionPerformed(evt);
            }
        });

        PrevBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/Actions-go-previous-view-icon-24.png"))); // NOI18N
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

        NextBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/Actions-go-next-view-icon-24.png"))); // NOI18N
        NextBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NextBtnActionPerformed(evt);
            }
        });

        LastBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/Actions-go-last-view-icon-24.png"))); // NOI18N
        LastBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LastBtnActionPerformed(evt);
            }
        });

        IzlazBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/Action-exit-icon.png"))); // NOI18N
        IzlazBtn.setText("Izlaz");
        IzlazBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IzlazBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(UnosBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(IzmenaBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(BrisanjeBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(12, 12, 12)
                        .addComponent(PregledBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(FirstBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(PrevBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(recordLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(NextBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LastBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(RefreshBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(IzlazBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(filterTxt))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {FirstBtn, LastBtn, NextBtn, PrevBtn});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(FirstBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PrevBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LastBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(RefreshBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(IzlazBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(UnosBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, Short.MAX_VALUE)
                        .addComponent(IzmenaBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BrisanjeBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(PregledBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(recordLbl, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(NextBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(filterTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {BrisanjeBtn, IzlazBtn, IzmenaBtn, PregledBtn, RefreshBtn, UnosBtn});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {FirstBtn, LastBtn, NextBtn, PrevBtn});

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel3.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel3.setText("Naziv:");

        mId.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        mId.setEnabled(false);

        mSifra.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        mSifra.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                mSifraCaretUpdate(evt);
            }
        });
        mSifra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mSifraActionPerformed(evt);
            }
        });
        mSifra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                mSifraKeyPressed(evt);
            }
        });

        mNaziv.setEditable(false);
        mNaziv.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel2.setText("Šifra:");

        jLabel1.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel1.setText("Id:");

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel4.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel4.setText("Aktivan:");

        mAktivan.setSelected(true);
        mAktivan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mAktivanActionPerformed(evt);
            }
        });

        jPanel20.setBackground(new java.awt.Color(255, 255, 102));
        jPanel20.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        mEAN.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N

        jLabel35.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel35.setText("EAN kod:");

        jLabel13.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel13.setText("Vrsta pakovanja:");

        mVrstaPakovanjaArtikala.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        mVrstaPakovanjaArtikala.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        mVrstaPakovanjaArtikala.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mVrstaPakovanjaArtikalaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel35)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                        .addComponent(mEAN, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addGap(0, 92, Short.MAX_VALUE)
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel13)
                            .addComponent(mVrstaPakovanjaArtikala, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addComponent(jLabel13)
                .addGap(18, 18, 18)
                .addComponent(mVrstaPakovanjaArtikala, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mEAN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35))
                .addContainerGap())
        );

        jPanel19.setBackground(new java.awt.Color(255, 102, 102));
        jPanel19.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        mTezina.setText("0.00");
        mTezina.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N

        jLabel31.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel31.setText("Težina:");

        mAmbalaza.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        mAmbalaza.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel36.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel36.setText("Ambalaža:");

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                        .addComponent(jLabel31)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 75, Short.MAX_VALUE)
                        .addComponent(mTezina, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel36))
                    .addComponent(mAmbalaza, 0, 259, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(jLabel36)
                .addGap(18, 18, 18)
                .addComponent(mAmbalaza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mTezina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31))
                .addContainerGap())
        );

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));
        jPanel18.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel32.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel32.setText("Neto u jm:");

        mNetoUJm.setText("0.00");
        mNetoUJm.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        mNetoUJm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mNetoUJmActionPerformed(evt);
            }
        });

        mBrutoUJm.setText("0.00");
        mBrutoUJm.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        mBrutoUJm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mBrutoUJmActionPerformed(evt);
            }
        });

        jLabel33.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel33.setText("Bruto u jm:");

        jLabel34.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel34.setText("Komada:");

        mKomada.setText("0.00");
        mKomada.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        mKomada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mKomadaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel18Layout.createSequentialGroup()
                            .addComponent(jLabel34)
                            .addGap(32, 32, 32))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                            .addComponent(jLabel33)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                        .addComponent(jLabel32)
                        .addGap(18, 18, 18)))
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mKomada, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                    .addComponent(mNetoUJm, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                    .addComponent(mBrutoUJm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mNetoUJm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mBrutoUJm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33))
                .addGap(18, 18, 18)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(mKomada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel17.setBackground(new java.awt.Color(102, 153, 255));
        jPanel17.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel14.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel14.setText("Širina:");

        mSirina.setText("0.00");
        mSirina.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        mSirina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mSirinaActionPerformed(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel29.setText("Dužina:");

        mDuzina.setText("0.00");
        mDuzina.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N

        jLabel30.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel30.setText("Visina:");

        mVisina.setText("0.00");
        mVisina.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap(66, Short.MAX_VALUE)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel30)
                    .addComponent(jLabel14)
                    .addComponent(jLabel29))
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(mSirina, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(mVisina, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(mDuzina, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mSirina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mDuzina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29))
                .addGap(18, 18, 18)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mVisina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(mAktivan)
                .addContainerGap(1105, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(160, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mAktivan)
                    .addComponent(jLabel4)))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel18, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(34, 34, 34)))
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jPanel17, jPanel18, jPanel19, jPanel20});

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(mId, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(mSifra, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(mNaziv, javax.swing.GroupLayout.PREFERRED_SIZE, 462, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(22, 22, 22))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(mId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(mSifra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mNaziv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tblSifre.setAutoCreateRowSorter(true);
        tblSifre.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        tblSifre.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        tblSifre.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Title 2", "Sifra", "Datum", "Title 5", "Nabavna", "Planska", "Prodajna", "Prodajna sa PDV", "Maloprodajna bez PDV", "Maloprodajna", "Aktivan", "Rbr"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
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
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
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

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mSifraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mSifraActionPerformed
        // TODO add your handling code here:
//        mDatumCenovnika.requestFocus();
    }//GEN-LAST:event_mSifraActionPerformed

    private void UnosBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UnosBtnActionPerformed

        iMessage.setText("");

        DefaultTableModel model = (DefaultTableModel) tblSifre.getModel();

        if (!DaLiPostojiPakovanje(mSifra.getText(),  vrati_id_za_naziv(conn, "vrste_pakovanja_artikala", mVrstaPakovanjaArtikala.getSelectedItem().toString()), "1")) {
            return;
        }
        
        if (!Validation()) {
            return;
        }

        try {
            String SQLinsertPakovanja = " insert into "
                    + " artikli_pakovanja ("
                    + " artikal_id, artikal_sifra, "
                    + " tippak, ean, \n"
                    + "                naziv, jm, netojm, \n"
                    + " brutojm, \n"
                    + " sirina, visina, duzina, komada,  aktivan, \n"
                    + "                sifra_ambalaze, tezina ) \n"
                    + "values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement stmt
                    = conn.prepareStatement(SQLinsertPakovanja, Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, vrati_id_za_sifru(conn, "artikli", mSifra.getText()));
            stmt.setString(2, mSifra.getText());
            stmt.setInt(3, vrati_id_za_naziv(conn, "vrste_pakovanja_artikala", mVrstaPakovanjaArtikala.getSelectedItem().toString()));
            stmt.setString(4, mEAN.getText());
            stmt.setString(5, "");
            stmt.setString(6, "");
            stmt.setDouble(7, mNetoUJm.getValueDFP().doubleValue());
            stmt.setDouble(8, mBrutoUJm.getValueDFP().doubleValue());
            stmt.setDouble(9, mSirina.getValueDFP().doubleValue());
            stmt.setDouble(10, mVisina.getValueDFP().doubleValue());
            stmt.setDouble(11, mDuzina.getValueDFP().doubleValue());
            stmt.setDouble(12, mKomada.getValueDFP().doubleValue());
            stmt.setInt(13, 1);
            stmt.setInt(14, vrati_id_za_naziv(conn, "ambalaza", mAmbalaza.getSelectedItem().toString()));
            stmt.setDouble(15, mTezina.getValueDFP().doubleValue());

            if (uzmiParametar(conn, "debugiranje").equals("1")) {
                System.out.println(stmt);
            }
            int i = stmt.executeUpdate();

            iMessage.setText("Podatak je uspesno dodat ...");
            MyLogger.logger.info("Podatak je uspesno dodat " + mSifra.getText());

        } catch (Exception e) {

            iMessage.setText(e.toString());
            MyLogger.logger.error(e);
        }

        PripremaTabelePakovanja(mSifra.getText());
        nuliranjeForme();
    }//GEN-LAST:event_UnosBtnActionPerformed

    private void IzmenaBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IzmenaBtnActionPerformed

        iMessage.setText("");

        DefaultTableModel model = (DefaultTableModel) tblSifre.getModel();
        if (tblSifre.getSelectedRow() == -1) {
            if (tblSifre.getRowCount() == 0) {
                iMessage.setText("Tabela je prazna");
            } else {
                iMessage.setText("Morate izabrati rekord");
            }
        } else {

//            model.setValueAt(mId.getText(), tblSifre.getSelectedRow(), 0);
//            model.setValueAt(mSifra.getText(), tblSifre.getSelectedRow(), 1);
//            try {
//                model.setValueAt(mDatumCenovnika.getSQLDate(), tblSifre.getSelectedRow(), 2);
//            } catch (ParseException ex) {
//                Logger.getLogger(Pakovanja.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//            model.setValueAt(mNabavnaCena.getValueDFP().BigDecimalValue(), tblSifre.getSelectedRow(), 3);
//            model.setValueAt(mPlanskaCena.getValueDFP().BigDecimalValue(), tblSifre.getSelectedRow(), 4);
//            model.setValueAt(mProdajnaCena.getValueDFP().BigDecimalValue(), tblSifre.getSelectedRow(), 5);
//            model.setValueAt(mProdajnaCenaSaPDV.getValueDFP().BigDecimalValue(), tblSifre.getSelectedRow(), 6);
//            model.setValueAt(mMaloprodajnaCenaBezPDV.getValueDFP().BigDecimalValue(), tblSifre.getSelectedRow(), 7);
//            model.setValueAt(mMaloprodajnaCena, tblSifre.getSelectedRow(), 8);
//            model.setValueAt(mAktivan.isSelected(), tblSifre.getSelectedRow(), 9);
            try {

                conn.setAutoCommit(false); //transaction block start                  
                id_false(conn, tabela, mId.getText());

                try {
                    String SQLinsertPakovanja = " insert into "
                            + " artikli_pakovanja ("
                            + " artikal_id, artikal_sifra, "
                            + " tippak, ean, \n"
                            + "                naziv, jm, netojm, \n"
                            + " brutojm, \n"
                            + " sirina, visina, duzina, komada,  aktivan, \n"
                            + "                sifra_ambalaze , tezina) \n"
                            + "values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                    PreparedStatement stmt
                            = conn.prepareStatement(SQLinsertPakovanja, Statement.RETURN_GENERATED_KEYS);

                    stmt.setInt(1, vrati_id_za_sifru(conn, "artikli", mSifra.getText()));
                    stmt.setString(2, mSifra.getText());
                    stmt.setInt(3, vrati_id_za_naziv(conn, "vrste_pakovanja_artikala", mVrstaPakovanjaArtikala.getSelectedItem().toString()));
                    stmt.setString(4, mEAN.getText());
                    stmt.setString(5, "");
                    stmt.setString(6, "");
                    stmt.setDouble(7, mNetoUJm.getValueDFP().doubleValue());
                    stmt.setDouble(8, mBrutoUJm.getValueDFP().doubleValue());
                    stmt.setDouble(9, mSirina.getValueDFP().doubleValue());
                    stmt.setDouble(10, mVisina.getValueDFP().doubleValue());
                    stmt.setDouble(11, mDuzina.getValueDFP().doubleValue());
                    stmt.setDouble(12, mKomada.getValueDFP().doubleValue());
                    stmt.setInt(13, 1);
                    stmt.setInt(14, vrati_id_za_naziv(conn, "ambalaza", mAmbalaza.getSelectedItem().toString()));
                    stmt.setDouble(15, mTezina.getValueDFP().doubleValue());

                    if (uzmiParametar(conn, "debugiranje").equals("1")) {
                        System.out.println(stmt);
                    }
                    int i = stmt.executeUpdate();

                    iMessage.setText("Podatak je uspesno dodat ...");
                    MyLogger.logger.info("Podatak je uspesno dodat " + mSifra.getText());

                } catch (Exception e) {

                    iMessage.setText(e.toString());
                    MyLogger.logger.error(e.toString());
                }

                conn.commit(); //transaction block end

                iMessage.setText("Podatak je izmenjen.");
                MyLogger.logger.info("Podatak je izmenjen.");

            } catch (SQLException ex) {
                MyLogger.logger.error("Cenovnik " + ex);
                iMessage.setText(ex.toString());
            }
        }
        PripremaTabelePakovanja(mSifra.getText());
        nuliranjeForme();
    }//GEN-LAST:event_IzmenaBtnActionPerformed

    private void tblSifreMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSifreMouseClicked

        int index = tblSifre.getSelectedRow();
        iMessage.setText("");

        TableModel model = tblSifre.getModel();

        String id = model.getValueAt(index, 0).toString();

        try {

            String sqlQuery = "SELECT artikli_pakovanja.id, artikli_pakovanja.tippak, \n"
                    + "artikli_pakovanja.artikal_sifra, \n"
                    + "artikli_pakovanja.ean,  artikli_pakovanja.netojm, artikli_pakovanja.brutojm, \n"
                    + "artikli_pakovanja.sirina,  artikli_pakovanja.visina, artikli_pakovanja.duzina, \n"
                    + "artikli_pakovanja.komada,  artikli_pakovanja.tezina, artikli_pakovanja.sifra_ambalaze, "
                    + "vrste_pakovanja_artikala.naziv, ambalaza.naziv, artikli_pakovanja.aktivan, \n"
                    + " artikli.naziv as an"
                    + " FROM artikli_pakovanja \n"
                    + "LEFT JOIN vrste_pakovanja_artikala ON vrste_pakovanja_artikala.id = artikli_pakovanja.tippak AND vrste_pakovanja_artikala.aktivan \n"
                    + "LEFT JOIN ambalaza ON ambalaza.id = artikli_pakovanja.sifra_ambalaze AND ambalaza.aktivan \n"
                    + "LEFT JOIN artikli  ON artikli.sifra = artikli_pakovanja.artikal_sifra AND artikli.aktivan \n"
                    + "WHERE \n"
                    + "artikli_pakovanja.id = " + id + " AND artikli_pakovanja.aktivan=1 " // `id`,
                    + "order by artikli_pakovanja.tippak "; // desc

            if (uzmiParametar(conn, "debugiranje").equals("1")) {
                System.out.println(sqlQuery);
            }

            PreparedStatement stmt = conn.prepareStatement(sqlQuery);
            ResultSet rs = stmt.executeQuery(sqlQuery);

            while (rs.next()) {

                ArtikliPakovanja ap = new ArtikliPakovanja();
                ap.setId(rs.getInt("id"));
                ap.setTippak(rs.getString("tippak"));
                ap.setEan(rs.getString("ean"));
                ap.setArtikalSifra(rs.getString("artikal_sifra"));
                ap.setNaziv(rs.getString("naziv"));
//                ap.setJm(rs.getString("jm"));
                ap.setNetojm(rs.getBigDecimal("netojm"));
                ap.setBrutojm(rs.getBigDecimal("brutojm"));
                ap.setSirina(rs.getBigDecimal("sirina"));
                ap.setVisina(rs.getBigDecimal("visina"));
                ap.setDuzina(rs.getBigDecimal("duzina"));
                ap.setKomada(rs.getBigDecimal("komada"));
                ap.setSifraAmbalaze(rs.getString("sifra_ambalaze"));
                ap.setAktivan(rs.getBoolean("aktivan"));

                mId.setText(rs.getString("id"));
                mSifra.setText(rs.getString("artikal_sifra"));
                mNaziv.setText(rs.getString("an"));
                mVrstaPakovanjaArtikala
                        .setSelectedItem(rs.getString("vrste_pakovanja_artikala.naziv"));
                mEAN.setText(rs.getString("ean"));
                mAmbalaza.setSelectedItem(rs.getString("ambalaza.naziv"));
                mSirina.setValue(new AsoftDFP(rs.getDouble("sirina")));
                mDuzina.setValue(new AsoftDFP(rs.getDouble("duzina")));
                mVisina.setValue(new AsoftDFP(rs.getDouble("visina")));
                mKomada.setValue(new AsoftDFP(rs.getDouble("komada")));
                mNetoUJm.setValue(new AsoftDFP(rs.getDouble("netojm")));
                mBrutoUJm.setValue(new AsoftDFP(rs.getDouble("brutojm")));
                mTezina.setValue(new AsoftDFP(rs.getDouble("tezina")));
                pakovanjaArray.add(ap);
            }

        } catch (SQLException ex) {
            MyLogger.logger.info(ex.toString());
            iMessage.setText(ex.toString());
        }


    }//GEN-LAST:event_tblSifreMouseClicked
    public ArrayList<model.Cenovnik> ListSifre(String ValToSearch) {
        ArrayList<model.Cenovnik> usersList = new ArrayList<model.Cenovnik>();
        Statement st;
        ResultSet rs;
        try {
            // Connection con = getConnection();
            st = conn.createStatement();
            String searchQuery = "SELECT * FROM cenovnik WHERE "
                    + "CONCAT(`id`, `sifra`, `datum`) LIKE '%" + ValToSearch + "%'";
            rs = st.executeQuery(searchQuery);
            model.Cenovnik cenovnik;
            while (rs.next()) {
                cenovnik = new model.Cenovnik(rs.getInt("id")); //, rs.getString("sifra"), rs.getDate("datum")
                usersList.add(cenovnik);
            }
        } catch (Exception ex) {
            iMessage.setText(ex.toString());
            ml.logger.error(ex);
        }
        return usersList;
    }

    private void BrisanjeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BrisanjeBtnActionPerformed
        iMessage.setText("");
        DefaultTableModel model = (DefaultTableModel) tblSifre.getModel();
        if (tblSifre.getSelectedRow() == -1) {
            if (tblSifre.getRowCount() == 0) {
                iMessage.setText("Tabela je prazna");
            } else {
                iMessage.setText("Morate izabrati rekord");
            }
        } else {

            Statement stmt = null;
            try {
                stmt = conn.createStatement();

                String sqlQuery = null;

                sqlQuery = " UPDATE " + tabela
                        + " SET aktivan=0 WHERE id = '" + mId.getText()
                        + "'";
                int choice = JOptionPane.showOptionDialog(this,
                        "Da li ste sigurni da želite obrisati rekord?",
                        "Brisanje?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null, null, null);
                if (choice == JOptionPane.YES_OPTION) {
                    stmt.execute(sqlQuery);
                    model.removeRow(tblSifre.getSelectedRow());
                    iMessage.setText("Rekord je obrisan.");
                }

            } catch (SQLException ex) {
                iMessage.setText(ex.toString());
                ml.logger.error(ex);
            }
        }
        PripremaTabelePakovanja(mSifra.getText());
        nuliranjeForme();
        filterTxt.setText("");
    }//GEN-LAST:event_BrisanjeBtnActionPerformed

    private void RefreshBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshBtnActionPerformed
        filterTxt.setText("");
        iMessage.setText("");

        PripremaTabelePakovanja(mSifra.getText());
        nuliranjeForme();

    }//GEN-LAST:event_RefreshBtnActionPerformed

    private void mAktivanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mAktivanActionPerformed
        System.out.println(mAktivan.isSelected());
        if (mAktivan.isSelected()) {
            mAktivan.setSelected(true);
        } else {
            mAktivan.setSelected(false);
        }
    }//GEN-LAST:event_mAktivanActionPerformed

    private void filterTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_filterTxtKeyReleased
        // TODO add your handling code here:
//        String query = filterTxt.getText();
//        filter(query);
//        findUsers();
        iMessage.setText("");

//        punjenjeJtableIPrikazModelaSaUslovom(filterTxt.getText());
        PripremaTabelePakovanja(filterTxt.getText());
        nuliranjeForme();
    }//GEN-LAST:event_filterTxtKeyReleased

    private void PregledBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PregledBtnActionPerformed
        // TODO add your handling code here:
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                try {
                    String mIzvestaj = "opstina.jrxml";
                    Map parameters = new HashMap();
                    parameters.put("id", tabela);
                    if (tabela.equals("opstina")) {
                        mIzvestaj = "opstina.jrxml";
                    } else if (tabela.equals("drzave")) {
                        mIzvestaj = "drzave.jrxml";
                    }
                    String reportPath = service.uzmiParametar.uzmiParametar(conn, "putanja_izvestaja").concat(mIzvestaj);
//                    String reportPath = "C:\\Users\\ms\\Documents\\NetBeansProjects\\gui\\src\\report\\karton1004.jrxml";
                    JasperReport jr = JasperCompileManager.compileReport(reportPath);
                    JasperPrint jp = JasperFillManager.fillReport(jr, parameters, conn);
                    JasperViewer.viewReport(jp, false);
                } catch (JRException ex) {
                    java.util.logging.Logger.getLogger(Pakovanja.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        t1.start();
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

    private void IzlazBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IzlazBtnActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_IzlazBtnActionPerformed

    private void mSifraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mSifraKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

            try {
                Statement stmt = conn.createStatement();

                String sqlQuery = " select * from artikli where aktivan and sifra = '"
                        + mSifra.getText() + "'";

                ResultSet rs = stmt.executeQuery(sqlQuery);

                while (rs.next()) {
                    mNaziv.setText(rs.getString("naziv"));
                }
            } catch (SQLException ex) {
                iMessage.setText(ex.toString());
                MyLogger.logger.error(ex);
                mSifra.grabFocus();
                mSifra.requestFocus();
            }

            mVrstaPakovanjaArtikala.requestFocus();
//            mVrstaPakovanjaArtikala.setFocus();
            mVrstaPakovanjaArtikala.grabFocus();
            mNaziv.repaint();

        }
    }//GEN-LAST:event_mSifraKeyPressed

    private void mNetoUJmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mNetoUJmActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mNetoUJmActionPerformed

    private void mBrutoUJmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mBrutoUJmActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mBrutoUJmActionPerformed

    private void mKomadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mKomadaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mKomadaActionPerformed

    private void mSirinaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mSirinaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mSirinaActionPerformed

    private void mVrstaPakovanjaArtikalaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mVrstaPakovanjaArtikalaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mVrstaPakovanjaArtikalaActionPerformed

    private void mSifraCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_mSifraCaretUpdate
        // TODO add your handling code here:
        try {
            Statement stmt = conn.createStatement();

            String sqlQuery = " select * from artikli where aktivan and sifra = '"
                    + mSifra.getText() + "'";

            ResultSet rs = stmt.executeQuery(sqlQuery);

            while (rs.next()) {
                mNaziv.setText(rs.getString("naziv"));

            }
        } catch (SQLException ex) {
            iMessage.setText(ex.toString());
            MyLogger.logger.error(ex);
            mSifra.grabFocus();
            mSifra.requestFocus();
        }
    }//GEN-LAST:event_mSifraCaretUpdate

    private void filter(String query) {
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>((DefaultTableModel) tblSifre.getModel());
        tblSifre.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(query));
    }

    private void PripremaTabelePakovanja(String id) {

        pakovanjaArray.clear();

        try {

            String sqlQuery = "SELECT artikli_pakovanja.id, artikli_pakovanja.tippak, \n"
                    + "artikli_pakovanja.artikal_sifra, \n"
                    + "artikli_pakovanja.artikal_id, \n"
                    + "artikli_pakovanja.ean,  artikli_pakovanja.netojm, artikli_pakovanja.brutojm, \n"
                    + "artikli_pakovanja.sirina,  artikli_pakovanja.visina, artikli_pakovanja.duzina, \n"
                    + "artikli_pakovanja.komada,  artikli_pakovanja.tezina, artikli_pakovanja.sifra_ambalaze, "
                    + "vrste_pakovanja_artikala.naziv, ambalaza.naziv, artikli_pakovanja.aktivan \n"
                    + " FROM artikli_pakovanja \n"
                    + "LEFT JOIN vrste_pakovanja_artikala ON vrste_pakovanja_artikala.sifra = artikli_pakovanja.tippak \n"
                    + "LEFT JOIN ambalaza ON ambalaza.sifra = artikli_pakovanja.sifra_ambalaze \n"
                    + "WHERE \n"
                    + "artikli_pakovanja.artikal_sifra LIKE '%" + id + "%' AND artikli_pakovanja.aktivan=1 " // `id`,
                    + "order by artikli_pakovanja.tippak "; // desc

            if (uzmiParametar(conn, "debugiranje").equals("1")) {
                System.out.println(sqlQuery);
            }

            PreparedStatement stmt = conn.prepareStatement(sqlQuery);
            ResultSet rs = stmt.executeQuery(sqlQuery);

            while (rs.next()) {

                ArtikliPakovanja ap = new ArtikliPakovanja();
                ap.setId(rs.getInt("id"));
                ap.setArtikalSifra(rs.getString("artikal_sifra"));
                ap.setTippak(rs.getString("tippak"));
                ap.setEan(rs.getString("ean"));

//                ap.setNaziv(rs.getString("naziv"));
//                ap.setJm(rs.getString("jm"));
                ap.setNetojm(rs.getBigDecimal("netojm"));
                ap.setBrutojm(rs.getBigDecimal("brutojm"));
                ap.setSirina(rs.getBigDecimal("sirina"));
                ap.setDuzina(rs.getBigDecimal("duzina"));
                ap.setVisina(rs.getBigDecimal("visina"));

                ap.setKomada(rs.getBigDecimal("komada"));
                ap.setSifraAmbalaze(rs.getString("sifra_ambalaze"));
                ap.setAktivan(rs.getBoolean("aktivan"));

                mVrstaPakovanjaArtikala.setSelectedItem(rs.getString("vrste_pakovanja_artikala.naziv"));
                mEAN.setText(rs.getString("ean"));
                mAmbalaza.setSelectedItem(rs.getString("ambalaza.naziv"));
                mSirina.setValue(new AsoftDFP(rs.getDouble("sirina")));
                mDuzina.setValue(new AsoftDFP(rs.getDouble("duzina")));
                mVisina.setValue(new AsoftDFP(rs.getDouble("visina")));
                mKomada.setValue(new AsoftDFP(rs.getDouble("komada")));
                mNetoUJm.setValue(new AsoftDFP(rs.getDouble("netojm")));
                mBrutoUJm.setValue(new AsoftDFP(rs.getDouble("brutojm")));
                mTezina.setValue(new AsoftDFP(rs.getDouble("tezina")));
                pakovanjaArray.add(ap);
            }

        } catch (SQLException ex) {
            MyLogger.logger.info(ex.toString());
            iMessage.setText(ex.toString());
        }

        DefaultTableModel model = new DefaultTableModel();

//        DefaultTableModel model = (DefaultTableModel) jTableCene.getModel();
        TableColumnModel tcm = tblSifre.getColumnModel();

        tcm.getColumn(0).setPreferredWidth(20);     //Name
        tcm.getColumn(1).setPreferredWidth(120);    //Title
        tcm.getColumn(2).setPreferredWidth(250);    //Surname
        tcm.getColumn(3).setPreferredWidth(50);    //ID
        tcm.getColumn(4).setPreferredWidth(50);    //ID
        tcm.getColumn(5).setPreferredWidth(50);    //ID
        tcm.getColumn(6).setPreferredWidth(50);    //ID
        tcm.getColumn(7).setPreferredWidth(50);    //ID
        tcm.getColumn(8).setPreferredWidth(50);    //ID
        tcm.getColumn(9).setPreferredWidth(50);    //ID
        tcm.getColumn(10).setPreferredWidth(50);    //ID
        tcm.getColumn(11).setPreferredWidth(50);    //ID
        tcm.getColumn(12).setPreferredWidth(50);    //ID
        //         
        model.setColumnIdentifiers(new Object[]{"Id", "Sifra", "Vrsta pakovanja",
            "Ean", "Netojm",
            "Brutojm", "Sirina", "Duzina", "Visina", "Komada",
            "Ambalaza", "Aktivan", "Rbr"});
        rekordaUslektu = 0;
        Object[] row = new Object[13];
        for (int i = 0; i < pakovanjaArray.size(); i++) {
            row[0] = pakovanjaArray.get(i).getId();
            row[1] = pakovanjaArray.get(i).getArtikalSifra();
            row[2] = pakovanjaArray.get(i).getTippak();
            row[3] = pakovanjaArray.get(i).getEan();
            row[4] = pakovanjaArray.get(i).getNetojm();
            row[5] = pakovanjaArray.get(i).getBrutojm();
            row[6] = pakovanjaArray.get(i).getSirina();
            row[7] = pakovanjaArray.get(i).getDuzina();
            row[8] = pakovanjaArray.get(i).getVisina();
            row[9] = pakovanjaArray.get(i).getKomada();
            row[10] = pakovanjaArray.get(i).getSifraAmbalaze();
            row[11] = pakovanjaArray.get(i).getAktivan();
            row[12] = rekordaUslektu;
            model.addRow(row);
            rekordaUslektu++;
        }

        tblSifre.setModel(model);

        tblSifre.getColumnModel().getColumn(0).setCellRenderer(new CustomRenderer(1));
        tblSifre.getColumnModel().getColumn(1).setCellRenderer(new CustomRenderer(1));
        tblSifre.getColumnModel().getColumn(2).setCellRenderer(new CustomRenderer(1));
        tblSifre.getColumnModel().getColumn(3).setCellRenderer(new CustomRenderer(1));
        tblSifre.getColumnModel().getColumn(4).setCellRenderer(new CustomRenderer(1));
        tblSifre.getColumnModel().getColumn(5).setCellRenderer(new CustomRenderer(1));
        tblSifre.getColumnModel().getColumn(6).setCellRenderer(new CustomRenderer(1));
        tblSifre.getColumnModel().getColumn(7).setCellRenderer(new CustomRenderer(1));
        tblSifre.getColumnModel().getColumn(8).setCellRenderer(new CustomRenderer(1));
        tblSifre.getColumnModel().getColumn(9).setCellRenderer(new CustomRenderer(1));
        tblSifre.getColumnModel().getColumn(10).setCellRenderer(new CustomRenderer(1));
        tblSifre.getColumnModel().getColumn(11).setCellRenderer(new CustomRenderer(1));
        tblSifre.getColumnModel().getColumn(12).setCellRenderer(new CustomRenderer(1));
    }

//    private void PripremaTabelePakovanja2(String sifra) {
//
//        cenovnikArray.clear();
//
//        try {
//
//            String sqlQuery = " select * from artikli_pakovanja where artikal_sifra like  '%"
//                    + sifra + "%'" + " and aktivan order by datum desc";
//            PreparedStatement stmt = conn.prepareStatement(sqlQuery);
//            ResultSet rs = stmt.executeQuery(sqlQuery);
//
//            if (uzmiParametar(conn, "debugiranje").equals("1")) {
//                System.out.println(sqlQuery);
//            }
//
//            while (rs.next()) {
//
//                model.ArtikliPakovanja pak = new model.ArtikliPakovanja();
//                pak.setId(rs.getInt("id"));
//                pak.setArtikalId(rs.getInt("artikal_id"));
//                pak.setSifra(rs.getString("sifra"));
//                pak.setArtikalSifra(rs.getString("artikal_sifra"));
//                pak.setDatum(rs.getDate("datum"));
//                pak.setNabavnaCena(rs.getBigDecimal("nabavna_cena"));
//                pak.setPlanskaCena(rs.getBigDecimal("planska_cena"));
//                pak.setProdajnaCena(rs.getBigDecimal("prodajna_cena"));
//                pak.setProdajnaCenaSaPdv(rs.getBigDecimal("prodajna_cena_sa_pdv"));
//                pak.setMaloprodajnaCenaBezPdv(rs.getBigDecimal("maloprodajna_cena_bez_pdv"));
//                pak.setMaloprodajnaCena(rs.getBigDecimal("maloprodajna_cena"));
//                pak.setAktivan(rs.getBoolean("aktivan"));
//
//                mNabavnaCena.setValue(new AsoftDFP(rs.getDouble("nabavna_cena")));
//                mPlanskaCena.setValue(new AsoftDFP(rs.getDouble("planska_cena")));
//                mProdajnaCena.setValue(new AsoftDFP(rs.getDouble("prodajna_cena")));
//                mProdajnaCenaSaPDV.setValue(new AsoftDFP(rs.getDouble("prodajna_cena_sa_pdv")));
//                mMaloprodajnaCena.setValue(new AsoftDFP(rs.getDouble("maloprodajna_cena")));
//                mMaloprodajnaCenaBezPDV.setValue(new AsoftDFP(rs.getDouble("maloprodajna_cena_bez_pdv")));
//
//                try {
//                    mDatumCenovnika.setDate(rs.getDate("datum"));
//                } catch (ParseException ex) {
//                    ml.logger.info(ex.toString());
//                    iMessage.setText(ex.toString());
//                }
//
//                cenovnikArray.add(cenovnik);
//            }
//
//        } catch (SQLException ex) {
//            ml.logger.info(ex.toString());
//            iMessage.setText(ex.toString());
//        }
//
////        FillListPodaciOCenama();
//        DefaultTableModel model = new DefaultTableModel();
//
////        DefaultTableModel model = (DefaultTableModel) jTableCene.getModel();
//        TableColumnModel tcm = tblSifre.getColumnModel();
//
//        tcm.getColumn(0).setPreferredWidth(20);     //Name
//        tcm.getColumn(1).setPreferredWidth(120);    //Title
//        tcm.getColumn(2).setPreferredWidth(250);    //Surname
//        tcm.getColumn(3).setPreferredWidth(50);    //ID
//        tcm.getColumn(4).setPreferredWidth(50);    //ID
//        tcm.getColumn(5).setPreferredWidth(50);    //ID
//        tcm.getColumn(6).setPreferredWidth(50);    //ID
//        tcm.getColumn(7).setPreferredWidth(50);    //ID
//        tcm.getColumn(8).setPreferredWidth(50);    //ID
//        tcm.getColumn(9).setPreferredWidth(50);    //ID
//        tcm.getColumn(10).setPreferredWidth(50);    //ID
//
//        //         
//        rekordaUslektu = 0;
//        model.setColumnIdentifiers(new Object[]{"Id", "Sifra", "Datum", "Nabavna", "Planska cena",
//            "Prodajna cena", "Prodajna sa pdv", "Maloprodajna bez pdv", "Maloprodajna", "Aktivan", "Rbr"});
//        Object[] row = new Object[11];
//        for (int i = 0; i < cenovnikArray.size(); i++) {
//            row[0] = cenovnikArray.get(i).getId();
////            row[1] = cenovnikArray.get(i).getSifra();
//            row[1] = cenovnikArray.get(i).getArtikalSifra();
//            row[2] = cenovnikArray.get(i).getDatum();
//            row[3] = cenovnikArray.get(i).getNabavnaCena();
//            row[4] = cenovnikArray.get(i).getPlanskaCena();
//            row[5] = cenovnikArray.get(i).getProdajnaCena();
//            row[6] = cenovnikArray.get(i).getProdajnaCenaSaPdv();
//
//            row[7] = cenovnikArray.get(i).getMaloprodajnaCenaBezPdv();
//            row[8] = cenovnikArray.get(i).getMaloprodajnaCena();
//            row[9] = cenovnikArray.get(i).getAktivan();
//            row[10] = rekordaUslektu;
//            model.addRow(row);
//            rekordaUslektu++;
//        }
//
//        tblSifre.setModel(model);
//
//        tblSifre.getColumnModel().getColumn(0).setCellRenderer(new CustomRenderer(1));
//        tblSifre.getColumnModel().getColumn(1).setCellRenderer(new CustomRenderer(1));
//        tblSifre.getColumnModel().getColumn(2).setCellRenderer(new CustomRenderer(1));
//        tblSifre.getColumnModel().getColumn(3).setCellRenderer(new CustomRenderer(1));
//        tblSifre.getColumnModel().getColumn(4).setCellRenderer(new CustomRenderer(1));
//        tblSifre.getColumnModel().getColumn(5).setCellRenderer(new CustomRenderer(1));
//        tblSifre.getColumnModel().getColumn(6).setCellRenderer(new CustomRenderer(1));
//        tblSifre.getColumnModel().getColumn(7).setCellRenderer(new CustomRenderer(1));
//        tblSifre.getColumnModel().getColumn(8).setCellRenderer(new CustomRenderer(1));
//        tblSifre.getColumnModel().getColumn(9).setCellRenderer(new CustomRenderer(1));
//        tblSifre.getColumnModel().getColumn(10).setCellRenderer(new CustomRenderer(1));
//    }
    private void punjenjeJtableIPrikazModelaSaUslovom(String ValToSearch) {

        DefaultTableModel model = (DefaultTableModel) tblSifre.getModel();
        TableColumnModel tcm = tblSifre.getColumnModel();

        tcm.getColumn(0).setPreferredWidth(10);     //Name
        tcm.getColumn(1).setPreferredWidth(10);    //Title
        tcm.getColumn(2).setPreferredWidth(170);    //Surname
        tcm.getColumn(3).setPreferredWidth(10);    //ID
        tcm.getColumn(4).setPreferredWidth(10);    //ID
        tcm.getColumn(5).setPreferredWidth(10);    //ID
        tcm.getColumn(6).setPreferredWidth(10);    //ID
        tcm.getColumn(7).setPreferredWidth(10);    //ID
        tcm.getColumn(8).setPreferredWidth(10);    //ID
        tcm.getColumn(9).setPreferredWidth(10);    //ID
//        tcm.getColumn(10).setPreferredWidth(10);    //ID        
//        tcm.getColumn(11).setPreferredWidth(10);    //ID        

        tblSifre.getColumnModel().getColumn(0).setCellRenderer(new CustomRenderer(0));
        tblSifre.getColumnModel().getColumn(1).setCellRenderer(new CustomRenderer(0));
        tblSifre.getColumnModel().getColumn(2).setCellRenderer(new CustomRenderer(0));
        tblSifre.getColumnModel().getColumn(3).setCellRenderer(new CustomRenderer(0));
        tblSifre.getColumnModel().getColumn(4).setCellRenderer(new CustomRenderer(0));
        tblSifre.getColumnModel().getColumn(5).setCellRenderer(new CustomRenderer(0));
        tblSifre.getColumnModel().getColumn(6).setCellRenderer(new CustomRenderer(0));
        tblSifre.getColumnModel().getColumn(7).setCellRenderer(new CustomRenderer(0));
        tblSifre.getColumnModel().getColumn(8).setCellRenderer(new CustomRenderer(0));
        tblSifre.getColumnModel().getColumn(9).setCellRenderer(new CustomRenderer(0));
//        tblSifre.getColumnModel().getColumn(10).setCellRenderer(new CustomRenderer(0));
//        tblSifre.getColumnModel().getColumn(11).setCellRenderer(new CustomRenderer(0));

//        tblSifre.removeAll();
        if (model.getRowCount() > 0) {
            for (int i = model.getRowCount() - 1; i > -1; i--) {
                model.removeRow(i);
            }
        }

        try {
            String searchQuery = "SELECT * FROM cenovnik  WHERE aktivan and "
                    + "LOWER(CONCAT(`id`, `sifra`, `datum`, "
                    + "nabavna_cena, planska_cena, "
                    + "prodajna_cena, prodajna_cena_sa_pdv, "
                    + "maloprodajna_cena_bez_pdv, "
                    + "maloprodajna_cena)) LIKE LOWER('%" + ValToSearch + "%') order by id";

            PreparedStatement stmt = conn.prepareStatement(searchQuery);

            ResultSet rs = stmt.executeQuery(); // searchQuery
            rekordaUslektu = 0;
            while (rs.next()) {
                mId.setText(rs.getString(1));
                mSifra.setText(rs.getString(2));
                mNaziv.setText(rs.getString(3));
//                    int cetvrti = rs.getInt(5);
                boolean mmAktivan = (rs.getInt(5) == 1) ? true : false;

                mAktivan.setSelected(mmAktivan);
//                    if (rs.getInt(4))
                model.addRow(new Object[]{mId.getText(), mSifra.getText(), mNaziv.getText(), mmAktivan, rekordaUslektu});
                rekordaUslektu++;
            }
            mId.setText("");
            mSifra.setText("");
            mNaziv.setText("");
            mAktivan.setSelected(true);

        } catch (SQLException ex) {
            MyLogger.logger.error(ex);
            iMessage.setText(ex.toString());
        }

    }

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
            java.util.logging.Logger.getLogger(Pakovanja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pakovanja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pakovanja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pakovanja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Pakovanja().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BrisanjeBtn;
    private javax.swing.JButton FirstBtn;
    private javax.swing.JButton IzlazBtn;
    private javax.swing.JButton IzmenaBtn;
    private javax.swing.JButton LastBtn;
    private javax.swing.JButton NextBtn;
    private javax.swing.JButton PregledBtn;
    private javax.swing.JButton PrevBtn;
    private javax.swing.JButton RefreshBtn;
    private javax.swing.JButton UnosBtn;
    private javax.swing.JTextField filterTxt;
    private javax.swing.JLabel iMessage;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JCheckBox mAktivan;
    private javax.swing.JComboBox<String> mAmbalaza;
    private org.asoft.library.AsoftNumericField mBrutoUJm;
    private org.asoft.library.AsoftNumericField mDuzina;
    private javax.swing.JTextField mEAN;
    private javax.swing.JTextField mId;
    private org.asoft.library.AsoftNumericField mKomada;
    private javax.swing.JTextField mNaziv;
    private org.asoft.library.AsoftNumericField mNetoUJm;
    private javax.swing.JTextField mSifra;
    private org.asoft.library.AsoftNumericField mSirina;
    private org.asoft.library.AsoftNumericField mTezina;
    private org.asoft.library.AsoftNumericField mVisina;
    private javax.swing.JComboBox<String> mVrstaPakovanjaArtikala;
    private javax.swing.JLabel recordLbl;
    private javax.swing.JTable tblSifre;
    // End of variables declaration//GEN-END:variables

    private int vratiJedanIliNula(boolean selected) {
        if (selected) {
            return 1;
        } else {
            return 0;
        }
    }

    private void nuliranjeForme() {

        UnosBtn.setEnabled(true);

        mId.setText("");
        mNaziv.setText("");
        mSifra.setText("");
        mEAN.setText("");

        List<String> listVrstaPakovanja = puniComboMaticni.puni_maticne(conn, "vrste_pakovanja_artikala");
        mVrstaPakovanjaArtikala.setModel(new DefaultComboBoxModel(listVrstaPakovanja.toArray()));

        List<String> listAmbalaza = puniComboMaticni.puni_maticne(conn, "ambalaza");
        mAmbalaza.setModel(new DefaultComboBoxModel(listAmbalaza.toArray()));

        mTezina.setValue(NULA);
        mNetoUJm.setValue(NULA);
        mBrutoUJm.setValue(NULA);
        mKomada.setValue(NULA);
        mSirina.setValue(NULA);
        mDuzina.setValue(NULA);
        mVisina.setValue(NULA);

        mAktivan.setSelected(true);
//        filterTxt.setText("");

    }
    
    private void nuliranjeFormeBezSifre() {

        UnosBtn.setEnabled(true);

        mId.setText("");
        mNaziv.setText("");
//        mSifra.setText("");
        mEAN.setText("");

        List<String> listVrstaPakovanja = puniComboMaticni.puni_maticne(conn, "vrste_pakovanja_artikala");
        mVrstaPakovanjaArtikala.setModel(new DefaultComboBoxModel(listVrstaPakovanja.toArray()));

        List<String> listAmbalaza = puniComboMaticni.puni_maticne(conn, "ambalaza");
        mAmbalaza.setModel(new DefaultComboBoxModel(listAmbalaza.toArray()));

        mTezina.setValue(NULA);
        mNetoUJm.setValue(NULA);
        mBrutoUJm.setValue(NULA);
        mKomada.setValue(NULA);
        mSirina.setValue(NULA);
        mDuzina.setValue(NULA);
        mVisina.setValue(NULA);

        mAktivan.setSelected(true);
//        filterTxt.setText("");

    }    

    private boolean Validation() {

        if (mSifra.getText().equals("") || mSifra.getText().isEmpty()) {
            MyLogger.logger.error("Šifra nesme biti prazna ...");
            iMessage.setText("Šifra nesme biti prazna ...");
            return false;
        }
//        if (mNaziv.getText().equals("") || mNaziv.getText().isEmpty()) {
//            MyLogger.logger.error("Naziv nesme biti prazna ...");
//            iMessage.setText("Naziv nesme biti prazna ...");
//            return false;
//        }

        return true;
    }

    private boolean DaLiPostojiPakovanje(String sifra, Integer vrstaPakovanja, String aktivan) {

        try {
            Statement stmt = conn.createStatement();

            String sqlQuery = " select * from artikli_pakovanja where artikal_sifra = '" + sifra + "' and  " + aktivan + " and tippak = '"
                    + vrstaPakovanja + "'";

            ResultSet rs = stmt.executeQuery(sqlQuery);

            while (rs.next()) {
                mNaziv.setText(rs.getString("naziv"));

            }
        } catch (SQLException ex) {
            iMessage.setText(ex.toString());
            MyLogger.logger.error(ex);
            mSifra.grabFocus();
            mSifra.requestFocus();
        }
        return true;
    }    
    
}
