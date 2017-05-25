package com.example.park.togetherclass;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CodeActivity extends AppCompatActivity {
    EditText Code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);
        init();
    }

    void init() {
        Code = (EditText) findViewById(R.id.codeEt);
    }

    public void MyOnClick(View v) {
        Intent intent;
        if (v.getId() == R.id.confirmButton) {
            if (Code.getText().toString().equals("001")) { // 학생용
                intent = new Intent(CodeActivity.this, RegisterActivity.class);
                intent.putExtra("CODE", Code.getText().toString());
                startActivity(intent);
                finish();
            } else if (Code.getText().toString().equals("002")) { // 교수용
                intent = new Intent(CodeActivity.this, RegisterActivity.class);
                intent.putExtra("CODE", Code.getText().toString());
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "올바른 코드를 입력해주세요.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
