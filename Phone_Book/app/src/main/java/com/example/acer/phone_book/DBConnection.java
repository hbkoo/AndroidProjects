package com.example.acer.phone_book;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBConnection extends SQLiteOpenHelper{

    static final String Database_Name = "PhoneBook.db";
    static final int Database_Version = 1;
    SQLiteDatabase db;
    public int id_this;
    Cursor cursor;

    //定义数据库名称及结构
    static String TABLE_NAME = "Users2";
    static String ID = "_id";
    static String USER_NAME = "user_name";
    static String ADDRESS = "address";
    static String TELEPHONE = "telephone";
    static String MAIL_ADDRESS = "mail_address";

    DBConnection(Context context){
        super(context,Database_Name,null,Database_Version);
    }

    public void onCreate(SQLiteDatabase database){

        String sql = "CREATE TABLE" + TABLE_NAME + "(" + ID + "INTEGER primary key autoincrement, "
                + USER_NAME + "text not null, " + TELEPHONE + "text not null, " + ADDRESS
                + "text not null, " + MAIL_ADDRESS + "text not null " + ");" ;
        database.execSQL(sql);

    }

    public void onUpgrade(SQLiteDatabase database,int oldVersion,int newVersion){

    }



}