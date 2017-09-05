package maticni.cenovnik;

import maticni.*;
import forms_pos.Main;
import forms_pos.Prijava;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
//import forme.SearchWithTab;
import java.sql.Connection;
import java.sql.Date;
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
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableCellRenderer;
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
import static service.uzmiParametar.uzmiParametar;
import static service.vrati_id_za_sifru.vrati_id_za_sifru;

/**
 *
 * @author ms
 */
public class Cenovnik extends javax.swing.JFrame {

    ArrayList<model.Cenovnik> cenovnikArray = new ArrayList<>();
    Connection conn;
    String tabela, sifraArtikla = "";
    int rekordaUslektu = 0;
    MyLogger ml = new MyLogger();

    public Cenovnik() {

        initComponents();
        MyLogger.logger.info("Cenovnik");
        this.setTitle(tabela);
        setLocationRelativeTo(null);
        konekcija n = new konekcija();
        conn = n.konekcija();
        tabela = "cenovnik";
        PripremaTabeleCena(sifraArtikla);
        navigacija();
        nuliranjeForme();
    }

    public Cenovnik(final Connection conn, final String tabela, String sifraArtikla) {

        this.conn = conn;
        this.tabela = tabela;
        this.sifraArtikla = sifraArtikla;

        initComponents();
        MyLogger.logger.info("Cenovnik");
        this.setTitle(tabela);

        setLocationRelativeTo(null);
        nuliranjeFormeBezSifre();
//        punjenjeJtableIPrikazModelaSaUslovom(filterTxt.getText());
        PripremaTabeleCena(sifraArtikla);

        if (!Prijava.getKosam()) {
            UnosBtn.setEnabled(false);
            IzmenaBtn.setEnabled(false);
            BrisanjeBtn.setEnabled(false);
            PregledBtn.setEnabled(false);
        }

        NextBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String mmId = mId.getText();
                Integer pos = Integer.valueOf(mmId);

                pos++;

                ShowPosInfo(pos, " order by id asc LIMIT 1");
                DefaultTableModel model = (DefaultTableModel) tblSifre.getModel();
                String id = model.getValueAt(tblSifre.getSelectedRow(), 10).toString();
                int foo = Integer.parseInt(id);
                if (foo < rekordaUslektu - 1) {
                    foo++;
                }
                tblSifre.changeSelection(foo, 0, false, false);
            }

