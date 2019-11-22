package com.example.acer.experimenttext2.Class;

/**
 * Created by acer on 2017/3/17.
 */

public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}