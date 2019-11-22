package com.example.acer.wifi_test;

import android.net.wifi.ScanResult;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ScrollView sView;//右侧滚动条按钮
    private TextView allNetWork;
    private Button btnscan,btnstart,btnstop,btncheck;
    private WifiTest mWifiAdmin;
    private List<ScanResult> list;
    private ScanResult mScanResult;
    private StringBuffer mStringBuffer = new StringBuffer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWifiAdmin = new WifiTest(this);
        init();
    }

    /*按钮的初始化*/
    public void init(){
        sView = (ScrollView) findViewById(R.id.scrollView);
        allNetWork = (TextView) findViewById(R.id.textView1);
        btnscan = (Button) findViewById(R.id.buttonScan);
        btnstart = (Button) findViewById(R.id.buttonOpen);
        btnstop = (Button) findViewById(R.id.buttonClose);
        btncheck = (Button) findViewById(R.id.buttonState);
        btnscan.setOnClickListener(this);
        btnstart.setOnClickListener(this);
        btnstop.setOnClickListener(this);
        btncheck.setOnClickListener(this);
    }

    /*打开*/
    public void start(){
        mWifiAdmin.openWifi();
        Toast.makeText(this,"当前Wifi网卡状态为" + mWifiAdmin.checkState(),Toast.LENGTH_SHORT).show();
    }

    /*关闭*/
    public void close(){
        mWifiAdmin.closeWifi();
        Toast.makeText(this,"当前Wifi网卡状态为" + mWifiAdmin.checkState(),Toast.LENGTH_SHORT).show();
    }

    /*检查状态*/
    public void check(){
        Toast.makeText(this,"当前Wifi网卡状态为" + mWifiAdmin.checkState(),Toast.LENGTH_SHORT).show();
    }

    /*扫描网络*/
    public void getAllNetWorkList(){
        //每次扫描之前清空上一次的扫描结果
        if(mStringBuffer != null)
            mStringBuffer = new StringBuffer();
        //开始扫描网络
        mWifiAdmin.startScan();
        list = mWifiAdmin.getWifiList();
        if(list != null){
            for(int i = 0;i < list.size(); i++){
                mScanResult = list.get(i);
                //得到网络的SSID
                mStringBuffer = mStringBuffer.append(mScanResult.SSID).append("  ")
                        .append(mScanResult.BSSID).append("  ")
                        .append(mScanResult.capabilities).append("  ")
                        .append(mScanResult.frequency).append("  ")
                        .append(mScanResult.level).append("  ")
                        .append("\n\n");
            }
            allNetWork.setText("扫描到的所有Wifi网络：\n" + mStringBuffer.toString());
        }

    }

    public void onClick(View v){

        switch (v.getId()){
            case R.id.buttonScan:
                getAllNetWorkList();
                break;
            case R.id.buttonOpen:
                start();
                break;
            case R.id.buttonClose:
                close();
                break;
            case R.id.buttonState:
                check();
                break;
            default:
                break;
        }

    }


}
