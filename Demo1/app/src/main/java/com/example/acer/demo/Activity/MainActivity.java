package com.example.acer.demo.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.acer.demo.R;
import com.example.acer.demo.Tools.MainItem;
import com.example.acer.demo.Tools.MainAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<MainItem> objects = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化数据
        InitData();

        GridView gridView = (GridView) findViewById(R.id.grid_view);
        MainAdapter adapter = new MainAdapter(this,objects);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new mItemClick());

    }

    private class mItemClick implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (objects.get(position).getName()) {
                case "记账本":

                    break;
                case "备忘录":
                    startActivity(new Intent(MainActivity.this,MemoryActivity.class));
                    break;
                case "计算器":
                    startActivity(new Intent(MainActivity.this,CaculateActivity.class));
                    break;
                case "通讯录":
                    startActivity(new Intent(MainActivity.this,PhoneBookActivity.class));
                    break;
                case "天气":

                    break;
                case "帮助":
                    startActivity(new Intent(MainActivity.this,AboutActivity.class));
                    break;
            }
        }
    }

    private void InitData() {
        MainItem item = new MainItem(R.drawable.account_book,"记账本");
        objects.add(item);
        MainItem item1 = new MainItem(R.drawable.memo_info,"备忘录");
        objects.add(item1);
        MainItem item2 = new MainItem(R.drawable.calculator,"计算器");
        objects.add(item2);
        MainItem item3 = new MainItem(R.drawable.phone_book,"通讯录");
        objects.add(item3);
        MainItem item4 = new MainItem(R.drawable.weather_info,"天气");
        objects.add(item4);
        MainItem item5 = new MainItem(R.drawable.help_info,"帮助");
        objects.add(item5);
    }

}
