/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package main;

import database.DatabaseUtils;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.table.DefaultTableModel;
import model.NhanVienDao;
import model.User;
import main.NhanVienMainFrame;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.Timer;
import database.SessionHelper;
import database.XImage;
import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.ChamCong;
import model.TangCa;
import model.TangCaDao;


/**
 *
 * @author qivub
 */
public class WindowAPP extends javax.swing.JFrame {
    NhanVienDao dao = new NhanVienDao();
    TangCaDao daoo = new TangCaDao();
        DefaultTableModel model;
        String strHinhAnh = null;
        NhanVienMainFrame nvDL;
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        private static Date buttonPressTime;
        TangCa tc = new TangCa(); 
        static String dongho;
    /**
     * Creates new form WindowAPP
     */
    public WindowAPP() {
 
        initComponents();
        
        if (SessionHelper.getRole() == -1) {
            System.out.println("sai vai trò");
            JOptionPane.showMessageDialog(this, "Sai Vai Trò");
            return;
        }else if(SessionHelper.getRole() == 0){
            lblRole.setText("Nhân Viên");
            System.out.println("nhan vien");
            this.setVisible(true);
        }else if(SessionHelper.getRole() == 2){
            System.out.println("quanly");
            lblRole.setText("Quản Lý" );
            this.setVisible(true);
        }else if(SessionHelper.getRole() == 1){
            
            this.setVisible(true);
            lblRole.setText("Giám đốc" );
            System.out.println("Giám Đốc");
        }else if(SessionHelper.getRole() == 3){
             System.out.println("nhan vien");
             lblRole.setText("Thư Ký" );
            this.setVisible(true);       
        }else if(SessionHelper.getRole() == 4){
            
            lblRole.setText("Đội trưởng đội cổ vũ" );
             System.out.println("nhan vien");
            this.setVisible(true);       
        }
        
        model = (DefaultTableModel) tbNhanVien.getModel();
        fillDataTABLE();
        openWelcome();
        Account();
        startNgayGio();
    }
    public void Account(){
        User us = new User();
        String anh = null;
        lblMaNV.setText(SessionHelper.getIdUser());
        lblTenNV.setText(SessionHelper.getIdPassword());
        anh = (String) SessionHelper.getAvtar();
//        System.out.println("Avatar: "+ anh);
            lblAvatar.setText((String) SessionHelper.getAvtar() );
            if (!anh.equals("No Avatar")) {
                try {
                    BufferedImage img = ImageIO.read(new File("D:\\NetBeansProjects\\DuAn1_ PRO1041\\src\\main\\resources\\Avatar\\" + anh));
                    int width = lblAvatar.getWidth();
                    int height = lblAvatar.getHeight();
                    lblAvatar.setIcon(new ImageIcon(img.getScaledInstance(width, height, 0)));
                } catch (IOException ex) {
                    System.out.println("Error: " + ex.toString());
                    ex.printStackTrace();
                }
            } else {
                lblAvatar.setIcon(null);
            }
    }
    void openWelcome(){
        
        if(SessionHelper.getRole() == 0){
            btnDeadline.setVisible(true);
            btnBangLuong.setVisible(false);
            btnQL.setVisible(false);
            
            btnTangCa.setVisible(true);
            btnKT.setVisible(false);
        }else if(SessionHelper.getRole() == 1){
            btnDeadline.setVisible(true);
            btnBangLuong.setVisible(true);
            btnQL.setVisible(true);
            
            btnTangCa.setVisible(true);
            btnKT.setVisible(true);
        }else if(SessionHelper.getRole() == 2){
            btnDeadline.setVisible(true);
            btnBangLuong.setVisible(true);
            btnQL.setVisible(true);
            btnTangCa.setVisible(true);
            btnKT.setVisible(true);
        }else if(SessionHelper.getRole() == 3){                    
            btnDeadline.setVisible(true);
            btnBangLuong.setVisible(true);
            btnQL.setVisible(true);
            btnTangCa.setVisible(true);
            btnKT.setVisible(true);    
        }else if(SessionHelper.getRole() == 4){                    
            btnDeadline.setVisible(true);
            btnBangLuong.setVisible(true);
            btnQL.setVisible(true);
            btnTangCa.setVisible(true);
            btnKT.setVisible(true);    
        }
    }
    public void fillDataTABLE() {
        model.setRowCount(0); 
        for (User sv : dao.getallNhanViens()) {
            Object rowData[] = new Object[6];
            rowData[0] = sv.getMaNV();
            rowData[1] = sv.getTenNV();
            if (sv.getRole() == 1) {
                rowData[2] = "Giám đốc";
            } else if (sv.getRole() == 2) {
                rowData[2] = "Quản lý";
            } else if(sv.getRole() == 0){
                rowData[2] = "Nhân viên";
            }else if(sv.getRole() == 3){
                rowData[2] = "Thư Ký";
            }else if(sv.getRole() == 4){
                rowData[2] = "Đội trưởng đội cổ vũ";
            }
            rowData[3] = sv.getBanNganh();
            rowData[4] = sv.getHinhAnh();
            if (sv.getTrangThai() == 0) {
                rowData[5] = "Đang Hoạt Động";
            } else if (sv.getTrangThai() == 1) {
                rowData[5] = "Ko Hoạt Động";
            }
            model.addRow(rowData);
        }
    }
    
//    public static void CongVao() {
//        ChamCong cc = new ChamCong();
//        LocalDateTime recordedTime = LocalDateTime.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy ");
//        String formattedTime = recordedTime.format(formatter);
//        LocalDateTime recordedTimes = LocalDateTime.now();
//        DateTimeFormatter formattere = DateTimeFormatter.ofPattern("HH:mm:ss ");
//        String formattedTimer = recordedTime.format(formatter);
//        // Hiển thị thời điểm đã định dạng lên giao diện người dùng
//        txtDate.setText(formattedTime);
//        txtDate1.setText(formattedTimer);
//        // Lưu giá trị vào đối tượng ChamCong
//        cc.setGioRa(formattedTimer);
//        cc.setNgayNhan(formattedTime);
//        // Hiển thị giá trị đã định dạng trên console (để kiểm tra)
//        System.out.println("GioRa: " + cc.getGioRa());
//        System.out.println("NgayNhan: " + cc.getNgayNhan());
//    }
   
