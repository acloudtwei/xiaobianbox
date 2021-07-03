package com.example.one.activity;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.one.R;
import com.example.one.textcolor.textcolor1;
import com.githang.statusbar.StatusBarCompat;
import com.example.one.Request.request1;
import com.loopj.android.image.SmartImageView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class function4_preview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function4_preview);
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.top_color),false);
        textcolor();
        SmartImageView img = (SmartImageView) findViewById(R.id.function4_preview_img) ;
        Glide.with(this).load(R.mipmap.dingdings).into(img);
        Intent intent =getIntent();
        String url = intent.getStringExtra("url");
        String api = intent.getStringExtra("api");
        String type = intent.getStringExtra("type");
        request1.getphoto(img,url,api,type);
    }

    private void textcolor()
    {
        // 作用是修改字体的颜色
        TextView function4_preview_text = (TextView) findViewById(R.id.function4_preview_text);
        textcolor1.setTextViewStyles(function4_preview_text);
    }
}