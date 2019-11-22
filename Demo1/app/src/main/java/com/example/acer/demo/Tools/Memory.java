package com.example.acer.demo.Tools;

import java.io.Serializable;

/**
 * 备忘录
 */

public class Memory implements Serializable {
    String id;
    String time, content;

    public Memory(String time, String content) {
        this.time = time;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
