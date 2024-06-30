/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package main;

import database.SessionHelper;
import java.awt.CardLayout;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Lich;
import model.LichDao;
import model.NhanVienDao;
import model.User;

/**
 *
 * @author qivub
 */
public class DeadlineFrame extends javax.swing.JFrame {
    LichDao dao = new LichDao();
    NhanVienDao nhanvien = new NhanVienDao();
//    List<User> ls = nhanvien.getAllNhanVien();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    DefaultTableModel model;
    User us = new User();
    /**
     * Creates new form LichDeadlineFrame
     */
    public DeadlineFrame() {
//        if (SessionHelper.getRole() == -1) {
//            System.out.println("sai vai trò");
//            JOptionPane.showMessageDialog(this, "sai vai trò");
//            return;
//        }else if(SessionHelper.getRole() == 0){
//            this.setVisible(true);
//            NV();
//        }else if(SessionHelper.getRole() == 2){
//            GdVSQl();
//            this.setVisible(true);
//        }else if(SessionHelper.getRole() == 1){
//            GdVSQl();
//            this.setVisible(true);
//        }
        initComponents();
        model = (DefaultTableModel) tbDl.getModel();
        fillDataTABLE();
        setanlydo();
        trong();
        
    }
    public void GdVSQl(){
        lblGiaoCV.setVisible(true);
    }
    public void NV(){
        lblGiaoCV.setVisible(false);
    }
    public void trong(){
        pnTrong.setVisible(true);
        pnGiaoCongViec.setVisible(false);
        pnNhanCongViec.setVisible(false);
    }
    public void fillDataTABLE() {
        model.setRowCount(0);
        List<Lich> lich = dao.getLichforUser();  
        for (Lich ls : lich) {
        
            Object rowData[] = new Object[8];
            rowData[0] = ls.getIDCV();
            rowData[1] = ls.getCongViec();
            rowData[2] = ls.getNoidung();
            rowData[3] = ls.getMaNV();
            rowData[4] = ls.getTenNV();          
            rowData[5] = ls.getDeadline();
            if (ls.getTinhTrang() == 0) {
                rowData[6] = "Đang Làm";
            } else if (ls.getTinhTrang() == 1) {
                rowData[6] = "Xong";
            } else if(ls.getTinhTrang() == 2){
                rowData[6] = "Chưa Hoàn Thành";
            }
            rowData[7] = ls.getLydoCham();
            model.addRow(rowData);
        }   
    }
    public void setModel(Lich lich){
        int row = tbDl.getSelectedRow();
        txtIDNhanCV.setText(String.valueOf( tbDl.getValueAt(row, 0)));
        txtNhanCongViec.setText((String) tbDl.getValueAt(row,1));
        txtNhanND.setText((String) tbDl.getValueAt(row, 2));
        txtMaNVNhan.setText((String) tbDl.getValueAt(row, 3));
        txtTenNVNhan.setText((String) tbDl.getValueAt(row, 4));
        Object value = tbDl.getValueAt(row, 5);
        if (value != null) {
            if (value instanceof java.sql.Date) {
                java.sql.Date deadlineDate = (java.sql.Date) value;
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String deadlineText = dateFormat.format(deadlineDate);
                txtNhanDeadline.setText(deadlineText);
            } 
        }
//        int tinhtrang = 0 ;
//            lich.setTinhTrang(tinhtrang);
//                if(tinhtrang == 0 ){
//                    rdDanglam.setSelected(true);
//                }else if(tinhtrang == 2){
//                    rdChuahoanthanh.setSelected(true);
//                }else if(tinhtrang == 1){
//                    rdXong.setSelected(true);
//                }
         Object rawValue = (String) tbDl.getValueAt(row, 6);
            if (rawValue instanceof Integer) {
            int tinhtrang = (int) rawValue;
            lich.setTinhTrang(tinhtrang);
                if(tinhtrang == 0 ){
                    rdDanglam.setSelected(true);
                }else if(tinhtrang == 2){
                    rdChuahoanthanh.setSelected(true);
                }else if(tinhtrang == 1){
                    rdXong.setSelected(true);
                }
            }   
    }
    
