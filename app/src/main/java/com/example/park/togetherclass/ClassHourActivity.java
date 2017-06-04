package com.example.park.togetherclass;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ClassHourActivity extends AppCompatActivity implements ActionBar.TabListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_hour);
        setActionBar();
        Intent intent = getIntent();
        String subject = intent.getStringExtra("Subject");
        Toast.makeText(getApplicationContext(), subject,Toast.LENGTH_LONG).show();
    }

    void setActionBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.addTab(actionBar.newTab().setText("메인").setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("수업시간").setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("자유게시판").setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("과목공지").setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("교수 정보").setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("포탈").setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("스케줄").setTabListener(this));

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);            //액션바 아이콘을 업 네비게이션 형태로 표시합니다.
        actionBar.setDisplayShowTitleEnabled(false);        //액션바에 표시되는 제목의 표시유무를 설정합니다.
        actionBar.setDisplayShowHomeEnabled(false);            //홈 아이콘을 숨김처리합니다.

        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.Mint)));

        View view = getLayoutInflater().inflate(R.layout.action_bar, null);
        actionBar.setCustomView(view);
    }
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        Intent intent;
        if (tab.getText().equals("자유게시판")) {
            intent = new Intent(ClassHourActivity.this, FreeBoardActivity.class);
            startActivity(intent);
        } else if (tab.getText().equals("과목공지")) {
            intent = new Intent(ClassHourActivity.this, NoticeActivity.class);
            startActivity(intent);
        } else if (tab.getText().equals("교수 정보")) {
            intent = new Intent(ClassHourActivity.this, MeetingActivity.class);
            startActivity(intent);
        } else if (tab.getText().equals("포탈")) {
            intent = new Intent(ClassHourActivity.this, PotalActivity.class);
            startActivity(intent);
        } else if (tab.getText().equals("스케줄")) {
            intent = new Intent(ClassHourActivity.this, HomeWorkActivity.class);
            startActivity(intent);
        } else if (tab.getText().equals("수업시간")) {
            intent = new Intent(ClassHourActivity.this, ClassHourActivity.class);
            startActivity(intent);
        } else if (tab.getText().equals("메인")) {
            intent = new Intent(ClassHourActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
}
