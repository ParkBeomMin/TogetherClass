package com.example.park.togetherclass;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CodeActivity extends AppCompatActivity {
EditText Code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);
    }

    public void MyonClick(View v) {
        Intent intent = new Intent(CodeActivity.this, SRegisterActivity.class);
        startActivity(intent);
        finish();
    }
}
