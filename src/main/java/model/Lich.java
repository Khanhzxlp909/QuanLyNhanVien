/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.util.Date;
/**
 *
 * @author qivub
 */
public class Lich extends User{
    int IDCV;
    String CongViec;
    String noidung;
    Date deadline;
    String MaNV;
    String TenNV;
    int TinhTrang;
    String LydoCham;
    public Lich() {
        
    }

    public Lich(int IDCV, String CongViec, String noidung, Date deadline, String MaNV, String TenNV, int TinhTrang, String LydoCham) {
        this.IDCV = IDCV;
        this.CongViec = CongViec;
        this.noidung = noidung;
        this.deadline = deadline;
        this.MaNV = MaNV;
        this.TenNV = TenNV;
        this.TinhTrang = TinhTrang;
        this.LydoCham = LydoCham;
    }

    

    public int getTinhTrang() {
        return TinhTrang;
    }

    public void setTinhTrang(int TinhTrang) {
        this.TinhTrang = TinhTrang;
    }

    public String getLydoCham() {
        return LydoCham;
    }

    public void setLydoCham(String LydoCham) {
        this.LydoCham = LydoCham;
    }

    public int getIDCV() {
        return IDCV;
    }

    public void setIDCV(int IDCV) {
        this.IDCV = IDCV;
    }
    
    
    public String getCongViec() {
        return CongViec;
    }

    public void setCongViec(String CongViec) {
        this.CongViec = CongViec;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    public String getTenNV() {
        return TenNV;
    }

    public void setTenNV(String TenNV) {
        this.TenNV = TenNV;
    }
    
}