    public static void setlydo(){
        txtLydo.setVisible(true);
        lbllydo.setVisible(true);
    }
    public static void setanlydo(){
        txtLydo.setVisible(false);
        lbllydo.setVisible(false);
    }
    public boolean validateForm(){
         if (txtND.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nội dung Chưa Nhập");
            return false;
        }else if (txtDeadline.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Chưa có hạn deadline");
            return false;
        }
        return true;
    }    
    public boolean validateForms(){
        
        return true;
    }    
        public Lich getModels() throws ParseException {
            User us = new User();
            Lich lich = new Lich();
            lich.setIDCV(Integer.parseInt(txtIDCV.getText()));
            lich.setCongViec(txtCongViec.getText());
            lich.setTenNV(txtTenNV.getText());
            lich.setMaNV(txtMaNV.getText());
            lich.setNoidung(txtND.getText());
            String deadlineText = txtDeadline.getText();
            try {
                Date deadline = dateFormat.parse(deadlineText);
                if (deadline != null) {
                    lich.setDeadline(deadline);
                } else {
                    JOptionPane.showMessageDialog(this, "Deadline chỉ sử dụng được ngày tháng");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return lich;
        }
        public Lich getModelLuu() throws ParseException {
            User us = new User();
            Lich lich = new Lich();
//            lich.setIDCV(Integer.parseInt(txtIDCV.getText()));
            lich.setCongViec(txtCongViec.getText());
            lich.setTenNV(txtTenNV.getText());
            lich.setMaNV(txtMaNV.getText());
            lich.setNoidung(txtND.getText());
            String deadlineText = txtDeadline.getText();
            try {
                Date deadline = dateFormat.parse(deadlineText);
                if (deadline != null) {
                    lich.setDeadline(deadline);
                } else {
                    JOptionPane.showMessageDialog(this, "Deadline chỉ sử dụng được ngày tháng");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return lich;
        }
        public Lich getModel() throws ParseException {
            User us = new User();
            Lich lich = new Lich();
            lich.setIDCV(Integer.parseInt(txtIDCV.getText()));
            lich.setCongViec(txtNhanCongViec.getText());
            lich.setTenNV(txtTenNVNhan.getText());
            lich.setMaNV(txtMaNVNhan.getText());
            lich.setNoidung(txtNhanND.getText());
            String deadlineText = txtNhanDeadline.getText();
            
            try {
                Date deadline = dateFormat.parse(deadlineText);
                if (deadline != null) {
                    lich.setDeadline(deadline);
                } else {
                    System.err.println("Lỗi: Ngày hết hạn không hợp lệ");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
                if (rdDanglam.isSelected()) {
                    lich.setTinhTrang(0);
                } else if(rdXong.isSelected()){
                    lich.setTinhTrang(1);
                }else if(rdChuahoanthanh.isSelected()) {
                    lich.setTinhTrang(2);
                }
            lich.setLydoCham(txtLydo.getText());
            return lich;
        }
        public Lich getModelNhan() throws ParseException {
            User us = new User();
            Lich lich = new Lich();
            lich.setIDCV(Integer.parseInt(txtIDNhanCV.getText()));
            lich.setCongViec(txtNhanCongViec.getText());
            lich.setTenNV(txtTenNVNhan.getText());
            lich.setMaNV(txtMaNVNhan.getText());
            lich.setNoidung(txtNhanND.getText());
            String deadlineText = txtNhanDeadline.getText();
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date deadline = dateFormat.parse(deadlineText);
                if (deadline != null) {
                    lich.setDeadline(deadline);
                } else {
                    System.err.println("Lỗi: Ngày hết hạn không hợp lệ");
                }
            } catch (ParseException e) {
                System.err.println("Lỗi: Ngày hết hạn không hợp lệ");
                e.printStackTrace();
            }
            if (rdDanglam.isSelected()) {
                lich.setTinhTrang(0);
            } else if (rdXong.isSelected()) {
                lich.setTinhTrang(1);
            } else if (rdChuahoanthanh.isSelected()) {
                lich.setTinhTrang(2);
            } else {
                System.err.println("Lỗi: Chưa chọn tình trạng");
            }
            lich.setLydoCham(txtLydo.getText());
            return lich;
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
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        pnTrong = new javax.swing.JPanel();
        pnNhanCongViec = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        btnThem1 = new javax.swing.JButton();
        btnSua1 = new javax.swing.JButton();
        btnMain1 = new javax.swing.JButton();
        rdDanglam = new javax.swing.JRadioButton();
        rdXong = new javax.swing.JRadioButton();
        rdChuahoanthanh = new javax.swing.JRadioButton();
        txtLydo = new javax.swing.JTextField();
        lbllydo = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbDl = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        txtSearchDL = new javax.swing.JTextField();
        btnTimkiemCv = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtNhanDeadline = new javax.swing.JTextField();
        txtTenNVNhan = new javax.swing.JTextField();
        txtMaNVNhan = new javax.swing.JTextField();
        txtIDNhanCV = new javax.swing.JTextField();
        txtNhanCongViec = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtNhanND = new javax.swing.JTextArea();
        pnGiaoCongViec = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtIDCV = new javax.swing.JTextField();
        btnLuu = new javax.swing.JButton();
        txtCongViec = new javax.swing.JTextField();
        btnSua = new javax.swing.JButton();
        txtDeadline = new javax.swing.JTextField();
        btnClear = new javax.swing.JButton();
        txtMaNV = new javax.swing.JTextField();
        btnXoa = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        btnHome = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtND = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        txtFindMaNV = new javax.swing.JTextField();
        btnSearchMaNv = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtTenNV = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        txtSearchDLGiao = new javax.swing.JTextField();
        btnSearchCV = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        lblNhanCv = new javax.swing.JLabel();
        lblGiaoCV = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("DeadLine");

        jPanel1.setBackground(new java.awt.Color(102, 204, 255));

        jPanel4.setBorder(new javax.swing.border.MatteBorder(null));

        pnTrong.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout pnTrongLayout = new javax.swing.GroupLayout(pnTrong);
        pnTrong.setLayout(pnTrongLayout);
        pnTrongLayout.setHorizontalGroup(
            pnTrongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 957, Short.MAX_VALUE)
        );
        pnTrongLayout.setVerticalGroup(
            pnTrongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 988, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(255, 102, 102));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Công Việc");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Deadline");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("IDCV");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Nội dung");

        btnThem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Add.png"))); // NOI18N
        btnThem1.setText("Clear");
        btnThem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThem1ActionPerformed(evt);
            }
        });

        btnSua1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Notes.png"))); // NOI18N
        btnSua1.setText("Cập Nhật");
        btnSua1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSua1ActionPerformed(evt);
            }
        });

        btnMain1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Home.png"))); // NOI18N
        btnMain1.setText("Home");
        btnMain1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMain1ActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdDanglam);
        rdDanglam.setForeground(new java.awt.Color(255, 255, 255));
        rdDanglam.setText("Đang làm");
        rdDanglam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdDanglamActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdXong);
        rdXong.setForeground(new java.awt.Color(255, 255, 255));
        rdXong.setText("Đã Hoàn Thành");
        rdXong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdXongActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdChuahoanthanh);
        rdChuahoanthanh.setForeground(new java.awt.Color(255, 255, 255));
        rdChuahoanthanh.setText("Chưa Hoàn Thành");
        rdChuahoanthanh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdChuahoanthanhActionPerformed(evt);
            }
        });

        txtLydo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lbllydo.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lbllydo.setForeground(new java.awt.Color(255, 255, 255));
        lbllydo.setText("Lý do:");

        tbDl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID Việc", "Tên Việc", "Nội dung", "Mã NV", "Tên Nhân Viên", "Deadline", "Tình Trạng ", "Lý Do Chậm"
            }
        ));
        tbDl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDlMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbDl);

        jPanel8.setBackground(new java.awt.Color(255, 204, 204));
        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel16.setText("Tìm kiếm");

        btnTimkiemCv.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        btnTimkiemCv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Search.png"))); // NOI18N
        btnTimkiemCv.setText("Tìm kiếm");
        btnTimkiemCv.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnTimkiemCv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimkiemCvbtnTimKiemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtSearchDL, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnTimkiemCv, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtSearchDL, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimkiemCv))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jLabel17.setFont(new java.awt.Font("Segoe UI", 2, 24)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Hệ Thống Nhận Deadline");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Mã Nhân Viên");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Tên NV");

        txtNhanDeadline.setEditable(false);
        txtNhanDeadline.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtTenNVNhan.setEditable(false);
        txtTenNVNhan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtMaNVNhan.setEditable(false);
        txtMaNVNhan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtIDNhanCV.setEditable(false);
        txtIDNhanCV.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtNhanCongViec.setEditable(false);
        txtNhanCongViec.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtNhanND.setEditable(false);
        txtNhanND.setColumns(20);
        txtNhanND.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtNhanND.setRows(5);
        jScrollPane3.setViewportView(txtNhanND);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jScrollPane2))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(txtIDNhanCV, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(45, 45, 45)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                                            .addComponent(txtNhanCongViec)))
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txtTenNVNhan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
                                        .addComponent(txtNhanDeadline, javax.swing.GroupLayout.Alignment.LEADING))
                                    .addComponent(txtMaNVNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(49, 49, 49)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(rdChuahoanthanh, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(rdDanglam, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(15, 15, 15)
                                        .addComponent(rdXong, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 483, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(lbllydo, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtLydo, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(btnThem1)
                                        .addGap(33, 33, 33)
                                        .addComponent(btnMain1)
                                        .addGap(47, 47, 47)
                                        .addComponent(btnSua1, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(227, 227, 227)
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(0, 74, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel17)
                .addGap(18, 18, 18)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel8)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rdDanglam)
                            .addComponent(rdXong))
                        .addGap(18, 18, 18)
                        .addComponent(rdChuahoanthanh)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbllydo)
                            .addComponent(txtLydo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtIDNhanCV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNhanCongViec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel14)
                        .addGap(15, 15, 15)
                        .addComponent(txtMaNVNhan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtTenNVNhan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNhanDeadline, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnMain1)
                    .addComponent(btnThem1)
                    .addComponent(btnSua1))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 483, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );

        javax.swing.GroupLayout pnNhanCongViecLayout = new javax.swing.GroupLayout(pnNhanCongViec);
        pnNhanCongViec.setLayout(pnNhanCongViecLayout);
        pnNhanCongViecLayout.setHorizontalGroup(
            pnNhanCongViecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnNhanCongViecLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnNhanCongViecLayout.setVerticalGroup(
            pnNhanCongViecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel7.setBackground(new java.awt.Color(255, 102, 102));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Deadline(Định dạng nhập: yyyy-MM-dd)");

        txtIDCV.setEditable(false);
        txtIDCV.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        btnLuu.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        btnLuu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Save.png"))); // NOI18N
        btnLuu.setText("Lưu");
        btnLuu.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnLuu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuActionPerformed(evt);
            }
        });

        txtCongViec.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        btnSua.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Notes.png"))); // NOI18N
        btnSua.setText("Sửa");
        btnSua.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        txtDeadline.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        btnClear.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        btnClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Bomb.png"))); // NOI18N
        btnClear.setText("Làm trắng");
        btnClear.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        txtMaNV.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        btnXoa.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Delete.png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Nội dung");

        btnHome.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        btnHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Exit.png"))); // NOI18N
        btnHome.setText("Trang Chủ");
        btnHome.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHomeActionPerformed(evt);
            }
        });

        txtND.setColumns(20);
        txtND.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtND.setRows(5);
        jScrollPane1.setViewportView(txtND);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Mã Nhân Viên");

        jPanel5.setBackground(new java.awt.Color(255, 204, 204));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel15.setText("Tìm kiếm");

        txtFindMaNV.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        btnSearchMaNv.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        btnSearchMaNv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Search.png"))); // NOI18N
        btnSearchMaNv.setText("Tìm kiếm Mã Nhân Viên");
        btnSearchMaNv.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnSearchMaNv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtFindMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSearchMaNv)
                .addGap(41, 41, 41))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtFindMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearchMaNv))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Tên NV");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("IDCV");

        txtTenNV.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Công Việc");

        jLabel23.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel23.setText("Tìm kiếm");

        btnSearchCV.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        btnSearchCV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Search.png"))); // NOI18N
        btnSearchCV.setText("Tìm kiếm Công Việc");
        btnSearchCV.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnSearchCV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchCVbtnTimKiemActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Segoe UI", 2, 24)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Hệ Thống Giao Deadline Deadline");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(59, 59, 59))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtIDCV, javax.swing.GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE)
                            .addComponent(txtMaNV)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel7)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel7Layout.createSequentialGroup()
                                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtSearchDLGiao, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(txtCongViec)
                                .addComponent(txtDeadline)
                                .addComponent(txtTenNV, javax.swing.GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(72, 72, 72)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                    .addComponent(btnHome, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(156, 156, 156))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(jPanel7Layout.createSequentialGroup()
                                            .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel7Layout.createSequentialGroup()
                                            .addComponent(btnLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(59, 59, 59)))
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSearchCV, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(304, 304, 304)
                .addComponent(jLabel19)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel19)
                .addGap(44, 44, 44)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(txtSearchDLGiao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearchCV, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(txtIDCV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6)
                        .addGap(11, 11, 11)
                        .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(txtTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(16, 16, 16)
                                .addComponent(jLabel7))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(8, 8, 8)
                        .addComponent(txtCongViec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(txtDeadline, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(94, 94, 94)
                        .addComponent(btnHome, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(484, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnGiaoCongViecLayout = new javax.swing.GroupLayout(pnGiaoCongViec);
        pnGiaoCongViec.setLayout(pnGiaoCongViecLayout);
        pnGiaoCongViecLayout.setHorizontalGroup(
            pnGiaoCongViecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnGiaoCongViecLayout.setVerticalGroup(
            pnGiaoCongViecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnGiaoCongViecLayout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 969, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(pnNhanCongViec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(pnGiaoCongViec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(pnTrong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1092, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(pnNhanCongViec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(pnGiaoCongViec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(pnTrong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        lblNhanCv.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNhanCv.setText("Giao Công Việc");
        lblNhanCv.setBorder(new javax.swing.border.MatteBorder(null));
        lblNhanCv.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblNhanCvMouseClicked(evt);
            }
        });

        lblGiaoCV.setBackground(new java.awt.Color(255, 255, 204));
        lblGiaoCV.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblGiaoCV.setText("Nhận Công Việc");
        lblGiaoCV.setBorder(new javax.swing.border.MatteBorder(null));
        lblGiaoCV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblGiaoCVMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblNhanCv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblGiaoCV, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(lblGiaoCV, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(lblNhanCv, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    public void reset(){
            txtCongViec.setText("");
            txtIDCV.setText("");
            txtND.setText("");
            txtMaNV.setText("");
            txtTenNV.setText("");
            txtDeadline.setText("");        
        }
    private void btnThem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThem1ActionPerformed
        // TODO add your handling code here:
        txtIDNhanCV.setText("");
        txtMaNVNhan.setText("");
        txtSearchDLGiao.setText("");
        txtTenNVNhan.setText("");
        txtNhanDeadline.setText("");
        txtNhanCongViec.setText("");
        txtNhanND.setText("");
        rdChuahoanthanh.setSelected(true);
    }//GEN-LAST:event_btnThem1ActionPerformed

    private void btnSua1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSua1ActionPerformed
        if (validateForms()) {
            try {
                Lich lich = getModelNhan();
                if (dao.update(lich)>0){
                    JOptionPane.showMessageDialog(this, "Cập nhật thành công");
                    fillDataTABLE();
                }
            } catch (ParseException ex) {

            }
        }else{
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập thông tin ");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSua1ActionPerformed

    private void btnMain1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMain1ActionPerformed
        // TODO add your handling code here:
        new WindowAPP().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnMain1ActionPerformed

    private void rdDanglamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdDanglamActionPerformed
        setanlydo();
    }//GEN-LAST:event_rdDanglamActionPerformed

    private void rdXongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdXongActionPerformed
        setanlydo();
    }//GEN-LAST:event_rdXongActionPerformed

    private void rdChuahoanthanhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdChuahoanthanhActionPerformed
        setlydo();

    }//GEN-LAST:event_rdChuahoanthanhActionPerformed

    private void tbDlMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDlMouseClicked
        // TODO add your handling code here:
        int id = tbDl.rowAtPoint(evt.getPoint());
        String manv = tbDl.getValueAt(id, 0).toString();
        Lich ls = dao.getDLnByID(manv);
        setModel(ls);
    }//GEN-LAST:event_tbDlMouseClicked

    private void lblNhanCvMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNhanCvMouseClicked
        // TODO add your handling code here:
          pnGiaoCongViec.setVisible(true);
          pnNhanCongViec.setVisible(false);
          pnTrong.setVisible(false);
    }//GEN-LAST:event_lblNhanCvMouseClicked

    private void lblGiaoCVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblGiaoCVMouseClicked
        // TODO add your handling code here:
          pnGiaoCongViec.setVisible(false);
          pnNhanCongViec.setVisible(true);
          pnTrong.setVisible(false);
    }//GEN-LAST:event_lblGiaoCVMouseClicked

    private void btnLuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuActionPerformed
        // TODO add your handling code here:
        if (validateForm()) {
            try {
                Lich lich= getModelLuu();
                if (dao.add(lich)>0) {
                    JOptionPane.showMessageDialog(this, "Lưu thành công! ");
                    fillDataTABLE();
                }
            } catch (ParseException ex) {

            }
        }else{

            JOptionPane.showMessageDialog(this, "Chưa nhập đủ! ");
        }
    }//GEN-LAST:event_btnLuuActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        if (txtFindMaNV.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập Mã Nhân Viên");
        }else{
            User nv = dao.getNhanVienByID(txtFindMaNV.getText());
            Lich lich = new Lich();
            txtMaNV.setText(nv.getMaNV());
            txtTenNV.setText(nv.getTenNV());
            
        }
        
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        reset();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        if (validateForm()) {
            try {
                Lich lich = getModels();
                if (dao.update(lich)>0){
                    JOptionPane.showMessageDialog(this, "Cập nhật thành công");
                    fillDataTABLE();
                }
            } catch (ParseException ex) {

            }
        }else{
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập thông tin ");
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        if (txtIDCV.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this,"Bạn chưa nhập IDCV để xóa");

        }else{
            if (dao.del(txtIDCV.getText())>0){
                JOptionPane.showMessageDialog(this,"Xóa thành công");
                fillDataTABLE();
            }else{
                JOptionPane.showMessageDialog(this,"Không tìm thấy idCV để xóa");
            }
        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHomeActionPerformed
        // TODO add your handling code here:
        WindowAPP main = new WindowAPP();
        main.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnHomeActionPerformed

    private void btnTimkiemCvbtnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimkiemCvbtnTimKiemActionPerformed
        if (txtSearchDL.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập Mã Công Việc");
        }else{
            Lich lich = dao.getDLByIdCv(txtSearchDL.getText());
            txtIDNhanCV.setText(String.valueOf(lich.getIDCV()));
            txtMaNVNhan.setText(lich.getMaNV());
            txtTenNVNhan.setText(lich.getTenNV());
            txtNhanCongViec.setText(lich.getCongViec());
            Date deadline = lich.getDeadline();
            String deadlineAsString = deadline.toString();
            txtNhanDeadline.setText(deadlineAsString);
            txtNhanND.setText(lich.getNoidung());
            
            txtLydo.setText(lich.getLydoCham());
        }
    }//GEN-LAST:event_btnTimkiemCvbtnTimKiemActionPerformed

    private void btnSearchCVbtnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchCVbtnTimKiemActionPerformed
        if (txtSearchDLGiao.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập Mã Công Việc");
        }else{
            Lich lich = dao.getDLByIdCv(txtSearchDLGiao.getText());
            txtIDCV.setText(String.valueOf(lich.getIDCV()));
            txtMaNV.setText(lich.getMaNV());
            txtTenNV.setText(lich.getTenNV());
            txtCongViec.setText(lich.getCongViec());
            Date deadline = lich.getDeadline();
            String deadlineAsString = deadline.toString();
            txtDeadline.setText(deadlineAsString);
            txtND.setText(lich.getNoidung());
        }
    }//GEN-LAST:event_btnSearchCVbtnTimKiemActionPerformed
    
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
            java.util.logging.Logger.getLogger(DeadlineFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DeadlineFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DeadlineFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DeadlineFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DeadlineFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnHome;
    private javax.swing.JButton btnLuu;
    private javax.swing.JButton btnMain1;
    private javax.swing.JButton btnSearchCV;
    private javax.swing.JButton btnSearchMaNv;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnSua1;
    private javax.swing.JButton btnThem1;
    private javax.swing.JButton btnTimkiemCv;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblGiaoCV;
    private javax.swing.JLabel lblNhanCv;
    public static javax.swing.JLabel lbllydo;
    public static javax.swing.JPanel pnGiaoCongViec;
    public static javax.swing.JPanel pnNhanCongViec;
    private javax.swing.JPanel pnTrong;
    private javax.swing.JRadioButton rdChuahoanthanh;
    private javax.swing.JRadioButton rdDanglam;
    private javax.swing.JRadioButton rdXong;
    public static javax.swing.JTable tbDl;
    private javax.swing.JTextField txtCongViec;
    private javax.swing.JTextField txtDeadline;
    private javax.swing.JTextField txtFindMaNV;
    private javax.swing.JTextField txtIDCV;
    private javax.swing.JTextField txtIDNhanCV;
    public static javax.swing.JTextField txtLydo;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtMaNVNhan;
    private javax.swing.JTextArea txtND;
    private javax.swing.JTextField txtNhanCongViec;
    private javax.swing.JTextField txtNhanDeadline;
    private javax.swing.JTextArea txtNhanND;
    private javax.swing.JTextField txtSearchDL;
    private javax.swing.JTextField txtSearchDLGiao;
    private javax.swing.JTextField txtTenNV;
    private javax.swing.JTextField txtTenNVNhan;
    // End of variables declaration//GEN-END:variables
}
