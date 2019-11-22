package com.example.acer.litepaltest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

public class deleteActivity extends AppCompatActivity {

    private EditText nameDeleteEdit,authorDeleteEdit,priceDeleteEdit,pageDeleteEdit;
    private Button DeleteButton,cancleDeleteButton,previousButton,nextButton;
    Book book1;
    int n = 0;

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
        setContentView(R.layout.activity_delete);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("删除");
        }

        nameDeleteEdit = (EditText) findViewById(R.id.nameDeleteEditText);
        authorDeleteEdit = (EditText) findViewById(R.id.authorDeleteEditText);
        priceDeleteEdit = (EditText) findViewById(R.id.priceDeleteEditText);
        pageDeleteEdit = (EditText) findViewById(R.id.pageDeleteEditText);
        DeleteButton = (Button) findViewById(R.id.DeleteButton);
        cancleDeleteButton = (Button) findViewById(R.id.cancleDeleteButton);
        previousButton = (Button) findViewById(R.id.previousDelete);
        nextButton = (Button) findViewById(R.id.nextDelete);

        final List<Book> books = DataSupport.findAll(Book.class);
        if(books.size() == 0){
            nameDeleteEdit.setText("×××");
            authorDeleteEdit.setText("×××");
            priceDeleteEdit.setText("0.0");
            pageDeleteEdit.setText("0");
        }
        else{
            book1 = books.get(n);
            nameDeleteEdit.setText(book1.getName());
            authorDeleteEdit.setText(book1.getAuthor());
            priceDeleteEdit.setText(String.valueOf(book1.getPrice()));
            pageDeleteEdit.setText(String.valueOf(book1.getPage()));
        }

        DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(deleteActivity.this);
                builder.setTitle("提示");
                builder.setMessage("是否删除所选书目？");
                builder.setNegativeButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DataSupport.deleteAll(Book.class,"name = ? and author = ? and price = ? and page = ?",
                                nameDeleteEdit.getText().toString(),authorDeleteEdit.getText().toString(),
                                priceDeleteEdit.getText().toString(), pageDeleteEdit.getText().toString());
                        Toast.makeText(deleteActivity.this,"删除书《" + nameDeleteEdit.getText().toString() + "》",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(deleteActivity.this,deleteActivity.class);
                        deleteActivity.this.startActivity(intent);
                        deleteActivity.this.finish();
                    }
                });
                builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setCancelable(false);
                builder.show();


            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(n == 0){
                    Toast.makeText(deleteActivity.this,"已经是第一条数据了！",Toast.LENGTH_SHORT).show();
                }
                else{
                    book1 = books.get(--n);
                    nameDeleteEdit.setText(book1.getName());
                    authorDeleteEdit.setText(book1.getAuthor());
                    priceDeleteEdit.setText(String.valueOf(book1.getPrice()));
                    pageDeleteEdit.setText(String.valueOf(book1.getPage()));
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((n+1) < books.size()){
                    book1 = books.get(++n);
                    nameDeleteEdit.setText(book1.getName());
                    authorDeleteEdit.setText(book1.getAuthor());
                    priceDeleteEdit.setText(String.valueOf(book1.getPrice()));
                    pageDeleteEdit.setText(String.valueOf(book1.getPage()));
                }
                else{
                    Toast.makeText(deleteActivity.this,"已经是最后一条数据了！",Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancleDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
