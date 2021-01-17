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
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Pionir SU
 */
public class konekcija {

    static Connection conn;

    private static void displayEndOfLog(boolean success) {

        String mssg = "";
        
        if (success) {
            mssg = mssg + "Uspesna Potvrda prijema!";
        } else {
            mssg = mssg + "NEUSPESNA Potvrda prijema!";
        }
        log.info(mssg); // NOI18N
        log.info(
                "---------------------------------------------------------------------------------------------------");
        log.info("                                 Kraj loga:" + " " + new java.util.Date());
        log.info(
                "---------------------------------------------------------------------------------------------------");    
    }
    
    public Connection conn1 = null;
    Connection conn2 = null;
    Connection conn3 = null;
    String url1 = "jdbc:mysql://localhost:3306/personal?useUnicode=true&characterEncoding=utf-8";
    String user = "root";
    String password = "";
    String host = "";
    String DRIVER_MANAGER_CONNECTION_STRING = "jdbc:postgresql://10.11.124.202:5432/asoft?user=vlada&password=vladakk";
   //  Database credentials
      static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/asoft";
      static final String USER = "vlada";
      static final String PASS = "vladakk";
   //    
    static Log log = LogFactory.getLog("asoftsyncpgdiff");
    final static int SUCCESSFUL_EXIT = 0;
    final static int UNSUCCESSFUL_EXIT = 1;
    final static int HALT_SYSTEM = 2;
    static String subject_successful;
    static String subject_unsuccessful;    

    public Connection konekcija() {
        
        
	try {
		Class.forName("org.postgresql.Driver");
	} catch (ClassNotFoundException e) {
		System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
		e.printStackTrace();
		// return;
	}
 
	System.out.println("PostgreSQL JDBC Driver successfully connected");
	Connection connection = null;
 
	try {
		conn = DriverManager
		.getConnection(DB_URL, USER, PASS);
 
	} catch (SQLException e) {
		System.out.println("Connection Failed");
		e.printStackTrace();
		// return;
	}
 
	if (conn != null) {
		System.out.println("You successfully connected to database now");
	} else {
		System.out.println("Failed to make connection to database");
	}        
        
        
        /*
        File FILENAME = new File("c:\\gui\\reports\\boot.txt");
        if (FILENAME.exists()) {
            try {
                host = readFile("c:\\gui\\reports\\boot.txt");
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

        } else {

            try {         
//      static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/asoft";
//      static final String USER = "vlada";
//      static final String PASS = "vladakk";                
              //  conn = DriverManager.getConnection("jdbc:postgresql://10.11.124.202:5432/asoft?user=vlada&password=vladakk");
                conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/asoft?user=vlada&password=vladakk");
                conn.setAutoCommit(false);
            } catch (SQLException ex) {
                log.error(ex.toString());
                SendEmail(HALT_SYSTEM, subject_unsuccessful, ex.toString());
                JFrame frame = new JFrame("Information");
                JOptionPane.showMessageDialog(frame, "An error occurred. Maybe user/password is invalid");

            }

            return conn;
        }

        return conn1;
    }
    
    private static void SendEmail(int CODE, String subject, String body) {

        // https://www.javatpoint.com/example-of-sending-email-using-java-mail-api
        String host = "mail.agroupm.com";
        final String user = "nenad.kolar@a-pionir.com";
        final String password = "nenadkrs";

        String[] to = {"nenad.kolar@pionir.rs", "nenad.kolar@pionir.rs"};
        // if (CODE==UNSUCCESSFUL_EXIT)
        // to[1] = "zoran.cincovic@a-asoft.com";        

        //Get the session object  
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");

        javax.mail.Session session = javax.mail.Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });
 
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));

            for (int i = 0; i < to.length; i++) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to[i]));
            }

            message.setSubject(subject);
            message.setText(body); 
            Transport.send(message);

        } catch (MessagingException e) {
            log.info("Neuspesno slanje emaila! " + e.getMessage());
            e.printStackTrace();
        }

        if (CODE == 2) {
            displayEndOfLog(false);
            System.exit(0);
        }
    }    

    public String readFile(String filename) throws IOException {
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
            if (reader != null) {
                reader.close();
            }
        }
        return content;
    }
*/
        return conn;
    }
}
