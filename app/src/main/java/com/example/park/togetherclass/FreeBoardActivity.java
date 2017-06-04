package com.example.park.togetherclass;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
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

public class FreeBoardActivity extends AppCompatActivity {
    Spinner spinner;
    ArrayAdapter adapter;
    ListView l1;
    ArrayList<Free> freeArrayList = new ArrayList<Free>();
    ArrayList<Free> selectfreeArrayList = new ArrayList<Free>();
    FreeAdapter freeAdapter;
    FreeAdapter selecfreeAdapter;
    Button b1;
    String Nick, Name, Pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_board);
        setActionBar();
        init();
        SpinnerMethod();
        new BackgroundTask().execute();
        setList("");
        ListViewMethod();
    }

    void init() {
        b1 = (Button) findViewById(R.id.GFreebtn);
        b1.setEnabled(true);
        SharedPreferences info = getSharedPreferences("info", Activity.MODE_PRIVATE);
        Name = info.getString("Name", null);
        Nick = info.getString("Nick", null);
        Pw = info.getString("Pw", null);

        spinner = (Spinner) findViewById(R.id.FreeSpin);
        adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.Free, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        l1 = (ListView) findViewById(R.id.freelist);
        freeAdapter = new FreeAdapter(freeArrayList, getApplicationContext());
//        l1.setAdapter(freeAdapter);
        selecfreeAdapter = new FreeAdapter(selectfreeArrayList, getApplicationContext());
//        setList("");

    }

    void SpinnerMethod() {
//        Toast.makeText(getApplicationContext(), freeArrayList.get(3).Title, Toast.LENGTH_LONG).show();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                for (int i = 0; i < freeArrayList.size(); i++) {
                switch (((TextView) view).getText().toString()) {
                    case "전체":
                        setList("");
//                        l1.setAdapter(freeAdaptnotifyDataSetChanged();
                        break;
                    case "모앱":
                        setList("모앱");
                        break;
                    case "컴구":
                        setList("컴구");
                        break;
                    case "디비":
                        setList("디비");
                        break;
                    case "운영체제":
                        setList("운영체제");
                        break;
                    case "데통":
                        setList("데통");
                        break;
                    case "멀미":
                        setList("멀미");
                        break;
                    case "알고리즘":
                        setList("알고리즘");
                        break;
                }
            }

            //            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    void setList(String s) {
        selectfreeArrayList.clear();
        l1.setAdapter(selecfreeAdapter);
        if (s.equals("")) {
            for (int i = 0; i < freeArrayList.size(); i++) {
                selectfreeArrayList.add(freeArrayList.get(i));
            }
            selecfreeAdapter.notifyDataSetChanged();
        } else {
            for (int i = 0; i < freeArrayList.size(); i++) {
                if (freeArrayList.get(i).Subject.contains(s)) {
                    selectfreeArrayList.add(freeArrayList.get(i));
                }
                selecfreeAdapter.notifyDataSetChanged();
            }
        }
    }

    void ListViewMethod() {
        l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                freeAdapter.setContent(position);
                selecfreeAdapter.setContent(position);
                selecfreeAdapter.notifyDataSetChanged();
                freeAdapter.notifyDataSetChanged();
            }
        });
        l1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final String title = selectfreeArrayList.get(position).Title;
                final String nick = selectfreeArrayList.get(position).Nick;
                final String date = selectfreeArrayList.get(position).Date;
                final String pw = selectfreeArrayList.get(position).Pw;
                final int pos = position;
                LayoutInflater inflater = getLayoutInflater();
                View deleteView = inflater.inflate(R.layout.delete_free_list, null);
                final TextView t1 = (TextView) deleteView.findViewById(R.id.deleteNickTv);
                final EditText e1 = (EditText) deleteView.findViewById(R.id.deletePwEt);
                t1.setText("작성자 : " + nick + "님");
                AlertDialog.Builder builder = new AlertDialog.Builder(FreeBoardActivity.this);
                builder.setTitle("삭제")
                        .setView(deleteView)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Response.Listener<String> responseListener = new Response.Listener<String>() {

                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            boolean success = false;
                                            if (pw.equals(e1.getText().toString())) {
                                                success = true;
                                            }
                                            if (success) {
                                                Toast.makeText(getApplicationContext(), "삭제되었습니다.", Toast.LENGTH_LONG).show();
                                                freeAdapter.notifyDataSetChanged();
                                                selectfreeArrayList.remove(pos);
                                                selecfreeAdapter.notifyDataSetChanged();
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
                                FreeDeleteRequest deleteRequest = new FreeDeleteRequest(title, nick, date, e1.getText().toString(), responseListener);
                                RequestQueue queue = Volley.newRequestQueue(FreeBoardActivity.this);
                                queue.add(deleteRequest);
                            }
                        })
                        .setNegativeButton("취소", null)
                        .show();
                return true;
            }
        });
    }

    public void MyOnClick(View v) {
        Intent intent;
        if (v.getId() == R.id.FreeRegiBtn) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = getLayoutInflater().inflate(R.layout.add_free_list, null);
            ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.AddFree, android.R.layout.simple_spinner_dropdown_item);
            final Spinner spinner1 = (Spinner) view.findViewById(R.id.AFSpinner);
            spinner1.setAdapter(arrayAdapter);
            final EditText e1 = (EditText) view.findViewById(R.id.AFTitleEt);
            final EditText e2 = (EditText) view.findViewById(R.id.AFContentEt);
            final String date = doCurrentDate();
            final String[] Subject = {""};
            final String[] Title = {""};
            Subject[0] = spinner1.getSelectedItem().toString();
            Title[0] = e1.getText().toString();
            builder.setTitle("등록하기")
                    .setView(view)
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            Toast.makeText(getApplicationContext(), "게시물이 등록되었습니다.", Toast.LENGTH_LONG).show();
                                            selectfreeArrayList.add(new Free(Nick, Title[0], e2.getText().toString(), date, Pw, Subject[0]));
                                            selecfreeAdapter.notifyDataSetChanged();
                                            BackgroundTask task = new BackgroundTask();
                                            task.execute();
                                        }
                                    };
                                    Subject[0] = spinner1.getSelectedItem().toString();
                                    Title[0] = e1.getText().toString();
                                    Log.d("BEOM8", Nick + e1.getText().toString() + e2.getText().toString() + date + Pw);
                                    FreeBoardRequest write = new FreeBoardRequest(Nick, Title[0], e2.getText().toString(), date, Pw, Subject[0], responseListener);
                                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                    queue.add(write);
                                }
                            }

                    )
                    .setNegativeButton("취소", null)
                    .show();
        } else if (v.getId() == R.id.GMainbtn) {
            intent = new Intent(FreeBoardActivity.this, MainActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.GClassbtn) {
            intent = new Intent(FreeBoardActivity.this, ClassHourActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.GHomebtn) {
            intent = new Intent(FreeBoardActivity.this, HomeWorkActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.GMeetbtn) {
            intent = new Intent(FreeBoardActivity.this, MeetingActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.GNoticebtn) {
            intent = new Intent(FreeBoardActivity.this, NoticeActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.GPotalbtn) {
            intent = new Intent(FreeBoardActivity.this, PotalActivity.class);
            startActivity(intent);
        }
    }


    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            target = "http://pkr10.cafe24.com/FreeList.php";
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
                freeArrayList.clear();
                selectfreeArrayList.clear();
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                String freeContent, freeTitle, freeNick, freeDate, freePw, freeSubject;
                while (count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    freeContent = object.getString("FreeContent");
                    freeTitle = object.getString("FreeTitle");
                    freeNick = object.getString("FreeNick");
                    freeDate = object.getString("FreeDate");
                    freePw = object.getString("FreePw");
                    freeSubject = object.getString("FreeSubject");
                    Free free = new Free(freeTitle, freeNick, freeDate, freePw, freeContent, freeSubject);
                    freeArrayList.add(free);
                    selectfreeArrayList.add(free);
                    count++;
                }
                freeAdapter.notifyDataSetChanged();
                selecfreeAdapter.notifyDataSetChanged();
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
            Intent intent = new Intent(FreeBoardActivity.this, InfoActivity.class);
            intent.putExtra("Name", Name);
            intent.putExtra("Nick", Nick);
//            intent.putExtra("Time", Time);
            startActivity(intent);
        } else if (item.getItemId() == 2) {
            Intent intent = new Intent(FreeBoardActivity.this, LoginActivity.class);
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
}
