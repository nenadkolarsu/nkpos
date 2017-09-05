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
public class id_false {


        private static String id;
        
    public static void id_false(Connection conn, String table, String id) throws SQLException {

        PreparedStatement stmt = conn.prepareStatement("update " + table
                + " set aktivan=? where id=?");

        stmt.setInt(1, 0);
        stmt.setString(2, id);

        stmt.executeUpdate();

        if (uzmiParametar(conn, "debugiranje").equals("1")) {
            System.out.println(stmt);
        }
        
    }

}
