/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maticni.partneri;

/**
 *
 * @author ms
 */
//import Writesheet.KartonExcel1;
//import Writesheet.KartonExcelSvi;
import maticni.artikli.*;
import model.ArtikalKolicinaCena;
import java.awt.Font;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import konekcija.konekcija;
import maticni.CustomRenderer;
import maticni.cenovnik.Akcije;
import maticni.cenovnik.Cenovnik;
import model.Partneri;
//import maticni.CustomRenderer;
//import model.User;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import org.asoft.library.AsoftComboBox;
import service.MyLogger;
import static service.uzmiParametar.uzmiParametar;

public class TrazenjePartnera extends javax.swing.JFrame {

    private Connection conn;
    String mIzabraniId = null;
    String mIzabranaSifra = null;
    private String artikli;
    ArrayList<Partneri> akcList = new ArrayList<Partneri>();

    public TrazenjePartnera(Connection conn, String partner) {
        this.conn = conn;
        this.artikli = partner;
        initComponents();
        MyLogger.logger.info("Trazenje partnera");
        this.setTitle("Šifarnik partnera");
        jTable_Users.setFont(new Font("Verdana", Font.PLAIN, 14));
        setLocationRelativeTo(null);
//        mDatumRodj.initPicker("datdokMmiDorade", null);
//        konekcija n = new konekcija();
//        conn = n.konekcija();
        searchText.grabFocus();

//        FillList();
        puniTabelu();
    }

    /**
     * Creates new form Search
     */
    final void FillList() {

        try {
            Statement stmt = conn.createStatement();

            String sqlQuery = " select * from partneri ";
            ResultSet rs = stmt.executeQuery(sqlQuery);
            DefaultListModel DLM = new DefaultListModel();

            while (rs.next()) {
                DLM.addElement(rs.getString(1)
                        + AsoftComboBox.COMBOBOX_ITEM_SEPARATOR + rs.getString("sifra")
                        + AsoftComboBox.COMBOBOX_ITEM_SEPARATOR + rs.getString("naziv")
                        + AsoftComboBox.COMBOBOX_ITEM_SEPARATOR + rs.getString("napomena")
                );
            }

            List1.setModel(DLM);

        } catch (SQLException ex) {
            MyLogger.logger.error(ex.toString());
            iMessage.setText(ex.toString());
        }

    }

    public TrazenjePartnera() {

        initComponents();
        jTable_Users.setFont(new Font("Verdana", Font.PLAIN, 14));
        setLocationRelativeTo(null);
//        mDatumRodj.initPicker("datdokMmiDorade", null);
        konekcija n = new konekcija();
        conn = n.konekcija();
        searchText.grabFocus();
//        List<String> list = puniComboPtt.puni_ptt(conn);
//        mMestoRodj.setModel(new DefaultComboBoxModel(list.toArray()));
//        List<String> listDrzavljanstvo = puniComboDrzavljanstvo.puni_ptt(conn);
//        mDrzavljanstvo.setModel(new DefaultComboBoxModel(listDrzavljanstvo.toArray()));
//        List<String> listNacionalnost = puniComboNacionalnost.puni_ptt(conn);
//        mNacionalnost.setModel(new DefaultComboBoxModel(listNacionalnost.toArray()));

//        FillList();
        puniTabelu();

    }

