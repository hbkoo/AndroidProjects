package com.example.acer.experimenttext2.Activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.experimenttext2.Class.HttpCallbackListener;
import com.example.acer.experimenttext2.Class.HttpUtil;
import com.example.acer.experimenttext2.R;
import com.example.acer.experimenttext2.Class.StuLeave;
import com.example.acer.experimenttext2.Class.StuLeaveAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class StuLeaveActivity extends AppCompatActivity {


    private List<StuLeave> stuLeaveList = new ArrayList<StuLeave>();
     String str;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_leave);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        stuLeaveList = DataSupport.findAll(StuLeave.class);
        for (StuLeave stuLeave:stuLeaveList){
            Log.d("输出：",stuLeave.getName());
        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleView_leave);
        LinearLayoutManager layoutManager = new LinearLayoutManager(StuLeaveActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        StuLeaveAdapter adapter = new StuLeaveAdapter(stuLeaveList,this);
        recyclerView.setAdapter(adapter);

        TextView leaveNum = (TextView) findViewById(R.id.leaveNum_TextView);
        leaveNum.setText("请假人数总共为" + stuLeaveList.size() + "人");

        if(stuLeaveList.size() == 0)
            Toast.makeText(StuLeaveActivity.this,"目前没有人请假",Toast.LENGTH_SHORT).show();

//        leaveNum.setText("请假人数总共为2人");
//        leaveNum.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                HttpUtil.sendOKHttpRequest("http://27.18.197.229/stu_leave.json", new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        Log.d("访问失败：","8888");
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
////                Gson gson = new Gson();
//                        str = response.body().string();
////                stuLeaveList = gson.fromJson(response.body().string(),new TypeToken<List<StuLeave>>()
////                {}.getType());
//                        parse(str);
//                    }
//                });
//
//                Toast.makeText(StuLeaveActivity.this,str,Toast.LENGTH_SHORT).show();
//            }
//        });

    }

//    public void parse(String str){
//        try{
//            JSONArray jsonArray = new JSONArray(str);
//            for (int i = 0; i < jsonArray.length(); i++){
//                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                StuLeave stuLeave = new StuLeave(jsonObject.getString("name"),jsonObject.getString("class_"),
//                        jsonObject.getString("ID"),jsonObject.getString("telephone"),jsonObject.getString("reason"));
//                stuLeaveList.add(stuLeave);
//            }
//
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//    }

}
