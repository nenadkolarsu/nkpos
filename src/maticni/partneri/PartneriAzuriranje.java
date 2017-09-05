package maticni.partneri;

import forms_pos.Main;
import forms_pos.Prijava;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import konekcija.konekcija;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import maticni.CustomRenderer;
import model.ArtikalBarkod;
import model.ArtikalCene;
import model.ArtikliPakovanja;
import model.Akcije;
import model.PartneriRacuniModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import static org.asoft.library.AsoftDFP.NULA;
import service.MyLogger;
import static service.OperacijeSaSifrom.vratiMaxSifru;
import static service.id_false.id_false;
import service.puniComboMaticni;
import static service.uzmiParametar.uzmiParametar;

/**
 *
 * @author ms
 */
public class PartneriAzuriranje extends javax.swing.JFrame {

    /**
     * Creates new form MaticniPodaci
     */
    Connection conn;
    String tabela = "partneri", par, akcija;
    int rekordaUslektu = 0;
    MyLogger ml = new MyLogger();
    String mmSifra = "0";
    InputStream mphoto = null;
    ArrayList<PartneriRacuniModel> PartneriRacuniModelArray = new ArrayList<>();
    ArrayList<ArtikliPakovanja> pakovanjaArray = new ArrayList<ArtikliPakovanja>();
    ArrayList<Akcije> akcijeArray = new ArrayList<Akcije>();
    private TrazenjePartnera tp;

    public PartneriAzuriranje() {

        initComponents();
        MyLogger ml = new MyLogger();
        setLocationRelativeTo(null);
        konekcija n = new konekcija();
        conn = n.konekcija();
        tabela = "partneri";
        this.setTitle(tabela);

//        punjenjeJtable();
    }

    public PartneriAzuriranje(final Connection conn, final String akcija, String id, TrazenjePartnera tp) {

        this.conn = conn;
        this.akcija = akcija;
        this.par = id;
        this.tp = tp;

        initComponents();
        this.setTitle(tabela);
        this.setTitle("Ažuriranje partnera");

//         MyLogger ml = new MyLogger();
        MyLogger.logger.info("Ažuriranje partnera");

        nuliranjeForme0();
        mSifra.setEditable(false);

        if (akcija.equalsIgnoreCase("IZMENA")) {
            UnosBtn.setEnabled(false);

            if (!Prijava.getKosam()) {
                UnosBtn.setEnabled(false);
                IzmenaBtn.setEnabled(false);
            }

            napuniEkran(id);
        }

        if (akcija.equalsIgnoreCase("UNOS")) {
            IzmenaBtn.setEnabled(false);
            if (!Prijava.getKosam()) {
                UnosBtn.setEnabled(false);
                IzmenaBtn.setEnabled(false);
            }
            try {
                //            nuliranjeForme();
//            if (mId.getText().equals("") || mId.getText().isEmpty()) {
//                mmSifra="0";
//            }

                mSifra.setText(vratiMaxSifru(conn, tabela).toString());
            } catch (SQLException ex) {
                Logger.getLogger(PartneriAzuriranje.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        PripremaTabeleBankeRacuni(mSifra.getText());
        mNaziv.grabFocus();

        setLocationRelativeTo(null);

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
                    Main.track.error(tabela + " SQLException " + ex);
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

                    Main.track.error(tabela + " SQLException " + ex);
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

                if (pos < ListArtikalCene("").size()) {
                    ShowPosInfo(pos, " order by id desc ");
                } else {
                    pos = ListArtikalCene("").size() - 1;
                    ShowPosInfo(pos, " order by id desc ");
                }
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
                    Main.track.error(tabela + " SQLException " + ex);
                }
            }
        }
        );

        LastBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String mmId = mId.getText();
                Integer pos = Integer.valueOf(mmId);

