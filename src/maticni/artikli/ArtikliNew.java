package maticni.artikli;

import forms_pos.Main;
import forms_pos.Prijava;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static java.sql.Statement.RETURN_GENERATED_KEYS;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import konekcija.konekcija;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import maticni.CustomRenderer;
import model.ArtikalAkcije;
import model.ArtikalBarkod;
import model.ArtikalCene;
import model.ArtikalKolicinaCena;
import model.ArtikliPakovanja;
import model.Cenovnik;
import model.Akcije;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.log4j.Logger;
import org.asoft.library.AsoftDFP;
import static org.asoft.library.AsoftDFP.NULA;
import org.asoft.library.AsoftDate;
import service.MyLogger;
import static service.id_false.id_false;
import service.puniComboMaticni;
import static service.uzmiParametar.uzmiParametar;

/**
 *
 * @author ms
 */
public class ArtikliNew extends javax.swing.JFrame {

    /**
     * Creates new form MaticniPodaci
     */
    Connection conn;
    String tabela, par, akcija;
    int rekordaUslektu = 0;
    MyLogger ml = new MyLogger();

    InputStream mphoto = null;
    ArrayList<Cenovnik> cenovnikArray = new ArrayList<>();
    ArrayList<ArtikliPakovanja> pakovanjaArray = new ArrayList<ArtikliPakovanja>();
    ArrayList<Akcije> akcijeArray = new ArrayList<Akcije>();

    public ArtikliNew() {

        initComponents();
        MyLogger ml = new MyLogger();
        setLocationRelativeTo(null);
        konekcija n = new konekcija();
        conn = n.konekcija();
        tabela = "artikli";

//        punjenjeJtable();
    }

