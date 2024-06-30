/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author qivub
 */
public class ChamCong {
    int ID;
    String manv;
    String tennv;
    String NgayNhan;
    String GioVao;
    String GioRa;
    int soNgayCong;

    public ChamCong(int ID, String manv, String tennv, String NgayNhan, String GioVao, String GioRa, int soNgayCong) {
        this.ID = ID;
        this.manv = manv;
        this.tennv = tennv;
        this.NgayNhan = NgayNhan;
        this.GioVao = GioVao;
        this.GioRa = GioRa;
        this.soNgayCong = soNgayCong;
    }

    public String getGioRa() {
        return GioRa;
    }

    public void setGioRa(String GioRa) {
        this.GioRa = GioRa;
    }

    

    public int getSoNgayCong() {
        return soNgayCong;
    }

    public void setSoNgayCong(int soNgayCong) {
        this.soNgayCong = soNgayCong;
    }
    

   
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    

    public ChamCong() {
    }

    public String getManv() {
        return manv;
    }

    public void setManv(String manv) {
        this.manv = manv;
    }

    public String getTennv() {
        return tennv;
    }

    public void setTennv(String tennv) {
        this.tennv = tennv;
    }

    public String getNgayNhan() {
        return NgayNhan;
    }

    public void setNgayNhan(String NgayNhan) {
        this.NgayNhan = NgayNhan;
    }

    public String getGioVao() {
        return GioVao;
    }

    public void setGioVao(String GioVao) {
        this.GioVao = GioVao;
    }

    
    
}
