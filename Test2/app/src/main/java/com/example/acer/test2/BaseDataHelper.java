package com.example.acer.test2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 创建数据库
 */

public class BaseDataHelper extends SQLiteOpenHelper {

    private String CREATE_TABLE = "create table people(" +
            "id integer primary key autoincrement," +
            "username text unique," +
            "password text," +
            "tel text," +
            "email text," +
            "sex text)";

    private Context context;

    public BaseDataHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
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
