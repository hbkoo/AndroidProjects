package com.example.acer.coolweather.db;

import org.litepal.crud.DataSupport;

/**
 * Created by acer on 2017/4/12.
 */

public class Country extends DataSupport {

    private int id;
    private String countryName;  //记录县的名字
    private String weatherId;    //记录天气id
    private int cityId;          //记录县所属市的Id

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
}
