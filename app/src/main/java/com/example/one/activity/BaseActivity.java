package com.example.one.activity;


import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    public Context main_context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main_context = this;

    }
    public void showToast(String msg)
    {
        Toast.makeText(main_context,msg,Toast.LENGTH_SHORT).show();
    }
}
