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
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import javax.crypto.Cipher;
/**
 *
 * @author VanAnh
 */
public class Decryption {
     private PrivateKey privateKey;
     Decryption(PrivateKey privateKey){
         this.privateKey=privateKey;
     }
    public void loadPrivateKey(String privateKeyFilePath) throws Exception {
        byte[] keyBytes = Files.readAllBytes(Paths.get(privateKeyFilePath));
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        this.privateKey = keyFactory.generatePrivate(spec);
    }

    public void decryptFile(File inputFile, File outputFile) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        try (FileInputStream fis = new FileInputStream(inputFile);
             FileOutputStream fos = new FileOutputStream(outputFile)) {
            byte[] buffer = new byte[256];
            int len;
            while ((len = fis.read(buffer)) > 0) {
                byte[] decrypted = cipher.doFinal(buffer, 0, len);
                fos.write(decrypted);
            }
        }
    }

    public void decryptDirectory(File directory) throws Exception {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".encrypted")) {
                    File decryptedFile = new File(file.getAbsolutePath().replace(".encrypted", ""));
                    decryptFile(file, decryptedFile);
                    file.delete();
                } else if (file.isDirectory()) {
                    decryptDirectory(file);
                }
            }
        }
    }
}
