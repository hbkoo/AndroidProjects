package com.example.acer.demo.Activity;

import android.content.ContentValues;
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
import com.example.acer.demo.Tools.Memory;

public class ChangeMemoryActivity extends AppCompatActivity {

    TextView back_tv, save_tv;
    TextView time_tv;
    EditText content_et;
    String content;
    Memory memory;
    private Boolean isChange = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_memory);

        Bundle bundle = getIntent().getBundleExtra("bundle");
        memory = (Memory) bundle.getSerializable("memory");

        back_tv = (TextView) findViewById(R.id.memo_back);
        save_tv = (TextView) findViewById(R.id.memo_change);
        time_tv = (TextView) findViewById(R.id.memo_time);
        content_et = (EditText) findViewById(R.id.memo_content);
        back_tv.setOnClickListener(new mClick());
        save_tv.setOnClickListener(new mClick());

        if (memory != null) {
            content = memory.getContent();
            time_tv.setText(memory.getTime());
            content_et.setText(content);
            content_et.setSelection(content.length());
        }


    }

    private class mClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.memo_back:
                    if (!content.equals(content_et.getText().toString())) {
                        IsCancel();
                    } else {
                        Intent intent = new Intent();
                        intent.putExtra("isChange", isChange);
                        setResult(1, intent);
                        finish();
                    }
                    break;
                case R.id.memo_change:
                    if (!content.equals(content_et.getText().toString())) {
                        content = content_et.getText().toString();
                        SQLiteDatabase database = MemoryActivity.database;
                        ContentValues values = new ContentValues();
                        values.put("mem_time", time_tv.getText().toString());
                        values.put("mem_content", content);
                        int count = database.update("t_memory", values, "id = ?", new String[]{memory.getId()});
                        if (count > 0) {
                            isChange = true;
                            Toast.makeText(ChangeMemoryActivity.this, "修改成功!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ChangeMemoryActivity.this, "修改失败!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
            }
        }
    }

    private void IsCancel() {
        View view = getLayoutInflater().inflate(R.layout.delete_dialog, null, false);
        TextView inform_content = (TextView) view.findViewById(R.id.inform_content);
        TextView continues = (TextView) view.findViewById(R.id.cancel_tv);
        TextView finish = (TextView) view.findViewById(R.id.delete_tv);
        inform_content.setText("放弃修改吗？");
        continues.setText("继续编辑");
        finish.setText("放弃编辑");

        AlertDialog.Builder builder = new AlertDialog.Builder(ChangeMemoryActivity.this);
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
                intent.putExtra("isChange", isChange);
                setResult(1, intent);
                finish();
            }
        });


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            if (!content.equals(content_et.getText().toString())) {
                IsCancel();
            } else {
                Intent intent = new Intent();
                intent.putExtra("isChange", isChange);
                setResult(1, intent);
                return super.onKeyDown(keyCode, event);
            }
        }
        return true;
    }
}