            private void ShowPosInfo(Integer pos, String orderilimit) {

                try {
                    Statement stmt = conn.createStatement();

                    String sqlQuery = " select * from " + tabela + " where id >= "
                            + pos.toString() + orderilimit;
                    ResultSet rs = stmt.executeQuery(sqlQuery);

                    while (rs.next()) {
                        mId.setText(rs.getString(1));
                        mSifra.setText(rs.getString(2));
//                        mNaziv.setText(rs.getString(3));

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

        PrevBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String mmId = mId.getText();
                Integer pos = Integer.valueOf(mmId);
                Integer pozicija;

                pos--;
                pozicija = ShowPosInfo(pos, " order by id desc LIMIT 1");
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

                try {
                    Statement stmt = conn.createStatement();

                    String sqlQuery = " select * from " + tabela + " where id <= "
                            + pos.toString() + orderilimit;

                    ResultSet rs = stmt.executeQuery(sqlQuery);

                    while (rs.next()) {
                        vracam = rs.getInt(1);
                        mId.setText(rs.getString(1));
                        mSifra.setText(rs.getString(2));

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

        FirstBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String mmId = mId.getText();
                Integer pos = Integer.valueOf(mmId);
                if (pos < ListSifre("").size()) {
                    ShowPosInfo(pos, " order by id desc ");
                } else {
                    pos = ListSifre("").size() - 1;
                    ShowPosInfo(pos, " order by id desc ");
                }

                tblSifre.changeSelection(0, 1, false, false);

            }

            private void ShowPosInfo(Integer pos, String orderilimit) {

                try {
                    Statement stmt = conn.createStatement();

                    String sqlQuery = " select * from " + tabela + " where id < "
                            + pos.toString() + orderilimit;

                    ResultSet rs = stmt.executeQuery(sqlQuery);

                    while (rs.next()) {
                        mId.setText(rs.getString(1));
                        mSifra.setText(rs.getString(2));
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

                    String sqlQuery = " select * from " + tabela + " where id > "
                            + pos.toString() + orderilimit;

//                    System.out.println(sqlQuery);
                    ResultSet rs = stmt.executeQuery(sqlQuery);

                    while (rs.next()) {
                        mId.setText(rs.getString(1));
                        mSifra.setText(rs.getString(2));
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
        mDatumCenovnika = new org.asoft.library.picker.AsoftHistoryDatePicker();
        jPanel15 = new javax.swing.JPanel();
        mNabavnaCena = new org.asoft.library.AsoftNumericField();
        mPlanskaCena = new org.asoft.library.AsoftNumericField();
        mProdajnaCena = new org.asoft.library.AsoftNumericField();
        jLabel12 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        mProdajnaCenaSaPDV = new org.asoft.library.AsoftNumericField();
        mMaloprodajnaCenaBezPDV = new org.asoft.library.AsoftNumericField();
        mMaloprodajnaCena = new org.asoft.library.AsoftNumericField();
        jLabel27 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        mAktivan = new javax.swing.JCheckBox();
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

        BrisanjeBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/delete-file-icon-32.png"))); // NOI18N
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
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(UnosBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(IzmenaBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(BrisanjeBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                        .addGap(12, 12, 12)
                        .addComponent(PregledBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(FirstBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(PrevBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(recordLbl, javax.swing.GroupLayout.DEFAULT_SIZE, 15, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(NextBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LastBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(RefreshBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(IzlazBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE))
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

        mNaziv.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        mNaziv.setEnabled(false);

        jLabel2.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel2.setText("Šifra:");

        jLabel1.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel1.setText("Id:");

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel15.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        mNabavnaCena.setText("0.00");
        mNabavnaCena.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N

        mPlanskaCena.setText("0.00");
        mPlanskaCena.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N

        mProdajnaCena.setText("0.00");
        mProdajnaCena.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel12.setText("Nabavna cena:");

        jLabel25.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel25.setText("Planska cena:");

        jLabel26.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel26.setText("Prodajna cena:");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(0, 24, Short.MAX_VALUE)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(18, 18, 18)
                        .addComponent(mNabavnaCena, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel25)
                            .addComponent(jLabel26))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(mProdajnaCena, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(mPlanskaCena, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mNabavnaCena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mPlanskaCena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mProdajnaCena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26))
                .addContainerGap())
        );

        jPanel14.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        mProdajnaCenaSaPDV.setText("0.00");
        mProdajnaCenaSaPDV.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N

        mMaloprodajnaCenaBezPDV.setText("0.00");
        mMaloprodajnaCenaBezPDV.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N

        mMaloprodajnaCena.setText("0.00");
        mMaloprodajnaCena.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        mMaloprodajnaCena.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mMaloprodajnaCenaActionPerformed(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel27.setText("Prodajna cena sa PDV:");

        jLabel15.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel15.setText("Maloprodajna cena bez PDV:");

        jLabel28.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel28.setText("Maloprodajna cena:");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addGap(18, 18, 18)
                        .addComponent(mProdajnaCenaSaPDV, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(18, 18, 18)
                        .addComponent(mMaloprodajnaCenaBezPDV, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel28)
                        .addGap(18, 18, 18)
                        .addComponent(mMaloprodajnaCena, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(mProdajnaCenaSaPDV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mMaloprodajnaCenaBezPDV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mMaloprodajnaCena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28))
                .addContainerGap())
        );

        jLabel4.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel4.setText("Aktivan:");

        mAktivan.setSelected(true);
        mAktivan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mAktivanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(mDatumCenovnika, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(mAktivan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(34, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(mDatumCenovnika, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(mAktivan)
                            .addComponent(jLabel4)))))
        );

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
                        .addComponent(mNaziv, javax.swing.GroupLayout.PREFERRED_SIZE, 466, javax.swing.GroupLayout.PREFERRED_SIZE)
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

            },
            new String [] {
                "Id", "Sifra", "Datum", "Nabavna", "Planska", "Prodajna", "Prodajna sa PDV", "Maloprodajna bez PDV", "Maloprodajna", "Aktivan", "Rbr"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
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
        if (!Validation()) {
            return;
        }

        try {
            if (!DaLiPostojiCenovnikSaDatumom(mSifra.getText(), mDatumCenovnika.getSQLDate())) {
                return;
            }
        } catch (ParseException ex) {
            Logger.getLogger(Cenovnik.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            PreparedStatement sqlInsert = conn.prepareStatement(
                    "insert into cenovnik ( artikal_id, "
                    + "artikal_sifra,"
                    + "sifra, "
                    + "datum, nabavna_cena, planska_cena, "
                    + " prodajna_cena, maloprodajna_cena_bez_pdv, "
                    + " maloprodajna_cena, prodajna_cena_sa_pdv, "
                    + " aktivan) values(?,?,?,?,?,?,?,?,?,?,?)");
            int i = vrati_id_za_sifru(conn, "artikli", mSifra.getText());

            sqlInsert.setInt(1, vrati_id_za_sifru(conn, "artikli", mSifra.getText()));
            sqlInsert.setString(2, mSifra.getText());
            sqlInsert.setString(3, mSifra.getText());
            sqlInsert.setDate(4, mDatumCenovnika.getSQLDate());
            sqlInsert.setBigDecimal(5, mNabavnaCena.getValueDFP().BigDecimalValue());
            sqlInsert.setBigDecimal(6, mPlanskaCena.getValueDFP().BigDecimalValue());
            sqlInsert.setBigDecimal(7, mProdajnaCena.getValueDFP().BigDecimalValue());
            sqlInsert.setBigDecimal(8, mMaloprodajnaCenaBezPDV.getValueDFP().BigDecimalValue());
            sqlInsert.setBigDecimal(9, mMaloprodajnaCena.getValueDFP().BigDecimalValue());
            sqlInsert.setBigDecimal(10, mProdajnaCenaSaPDV.getValueDFP().BigDecimalValue());
            sqlInsert.setInt(11, vratiJedanIliNula(mAktivan.isSelected()));

            sqlInsert.executeUpdate();

            iMessage.setText("Podatak je uspesno dodat ...");
            MyLogger.logger.info("Podatak je uspesno dodat " + mSifra.getText());

            if (uzmiParametar(conn, "debugiranje").equals("1")) {
                System.out.println(sqlInsert);
            }

        } catch (Exception e) {

            iMessage.setText(e.toString());
            MyLogger.logger.error(e.toString());
        }

        PripremaTabeleCena(mSifra.getText());
        nuliranjeFormeBezSifre();
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

            model.setValueAt(mId.getText(), tblSifre.getSelectedRow(), 0);
            model.setValueAt(mSifra.getText(), tblSifre.getSelectedRow(), 1);
            try {
                model.setValueAt(mDatumCenovnika.getSQLDate(), tblSifre.getSelectedRow(), 2);
            } catch (ParseException ex) {
                Logger.getLogger(Cenovnik.class.getName()).log(Level.SEVERE, null, ex);
            }

            model.setValueAt(mNabavnaCena.getValueDFP().BigDecimalValue(), tblSifre.getSelectedRow(), 3);
            model.setValueAt(mPlanskaCena.getValueDFP().BigDecimalValue(), tblSifre.getSelectedRow(), 4);
            model.setValueAt(mProdajnaCena.getValueDFP().BigDecimalValue(), tblSifre.getSelectedRow(), 5);
            model.setValueAt(mProdajnaCenaSaPDV.getValueDFP().BigDecimalValue(), tblSifre.getSelectedRow(), 6);
            model.setValueAt(mMaloprodajnaCenaBezPDV.getValueDFP().BigDecimalValue(), tblSifre.getSelectedRow(), 7);
            model.setValueAt(mMaloprodajnaCena, tblSifre.getSelectedRow(), 8);
            model.setValueAt(mAktivan.isSelected(), tblSifre.getSelectedRow(), 9);

            try {

                conn.setAutoCommit(false); //transaction block start                  
                id_false(conn, tabela, mId.getText());

//                if (!OperacijeSaSifrom(conn, tabela, mSifra.getText())) {
//                    throw new SQLException("postoji dupla sifra koja je aktivna");
//                }
                try {
                    PreparedStatement sqlInsert = conn.prepareStatement(
                            "insert into cenovnik ( artikal_id, "
                            + "artikal_sifra,"
                            + "sifra, "
                            + "datum, nabavna_cena, planska_cena, "
                            + " prodajna_cena, maloprodajna_cena_bez_pdv, "
                            + " maloprodajna_cena, prodajna_cena_sa_pdv, "
                            + " aktivan) values(?,?,?,?,?,?,?,?,?,?,?)");

                    sqlInsert.setInt(1, vrati_id_za_sifru(conn, "artikli", mSifra.getText()));
                    sqlInsert.setString(2, mSifra.getText());
                    sqlInsert.setString(3, mSifra.getText());
                    sqlInsert.setDate(4, mDatumCenovnika.getSQLDate());
                    sqlInsert.setBigDecimal(5, mNabavnaCena.getValueDFP().BigDecimalValue());
                    sqlInsert.setBigDecimal(6, mPlanskaCena.getValueDFP().BigDecimalValue());
                    sqlInsert.setBigDecimal(7, mProdajnaCena.getValueDFP().BigDecimalValue());
                    sqlInsert.setBigDecimal(8, mMaloprodajnaCenaBezPDV.getValueDFP().BigDecimalValue());
                    sqlInsert.setBigDecimal(9, mMaloprodajnaCena.getValueDFP().BigDecimalValue());
                    sqlInsert.setBigDecimal(10, mProdajnaCenaSaPDV.getValueDFP().BigDecimalValue());
                    sqlInsert.setInt(11, vratiJedanIliNula(mAktivan.isSelected()));

                    sqlInsert.executeUpdate();

                    iMessage.setText("Podatak je uspesno dodat ...");
                    MyLogger.logger.info("Podatak je uspesno dodat " + mSifra.getText());

                    if (uzmiParametar(conn, "debugiranje").equals("1")) {
                        System.out.println(sqlInsert);
                    }

                } catch (Exception e) {

                    iMessage.setText(e.toString());
                    MyLogger.logger.error(e.toString());
                }

                conn.commit(); //transaction block end

                iMessage.setText("Podatak je izmenjen.");
                MyLogger.logger.info("Podatak je izmenjen.");

            } catch (SQLException ex) {
                MyLogger.logger.error("Cenovnik " + ex.toString());
                iMessage.setText(ex.toString());
            }
        }
        PripremaTabeleCena(mSifra.getText());
        nuliranjeForme();
    }//GEN-LAST:event_IzmenaBtnActionPerformed

    private void tblSifreMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSifreMouseClicked
        int index = tblSifre.getSelectedRow();
        iMessage.setText("");
        TableModel model = tblSifre.getModel();

        String id = model.getValueAt(index, 0).toString();

        try {
            Statement stmt = conn.createStatement();
            String sqlQuery = " select * from " + tabela + " where id = '" + id
                    + "'";
            ResultSet rs = stmt.executeQuery(sqlQuery);

            while (rs.next()) {
                mId.setText(rs.getString("id"));
                mSifra.setText(rs.getString("artikal_sifra"));
//                mNaziv.setText(rs.getString("naziv"));
                int mmAktivan = rs.getInt("aktivan");
                if (mmAktivan == 1) {
                    mAktivan.setSelected(true);
                } else {
                    mAktivan.setSelected(false);
                }

                mDatumCenovnika.setDate(rs.getDate("datum"));
                mNabavnaCena.setValue(new AsoftDFP(rs.getDouble("nabavna_cena")));
                mPlanskaCena.setValue(new AsoftDFP(rs.getDouble("planska_cena")));
                mProdajnaCena.setValue(new AsoftDFP(rs.getDouble("prodajna_cena")));
                mProdajnaCenaSaPDV.setValue(new AsoftDFP(rs.getDouble("prodajna_cena_sa_pdv")));
                mMaloprodajnaCena.setValue(new AsoftDFP(rs.getDouble("maloprodajna_cena")));
                mMaloprodajnaCenaBezPDV.setValue(new AsoftDFP(rs.getDouble("maloprodajna_cena_bez_pdv")));

            }
        } catch (SQLException | ParseException ex) {
            iMessage.setText(ex.toString());
            MyLogger.logger.error(ex.toString());

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
        PripremaTabeleCena(mSifra.getText());
        nuliranjeForme();
        filterTxt.setText("");
    }//GEN-LAST:event_BrisanjeBtnActionPerformed

    private void RefreshBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshBtnActionPerformed
        filterTxt.setText("");
        iMessage.setText("");

        PripremaTabeleCena(mSifra.getText());
        nuliranjeForme();

    }//GEN-LAST:event_RefreshBtnActionPerformed

    private void mAktivanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mAktivanActionPerformed
//        System.out.println(mAktivan.isSelected());
        if (mAktivan.isSelected()) {
            mAktivan.setSelected(true);
        } else {
            mAktivan.setSelected(false);
        }
    }//GEN-LAST:event_mAktivanActionPerformed

    private void filterTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_filterTxtKeyReleased

        iMessage.setText("");
        PripremaTabeleCena(filterTxt.getText());
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
                    java.util.logging.Logger.getLogger(Cenovnik.class.getName()).log(Level.SEVERE, null, ex);
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
        mNaziv.setText("");
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
                MyLogger.logger.error(ex.toString());
//                mSifra.grabFocus();
                mSifra.requestFocus();
            }

////            mDatumCenovnika.requestFocus();
            mDatumCenovnika.setFocus();
////            mDatumCenovnika.grabFocus();
//
        }

    }//GEN-LAST:event_mSifraKeyPressed

    private void mMaloprodajnaCenaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mMaloprodajnaCenaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mMaloprodajnaCenaActionPerformed

    private void mSifraCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_mSifraCaretUpdate
        // TODO add your handling code here:
        mNaziv.setText("");
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
            MyLogger.logger.error(ex.toString());
//            mSifra.grabFocus();
            mSifra.requestFocus();
        }
    }//GEN-LAST:event_mSifraCaretUpdate

    private void filter(String query) {
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>((DefaultTableModel) tblSifre.getModel());
        tblSifre.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(query));
    }

    private void navigacija() {

        NextBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String mmId = mId.getText();
                Integer pos = Integer.valueOf(mmId);

                pos++;

                ShowPosInfo(pos, " order by id asc LIMIT 1");
                DefaultTableModel model = (DefaultTableModel) tblSifre.getModel();
                String id = model.getValueAt(tblSifre.getSelectedRow(), 10).toString();
                int foo = Integer.parseInt(id);
                if (foo < rekordaUslektu - 1) {
                    foo++;
                }
                tblSifre.changeSelection(foo, 0, false, false);
            }

            private void ShowPosInfo(Integer pos, String orderilimit) {

                try {
                    Statement stmt = conn.createStatement();

                    String sqlQuery = " select * from " + tabela + " where id >= "
                            + pos.toString() + orderilimit;
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
                    MyLogger.logger.info(ex.toString());
                    iMessage.setText(ex.toString());
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
                String id = model.getValueAt(tblSifre.getSelectedRow(), 10).toString();
                int foo = Integer.parseInt(id);
                if (foo > 0) {
                    foo--;
                }
                tblSifre.changeSelection(foo, 0, false, false);

            }

            private Integer ShowPosInfo(Integer pos, String orderilimit) {

                Integer vracam = 0;

                try {
                    Statement stmt = conn.createStatement();

                    String sqlQuery = " select * from " + tabela + " where id <= "
                            + pos.toString() + orderilimit;

                    ResultSet rs = stmt.executeQuery(sqlQuery);

                    while (rs.next()) {
                        vracam = rs.getInt(1);
                        mId.setText(rs.getString(1));
                        mSifra.setText(rs.getString(2));

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
                if (pos < ListSifre("").size()) {
                    ShowPosInfo(pos, " order by id desc ");
                } else {
                    pos = ListSifre("").size() - 1;
                    ShowPosInfo(pos, " order by id desc ");
                }

                tblSifre.changeSelection(0, 1, false, false);

            }

            private void ShowPosInfo(Integer pos, String orderilimit) {

                try {
                    Statement stmt = conn.createStatement();

                    String sqlQuery = " select * from " + tabela + " where id < "
                            + pos.toString() + orderilimit;

                    ResultSet rs = stmt.executeQuery(sqlQuery);

                    while (rs.next()) {
                        mId.setText(rs.getString(1));
                        mSifra.setText(rs.getString(2));
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

                    String sqlQuery = " select * from " + tabela + " where id > "
                            + pos.toString() + orderilimit;

//                    System.out.println(sqlQuery);
                    ResultSet rs = stmt.executeQuery(sqlQuery);

                    while (rs.next()) {
                        mId.setText(rs.getString(1));
                        mSifra.setText(rs.getString(2));
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

    }

    private void PripremaTabeleCena(String sifra) {

        cenovnikArray.clear();

        mSifra.setText(sifra);
        filterTxt.setText(sifra);

        try {

            String sqlQuery = " select * from cenovnik where artikal_sifra =  '"
                    + sifra + "'" + " and aktivan order by datum desc";

            PreparedStatement stmt = conn.prepareStatement(sqlQuery);
            ResultSet rs = stmt.executeQuery(sqlQuery);

            if (uzmiParametar(conn, "debugiranje").equals("1")) {
                System.out.println(sqlQuery);
            }

            while (rs.next()) {

                model.Cenovnik cenovnik = new model.Cenovnik();
                cenovnik.setId(rs.getInt("id"));
                cenovnik.setArtikalId(rs.getInt("artikal_id"));
                cenovnik.setSifra(rs.getString("sifra"));
                cenovnik.setArtikalSifra(rs.getString("artikal_sifra"));
                cenovnik.setDatum(rs.getDate("datum"));
                cenovnik.setNabavnaCena(rs.getBigDecimal("nabavna_cena"));
                cenovnik.setPlanskaCena(rs.getBigDecimal("planska_cena"));
                cenovnik.setProdajnaCena(rs.getBigDecimal("prodajna_cena"));
                cenovnik.setProdajnaCenaSaPdv(rs.getBigDecimal("prodajna_cena_sa_pdv"));
                cenovnik.setMaloprodajnaCenaBezPdv(rs.getBigDecimal("maloprodajna_cena_bez_pdv"));
                cenovnik.setMaloprodajnaCena(rs.getBigDecimal("maloprodajna_cena"));
                cenovnik.setAktivan(rs.getBoolean("aktivan"));

                mNabavnaCena.setValue(new AsoftDFP(rs.getDouble("nabavna_cena")));
                mPlanskaCena.setValue(new AsoftDFP(rs.getDouble("planska_cena")));
                mProdajnaCena.setValue(new AsoftDFP(rs.getDouble("prodajna_cena")));
                mProdajnaCenaSaPDV.setValue(new AsoftDFP(rs.getDouble("prodajna_cena_sa_pdv")));
                mMaloprodajnaCena.setValue(new AsoftDFP(rs.getDouble("maloprodajna_cena")));
                mMaloprodajnaCenaBezPDV.setValue(new AsoftDFP(rs.getDouble("maloprodajna_cena_bez_pdv")));

                try {
                    mDatumCenovnika.setDate(rs.getDate("datum"));
                } catch (ParseException ex) {
                    ml.logger.info(ex.toString());
                    iMessage.setText(ex.toString());
                }

                cenovnikArray.add(cenovnik);
            }

        } catch (SQLException ex) {
            MyLogger.logger.info(ex.toString());
            iMessage.setText(ex.toString());
        }

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"Id", "Sifra", "Datum", "Nabavna", "Planska cena",
            "Prodajna cena", "Prodajna sa pdv", "Maloprodajna bez pdv", "Maloprodajna", "Aktivan", "Rbr"});
        TableColumnModel tcm = tblSifre.getColumnModel();

        tcm.getColumn(0).setPreferredWidth(20);     //Name
        tcm.getColumn(1).setPreferredWidth(120);    //Title
        tcm.getColumn(2).setPreferredWidth(350);    //Surname
        tcm.getColumn(3).setPreferredWidth(50);    //ID
        tcm.getColumn(4).setPreferredWidth(50);    //ID
        tcm.getColumn(5).setPreferredWidth(50);    //ID
        tcm.getColumn(6).setPreferredWidth(50);    //ID
        tcm.getColumn(7).setPreferredWidth(50);    //ID
        tcm.getColumn(8).setPreferredWidth(50);    //ID
        tcm.getColumn(9).setPreferredWidth(50);    //ID
        tcm.getColumn(10).setPreferredWidth(50);    //ID

        //         
        rekordaUslektu = 0;

        Object[] row = new Object[11];
        for (int i = 0; i < cenovnikArray.size(); i++) {
            row[0] = cenovnikArray.get(i).getId();
//            row[1] = cenovnikArray.get(i).getSifra();
            row[1] = cenovnikArray.get(i).getArtikalSifra();
            row[2] = cenovnikArray.get(i).getDatum();
            row[3] = cenovnikArray.get(i).getNabavnaCena();
            row[4] = cenovnikArray.get(i).getPlanskaCena();
            row[5] = cenovnikArray.get(i).getProdajnaCena();
            row[6] = cenovnikArray.get(i).getProdajnaCenaSaPdv();

            row[7] = cenovnikArray.get(i).getMaloprodajnaCenaBezPdv();
            row[8] = cenovnikArray.get(i).getMaloprodajnaCena();
            row[9] = cenovnikArray.get(i).getAktivan();
            row[10] = rekordaUslektu;
            model.addRow(row);
            rekordaUslektu++;
        }

        tblSifre.setModel(model);

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(DefaultTableCellRenderer.RIGHT);
//        tblSifre.setAlignmentY(rightRenderer);
        tblSifre.getColumnModel().getColumn(0).setCellRenderer(new CustomRenderer(1));
        tblSifre.getColumnModel().getColumn(1).setCellRenderer(new CustomRenderer(1));
        tblSifre.getColumnModel().getColumn(2).setCellRenderer(new CustomRenderer(1));
        // tblSifre.getColumnModel().getColumn(2).setWidth(150);
        tblSifre.getColumnModel().getColumn(2).setPreferredWidth(100);
//        tblSifre.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
        tblSifre.getColumnModel().getColumn(3).setCellRenderer(new CustomRendererRight(1));
//        tblSifre.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);      
        tblSifre.getColumnModel().getColumn(4).setCellRenderer(new CustomRendererRight(1));

        tblSifre.getColumnModel().getColumn(5).setCellRenderer(new CustomRendererRight(1));
//        tblSifre.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);        
        tblSifre.getColumnModel().getColumn(6).setCellRenderer(new CustomRendererRight(1));
//        tblSifre.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);        
        tblSifre.getColumnModel().getColumn(7).setCellRenderer(new CustomRendererRight(1));
//        tblSifre.getColumnModel().getColumn(7).setCellRenderer(rightRenderer);        
        tblSifre.getColumnModel().getColumn(8).setCellRenderer(new CustomRendererRight(1));
//        tblSifre.getColumnModel().getColumn(8).setCellRenderer(rightRenderer);        
        tblSifre.getColumnModel().getColumn(9).setCellRenderer(new CustomRenderer(1));
//        tblSifre.getColumnModel().getColumn(9).setCellRenderer(rightRenderer);        
        tblSifre.getColumnModel().getColumn(10).setCellRenderer(new CustomRenderer(1));
//        tblSifre.getColumnModel().getColumn(10).setCellRenderer(rightRenderer);        
//        tblSifre.repaint();
    }

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
            MyLogger.logger.error(ex.toString());
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
            java.util.logging.Logger.getLogger(Cenovnik.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Cenovnik.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Cenovnik.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Cenovnik.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
                new Cenovnik().setVisible(true);
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
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JCheckBox mAktivan;
    private org.asoft.library.picker.AsoftHistoryDatePicker mDatumCenovnika;
    private javax.swing.JTextField mId;
    private org.asoft.library.AsoftNumericField mMaloprodajnaCena;
    private org.asoft.library.AsoftNumericField mMaloprodajnaCenaBezPDV;
    private org.asoft.library.AsoftNumericField mNabavnaCena;
    private javax.swing.JTextField mNaziv;
    private org.asoft.library.AsoftNumericField mPlanskaCena;
    private org.asoft.library.AsoftNumericField mProdajnaCena;
    private org.asoft.library.AsoftNumericField mProdajnaCenaSaPDV;
    private javax.swing.JTextField mSifra;
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

//        if (akcija == "UNOS") {
//            IzmenaBtn.setEnabled(false);
//        }
//        UnosBtn.setEnabled(false);
//        if (akcija == "IZMENA") {
//            IzmenaBtn.setEnabled(true);
//        }
        UnosBtn.setEnabled(true);

        mDatumCenovnika.initPicker("mDatumCenovnika", null);

        try {
            mDatumCenovnika.setDate(AsoftDate.EMPTY_DATE);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, ex);
            Main.track.error(" ParseException " + ex);
        }

        mNaziv.setText("");
        mSifra.setText("");
        mNabavnaCena.setValue(NULA);
        mPlanskaCena.setValue(NULA);
        mProdajnaCena.setValue(NULA);
        mProdajnaCenaSaPDV.setValue(NULA);
        mMaloprodajnaCenaBezPDV.setValue(NULA);
        mMaloprodajnaCena.setValue(NULA);
        mAktivan.setSelected(true);
//        filterTxt.setText("");

    }

    private void nuliranjeFormeBezSifre() {

//        if (akcija == "UNOS") {
//            IzmenaBtn.setEnabled(false);
//        }
//        UnosBtn.setEnabled(false);
//        if (akcija == "IZMENA") {
//            IzmenaBtn.setEnabled(true);
//        }
        UnosBtn.setEnabled(true);

        mDatumCenovnika.initPicker("mDatumCenovnika", null);

        try {
            mDatumCenovnika.setDate(AsoftDate.EMPTY_DATE);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, ex);
            Main.track.error(" ParseException " + ex);
        }

        mNaziv.setText("");
//        mSifra.setText("");
        mNabavnaCena.setValue(NULA);
        mPlanskaCena.setValue(NULA);
        mProdajnaCena.setValue(NULA);
        mProdajnaCenaSaPDV.setValue(NULA);
        mMaloprodajnaCenaBezPDV.setValue(NULA);
        mMaloprodajnaCena.setValue(NULA);
        mAktivan.setSelected(true);
//        filterTxt.setText("");

    }

    private boolean Validation() {

        if (mSifra.getText().equals("") || mSifra.getText().isEmpty()) {
            MyLogger.logger.error("Šifra ne sme biti prazna ...");
            iMessage.setText("Šifra ne sme biti prazna ...");
            return false;
        }
        if (mNaziv.getText().equals("") || mNaziv.getText().isEmpty()) {
            MyLogger.logger.error("Naziv ne sme biti prazan ...");
            iMessage.setText("Naziv ne sme biti prazan ...");
            return false;
        }

        return true;
    }

    private boolean DaLiPostojiCenovnikSaDatumom(String sifra, Date sqlDate) {
        try {
            Statement stmt = conn.createStatement();

            String sqlQuery = " select * from cenovnik where artikal_sifra = '" + sifra + "' and  " + " datum = '"
                    + sqlDate + "'";

            ResultSet rs = stmt.executeQuery(sqlQuery);

            while (rs.next()) {
//                mNaziv.setText(rs.getString("artikal_sifra"));
                MyLogger.logger.error("Postoji cenovnik sa traženim datumom, moguca je samo izmena ...");
                iMessage.setText("Postoji cenovnik sa traženim datumom, moguca je samo izmena ...");
                return false;
            }
            return true;
        } catch (SQLException ex) {
            iMessage.setText(ex.toString());
            MyLogger.logger.error(ex);
            mSifra.grabFocus();
            mSifra.requestFocus();
        }
        return true;
    }

}
