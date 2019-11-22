package com.example.acer.experimenttext2.Class;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.experimenttext2.Activity.StuLeaveDialogActivity;
import com.example.acer.experimenttext2.R;

import java.util.List;

/**
 * Created by acer on 2017/3/2.
 */

public class StuLeaveAdapter extends RecyclerView.Adapter<StuLeaveAdapter.ViewHolder> {

    private StuLeave stu_leave;
    private List<StuLeave> mStuLeaveList;
    private Context context;
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView leaveName,leaveClass,leaveNameDialog,leaveClassDialog,leaveIDDialog,
                leaveTelephoneDialog,leaveReasonDialog;
        View stuLeaveView;

        public ViewHolder(View view){
            super(view);
            stuLeaveView = view;
            leaveName = (TextView) view.findViewById(R.id.leaveName_TextView);
            leaveClass = (TextView) view.findViewById(R.id.leaveClass_TextView);
        }
    }

    public StuLeaveAdapter(List<StuLeave> stuLeaveList,Context context){
        mStuLeaveList = stuLeaveList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_leave_item,parent,false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.stuLeaveView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                StuLeave stuLeave = mStuLeaveList.get(position);
                Intent intent = new Intent(context,StuLeaveDialogActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("data",stuLeave);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        stu_leave = mStuLeaveList.get(position);
        holder.leaveName.setText("姓名：" + stu_leave.getName());
        holder.leaveClass.setText("班级：" + stu_leave.getClass_());
    }

    @Override
    public int getItemCount() {
        return mStuLeaveList.size();
    }
}
