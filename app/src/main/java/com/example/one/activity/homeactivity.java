package com.example.one.activity;


import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import com.example.one.textcolor.*;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.one.R;

public class homeactivity extends AppCompatActivity {

    private TextView home;
    private TextView function;
    private TextView my;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeactivity);
        home = (TextView) findViewById(R.id.home);
        function = (TextView) findViewById(R.id.function);
        my = (TextView) findViewById(R.id.my);
        textcolor();
    }

    private void textcolor()
    {
        textcolor1.setTextViewStyles(home);
        textcolor1.setTextViewStyles(function);
        textcolor1.setTextViewStyles(my);
    }

}
