/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author trung
 */
public class DatabaseUtils {
    public static Connection getDBConnect() throws Exception{
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionUrl = "jdbc:sqlserver://localhost;database=DB_QuanLyNV";
        String acc = "sa";
        String pass = "123456789";
        Connection con = DriverManager.getConnection(connectionUrl,acc,pass);
        return con;
    }
}
