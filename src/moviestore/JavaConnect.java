/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moviestore;

import java.sql.*;


public class JavaConnect {
    Connection conn=null;
    public static Connection ConnectDB(){
        try {
            Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/movie","root","");
            if (conn!=null){
                System.out.println("Connection to database has been established!");
            }else{
                System.out.println("Connection to database failed!");
            }
            return conn;
        } catch (SQLException ex) {
           ex.printStackTrace();
        }
        return null;
    }
    
    
}
