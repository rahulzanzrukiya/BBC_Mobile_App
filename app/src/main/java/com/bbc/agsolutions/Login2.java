package com.bbc.agsolutions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Login2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.getstart).setOnClickListener(v -> {

            startActivity(new Intent(Login2.this, Sign_upActivity.class));

        });

    }
}