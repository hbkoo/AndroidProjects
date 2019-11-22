package com.example.acer.ball_activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {


    int i =80, j = 10, step;
    ballView view;
    Button btn1,btn2,btn3;
    mHandler handler;
    mThread thread;
    boolean STOP = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = (ballView) findViewById(R.id.view1);
        view.setOnTouchListener(new mOnTouch());
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn1.setOnClickListener(new mClick());
        btn2.setOnClickListener(new mClick());
        btn3.setOnClickListener(new mClick());
        handler = new mHandler();
        thread = new mThread();
        view.setXY(i,j);

    }

    class mOnTouch implements View.OnTouchListener{

        public boolean onTouch(View v, MotionEvent event){

            if(event.getAction() == MotionEvent.ACTION_MOVE){
                i = (int) event.getX();
                j = (int) event.getY();
                view.setXY(i,j);
                view.invalidate();
            }

            if(event.getAction() == MotionEvent.ACTION_DOWN){

                i = (int) event.getX();
                j = (int) event.getY();
                view.setXY(i,j);
                view.invalidate();

            }

            return true;

        }

    }

    class mClick implements View.OnClickListener {
        public void onClick(View argo){
            if(argo == btn1){
                STOP = false;
                thread.start();
            }
            else if(argo == btn3){

                i = 80;
                j = 10;
                step = 0;
                view.setXY(i,j);
                view.invalidate();
                STOP = true;
            }
            else if(argo == btn2){
                STOP = true;
            }


        }
    }

    private class mHandler extends Handler {

        public void handleMessage(Message msg){
            switch (msg.arg1){
                case 1:{
                    step = step+5;
                    j = j + step;

                    //设置停止线程中循环的标志位
//                    if(j>220)
//                        STOP = true;

                    break;
                }
            }


            //更新小球的坐标位置
            view.setXY(i,j);  //设置小球位置
            view.invalidate(); //刷新画布页面

        }
    }

    private class mThread extends Thread{

        public void run(){

            while(!STOP){

                try{
                    Thread.sleep(500);//延时0.5秒
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }

                //发送消息
                Message msg = new Message();
                msg.arg1 = 1; //设置标志信息
                handler.sendMessage(msg);

            }

        }
    }

}
