package com.BSP.bean;

public class User {
    private String userName;
    private String password;
    private String tel;
    private int status;
    private String id;

    public User() {

    }

    public User(String name, String password, String tel) {
        this.userName = name;
        this.password = password;
        this.tel = tel;
        this.status = 0;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getTel() {
        return tel;
    }

    public int getStatus() {
        return status;
    }

    public String getId() {
        return id;
    }

    public void setUserName(String name) {
        this.userName = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setId(String id) {
        this.id = id;
    }
}