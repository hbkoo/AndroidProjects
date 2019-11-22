package com.example.acer.experimenttext2.Class;

import java.io.Serializable;

/**
 * Created by acer on 2017/3/20.
 */

public class AddCourse implements Serializable{

    private String name,class_,time,content;
    private String [] classroom;
    private int StuCourseSize;

    public AddCourse(){}

    public AddCourse(String name,String class_,String time,String[] classroom,int stuCourseSize,String content){
        this.name = name;
        this.class_ = class_;
        this.time = time;
        this.classroom = new String[classroom.length];
        this.classroom = classroom;
        this.StuCourseSize = stuCourseSize;
        this.content = content;
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

    public String[] getClassroom(){
        return classroom;
    }

    public void setClassroom(String[] classroom){
        this.classroom = new String[classroom.length];
        this.classroom = classroom;
    }

    public int getClassroomSize(){
        return classroom.length;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String content){
        this.content = content;
    }

    public int getStuCourseSize(){
        return StuCourseSize;
    }

    public void setStuCourseSize(int stuCourseSize){
        this.StuCourseSize = stuCourseSize;
    }


}
