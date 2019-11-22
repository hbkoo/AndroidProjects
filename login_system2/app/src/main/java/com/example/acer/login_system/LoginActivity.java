package com.example.acer.login_system;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

public class LoginActivity extends Activity {
    ProgressDialog mydialog;
    Button btn1,btn2,btn3,btn4,btn5,btn6,btn7;
    LinearLayout login;

    NotificationManager n_Manager;
    Notification notification;

    public void onCreate(Bundle savedInstanceStates){

        super.onCreate(savedInstanceStates);
        setContentView(R.layout.activity_login);
        n_Manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        btn1 = (Button) findViewById(R.id.button1);
        btn2 = (Button) findViewById(R.id.button2);
        btn3 = (Button) findViewById(R.id.button3);
        btn4 = (Button) findViewById(R.id.button4);
        btn5 = (Button) findViewById(R.id.button5);
        btn6 = (Button) findViewById(R.id.button6);
        btn7 = (Button) findViewById(R.id.button7);
        btn1.setOnClickListener(new mClick());
        btn2.setOnClickListener(new mClick());
        btn3.setOnClickListener(new mClick());
        btn4.setOnClickListener(new mClick());
        btn5.setOnClickListener(new mClick());
        btn6.setOnClickListener(new mClick());
        btn7.setOnClickListener(new mClick());

    }

    class mClick implements View.OnClickListener {

        int m_year = 2012;
        int m_month = 1;
        int m_day = 1;
        int m_hour = 12, m_minute = 1;

        AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
        public void onClick(View argo){
            if(argo==btn1){
                //设置对话框标题
                dialog.setTitle("警告");
                //设置对话框图标
                dialog.setIcon(R.drawable.icon1);
                //设置对话框显示内容
                dialog .setMessage("本项操作可能导致信息泄露！");
                //设置对话框的“确定”按钮
                dialog.setPositiveButton("确定",new okClick());
                //创建对话框
                dialog.create();
                //显示对话框
                dialog.show();
            }

            else if(argo == btn2){
                //从另外布局中关联组件
                login = (LinearLayout) getLayoutInflater().inflate(R.layout.login,null);
                dialog.setTitle("用户登录").setMessage("请输入用户名和密码").setView(login);
                dialog.setPositiveButton("确定",new loginClick());
                dialog.setNegativeButton("退出",new exitClick());
                dialog.setIcon(R.drawable.icon2);
                dialog.create();
                dialog.show();

            }

            else if(argo == btn3){

                ProgressDialog d = new ProgressDialog(LoginActivity.this);
                d.setTitle("进度对话框");
                d.setIndeterminate(true);
                d.setMessage("程序正在Loading...");
                d.setCancelable(true);
                d.setMax(10);
                d.show();

            }

            else if(argo == btn4){

                //设置日期监听器
                DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        m_year = year;
                        m_month = month;
                        m_day = dayOfMonth;
                    }
                };
                //创建日期对话框对象
                DatePickerDialog date = new DatePickerDialog(LoginActivity.this,dateListener,m_year,m_month,m_day);
                date.setTitle("日期对话框");
                date.show();

            }

            else if(argo == btn5){

                //设置时间监听器
                TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        m_hour = hourOfDay;
                        m_minute = minute;
                    }
                };

                TimePickerDialog time = new TimePickerDialog(LoginActivity.this,timeListener,m_hour,m_minute,true);
                time.setTitle("时间对话框");
                time.show();
            }

            else if(argo == btn6){

                int icon = R.drawable.icon1;
                CharSequence tickerText = "紧急通知，程序已启动";
                long when = System.currentTimeMillis();
                notification = new Notification(icon,tickerText,when);
                Intent intent = new Intent(LoginActivity.this,LoginActivity.class);
                PendingIntent pi = PendingIntent.getActivity(LoginActivity.this,0,intent,0);

              //  notification.setLatestEventInfo(LoginActivity.this,"通知","通知内容",pi);
                n_Manager.notify(0,notification);

            }
            else if(argo == btn7){
                n_Manager.cancelAll();
            }

        }

    }

    /*普通对话框的“确定”按钮事件*/
    class okClick implements DialogInterface.OnClickListener{
        public void onClick(DialogInterface dialog,int which){
            dialog.cancel();//关闭对话框
        }
    }

    /*输入对话框的“确定”按钮事件*/
    class loginClick implements DialogInterface.OnClickListener{
        EditText txt;
        public void onClick(DialogInterface dialog,int which){
            txt = (EditText) findViewById(R.id.passwordEdit);
            if((txt.getText().toString()).equalsIgnoreCase("123456"))
                Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
            // dialog.dismiss();//关闭对话框
        }
    }

    /*输入对话框的“退出”按钮事件*/
    class exitClick implements DialogInterface.OnClickListener{
        public void onClick(DialogInterface dialog,int which){
            LoginActivity.this.finish();
        }
    }

}
