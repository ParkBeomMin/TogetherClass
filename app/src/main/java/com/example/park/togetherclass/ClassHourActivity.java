package com.example.park.togetherclass;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ClassHourActivity extends AppCompatActivity  {

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

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);            //액션바 아이콘을 업 네비게이션 형태로 표시합니다.
        actionBar.setDisplayShowTitleEnabled(false);        //액션바에 표시되는 제목의 표시유무를 설정합니다.
        actionBar.setDisplayShowHomeEnabled(false);            //홈 아이콘을 숨김처리합니다.

        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.MyBlue)));

        View view = getLayoutInflater().inflate(R.layout.action_bar, null);
        actionBar.setCustomView(view);
    }

    public void MyOnClick(View v) {
        Intent intent;
        if (v.getId() == R.id.GMainbtn) {
            intent = new Intent(ClassHourActivity.this, MainActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.GFreebtn) {
            intent = new Intent(ClassHourActivity.this, FreeBoardActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.GHomebtn) {
            intent = new Intent(ClassHourActivity.this, HomeWorkActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.GMeetbtn) {
            intent = new Intent(ClassHourActivity.this, MeetingActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.GNoticebtn) {
            intent = new Intent(ClassHourActivity.this, NoticeActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.GPotalbtn) {
            intent = new Intent(ClassHourActivity.this, PotalActivity.class);
            startActivity(intent);
        }
    }
}
