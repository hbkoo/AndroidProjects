package com.example.acer.experimenttext2.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.experimenttext2.Class.AESUtils;
import com.example.acer.experimenttext2.R;

import java.security.MessageDigest;


public class MainActivity extends AppCompatActivity {

    private EditText usernameEditText,passwordEditText;
    private Button loginButton;
    private TextView findPassword;
    private CheckBox rememberCheckBox;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private boolean isRemember,isFirstStart;

    private String masterPassword = "android";



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        isFirstStart = sharedPreferences.getBoolean("firstStart",true);
        isRemember = sharedPreferences.getBoolean("remember_password",false);


//        getWindow().getDecorView().post(new Runnable() {
//            @Override
//            public void run() {
//                // 开启延迟加载,实现fragment里面的动画效果
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        setContentView(getLayoutInflater().inflate(R.layout.activity_start,null));
//                    }
//                },8000);
//
////              mHandler.post(new DelayRunnable());//不开启延迟加载
//            }
//        });



        if(isFirstStart ){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
        } else {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            this.setVisible(false);
            Intent intent = new Intent(MainActivity.this,HomePageActivity.class);
            startActivity(intent);
            finish();
        }
        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        loginButton = (Button) findViewById(R.id.loginButton);
        findPassword = (TextView) findViewById(R.id.findPassword);
        rememberCheckBox = (CheckBox) findViewById(R.id.rememberCheckBox);


        if(isRemember){
            try{
                String Name = sharedPreferences.getString("username","");
                String PassWord = sharedPreferences.getString("password","");
                Log.d("解密码:  ",PassWord);
                String s = AESUtils.decrypt(masterPassword,PassWord);
                Log.d("解密后:  ",s);
                usernameEditText.setText(Name);
                passwordEditText.setText(AESUtils.decrypt(masterPassword,PassWord));
                rememberCheckBox.setChecked(true);
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle("实验管理登录界面");
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username,password;
                username = usernameEditText.getText().toString().trim();
                password = passwordEditText.getText().toString().trim();
                if(username.equalsIgnoreCase("123") && password.equalsIgnoreCase("123")){

                    editor = sharedPreferences.edit();

                    if(rememberCheckBox.isChecked()){
                        try{
                            editor.putBoolean("firstStart",false);
                            editor.putString("username",username);
//                            Log.d("真实密码:  ",password);
//                            String s = AESUtils.encrypt(masterPassword,password);
//                            Log.d("加密后:  ",s);
                            editor.putString("password", AESUtils.encrypt(masterPassword,password));
                            editor.putBoolean("remember_password",true);
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                    else {
                        editor.clear();
                    }
                    editor.apply();
                    Intent intent = new Intent(MainActivity.this,HomePageActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(MainActivity.this,"密码或用户名错误",Toast.LENGTH_SHORT).show();
                }
            }
        });

        findPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this,"找回密码",Toast.LENGTH_SHORT).show();

            }
        });
    }


//    //MD5加密 32位
//    public static String MD5(String str) {
//        MessageDigest md5 =null;
//        try {
//            md5 = MessageDigest.getInstance("MD5");
//        } catch(Exception e) {
//            e.printStackTrace();
//            return "";
//        }
//
//        char[] charArray = str.toCharArray();
//        byte[] byteArray =new byte[charArray.length];
//
//        for (int i = 0; i < charArray.length; i++) {
//            byteArray[i] = (byte) charArray[i];
//        }
//        byte[] md5Bytes = md5.digest(byteArray);
//
//        StringBuffer hexValue =new StringBuffer();
//        for (int i = 0; i < md5Bytes.length; i++) {
//            int val = ((int) md5Bytes[i]) &0xff;
//            if (val < 16) {
//                hexValue.append("0");
//            }
//            hexValue.append(Integer.toHexString(val));
//        }
//        return hexValue.toString();
//    }
//
//    // 可逆的加密算法
//    public static String encryptmd5(String str) {
//        char[] a = str.toCharArray();
//        for (int i = 0; i < a.length; i++) {
//            a[i] = (char) (a[i] ^'l');
//        }
//        String s = new String(a);
//        return s;
//    }
//
//    // 加密后解密
//    public static String JM(String inStr) {
//        char[] a = inStr.toCharArray();
//        for (int i = 0; i < a.length; i++) {
//            a[i] = (char) (a[i] ^ 't');
//        }
//        String k = new String(a);
//        return k;
//    }


//    // MD5加码。32位
//    public static String MD5(String inStr) {
//        MessageDigest md5 = null;
//        try {
//            md5 = MessageDigest.getInstance("MD5");
//        } catch (Exception e) {
//            System.out.println(e.toString());
//            e.printStackTrace();
//            return "";
//        }
//        char[] charArray = inStr.toCharArray();
//        byte[] byteArray = new byte[charArray.length];
//
//        for (int i = 0; i < charArray.length; i++)
//            byteArray[i] = (byte) charArray[i];
//
//        byte[] md5Bytes = md5.digest(byteArray);
//
//        StringBuffer hexValue = new StringBuffer();
//
//        for (int i = 0; i < md5Bytes.length; i++) {
//            int val = ((int) md5Bytes[i]) & 0xff;
//            if (val < 16)
//                hexValue.append("0");
//            hexValue.append(Integer.toHexString(val));
//        }
//
//        return hexValue.toString();
//    }
//
//    // 可逆的加密算法
//    public static String KL(String inStr) {
//        // String s = new String(inStr);
//        char[] a = inStr.toCharArray();
//        for (int i = 0; i < a.length; i++) {
//            a[i] = (char) (a[i] ^ 't');
//        }
//        String s = new String(a);
//        return s;
//    }
//
//    // 加密后解密
//    public static String JM(String inStr) {
//        char[] a = inStr.toCharArray();
//        for (int i = 0; i < a.length; i++) {
//            a[i] = (char) (a[i] ^ 't');
//        }
//        String k = new String(a);
//        return k;
//    }

}
