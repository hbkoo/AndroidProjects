package com.example.acer.experimenttext2.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.acer.experimenttext2.Class.Course;
import com.example.acer.experimenttext2.Class.Date_dialog;
import com.example.acer.experimenttext2.R;
import com.example.acer.experimenttext2.Class.Time_dialog;

import java.util.ArrayList;
import java.util.Calendar;

public class CourseCheckActivity extends AppCompatActivity {

    private ArrayAdapter<String> classroom_adapter = null;
    private Spinner classroom_Spinner;
    private TextView nameCourse_TextView,classCourse_TextView,timeCourse_textView;
    private EditText contentCourse_EditText;
    private String mClassroom,mContent;
    private Course course;

    //用于保存修改Spinner的数据
    ArrayList<String> options_classroom=new ArrayList<String>();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.course_check_toolbar,menu);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            dialogInformation();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_dialog);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        course = (Course) getIntent().getExtras().getParcelable("course_data");

        nameCourse_TextView = (TextView) findViewById(R.id.course_nameCheck_TextView);
        classCourse_TextView = (TextView) findViewById(R.id.classCheck_course_TextView);
        classroom_Spinner = (Spinner) findViewById(R.id.classroomCheck_course_spinner);
        timeCourse_textView = (TextView) findViewById(R.id.timecCheck_course_TextView);
        contentCourse_EditText = (EditText) findViewById(R.id.contentCheck_course_EditText);



        /*这里面的数据排序必须和Spinner里的数据排序一致*/
        options_classroom.add("--请选择--");
        options_classroom.add("1-301");
        options_classroom.add("2-502");

        classroom_adapter = new ArrayAdapter<String>(CourseCheckActivity.this,
                android.R.layout.simple_spinner_dropdown_item,options_classroom);
        classroom_Spinner.setFocusable(false);
        classroom_Spinner.setAdapter(classroom_adapter);

        getInformation();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                dialogInformation();
                break;
            case R.id.OK_menu:

                break;
            default:
                break;
        }
        return true;
    }

    public void getInformation(){

        mClassroom = course.getClassroom();
        mContent = course.getContent();

        //indexOf是判断已经选中的信息在Spinner总信息的位置
        classroom_Spinner.setSelection(options_classroom.indexOf(mClassroom));

        nameCourse_TextView.setText(course.getName());
        classCourse_TextView.setText(course.getClass_());
        timeCourse_textView.setText(course.getTime());
        contentCourse_EditText.setText(mContent);

    }

    /*判断各个数据框的内容是否变化*/
    public boolean isChange(){
        if (!mClassroom.equals(classroom_Spinner.getSelectedItem().toString()))
            return true;
        else if (!mContent.equals(contentCourse_EditText.getText().toString()))
            return true;
        return false;
    }

    //提示消息对话框
    public void dialogInformation(){
        if(isChange()){
            AlertDialog.Builder dialog = new AlertDialog.Builder(CourseCheckActivity.this);
            dialog.setTitle("提示");
            dialog.setMessage("放弃编辑？");
            dialog.setCancelable(true);
            dialog.setPositiveButton("放弃", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            dialog.setNegativeButton("继续编辑", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            dialog.show();
        }
        else
            finish();
    }

}
