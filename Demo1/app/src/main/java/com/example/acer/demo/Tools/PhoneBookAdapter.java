package com.example.acer.demo.Tools;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.demo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 适配器
 */

public class PhoneBookAdapter extends BaseAdapter {

    private Context context;
    private List<PhoneBookItem> phoneList = new ArrayList<>();
    private List<PhoneBookItem> deleteList = new ArrayList<>();
    private HashMap<Integer,Boolean>isChecked = new HashMap<>();
    private boolean isCheckable = false;

    public PhoneBookAdapter(Context context, List<PhoneBookItem> phoneList) {
        this.context = context;
        this.phoneList = phoneList;
        for (int i = 0; i < phoneList.size(); i++){
            isChecked.put(i,false);
        }
    }

    @Override
    public int getCount() {
        return phoneList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final PhoneBookItem item = (PhoneBookItem) getItem(position);
        View view;
        final ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.phone_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.item = new PhoneBookItem(item.getName(),item.getTelephone(),item.getImage());
            viewHolder.nameTextView = (TextView) view.findViewById(R.id.phonebook_item_name);
            viewHolder.checkBox = (CheckBox) view.findViewById(R.id.phonebook_checkbox);
            viewHolder.phoneBookImage = (ImageView) view.findViewById(R.id.phonebook_item_image);
            viewHolder.phoneCallImage = (ImageView) view.findViewById(R.id.phonebook_call_icon);
            viewHolder.nameTextView.setText(item.getName());
            viewHolder.phoneCallImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,viewHolder.item.getName(),Toast.LENGTH_SHORT).show();

                    Uri uri = Uri.parse("tel:" + viewHolder.item.getTelephone());
                    Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                    //context.startActivity(intent);
                }
            });
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
            viewHolder.nameTextView.setText(item.getName());
        }

        if (isCheckable) {
            viewHolder.checkBox.setVisibility(View.VISIBLE);
            viewHolder.checkBox.setChecked(isChecked.get(position));
        } else {
            isChecked.put(position,false);
            viewHolder.checkBox.setVisibility(View.GONE);
        }

        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.checkBox.isChecked()){
                    Toast.makeText(context,item.getName(),Toast.LENGTH_SHORT).show();
                    deleteList.add(item);
                } else {
                    deleteList.remove(item);
                }
            }
        });

        return view;
    }

    private class ViewHolder {
        CheckBox checkBox;
        ImageView phoneBookImage, phoneCallImage;
        TextView nameTextView;
        PhoneBookItem item;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return phoneList.get(position);
    }

    public void ShowCheckBox(boolean isCheckable){
        this.isCheckable = isCheckable;
        if (!isCheckable && deleteList.size() != 0){
            for(PhoneBookItem item : deleteList){
                phoneList.remove(item);
            }
            deleteList.clear();
        }
        notifyDataSetChanged();
    }

    public void AddBook(PhoneBookItem item){
        phoneList.add(0,item);
        notifyDataSetChanged();
    }

}