    public ArrayList<Partneri> ListaPartnera(String ValToSearch) {

        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();

            String searchQuery = "SELECT *, ptt_brojevi.naziv AS ptt, drzave.naziv AS drz FROM `partneri` "
                    + " LEFT JOIN ptt_brojevi ON ptt_brojevi.id = partneri.ptt_broj_id "
                    + " and ptt_brojevi.aktivan "
                    + " LEFT JOIN drzave ON drzave.id = partneri.zemlja_id and drzave.aktivan "
                    + " WHERE "
                    + " CONCAT(partneri.id, partneri.sifra, partneri.naziv) LIKE '%" 
                    + ValToSearch + "%'"
                    + " and partneri.aktivan=1 "
                    + " order by partneri.sifra "; // and cenovnik.aktivan=1 and zaliha.aktivan=1 

            if (uzmiParametar(conn, "debugiranje").equals("1")) {
                System.out.println(searchQuery);
            }

            rs = st.executeQuery(searchQuery);

            Partneri user;
            user = new Partneri();
            akcList.clear();
            String msifra = "-";

            while (rs.next()) {
                if (!msifra.equals(rs.getString("sifra"))) {
                    msifra = rs.getString("sifra");
                    user = new Partneri(
                            rs.getInt("id"), 
                            rs.getString("sifra"),
                            rs.getString("naziv"),  
                            rs.getString("adresa"),
                            rs.getString("ptt"),
                            rs.getString("drz"),
                            rs.getBoolean("aktivan")
                    );
                    akcList.add(user);
                }
            }
        } catch (Exception ex) {
            MyLogger.logger.error(ex.toString());
            iMessage.setText(ex.toString());
        }
        return akcList;
    }

    public void puniTabelu() {

        ArrayList<Partneri> artikli = ListaPartnera(searchText.getText());
        DefaultTableModel model = new DefaultTableModel();

//        DefaultTableModel model = (DefaultTableModel) jTable_Users.getModel();
        TableColumnModel tcm = jTable_Users.getColumnModel();

        tcm.getColumn(0).setPreferredWidth(20);     //Name
        tcm.getColumn(1).setPreferredWidth(120);    //Title
        tcm.getColumn(2).setPreferredWidth(250);    //Surname
        tcm.getColumn(3).setPreferredWidth(50);    //ID
        tcm.getColumn(4).setPreferredWidth(50);    //ID
        tcm.getColumn(5).setPreferredWidth(50);    //ID
        tcm.getColumn(6).setPreferredWidth(50);    //ID
        //         

        model.setColumnIdentifiers(new Object[]{"Id", "Sifra", "Naziv", 
            "Adresa", "Mesto", "Zemlja", "Aktivan"});
        Object[] row = new Object[7];
        for (int i = 0; i < artikli.size(); i++) {
            row[0] = artikli.get(i).getId();
            row[1] = artikli.get(i).getSifra();
            row[2] = artikli.get(i).getNaziv();
            row[3] = artikli.get(i).getAdresa();
            row[4] = artikli.get(i).getPttBroj(); 
            row[5] = artikli.get(i).getZemlja(); 
//            row[3] = artikli.get(i).getKolicina();
//            row[4] = artikli.get(i).getMaloprodajna_cena();
            row[6] = artikli.get(i).getAktivan();
            model.addRow(row);
        }

        jTable_Users.setModel(model);

        jTable_Users.getColumnModel().getColumn(0).setCellRenderer(new CustomRenderer(1));
        jTable_Users.getColumnModel().getColumn(1).setCellRenderer(new CustomRenderer(1));
        jTable_Users.getColumnModel().getColumn(2).setCellRenderer(new CustomRenderer(1));
        jTable_Users.getColumnModel().getColumn(3).setCellRenderer(new CustomRenderer(1));
        jTable_Users.getColumnModel().getColumn(4).setCellRenderer(new CustomRenderer(1));
        jTable_Users.getColumnModel().getColumn(5).setCellRenderer(new CustomRenderer(1));
        jTable_Users.getColumnModel().getColumn(6).setCellRenderer(new CustomRenderer(1));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        List1 = new javax.swing.JList();
        jPanel2 = new javax.swing.JPanel();
        searchText = new javax.swing.JTextField();
        searchBtn = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable_Users = new javax.swing.JTable();
        iMessage = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        UpdateBtn = new javax.swing.JButton();
        DeleteBtn = new javax.swing.JButton();
        IzlazBtn = new javax.swing.JButton();
        AddBtn = new javax.swing.JButton();
        PregledBtn = new javax.swing.JButton();
        BtnRefresh = new javax.swing.JButton();
        btnCenovnik = new javax.swing.JButton();
        btnPakovanja = new javax.swing.JButton();

        List1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                List1ValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(List1);

        searchText.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                searchTextCaretUpdate(evt);
            }
        });

        searchBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/Zoom-icon-32.png"))); // NOI18N
        searchBtn.setText("Traženje");
        searchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBtnActionPerformed(evt);
            }
        });

        jTable_Users.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable_Users.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_UsersMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable_Users);

        iMessage.setBackground(new java.awt.Color(153, 204, 255));
        iMessage.setForeground(new java.awt.Color(255, 0, 0));
        iMessage.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(searchText, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(searchBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(iMessage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(iMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        UpdateBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/Actions-document-edit-icon.png"))); // NOI18N
        UpdateBtn.setText("Izmena");
        UpdateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateBtnActionPerformed(evt);
            }
        });

        DeleteBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/delete-file-icon.png"))); // NOI18N
        DeleteBtn.setText("Brisanje");
        DeleteBtn.setEnabled(false);
        DeleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteBtnActionPerformed(evt);
            }
        });

        IzlazBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/Button-Close-icon-32.png"))); // NOI18N
        IzlazBtn.setText("Izlaz");
        IzlazBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IzlazBtnActionPerformed(evt);
            }
        });

        AddBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/Files-New-File-icon.png"))); // NOI18N
        AddBtn.setText("Unos");
        AddBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddBtnActionPerformed(evt);
            }
        });

        PregledBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/print-icon-48.png"))); // NOI18N
        PregledBtn.setText("Pregled");
        PregledBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PregledBtnActionPerformed(evt);
            }
        });

        BtnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/Button-Refresh-icon-32.png"))); // NOI18N
        BtnRefresh.setText("Refresh");
        BtnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRefreshActionPerformed(evt);
            }
        });

        btnCenovnik.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/price-tag-icon-48.png"))); // NOI18N
        btnCenovnik.setText("Cenovnik");
        btnCenovnik.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCenovnikActionPerformed(evt);
            }
        });

        btnPakovanja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone_nove/Barcode-icon-48.png"))); // NOI18N
        btnPakovanja.setText("Pakovanja");
        btnPakovanja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPakovanjaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(AddBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(UpdateBtn)
                .addGap(16, 16, 16)
                .addComponent(DeleteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(PregledBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(BtnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPakovanja)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCenovnik)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(IzlazBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {DeleteBtn, UpdateBtn});

        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(UpdateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DeleteBtn)
                    .addComponent(IzlazBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AddBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PregledBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPakovanja, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCenovnik, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 42, Short.MAX_VALUE))
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {DeleteBtn, UpdateBtn});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void List1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_List1ValueChanged
//        try { 
//            Statement stmt = conn.createStatement();
//            // String[] array = yourString.split(wordSeparator);
//            String aa = (String) List1.getSelectedValue();
//            String[] niz = aa.split(AsoftComboBox.COMBOBOX_ITEM_SEPARATOR);
//
////            String sqlQuery = " select * from licni_podaci where id like '" + niz[0]
////                    + "' and jmbg like '" + niz[1]
////                    + "' and ime like '" + niz[2] + "'";
//            String sqlQuery = " select * from licni_podaci where id like '" + niz[0]
//                    + "'";
//
//            ResultSet rs = stmt.executeQuery(sqlQuery);

