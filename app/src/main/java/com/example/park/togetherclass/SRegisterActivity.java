package com.example.park.togetherclass;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class SRegisterActivity extends AppCompatActivity {
EditText Id, Pw, Pw2, Name;
    Spinner StudentNum, Major;
    TextView Stv, Ptv;
    final int REGISTER_CODE = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sregister);
    }
    public void MyonClick(View v) {
        switch (v.getId()){
            case R.id.RConfirmBtn :
                Intent intent = new Intent(SRegisterActivity.this, LoginActivity.class);
//                intent.putExtra("userId", Id.getText().toString());
//                startActivityForResult(intent,REGISTER_CODE);
                startActivity(intent);
                break;
            case R.id.cancelBtn :
                Intent intent1 = new Intent(SRegisterActivity.this, LoginActivity.class);
                startActivity(intent1);
        }
    }
}
