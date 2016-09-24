package com.vitalii.s.a5mynotes;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RandomReadingActivity extends AppCompatActivity {

    private EditText mSpeed;
    private TextView mResultText;
    private List<String> stringList;


    public class MyThread implements Runnable {
        int pos;
        List<Integer> tempPosList = new ArrayList<>();


        @Override
        public void run() {

            while (true) {
                try {
                    int speedValue = Integer.parseInt(mSpeed.getText().toString());
                    Thread.sleep(speedValue * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        do {
                            pos = (int) (Math.random() * stringList.size());
                        } while (wasAlready(pos));
                        mResultText.setText(pos + ". " + stringList.get(pos));
                    }
                });

            }

        }

        public boolean wasAlready(int pos) {

            for (int i = 0; i < tempPosList.size(); i++) {
                if (tempPosList.get(i) == pos) return true;
            }

            tempPosList.add(pos);
            if (tempPosList.size() == stringList.size()) tempPosList.clear();
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_reading);
        mSpeed = (EditText) findViewById(R.id.editSpeedText);
        mResultText = (TextView) findViewById(R.id.resultText);
        fillStringList();
        Thread thread = new Thread(new MyThread());
        thread.setDaemon(true);
        thread.start();

    }


    public void onSpeedUpButtonClick(View view) {
        int speedValue = Integer.parseInt(mSpeed.getText().toString());
        if (speedValue > 1) {
            speedValue--;
            mSpeed.setText(Integer.toString(speedValue));
        }
    }


    public void onSpeedDownButtonClick(View view) {
        int speedValue = Integer.parseInt(mSpeed.getText().toString());
        speedValue++;
        mSpeed.setText(Integer.toString(speedValue));
    }

    public void fillStringList() {
        stringList = StandartReadingActivity.readFromFileExternalStorage();

    }


}




