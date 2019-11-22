package com.example.acer.experimenttext2.Activity;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.acer.experimenttext2.Class.AddCourse;
import com.example.acer.experimenttext2.Class.AddCourseAdapter;
import com.example.acer.experimenttext2.Class.Course;
import com.example.acer.experimenttext2.Class.CourseAdapter;
import com.example.acer.experimenttext2.R;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CourseActivity extends AppCompatActivity {

    protected MenuItem refreshItem;


    static List<Course> courseList = new ArrayList<Course>();

    private List<AddCourse> addcourseList = new ArrayList<AddCourse>();
    private List<AddCourse> addCourseList_adapter = new ArrayList<AddCourse>();

    private Toolbar toolBar_course;

    private ViewPager viewPager;
    private View check_view, add_view;
    private ArrayList<View> pageview;
    private TextView addCourse_TextView, checkCourse_TextView;
    private ImageView scrollbar;

    private ArrayAdapter<String> week_adapter = null;

    static CourseAdapter adapter;
    private AddCourseAdapter addCourseAdapter;

    private FloatingActionButton course_fab;
    private RecyclerView recyclerView;
    private Spinner week_spinner;
    private List<String> week = new ArrayList<String>();
    private boolean tag;
    private int current_week, selected_week = 0, current_XingQi;
    //初始化周数图片
    private int week_image[] = {R.drawable.number_1, R.drawable.number_2,
            R.drawable.number_3, R.drawable.number_4, R.drawable.number_5, R.drawable.number_6,
            R.drawable.number_7, R.drawable.number_8, R.drawable.number_9, R.drawable.number_10,
            R.drawable.number_11, R.drawable.number_12, R.drawable.number_13, R.drawable.number_14,
            R.drawable.number_15, R.drawable.number_16, R.drawable.number_17, R.drawable.number_18,
            R.drawable.number_19, R.drawable.number_20, R.drawable.number_21, R.drawable.number_22,
            R.drawable.number_23, R.drawable.number_24, R.drawable.number_25, R.drawable.number_26,
            R.drawable.number_27, R.drawable.number_28, R.drawable.number_29, R.drawable.number_30};

    private TextView current_week_tv;
    private TextView month_tv, Mon_tv, Tue_tv, Wed_tv, Thu_tv, Fri_tv, Sat_tv, Sun_tv;
    private LinearLayout Mon_layout, Tue_layout, Wed_layout, Thu_layout, Fri_layout, Sat_layout, Sun_layout;


    // 滚动条初始偏移量
    private int offset = 0;
    // 当前页编号
    private int currIndex = 0;
    // 滚动条宽度
    private int bmpW;
    //一倍滚动量
    private int one;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        toolBar_course = (Toolbar) findViewById(R.id.toolbar_course);
        viewPager = (ViewPager) findViewById(R.id.course_viewPager);
        addCourse_TextView = (TextView) findViewById(R.id.add_course_bar);
        checkCourse_TextView = (TextView) findViewById(R.id.check_course_bar);
        scrollbar = (ImageView) findViewById(R.id.course_scrollbar);

        LayoutInflater inflater = getLayoutInflater();
        check_view = inflater.inflate(R.layout.check_course, null);
        add_view = inflater.inflate(R.layout.add_course, null);
        pageview = new ArrayList<View>();
        pageview.add(check_view);
        pageview.add(add_view);


        //加载滑动页面布局内容
        ViewPage();
        //"查看课程"页面显示
        check_course_create();
        //"新增课程"页面显示
        add_course_create();

    }


    /*加载滑动页面布局内容*/
    public void ViewPage() {

        //建适配器
        PagerAdapter mPagerAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return pageview.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            //使从ViewGroup中移除当前View
            public void destroyItem(View arg0, int arg1, Object arg2) {
                ((ViewPager) arg0).removeView(pageview.get(arg1));
            }

            //返回一个对象，这个对象表明PagerAdapter适配器选择哪个对象放在当前的额ViewPage中
            public Object instantiateItem(View arg0, int arg1) {
                ((ViewPager) arg0).addView(pageview.get(arg1));
                return pageview.get(arg1);
            }
        };
        //绑定适配器
        viewPager.setAdapter(mPagerAdapter);
        //设置ViewPage初始界面为第一个界面
        viewPager.setCurrentItem(0);
        //添加切换界面监听器
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
        // 获取滚动条的宽度
        bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.app_image).getWidth();
        //为了获取屏幕宽度，新建一个DisplayMetrics对象
        DisplayMetrics displayMetrics = new DisplayMetrics();
        //将当前窗口的一些信息放在DisplayMetrics类中
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        //得到屏幕的宽度
        int screenW = displayMetrics.widthPixels;
        //计算出滚动条初始的偏移量
        offset = (screenW / 2 - bmpW) / 2;
        //计算出切换一个界面时，滚动条的位移量
        one = offset * 2 + bmpW;
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        //将滚动条的初始位置设置成与左边界间隔一个offset
        scrollbar.setImageMatrix(matrix);

        checkCourse_TextView.setTextColor(Color.WHITE);

        //查看课程文本框时间的实现
        checkCourse_TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCourse_TextView.setTextColor(Color.WHITE);
                addCourse_TextView.setTextColor(Color.BLACK);
                viewPager.setCurrentItem(0);
            }
        });

        //添加课程文本框时间的实现
        addCourse_TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCourse_TextView.setTextColor(Color.BLACK);
                addCourse_TextView.setTextColor(Color.WHITE);
                viewPager.setCurrentItem(1);
                //        alarmNotification();
                //         getInformation();

            }
        });


    }

    /*滑动页面监听*/
    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int arg0) {
            Animation animation = null;
            switch (arg0) {
                case 0:
                    /**
                     * TranslateAnimation的四个属性分别为
                     * float fromXDelta 动画开始的点离当前View X坐标上的差值
                     * float toXDelta 动画结束的点离当前View X坐标上的差值
                     * float fromYDelta 动画开始的点离当前View Y坐标上的差值
                     * float toYDelta 动画开始的点离当前View Y坐标上的差值
                     **/
                    //进入查看课程页面
                    checkCourse_TextView.setTextColor(Color.WHITE);
                    addCourse_TextView.setTextColor(Color.BLACK);
                    animation = new TranslateAnimation(one, 0, 0, 0);
                    break;
                case 1:
                    //进入添加课程页面
                    checkCourse_TextView.setTextColor(Color.BLACK);
                    addCourse_TextView.setTextColor(Color.WHITE);
                    //                getInformation();
                    animation = new TranslateAnimation(offset, one, 0, 0);
                    break;
            }
            //arg0为切换到的页的编码
            currIndex = arg0;
            // 将此属性设置为true可以使得图片停在动画结束时的位置
            animation.setFillAfter(true);
            //动画持续时间，单位为毫秒
            animation.setDuration(200);
            //滚动条开始动画
            scrollbar.startAnimation(animation);

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }

    /*“查看课程”页面设计*/
    public void check_course_create() {

//        courseList = getIntent().getExtras().getParcelableArrayList("course_data");
        courseList = DataSupport.findAll(Course.class);
        RecyclerView recyclerView = (RecyclerView) check_view.findViewById(R.id.course_recycleView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(CourseActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CourseAdapter(courseList, CourseActivity.this);
        recyclerView.setAdapter(adapter);

    }

    public void getData() {
        for (Course course : courseList) {

            String time = course.getTime();


        }
    }

    public void add_course_create() {
        loadWidgetOfAdd();//为添加课程页面各个控件实例化
        getCurrentDay();//获取当前周数
        init();//初始化数据
        LinearLayoutManager layoutManager = new LinearLayoutManager(CourseActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        addCourseAdapter = new AddCourseAdapter(addcourseList, CourseActivity.this);
        recyclerView.setAdapter(addCourseAdapter);


        course_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LinearLayout linearLayout = (LinearLayout) getLayoutInflater()
                        .inflate(R.layout.see_week, null);
                week_spinner = (Spinner) linearLayout.findViewById(R.id.week_Spinner);
                current_week_tv = (TextView) linearLayout.findViewById(R.id.current_week_TextView);
                current_week_tv.setText("当前为第" + current_week + "周");
                final android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.
                        AlertDialog.Builder(CourseActivity.this);
                dialog.setTitle("查看");
                dialog.setView(linearLayout);
                final android.support.v7.app.AlertDialog a = dialog.show();

                initweek();//为Spinner加载适配器

                week_spinner.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        tag = true;
                        return false;
                    }
                });

                week_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (tag) {
                            a.dismiss();
                            tag = false;
                            selected_week = position + 1;
                            course_fab.setImageResource(week_image[position]);
                            change_week();
                            init();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

            }
        });

    }

    //初始化数据
    public void init() {
        if (selected_week == 0) {
            selected_week = current_week;
        }
        addcourseList.clear();
        for (int i = 1; i <= 7; i++) {
            for (int j = 1; j <= 5; j++) {
                AddCourse addCourse = new AddCourse("JAVA", "y1501", "第" + selected_week + "周 周"
                        + i + "第" + j + "大节", new String[]{"--请选择--", "1-301", "2-502"},
                        10 * j + i, "上机");
                addcourseList.add(addCourse);
            }

        }
    }

    //为添加课程页面各个控件实例化
    public void loadWidgetOfAdd() {
        recyclerView = (RecyclerView) add_view.findViewById(R.id.recycleView_course);
        course_fab = (FloatingActionButton) add_view.findViewById(R.id.addCourse_fab);
        month_tv = (TextView) add_view.findViewById(R.id.month_TextView);
        Mon_tv = (TextView) add_view.findViewById(R.id.Mon_TextView);
        Tue_tv = (TextView) add_view.findViewById(R.id.Tue_TextView);
        Wed_tv = (TextView) add_view.findViewById(R.id.Wes_TextView);
        Thu_tv = (TextView) add_view.findViewById(R.id.Thu_TextView);
        Fri_tv = (TextView) add_view.findViewById(R.id.Fri_TextView);
        Sat_tv = (TextView) add_view.findViewById(R.id.Sat_TextView);
        Sun_tv = (TextView) add_view.findViewById(R.id.Sun_TextView);
        Mon_layout = (LinearLayout) add_view.findViewById(R.id.Mon_LinearLayout);
        Tue_layout = (LinearLayout) add_view.findViewById(R.id.Tue_LinearLayout);
        Wed_layout = (LinearLayout) add_view.findViewById(R.id.Wed_LinearLayout);
        Thu_layout = (LinearLayout) add_view.findViewById(R.id.Thu_LinearLayout);
        Fri_layout = (LinearLayout) add_view.findViewById(R.id.Fri_LinearLayout);
        Sat_layout = (LinearLayout) add_view.findViewById(R.id.Sat_LinearLayout);
        Sun_layout = (LinearLayout) add_view.findViewById(R.id.Sun_LinearLayout);
    }

    //获取当前周数
    public void getCurrentDay() {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(calendar2.get(Calendar.YEAR), 1, 20);//第一周的时间

        long diff = calendar1.getTimeInMillis() - calendar2.getTimeInMillis();
        long day = diff / (24 * 60 * 60 * 1000);//计算相差的天数

        current_week = (int) (1 + day / 7);
        course_fab.setImageResource(week_image[current_week - 1]);
        current_XingQi = calendar1.get(Calendar.DAY_OF_WEEK) - 1;
        change_week();

    }

    //设置当选择某一周后显示的时间
    public void change_week() {

        int days;
        int new_month;
        int Mon_date, Tue_date, Wed_date, Thu_date, Fri_date, Sat_date, Sun_date;

        if (selected_week == 0) {
            days = (current_week - 1) * 7;
        } else {
            days = (selected_week - 1) * 7;
        }

        //为当前周数的当前星期几做标记
        switch (current_XingQi) {
            case 1:
                if (selected_week == current_week || selected_week == 0) {
                    Mon_layout.setBackgroundColor(Color.GREEN);
                } else {
                    Mon_layout.setBackgroundColor(Color.WHITE);
                }
                break;
            case 2:
                if (selected_week == current_week || selected_week == 0) {
                    Tue_layout.setBackgroundColor(Color.GREEN);
                } else {
                    Tue_layout.setBackgroundColor(Color.WHITE);
                }
                break;
            case 3:
                if (selected_week == current_week || selected_week == 0) {
                    Wed_layout.setBackgroundColor(Color.GREEN);
                } else {
                    Wed_layout.setBackgroundColor(Color.WHITE);
                }
                break;
            case 4:
                if (selected_week == current_week || selected_week == 0) {
                    Thu_layout.setBackgroundColor(Color.GREEN);
                } else {
                    Thu_layout.setBackgroundColor(Color.WHITE);
                }
                break;
            case 5:
                if (selected_week == current_week || selected_week == 0) {
                    Fri_layout.setBackgroundColor(Color.GREEN);
                } else {
                    Fri_layout.setBackgroundColor(Color.WHITE);
                }
                break;
            case 6:
                if (selected_week == current_week || selected_week == 0) {
                    Sat_layout.setBackgroundColor(Color.GREEN);
                } else {
                    Sat_layout.setBackgroundColor(Color.WHITE);
                }
                break;
            case 0:
                if (selected_week == current_week || selected_week == 0) {
                    Sun_layout.setBackgroundColor(Color.GREEN);
                } else {
                    Sun_layout.setBackgroundColor(Color.WHITE);
                }
                break;
            default:
                break;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), 1, 20);//设置为第一周的时间
        calendar.add(Calendar.DAY_OF_MONTH, days);//加上相隔时间后得到选择的周数的时间
        new_month = calendar.get(Calendar.MONTH);//获取选择的周数的月份
        month_tv.setText(String.valueOf(new_month + 1));

        Mon_date = calendar.get(Calendar.DAY_OF_MONTH);
        if (Mon_date == 1) {
            Mon_tv.setText(String.valueOf(new_month + 1) + "月");
        } else {
            Mon_tv.setText(String.valueOf(Mon_date));
        }

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Tue_date = calendar.get(Calendar.DAY_OF_MONTH);
        if (Tue_date == 1) {
            Tue_tv.setText(String.valueOf(new_month + 2) + "月");
        } else {
            Tue_tv.setText(String.valueOf(Tue_date));
        }

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Wed_date = calendar.get(Calendar.DAY_OF_MONTH);
        if (Wed_date == 1) {
            Wed_tv.setText(String.valueOf(new_month + 2) + "月");
        } else {
            Wed_tv.setText(String.valueOf(Wed_date));
        }

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Thu_date = calendar.get(Calendar.DAY_OF_MONTH);
        if (Thu_date == 1) {
            Thu_tv.setText(String.valueOf(new_month + 2) + "月");
        } else {
            Thu_tv.setText(String.valueOf(Thu_date));
        }

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Fri_date = calendar.get(Calendar.DAY_OF_MONTH);
        if (Fri_date == 1) {
            Fri_tv.setText(String.valueOf(new_month + 2) + "月");
        } else {
            Fri_tv.setText(String.valueOf(Fri_date));
        }

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Sat_date = calendar.get(Calendar.DAY_OF_MONTH);
        if (Sat_date == 1) {
            Sat_tv.setText(String.valueOf(new_month + 2) + "月");
        } else {
            Sat_tv.setText(String.valueOf(Sat_date));
        }

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Sun_date = calendar.get(Calendar.DAY_OF_MONTH);
        if (Sun_date == 1) {
            Sun_tv.setText(String.valueOf(new_month + 2) + "月");
        } else {
            Sun_tv.setText(String.valueOf(Sun_date));
        }

    }

    //初始化Spinner适配器
    public void initweek() {
        week.clear();
        for (int i = 1; i < 31; i++) {
            if (i == current_week) {
                week.add("        第" + i + "周（本周）      ");
            } else {
                week.add("            第" + i + "周          ");
            }
        }
        week_adapter = new ArrayAdapter<String>(CourseActivity.this,
                android.R.layout.simple_spinner_dropdown_item, week);
        week_spinner.setFocusable(false);
        week_spinner.setAdapter(week_adapter);

        if (selected_week != 0) {
            week_spinner.setSelection(selected_week - 1);
        } else {
            week_spinner.setSelection(current_week - 1);
        }

    }


    public void alarmNotification() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(2017, 3, 24, 20, 35);

        NotificationManager manager = (NotificationManager) CourseActivity.this
                .getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(CourseActivity.this)
                .setContentTitle("通知")
                .setContentText("明天有实验课")
                .setWhen(System.currentTimeMillis())
                .setVibrate(new long[]{0, 1000, 1000, 1000})
                .setLights(Color.GREEN, 1000, 1000)
                .setAutoCancel(true).build();
        manager.notify(1, notification);

