package com.example.acer.litepaltest;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.backup:
                Toast.makeText(MainActivity.this,"你点击了backup按钮",Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(MainActivity.this,"你点击了delete按钮",Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings:
                Toast.makeText(MainActivity.this,"你点击了settings按钮",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }

    /*加载menu布局*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        navView.setCheckedItem(R.id.nav_call);//将call菜单项设置为默认选中
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_call:
                        Toast.makeText(MainActivity.this,"你点击了Call按钮",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_friends:
                        Toast.makeText(MainActivity.this,"你点击了Friends按钮",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_location:
                        Toast.makeText(MainActivity.this,"你点击了Location按钮",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_mail:
                        Toast.makeText(MainActivity.this,"你点击了Mail按钮",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_task:
                        Toast.makeText(MainActivity.this,"你点击了Task按钮",Toast.LENGTH_SHORT).show();
                        break;
                    default:break;
                }
                return true;
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"你点击了悬浮按钮",Toast.LENGTH_SHORT).show();
            }
        });

        final Button createDatabase = (Button) findViewById(R.id.createDatabase);
        createDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LitePal.getDatabase();
                createDatabase.setEnabled(false);
                Toast.makeText(MainActivity.this,"数据库创建成功！",Toast.LENGTH_SHORT).show();
            }
        });

        Button addData = (Button) findViewById(R.id.addData);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,addActivity.class);
                startActivity(intent);
            }
        });

        Button changeData = (Button) findViewById(R.id.changeData);
        changeData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,changeActivity.class);
                startActivity(intent);
            }
        });

        Button deleteData = (Button) findViewById(R.id.deleteData);
        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,deleteActivity.class);
                startActivity(intent);
            }
        });

        Button queryData = (Button) findViewById(R.id.qureyData);
        queryData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,queryActivity.class);
                startActivity(intent);
            }
        });

    }
}
