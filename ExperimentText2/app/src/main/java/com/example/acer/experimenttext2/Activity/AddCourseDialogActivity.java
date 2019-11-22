package com.example.acer.experimenttext2.Activity;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.experimenttext2.Class.AddCourse;
import com.example.acer.experimenttext2.Class.Course;
import com.example.acer.experimenttext2.R;

public class AddCourseDialogActivity extends AppCompatActivity {

    private ArrayAdapter<String> classroom_adapter = null;
    private TextView course_name,course_class,course_time;
    private EditText course_content;
    private Button add_button,cancel_button;
    private Spinner course_classroom;
    private String[] classroom;
    private String selectClassroom;
    private AddCourse addCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course_dialog);

        course_name = (TextView) findViewById(R.id.addCourse_name_TextView);
        course_class = (TextView) findViewById(R.id.addCourse_class_TextView);
        course_content = (EditText) findViewById(R.id.addCourse_content_EditText);
        course_time = (TextView) findViewById(R.id.addCourse_time_TextView);
        course_classroom = (Spinner) findViewById(R.id.addCourse_classroom_spinner);
        add_button = (Button) findViewById(R.id.addCourse_add_button);
        cancel_button = (Button) findViewById(R.id.addCourse_cancel_button);

        addCourse = (AddCourse) getIntent().getExtras().getSerializable("course_data");
        classroom = new String [addCourse.getClassroomSize()];
        classroom = addCourse.getClassroom();
        course_name.setText(addCourse.getName());
        course_class.setText(addCourse.getClass_());
        course_time.setText(addCourse.getTime());

        classroom_adapter = new ArrayAdapter<String>(AddCourseDialogActivity.this,
                android.R.layout.simple_dropdown_item_1line,classroom);
        course_classroom.setFocusable(false);
        course_classroom.setAdapter(classroom_adapter);

        add_button.setOnClickListener(new mClick());
        cancel_button.setOnClickListener(new mClick());

    }

    class mClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                //TODO 添加课程到数据库
                case R.id.addCourse_add_button:

                    selectClassroom = course_classroom.getSelectedItem().toString();
                    if (selectClassroom.equals("--请选择--")){
                        Toast.makeText(AddCourseDialogActivity.this,"请选择教室",Toast.LENGTH_SHORT).show();
                    } else {

                        Course course = new Course(addCourse.getName(),addCourse.getClass_(),
                                addCourse.getTime(),selectClassroom,course_content.getText().toString());
                        CourseActivity.courseList.add(course);
                        CourseActivity.adapter.notifyDataSetChanged();

                        Intent intent = new Intent("com.example.acer.experimenttext2.course_size_broadcast");
                        intent.putExtra("course_size",CourseActivity.courseList.size());
                        sendBroadcast(intent);

                        Course course1 = new Course();
                        course1.setName(course.getName());
                        course1.setClass_(course.getClass_());
                        course1.setTime(course.getTime());
                        course1.setClassroom(course.getClassroom());
                        course1.setContent(course.getContent());
                        course1.save();

                        Toast.makeText(AddCourseDialogActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    break;
                case R.id.addCourse_cancel_button:
                    finish();
                    break;
                default:
                    break;
            }
        }
    }

}
