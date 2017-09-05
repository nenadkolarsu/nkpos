package maticni.cenovnik;

import maticni.*;
import forms_pos.Main;
import forms_pos.Prijava;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
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
public class Akcije extends javax.swing.JFrame {

    ArrayList<model.Akcije> akcijeArray = new ArrayList<>();
    Connection conn;
    String tabela, sifraArtikla = "";
    int rekordaUslektu = 0;
    MyLogger ml = new MyLogger();
//    static Logger logger = Logger.getLogger(MaticniPodaci.class);

    public Akcije() {

        initComponents();
        MyLogger.logger.info("Akcije");
        this.setTitle(tabela);
        setLocationRelativeTo(null);
        konekcija n = new konekcija();
        conn = n.konekcija();
        tabela = "akcije";
        PripremaTabeleAkcije(sifraArtikla);
    }

    public Akcije(final Connection conn, final String tabela, String sifraArtikla) {

        this.conn = conn;
        this.tabela = tabela;
        this.sifraArtikla = sifraArtikla;

        initComponents();
        MyLogger.logger.info("Akcije");
        this.setTitle(tabela);

        setLocationRelativeTo(null);
        nuliranjeFormeBezSifre();
        PripremaTabeleAkcije(sifraArtikla);
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
                    Main.track.info("this is a information log message" + ex);
//                    Logger.getLogger(PttBrojevi.class.getName()).log(Level.SEVERE, null, ex);

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
                    ml.logger.error(ex);
                    // Main.track.info("this is a information log message" + ex);

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
//                   Logger.getLogger(PttBrojevi.class.getName()).log(Level.SEVERE, null, ex);
//                    obj.runMe("Exception First button ..... ");
                    Main.track.info("First button " + ex);
                    ml.logger.error(ex);
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
                    // Logger.getLogger(PttBrojevi.class.getName()).log(Level.SEVERE, null, ex);
//                    obj.runMe("Exception Last button ..... ");
                    // Main.track.info("this is a information log message" + ex);
                    ml.logger.error(ex);
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
        jPanel15 = new javax.swing.JPanel();
        mAkcijaTrajeOd = new org.asoft.library.picker.AsoftHistoryDatePicker();
        mAkcijaTrajeDo = new org.asoft.library.picker.AsoftHistoryDatePicker();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        mAkcijskaCena = new org.asoft.library.AsoftNumericField();
        mMaloprodajnaCena = new org.asoft.library.AsoftNumericField();
        jLabel27 = new javax.swing.JLabel();
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

        mNaziv.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        mNaziv.setEnabled(false);

        jLabel2.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel2.setText("Šifra:");

        jLabel1.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel1.setText("Id:");

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel15.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel17.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel17.setText("Akcija traje do: ");

        jLabel18.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel18.setText("Akcija traje od: ");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(mAkcijaTrajeDo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(mAkcijaTrajeOd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mAkcijaTrajeOd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel18)))
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mAkcijaTrajeDo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 13, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel17)
                        .addGap(27, 27, 27))))
        );

        jPanel14.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        mAkcijskaCena.setForeground(new java.awt.Color(255, 0, 0));
        mAkcijskaCena.setText("0.00");
        mAkcijskaCena.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N

        mMaloprodajnaCena.setText("0.00");
        mMaloprodajnaCena.setEnabled(false);
        mMaloprodajnaCena.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        mMaloprodajnaCena.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mMaloprodajnaCenaActionPerformed(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 0, 0));
        jLabel27.setText("Akcijska cena:");

        jLabel28.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel28.setText("Maloprodajna cena:");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap(85, Short.MAX_VALUE)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addGap(18, 18, 18)
                        .addComponent(mAkcijskaCena, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mMaloprodajnaCena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mAkcijskaCena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addGap(25, 25, 25))
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
                .addGap(20, 20, 20)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(mAktivan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(mAktivan)
                    .addComponent(jLabel4))
                .addContainerGap(34, Short.MAX_VALUE))
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
                        .addComponent(mNaziv, javax.swing.GroupLayout.PREFERRED_SIZE, 462, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 18, Short.MAX_VALUE)))
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
                "Id", "Sifra", "Datum od", "Datum do", "Maloprodajna cena", "Akcijska cena", "Aktivan", "Rbr"
            }
        ));
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
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
            if (!DaLiPostojiAkcijaSaDatumom(mSifra.getText(), mAkcijaTrajeOd.getSQLDate())) {
                return;
            }
        } catch (ParseException ex) {
            Logger.getLogger(Akcije.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            PreparedStatement sqlInsert = conn.prepareStatement(
                    "insert into akcije ( artikal_id, "
                    + "artikal_sifra,"
                    + "mpcena, "
                    + "mpcena_akcijska, datum_akcije_od, datum_akcije_do, "
                    + " aktivan) values(?,?,?,?,?,?,?)");

            sqlInsert.setInt(1, vrati_id_za_sifru(conn, "artikli", mSifra.getText()));
            sqlInsert.setString(2, mSifra.getText());
            sqlInsert.setBigDecimal(3, mMaloprodajnaCena.getValueDFP().BigDecimalValue());
            sqlInsert.setBigDecimal(4, mAkcijskaCena.getValueDFP().BigDecimalValue());
            sqlInsert.setDate(5, mAkcijaTrajeOd.getSQLDate());
            sqlInsert.setDate(6, mAkcijaTrajeDo.getSQLDate());
            sqlInsert.setInt(7, vratiJedanIliNula(mAktivan.isSelected()));

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

        PripremaTabeleAkcije(mSifra.getText());
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

            model.setValueAt(mId.getText(), tblSifre.getSelectedRow(), 0);
            model.setValueAt(mSifra.getText(), tblSifre.getSelectedRow(), 1);
            try {
                model.setValueAt(mAkcijaTrajeOd.getSQLDate(), tblSifre.getSelectedRow(), 2);
            } catch (ParseException ex) {
                Logger.getLogger(Akcije.class.getName()).log(Level.SEVERE, null, ex);
            }

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

//                if (!OperacijeSaSifrom(conn, tabela, mSifra.getText())) {
//                    throw new SQLException("postoji dupla sifra koja je aktivna");
//                }
                try {
                    PreparedStatement sqlInsert = conn.prepareStatement(
                            "insert into akcije ( artikal_id, "
                            + "artikal_sifra,"
                            + "mpcena, "
                            + "mpcena_akcijska, datum_akcije_od, datum_akcije_do, "
                            + " aktivan) values(?,?,?,?,?,?,?)");

                    sqlInsert.setInt(1, vrati_id_za_sifru(conn, "artikli", mSifra.getText()));
                    sqlInsert.setString(2, mSifra.getText());
                    sqlInsert.setBigDecimal(3, mMaloprodajnaCena.getValueDFP().BigDecimalValue());
                    sqlInsert.setBigDecimal(4, mAkcijskaCena.getValueDFP().BigDecimalValue());
                    sqlInsert.setDate(5, mAkcijaTrajeOd.getSQLDate());
                    sqlInsert.setDate(6, mAkcijaTrajeDo.getSQLDate());
                    sqlInsert.setInt(7, vratiJedanIliNula(mAktivan.isSelected()));

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
                MyLogger.logger.error("Cenovnik " + ex);
                iMessage.setText(ex.toString());
            }
        }
        PripremaTabeleAkcije(mSifra.getText());
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
                String aa = rs.getString("artikal_sifra");
                mSifra.setText(rs.getString("artikal_sifra"));
//                mNaziv.setText(rs.getString("naziv"));
                int mmAktivan = rs.getInt("aktivan");
                if (mmAktivan == 1) {
                    mAktivan.setSelected(true);
                } else {
                    mAktivan.setSelected(false);
                }

                mAkcijaTrajeOd.setDate(rs.getDate("datum_akcije_od"));
                mAkcijaTrajeDo.setDate(rs.getDate("datum_akcije_do"));

                mAkcijskaCena.setValue(new AsoftDFP(rs.getDouble("mpcena_akcijska")));
                mMaloprodajnaCena.setValue(new AsoftDFP(rs.getDouble("mpcena")));

            }
        } catch (SQLException | ParseException ex) {
            iMessage.setText(ex.toString());
            MyLogger.logger.error(ex.toString());

        }

    }//GEN-LAST:event_tblSifreMouseClicked
    public ArrayList<model.Akcije> ListSifre(String ValToSearch) {
        ArrayList<model.Akcije> usersList = new ArrayList<model.Akcije>();
        Statement st;
        ResultSet rs;
        try {
            // Connection con = getConnection();
            st = conn.createStatement();
            String searchQuery = "SELECT * FROM akcije WHERE "
                    + "CONCAT(`id`, `sifra`, `datum`) LIKE '%" + ValToSearch + "%'";
            rs = st.executeQuery(searchQuery);
            model.Akcije akcije;
            while (rs.next()) {
                akcije = new model.Akcije(rs.getInt("id")); //, rs.getString("sifra"), rs.getDate("datum")
                usersList.add(akcije);
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
        PripremaTabeleAkcije(mSifra.getText());
        nuliranjeForme();
        filterTxt.setText("");
    }//GEN-LAST:event_BrisanjeBtnActionPerformed

    private void RefreshBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshBtnActionPerformed
        filterTxt.setText("");
        iMessage.setText("");

        PripremaTabeleAkcije(mSifra.getText());
        nuliranjeForme();

    }//GEN-LAST:event_RefreshBtnActionPerformed

    private void mAktivanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mAktivanActionPerformed
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
        PripremaTabeleAkcije(filterTxt.getText());
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
                    java.util.logging.Logger.getLogger(Akcije.class.getName()).log(Level.SEVERE, null, ex);
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
                MyLogger.logger.error(ex.toString());
//                mSifra.grabFocus();
                mSifra.requestFocus();
            }

