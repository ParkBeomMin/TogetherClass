package com.example.park.togetherclass;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private long lastTimeBackPressed;
    boolean auto;
    String Nick, Name, Time;
    LinearLayout l1, l2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    void init() {
        SharedPreferences info = getSharedPreferences("info", Activity.MODE_PRIVATE);
        auto = info.getBoolean("auto", false);
        Nick = info.getString("Nick", null);
        Name = info.getString("Name", null);
        Time = info.getString("Time", null);
        Log.d("BEOM7", Nick + Name + Time);
        SharedPreferences.Editor editor = info.edit();
        editor.putString("Time", java.util.Calendar.getInstance().get(java.util.Calendar.YEAR) + "-" + java.util.Calendar.getInstance().get(java.util.Calendar.MONTH) + "-" + java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH) +" "
                + Calendar.getInstance().get(Calendar.HOUR) +":"+ Calendar.getInstance().get(Calendar.MINUTE) +":"+ Calendar.getInstance().get(Calendar.SECOND));
        editor.commit();
        if (Nick != null)
            if (Nick.equals("교수님")) {
                l1.setVisibility(View.GONE);
                l2.setVisibility(View.VISIBLE);
            }
        if (auto) {
            Snackbar.make(getWindow().getDecorView().getRootView(), "자동로그인되었습니다.", Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(getWindow().getDecorView().getRootView(), "로그인되었습니다.", Snackbar.LENGTH_SHORT).show();
        }

        l1 = (LinearLayout) findViewById(R.id.StudentMain);
        l2 = (LinearLayout) findViewById(R.id.ProfessorMain);
    }

    public void MyOnClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.MClassHourBtn:
                final int[] SelectSubject = new int[1];
                final String[] Subject = {"모앱", "디비", "컴구", "OS", "알고리즘"};
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("어떤 과목의 수업입니까?")
                        .setSingleChoiceItems(Subject, 0, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        SelectSubject[0] = i;
                                    }
                                }
                        )
                        .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(MainActivity.this, ClassHourActivity.class);
                                intent.putExtra("Subject", Subject[SelectSubject[0]]);
                                startActivity(intent);
                            }
                        })
                        .setPositiveButton("취소", null)
                        .show();
                break;
            case R.id.MFreeBoardBtn:
                intent = new Intent(MainActivity.this, FreeBoardActivity.class);
                startActivity(intent);
                break;
            case R.id.MNoticeBtn:
                intent = new Intent(MainActivity.this, NoticeActivity.class);
                startActivity(intent);
                break;
            case R.id.MQuestionBtn:
                intent = new Intent(MainActivity.this, QuestionActivity.class);
                startActivity(intent);
                break;
            case R.id.MMeetingBtn:
                intent = new Intent(MainActivity.this, MeetingActivity.class);
                startActivity(intent);
                break;
            case R.id.MHomePageBtn:
                intent = new Intent(MainActivity.this, PotalActivity.class);
                startActivity(intent);
                break;
            case R.id.MHomeWorkBtn:
                intent = new Intent(MainActivity.this, HomeWorkActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "내 정보");
        menu.add(0, 2, 0, "로그아웃");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 1) {
            Intent intent = new Intent(MainActivity.this, InfoActivity.class);
            intent.putExtra("Name", Name);
            intent.putExtra("Nick", Nick);
            intent.putExtra("Time", Time);
            startActivity(intent);
        } else if (item.getItemId() == 2) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.putExtra("Logout", "Logout");
            SharedPreferences info = getSharedPreferences("info", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = info.edit();
            editor.clear();
            editor.commit();
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        if (System.currentTimeMillis() - lastTimeBackPressed < 1500) {
            finish();
            return;
        }
        Toast.makeText(this, "뒤로 버튼을 한번 더 누르면 종료됩니다", Toast.LENGTH_SHORT).show();
        lastTimeBackPressed = System.currentTimeMillis();
    }

}
