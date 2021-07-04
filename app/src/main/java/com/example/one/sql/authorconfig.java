package com.example.one.sql;
import cn.bmob.v3.BmobObject;


public class authorconfig extends BmobObject {

    private String author;
    private String web;
    private String chat;

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String name) {
        this.author = name;
    }
    public String getWeb()
    {
        return web;
    }
    public void setWeb(String webs)
    {
        this.web = webs;
    }
    public String getChat() {
        return chat;
    }
    public void setChat(String address) {
        this.chat = address;
    }

}
