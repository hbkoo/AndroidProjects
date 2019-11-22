package com.example.acer.demo.Tools;

/**
 * 通讯录数据
 */

public class PhoneBookItem {
    private String name,telephone;
    private int image;

    public PhoneBookItem(String name, String telephone, int image) {
        this.name = name;
        this.telephone = telephone;
        this.image = image;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

}
