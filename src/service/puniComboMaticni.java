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
import org.asoft.library.AsoftTaskData;
import org.asoft.library.entiteti.AsoftTabele;
import org.asoft.library.entiteti.Postanski_brojTable;
import org.asoft.library.entiteti.ZemljaTable;

/**
 *
 * @author ms
 */
public class puniComboMaticni {

    static ArrayList myList = new ArrayList();

    public static ArrayList puni_maticne(AsoftTaskData td, String tabela) throws Exception {

        myList.clear();
        Connection conn = td.getConn();

        ZemljaTable zemlja = new ZemljaTable(conn, td.getSchema(AsoftTabele.SCHEMA.ZEMLJA));
        Postanski_brojTable posta = new Postanski_brojTable(conn, td.getSchema(AsoftTabele.SCHEMA.POSTANSKI_BROJ));

        String sqlQuery = String.format("SELECT * FROM %s.zemlja WHERE aktivan and vazeci",
                td.getSchema(AsoftTabele.SCHEMA.ZEMLJA));
        Statement stmt = null;
        ResultSet rset = null;

        try {
            stmt = conn.createStatement();
            rset = stmt.executeQuery(sqlQuery);
            if (rset.next()) {
                zemlja.rsToData(rset);
                myList.add(rset.getString("naziv"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (rset != null) {
                try {
                    rset.close();
                    rset = null;
                } catch (Exception e) {
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                    stmt = null;
                } catch (Exception e) {
                }
            }
        }

        return myList;
    }
}
