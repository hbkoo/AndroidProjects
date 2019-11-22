package com.example.acer.litepaltest;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.List;

public class queryActivity extends AppCompatActivity {



    private EditText nameQueryEdit,authorQueryEdit,priceQueryEdit,pageQueryEdit;
    private Button QueryButton,cancleQueryButton,previousQuery,nextQuery;
    int n = 0;
    private Book book1;
    private List<Book> books;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("查找");
        }

        nameQueryEdit = (EditText) findViewById(R.id.nameQueryEditText);
        authorQueryEdit = (EditText) findViewById(R.id.authorQueryEditText);
        priceQueryEdit = (EditText) findViewById(R.id.priceQueryEditText);
        pageQueryEdit = (EditText) findViewById(R.id.pageQueryEditText);
        QueryButton = (Button) findViewById(R.id.QueryButton);
        cancleQueryButton = (Button) findViewById(R.id.cancleQueryButton);
        previousQuery = (Button) findViewById(R.id.previousQuery);
        nextQuery = (Button) findViewById(R.id.nextQuery);
        previousQuery.setEnabled(false);
        nextQuery.setEnabled(false);

        authorQueryEdit.setText("×××");
        priceQueryEdit.setText("0.0");
        pageQueryEdit.setText("0");

        previousQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(n == 0){
                    Toast.makeText(queryActivity.this,"已经是第一条数据了！",Toast.LENGTH_SHORT).show();
                }
                else{
                    books = DataSupport.where("name = ?",nameQueryEdit.getText().toString()).find(Book.class);
                    book1 = books.get(--n);
                    nameQueryEdit.setText(book1.getName());
                    authorQueryEdit.setText(book1.getAuthor());
                    priceQueryEdit.setText(String.valueOf(book1.getPrice()));
                    pageQueryEdit.setText(String.valueOf(book1.getPage()));
                }
            }
        });

        nextQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((n+1) < books.size()){
                    books = DataSupport.where("name = ?",nameQueryEdit.getText().toString()).find(Book.class);
                    book1 = books.get(++n);
                    nameQueryEdit.setText(book1.getName());
                    authorQueryEdit.setText(book1.getAuthor());
                    priceQueryEdit.setText(String.valueOf(book1.getPrice()));
                    pageQueryEdit.setText(String.valueOf(book1.getPage()));
                }
                else{
                    Toast.makeText(queryActivity.this,"已经是最后一条数据了！",Toast.LENGTH_SHORT).show();
                }
            }
        });

        QueryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                books = DataSupport.where("name = ?",nameQueryEdit.getText().toString()).find(Book.class);
                if(books.size() == 0){
                    authorQueryEdit.setText("×××");
                    priceQueryEdit.setText("0.0");
                    pageQueryEdit.setText("0");
                    previousQuery.setEnabled(false);
                    nextQuery.setEnabled(false);
                    Toast.makeText(queryActivity.this,"没有找到该书！",Toast.LENGTH_LONG).show();
                }
//                else if(books.size() == 1){
//                    Book book = books.get(0);
//                    authorQueryEdit.setText(book.getAuthor());
//                    priceQueryEdit.setText(String.valueOf(book.getPrice()));
//                    pageQueryEdit.setText(String.valueOf(book.getPage()));
//                }
                else{
                    Book book = books.get(0);
                    authorQueryEdit.setText(book.getAuthor());
                    priceQueryEdit.setText(String.valueOf(book.getPrice()));
                    pageQueryEdit.setText(String.valueOf(book.getPage()));
                    previousQuery.setEnabled(true);
                    nextQuery.setEnabled(true);
                }

            }
        });

        cancleQueryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
