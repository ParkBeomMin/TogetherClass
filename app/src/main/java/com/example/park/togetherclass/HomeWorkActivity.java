package com.example.park.togetherclass;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
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
import java.util.Calendar;
import java.util.Collections;

public class HomeWorkActivity extends AppCompatActivity {
    EditText e1;
    RadioButton r1, r2, r3;
    Button DdayBtn, cancel, confirm;
    ListView listView;
    ArrayList<HomeWork> homeWorkArrayList = new ArrayList<HomeWork>();
    HomeWorkAdapter homeWorkAdapter;
    final String MYPATH = getExternalPath() + "TC";
    int PickYear, PickMonth, PickDay;
    int todayYear = Calendar.getInstance().get(Calendar.YEAR);
    int todayMonth = (Calendar.getInstance().get(Calendar.MONTH));
    int todayDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    String WriteData;
    String Name, Nick;
    Button b1, b2, b3;
    HorizontalScrollView s1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_work);
        setActionBar();
        init();
        ListViewMethod();
        homeWorkAdapter.setSort(0);
    }

    void init() {
        b1 = (Button) findViewById(R.id.GHomebtn);
        b2 = (Button) findViewById(R.id.GFreebtn);
        b3 = (Button) findViewById(R.id.GMeetbtn);
        b1.setBackground(new ColorDrawable(getResources().getColor(R.color.ActionBar)));
        b1.setTextColor(getResources().getColor(R.color.White));
        s1 = (HorizontalScrollView) findViewById(R.id.scrollView);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                s1.smoothScrollBy(700, 0);
            }
        }, 200);
        makeDir();
        listView = (ListView) findViewById(R.id.HomeWorkListView);
        homeWorkAdapter = new HomeWorkAdapter(homeWorkArrayList, getApplication());
        listView.setAdapter(homeWorkAdapter);

        SharedPreferences info = getSharedPreferences("info", Activity.MODE_PRIVATE);
        Nick = info.getString("Nick", null);
        if (Nick.contains("교수님")) {
            b3.setVisibility(View.GONE);
            b2.setVisibility(View.GONE);
        }
    }

    void init2(View view) {
        e1 = (EditText) view.findViewById(R.id.inputSubjectEt);
        r1 = (RadioButton) view.findViewById(R.id.r1);
        r2 = (RadioButton) view.findViewById(R.id.r2);
        r3 = (RadioButton) view.findViewById(R.id.r3);

        cancel = (Button) view.findViewById(R.id.cancelBtn);
        confirm = (Button) view.findViewById(R.id.confirmBtn);
        DdayBtn = (Button) view.findViewById(R.id.DdayBtn);
        DdayBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(HomeWorkActivity.this, listener,
                        todayYear, todayMonth, todayDay); //현재날짜로 설정.
                datePickerDialog.show();
            }
        });
    }


    void ListViewMethod() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, int i, long l) {
                final int position = i;
                View v = getLayoutInflater().inflate(R.layout.delete_free_list, null);
                TextView title = (TextView) v.findViewById(R.id.deleteNickTv);
                title.setText("삭제 하시겠습니까?");
                title.setTextColor(Color.RED);
                title.setPadding(0, 30, 0, 10);
                title.setGravity(Gravity.CENTER);
                EditText e1 = (EditText) v.findViewById(R.id.deletePwEt);
                e1.setVisibility(View.GONE);
                Button cancel = (Button) v.findViewById(R.id.cancelBtn);
                Button confirm = (Button) v.findViewById(R.id.confirmBtn);

                View titleView = getLayoutInflater().inflate(R.layout.add_title, null);
                TextView t1 = (TextView) titleView.findViewById(R.id.addtitleTv);
                t1.setText("삭제");
                final AlertDialog alertDialog = new AlertDialog.Builder(HomeWorkActivity.this).create();
                alertDialog.setCustomTitle(titleView);
                alertDialog.setView(v);
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
                        File file = new File(MYPATH + "/" + homeWorkArrayList.get(position).Name);
                        file.delete();
                        homeWorkArrayList.remove(position);
                        homeWorkAdapter.notifyDataSetChanged();
                        alertDialog.dismiss();
                    }
                });
                return false;
            }
        });
    }

    public void MyOnClick(View v) {
        Intent intent;
        if (v.getId() == R.id.HomeWorkAddBtn) {
            View titleView = getLayoutInflater().inflate(R.layout.add_title, null);
            TextView t1 = (TextView) titleView.findViewById(R.id.addtitleTv);
            t1.setText("일정");
            View view = View.inflate(this, R.layout.homework_add, null);
            init2(view);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final AlertDialog alertDialog;
            alertDialog = new AlertDialog.Builder(this).create();
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
                    String M = PickMonth + "";
                    if (PickMonth < 10) {
                        M = "0" + M;
                    }
                    String D = PickDay + "";
                    if (PickDay < 10) {
                        D = "0" + D;
                    }
                    try {
                        WriteData = e1.getText().toString() + "\n" + RadioCheck() + "\n" + PickYear + "-" + M + "-" + D + "\n";
                        BufferedWriter bw = new BufferedWriter(new FileWriter(MYPATH + "/" + e1.getText().toString(), false));
                        bw.write(WriteData);
                        bw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        BufferedReader br = new BufferedReader(new FileReader(MYPATH + "/" + e1.getText().toString()));
                        String readStr = "";
                        String str = null;
                        while ((str = br.readLine()) != null) {
                            readStr += str + "\n";
                        }
                        br.close();
                        String s[] = readStr.split("\n");
                        HomeWork homeWork = new HomeWork(s[0], s[1], s[2]);
                        Log.d("BEOM5", s[0] + s[1] + s[2]);
                        for(int i = 0; i < homeWorkArrayList.size(); i++) {
                            if(s[0].equals(homeWorkArrayList.get(i).Name)) {
                                homeWorkArrayList.remove(i);
                                Toast.makeText(getApplicationContext(), "중복된 일정은 수정됩니다.", Toast.LENGTH_LONG).show();
                            }
                        }
                        homeWorkArrayList.add(homeWork);
                        homeWorkAdapter.setSort(0);
                        homeWorkAdapter.notifyDataSetChanged();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    alertDialog.dismiss();

                }
            });
        } else if (v.getId() == R.id.GFreebtn) {
            intent = new Intent(HomeWorkActivity.this, FreeBoardActivity.class);
            startActivity(intent);
            finish();
        } else if (v.getId() == R.id.GClassbtn) {
            intent = new Intent(HomeWorkActivity.this, ClassHourActivity.class);
            startActivity(intent);
            finish();
        } else if (v.getId() == R.id.GMeetbtn) {
            intent = new Intent(HomeWorkActivity.this, MeetingActivity.class);
            startActivity(intent);
            finish();
        } else if (v.getId() == R.id.GNoticebtn) {
            intent = new Intent(HomeWorkActivity.this, NoticeActivity.class);
            startActivity(intent);
            finish();
        } else if (v.getId() == R.id.GPotalbtn) {
            intent = new Intent(HomeWorkActivity.this, PotalActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) { // 선택한 날짜
            Toast.makeText(getApplicationContext(), year + "년" + (month + 1) + "월" + dayOfMonth + "일", Toast.LENGTH_LONG).show();
            PickYear = year;
            PickMonth = month;

            PickDay = dayOfMonth;
        }
    };

    String RadioCheck() {
        String s = "과제";
        if (r2.isChecked()) {
            s = "공모전";
        } else if (r3.isChecked()) {
            s = "개인일정";
        }
        return s;
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
                String s[] = readStr.split("\n");
                HomeWork homeWork = new HomeWork(s[0], s[1], s[2]);
                homeWorkArrayList.add(homeWork);
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
        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);finish();
            }
        });
        actionBar1.setCustomView(view);
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
/*
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
                        LoadList();
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
    };*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}
