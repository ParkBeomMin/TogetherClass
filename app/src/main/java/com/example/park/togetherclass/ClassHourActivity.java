package com.example.park.togetherclass;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Scanner;

public class ClassHourActivity extends AppCompatActivity {
    String Name, Nick, Pw;
    HorizontalScrollView s1;
    Button b1, b2, b3, b4, b5, b6;
    EditText e1;
    ArrayList<ClassHour> arrayList = new ArrayList<ClassHour>();
    ClassHourAdapter adapter;
    ListView l1;
    String Sign = "";
//    final String RECORDED_FILE = getExternalPath() + "TCR";
//    MediaRecorder recorder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_hour);
        setActionBar();
//        checkPermission2();
//        makeDir();
        init();
        ListViewMethod();
        new BackgroundTask().execute();
        if (Nick.contains("교수님")) {
            myThread.start();
            b3.setText("수업 종료");
            b4.setVisibility(View.GONE);
            b5.setVisibility(View.GONE);
            b6.setVisibility(View.GONE);
            e1.setVisibility(View.GONE);
        }
    }

    void init() {
        b3 = (Button) findViewById(R.id.understandingButton);
        b2 = (Button) findViewById(R.id.recodeBtn);
        b4 = (Button) findViewById(R.id.addQuestionButton);
        b5 = (Button) findViewById(R.id.GFreebtn);
        b6 = (Button) findViewById(R.id.GMeetbtn);
        SharedPreferences info = getSharedPreferences("info", Activity.MODE_PRIVATE);
        Name = info.getString("Name", null);
        Nick = info.getString("Nick", null);
        Pw = info.getString("Pw", null);
        Log.d("BEOM10", Nick);
        l1 = (ListView) findViewById(R.id.questionList);
        adapter = new ClassHourAdapter(getApplicationContext(), arrayList);
        l1.setAdapter(adapter);
        e1 = (EditText) findViewById(R.id.questionEt);
        b1 = (Button) findViewById(R.id.GClassbtn);
        b1.setEnabled(true);
        b1.setBackground(new ColorDrawable(getResources().getColor(R.color.ActionBar)));
        b1.setTextColor(getResources().getColor(R.color.White));
        s1 = (HorizontalScrollView) findViewById(R.id.scrollView);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                s1.smoothScrollBy(0, 0);
            }
        }, 200);
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

    void ListViewMethod() {
        l1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final String content = arrayList.get(position).Content;
                final String nick = arrayList.get(position).Nick;
                final String date = arrayList.get(position).Date;
                final String pw = arrayList.get(position).Pw;
                final int pos = position;
                LayoutInflater inflater = getLayoutInflater();
                View titleView = inflater.inflate(R.layout.add_title, null);
                TextView title = (TextView) titleView.findViewById(R.id.addtitleTv);
                title.setText("삭제");
                View deleteView = inflater.inflate(R.layout.delete_free_list, null);
                Button cancel = (Button) deleteView.findViewById(R.id.cancelBtn);
                Button confirm = (Button) deleteView.findViewById(R.id.confirmBtn);
                final TextView t1 = (TextView) deleteView.findViewById(R.id.deleteNickTv);
                final EditText e1 = (EditText) deleteView.findViewById(R.id.deletePwEt);
                t1.setText("작성자 : " + nick + "님");
                final AlertDialog alertDialog = new AlertDialog.Builder(ClassHourActivity.this).create();
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
                                    if (pw.equals(e1.getText().toString())) {
                                        success = true;
                                    }
                                    if (success) {
                                        Toast.makeText(getApplicationContext(), "삭제되었습니다.", Toast.LENGTH_LONG).show();
                                        adapter.notifyDataSetChanged();
                                        arrayList.remove(pos);
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
                        ClassDeleteRequest deleteRequest = new ClassDeleteRequest(content, nick, e1.getText().toString(), date, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(ClassHourActivity.this);
                        queue.add(deleteRequest);
                        alertDialog.dismiss();
                    }
                });
                return true;
            }
        });
    }

    public void MyOnClick(View v) {
        Intent intent;
        if (v.getId() == R.id.GFreebtn) {
            intent = new Intent(ClassHourActivity.this, FreeBoardActivity.class);
            startActivity(intent);
            finish();
        } else if (v.getId() == R.id.GHomebtn) {
            intent = new Intent(ClassHourActivity.this, HomeWorkActivity.class);
            startActivity(intent);
            finish();
        } else if (v.getId() == R.id.GMeetbtn) {
            intent = new Intent(ClassHourActivity.this, MeetingActivity.class);
            startActivity(intent);
            finish();
        } else if (v.getId() == R.id.GNoticebtn) {
            intent = new Intent(ClassHourActivity.this, NoticeActivity.class);
            startActivity(intent);
            finish();
        } else if (v.getId() == R.id.GPotalbtn) {
            intent = new Intent(ClassHourActivity.this, PotalActivity.class);
            startActivity(intent);
            finish();
        } else if (v.getId() == R.id.understandingButton) {
            if(Nick.contains("교수님")) {
                Toast.makeText(getApplicationContext(), "질문내용이 초기화 됩니다.", Toast.LENGTH_LONG).show();
                Response.Listener<String> responseListener1 = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                    }
                };
                ClassAllDeleteRequest deleteRequest = new ClassAllDeleteRequest(responseListener1);
                RequestQueue queue1 = Volley.newRequestQueue(ClassHourActivity.this);
                queue1.add(deleteRequest);
            }
            else {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "!", Toast.LENGTH_LONG).show();
                    }
                };
                SendSignRequest write = new SendSignRequest("1", responseListener);
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(write);
            }

        } else if (v.getId() == R.id.addQuestionButton) {
            String content = e1.getText().toString();
            String Date = doCurrentDate();
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(getApplicationContext(), "게시되었습니다.", Toast.LENGTH_LONG).show();
                    new BackgroundTask().execute();
                }
            };
            ClassWriteRequest write = new ClassWriteRequest(content, Nick, Pw, Date, responseListener);
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            queue.add(write);
            e1.setText("");
        }
        /*else if (v.getId() == R.id.recodeBtn) {
            if (b2.getText().toString().equals("녹음하기")) {
//                Intent intent11 = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
//                startActivity(intent11);

                String recodeDate = doCurrentDate();
//                if (recorder != null) {
//                    recorder.stop();
//                    recorder.release();
//                    recorder = null;
//                }// TODO Auto-generated method stub
                if (recorder == null) {
                    recorder = new MediaRecorder();
                } else {
                    recorder.reset();
                }
//                recorder = new MediaRecorder();
                recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                recorder.setOutputFile(RECORDED_FILE + "/" + recodeDate + ".3gp");
                Log.d("BEOM13", RECORDED_FILE + "/" + recodeDate + ".3gp");
                try {
                    Toast.makeText(getApplicationContext(),
                            "녹음을 시작합니다.", Toast.LENGTH_LONG).show();
                    recorder.prepare();
                    recorder.start();
                } catch (Exception ex) {
                    Log.e("SampleAudioRecorder", "Exception : ", ex);
                }
                b2.setText("녹음중단");
            } else {
                if (recorder == null)
                    return;

                recorder.stop();
                recorder.release();
                recorder = null;

                Toast.makeText(getApplicationContext(),
                        "녹음이 중지되었습니다.", Toast.LENGTH_LONG).show();
                // TODO Auto-generated method stub
                b2.setText("녹음하기");

            }
        }*/
    }

    void Push() {
        if (Nick.contains("교수님")) {
            Log.d("BEOM11", "Before : " + Sign);
            new BackgroundTask1().execute();
            Log.d("BEOM11", "After : " + Sign);
            //받아오기
            if (Sign.equals("1")) {
                NotificationManager notificationManager = (NotificationManager) ClassHourActivity.this.getSystemService(ClassHourActivity.this.NOTIFICATION_SERVICE);
                Intent intent1 = new Intent(ClassHourActivity.this.getApplicationContext(), ClassHourActivity.class); //인텐트 생성.
                Notification.Builder builder = new Notification.Builder(getApplicationContext());
                intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);//현재 액티비티를 최상으로 올리고, 최상의 액티비티를 제외한 모든 액티비티를 없앤다.
                PendingIntent pendingNotificationIntent = PendingIntent.getActivity(ClassHourActivity.this, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);

                builder.setSmallIcon(R.drawable.title).setTicker("함수").setWhen(System.currentTimeMillis())
                        .setNumber(1).setContentTitle("한번 더 설명해주세요!").setContentText("한번 더 설명해주세요!")
                        .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE).setContentIntent(pendingNotificationIntent).setAutoCancel(true).setOngoing(true);
                //해당 부분은 API 4.1버전부터 작동합니다.

                notificationManager.notify(1, builder.build());

                Response.Listener<String> responseListener1 = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                    }
                };
                DeleteSignRequest deleteRequest = new DeleteSignRequest(Sign, responseListener1);
                RequestQueue queue1 = Volley.newRequestQueue(ClassHourActivity.this);
                queue1.add(deleteRequest);
            }
        } else {
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "내 정보");
//        if (!Nick.contains("교수님"))
//            menu.add(0, 2, 0, "녹음목록");
        menu.add(0, 3, 0, "로그아웃");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 1) {
            Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
            startActivity(intent);
//            finish();
        } else if (item.getItemId() == 3) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.putExtra("Logout", "Logout");
            SharedPreferences info = getSharedPreferences("info", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = info.edit();
            editor.clear();
            editor.commit();
            startActivity(intent);
            finish();
        } else if (item.getItemId() == 2) {
            Intent intent = new Intent(getApplicationContext(), RecodeActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
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

    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            target = "http://pkr10.cafe24.com/ClassList.php";
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
                String ClassContent, ClassNick, ClassDate, ClassPw;
                while (count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    ClassContent = object.getString("ClassContent");
                    ClassNick = object.getString("ClassNick");
                    ClassDate = object.getString("ClassDate");
                    ClassPw = object.getString("ClassPw");
                    ClassHour classHour = new ClassHour(ClassContent, ClassNick, ClassPw, ClassDate);
                    arrayList.add(classHour);
                    count++;
                }
                adapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    class BackgroundTask1 extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            target = "http://pkr10.cafe24.com/Sign.php";
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
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                while (count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    Sign = object.getString("SIGN");
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (Nick.contains("교수님")) {
            Toast.makeText(getApplicationContext(), "질문내용이 초기화 됩니다.", Toast.LENGTH_LONG).show();
            Response.Listener<String> responseListener1 = new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                }
            };
            ClassAllDeleteRequest deleteRequest = new ClassAllDeleteRequest(responseListener1);
            RequestQueue queue1 = Volley.newRequestQueue(ClassHourActivity.this);
            queue1.add(deleteRequest);
        }
        Intent intent = new Intent(ClassHourActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    int time = 99999;
    Handler myHandler = new Handler();
    Thread myThread = new Thread() {
        @Override
        public void run() {
            super.run();
            while (time > 0) {
                myHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        new BackgroundTask().execute();
                        Push();
                        time--;
                    }
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            time = 99999;
        }
    };

//    void makeDir() {
//        File file = new File(RECORDED_FILE);
//        file.mkdir();
//        String msg = "디렉터리생성";
//
//        if (file.isDirectory() == false)
//            msg = "디렉터리 생성오류";
//        Log.d("MakeDir", msg);
//    }
//
//    public String getExternalPath() {
//        String sdPath = "";
//        String ext = Environment.getExternalStorageState();
//        if (ext.equals(Environment.MEDIA_MOUNTED)) {
//            sdPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
//            Log.d("PATH", sdPath);
//        } else {
//            sdPath = getFilesDir() + "";
//            Toast.makeText(getApplicationContext(), sdPath, Toast.LENGTH_LONG).show();
//        }
//        return sdPath;
//    }
//
//    void checkPermission2() {
//
//        int permissionCheck_RECORD = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
//        if (permissionCheck_RECORD == PackageManager.PERMISSION_GRANTED) {
//        } else {
//            Toast.makeText(getApplicationContext(),
//                    "허용을 눌러야 정상적인 앱 실행이 가능합니다.", Toast.LENGTH_SHORT).show();
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.RECORD_AUDIO},
//                    100);
//        }
//    }
}
