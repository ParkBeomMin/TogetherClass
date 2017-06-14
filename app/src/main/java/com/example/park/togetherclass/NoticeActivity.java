package com.example.park.togetherclass;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class NoticeActivity extends AppCompatActivity {
    ListView l1;
    ArrayList<Notice> arrayList = new ArrayList<Notice>();
    NoticeAdapter adapter;
    String Name, Nick, Pw, Subject;
    Button b1, b2, b3, b4;
    HorizontalScrollView s1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        setActionBar();
        init();
        new BackgroundTask().execute();
        ListViewMethod();
    }

    void init() {
        b1 = (Button) findViewById(R.id.NoticeBtn);
        b2 = (Button) findViewById(R.id.GNoticebtn);
        b3 = (Button) findViewById(R.id.GMeetbtn);
        b4 = (Button) findViewById(R.id.GFreebtn);
        b2.setBackground(new ColorDrawable(getResources().getColor(R.color.ActionBar)));
        b2.setTextColor(getResources().getColor(R.color.White));
        s1 = (HorizontalScrollView) findViewById(R.id.scrollView);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                s1.smoothScrollBy(0, 0);
            }
        }, 200);
        SharedPreferences info = getSharedPreferences("info", Activity.MODE_PRIVATE);
        Name = info.getString("Name", null);
        Nick = info.getString("Nick", null);
        Pw = info.getString("Pw", null);
        Subject = info.getString("Subject", null);
        if (Nick.contains("교수님")) {
            b1.setVisibility(View.VISIBLE);
            b3.setVisibility(View.GONE);
            b4.setVisibility(View.GONE);
        }
        l1 = (ListView) findViewById(R.id.NoticeList);
        adapter = new NoticeAdapter(arrayList, getApplication());
        l1.setAdapter(adapter);
    }

    void ListViewMethod() {
        l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                View titleView = getLayoutInflater().inflate(R.layout.add_title, null);
                TextView t1 = (TextView) titleView.findViewById(R.id.addtitleTv);
                t1.setText("공지사항");
                View v = getLayoutInflater().inflate(R.layout.notice_list, null);
                TextView t2 = (TextView) v.findViewById(R.id.notice_titleTv);
                t2.setText("[" + arrayList.get(position).Title + "]");
                TextView t3 = (TextView) v.findViewById(R.id.notice_contentTv);
                t3.setText(arrayList.get(position).Content);
                AlertDialog.Builder builder = new AlertDialog.Builder(NoticeActivity.this);
                builder.setCustomTitle(titleView)
                        .setView(v).setNegativeButton("확인", null)
                        .show();
            }
        });
        if (Nick.contains("교수님")) {
            l1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                    LayoutInflater inflater = getLayoutInflater();
                    View titleView = inflater.inflate(R.layout.add_title, null);
                    TextView titleTv = (TextView) titleView.findViewById(R.id.addtitleTv);
                    titleTv.setText("삭제");
                    View deleteView = inflater.inflate(R.layout.delete_free_list, null);
                    final TextView t1 = (TextView) deleteView.findViewById(R.id.deleteNickTv);
                    final EditText e1 = (EditText) deleteView.findViewById(R.id.deletePwEt);
                    Button cancel = (Button) deleteView.findViewById(R.id.cancelBtn);
                    Button confirm = (Button) deleteView.findViewById(R.id.confirmBtn);
                    t1.setText("작성자 : " + arrayList.get(position).Nick);
                    t1.setPadding(10, 30, 0, 0);
                    final AlertDialog alertDialog = new AlertDialog.Builder(NoticeActivity.this).create();
                    alertDialog.setCustomTitle(titleView);
                    alertDialog.setView(deleteView);
                    alertDialog.show();
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });
                    confirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Response.Listener<String> responseListener = new Response.Listener<String>() {

                                @Override
                                public void onResponse(String response) {
                                    try {
                                        boolean success = false;
                                        if (arrayList.get(position).Pw.equals(e1.getText().toString())) {
                                            success = true;
                                        }
                                        if (success) {
                                            Toast.makeText(getApplicationContext(), "삭제되었습니다.", Toast.LENGTH_LONG).show();
                                            new BackgroundTask().execute();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "비밀번호를 확인해주세요.", Toast.LENGTH_LONG).show();
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    BackgroundTask task = new BackgroundTask();
                                    task.execute();
                                }
                            };
                            NoticeDeleteRequest deleteRequest = new NoticeDeleteRequest(arrayList.get(position).Title, arrayList.get(position).Date,
                                    arrayList.get(position).Nick, e1.getText().toString(), arrayList.get(position).Subject, responseListener);
                            RequestQueue queue = Volley.newRequestQueue(NoticeActivity.this);
                            queue.add(deleteRequest);
                            alertDialog.dismiss();
                        }
                    });
                    return true;
                }
            });
        }
    }

    public void MyOnClick(View v) {
        Intent intent;
        if (v.getId() == R.id.NoticeBtn) {
            View titleView = getLayoutInflater().inflate(R.layout.add_title, null);
            TextView t1 = (TextView) titleView.findViewById(R.id.addtitleTv);
            t1.setText("등록하기");
            View view = getLayoutInflater().inflate(R.layout.add_free_list, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            final Spinner spinner1 = (Spinner) view.findViewById(R.id.AFSpinner);
            spinner1.setVisibility(View.GONE);
            final EditText e1 = (EditText) view.findViewById(R.id.AFTitleEt);
            final EditText e2 = (EditText) view.findViewById(R.id.AFContentEt);
            final String date = doCurrentDate();
            Button cancel = (Button) view.findViewById(R.id.cancelBtn);
            Button confirm = (Button) view.findViewById(R.id.confirmBtn);
            alertDialog.setCustomTitle(titleView);
            alertDialog.setView(view);
            alertDialog.show();
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(getApplicationContext(), "게시물이 등록되었습니다.", Toast.LENGTH_LONG).show();
                            BackgroundTask task = new BackgroundTask();
                            task.execute();
                        }
                    };
                    NoticeAddRequest write = new NoticeAddRequest(e1.getText().toString(), e2.getText().toString(), Nick, date, Pw, Subject, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    queue.add(write);
                    alertDialog.dismiss();
                }
            });
        } else if (v.getId() == R.id.GClassbtn) {
            intent = new Intent(NoticeActivity.this, ClassHourActivity.class);
            startActivity(intent);
            finish();
        } else if (v.getId() == R.id.GFreebtn) {
            intent = new Intent(NoticeActivity.this, FreeBoardActivity.class);
            startActivity(intent);
            finish();
        } else if (v.getId() == R.id.GHomebtn) {
            intent = new Intent(NoticeActivity.this, HomeWorkActivity.class);
            startActivity(intent);
            finish();
        } else if (v.getId() == R.id.GMeetbtn) {
            intent = new Intent(NoticeActivity.this, MeetingActivity.class);
            startActivity(intent);
            finish();
        } else if (v.getId() == R.id.GPotalbtn) {
            intent = new Intent(NoticeActivity.this, PotalActivity.class);
            startActivity(intent);
            finish();
        }
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            target = "http://pkr10.cafe24.com/NoticeList.php";
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
                String NoticeContent, NoticeTitle, NoticeSubject, NoticeDate, NoticePw, NoticeName;
                while (count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    NoticeContent = object.getString("NoticeContent");
                    NoticeTitle = object.getString("NoticeTitle");
                    NoticeName = object.getString("NoticeName");
                    NoticeDate = object.getString("NoticeDate");
                    NoticePw = object.getString("NoticePw");
                    NoticeSubject = object.getString("NoticeSubject");
                    Notice notice = new Notice(NoticeTitle, NoticeContent, NoticeDate, NoticePw, NoticeSubject, NoticeName);
                    arrayList.add(notice);
                    count++;
                }
                adapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public String doCurrentDate() {
        int nYear;
        String nMonth;
        String nDay;
        String nTime;
        String nMin;
        String nSec;
        // 현재 날짜 구하기
        Calendar calendar = new GregorianCalendar(Locale.KOREA);
        nYear = calendar.get(Calendar.YEAR);
        nMonth = (calendar.get(Calendar.MONTH) + 1) + "";
        if (nMonth.length() < 2) {
            nMonth = "0" + nMonth;
        }
        nDay = calendar.get(Calendar.DAY_OF_MONTH) + "";
        if (nDay.length() < 2) {
            nDay = "0" + nDay;
        }
        nTime = calendar.get(Calendar.HOUR_OF_DAY) + "";
        if (nTime.length() < 2) {
            nTime = "0" + nTime;
        }
        nMin = calendar.get(Calendar.MINUTE) + "";
        if (nMin.length() < 2) {
            nMin = "0" + nMin;
        }
        nSec = calendar.get(Calendar.SECOND) + "";
        if (nSec.length() < 2) {
            nSec = "0" + nSec;
        }
        String strD = nYear + "-" + nMonth + "-" + nDay + " " + nTime + ":" + nMin + ":" + nSec;
        return strD;
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
                startActivity(intent);finish();
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
//        new BackgroundTask().cancel(true);
        finish();
    }

}
