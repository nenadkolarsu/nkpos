/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import static service.uzmiParametar.uzmiParametar;
//import org.asoft.library.AsoftComboBox;

/**
 *
 * @author ms
 */
public class vrati_id_za_naziv {

    String artikal;
    Connection conn;

    public static int vrati_id_za_naziv(Connection conn, String tabela, String artikalNaziv) {

        String insertSQL = "SELECT id from "+ tabela + " where aktivan and naziv = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(insertSQL);
            stmt.setString(1, artikalNaziv);
            
            if (uzmiParametar(conn, "debugiranje").equals("1")) {
                System.out.println(stmt);
            }
            ResultSet rs = stmt.executeQuery();

            while ((rs.next())) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            MyLogger.logger.error(e.toString());

        }
        return 0;
    }        
    
}