                if (pos < ListArtikalCene("").size()) {
                    ShowPosInfo(pos, " order by id asc ");
                } else {
                    pos = ListArtikalCene("").size() - 1;
                    ShowPosInfo(pos, " order by id asc ");
                }
            }

            private void ShowPosInfo(Integer pos, String orderilimit) {

                recordLbl.setText(pos.toString());

                try {
                    Statement stmt = conn.createStatement();

                    String sqlQuery = " select * from " + tabela + " where id > "
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
                    MyLogger.logger.error(ex.toString());
                    iMessage.setText(ex.toString());
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

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        UnosBtn = new javax.swing.JButton();
        IzmenaBtn = new javax.swing.JButton();
        BrisanjeBtn = new javax.swing.JButton();
        RefreshBtn = new javax.swing.JButton();
        PregledBtn = new javax.swing.JButton();
        FirstBtn = new javax.swing.JButton();
        PrevBtn = new javax.swing.JButton();
        recordLbl = new javax.swing.JLabel();
        NextBtn = new javax.swing.JButton();
        LastBtn = new javax.swing.JButton();
        IzlazBtn = new javax.swing.JButton();
        saveBtn = new javax.swing.JButton();
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
        iMessage = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        mWeb = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        mNapomena = new javax.swing.JTextArea();
        jLabel12 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        mPDV = new javax.swing.JTextField();
        mJmbg = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        mPttBroj = new javax.swing.JComboBox();
        mDrzava = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        mKategorija = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        mKalo = new org.asoft.library.AsoftNumericField();
        mAdresa = new javax.swing.JTextField();
        mFax = new javax.swing.JTextField();
        mEmail = new javax.swing.JTextField();
        mTelefon = new javax.swing.JTextField();
        mMaticniBroj = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        mPib = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableBankeRacuni = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        BrisanjeBtnRacun = new javax.swing.JButton();
        UnosBtnRacun = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        UnosBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/Files-New-File-icon-32.png"))); // NOI18N
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

        RefreshBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/Button-Refresh-icon-32.png"))); // NOI18N
        RefreshBtn.setText("Osveži");
        RefreshBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefreshBtnActionPerformed(evt);
            }
        });

        PregledBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/print-icon-32.png"))); // NOI18N
        PregledBtn.setText("Pregled");
        PregledBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PregledBtnActionPerformed(evt);
            }
        });

        FirstBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_stare/c.png"))); // NOI18N
        FirstBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FirstBtnActionPerformed(evt);
            }
        });

        PrevBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_stare/go-previous.png"))); // NOI18N
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

        NextBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_stare/go-next.png"))); // NOI18N
        NextBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NextBtnActionPerformed(evt);
            }
        });

        LastBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_stare/d.png"))); // NOI18N
        LastBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LastBtnActionPerformed(evt);
            }
        });

        IzlazBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/Button-Close-icon-32.png"))); // NOI18N
        IzlazBtn.setText("Izlaz");
        IzlazBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IzlazBtnActionPerformed(evt);
            }
        });

        saveBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/Save-icon-32.png"))); // NOI18N
        saveBtn.setText("Sačuvaj");
        saveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(UnosBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(saveBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(IzmenaBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(BrisanjeBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(PregledBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(FirstBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PrevBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(recordLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(NextBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LastBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(RefreshBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(IzlazBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {FirstBtn, LastBtn, NextBtn, PrevBtn});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(UnosBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, Short.MAX_VALUE)
                .addComponent(saveBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(IzmenaBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(FirstBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(PrevBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(LastBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(RefreshBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(IzlazBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                .addComponent(recordLbl, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(NextBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(BrisanjeBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(PregledBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {BrisanjeBtn, IzlazBtn, IzmenaBtn, PregledBtn, RefreshBtn, UnosBtn});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {FirstBtn, LastBtn, NextBtn, PrevBtn});

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lTabela.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        lTabela.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/smartPOS5.jpg"))); // NOI18N

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
        mNaziv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mNazivActionPerformed(evt);
            }
        });
        mNaziv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                mNazivKeyReleased(evt);
            }
        });

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
                .addGap(32, 32, 32)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(mSifra, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(551, 551, 551)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(mAktivan))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(mId, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(mNaziv, javax.swing.GroupLayout.PREFERRED_SIZE, 586, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lTabela, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(0, 13, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(mId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(mNaziv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(mSifra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(mAktivan)
                                    .addComponent(jLabel4)))))
                    .addComponent(lTabela, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        iMessage.setBackground(new java.awt.Color(153, 204, 255));
        iMessage.setForeground(new java.awt.Color(255, 0, 0));
        iMessage.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTabbedPane1.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N

        jPanel13.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        mWeb.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        mWeb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mWebActionPerformed(evt);
            }
        });
        mWeb.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                mWebKeyReleased(evt);
            }
        });

        mNapomena.setColumns(20);
        mNapomena.setRows(5);
        mNapomena.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                mNapomenaKeyReleased(evt);
            }
        });
        jScrollPane5.setViewportView(mNapomena);

        jLabel12.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel12.setText("Web:");

        jLabel24.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel24.setText("PDV:");

        mPDV.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        mPDV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mPDVActionPerformed(evt);
            }
        });
        mPDV.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                mPDVKeyReleased(evt);
            }
        });

        mJmbg.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        mJmbg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mJmbgActionPerformed(evt);
            }
        });
        mJmbg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                mJmbgKeyReleased(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel25.setText("Jmbg:");

        jLabel26.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel26.setText("Napomena:");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel24)
                            .addComponent(jLabel12)
                            .addComponent(jLabel25)))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(36, 36, 36)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mJmbg, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mWeb, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mPDV, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(43, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mWeb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(mPDV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mJmbg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel16.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel6.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel6.setText("Adresa:");

        jLabel7.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel7.setText("Ptt broj:");

        mPttBroj.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        mPttBroj.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        mPttBroj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mPttBrojActionPerformed(evt);
            }
        });

        mDrzava.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        mDrzava.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        mDrzava.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mDrzavaActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel8.setText("Drzava:");

        jLabel9.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel9.setText("Kategorija:");

        mKategorija.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        mKategorija.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        mKategorija.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mKategorijaActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel10.setText("Telefon:");

        jLabel22.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel22.setText("Fax:");

        jLabel23.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel23.setText("Email:");

        jLabel21.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel21.setText("Procenat popusta%:");

        mKalo.setText("0.00");
        mKalo.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N

        mAdresa.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        mAdresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mAdresaActionPerformed(evt);
            }
        });
        mAdresa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                mAdresaKeyReleased(evt);
            }
        });

        mFax.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        mFax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mFaxActionPerformed(evt);
            }
        });
        mFax.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                mFaxKeyReleased(evt);
            }
        });

        mEmail.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        mEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mEmailActionPerformed(evt);
            }
        });
        mEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                mEmailKeyReleased(evt);
            }
        });

        mTelefon.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        mTelefon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mTelefonActionPerformed(evt);
            }
        });
        mTelefon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                mTelefonKeyReleased(evt);
            }
        });

        mMaticniBroj.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        mMaticniBroj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mMaticniBrojActionPerformed(evt);
            }
        });
        mMaticniBroj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                mMaticniBrojKeyReleased(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel5.setText("MBR:");

        mPib.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        mPib.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mPibActionPerformed(evt);
            }
        });
        mPib.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                mPibKeyReleased(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel20.setText("PIB:");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel16Layout.createSequentialGroup()
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10)
                            .addComponent(jLabel22)
                            .addComponent(jLabel23))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(mAdresa)
                            .addComponent(mFax, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(mEmail)
                            .addComponent(mTelefon)))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                        .addComponent(mKalo, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel20))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(mPib, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(mPttBroj, javax.swing.GroupLayout.Alignment.TRAILING, 0, 239, Short.MAX_VALUE)
                            .addComponent(mDrzava, javax.swing.GroupLayout.Alignment.TRAILING, 0, 239, Short.MAX_VALUE)
                            .addComponent(mKategorija, javax.swing.GroupLayout.Alignment.TRAILING, 0, 239, Short.MAX_VALUE)
                            .addComponent(mMaticniBroj, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addGap(0, 25, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(mAdresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(mPttBroj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(mDrzava, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(mKategorija, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(mTelefon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(mFax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(mEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mMaticniBroj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mPib, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mKalo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(135, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(44, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Partneri", jPanel5);

        jTableBankeRacuni.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Sifra", "Naziv", "Tekuci racun", "Aktivan"
            }
        ));
        jScrollPane3.setViewportView(jTableBankeRacuni);

        jPanel10.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        BrisanjeBtnRacun.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/delete-file-icon.png"))); // NOI18N
        BrisanjeBtnRacun.setText("Brisanje");
        BrisanjeBtnRacun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BrisanjeBtnRacunActionPerformed(evt);
            }
        });

        UnosBtnRacun.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/Files-New-File-icon-32.png"))); // NOI18N
        UnosBtnRacun.setText("Unos");
        UnosBtnRacun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UnosBtnRacunActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(UnosBtnRacun, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(BrisanjeBtnRacun, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BrisanjeBtnRacun)
                    .addComponent(UnosBtnRacun))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1121, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Računi", jPanel6);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1145, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 390, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Ostali podaci", jPanel8);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1145, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 390, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Dodatne informacije", jPanel9);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(iMessage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(jTabbedPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(iMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mSifraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mSifraActionPerformed
        // TODO add your handling code here:
        mNaziv.requestFocus();
    }//GEN-LAST:event_mSifraActionPerformed

    private void UnosBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UnosBtnActionPerformed

        insertuj_rekord(false);
        UnosBtn.setEnabled(false);
        UnosBtn.repaint();

    }//GEN-LAST:event_UnosBtnActionPerformed

    private void IzmenaBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IzmenaBtnActionPerformed

        insertuj_rekord(true);

//        iMessage.setText("");
//
//        Statement stmt = null;
//        mphoto = null;
//
//        try {
//            stmt = conn.createStatement();
//
//            String sqlQuery = null;
//
//            mphoto = null;
//            try {
//                if (!mSlika.getText().isEmpty()) {
//                    mphoto = new FileInputStream(new File(mSlika.getText()));
//                }
//            } catch (FileNotFoundException ex) {
//                ml.logger.error(ex.toString());
//                iMessage.setText(ex.toString());
//            }
//
//            sqlQuery = " UPDATE artikli SET sifra='" + mSifra.getText()
//                    + "', naziv='" + mNaziv.getText()
//                    + "', sifra2='" + mSifra2.getText()
//                    + "', plu='" + mPLU.getText()
//                    + "', aktivan='" + vratiJedanIliNula(mAktivan.isSelected())
//                    + "', artikal_tip_id='" + uzmiIdZaNaziv("tip_artikla", mTip.getSelectedItem().toString())
//                    + "', jedinica_mere_id='" + uzmiIdZaNaziv("jedinica_mere", mJedinicaMere.getSelectedItem().toString())
//                    + "', poreska_grupa_id='" + uzmiIdZaNaziv("poreska_tarifa", mPoreskaGrupa.getSelectedItem().toString())
//                    + "', odeljenje_id='" + uzmiIdZaNaziv("odeljenje", mOdeljenje.getSelectedItem().toString())
//                    + "', artikal_kategorija_id='" + uzmiIdZaNaziv("klasifikacija_artikala", mKlasifikacija.getSelectedItem().toString())
//                    + "', carinska_tarifa_artikala_id='" + uzmiIdZaNaziv("carinska_tarifa_artikala", mCarinskaTarifa.getSelectedItem().toString())
//                    + "', partneri_id='" + uzmiIdZaNaziv("partneri", mProizvodjac.getSelectedItem().toString())
//                    + "', sklop='" + vratiJedanIliNula(jCheckBoxSklop.isSelected())
//                    + "', rok_trajanja='" + mRokTrajanja.getValueDFP().doubleValue()
//                    + "', kalo='" + mKalo.getValueDFP().doubleValue()
//                    + "', slika='" + mSlika.getText()
//                    + "', photo='" + mphoto
//                    + "' WHERE id = '" + mId.getText()
//                    + "'";
//
//            System.out.println(sqlQuery);
//
//            stmt.execute(sqlQuery);
//            iMessage.setText("Podatak je izmenjen.");
//            ml.logger.info("Podatak je izmenjen.");
//
//        } catch (SQLException ex) {
//            ml.logger.error(tabela + " SQLException " + ex.toString());
//            iMessage.setText(ex.toString());
//        }
//
//        PreparedStatement preparedStatement = null;
//
//        String updateTableSQL = "UPDATE artikli SET photo = ? "
//                + " WHERE id = ?";
//
//        try {
////			conn = getDBConnection();
//            preparedStatement = conn.prepareStatement(updateTableSQL);
//
//            preparedStatement.setBlob(1, mphoto);
//
//            // execute update SQL stetement
//            preparedStatement.executeUpdate();
//
//            System.out.println("Record is updated to DBUSER table!");
//
//        } catch (SQLException e) {
//
//            System.out.println(e.getMessage());
//
//        } finally {
//
//        }

    }//GEN-LAST:event_IzmenaBtnActionPerformed

    public void findUsers() {

//        ArrayList<model.MaticniPodaci> users = ListSifre(filterTxt.getText());
        // PRAZNI MODEL
//        if (model.getRowCount() > 0) {
//            for (int i = model.getRowCount() - 1; i > -1; i--) {
//                model.removeRow(i);
//            }
//        }
//        Object[] row = new Object[4];
//        for (int i = 0; i < users.size(); i++) {
//            row[0] = users.get(i).getId();
//            row[1] = users.get(i).getSifra();
//            row[2] = users.get(i).getNaziv();
//            row[3] = users.get(i).getAktivan();
//            model.addRow(row);
//        }
//        tblSifre.setModel(model);
    }
    private void BrisanjeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BrisanjeBtnActionPerformed

        iMessage.setText("");

//        DefaultTableModel model = (DefaultTableModel) tblSifre.getModel();
//        if (tblSifre.getSelectedRow() == -1) {
//            if (tblSifre.getRowCount() == 0) {
//                iMessage.setText("Tabela je prazna");
//            } else {
//                iMessage.setText("Morate izabrati rekord");
//            }
//        } else {
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
                    "Da li ste sigurni da želite obrisati rekord?",
                    "Brisanje?",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null, null, null);

            // interpret the user's choice
            if (choice == JOptionPane.YES_OPTION) {
                stmt.execute(sqlQuery);
//                    model.removeRow(tblSifre.getSelectedRow());
                iMessage.setText("Rekord je obrisan.");

            }

        } catch (SQLException ex) {
            MyLogger.logger.error("Partneri " + ex.toString());
            iMessage.setText(ex.toString());
        }
