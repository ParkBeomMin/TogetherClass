package com.example.park.togetherclass;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.os.CancellationSignal;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    private long lastTimeBackPressed;
    TextView t1, t2;
    EditText NameEt, NickEt, PwEt;
    RadioButton r1, r2;
    CheckBox c1;
    String Name, Nick, Pw, Subject;
    Boolean auto;
    String Time;
    MyManageDB myManageDB;
    ArrayList<String> ProfessorId = new ArrayList<String>();
    ArrayList<String> ProfessorName = new ArrayList<String>();
    ArrayList<String> ProfessorPw = new ArrayList<String>();
    ArrayList<String> ProfessorSub = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        autoLogin();
        checkPermission();
    }

    void init() {
        myManageDB = MyManageDB.getInstance(this);
        myManageDB.execINSERTProfessor("001", "장소연 교수님", "111", "모앱");
        myManageDB.execINSERTProfessor("002", "박성주 교수님", "111", "컴구");
        myManageDB.execINSERTProfessor("003", "조성현 교수님", "111", "데통");
        myManageDB.execINSERTProfessor("004", "김태형 교수님", "111", "알고리즘");
        myManageDB.execINSERTProfessor("005", "김영훈 교수님", "111", "디비");
        String sql = "SELECT id FROM professor";
        Cursor recordset = myManageDB.execSELECTStudent(sql);
        recordset.moveToFirst();
        String str = "";
        do {
            str = recordset.getString(0);
            Log.d("BEOM16", str);
            ProfessorId.add(str);
        } while (recordset.moveToNext());
        recordset.close();
        sql = "SELECT name FROM professor";
        recordset = myManageDB.execSELECTStudent(sql);
        recordset.moveToFirst();
        str = "";
        do {
            str = recordset.getString(0);
            Log.d("BEOM16", str);
            ProfessorName.add(str);
        } while (recordset.moveToNext());
        recordset.close();
        sql = "SELECT password FROM professor";
        recordset = myManageDB.execSELECTStudent(sql);
        recordset.moveToFirst();
        str = "";
        do {
            str = recordset.getString(0);
            Log.d("BEOM16", str);
            ProfessorPw.add(str);
        } while (recordset.moveToNext());
        recordset.close();
        sql = "SELECT subject FROM professor";
        recordset = myManageDB.execSELECTStudent(sql);
        recordset.moveToFirst();
        str = "";
        do {
            str = recordset.getString(0);
            Log.d("BEOM16", str);
            ProfessorSub.add(str);
        } while (recordset.moveToNext());
        recordset.close();
        Intent intent = getIntent();
        if (intent.getStringExtra("Logout") != null) {
            String logout = intent.getStringExtra("Logout");
            if (logout.equals("Logout")) {
                Snackbar.make(getWindow().getDecorView().getRootView(), Html.fromHtml("<font color=\"#ffffff\">로그아웃되었습니다.</font>"), Snackbar.LENGTH_SHORT).show();
            }
        }
        SharedPreferences info = getSharedPreferences("info", Activity.MODE_PRIVATE);
        Name = info.getString("Name", null);
        Nick = info.getString("Nick", null);
        Pw = info.getString("Pw", null);
        auto = info.getBoolean("auto", false);
        Time = info.getString("Time", null);
        NameEt = (EditText) findViewById(R.id.nameEt);
        NickEt = (EditText) findViewById(R.id.nickEt);
        PwEt = (EditText) findViewById(R.id.pwEt);
        r1 = (RadioButton) findViewById(R.id.studentLogin);
        r2 = (RadioButton) findViewById(R.id.professorLogin);
        r2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                t1 = (TextView) findViewById(R.id.NameTv);
                t2 = (TextView) findViewById(R.id.NickTv);
                if (b) {
                    t1.setText("수업코드");
                    t2.setVisibility(View.GONE);
                    NickEt.setText("교수님");
                    NickEt.setVisibility(View.GONE);
                } else {
                    t1.setText("NAME");
                    t2.setVisibility(View.VISIBLE);
                    NickEt.setText("");
                    NickEt.setVisibility(View.VISIBLE);
                }
            }
        });
        c1 = (CheckBox) findViewById(R.id.autoLoginCheckBox);
        c1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferences info = getSharedPreferences("info", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = info.edit();
                if (b) {
                    editor.putBoolean("auto", true);
                } else {
                    editor.putBoolean("auto", false);
                    editor.clear();
                }
                editor.commit();
            }
        });
    }

    public void MyOnClick(View v) {
        switch (v.getId()) {
            case R.id.loginButton:
                if (!NickEt.getText().toString().contains("교수님")) {
                    if (NameEt.getText().toString().length() == 0 || NickEt.getText().toString().length() == 0 || PwEt.getText().toString().length() == 0) {
                        if (NameEt.getText().toString().length() == 0) {
                            NameEt.requestFocus();
                            NameEt.setHintTextColor(Color.RED);
                            NameEt.setHint("이름을 입력해주세요!");
                        } else if (NickEt.getText().toString().length() == 0) {
                            NickEt.requestFocus();
                            NickEt.setHintTextColor(Color.RED);
                            NickEt.setHint("닉네임을 입력해주세요!");
                        } else if (PwEt.getText().toString().length() == 0) {
                            PwEt.requestFocus();
                            PwEt.setHintTextColor(Color.RED);
                            PwEt.setHint("비밀번호를 입력해주세요!");
                        }
                    } else {
                        SharedPreferences info = getSharedPreferences("info", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = info.edit();
                        editor.putString("Name", NameEt.getText().toString());
                        editor.putString("Nick", NickEt.getText().toString());
                        editor.putString("Pw", PwEt.getText().toString());
                        if (c1.isChecked())
                            editor.putBoolean("auto", true);
                        if (NameEt.getText().toString().length() == 0 || NickEt.getText().toString().length() == 0 || PwEt.getText().toString().length() == 0) {
                            editor.putBoolean("auto", false);
                        }
                        editor.commit();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("Name", NameEt.getText().toString());
                        intent.putExtra("Nick", NickEt.getText().toString());
                        intent.putExtra("Pw", PwEt.getText().toString());
                        startActivity(intent);
                        finish();
                    }
                } else {
                    if (NameEt.getText().toString().length() == 0 || PwEt.getText().toString().length() == 0) {
                        if (NameEt.getText().toString().length() == 0) {
                            NameEt.requestFocus();
                            NameEt.setHintTextColor(Color.RED);
                            NameEt.setHint("수업번호를 입력해주세요!");
                        } else if (PwEt.getText().toString().length() == 0) {
                            PwEt.requestFocus();
                            PwEt.setHintTextColor(Color.RED);
                            PwEt.setHint("비밀번호를 입력해주세요!");
                        }
                    } else {
                        for (int i = 0; i < ProfessorId.size(); i++) {
                            if (NameEt.getText().toString().equals(ProfessorId.get(i).toString())) {
                                Name = ProfessorId.get(i).toString();
                                Nick = ProfessorName.get(i).toString();
                                Pw = ProfessorPw.get(i).toString();
                                Subject = ProfessorSub.get(i).toString();
                            }
                        }
                        if (NameEt.getText().toString().equals(Name) && PwEt.getText().toString().equals(Pw)) {
                            SharedPreferences info = getSharedPreferences("info", Activity.MODE_PRIVATE);
                            SharedPreferences.Editor editor = info.edit();
                            editor.putString("Name", NameEt.getText().toString());
                            editor.putString("Nick", Nick);
                            editor.putString("Pw", PwEt.getText().toString());
                            editor.putString("Subject", Subject);
                            if (c1.isChecked())
                                editor.putBoolean("auto", true);
                            if (NameEt.getText().toString().length() == 0 || PwEt.getText().toString().length() == 0) {
                                editor.putBoolean("auto", false);
                            }

                            editor.commit();

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("Name", NameEt.getText().toString());
                            intent.putExtra("Nick", NickEt.getText().toString());
                            intent.putExtra("Pw", PwEt.getText().toString());
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(this, "수업코드 및 비밀번호가 잘못되었습니다!", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                break;
        }
    }

    void autoLogin() {
        if (auto) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("Name", Name);
            intent.putExtra("Nick", Nick);
            intent.putExtra("Pw", Pw);
            startActivity(intent);
            finish();
        }
    }

    void checkPermission() {

        int permissioninfo = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissioninfo == PackageManager.PERMISSION_GRANTED) {
        } else {
            Toast.makeText(getApplicationContext(),
                    "허용을 눌러야 정상적인 앱 실행이 가능합니다.", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    100);
        }
    }
    public void onBackPressed() {
        if (System.currentTimeMillis() - lastTimeBackPressed < 1500) {
            System.exit(0);
            return;
        }
        Toast.makeText(this, "뒤로 버튼을 한번 더 누르면 종료됩니다", Toast.LENGTH_SHORT).show();
        lastTimeBackPressed = System.currentTimeMillis();
    }
}
//
//
//}
