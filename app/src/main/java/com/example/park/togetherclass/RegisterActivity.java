package com.example.park.togetherclass;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.jar.Attributes;

public class RegisterActivity extends AppCompatActivity {
    ArrayAdapter adapter1, adapter2;
    EditText IdEt, PwEt, Pw2Et, NameEt;
    Spinner StudentNumSpin, MajorSpin;
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
        IdEt = (EditText) findViewById(R.id.RIdEt);
        PwEt = (EditText) findViewById(R.id.RPwEt);
        Pw2Et = (EditText) findViewById(R.id.RPw2Et);
        NameEt = (EditText) findViewById(R.id.RNameEt);
        StudentNumSpin = (Spinner) findViewById(R.id.RStudentNum);
        adapter1 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.StudentNum, android.R.layout.simple_spinner_dropdown_item);
        StudentNumSpin.setAdapter(adapter1);
        MajorSpin = (Spinner) findViewById(R.id.RMajor);
        adapter2 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.Major, android.R.layout.simple_spinner_dropdown_item);
        MajorSpin.setAdapter(adapter2);
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

    public void MyOnClick(View v) {
        switch (v.getId()) {
            case R.id.RConfirmBtn:
                final String ID = IdEt.getText().toString();
                final String Password = PwEt.getText().toString();
                final String Password2 = Pw2Et.getText().toString();
                final String Name = NameEt.getText().toString();
                final String StudentNumber = StudentNumSpin.getSelectedItem().toString();
                final String Major = MajorSpin.getSelectedItem().toString();
//                final String Subject = spinner.getSelectedItem().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            //     boolean success = jsonResponse.getBoolean("success");
                            boolean success ;

                            if (Password.length() == 0 || Password2.length() == 0 || Name.length() == 0) {
                                success = false;
                            } else {
                                if (Password.equals(Password2)) {
                                    success = true;
                                } else {
                                    success = false;
                                }
                            }
                            if (success) {
                                Toast.makeText(RegisterActivity.this, "회원가입 되셨습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                RegisterActivity.this.startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                if (Password.length() == 0 || Password2.length() == 0 || Name.length() == 0) {
                                    builder.setMessage("회원정보를 모두 입력해주세요.")
                                            .setNegativeButton("다시시도", null)
                                            .create()
                                            .show();
                                } else if (Password != Password2) {
                                    builder.setMessage("2차 비밀번호가 다릅니다.")
                                            .setNegativeButton("다시시도", null)
                                            .create()
                                            .show();
                                }
                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                RegisterRequest registerRequest = new RegisterRequest(ID, Password, Name, StudentNumber, Major,Code, "", responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
                break;
            case R.id.cancelBtn:
                Intent intent1 = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent1);
        }
    }
}
