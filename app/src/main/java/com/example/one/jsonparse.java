package com.example.one;

import com.google.gson.Gson;

public class jsonparse {
    public static javabean getBean (String json)
    {
        Gson gson = new Gson();
        return gson.fromJson(json,javabean.class);
    }
}
