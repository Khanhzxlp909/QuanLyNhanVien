/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.util.Date;
import java.sql.Time;

/**
 *
 * @author qivub
 */
public class SessionHelper {
    
    
    public static int CURRENT_ROLE = -1;    
    public static String CURRENT_USER_ID = "";
    public static String CURRENT_USER_NAME = "";
    public static String AVTATAR = "";
    
    public static String CURRENT_CC_TimeVao;
    public static String CURRENT_CC_TimeRa;
    public static String CURRENT_ChamCong_ID;
    
    
     public static void setNgayNhan(String id) {
        CURRENT_ChamCong_ID = id;
    }

    public static String getNgayNhan() {
        return CURRENT_ChamCong_ID;
    }
    public static void setTimeVao(String timeVao) {
        CURRENT_CC_TimeVao = timeVao;
    }

    public static String getTimeVao() {
        return CURRENT_CC_TimeVao;
    }
    
    
    public static void setTimeRa(String timeRa) {
        CURRENT_CC_TimeRa = timeRa;
    }

    public static String getTimeRa() {
        return CURRENT_CC_TimeRa;
    }
    
    public static void setRole(int role) {
        CURRENT_ROLE = role;
    }

    public static int getRole() {
        return CURRENT_ROLE;
    }
    
    public static void setIdUser(String id) {
        CURRENT_USER_ID = id;
    }

    public static String getIdUser() {
        return CURRENT_USER_ID;
    }
    public static void setIdPassword(String password) {
        CURRENT_USER_NAME = password;
    }

    public static String getIdPassword() {
        return CURRENT_USER_NAME;
    }
    public static void setAvtar(String avatar) {
        AVTATAR = avatar;
    }

    public static String getAvtar() {
        return AVTATAR;
    }
    

    public static boolean isValid() {
        return CURRENT_ROLE == -1;
    }

    public static void removeRole() {
        CURRENT_ROLE = -1;
    }  
}
