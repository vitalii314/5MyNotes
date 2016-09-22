package com.vitalii.s.a5mynotes;

import android.content.Intent;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPassword = (EditText)findViewById(R.id.passwordEditText);

    }

    public void submitButtonClick(View view) {
        if (mPassword.getText().toString().hashCode()==1456947942) {
            Intent intent = new Intent(MainActivity.this, StartingActivity.class);
            startActivity(intent);
        }
    }
}
