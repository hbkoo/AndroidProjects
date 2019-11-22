package com.example.acer.experimenttext2.Class;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.experimenttext2.Activity.CourseActivity;
import com.example.acer.experimenttext2.Activity.CourseCheckActivity;
import com.example.acer.experimenttext2.R;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by acer on 2017/3/4.
 */

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    private Course course;
    private List<Course> mCourseList;
    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView time_course,course_name,course_place,course_class;
        View mView;

        public ViewHolder(View view){
            super(view);
            mView = view;
            time_course = (TextView) view.findViewById(R.id.time_course_recycle);
            course_name = (TextView) view.findViewById(R.id.course_name_recycle);
            course_class = (TextView) view.findViewById(R.id.course_class_recycle);
            course_place = (TextView) view.findViewById(R.id.course_place_recycle);
        }
    }

    public CourseAdapter(List<Course> courseList, Context context){
        mCourseList = courseList;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_item,parent,false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                Log.d("position",""+position);
                Course course = mCourseList.get(position);
                Intent intent = new Intent(mContext,CourseCheckActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("course_data",course);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });

        viewHolder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
                dialog.setTitle("提示");
                dialog.setMessage("确定要删除课程？");

                //TODO 删除数据库中的数据
                dialog.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int position = viewHolder.getAdapterPosition();
                        Course course = mCourseList.get(position);
                        DataSupport.deleteAll(Course.class,"time = ? and classroom = ? and content = ?",
                                course.getTime(),course.getClassroom(),course.getContent());

                        mCourseList.remove(position);
                        notifyDataSetChanged();

                        Toast.makeText(mContext,"删除成功",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent("com.example.acer.experimenttext2.course_size_broadcast");
                        intent.putExtra("course_size", mCourseList.size());
                        mContext.sendBroadcast(intent);
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();
                return true;
            }
        });

        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return mCourseList.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        course = mCourseList.get(position);
        holder.time_course.setText(course.getTime());
        holder.course_name.setText(course.getName());
        holder.course_class.setText("班级：" + course.getClass_() + "");
        holder.course_place.setText("地点：" + course.getClassroom() + "");
    }
}
