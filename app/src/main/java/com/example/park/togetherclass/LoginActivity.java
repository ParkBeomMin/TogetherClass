package com.example.park.togetherclass;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
EditText ID, PW;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void MyonClick(View v) {
        switch (v.getId()) {
            case R.id.loginButton:
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.registerButton:
                Intent intent1 = new Intent(LoginActivity.this, CodeActivity.class);
                startActivity(intent1);
                finish();
                break;
        }
    }
}
