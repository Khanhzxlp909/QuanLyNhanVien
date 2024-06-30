/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package main;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import database.RoundedCornerBorder2;
import database.RoundedCornerBorder2;
import database.SessionHelper;
import static java.awt.Color.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.GennerateQRCode;
import static model.GennerateQRCode.createQRCode;
import static model.GennerateQRCode.generateSecretKey;
import model.NhanVienDao;
import model.User;

    

/**
 *
 * @author qivub
 */
public class NhanVienMainFrame extends javax.swing.JFrame {
    NhanVienDao dao = new NhanVienDao();
        DefaultTableModel model;
        String strHinhAnh = null;
//        NhanVien nv = new NhanVien(null);
    /**
     * Creates new form NhanVienMainFrame
     */
    public NhanVienMainFrame() {
        initComponents();
        if (SessionHelper.getRole() == -1) {
            System.out.println("sai vai trò");
            JOptionPane.showMessageDialog(this, "sai vai trò");
            return;
        }else if(SessionHelper.getRole() == 0){
            System.out.println("nhan vien");
            JOptionPane.showMessageDialog(this, "Quyền hạn hiện tại: Nhân viên, Không đủ quyền hạn truy cập");
            this.dispose();
            
        }else if(SessionHelper.getRole() == 2){
            
            System.out.println("quanly");
            
            this.setVisible(true);
        }else if(SessionHelper.getRole() == 1){
            this.setVisible(true);
        }
        model = (DefaultTableModel) tbNhanVien.getModel();
        fillDataTABLE();
    }
    public void fillDataTABLE() {
        model.setRowCount(0); // Xóa tất cả các dòng hiện tại trong bảng
        for (User sv : dao.getallNhanViens()) {
            Object rowData[] = new Object[9];
            rowData[0] = sv.getMaNV();
            rowData[1] = sv.getPassword();
            rowData[2] = sv.getTenNV();
            if (sv.getRole() == 1) {
                rowData[3] = "Giám đốc";
            } else if (sv.getRole() == 2) {
                rowData[3] = "Quản lý";
            } else if(sv.getRole() == 0){
                rowData[3] = "Nhân viên";
            }else if(sv.getRole() == 3){
                rowData[3] = "Thư Ký Chân Dài";
            }else if(sv.getRole() == 4){
                rowData[3] = "Đội trưởng đội cổ vũ";
            }
            rowData[4]=sv.getBanNganh();
            if (sv.getTrangThai() == 0) {
                rowData[5] = "Đang Hoạt Động";
            } else if (sv.getTrangThai() == 1) {
                rowData[5] = "Ko Hoạt Động";
            }
            rowData[6]=sv.getHinhAnh();
            rowData[7]=sv.getHeSoLuong();
            rowData[8]=sv.getLuongThoaThuan();
            model.addRow(rowData);
        }
    }
    public void reset(){
        
        txtMaNV.setText("");
        txtPass.setText("");
        lblHinhAnh.setText("");
        txtTenNV.setText("");
//        rdNV.isSelected();
        lblHinhAnh.setIcon(null);
        strHinhAnh= null;
        txtBan.setText("");
        rdDangHD.isSelected();
        txtLuongThoaThuan.setText("");
        txtHsLuong.setText("");
    }
    public void hinhAnh(){
        try {
            JFileChooser jfc = new JFileChooser("D:\\NetBeansProjects\\DuAn1_ PRO1041\\src\\main\\resources\\Avatar");
            jfc.showOpenDialog(null);
            File file = jfc.getSelectedFile();
            Image img = ImageIO.read(file);
            strHinhAnh = file.getName();
            lblHinhAnh.setText("");
            int width = lblHinhAnh.getWidth();
            int height = lblHinhAnh.getHeight();
            lblHinhAnh.setIcon(new ImageIcon(img.getScaledInstance(width, height, 0)));
        } catch (IOException ex) {
            System.out.println("Error: "+ ex.toString());
        }
    }    
    public User getModels() throws ParseException{
        User us= new User();
        us.setMaNV(txtMaNV.getText());
        us.setPassword(txtPass.getText());
        us.setTenNV(txtTenNV.getText());
        
        if (rdQly.isSelected()) {
            us.setRole(2);
        } else if(rdGd.isSelected()){
            us.setRole(1);
        }else if(rdNV.isSelected()) {
            us.setRole(0);
        }else if(rdThuKy.isSelected()) {
            us.setRole(3);
        }else if(rdDTDCV.isSelected()) {
            us.setRole(4);
        }
        us.setHinhAnh(lblHinhAnh.getText());
        if (strHinhAnh == null) {
            us.setHinhAnh("No Avatar");
        }else{
            us.setHinhAnh(strHinhAnh);
        }
        
        if (rdKhongHoatDong.isSelected()) {
            us.setTrangThai(1);
        } else if(rdDangHD.isSelected()){
            us.setTrangThai(0);
        }
        
        us.setBanNganh(txtBan.getText()); 
        float heSoLuong = Float.parseFloat(txtHsLuong.getText());
        us.setHeSoLuong(heSoLuong);
        
       
        int luongTT = Integer.parseInt(txtLuongThoaThuan.getText());
        us.setLuongThoaThuan(luongTT);
        return us;
    }
    
