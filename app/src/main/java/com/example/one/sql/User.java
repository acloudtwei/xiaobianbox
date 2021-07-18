package com.example.one.sql;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobGeoPoint;

public class User extends BmobUser {


    private String nickname;

    private String qq;

    private BmobGeoPoint address;

    private boolean wx_sport;
    private boolean music_163;
    private boolean hz;

    public boolean getWx_sport() {
        return wx_sport;
    }

    public User setWx_sport(boolean wx_sport) {
        this.wx_sport = wx_sport;
        return this;
    }

    public boolean getMusic163() {
        return music_163;
    }

    public User setMusic_163(boolean music_163) {
        this.music_163 = music_163;
        return this;
    }

    public boolean getHz() {
        return hz;
    }

    public User setHz(boolean hz) {
        this.hz = hz;
        return this;
    }

    public String getNickname() {
        return nickname;
    }

    public User setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public String getQq() {
        return qq;
    }

    public User setQq(String qq) {
        this.qq = qq;
        return this;
    }

    public BmobGeoPoint getAddress() {
        return address;
    }

    public User setAddress(BmobGeoPoint address) {
        this.address = address;
        return this;
    }
}