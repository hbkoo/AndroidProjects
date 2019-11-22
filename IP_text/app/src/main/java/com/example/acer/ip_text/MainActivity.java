package com.example.acer.ip_text;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Socket socket;
    DataInputStream dis;
    DataOutputStream dos;
    private Button btn;
    public TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.button1);
        tv = (TextView) findViewById(R.id.textView1);
        btn.setOnClickListener(this);
    }

    public void onClick(View v){
        //Client();
    }

//    public void Client(){
//
//        try{
//            socket = new Socket("192.168.1.133",4321);
//        }catch (Exception ioe){
//            System.out.println("socket err...");
//            Toast.makeText(this,"socket err...",Toast.LENGTH_SHORT);
//        }
//        try{
//            dis = new DataInputStream(socket.getInputStream());
//            dos = new DataOutputStream(socket.getOutputStream());
//
//        }catch (IOException ioe){
//            System.out.println("DataStream create err...");
//            Toast.makeText(this,"DataStream create err...",Toast.LENGTH_SHORT);
//        }
//        ReadStr();//读取数据
//
//        try{
//            Thread.sleep(20000);
//            String str = "Hello,Here is the Android socketClient!!!!!";
//            WriteString(str);
//            dis.close();
//            socket.close();
//
//        }catch (Exception ioe){
//            System.out.println("socket close() err...");
//        }
//
//    }
//
//    //读取数据
//    public void ReadStr(){
//        try{
//            String str;
//            dis = new DataInputStream(socket.getInputStream());
//            if((str = dis.readUTF()) != null){
//                Toast.makeText(this,str,Toast.LENGTH_SHORT);
//                tv.setText(str);
//            }
//        }catch (IOException ioe){
//            System.out.println("ReadStr() err...");
//        }
//    }
//
//    //发送数据
//    public void WriteString(String str){
//        try{
//            dos.writeUTF(str);
//            dos.close();
//            dis.close();
//            socket.close();
//
//        }catch (IOException ioe){
//            System.out.println("WriteString() err...");
//        }
//    }

}
