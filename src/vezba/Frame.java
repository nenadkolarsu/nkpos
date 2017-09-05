/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vezba;

/**
 *
 * @author Administrator
 */




//package javadb_001;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
//public class WorkClass {
public class Frame {    
       
  public static class frame extends JFrame{

          JLabel JL_fname,JL_lname,JL_age,JL_id;
          JTextField JT_fname,JT_lname,JT_age,JT_id;
          JButton btn_first,btn_next,btn_previous,btn_last;
          int pos = 0;



    //get the Connection With Mysql database
    public static Connection getConnection()      
    {
        Connection cn;
        try{
            cn = DriverManager.getConnection("jdbc:mysql://localhost/nkpos","porta","porta1");
            return cn;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
    



    // bind a list with ResultSet
    public static List<Users> BindList(){
        try{
            Connection con = getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from users");
            List<Users> list = new ArrayList<Users>();
            while(rs.next()){
                Users u = new Users(Integer.parseInt(rs.getString("id")),
                                    rs.getString("fname"),
                                    rs.getString("lname"),
                                    Integer.parseInt(rs.getString("age"))
                 );
                list.add(u);
            }
            return list;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
    



   //Showing the Users Info in jtexfields
    public void ShowPosInfo(int index){
        
        JT_id.setText(Integer.toString(BindList().get(index).getId()));
        JT_fname.setText(BindList().get(index).getFname());
        JT_lname.setText(BindList().get(index).getLname());
        JT_age.setText(Integer.toString(BindList().get(index).getAge()));        
        
//        JT_id.setText(Integer.toString(BindList().get(index).getId()));
//        JT_fname.setText(BindList().get(index).getFname());
//        JT_lname.setText(BindList().get(index).getLname());
//        JT_age.setText(Integer.toString(BindList().get(index).getAge()));
    }
    
    public frame(){
     super("Navigation");
     JL_id = new JLabel("Id:");
     JL_fname = new JLabel("Fname:");
     JL_lname = new JLabel("Lname:");
     JL_age = new JLabel("Age:");
     JL_id.setBounds(20, 20, 100, 20);
     JL_fname.setBounds(20, 50, 100, 20);
     JL_lname.setBounds(20, 80, 100, 20);
     JL_age.setBounds(20, 110, 100, 20);
     
     JT_id = new JTextField(20);
     JT_fname = new JTextField(20);
     JT_lname = new JTextField(20);
     JT_age = new JTextField(20);
     JT_id.setBounds(130,20,150,20);
     JT_fname.setBounds(130, 50, 150, 20);
     JT_lname.setBounds(130, 80, 150, 20);
     JT_age.setBounds(130, 110, 150, 20);
     btn_first = new JButton("First");
     btn_next = new JButton("Next");
     btn_previous = new JButton("Previous");
     btn_last = new JButton("Last");
     btn_first.setBounds(300, 20, 100, 20);
     btn_next.setBounds(300, 50, 100, 20);
     btn_previous.setBounds(300, 80, 100, 20);
     btn_last.setBounds(300, 110, 100, 20);
     
        



        // Button to show the Next user from the List
        btn_next.addActionListener(new ActionListener() {
          
         @Override
         public void actionPerformed(ActionEvent e) {
               pos++;
               if(pos < BindList().size()){
                   ShowPosInfo(pos);
               }
               else{
                   pos = BindList().size() - 1;
                   ShowPosInfo(pos);
                   JOptionPane.showMessageDialog(null, "END");
               }
         }
         
     });



        
        // Button to show the First user from the List
        btn_first.addActionListener(new ActionListener(){
        
        @Override
        public void actionPerformed(ActionEvent e){
             pos = 0;
             ShowPosInfo(pos);
        }
        });
        



        // Button to show the Last user from the List
        btn_last.addActionListener(new ActionListener(){
        
        @Override
        public void actionPerformed(ActionEvent e){
           
            pos = BindList().size() - 1;
            ShowPosInfo(pos);
        }
        });
                



         //Button to show the Previous user from the List



btn_previous.addActionListener(new ActionListener(){
        
        @Override
        public void actionPerformed(ActionEvent e){
          
            pos--;
            if(pos > 0){
                ShowPosInfo(pos);                
            }
            else{
                pos = 0;
                ShowPosInfo(pos);
                JOptionPane.showMessageDialog(null, "END");
            }
        }
        });
        
        ShowPosInfo(pos);
        add(btn_last);
        add(btn_first);
        add(btn_previous);
        add(btn_next);
        add(JT_id);
        add(JT_fname);
        add(JT_lname);
        add(JT_age);
        setLayout(null);
        getContentPane().setBackground(Color.decode("#bdb76b"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(500,200);
        setVisible(true);

    } 
    
    
     }  
       
       
 public static void main(String[] args){ 
     
       new frame();
 }
}
