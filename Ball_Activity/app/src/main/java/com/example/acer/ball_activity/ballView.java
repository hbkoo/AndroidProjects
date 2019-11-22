package com.example.acer.ball_activity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class ballView extends View{

    int x,y,i=0,j=0;

    public ballView (Context context, AttributeSet attrs){

        super(context,attrs);
    }

    void setXY(int _x,int _y){
        x = _x;
        y = _y;

    }

    protected void onDraw(Canvas canvas){

        super.onDraw(canvas);

        //绘制小球
        canvas.drawColor(Color.CYAN);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);    //除去边缘锯齿
        canvas.drawCircle(x,y,50,paint);
        paint.setColor(Color.WHITE);
        canvas.drawCircle(x-6,y-6,3,paint);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(8.0f);
        canvas.drawLine(i,j,x,y,paint);
        i=x;
        j=y;
    }

}