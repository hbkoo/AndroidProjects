package com.example.acer.test2;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String file_name = "data";

    private Spinner username_spinner;
    private Button save_btn, add_btn, delete_btn, clear_btn;
    private EditText username_et, password_et, email_et, tel_et;
    private RadioButton boy_radio, girl_radio;
    private ImageView password_see;

    private DataUnits units;
    private List<People> list = new ArrayList<>();
    private List<String> names = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    private int current = 0;

    private boolean isSee = false;

    private String username, password, tel, email, sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Init();
        LoadData();

    }

    private void Init() {
        BaseDataHelper helper = new BaseDataHelper(MainActivity.this, file_name, null, 1);
        units = new DataUnits(helper.getWritableDatabase());
        username_spinner = (Spinner) findViewById(R.id.user_name_spinner);
        username_et = (EditText) findViewById(R.id.user_name_et);
        password_et = (EditText) findViewById(R.id.password_et);
        tel_et = (EditText) findViewById(R.id.tel_et);
        email_et = (EditText) findViewById(R.id.email_et);
        password_see = (ImageView) findViewById(R.id.password_see);
        boy_radio = (RadioButton) findViewById(R.id.boy_radio);
        girl_radio = (RadioButton) findViewById(R.id.girl_radio);
        password_see = (ImageView) findViewById(R.id.password_see);
        add_btn = (Button) findViewById(R.id.add_btn);
        save_btn = (Button) findViewById(R.id.save_btn);
        clear_btn = (Button) findViewById(R.id.clear_btn);
        delete_btn = (Button) findViewById(R.id.delete_btn);
        username_spinner.setOnItemSelectedListener(new mSelected());
        password_see.setOnClickListener(new mClick());
        add_btn.setOnClickListener(new mClick());
        save_btn.setOnClickListener(new mClick());
        clear_btn.setOnClickListener(new mClick());
        delete_btn.setOnClickListener(new mClick());
        boy_radio.setChecked(true);
    }

    private void LoadData() {
        Query();
        if (list.size() == 0) {
            delete_btn.setEnabled(false);
            save_btn.setEnabled(false);
        }
        names.clear();
        for (People people : list) {
            names.add(people.getUsername());
        }

        adapter = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_spinner_dropdown_item, names);
        username_spinner.setAdapter(adapter);
        SetData(0);
    }

    private class mClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.add_btn:
                    Add();
                    break;
                case R.id.save_btn:
                    Save();
                    break;
                case R.id.clear_btn:
                    Clear();
                    break;
                case R.id.delete_btn:
                    Delete();
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
                case R.id.user_name_spinner:
                    SetData(current);
                    break;
            }
        }
    }

//    private class mItemClick implements AdapterView.OnItemClickListener{
//
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            current = position;
//            SetData(position);
//        }
//    }

    private class mSelected implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//            try {
//                //Field field = AdapterView.class.getDeclaredField("mOldSelectedPosition");
//                //field.setAccessible(true);
//
//                //field.setInt(username_spinner,AdapterView.INVALID_POSITION);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            current = position;
            SetData(position);

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private void Add() {
        String username, password, tel, email, sex;
        username = username_et.getText().toString();
        password = password_et.getText().toString();
        tel = tel_et.getText().toString();
        email = email_et.getText().toString();
        if (username.equals("") || password.equals("") ||
                tel.equals("") || email.equals("")) {
            Toast.makeText(MainActivity.this, "请填写完整的信息!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (boy_radio.isChecked()) {
            sex = "男";
        } else {
            sex = "女";
        }
        People people = new People(0, username, password, tel, email, sex);
        if (units.Insert(people)) {
            if (!delete_btn.isEnabled()) {
                delete_btn.setEnabled(true);
                save_btn.setEnabled(true);
            }
            names.add(username);
            adapter.notifyDataSetChanged();
            username_spinner.setSelection(names.size() - 1);
            list.add(people);
            current = list.size() - 1;
            Toast.makeText(MainActivity.this, "新增成功!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "该姓名已经存在!", Toast.LENGTH_SHORT).show();
        }


    }

    private void Save() {
        if (isChange()) {
            if (username.equals("") || password.equals("") ||
                    tel.equals("") || email.equals("")) {
                Toast.makeText(MainActivity.this, "填写的信息不能为空！", Toast.LENGTH_SHORT).show();
                return;
            }
            People people = list.get(current);
            people.setUsername(username);
            people.setEmail(email);
            people.setPassword(password);
            people.setTel(tel);
            people.setSex(sex);
            if (units.Update(people)) {
                if (!names.get(current).equals(username)) {
                    names.remove(current);
                    names.add(current, username);
                    adapter.notifyDataSetChanged();
                }
                list.remove(current);
                list.add(current, people);
                Toast.makeText(MainActivity.this, "修改成功!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "修改失败!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(MainActivity.this, "请修改信息后再点击保存!", Toast.LENGTH_SHORT).show();
        }
    }

    private void Query() {
        list.clear();
        list = units.Query();
    }

    private void Delete() {

        View view1 = getLayoutInflater().inflate(R.layout.delete_dialog, null, false);
        TextView cancel = (TextView) view1.findViewById(R.id.cancel_tv);
        final TextView delete = (TextView) view1.findViewById(R.id.delete_tv);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(view1);
        final AlertDialog dialog = builder.show();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (units.Delete(list.get(current))) {
                    list.remove(current);
                    names.remove(current);
                    adapter.notifyDataSetChanged();
                    if (list.size() == 0) {
                        Clear();
                        delete_btn.setEnabled(false);
                        save_btn.setEnabled(false);
                    }
                    username_spinner.setSelection(0);
                    SetData(0);
                    Toast.makeText(MainActivity.this, "删除成功!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "删除失败!", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });

    }

    private void Clear() {
        add_btn.setEnabled(true);
        delete_btn.setEnabled(false);
        save_btn.setEnabled(false);
        username_et.setText("");
        password_et.setText("");
        tel_et.setText("");
        email_et.setText("");
        boy_radio.setChecked(true);
        girl_radio.setChecked(false);
    }

    private void SetData(int position) {
        if (list.size() == 0) {
            Clear();
            return;
        }
        add_btn.setEnabled(false);
        if (!delete_btn.isEnabled()) {
            delete_btn.setEnabled(true);
            save_btn.setEnabled(true);
        }
        People people = list.get(position);
        username_et.setText(people.getUsername());
        password_et.setText(people.getPassword());
        tel_et.setText(people.getTel());
        email_et.setText(people.getEmail());
        if (people.getSex().equals("男")) {
            boy_radio.setChecked(true);
            girl_radio.setChecked(false);
        } else {
            boy_radio.setChecked(false);
            girl_radio.setChecked(true);
        }
    }

    private boolean isChange() {
        boolean isChange = true;
        People people = list.get(current);

        username = username_et.getText().toString();
        password = password_et.getText().toString();
        tel = tel_et.getText().toString();
        email = email_et.getText().toString();
        if (boy_radio.isChecked()) {
            sex = "男";
        } else {
            sex = "女";
        }
        if (username.equals(people.getUsername()) && password.equals(people.getPassword())
                && tel.equals(people.getTel()) && email.equals(people.getEmail())
                && sex.equals(people.getSex())) {
            isChange = false;
        }
        return isChange;
    }

}
