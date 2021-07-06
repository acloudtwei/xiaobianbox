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

public class email extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.top_color),false);
        textcolor();
    }


    public void email_submit(View view)
    {
        EditText email_emailnames = (EditText) findViewById(R.id.email_emailnames);
        final User user = BmobUser.getCurrentUser(User.class);
        user.setEmail(email_emailnames.getText().toString().trim());
        user.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    showToast("修改邮箱成功：" + user.getEmail());
                } else {
                    showToast("修改邮箱失败：" + e.getMessage());
                }
            }
        });

    }

    public void email_back(View view)
    {
        Intent intent = new Intent(email.this, accounts.class);
        startActivity(intent);
    }


    private void textcolor() {
        // 作用是修改字体的颜色
        TextView email_emailname = (TextView) findViewById(R.id.email_emailname);
        textcolor1.setTextViewStyles(email_emailname);
    }

}