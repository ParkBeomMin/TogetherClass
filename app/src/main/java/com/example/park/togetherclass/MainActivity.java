package com.example.park.togetherclass;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private  long lastTimeBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void MyonClick(View v) {
        switch (v.getId()) {
            case R.id.MClassHourBtn :
                Intent intent = new Intent(MainActivity.this, ClassHourActivity.class);
                startActivity(intent);
                break;
            case R.id.MFreeBoardBtn :
                Intent intent1 = new Intent(MainActivity.this, FreeBoardActivity.class);
                startActivity(intent1);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,1,0, "내 정보");
        menu.add(0,2,0, "로그아웃");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == 1) {

        }else if (item.getItemId() == 2) {

        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        if(System.currentTimeMillis() - lastTimeBackPressed < 1500) {
            finish();
            return;
        }
        Toast.makeText(this, "뒤로 버튼을 한번 더 누르면 종료됩니다", Toast.LENGTH_SHORT).show();
        lastTimeBackPressed = System.currentTimeMillis();
    }

}
