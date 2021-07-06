package com.example.one.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.one.R;
import com.example.one.sql.User;
import com.example.one.sql.myphoto;
import com.example.one.textcolor.textcolor1;
import com.example.one.util.StringUtils;
import com.githang.statusbar.StatusBarCompat;
import com.squareup.picasso.Picasso;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class resetPassword extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.top_color), false);
        textcolor();
        query();

    }

    public void initData(View view) {
        EditText reset_email = (EditText) findViewById(R.id.reset_email);
        if (StringUtils.isEmpty(reset_email.getText().toString().trim())) {
            showToast("请输入你的邮箱");
            return;
        }
                final String email = reset_email.getText().toString().trim();
                BmobUser.resetPasswordByEmail(email, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            showToast("重置密码请求成功，请到" + email + "邮箱进行密码重置操作");
                            Intent intent = new Intent(resetPassword.this,login_activity.class);
                            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
                            showToast(e.getMessage());
                        }
                    }
                });
    }


    private void query() { //查询数据库，获取的数据存在数组里面
        ImageView reset_img = (ImageView) findViewById(R.id.reset_img);
        String sql = "select * from myphoto";
        BmobQuery<myphoto> bmobQuery = new BmobQuery<>();
        bmobQuery.setSQL(sql);
        bmobQuery.doSQLQuery(new SQLQueryListener<myphoto>() {
            @Override
            public void done(BmobQueryResult<myphoto> bmobQueryResult, BmobException e) {
                if (e == null) {
                    List<myphoto> list = (List<myphoto>) bmobQueryResult.getResults();
                    Picasso.with(resetPassword.this).load(list.get(0).getUrl()).into(reset_img);
                } else {
                    Picasso.with(resetPassword.this).load("https://tva1.sinaimg.cn/large/0072Vf1pgy1foxkjenkjaj31hc0u0dwt.jpg").into(reset_img);
                }
            }
        });
    }

    private void textcolor()
    {
        // 作用是修改字体的颜色
        TextView tips = (TextView) findViewById(R.id.tips);
        textcolor1.setTextViewStyles(tips);
    }
}