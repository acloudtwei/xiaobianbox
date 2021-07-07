package com.example.one.My;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.one.MainActivity;
import com.example.one.R;
import com.example.one.UserMessage.email;
import com.example.one.activity.BaseActivity;
import com.example.one.activity.homeactivity;
import com.example.one.activity.myactivity;
import com.example.one.sql.User;
import com.example.one.sql.authorconfig;
import com.example.one.textcolor.textcolor1;
import com.example.one.util.StringUtils;
import com.githang.statusbar.StatusBarCompat;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class PassWord extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_word);
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.top_color),false);
        textcolor();
        change_psws();
    }

    private void change_psws()
    {
        Button change_psw = (Button) findViewById(R.id.change_psw);
        EditText before_psws = (EditText) findViewById(R.id.before_psws);
        EditText after_psws = (EditText) findViewById(R.id.after_psws);
        change_psw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = BmobUser.getCurrentUser(User.class);
                if (StringUtils.isEmpty(before_psws.getText().toString().trim())) {
                    showToast("请输入原密码");
                    return;
                }
                if (StringUtils.isEmpty(after_psws.getText().toString().trim())) {
                    showToast("请输入新密码");
                    return;
                }

                BmobUser.updateCurrentUserPassword(before_psws.getText().toString().trim(), after_psws.getText().toString().trim(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            showToast("密码修改成功！");
                            BmobUser.logOut();
                            Intent intent = new Intent(PassWord.this, MainActivity.class);
                            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
                            showToast(e.getMessage());
                        }
                    }
                });
            }
        });

//                if (StringUtils.isEmpty(before_emails.getText().toString().trim())) {
//                    showToast("请输入邮箱");
//                    return;
//                }
//                final String email = user.getEmail();
//                BmobUser.resetPasswordByEmail(email, new UpdateListener() {
//
//                    @Override
//                    public void done(BmobException e) {
//                        if (e == null) {
//                            showToast("重置密码请求成功，请到" + email + "邮箱进行密码重置操作");
//                        } else {
//                            Log.e("BMOB", e.toString());
//                            showToast(e.getMessage());
//                        }
//                    }
//                });

    }

    public void main_function(View view)
    {
        Intent intent = new Intent(PassWord.this,myactivity.class);
        startActivity(intent);
        finish();
    }

    private void textcolor()
    {
        // 作用是修改字体的颜色
        TextView before_psw = (TextView) findViewById(R.id.before_psw);
        TextView after_psw = (TextView) findViewById(R.id.after_psw);
        TextView function1_text = (TextView) findViewById(R.id.function1_text);
        textcolor1.setTextViewStyles(function1_text);
        textcolor1.setTextViewStyles(before_psw);
        textcolor1.setTextViewStyles(after_psw);
    }

}