package com.example.acer.demo.Activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.demo.R;
import com.example.acer.demo.Tools.BaseDBHelper;
import com.example.acer.demo.Tools.Memory;
import com.example.acer.demo.Tools.MemoryAdapter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MemoryActivity extends AppCompatActivity {

    private static String FILE_NAME = "memory";

    Button back_btn, add_btn;
    ListView listView;
    LinearLayout layout;
    MemoryAdapter adapter = null;

    private List<Memory> memories = new ArrayList<>();

    public static SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);


        back_btn = (Button) findViewById(R.id.memory_back_btn);
        add_btn = (Button) findViewById(R.id.add_btn);
        listView = (ListView) findViewById(R.id.list_view);
        layout = (LinearLayout) findViewById(R.id.empty_layout);

        adapter = new MemoryAdapter(this, memories);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new mItemClick());
        listView.setOnItemLongClickListener(new mItemLongClick());

        BaseDBHelper helper = new BaseDBHelper(this, "firstDemo_bd", null, 1);
        database = helper.getWritableDatabase();

        //注册上下文菜单
        //registerForContextMenu(listView);

        //数据库形式加载
        loadMemoryByDB();
        //文件形式加载
        //loadMemoryByFile();

        back_btn.setOnClickListener(new mClick());
        add_btn.setOnClickListener(new mClick());

    }

    private class mItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(MemoryActivity.this, ChangeMemoryActivity.class);
            //intent.putExtra("memory", (Serializable) memories.get(position));
            Bundle bundle = new Bundle();
            bundle.putSerializable("memory", (Serializable) memories.get(position));
            intent.putExtra("bundle", bundle);
            startActivityForResult(intent, 1);
        }
    }

    private class mItemLongClick implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
            View view1 = getLayoutInflater().inflate(R.layout.delete_dialog, null, false);
            TextView cancel = (TextView) view1.findViewById(R.id.cancel_tv);
            final TextView delete = (TextView) view1.findViewById(R.id.delete_tv);
            AlertDialog.Builder builder = new AlertDialog.Builder(MemoryActivity.this);
            builder.setView(view1);
            final AlertDialog dialog = builder.show();
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteMemoryDB(memories.get(position));
                    //UpdateMemories();
                    dialog.dismiss();
                }
            });
            return true;
        }
    }

    private class mClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.memory_back_btn:
                    finish();
                    break;
                case R.id.add_btn:
                    startActivityForResult(new Intent(MemoryActivity.this, AddMemoryActivity.class), 1);
                    break;
            }
        }
    }

    /**
     * 上下文菜单
     *
     * @param menu
     * @param v
     * @param menuInfo
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.delete_memory, menu);
    }

    /**
     * 上下文菜单的点击事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_memory:

                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            switch (resultCode) {
                case 0:
                    if (data == null) break;
                    Boolean isAdd = data.getBooleanExtra("isAdd", false);
                    if (isAdd) {
                        //loadMemoryByFile();
                        loadMemoryByDB();
                        if (memories.size() != 0) {
                            adapter.notifyDataSetChanged();
                        }
                    }
                    break;
                case 1:
                    if (data == null) break;
                    Boolean isChange = data.getBooleanExtra("isChange", false);
                    if (isChange) {
                        loadMemoryByDB();
                        adapter.notifyDataSetChanged();
                    }
                    break;
            }

        }
    }

    //读取数据库信息
    private void loadMemoryByDB() {
        memories.clear();
        String id, time, content;

        Cursor cursor = database.query("t_memory", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                id = String.valueOf(cursor.getInt(cursor.getColumnIndex("id")));
                time = cursor.getString(cursor.getColumnIndex("mem_time"));
                content = cursor.getString(cursor.getColumnIndex("mem_content"));
                Memory memory = new Memory(time, content);
                memory.setId(id);
                memories.add(memory);
            } while (cursor.moveToNext());
        }
        cursor.close();
        if (memories.size() != 0) {
            layout.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        } else {
            layout.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }
    }

    //删除数据库中的一条信息
    private void deleteMemoryDB(Memory memory) {
        int count = database.delete("t_memory", "id = ?", new String[]{memory.getId()});
        if (count > 0) {
            memories.remove(memory);
            adapter.notifyDataSetChanged();
            Toast.makeText(MemoryActivity.this, "删除成功!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MemoryActivity.this, "删除失败!", Toast.LENGTH_SHORT).show();
        }
        if (memories.size() == 0) {
            layout.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }
    }

    //读取文件信息
    private void loadMemoryByFile() {
        memories.clear();
        FileInputStream inputStream;
        BufferedReader reader = null;
        String line;
        try {
            inputStream = openFileInput(FILE_NAME);
            reader = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(":");
                Memory memory = new Memory(data[0], data[1]);
                memories.add(memory);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (memories.size() != 0) {
            layout.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        } else {
            layout.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }

    }

    //删除一条数据后的更新文件信息
    private void UpdateMemories() {
        if (memories.size() == 0) {
            layout.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }
        String content;
        FileOutputStream outputStream;
        BufferedWriter writer = null;
        try {
            outputStream = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(outputStream));
            for (Memory memory : memories) {
                content = memory.getTime() + ":" + memory.getContent() + "\n";
                writer.write(content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Toast.makeText(MemoryActivity.this, "删除成功!", Toast.LENGTH_SHORT).show();
    }

}
