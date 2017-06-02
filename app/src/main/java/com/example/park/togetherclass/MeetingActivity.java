package com.example.park.togetherclass;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);
        init();
        new BackgroundTask().execute();
        GridViewMethod();
    }

    void init() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MeetingActivity.this);
        builder.setTitle("메일보내기 & 사이트 접속하기")
                .setMessage("짧게 클릭 시 메일보내기\n" +
                        "길게 클릭 시 사이트 접속")
                .setNegativeButton("확인", null)
                .show();
        g1 = (GridView) findViewById(R.id.meetingList);
        adapter = new MeetAdapter(arrayList, getApplication());
        g1.setAdapter(adapter);
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
                if(site.equals("-")){
                    Toast.makeText(getApplicationContext(), "사이트가 존재하지 않습니다.", Toast.LENGTH_LONG).show();
                }
                else {
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

//
//
//    class myAsyncTask extends AsyncTask<Void, Void, Void> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//        @Override
//        protected Void doInBackground(Void... params) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(MeetingActivity.this);
//            builder.setTitle("메일보내기 & 사이트 접속하기")
//                    .setMessage("짧게 클릭 시 메일보내기\n" +
//                    "길게 클릭 시 사이트 접속")
//            .show();
//            try {
//                Thread.sleep(3000);
//                builder.
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//        }
//
//        @Override
//        protected void onProgressUpdate(Void... values) {
//            super.onProgressUpdate(values);
//        }
//    }
}
