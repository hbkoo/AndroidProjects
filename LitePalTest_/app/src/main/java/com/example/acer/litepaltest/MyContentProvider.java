package com.example.acer.litepaltest;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import org.litepal.crud.DataSupport;

import java.util.List;

public class MyContentProvider extends ContentProvider {

    public static final int BOOK_DIR = 0;
    public static final int BOOK_ITEM = 1;
    public static final String AUTHORITY = "com.example.acer.litepaltest.provider";
    private static UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY,"book",BOOK_DIR);
        uriMatcher.addURI(AUTHORITY,"book/#",BOOK_ITEM);
    }


    public MyContentProvider() {

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        int deleteRows = 0;
        switch (uriMatcher.match(uri)){
            case BOOK_DIR:
                //TODO
                deleteRows = DataSupport.deleteAll(Book.class,selection);
                break;
            case BOOK_ITEM:
                String id = uri.getPathSegments().get(1);
                deleteRows = DataSupport.deleteAll(Book.class,"id = ?",id);
                break;
            default:
                break;
        }
        return deleteRows;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        switch (uriMatcher.match(uri)){
            case BOOK_DIR:
                return "vnd.android.cursor.dir/vnd.com.example.acer.litepaltest.provider.book";
            case BOOK_ITEM:
                return "vnd.android.cursor.item/vnd.com.example.acer.litepaltest.provider.book";
        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        Uri uri1 = null;
        switch (uriMatcher.match(uri)){
            case BOOK_DIR:
            case BOOK_ITEM:
                Book book = new Book();
                book.setName(values.get("name").toString());
                book.setAuthor(values.get("author").toString());
                book.setPrice(Double.parseDouble(values.get("price").toString()));
                book.setPage(Integer.parseInt(values.get("page").toString()));
                book.save();
                int id = book.getId();
                uri1 = Uri.parse("content://" + AUTHORITY + "/book/" + id);
        }
        return uri1;
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        Cursor cursor = null;
        switch (uriMatcher.match(uri)){
            case BOOK_DIR:
                cursor = DataSupport.findBySQL("select * from Book");
                break;
            case BOOK_ITEM:
                String id = uri.getPathSegments().get(1);
                cursor = DataSupport.findBySQL("select * from Book where id = ?",id);
                break;
            default:
                break;
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        int updateRows = 0;
        switch (uriMatcher.match(uri)){
            case BOOK_DIR:
                //TODO
//                Book book = new Book();
//                book.setName(values.get("name").toString());
//                book.setAuthor(values.get("author").toString());
//                book.setPrice(Double.parseDouble(values.get("price").toString()));
//                book.setPage(Integer.parseInt(values.get("page").toString()));
//                updateRows = book.updateAll(selection,selection);
                updateRows = DataSupport.updateAll(Book.class,values);
                break;
            case BOOK_ITEM:

                updateRows = DataSupport.update(Book.class,values,Long.parseLong(uri.getPathSegments().get(1)));
                break;
        }
        return updateRows;
    }
}
