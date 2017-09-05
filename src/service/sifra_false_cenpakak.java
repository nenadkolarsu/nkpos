/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import static service.uzmiParametar.uzmiParametar;

/**
 *
 * @author ms
 */
public class sifra_false_cenpakak {

//        MyLogger ml = new MyLogger();
        private static String id;
//        private static String tabela;
//        private static Connection conn;
        
    public static void sifra_false_cenpakak(Connection conn, String table, String kolona, String id) throws SQLException {


        PreparedStatement stmt = conn.prepareStatement("update " + table
                + " set aktivan=? where ?=?");

        stmt.setInt(1, 0);
        stmt.setString(2, kolona);
        stmt.setString(3, id);

        stmt.executeUpdate();

        if (uzmiParametar(conn, "debugiranje").equals("1")) {
            System.out.println(stmt);
        }

    }

}
