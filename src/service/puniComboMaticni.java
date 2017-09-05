/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author ms
 */
public class puniComboMaticni {

    static ArrayList myList = new ArrayList();

    public static ArrayList puni_maticne(Connection conn, String tabela) {

        myList.clear();
        String rq1 = "SELECT naziv from " + tabela + " where aktivan order by sifra";

        try {
            Statement st = conn.createStatement();
            ResultSet rs1 = st.executeQuery(rq1);
            while ((rs1.next())) {
                myList.add(rs1.getString("naziv"));
            }
            st.close();
            rs1.close();            
        } catch (SQLException e) {
        }
        return myList;
}
}
