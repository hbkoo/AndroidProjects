package com.example.acer.test2;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * 对数据库的操作
 */

public class DataUnits {

    private String table_name = "people";

    private SQLiteDatabase database;

    public DataUnits(SQLiteDatabase database) {
        this.database = database;
    }

    public boolean Insert(People people) {
        boolean isSuccess = true;

        ContentValues values = new ContentValues();
        values.put("username", people.getUsername());
        values.put("password", people.getPassword());
        values.put("tel", people.getTel());
        values.put("email", people.getEmail());
        values.put("sex", people.getSex());
        long tag = database.insert(table_name, null, values);
        if (tag == -1) {
            isSuccess = false;
        }
        return isSuccess;
    }

    public List<People> Query() {
        List<People> list = new ArrayList<>();
        Cursor cursor = database.query(table_name, null, null, null, null, null, null);
        People people;
        int id;
        String username, password, tel, email, sex;
        while (cursor.moveToNext()) {
            id = cursor.getInt(cursor.getColumnIndex("id"));
            username = cursor.getString(cursor.getColumnIndex("username"));
            password = cursor.getString(cursor.getColumnIndex("password"));
            tel = cursor.getString(cursor.getColumnIndex("tel"));
            email = cursor.getString(cursor.getColumnIndex("email"));
            sex = cursor.getString(cursor.getColumnIndex("sex"));
            people = new People(id, username, password, tel, email, sex);
            list.add(people);
        }
        cursor.close();
        return list;
    }

    public boolean Delete(People people) {
        boolean isSuccess = false;

        int tag = database.delete(table_name, "id = ? or username = ?",
                new String[]{String.valueOf(people.getId()), people.getUsername()});
        if (tag > 0) {
            isSuccess = true;
        }
        return isSuccess;
    }

    public boolean Update(People people) {
        boolean isSuccess = true;

        ContentValues values = new ContentValues();
        values.put("username", people.getUsername());
        values.put("password", people.getPassword());
        values.put("tel", people.getTel());
        values.put("email", people.getEmail());
        values.put("sex", people.getSex());

        int tag = database.update(table_name, values, "id = ?",
                new String[]{String.valueOf(people.getId())});
        if (tag == -1) {
            isSuccess = false;
        }
        return isSuccess;
    }

}
