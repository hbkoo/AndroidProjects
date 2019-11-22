package com.example.acer.experimenttext2.Class;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.acer.experimenttext2.Activity.AddCourseDialogActivity;
import com.example.acer.experimenttext2.Activity.CourseCheckActivity;
import com.example.acer.experimenttext2.R;

import java.util.List;

/**
 * Created by acer on 2017/3/20.
 */

public class AddCourseAdapter extends RecyclerView.Adapter<AddCourseAdapter.ViewHolder>{

    private AddCourse addCourse;
    private List<AddCourse> mAddCourseList;
    private Context mContext;
    private int position;

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView Mon_tv_item,Tue_tv_item,Wes_tv_item,Thu_tv_item,Fri_tv_item,
                Sat_tv_item,Sun_tv_item,course_order;
        View mView;

        public ViewHolder(View view){
            super(view);
            mView = view;
            Mon_tv_item = (TextView) view.findViewById(R.id.Mon_tv_item);
            Tue_tv_item = (TextView) view.findViewById(R.id.Tue_tv_item);
            Wes_tv_item = (TextView) view.findViewById(R.id.Wes_tv_item);
            Thu_tv_item = (TextView) view.findViewById(R.id.Thu_tv_item);
            Fri_tv_item = (TextView) view.findViewById(R.id.Fri_tv_item);
            Sat_tv_item = (TextView) view.findViewById(R.id.Sat_tv_item);
            Sun_tv_item = (TextView) view.findViewById(R.id.Sun_tv_item);
            course_order = (TextView) view.findViewById(R.id.course_order);

        }
    }

    public AddCourseAdapter(List<AddCourse> addCourseList, Context context){
        mAddCourseList = addCourseList;
        mContext = context;
    }

    @Override
    public AddCourseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.add_course_item,parent,false);
        final AddCourseAdapter.ViewHolder viewHolder = new AddCourseAdapter.ViewHolder(view);

        viewHolder.Mon_tv_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = viewHolder.getAdapterPosition();
                click(position);
            }
        });
        viewHolder.Tue_tv_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = viewHolder.getAdapterPosition();
                click(position + 5);
            }
        });
        viewHolder.Wes_tv_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = viewHolder.getAdapterPosition();
                click(position + 10);
            }
        });
        viewHolder.Thu_tv_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = viewHolder.getAdapterPosition();
                click(position + 15);
            }
        });
        viewHolder.Fri_tv_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = viewHolder.getAdapterPosition();
                click(position + 20);
            }
        });
        viewHolder.Sat_tv_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = viewHolder.getAdapterPosition();
                click(position + 25);
            }
        });
        viewHolder.Sun_tv_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = viewHolder.getAdapterPosition();
                click(position + 30);
            }
        });

        return viewHolder;
    }

    public void click(int position){

        AddCourse addCourse = mAddCourseList.get(position);
        Intent intent = new Intent(mContext,AddCourseDialogActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("course_data",addCourse);
        intent.putExtras(bundle);
        mContext.startActivity(intent);


    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.course_order.setText(""+ (position+1));
        addCourse = mAddCourseList.get(position);
        holder.Mon_tv_item.setText("上课学生" + addCourse.getStuCourseSize() + "\n\n实验室数"
                + addCourse.getClassroomSize());
        addCourse = mAddCourseList.get(position + 5);
        holder.Tue_tv_item.setText("上课学生" + addCourse.getStuCourseSize() + "\n\n实验室数"
                + addCourse.getClassroomSize());
        addCourse = mAddCourseList.get(position + 10);
        holder.Wes_tv_item.setText("上课学生" + addCourse.getStuCourseSize() + "\n\n实验室数"
                + addCourse.getClassroomSize());
        addCourse = mAddCourseList.get(position + 15);
        holder.Thu_tv_item.setText("上课学生" + addCourse.getStuCourseSize() + "\n\n实验室数"
                + addCourse.getClassroomSize());
        addCourse = mAddCourseList.get(position + 20);
        holder.Fri_tv_item.setText("上课学生" + addCourse.getStuCourseSize() + "\n\n实验室数"
                + addCourse.getClassroomSize());
        addCourse = mAddCourseList.get(position + 25);
        holder.Sat_tv_item.setText("上课学生" + addCourse.getStuCourseSize() + "\n\n实验室数"
                + addCourse.getClassroomSize());
        addCourse = mAddCourseList.get(position + 30);
        holder.Sun_tv_item.setText("上课学生" + addCourse.getStuCourseSize() + "\n\n实验室数"
                + addCourse.getClassroomSize());

    }

    @Override
    public int getItemCount() {
        return 5;
    }


}