//        }
                tp.puniTabelu();
    }//GEN-LAST:event_BrisanjeBtnActionPerformed

    private void RefreshBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshBtnActionPerformed
//        filterTxt.setText("");
        iMessage.setText("");
//        punjenjeJtableIPrikazModelaSaUslovom(filterTxt.getText());
    }//GEN-LAST:event_RefreshBtnActionPerformed

    private void mAktivanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mAktivanActionPerformed
        System.out.println(mAktivan.isSelected());
        if (mAktivan.isSelected()) {
            mAktivan.setSelected(true);
        } else {
            mAktivan.setSelected(false);
        }
    }//GEN-LAST:event_mAktivanActionPerformed

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
                    MyLogger.logger.error("Artikli " + ex.toString());
                    iMessage.setText(ex.toString());
                    java.util.logging.Logger.getLogger(PartneriAzuriranje.class.getName()).log(Level.SEVERE, null, ex);
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
//        JDialog.setDefaultLookAndFeelDecorated(true);
//        Object stringArray[] = {"Da", "Ne"};
//
//        Icon blueIcon = new ImageIcon("yourFile.gif");
//        int response = JOptionPane.showOptionDialog(null, "Ukoliko ste pravili izmene, podatke treba snimiti ... \n Da li želite izlaz?", "Potvrda",
//                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, blueIcon, stringArray,
//                stringArray[0]);
//        if (response == JOptionPane.NO_OPTION) {
//            return;
//        } else if (response == JOptionPane.YES_OPTION) {
//        } else if (response == JOptionPane.CLOSED_OPTION) {
//            return;
//        }

        upisiZaKraj();
        this.dispose();

    }//GEN-LAST:event_IzlazBtnActionPerformed

    private void mMaticniBrojActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mMaticniBrojActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mMaticniBrojActionPerformed

    private void mPibActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mPibActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mPibActionPerformed

    private void mNazivActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mNazivActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_mNazivActionPerformed

    private void mWebActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mWebActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mWebActionPerformed

    private void mPttBrojActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mPttBrojActionPerformed
        // TODO add your handling code here:
//        mDrzava.requestFocus();
    }//GEN-LAST:event_mPttBrojActionPerformed

    private void BrisanjeBtnRacunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BrisanjeBtnRacunActionPerformed
        // TODO add your handling code here:

        iMessage.setText("");
        int index = 0;
        DefaultTableModel model = (DefaultTableModel) jTableBankeRacuni.getModel();
        if (jTableBankeRacuni.getSelectedRow() == -1) {
            if (jTableBankeRacuni.getRowCount() == 0) {
                iMessage.setText("Tabela je prazna");
            } else {
                iMessage.setText("Morate izabrati rekord");
                return;
            }
        } else {

            index = jTableBankeRacuni.getSelectedRow();

        }

//        TableModel model = jTableBankeRacuni.getModel();
        String mIzabranaSifra = model.getValueAt(index, 0).toString();

        //  model.setValueAt(mSifra.getText(),tblSifre.getSelectedRow(), 1);
        //  model.setValueAt(mNaziv.getText(),tblSifre.getSelectedRow(), 2);   
        Statement stmt = null;
        try {
            stmt = conn.createStatement();

            String sqlQuery = null;

//            sqlQuery = " DELETE FROM " + tabela
//                    + " WHERE id = '" + mId.getText()
//                    + "'";
            sqlQuery = " UPDATE partneri_racuni "
                    + " SET aktivan=0 WHERE id = '" + mIzabranaSifra
                    + "'";
//                System.out.println(sqlQuery);
            // display the showOptionDialog
            int choice = JOptionPane.showOptionDialog(this,
                    "Da li ste sigurni da želite obrisati rekord?",
                    "Brisanje?",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null, null, null);

            // interpret the user's choice
            if (choice == JOptionPane.YES_OPTION) {
                stmt.execute(sqlQuery);
//                    model.removeRow(tblSifre.getSelectedRow());
                model.removeRow(jTableBankeRacuni.getSelectedRow());
                iMessage.setText("Rekord je obrisan.");

            }

        } catch (SQLException ex) {
            MyLogger.logger.error("Partneri " + ex.toString());
            iMessage.setText(ex.toString());
        }
//        }  
    }//GEN-LAST:event_BrisanjeBtnRacunActionPerformed

    private void saveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtnActionPerformed
        // TODO add your handling code here:

        if (mId.getText().equals("") || mId.getText().isEmpty()) {
            insertuj_rekord(false);
        } else {
            insertuj_rekord(true);
        }
        tp.puniTabelu();
        UnosBtn.setEnabled(false);
        UnosBtn.repaint();
    }//GEN-LAST:event_saveBtnActionPerformed

    private void UnosBtnRacunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UnosBtnRacunActionPerformed
        // TODO add your handling code here:
        String mIzabraniId = mId.getText();
        if (mIzabraniId != null && !mIzabraniId.isEmpty()) {
//            Thread t1 = new Thread(new Runnable() { // nemoze u tred jer je onda tred this
//                public void run() {

            String mIzabranaSifra = mSifra.getText();

            if (mIzabranaSifra != null && !mIzabranaSifra.isEmpty()) {
//                        mIzabranaSifra = mSifra.getText();
                maticni.partneri.PartneriRacuni cen = new maticni.partneri.PartneriRacuni(conn, "partneri_racuni", mIzabranaSifra, this);
                cen.setVisible(true);

            } else {

//                    mIzabranaSifra = model.getValueAt(index, 1).toString();
                maticni.partneri.PartneriRacuni cen = new maticni.partneri.PartneriRacuni();
                cen.setVisible(true);

            }

//                }
//            });
//            t1.start();
        } else {
            MyLogger.logger.error("Potrebno je prvo sačuvati partnera.... ");
            iMessage.setText("Potrebno je prvo sačuvati partnera.... ");
        }


    }//GEN-LAST:event_UnosBtnRacunActionPerformed

    private void mAdresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mAdresaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mAdresaActionPerformed

    private void mFaxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mFaxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mFaxActionPerformed

    private void mEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mEmailActionPerformed

    private void mTelefonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mTelefonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mTelefonActionPerformed

    private void mPDVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mPDVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mPDVActionPerformed

    private void mJmbgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mJmbgActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mJmbgActionPerformed

    private void mNazivKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mNazivKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            mAdresa.requestFocus();
        }
    }//GEN-LAST:event_mNazivKeyReleased

    private void mAdresaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mAdresaKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            mPttBroj.requestFocus();
        }
    }//GEN-LAST:event_mAdresaKeyReleased

    private void mTelefonKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mTelefonKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            mFax.requestFocus();
        }
    }//GEN-LAST:event_mTelefonKeyReleased

    private void mFaxKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mFaxKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            mEmail.requestFocus();
        }
    }//GEN-LAST:event_mFaxKeyReleased

    private void mEmailKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mEmailKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            mMaticniBroj.requestFocus();
        }
    }//GEN-LAST:event_mEmailKeyReleased

    private void mMaticniBrojKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mMaticniBrojKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            mPib.requestFocus();
        }
    }//GEN-LAST:event_mMaticniBrojKeyReleased

    private void mPibKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mPibKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            mWeb.requestFocus();
        }
    }//GEN-LAST:event_mPibKeyReleased

    private void mWebKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mWebKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            mPDV.requestFocus();
        }
    }//GEN-LAST:event_mWebKeyReleased

    private void mPDVKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mPDVKeyReleased
        // TODO add your handling code here:

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            mJmbg.requestFocus();
        }
    }//GEN-LAST:event_mPDVKeyReleased

    private void mJmbgKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mJmbgKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            mNapomena.requestFocus();
        }
    }//GEN-LAST:event_mJmbgKeyReleased

    private void mNapomenaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mNapomenaKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            saveBtn.requestFocus();
        }
    }//GEN-LAST:event_mNapomenaKeyReleased

    private void mDrzavaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mDrzavaActionPerformed
        // TODO add your handling code here:
