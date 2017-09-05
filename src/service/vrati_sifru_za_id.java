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
//import org.asoft.library.AsoftComboBox;

/**
 *
 * @author ms
 */
public class vrati_sifru_za_id {

    String artikal;
    Connection conn;

    public static String vrati_sifru_za_id(Connection conn, String tabela, Integer id) {

        String insertSQL = "SELECT sifra from "+ tabela + " where aktivan and id = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(insertSQL);
            stmt.setInt(1, id);
            
            if (uzmiParametar(conn, "debugiranje").equals("1")) {
                System.out.println(stmt);
            }
            ResultSet rs = stmt.executeQuery();

            while ((rs.next())) {
                return rs.getString(1);
            }

        } catch (SQLException e) {
            MyLogger.logger.error(e.toString());

        }
        return "0";
    }
    
    
}
