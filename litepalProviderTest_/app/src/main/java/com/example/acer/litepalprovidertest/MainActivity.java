package com.example.acer.litepalprovidertest;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button addButton,changeButton,deleteButton,queryButton;
    private String newID;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Toast.makeText(MainActivity.this,"点击了菜单导航按钮",Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setHomeAsUpIndicator(R.mipmap.ic_launcher);
        }
        addButton = (Button) findViewById(R.id.addButton);
        deleteButton = (Button) findViewById(R.id.deleteButton);
        changeButton = (Button) findViewById(R.id.changeButton);
        queryButton = (Button) findViewById(R.id.queryButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://com.example.acer.litepaltest.provider/book");
                ContentValues values = new ContentValues();
                values.put("name","计算机导论");
                values.put("author","杜海涛");
                values.put("price",15.9);
                values.put("page",350);
                Uri newuri = getContentResolver().insert(uri,values);
                newID = newuri.getPathSegments().get(1);
                Toast.makeText(MainActivity.this,"新增成功",Toast.LENGTH_LONG).show();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://com.example.acer.litepaltest.provider/book/" + newID);
                getContentResolver().delete(uri,null,null);
                Toast.makeText(MainActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
            }
        });

        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://com.example.acer.litepaltest.provider/book/" + newID);
                ContentValues values = new ContentValues();
                values.put("price",66.66);
                values.put("page",500);
                getContentResolver().update(uri,values,null,null);
                Toast.makeText(MainActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
            }
        });

        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://com.example.acer.litepaltest.provider/book/" + newID);
                Cursor cursor = getContentResolver().query(uri,null,null,null,null);
                if(cursor != null){
                    while(cursor.moveToNext()){
                        Toast.makeText(MainActivity.this,"书名为：" + cursor.getString(cursor.getColumnIndex("name"))
                        + "\n作者为：" + cursor.getString(cursor.getColumnIndex("author")),Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(MainActivity.this,"没有找到书目",Toast.LENGTH_LONG).show();
                }
                cursor.close();
            }
        });

    }
}
