package com.example.acer.wifi_test;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.util.List;

public class WifiTest {

    private WifiManager mWifiManager;//定义WifiManager对象
    private WifiInfo mWifiInfo;//定义WifiInfo对象
    private List<ScanResult> mWifiList;//扫描出的网络连接列表
    private List<WifiConfiguration> mWifiConfiguration;//网络连接列表
    WifiManager.WifiLock mWifiLock;//定义一个WifiLock
    public WifiTest(Context context){
        mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);//取得WifiManager对象
        mWifiInfo = mWifiManager.getConnectionInfo();//取得WifiInfo对象
    }

    /*打开Wifi*/
    public void openWifi(){
        if(!mWifiManager.isWifiEnabled())
            mWifiManager.setWifiEnabled(true);
    }

    /*关闭Wifi*/
    public void closeWifi(){
        if(mWifiManager.isWifiEnabled())
            mWifiManager.setWifiEnabled(false);
    }

    /*检查当前Wifi状态*/
    public int checkState(){
        return mWifiManager.getWifiState();
    }

    /*检查网络*/
    public void startScan(){
        mWifiManager.startScan();
        mWifiList = mWifiManager.getScanResults();//得到扫描结果
        mWifiConfiguration = mWifiManager.getConfiguredNetworks();//得到配置好的网络连接
    }

    /*得到网络列表*/
    public List<ScanResult> getWifiList(){
        return mWifiList;
    }

}