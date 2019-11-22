package com.example.acer.demo.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.acer.demo.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Button backBtn = (Button) findViewById(R.id.layout_help_bn_back);
        backBtn.setOnClickListener(new mClick());
    }

    private class mClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.layout_help_bn_back:
                    finish();
                    break;
            }
        }
    }

}
