/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.util.ArrayList;
import java.util.List;
import model.User;
import database.DatabaseUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import database.SessionHelper;
/**
 *
 * @author qivub
 */
public class UserDao {
    List<User> ls = new ArrayList<User>();


    public User getUserByID(String username){
         Connection conn = null;
         PreparedStatement sttm = null;
         ResultSet rs = null;
         User ee = new User();
         
         try {
            String sSQL = "select * from Login where MaTK = ?";
            
             conn= DatabaseUtils.getDBConnect();
             sttm = conn.prepareStatement (sSQL);
             sttm.setString(1, username);
             rs = sttm.executeQuery();
             if (rs.next()) {                 
                 ee.setMaNV(rs.getString(1));
                 SessionHelper.setIdUser(rs.getString(1));
                 ee.setPassword(rs.getString(2));
                  SessionHelper.setIdPassword(rs.getString(2));
                 ee.setTenNV(rs.getString(3));
//                
                 SessionHelper.setRole(rs.getInt(4));
                 ee.setHinhAnh(rs.getString(5));
                 SessionHelper.setAvtar(rs.getString(5));
                 ee.setSecretKey(rs.getString(10));
                 Boolean isActive2FA = (Boolean) rs.getObject(11);
                 ee.setIsActive2FA(isActive2FA);

                 return ee;
             }
        } catch (Exception e) {
             System.out.println("Error:"+e.toString());
             e.printStackTrace();
        }finally{
             try {
                 conn.close();
                 rs.close();
                 sttm.close();
             } catch (Exception e) {
             }
         }
         return null; 
    }
    
    public String getPasswordByID(String id){
         Connection conn = null;
         PreparedStatement sttm = null;
         ResultSet rs = null;
         User ee = new User();
         
         try {
            String sSQL = "select password from Login where MaTK = ?";
            
             conn= DatabaseUtils.getDBConnect();
             sttm = conn.prepareStatement (sSQL);
             sttm.setString(1, id);
             rs = sttm.executeQuery();
             if (rs.next()) {
                 return rs.getString(1);
             }
        } catch (Exception e) {
             System.out.println("Error:"+e.toString());
             e.printStackTrace();
        }finally{
             try {
                 conn.close();
                 rs.close();
                 sttm.close();
             } catch (Exception e) {
             }
         }
         return ""; 
    }
    
    public String getAccountNameByID(String id){
         Connection conn = null;
         PreparedStatement sttm = null;
         ResultSet rs = null;
         User ee = new User();
         
         try {
            String sSQL = "select TenTK from Login where MaTK = ?";
            
             conn= DatabaseUtils.getDBConnect();
             sttm = conn.prepareStatement (sSQL);
             sttm.setString(1, id);
             rs = sttm.executeQuery();
             if (rs.next()) {
                 return rs.getString(1);
             }
        } catch (Exception e) {
             System.out.println("Error:"+e.toString());
             e.printStackTrace();
        }finally{
             try {
                 conn.close();
                 rs.close();
                 sttm.close();
             } catch (Exception e) {
             }
         }
         return ""; 
    }
    
    public boolean checkLogin(String username, String pass){
        User user = getUserByID(username);        
        if (user != null) {
            System.out.println(user.getRole());
            if ( user.getPassword().equals(pass)) {
                return true;
//                if (secretKey.get) {
//                    
//                }
            }else{
                
            }
        }
        return false;
    }
    public boolean checkForgot(String username, String tennv){
        User user = getUserByID(username);        
        if (user != null) {
            System.out.println(user.getTenNV());
            if ( user.getTenNV().equals(tennv)) {
                return true;
            }else{
                
            }
        }
        return false;
    }
    public boolean isActive2FaByUserId(String id) {
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        User ee = new User();
        
        try {
            String sSQL = "select IsActive2FA from Login where MaTK = ?";
            
             conn= DatabaseUtils.getDBConnect();
             sttm = conn.prepareStatement (sSQL);
             sttm.setString(1, id);
             
             rs = sttm.executeQuery();
             if (rs.next()) {
                 return rs.getBoolean(1);
             }
             
        } catch (Exception e) {
             System.out.println("Error:"+e.toString());
             e.printStackTrace();
        } finally{
             try {
                 conn.close();
                 rs.close();
                 sttm.close();
             } catch (Exception e) {
             }
         }       
        return false;
    }

}
