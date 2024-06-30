/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import database.DatabaseUtils;
import database.SessionHelper;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;




/**
 *
 * @author qivub
 */
public class LichDao {
     Lich lich = new Lich();    
     User us = new User();     
     SimpleDateFormat date = new SimpleDateFormat("dd/mm/yyyy");
    public int add (Lich lich) {
        List<User> ls = new ArrayList<>();
        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            String sSQL = "insert into Lich (CongViec,NoiDung,MaTK,TenTK,Deadline,TinhTrang,LydoCham) values(?,?,?,?,?,?,?)";
            conn = DatabaseUtils.getDBConnect();
            sttm= conn.prepareStatement(sSQL);
            sttm.setString(1, lich.getCongViec());
            sttm.setString(2, lich.getNoidung());
            sttm.setString(3, lich.getMaNV());
            sttm.setString(4, lich.getTenNV());
            sttm.setDate(5, new java.sql.Date(lich.getDeadline().getTime()));
            sttm.setInt(6, lich.getTinhTrang());
            sttm.setString(7, lich.getLydoCham());
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
    public List<Lich> getLichforUser(){
        List<Lich> ls = new ArrayList<>();
        Connection conn = null;
        Statement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL= "select * from Lich";
            conn= DatabaseUtils.getDBConnect();
            sttm = conn.createStatement();
            rs = sttm.executeQuery (sSQL);
            while (rs.next()) {                
                User us = new User();
                Lich lichs = new Lich();
                lichs.setIDCV(rs.getInt(1));
                lichs.setCongViec(rs.getString(2));
                lichs.setNoidung(rs.getString(3));
                lichs.setMaNV(rs.getString(4));
                lichs.setTenNV(rs.getString(5));
                lichs.setDeadline(rs.getDate(6));
                lichs.setTinhTrang(rs.getInt(7));
                lichs.setLydoCham(rs.getString(8));
                
                ls.add(lichs);
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
        return ls;
    }
    public Lich getDLnByID (String id) {
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        Lich sv = new Lich();
        User us = new User();
        try {
            String sSQL = "select * from Lich where IDCV = ? ";
            conn= DatabaseUtils.getDBConnect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, id);
            rs = sttm.executeQuery();
            while (rs.next()) {
                sv.setIDCV(rs.getInt(1));
                sv.setCongViec(rs.getString(2));
                sv.setNoidung(rs.getString(3));
                us.setMaNV(rs.getString(4));
                us.setTenNV(rs.getString(5));
                sv.setDeadline(rs.getDate(6));
                
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
        return sv;
    }
    public Lich getDLByIdCv (String id) {
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        Lich lich = new Lich();
        
        try {
            String sSQL = "select * from Lich where IDCV = ? ";
            conn= DatabaseUtils.getDBConnect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, id);
            rs = sttm.executeQuery();
            while (rs.next()) {
                lich.setIDCV(rs.getInt(1));
                lich.setCongViec(rs.getString(2));
                lich.setNoidung(rs.getString(3));
                lich.setMaNV(rs.getString(4));
                lich.setTenNV(rs.getString(5));
                lich.setDeadline(rs.getDate(6));
                lich.setTinhTrang(rs.getInt(7));
                lich.setLydoCham(rs.getString(8));
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
        return lich;
    }
    public User getNhanVienByID (String user) {
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        User sv = new User();
        try {
            String sSQL = "select MaTK,TenTK from Login where MaTK = ? ";
            conn= DatabaseUtils.getDBConnect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, user);
            rs = sttm.executeQuery();
            while (rs.next()) {
                sv.setMaNV(rs.getString(1));              
                sv.setTenNV(rs.getString(2));
                
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
        return sv;
    }
    public int del(String id) {
        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            String sSQL = "delete Lich where IDCV = ?";
            conn = DatabaseUtils.getDBConnect();
            sttm= conn.prepareStatement(sSQL);
            sttm.setString(1,id);
            
            if (sttm.executeUpdate() > 0) {
                System.out.println("xoa thanh cong");
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
    public int update(Lich lich) {
        List<Lich> ls = new ArrayList<>();
        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            String sSQL = "update Lich set CongViec=?,NoiDung=?,MaTK=?,TenTK=?,Deadline=?,TinhTrang=?,LydoCham=? where IDCV=?";
            conn = DatabaseUtils.getDBConnect();
            sttm= conn.prepareStatement(sSQL);
            sttm.setInt(8, lich.getIDCV());
            sttm.setString(1, lich.getCongViec());
            sttm.setString(2, lich.getNoidung());
            sttm.setString(3, lich.getMaNV());
            sttm.setString(4, lich.getTenNV());
            sttm.setDate(5, new java.sql.Date(lich.getDeadline().getTime()));
            sttm.setInt(6, lich.getTinhTrang());
            sttm.setString(7, lich.getLydoCham());
            if (sttm.executeUpdate() > 0) {
                System.out.println("Update thanh cong");
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
