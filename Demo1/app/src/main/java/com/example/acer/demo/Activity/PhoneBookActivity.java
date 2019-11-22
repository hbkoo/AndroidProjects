package com.example.acer.demo.Activity;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.demo.R;
import com.example.acer.demo.Tools.PhoneBookAdapter;
import com.example.acer.demo.Tools.PhoneBookAdapterRC;
import com.example.acer.demo.Tools.PhoneBookItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PhoneBookActivity extends AppCompatActivity {

    private Button book_editor;
    private TextView delete_tv;

    private List<PhoneBookItem> bookList = new ArrayList<>();
    private RecyclerView recyclerView;
    private PhoneBookAdapterRC adapter;

    private boolean isDelete = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_book);
        Button backBtn = (Button) findViewById(R.id.phone_book_back_btn);
        recyclerView = (RecyclerView) findViewById(R.id.recycle);
        book_editor = (Button) findViewById(R.id.book_editor);
        delete_tv = (TextView) findViewById(R.id.delete_tv);

        LoadingData();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        adapter = new PhoneBookAdapterRC(this, bookList);
        recyclerView.setAdapter(adapter);
        backBtn.setOnClickListener(new mClick());
        book_editor.setOnClickListener(new mClick());
        delete_tv.setOnClickListener(new mClick());

    }

    private class mClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.phone_book_back_btn:
                    finish();
                    break;
                case R.id.book_editor:
                    showPopMenu(book_editor);
                    break;
                case R.id.delete_tv:
                    if (adapter.getDeleteCount() == 0){
                        Toast.makeText(PhoneBookActivity.this,"请选择删除的选项",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    isDelete = false;
                    delete_tv.setVisibility(View.INVISIBLE);
                    book_editor.setVisibility(View.VISIBLE);
                    adapter.ShowCheckBox(false);
                    adapter.DeleteBooks();
                    break;
            }
        }
    }

    //展示菜单选项
    private void showPopMenu(View editor) {
        PopupMenu menu = new PopupMenu(PhoneBookActivity.this, editor);
        menu.getMenuInflater().inflate(R.menu.book_editor, menu.getMenu());
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.add_book:
                        showAddPhone();
                        break;
                    case R.id.delete_book:
                        isDelete = true;
                        book_editor.setVisibility(View.INVISIBLE);
                        delete_tv.setVisibility(View.VISIBLE);
                        adapter.ShowCheckBox(true);
                        break;
                }
                return true;
            }
        });
        menu.show();
    }

    //新增用户
    private void showAddPhone() {
        View view = getLayoutInflater().inflate(R.layout.phone_book, null, false);
        TextView add = (TextView) view.findViewById(R.id.phone_book_change);
        TextView cancel = (TextView) view.findViewById(R.id.phone_book_cancel);
        add.setText("新增");
        final EditText name = (EditText) view.findViewById(R.id.phone_book_name_et);
        final EditText tel = (EditText) view.findViewById(R.id.phone_book_tel_et);
        AlertDialog.Builder dialog = new AlertDialog.Builder(PhoneBookActivity.this);
        dialog.setView(view);
        final AlertDialog alertDialog = dialog.show();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("".equals(name.getText().toString()) || "".equals(tel.getText().toString())){
                    Toast.makeText(PhoneBookActivity.this,"姓名或者电话为空！",Toast.LENGTH_SHORT).show();
                } else {
                    adapter.AddBook(new PhoneBookItem(name.getText().toString(),
                            tel.getText().toString(), 0));
                    alertDialog.dismiss();
                    Toast.makeText(PhoneBookActivity.this, "新增成功", Toast.LENGTH_SHORT).show();
                }

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    //初始化数据
    private void LoadingData() {
        bookList.clear();
        PhoneBookItem item_1 = new PhoneBookItem("人事科", "87651462", 0);
        bookList.add(item_1);
        PhoneBookItem item_2 = new PhoneBookItem("工资科", "87651466", 0);
        bookList.add(item_2);
        PhoneBookItem item_3 = new PhoneBookItem("教师工作办公室", "87651526", 0);
        bookList.add(item_3);
        PhoneBookItem item_4 = new PhoneBookItem("职称工作办公室", "87869147", 0);
        bookList.add(item_4);
        PhoneBookItem item_5 = new PhoneBookItem("基础研究办公室", "87214969", 0);
        bookList.add(item_5);
        PhoneBookItem item_6 = new PhoneBookItem("成果管理办公室", "87218140", 0);
        bookList.add(item_6);
        PhoneBookItem item_7 = new PhoneBookItem("科技开发部", "87651485", 0);
        bookList.add(item_7);
        PhoneBookItem item_8 = new PhoneBookItem("教学研究办公室", "87850017", 0);
        bookList.add(item_8);
        PhoneBookItem item_9 = new PhoneBookItem("教务管理办公室", "87850027", 0);
        bookList.add(item_9);
        PhoneBookItem item_10 = new PhoneBookItem("考务管理中心", "87850509", 0);
        bookList.add(item_10);
        PhoneBookItem item_11 = new PhoneBookItem("教师教学发展中心", "87859099", 0);
        bookList.add(item_11);
        PhoneBookItem item_12 = new PhoneBookItem("培养处教学管理科", "87651477", 0);
        bookList.add(item_12);
        PhoneBookItem item_13 = new PhoneBookItem("留学生管理科", "87855613", 0);
        bookList.add(item_13);
        PhoneBookItem item_14 = new PhoneBookItem("学位办学位管理科", "87651521", 0);
        bookList.add(item_14);
        PhoneBookItem item_15 = new PhoneBookItem("余区办公室", "86558526", 0);
        bookList.add(item_15);
        PhoneBookItem item_16 = new PhoneBookItem("科研经费管理科", "87671337", 0);
        bookList.add(item_16);
        PhoneBookItem item_17 = new PhoneBookItem("人员经费管理科", "87859047", 0);
        bookList.add(item_17);
        PhoneBookItem item_18 = new PhoneBookItem("马区会计核算科", "87651502", 0);
        bookList.add(item_18);
        PhoneBookItem item_19 = new PhoneBookItem("余区会计核算科", "86534332", 0);
        bookList.add(item_19);
        PhoneBookItem item_20 = new PhoneBookItem("公共卫生科", "87864894", 0);
        bookList.add(item_20);
        PhoneBookItem item_21 = new PhoneBookItem("治安科", "87392065", 0);
        bookList.add(item_21);
        PhoneBookItem item_22 = new PhoneBookItem("户政室", "87398030", 0);
        bookList.add(item_22);
        PhoneBookItem item_23 = new PhoneBookItem("校园交通与公共秩序管理中心", "87394516", 0);
        bookList.add(item_23);
        PhoneBookItem item_24 = new PhoneBookItem("马区综合服务中心", "87864689", 0);
        bookList.add(item_24);
        PhoneBookItem item_25 = new PhoneBookItem("余区后勤综合办公室", "86868802", 0);
        bookList.add(item_25);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && isDelete){
            isDelete = false;
            delete_tv.setVisibility(View.INVISIBLE);
            book_editor.setVisibility(View.VISIBLE);
            adapter.ShowCheckBox(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
