package com.example.acer.experimenttext2.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.acer.experimenttext2.Class.Course;
import com.example.acer.experimenttext2.Class.HttpUtil;
import com.example.acer.experimenttext2.Class.StuLeave;

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

public class MyService extends Service {

    private MyBind mBinder = new MyBind();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class MyBind extends Binder {

        public void getStuLeave(String url, final List<StuLeave> stuLeaveList) {
            HttpUtil.sendOKHttpRequest(url, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("网络连接失败：", "88888");
                    //               Toast.makeText(HomePageActivity.this,"请检查网络连接",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
//                    Log.d("类型00000999："," "+response.code());
//                    InputStream in = response.body().byteStream();
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//                    StringBuilder stringBuilder = new StringBuilder();
//                    String line;
//                    while ((line = reader.readLine()) != null){
//                        stringBuilder.append(line);
//                    }
//                    Log.d("信息888：",stringBuilder.toString());
//                    JSONArray jsonArray = new JSONArray(stringBuilder.toString());
                        stuLeaveList.clear();
                        JSONArray jsonArray = new JSONArray(response.body().string());

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            StuLeave stuLeave = new StuLeave(jsonObject.getString("name"), jsonObject.getString("class_"),
                                    jsonObject.getString("ID"), jsonObject.getString("telephone"), jsonObject.getString("reason"));
                            stuLeaveList.add(stuLeave);

                            Log.d("信息：", stuLeave.getName() + "\n" + stuLeave.getClass_() + "\n" +
                                    stuLeave.getNumber() + "\n" + stuLeave.getTelephone() + "\n" + stuLeave.getReason());

                            StuLeave stuLeave1 = new StuLeave();
                            stuLeave1.setName(stuLeave.getName());
                            stuLeave1.setClass_(stuLeave.getClass_());
                            stuLeave1.setNumber(stuLeave.getNumber());
                            stuLeave1.setTelephone(stuLeave.getTelephone());
                            stuLeave1.setReason(stuLeave.getReason());
                            stuLeave1.save();

                        }
                        Intent intent = new Intent("com.example.acer.experimenttext2.stuLeave_size_broadcast");
                        intent.putExtra("stuLeave_size", stuLeaveList.size());
                        sendBroadcast(intent);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        public void getCourse(String url, final List<Course> courseList) {
            HttpUtil.sendOKHttpRequest(url, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("请检查网络：", "****8888");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {


                        courseList.clear();
                        JSONArray jsonArray = new JSONArray(response.body().string());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            Course course = new Course(object.getString("name"), object.getString("class_"),
                                    object.getString("time"), object.getString("classroom"),
                                    object.getString("content"));
                            courseList.add(course);
                            Log.d("实验具体安排：", course.getName() + "\n" + course.getClass_() + "\n" + course.getTime()
                                    + "\n" + course.getClassroom() + "\n" + course.getContent());
                            Course course1 = new Course();
                            course1.setName(course.getName());
                            course1.setClass_(course.getClass_());
                            course1.setTime(course.getTime());
                            course1.setClassroom(course.getClassroom());
                            course1.setContent(course.getContent());
                            course1.save();

                        }

                        Intent intent = new Intent("com.example.acer.experimenttext2.course_size_broadcast");
                        intent.putExtra("course_size", courseList.size());
                        sendBroadcast(intent);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
        }


    }

}
