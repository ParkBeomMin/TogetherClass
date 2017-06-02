package com.example.park.togetherclass;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
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
    String Name, Nick, Pw;
    Button b1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        init();
        new BackgroundTask().execute();
        ListViewMethod();
    }

    void init() {
        b1 = (Button) findViewById(R.id.NoticeBtn);
        SharedPreferences info = getSharedPreferences("info", Activity.MODE_PRIVATE);
        Name = info.getString("Name", null);
        Nick = info.getString("Nick", null);
        Pw = info.getString("Pw", null);
        if(Nick.equals("교수님"))
            b1.setVisibility(View.VISIBLE);
        l1 = (ListView) findViewById(R.id.NoticeList);
        adapter = new NoticeAdapter(arrayList, getApplication());
        l1.setAdapter(adapter);
    }
    void ListViewMethod() {
        l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(NoticeActivity.this);
                builder.setTitle(arrayList.get(position).Title)
                        .setMessage(arrayList.get(position).Content)
                        .setNegativeButton("확인",null)
                        .show();
            }
        });
    }

    public void MyOnClick(View v) {
        View view = getLayoutInflater().inflate(R.layout.add_free_list, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final Spinner spinner1 = (Spinner) view.findViewById(R.id.AFSpinner);
        spinner1.setVisibility(View.GONE);
        final EditText e1 = (EditText) view.findViewById(R.id.AFTitleEt);
        final EditText e2 = (EditText) view.findViewById(R.id.AFContentEt);
        final String date = doCurrentDate();
        builder.setTitle("등록하기")
                .setView(view)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Response.Listener<String> responseListener = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Toast.makeText(getApplicationContext(), "게시물이 등록되었습니다.", Toast.LENGTH_LONG).show();
                                        BackgroundTask task = new BackgroundTask();
                                        task.execute();
                                    }
                                };
                                NoticeAddRequest write = new NoticeAddRequest(e1.getText().toString(), e2.getText().toString(), date, Pw, "모앱", responseListener);
                                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                queue.add(write);
                            }
                        }

                )
                .setNegativeButton("취소", null)
                .show();
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
                String NoticeContent, NoticeTitle, NoticeSubject, NoticeDate, NoticePw;
                while (count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    NoticeContent = object.getString("NoticeContent");
                    NoticeTitle = object.getString("NoticeTitle");
                    NoticeDate = object.getString("NoticeDate");
                    NoticePw = object.getString("NoticePw");
                    NoticeSubject = object.getString("NoticeSubject");
                    Notice notice = new Notice(NoticeTitle, NoticeContent, NoticeDate, NoticePw, NoticeSubject);
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
}
