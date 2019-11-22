package com.example.acer.databaseconnection;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.databaseconnection.view.MyView;

import java.sql.Connection;

public class MainActivity extends AppCompatActivity {

    //private TextView mTextMessage;
    private Button connect,unConnect,query,logic;
    private Connection getconn;

    private String sql = "select * from User_logic";
    private ConnectToDataBase connectToDataBase;

    private MyView myView = null;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    //mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    //mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        connect = (Button) findViewById(R.id.connect_btn);
        unConnect = (Button) findViewById(R.id.unConnect_btn);
        query = (Button) findViewById(R.id.query_btn);
        logic = (Button) findViewById(R.id.logic_btn);

        myView = (MyView) findViewById(R.id.myView);
        myView.setOnTouchListener(new mTouch());

        unConnect.setEnabled(false);
        query.setEnabled(false);
        logic.setEnabled(false);

        connect.setOnClickListener(new mClick());
        unConnect.setOnClickListener(new mClick());
        query.setOnClickListener(new mClick());
        logic.setOnClickListener(new mClick());

    }

    private class mTouch implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            myView.setCircleX(event.getX());
            myView.setCircleY(event.getY());
            myView.invalidate();
            return true;
        }
    }

    private class mClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.connect_btn:
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//
//                        }
//                    }).start();
                    connectToDataBase = new ConnectToDataBase(MainActivity.this);
                    ConnectToDataBase.getDataBaseConnection("passenger_databases");
                   // getconn = connectToDataBase.getDataBaseConnection();
                    unConnect.setEnabled(true);
                    query.setEnabled(true);
                    logic.setEnabled(true);
                    break;
                case R.id.query_btn:

                    String information = passenger.query(2);
                    Toast.makeText(MainActivity.this,information,Toast.LENGTH_SHORT).show();

                    break;
                case R.id.logic_btn:
                    connectToDataBase.logic(getconn,sql);
                    break;
                case R.id.unConnect_btn:

                    break;
            }
        }
    }



}
