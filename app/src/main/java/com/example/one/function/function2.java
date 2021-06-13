package com.example.one.function;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.one.R;
import com.example.one.activity.functionactivity;
import com.example.one.textcolor.textcolor1;
import com.githang.statusbar.StatusBarCompat;

public class function2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_functionc2);
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.top_color),false);
        textcolor();
    }

    private void textcolor()
    {
        // 作用是修改字体的颜色
        TextView function1_text = (TextView) findViewById(R.id.function1_text);
        textcolor1.setTextViewStyles(function1_text);
    }

    public void main_function(View view)
    {
        Intent intent = new Intent(this, functionactivity.class);
        startActivity(intent);
    }
}