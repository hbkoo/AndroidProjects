package com.example.acer.experimenttext2.Class;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by acer on 2017/3/4.
 */

public class Course extends DataSupport implements Parcelable{

    private int id;
    private String name,class_,time,classroom,content;

    public Course(){}

    public Course(String name,String class_,String time,String classroom,String content){
        this.name = name;
        this.class_ = class_;
        this.time = time;
        this.classroom = classroom;
        this.content = content;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getClass_(){
        return class_;
    }

    public void setClass_(String class_){
        this.class_ = class_;
    }

    public String getTime(){
        return time;
    }

    public void setTime(String time){
        this.time = time;
    }

    public String getClassroom(){
        return classroom;
    }

    public void setClassroom(String classroom){
        this.classroom = classroom;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String content){
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(class_);
        dest.writeString(time);
        dest.writeString(classroom);
        dest.writeString(content);
    }



    public static final Parcelable.Creator<Course> CREATOR = new
            Creator<Course>() {

                @Override
                public Course createFromParcel(Parcel source) {
                    Course course = new Course();
                    course.name = source.readString();
                    course.class_ = source.readString();
                    course.time = source.readString();
                    course.classroom = source.readString();
                    course.content = source.readString();
                    return course;
                }

                @Override
                public Course[] newArray(int size){
                    return new Course[size];
                }
            };

}
