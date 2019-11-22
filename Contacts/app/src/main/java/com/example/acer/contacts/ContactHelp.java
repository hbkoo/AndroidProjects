package com.example.acer.contacts;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 对通讯录中的联系人进行各种操作
 */

public class ContactHelp {

    /**
     * 添加一个联系人到通讯录中
     */
    public static boolean addContact(Context context,Contact contact) {

        Uri uri = ContactsContract.Data.CONTENT_URI;

        String name = contact.getName();
        String telephone = contact.getTelephone();

        ContentResolver resolver = context.getContentResolver();
        ContentValues values = new ContentValues();

        //首先获取到添加地方的id
        Uri iduri = resolver.insert(ContactsContract.RawContacts.CONTENT_URI,values);
        long newId = ContentUris.parseId(iduri);

        //添加姓名
        values.put(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,name);
        values.put(ContactsContract.Data.RAW_CONTACT_ID,newId);
        values.put(ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        resolver.insert(uri,values);

        //添加电话号码
        values.clear();
        values.put(ContactsContract.CommonDataKinds.Phone.NUMBER,telephone);
        values.put(ContactsContract.Data.RAW_CONTACT_ID,newId);
        values.put(ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        resolver.insert(uri,values);

        return true;
    }

    /**
     * 删除某个联系人 TODO 有bug
     */
    public static boolean deleteContact(Context context,String contactID) {

        Uri deleteUri = ContactsContract.RawContacts.CONTENT_URI;

        ContentResolver resolver = context.getContentResolver();
        int num = resolver.delete(deleteUri, ContactsContract.Contacts._ID + "=?",
                new String[] {contactID});
        return num > 0;
    }

    /**
     * 修改某个联系人 TODO 有bug
     */
    public static boolean updateContact(Context context,Contact contact) {

        //获取通讯录数据data表的uri
        Uri updateUri = ContactsContract.Data.CONTENT_URI;

        String id = contact.getId();
        String name = contact.getName();
        String telephone = contact.getTelephone();

        ContentResolver resolver = context.getContentResolver();
        ContentValues values = new ContentValues();

        //更新条件
        String where = ContactsContract.Data.MIMETYPE + "=?," +
                ContactsContract.Data.RAW_CONTACT_ID + "=?";
        String[] selectionArgs = null;

        //更新姓名
        values.put(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,name);
        selectionArgs = new String[]
                {ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE,id};
        int num1 = resolver.update(updateUri,values,where,selectionArgs);

        //更新电话号码
        values.clear();
        values.put(ContactsContract.CommonDataKinds.Phone.NUMBER,telephone);
        selectionArgs = new String[]
                {ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,id};
        int num2 = resolver.update(updateUri,values,where,selectionArgs);

        return (num1 > 0) && (num2 > 0);

    }

    /**
     * 查询联系人信息
     */
    public static List<Contact> queryContact(Context context) {

        //Uri queryUri = ContactsContract.Data.CONTENT_URI;

        Uri queryUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        //Uri queryUri = ContactsContract.Contacts.CONTENT_URI;

        List<Contact> contacts = new ArrayList<>();

        ContentResolver resolver = context.getContentResolver();

        Cursor cursor = resolver.query(queryUri,null,null,null,null);

        if (cursor == null) {
            return null;
        }

        while (cursor.moveToNext()) {
            int idIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.RAW_CONTACT_ID);
            int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            Contact contact = new Contact();
            contact.setId(cursor.getString(idIndex));
            contact.setName(cursor.getString(nameIndex));
            contact.setTelephone(cursor.getString(phoneIndex));
//            contact.setId(cursor.getString(cursor
//                    .getColumnIndex(ContactsContract.Contacts._ID)));
////            ContactsContract.Data.RAW_CONTACT_ID
//            contact.setName(cursor.getString(cursor
//                    .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
//            //ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME
//            contact.setTelephone(cursor.getString(cursor
//                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
//            //ContactsContract.CommonDataKinds.Phone.NUMBER
            String selection = ContactsContract.Data.RAW_CONTACT_ID + "=?" +
                    ContactsContract.Data.MIMETYPE + "=?";

//            Cursor phoneCursor = resolver.query(ContactsContract.Data.CONTENT_URI,null,selection,
//                    new String[] {contact.getId(), ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE},null);
//            if (phoneCursor.moveToNext()) {
//                String phone = phoneCursor.getString(phoneCursor
//                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                contact.setTelephone(phone);
//                break;
//            }

            contacts.add(contact);
        }
        cursor.close();
        Collections.reverse(contacts);
        return contacts;
    }


}
