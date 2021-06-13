package com.example.one.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.one.R;
import com.example.one.api.text_api;

public class functionactivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_functionactivity);
        TextView layout_top2 = (TextView) findViewById(R.id.layout_top2);
        text_api.getTopText(layout_top2);
    }
}