package com.example.park.togetherclass;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MeetingActivity extends AppCompatActivity {
    GridView g1;
    ArrayList<Meet> arrayList = new ArrayList<Meet>();
    MeetAdapter adapter;
String Name, Nick;
    Button b1;
    HorizontalScrollView s1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);
        setActionBar();
        init();
        new BackgroundTask().execute();
        GridViewMethod();
    }

    void init() {
        b1 = (Button) findViewById(R.id.GMeetbtn);
        b1.setEnabled(true);
        b1.setBackground(new ColorDrawable(getResources().getColor(R.color.ActionBar)));
        b1.setTextColor(getResources().getColor(R.color.White));
        s1 = (HorizontalScrollView) findViewById(R.id.scrollView);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                s1.smoothScrollBy(400, 0);
            }
        }, 200);
        AlertDialog.Builder builder = new AlertDialog.Builder(MeetingActivity.this);
        builder.setTitle("메일보내기 & 사이트 접속하기")
                .setMessage("짧게 클릭 시 메일보내기\n" +
                        "길게 클릭 시 사이트 접속")
                .setNegativeButton("확인", null)
                .show();
        g1 = (GridView) findViewById(R.id.meetingList);
        adapter = new MeetAdapter(arrayList, getApplication());
        g1.setAdapter(adapter);

        SharedPreferences info = getSharedPreferences("info", Activity.MODE_PRIVATE);
        Name = info.getString("Name", null);
        Nick = info.getString("Nick", null);
    }

    public void MyOnClick(View v) {
        Intent intent;
        if (v.getId() == R.id.GFreebtn) {
            intent = new Intent(MeetingActivity.this, FreeBoardActivity.class);
            startActivity(intent);
            finish();
        } else if (v.getId() == R.id.GClassbtn) {
            intent = new Intent(MeetingActivity.this, ClassHourActivity.class);
            startActivity(intent);
            finish();
        } else if (v.getId() == R.id.GHomebtn) {
            intent = new Intent(MeetingActivity.this, HomeWorkActivity.class);
            startActivity(intent);
            finish();
        } else if (v.getId() == R.id.GNoticebtn) {
            intent = new Intent(MeetingActivity.this, NoticeActivity.class);
            startActivity(intent);
            finish();
        } else if (v.getId() == R.id.GPotalbtn) {
            intent = new Intent(MeetingActivity.this, PotalActivity.class);
            startActivity(intent);
            finish();
        }
    }

    void GridViewMethod() {
        g1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String[] mail = {arrayList.get(position).Email};
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL, mail);
                intent.setType("text/plain");
//                startActivity(intent);
                startActivity(Intent.createChooser(intent, "Choose Email Client"));
            }
        });
        g1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String site = arrayList.get(position).Site;
                if (site.equals("-")) {
                    Toast.makeText(getApplicationContext(), "사이트가 존재하지 않습니다.", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(site));
                    startActivity(intent);
                }
                return true;
            }
        });
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            target = "http://pkr10.cafe24.com/MeetList.php";
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }
                bufferReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate();
        }

        @Override
        public void onPostExecute(String result) {
            try {
                arrayList.clear();
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                String Name, Room, Site, Call, Email;
                while (count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    Name = object.getString("Name");
                    Room = object.getString("Room");
                    Site = object.getString("Site");
                    Call = object.getString("CallNum");
                    Email = object.getString("Email");
                    Meet meet = new Meet(Name, Room, Site, Call, Email);
                    arrayList.add(meet);
                    count++;
                }
                adapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void setActionBar() {
        ActionBar actionBar = getSupportActionBar();


        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);            //액션바 아이콘을 업 네비게이션 형태로 표시합니다.
        actionBar.setDisplayShowTitleEnabled(false);        //액션바에 표시되는 제목의 표시유무를 설정합니다.
        actionBar.setDisplayShowHomeEnabled(false);            //홈 아이콘을 숨김처리합니다.

        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.ActionBar)));

        View view = getLayoutInflater().inflate(R.layout.action_bar, null);
        ImageButton i1 = (ImageButton) view.findViewById(R.id.homeBtn);
        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        actionBar.setCustomView(view);
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
            Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
//            intent.putExtra("Name", Name);
//            intent.putExtra("Nick", Nick);
//            intent.putExtra("Time", Time);
            startActivity(intent);
        } else if (item.getItemId() == 2) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
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
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}
