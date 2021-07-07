package com.example.one.UserMessage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.one.My.accounts;
import com.example.one.R;
import com.example.one.activity.BaseActivity;
import com.example.one.sql.User;
import com.example.one.textcolor.textcolor1;
import com.githang.statusbar.StatusBarCompat;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class qq extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qq);
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.top_color),false);
        textcolor();
    }

    public void qq_submit(View view)
    {
        EditText qq_qqnames = (EditText) findViewById(R.id.qq_qqnames);
        final User user = BmobUser.getCurrentUser(User.class);
        user.setQq(qq_qqnames.getText().toString().trim());
        user.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    showToast("修改QQ成功：" + user.getQq());
                } else {
                    showToast("修改QQ失败：" + e.getMessage());
                }
            }
        });

    }

    public void qq_back(View view)
    {
        Intent intent = new Intent(qq.this, accounts.class);
        startActivity(intent);
        finish();
    }

    private void textcolor() {
        // 作用是修改字体的颜色
        TextView qq_qqname = (TextView) findViewById(R.id.qq_qqname);
        TextView function1_text = (TextView) findViewById(R.id.function1_text);
        textcolor1.setTextViewStyles(function1_text);
        textcolor1.setTextViewStyles(qq_qqname);
    }

}