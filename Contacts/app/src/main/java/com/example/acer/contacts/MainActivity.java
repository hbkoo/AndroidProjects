package com.example.acer.contacts;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static List<Contact> contacts = new ArrayList<>();
    private RecyclerView recyclerView;
    private ContactAdapter adapter;
    private Button add_btn;

    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        relativeLayout = (RelativeLayout) findViewById(R.id.pull_to_refresh_head);

        //relativeLayout = (RelativeLayout) getLayoutInflater().inflate(R.id.pull_to_refresh_head, null);

        add_btn = (Button) findViewById(R.id.add_btn);
        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        adapter = new ContactAdapter(this, contacts);
        recyclerView.setAdapter(adapter);
        permission();
        showContacts();

        add_btn.setOnClickListener(new mClick());

    }

    private void permission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.WRITE_CONTACTS
            }, 1);
        }
    }

    //读取联系人信息
    private void showContacts() {
        List<Contact> contactList = ContactHelp.queryContact(MainActivity.this);
        contacts.clear();
        if (contactList != null) {
            contacts.addAll(contactList);
        }

        if (contacts.size() != 0) {
            Contact contact = contacts.get(0);
            Contact contact1 = contacts.get(1);
            Toast.makeText(MainActivity.this, contact.toString() + contact1.toString(),
                    Toast.LENGTH_SHORT).show();
        }

        adapter.notifyDataSetChanged();
    }

    private class mClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            showDialog(MainActivity.this);
        }
    }

    private void showDialog(final Context context) {
        LinearLayout layout = (LinearLayout) ((Activity) context).getLayoutInflater()
                .inflate(R.layout.item_dialog, null);
        final EditText name = (EditText) layout.findViewById(R.id.name_et);
        final EditText phone = (EditText) layout.findViewById(R.id.phone_et);
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setView(layout);
        dialog.setTitle("添加联系人");
        dialog.setCancelable(false);
        dialog.setNegativeButton("取消", null);
        dialog.setPositiveButton("添加", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Contact contact1 = new Contact();
                contact1.setName(name.getText().toString());
                contact1.setTelephone(phone.getText().toString());
                if (ContactHelp.addContact(context, contact1)) {
                    contacts.add(0, contact1);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(context, "添加成功", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showContacts();
                } else {
                    Toast.makeText(MainActivity.this,"只有同意了权限才能使用本程序！",Toast.LENGTH_SHORT)
                            .show();
                    finish();
                }
                break;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
