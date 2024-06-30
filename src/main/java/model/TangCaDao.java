/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import database.DatabaseUtils;
import database.SessionHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import main.WindowAPP;

/**
 *
 * @author qivub
 */
public class TangCaDao {
    SimpleDateFormat date = new SimpleDateFormat("dd/mm/yyyy");
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    
    LocalDateTime recordedTime = LocalDateTime.now();
    DateTimeFormatter anotherTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    String gioRa = recordedTime.format(anotherTimeFormatter);
    
    List<TangCa> ls = new ArrayList<>();
    public int add (TangCa tangca) {
        List<TangCa> ls = new ArrayList<>();
        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            String sSQL = "insert into ChamCongDB (MaTK,TenTK,NgayNhan,Ca,GioVao,GioRa,ThoiGianLam) values(?,?,?,?,?,?,?)";
            conn = DatabaseUtils.getDBConnect();
            sttm= conn.prepareStatement(sSQL);
//            sttm.setString(1, tangca.getId());
            sttm.setString(1, tangca.getMaNV());
            sttm.setString(2, tangca.getTenNV());
            sttm.setDate(3, new java.sql.Date(tangca.getNgayNhan().getTime()));
            sttm.setInt(4, tangca.getCaLam());
            sttm.setTime(5, new Time(tangca.getGioVao().getTime()));
            sttm.setTime(6, new Time(tangca.getGioRa().getTime()));
            sttm.setTime(7, new Time(tangca.getThoiGianLam().getTime()));
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
    public int update(TangCa tangca) {
        List<TangCa> ls = new ArrayList<>();
        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            String sSQL = "update ChamCongDB set MaTK=?,TenTK=?,NgayNhan=?,Ca=?,GioVao=?,GioRa=?,ThoiGianLam=? where ID=?";
            conn = DatabaseUtils.getDBConnect();
            sttm= conn.prepareStatement(sSQL);
            sttm.setString(8, tangca.getId());
            sttm.setString(1, tangca.getMaNV());
            sttm.setString(2, tangca.getTenNV());
            sttm.setDate(3, new java.sql.Date(tangca.getNgayNhan().getTime()));
            sttm.setInt(4, tangca.getCaLam());
            sttm.setTime(5, new Time(tangca.getGioVao().getTime()));
            sttm.setTime(6, new Time(tangca.getGioRa().getTime()));
            sttm.setTime(7, new Time(tangca.getThoiGianLam().getTime()));
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
    public List<TangCa> getTangCaforUser(){
        System.out.println(1);
        
        Connection conn = null;
        Statement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "select * from ChamCongDB";
            conn= DatabaseUtils.getDBConnect();
            sttm = conn.createStatement();
            rs = sttm.executeQuery (sSQL);
            while (rs.next()) {                
                TangCa tangca = new TangCa();
                tangca.setId(rs.getString(1));
                tangca.setMaNV(rs.getString(2));
                tangca.setTenNV(rs.getString(3));
                tangca.setNgayNhan(rs.getDate(4));
                tangca.setCaLam(rs.getInt(5));
                tangca.setGioVao(rs.getTime(6));
                tangca.setGioRa(rs.getTime(7));
                tangca.setThoiGianLam(rs.getTime(8));
                
                ls.add(tangca);
                
            }
            System.out.println(1);
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
    public TangCa getIDChamCongByID (String tc) {
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        TangCa tangca = new TangCa();
        try {
            String sSQL = "select * from ChamCongDB where ID = ? ";
            conn= DatabaseUtils.getDBConnect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, tc);
            rs = sttm.executeQuery();
            while (rs.next()) {
                tangca.setId(rs.getString(1));
                tangca.setMaNV(rs.getString(2));
                tangca.setTenNV(rs.getString(3));
                tangca.setNgayNhan(rs.getDate(4));
                
                tangca.setCaLam(rs.getInt(5));
                tangca.setGioVao(rs.getTime(6));
                tangca.setGioRa(rs.getTime(7));
                
                tangca.setThoiGianLam(rs.getTime(8));
                
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
        return tangca;
    }
    public int GioVao(TangCa nv) {
        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            String sSQL = "update ChamCongDB set GioVao=? where MaTK=?";
            conn = DatabaseUtils.getDBConnect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setTime(1, new Time(nv.getGioVao().getTime()));
            sttm.setString(2, nv.getMaNV());

            if (sttm.executeUpdate() > 0) {
                System.out.println("Update thành công");
                return 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {
            try {
                if (sttm != null) {
                    sttm.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace(); 
            }
        }
        return -1;
    }
     public int GioRa(TangCa nv) {
        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            String sSQL = "update ChamCongDB set GioRa=? where MaTK=?";
            conn = DatabaseUtils.getDBConnect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setTime(1, new Time(nv.getGioRa().getTime()));
            sttm.setString(2, nv.getMaNV());

            if (sttm.executeUpdate() > 0) {
                System.out.println("Update thành công");
                return 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {
            try {
                if (sttm != null) {
                    sttm.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace(); 
            }
        }
        return -1;
    }
     public int del(TangCa id) {
        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            
            String sSQL = "DELETE FROM ChamCongDB WHERE ID = ?";
            conn = DatabaseUtils.getDBConnect();
            sttm= conn.prepareStatement(sSQL);
            sttm.setString(1, id.getId());
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
    public int ChamcongVao (ChamCong chamcong) {
        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            WindowAPP app = new WindowAPP();
            String sSQL = "insert into ChamCong (MaTK,TenTK,NgayNhan,ThoiGian) values(?,?,?,?)";
            conn = DatabaseUtils.getDBConnect();
            sttm= conn.prepareStatement(sSQL);
            
            sttm.setString(1, SessionHelper.getIdUser());
            sttm.setString(2, SessionHelper.getIdPassword());
            sttm.setString(3, chamcong.getNgayNhan());
            System.out.println("Ngay nhan ID: "+ SessionHelper.getNgayNhan());
            sttm.setString(4, chamcong.getGioVao());
            
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
    public List<ChamCong> getallChamCong(){
        List<ChamCong> ls = new ArrayList<>();
        Connection conn = null;
        Statement sttm = null;
        ResultSet rs = null;
        ChamCong ck = new ChamCong();
        try {
            String sSQL= "select * from ChamCong";
            conn= DatabaseUtils.getDBConnect();
            sttm = conn.createStatement();
            rs = sttm.executeQuery (sSQL);
            while (rs.next()) {        
                ck.setID(rs.getInt(1));
                ck.setManv(rs.getString(2));
                ck.setTennv(rs.getString(3));
                ck.setNgayNhan(rs.getString(4));
                ck.setGioVao(rs.getString(5));
                ck.setGioRa(rs.getString(6));
            }
            System.out.println(SessionHelper.getNgayNhan());
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
    public List<ChamCong> TongHopNgayCong() {
        List<ChamCong> ls = new ArrayList<>();
        Connection conn = null;
        Statement sttm = null;
        ResultSet rs = null;

        try {
            String sSQL = "SELECT MaTK, COUNT(*) AS SoCong FROM ChamCong GROUP BY MaTK HAVING COUNT(*) > 1 ";
            conn = DatabaseUtils.getDBConnect();
            sttm = conn.createStatement();
            rs = sttm.executeQuery(sSQL);

            while (rs.next()) {
                ChamCong ck = new ChamCong(); // Tạo một đối tượng mới cho mỗi dòng
                ck.setManv(rs.getString(1));
//                System.out.println("manv"+ck.getManv());
                ck.setSoNgayCong(rs.getInt(2));
//                System.out.println("Ngaycong"+ck.getSoNgayCong());
                ls.add(ck); // Thêm đối tượng vào danh sách
//                System.out.println("Ls: "+ls);
            }
            System.out.println(SessionHelper.getNgayNhan());
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
        } finally {
            try {
                // Đảm bảo đóng tất cả các nguồn tài nguyên
                if (conn != null) {
                    conn.close();
                }
                if (sttm != null) {
                    sttm.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                // Xử lý ngoại lệ nếu cần thiết
            }
        }
        return ls;
    }

}
