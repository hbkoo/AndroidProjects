package com.example.acer.demo.Tools;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库
 */

public class BaseDBHelper extends SQLiteOpenHelper {

    static String CREATE_TABLE = "create table t_memory(" +
            "id integer primary key autoincrement," +
            "mem_time text," +
            "mem_content text)";
    private Context context;

    public BaseDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
