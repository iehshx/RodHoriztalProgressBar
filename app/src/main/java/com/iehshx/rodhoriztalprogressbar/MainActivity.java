package com.iehshx.rodhoriztalprogressbar;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private Handler handler;
    private RodHoriztalProgressBar pb;
    private int currentProgress = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                pb.setProgress(currentProgress+=5);
            }
        };
        setContentView(R.layout.activity_main);
        pb = (RodHoriztalProgressBar) findViewById(R.id.mine_credit_pb_credit);
        pb.setProgress(25);
        start();
    }

    private void start() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                while (currentProgress<=100) {
                    handler.sendEmptyMessage(0);
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
