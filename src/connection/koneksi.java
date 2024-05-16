package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author adity
 */
public class koneksi {
   private Connection kon;
    public Connection connect() {
       try {
           Class.forName("com.mysql.cj.jdbc.Driver");
           System.out.println("DRIVER DITEMUKAN");
       } catch (ClassNotFoundException e) {
           System.out.println("DRIVER TIDAK DITEMUKAN" +e);
       }
       String url = "jdbc:mysql://localhost:3306/app_pegawai";
       try {
           kon = DriverManager.getConnection(url, "root", "");
           System.out.println("KONEKSI DATABASE BERHASIL");
       } catch (SQLException e) {
           System.out.println("KONEKSI DATABASE GAGAL" +e);
       }
       return kon;
    }
    
}
