package com.example.acer.experimenttext2.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.acer.experimenttext2.R;
import com.example.acer.experimenttext2.Class.StuLeave;

public class StuLeaveDialogActivity extends AppCompatActivity {

    private TextView leaveName,leaveClass,leaveID,leaveTelephone,leaveReason;
    private Button exit_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_leave_dialog);
        exit_button = (Button) findViewById(R.id.exit_leave_button);
        leaveName = (TextView) findViewById(R.id.leaveNameDialog_TextView);
        leaveClass = (TextView) findViewById(R.id.leaveClassDialog_TextView);
        leaveID = (TextView) findViewById(R.id.leaveIDDialog_TextView);
        leaveTelephone = (TextView) findViewById(R.id.leaveTelephoneDialog_TextView);
        leaveReason = (TextView) findViewById(R.id.leaveReasonDialog_TextView);
        leaveReason.setMovementMethod(ScrollingMovementMethod.getInstance());//当输入内容过多时可以滚动查看

        StuLeave stu_leave_data = (StuLeave) getIntent().getExtras().getParcelable("data");
        leaveName.setText(stu_leave_data.getName());
        leaveClass.setText(stu_leave_data.getClass_());
        leaveID.setText(stu_leave_data.getNumber());
        leaveTelephone.setText(stu_leave_data.getTelephone());
        leaveReason.setText(stu_leave_data.getReason());

        exit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
