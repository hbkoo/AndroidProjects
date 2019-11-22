package com.example.acer.experimenttext2.Class;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.crud.DataSupport;

/**
 * Created by acer on 2017/3/2.
 */

public class StuLeave extends DataSupport implements Parcelable{

    private int id;
    private String name,class_,number,telephone,reason;

    public StuLeave(){}

    public StuLeave(String name,String class_,String number,String telephone,String reason){
        this.name = name;
        this.class_ = class_;
        this.number = number;
        this.telephone = telephone;
        this.reason = reason;
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

    public String getNumber(){
        return number;
    }

    public void setNumber(String number){
        this.number = number;
    }

    public String getTelephone(){
        return telephone;
    }

    public void setTelephone(String telephone){
        this.telephone = telephone;
    }

    public String getReason(){
        return reason;
    }

    public void setReason(String reason){
        this.reason = reason;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(class_);
        dest.writeString(number);
        dest.writeString(telephone);
        dest.writeString(reason);
    }

    public static final Parcelable.Creator<StuLeave> CREATOR = new
            Creator<StuLeave>() {

                @Override
                public StuLeave createFromParcel(Parcel source) {
                    StuLeave stu_leave = new StuLeave();
                    stu_leave.name = source.readString();
                    stu_leave.class_ = source.readString();
                    stu_leave.number = source.readString();
                    stu_leave.telephone = source.readString();
                    stu_leave.reason = source.readString();
                    return stu_leave;
                }

                @Override
                public StuLeave[] newArray(int size){
                    return new StuLeave[size];
                }
    };

}
