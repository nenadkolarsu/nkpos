/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
//import org.asoft.library.AsoftComboBox;

/**
 *
 * @author ms
 */
public class puniComboPtt {

    static ArrayList myList = new ArrayList();

    public static ArrayList puni_ptt(Connection conn) {

        myList.clear();
        String rq1 = "SELECT sifra , naziv from ptt_brojevi where aktivan order by naziv";

        try {

            Statement st = conn.createStatement();
            ResultSet rs1 = st.executeQuery(rq1);
//            myList.add("");
            while ((rs1.next())) {
//                myList.add(rs1.getString(1) + AsoftComboBox.COMBOBOX_ITEM_SEPARATOR + rs1.getString(2) );
                myList.add(rs1.getString(2) );
//                comboBox_gouver.addItem(rs1.getString(1));
//                comboBox_ACTELS.addItem(rs2.getString(1));
            }
            st.close();
            rs1.close();
//               rs2.close();
//            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return myList;

}
}
