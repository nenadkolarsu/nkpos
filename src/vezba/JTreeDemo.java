/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vezba;
/*
File Name : JTreeDemo.java
WebSite : http://javaproglang.blogspot.in/
Facebook : https://www.facebook.com/AdvanceJavaProgramming
Twitter ID : https://twitter.com/AdvanceJava
Created By : Bintu Chaudhary
===========================================================
*/
import java.awt.*;
import java.net.*;
import java.io.*;
import java.sql.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.print.*;
import java.awt.geom.*;
import javax.swing.event.*;
import javax.swing.table.*;
import javax.swing.border.*;
import java.util.Calendar;
import javax.swing.colorchooser.*;
import javax.swing.tree.*;
import javax.swing.text.html.*;
import javax.swing.text.html.HTMLDocument;
import javax.swing.ImageIcon;

public class JTreeDemo extends JFrame implements ActionListener,TreeSelectionListener
{
 
 Color c1=new Color(255,146,34);
 Color c2=new Color(251,254,120);//yellow
 Color c3=new Color(140,140,255);//Sky blue
 Color c4=new Color(120,120,230);
 Color c5=new Color(190,190,240);
 Color c6=new Color(147,255,174);//Light Green

 Font f1=new Font("Times New Roman",Font.BOLD,40);
 Font f2=new Font("Times New Roman",Font.BOLD,14);
 Font f3=new Font("Times New Roman",Font.BOLD,18);
 Font f4=new Font("Times New Roman",Font.BOLD,12);
 Font f5=new Font("Times New Roman",Font.BOLD,11);
 Font f6=new Font("COMIC SANS MS",Font.BOLD,22);
 Font fp=new Font("Times New Roman",Font.BOLD,45);
 Font fp1=new Font("COMIC SANS SERIF",Font.BOLD,30);
  
 Border border;
 Container c;
 JPanel p1,p2,p3,p4,p5;
 JLabel lbl_find,logoname;

 JButton btn_infor;
 JTextField txt_infor,txtTree;
 TextArea ta_east;
 JTree tree;
 DefaultMutableTreeNode root;
 DefaultMutableTreeNode file,edit,view,search,help,exit;
 DefaultMutableTreeNode newaddmission;
 DefaultMutableTreeNode update,delete;
 DefaultMutableTreeNode viewrcd;
 DefaultMutableTreeNode searchrcd;
 DefaultMutableTreeNode helpsys;
 DefaultMutableTreeNode exitsys;

 public JTreeDemo()
 {
  super("JTreeDemo");
  c=getContentPane();
  c.setLayout(new BorderLayout());
  p1=new JPanel();
  p2=new JPanel();
  p3=new JPanel();
  p4=new JPanel();
  p5=new JPanel();

  p1.setBackground(c5);
  p2.setBackground(c5);
  p3.setBackground(c5);
  p4.setBackground(c5);
  p5.setBackground(c5);                                                         
  
  logoname=new JLabel("Java Programming");
  logoname.setFont(fp);
  logoname.setForeground(c4);
  p1.add(logoname);
  c.add(p1,"North");
          
  root=new DefaultMutableTreeNode("Root Panel");
  
  file=new DefaultMutableTreeNode(" File Panel ");
  newaddmission=new DefaultMutableTreeNode("New");      
  exitsys=new DefaultMutableTreeNode("Exit");                             
  file.add(newaddmission);
  file.add(exitsys);
  root.add(file);
  
  edit=new DefaultMutableTreeNode(" Edit Panel ");
  update=new DefaultMutableTreeNode("Update");
  delete=new DefaultMutableTreeNode("Delete");
  edit.add(update);
  edit.add(delete);
  root.add(edit);
   
  search=new DefaultMutableTreeNode(" Search Panel ");
  searchrcd=new DefaultMutableTreeNode("Search");
  search.add(searchrcd);
  root.add(search);
  
  help=new DefaultMutableTreeNode(" Help Panel ");
  helpsys=new DefaultMutableTreeNode("Help");
  help.add(helpsys);
  root.add(help);
      
  tree=new JTree(root);
  tree.addTreeSelectionListener(this);
  p2.add(new JScrollPane(tree),BorderLayout.CENTER);
  p2.setSize(100,100);
  p2.setVisible(true);
  p2.setBorder(BorderFactory.createTitledBorder("JTreeDemo"));
  c.add(p2,"West");
          
  ta_east=new TextArea(25,87);                                
  ta_east.setEditable(false);
  ta_east.setFont(f5);
  p3.add(ta_east);
  p3.setBorder(BorderFactory.createTitledBorder("Your Search"));
  c.add(p3,"East");
  
  border=new BevelBorder(BevelBorder.LOWERED);                                                         
            
  lbl_find=new JLabel("Find What",SwingConstants.CENTER);
  txt_infor=new JTextField(35);                                
  txt_infor.setBorder(border);
  btn_infor=new JButton(" Find What ");
  btn_infor.setBackground(c5);
  lbl_find.setFont(f2);
  txt_infor.setFont(f5);
  btn_infor.setFont(f4);
  btn_infor.setMnemonic(KeyEvent.VK_F);
  btn_infor.addActionListener(this);
  
  p4.setBorder(BorderFactory.createTitledBorder("Find What"));                                
  p4.add(lbl_find);
  p4.add(txt_infor);
  p4.add(btn_infor);  
                                
  c.add(p4,"South");
  c.add(p5,"Center");
  
  //This JTextField Free
  txtTree=new JTextField(20);
 }
 public void valueChanged(TreeSelectionEvent te)
 {
  JTextArea tafree=new JTextArea();
  tafree.setText(tree.getLastSelectedPathComponent().toString());
                                
  String str_Tree=tafree.getText();                                
  
  String str_new  ="New";
  String str_exit ="Exit";
  String str_up   ="Update";         
  String str_del  ="Delete";                            
  String str_src  ="Search";
  String str_help ="Help";         
  

  if(str_Tree.equalsIgnoreCase(str_new))
  {
   ta_east.append("\n--New--\n");
  }  
  if(str_Tree.equalsIgnoreCase(str_exit))
  {
   ta_east.append("\n--Exit--\n");
  }  
  if(str_Tree.equalsIgnoreCase(str_up))
  {                                     
   ta_east.append("\n--Update--\n");
  }
  if(str_Tree.equalsIgnoreCase(str_del))
  {                                     
   ta_east.append("\n--Delete--\n");
  }
  if(str_Tree.equalsIgnoreCase(str_src))
  {                                     
   ta_east.append("\n--Search--\n");
  }
  if(str_Tree.equalsIgnoreCase(str_help))
  {                                     
   ta_east.append("\n--Help--\n");
  }                                
 }
 public void actionPerformed(ActionEvent ae)
 {
  if(ae.getSource()==btn_infor)
  {
   String str_infor=txt_infor.getText();
   if(str_infor.length()==0)
   {
    JOptionPane.showMessageDialog(null,"Please Enter the Find Field");
    txt_infor.requestFocus();
    return;                                                                                        
   }
   else
   {
    // FindWhat();                                               
   }
  }
 } 
//________________________________________________________________________________________
                                /* MAIN CLASS */
//----------------------------------------------------------------------------------------
 public static void main(String args[])
 {
  JTreeDemo tdemo=new JTreeDemo();
  tdemo.setSize(800,600);
  tdemo.setVisible(true); 
  tdemo.setResizable(false);               
 }
}  /* END OF THE JTreeDemo MAIN CLASS */
//********************************************************************************
                        /* JTreeDemo Created by Bintu Chaudhary */
//******************************************************************************** 