//            mAkcijaTrajeOd.requestFocus();
            mAkcijaTrajeOd.setFocus();
//            mAkcijaTrajeOd.grabFocus();

        }
    }//GEN-LAST:event_mSifraKeyPressed

    private void mMaloprodajnaCenaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mMaloprodajnaCenaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mMaloprodajnaCenaActionPerformed

    private void mSifraCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_mSifraCaretUpdate
        // TODO add your handling code here:
        if (mSifra.getText() != null && !mSifra.getText().isEmpty()) {
            try {
                Statement stmt = conn.createStatement();

                String sqlQuery = " select * from artikli where aktivan and sifra = "
                        + mSifra.getText();

                ResultSet rs = stmt.executeQuery(sqlQuery);

                while (rs.next()) {
                    mNaziv.setText(rs.getString("naziv"));

                }
            } catch (SQLException ex) {
                iMessage.setText(ex.toString());
                MyLogger.logger.error(ex.toString());
                mSifra.grabFocus();
                mSifra.requestFocus();
            }

            try {
                Statement stmt = conn.createStatement();

                String sqlQuery = " select * from cenovnik where aktivan and artikal_sifra = '"
                        + mSifra.getText() + "'";

                ResultSet rs = stmt.executeQuery(sqlQuery);

                while (rs.next()) {
                    mMaloprodajnaCena.setValue(new AsoftDFP(rs.getDouble("maloprodajna_cena")));
                }
            } catch (SQLException ex) {
                iMessage.setText(ex.toString());
                MyLogger.logger.error(ex.toString());
                mSifra.grabFocus();
                mSifra.requestFocus();
            }

//            mAkcijaTrajeOd.requestFocus();
//            mAkcijaTrajeOd.setFocus();
//            mAkcijaTrajeOd.grabFocus();   
        }
    }//GEN-LAST:event_mSifraCaretUpdate

    private void filter(String query) {
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>((DefaultTableModel) tblSifre.getModel());
        tblSifre.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(query));
    }

    private void PripremaTabeleAkcije(String sifra) {

        akcijeArray.clear();

        try {

            String sqlQuery = " select * from akcije where artikal_sifra like  '%"
                    + sifra + "%'" + " and aktivan order by datum_akcije_od desc";

            PreparedStatement stmt = conn.prepareStatement(sqlQuery);
            ResultSet rs = stmt.executeQuery(sqlQuery);

            if (uzmiParametar(conn, "debugiranje").equals("1")) {
                System.out.println(sqlQuery);
            }

            while (rs.next()) {

                model.Akcije akcije = new model.Akcije();
                akcije.setId(rs.getInt("id"));
                akcije.setArtikalId(rs.getInt("artikal_id"));
                akcije.setArtikalSifra(rs.getString("artikal_sifra"));
                akcije.setDatumAkcijeOd(rs.getDate("datum_akcije_od"));
                akcije.setDatumAkcijeDo(rs.getDate("datum_akcije_do"));

                akcije.setMpcena(rs.getBigDecimal("mpcena"));
                akcije.setMpcenaAkcijska(rs.getBigDecimal("mpcena_akcijska"));
                akcije.setAktivan(rs.getBoolean("aktivan"));

                mAkcijskaCena.setValue(new AsoftDFP(rs.getDouble("mpcena_akcijska")));
                mMaloprodajnaCena.setValue(new AsoftDFP(rs.getDouble("mpcena")));

                try {
                    mAkcijaTrajeOd.setDate(rs.getDate("datum_akcije_od"));
                    mAkcijaTrajeDo.setDate(rs.getDate("datum_akcije_do"));
                } catch (ParseException ex) {
                    MyLogger.logger.info(ex.toString());
                    iMessage.setText(ex.toString());
                }

                akcijeArray.add(akcije);
            }

        } catch (SQLException ex) {
            MyLogger.logger.info(ex.toString());
            iMessage.setText(ex.toString());
        }

//        FillListPodaciOCenama();
        DefaultTableModel model = new DefaultTableModel();

//        DefaultTableModel model = (DefaultTableModel) jTableCene.getModel();
        model.setColumnIdentifiers(new Object[]{"Id", "Sifra", "Datum od", "Datum do", "Maloprodajna cena",
            "Akcijska cena", "Aktivan", "Rbr"});
        TableColumnModel tcm = tblSifre.getColumnModel();

        tcm.getColumn(0).setPreferredWidth(20);     //Name
        tcm.getColumn(1).setPreferredWidth(120);    //Title
        tcm.getColumn(2).setPreferredWidth(350);    //Surname
        tcm.getColumn(3).setPreferredWidth(50);    //ID
        tcm.getColumn(4).setPreferredWidth(50);    //ID
        tcm.getColumn(5).setPreferredWidth(50);    //ID
        tcm.getColumn(6).setPreferredWidth(50);    //ID
        tcm.getColumn(7).setPreferredWidth(50);    //ID
//        tcm.getColumn(8).setPreferredWidth(50);    //ID
//        tcm.getColumn(9).setPreferredWidth(50);    //ID
//        tcm.getColumn(10).setPreferredWidth(50);    //ID

        //         
        rekordaUslektu = 0;

        Object[] row = new Object[11];
        for (int i = 0; i < akcijeArray.size(); i++) {
            row[0] = akcijeArray.get(i).getId();
//            row[1] = cenovnikArray.get(i).getSifra();
            row[1] = akcijeArray.get(i).getArtikalSifra();
            row[2] = akcijeArray.get(i).getDatumAkcijeOd();
            row[3] = akcijeArray.get(i).getDatumAkcijeDo();
            row[4] = akcijeArray.get(i).getMpcena();
            row[5] = akcijeArray.get(i).getMpcenaAkcijska();

            row[6] = akcijeArray.get(i).getAktivan();
            row[7] = rekordaUslektu;
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
//        tblSifre.gmAkcijskaCenatColumn(4).setCellRenderer(rightRenderer);      
        tblSifre.getColumnModel().getColumn(4).setCellRenderer(new CustomRendererRight(1));

        tblSifre.getColumnModel().getColumn(5).setCellRenderer(new CustomRendererRight(1));
//        tblSifre.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);        
        tblSifre.getColumnModel().getColumn(6).setCellRenderer(new CustomRendererRight(1));
//        tblSifre.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);        
        tblSifre.getColumnModel().getColumn(7).setCellRenderer(new CustomRendererRight(1));

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
            java.util.logging.Logger.getLogger(Akcije.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Akcije.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Akcije.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Akcije.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new Akcije().setVisible(true);
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
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
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
    private org.asoft.library.picker.AsoftHistoryDatePicker mAkcijaTrajeDo;
    private org.asoft.library.picker.AsoftHistoryDatePicker mAkcijaTrajeOd;
    private org.asoft.library.AsoftNumericField mAkcijskaCena;
    private javax.swing.JCheckBox mAktivan;
    private javax.swing.JTextField mId;
    private org.asoft.library.AsoftNumericField mMaloprodajnaCena;
    private javax.swing.JTextField mNaziv;
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

        mAkcijaTrajeOd.initPicker("mAkcijaOd", null);
        mAkcijaTrajeDo.initPicker("mAkcijaDo", null);
        try {
            mAkcijaTrajeOd.setDate(AsoftDate.EMPTY_DATE);
            mAkcijaTrajeDo.setDate(AsoftDate.EMPTY_DATE);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, ex);
            Main.track.error(" ParseException " + ex);
        }

        mNaziv.setText("");
        mSifra.setText("");

        mAkcijskaCena.setValue(NULA);
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

        mAkcijaTrajeOd.initPicker("mAkcijaOd", null);
        mAkcijaTrajeDo.initPicker("mAkcijaDo", null);
        try {
            mAkcijaTrajeOd.setDate(AsoftDate.EMPTY_DATE);
            mAkcijaTrajeDo.setDate(AsoftDate.EMPTY_DATE);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, ex);
            Main.track.error(" ParseException " + ex);
        }

        mNaziv.setText("");
//        mSifra.setText("");

        mAkcijskaCena.setValue(NULA);
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
    
        private boolean DaLiPostojiAkcijaSaDatumom(String sifra, Date sqlDate) {
        try {
            Statement stmt = conn.createStatement();

            String sqlQuery = " select * from akcije where artikal_sifra = '" + sifra + "' and  " + " datum_akcije_od = '"
                    + sqlDate + "'";

            ResultSet rs = stmt.executeQuery(sqlQuery);

            while (rs.next()) {
//                mNaziv.setText(rs.getString("artikal_sifra"));
                MyLogger.logger.error("Postoje akcije sa traženim datumom ...");
                iMessage.setText("Postoje akcije sa traženim datumom ...");
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
