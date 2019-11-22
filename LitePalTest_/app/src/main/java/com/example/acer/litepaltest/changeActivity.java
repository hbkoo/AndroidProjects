package com.example.acer.litepaltest;

import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.List;

public class changeActivity extends AppCompatActivity {

    private EditText nameChangeEdit,authorChangeEdit,priceChangeEdit,pageChangeEdit;
    private Button ChangeButton,cancleChangeButton,previousButton,nextButton;
    private int n = 0;
    Book book1;

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
        setContentView(R.layout.activity_change);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("修改");
        }


        nameChangeEdit = (EditText) findViewById(R.id.nameChangeEditText);
        authorChangeEdit = (EditText) findViewById(R.id.authorChangeEditText);
        priceChangeEdit = (EditText) findViewById(R.id.priceChangeEditText);
        pageChangeEdit = (EditText) findViewById(R.id.pageChangeEditText);
        ChangeButton = (Button) findViewById(R.id.ChangeButton);
        cancleChangeButton = (Button) findViewById(R.id.cancleChangeButton);
        previousButton = (Button) findViewById(R.id.previousChange);
        nextButton = (Button) findViewById(R.id.nextChange);
        nameChangeEdit.setEnabled(false);

        final List<Book> books = DataSupport.findAll(Book.class);
        if(books.size() == 0){
            nameChangeEdit.setText("×××");
            authorChangeEdit.setText("×××");
            priceChangeEdit.setText("0.0");
            pageChangeEdit.setText("0");
        }
        else{
            book1 = books.get(n);
            nameChangeEdit.setText(book1.getName());
            authorChangeEdit.setText(book1.getAuthor());
            priceChangeEdit.setText(String.valueOf(book1.getPrice()));
            pageChangeEdit.setText(String.valueOf(book1.getPage()));
        }


        ChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = new Book();
                book.setAuthor(authorChangeEdit.getText().toString());
                book.setPage(Integer.parseInt(pageChangeEdit.getText().toString()));
                book.setPrice(Double.parseDouble(priceChangeEdit.getText().toString()));
                book.updateAll("name = ?",nameChangeEdit.getText().toString());
            //    Toast.makeText(changeActivity.this,"修改书《" + nameChangeEdit.getText().toString() + "》成功！",Toast.LENGTH_SHORT).show();

                Snackbar.make(v,"修改",Snackbar.LENGTH_SHORT).setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(changeActivity.this,"修改书《" + nameChangeEdit.getText().toString() + "》成功！",Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(n == 0){
                    Toast.makeText(changeActivity.this,"已经是第一条数据了！",Toast.LENGTH_SHORT).show();
                }
                else{
                    book1 = books.get(--n);
                    nameChangeEdit.setText(book1.getName());
                    authorChangeEdit.setText(book1.getAuthor());
                    priceChangeEdit.setText(String.valueOf(book1.getPrice()));
                    pageChangeEdit.setText(String.valueOf(book1.getPage()));
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((n+1) < books.size()){
                    book1 = books.get(++n);
                    nameChangeEdit.setText(book1.getName());
                    authorChangeEdit.setText(book1.getAuthor());
                    priceChangeEdit.setText(String.valueOf(book1.getPrice()));
                    pageChangeEdit.setText(String.valueOf(book1.getPage()));
                }
                else{
                    Toast.makeText(changeActivity.this,"已经是最后一条数据了！",Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancleChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
