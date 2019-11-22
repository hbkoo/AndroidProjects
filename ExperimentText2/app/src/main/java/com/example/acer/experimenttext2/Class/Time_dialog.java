package com.example.acer.experimenttext2.Class;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.acer.experimenttext2.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by acer on 2017/3/5.
 */

public class Time_dialog implements TimePicker.OnTimeChangedListener {

    private Activity activity;
    private String inittime;
    private AlertDialog ad;
    private TimePicker timePicker;
    private String time;
    private int m_hour,m_minute;

    public Time_dialog(Activity activity,String inittime){
        this.activity = activity;
        this.inittime = inittime;
    }

    public void init(TimePicker timePicker){

        Calendar calendar = Calendar.getInstance();
        if (!(null == inittime || "".equals(inittime))) {
            initdate();
            time = inittime;
            timePicker.setCurrentHour(m_hour);
            timePicker.setCurrentMinute(m_minute);
        } else {

            //设置小时和分钟
            timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY) );
            timePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
            time = timePicker.getCurrentHour() + ":" + calendar.get(Calendar.MINUTE);
        }


    }

    public AlertDialog datePicKDialog(final TextView inputDate){
        LinearLayout dateLayout = (LinearLayout) activity.getLayoutInflater()
                .inflate(R.layout.time_picker, null);
        timePicker = (TimePicker) dateLayout.findViewById(R.id.timepicker);

        init(timePicker);
        timePicker.setIs24HourView(true);
        timePicker.setOnTimeChangedListener(this);

        ad = new AlertDialog.Builder(activity)
                .setTitle(time)
                .setView(dateLayout)
                .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        inputDate.setText(time);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        inputDate.setText(inittime);
                    }
                }).show();

        return ad;
    }


    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

        Calendar calendar = Calendar.getInstance();
        //把calendar设置成选中的时间
        calendar.set(0,0,0,timePicker.getCurrentHour(),timePicker.getCurrentMinute());
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        time = sdf.format(calendar.getTime());
        ad.setTitle(time);
    }

    public void initdate(){

        String hourStr = spliteString(inittime, ":", "index", "front"); // 时
        String minuteStr = spliteString(inittime, ":", "index", "back"); // 分

        m_hour = Integer.parseInt(hourStr);
        m_minute = Integer.parseInt(minuteStr) ;

    }

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