    void startNgayGio(){
        
        SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyy ___|___ [HH:mm:ss]");
        new Timer(1000, (ActionEvent e) -> {
            lblDongHo.setText(formater.format(new Date()));
            
        }).start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel7 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtDate = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        lblDongHo = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        btnDeadline = new javax.swing.JButton();
        btnTangCa = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        btnQL = new javax.swing.JButton();
        btnBangLuong = new javax.swing.JButton();
        btnKT = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbNhanVien = new javax.swing.JTable();
        btnChamCong = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        lblAvatar = new javax.swing.JLabel();
        lbl1 = new javax.swing.JLabel();
        lblMaNV = new javax.swing.JLabel();
        lblTK = new javax.swing.JLabel();
        lblTenNV = new javax.swing.JLabel();
        lbl3 = new javax.swing.JLabel();
        lblRole = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        MenuHeThong = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        MenuTinhnang = new javax.swing.JMenu();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("WindowApps");

        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Segoe UI", 3, 26)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Hệ Thống Quản Lý Nhân Viên");
        jPanel7.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 10, 400, -1));
        jPanel7.add(txtDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 210, -1));

        lblDongHo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblDongHo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDongHo.setText(" 11/11/2022 ___|___ [00:00:00 PM]");
        lblDongHo.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                lblDongHoAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(lblDongHo, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblDongHo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 260, 30));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(51, 153, 255));

        btnDeadline.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Properties.png"))); // NOI18N
        btnDeadline.setText("Deadline");
        btnDeadline.setBorder(new javax.swing.border.MatteBorder(null));
        btnDeadline.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDeadline.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDeadline.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeadlineActionPerformed(evt);
            }
        });

        btnTangCa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Numbered list.png"))); // NOI18N
        btnTangCa.setText("Tăng ca");
        btnTangCa.setBorder(new javax.swing.border.MatteBorder(null));
        btnTangCa.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTangCa.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTangCa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTangCaActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Log out.png"))); // NOI18N
        jButton1.setText("Đăng xuất");
        jButton1.setBorder(new javax.swing.border.MatteBorder(null));
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Stop.png"))); // NOI18N
        jButton2.setText("Thoát");
        jButton2.setBorder(new javax.swing.border.MatteBorder(null));
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jPanel9.setBackground(new java.awt.Color(51, 204, 255));
        jPanel9.setBorder(new javax.swing.border.MatteBorder(null));

        btnQL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Address book.png"))); // NOI18N
        btnQL.setText("Quản Lý");
        btnQL.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(255, 255, 0)));
        btnQL.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnQL.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnQL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLActionPerformed(evt);
            }
        });

        btnBangLuong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Users.png"))); // NOI18N
        btnBangLuong.setText("Bảng Lương ");
        btnBangLuong.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(255, 255, 102)));
        btnBangLuong.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnBangLuong.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnBangLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBangLuongActionPerformed(evt);
            }
        });

        btnKT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Help.png"))); // NOI18N
        btnKT.setText("Bảng Kế Toán");
        btnKT.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(255, 255, 102)));
        btnKT.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnKT.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnKT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(109, Short.MAX_VALUE)
                .addComponent(btnKT, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnBangLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnQL, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnKT, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnQL, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBangLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnTangCa, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDeadline, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 2, Short.MAX_VALUE)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnTangCa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDeadline, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel10.setLayout(new java.awt.CardLayout());

        tbNhanVien.setBackground(new java.awt.Color(204, 255, 255));
        tbNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID NV", "Tên NV	", "Quyền Hạn", "Ban Ngành", "Hình ảnh", "Trạng Thái"
            }
        ));
        tbNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbNhanVienMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbNhanVien);

        jPanel10.add(jScrollPane1, "card2");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 918, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel7.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 920, 550));

        btnChamCong.setText("Bắt Đầu Ca");
        btnChamCong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChamCongActionPerformed(evt);
            }
        });
        jPanel7.add(btnChamCong, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 70, 190, 30));

        jPanel1.setBackground(new java.awt.Color(204, 255, 204));
        jPanel1.setForeground(new java.awt.Color(204, 255, 204));

        lblAvatar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAvatar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 0, 0), 3, true));

        lbl1.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        lbl1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl1.setText("Mã Tài Khoản:");

        lblMaNV.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblMaNV.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblMaNV.setText("........................................................................");

        lblTK.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        lblTK.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTK.setText("Tên Tài Khoản:");

        lblTenNV.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblTenNV.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTenNV.setText("......................................................................................................");

        lbl3.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        lbl3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl3.setText("Chức Vụ:");

        lblRole.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblRole.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblRole.setText("......................................................................................................");

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Key.png"))); // NOI18N
        jButton5.setText("Đổi Mật Khẩu");
        jButton5.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(51, 153, 255)));
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(255, 255, 153));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/China_dragon - Copy.png"))); // NOI18N
        jPanel5.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 290, 160));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 3, 26)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Account");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl3, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblRole, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTK, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(lblTenNV, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblMaNV)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTK)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTenNV)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblRole)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel7.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 10, 300, 650));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Backgrounds.png"))); // NOI18N
        jPanel7.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1260, 710));

        jMenuBar1.setBackground(new java.awt.Color(255, 255, 102));
        jMenuBar1.setBorder(null);
        jMenuBar1.setName(""); // NOI18N

        MenuHeThong.setText("Hệ thống");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_DOWN_MASK));
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Exit.png"))); // NOI18N
        jMenuItem1.setText("Đăng xuất");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        MenuHeThong.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Stop.png"))); // NOI18N
        jMenuItem2.setText("Thoát");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        MenuHeThong.add(jMenuItem2);

        jMenuBar1.add(MenuHeThong);

        MenuTinhnang.setText("Tính năng");

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Clien list.png"))); // NOI18N
        jMenu1.setText("Giám Đốc");

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Address book.png"))); // NOI18N
        jMenuItem3.setText("Quản lý Nhân Viên");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Users.png"))); // NOI18N
        jMenuItem4.setText("Bảng Lương");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Scroll list.png"))); // NOI18N
        jMenuItem5.setText("Deadline");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem5);

        jMenuItem6.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_K, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Certificate.png"))); // NOI18N
        jMenuItem6.setText("Kế Toán");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem6);

        MenuTinhnang.add(jMenu1);

        jMenuBar1.add(MenuTinhnang);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 1260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 8, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        getAccessibleContext().setAccessibleName("JavaDuan1");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        LoginFrame main = new LoginFrame();
        main.setVisible(true);

        this.dispose();
        JOptionPane.showMessageDialog(this, "Đã Đăng Xuất");
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        if(SessionHelper.getRole() == 0){
            System.out.println("nhan vien");
            JOptionPane.showMessageDialog(this, "Quyền hạn hiện tại: Nhân viên, Không đủ quyền hạn truy cập");
            this.dispose();
            return;
        }else if(SessionHelper.getRole() == 2){

            System.out.println("quanly");
            JOptionPane.showMessageDialog(this, "Quyền hạn hiện tại: Quản lý");
            NhanVienMainFrame main = new NhanVienMainFrame();
            main.setVisible(true);
            this.dispose();
        }else if(SessionHelper.getRole() == 1){

            System.out.println("Giám Đốc");
            JOptionPane.showMessageDialog(this, "Quyền hạn hiện tại: Giám Đốc");
            NhanVienMainFrame main = new NhanVienMainFrame();
            main.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        if(SessionHelper.getRole() == 0){
            System.out.println("nhan vien");
            JOptionPane.showMessageDialog(this, "Quyền hạn hiện tại: Nhân viên, Không đủ quyền hạn truy cập");
            this.dispose();
            return;
        }else if(SessionHelper.getRole() == 2){
            System.out.println("quanly");
            JOptionPane.showMessageDialog(this, "Quyền hạn hiện tại: Quản Lý, Không đủ quyền hạn truy cập");
            this.dispose();
        }else if(SessionHelper.getRole() == 1){
            System.out.println("Giám Đốc");

            this.dispose();
        }
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        DeadlineFrame main = new DeadlineFrame();
        main.setVisible(true);
        main.dispose();
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void lblDongHoAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_lblDongHoAncestorAdded
        startNgayGio();
    }//GEN-LAST:event_lblDongHoAncestorAdded
    
    private void btnDeadlineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeadlineActionPerformed
        DeadlineFrame main = new DeadlineFrame();
        main.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnDeadlineActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        LoginFrame main = new LoginFrame();
        main.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnBangLuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBangLuongActionPerformed

        if(SessionHelper.getRole() == 0){
            System.out.println("nhan vien");
        }else if(SessionHelper.getRole() == 2){

            System.out.println("quanly");
            JOptionPane.showMessageDialog(this, "Quyền hạn hiện tại: Quản lý");

            KeToanFrame main = new KeToanFrame();
            main.setVisible(true);
            this.dispose();
        }else if(SessionHelper.getRole() == 1){

            System.out.println("Giám Đốc");
            JOptionPane.showMessageDialog(this, "Quyền hạn hiện tại: Giám Đốc");
            KeToanFrame main = new KeToanFrame();
            main.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_btnBangLuongActionPerformed
      JPanel mainPanel = new JPanel(new CardLayout());
    private void btnQLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQLActionPerformed

        if(SessionHelper.getRole() == 0){
            System.out.println("nhan vien");
            JOptionPane.showMessageDialog(this, "Chức vụ: Nhân Viên, Chưa đủ quyền hạn truy cập");
        }else if(SessionHelper.getRole() == 2){

            System.out.println("quanly");
            JOptionPane.showMessageDialog(this, "Chức vụ hiện tại: Quản lý");
            NhanVienMainFrame main = new NhanVienMainFrame();
            main.setVisible(true);
            
        }else if(SessionHelper.getRole() == 1){
            
            System.out.println("Giám Đốc");
            JOptionPane.showMessageDialog(this, "Chức vụ hiện tại: Giám Đốc");
            NhanVienMainFrame main = new NhanVienMainFrame();
            main.setVisible(true);
            return ;
        }
    }//GEN-LAST:event_btnQLActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnKTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKTActionPerformed
        KeToanFrame kt = new KeToanFrame();
        kt.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnKTActionPerformed

    private void btnTangCaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTangCaActionPerformed
        // TODO add your handling code here:
        DangKyTangCaFrame tangca = new DangKyTangCaFrame();
        tangca.setVisible(true);
    }//GEN-LAST:event_btnTangCaActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // TODO add your handling code here:
        KeToanFrame kt = new KeToanFrame();
        kt.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jMenuItem6ActionPerformed
   
    
    

    private void btnChamCongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChamCongActionPerformed
        // TODO add your handling code here:
        
        chamcongVao();
    }//GEN-LAST:event_btnChamCongActionPerformed

    private void tbNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbNhanVienMouseClicked
        // TODO add your handling code here:
        try {
            int id = tbNhanVien.rowAtPoint(evt.getPoint());
            String masv = tbNhanVien.getValueAt(id, 0).toString();
            User sv = dao.tabblebyIDNhanVien(masv);

        } catch (Exception e) {

        }
    }//GEN-LAST:event_tbNhanVienMouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        ChangePassDialog cg = new ChangePassDialog(this, true);
        cg.setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed
      public ChamCong getModels() throws ParseException{
          ChamCong us= new ChamCong();  
        LocalDateTime recordedTime = LocalDateTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter anotherTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        String ngayNhan = recordedTime.format(dateFormatter);
        String gioVao = recordedTime.format(timeFormatter);
//        String gioRa = recordedTime.format( anotherTimeFormatter);
        // Kiểm tra giá trị trước khi gán
        System.out.println("ngayNhan: " + ngayNhan);
        System.out.println("gioNhan: " + gioVao);
//        System.out.println("GioRa: '" + gioRa+"'");      
        String date = ngayNhan;
        String hour = gioVao;
        System.out.println("ID: "+  us.getNgayNhan());
        SessionHelper.setNgayNhan(date);
        
   
              
          us.setNgayNhan(ngayNhan);
          System.out.println(us.getNgayNhan());
          us.setGioVao(gioVao);
          System.out.println(us.getGioVao());
        return us;        
    }
    private boolean isLate(ChamCong nv) {
        // Lấy thời gian làm muộn so với 8:00 AM
        LocalTime targetTime = LocalTime.parse("08:00");
        LocalTime gioVao = LocalTime.parse(nv.getGioVao(), DateTimeFormatter.ofPattern("HH:mm"));

        // So sánh thời gian và trả về kết quả
        return gioVao.isAfter(targetTime);
    }
    public void chamcongVao(){
         TangCaDao daoo = new TangCaDao();
        ChamCong cc= new ChamCong();
        try {
                ChamCong nv = getModels();
                if (daoo.ChamcongVao(nv) > 0) {
                        if (isLate(nv)) {
                            JOptionPane.showMessageDialog(this, "Lưu thành công! Đi làm muộn.");
                        } else {
                            JOptionPane.showMessageDialog(this, "Lưu thành công!");
                        }
                        
                }
            } catch (ParseException ex) {
                Logger.getLogger(NhanVienMainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        startNgayGio();
    }
    public boolean validateForm(){
        
        if (txtDate.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "?");
            return true;
            
        }
        return true;
    }   
//    public void chamCongRa(){
//        ChamCong cc= new ChamCong();
//        TangCaDao daoos = new TangCaDao();
//        try {
//                ChamCong nv = getModel();
//                System.out.println("1");
//                if (daoos.ChamcongRa(nv)>0){
//                    JOptionPane.showMessageDialog(this, "Cập nhật thành công");
//                    
//                }
//            } catch (ParseException ex) {
//                Logger.getLogger(NhanVienMainFrame.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }    
//    public void sang(){
//        ChamCong us= new ChamCong();
//        LocalTime currentTime = LocalTime.now();
//        LocalDateTime recordedTime = LocalDateTime.now();
//        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
////        DateTimeFormatter anotherTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
//        String ngayNhan = recordedTime.format(dateFormatter);
//        String gioVao = recordedTime.format(timeFormatter);
////        String gioRa = recordedTime.format( anotherTimeFormatter);
//        // Kiểm tra giá trị trước khi gán
//        System.out.println("ngayNhan: " + ngayNhan);
//        System.out.println("gioNhan: " + gioVao);
////        System.out.println("GioRa: '" + gioRa+"'");      
//        
//        SessionHelper.getNgayNhan(ngayNhan);
//        JOptionPane.showMessageDialog(this, "Vào: "+gioVao);
////        if (currentTime.isBefore(LocalTime.NOON)) {
////            txtDate1.setText(gioRa);
////            System.out.println("sáng");
////        } else {
////            txtDate1.setText(gioRa);
////            System.out.println("Chiều");
////        }
//    }
//    public void chieu(){
//       
//        
////        txtDate1.setText(gioVao);
////        if (currentTime.isBefore(LocalTime.NOON)) {
////            txtDate1.setText(gioRa);
////            System.out.println("sáng");
////        } else {
////            txtDate1.setText(gioRa);
////            System.out.println("Chiều");
////        }
//    }
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
            java.util.logging.Logger.getLogger(WindowAPP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WindowAPP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WindowAPP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WindowAPP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu MenuHeThong;
    private javax.swing.JMenu MenuTinhnang;
    private javax.swing.JButton btnBangLuong;
    private javax.swing.JButton btnChamCong;
    private javax.swing.JButton btnDeadline;
    private javax.swing.JButton btnKT;
    private javax.swing.JButton btnQL;
    private javax.swing.JButton btnTangCa;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl1;
    public static javax.swing.JLabel lbl3;
    private javax.swing.JLabel lblAvatar;
    public static javax.swing.JLabel lblDongHo;
    private javax.swing.JLabel lblMaNV;
    public static javax.swing.JLabel lblRole;
    private javax.swing.JLabel lblTK;
    private javax.swing.JLabel lblTenNV;
    private javax.swing.JTable tbNhanVien;
    public static javax.swing.JTextField txtDate;
    // End of variables declaration//GEN-END:variables
}
