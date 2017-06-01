package com.example.park.togetherclass;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.EditText;
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

    String Nick, Name, Pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_board);
        init();
        new BackgroundTask().execute();
        ListViewMethod();
        SpinnerMethod();
    }

    void init() {
        SharedPreferences info = getSharedPreferences("info", Activity.MODE_PRIVATE);
        Name = info.getString("Name", null);
        Nick = info.getString("Nick", null);
        Pw = info.getString("Pw", null);

        spinner = (Spinner) findViewById(R.id.FreeSpin);
        adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.Free, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        l1 = (ListView) findViewById(R.id.freelist);
        freeAdapter = new FreeAdapter(freeArrayList, getApplicationContext());
        selecfreeAdapter = new FreeAdapter(selectfreeArrayList, getApplicationContext());
        l1.setAdapter(freeAdapter);
    }

    void SpinnerMethod() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectfreeArrayList.clear();
                l1.setAdapter(selecfreeAdapter);
                for (int i = 0; i < freeArrayList.size(); i++) {
                    switch (((TextView)view).getText().toString()) {
                        case "전체":
                            l1.setAdapter(freeAdapter);
                            break;
                        case "모앱":
                            if (freeArrayList.get(i).Title.contains("[모앱]")) {
                                selectfreeArrayList.add(freeArrayList.get(i));
                            }
                            selecfreeAdapter.notifyDataSetChanged();
                            break;
                        case "컴구":
                            if (freeArrayList.get(i).Title.contains("[컴구]")) {
                                selectfreeArrayList.add(freeArrayList.get(i));
                            }
                            selecfreeAdapter.notifyDataSetChanged();

                            break;
                        case "디비":
                            if (freeArrayList.get(i).Title.contains("[디비]")) {
                                selectfreeArrayList.add(freeArrayList.get(i));
                            }
                            selecfreeAdapter.notifyDataSetChanged();

                            break;
                        case "운영체제":
                            if (freeArrayList.get(i).Title.contains("[운영체제]")) {
                                selectfreeArrayList.add(freeArrayList.get(i));
                            }
                            selecfreeAdapter.notifyDataSetChanged();

                            break;
                        case "데통":
                            if (freeArrayList.get(i).Title.contains("[데통]")) {
                                selectfreeArrayList.add(freeArrayList.get(i));
                            }
                            selecfreeAdapter.notifyDataSetChanged();

                            break;
                        case "멀미":
                            if (freeArrayList.get(i).Title.contains("[멀미]")) {
                                selectfreeArrayList.add(freeArrayList.get(i));
                            }
                            selecfreeAdapter.notifyDataSetChanged();

                            break;
                        case "알고리즘":
                            if (freeArrayList.get(i).Title.contains("[알고리즘]")) {
                                selectfreeArrayList.add(freeArrayList.get(i));
                            }
                            selecfreeAdapter.notifyDataSetChanged();
                            break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
                final String title = freeArrayList.get(position).Title;
                final String nick = freeArrayList.get(position).Nick;
                final String date = freeArrayList.get(position).Date;
                final String pw = freeArrayList.get(position).Pw;
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
                                                selecfreeAdapter.notifyDataSetChanged();
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
                                    Subject[0] = spinner1.getSelectedItem().toString();
                                    Title[0] = "[" + Subject[0] + "]" + e1.getText().toString();
                                    Log.d("BEOM8", Nick + e1.getText().toString() + e2.getText().toString() + date + Pw);
                                    FreeBoardRequest write = new FreeBoardRequest(Nick, Title[0], e2.getText().toString(), date, Pw, Subject[0], responseListener);
                                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                    queue.add(write);
                                }
                            }

                    )
                    .setNegativeButton("취소", null)
                    .show();
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
                String freeContent, freeTitle, freeNick, freeDate, freePw;
                while (count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    freeContent = object.getString("FreeContent");
                    freeTitle = object.getString("FreeTitle");
                    freeNick = object.getString("FreeNick");
                    freeDate = object.getString("FreeDate");
                    freePw = object.getString("FreePw");
                    Free free = new Free(freeTitle, freeNick, freeDate, freePw, freeContent);
                    freeArrayList.add(free);
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
        int nMonth;
        int nDay;
        int nTime;
        int nMin;
        int nSec;
        // 현재 날짜 구하기
        Calendar calendar = new GregorianCalendar(Locale.KOREA);
        nYear = calendar.get(Calendar.YEAR);
        nMonth = calendar.get(Calendar.MONTH) + 1;
        nDay = calendar.get(Calendar.DAY_OF_MONTH);
        nTime = calendar.get(Calendar.HOUR_OF_DAY);
        nMin = calendar.get(Calendar.MINUTE);
        nSec = calendar.get(Calendar.SECOND);
        String strD = nYear + "-" + nMonth + "-" + nDay + " " + nTime + ":" + nMin + ":" + nSec;
        return strD;
    }
}