//            while (rs.next()) {
//                mId.setText(rs.getString(1));
//                mJmbg.setText(rs.getString(2));
//                mIme.setText(rs.getString(3));
//                mPrezime.setText(rs.getString(4));
//                mImeOca.setText(rs.getString(5));
//                mImeMajke.setText(rs.getString(6));
//                Date a = rs.getDate(9);
//                mDatumRodj.setDate(a);
//                try {
//                    mDatumRodj.setDate(rs.getDate(9));
//                } catch (ParseException ex) {
//                    Logger.getLogger(TrazenjePersone.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                String ddd = rs.getString("mestorodj");
//                mMestoRodj.setSelectedItem(rs.getString("mestorodj"));
//
//            }
//
//        } catch (SQLException ex) {
//            Logger.getLogger(TrazenjePersone.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ParseException ex) {
//            Logger.getLogger(TrazenjePersone.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }//GEN-LAST:event_List1ValueChanged

    private void UpdateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateBtnActionPerformed

        int index = jTable_Users.getSelectedRow();
        TableModel model = jTable_Users.getModel();
        mIzabraniId = model.getValueAt(index, 0).toString();

        PartneriAzuriranje aP = new PartneriAzuriranje(conn, "IZMENA", mIzabraniId, this);
        aP.setVisible(true);
        
