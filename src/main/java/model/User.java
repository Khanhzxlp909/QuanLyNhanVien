/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author qivub
 */
public class User {
    private String TenNV;
    private String password;
    private int role;
    private String MaNV;
    private String HinhAnh;
    private String BanNganh;
    private int trangThai;
    private String secretKey;
    private boolean IsActive2FA;
    private float heSoLuong;
    private int luongThoaThuan;
    public User() {
    }

    public User(String TenNV, String password, int role, String MaNV, String HinhAnh, String BanNganh, int trangThai, String secretKey, boolean IsActive2FA, float heSoLuong, int luongThoaThuan) {
        this.TenNV = TenNV;
        this.password = password;
        this.role = role;
        this.MaNV = MaNV;
        this.HinhAnh = HinhAnh;
        this.BanNganh = BanNganh;
        this.trangThai = trangThai;
        this.secretKey = secretKey;
        this.IsActive2FA = IsActive2FA;
        this.heSoLuong = heSoLuong;
        this.luongThoaThuan = luongThoaThuan;
    }

    public int getLuongThoaThuan() {
        return luongThoaThuan;
    }

    public void setLuongThoaThuan(int luongThoaThuan) {
        this.luongThoaThuan = luongThoaThuan;
    }

    

    public float getHeSoLuong() {
        return heSoLuong;
    }

    public void setHeSoLuong(float heSoLuong) {
        this.heSoLuong = heSoLuong;
    }

   

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    

    public boolean isIsActive2FA() {
        return IsActive2FA;
    }

    public void setIsActive2FA(boolean IsActive2FA) {
        this.IsActive2FA = IsActive2FA;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
    public String getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(String HinhAnh) {
        this.HinhAnh = HinhAnh;
    }

    

    public String getTenNV() {
        return TenNV;
    }

    public void setTenNV(String TenNV) {
        this.TenNV = TenNV;
    }

    

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    

   

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getBanNganh() {
        return BanNganh;
    }

    public void setBanNganh(String BanNganh) {
        this.BanNganh = BanNganh;
    }

  
    
    
    
    
}
