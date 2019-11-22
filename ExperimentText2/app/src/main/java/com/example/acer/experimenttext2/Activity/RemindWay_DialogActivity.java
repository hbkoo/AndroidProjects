package com.example.acer.experimenttext2.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import com.example.acer.experimenttext2.R;

public class RemindWay_DialogActivity extends AppCompatActivity {

    private Button OK_Remind,exit_Remind;
    private RadioButton mail_remind,message_remind,notification_remind;
    private EditText remind_EditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind_way__dialog);
        mail_remind = (RadioButton) findViewById(R.id.Mail_Remind);
        message_remind = (RadioButton) findViewById(R.id.Message_Remind);
        notification_remind = (RadioButton) findViewById(R.id.Notification_Remind);
        remind_EditText = (EditText) findViewById(R.id.remind_EditText);
        OK_Remind = (Button) findViewById(R.id.OKDialog_button);
        exit_Remind = (Button) findViewById(R.id.exitDialog_button);

//        ActionBar actionBar = getSupportActionBar();
//        if(actionBar != null){
//            actionBar.setTitle("提示设置");
//        }

        //TODO 提示类型实现
        OK_Remind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        exit_Remind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
