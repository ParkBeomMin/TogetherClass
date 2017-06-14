package com.example.park.togetherclass;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class InfoActivity extends AppCompatActivity {
    String Name, Nick, Time;
    TextView t1;
    ListView listView;
    String MYPATH = getExternalPath() + "TC_Time";
    ArrayList<Info> arrayList = new ArrayList<Info>();
    InfoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        setActionBar();
        init();
        ListViewMethod();
    }

    void init() {
        SharedPreferences info = getSharedPreferences("info", Activity.MODE_PRIVATE);
//        Intent intent = getIntent();
        Name = info.getString("Name", null);
        Nick = info.getString("Nick", null);
        Time = info.getString("Time", null);
        Log.d("BEOM8", "infoActivity : " + Name + Nick + Time);
        makeDir();
        WriteRead();
        t1 = (TextView) findViewById(R.id.helloTv);
        t1.setText("안녕하세요!!" + Nick + "(" + Name + ")" + "님");
        listView = (ListView) findViewById(R.id.ConnectionRecordList);
        adapter = new InfoAdapter(arrayList, getApplication());
        listView.setAdapter(adapter);
    }

    void ListViewMethod() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int position = i;
                AlertDialog.Builder builder = new AlertDialog.Builder(InfoActivity.this);
                builder.setTitle("삭제")
                        .setMessage("삭제하시겠습니까?")
                        .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                File file = new File(MYPATH + "/" + arrayList.get(position).Time);
                                file.delete();
                                arrayList.remove(position);
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setPositiveButton("취소", null)
                        .show();
                return false;
            }
        });
    }

    void WriteRead() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(MYPATH + "/" + Time, false));
            bw.write(Nick + "(" + Name + ")");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(MYPATH + "/" + Time));
            String readStr = "";
            String str = null;
            while ((str = br.readLine()) != null) {
                readStr += str + "\n";
            }
            Info info = new Info(readStr, Time);
            arrayList.add(info);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void makeDir() {
        File file = new File(MYPATH);
        file.mkdir();
        String msg = "디렉터리생성";

        if (file.isDirectory() == false)
            msg = "디렉터리 생성오류";
        else
            LoadList();
        Log.d("MakeDir", msg);
    }

    void LoadList() {
        File[] files = new File(MYPATH).listFiles();
        String fname = "";
        for (File f : files) {
            fname = f.getName();
            Log.d("BEOM6", fname);
            try {
                BufferedReader br = new BufferedReader(new FileReader(MYPATH + "/" + fname));
                String readStr = "";
                String str = null;
                while ((str = br.readLine()) != null) {
                    readStr += str + "\n";
                }
                br.close();
                Info info = new Info(readStr, fname);
                arrayList.add(info);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            fname = "";
        }
    }

    public String getExternalPath() {
        String sdPath = "";
        String ext = Environment.getExternalStorageState();
        if (ext.equals(Environment.MEDIA_MOUNTED)) {
            sdPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
            Log.d("PATH", sdPath);
        } else {
            sdPath = getFilesDir() + "";
            Toast.makeText(getApplicationContext(), sdPath, Toast.LENGTH_LONG).show();
        }
        return sdPath;
    }

    void setActionBar() {
        ActionBar actionBar1 = getSupportActionBar();

        actionBar1.setDisplayShowCustomEnabled(true);
        actionBar1.setDisplayHomeAsUpEnabled(false);            //액션바 아이콘을 업 네비게이션 형태로 표시합니다.
        actionBar1.setDisplayShowTitleEnabled(false);        //액션바에 표시되는 제목의 표시유무를 설정합니다.
        actionBar1.setDisplayShowHomeEnabled(false);            //홈 아이콘을 숨김처리합니다.

        actionBar1.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.ActionBar)));

        View view = getLayoutInflater().inflate(R.layout.action_bar, null);
        ImageButton i1 = (ImageButton) view.findViewById(R.id.homeBtn);
        i1.setVisibility(View.INVISIBLE);
//        i1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
        actionBar1.setCustomView(view);
    }

//    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//        startActivity(intent);
        finish();
    }
}
