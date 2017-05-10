package com.example.park.togetherclass;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {
    ArrayAdapter adapter1, adapter2;
    EditText Id, Pw, Pw2, Name;
    Spinner StudentNum, Major;
    RelativeLayout RS, RP;
    final int REGISTER_CODE = 0;
    String Code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        ChangeMode();
    }
    void init(){
        Id = (EditText) findViewById(R.id.RIdEt);
        Pw = (EditText) findViewById(R.id.RPwEt);
        Pw2 = (EditText) findViewById(R.id.RPw2Et);
        Name = (EditText) findViewById(R.id.RNameEt);
        StudentNum = (Spinner) findViewById(R.id.RStudentNum);
        adapter1 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.StudentNum, android.R.layout.simple_spinner_dropdown_item);
        StudentNum.setAdapter(adapter1);
        Major = (Spinner) findViewById(R.id.RMajor);
        adapter2 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.Major, android.R.layout.simple_spinner_dropdown_item);
        Major.setAdapter(adapter2);
        RS = (RelativeLayout) findViewById(R.id.RStudent);
        RP = (RelativeLayout) findViewById(R.id.RProfessor);
    }
    void ChangeMode() {
        Intent intent = getIntent();
        Code = intent.getStringExtra("CODE");
        Log.d("CodeCheck", Code);
        if(Code.equals("002")) {
            RS.setVisibility(View.GONE);
            RP.setVisibility(View.VISIBLE);
        }
    }

    public void MyonClick(View v) {
        switch (v.getId()) {
            case R.id.RConfirmBtn:
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
//                intent.putExtra("userId", Id.getText().toString());
//                startActivityForResult(intent,REGISTER_CODE);
                startActivity(intent);
                break;
            case R.id.cancelBtn:
                Intent intent1 = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent1);
        }
    }
}
