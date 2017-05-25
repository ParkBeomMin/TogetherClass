package com.example.park.togetherclass;

import android.content.DialogInterface;
import android.os.Environment;
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
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class HomeWorkActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> arrayList = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    final String MYPATH = getExternalPath() + "TC";

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
            final EditText e1 = (EditText) view.findViewById(R.id.inputSubjectEt);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("과제")
                    .setView(view)
                    .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            try {
                                BufferedWriter bw = new BufferedWriter(new FileWriter(MYPATH +"/"+ e1.getText().toString(), false));
                                bw.write(e1.getText().toString());
                                bw.close();
                                arrayList.add(e1.getText().toString());
                                adapter.notifyDataSetChanged();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    })
                    .setPositiveButton("취소", null)
                    .show();
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
