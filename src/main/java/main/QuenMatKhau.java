/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package main;

import database.MsgBox;
import database.SessionHelper;
import model.NhanVienDao;
import model.User;

/**
 *
 * @author qivub
 */
public class QuenMatKhau extends javax.swing.JFrame {
    NhanVienDao dao= new  NhanVienDao();
    /**
     * Creates new form QuenMatKhau
     */
    public QuenMatKhau() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtPassNew = new javax.swing.JPasswordField();
        txtConfirm = new javax.swing.JPasswordField();
        lblXacNhanMatKhauMoi = new javax.swing.JLabel();
        lblMatKhauMoi = new javax.swing.JLabel();
        btnDongY = new javax.swing.JButton();
        lblDoiMatKhau = new javax.swing.JLabel();
        btnCancal = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblXacNhanMatKhauMoi.setText("Xác nhận mật khẩu mới");

        lblMatKhauMoi.setText("Mật khẩu mới");

        btnDongY.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Key.png"))); // NOI18N
        btnDongY.setText("Đồng ý");
        btnDongY.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDongYActionPerformed(evt);
            }
        });

        lblDoiMatKhau.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblDoiMatKhau.setForeground(new java.awt.Color(0, 102, 51));
        lblDoiMatKhau.setText("ĐỔI MẬT KHẨU");

        btnCancal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Exit.png"))); // NOI18N
        btnCancal.setText("Cancel");
        btnCancal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnDongY, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(36, 36, 36)
                                .addComponent(btnCancal, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblDoiMatKhau))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtConfirm, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPassNew, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblXacNhanMatKhauMoi)
                                    .addComponent(lblMatKhauMoi))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(46, 46, 46))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(lblDoiMatKhau)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(lblMatKhauMoi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtPassNew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(lblXacNhanMatKhauMoi)
                .addGap(18, 18, 18)
                .addComponent(txtConfirm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDongY)
                    .addComponent(btnCancal))
                .addGap(40, 40, 40))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDongYActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDongYActionPerformed
        this.ChangePass();
        new WindowAPP().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnDongYActionPerformed

    private void btnCancalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancalActionPerformed
        // TODO add your handling code here:
        new LoginFrame().setVisible(true);
    }//GEN-LAST:event_btnCancalActionPerformed
    private void ChangePass() {//Ảnh Hưởng LoginFrame
        User us = dao.getNhanVienByID(SessionHelper.getIdUser());//link đếb Userdao

        if (us == null) {
            MsgBox.alert(this, "Sai tên đăng nhập");//hiển thị thông báo
        } else {
            String mkMoi = new String(txtPassNew.getPassword());
            String xacNhanMkMoi = new String(txtConfirm.getPassword());
            
            if (!mkMoi.equals(xacNhanMkMoi)) {//lưu vào biến xacNhanMKMoi
                MsgBox.alert(this, "Xác nhận mật khẩu mới không đúng");
            } else {
                us.setPassword(mkMoi);//đổi mật khẩu user(ArrayList<User>)
                dao.updatePassWordByID(us);//Đổi Mật Khẩu trong cơ sở dữ liệu
                MsgBox.alert(this, "Đổi mật khẩu thành công");
            }
        }
    }
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
            java.util.logging.Logger.getLogger(QuenMatKhau.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuenMatKhau.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuenMatKhau.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuenMatKhau.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QuenMatKhau().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancal;
    private javax.swing.JButton btnDongY;
    private javax.swing.JLabel lblDoiMatKhau;
    private javax.swing.JLabel lblMatKhauMoi;
    private javax.swing.JLabel lblXacNhanMatKhauMoi;
    private javax.swing.JPasswordField txtConfirm;
    private javax.swing.JPasswordField txtPassNew;
    // End of variables declaration//GEN-END:variables
}
