package com.example.acer.demo.Tools;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.demo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 数据适配器
 */

public class PhoneBookAdapterRC extends RecyclerView.Adapter<PhoneBookAdapterRC.ViewHolder> {

    private Context context;
    private List<PhoneBookItem> phoneList = new ArrayList<>();
    private List<PhoneBookItem> deleteList = new ArrayList<>();
    private boolean isCheckable = false;
    //recyclerView与CheckBox结合使用需要设置一个标志来判断当前CheckBox是否被选中
    private HashMap<Integer, Boolean> isCheckeds = new HashMap<>();

    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout_item;
        CheckBox checkBox;
        ImageView phoneBookImage, phoneCallImage;
        TextView nameTextView;

        public ViewHolder(View view) {
            super(view);
            layout_item = (LinearLayout) view.findViewById(R.id.book_item);
            nameTextView = (TextView) view.findViewById(R.id.phonebook_item_name);
            checkBox = (CheckBox) view.findViewById(R.id.phonebook_checkbox);
            phoneBookImage = (ImageView) view.findViewById(R.id.phonebook_item_image);
            phoneCallImage = (ImageView) view.findViewById(R.id.phonebook_call_icon);
        }
    }

    public PhoneBookAdapterRC(Context context, List<PhoneBookItem> phoneList) {
        this.context = context;
        this.phoneList = phoneList;
        for (int i = 0; i < phoneList.size(); i++) {
            isCheckeds.put(i, false);
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        PhoneBookItem item = phoneList.get(position);
        holder.nameTextView.setText(item.getName());
        if (isCheckable) {
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.checkBox.setChecked(isCheckeds.get(position));
        } else {
            isCheckeds.put(position, false);
            holder.checkBox.setVisibility(View.GONE);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.phone_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        holder.layout_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneBookItem item = phoneList.get(holder.getAdapterPosition());
                ShowPhoneBook(item);
            }
        });

        holder.phoneCallImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                PhoneBookItem item = phoneList.get(position);
                //Toast.makeText(context,item.getName(),Toast.LENGTH_SHORT).show();
                Uri uri = Uri.parse("tel:" + item.getTelephone());
                Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                context.startActivity(intent);
            }
        });

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int position = holder.getAdapterPosition();
                PhoneBookItem item = phoneList.get(position);
                if (isChecked) {
                    isCheckeds.put(position, true);
                    deleteList.add(item);
                } else {
                    isCheckeds.put(position, false);
                    deleteList.remove(item);
                }
            }
        });
        return holder;
    }

    @Override
    public int getItemCount() {
        return phoneList.size();
    }

    //展示复选框按钮
    public void ShowCheckBox(boolean isCheckable) {
        this.isCheckable = isCheckable;
        notifyDataSetChanged();
    }

    //删除数据
    public void DeleteBooks(){
        for (PhoneBookItem item : deleteList) {
            phoneList.remove(item);
        }
        deleteList.clear();
        Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
        notifyDataSetChanged();
    }

    //新增数据
    public void AddBook(PhoneBookItem item) {
        phoneList.add(0, item);
        isCheckeds.put(phoneList.size() - 1, false);
        notifyDataSetChanged();
    }

    //展示每条数据的详细信息
    private void ShowPhoneBook(final PhoneBookItem item) {

        View phone_book_view = LayoutInflater.from(context).inflate(R.layout.phone_book, null, false);
        TextView change = (TextView) phone_book_view.findViewById(R.id.phone_book_change);
        TextView cancel = (TextView) phone_book_view.findViewById(R.id.phone_book_cancel);
        final EditText name = (EditText) phone_book_view.findViewById(R.id.phone_book_name_et);
        final EditText tel = (EditText) phone_book_view.findViewById(R.id.phone_book_tel_et);
        name.setText(item.getName());
        name.setSelection(item.getName().length());
        tel.setText(item.getTelephone());
        tel.setSelection(item.getTelephone().length());

        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setView(phone_book_view);
        final AlertDialog alertDialog = dialog.show();

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!item.getName().equals(name.getText().toString())
                        || !item.getTelephone().equals(tel.getText().toString())) {
                    item.setName(name.getText().toString());
                    item.setTelephone(tel.getText().toString());
                    Toast.makeText(context, "修改成功", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                }
                alertDialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }

    public int getDeleteCount() {
        return deleteList.size();
    }

}
