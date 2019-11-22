package com.example.acer.litepaltest;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class addActivity extends AppCompatActivity {

    private EditText nameEdit,authorEdit,priceEdit,pageEdit;
    private Button addButton,cancleButton;

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
        setContentView(R.layout.activity_add_data);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("新增");
        }

        nameEdit = (EditText) findViewById(R.id.nameEditText);
        authorEdit = (EditText) findViewById(R.id.authorEditText);
        priceEdit = (EditText) findViewById(R.id.priceEditText);
        pageEdit = (EditText) findViewById(R.id.pageEditText);
        addButton = (Button) findViewById(R.id.addButton);
        cancleButton = (Button) findViewById(R.id.cancleButton1);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = new Book();
                book.setName(nameEdit.getText().toString());
                book.setAuthor(authorEdit.getText().toString());
                book.setPrice(Double.parseDouble(priceEdit.getText().toString()));
                book.setPage(Integer.parseInt(pageEdit.getText().toString()));
                book.save();
                Toast.makeText(addActivity.this,"新增的书名为：" + nameEdit.getText().toString(),Toast.LENGTH_SHORT).show();
                nameEdit.setText("");
                authorEdit.setText("");
                priceEdit.setText("");
                pageEdit.setText("");
            }
        });

        cancleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
