package com.example.acer.ballgame;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    BallView mAnimView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        /*全屏显示窗口*/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /*强制横屏*/
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        /*显示自定义的游戏view*/
        mAnimView = new BallView(this);
        setContentView(mAnimView);

    }

    public class BallView extends SurfaceView implements Callback,Runnable,SensorEventListener{

        //每50帧刷新一次页面
        public static final int TIME_IN_FRAME = 50;

        //游戏画笔
        Paint mpaint = null;
        Paint mTextPaint = null;
        SurfaceHolder mSurfaceHolder = null;
        //控制游戏更新循环
        boolean mRunning = false;
        //游戏画布
        Canvas mCanvas = null;
        //控制游戏循环
        boolean mIsRunning = false;
        /*SensorManager管理器*/
        private SensorManager mSensorMgr = null;
        Sensor mSensor = null;
        /*手机屏幕宽高*/
        int mScreenWidth = 0;
        int mScreenHeight = 0;
        /*小球资源文件越界区域*/
        private int mScreenBallWidth = 0;
        private int mScreenBallHeight = 0;
        /*游戏背景文件*/
        private Bitmap mbitmapBg;
        /*小球资源文件*/
        private Bitmap mbitmapBall;
        /*小球坐标位置*/
        private float mPosX = 200;
        private float mPosY = 0;
        /* 重力感应X轴，Y轴，Z轴的重力值*/
        private float mGX = 0;
        private float mGY = 0;
        private float mGZ = 0;
        public BallView(Context context){
            super(context);
            /*设置当前view拥有的控制焦点*/
            this.setFocusable(true);
            /*获取SurfaceHolder对象*/
            mSurfaceHolder = this.getHolder();
            /*将mSurfaceHolder添加到Callback回调函数中*/
            mSurfaceHolder.addCallback(this);
            /*创建画布*/
            mCanvas = new Canvas();
            /*创建曲线画笔*/
            mpaint = new Paint();
            mpaint.setColor(Color.WHITE);
            /*加载小球资源*/
            mbitmapBall = BitmapFactory.decodeResource(this.getResources(),R.drawable.ball);
            /*加载游戏背景*/
            mbitmapBg = BitmapFactory.decodeResource(this.getResources(),R.drawable.bg);
            /*获取SensorManager对象*/
            mSensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
            mSensor = mSensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            mSensorMgr.registerListener(this,mSensor,SensorManager.SENSOR_DELAY_GAME);

        }

        private void Draw(){

            /*绘制游戏背景*/
            mCanvas.drawBitmap(mbitmapBg,0,0,mpaint);
            /*绘制小球*/
            mCanvas.drawBitmap(mbitmapBall,mPosX,mPosY,mpaint);
            /*显示X轴，Y轴，Z轴的重力值*/
            mCanvas.drawText("  X轴重力值：" + mGX,0,20,mpaint);
            mCanvas.drawText("  Y轴重力值：" + mGY,0,40,mpaint);
            mCanvas.drawText("  Z轴重力值：" + mGZ,0,60,mpaint);

        }

        public void surfaceChanged(SurfaceHolder holder,int format,int width,int height){}

        public void surfaceCreated(SurfaceHolder holder){

                /*开始游戏主循环线程*/
                mIsRunning = true;
                new Thread(this).start();
                /*得到当前屏幕宽高*/
                mScreenWidth = this.getWidth();
                mScreenHeight = this.getHeight();
                /*得到小球越界区域*/
                mScreenBallWidth = mScreenWidth - mbitmapBall.getWidth();
                mScreenBallHeight = mScreenHeight - mbitmapBall.getHeight();

        }


        public void surfaceDestroyed(SurfaceHolder holder){

                mIsRunning = false;

        }

        public void run(){

                while(mIsRunning){

                    /*取得更新游戏之前的时间*/
                    long startTime = System.currentTimeMillis();
                    /*在这里加上线程安全锁*/
                    synchronized (mSurfaceHolder){

                        /*拿到当前画布，然后锁定*/
                        mCanvas = mSurfaceHolder.lockCanvas();
                        Draw();
                        /*绘制结束后解锁显示在屏幕上*/
                        mSurfaceHolder.unlockCanvasAndPost(mCanvas);

                    }
                    /*取得更新游戏结束的时间*/
                    long endTime = System.currentTimeMillis();
                    /*计算出游戏一次更新的毫秒数*/
                    int diffTime = (int) (endTime - startTime);
                    /*确保每次更新时间为50帧*/
                    while (diffTime <= TIME_IN_FRAME){
                        diffTime = (int) (System.currentTimeMillis() - startTime);
                        /*线程等待*/
                        Thread.yield();
                    }

                }

        }

        public void onAccuracyChanged(Sensor argo, int arg1){}

        public void onSensorChanged(SensorEvent event){

            //获取X,Y,Z3个方向上的重力加速的的改变值
            mGX = event.values[0];
            mGY = event.values[1];
            mGZ = event.values[2];

            //乘以2是为了让小球移动更快
            mPosX += mGX*2;
            mPosY += mGY*2;

            if(mPosX < 0)
                mPosX =0;
            else if(mPosX > mScreenBallWidth)
                mPosX = mScreenBallWidth;
            if(mPosY < 0)
                mPosY = 0;
            else if(mPosY > mScreenBallHeight)
                mPosY = mScreenBallHeight;

        }

    }

}