    public ArtikliNew(final Connection conn, final String akcija, String par) {

        this.conn = conn;
        this.akcija = akcija;
        this.par = par;

        initComponents();
        this.setTitle(tabela);

//         MyLogger ml = new MyLogger();
        MyLogger.logger.info("Artikli");

        nuliranjeForme0();

        if (akcija.equalsIgnoreCase("IZMENA")) {
            UnosBtn.setEnabled(false);

            if (!Prijava.getKosam()) {
                UnosBtn.setEnabled(false);
                IzmenaBtn.setEnabled(false);
            }

            napuniEkran(par);
        }

        if (akcija.equalsIgnoreCase("UNOS")) {
            IzmenaBtn.setEnabled(false);
            if (!Prijava.getKosam()) {
                UnosBtn.setEnabled(false);
                IzmenaBtn.setEnabled(false);
            }
//            nuliranjeForme();
        }

        mSifra.grabFocus();

//        mId.setText(par);
//
//        List<String> listTipArtikla = puniComboMaticni.puni_maticne(conn, "tip_artikla");
//        mTip.setModel(new DefaultComboBoxModel(listTipArtikla.toArray()));
//        List<String> listJediniceMere = puniComboMaticni.puni_maticne(conn, "jedinica_mere");
//        mJedinicaMere.setModel(new DefaultComboBoxModel(listJediniceMere.toArray()));
//        List<String> listPoreskaGrupa = puniComboMaticni.puni_maticne(conn, "poreska_tarifa");
//        mPoreskaGrupa.setModel(new DefaultComboBoxModel(listPoreskaGrupa.toArray()));
//        List<String> listOdeljenje = puniComboMaticni.puni_maticne(conn, "odeljenje");
//        mOdeljenje.setModel(new DefaultComboBoxModel(listOdeljenje.toArray()));
//        List<String> listKlasifikacija = puniComboMaticni.puni_maticne(conn, "klasifikacija_artikala");
//        mKlasifikacija.setModel(new DefaultComboBoxModel(listKlasifikacija.toArray()));
////        mKategorija
//        mAkcijaTrajeOd.initPicker();
//        mAkcijaTrajeDo.initPicker();
//        mNabavnaCena.AsoftDFPEditor(NULA, new AsoftDFP("9999999999999.99"), new DecimalFormat("#,###,##0.00"));
//        mNabavnaCena.setInitalValue(new AsoftDFP(0));
//        mProdajnaCena.setInitalValue(new AsoftDFP(0));
//        mMaloprodajnaCenaBezPDV.setInitalValue(new AsoftDFP(0));
////        mMarza.setInitalValue(new AsoftDFP(0));
//        mAkcijskaCena.setInitalValue(new AsoftDFP(0));
        setLocationRelativeTo(null);

        akcijaCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (akcijaCheckBox.isSelected()) {
                    mAkcijaTrajeOd.setEditable(true);
                    mAkcijaTrajeDo.setEditable(true);
                    mAkcijskaCena.setEditable(true);
                    mAkcijskaCena.setEnabled(true);
                } else {
                    mAkcijaTrajeOd.setEditable(false);
                    mAkcijaTrajeDo.setEditable(false);
                    mAkcijskaCena.setEditable(false);
                    mAkcijskaCena.setEnabled(false);
                }
            }
        });

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
                    Main.track.error(tabela + " SQLException " + ex);
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
        jLabel5 = new javax.swing.JLabel();
        mSifra2 = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        mPLU = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        iMessage = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        mSlika = new javax.swing.JTextField();
        AttachBtn = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        mLabelSlika = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        mTip = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        mJedinicaMere = new javax.swing.JComboBox();
        mPoreskaGrupa = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        mOdeljenje = new javax.swing.JComboBox();
        mKlasifikacija = new javax.swing.JComboBox();
        mCarinskaTarifa = new javax.swing.JComboBox();
        mProizvodjac = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        mRokTrajanja = new org.asoft.library.AsoftNumericField();
        jLabel21 = new javax.swing.JLabel();
        mKalo = new org.asoft.library.AsoftNumericField();
        jLabel24 = new javax.swing.JLabel();
        jCheckBoxSklop = new javax.swing.JCheckBox();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableCene = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        UnosBtnCenovnik = new javax.swing.JButton();
        IzmenaBtn1 = new javax.swing.JButton();
        BrisanjeBtn1 = new javax.swing.JButton();
        RefreshBtn1 = new javax.swing.JButton();
        PregledBtn1 = new javax.swing.JButton();
        FirstBtn1 = new javax.swing.JButton();
        PrevBtn1 = new javax.swing.JButton();
        recordLbl1 = new javax.swing.JLabel();
        NextBtn1 = new javax.swing.JButton();
        LastBtn1 = new javax.swing.JButton();
        IzlazBtn1 = new javax.swing.JButton();
        mDatumCenovnika = new org.asoft.library.picker.AsoftHistoryDatePicker();
        jPanel14 = new javax.swing.JPanel();
        mProdajnaCenaSaPDV = new org.asoft.library.AsoftNumericField();
        mMaloprodajnaCenaBezPDV = new org.asoft.library.AsoftNumericField();
        mMaloprodajnaCena = new org.asoft.library.AsoftNumericField();
        jLabel27 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        mNabavnaCena = new org.asoft.library.AsoftNumericField();
        mPlanskaCena = new org.asoft.library.AsoftNumericField();
        mProdajnaCena = new org.asoft.library.AsoftNumericField();
        jLabel12 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableBarKod = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        UnosBtnBarkod = new javax.swing.JButton();
        IzmenaBtn2 = new javax.swing.JButton();
        BrisanjeBtn2 = new javax.swing.JButton();
        RefreshBtn2 = new javax.swing.JButton();
        PregledBtn2 = new javax.swing.JButton();
        FirstBtn2 = new javax.swing.JButton();
        PrevBtn2 = new javax.swing.JButton();
        recordLbl2 = new javax.swing.JLabel();
        NextBtn2 = new javax.swing.JButton();
        LastBtn2 = new javax.swing.JButton();
        IzlazBtn2 = new javax.swing.JButton();
        jPanel17 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        mSirina = new org.asoft.library.AsoftNumericField();
        jLabel29 = new javax.swing.JLabel();
        mDuzina = new org.asoft.library.AsoftNumericField();
        jLabel30 = new javax.swing.JLabel();
        mVisina = new org.asoft.library.AsoftNumericField();
        jPanel18 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        mNetoUJm = new org.asoft.library.AsoftNumericField();
        mBrutoUJm = new org.asoft.library.AsoftNumericField();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        mKomada = new org.asoft.library.AsoftNumericField();
        jPanel19 = new javax.swing.JPanel();
        mTezina = new org.asoft.library.AsoftNumericField();
        jLabel31 = new javax.swing.JLabel();
        mAmbalaza = new javax.swing.JComboBox<>();
        jLabel36 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        mEAN = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        mVrstaPakovanjaArtikala = new javax.swing.JComboBox<>();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableAkcije = new javax.swing.JTable();
        jPanel12 = new javax.swing.JPanel();
        UnosBtnAkcije = new javax.swing.JButton();
        IzmenaBtn3 = new javax.swing.JButton();
        BrisanjeBtn3 = new javax.swing.JButton();
        RefreshBtn3 = new javax.swing.JButton();
        PregledBtn3 = new javax.swing.JButton();
        FirstBtn3 = new javax.swing.JButton();
        PrevBtn3 = new javax.swing.JButton();
        recordLbl3 = new javax.swing.JLabel();
        NextBtn3 = new javax.swing.JButton();
        LastBtn3 = new javax.swing.JButton();
        IzlazBtn3 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        mAkcijaTrajeOd = new org.asoft.library.picker.AsoftHistoryDatePicker();
        jLabel18 = new javax.swing.JLabel();
        mAkcijaTrajeDo = new org.asoft.library.picker.AsoftHistoryDatePicker();
        jLabel19 = new javax.swing.JLabel();
        mAkcijskaCena = new org.asoft.library.AsoftNumericField();
        jPanel21 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        akcijaCheckBox = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        UnosBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_stare/new.png"))); // NOI18N
        UnosBtn.setText("Unos");
        UnosBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UnosBtnActionPerformed(evt);
            }
        });

        IzmenaBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_stare/edit.png"))); // NOI18N
        IzmenaBtn.setText("Izmena");
        IzmenaBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IzmenaBtnActionPerformed(evt);
            }
        });

        BrisanjeBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_stare/delete_1.png"))); // NOI18N
        BrisanjeBtn.setText("Brisanje");
        BrisanjeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BrisanjeBtnActionPerformed(evt);
            }
        });

        RefreshBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_stare/refresh.png"))); // NOI18N
        RefreshBtn.setText("Osveži");
        RefreshBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefreshBtnActionPerformed(evt);
            }
        });

        PregledBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_stare/fileprint.png"))); // NOI18N
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

        IzlazBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/Button-Close-icon-24.png"))); // NOI18N
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
                .addComponent(UnosBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(IzmenaBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(17, 17, 17)
                .addComponent(BrisanjeBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PregledBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(12, 12, 12)
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
                .addComponent(UnosBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                .addComponent(IzmenaBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BrisanjeBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(PregledBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(FirstBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(PrevBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(LastBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(RefreshBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(IzlazBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                .addComponent(recordLbl, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(NextBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        jLabel5.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel5.setText("Šifra2:");

        mSifra2.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        mSifra2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mSifra2ActionPerformed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel20.setText("PLU:");

        mPLU.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        mPLU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mPLUActionPerformed(evt);
            }
        });

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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(mSifra2, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(mPLU, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(242, 242, 242)
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
                .addComponent(lTabela, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
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
                                    .addComponent(mSifra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5)
                                    .addComponent(mSifra2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel20)
                                    .addComponent(mPLU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
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

        mSlika.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        mSlika.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mSlikaActionPerformed(evt);
            }
        });

        AttachBtn.setText("Attach");
        AttachBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AttachBtnActionPerformed(evt);
            }
        });

        mLabelSlika.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jScrollPane4.setViewportView(mLabelSlika);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(AttachBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 203, Short.MAX_VALUE))
                    .addComponent(mSlika))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(mSlika, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54)
                        .addComponent(AttachBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 100, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel16.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel6.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel6.setText("Tip artikla:");

        mTip.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        mTip.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel7.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel7.setText("Jedinica mere:");

        mJedinicaMere.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        mJedinicaMere.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        mJedinicaMere.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mJedinicaMereActionPerformed(evt);
            }
        });

        mPoreskaGrupa.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        mPoreskaGrupa.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel8.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel8.setText("Poreska grupa:");

        jLabel9.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel9.setText("Odeljenje:");

        mOdeljenje.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        mOdeljenje.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        mKlasifikacija.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        mKlasifikacija.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        mCarinskaTarifa.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        mCarinskaTarifa.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        mProizvodjac.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        mProizvodjac.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel10.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel10.setText("Klasifikacija:");

        jLabel22.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel22.setText("Carinska tarifa:");

        jLabel23.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel23.setText("Kupac / Proizvođač:");

        jLabel11.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel11.setText("Rok trajanja (dana):");

        mRokTrajanja.setText("0.00");
        mRokTrajanja.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        mRokTrajanja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mRokTrajanjaActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel21.setText("Procenat kala %:");

        mKalo.setText("0.00");
        mKalo.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N

        jLabel24.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel24.setText("Sklop:");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel16Layout.createSequentialGroup()
                            .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel6)
                                .addComponent(jLabel7)
                                .addComponent(jLabel8)
                                .addComponent(jLabel9)
                                .addComponent(jLabel10)
                                .addComponent(jLabel22)
                                .addComponent(jLabel23))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(mTip, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(mJedinicaMere, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(mPoreskaGrupa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(mOdeljenje, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(mKlasifikacija, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(mCarinskaTarifa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(mProizvodjac, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel16Layout.createSequentialGroup()
                            .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel21)
                                .addComponent(jLabel11))
                            .addGap(41, 41, 41)
                            .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(mRokTrajanja, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(mKalo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addGap(140, 140, 140)
                        .addComponent(jCheckBoxSklop)))
                .addGap(0, 12, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(mTip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(mJedinicaMere, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(mPoreskaGrupa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(mOdeljenje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(mKlasifikacija, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(mCarinskaTarifa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(mProizvodjac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(mRokTrajanja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21)
                    .addComponent(mKalo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jCheckBoxSklop)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addContainerGap(145, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Artikli", jPanel5);

        jTableCene.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Sifra", "Datum", "Vazi od", "Nabavna", "Planska", "Prodajna", "Prodajna sa PDV", "Maloprodajna bez PDV", "Maloprodajna"
            }
        ));
        jScrollPane3.setViewportView(jTableCene);

        jPanel10.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        UnosBtnCenovnik.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/Files-New-File-icon.png"))); // NOI18N
        UnosBtnCenovnik.setText("Unos");
        UnosBtnCenovnik.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UnosBtnCenovnikActionPerformed(evt);
            }
        });

        IzmenaBtn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/Actions-document-edit-icon.png"))); // NOI18N
        IzmenaBtn1.setText("Izmena");
        IzmenaBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IzmenaBtn1ActionPerformed(evt);
            }
        });

        BrisanjeBtn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/delete-file-icon.png"))); // NOI18N
        BrisanjeBtn1.setText("Brisanje");
        BrisanjeBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BrisanjeBtn1ActionPerformed(evt);
            }
        });

        RefreshBtn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/Refresh-icon.png"))); // NOI18N
        RefreshBtn1.setText("Osveži");
        RefreshBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefreshBtn1ActionPerformed(evt);
            }
        });

        PregledBtn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/print-icon.png"))); // NOI18N
        PregledBtn1.setText("Pregled");
        PregledBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PregledBtn1ActionPerformed(evt);
            }
        });

        FirstBtn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/Actions-go-first-view-icon-24.png"))); // NOI18N
        FirstBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FirstBtn1ActionPerformed(evt);
            }
        });

        PrevBtn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/Actions-go-previous-view-icon-24.png"))); // NOI18N
        PrevBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrevBtn1ActionPerformed(evt);
            }
        });

        recordLbl1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        recordLbl1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        recordLbl1.setText("0");
        recordLbl1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        recordLbl1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        NextBtn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/Actions-go-next-view-icon-24.png"))); // NOI18N
        NextBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NextBtn1ActionPerformed(evt);
            }
        });

        LastBtn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/Actions-go-last-view-icon-24.png"))); // NOI18N
        LastBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LastBtn1ActionPerformed(evt);
            }
        });

        IzlazBtn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/Action-exit-icon.png"))); // NOI18N
        IzlazBtn1.setText("Izlaz");
        IzlazBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IzlazBtn1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(UnosBtnCenovnik, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(IzmenaBtn1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BrisanjeBtn1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(12, 12, 12)
                .addComponent(PregledBtn1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FirstBtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PrevBtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(recordLbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(NextBtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LastBtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(RefreshBtn1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(IzlazBtn1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(FirstBtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PrevBtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LastBtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(RefreshBtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(IzlazBtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(UnosBtnCenovnik, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(IzmenaBtn1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BrisanjeBtn1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(PregledBtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(recordLbl1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(NextBtn1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel14.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        mProdajnaCenaSaPDV.setText("0.00");
        mProdajnaCenaSaPDV.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N

        mMaloprodajnaCenaBezPDV.setText("0.00");
        mMaloprodajnaCenaBezPDV.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N

        mMaloprodajnaCena.setText("0.00");
        mMaloprodajnaCena.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N

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
                .addContainerGap(27, Short.MAX_VALUE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mMaloprodajnaCena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28)))
        );

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
                .addGap(0, 13, Short.MAX_VALUE)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(18, 18, 18)
                        .addComponent(mNabavnaCena, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addGap(18, 18, 18)
                        .addComponent(mPlanskaCena, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addGap(18, 18, 18)
                        .addComponent(mProdajnaCena, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))))
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
                    .addComponent(jLabel26)))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(mDatumCenovnika, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1157, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(mDatumCenovnika, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Cene", jPanel6);

        jTableBarKod.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Sifra", "Vrsta pakov", "Ean", "Neto u jm", "Bruto u jm", "Širina", "Dužina", "Visina", "Komada", "Ambalaža", "Aktivan"
            }
        ));
        jScrollPane1.setViewportView(jTableBarKod);

        jPanel11.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        UnosBtnBarkod.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/Files-New-File-icon.png"))); // NOI18N
        UnosBtnBarkod.setText("Unos");
        UnosBtnBarkod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UnosBtnBarkodActionPerformed(evt);
            }
        });

        IzmenaBtn2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/Actions-document-edit-icon.png"))); // NOI18N
        IzmenaBtn2.setText("Izmena");
        IzmenaBtn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IzmenaBtn2ActionPerformed(evt);
            }
        });

        BrisanjeBtn2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/delete-file-icon.png"))); // NOI18N
        BrisanjeBtn2.setText("Brisanje");
        BrisanjeBtn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BrisanjeBtn2ActionPerformed(evt);
            }
        });

        RefreshBtn2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/Refresh-icon.png"))); // NOI18N
        RefreshBtn2.setText("Osveži");
        RefreshBtn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefreshBtn2ActionPerformed(evt);
            }
        });

        PregledBtn2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/print-icon.png"))); // NOI18N
        PregledBtn2.setText("Pregled");
        PregledBtn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PregledBtn2ActionPerformed(evt);
            }
        });

        FirstBtn2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/Actions-go-first-view-icon-24.png"))); // NOI18N
        FirstBtn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FirstBtn2ActionPerformed(evt);
            }
        });

        PrevBtn2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/Actions-go-previous-view-icon-24.png"))); // NOI18N
        PrevBtn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrevBtn2ActionPerformed(evt);
            }
        });

        recordLbl2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        recordLbl2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        recordLbl2.setText("0");
        recordLbl2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        recordLbl2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        NextBtn2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/Actions-go-next-view-icon-24.png"))); // NOI18N
        NextBtn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NextBtn2ActionPerformed(evt);
            }
        });

        LastBtn2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/Actions-go-last-view-icon-24.png"))); // NOI18N
        LastBtn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LastBtn2ActionPerformed(evt);
            }
        });

        IzlazBtn2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/Action-exit-icon.png"))); // NOI18N
        IzlazBtn2.setText("Izlaz");
        IzlazBtn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IzlazBtn2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(UnosBtnBarkod, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(IzmenaBtn2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BrisanjeBtn2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(12, 12, 12)
                .addComponent(PregledBtn2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FirstBtn2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PrevBtn2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(recordLbl2, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(NextBtn2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LastBtn2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(RefreshBtn2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(IzlazBtn2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(FirstBtn2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PrevBtn2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LastBtn2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(RefreshBtn2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(IzlazBtn2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(UnosBtnBarkod, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(IzmenaBtn2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BrisanjeBtn2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(PregledBtn2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(recordLbl2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(NextBtn2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
                .addContainerGap(36, Short.MAX_VALUE)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel30)
                    .addComponent(jLabel14)
                    .addComponent(jLabel29))
                .addGap(27, 27, 27)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                        .addComponent(mSirina, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                        .addComponent(mDuzina, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                        .addComponent(mVisina, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mSirina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mDuzina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29))
                .addGap(12, 12, 12)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mVisina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(jLabel34)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(mKomada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel33)
                            .addComponent(jLabel32))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(mNetoUJm, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                            .addComponent(mBrutoUJm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(mNetoUJm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(mBrutoUJm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(mKomada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel19.setBackground(new java.awt.Color(255, 102, 102));
        jPanel19.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        mTezina.setText("0.00");
        mTezina.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N

        jLabel31.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel31.setText("Težina:");

        mAmbalaza.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        mAmbalaza.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

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
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel36, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(mAmbalaza, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addComponent(jLabel36)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(mAmbalaza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mTezina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31))
                .addContainerGap())
        );

        jPanel20.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        mEAN.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N

        jLabel35.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel35.setText("EAN kod:");

        jLabel13.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel13.setText("Vrsta pakovanja:");

        mVrstaPakovanjaArtikala.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        mVrstaPakovanjaArtikala.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addComponent(jLabel35))
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel13)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(mEAN)
                    .addComponent(mVrstaPakovanjaArtikala, 0, 148, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addGap(0, 8, Short.MAX_VALUE)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mVrstaPakovanjaArtikala, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(mEAN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, 108, Short.MAX_VALUE)
                    .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel8Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jPanel17, jPanel18, jPanel19, jPanel20});

        jTabbedPane1.addTab("Barkod", jPanel8);

        jTableAkcije.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Sifra", "Akcijska cena", "Akcija od", "Akcija do", "Aktivan"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTableAkcije);

        jPanel12.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        UnosBtnAkcije.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/Files-New-File-icon.png"))); // NOI18N
        UnosBtnAkcije.setText("Unos");
        UnosBtnAkcije.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UnosBtnAkcijeActionPerformed(evt);
            }
        });

        IzmenaBtn3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/Actions-document-edit-icon.png"))); // NOI18N
        IzmenaBtn3.setText("Izmena");
        IzmenaBtn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IzmenaBtn3ActionPerformed(evt);
            }
        });

        BrisanjeBtn3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/delete-file-icon.png"))); // NOI18N
        BrisanjeBtn3.setText("Brisanje");
        BrisanjeBtn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BrisanjeBtn3ActionPerformed(evt);
            }
        });

        RefreshBtn3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/Refresh-icon.png"))); // NOI18N
        RefreshBtn3.setText("Osveži");
        RefreshBtn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefreshBtn3ActionPerformed(evt);
            }
        });

        PregledBtn3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/print-icon.png"))); // NOI18N
        PregledBtn3.setText("Pregled");
        PregledBtn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PregledBtn3ActionPerformed(evt);
            }
        });

        FirstBtn3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/Actions-go-first-view-icon-24.png"))); // NOI18N
        FirstBtn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FirstBtn3ActionPerformed(evt);
            }
        });

        PrevBtn3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/Actions-go-previous-view-icon-24.png"))); // NOI18N
        PrevBtn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrevBtn3ActionPerformed(evt);
            }
        });

        recordLbl3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        recordLbl3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        recordLbl3.setText("0");
        recordLbl3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        recordLbl3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        NextBtn3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/Actions-go-next-view-icon-24.png"))); // NOI18N
        NextBtn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NextBtn3ActionPerformed(evt);
            }
        });

        LastBtn3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/Actions-go-last-view-icon-24.png"))); // NOI18N
        LastBtn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LastBtn3ActionPerformed(evt);
            }
        });

        IzlazBtn3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/Action-exit-icon.png"))); // NOI18N
        IzlazBtn3.setText("Izlaz");
        IzlazBtn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IzlazBtn3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(UnosBtnAkcije, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(IzmenaBtn3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BrisanjeBtn3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(12, 12, 12)
                .addComponent(PregledBtn3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FirstBtn3, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PrevBtn3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(recordLbl3, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(NextBtn3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LastBtn3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(RefreshBtn3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(IzlazBtn3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(FirstBtn3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PrevBtn3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LastBtn3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(RefreshBtn3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(IzlazBtn3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(UnosBtnAkcije, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(IzmenaBtn3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BrisanjeBtn3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(PregledBtn3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(recordLbl3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(NextBtn3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel17.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel17.setText("Akcija traje od: ");

        jLabel18.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel18.setText("do: ");

        jLabel19.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel19.setText("Akcijska cena: ");

        mAkcijskaCena.setText("0.00");
        mAkcijskaCena.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(mAkcijaTrajeOd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel18)
                .addGap(12, 12, 12)
                .addComponent(mAkcijaTrajeDo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jLabel19)
                .addGap(18, 18, 18)
                .addComponent(mAkcijskaCena, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(55, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mAkcijaTrajeOd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(mAkcijskaCena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(mAkcijaTrajeDo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel18))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel17)))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jPanel21.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel16.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel16.setText("Akcija uključena:");

        akcijaCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                akcijaCheckBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addContainerGap(68, Short.MAX_VALUE)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(akcijaCheckBox)
                .addContainerGap())
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(akcijaCheckBox)
                    .addComponent(jLabel16))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1157, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Akcijske cene", jPanel9);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1)
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
            Main.track.error(tabela + " SQLException " + ex);
            iMessage.setText(ex.toString());
//                Logger.getLogger(SearchWithTab.class.getName()).log(Level.SEVERE, null, ex);
        }
//        }
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
                    java.util.logging.Logger.getLogger(ArtikliNew.class.getName()).log(Level.SEVERE, null, ex);
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
        JDialog.setDefaultLookAndFeelDecorated(true);
        Object stringArray[] = {"Da", "Ne"};

        Icon blueIcon = new ImageIcon("yourFile.gif");
        int response = JOptionPane.showOptionDialog(null, "Ukoliko ste pravili izmene, podatke treba snimiti ... \n Da li želite izlaz?", "Potvrda",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, blueIcon, stringArray,
                stringArray[0]);
        if (response == JOptionPane.NO_OPTION) {
            return;
        } else if (response == JOptionPane.YES_OPTION) {
        } else if (response == JOptionPane.CLOSED_OPTION) {
            return;
        }

        upisiZaKraj();
        this.dispose();

    }//GEN-LAST:event_IzlazBtnActionPerformed

    private void akcijaCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_akcijaCheckBoxActionPerformed
        // TODO add your handling code here:
//        if (akcijaCheckBox.isSelected()) {
//            mAkcijaTrajeOd.setEditable(true);
//            mAkcijaTrajeDo.setEditable(true);
//            mAkcijskaCena.setEditable(true);
//        }
//        else
//        {
//            mAkcijaTrajeOd.setEditable(false);
//            mAkcijaTrajeDo.setEditable(false);
//            mAkcijskaCena.setEditable(false);
//            
//        }
    }//GEN-LAST:event_akcijaCheckBoxActionPerformed

    private void mSifra2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mSifra2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mSifra2ActionPerformed

    private void mPLUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mPLUActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mPLUActionPerformed

    private void mNazivActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mNazivActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mNazivActionPerformed

    private void UnosBtnCenovnikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UnosBtnCenovnikActionPerformed
        try {
            // TODO add your handling code here:

            Cenovnik cenovnik = new Cenovnik();
//                cenovnik.setId(rs.getInt("id"));
//                cenovnik.setArtikalId(rs.getInt("artikal_id"));
//                cenovnik.setSifra(rs.getString("artikal_sifra"));

            cenovnik.setDatum(mDatumCenovnika.getSQLDate());
            cenovnik.setNabavnaCena(mNabavnaCena.getValueDFP().BigDecimalValue());
            cenovnik.setPlanskaCena(mNabavnaCena.getValueDFP().BigDecimalValue());
            cenovnik.setProdajnaCena(mNabavnaCena.getValueDFP().BigDecimalValue());
            cenovnik.setMaloprodajnaCenaBezPdv(mNabavnaCena.getValueDFP().BigDecimalValue());
            cenovnik.setMaloprodajnaCena(mNabavnaCena.getValueDFP().BigDecimalValue());

//            cenovnik.setAktivan(vratiJedanIliNula(mAktivan.isSelected()));
//                pops.setSvojstvo(rs.getString("svojstvo"));
//                pops.setDekret(rs.getString("dekret"));
//                pops.setPocetakSluzbeOd(rs.getDate("pocetak_sluzbe_od"));
//                pops.setTrajanjeSluzbeDo(rs.getDate("trajanje_sluzbe_do"));
//                pops.setUserId(rs.getInt("user_id"));
//                pops.setRbr(popsArray.size() + 1);
//                pops.setJmbg(mJmbg.getText());
//                mDatumCenovnika.setDate(rs.getDate("datum"));
//            mNabavnaCena.setValue(new AsoftDFP(rs.getDouble("nabavna_cena")));
//            mPlanskaCena.setValue(new AsoftDFP(rs.getDouble("planska_cena")));
//            mProdajnaCena.setValue(new AsoftDFP(rs.getDouble("prodajna_cena")));
//            mProdajnaCenaSaPDV.setValue(new AsoftDFP(rs.getDouble("prodajna_cena_bez_pdv")));
//            mMaloprodajnaCena.setValue(new AsoftDFP(rs.getDouble("maloprodajna_cena")));
//            mMaloprodajnaCenaBezPDV.setValue(new AsoftDFP(rs.getDouble("maloprodajna_cena_bez_pdv")));
//                mPOPSMestoSluzbe.setSelectedItem(rs.getString("mesto_sluzbe"));
//                mPOPSNazivUstanove.setSelectedItem(rs.getString("naziv_ustanove"));
//                mPOPSNazivZupe.setSelectedItem(rs.getString("naziv_zupe"));
//                mPOPSSvojstvo.setSelectedItem(rs.getString("svojstvo"));
//                mPOPSDekret.setText(rs.getString("dekret"));
//            try {
//                mDatumCenovnika.setDate(rs.getDate("datum"));
//            } catch (ParseException ex) {
////                    Logger.getLogger(ArtikliNew.class.getName()).log(Level.SEVERE, null, ex);
//            }

            cenovnikArray.add(cenovnik);

        } catch (ParseException ex) {
            Main.track.error(tabela + " SQLException " + ex);
            iMessage.setText(ex.toString());
        }

    }//GEN-LAST:event_UnosBtnCenovnikActionPerformed

    private void IzmenaBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IzmenaBtn1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IzmenaBtn1ActionPerformed

    private void BrisanjeBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BrisanjeBtn1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BrisanjeBtn1ActionPerformed

    private void RefreshBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshBtn1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RefreshBtn1ActionPerformed

    private void PregledBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PregledBtn1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PregledBtn1ActionPerformed

    private void FirstBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FirstBtn1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FirstBtn1ActionPerformed

    private void PrevBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PrevBtn1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PrevBtn1ActionPerformed

    private void NextBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NextBtn1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NextBtn1ActionPerformed

    private void LastBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LastBtn1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LastBtn1ActionPerformed

    private void IzlazBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IzlazBtn1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IzlazBtn1ActionPerformed

    private void UnosBtnBarkodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UnosBtnBarkodActionPerformed
        // TODO add your handling code here:
        // puni arrayCenovnik
//                        stmt.setInt(1, generatedKey);
//                stmt.setString(2, u.getArtikalSifra());
//                stmt.setString(3, u.getTippak());
//                stmt.setString(4, u.getEan());
//                stmt.setString(5, u.getNaziv());
//                stmt.setString(6, u.getJm());
//                stmt.setBigDecimal(7, u.getNetojm());
//                stmt.setBigDecimal(8, u.getBrutojm());
//                stmt.setBigDecimal(9, u.getSirina());
//                stmt.setBigDecimal(10, u.getVisina());
//                stmt.setBigDecimal(11, u.getDuzina());
//                stmt.setBigDecimal(12, u.getKomada());
//                stmt.setInt(13, vratiJedanIliNula(mAktivan.isSelected()));
//                stmt.setString(14, u.getSifraAmbalaze());
                

//        ArtikliPakovanja ap = new ArtikliPakovanja();
//        ap.setId(rs.getInt("id"));
//        ap.setArtikalId(rs.getInt("artikal_id"));
//        ap.setSifra(rs.getString("artikal_sifra"));
//        ap.setDatum(rs.getDate("datum"));
//        ap.setNabavnaCena(rs.getBigDecimal("nabavna_cena"));
//        ap.setPlanskaCena(rs.getBigDecimal("planska_cena"));
//        ap.setProdajnaCena(rs.getBigDecimal("prodajna_cena"));
//        ap.setMaloprodajnaCenaBezPdv(rs.getBigDecimal("maloprodajna_cena_bez_pdv"));
//        ap.setMaloprodajnaCena(rs.getBigDecimal("maloprodajna_cena"));
//        ap.setAktivan(rs.getBoolean("aktivan"));
//
////                pops.setSvojstvo(rs.getString("svojstvo"));
////                pops.setDekret(rs.getString("dekret"));
////                pops.setPocetakSluzbeOd(rs.getDate("pocetak_sluzbe_od"));
////                pops.setTrajanjeSluzbeDo(rs.getDate("trajanje_sluzbe_do"));
////                pops.setUserId(rs.getInt("user_id"));
////                pops.setRbr(popsArray.size() + 1);
////                pops.setJmbg(mJmbg.getText());
////                mDatumCenovnika.setDate(rs.getDate("datum"));
//        mNabavnaCena.setValue(new AsoftDFP(rs.getDouble("nabavna_cena")));
//        mPlanskaCena.setValue(new AsoftDFP(rs.getDouble("planska_cena")));
//        mProdajnaCena.setValue(new AsoftDFP(rs.getDouble("prodajna_cena")));
//        mProdajnaCenaSaPDV.setValue(new AsoftDFP(rs.getDouble("prodajna_cena_bez_pdv")));
//        mMaloprodajnaCena.setValue(new AsoftDFP(rs.getDouble("maloprodajna_cena")));
//        mMaloprodajnaCenaBezPDV.setValue(new AsoftDFP(rs.getDouble("maloprodajna_cena_bez_pdv")));

//                mPOPSMestoSluzbe.setSelectedItem(rs.getString("mesto_sluzbe"));
//                mPOPSNazivUstanove.setSelectedItem(rs.getString("naziv_ustanove"));
//                mPOPSNazivZupe.setSelectedItem(rs.getString("naziv_zupe"));
//                mPOPSSvojstvo.setSelectedItem(rs.getString("svojstvo"));
//                mPOPSDekret.setText(rs.getString("dekret"));
//        try {
//            mDatumCenovnika.setDate(rs.getDate("datum"));
//        } catch (ParseException ex) {
////                    Logger.getLogger(ArtikliNew.class.getName()).log(Level.SEVERE, null, ex);
//        }

//        cenovnikArray.add(cenovnik);
    }//GEN-LAST:event_UnosBtnBarkodActionPerformed

    private void IzmenaBtn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IzmenaBtn2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IzmenaBtn2ActionPerformed

    private void BrisanjeBtn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BrisanjeBtn2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BrisanjeBtn2ActionPerformed

    private void RefreshBtn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshBtn2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RefreshBtn2ActionPerformed

    private void PregledBtn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PregledBtn2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PregledBtn2ActionPerformed

    private void FirstBtn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FirstBtn2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FirstBtn2ActionPerformed

    private void PrevBtn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PrevBtn2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PrevBtn2ActionPerformed

    private void NextBtn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NextBtn2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NextBtn2ActionPerformed

    private void LastBtn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LastBtn2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LastBtn2ActionPerformed

    private void IzlazBtn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IzlazBtn2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IzlazBtn2ActionPerformed

    private void UnosBtnAkcijeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UnosBtnAkcijeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_UnosBtnAkcijeActionPerformed

    private void IzmenaBtn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IzmenaBtn3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IzmenaBtn3ActionPerformed

    private void BrisanjeBtn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BrisanjeBtn3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BrisanjeBtn3ActionPerformed

    private void RefreshBtn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshBtn3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RefreshBtn3ActionPerformed

    private void PregledBtn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PregledBtn3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PregledBtn3ActionPerformed

    private void FirstBtn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FirstBtn3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FirstBtn3ActionPerformed

    private void PrevBtn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PrevBtn3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PrevBtn3ActionPerformed

    private void NextBtn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NextBtn3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NextBtn3ActionPerformed

    private void LastBtn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LastBtn3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LastBtn3ActionPerformed

    private void IzlazBtn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IzlazBtn3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IzlazBtn3ActionPerformed

    private void mRokTrajanjaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mRokTrajanjaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mRokTrajanjaActionPerformed

    private void mSirinaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mSirinaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mSirinaActionPerformed

    private void mNetoUJmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mNetoUJmActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mNetoUJmActionPerformed

    private void mBrutoUJmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mBrutoUJmActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mBrutoUJmActionPerformed

    private void mKomadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mKomadaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mKomadaActionPerformed

    private void mSlikaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mSlikaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mSlikaActionPerformed

    private void AttachBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AttachBtnActionPerformed
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
        String filename = f.getAbsolutePath();
        filename = filename.replace("\\", "\\\\");
        mSlika.setText(filename);
        ImageIcon icon = new ImageIcon(filename);
        mLabelSlika.setIcon(icon);
    }//GEN-LAST:event_AttachBtnActionPerformed

    private void mJedinicaMereActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mJedinicaMereActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mJedinicaMereActionPerformed

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
            Main.track.error(tabela + " SQLException " + ex);
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
            Main.track.error(tabela + " SQLException " + ex);
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
            java.util.logging.Logger.getLogger(ArtikliNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ArtikliNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ArtikliNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ArtikliNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new ArtikliNew().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AttachBtn;
    private javax.swing.JButton BrisanjeBtn;
    private javax.swing.JButton BrisanjeBtn1;
    private javax.swing.JButton BrisanjeBtn2;
    private javax.swing.JButton BrisanjeBtn3;
    private javax.swing.JButton FirstBtn;
    private javax.swing.JButton FirstBtn1;
    private javax.swing.JButton FirstBtn2;
    private javax.swing.JButton FirstBtn3;
    private javax.swing.JButton IzlazBtn;
    private javax.swing.JButton IzlazBtn1;
    private javax.swing.JButton IzlazBtn2;
    private javax.swing.JButton IzlazBtn3;
    private javax.swing.JButton IzmenaBtn;
    private javax.swing.JButton IzmenaBtn1;
    private javax.swing.JButton IzmenaBtn2;
    private javax.swing.JButton IzmenaBtn3;
    private javax.swing.JButton LastBtn;
    private javax.swing.JButton LastBtn1;
    private javax.swing.JButton LastBtn2;
    private javax.swing.JButton LastBtn3;
    private javax.swing.JButton NextBtn;
    private javax.swing.JButton NextBtn1;
    private javax.swing.JButton NextBtn2;
    private javax.swing.JButton NextBtn3;
    private javax.swing.JButton PregledBtn;
    private javax.swing.JButton PregledBtn1;
    private javax.swing.JButton PregledBtn2;
    private javax.swing.JButton PregledBtn3;
    private javax.swing.JButton PrevBtn;
    private javax.swing.JButton PrevBtn1;
    private javax.swing.JButton PrevBtn2;
    private javax.swing.JButton PrevBtn3;
    private javax.swing.JButton RefreshBtn;
    private javax.swing.JButton RefreshBtn1;
    private javax.swing.JButton RefreshBtn2;
    private javax.swing.JButton RefreshBtn3;
    private javax.swing.JButton UnosBtn;
    private javax.swing.JButton UnosBtnAkcije;
    private javax.swing.JButton UnosBtnBarkod;
    private javax.swing.JButton UnosBtnCenovnik;
    private javax.swing.JCheckBox akcijaCheckBox;
    private javax.swing.JLabel iMessage;
    private javax.swing.JCheckBox jCheckBoxSklop;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
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
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableAkcije;
    private javax.swing.JTable jTableBarKod;
    private javax.swing.JTable jTableCene;
    private javax.swing.JLabel lTabela;
    private org.asoft.library.picker.AsoftHistoryDatePicker mAkcijaTrajeDo;
    private org.asoft.library.picker.AsoftHistoryDatePicker mAkcijaTrajeOd;
    private org.asoft.library.AsoftNumericField mAkcijskaCena;
    private javax.swing.JCheckBox mAktivan;
    private javax.swing.JComboBox<String> mAmbalaza;
    private org.asoft.library.AsoftNumericField mBrutoUJm;
    private javax.swing.JComboBox mCarinskaTarifa;
    private org.asoft.library.picker.AsoftHistoryDatePicker mDatumCenovnika;
    private org.asoft.library.AsoftNumericField mDuzina;
    private javax.swing.JTextField mEAN;
    private javax.swing.JTextField mId;
    private javax.swing.JComboBox mJedinicaMere;
    private org.asoft.library.AsoftNumericField mKalo;
    private javax.swing.JComboBox mKlasifikacija;
    private org.asoft.library.AsoftNumericField mKomada;
    private javax.swing.JLabel mLabelSlika;
    private org.asoft.library.AsoftNumericField mMaloprodajnaCena;
    private org.asoft.library.AsoftNumericField mMaloprodajnaCenaBezPDV;
    private org.asoft.library.AsoftNumericField mNabavnaCena;
    private javax.swing.JTextField mNaziv;
    private org.asoft.library.AsoftNumericField mNetoUJm;
    private javax.swing.JComboBox mOdeljenje;
    private javax.swing.JTextField mPLU;
    private org.asoft.library.AsoftNumericField mPlanskaCena;
    private javax.swing.JComboBox mPoreskaGrupa;
    private org.asoft.library.AsoftNumericField mProdajnaCena;
    private org.asoft.library.AsoftNumericField mProdajnaCenaSaPDV;
    private javax.swing.JComboBox mProizvodjac;
    private org.asoft.library.AsoftNumericField mRokTrajanja;
    private javax.swing.JTextField mSifra;
    private javax.swing.JTextField mSifra2;
    private org.asoft.library.AsoftNumericField mSirina;
    private javax.swing.JTextField mSlika;
    private org.asoft.library.AsoftNumericField mTezina;
    private javax.swing.JComboBox mTip;
    private org.asoft.library.AsoftNumericField mVisina;
    private javax.swing.JComboBox<String> mVrstaPakovanjaArtikala;
    private javax.swing.JLabel recordLbl;
    private javax.swing.JLabel recordLbl1;
    private javax.swing.JLabel recordLbl2;
    private javax.swing.JLabel recordLbl3;
    // End of variables declaration//GEN-END:variables

    private int vratiJedanIliNula(boolean selected) {
        if (selected) {
            return 1;
        } else {
            return 0;
        }
    }

    private void nuliranjeForme0() {

        mLabelSlika.setIcon(null);
        mDatumCenovnika.initPicker("mDatumCenovnika", null);
        mAkcijaTrajeOd.initPicker("mAkcijaTrajeOd", null);
        mAkcijaTrajeDo.initPicker("mAkcijaTrajeDo", null);

        try {
            mDatumCenovnika.setDate(AsoftDate.EMPTY_DATE);
            mAkcijaTrajeOd.setDate(AsoftDate.EMPTY_DATE);
            mAkcijaTrajeDo.setDate(AsoftDate.EMPTY_DATE);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, ex);
            Main.track.error(tabela + " SQLException " + ex);
        }

        mNaziv.setText("");
        mSifra.setText("");
        mSifra2.setText("");
        mPLU.setText("");

        List<String> listTipArtikla = puniComboMaticni.puni_maticne(conn, "tip_artikla");
        mTip.setModel(new DefaultComboBoxModel(listTipArtikla.toArray()));

        List<String> listJediniceMere = puniComboMaticni.puni_maticne(conn, "jedinica_mere");
        mJedinicaMere.setModel(new DefaultComboBoxModel(listJediniceMere.toArray()));

        List<String> listPoreskaGrupa = puniComboMaticni.puni_maticne(conn, "poreska_tarifa");
        mPoreskaGrupa.setModel(new DefaultComboBoxModel(listPoreskaGrupa.toArray()));

        List<String> listOdeljenje = puniComboMaticni.puni_maticne(conn, "odeljenje");
        mOdeljenje.setModel(new DefaultComboBoxModel(listOdeljenje.toArray()));

        List<String> listKlasifikacija = puniComboMaticni.puni_maticne(conn, "klasifikacija_artikala");
        mKlasifikacija.setModel(new DefaultComboBoxModel(listKlasifikacija.toArray()));

        List<String> listCarinskaTarifa = puniComboMaticni.puni_maticne(conn, "carinska_tarifa_artikala");
        mCarinskaTarifa.setModel(new DefaultComboBoxModel(listCarinskaTarifa.toArray()));

        List<String> listPartneri = puniComboMaticni.puni_maticne(conn, "partneri");
        mProizvodjac.setModel(new DefaultComboBoxModel(listPartneri.toArray()));

        mSlika.setText(service.uzmiParametar.uzmiParametar(conn, "artikal_icon"));

//        proizvodjac/kupac
//        List<String> listCarinskaTarifa = puniComboMaticni.puni_maticne(conn, "carinska_tarifa_artikala");
//        mCarinskaTarifa.setModel(new DefaultComboBoxModel(listCarinskaTarifa.toArray())); 

        mRokTrajanja.setValue(NULA);
        mKalo.setValue(NULA);
        jCheckBoxSklop.setSelected(false);

        mNabavnaCena.setValue(NULA);
        mPlanskaCena.setValue(NULA);
        mProdajnaCena.setValue(NULA);
        mProdajnaCenaSaPDV.setValue(NULA);
        mMaloprodajnaCenaBezPDV.setValue(NULA);
        mMaloprodajnaCenaBezPDV.setValue(NULA);

        List<String> listVrstaPakovanjaArtikala = puniComboMaticni.puni_maticne(conn, "vrste_pakovanja_artikala");
        mVrstaPakovanjaArtikala.setModel(new DefaultComboBoxModel(listVrstaPakovanjaArtikala.toArray()));

        mEAN.setText("");

        List<String> listAmbalaza = puniComboMaticni.puni_maticne(conn, "ambalaza");
        mAmbalaza.setModel(new DefaultComboBoxModel(listAmbalaza.toArray()));

        mSirina.setValue(NULA);
        mDuzina.setValue(NULA);
        mVisina.setValue(NULA);
        mTezina.setValue(NULA);

        mNetoUJm.setValue(NULA);
        mBrutoUJm.setValue(NULA);
        mKomada.setValue(NULA);

        akcijaCheckBox.setSelected(false);
        mAkcijskaCena.setValue(NULA);

        mAkcijskaCena.setEnabled(false);
        mAkcijaTrajeOd.setEnabled(false);
        mAkcijaTrajeDo.setEnabled(false);

    }

    private void napuniEkran(String id) {

        // iniciraj combo
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

            System.out.println(sqlQuery);
            rs = stmt.executeQuery(sqlQuery);

            while (rs.next()) {

                //******* citanje iz baze ******* //
//                if (rs.getString("photo") != null && !rs.getString("photo").isEmpty()) {
//                            byte[] img = rs.getBytes("photo");
//                            ImageIcon image =new ImageIcon(img);
//                            Image im = image.getImage();
//                            Image myImg = im.getScaledInstance(mLabelSlika.getWidth(), mLabelSlika.getHeight(), Image.SCALE_SMOOTH);
//                            ImageIcon icon =new ImageIcon(myImg);
//                            mLabelSlika.setIcon(icon); 
//                }
                if (rs.getString("slika") != null && !rs.getString("slika").isEmpty()) {

                    String filename = rs.getString("slika");
                    System.out.println("Slika " + filename);

//                    String[] array = filename.split("\\\\"); 
//                    int i;String token = filename;
//                    
//                    for(i = 0;i<array.length;i++) {
//                        token = token+array[i]+"\\";
//                    }
                    mSlika.setText(filename);
                    ImageIcon icon = new ImageIcon(filename);
                    mLabelSlika.setIcon(icon);
                }

                Integer mAktivann = rs.getInt("artikli.aktivan");
                if (mAktivann == 1) {
                    mAktivan.setSelected(true);
                } else {
                    mAktivan.setSelected(false);
                }
                Integer mSklop = rs.getInt("artikli.sklop");
                if (mSklop == 1) {
                    jCheckBoxSklop.setSelected(true);
                } else {
                    jCheckBoxSklop.setSelected(false);
                }

                mNaziv.setText(rs.getString("artikli.naziv"));
                mSifra.setText(rs.getString("artikli.sifra"));
                mSifra2.setText(rs.getString("artikli.sifra2"));
                mPLU.setText(rs.getString("artikli.plu"));

                mId.setText(rs.getString("artikli.id"));

                List<String> listTipArtikla = puniComboMaticni.puni_maticne(conn, "tip_artikla");
                mTip.setModel(new DefaultComboBoxModel(listTipArtikla.toArray()));
                String aa = rs.getString("tip_artikla.naziv");
                mTip.setSelectedItem(rs.getString("tip_artikla.naziv"));

                List<String> listJediniceMere = puniComboMaticni.puni_maticne(conn, "jedinica_mere");
                mJedinicaMere.setModel(new DefaultComboBoxModel(listJediniceMere.toArray()));
                aa = rs.getString("jedinica_mere.naziv");
                mJedinicaMere.setSelectedItem(rs.getString("jedinica_mere.naziv"));

                List<String> listPoreskaGrupa = puniComboMaticni.puni_maticne(conn, "poreska_tarifa");
                mPoreskaGrupa.setModel(new DefaultComboBoxModel(listPoreskaGrupa.toArray()));
                aa = rs.getString("poreska_tarifa.naziv");
                mPoreskaGrupa.setSelectedItem(rs.getString("poreska_tarifa.naziv"));

                List<String> listOdeljenje = puniComboMaticni.puni_maticne(conn, "odeljenje");
                mOdeljenje.setModel(new DefaultComboBoxModel(listOdeljenje.toArray()));
                mOdeljenje.setSelectedItem(rs.getString("odeljenje.naziv"));

                List<String> listKlasifikacija = puniComboMaticni.puni_maticne(conn, "klasifikacija_artikala");
                mKlasifikacija.setModel(new DefaultComboBoxModel(listKlasifikacija.toArray()));
                mKlasifikacija.setSelectedItem(rs.getString("klasifikacija_artikala.naziv"));

                List<String> listCarinskaTarifa = puniComboMaticni.puni_maticne(conn, "carinska_tarifa_artikala");
                mCarinskaTarifa.setModel(new DefaultComboBoxModel(listCarinskaTarifa.toArray()));
                mCarinskaTarifa.setSelectedItem(rs.getString("carinska_tarifa_artikala.naziv"));

                List<String> listProizvodjac = puniComboMaticni.puni_maticne(conn, "partneri");
                mProizvodjac.setModel(new DefaultComboBoxModel(listProizvodjac.toArray()));
                mProizvodjac.setSelectedItem(rs.getString("partneri.naziv"));

                mRokTrajanja.setValue(rs.getDouble("rok_trajanja"));
                mKalo.setValue(rs.getDouble("kalo"));

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
            iMessage.setText(ex.toString());
            ml.logger.error(tabela + " SQLException " + ex.toString());
        }

        PripremaTabeleCena(mSifra.getText());
        PripremaTabelePakovanja(mSifra.getText());
        PripremaTabeluAkcija(mSifra.getText());
//        puniTabeluCena();

//        puniTabeluPakovanja();
//        puniTabeluAkcija();
    }

    private void PripremaTabeluAkcija(String id) {

        try {

//            String sqlQuery = "SELECT artikli_pakovanja.id, artikli_pakovanja.tippak, \n"
//                    + "artikli_pakovanja.artikal_sifra, \n"
//                    + "artikli_pakovanja.ean,  artikli_pakovanja.netojm, artikli_pakovanja.brutojm, \n"
//                    + "artikli_pakovanja.sirina,  artikli_pakovanja.visina, artikli_pakovanja.duzina, \n"
//                    + "artikli_pakovanja.komada,  artikli_pakovanja.sifra_ambalaze, "
//                     + "vrste_pakovanja_artikala.naziv, ambalaza.naziv, artikli_pakovanja.aktivan \n"
//                    + " FROM artikli_pakovanja \n"
//                    + "LEFT JOIN vrste_pakovanja_artikala ON vrste_pakovanja_artikala.sifra = artikli_pakovanja.tippak \n"
//                    + "LEFT JOIN ambalaza ON ambalaza.sifra = artikli_pakovanja.sifra_ambalaze \n"
//                    + "WHERE \n"
//                    + "artikli_pakovanja.artikal_sifra LIKE '%" + id + "%' AND artikli_pakovanja.aktivan=1 " // `id`,
//                    + "order by artikli_pakovanja.tippak "; // desc
            String sqlQuery = "SELECT id, artikal_sifra, mpcena, mpcena_akcijska, datum_akcije_od, datum_akcije_do, \n"
                    + "aktivan, slike, zvuk, video \n"
                    + " FROM akcije \n"
                    + "WHERE \n"
                    + "artikal_sifra LIKE '%" + id + "%' AND aktivan=1 " // `id`,
                    + "order by datum_akcije_od  "; // desc            

            if (uzmiParametar(conn, "debugiranje").equals("1")) {
                System.out.println(sqlQuery);
            }

            PreparedStatement stmt = conn.prepareStatement(sqlQuery);
            ResultSet rs = stmt.executeQuery(sqlQuery);

            while (rs.next()) {

                Akcije ap = new Akcije();
                ap.setId(rs.getInt("id"));
                ap.setArtikalSifra(rs.getString("artikal_sifra"));
                ap.setMpcena(rs.getBigDecimal("mpcena"));
                ap.setMpcenaAkcijska(rs.getBigDecimal("mpcena_akcijska"));
                ap.setDatumAkcijeOd(rs.getDate("datum_akcije_od"));
                ap.setDatumAkcijeDo(rs.getDate("datum_akcije_do"));

                ap.setAktivan(rs.getBoolean("aktivan"));

                mAkcijaTrajeOd.setDate(rs.getDate("datum_akcije_od"));
                mAkcijaTrajeDo.setDate(rs.getDate("datum_akcije_do"));
                mAkcijskaCena.setValue(new AsoftDFP(rs.getDouble("mpcena_akcijska")));

//                mEAN.setText(rs.getString("ean"));
//                mAmbalaza.setSelectedItem(rs.getString("ambalaza.naziv"));
//                mSirina.setValue(new AsoftDFP(rs.getDouble("sirina")));
//                mDuzina.setValue(new AsoftDFP(rs.getDouble("duzina")));
//                mVisina.setValue(new AsoftDFP(rs.getDouble("visina")));
//                mKomada.setValue(new AsoftDFP(rs.getDouble("komada")));
//                mNetoUJm.setValue(new AsoftDFP(rs.getDouble("netojm")));
//                mBrutoUJm.setValue(new AsoftDFP(rs.getDouble("brutojm")));
                akcijeArray.add(ap);
            }

        } catch (SQLException ex) {
            MyLogger.logger.info(ex.toString());
            iMessage.setText(ex.toString());
        } catch (ParseException ex) {
            MyLogger.logger.info(ex.toString());
            iMessage.setText(ex.toString());
        }

        DefaultTableModel model = new DefaultTableModel();

//        DefaultTableModel model = (DefaultTableModel) jTableCene.getModel();
        TableColumnModel tcm = jTableAkcije.getColumnModel();

        tcm.getColumn(0).setPreferredWidth(20);    //Name
        tcm.getColumn(1).setPreferredWidth(120);   //Title
        tcm.getColumn(2).setPreferredWidth(250);   //Surname
        tcm.getColumn(3).setPreferredWidth(50);    //ID
        tcm.getColumn(4).setPreferredWidth(50);    //ID
        tcm.getColumn(5).setPreferredWidth(50);    //ID

//        tcm.getColumn(6).setPreferredWidth(50);    //ID
//        tcm.getColumn(7).setPreferredWidth(50);    //ID
//        tcm.getColumn(8).setPreferredWidth(50);    //ID
//        tcm.getColumn(9).setPreferredWidth(50);    //ID        
        //         
        model.setColumnIdentifiers(new Object[]{"Id", "Sifra",
            "Akcija", "Datum od", "Datum do", "Aktivan"});

        Object[] row = new Object[10];
        for (int i = 0; i < akcijeArray.size(); i++) {
            row[0] = akcijeArray.get(i).getId();
            row[1] = akcijeArray.get(i).getArtikalSifra();
//            row[2] = akcijeArray.get(i).getMpcena();
            row[2] = akcijeArray.get(i).getMpcenaAkcijska();
            row[3] = akcijeArray.get(i).getDatumAkcijeOd();
            row[4] = akcijeArray.get(i).getDatumAkcijeDo();
            row[5] = akcijeArray.get(i).getAktivan();
//            row[7] = akcijeArray.get(i).getSlike();
//            row[8] = akcijeArray.get(i).getZvuk();
//            row[9] = akcijeArray.get(i).getVideo();

            model.addRow(row);
        }

        jTableAkcije.setModel(model);

        jTableAkcije.getColumnModel().getColumn(0).setCellRenderer(new CustomRenderer(1));
        jTableAkcije.getColumnModel().getColumn(1).setCellRenderer(new CustomRenderer(1));
        jTableAkcije.getColumnModel().getColumn(2).setCellRenderer(new CustomRenderer(1));
        jTableAkcije.getColumnModel().getColumn(3).setCellRenderer(new CustomRenderer(1));
        jTableAkcije.getColumnModel().getColumn(4).setCellRenderer(new CustomRenderer(1));
        jTableAkcije.getColumnModel().getColumn(5).setCellRenderer(new CustomRenderer(1));
//        jTableAkcije.getColumnModel().getColumn(6).setCellRenderer(new CustomRenderer(1));
//        jTableAkcije.getColumnModel().getColumn(7).setCellRenderer(new CustomRenderer(1));
//        jTableAkcije.getColumnModel().getColumn(8).setCellRenderer(new CustomRenderer(1));
//        jTableAkcije.getColumnModel().getColumn(9).setCellRenderer(new CustomRenderer(1));                

    }

    private void PripremaTabeleCena(String id) {

//        PodaciOPastoralnojSluzbi pops = new PodaciOPastoralnojSluzbi();
//        cenovnikArray.removeAll();
        try {

            String sqlQuery = " select * from cenovnik where artikal_sifra = '"
                    + id + "'" + " and aktivan order by datum desc";
            PreparedStatement stmt = conn.prepareStatement(sqlQuery);
            ResultSet rs = stmt.executeQuery(sqlQuery);

            if (uzmiParametar(conn, "debugiranje").equals("1")) {
                System.out.println(sqlQuery);
            }

            while (rs.next()) {

                Cenovnik cenovnik = new Cenovnik();
                cenovnik.setId(rs.getInt("id"));
                cenovnik.setArtikalId(rs.getInt("artikal_id"));
                cenovnik.setSifra(rs.getString("artikal_sifra"));
                cenovnik.setDatum(rs.getDate("datum"));
                cenovnik.setNabavnaCena(rs.getBigDecimal("nabavna_cena"));
                cenovnik.setPlanskaCena(rs.getBigDecimal("planska_cena"));
                cenovnik.setProdajnaCena(rs.getBigDecimal("prodajna_cena"));
                cenovnik.setMaloprodajnaCenaBezPdv(rs.getBigDecimal("maloprodajna_cena_bez_pdv"));
                cenovnik.setMaloprodajnaCena(rs.getBigDecimal("maloprodajna_cena"));
                cenovnik.setAktivan(rs.getBoolean("aktivan"));

//                pops.setSvojstvo(rs.getString("svojstvo"));
//                pops.setDekret(rs.getString("dekret"));
//                pops.setPocetakSluzbeOd(rs.getDate("pocetak_sluzbe_od"));
//                pops.setTrajanjeSluzbeDo(rs.getDate("trajanje_sluzbe_do"));
//                pops.setUserId(rs.getInt("user_id"));
//                pops.setRbr(popsArray.size() + 1);
//                pops.setJmbg(mJmbg.getText());
//                mDatumCenovnika.setDate(rs.getDate("datum"));
                mNabavnaCena.setValue(new AsoftDFP(rs.getDouble("nabavna_cena")));
                mPlanskaCena.setValue(new AsoftDFP(rs.getDouble("planska_cena")));
                mProdajnaCena.setValue(new AsoftDFP(rs.getDouble("prodajna_cena")));
                mProdajnaCenaSaPDV.setValue(new AsoftDFP(rs.getDouble("prodajna_cena_sa_pdv")));
                mMaloprodajnaCena.setValue(new AsoftDFP(rs.getDouble("maloprodajna_cena")));
                mMaloprodajnaCenaBezPDV.setValue(new AsoftDFP(rs.getDouble("maloprodajna_cena_bez_pdv")));

//                mPOPSMestoSluzbe.setSelectedItem(rs.getString("mesto_sluzbe"));
//                mPOPSNazivUstanove.setSelectedItem(rs.getString("naziv_ustanove"));
//                mPOPSNazivZupe.setSelectedItem(rs.getString("naziv_zupe"));
//                mPOPSSvojstvo.setSelectedItem(rs.getString("svojstvo"));
//                mPOPSDekret.setText(rs.getString("dekret"));
                try {
                    mDatumCenovnika.setDate(rs.getDate("datum"));
                } catch (ParseException ex) {
//                    Logger.getLogger(ArtikliNew.class.getName()).log(Level.SEVERE, null, ex);
                }

                cenovnikArray.add(cenovnik);
            }

        } catch (SQLException ex) {
            ml.logger.info(ex.toString());
            iMessage.setText(ex.toString());
        }

//        FillListPodaciOCenama();
        DefaultTableModel model = new DefaultTableModel();

//        DefaultTableModel model = (DefaultTableModel) jTableCene.getModel();
        TableColumnModel tcm = jTableCene.getColumnModel();

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
        for (int i = 0; i < cenovnikArray.size(); i++) {
            row[0] = cenovnikArray.get(i).getId();
            row[1] = cenovnikArray.get(i).getArtikalId();
            row[2] = cenovnikArray.get(i).getDatum();
            row[3] = cenovnikArray.get(i).getNabavnaCena();
            row[4] = cenovnikArray.get(i).getPlanskaCena();
            row[5] = cenovnikArray.get(i).getProdajnaCenaSaPdv();
            row[6] = cenovnikArray.get(i).getProdajnaCena();
            row[7] = cenovnikArray.get(i).getMaloprodajnaCenaBezPdv();
            row[8] = cenovnikArray.get(i).getMaloprodajnaCena();
            row[9] = cenovnikArray.get(i).getAktivan();
            model.addRow(row);
        }

        jTableCene.setModel(model);

        jTableCene.getColumnModel().getColumn(0).setCellRenderer(new CustomRenderer(1));
        jTableCene.getColumnModel().getColumn(1).setCellRenderer(new CustomRenderer(1));
        jTableCene.getColumnModel().getColumn(2).setCellRenderer(new CustomRenderer(1));
        jTableCene.getColumnModel().getColumn(3).setCellRenderer(new CustomRenderer(1));
        jTableCene.getColumnModel().getColumn(4).setCellRenderer(new CustomRenderer(1));
        jTableCene.getColumnModel().getColumn(5).setCellRenderer(new CustomRenderer(1));
        jTableCene.getColumnModel().getColumn(6).setCellRenderer(new CustomRenderer(1));
        jTableCene.getColumnModel().getColumn(7).setCellRenderer(new CustomRenderer(1));
        jTableCene.getColumnModel().getColumn(8).setCellRenderer(new CustomRenderer(1));
        jTableCene.getColumnModel().getColumn(9).setCellRenderer(new CustomRenderer(1));

    }

    private void PripremaTabelePakovanja(String id) {

        //        PodaciOPastoralnojSluzbi pops = new PodaciOPastoralnojSluzbi();
        //        cenovnikArray.removeAll();
        try {

            String sqlQuery = "SELECT artikli_pakovanja.id, artikli_pakovanja.tippak, \n"
                    + "artikli_pakovanja.artikal_sifra, \n"
                    + "artikli_pakovanja.ean,  artikli_pakovanja.netojm, artikli_pakovanja.brutojm, \n"
                    + "artikli_pakovanja.sirina,  artikli_pakovanja.visina, artikli_pakovanja.duzina, \n"
                    + "artikli_pakovanja.komada,  artikli_pakovanja.sifra_ambalaze, "
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

                mVrstaPakovanjaArtikala.setSelectedItem(rs.getString("vrste_pakovanja_artikala.naziv"));
                mEAN.setText(rs.getString("ean"));
                mAmbalaza.setSelectedItem(rs.getString("ambalaza.naziv"));
                mSirina.setValue(new AsoftDFP(rs.getDouble("sirina")));
                mDuzina.setValue(new AsoftDFP(rs.getDouble("duzina")));
                mVisina.setValue(new AsoftDFP(rs.getDouble("visina")));
                mKomada.setValue(new AsoftDFP(rs.getDouble("komada")));
                mNetoUJm.setValue(new AsoftDFP(rs.getDouble("netojm")));
                mBrutoUJm.setValue(new AsoftDFP(rs.getDouble("brutojm")));

                pakovanjaArray.add(ap);
            }

        } catch (SQLException ex) {
            MyLogger.logger.info(ex.toString());
            iMessage.setText(ex.toString());
        }

//        FillListPodaciOCenama();
//        DefaultTableModel model = new DefaultTableModel();
        DefaultTableModel model = new DefaultTableModel();

//        DefaultTableModel model = (DefaultTableModel) jTableCene.getModel();
        TableColumnModel tcm = jTableBarKod.getColumnModel();

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
        //         
        model.setColumnIdentifiers(new Object[]{"Id", "Sifra", "Vrsta pakovanja", "Ean", "Netojm",
            "Brutojm", "Sirina", "Duzina", "Visina", "Komada", "Ambalaza", "Aktivan"});
        Object[] row = new Object[11];
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
            row[9] = pakovanjaArray.get(i).getSifraAmbalaze();
            row[10] = pakovanjaArray.get(i).getAktivan();
            model.addRow(row);
        }

        jTableBarKod.setModel(model);

        jTableBarKod.getColumnModel().getColumn(0).setCellRenderer(new CustomRenderer(1));
        jTableBarKod.getColumnModel().getColumn(1).setCellRenderer(new CustomRenderer(1));
        jTableBarKod.getColumnModel().getColumn(2).setCellRenderer(new CustomRenderer(1));
        jTableBarKod.getColumnModel().getColumn(3).setCellRenderer(new CustomRenderer(1));
        jTableBarKod.getColumnModel().getColumn(4).setCellRenderer(new CustomRenderer(1));
        jTableBarKod.getColumnModel().getColumn(5).setCellRenderer(new CustomRenderer(1));
        jTableBarKod.getColumnModel().getColumn(6).setCellRenderer(new CustomRenderer(1));
        jTableBarKod.getColumnModel().getColumn(7).setCellRenderer(new CustomRenderer(1));
        jTableBarKod.getColumnModel().getColumn(8).setCellRenderer(new CustomRenderer(1));
        jTableBarKod.getColumnModel().getColumn(9).setCellRenderer(new CustomRenderer(1));
        jTableBarKod.getColumnModel().getColumn(10).setCellRenderer(new CustomRenderer(1));
        jTableBarKod.getColumnModel().getColumn(11).setCellRenderer(new CustomRenderer(1));

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

                mVrstaPakovanjaArtikala.setSelectedItem(rs.getString("vrste_pakovanja_artikala.naziv"));
                mEAN.setText(rs.getString("ean"));
                mAmbalaza.setSelectedItem(rs.getString("ambalaza.naziv"));
                mSirina.setValue(new AsoftDFP(rs.getDouble("sirina")));
                mDuzina.setValue(new AsoftDFP(rs.getDouble("duzina")));
                mVisina.setValue(new AsoftDFP(rs.getDouble("visina")));
                mKomada.setValue(new AsoftDFP(rs.getDouble("komada")));
                mNetoUJm.setValue(new AsoftDFP(rs.getDouble("netojm")));
                mBrutoUJm.setValue(new AsoftDFP(rs.getDouble("brutojm")));

                // mneoporeziviIznos = npNeoporeziviIznos.getValueDFP().doubleValue();
            }
        } catch (Exception ex) {
            Main.track.error(tabela + " SQLException " + ex);
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
        TableColumnModel tcm = jTableCene.getColumnModel();

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

        jTableCene.setModel(model);

        jTableCene.getColumnModel().getColumn(0).setCellRenderer(new CustomRenderer(1));
        jTableCene.getColumnModel().getColumn(1).setCellRenderer(new CustomRenderer(1));
        jTableCene.getColumnModel().getColumn(2).setCellRenderer(new CustomRenderer(1));
        jTableCene.getColumnModel().getColumn(3).setCellRenderer(new CustomRenderer(1));
        jTableCene.getColumnModel().getColumn(4).setCellRenderer(new CustomRenderer(1));
        jTableCene.getColumnModel().getColumn(5).setCellRenderer(new CustomRenderer(1));
        jTableCene.getColumnModel().getColumn(6).setCellRenderer(new CustomRenderer(1));
        jTableCene.getColumnModel().getColumn(7).setCellRenderer(new CustomRenderer(1));
        jTableCene.getColumnModel().getColumn(8).setCellRenderer(new CustomRenderer(1));
        jTableCene.getColumnModel().getColumn(9).setCellRenderer(new CustomRenderer(1));

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
                mDatumCenovnika.setDate(rs.getDate("datum"));
                mNabavnaCena.setValue(new AsoftDFP(rs.getDouble("nabavna_cena")));
                mPlanskaCena.setValue(new AsoftDFP(rs.getDouble("planska_cena")));
                mProdajnaCena.setValue(new AsoftDFP(rs.getDouble("prodajna_cena")));
                mProdajnaCenaSaPDV.setValue(new AsoftDFP(rs.getDouble("prodajna_cena_bez_pdv")));
                mMaloprodajnaCena.setValue(new AsoftDFP(rs.getDouble("maloprodajna_cena")));
                mMaloprodajnaCenaBezPDV.setValue(new AsoftDFP(rs.getDouble("maloprodajna_cena_bez_pdv")));
                // mneoporeziviIznos = npNeoporeziviIznos.getValueDFP().doubleValue();
            }
        } catch (Exception ex) {
            Main.track.error(tabela + " SQLException " + ex);
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
            ml.logger.info(ex.toString());
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
                + " artikli (sifra, naziv, sifra2, plu, aktivan, artikal_tip_id,"
                + "jedinica_mere_id, poreska_grupa_id, odeljenje_id, artikal_kategorija_id, "
                + "carinska_tarifa_artikala_id, partneri_id, sklop, rok_trajanja, kalo, "
                + "slika, photo ) "
                + "values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            mphoto = null;
            try {
                if (!mSlika.getText().isEmpty()) {
                    mphoto = new FileInputStream(new File(mSlika.getText()));
                }
            } catch (FileNotFoundException ex) {
                MyLogger.logger.error(ex.toString());
                iMessage.setText(ex.toString());
            }

//                    + "', artikal_tip_id='" + uzmiIdZaNaziv("tip_artikla", mTip.getSelectedItem().toString())
//                    + "', jedinica_mere_id='" + uzmiIdZaNaziv("jedinica_mere", mJedinicaMere.getSelectedItem().toString())
//                    + "', poreska_grupa_id='" + uzmiIdZaNaziv("poreska_tarifa", mPoreskaGrupa.getSelectedItem().toString())
//                    + "', odeljenje_id='" + uzmiIdZaNaziv("odeljenje", mOdeljenje.getSelectedItem().toString())
//                    + "', artikal_kategorija_id='" + uzmiIdZaNaziv("klasifikacija_artikala", mKlasifikacija.getSelectedItem().toString())
//                    + "', carinska_tarifa_artikala_id='" + uzmiIdZaNaziv("carinska_tarifa_artikala", mCarinskaTarifa.getSelectedItem().toString())
//                    + "', partneri_id='" + uzmiIdZaNaziv("partneri", mProizvodjac.getSelectedItem().toString())
//               
            PreparedStatement stmt
                    = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, mSifra.getText());
            stmt.setString(2, mNaziv.getText());
            stmt.setString(3, mSifra2.getText());
            stmt.setString(4, mPLU.getText());
            stmt.setInt(5, vratiJedanIliNula(mAktivan.isSelected()));
            stmt.setInt(6, uzmiIdZaNaziv("tip_artikla", mTip.getSelectedItem().toString()));
            stmt.setInt(7, uzmiIdZaNaziv("jedinica_mere", mJedinicaMere.getSelectedItem().toString()));
            stmt.setInt(8, uzmiIdZaNaziv("poreska_tarifa", mPoreskaGrupa.getSelectedItem().toString()));
            stmt.setInt(9, uzmiIdZaNaziv("odeljenje", mOdeljenje.getSelectedItem().toString()));
            stmt.setInt(10, uzmiIdZaNaziv("klasifikacija_artikala", mKlasifikacija.getSelectedItem().toString()));
            stmt.setInt(11, uzmiIdZaNaziv("carinska_tarifa_artikala", mCarinskaTarifa.getSelectedItem().toString()));
            stmt.setInt(12, uzmiIdZaNaziv("partneri", mProizvodjac.getSelectedItem().toString()));
            stmt.setInt(13, vratiJedanIliNula(jCheckBoxSklop.isSelected()));
            stmt.setDouble(14, mRokTrajanja.getValueDFP().doubleValue());
            stmt.setDouble(15, mKalo.getValueDFP().doubleValue());
            stmt.setString(16, mSlika.getText());
            stmt.setBlob(17, mphoto);

            int i = stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            int generatedKey = 0;
            if (rs.next()) {
                generatedKey = rs.getInt(1);
                mId.setText(Integer.toString(generatedKey));
            }

            if (uzmiParametar(conn, "debugiranje").equals("1")) {
                System.out.println(insertSQL);
            }

            // **** brisanje starog id u tabeli artikli ****** ///
            if (saBrisanjem) {
                id_false(conn, "artikli", mmIdZaBrisanje);
            }
            // **** kraj brisanje starog id u tabeli artikli * ///

            for (Cenovnik u : cenovnikArray) {

                if (uzmiParametar(conn, "debugiranje").equals("1")) {
                    System.out.println(u);
                    u.toString();
                }
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

                String SQLinsertCenovnik = " insert into "
                        + " cenovnik ("
                        + " `artikal_id`, `artikal_sifra`, `datum`, \n"
                        + " `nabavna_cena`, `planska_cena`, `prodajna_cena`, "
                        + " `maloprodajna_cena_bez_pdv`, \n"
                        + " `maloprodajna_cena`, `aktivan`, \n"
                        + " `prodajna_cena_bez_pdv`) "
                        + "values ( ?,?,?,?,?,?,?,?,?,?,?)";

                stmt.setInt(1, generatedKey);
                stmt.setInt(2, u.getArtikalId());
                stmt.setString(3, u.getArtikalSifra());
                stmt.setString(4, formatter.format(u.getDatum()));
                stmt.setBigDecimal(5, u.getNabavnaCena());
                stmt.setBigDecimal(6, u.getPlanskaCena());
                stmt.setBigDecimal(7, u.getProdajnaCena());
                stmt.setBigDecimal(8, u.getMaloprodajnaCenaBezPdv());
                stmt.setBigDecimal(9, u.getMaloprodajnaCena());
                stmt.setInt(10, vratiJedanIliNula(mAktivan.isSelected()));
                stmt.setBigDecimal(11, u.getMaloprodajnaCenaBezPdv());

                if (uzmiParametar(conn, "debugiranje").equals("1")) {
                    System.out.println(SQLinsertCenovnik);
                }

                stmt.executeUpdate(SQLinsertCenovnik);
            }

            for (ArtikliPakovanja u : pakovanjaArray) {

                String SQLinsertPakovanja = " insert into "
                        + " artikli_pakovanja ("
                        + " artikal_id, artikal_sifra, tippak, ean, \n"
                        + "                naziv, jm, netojm, \n"
                        + " brutojm, \n"
                        + " sirina, visina, duzina, komada,  aktivan, \n"
                        + "                sifra_ambalaze ) \n"
                        + "values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                stmt.setInt(1, generatedKey);
                stmt.setString(2, u.getArtikalSifra());
                stmt.setString(3, u.getTippak());
                stmt.setString(4, u.getEan());
                stmt.setString(5, u.getNaziv());
                stmt.setString(6, u.getJm());
                stmt.setBigDecimal(7, u.getNetojm());
                stmt.setBigDecimal(8, u.getBrutojm());
                stmt.setBigDecimal(9, u.getSirina());
                stmt.setBigDecimal(10, u.getVisina());
                stmt.setBigDecimal(11, u.getDuzina());
                stmt.setBigDecimal(12, u.getKomada());
                stmt.setInt(13, vratiJedanIliNula(mAktivan.isSelected()));
                stmt.setString(14, u.getSifraAmbalaze());

                if (uzmiParametar(conn, "debugiranje").equals("1")) {
                    System.out.println(SQLinsertPakovanja);
                }

                stmt.executeUpdate(SQLinsertPakovanja);
            }

            for (Akcije u : akcijeArray) {

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String SQLinsertPakovanja = " insert into "
                        + " akcije  ("
                        + " artikal_id, artikal_sifra, \n"
                        + " mpcena, mpcena_akcijska, datum_akcije_od, datum_akcije_do, aktivan \n"
                        + " ) \n"
                        + "values ( ?,?,?,?,?,?,?)";

                stmt.setInt(1, generatedKey);
                stmt.setString(2, u.getArtikalSifra());
                stmt.setBigDecimal(3, u.getMpcena());
                stmt.setBigDecimal(4, u.getMpcenaAkcijska());
                stmt.setString(5, formatter.format(u.getDatumAkcijeOd()));
                stmt.setString(6, formatter.format(u.getDatumAkcijeDo()));
                stmt.setInt(7, vratiJedanIliNula(mAktivan.isSelected()));

                if (uzmiParametar(conn, "debugiranje").equals("1")) {
                    System.out.println(SQLinsertPakovanja);
                }

                stmt.executeUpdate(SQLinsertPakovanja);
            }

        } catch (Exception e) {
            MyLogger.logger.error(e.toString());
            iMessage.setText(e.toString());
        }

    }
}
