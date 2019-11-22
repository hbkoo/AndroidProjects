package com.example.acer.encrypt;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends Activity implements OnClickListener, TextToSpeech.OnInitListener {
    private TextView tvTip = null;
    private EditText etKey = null;
    private EditText etStr = null;
    private Button btnEncrypt = null;
    private Button btnDecrypt = null;
    private Button btnRead = null;

    private TextToSpeech textToSpeech;
    //
    String src = null;
    String key = null;
    String dest = null;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTip = (TextView) findViewById(R.id.tvTip);
        etKey = (EditText) findViewById(R.id.etKey);
        etStr = (EditText) findViewById(R.id.etStr);
        btnEncrypt = (Button) findViewById(R.id.btnEncrypt);
        btnEncrypt.setOnClickListener(this);
        btnDecrypt = (Button) findViewById(R.id.btnDecrypt);
        btnDecrypt.setOnClickListener(this);
        btnRead = (Button) findViewById(R.id.btnRead);
        btnRead.setOnClickListener(this);
        btnEncrypt.setEnabled(true);
        btnDecrypt.setEnabled(false);

        textToSpeech = new TextToSpeech(this,this);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v == btnEncrypt) {
//            NotificationManager manager = (NotificationManager) MainActivity.this
//                    .getSystemService(Context.NOTIFICATION_SERVICE);
//            Notification notification = new NotificationCompat.Builder(MainActivity.this)
//                    .setContentTitle("通知")
//                    .setContentText("明天有实验课")
//                    .setWhen(System.currentTimeMillis())
//                    .setVibrate(new long[] {0,1000,1000,1000})
//                    .setLights(Color.GREEN,1000,1000)
//                    .setAutoCancel(true).build();
//            manager.notify(1,notification);
            src = etStr.getText().toString().trim();
            key = etKey.getText().toString().trim();
            if (!src.equals("") && !key.equals("")) {
                try {
                    dest = AESCipher.encrypt(key, src);
                    tvTip.setText("Encrypted:");
                    etStr.setText(dest);
                    btnEncrypt.setEnabled(false);
                    btnDecrypt.setEnabled(true);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        } else if (v == btnDecrypt) {
            src = etStr.getText().toString().trim();
            key = etKey.getText().toString().trim();
            if (!src.equals("") && !key.equals("")) {
                try {
                    dest = AESCipher.decrypt(key, src);
                    tvTip.setText("Decrypted:");
                    etStr.setText(dest);
                    btnDecrypt.setEnabled(false);
                    btnEncrypt.setEnabled(true);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }else{
                tvTip.setText("Source:");
                btnDecrypt.setEnabled(false);
                btnEncrypt.setEnabled(true);
            }
        } else if(v == btnRead) {
            textToSpeech.speak(etStr.getText().toString(),TextToSpeech.QUEUE_FLUSH,null);
        }
    }

    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS) {
            int result = textToSpeech.setLanguage(Locale.US);
            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Toast.makeText(this,"数据丢失或不支持",Toast.LENGTH_SHORT).show();
            }
        }
    }
}

//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//    }
//}
