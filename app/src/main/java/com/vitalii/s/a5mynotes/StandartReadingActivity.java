package com.vitalii.s.a5mynotes;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class StandartReadingActivity extends AppCompatActivity {

    private static TextView mResultText;
    private static final String DIR_SD = "MyNotes";
    private static final String FILENAME = "mynotes.txt";
    private static final String LOG_TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standart_reading);
        mResultText = (TextView)findViewById(R.id.resultText);
        readFromFileExternalStorage();
    }

    public static List<String> readFromFileExternalStorage() {
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Log.d(LOG_TAG, "SD-карта не доступна: " + Environment.getExternalStorageState());
            return new ArrayList<String>();
        }
        File sdPath = Environment.getExternalStorageDirectory();
        sdPath = new File(sdPath.getAbsolutePath() + "/" + DIR_SD);
        // формируем объект File, который содержит путь к файлу
        File sdFile = new File(sdPath, FILENAME);
        List<byte[]> byteList = new ArrayList<>();
        List<String> stringList = new ArrayList<>();

        try {
            byteList = Encrypter.readDecryptedBytesList(sdFile);
            stringList = getStringListFromBytesList(byteList);
            StringBuilder sb = new StringBuilder();
            for (int i =0; i<stringList.size(); i++) {
                sb.append(i).append(". ");
                sb.append(stringList.get(i));
                sb.append("\n");
                sb.append("\n");
            }

            mResultText.setText(sb.toString());


        } catch (Exception e) {
            System.out.println("shit happens");
            e.printStackTrace();

        }
        return stringList;
    }

    public static List<String> getStringListFromBytesList(List<byte[]> byteList) throws Exception {
        List<String> stringList = new ArrayList<>();

        for (byte[] b : byteList) {
            String s = new String(b, "UTF-8");
            s = s.trim();
            stringList.add(s);
        }
        return stringList;
    }
}
