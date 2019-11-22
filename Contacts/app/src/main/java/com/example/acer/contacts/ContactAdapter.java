package com.example.acer.contacts;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * 联系人适配器
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private List<Contact> contacts;
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView contact_name,contact_phone;
        Button delete_btn;
        LinearLayout contact_item;

        public ViewHolder(View view) {
            super(view);
            contact_name = (TextView) view.findViewById(R.id.contact_name);
            contact_phone = (TextView) view.findViewById(R.id.contact_phone);
            delete_btn = (Button) view.findViewById(R.id.contact_delete);
            contact_item = (LinearLayout) view.findViewById(R.id.contact_item);
        }

    }

    public ContactAdapter(Context context,List<Contact> contacts) {
        this.context = context;
        this.contacts = contacts;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Contact contact = contacts.get(position);
        holder.contact_name.setText(contact.getName());
        holder.contact_phone.setText(contact.getTelephone());
        holder.delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("提示")
                        .setMessage("确定删除么？")
                        .setNegativeButton("取消",null)
                        .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                if (ContactHelp.deleteContact(context,contact.getId())){
                                    contacts.remove(position);
                                    notifyDataSetChanged();
                                    Toast.makeText(context,"删除成功！",Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context,"删除失败！",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).show();

            }
        });

        holder.contact_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(context,contact);
            }
        });

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.contact_item,parent);
//        return new ViewHolder(view);
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    private void showDialog(final Context context, final Contact contact) {
        LinearLayout layout = (LinearLayout) ((Activity) context).getLayoutInflater()
                .inflate(R.layout.item_dialog,null);
        final EditText name = (EditText) layout.findViewById(R.id.name_et);
        final EditText phone = (EditText) layout.findViewById(R.id.phone_et);
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setView(layout);
        dialog.setNegativeButton("取消",null);
        name.setText(contact.getName());
        phone.setText(contact.getTelephone());
        dialog.setPositiveButton("修改", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Contact contact1 = new Contact();
                contact1.setName(name.getText().toString());
                contact1.setTelephone(phone.getText().toString());
                contact1.setId(contact.getId());
                if (ContactHelp.updateContact(context,contact1)) {
                    contacts.remove(contact);
                    contacts.add(contact1);
                    notifyDataSetChanged();
                    Toast.makeText(context, "修改成功", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }
}