//        ArtikliAzuriranje aP = new ArtikliAzuriranje(conn, "IZMENA", mIzabraniId);
//        aP.setVisible(true);

//        Statement stmt = null;
//        try {
//            stmt = conn.createStatement();
//
//            String sqlQuery = null;
//
//            try {
//                sqlQuery = " UPDATE licni_podaci SET ime='"
//                        + mIme.getText()
//                        // + asoftFormattedTextField1.getText()
//                        + "', prezime='" + mPrezime.getText()
//                        + "', jmbg='" + mJmbg.getText()
//                        + "', ime_oca='" + mImeOca.getText()
//                        + "', ime_majke='" + mImeMajke.getText()
//                        + "', datumrodj='" + mDatumRodj.getSQLDate()
//                        + "', mestorodj='" + mMestoRodj.getSelectedItem()
//                        + "' WHERE id = '" + mId.getText()
//                        + "'";
//            } catch (ParseException ex) {
//                Logger.getLogger(TrazenjePersone.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            System.out.println(sqlQuery);
//            stmt.execute(sqlQuery);
//            FillList();
//
//        } catch (SQLException ex) {
//            Logger.getLogger(TrazenjePersone.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }//GEN-LAST:event_UpdateBtnActionPerformed

    private void DeleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteBtnActionPerformed
//        Statement stmt = null;
//        try {
//            stmt = conn.createStatement();
//
//            String sqlQuery = null;
//
//            sqlQuery = " DELETE FROM licni_podaci "
//                    + " WHERE id = '" + mId.getText()
//                    + "'";
//            System.out.println(sqlQuery);
//            // display the showOptionDialog
//            int choice = JOptionPane.showOptionDialog(this,
//                    "Da li ste sigurni u brisanje?",
//                    "Brisanje?",
//                    JOptionPane.YES_NO_OPTION,
//                    JOptionPane.QUESTION_MESSAGE,
//                    null, null, null);
//
//            // interpret the user's choice
//            if (choice == JOptionPane.YES_OPTION) {
//                stmt.execute(sqlQuery);
//                FillList();
//            }
//
//        } catch (SQLException ex) {
//            Logger.getLogger(TrazenjePersone.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }//GEN-LAST:event_DeleteBtnActionPerformed

    private void searchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBtnActionPerformed

//        int result = List1.getNextMatch(searchText.getText(), 0, Position.Bias.Forward);
//        List1.setSelectedIndex(result);
        puniTabelu();
        //   http://stackoverflow.com/questions/12496038/searching-in-a-arraylist-with-custom-objects-for-certain-strings
    }//GEN-LAST:event_searchBtnActionPerformed

    private void jTable_UsersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_UsersMouseClicked

        int index = jTable_Users.getSelectedRow();
        TableModel model = jTable_Users.getModel();

        mIzabraniId = model.getValueAt(index, 0).toString();
        String id = model.getValueAt(index, 0).toString();
//        String firstName = model.getValueAt(index, 1).toString();
//        String lastName = model.getValueAt(index, 2).toString();
//        String age = model.getValueAt(index, 3).toString();

