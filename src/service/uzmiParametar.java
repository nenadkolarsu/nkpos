/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author ms
 */
public class uzmiParametar {

    public static String uzmiParametar(Connection conn, String parametar) {
        try {
            Statement stmt = conn.createStatement();
            String sqlQuery = " select * from parametri where aktivan=1 "; // id like '1' and
            ResultSet rs = stmt.executeQuery(sqlQuery);
            System.out.println(sqlQuery);
            while (rs.next()) {
                if (parametar.equals("default_slika")) {
                    return (rs.getString("person_icon"));
                } else if (parametar.equals("putanja_izvestaja")) {
                    return (rs.getString("putanja_izvestaja"));                    
                } else if (parametar.equals("artikal_icon")) {
                    return (rs.getString("artikal_icon"));
                } else if (parametar.equals("aktivanilibrisi")) {
                    return (rs.getString("aktivanilibrisi"));   
                } else if (parametar.equals("debugiranje")) {
                    return (rs.getString("debugiranje"));                   
                } else if (parametar.equals("konekcija")) {
                    return (rs.getString("konekcija"));
                } else if (parametar.equals("user")) {
                    return (rs.getString("user"));
                } else if (parametar.equals("password")) {
                    return (rs.getString("password"));
                }
            }

        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, e);

        }
        return null;
    }

}
