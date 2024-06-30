package model;

import database.DatabaseUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author qivub
 */
public class KeToanDao {
//    public static  List<KeToan> ls = new ArrayList<>();
    
     public void insertDataIntoKeToan() {
        Connection conn = null;
        Statement sttm = null;
        try {
            String sSQL = "INSERT INTO KeToan (MaTK, HeSoLuong, LuongThoaThuan, SoCong) " +
                            "SELECT Login.MaTK, Login.HeSoLuong, Login.LuongThoaThuan, CongNhieu.SoCong " +
                            "FROM Login " +
                            "INNER JOIN (" +
                            "    SELECT MaTK, COUNT(*) AS SoCong " +
                            "    FROM ChamCong " +
                            "    GROUP BY MaTK " +
                            "    HAVING COUNT(*) > 1" +
                            ") AS CongNhieu ON Login.MaTK = CongNhieu.MaTK" +
                            "WHERE NOT EXISTS (" +
                            "    SELECT 1" +
                            "    FROM KeToan" +
                            "    WHERE KeToan.MaTK = Login.MaTK" +
                            ")";

            System.out.println("1");
            conn = DatabaseUtils.getDBConnect();
            sttm = conn.createStatement();
            int rowsAffected = sttm.executeUpdate(sSQL);
            System.out.println("Rows affected: " + rowsAffected);
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
            e.printStackTrace();
        } finally {
            try {
                conn.close();
                sttm.close();
            } catch (Exception e) {
            }
        }
    }

    public List<KeToan> getAllLuongs() {
        List<KeToan> ls = new ArrayList<>();
        Connection conn = null;
        Statement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT * FROM KeToan";
            System.out.println("2");
            conn = DatabaseUtils.getDBConnect();
            sttm = conn.createStatement();
            rs = sttm.executeQuery(sSQL);
            while (rs.next()) {
                KeToan us = new KeToan();
                us.setID(rs.getInt(1));
                us.setMaNV(rs.getString(2));
                us.setHeSoLuong(rs.getFloat(3));
                us.setLuongThoaThuan(rs.getInt(4));
                us.setSoCong(rs.getInt(5));
                ls.add(us);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
            e.printStackTrace();
        } finally {
            try {
                conn.close();
                sttm.close();
            } catch (Exception e) {
            }
        }
        return ls;
    }

     public KeToan getLuongByID (String kt) {
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        KeToan nv = new KeToan();
        try {
            String sSQL = "select * from KeToan where ID = ? ";
            conn= DatabaseUtils.getDBConnect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, kt);
            rs = sttm.executeQuery();
            while (rs.next()) {
                KeToan us = new KeToan();
                us.setID(rs.getInt(1));
                us.setMaNV(rs.getString(2));
                us.setHeSoLuong(rs.getFloat(3));
                us.setLuongThoaThuan(rs.getInt(4));
                us.setSoCong(rs.getInt(5));
                us.setLuongCb(rs.getInt(6));
                us.setLuongTg(rs.getInt(7));
                us.setTongLuong(rs.getInt(8));
                
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            
            try {
                conn.close();
                rs.close();
                sttm.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return nv;
    }
    public int add (KeToan kt) {  
        Connection conn = null;
     
        PreparedStatement sttm = null;
        try {
            
            String sSQL = "update KeToan set MaTK=?,HeSoLuong=?, LuongThoaThuan=?, SoCong=?,LuongCB=?,LuongThuong=?,TongLuong=? where ID=?";
            conn = DatabaseUtils.getDBConnect();
            sttm= conn.prepareStatement(sSQL);
            sttm.setInt(8, kt.getID());
            sttm.setString(1, kt.getMaNV());
            sttm.setFloat(2, kt.getHeSoLuong());
            sttm.setInt(3, kt.getLuongThoaThuan());
            sttm.setInt(4, kt.getSoCong());   
            sttm.setInt(5, kt.getLuongCb()); 
            sttm.setInt(6, kt.getLuongTg()); 
            sttm.setInt(7, kt.getTongLuong()); 
            if (sttm.executeUpdate() > 0) {
                System.out.println("add thanh cong");
                return 1;
            }
        } catch (Exception e) {
            System.out.println("Error: "+e.toString());
        }finally{
            try {
                conn.close();
                sttm.close();
            } catch (Exception e) {
            }
        }
        return -1;
    }
}