//                mKategorija.requestFocus();
    }//GEN-LAST:event_mDrzavaActionPerformed

    private void mKategorijaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mKategorijaActionPerformed
        // TODO add your handling code here:
//                mTelefon.requestFocus();
    }//GEN-LAST:event_mKategorijaActionPerformed

    // filter data
    private void filter(String query) {
//        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>((DefaultTableModel) tblSifre.getModel());
//        tr.setRowFilter(RowFilter.regexFilter(query));
    }

    private void punjenjeJtable() {

        lTabela.setText(" " + tabela);

//        DefaultTableModel model = (DefaultTableModel) tblSifre.getModel();
//        TableColumnModel tcm = tblSifre.getColumnModel();
//        tcm.getColumn(0).setPreferredWidth(10);     //Name
//        tcm.getColumn(1).setPreferredWidth(10);    //Title
//        tcm.getColumn(2).setPreferredWidth(170);    //Surname
//        tcm.getColumn(3).setPreferredWidth(5);    //ID
//        tcm.getColumn(4).setPreferredWidth(5);    //ID
//        tblSifre.getColumnModel().getColumn(0).setPreferredWidth(10);
//        tblSifre.getColumnModel().getColumn(1).setPreferredWidth(10);
//        tblSifre.getColumnModel().getColumn(2).setPreferredWidth(170);
//        tblSifre.getColumnModel().getColumn(3).setPreferredWidth(5);
//        tblSifre.getColumnModel().getColumn(4).setPreferredWidth(5);
//        tblSifre.removeAll();
//        if (model.getRowCount() > 0) {
//            for (int i = model.getRowCount() - 1; i > -1; i--) {
//                model.removeRow(i);
//            }
//        }
//        model.setValueAt(mId.getText(), tblSifre.getSelectedRow(), 0);
//        model.setValueAt(mSifra.getText(), tblSifre.getSelectedRow(), 1);
//        model.setValueAt(mNaziv.getText(), tblSifre.getSelectedRow(), 2);
        try {
            Statement stmt = conn.createStatement();
            // String[] array = yourString.split(wordSeparator);
//            String aa = (String) List1.getSelectedValue();
//            String[] niz = aa.split(AsoftComboBox.COMBOBOX_ITEM_SEPARATOR);

            String sqlQuery = " select * from " + tabela + " order by id ";
            // String sqlQuery = " select * from " + tabela + " where aktivan order by id ";
            ResultSet rs = stmt.executeQuery(sqlQuery);

            while (rs.next()) {
                mId.setText(rs.getString(1));
                mSifra.setText(rs.getString(2));
                mNaziv.setText(rs.getString(3));
//                    int cetvrti = rs.getInt(5);
                boolean mmAktivan = (rs.getInt(5) == 1) ? true : false;

                mAktivan.setSelected(mmAktivan);
//                    if (rs.getInt(4))
//                model.addRow(new Object[]{mId.getText(), mSifra.getText(), mNaziv.getText(), mmAktivan});
            }
            mId.setText("");
            mSifra.setText("");
            mNaziv.setText("");
            mAktivan.setSelected(true);

        } catch (SQLException ex) {
            MyLogger.logger.error("Artikli " + ex.toString());
            iMessage.setText(ex.toString());
        }

    }
    // puni tabelu i model sa uslovom

    private void punjenjeJtableIPrikazModelaSaUslovom(String ValToSearch) {

        lTabela.setText(" " + tabela);
//        DefaultTableModel model = (DefaultTableModel) tblSifre.getModel();
//        TableColumnModel tcm = tblSifre.getColumnModel();

//        tblSifre.getColumnModel().getColumn(0).setPreferredWidth(10);
//        tblSifre.getColumnModel().getColumn(1).setPreferredWidth(10);
//        tblSifre.getColumnModel().getColumn(2).setPreferredWidth(170);
//        tblSifre.getColumnModel().getColumn(3).setPreferredWidth(10);
//        tcm.getColumn(0).setPreferredWidth(10);     //Name
//        tcm.getColumn(1).setPreferredWidth(10);    //Title
//        tcm.getColumn(2).setPreferredWidth(170);    //Surname
//        tcm.getColumn(3).setPreferredWidth(10);    //ID
//        tcm.getColumn(4).setPreferredWidth(10);    //ID
//
//        tblSifre.getColumnModel().getColumn(0).setCellRenderer(new CustomRenderer());
//        tblSifre.getColumnModel().getColumn(1).setCellRenderer(new CustomRenderer());
//        tblSifre.getColumnModel().getColumn(2).setCellRenderer(new CustomRenderer());
//        tblSifre.getColumnModel().getColumn(3).setCellRenderer(new CustomRenderer());
//        tblSifre.getColumnModel().getColumn(4).setCellRenderer(new CustomRenderer());
//        tblSifre.getColumnModel().getColumn(0).setPreferredWidth(10);
//        tblSifre.getColumnModel().getColumn(1).setPreferredWidth(10);
//        tblSifre.getColumnModel().getColumn(2).setPreferredWidth(170);
//        tblSifre.getColumnModel().getColumn(3).setPreferredWidth(10);
//        tblSifre.removeAll();
//        if (model.getRowCount() > 0) {
//            for (int i = model.getRowCount() - 1; i > -1; i--) {
//                model.removeRow(i);
//            }
//        }
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
                    + "LOWER(CONCAT(id`, `sifra`, `naziv`)) LIKE LOWER('%" + ValToSearch + "%') order by id";

//            String searchQuery = "SELECT * FROM " + tabela + " WHERE "
//                    + "CONCAT(`id`, `sifra`, `naziv`) LIKE '%" + ValToSearch + "%' order by id";
            ResultSet rs = stmt.executeQuery(searchQuery);
            rekordaUslektu = 0;
            while (rs.next()) {
                mId.setText(rs.getString(1));
                mSifra.setText(rs.getString(2));
                mNaziv.setText(rs.getString(3));
//                    int cetvrti = rs.getInt(5);
                boolean mmAktivan = (rs.getInt(5) == 1) ? true : false;

                mAktivan.setSelected(mmAktivan);
//                    if (rs.getInt(4))
//                model.addRow(new Object[]{mId.getText(), mSifra.getText(), mNaziv.getText(), mmAktivan, rekordaUslektu});
                rekordaUslektu++;
            }
            mId.setText("");
            mSifra.setText("");
            mNaziv.setText("");
            mAktivan.setSelected(true);

        } catch (SQLException ex) {
            MyLogger.logger.error("Artikli " + ex.toString());
            iMessage.setText(ex.toString());
        }
    }