    public boolean validateForm(){
        
        if (txtMaNV.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mã Nhân Viên chưa nhập");
            return false;
            
        }else if (txtTenNV.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Thiếu tên nhân viên");
            return false;
            
        }else if (txtPass.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Thiếu mật khẩu đăng nhập tài khoản nhân viên");
            return false;
            
        }else if (txtBan.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Thiếu ban ngành");
            return false;
            
        }
        return true;
    }   
    
    public void setModel(User nv){
        String anh = null;
        int row = tbNhanVien.getSelectedRow();
        if(row<0){
            JOptionPane.showConfirmDialog(this, "Không có thông tin");
            return;
        }else{
            txtMaNV.setText((String) tbNhanVien.getValueAt(row, 0));
            txtPass.setText((String) tbNhanVien.getValueAt(row, 1));
            txtTenNV.setText((String) tbNhanVien.getValueAt(row, 2));
       
            Object rawValue = (String) tbNhanVien.getValueAt(row, 3);
            if (rawValue instanceof Integer) {
            int quyenhan = (int) rawValue;
            nv.setRole(quyenhan);
                if(quyenhan == 1 ){
                    rdGd.setSelected(true);
                }else if(quyenhan == 2){
                    rdQly.setSelected(true);
                }else if(quyenhan == 3){
                    rdThuKy.setSelected(true);
                }else if(quyenhan == 4){
                    rdDTDCV.setSelected(true);
                }else if(quyenhan == 0){
                    rdNV.setSelected(true);
                }
            }
            txtBan.setText((String) tbNhanVien.getValueAt(row, 4));
            Object rawValues = tbNhanVien.getValueAt(row, 5);
            if (rawValues instanceof Integer) {
            int trangthai = (int) rawValues;
            nv.setTrangThai(trangthai);
                if(trangthai == 1 ){
                    rdKhongHoatDong.setSelected(true);
                }else if(trangthai == 0){
                    rdDangHD.setSelected(true);
                }
            }
            anh = (String) tbNhanVien.getValueAt(row, 6);
            lblHinhAnh.setText((String) tbNhanVien.getValueAt(row, 6));
            if (!anh.equals("No Avatar")) {
                try {
                    BufferedImage img = ImageIO.read(new File("D:\\NetBeansProjects\\DuAn1_ PRO1041\\src\\main\\resources\\Avatar\\" + anh));
                    int width = lblHinhAnh.getWidth();
                    int height = lblHinhAnh.getHeight();
                    lblHinhAnh.setIcon(new ImageIcon(img.getScaledInstance(width, height, 0)));
                } catch (IOException ex) {
                    System.out.println("Error: " + ex.toString());
                    ex.printStackTrace();
                }
            } else {
                // Nếu không có hình ảnh, xóa hình ảnh trên lblHinhAnh
                lblHinhAnh.setIcon(null);
            }
            txtHsLuong.setText(String.valueOf((Float) tbNhanVien.getValueAt(row, 7)));
            txtLuongThoaThuan.setText(String.valueOf((Integer) tbNhanVien.getValueAt(row, 8)));

        
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        rdQly = new javax.swing.JRadioButton();
        rdNV = new javax.swing.JRadioButton();
        btnThem = new javax.swing.JButton(){
            @Override protected void paintComponent(Graphics g) {
                if (!isOpaque() && getBorder() instanceof RoundedCornerBorder2) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setPaint(getBackground());
                    g2.fill(((RoundedCornerBorder2) getBorder()).getBorderShape(
                        0, 0, getWidth() - 1, getHeight() - 1));
                g2.dispose();
            } super.paintComponent(g);}
            @Override public void updateUI() {
                super.updateUI();
                setOpaque(false);
            }
        };
        lblHinhAnh = new javax.swing.JLabel();
        btnLuu = new javax.swing.JButton(){
            @Override protected void paintComponent(Graphics g) {
                if (!isOpaque() && getBorder() instanceof RoundedCornerBorder2) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setPaint(getBackground());
                    g2.fill(((RoundedCornerBorder2) getBorder()).getBorderShape(
                        0, 0, getWidth() - 1, getHeight() - 1));
                g2.dispose();
            } super.paintComponent(g);}
            @Override public void updateUI() {
                super.updateUI();
                setOpaque(false);
            }
        };
        btnSua = new javax.swing.JButton(){
            @Override protected void paintComponent(Graphics g) {
                if (!isOpaque() && getBorder() instanceof RoundedCornerBorder2) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setPaint(getBackground());
                    g2.fill(((RoundedCornerBorder2) getBorder()).getBorderShape(
                        0, 0, getWidth() - 1, getHeight() - 1));
                g2.dispose();
            } super.paintComponent(g);}
            @Override public void updateUI() {
                super.updateUI();
                setOpaque(false);
            }
        };
        btnXoa = new javax.swing.JButton();
        txtMaNV = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtPass = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtTenNV = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        btnMain = new javax.swing.JButton(){
            @Override protected void paintComponent(Graphics g) {
                if (!isOpaque() && getBorder() instanceof RoundedCornerBorder2) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setPaint(getBackground());
                    g2.fill(((RoundedCornerBorder2) getBorder()).getBorderShape(
                        0, 0, getWidth() - 1, getHeight() - 1));
                g2.dispose();
            } super.paintComponent(g);}
            @Override public void updateUI() {
                super.updateUI();
                setOpaque(false);
            }
        };
        jPanel3 = new javax.swing.JPanel();
        btnTimKiem = new javax.swing.JButton();
        txtFindMaNV = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtBan = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        rdGd = new javax.swing.JRadioButton();
        rdThuKy = new javax.swing.JRadioButton();
        rdDTDCV = new javax.swing.JRadioButton();
        jButton2 = new javax.swing.JButton();
        btnResetSecretKey = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        rdDangHD = new javax.swing.JRadioButton();
        rdKhongHoatDong = new javax.swing.JRadioButton();
        jLabel17 = new javax.swing.JLabel();
        txtHsLuong = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtLuongThoaThuan = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbNhanVien = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Quản Lý Nhân Viên");

        jTabbedPane1.setBorder(new javax.swing.border.MatteBorder(null));
        jTabbedPane1.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.LEFT);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        buttonGroup1.add(rdQly);
        rdQly.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        rdQly.setText("Quản Lý");
        rdQly.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdQlyActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdNV);
        rdNV.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        rdNV.setText("Nhân Viên");
        rdNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdNVActionPerformed(evt);
            }
        });

        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Clipboard.png"))); // NOI18N
        btnThem.setText("Clear");
        btnThem.setBorder(new RoundedCornerBorder2(red));
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        lblHinhAnh.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblHinhAnh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHinhAnh.setText("Chọn ảnh ");
        lblHinhAnh.setBorder(new RoundedCornerBorder2());
        lblHinhAnh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHinhAnhMouseClicked(evt);
            }
        });

        btnLuu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Save.png"))); // NOI18N
        btnLuu.setText("Lưu");
        btnLuu.setBorder(new RoundedCornerBorder2(red));
        btnLuu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuActionPerformed(evt);
            }
        });

        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Notes.png"))); // NOI18N
        btnSua.setText("Sửa");
        btnSua.setBorder(new RoundedCornerBorder2(red));
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Delete.png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.setBorder(new RoundedCornerBorder2(red));
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        txtMaNV.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtMaNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaNVActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("TênNV:");

        txtPass.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPassActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Phân Quyền:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Password:");

        txtTenNV.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Mã NV:");

        btnMain.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Home.png"))); // NOI18N
        btnMain.setText("Cancel");
        btnMain.setBorder(new RoundedCornerBorder2(red));
        btnMain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMainActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Search.png"))); // NOI18N
        btnTimKiem.setText("Tìm Kiếm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        txtFindMaNV.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtFindMaNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFindMaNVActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel15.setText("Mã NV:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel15)
                .addGap(18, 18, 18)
                .addComponent(txtFindMaNV, javax.swing.GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE)
                .addGap(36, 36, 36)
                .addComponent(btnTimKiem)
                .addGap(32, 32, 32))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFindMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiem))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txtBan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel16.setText("Hệ Số Lương");

        buttonGroup1.add(rdGd);
        rdGd.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        rdGd.setText("Giám Đốc");
        rdGd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdGdActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdThuKy);
        rdThuKy.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        rdThuKy.setText("Thư ký");
        rdThuKy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdThuKyActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdDTDCV);
        rdDTDCV.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        rdDTDCV.setSelected(true);
        rdDTDCV.setText("Đội trưởng đội cổ vũ");
        rdDTDCV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdDTDCVActionPerformed(evt);
            }
        });

        jButton2.setText("QrCode");
        jButton2.setBorder(new RoundedCornerBorder2());
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        btnResetSecretKey.setText("Reset SecretKey");
        btnResetSecretKey.setBorder(new RoundedCornerBorder2());
        btnResetSecretKey.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetSecretKeyActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Trạng Thái:");

        buttonGroup3.add(rdDangHD);
        rdDangHD.setText("Đang Hoạt Động");

        buttonGroup3.add(rdKhongHoatDong);
        rdKhongHoatDong.setText("Không Hoạt Động");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel17.setText("Lương Thỏa Thuận");

        txtHsLuong.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel18.setText("Ban Ngành: ");

        txtLuongThoaThuan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(rdGd, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(32, 32, 32)
                                        .addComponent(rdQly, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(12, 12, 12)
                                        .addComponent(rdNV, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(rdThuKy)
                                        .addGap(65, 65, 65)
                                        .addComponent(rdDTDCV, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(30, 30, 30)
                        .addComponent(lblHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)
                        .addComponent(btnLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(59, 59, 59)
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51)
                        .addComponent(btnMain))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jLabel18))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel16)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(txtHsLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(60, 60, 60)
                                .addComponent(jLabel17)
                                .addGap(18, 18, 18)
                                .addComponent(txtLuongThoaThuan, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(txtBan, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(184, 184, 184)
                                .addComponent(btnResetSecretKey, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(10, 10, 10)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 279, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdDangHD)
                .addGap(18, 18, 18)
                .addComponent(rdKhongHoatDong)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(jLabel4)
                        .addGap(40, 40, 40)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addComponent(txtTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rdGd)
                            .addComponent(rdQly)
                            .addComponent(rdNV))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rdThuKy)
                            .addComponent(rdDTDCV)))
                    .addComponent(lblHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnResetSecretKey, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtLuongThoaThuan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHsLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdDangHD)
                    .addComponent(rdKhongHoatDong))
                .addGap(46, 46, 46)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMain, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jTabbedPane1.addTab("Danh sách", new javax.swing.ImageIcon(getClass().getResource("/images/To do list.png")), jPanel2); // NOI18N

        tbNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã NV", "Password", "TênNV", "Quyền Hạn", "Ban Ngành", "Trạng Thái", "Hình Ảnh", "Hệ Số Lương", "Lương Thỏa Thuận"
            }
        ));
        tbNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbNhanVienMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbNhanVien);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1040, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 651, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(11, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("List Nhân Viên", new javax.swing.ImageIcon(getClass().getResource("/images/Application form.png")), jPanel4); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Quản Lý Nhân Viên");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(234, 234, 234)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 670, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rdQlyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdQlyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdQlyActionPerformed

    private void rdNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdNVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdNVActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        reset();
    }//GEN-LAST:event_btnThemActionPerformed

    private void lblHinhAnhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHinhAnhMouseClicked
        hinhAnh();
    }//GEN-LAST:event_lblHinhAnhMouseClicked

    private void btnLuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuActionPerformed
        // TODO add your handling code here:
        if (validateForm()) {
            try {
                User nv = getModels();
                String secretKey = generateSecretKey();
                System.out.println(secretKey);
                nv.setSecretKey(secretKey);
                
                if (dao.add(nv)>0) {
                    JOptionPane.showMessageDialog(this, "Lưu thành công! ");
                    fillDataTABLE();
                }
            } catch (ParseException ex) {
                Logger.getLogger(NhanVienMainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnLuuActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed

        if (validateForm()) {
            try {
                User nv = getModels();
                if (dao.updateNhanVienByID(nv)>0){
                    JOptionPane.showMessageDialog(this, "Cập nhật thành công");
                    fillDataTABLE();
                }
            } catch (ParseException ex) {
                Logger.getLogger(NhanVienMainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập thông tin ");
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
 
        
        if (validateForm()) {
            try {
                User nv = getModels();
                if (dao.delNhanVienByID(nv)>0){
                    JOptionPane.showMessageDialog(this, "Xóa thành công");
                    fillDataTABLE();
                }
            } catch (ParseException ex) {
                Logger.getLogger(NhanVienMainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập thông tin ");
        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void tbNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbNhanVienMouseClicked
        // TODO add your handling code here:
        int id = tbNhanVien.rowAtPoint(evt.getPoint());
        String manv = tbNhanVien.getValueAt(id, 0).toString();
        User sv = dao.getNhanVienByID(manv);
        setModel(sv);
    }//GEN-LAST:event_tbNhanVienMouseClicked

    private void txtMaNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaNVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaNVActionPerformed

    private void txtPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPassActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPassActionPerformed

    private void btnMainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMainActionPerformed
        
        this.dispose();
    }//GEN-LAST:event_btnMainActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
        if (txtFindMaNV.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập Mã Nhân Viên");
        }else{
            User nv = dao.getNhanVienByID(txtFindMaNV.getText());

            txtMaNV.setText(nv.getMaNV());
            txtPass.setText(nv.getPassword());
            txtTenNV.setText(nv.getTenNV());
            txtBan.setText(nv.getBanNganh());
            lblHinhAnh.setText(nv.getHinhAnh());
            txtHsLuong.setText(String.valueOf((Float) nv.getHeSoLuong()));
            txtLuongThoaThuan.setText(String.valueOf((Integer) nv.getLuongThoaThuan()));
            Object rawValue = nv.getRole();
            if (rawValue instanceof Integer) {
            int quyenhan = (int) rawValue;
            nv.setRole(quyenhan);
                if(quyenhan == 1 ){
                    rdGd.setSelected(true);
                }else if(quyenhan == 2){
                    rdQly.setSelected(true);
                }else if(quyenhan == 3){
                    rdThuKy.setSelected(true);
                }else if(quyenhan == 4){
                    rdDTDCV.setSelected(true);
                }else if(quyenhan == 0){
                    rdNV.setSelected(true);
                }
            }
            
            Object rawValues = nv.getTrangThai();
            if (rawValues instanceof Integer) {
            int trangthai = (int) rawValues;
            nv.setTrangThai(trangthai);
                if(trangthai == 1 ){
                    rdKhongHoatDong.setSelected(true);
                }else if(trangthai == 0){
                    rdDangHD.setSelected(true);
                }
            }
            
            String anh = null;
            anh = nv.getHinhAnh();
            lblHinhAnh.setText((anh));
            if (!anh.equals("No Avatar")) {
                try {
                    BufferedImage img = ImageIO.read(new File("D:\\NetBeansProjects\\DuAn1_ PRO1041\\src\\main\\resources\\Avatar\\" + anh));
                    int width = lblHinhAnh.getWidth();
                    int height = lblHinhAnh.getHeight();
                    lblHinhAnh.setIcon(new ImageIcon(img.getScaledInstance(width, height, 0)));
                } catch (IOException ex) {
                    System.out.println("Error: " + ex.toString());
                    ex.printStackTrace();
                }
            } else {
                lblHinhAnh.setIcon(null);
            }
        }
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void txtFindMaNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFindMaNVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFindMaNVActionPerformed

    private void rdGdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdGdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdGdActionPerformed

    private void rdThuKyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdThuKyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdThuKyActionPerformed

    private void rdDTDCVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdDTDCVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdDTDCVActionPerformed
    
    public static void F2a(){
        GennerateQRCode gn = new GennerateQRCode();
        String secretKey = gn.generateSecretKey();
        String accountName = txtMaNV.getText();
        System.out.println(accountName);
        gn.createQRCode(secretKey, accountName);
    }
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        QRCodeF2a main = new QRCodeF2a();
        main.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnResetSecretKeyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetSecretKeyActionPerformed
        // TODO add your handling code here:
        if (validateForm()) {
            try {
                User nv = getModels();
                String secretKey = generateSecretKey();
                System.out.println(secretKey);
                nv.setSecretKey(secretKey);
                JOptionPane.showMessageDialog(this, "SecretKey đã được reset thành công: " + secretKey);
                if (dao.updateSecretKeyByID(nv)>0){
                    JOptionPane.showMessageDialog(this, "Cập nhật thành công");
                    fillDataTABLE();
                }
            } catch (ParseException ex) {
                Logger.getLogger(NhanVienMainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập thông tin ");
        }
    }//GEN-LAST:event_btnResetSecretKeyActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NhanVienMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NhanVienMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NhanVienMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NhanVienMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NhanVienMainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLuu;
    private javax.swing.JButton btnMain;
    private javax.swing.JButton btnResetSecretKey;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblHinhAnh;
    public static javax.swing.JRadioButton rdDTDCV;
    private javax.swing.JRadioButton rdDangHD;
    public static javax.swing.JRadioButton rdGd;
    private javax.swing.JRadioButton rdKhongHoatDong;
    public static javax.swing.JRadioButton rdNV;
    public static javax.swing.JRadioButton rdQly;
    public static javax.swing.JRadioButton rdThuKy;
    private javax.swing.JTable tbNhanVien;
    public static javax.swing.JTextField txtBan;
    private javax.swing.JTextField txtFindMaNV;
    public static javax.swing.JTextField txtHsLuong;
    public static javax.swing.JTextField txtLuongThoaThuan;
    public static javax.swing.JTextField txtMaNV;
    public static javax.swing.JTextField txtPass;
    public static javax.swing.JTextField txtTenNV;
    // End of variables declaration//GEN-END:variables
}
