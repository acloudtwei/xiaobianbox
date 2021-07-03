package com.example.one.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;

import com.example.one.R;
import com.example.one.textcolor.textcolor1;
import com.example.one.util.imagUtil;
import com.githang.statusbar.StatusBarCompat;
import com.loopj.android.image.SmartImageView;

public class function5_preview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function5_preview);
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.top_color),false);
        textcolor();
        SmartImageView img = (SmartImageView) findViewById(R.id.function3_preview_img) ;
        Intent intent =getIntent();
        String url = intent.getStringExtra("url");
        String base64 = intent.getStringExtra("base64");
        img.setImageUrl(url);
        img.setOnLongClickListener(new View.OnLongClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onLongClick(View v) {
                imagUtil.saveImageToGallery(function5_preview.this, tobitmap(base64));
                return true;
            }
        });
    }

    private Bitmap tobitmap(String base64)
    {
        byte[] bitmapByte = Base64.decode(base64, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bitmapByte, 0, bitmapByte.length);
    }

    private void textcolor()
    {
        // 作用是修改字体的颜色
        TextView function3_preview_text = (TextView) findViewById(R.id.function3_preview_text);
        textcolor1.setTextViewStyles(function3_preview_text);
    }
}