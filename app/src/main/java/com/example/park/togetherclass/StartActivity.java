package com.example.park.togetherclass;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {
    ProgressBar progressBar;
TextView t1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        t1 = (TextView) findViewById(R.id.percent);
        startProgressBarThread();
//        Handler handler = new Handler() {
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//
//            }
//        };
//        handler.sendEmptyMessageDelayed(0, 1500);
    }

    private volatile Thread theProgressBarThread;
    public int CurrentPosition = 0;

    public synchronized void startProgressBarThread() {
        if (theProgressBarThread == null) {
            theProgressBarThread = new Thread(null, backgroundThread, "startProgressBarThread");
            CurrentPosition = 0;
            theProgressBarThread.start();
        }
    }

    public synchronized void stopProgressBarThread() {
        if (theProgressBarThread != null) {
            Thread tmpThread = theProgressBarThread;
            theProgressBarThread = null;
            tmpThread.interrupt();
        }
    }

    private Runnable backgroundThread = new Runnable() {
        @Override
        public void run() {
            if (Thread.currentThread() == theProgressBarThread) {
                CurrentPosition = 0;
                final int total = 100;
                while (CurrentPosition < total) {
                    try {
                        progressBarHandle.sendMessage(progressBarHandle.obtainMessage());
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };

        Handler progressBarHandle = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                CurrentPosition++;
                progressBar.setProgress(CurrentPosition);
                t1.setText(CurrentPosition + "%");
                if (CurrentPosition == 100) {
                    stopProgressBarThread();
                    Intent registerIntent = new Intent(StartActivity.this, LoginActivity.class);
                    startActivity(registerIntent);
                    finish();
                }
            }
        };

    }
