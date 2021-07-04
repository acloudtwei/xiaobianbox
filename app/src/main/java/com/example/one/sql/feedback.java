package com.example.one.sql;

import cn.bmob.v3.BmobObject;

public class feedback extends BmobObject {

    private String message;
    private String username;
    private String username_email;
    private int number;

    public String getUsername() {
        return username;
    }
    public void setUsername(String name) {
        this.username = name;
    }
    public String getUsername_email()
    {
        return username_email;
    }
    public void setUsername_email(String webs)
    {
        this.username_email = webs;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String address) {
        this.message = address;
    }
    public int getNumber(){return number;}
    public void setNumber(int nownum){this.number = nownum;}
}
