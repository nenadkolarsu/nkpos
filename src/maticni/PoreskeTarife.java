package maticni;

import forms_pos.Main;
import forms_pos.Prijava;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import static org.asoft.library.AsoftDFP.NULA;

/**
 *
 * @author ms
 */
public class PoreskeTarife extends javax.swing.JFrame {

    /**
     * Creates new form MaticniPodaci
     */
    Connection conn;
    String tabela;
    int rekordaUslektu = 0;

    
    public PoreskeTarife() {
        
        initComponents();
        setLocationRelativeTo(null);
        konekcija n = new konekcija();
        conn = n.konekcija();
        tabela = "poreska_tarifa";
        punjenjeJtable();
    }
    
    public PoreskeTarife(final Connection conn, final String tabela) {
        
        this.conn = conn;
        this.tabela = tabela;
           
        initComponents();
        this.setTitle(tabela);

        setLocationRelativeTo(null);

        punjenjeJtableIPrikazModelaSaUslovom(filterTxt.getText());
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
                  Main.track.info("this is a information log message" + ex);                                    
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

                        int mmAktivan = rs.getInt("aktivan");
                        if (mmAktivan == 1) {
                            mAktivan.setSelected(true);
                        } else {
                            mAktivan.setSelected(false);
                        }
                        recordLbl.setText(rs.getString(1));
                    }
                } catch (SQLException ex) {
                    iMessage.setText(ex.toString());
                    Main.track.info("table" + tabela + ex);
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
                    iMessage.setText(ex.toString());
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

//                pos++;
                if (pos < ListSifre("").size()) {
                    ShowPosInfo(pos, " order by id asc ");
                } else {
                    pos = ListSifre("").size() - 1;
                    ShowPosInfo(pos, " order by id asc ");
//                    JOptionPane.showMessageDialog(null, " END ");
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
                    iMessage.setText(ex.toString());
                    Main.track.info("this is a information log message" + ex);
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
        lTabela = new javax.swing.JLabel();
        mAktivan = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        mNaziv = new javax.swing.JTextField();
        mSifra = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        mId = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        mProcenat = new org.asoft.library.AsoftNumericField();
        jLabel7 = new javax.swing.JLabel();
        mFiksno = new org.asoft.library.AsoftNumericField();
        jLabel8 = new javax.swing.JLabel();
        mNapomena = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        mPDV = new javax.swing.JCheckBox();
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

        jLabel6.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel6.setText("Procenat:");

        mProcenat.setText("0.00");
        mProcenat.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        mProcenat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mProcenatActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel7.setText("Fiksno:");

        mFiksno.setText("0.00");
        mFiksno.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        mFiksno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mFiksnoActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel8.setText("Napomena:");

        mNapomena.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        mNapomena.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mNapomenaActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel9.setText("PDV:");

        mPDV.setSelected(true);
        mPDV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mPDVActionPerformed(evt);
            }
        });

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
                    .addComponent(jLabel4)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(mProcenat, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)
                        .addGap(23, 23, 23)
                        .addComponent(mFiksno, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(mNapomena))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(mAktivan)
                        .addGap(35, 35, 35)
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(mPDV)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(mNaziv)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(mId, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(mSifra, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lTabela, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(22, 22, 22))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 29, Short.MAX_VALUE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(mProcenat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(mFiksno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(mNapomena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mAktivan)
                    .addComponent(jLabel4)
                    .addComponent(jLabel9)
                    .addComponent(mPDV)))
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
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
        mNaziv.requestFocus();
    }//GEN-LAST:event_mSifraActionPerformed

    private void UnosBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UnosBtnActionPerformed
        iMessage.setText("");
        DefaultTableModel model = (DefaultTableModel) tblSifre.getModel();
        
        if (!mSifra.getText().trim().equals("")) {
            try {
                
                Statement stmt = conn.createStatement();

                String sqlQuery = " insert into " + tabela + "(sifra, naziv, aktivan, "
                        + "pdv, napomena, procenat, fiksno "
                        + ") "
                        + "values ('" + mSifra.getText() + "', '"
                        + mNaziv.getText() + "', '" 
                        + vratiJedanIliNula(mAktivan.isSelected()) + "', '" 
                        + vratiJedanIliNula(mPDV.isSelected()) + "', '" 
                        + mNapomena.getText() + "', '" 
                        + mProcenat.getValueDFP().doubleValue() + "', '" 
                        + mFiksno.getValueDFP().doubleValue() 
                        + "')";

//                System.out.println(sqlQuery);
                stmt.executeUpdate(sqlQuery);
                //    conn.commit();

//                JOptionPane.showMessageDialog(null, "Upisano:");
            } catch (Exception e) {
                try {
                    iMessage.setText(e.toString());
                    conn.rollback();
                } catch (SQLException ex) {
                Main.track.info("Rollback " + tabela + " SQLException " + ex);
                iMessage.setText(ex.toString());
                }
                Main.track.info("Insert " + tabela + " SQLException " + e);
                iMessage.setText(e.toString());
            } finally {
//      if (rset != null) { try { rset.close();  rset = null; } catch(Exception e){e.printStackTrace();} }
//      if (stmt != null) { try { stmt.close();  stmt = null; } catch(Exception e){e.printStackTrace();} }
            }
            model.addRow(new Object[]{mId.getText(), mSifra.getText(), mNaziv.getText(), mAktivan.isSelected()});
            prazniEkran();
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
                iMessage.setText("Morate izabrati rekord");
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
                        + "', napomena='" + mNapomena.getText()
                        + "', procenat='" + mProcenat.getValueDFP().doubleValue()
                        + "', fiksno='" + mFiksno.getValueDFP().doubleValue()
                        + "', aktivan='" + vratiJedanIliNula(mAktivan.isSelected())
                        + "', pdv='" + vratiJedanIliNula(mPDV.isSelected())
                        + "' WHERE id = '" + mId.getText()
                        + "'";

//                System.out.println(sqlQuery);
                stmt.execute(sqlQuery);
                iMessage.setText("Podatak je izmenjen.");
                Main.track.info("Podatak je izmenjen " );

            } catch (SQLException ex) {
                Main.track.info("Update table SQLException " + ex);
                iMessage.setText(ex.toString());
            }
        }
    }//GEN-LAST:event_IzmenaBtnActionPerformed

    private void tblSifreMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSifreMouseClicked
        int index = tblSifre.getSelectedRow();
        iMessage.setText("");
        TableModel model = tblSifre.getModel();
        
        String id = model.getValueAt(index, 0).toString();
        
        try {
            Statement stmt = conn.createStatement();
            String sqlQuery = " select id, sifra, naziv, procenat, fiksno, napomena, "
                    + " pdv, aktivan from " + tabela + " where id like '" + id
                    + "'";
            ResultSet rs = stmt.executeQuery(sqlQuery);
            
            while (rs.next()) {
                mId.setText(rs.getString(1));
                mSifra.setText(rs.getString(2));
                mNaziv.setText(rs.getString(3));
                mNapomena.setText(rs.getString("napomena"));
                mProcenat.setValue(rs.getDouble("procenat"));
                mFiksno.setValue(rs.getDouble("fiksno"));
                int mmAktivan = rs.getInt("aktivan");
                if (mmAktivan == 1) {
                    mAktivan.setSelected(true);
                } else {
                    mAktivan.setSelected(false);
                }
                int mmPDV = rs.getInt("pdv");
                if (mmPDV == 1) {
                    mPDV.setSelected(true);
                } else {
                    mPDV.setSelected(false);
                }
                
            }
        } catch (Exception ex) {
            iMessage.setText(ex.toString());
            Main.track.info("Mouse click on table Exception " + ex);
        }


    }//GEN-LAST:event_tblSifreMouseClicked
    public ArrayList<model.MaticniPodaci> ListSifre(String ValToSearch) {
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
    
    public void findUsers() {
        ArrayList<model.MaticniPodaci> users = ListSifre(filterTxt.getText());

        DefaultTableModel model = (DefaultTableModel) tblSifre.getModel();
        // PRAZNI MODEL
        if (model.getRowCount() > 0) {
            for (int i = model.getRowCount() - 1; i > -1; i--) {
                model.removeRow(i);
            }
        }
        Object[] row = new Object[4];
        for (int i = 0; i < users.size(); i++) {
            row[0] = users.get(i).getId();
            row[1] = users.get(i).getSifra();
            row[2] = users.get(i).getNaziv();
            row[3] = users.get(i).getAktivan();
            model.addRow(row);
        }
        tblSifre.setModel(model);
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
                
                sqlQuery = " DELETE FROM " + tabela
                        + " WHERE id = '" + mId.getText()
                        + "'";
                sqlQuery = " UPDATE " + tabela
                        + " SET aktivan=0 WHERE id = '" + mId.getText()
                        + "'";

                int choice = JOptionPane.showOptionDialog(this,
                        "Da li ste sigurni da želite obrisati rekord?",
                        "Brisanje?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null, null, null);

                // interpret the user's choice
                if (choice == JOptionPane.YES_OPTION) {
                    stmt.execute(sqlQuery);
                    model.removeRow(tblSifre.getSelectedRow());
                    iMessage.setText("Rekord je obrisan.");
                    // FillList();
                }
                
            } catch (SQLException ex) {
                Main.track.info("Brisanje " + tabela + " SQLException " + ex);
                iMessage.setText(ex.toString());

            }
        }
    }//GEN-LAST:event_BrisanjeBtnActionPerformed

    private void RefreshBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshBtnActionPerformed
        filterTxt.setText("");
        iMessage.setText("");
        punjenjeJtableIPrikazModelaSaUslovom(filterTxt.getText());
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

        iMessage.setText("");        
        punjenjeJtableIPrikazModelaSaUslovom(filterTxt.getText());
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
                    java.util.logging.Logger.getLogger(PoreskeTarife.class.getName()).log(Level.SEVERE, null, ex);
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

    private void mProcenatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mProcenatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mProcenatActionPerformed

    private void mFiksnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mFiksnoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mFiksnoActionPerformed

    private void mNapomenaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mNapomenaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mNapomenaActionPerformed

    private void mPDVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mPDVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mPDVActionPerformed

    // filter data
    private void filter(String query) {
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>((DefaultTableModel) tblSifre.getModel());
        tblSifre.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(query));
    }
    
    private void punjenjeJtable() {
        
        lTabela.setText(" " + tabela);
        
        DefaultTableModel model = (DefaultTableModel) tblSifre.getModel();
        TableColumnModel tcm = tblSifre.getColumnModel();
        tcm.getColumn(0).setPreferredWidth(10);     //Name
        tcm.getColumn(1).setPreferredWidth(10);    //Title
        tcm.getColumn(2).setPreferredWidth(170);    //Surname
        tcm.getColumn(3).setPreferredWidth(5);    //ID
        tcm.getColumn(4).setPreferredWidth(5);    //ID
        tblSifre.getColumnModel().getColumn(0).setPreferredWidth(10);
        tblSifre.getColumnModel().getColumn(1).setPreferredWidth(10);
        tblSifre.getColumnModel().getColumn(2).setPreferredWidth(170);
        tblSifre.getColumnModel().getColumn(3).setPreferredWidth(5);
        tblSifre.getColumnModel().getColumn(4).setPreferredWidth(5);
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
                model.addRow(new Object[]{mId.getText(), mSifra.getText(), mNaziv.getText(), mmAktivan});
            }
            mId.setText("");
            mSifra.setText("");
            mNaziv.setText("");
            mAktivan.setSelected(true);
            
        } catch (SQLException ex) {
            Main.track.info("Punjenje Jtable " + tabela + " SQLException " + ex);
//            Logger.getLogger(SearchWithTab.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    // puni tabelu i model sa uslovom

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

        tblSifre.getColumnModel().getColumn(0).setCellRenderer(new CustomRenderer(0));
        tblSifre.getColumnModel().getColumn(1).setCellRenderer(new CustomRenderer(0));
        tblSifre.getColumnModel().getColumn(2).setCellRenderer(new CustomRenderer(0));
        tblSifre.getColumnModel().getColumn(3).setCellRenderer(new CustomRenderer(0));
        tblSifre.getColumnModel().getColumn(4).setCellRenderer(new CustomRenderer(0));
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
//            Logger.getLogger(MaticniPodaci.class.getName()).log(Level.SEVERE, null, ex);
//            obj.runMe("Exception First button ..... ");
            Main.track.info("punjenjeJtableIPrikazModelaSaUslovom " + tabela + " SQLException " + ex);
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
            java.util.logging.Logger.getLogger(PoreskeTarife.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PoreskeTarife.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PoreskeTarife.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PoreskeTarife.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new PoreskeTarife().setVisible(true);
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lTabela;
    private javax.swing.JCheckBox mAktivan;
    private org.asoft.library.AsoftNumericField mFiksno;
    private javax.swing.JTextField mId;
    private javax.swing.JTextField mNapomena;
    private javax.swing.JTextField mNaziv;
    private javax.swing.JCheckBox mPDV;
    private org.asoft.library.AsoftNumericField mProcenat;
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

    private void prazniEkran() {
            mId.setText("");
            mSifra.setText("");
            mNaziv.setText("");
            mNapomena.setText("");
            mProcenat.setValue(NULA);
            mFiksno.setValue(NULA);
            mAktivan.setSelected(true);
            mPDV.setSelected(true);
    }
}
