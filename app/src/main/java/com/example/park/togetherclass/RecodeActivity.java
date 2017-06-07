package com.example.park.togetherclass;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class RecodeActivity extends AppCompatActivity {
    ListView l1;
    ArrayList<Record> arrayList = new ArrayList<Record>();
    RecordAdapter adapter;
    String RECORDPATH = getExternalPath() + "TCR";
    MediaPlayer player;
    int playbackPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recode);
        setActionBar();
        init();
        LoadList();
        ListViewMethod();
    }

    void init() {
        l1 = (ListView) findViewById(R.id.recordList);
        adapter = new RecordAdapter(arrayList, this);
        l1.setAdapter(adapter);
    }

    void ListViewMethod() {
        final Boolean[] play = {true};
        l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("BEOM13", play[0] + "");
                if (play[0]) {
                    if (player != null) {
                        player.stop();
                        player.release();
                        player = null;
                    }
                    Toast.makeText(getApplicationContext(), "녹음된 파일을 재생합니다.", Toast.LENGTH_LONG).show();
                    try {
                        player = new MediaPlayer();

                        Log.d("BEOM14", RECORDPATH+ "/" + arrayList.get(position).Name);
                        player.setDataSource(RECORDPATH + "/" + arrayList.get(position).Name);
                        player.prepare();
                        player.start();
                    } catch (Exception e) {
                        Log.e("SampleAudioRecorder", "Audio play failed.", e);
                    }
                    play[0] = false;
                }
        else if (!play[0])
        {
            if (player != null) {
                playbackPosition = player.getCurrentPosition();
                player.pause();
                play[0] = true;
                adapter.setImage(position);
                adapter.notifyDataSetChanged();
            }
        }
    }
}

);
        }

private void playAudio(String url)throws Exception{
        killMediaPlayer();
        player=new MediaPlayer();
        player.setDataSource(url);
        player.prepare();
        player.start();
        }

protected void onDestroy(){
        super.onDestroy();
        killMediaPlayer();
        }

private void killMediaPlayer(){
        if(player!=null){
        try{
        player.release();
        }catch(Exception e){
        e.printStackTrace();
        }
        }

        }

protected void onPause(){
        if(player!=null){
        player.release();
        player=null;
        }

        super.onPause();

        }

        void setActionBar(){
        ActionBar actionBar1=getSupportActionBar();

        actionBar1.setDisplayShowCustomEnabled(true);
        actionBar1.setDisplayHomeAsUpEnabled(false);            //액션바 아이콘을 업 네비게이션 형태로 표시합니다.
        actionBar1.setDisplayShowTitleEnabled(false);        //액션바에 표시되는 제목의 표시유무를 설정합니다.
        actionBar1.setDisplayShowHomeEnabled(false);            //홈 아이콘을 숨김처리합니다.

        actionBar1.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.ActionBar)));

        View view=getLayoutInflater().inflate(R.layout.action_bar,null);
        ImageButton i1=(ImageButton)view.findViewById(R.id.homeBtn);
        i1.setOnClickListener(new View.OnClickListener(){
@Override
public void onClick(View v){
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        }
        });
        actionBar1.setCustomView(view);
        }


        void LoadList(){
        File[]files=new File(RECORDPATH).listFiles();
        String fname="";
        for(File f:files){
        fname=f.getName();
        Record record=new Record(fname);
        arrayList.add(record);
        fname="";
        }
        }

public String getExternalPath(){
        String sdPath="";
        String ext=Environment.getExternalStorageState();
        if(ext.equals(Environment.MEDIA_MOUNTED)){
        sdPath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/";
        Log.d("PATH",sdPath);
        }else{
        sdPath=getFilesDir()+"";
        Toast.makeText(getApplicationContext(),sdPath,Toast.LENGTH_LONG).show();
        }
        return sdPath;
        }
        }
