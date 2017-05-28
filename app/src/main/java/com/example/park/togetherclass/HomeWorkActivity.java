package com.example.park.togetherclass;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
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
    Button DdayBtn;
    ListView listView;
    ArrayList<String> arrayList = new ArrayList<String>();
    ArrayList<HomeWork> homeWorkArrayList = new ArrayList<HomeWork>();
    ArrayAdapter<String> adapter;
    final String MYPATH = getExternalPath() + "TC";
    int PickYear, PickMonth, PickDay;
    int todayYear = Calendar.getInstance().get(Calendar.YEAR);
    int todayMonth = (Calendar.getInstance().get(Calendar.MONTH));
    int todayDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    String WriteData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_work);
        init();
        ListViewMethod();
    }

    void init() {
        makeDir();
        listView = (ListView) findViewById(R.id.HomeWorkListView);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);
    }

    void init2(View view) {
        e1 = (EditText) view.findViewById(R.id.inputSubjectEt);
        r1 = (RadioButton) view.findViewById(R.id.r1);
        r2 = (RadioButton) view.findViewById(R.id.r2);
        r3 = (RadioButton) view.findViewById(R.id.r3);
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
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int position = i;
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeWorkActivity.this);
                builder.setTitle("삭제")
                        .setMessage("삭제하시겠습니까?")
                        .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                File file = new File(MYPATH + "/" + arrayList.get(position).toString());
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

    public void MyOnClick(View v) {
        if (v.getId() == R.id.HomeWorkAddBtn) {
            View view = View.inflate(this, R.layout.homework_add, null);
            init2(view);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("과제")
                    .setView(view)
                    .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            try {
                                WriteData = e1.getText().toString() + "\n" + RadioCheck() + "\n" + PickYear + PickMonth + PickDay + "\n";
                                BufferedWriter bw = new BufferedWriter(new FileWriter(MYPATH + "/" + e1.getText().toString(), false));
                                bw.write(WriteData);
                                bw.close();
//                                arrayList.add(e1.getText().toString());
//                                adapter.notifyDataSetChanged();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            try {
                                BufferedReader br = new BufferedReader(new FileReader(MYPATH + "/" + e1.getText().toString()));
                                String readStr = "";
                                String str = null;
                                while((str = br.readLine()) != null) {
                                    readStr += str + "\n";
                                }
                                br.close();
                                String s[] = readStr.split("\n");
                                HomeWork homeWork = new HomeWork(s[0],s[1],s[2]);
                                Toast.makeText(HomeWorkActivity.this, readStr, Toast.LENGTH_LONG).show();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    })
                    .setPositiveButton("취소", null)
                    .show();
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
        String str = "";
        for (File f : files) {
            str = f.getName();
            arrayList.add(str);
            str = "";
        }
//        Collections.sort(arrayList, comparator);
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
}
