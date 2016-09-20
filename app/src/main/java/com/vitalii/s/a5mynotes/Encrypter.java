package com.vitalii.s.a5mynotes;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by user on 07.09.2016.
 */
public class Encrypter {


    public static byte[] encrypt(byte[] bytes, String password) throws Exception {
        byte[] cipherText;
        byte[] pass = MessageDigest.getInstance("MD5").digest(password.getBytes());
        Key key = new SecretKeySpec(pass, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        cipherText = cipher.doFinal(bytes);
        return  cipherText;



    }

    public static byte[] decrypt(byte[] text, String password) throws Exception {
        byte[] decryptedText = null;
        byte[] pass = MessageDigest.getInstance("MD5").digest(password.getBytes());
        Key key = new SecretKeySpec(pass, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        decryptedText = cipher.doFinal(text);
        return decryptedText;
    }
    

    public static void writeEncryptedBytesToFile(List<byte[]> bytesArray, String fileName) throws Exception {
        FileOutputStream fos = new FileOutputStream(fileName);
        for (byte[] bytes: bytesArray) {
            fos.write(encrypt(bytes,"mynoteskey"));
        }
        fos.flush();
        fos.close();

    }

    public static byte[]  readDecryptedBytesFromFile(String fileName) throws Exception {
        FileInputStream fis = new FileInputStream(fileName);
        byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        return decrypt(buffer,"mynoteskey");
    }

    public static List<byte[]> readDecryptedBytesList(String fileName) throws Exception {
        byte[] bytes = readDecryptedBytesFromFile(fileName);
        List<byte[]> bytesList = new ArrayList<>();
        byte[] temp = new byte[2048];
        int count = -1;
        for (byte b: bytes) {
            if (b==64) {
                bytesList.add(temp);
                temp = new byte[2048];
                count = -1;
               continue;
            }
         count ++;
         temp[count] = b;


        }
        return bytesList;
    }




}


