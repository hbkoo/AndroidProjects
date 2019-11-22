package com.example.acer.experimenttext2.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.acer.experimenttext2.Class.Course;
import com.example.acer.experimenttext2.Class.HttpUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by acer on 2017/3/19.
 */

public class Course_Service extends Service {

    private CourseBind mBinder = new CourseBind();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class CourseBind extends Binder{

        public void getCourse(String url, final List<Course> courseList){
            HttpUtil.sendOKHttpRequest("http://59.174.223.136/course.json", new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("请检查网络：","****8888");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try{
                        JSONArray jsonArray = new JSONArray(response.body().string());
                        courseList.clear();
                        for (int i = 0; i < jsonArray.length(); i ++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            Course course = new Course(object.getString("name"),object.getString("class_"),
                                    object.getString("time"),object.getString("classroom"),
                                    object.getString("content"));
                            courseList.add(course);
                            Log.d("实验具体安排：",course.getName() + "\n" + course.getClass_() + "\n" +course.getTime()
                                    + "\n" +course.getClassroom() + "\n" +course.getContent());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
        }


    }



}
