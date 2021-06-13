package com.example.one.activity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import com.example.one.textcolor.*;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.one.R;
import com.githang.statusbar.StatusBarCompat;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class homeactivity extends AppCompatActivity {

    private static final int GET_IMG = 1001;
    private TextView home;
    private TextView function;
    private TextView my;
    private TextView top2;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeactivity);
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.top_color),false);
        init();
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                if(msg.what == GET_IMG)
                {
                    top2.setText(msg.obj.toString());
                }
                return true;
            }
        });
    }

    private void init(){
        home = (TextView) findViewById(R.id.home);
        function = (TextView) findViewById(R.id.function);
        my = (TextView) findViewById(R.id.my);
        top2 = (TextView) findViewById(R.id.layout_top2);
        textcolor();
        getTopText();
    }

    private void textcolor()
    {
        // 作用是修改字体的颜色
        textcolor1.setTextViewStyles(home);
        textcolor1.setTextViewStyles(function);
        textcolor1.setTextViewStyles(my);
    }

    public void onclick_function(View view)
    {
        Intent intent = new Intent(homeactivity.this,functionactivity.class);
        startActivity(intent);
    }

    private void getTopText()
    {
        String url = "https://xiaojieapi.com/api/v1/get/yiyan";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                top2.setText("威威工具箱，一个beta版本工具箱~");
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    JSONObject jsonobject = null;
                    try {
                        jsonobject = new JSONObject(response.body().string());
                        Message message= new Message();
                        message.what = GET_IMG;
                        message.obj = jsonobject.optString("msg");
                        handler.sendMessage(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

}
