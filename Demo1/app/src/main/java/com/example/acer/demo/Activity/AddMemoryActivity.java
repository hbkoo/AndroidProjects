package com.example.acer.demo.Activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.demo.R;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;

public class AddMemoryActivity extends AppCompatActivity {

    static String FILE_NAME = "memory";

    private Boolean isChange = false;

    TextView back_tv, save_tv;
    TextView time_tv;
    EditText content_et;
    String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_memory);
        back_tv = (TextView) findViewById(R.id.add_memo_back);
        save_tv = (TextView) findViewById(R.id.add_memo_save);
        time_tv = (TextView) findViewById(R.id.add_memo_time);
        content_et = (EditText) findViewById(R.id.add_memo_content);
        time = getCurrentTime();
        back_tv.setOnClickListener(new mClick());
        save_tv.setOnClickListener(new mClick());
    }

    private class mClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.add_memo_back:
                    if (!content_et.getText().toString().equals("")) {
                        IsSave();
                    } else {
                        Intent intent = new Intent();
                        intent.putExtra("isAdd", isChange);
                        setResult(0, intent);
                        finish();
                    }
                    break;
                case R.id.add_memo_save:
                    if (content_et.getText().toString().equals("")) {
                        Toast.makeText(AddMemoryActivity.this, "请输入要保存的记录！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (SaveDB()) {
                        isChange = true;
                        Toast.makeText(AddMemoryActivity.this, "保存成功！", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddMemoryActivity.this, "保存失败！", Toast.LENGTH_SHORT).show();
                    }
                    content_et.setText("");
                    break;
            }
        }
    }

    //保存数据库信息
    private Boolean SaveDB() {
        Boolean isSuccess = true;
        SQLiteDatabase database = MemoryActivity.database;
        ContentValues values = new ContentValues();
        values.put("mem_time", time);
        values.put("mem_content", content_et.getText().toString());
        long tag = database.insert("t_memory", null, values);
        if (tag == -1) {
            isSuccess = false;
        }
        return isSuccess;
    }

    //保存文件数据
    private Boolean SaveFile() {
        Boolean isSuccess = false;
        String content;
        FileOutputStream outputStream;
        BufferedWriter writer = null;
        try {
            outputStream = openFileOutput(FILE_NAME, Context.MODE_APPEND);
            writer = new BufferedWriter(new OutputStreamWriter(outputStream));
            content = time + ":" + content_et.getText().toString() + "\n";
            writer.write(content);
            isSuccess = true;
        } catch (IOException e) {
            e.printStackTrace();
            isSuccess = false;
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                isSuccess = false;
            }
        }
        return isSuccess;
    }

    private String getCurrentTime() {
        String time;
        Calendar calendar = Calendar.getInstance();
        time = calendar.get(Calendar.YEAR) + "年" +
                (calendar.get(Calendar.MONTH) + 1) + "月" +
                calendar.get(Calendar.DAY_OF_MONTH) + "日";
        time_tv.setText(time);
        return time;
    }

    private void IsSave() {
        View view = getLayoutInflater().inflate(R.layout.delete_dialog, null, false);
        TextView inform_content = (TextView) view.findViewById(R.id.inform_content);
        TextView continues = (TextView) view.findViewById(R.id.cancel_tv);
        TextView finish = (TextView) view.findViewById(R.id.delete_tv);
        inform_content.setText("放弃添加吗？");
        continues.setText("继续编辑");
        finish.setText("放弃添加");


        AlertDialog.Builder builder = new AlertDialog.Builder(AddMemoryActivity.this);
        builder.setCancelable(false);
        builder.setView(view);
        final AlertDialog dialog = builder.show();

        continues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("isAdd", isChange);
                setResult(1, intent);
                finish();
            }
        });


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            if (!content_et.getText().toString().equals("")) {
                IsSave();
            } else {
                Intent intent = new Intent();
                intent.putExtra("isAdd", isChange);
                setResult(0, intent);
                return super.onKeyDown(keyCode, event);
            }
        }
        return true;
    }
}
