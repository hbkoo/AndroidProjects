package com.example.acer.demo.Tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.acer.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 备忘录适配器
 */

public class MemoryAdapter extends BaseAdapter {

    private Context context;
    private List<Memory> memories = new ArrayList<>();
    public MemoryAdapter(Context context, List<Memory> memories){
        this.context = context;
        this.memories = memories;
    }

    public void setMemories(List<Memory> memories){
        this.memories.clear();
        this.memories = memories;
    }

    @Override
    public int getCount() {
        return memories.size();
    }

    @Override
    public Object getItem(int position) {
        return memories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Memory memory = (Memory) getItem(position);
        ViewHolder viewHolder;
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.memory_item,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.time = (TextView) view.findViewById(R.id.memo_item_time);
            viewHolder.content = (TextView) view.findViewById(R.id.memo_item_content);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.time.setText(memory.getTime());
        viewHolder.content.setText(memory.getContent());

        return view;
    }

    private class ViewHolder{
        TextView time,content;
    }

}
