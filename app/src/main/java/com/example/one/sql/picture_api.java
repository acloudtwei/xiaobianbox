package com.example.one.sql;
import cn.bmob.v3.BmobObject;

public class picture_api extends BmobObject {

    private int num;
    private String api;
    private String choose;
    private String other;

    public String getApi() {
        return api;
    }
    public void setApi(String name) {
        this.api = name;
    }
    public int getNum()
    {
        return num;
    }
    public void setNum(int nums)
    {
        this.num = nums;
    }
    public String getChoose() {
        return choose;
    }
    public void setChoose(String address) {
        this.choose = address;
    }
    public String getOther() { return other; }
    public void setOther(String other) { this.other = other; }

}
