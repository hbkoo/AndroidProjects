package com.example.acer.demo.Tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.acer.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建GridView的适配器
 */

public class MainAdapter extends BaseAdapter {

    private Context context;
    private List<MainItem> objects = new ArrayList<>();

    public MainAdapter(Context context, List<MainItem> objects){
        this.context = context;
        this.objects = objects;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MainItem item = (MainItem) getItem(position);
        View view = LayoutInflater.from(context).inflate(R.layout.main_item,parent,false);
        ImageView image = (ImageView) view.findViewById(R.id.image_item);
        TextView name = (TextView) view.findViewById(R.id.text_item);
        image.setImageResource(item.getImageID());
        name.setText(item.getName());
        return view;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }
}
