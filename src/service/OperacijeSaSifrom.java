/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static service.uzmiParametar.uzmiParametar;

/**
 *
 * @author ms
 */
public class OperacijeSaSifrom {

    public static boolean daLiPostojiDuplaSifra(Connection conn, String table,
            String sifra) throws SQLException {

        PreparedStatement stmt = conn.prepareStatement("SELECT sifra from  " + table
                + " where sifra=? and aktivan=1");

        stmt.setString(1, sifra);

        if (uzmiParametar(conn, "debugiranje").equals("1")) {
            System.out.println(stmt);
        }

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            throw new SQLException("duplicate sifra status aktivan");
//            return false;
        }
        return true;
    }
    
        public static Integer vratiMaxSifru(Connection conn, String table) throws SQLException {

        PreparedStatement stmt = conn.prepareStatement("SELECT max(cast(sifra as unsigned integer))+1 as vrati from  " + table );

//        stmt.setString(1, sifra);

        if (uzmiParametar(conn, "debugiranje").equals("1")) {
            System.out.println(stmt);
        }

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            return (rs.getInt("vrati"));
        }
        throw new SQLException("duplicate sifra status aktivan");
//                return 0;
    }
}
