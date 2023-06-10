
package com.koneksi;

import java.sql.Connection;
import java.sql.DriverManager;
import com.mysql.cj.jdbc.Driver;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class koneksi {

    private static Connection koneksi;
    
    public static Connection getKoneksi() throws SQLException {
        if (koneksi == null) {
            try {
                String url = "jdbc:mysql://localhost/vapestore";
                String user = "root";
                String pass = "";
                
                DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
                koneksi = DriverManager.getConnection(url,user,pass);
            }catch (Exception e) {
                System.out.print(e);
            } finally {
                
                }
        }
        return koneksi;
    }
    public static void main(String args[]) throws SQLException{
       Connection koneksi = new koneksi().getKoneksi();
    }
    
}