//        jtRowData.setVisible(true);
//        jtRowData.pack();
//        jtRowData.setLocationRelativeTo(null);
//        jtRowData.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//
//        jtRowData.jTextField_Id.setText(id);
//        jtRowData.jTextField_FirstName.setText(firstName);
//        jtRowData.jTextField_LastName.setText(lastName);
//        jtRowData.jTextField_Age.setText(age);
        try {
            Statement stmt = conn.createStatement();
            // String[] array = yourString.split(wordSeparator);
//            String aa = (String) List1.getSelectedValue();
//            String[] niz = aa.split(AsoftComboBox.COMBOBOX_ITEM_SEPARATOR);

//            String sqlQuery = " select * from licni_podaci where id like '" + niz[0]
//                    + "' and jmbg like '" + niz[1]
//                    + "' and ime like '" + niz[2] + "'";
            String sqlQuery = " select * from artikli where id like '" + id
                    + "'";

            ResultSet rs = stmt.executeQuery(sqlQuery);

//            while (rs.next()) {
//                mId.setText(rs.getString(1));
//                mJmbg.setText(rs.getString(2));
//                mIme.setText(rs.getString(3));
//                mPrezime.setText(rs.getString(4));
//                mImeOca.setText(rs.getString(5));
//                mImeMajke.setText(rs.getString(6));
//                Date a = rs.getDate(9);
//                mDatumRodj.setDate(a);
//                try {
//                    mDatumRodj.setDate(rs.getDate(9));
//                } catch (ParseException ex) {
//                    Logger.getLogger(TrazenjePersone.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                String ddd = rs.getString("mestorodj");
//                mMestoRodj.setSelectedItem(rs.getString("mestorodj"));
//                mDrzavljanstvo.setSelectedItem(rs.getString("drzavljanstvo"));
//                mNacionalnost.setSelectedItem(rs.getString("nacionalnost"));
//                mTelefon.setText(rs.getString("telefon"));
//                mEmail.setText(rs.getString("e_mail"));
//
//            }
        } catch (SQLException ex) {
            Logger.getLogger(TrazenjePartnera.class.getName()).log(Level.SEVERE, null, ex);
        }

// Read more at http://1bestcsharp.blogspot.com/2016/01/java-display-jtable-row-data-jtextfields.html#ZkGiDSd8vE40YBKb.99
// Read more at http://1bestcsharp.blogspot.com/2016/01/java-display-jtable-row-data-jtextfields.html#ZkGiDSd8vE40YBKb.99

// Read more at http://1bestcsharp.blogspot.com/2016/01/java-display-jtable-row-data-jtextfields.html#ZkGiDSd8vE40YBKb.99
// Read more at http://1bestcsharp.blogspot.com/2016/01/java-display-jtable-row-data-jtextfields.html#ZkGiDSd8vE40YBKb.99
    }//GEN-LAST:event_jTable_UsersMouseClicked

    private void IzlazBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IzlazBtnActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_IzlazBtnActionPerformed

    private void searchTextCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_searchTextCaretUpdate
        // TODO add your handling code here:
        puniTabelu();
    }//GEN-LAST:event_searchTextCaretUpdate

    private void AddBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddBtnActionPerformed
        // TODO add your handling code here:
        PartneriAzuriranje aP = new PartneriAzuriranje(conn, "UNOS", "", this);
        aP.setVisible(true);
    }//GEN-LAST:event_AddBtnActionPerformed

    private void PregledBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PregledBtnActionPerformed
        // TODO add your handling code here:
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                try {
                    Map parameters = new HashMap();
                    parameters.put("id", mIzabraniId);
                    String reportPath = service.uzmiParametar.uzmiParametar(conn, "putanja_izvestaja").concat("karton1004.jrxml");
//                    String reportPath = "C:\\Users\\ms\\Documents\\NetBeansProjects\\gui\\src\\report\\karton1004.jrxml";
                    JasperReport jr = JasperCompileManager.compileReport(reportPath);
                    JasperPrint jp = JasperFillManager.fillReport(jr, parameters, conn);
                    JasperViewer.viewReport(jp, false);
                } catch (JRException ex) {

                    MyLogger.logger.error(ex.toString());
                    iMessage.setText(ex.toString());
                }
            }
        });
        t1.start();
    }//GEN-LAST:event_PregledBtnActionPerformed

    private void BtnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRefreshActionPerformed
        // TODO add your handling code here:
        puniTabelu();
        
