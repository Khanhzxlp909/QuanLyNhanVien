/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import javax.xml.transform.Result;
import model.UserDao;
import java.sql.ResultSet;
import database.DatabaseUtils;
import database.SessionHelper;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;

import main.NhanVienMainFrame;
public class NhanVienDao {
    public static  List<User> ls = new ArrayList<>();
    
//    public User getInfoById(String id) throws SQLException {
//        User nhanVien = null;
//        Connection conn = null;
//        PreparedStatement sttm = null;
//        ResultSet rs = null;
//        NhanVienMainFrame nv = new NhanVienMainFrame();
//        try 
    
//            conn = DatabaseUtils.getDBConnect();
//            String sql = "SELECT TenTK, Password, Vaitro, Hinhanh, BanNganh FROM Login WHERE MaTK = ?";
//            sttm = conn.prepareStatement(sql);
//            sttm.setString(1, id);
//            rs = sttm.executeQuery();
//
//            if (rs.next()) {
//                String tenNhanVien = rs.getString("TenTK");
//                String matKhau = rs.getString("password");
//                int vaitro = rs.getInt("Vaitro");
//                
//                if (vaitro == 0) {
//                    nv.rdNV.setSelected(true);
//                } else if (vaitro == 1) {
//                    nv.rdGd.setSelected(true);
//                }else if (vaitro == 2) {
//                    nv.rdQly.setSelected(true);
//                }else if (vaitro == 3) {
//                    nv.rdThuKy.setSelected(true);
//                }else if (vaitro == 4) {
//                    nv.rdDTDCV.setSelected(true);
//                }
//                String anh = rs.getString("Hinhanh");
//                String banNganh = rs.getString("BanNganh");
//                String secret = rs.getString("SecretKey");
//                boolean isActive = rs.getBoolean("IsActive2FA");
//                nhanVien = new User(tenNhanVien, matKhau, vaitro, id, anh,banNganh,secret,isActive);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            // Đảm bảo đóng tất cả tài nguyên
//                conn.close();
//                rs.close();
//                sttm.close();
//        }
//        return nhanVien;
//    }
    public User tabblebyIDNhanVien (String user) {
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        User us = new User();
        try {
            String sSQL = "SELECT TenTK, Vaitro, Hinhanh, BanNganh, TrangThai FROM Login WHERE MaTK = ? ";
            conn= DatabaseUtils.getDBConnect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, user);
            rs = sttm.executeQuery();
            while (rs.next()) {                 
                us.setMaNV(rs.getString(1));
                us.setTenNV(rs.getString(2));
                us.setRole(rs.getInt(3));
                us.setHinhAnh(rs.getString(4));
                us.setBanNganh(rs.getString(5));
                us.setTrangThai(rs.getInt(6));
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
        return us;
    }
    
    public User getNhanVienByID (String user) {
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        User nv = new User();
        try {
            String sSQL = "select * from Login where MaTK = ? ";
            conn= DatabaseUtils.getDBConnect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, user);
            rs = sttm.executeQuery();
            while (rs.next()) {
                nv.setMaNV(rs.getString(1));
                nv.setPassword(rs.getString(2));
                nv.setTenNV(rs.getString(3));
                nv.setRole(rs.getInt(4));
                nv.setHinhAnh(rs.getString(5)); 
                nv.setBanNganh(rs.getString(6));
                nv.setTrangThai(rs.getInt(7));
                nv.setHeSoLuong(rs.getFloat(8));
                nv.setLuongThoaThuan(rs.getInt(9));
                
                nv.setSecretKey(rs.getString(10));
                
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
    public int add (User user) {
        List<User> ls = new ArrayList<>();
        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            String sSQL = "insert into Login (MaTK,Password,TenTK,Vaitro,Hinhanh,BanNganh,TrangThai,SecretKey,HeSoLuong,LuongThoaThuan) values(?,?,?,?,?,?,?,?,?,?)";
            conn = DatabaseUtils.getDBConnect();
            sttm= conn.prepareStatement(sSQL);
            sttm.setString(1, user.getMaNV());
            sttm.setString(2, user.getPassword());
            sttm.setString(3, user.getTenNV());
            sttm.setInt(4, user.getRole());
            sttm.setString(5, user.getHinhAnh());
            sttm.setString(6, user.getBanNganh());
            sttm.setInt(7, user.getTrangThai());
            sttm.setString(8, user.getSecretKey());
            sttm.setFloat(10, user.getHeSoLuong());
            sttm.setInt(11, user.getLuongThoaThuan());
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
    public int changePassWord(User nv) {
        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            String sSQL = "update Login set Password=? where MaTK=?";
            conn = DatabaseUtils.getDBConnect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, nv.getPassword()); 
            sttm.setString(2, nv.getMaNV());

            if (sttm.executeUpdate() > 0) {
                System.out.println("Update thành công");
                return 1;
            }
        } catch (Exception e) {
            e.printStackTrace(); // In lỗi để xem nguyên nhân
            return -1; // Trả về -1 nếu có lỗi
        } finally {
            try {
                if (sttm != null) {
                    sttm.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace(); // In lỗi nếu có lỗi khi đóng kết nối
            }
        }
        return -1;
    }
    
    public int updateSecretKeyByID(User nv) {
        List<User> ls = new ArrayList<>();
        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            String sSQL = "update Login set SecretKey=?,IsActive2FA=0 where MaTK=?";
            conn = DatabaseUtils.getDBConnect();
            sttm= conn.prepareStatement(sSQL);
            sttm.setString(2, nv.getMaNV());
            sttm.setString(1, nv.getSecretKey());
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
    public int updatePassWordByID(User nv) {
        List<User> ls = new ArrayList<>();
        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            String sSQL = "update Login set Password=? where MaTK=?";
            conn = DatabaseUtils.getDBConnect();
            sttm= conn.prepareStatement(sSQL);
            sttm.setString(2, nv.getMaNV());
            sttm.setString(1, nv.getPassword());
            
            if (sttm.executeUpdate() > 0) {
                System.out.println("Đổi Mật Khẩu Thành công");
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
    public boolean  updateSecretByID(User nv) {
        List<User> ls = new ArrayList<>();
        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            String sSQL = "update Login set IsActive2FA =? where MaTK=?";
            conn = DatabaseUtils.getDBConnect();
            sttm= conn.prepareStatement(sSQL);
            sttm.setString(2, nv.getMaNV());
            sttm.setBoolean(1, nv.isIsActive2FA());
            if (sttm.executeUpdate() > 0) {
                System.out.println("Update thanh cong");
                return true;
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
        return true;
    }
    public List<User> getAllNhanVien () {
        List<User> ls = new ArrayList<>();
        Connection conn = null;
        Statement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL= "select * from Login";
            conn= DatabaseUtils.getDBConnect();
            sttm = conn.createStatement();
            rs = sttm.executeQuery (sSQL);
            while (rs.next()) {                
                User sv = new User();
                sv.setMaNV(rs.getString(1));
                sv.setPassword(rs.getString(2));
                sv.setTenNV(rs.getString(3));
                sv.setRole(rs.getInt(4));
                sv.setHinhAnh(rs.getString(5));
                sv.setBanNganh(rs.getString(6));
                
                ls.add(sv);
//                System.out.println(ls);
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
        return ls ;
    }
    
    
    
    
    
    
    public List<User> getallNhanViens(){
        List<User> ls = new ArrayList<>();
        Connection conn = null;
        Statement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL= "select * from Login";
            conn= DatabaseUtils.getDBConnect();
            sttm = conn.createStatement();
            rs = sttm.executeQuery (sSQL);
            while (rs.next()) {                
                User us = new User();
                us.setMaNV(rs.getString(1));
                us.setPassword(rs.getString(2));
                us.setTenNV(rs.getString(3));
                us.setRole(rs.getInt(4));
                us.setHinhAnh(rs.getString(5));
                us.setBanNganh(rs.getString(6));
                us.setTrangThai(rs.getInt(7));
                us.setHeSoLuong(rs.getFloat(8));
                us.setLuongThoaThuan(rs.getInt(9));
                ls.add(us);
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
    public List<User> getNhanVienByChamCong(){
        List<User> ls = new ArrayList<>();
        Connection conn = null;
        Statement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "select * from ChamCong";
            conn= DatabaseUtils.getDBConnect();
            sttm = conn.createStatement();
            rs = sttm.executeQuery (sSQL);
            while (rs.next()) {                
                User us = new User();
                us.setMaNV(rs.getString(1));
                
                ls.add(us);
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
    public int updateNhanVienByID(User nv) {
        List<User> ls = new ArrayList<>();
        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            String sSQL = "update Login set Password=?,TenTK=?,Vaitro=?,Hinhanh=?,BanNganh=?,TrangThai=?,HeSoLuong=?,LuongThoaThuan=? where MaTK=?";
            conn = DatabaseUtils.getDBConnect();
            sttm= conn.prepareStatement(sSQL);
            sttm.setString(9, nv.getMaNV());
            sttm.setString(1, nv.getPassword());
            sttm.setString(2, nv.getTenNV());
            sttm.setInt(3, nv.getRole());
            sttm.setString(4, nv.getHinhAnh());
            sttm.setString(5, nv.getBanNganh());
            sttm.setInt(6, nv.getTrangThai());
            sttm.setFloat(7, nv.getHeSoLuong());
            sttm.setFloat(8, nv.getLuongThoaThuan());
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
    public int delNhanVienByID(User maNV) {
        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            
            String sSQL = "update Login set TrangThai = ? where MaTK=?";
            conn = DatabaseUtils.getDBConnect();
            sttm= conn.prepareStatement(sSQL);
            sttm.setString(2, maNV.getMaNV());
            sttm.setInt(1, maNV.getTrangThai());
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
}
