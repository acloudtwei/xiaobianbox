package com.example.one.My;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.one.MainActivity;
import com.example.one.R;
import com.example.one.UserMessage.email;
import com.example.one.UserMessage.nickname;
import com.example.one.UserMessage.qq;
import com.example.one.activity.BaseActivity;
import com.example.one.activity.myactivity;
import com.example.one.sql.User;
import com.example.one.textcolor.textcolor1;
import com.githang.statusbar.StatusBarCompat;
import com.squareup.picasso.Picasso;

import cn.bmob.v3.BmobUser;
import de.hdodenhof.circleimageview.CircleImageView;

public class accounts extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.top_color),false);
        textcolor();
        CircleImageView photoView = (CircleImageView) findViewById(R.id.touxiang);
        User user = BmobUser.getCurrentUser(User.class);
        Picasso.with(accounts.this).load("https://q1.qlogo.cn/headimg_dl?dst_uin="+user.getQq()+"&spec=100").into(photoView);
        showmessage();
        nickname_message();
        username_message();
        email_message();
        qq_message();
    }

    private void nickname_message()
    {
        LinearLayout accout_nickname = (LinearLayout) findViewById(R.id.accout_nickname);
        accout_nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(accounts.this, nickname.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    private void username_message()
    {
        LinearLayout accout_username = (LinearLayout) findViewById(R.id.accout_username);
        accout_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("用户名是唯一的，无法修改");
            }
        });
    }

    private void email_message()
    {
        LinearLayout account_useremail = (LinearLayout) findViewById(R.id.account_useremail);
        account_useremail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(accounts.this, email.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    private void qq_message()
    {
        LinearLayout account_qq = (LinearLayout) findViewById(R.id.account_qq);
        account_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(accounts.this, qq.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }


    private void showmessage()
    {
        TextView accout_nicknamed = (TextView) findViewById(R.id.accout_nicknamed);
        TextView accout_usernamed = (TextView) findViewById(R.id.accout_usernamed);
        TextView account_useremaild = (TextView) findViewById(R.id.account_useremaild);
        TextView account_qqd = (TextView) findViewById(R.id.account_qqd);
        User user = BmobUser.getCurrentUser(User.class);

        accout_nicknamed.setText(user.getNickname());
        accout_usernamed.setText(user.getUsername());
        account_useremaild.setText(user.getEmail());
        account_qqd.setText(user.getQq());

    }

    public void back_myactivity(View view)
    {
        Intent intent = new Intent(accounts.this, myactivity.class);
        startActivity(intent);
        finish();
    }

    private void textcolor()
    {
        // 作用是修改字体的颜色
        TextView accout_nicknames = (TextView) findViewById(R.id.accout_nicknames);
        TextView accout_usernames = (TextView) findViewById(R.id.accout_usernames);
        TextView account_useremails = (TextView) findViewById(R.id.account_useremails);
        TextView account_qqs = (TextView) findViewById(R.id.account_qqs);
        TextView function1_text = (TextView) findViewById(R.id.function1_text);
        textcolor1.setTextViewStyles(function1_text);
        textcolor1.setTextViewStyles(accout_nicknames);
        textcolor1.setTextViewStyles(accout_usernames);
        textcolor1.setTextViewStyles(account_useremails);
        textcolor1.setTextViewStyles(account_qqs);
    }

}