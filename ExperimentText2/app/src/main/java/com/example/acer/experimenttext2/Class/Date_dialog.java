package com.example.acer.experimenttext2.Class;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.acer.experimenttext2.R;


/**
 * Created by acer on 2017/3/5.
 */

public class Date_dialog implements DatePicker.OnDateChangedListener{

    private Activity activity;
    private String date;
    private String initdate;
    private DatePicker datePicker;
    private AlertDialog ad;

    private int m_year,m_month,m_day;

    public Date_dialog(Activity activity,String initdate){
        this.activity = activity;
        this.initdate = initdate;
    }



    public void init(DatePicker datePicker) {
        Calendar calendar = Calendar.getInstance();
        if (!(null == initdate || "".equals(initdate))) {
        //    initdate();
            date = initdate;
            datePicker.init(m_year,m_month,m_day,this);
        } else {
            date = calendar.get(Calendar.YEAR) + "年"
                    + calendar.get(Calendar.MONTH) + "月"
                    + calendar.get(Calendar.DAY_OF_MONTH) + "日 ";
            datePicker.init(calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH), this);
        }

    }

    public AlertDialog datePicKDialog(final TextView inputDate){
        LinearLayout dateLayout = (LinearLayout) activity.getLayoutInflater()
                .inflate(R.layout.date_picker, null);
        datePicker = (DatePicker) dateLayout.findViewById(R.id.datepicker);


        init(datePicker);

        ad = new AlertDialog.Builder(activity)
                .setTitle(date)
                .setView(dateLayout)
                .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        inputDate.setText(date);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        inputDate.setText(initdate);
                    }
                }).show();

        onDateChanged(null, 0, 0, 0);
        return ad;
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        // 获得日历实例
        Calendar calendar = Calendar.getInstance();


        calendar.set(datePicker.getYear(), datePicker.getMonth(),
                datePicker.getDayOfMonth());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");

        date = sdf.format(calendar.getTime());
        ad.setTitle(date);

    }



//    public void initdate(){
//
//        //2017年3月5日
//
//        String date = spliteString(initdate, "日", "index", "front"); // 日期
//        String yearStr = spliteString(date, "年", "index", "front"); // 年份
//        String monthAndDay = spliteString(date, "年", "index", "back"); // 月日
//        String monthStr = spliteString(monthAndDay, "月", "index", "front"); // 月
//        String dayStr = spliteString(monthAndDay, "月", "index", "back"); // 日
//
//        m_year = Integer.parseInt(yearStr);
//        m_month = Integer.parseInt(monthStr) - 1;   //为什么减一？？
//        m_day = Integer.parseInt(dayStr);
//
//    }

    public static String spliteString(String srcStr, String pattern,
                                      String indexOrLast, String frontOrBack) {
        String result = "";
        int loc = -1;
        if (indexOrLast.equalsIgnoreCase("index")) {
            loc = srcStr.indexOf(pattern); // 取得字符串第一次出现的位置
        } else {
            loc = srcStr.lastIndexOf(pattern); // 最后一个匹配串的位置
        }
        if (frontOrBack.equalsIgnoreCase("front")) {
            if (loc != -1)
                result = srcStr.substring(0, loc); // 截取子串
        } else {
            if (loc != -1)
                result = srcStr.substring(loc + 1, srcStr.length()); // 截取子串
        }
        return result;
    }


}
