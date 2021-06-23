package com.example.one.sql;

import cn.bmob.v3.BmobObject;

public class background extends BmobObject{
    private int num;
    private String url;
    private String type;

    public String getUrl() {
        return url;
    }
    public void setUrl(String name) {
        this.url = name;
    }
    public int getNum()
    {
        return num;
    }
    public void setNum(int nums)
    {
        this.num = nums;
    }
    public String getType() {
        return type;
    }
    public void setType(String address) {
        this.type = address;
    }

}
