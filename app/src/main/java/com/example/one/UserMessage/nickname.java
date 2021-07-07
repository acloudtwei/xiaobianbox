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
import com.example.one.activity.myactivity;
import com.example.one.sql.User;
import com.example.one.textcolor.textcolor1;
import com.githang.statusbar.StatusBarCompat;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class nickname extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nickname);
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.top_color),false);
        textcolor();

    }

    public void nick_submit(View view)
    {
        EditText nick_nicknames = (EditText) findViewById(R.id.nick_nicknames);
        final User user = BmobUser.getCurrentUser(User.class);
        user.setNickname(nick_nicknames.getText().toString().trim());
        user.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    showToast("修改昵称成功：" + user.getNickname());
                } else {
                   showToast("修改昵称失败：" + e.getMessage());
                }
            }
        });

    }

    public void nick_back(View view)
    {
        Intent intent = new Intent(nickname.this, accounts.class);
        startActivity(intent);
        finish();
    }


    private void textcolor() {
        // 作用是修改字体的颜色
        TextView nick_nickname = (TextView) findViewById(R.id.nick_nickname);
        TextView function1_text = (TextView) findViewById(R.id.function1_text);
        textcolor1.setTextViewStyles(function1_text);
        textcolor1.setTextViewStyles(nick_nickname);
    }

}