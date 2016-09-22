package com.vitalii.s.a5mynotes;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class StartingActivity extends AppCompatActivity {

    private TextView mResultText;
    private EditText mAddText;
    private List<byte[]> byteList = new ArrayList<>();
    private List<String> stringList = new ArrayList<>();
    private final String DIR_SD = "MyNotes";
    private final String FILENAME = "mynotes.txt";
    private final String LOG_TAG = "myLogs";
    private Button mButtonWrite;
    private EditText mDeletePhrase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);
        mResultText = (TextView) findViewById(R.id.resultText);
        mAddText = (EditText) findViewById(R.id.editText);
        mButtonWrite = (Button) findViewById(R.id.buttonWriteToFile);
        mDeletePhrase = (EditText) findViewById(R.id.deletePhraseEditText);

    }


    public void onButtonWriteClick(View view) {

        if (mAddText.getText().toString().length()!=0) {
            writeFileToExternalStorage(mAddText.getText().toString(),true);
        }

    }

    public void onReadButtonClick(View view) {
        Intent intent = new Intent(StartingActivity.this, StandartReadingActivity.class);
        startActivity(intent);

    }

    public void onRandomReadButtonClick(View view) {
        Intent intent = new Intent(StartingActivity.this, RandomReadingActivity.class);
        startActivity(intent);
    }

    public void onDeletePhraseButtonClick(View view) {

        if (mDeletePhrase.getText().toString().length()!=0) {
            stringList = StandartReadingActivity.readFromFileExternalStorage();
            int pos = Integer.parseInt(mDeletePhrase.getText().toString());
            stringList.remove(pos);
            for (int i=0; i< stringList.size(); i++) {
                if (i==0) {
                    writeFileToExternalStorage(stringList.get(i),false);
                } else {
                    writeFileToExternalStorage(stringList.get(i),true);
                }
            }
            mDeletePhrase.setText(R.string.empty);
        }
    }



    public void writeFileToExternalStorage(String s, boolean append) {
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
            s = s+"@";
            byte[] bytesNew = s.getBytes();
            List<byte[]> byteList1=  new ArrayList<>();
            byteList1.add(bytesNew);
            Encrypter.writeEncryptedBytesToFile(byteList1, sdFile, append);

        } catch (Exception e) {
            e.printStackTrace();
        }

        mAddText.setText("");
    }


}
