package com.vitalii.s.a5mynotes;

import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by user on 10.09.2016.
 */
public class EncrypterTest {

   // List<String> list = new ArrayList<>();
    List<byte[]> bytesList = new ArrayList<>();
    String fileName = "d:/encryptiontest.txt";

//    @Before
//    public void prepare() {
//        list.add("Первая строка для шифровкаи");
//        list.add("second line for encryption");
//        list.add("third line for encryption");
//        list.add("fifth line for encryption");
//    }

    @Before
    public void prepare() {
        bytesList.add("Первая строка для шифровкаи@".getBytes());
        bytesList.add("Вторая строка для шифровкаи@".getBytes());
        bytesList.add("Третья строка для шифровкаи@".getBytes());
    }

//    @Test
//    public void encrypt() throws Exception {
//        byte[] bytes = (Encrypter.encrypt(bytesList.get(0),"mynoteskey"));
//       // byte[] bytes2 = android.util.Base64.encode(bytes, Base64.DEFAULT);
//        String s1 = new String(bytes, "UTF_16");
//        //String s2 = Encrypter.decrypt(s1.getBytes(StandardCharsets.UTF_16),"mynoteskey");
//        System.out.println(new String(bytesList.get(0)));
//        System.out.println(new String(Encrypter.decrypt(bytes,"mynoteskey")));
//        assertEquals(new String(bytesList.get(0)),new String (Encrypter.decrypt(bytes,"mynoteskey")));
//    }



    @Test
    public void generalTest() throws Exception {
        Encrypter.writeEncryptedBytesToFile(bytesList,fileName);
        List<byte[]> resultBytesList = Encrypter.readDecryptedBytesList(fileName);
        List<String> stringList = new ArrayList<>();
        stringList = getStringListFromBytesList(resultBytesList);
        StringBuilder sb = new StringBuilder();
        for (String s: stringList) {
            sb.append(s);
            sb.append("\n");
        }

        List<String> listString = new ArrayList<>();
        //deleting @ symbol in the end of the string
        for (byte[] b:bytesList) {
            String s = new String(b);
            s=s.substring(0,(s.length()-1));
            System.out.println(s);
            listString.add(s);
        }
        System.out.println("******************");
        List<String> listResultString = new ArrayList<>();
        for (byte[] b: resultBytesList) {
            String s = new String(b,"UTF-8");
            //deleting spaces at the end
            int i = s.length()-1;
            while ((byte)s.charAt(i)==0) {
                i--;
            }
            s = s.substring(0,i+1);

            i=0;
            //deleting spaces at the beginning
            while ((byte)s.charAt(i)==12) {
                i++;
            }
            s=s.substring(i);
            System.out.println(s);
            listResultString.add(s);

        }
        String s1 = new String(bytesList.get(0));
        s1 = s1.substring(0,s1.length()-1);
        assertEquals(s1,listResultString.get(0));
        String s2 = new String(bytesList.get(1));
        s2 = s2.substring(0,s2.length()-1);
        assertEquals(s2,listResultString.get(1));
        String s3 = new String(bytesList.get(2));
        s3 = s3.substring(0,s3.length()-1);
        assertEquals(s3,listResultString.get(2));
//        System.out.println("SB=");
//        System.out.sprintln("SB=");
//        System.out.println(sb.toString());

    }

    public List<String> getStringListFromBytesList(List<byte[]> byteList) throws Exception {
        List<String> stringList = new ArrayList<>();


        for (byte[] b : byteList) {
            String s = new String(b, "UTF-8");
            //deleting symbol @
            s = s.substring(0, (s.length() - 1));
            //deleting spaces at the end
            int i = s.length() - 1;
            while ((byte) s.charAt(i) == 0) {
                i--;
            }
            s = s.substring(0, i + 1);

            i = 0;
            //deleting spaces at the beginning
            while ((byte) s.charAt(i) == 12) {
                i++;
            }
            s = s.substring(i);
            System.out.println(s);
            stringList.add(s);
        }
        return stringList;
    }


}