//        Intent intent = new Intent(CourseActivity.this,AddCourseDialogActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(CourseActivity.this,
//                0,intent,0);
//
//        AlarmManager alarmManager = (AlarmManager) CourseActivity.this
//                .getSystemService(Context.ALARM_SERVICE);
//        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);


    }

    /*加载menu菜单*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.course_refresh, menu);
        return true;
    }

    /*导航返回按钮设置*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.refresh:
                showRefreshAnimation(item);
                break;
            case R.id.open:
                hideRefreshAnimation();
                break;

            default:
                break;
        }
        return true;
    }


    /**
     * 加载XML类型动画
     */
    private void loadXMLAnimator() {

        //ValueAnimator类型
        final ValueAnimator valueAnimator = (ValueAnimator) AnimatorInflater
                .loadAnimator(CourseActivity.this, R.animator.value_animator);
        //添加动画监听
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //获取动画变化的属性值，并在控制台打印
                float value = (Float) valueAnimator.getAnimatedValue();
                course_fab.setAlpha(value);
            }
        });

        //ObjectAnimator类型
        ObjectAnimator objectAnimator = (ObjectAnimator) AnimatorInflater
                .loadAnimator(CourseActivity.this, R.animator.object_animator);
        objectAnimator.setTarget(course_fab);
        objectAnimator.start();

        //AnimatorSet(动画集合、复合动画)类型
        AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater
                .loadAnimator(CourseActivity.this, R.animator.set_animator);
        animatorSet.setTarget(course_fab);
        animatorSet.start();

    }

    /**
     * 代码加载动画
     */
    private void loadCodeAnimator() {

        //ValueAnimator类型
        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(1, 0, 1);
        valueAnimator.setDuration(1000);
        //添加动画监听
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //获取动画变化的属性值，并在控制台打印
                float value = (Float) valueAnimator.getAnimatedValue();
                course_fab.setAlpha(value);
            }
        });

        //ObjectAnimator类型,"rotationY"为设置的某个属性
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(course_fab, "rotationY",
                0, 360, 0);//绕Y轴从0旋转到360°再返回旋转到0
        objectAnimator.setDuration(1000 * 3);
        objectAnimator.start();

        //AnimatorSet(动画集合、复合动画)类型
        //设置平移推进动画
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(course_fab,
                "translationX",
                0, 400, 0);//X轴向右移动400再返回到原来的地方
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(course_fab,
                "translationY",
                0, 400, -400, 0);//Y轴移动到原始位置的下400又移动到原始位置的上400再移动到原始位置
        //设置缩放动画
        ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(course_fab,
                "scaleX",
                1.0f, 0.3f);//沿X轴从1倍（原图）放大到3倍
        ObjectAnimator objectAnimator4 = ObjectAnimator.ofFloat(course_fab,
                "scaleY",
                1.0f, 0.3f);//沿Y轴从1倍（原图）放大到3倍
        //设置旋转动画
        ObjectAnimator objectAnimator5 = ObjectAnimator.ofFloat(course_fab,
                "rotationX",
                0, 45, -30, 0);
        //创建动画集合对象
        AnimatorSet animatorSet = new AnimatorSet();
        //添加动画并设置动画播放顺序
        animatorSet.play(objectAnimator1).with(objectAnimator2).with(objectAnimator3)
                .with(objectAnimator4).after(objectAnimator5);
        animatorSet.setDuration(1000 * 2);
        animatorSet.start();

    }

    /*显示刷新动画*/
    private void showRefreshAnimation(MenuItem item) {

        refreshItem = item;

        //这里使用一个ImageView设置成MenuItem的ActionView，
        // 这样我们就可以使用这个ImageView显示旋转动画了
        ImageView refreshActionView = (ImageView) getLayoutInflater()
                .inflate(R.layout.course_refresh_action, null);
        refreshActionView.setImageResource(R.mipmap.ic_action_refresh);
        refreshItem.setActionView(refreshActionView);

        //显示刷新动画

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(refreshActionView,
                "rotation", 0, 360);
        objectAnimator.setRepeatMode(ObjectAnimator.RESTART);
        objectAnimator.setDuration(2000);
        objectAnimator.setRepeatCount(Animation.INFINITE);
        objectAnimator.start();

//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.refresh);
//        animation.setRepeatMode(Animation.RESTART);
//        animation.setRepeatCount(Animation.INFINITE);
//        refreshActionView.startAnimation(animation);

    }


    /*关闭刷新动画*/
    private void hideRefreshAnimation() {
        if (refreshItem != null) {
            View view = refreshItem.getActionView();
            if (view != null) {
                view.clearAnimation();
                refreshItem.setActionView(null);
            }
        }
    }


}