//        Thread t1 = new Thread(new Runnable() {
//            public void run() {
//                
//
//                int index = jTable_Users.getSelectedRow();
//                TableModel model = jTable_Users.getModel();
//                
//                if (index == -1) {
//                    Akcije cen = new Akcije();
//                    cen.setVisible(true);
//                } else {
//                    mIzabranaSifra = model.getValueAt(index, 1).toString();
//                    Akcije cen = new Akcije(conn, "akcije", mIzabranaSifra);
//                    cen.setVisible(true);
//                }                
//
//            }
//        });
//        t1.start();
    }//GEN-LAST:event_BtnRefreshActionPerformed

    private void btnCenovnikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCenovnikActionPerformed
        // TODO add your handling code here:
        int index = jTable_Users.getSelectedRow();
        TableModel model = jTable_Users.getModel();

        if (index == -1) {
            Cenovnik cen = new Cenovnik();
            cen.setVisible(true);
        } else {
            mIzabranaSifra = model.getValueAt(index, 1).toString();
            Cenovnik cen = new Cenovnik(conn, "cenovnik", mIzabranaSifra);
            cen.setVisible(true);
        }

    }//GEN-LAST:event_btnCenovnikActionPerformed

    private void btnPakovanjaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPakovanjaActionPerformed
        // TODO add your handling code here:
        int index = jTable_Users.getSelectedRow();
        TableModel model = jTable_Users.getModel();
        
        if (index == -1) {
            Pakovanja cen = new Pakovanja();
            cen.setVisible(true);
        } else {
            mIzabranaSifra = model.getValueAt(index, 1).toString();
            Pakovanja cen = new Pakovanja(conn, "artikli_pakovanja", mIzabranaSifra);
            cen.setVisible(true);
        }        
    }//GEN-LAST:event_btnPakovanjaActionPerformed

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
            java.util.logging.Logger.getLogger(TrazenjePartnera.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            MyLogger.logger.error(ex.toString());

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TrazenjePartnera.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            MyLogger.logger.error(ex.toString());

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TrazenjePartnera.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            MyLogger.logger.error(ex.toString());

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TrazenjePartnera.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            MyLogger.logger.error(ex.toString());

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
                new TrazenjePartnera().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddBtn;
    private javax.swing.JButton BtnRefresh;
    private javax.swing.JButton DeleteBtn;
    private javax.swing.JButton IzlazBtn;
    private javax.swing.JList List1;
    private javax.swing.JButton PregledBtn;
    private javax.swing.JButton UpdateBtn;
    private javax.swing.JButton btnCenovnik;
    private javax.swing.JButton btnPakovanja;
    private javax.swing.JLabel iMessage;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable_Users;
    private javax.swing.JButton searchBtn;
    private javax.swing.JTextField searchText;
    // End of variables declaration//GEN-END:variables

//class CustomRenderer extends DefaultTableCellRenderer 
//{ 
//private static final long serialVersionUID = 6703872492730589499L;
//
//    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
//    {
//        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//
//        if(row == 0){
//            cellComponent.setBackground(Color.YELLOW);
//        } else if ( row == 1){
//            cellComponent.setBackground(Color.GRAY);
//        } else {
//            cellComponent.setBackground(Color.CYAN);
//        }
//        return cellComponent;
//    }
//}
}