//
//    public void puniTabeluAkcija() {
//
////        ArrayList<ArtikalAkcije> abk = ListArtikalAkcije(mSifra.getText());
//        DefaultTableModel model = new DefaultTableModel();
//
////        DefaultTableModel model = (DefaultTableModel) jTableCene.getModel();
//        TableColumnModel tcm = jTableAkcije.getColumnModel();
//
//        tcm.getColumn(0).setPreferredWidth(20);    //Name
//        tcm.getColumn(1).setPreferredWidth(120);   //Title
//        tcm.getColumn(2).setPreferredWidth(250);   //Surname
//        tcm.getColumn(3).setPreferredWidth(50);    //ID
//        tcm.getColumn(4).setPreferredWidth(50);    //ID
//        tcm.getColumn(5).setPreferredWidth(50);    //ID
//        tcm.getColumn(6).setPreferredWidth(50);    //ID
//        tcm.getColumn(7).setPreferredWidth(50);    //ID
//        tcm.getColumn(8).setPreferredWidth(50);    //ID
//        tcm.getColumn(9).setPreferredWidth(50);    //ID        
//        //         
//        model.setColumnIdentifiers(new Object[]{"Id", "Sifra", "Mp cena", "Akcija",
//            "Datum od", "Datum do", "Aktivan", "Slike", "Zvuk", "Video"});
//
//        Object[] row = new Object[10];
//        for (int i = 0; i < abk.size(); i++) {
//            row[0] = abk.get(i).getId();
//            row[1] = abk.get(i).getSifra();
//            row[2] = abk.get(i).getMpcena();
//            row[3] = abk.get(i).getMpcena_akcijska();
//            row[4] = abk.get(i).getDatum_akcije_od();
//            row[5] = abk.get(i).getDatum_akcije_do();
//            row[6] = abk.get(i).getAktivan();
//            row[7] = abk.get(i).getSlike();
//            row[8] = abk.get(i).getZvuk();
//            row[9] = abk.get(i).getVideo();
//
//            model.addRow(row);
//        }
//
//        jTableAkcije.setModel(model);
//
//        jTableAkcije.getColumnModel().getColumn(0).setCellRenderer(new CustomRenderer(1));
//        jTableAkcije.getColumnModel().getColumn(1).setCellRenderer(new CustomRenderer(1));
//        jTableAkcije.getColumnModel().getColumn(2).setCellRenderer(new CustomRenderer(1));
//        jTableAkcije.getColumnModel().getColumn(3).setCellRenderer(new CustomRenderer(1));
//        jTableAkcije.getColumnModel().getColumn(4).setCellRenderer(new CustomRenderer(1));
//        jTableAkcije.getColumnModel().getColumn(5).setCellRenderer(new CustomRenderer(1));
//        jTableAkcije.getColumnModel().getColumn(6).setCellRenderer(new CustomRenderer(1));
//        jTableAkcije.getColumnModel().getColumn(7).setCellRenderer(new CustomRenderer(1));
//        jTableAkcije.getColumnModel().getColumn(8).setCellRenderer(new CustomRenderer(1));
//        jTableAkcije.getColumnModel().getColumn(9).setCellRenderer(new CustomRenderer(1));
//    }
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
            java.util.logging.Logger.getLogger(PartneriAzuriranje.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PartneriAzuriranje.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PartneriAzuriranje.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PartneriAzuriranje.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new PartneriAzuriranje().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BrisanjeBtn;
    private javax.swing.JButton BrisanjeBtnRacun;
    private javax.swing.JButton FirstBtn;
    private javax.swing.JButton IzlazBtn;
    private javax.swing.JButton IzmenaBtn;
    private javax.swing.JButton LastBtn;
    private javax.swing.JButton NextBtn;
    private javax.swing.JButton PregledBtn;
    private javax.swing.JButton PrevBtn;
    private javax.swing.JButton RefreshBtn;
    private javax.swing.JButton UnosBtn;
    private javax.swing.JButton UnosBtnRacun;
    private javax.swing.JLabel iMessage;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableBankeRacuni;
    private javax.swing.JLabel lTabela;
    private javax.swing.JTextField mAdresa;
    private javax.swing.JCheckBox mAktivan;
    private javax.swing.JComboBox mDrzava;
    private javax.swing.JTextField mEmail;
    private javax.swing.JTextField mFax;
    private javax.swing.JTextField mId;
    private javax.swing.JTextField mJmbg;
    private org.asoft.library.AsoftNumericField mKalo;
    private javax.swing.JComboBox mKategorija;
    private javax.swing.JTextField mMaticniBroj;
    private javax.swing.JTextArea mNapomena;
    private javax.swing.JTextField mNaziv;
    private javax.swing.JTextField mPDV;
    private javax.swing.JTextField mPib;
    private javax.swing.JComboBox mPttBroj;
    private javax.swing.JTextField mSifra;
    private javax.swing.JTextField mTelefon;
    private javax.swing.JTextField mWeb;
    private javax.swing.JLabel recordLbl;
    private javax.swing.JButton saveBtn;
    // End of variables declaration//GEN-END:variables

    private int vratiJedanIliNula(boolean selected) {
        if (selected) {
            return 1;
        } else {
            return 0;
        }
    }

    private void nuliranjeForme0() {

//        mLabelSlika.setIcon(null);
//        mDatumCenovnika.initPicker("mDatumCenovnika", null);
//        mAkcijaTrajeOd.initPicker("mAkcijaTrajeOd", null);
//        mAkcijaTrajeDo.initPicker("mAkcijaTrajeDo", null);
//
//        try {
////            mDatumCenovnika.setDate(AsoftDate.EMPTY_DATE);
//            mAkcijaTrajeOd.setDate(AsoftDate.EMPTY_DATE);
//            mAkcijaTrajeDo.setDate(AsoftDate.EMPTY_DATE);
//        } catch (ParseException ex) {
////            JOptionPane.showMessageDialog(null, ex);
//            MyLogger.logger.error("Artikli " + ex.toString());
//            iMessage.setText(ex.toString());
//        }
        mNaziv.setText("");
        mSifra.setText("");
        mMaticniBroj.setText("");
        mPib.setText("");
        mAdresa.setText("");

//        List<String> listTipArtikla = puniComboMaticni.puni_maticne(conn, "tip_artikla");
//        mTip.setModel(new DefaultComboBoxModel(listTipArtikla.toArray()));
        List<String> listJediniceMere = puniComboMaticni.puni_maticne(conn, "ptt_brojevi");
        mPttBroj.setModel(new DefaultComboBoxModel(listJediniceMere.toArray()));

        List<String> listPoreskaGrupa = puniComboMaticni.puni_maticne(conn, "drzave");
        mDrzava.setModel(new DefaultComboBoxModel(listPoreskaGrupa.toArray()));

        List<String> listOdeljenje = puniComboMaticni.puni_maticne(conn, "klasifikacija_partnera");
        mKategorija.setModel(new DefaultComboBoxModel(listOdeljenje.toArray()));

//        List<String> listKlasifikacija = puniComboMaticni.puni_maticne(conn, "klasifikacija_artikala");
//        mKlasifikacija.setModel(new DefaultComboBoxModel(listKlasifikacija.toArray()));
//
//        List<String> listCarinskaTarifa = puniComboMaticni.puni_maticne(conn, "carinska_tarifa_artikala");
//        mCarinskaTarifa.setModel(new DefaultComboBoxModel(listCarinskaTarifa.toArray()));
//
//        List<String> listPartneri = puniComboMaticni.puni_maticne(conn, "partneri");
//        mProizvodjac.setModel(new DefaultComboBoxModel(listPartneri.toArray()));
//        mSlika.setText(service.uzmiParametar.uzmiParametar(conn, "artikal_icon"));
        mNapomena.setText("");
//        proizvodjac/kupac
//        List<String> listCarinskaTarifa = puniComboMaticni.puni_maticne(conn, "carinska_tarifa_artikala");
//        mCarinskaTarifa.setModel(new DefaultComboBoxModel(listCarinskaTarifa.toArray())); 

//        mRokTrajanja.setValue(NULA);
        mKalo.setValue(NULA);
//        jCheckBoxSklop.setSelected(false);

//        mNabavnaCena.setValue(NULA);
//        mPlanskaCena.setValue(NULA);
//        mProdajnaCena.setValue(NULA);
//        mProdajnaCenaSaPDV.setValue(NULA);
//        mMaloprodajnaCenaBezPDV.setValue(NULA);
//        mMaloprodajnaCenaBezPDV.setValue(NULA);
//        List<String> listVrstaPakovanjaArtikala = puniComboMaticni.puni_maticne(conn, "vrste_pakovanja_artikala");
//        mVrstaPakovanjaArtikala.setModel(new DefaultComboBoxModel(listVrstaPakovanjaArtikala.toArray()));
//
//        mEAN.setText("");
//
//        List<String> listAmbalaza = puniComboMaticni.puni_maticne(conn, "ambalaza");
//        mAmbalaza.setModel(new DefaultComboBoxModel(listAmbalaza.toArray()));
//
//        mSirina.setValue(NULA);
//        mDuzina.setValue(NULA);
//        mVisina.setValue(NULA);
//        mTezina.setValue(NULA);
//
//        mNetoUJm.setValue(NULA);
//        mBrutoUJm.setValue(NULA);
//        mKomada.setValue(NULA);
//
//        akcijaCheckBox.setSelected(false);
//        mAkcijskaCena.setValue(NULA);
//
//        mAkcijskaCena.setEnabled(false);
//        mAkcijaTrajeOd.setEnabled(false);
//        mAkcijaTrajeDo.setEnabled(false);
    }

    private void napuniEkran(String id) {

//         iniciraj combo
        Statement stmt;
        ResultSet rs;

        try {
            stmt = conn.createStatement();

            String sqlQuery = "SELECT artikli.id, artikli.sifra, artikli.naziv, slika, \n"
                    + "tip_artikla.naziv, artikli.aktivan, artikli.sifra2, artikli.plu, \n"
                    + "artikli.poreska_grupa_id, artikli.odeljenje_id, artikli.artikal_kategorija_id, \n"
                    + "artikli.artikal_tip_id, artikli.carinska_tarifa_artikala_id, rok_trajanja, kalo, artikli.sklop, \n"
                    + "jedinica_mere.naziv, poreska_tarifa.naziv, odeljenje.naziv, klasifikacija_artikala.naziv, \n"
                    + "carinska_tarifa_artikala.naziv, partneri.naziv, photo \n"
                    + "FROM artikli \n"
                    + "LEFT JOIN tip_artikla ON tip_artikla.id = artikli.artikal_tip_id \n"
                    + "LEFT JOIN jedinica_mere ON jedinica_mere.id = artikli.jedinica_mere_id \n"
                    + "LEFT JOIN poreska_tarifa ON poreska_tarifa.id = artikli.poreska_grupa_id \n"
                    + "LEFT JOIN odeljenje ON odeljenje.id = artikli.odeljenje_id \n"
                    + "LEFT JOIN klasifikacija_artikala ON klasifikacija_artikala.id = artikli.artikal_kategorija_id \n"
                    + "LEFT JOIN carinska_tarifa_artikala ON carinska_tarifa_artikala.id = artikli.carinska_tarifa_artikala_id \n"
                    + "LEFT JOIN partneri ON partneri.id = artikli.partneri_id \n"
                    + "where artikli.id like '" + id + "' and artikli.aktivan=1";

            sqlQuery = "SELECT * FROM `partneri` "
                    + " LEFT JOIN ptt_brojevi ON ptt_brojevi.id = partneri.ptt_broj_id "
                    + " and ptt_brojevi.aktivan "
                    + " LEFT JOIN drzave ON drzave.id = partneri.zemlja_id and drzave.aktivan "
                    + " LEFT JOIN klasifikacija_partnera ON klasifikacija_partnera.id "
                    + "= partneri.kategorija_partneri_id and klasifikacija_partnera.aktivan "
                    //                    + " WHERE "
                    + " where partneri.id like '" + id + "' and partneri.aktivan=1";

            System.out.println(sqlQuery);
            rs = stmt.executeQuery(sqlQuery);

            while (rs.next()) {

                Integer mAktivann = rs.getInt("partneri.aktivan");
                if (mAktivann == 1) {
                    mAktivan.setSelected(true);
                } else {
                    mAktivan.setSelected(false);
                }

                mNaziv.setText(rs.getString("partneri.naziv"));
                mSifra.setText(rs.getString("partneri.sifra"));
                mMaticniBroj.setText(rs.getString("partneri.maticni_broj"));
                mPib.setText(rs.getString("partneri.pib"));

                mId.setText(rs.getString("partneri.id"));

                mAdresa.setText(rs.getString("adresa"));
                mTelefon.setText(rs.getString("telefon"));
                mFax.setText(rs.getString("fax"));
                mEmail.setText(rs.getString("email"));
                mWeb.setText(rs.getString("web"));
                mPDV.setText(rs.getString("pdv"));
                mJmbg.setText(rs.getString("jmbg"));
                mNapomena.setText(rs.getString("napomena"));
//                List<String> listTipArtikla = puniComboMaticni.puni_maticne(conn, "tip_artikla");
//                mTip.setModel(new DefaultComboBoxModel(listTipArtikla.toArray()));
//                String aa = rs.getString("tip_artikla.naziv");
//                mTip.setSelectedItem(rs.getString("tip_artikla.naziv"));

                List<String> listJediniceMere = puniComboMaticni.puni_maticne(conn, "ptt_brojevi");
                mPttBroj.setModel(new DefaultComboBoxModel(listJediniceMere.toArray()));
                String aa = rs.getString("ptt_brojevi.naziv");
                mPttBroj.setSelectedItem(rs.getString("ptt_brojevi.naziv"));

                List<String> listPoreskaGrupa = puniComboMaticni.puni_maticne(conn, "drzave");
                mDrzava.setModel(new DefaultComboBoxModel(listPoreskaGrupa.toArray()));
                aa = rs.getString("drzave.naziv");
                mDrzava.setSelectedItem(rs.getString("drzave.naziv"));

//                List<String> listOdeljenje = puniComboMaticni.puni_maticne(conn, "odeljenje");
//                mKategorija.setModel(new DefaultComboBoxModel(listOdeljenje.toArray()));
//                mKategorija.setSelectedItem(rs.getString("odeljenje.naziv"));
                List<String> listKlasifikacija = puniComboMaticni.puni_maticne(conn, "klasifikacija_partnera");
                mKategorija.setModel(new DefaultComboBoxModel(listKlasifikacija.toArray()));
                mKategorija.setSelectedItem(rs.getString("klasifikacija_partnera.naziv"));

//                List<String> listCarinskaTarifa = puniComboMaticni.puni_maticne(conn, "carinska_tarifa_artikala");
//                mCarinskaTarifa.setModel(new DefaultComboBoxModel(listCarinskaTarifa.toArray()));
//                mCarinskaTarifa.setSelectedItem(rs.getString("carinska_tarifa_artikala.naziv"));
//                List<String> listProizvodjac = puniComboMaticni.puni_maticne(conn, "partneri");
//                mProizvodjac.setModel(new DefaultComboBoxModel(listProizvodjac.toArray()));
//                mProizvodjac.setSelectedItem(rs.getString("partneri.naziv"));
//                mRokTrajanja.setValue(rs.getDouble("rok_trajanja"));
//                mKalo.setValue(rs.getDouble("kalo"));
//                mDrzavljanstvo.setSelectedItem(rs.getString("drzavljanstvo"));
//                mTelefon.setText(rs.getString("telefon"));
//                mEmail.setText(rs.getString("e_mail"));
//                String a = rs.getString("oslovljavanje");
//                mOslovljavanje.setSelectedItem(rs.getString("oslovljavanje"));
//                mCrkvenaTitula.setSelectedItem(rs.getString("crkvena_titula"));
//                mAkademskaTitula.setSelectedItem(rs.getString("akademska_titula"));
//                mNacionalnost.setSelectedItem(rs.getString("nacionalnost"));
//                mZupaNaziv.setSelectedItem(rs.getString("zupa_naziv"));
//                mUstanovaNaziv.setSelectedItem(rs.getString("ustanova_naziv"));
//                mTipEntiteta.setSelectedItem(rs.getString("tip_entiteta"));
//                mBanka.setSelectedItem(rs.getString("banka"));
//                mTekuciRacun.setText(rs.getString("tekuci_racun"));
//                mDrzavljanstvo.setSelectedItem(rs.getString("drzavljanstvo"));
//                mTelefon.setText(rs.getString("telefon"));
//                mEmail.setText(rs.getString("e_mail"));
//                mImeULicnojKarti.setText(rs.getString("ime_u_lk"));
//                mIme.setText(rs.getString("ime"));
//                mPrezimeULicnojKarti.setText(rs.getString("prezime_u_lk"));
//                mPrezime.setText(rs.getString("prezime"));
//                mSlika.setText(rs.getString("slika"));
//                String filename = rs.getString("slika");
//                mSlika.setText(filename);
//                ImageIcon icon = new ImageIcon(filename);
//                mLabelSlika.setIcon(icon);
//                napuniListuOPastoralnojSluzbi(id);
//                napuniListuODodatnojSluzbi(id);
//                napuniListuOPodaciOOsiguranju(id);
            }

        } catch (SQLException ex) {
            MyLogger.logger.error("Partneri " + ex.toString());
            iMessage.setText(ex.toString());
        }

//        PripremaTabeleBankeRacuni(mSifra.getText());
//        PripremaTabelePakovanja(mSifra.getText());
//        PripremaTabeluAkcija(mSifra.getText());
    }

    public void PripremaTabeleBankeRacuni(String sifra) {

//        PodaciOPastoralnojSluzbi pops = new PodaciOPastoralnojSluzbi();
        PartneriRacuniModelArray.clear();
        try {

            String sqlQuery = " select *, banke.naziv as bankenaziv from partneri_racuni "
                    + "LEFT JOIN banke ON banke.sifra = partneri_racuni.banka_sifra "
                    + " and banke.aktivan \n"
                    + " where partner_sifra = '"
                    + sifra + "'" + " and partneri_racuni.aktivan";
            PreparedStatement stmt = conn.prepareStatement(sqlQuery);
            ResultSet rs = stmt.executeQuery(sqlQuery);

            if (uzmiParametar(conn, "debugiranje").equals("1")) {
                System.out.println(sqlQuery);
            }

            while (rs.next()) {

                PartneriRacuniModel partneriRacuni = new PartneriRacuniModel();
                partneriRacuni.setId(rs.getInt("id"));
                partneriRacuni.setBankaSifra(rs.getString("banka_sifra"));
                partneriRacuni.setSlika(rs.getString("bankenaziv"));
                partneriRacuni.setTekuciRacun(rs.getString("tekuci_racun"));
                partneriRacuni.setAktivan(rs.getBoolean("aktivan"));

                PartneriRacuniModelArray.add(partneriRacuni);
            }

        } catch (SQLException ex) {
            MyLogger.logger.info(ex.toString());
            iMessage.setText(ex.toString());
        }

//        FillListPodaciOCenama();
        DefaultTableModel model = new DefaultTableModel();

//        DefaultTableModel model = (DefaultTableModel) jTableCene.getModel();
        TableColumnModel tcm = jTableBankeRacuni.getColumnModel();

        tcm.getColumn(0).setPreferredWidth(20);     //Name
        tcm.getColumn(1).setPreferredWidth(120);    //Title
        tcm.getColumn(2).setPreferredWidth(250);    //Surname
        tcm.getColumn(3).setPreferredWidth(50);    //ID
        tcm.getColumn(4).setPreferredWidth(50);    //ID

        //         
        model.setColumnIdentifiers(new Object[]{"Id", "Sifra", "Naziv", "Tekuci racun", "Aktivan"});
        Object[] row = new Object[5];
        for (int i = 0; i < PartneriRacuniModelArray.size(); i++) {
            row[0] = PartneriRacuniModelArray.get(i).getId();
            row[1] = PartneriRacuniModelArray.get(i).getBankaSifra();
            row[2] = PartneriRacuniModelArray.get(i).getSlika();
            row[3] = PartneriRacuniModelArray.get(i).getTekuciRacun();
            row[4] = PartneriRacuniModelArray.get(i).getAktivan();
            model.addRow(row);
        }

        jTableBankeRacuni.setModel(model);

        jTableBankeRacuni.getColumnModel().getColumn(0).setCellRenderer(new CustomRenderer(1));
        jTableBankeRacuni.getColumnModel().getColumn(1).setCellRenderer(new CustomRenderer(1));
        jTableBankeRacuni.getColumnModel().getColumn(2).setCellRenderer(new CustomRenderer(1));
        jTableBankeRacuni.getColumnModel().getColumn(3).setCellRenderer(new CustomRenderer(1));
        jTableBankeRacuni.getColumnModel().getColumn(4).setCellRenderer(new CustomRenderer(1));

    }

    public ArrayList<model.ArtikalBarkod> ListArtikalBarkod(String ValToSearch) {
        ArrayList<model.ArtikalBarkod> acList = new ArrayList<model.ArtikalBarkod>();
        Statement st;
        ResultSet rs;
        try {
            // Connection con = getConnection();
            st = conn.createStatement();
            String searchQuery = "SELECT artikli_pakovanja.id, artikli_pakovanja.tippak, \n"
                    + "artikli_pakovanja.artikal_sifra, \n"
                    + "artikli_pakovanja.ean,  artikli_pakovanja.netojm, artikli_pakovanja.brutojm, \n"
                    + "artikli_pakovanja.sirina,  artikli_pakovanja.visina, artikli_pakovanja.duzina, \n"
                    + "artikli_pakovanja.komada,  artikli_pakovanja.sifra_ambalaze, "
                    + "vrste_pakovanja_artikala.naziv, ambalaza.naziv, artikli_pakovanja.aktivan \n"
                    + " FROM artikli_pakovanja \n"
                    + "LEFT JOIN vrste_pakovanja_artikala ON vrste_pakovanja_artikala.sifra = artikli_pakovanja.tippak \n"
                    + "LEFT JOIN ambalaza ON ambalaza.sifra = artikli_pakovanja.sifra_ambalaze \n"
                    + "WHERE \n"
                    + "artikli_pakovanja.artikal_sifra LIKE '%" + ValToSearch + "%' AND artikli_pakovanja.aktivan=1 " // `id`,
                    + "order by artikli_pakovanja.tippak "; // desc

            System.out.println(searchQuery);
            rs = st.executeQuery(searchQuery);

            model.ArtikalBarkod ac;

            while (rs.next()) {

                ac = new ArtikalBarkod(rs.getInt("id"), rs.getString("artikli_pakovanja.tippak"),
                        rs.getString("artikli_pakovanja.artikal_sifra"), rs.getString("ean"),
                        rs.getDouble("netojm"), rs.getDouble("brutojm"),
                        rs.getDouble("sirina"), rs.getDouble("visina"),
                        rs.getDouble("duzina"), rs.getDouble("komada"),
                        rs.getString("sifra_ambalaze"),
                        rs.getInt("artikli_pakovanja.aktivan")
                );

                acList.add(ac);

//                mVrstaPakovanjaArtikala.setSelectedItem(rs.getString("vrste_pakovanja_artikala.naziv"));
//                mEAN.setText(rs.getString("ean"));
//                mAmbalaza.setSelectedItem(rs.getString("ambalaza.naziv"));
//                mSirina.setValue(new AsoftDFP(rs.getDouble("sirina")));
//                mDuzina.setValue(new AsoftDFP(rs.getDouble("duzina")));
//                mVisina.setValue(new AsoftDFP(rs.getDouble("visina")));
//                mKomada.setValue(new AsoftDFP(rs.getDouble("komada")));
//                mNetoUJm.setValue(new AsoftDFP(rs.getDouble("netojm")));
//                mBrutoUJm.setValue(new AsoftDFP(rs.getDouble("brutojm")));
                // mneoporeziviIznos = npNeoporeziviIznos.getValueDFP().doubleValue();
            }
        } catch (Exception ex) {
            MyLogger.logger.error("Artikli " + ex.toString());
            iMessage.setText(ex.toString());
        }
        return acList;
    }
//
//    public void puniTabeluPakovanja() {
//
//        ArrayList<ArtikalBarkod> abk = ListArtikalBarkod(mSifra.getText());
//        DefaultTableModel model = new DefaultTableModel();
//
////        DefaultTableModel model = (DefaultTableModel) jTableCene.getModel();
//        TableColumnModel tcm = jTableBarKod.getColumnModel();
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
//        tcm.getColumn(11).setPreferredWidth(50);    //ID
//        //         
//        model.setColumnIdentifiers(new Object[]{"Id", "Sifra", "Vrsta pakovanja", "Ean", "Netojm",
//            "Brutojm", "Sirina", "Duzina", "Visina", "Komada", "Ambalaza", "Aktivan"});
//        Object[] row = new Object[11];
//        for (int i = 0; i < abk.size(); i++) {
//            row[0] = abk.get(i).getId();
//            row[1] = abk.get(i).getSifra_artikla();
//            row[2] = abk.get(i).getTippak();
//            row[3] = abk.get(i).getEan();
//            row[4] = abk.get(i).getNetojm();
//            row[5] = abk.get(i).getBrutojm();
//            row[6] = abk.get(i).getSirina();
//            row[7] = abk.get(i).getDuzina();
//            row[8] = abk.get(i).getVisina();
//            row[9] = abk.get(i).getKomada();
//            row[9] = abk.get(i).getSifra_ambalaze();
//            row[10] = abk.get(i).getAktivan();
//            model.addRow(row);
//        }
//
//        jTableBarKod.setModel(model);
//
//        jTableBarKod.getColumnModel().getColumn(0).setCellRenderer(new CustomRenderer(1));
//        jTableBarKod.getColumnModel().getColumn(1).setCellRenderer(new CustomRenderer(1));
//        jTableBarKod.getColumnModel().getColumn(2).setCellRenderer(new CustomRenderer(1));
//        jTableBarKod.getColumnModel().getColumn(3).setCellRenderer(new CustomRenderer(1));
//        jTableBarKod.getColumnModel().getColumn(4).setCellRenderer(new CustomRenderer(1));
//        jTableBarKod.getColumnModel().getColumn(5).setCellRenderer(new CustomRenderer(1));
//        jTableBarKod.getColumnModel().getColumn(6).setCellRenderer(new CustomRenderer(1));
//        jTableBarKod.getColumnModel().getColumn(7).setCellRenderer(new CustomRenderer(1));
//        jTableBarKod.getColumnModel().getColumn(8).setCellRenderer(new CustomRenderer(1));
//        jTableBarKod.getColumnModel().getColumn(9).setCellRenderer(new CustomRenderer(1));
//        jTableBarKod.getColumnModel().getColumn(10).setCellRenderer(new CustomRenderer(1));
//        jTableBarKod.getColumnModel().getColumn(11).setCellRenderer(new CustomRenderer(1));
//    }    

    public void puniTabeluCena() {

        ArrayList<ArtikalCene> ac = ListArtikalCene(mSifra.getText());

        DefaultTableModel model = new DefaultTableModel();

//        DefaultTableModel model = (DefaultTableModel) jTableCene.getModel();
        TableColumnModel tcm = jTableBankeRacuni.getColumnModel();

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
//        tcm.getColumn(10).setPreferredWidth(50);    //ID

        //         
        model.setColumnIdentifiers(new Object[]{"Id", "Sifra", "Datum", "Nabavna", "Planska cena",
            "Prodajna cena bez pdv", "Prodajna", "Maloprodajna", "Maloprodajna bez pdv", "Aktivan"});
        Object[] row = new Object[10];
        for (int i = 0; i < ac.size(); i++) {
            row[0] = ac.get(i).getId();
            row[1] = ac.get(i).getArtikal();
            row[2] = ac.get(i).getDatum();
            row[3] = ac.get(i).getNabavna_cena();
            row[4] = ac.get(i).getPlanska_cena();
            row[5] = ac.get(i).getProdajna_cena_sa_pdv();
            row[6] = ac.get(i).getProdajna_cena();
            row[7] = ac.get(i).getMaloprodajna_cena_bez_pdv();
            row[8] = ac.get(i).getMaloprodajna_cena();
            row[9] = ac.get(i).getAktivan();
            model.addRow(row);
        }

        jTableBankeRacuni.setModel(model);

        jTableBankeRacuni.getColumnModel().getColumn(0).setCellRenderer(new CustomRenderer(1));
        jTableBankeRacuni.getColumnModel().getColumn(1).setCellRenderer(new CustomRenderer(1));
        jTableBankeRacuni.getColumnModel().getColumn(2).setCellRenderer(new CustomRenderer(1));
        jTableBankeRacuni.getColumnModel().getColumn(3).setCellRenderer(new CustomRenderer(1));
        jTableBankeRacuni.getColumnModel().getColumn(4).setCellRenderer(new CustomRenderer(1));
        jTableBankeRacuni.getColumnModel().getColumn(5).setCellRenderer(new CustomRenderer(1));
        jTableBankeRacuni.getColumnModel().getColumn(6).setCellRenderer(new CustomRenderer(1));
        jTableBankeRacuni.getColumnModel().getColumn(7).setCellRenderer(new CustomRenderer(1));
        jTableBankeRacuni.getColumnModel().getColumn(8).setCellRenderer(new CustomRenderer(1));
        jTableBankeRacuni.getColumnModel().getColumn(9).setCellRenderer(new CustomRenderer(1));

    }

    // sluzi samo za navigaciju
    public ArrayList<model.ArtikalCene> ListArtikalCene(String ValToSearch) {
        ArrayList<model.ArtikalCene> acList = new ArrayList<model.ArtikalCene>();
        Statement st;
        ResultSet rs;
        try {
            // Connection con = getConnection();
            st = conn.createStatement();
            String searchQuery = "SELECT * FROM cenovnik WHERE "
                    + "CONCAT(`id`, `sifra`) LIKE '%" + ValToSearch + "%' AND aktivan=1 order by datum "; // desc
            rs = st.executeQuery(searchQuery);
            model.ArtikalCene ac;
            while (rs.next()) {
                ac = new model.ArtikalCene(rs.getInt("id"), rs.getString("artikal_sifra"), rs.getString("datum"),
                        rs.getDouble("nabavna_cena"), rs.getDouble("planska_cena"),
                        rs.getDouble("prodajna_cena_bez_pdv"), rs.getDouble("prodajna_cena"),
                        rs.getDouble("maloprodajna_cena_bez_pdv"), rs.getDouble("maloprodajna_cena"),
                        rs.getInt("aktivan")
                );

                acList.add(ac);

//                mDatumCenovnika.setDate(rs.getDate("datum"));
//                mNabavnaCena.setValue(new AsoftDFP(rs.getDouble("nabavna_cena")));
//                mPlanskaCena.setValue(new AsoftDFP(rs.getDouble("planska_cena")));
//                mProdajnaCena.setValue(new AsoftDFP(rs.getDouble("prodajna_cena")));
//                mProdajnaCenaSaPDV.setValue(new AsoftDFP(rs.getDouble("prodajna_cena_bez_pdv")));
//                mMaloprodajnaCena.setValue(new AsoftDFP(rs.getDouble("maloprodajna_cena")));
//                mMaloprodajnaCenaBezPDV.setValue(new AsoftDFP(rs.getDouble("maloprodajna_cena_bez_pdv")));
                // mneoporeziviIznos = npNeoporeziviIznos.getValueDFP().doubleValue();
            }
        } catch (Exception ex) {
            MyLogger.logger.error("Artikli " + ex.toString());
            iMessage.setText(ex.toString());
        }
        return acList;
    }

    private void upisiZaKraj() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

//    private ArrayList<ArtikalAkcije> ListArtikalAkcije(String ValToSearch) {
//        ArrayList<model.ArtikalAkcije> acList = new ArrayList<model.ArtikalAkcije>();
//        Statement st;
//        ResultSet rs;
//        try {
//            // Connection con = getConnection();
//            st = conn.createStatement();
//            String searchQuery = "SELECT id, sifra, mpcena, mpcena_akcijska, datum_akcije_od, datum_akcije_do, \n"
//                    + "aktivan, slike, zvuk, video \n"
//                    + " FROM akcije \n"
//                    + "WHERE \n"
//                    + "sifra LIKE '%" + ValToSearch + "%' AND aktivan=1 " // `id`,
//                    + "order by datum_akcije_od  "; // desc
//
//            System.out.println(searchQuery);
//            rs = st.executeQuery(searchQuery);
//
//            model.ArtikalAkcije ac;
//
//            while (rs.next()) {
//                ac = new ArtikalAkcije(rs.getInt("id"), rs.getString("sifra"),
//                        rs.getDouble("mpcena"), rs.getDouble("mpcena_akcijska"),
//                        rs.getString("datum_akcije_od"), rs.getString("datum_akcije_do"),
//                        rs.getInt("aktivan"), rs.getString("slike"),
//                        rs.getString("zvuk"), rs.getString("video")
//                );
//
//                acList.add(ac);
//
//                mAkcijaTrajeOd.setDate(rs.getDate("datum_akcije_od"));
//                mAkcijaTrajeDo.setDate(rs.getDate("datum_akcije_do"));
//                mAkcijskaCena.setValue(new AsoftDFP(rs.getDouble("mpcena")));
//
//                // mneoporeziviIznos = npNeoporeziviIznos.getValueDFP().doubleValue();
//            }
//        } catch (Exception ex) {
//            Main.track.error(tabela + " SQLException " + ex);
//        }
//        return acList;
//    }
    private int uzmiIdZaNaziv(String tabela, String selectedItem) {

        int vrati = 0;
        Statement stmt = null;
        try {
            stmt = conn.createStatement();

            String sqlQuery = null;

            sqlQuery = " select id from " + tabela + " WHERE naziv = '" + selectedItem + "'";

            System.out.println(sqlQuery);

            stmt.execute(sqlQuery);

            ResultSet rs = stmt.executeQuery(sqlQuery);

            while (rs.next()) {
                vrati = rs.getInt("id");
            }

        } catch (SQLException ex) {
            MyLogger.logger.error(ex.toString());
            iMessage.setText(ex.toString());
        }
        return vrati;
    }

    private boolean Validation() {

        if (mSifra.getText().equals("") || mSifra.getText().isEmpty()) {
            MyLogger.logger.error("Šifra ne sme biti prazna ...");
            iMessage.setText("Šifra ne sme biti prazna ...");
            return false;
        }
        if (mNaziv.getText().equals("") || mNaziv.getText().isEmpty()) {
            MyLogger.logger.error("Naziv ne sme biti prazna ...");
            iMessage.setText("Naziv ne sme biti prazna ...");
            return false;
        }

        return true;
    }

    private void insertuj_rekord(boolean saBrisanjem) {

        iMessage.setText("");
        String mmIdZaBrisanje = mId.getText();

        if (!Validation()) {
            return;
        }

        String insertSQL = " insert into "
                + " partneri (sifra, maticni_broj, pib,  jmbg, naziv,"
                + " kraci_naziv, adresa, ptt_broj_id, zemlja_id, "
                + " kategorija_partneri_id, telefon, fax, email, "
                + " web,  PDV, aktivan, napomena ) "
                + " values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try {

            PreparedStatement stmt
                    = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, mSifra.getText());
            stmt.setString(2, mMaticniBroj.getText());
            stmt.setString(3, mPib.getText());
            stmt.setString(4, mJmbg.getText());
            stmt.setString(5, mNaziv.getText());
            stmt.setString(6, mNaziv.getText());
            stmt.setString(7, mAdresa.getText());
            stmt.setInt(8, uzmiIdZaNaziv("ptt_brojevi", mPttBroj.getSelectedItem().toString()));
            stmt.setInt(9, uzmiIdZaNaziv("drzave", mDrzava.getSelectedItem().toString()));
            stmt.setInt(10, uzmiIdZaNaziv("klasifikacija_partnera", mKategorija.getSelectedItem().toString()));

            stmt.setString(11, mTelefon.getText());
            stmt.setString(12, mFax.getText());
            stmt.setString(13, mEmail.getText());
            stmt.setString(14, mWeb.getText());
            stmt.setString(15, mPDV.getText());

            stmt.setInt(16, vratiJedanIliNula(mAktivan.isSelected()));
            stmt.setString(17, mNapomena.getText());

            int i = stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            int generatedKey = 0;
            if (rs.next()) {
                generatedKey = rs.getInt(1);
                mId.setText(Integer.toString(generatedKey));
            }

            if (uzmiParametar(conn, "debugiranje").equals("1")) {
                System.out.println(stmt);
            }

            // **** brisanje starog id u tabeli artikli ****** ///
            if (saBrisanjem) {
                id_false(conn, "partneri", mmIdZaBrisanje);
            }
            // **** kraj brisanje starog id u tabeli artikli * ///

        } catch (Exception e) {
            MyLogger.logger.error(e.toString());
            iMessage.setText(e.toString());
        }
        if (saBrisanjem) {
            iMessage.setText("Partner je ispravljen ...");
            MyLogger.logger.info("Partner je ispravljen ...");
        } else {
            iMessage.setText("Partner je upisan ...");
            MyLogger.logger.info("Partner je upisan ...");
        }
    }
}
