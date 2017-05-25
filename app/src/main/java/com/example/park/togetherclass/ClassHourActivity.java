package com.example.park.togetherclass;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class ClassHourActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_hour);
        Intent intent = getIntent();
        String subject = intent.getStringExtra("Subject");
        Toast.makeText(getApplicationContext(), subject,Toast.LENGTH_LONG).show();
    }
}
