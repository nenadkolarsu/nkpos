/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vezba;

//package javadb_001;

public class Users {
    private int id;
    private String fname;
    private String lname;
    private int age;

    public Users(){}
    public Users(int _id,String _fname,String _lname,int _age){
        this.id = _id;
        this.fname = _fname;
        this.lname = _lname;
        this.age = _age;  
    }
    
    public int getId(){
     return this.id;
    }
    
    public String getFname(){
        return this.fname;
    }
    
    
    public String getLname(){
        return this.lname;
    }
    
    public int getAge(){
        return this.age;
    }
    
}




// END Users Class










