package com.vitalii.s.a5mynotes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView mResultText;
    private List<byte[]> byteList = new ArrayList<>();
    private List<String> stringList = new ArrayList<>();
    private String fileName = "d:/encryptiontest.txt";
    private final String DIR_SD = "MyNotes";
    private final String FILENAME_SD = "mynotes.txt";

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mResultText = (TextView)findViewById(R.id.resultText);
    }


    public void onButtonWriteClick(View view) {
//        StringBuilder sb = new StringBuilder();
//        sb.append("Создаем большое количество длинных строк").
//                append("\n").
//                append("Вторая строка").
//                append("\n").
//                append("Нам интересно знать, как это все будет выглядить на экране").
//                append("\n").
//                append("Будет ли автоматический перенос строк").
//                append("\n").
//                append("Добавим еще несколько строк").
//                append("Я думаю у нас все должно получится").
//                append("Ну ок , думаю пока хватит");
//        mResultText.setText(sb.toString());

    }

    public void onReadButtonClick(View view) {
        try {
            stringList = getStringListFromBytesList(Encrypter.readDecryptedBytesList(fileName));
            StringBuilder sb = new StringBuilder();
            for (String s: stringList) {
                sb.append(s);
                sb.append("\n");
            }
            mResultText.setText(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
