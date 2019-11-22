package com.example.acer.demo.Tools;

import java.io.Serializable;

/**
 * 单个控件
 */

public class MainItem{
    private int imageID;
    private String name;

    public MainItem(int imageID, String text) {
        this.imageID = imageID;
        this.name = text;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
