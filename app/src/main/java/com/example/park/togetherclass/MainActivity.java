package com.example.park.togetherclass;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.service.voice.VoiceInteractionSession;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    Button b1, b2, b3;
    private long lastTimeBackPressed;
    boolean auto;
    String Nick, Name, Time;
    ListView listView;
    ArrayList<String> arrayList = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setActionBar();
        init();
        new ReceiveWeather().execute();
    }

    void init() {
        b1 = (Button) findViewById(R.id.MFreeBoardBtn);
        b2 = (Button) findViewById(R.id.MMeetingBtn);
        listView = (ListView) findViewById(R.id.weather);
        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);
        SharedPreferences info = getSharedPreferences("info", Activity.MODE_PRIVATE);
        auto = info.getBoolean("auto", false);
        Nick = info.getString("Nick", null);
        Name = info.getString("Name", null);
        Time = info.getString("Time", null);
        Log.d("BEOM7", Nick + Name + Time);
        SharedPreferences.Editor editor = info.edit();
        editor.putString("Time", java.util.Calendar.getInstance().get(java.util.Calendar.YEAR) + "-" + java.util.Calendar.getInstance().get(java.util.Calendar.MONTH) + "-" + java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH) + " "
                + Calendar.getInstance().get(Calendar.HOUR) + ":" + Calendar.getInstance().get(Calendar.MINUTE) + ":" + Calendar.getInstance().get(Calendar.SECOND));
        editor.commit();
        if (Nick != null)
            if (Nick.equals("교수님")) {
                b1.setVisibility(View.GONE);
                b2.setVisibility(View.GONE);
            }
        if (auto) {
            Snackbar.make(getWindow().getDecorView().getRootView(), "자동로그인되었습니다.", Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(getWindow().getDecorView().getRootView(), "로그인되었습니다.", Snackbar.LENGTH_SHORT).show();
        }
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

    void setActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);            //액션바 아이콘을 업 네비게이션 형태로 표시합니다.
        actionBar.setDisplayShowTitleEnabled(false);        //액션바에 표시되는 제목의 표시유무를 설정합니다.
        actionBar.setDisplayShowHomeEnabled(false);            //홈 아이콘을 숨김처리합니다.

        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.ActionBar)));

        View view = getLayoutInflater().inflate(R.layout.action_bar, null);
        actionBar.setCustomView(view);
    }


    public class ReceiveWeather extends AsyncTask<URL, Integer, Long> {

        ArrayList<Weather> Weathers = new ArrayList<Weather>();

        protected Long doInBackground(URL... urls) {

            String url = "http://www.kma.go.kr/wid/queryDFSRSS.jsp?zone=4127152000";

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = null;

            try {
                response = client.newCall(request).execute();
                parseXML(response.body().string());
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(Long result) {
            String data = "";
            for (int i = 0; i < Weathers.size(); i++) {
                data =
                        java.util.Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + Integer.parseInt(Weathers.get(i).getDay()) + "일 " +
                                Weathers.get(i).getHour() + "시 " +
                                Weathers.get(i).getTemp() + "도 " +
                                Weathers.get(i).getWfKor() + " \n강수확률 " +
                                Weathers.get(i).getPop() + "%";
                arrayList.add(data);
            }
            adapter.notifyDataSetChanged();
        }

        void parseXML(String xml) {
            try {
                String tagName = "";
                boolean onHour = false;
                boolean onDay = false;
                boolean onTem = false;
                boolean onWfKor = false;
                boolean onPop = false;
                boolean onEnd = false;
                boolean isItemTag1 = false;
                int i = 0;

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser parser = factory.newPullParser();

                parser.setInput(new StringReader(xml));

                int eventType = parser.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG) {
                        tagName = parser.getName();
                        if (tagName.equals("data")) {
                            Weathers.add(new Weather());
                            onEnd = false;
                            isItemTag1 = true;
                        }
                    } else if (eventType == XmlPullParser.TEXT && isItemTag1) {
                        if (tagName.equals("hour") && !onHour) {
                            Weathers.get(i).setHour(parser.getText());
                            onHour = true;
                        }
                        if (tagName.equals("day") && !onDay) {
                            Weathers.get(i).setDay(parser.getText());
                            onDay = true;
                        }
                        if (tagName.equals("temp") && !onTem) {
                            Weathers.get(i).setTemp(parser.getText());
                            onTem = true;
                        }
                        if (tagName.equals("wfKor") && !onWfKor) {
                            Weathers.get(i).setWfKor(parser.getText());
                            onWfKor = true;
                        }
                        if (tagName.equals("pop") && !onPop) {
                            Weathers.get(i).setPop(parser.getText());
                            onPop = true;
                        }
                    } else if (eventType == XmlPullParser.END_TAG) {
                        if (tagName.equals("s06") && onEnd == false) {
                            i++;
                            onHour = false;
                            onDay = false;
                            onTem = false;
                            onWfKor = false;
                            onPop = false;
                            isItemTag1 = false;
                            onEnd = true;
                        }
                    }

                    eventType = parser.next();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
