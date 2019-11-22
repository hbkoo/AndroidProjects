package com.example.acer.demo.Tools;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.acer.demo.R;

/**
 * 自定义布局控件
 */

public class TopBarLayout extends RelativeLayout {

    private Button leftBtn,rightBtn;
    private TextView title;

//    public TopBarLayout(Context context) {
//        this(context,null);
//    }
//
//    public TopBarLayout(Context context, AttributeSet attrs) {
//        this(context, attrs,0);
//    }

    public TopBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.top_bar,this,true);

        leftBtn = (Button) findViewById(R.id.top_bar_left_btn);
        rightBtn = (Button) findViewById(R.id.top_bar_right_btn);
        title = (TextView) findViewById(R.id.top_bar_title);

        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.CustomTopBar);

        Boolean leftVisible = typedArray.getBoolean(R.styleable.CustomTopBar_left_btn_visible,true);
        if (leftVisible) {
            leftBtn.setVisibility(VISIBLE);
        } else {
            leftBtn.setVisibility(INVISIBLE);
        }

        leftBtn.setTextColor(typedArray.getColor(R.styleable.CustomTopBar_left_button_color, Color.BLACK));
        rightBtn.setTextColor(typedArray.getColor(R.styleable.CustomTopBar_right_button_color,Color.BLACK));
        title.setTextColor(typedArray.getColor(R.styleable.CustomTopBar_title_color,Color.BLACK));
        title.setText(typedArray.getString(R.styleable.CustomTopBar_title_text));

        Boolean rightVisible = typedArray.getBoolean(R.styleable.CustomTopBar_right_btn_visible,true);
        if (rightVisible) {
            rightBtn.setVisibility(VISIBLE);
        } else {
            rightBtn.setVisibility(INVISIBLE);
        }

        typedArray.recycle();


    }
}
