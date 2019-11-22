package com.example.acer.phone_book;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Toast toast;
    Button btnCreat,btnOpen,btnPrevious,btnNext,btnAdd,btnUpdate,btnDelete,btnClose;
    static EditText etName,etTelephone,etAddress,etMailAddress;
    DBConnection helper;
    SQLiteDatabase db;
    Cursor cursor;
    int id_this;
    Bundle savedInstanceState;
    //数据库名称及结构
    static String TABLE_NAME = "Users_info";
    static String ID = "_id";
    static String USER_NAME = "user_name";
    static String ADDRESS = "address";
    static String TELEPHONE = "telephone";
    static String MAIL_ADDRESS = "mail_address";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnCreat = (Button) findViewById(R.id.buttonCreat);
        btnOpen = (Button) findViewById(R.id.buttonOpen);
        btnPrevious = (Button) findViewById(R.id.buttonPrevious);
        btnNext = (Button) findViewById(R.id.buttonNext);
        btnAdd = (Button) findViewById(R.id.buttonAdd);
        btnUpdate = (Button) findViewById(R.id.buttonUpdate);
        btnDelete = (Button) findViewById(R.id.buttonDelete);
        btnClose = (Button) findViewById(R.id.buttonClose);
        etName = (EditText) findViewById(R.id.etName);
        etTelephone = (EditText) findViewById(R.id.etTelephone);
        etAddress = (EditText) findViewById(R.id.etAddress);
        etMailAddress = (EditText) findViewById(R.id.etMailAddress);

        btnCreat.setOnClickListener(new mClick());
        btnOpen.setOnClickListener(new mClick());
        btnPrevious.setOnClickListener(new mClick());
        btnNext.setOnClickListener(new mClick());
        btnAdd.setOnClickListener(new mClick());
        btnUpdate.setOnClickListener(new mClick());
        btnDelete.setOnClickListener(new mClick());
        btnClose.setOnClickListener(new mClick());
    }


    class mClick implements View.OnClickListener{

        public void onClick(View arg0){

            switch (arg0.getId()){

                case R.id.buttonCreat:
                    helper = new DBConnection(MainActivity.this);
                    db = helper.getWritableDatabase();
//                    String sql = "create table " + TABLE_NAME + "(" + "_id int," + "user_name text not null,"
//                            + "address text not null," + "mail_address text not null)";
//                    db = openOrCreateDatabase("PhoneBook.db", Context.MODE_PRIVATE,null);
//                    db.execSQL(sql);
                    break;
                case R.id.buttonOpen:
                    db = openOrCreateDatabase("PhoneBook.db", Context.MODE_PRIVATE,null);
                    cursor = db.query("Users",null,null,null,null,null,null);
                    cursor.moveToNext();
                    btnPrevious.setClickable(true);
                    btnNext.setClickable(true);
                    btnUpdate.setClickable(true);
                    btnDelete.setClickable(true);
                    break;
                case R.id.buttonPrevious:
                    if(!cursor.isFirst())
                        cursor.moveToPrevious();
                    else
                        Toast.makeText(getApplicationContext(),"已经是第一条记录！",Toast.LENGTH_SHORT).show();
                    datashow();
                    break;
                case R.id.buttonNext:
                    if(!cursor.isLast())
                        cursor.moveToNext();
                    else
                        Toast.makeText(getApplicationContext(),"已经是第一条记录！",Toast.LENGTH_SHORT).show();
                    datashow();
                    break;
                case R.id.buttonAdd:
                    add();
                    onCreate(savedInstanceState);
                    break;
                case R.id.buttonUpdate:
                    update();
                    onCreate(savedInstanceState);
                    break;
                case R.id.buttonDelete:
                    delete();
                    onCreate(savedInstanceState);
                    break;
                case R.id.buttonClose:
                    cursor.close();
                    etName.setText("数据库已关闭");
                    etTelephone.setText("数据库已关闭");
                    etAddress.setText("数据库已关闭");
                    etMailAddress.setText("数据库已关闭");
                    btnPrevious.setClickable(false);
                    btnNext.setClickable(false);
                    btnDelete.setClickable(false);
                    btnUpdate.setClickable(false);
                    break;

            }

        }

    }

    void datashow(){

        id_this = Integer.parseInt(cursor.getString(0));
        String user_name_this = cursor.getString(1);
        String telephone_this = cursor.getString(2);
        String address_this = cursor.getString(3);
        String mail_address_this = cursor.getString(4);
        etName.setText(user_name_this);
        etTelephone.setText(telephone_this);
        etAddress.setText(address_this);
        etMailAddress.setText(mail_address_this);

    }

    void add(){

        ContentValues vaules = new ContentValues();
        vaules.put(USER_NAME,MainActivity.etName.getText().toString());
        vaules.put(TELEPHONE,MainActivity.etTelephone.getText().toString());
        vaules.put(ADDRESS,MainActivity.etAddress.getText().toString());
        vaules.put(MAIL_ADDRESS,MainActivity.etMailAddress.getText().toString());
        SQLiteDatabase db2 = helper.getWritableDatabase();
        db2.insert("Users",null,vaules);
        db2.close();

    }

    void update(){

        ContentValues vaules = new ContentValues();
        vaules.put(USER_NAME,MainActivity.etName.getText().toString());
        vaules.put(TELEPHONE,MainActivity.etTelephone.getText().toString());
        vaules.put(ADDRESS,MainActivity.etAddress.getText().toString());
        vaules.put(MAIL_ADDRESS,MainActivity.etMailAddress.getText().toString());
        String where = ID + "=" + id_this;
        SQLiteDatabase db1 = helper.getWritableDatabase();
        db1.update(TABLE_NAME,vaules,where,null);
        db1.close();

    }

    void delete(){

        SQLiteDatabase db3 = helper.getWritableDatabase();
        String where = ID + "=" + id_this;
        db3.delete(TABLE_NAME,where,null);
        db3.close();

    }

}
