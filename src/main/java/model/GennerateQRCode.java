/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.security.SecureRandom;
import org.apache.commons.codec.binary.Base32;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticator;

/**
 *
 * @author qivub
 */
public class GennerateQRCode {
    
    public static String generateSecretKey() {
        GoogleAuthenticator gAuth = new GoogleAuthenticator();
        GoogleAuthenticatorKey key = gAuth.createCredentials();
        String secretKey = key.getKey();
        return secretKey;
    }
    
    public static void createQRCode (String password, String accountName) {
        
        String otpAuthURL = String.format("otpauth://totp/%s?secret=%s", accountName, password);

        // Khởi tạo đối tượng QRCodeWriter từ ZXing
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        int width = 300;
        int height = 300;

        // Tạo BitMatrix từ thông tin tài khoản và secret key
        BitMatrix bitMatrix;
        try {
            bitMatrix = qrCodeWriter.encode(otpAuthURL, BarcodeFormat.QR_CODE, width, height);
        } catch (WriterException e) {
            e.printStackTrace();
            return;
        }

        // Lưu BitMatrix vào một file ảnh (ví dụ: qr_code.png)
        Path path = FileSystems.getDefault().getPath("QR_Code.png");
        try {
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
            System.out.println("QR Code generated successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
