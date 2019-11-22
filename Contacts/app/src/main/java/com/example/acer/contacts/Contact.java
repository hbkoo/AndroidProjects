package com.example.acer.contacts;

/**
 * 联系人信息
 */

public class Contact {

    private String id;
    private String name;
    private String telephone;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Contact[id=" + id + ",name=" + name + ",telephone=" + telephone
                + "]";
    }
}
