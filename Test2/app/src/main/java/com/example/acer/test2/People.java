package com.example.acer.test2;

/**
 * 个人信息
 */

class People {

    private int id;
    private String username, password, tel, email, sex;

    public People() {
    }

    People(int id, String username, String password, String tel, String email, String sex) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.tel = tel;
        this.email = email;
        this.sex = sex;
    }

    int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    String getUsername() {
        return username;
    }

    void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

}
