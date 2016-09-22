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

        @Override
        public void run() {
            int pos = (int)Math.random()*stringList.size();
            mResultText.setText(pos+"."+stringList.get(pos));
            try {
                Thread.sleep(Integer.parseInt(mSpeed.toString())*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
        if (speedValue>1) {
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




