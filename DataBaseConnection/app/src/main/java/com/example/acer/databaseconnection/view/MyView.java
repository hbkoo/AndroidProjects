package com.example.acer.databaseconnection.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by acer on 2017/9/10.
 */

public class MyView extends View {

    private float circleX = 40;
    private float circleY = 50;
    private float circleR = 50;
    private Paint paint = new Paint();

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.d("MyView","onDraw");



        paint.setColor(Color.RED);
        canvas.drawCircle(circleX,circleY,circleR,paint);

    }


    public void setCircleX(float circleX) {
        this.circleX = circleX;
    }

    public void setCircleY(float circleY) {
        this.circleY = circleY;
    }

    public void setCircleR(float circleR) {
        this.circleR = circleR;
    }

    public float getCircleX() {

        return circleX;
    }

    public float getCircleY() {
        return circleY;
    }

    public float getCircleR() {
        return circleR;
    }
}
