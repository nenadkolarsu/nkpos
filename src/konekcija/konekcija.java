/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package konekcija;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Pionir SU
 */
public class konekcija {

    public Connection conn1 = null;
    Connection conn2 = null;
    Connection conn3 = null;
    String url1 = "jdbc:mysql://localhost:3306/personal?useUnicode=true&characterEncoding=utf-8";
    String user = "root";
    String password = "";
    String host = "";

    public Connection konekcija() {

//        File xmlFile = new File("kuci.txt");
        File FILENAME = new File("c:\\gui\\reports\\boot.txt");

//        if (xmlFile.exists()) {
//            url1 = "jdbc:mysql://localhost:3306/personal?useUnicode=true&characterEncoding=utf-8";
//            user = "root";
//            password = "";
//        } else 
        if (FILENAME.exists()) {
            try {
            host=  readFile("c:\\gui\\reports\\boot.txt");
            } catch (IOException ex) {
                Logger.getLogger(konekcija.class.getName()).log(Level.SEVERE, null, ex);
            }

            		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {

			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				host = sCurrentLine;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
                        
            url1 = "jdbc:mysql://" + host + ":3306/nkpos?useUnicode=true&characterEncoding=utf-8";
            user = "porta";
            password = "porta1";
            
//            JFrame frame = new JFrame("InputDialog Example #2");
//            String code = JOptionPane.showInputDialog(
//                    frame,
//                    "Enter host ",
//                    "Host needed (title)",
//                    JOptionPane.WARNING_MESSAGE
//            );
//            // if the user presses Cancel, this will be null
////            System.out.printf("The secret code is '%s'.\n", code);
//            String userp = JOptionPane.showInputDialog(
//                    frame,
//                    "Enter user ",
//                    "User needed (title)",
//                    JOptionPane.WARNING_MESSAGE
//            );
//            String pass = JOptionPane.showInputDialog(
//                    frame,
//                    "Enter pass ",
//                    "Host needed (title)",
//                    JOptionPane.WARNING_MESSAGE
//            );
//            url1 = "jdbc:mysql://" + code + ":3306/personal?useUnicode=true&characterEncoding=utf-8";
//            user = userp;
//            password = pass;
        }

        try {
            // connect way #1
            // con = DriverManager.getConnection("jdbc:mysql:///dbname?useUnicode=true&characterEncoding=utf-8", "user", "pass");

//            url1 = "jdbc:mysql://localhost:3306/personal";
//
//            url1 = "jdbc:mysql://localhost:3306/personal?useUnicode=true&characterEncoding=utf-8";
//            user = "root";
//            password = "";
            
            conn1 = DriverManager.getConnection(url1, user, password);

            if (conn1 != null) {
//                System.out.println("Connected to the database .... ");
            }

            // connect way #2
//            String url2 = "jdbc:mysql://localhost:3306/test2?user=root&password=secret";
//            conn2 = DriverManager.getConnection(url2);
//            if (conn2 != null) {
//                System.out.println("Connected to the database test2");
//            }
            // connect way #3
//            String url3 = "jdbc:mysql://localhost:3306/test3";
//            Properties info = new Properties();
//            info.put("user", "root");
//            info.put("password", "secret");
// 
//            conn3 = DriverManager.getConnection(url3, info);
//            if (conn3 != null) {
//                System.out.println("Connected to the database test3");
//            }
            
        } catch (SQLException ex) {
                      JFrame frame = new JFrame("Information");
            JOptionPane.showMessageDialog(frame, "An error occurred. Maybe user/password is invalid");
            System.out.println("An error occurred. Maybe user/password is invalid");
            ex.printStackTrace();
        }
        return conn1;
    }
    
    
    public String readFile(String filename) throws IOException
{
    String content = null;
    File file = new File(filename); //for ex foo.txt
    FileReader reader = null;
    try {
        reader = new FileReader(file);
        char[] chars = new char[(int) file.length()];
        reader.read(chars);
        content = new String(chars);
        reader.close();
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        if(reader !=null)
        {reader.close();}
    }
    return content;
}

}
