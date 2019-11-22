package com.example.acer.experimenttext2.Activity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.example.acer.experimenttext2.Class.Course;
import com.example.acer.experimenttext2.R;
import com.example.acer.experimenttext2.Class.StuLeave;
import com.example.acer.experimenttext2.Service.MyService;

import org.litepal.crud.DataSupport;
import java.util.ArrayList;
import java.util.List;


public class HomePageActivity extends AppCompatActivity {

    private Toolbar toolbar_Home;//主页的主题栏
    private CardView courseCard,materialFixCard,StuLeaveCard;
    private TextView courseTextView,materialFixTextView,StuLeaveTextView;
    private SwipeRefreshLayout swipRefresh_home;
    private DrawerLayout drawerLayout_home;
    private NavigationView nav_view_home;

    static List<StuLeave> stuLeaveList = new ArrayList<>(); //请假学生
    static List<Course> courseList = new ArrayList<Course>();//上课课程


    private IntentFilter course_filter,stuLeave_filter;
    private CourseSizeReceiver courseSizeReceiver;
    private StuLeaveReceiver stuLeaveReceiver;

    private boolean isFirst = true;

    private MyService.MyBind mBind;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBind = (MyService.MyBind) service;
            init();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        courseCard = (CardView) findViewById(R.id.courseCard_home);
        materialFixCard = (CardView) findViewById(R.id.materialFixCard_home);
        StuLeaveCard = (CardView) findViewById(R.id.StuLeaveCard_home);
        courseTextView = (TextView) findViewById(R.id.courseTextView_home);
        materialFixTextView = (TextView) findViewById(R.id.materialFixTextView_home);
        StuLeaveTextView = (TextView) findViewById(R.id.StuLeaveTextView_home);
        swipRefresh_home = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh_home);
        toolbar_Home = (Toolbar) findViewById(R.id.toolbar_Home);
        drawerLayout_home = (DrawerLayout) findViewById(R.id.drawer_layout);
        nav_view_home = (NavigationView) findViewById(R.id.nav_view);

        //启动服务和广播
        Service_Broadcast();
        courseTextView.setText("目前总共有" + courseList.size() + "节课待上");
        StuLeaveTextView.setText("总共有" + stuLeaveList.size() + "名学生请假");
        materialFixTextView.setText("点击报修器材");

        setSupportActionBar(toolbar_Home);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.actionbar);
        }

        courseCard.setOnClickListener(new mClick());
        materialFixCard.setOnClickListener(new mClick());
        StuLeaveCard.setOnClickListener(new mClick());

        swipRefresh_home.setColorSchemeResources(R.color.colorPrimary);
        swipRefresh_home.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshHome();
            }
        });


        nav_view_home.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        drawerLayout_home.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_about:

                        break;
                    case R.id.nav_setting:

                        break;
                    default:
                        break;
                }
                return true;
            }
        });

    }



    /*各个卡片点击事件监听*/
    class mClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.courseCard_home:
                    Intent intentCourse = new Intent(HomePageActivity.this,CourseActivity.class);
                    startActivity(intentCourse);
                    break;
                case R.id.materialFixCard_home:
                    Intent intentMaterialFix = new Intent(HomePageActivity.this,MaterialFixActivity.class);
                    startActivity(intentMaterialFix);
                    break;
                case R.id.StuLeaveCard_home:
                    Intent intentStuLeave = new Intent(HomePageActivity.this,StuLeaveActivity.class);
                    startActivity(intentStuLeave);
                    break;
                default:
                    break;

            }
        }


    }


    /*启动服务和广播*/
    public void Service_Broadcast(){
        //启动服务
        Intent intent = new Intent(this,MyService.class);
        bindService(intent,connection,BIND_AUTO_CREATE);
        //注册广播
        course_filter = new IntentFilter();
        stuLeave_filter = new IntentFilter();
        course_filter.addAction("com.example.acer.experimenttext2.course_size_broadcast");
        stuLeave_filter.addAction("com.example.acer.experimenttext2.stuLeave_size_broadcast");
        courseSizeReceiver = new CourseSizeReceiver();
        stuLeaveReceiver = new StuLeaveReceiver();
        registerReceiver(courseSizeReceiver,course_filter);
        registerReceiver(stuLeaveReceiver,stuLeave_filter);
    }

    //TODO 刷新功能的实现
    /*刷新功能实现*/
    private void refreshHome(){

        init();
        if (!isFirst){
            isFirst = true;
            Service_Broadcast();
        }

        materialFixTextView.setText("点击报修器材");

        swipRefresh_home.setRefreshing(false);

    }

    /*访问网络数据库来获取数据*/
    public void init(){
        DataSupport.deleteAll(Course.class);
        DataSupport.deleteAll(StuLeave.class);
        //http://10.138.89.109/stu_leave.json?name=ZhangSan&class_=y1501,http://111.172.85.88/course.json
        mBind.getStuLeave("http://111.172.85.88/stu_leave.json?name=ZhangSan&class_=y1501",stuLeaveList);
        mBind.getCourse("http://guolin.tech/api/china",courseList);

    }

    /*课程数广播接收器*/
    class CourseSizeReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            int size = intent.getIntExtra("course_size",0);
            courseTextView.setText("目前总共有" + size + "节课待上");
        }
    }

    /*请假学生数广播接收器*/
    class StuLeaveReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            int size = intent.getIntExtra("stuLeave_size",0);
            StuLeaveTextView.setText("总共有" + size + "名学生请假");
        }
    }


    //TODO 实现功能
    /*menu菜单中各个按钮事件的实现*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout_home.openDrawer(GravityCompat.START);
                break;
            case R.id.nav_remindWay:
                Intent intent_remind = new Intent(HomePageActivity.this,RemindWay_DialogActivity.class);
                startActivity(intent_remind);
                break;
            case R.id.nav_changePassword:

                break;
            case R.id.nav_exist:
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
                editor.putBoolean("firstStart",true);
                editor.apply();
                Intent intent = new Intent(HomePageActivity.this,MainActivity.class);
                startActivity(intent);
                unregisterReceiver(courseSizeReceiver);
                unregisterReceiver(stuLeaveReceiver);
                unbindService(connection);
                finish();
                break;
            default:
                break;
        }
        return true;
    }

    /*Toolbar中加载menu菜单*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    /*重写返回按钮事件*/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && drawerLayout_home.isDrawerOpen(GravityCompat.START)){
            drawerLayout_home.closeDrawer(GravityCompat.START);
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_BACK){

            if (isFirst){
                isFirst = false;
                unregisterReceiver(courseSizeReceiver);
                unregisterReceiver(stuLeaveReceiver);
                unbindService(connection);
            }
            moveTaskToBack(false);
        }

        return super.onKeyDown(keyCode, event);

    }


}
