package com.example.one;

import cn.bmob.v3.BmobObject;

public class person extends BmobObject {
    private int num;
    private String name;
    private String address;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getNum()
    {
        return num;
    }
    public void setNum(int nums)
    {
        this.num = nums;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
}