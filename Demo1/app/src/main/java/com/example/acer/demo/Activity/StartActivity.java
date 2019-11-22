package com.example.acer.demo.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.acer.demo.R;

public class StartActivity extends AppCompatActivity {

    private static int MAIN = 1;
    private static int WELCOME = 0;

    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    startMain();
                    break;
                case 0:
                    startWelcome();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        SharedPreferences preferences = getSharedPreferences("isFirst",MODE_PRIVATE);
        // 取得相应的值，如果没有该值，说明还未写入，用true作为默认值
        boolean isFirstIn = preferences.getBoolean("isFirstIn", true);

        if (isFirstIn) {
            handler.sendEmptyMessageDelayed(WELCOME,3000);
        } else {
            handler.sendEmptyMessageDelayed(MAIN,3000);
        }


    }

    // 不是第一次打开APP，则直接进入
    private void startMain(){
        startActivity(new Intent(StartActivity.this,MainActivity.class));
        finish();
    }

    // 第一次进入APP则先进入欢迎界面
    private void startWelcome(){
        startActivity(new Intent(StartActivity.this,WelcomeActivity.class));
        finish();
    }

}
