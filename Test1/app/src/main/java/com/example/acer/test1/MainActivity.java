package com.example.acer.test1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mylibrary.BindView;

public class MainActivity extends AppCompatActivity {

    private static String FILE_NAME = "data";
    private static String NAME_KEY = "name";
    private static String PASSWORD_KEY = "password";
    private static String TEL_KEY = "tel";
    private static String EMAIL_KEY = "email";
    private static String SEX_KEY = "sex";

    private String[] gender_list = new String[]{"男", "女"};

    private EditText name_et, password_et, tel_et, email_et;
    @BindView(R.id.sex_spinner)
    private Spinner sexSpinner;
    private ImageView password_see;
    boolean isSee = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name_et = (EditText) findViewById(R.id.user_name_et);
        password_et = (EditText) findViewById(R.id.password_et);
        tel_et = (EditText) findViewById(R.id.tel_et);
        email_et = (EditText) findViewById(R.id.email_et);
        sexSpinner = (Spinner) findViewById(R.id.sex_spinner);
        password_see = (ImageView) findViewById(R.id.password_see);
        Button save_btn = (Button) findViewById(R.id.save_btn);
        Button clear_btn = (Button) findViewById(R.id.clear_btn);
        Button read_btn = (Button) findViewById(R.id.read_btn);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, gender_list);
        sexSpinner.setAdapter(adapter);

        save_btn.setOnClickListener(new mClick());
        read_btn.setOnClickListener(new mClick());
        clear_btn.setOnClickListener(new mClick());
        password_see.setOnClickListener(new mClick());
//        password_see.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    password_et.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
//                }
////                if (event.getAction() == MotionEvent.ACTION_UP) {
////                    password_et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
////                }
//                return false;
//            }
//        });

    }

    private class mClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.save_btn:
                    SaveFile();
                    break;
                case R.id.read_btn:
                    ReadFile();
                    break;
                case R.id.clear_btn:
                    name_et.setText("");
                    password_et.setText("");
                    tel_et.setText("");
                    email_et.setText("");
                    sexSpinner.setSelection(0);
                    Toast.makeText(MainActivity.this, "清除成功！", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.password_see:
                    if (!isSee) {
                        isSee = true;
                        password_et.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        password_et.setSelection(password_et.getText().length());
                    } else {
                        isSee = false;
                        password_et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        password_et.setSelection(password_et.getText().length());
                    }
                    break;
            }
        }
    }

    private void SaveFile() {
        String name = name_et.getText().toString();
        String password = password_et.getText().toString();
        String tel = tel_et.getText().toString();
        String email = email_et.getText().toString();
        if (name.equals("") || password.equals("") || tel.equals("") || email.equals("")) {
            Toast.makeText(MainActivity.this, "请填写完整的信息！", Toast.LENGTH_SHORT).show();
            return;
        }
        SharedPreferences.Editor editor = getSharedPreferences(FILE_NAME, MODE_PRIVATE).edit();
        editor.putString(NAME_KEY, name);
        editor.putString(PASSWORD_KEY, password);
        editor.putString(TEL_KEY, tel);
        editor.putString(EMAIL_KEY, email);
        editor.putInt(SEX_KEY, sexSpinner.getSelectedItemPosition());
        editor.apply();
        Toast.makeText(MainActivity.this, "保存成功！", Toast.LENGTH_SHORT).show();
    }

    private void ReadFile() {
        SharedPreferences preferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        String name = preferences.getString(NAME_KEY, "");
        String password = preferences.getString(PASSWORD_KEY, "");
        String tel = preferences.getString(TEL_KEY, "");
        String email = preferences.getString(EMAIL_KEY, "");
        int sex = preferences.getInt(SEX_KEY, 0);
        if (name.equals("")) {
            Toast.makeText(MainActivity.this, "当前还未保存信息！", Toast.LENGTH_SHORT).show();
            return;
        }
        password_et.setInputType(InputType.TYPE_NULL);
        name_et.setText(name);
        password_et.setText(password);
        tel_et.setText(tel);
        email_et.setText(email);
        sexSpinner.setSelection(sex);
        password_et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        Toast.makeText(MainActivity.this, "读取成功！", Toast.LENGTH_SHORT).show();

    }

}
