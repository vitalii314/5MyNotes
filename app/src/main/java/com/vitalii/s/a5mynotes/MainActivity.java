package com.vitalii.s.a5mynotes;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView mResultText;
    private EditText mAddText;
    private List<byte[]> byteList = new ArrayList<>();
    private List<String> stringList = new ArrayList<>();
    private final String DIR_SD = "MyNotes";
    private final String FILENAME = "mynotes.txt";
    private final String LOG_TAG = "myLogs";
    private Button mButtonWrite;
    private Button mButtonRead;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mResultText = (TextView) findViewById(R.id.resultText);
        mAddText = (EditText) findViewById(R.id.editText);
        mButtonWrite = (Button) findViewById(R.id.buttonWriteToFile);
        mButtonRead = (Button) findViewById(R.id.buttonReadFromFile);
    }


    public void onButtonWriteClick(View view) {

        if (mAddText.getText().toString().length()!=0) writeFileToExternalStorage();

    }

    public void onReadButtonClick(View view) {
        readFromFileExternalStorage();
    }


    public void writeFileToExternalStorage() {
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Log.d(LOG_TAG, "SD-карта не доступна: " + Environment.getExternalStorageState());
            return;
        }
        // получаем путь к SD
        File sdPath = Environment.getExternalStorageDirectory();
        // добавляем свой каталог к пути
        sdPath = new File(sdPath.getAbsolutePath() + "/" + DIR_SD);
        // создаем каталог
        sdPath.mkdirs();
        // формируем объект File, который содержит путь к файлу
        File sdFile = new File(sdPath, FILENAME);
        try {
            String s = mAddText.getText().toString()+"@";
            byte[] bytesNew = s.getBytes();
            List<byte[]> byteList1=  new ArrayList<>();
            byteList1.add(bytesNew);
            Encrypter.writeEncryptedBytesToFile(byteList1, sdFile);

        } catch (Exception e) {
            e.printStackTrace();
        }

        mAddText.setText("");
    }

    public void readFromFileExternalStorage() {
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Log.d(LOG_TAG, "SD-карта не доступна: " + Environment.getExternalStorageState());
            return;
        }
        File sdPath = Environment.getExternalStorageDirectory();
        sdPath = new File(sdPath.getAbsolutePath() + "/" + DIR_SD);
        // формируем объект File, который содержит путь к файлу
        File sdFile = new File(sdPath, FILENAME);


        try {
            byteList = Encrypter.readDecryptedBytesList(sdFile);
            stringList = getStringListFromBytesList(byteList);
            StringBuilder sb = new StringBuilder();
            for (int i =0; i<stringList.size(); i++) {
                sb.append(i).append(".");
                sb.append(stringList.get(i));
                sb.append("\n");
            }

              mResultText.setText(sb.toString());


        } catch (Exception e) {
            System.out.println("shit happens");
            e.printStackTrace();

        }

    }

    void writeFile() {


        try {
            byte[] bytesNew = mAddText.getText().toString().getBytes();
            byteList.add(bytesNew);
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            for (byte[] bytes : byteList) {
                fos.write((bytes));
            }
            fos.close();
            //Encrypter.writeEncryptedBytesToFile(byteList, file);

        } catch (Exception e) {
            e.printStackTrace();
        }

        mButtonWrite.setText("Button pressed");

    }

    void readFile() {
        try {
            // открываем поток для чтения
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    openFileInput(FILENAME)));
            InputStream is = new FileInputStream(FILENAME);
            byte[] tempByteArr = new byte[is.available()];
            is.read(tempByteArr);
            byteList = Encrypter.divideBytesArrayToByteList(tempByteArr);
            stringList = getStringListFromBytesList(byteList);
            StringBuilder sb = new StringBuilder();
            for (String s : stringList) {
                sb.append(s);
                sb.append("\n");
            }
            System.out.println("ALL DONE");
            System.out.println(is.available());
            mResultText.setText(sb.toString());


        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public List<String> getStringListFromBytesList(List<byte[]> byteList) throws Exception {
        List<String> stringList = new ArrayList<>();

        for (byte[] b : byteList) {
            String s = new String(b, "UTF-8");
            s = s.trim();
            stringList.add(s);
        }
        return stringList;
    }
}
