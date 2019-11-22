package com.example.acer.camera;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class TextActivity extends AppCompatActivity {

    private boolean isTask = false;

    private Button start,stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        start = (Button) findViewById(R.id.start);
        stop = (Button) findViewById(R.id.stop);

        Start();

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isTask = false;
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isTask = true;
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        });

    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Toast.makeText(TextActivity.this,"开始",Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(TextActivity.this,"结束",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private void Start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isTask) {
                    //放慢速度
                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Message message = new Message();
                    message.what = 0;
                    handler.sendMessage(message);

//                    try {
//                        Thread.sleep(17000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }
            }
        }).start();
    }
}
