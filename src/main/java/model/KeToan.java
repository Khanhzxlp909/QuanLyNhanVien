/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author qivub
 */
public class KeToan {
    int ID;
    String MaNV;
    float HeSoLuong;
    int LuongThoaThuan;
    int SoCong;
    int luongCb;
    int luongTg;
    int tongLuong;

    public KeToan() {
    }

    public KeToan(int ID, String MaNV, float HeSoLuong, int LuongThoaThuan, int SoCong, int luongCb, int luongTg, int tongLuong) {
        this.ID = ID;
        this.MaNV = MaNV;
        this.HeSoLuong = HeSoLuong;
        this.LuongThoaThuan = LuongThoaThuan;
        this.SoCong = SoCong;
        this.luongCb = luongCb;
        this.luongTg = luongTg;
        this.tongLuong = tongLuong;
    }
    
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    public float getHeSoLuong() {
        return HeSoLuong;
    }

    public void setHeSoLuong(float HeSoLuong) {
        this.HeSoLuong = HeSoLuong;
    }

    public int getLuongThoaThuan() {
        return LuongThoaThuan;
    }

    public void setLuongThoaThuan(int LuongThoaThuan) {
        this.LuongThoaThuan = LuongThoaThuan;
    }

    public int getSoCong() {
        return SoCong;
    }

    public void setSoCong(int SoCong) {
        this.SoCong = SoCong;
    }

    public int getLuongCb() {
        return luongCb;
    }

    public void setLuongCb(int luongCb) {
        this.luongCb = luongCb;
    }

    public int getLuongTg() {
        return luongTg;
    }

    public void setLuongTg(int luongTg) {
        this.luongTg = luongTg;
    }

    public int getTongLuong() {
        return tongLuong;
    }

    public void setTongLuong(int tongLuong) {
        this.tongLuong = tongLuong;
    }

    

    
}
