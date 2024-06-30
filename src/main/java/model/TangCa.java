/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;
import java.sql.Time;

/**
 *
 * @author qivub
 */
public class TangCa {
    String Id;
    String maNV;
    String tenNV;
    Date ngayNhan;
    int caLam;
    Time gioVao;
    Time gioRa;
    Time thoiGianLam;

    public TangCa() {
    }

    public TangCa(String Id, String maNV, String tenNV, Date ngayNhan, int caLam, Time gioVao, Time gioRa, Time thoiGianLam) {
        this.Id = Id;
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.ngayNhan = ngayNhan;
        this.caLam = caLam;
        this.gioVao = gioVao;
        this.gioRa = gioRa;
        this.thoiGianLam = thoiGianLam;
    }

    public Time getThoiGianLam() {
        return thoiGianLam;
    }

    public void setThoiGianLam(Time thoiGianLam) {
        this.thoiGianLam = thoiGianLam;
    }

    

    public Time getGioVao() {
        return gioVao;
    }

    public void setGioVao(Time gioVao) {
        this.gioVao = gioVao;
    }

    public Time getGioRa() {
        return gioRa;
    }

    public void setGioRa(Time gioRa) {
        this.gioRa = gioRa;
    }

    

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public Date getNgayNhan() {
        return ngayNhan;
    }

    public void setNgayNhan(Date ngayNhan) {
        this.ngayNhan = ngayNhan;
    }

    public int getCaLam() {
        return caLam;
    }

    public void setCaLam(int caLam) {
        this.caLam = caLam;
    }

   

    
    
}
