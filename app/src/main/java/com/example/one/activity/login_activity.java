package com.example.one.activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.one.MainActivity;
import com.example.one.My.accounts;
import com.example.one.R;
import com.example.one.sql.User;
import com.example.one.sql.myphoto;
import com.example.one.sql.notice;
import com.example.one.util.StringUtils;
import com.githang.statusbar.StatusBarCompat;
import com.squareup.picasso.Picasso;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;

import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;

public class login_activity extends BaseActivity {

    private EditText accountName;
    private EditText accountPassword;
    private Button btnLogin;
    private Button btn_reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logins);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.top_color), false);
        query();
        initView();
        initData();
        initDatas();
    }

    protected void initView() {
        accountName = findViewById(R.id.et_account);
        accountPassword = findViewById(R.id.et_pwd);
        btnLogin = findViewById(R.id.btn_login);
        btn_reset = findViewById(R.id.btn_reset);
    }

    protected void initDatas() {
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login_activity.this,resetPassword.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                //finish();
            }
        });
    }

    protected void initData() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = accountName.getText().toString().trim();
                String pwd = accountPassword.getText().toString().trim();
                login(account, pwd);
            }
        });
    }

    private void query() { //查询数据库，获取的数据存在数组里面
        ImageView top_img = (ImageView) findViewById(R.id.top_img);
        String sql = "select * from myphoto";
        BmobQuery<myphoto> bmobQuery = new BmobQuery<>();
        bmobQuery.setSQL(sql);
        bmobQuery.doSQLQuery(new SQLQueryListener<myphoto>() {
            @Override
            public void done(BmobQueryResult<myphoto> bmobQueryResult, BmobException e) {
                if (e == null) {
                    List<myphoto> list = (List<myphoto>) bmobQueryResult.getResults();
                    Picasso.with(login_activity.this).load(list.get(0).getUrl()).into(top_img);
                } else {
                    Picasso.with(login_activity.this).load("https://tva1.sinaimg.cn/large/0072Vf1pgy1foxkjenkjaj31hc0u0dwt.jpg").into(top_img);
                }
            }
        });
    }

    private void login(String account, String pwd) {
        BmobUser bu2 = new BmobUser();
        if (StringUtils.isEmpty(account)) {
            showToast("请输入账号");
            return;
        }
        if (StringUtils.isEmpty(pwd)) {
            showToast("请输入密码");
            return;
        }
        // 新办法
        BmobUser bmobUser = new BmobUser();
        bmobUser.setUsername(account);
        bmobUser.setPassword(pwd);
        bmobUser.login(new SaveListener<BmobUser>() {
            @Override
            public void done(BmobUser bmobUser , BmobException e) {
                if (e == null) {
                    User user = BmobUser.getCurrentUser(User.class);
                    if(user.getEmailVerified())
                    {
                        showToast("登录成功：" + user.getUsername());
                        Intent intent = new Intent(login_activity.this, homeactivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }else{
                        showToast("你还没有验证邮箱，请验证，谢谢！");
                    }
                } else {
                    showToast("登录失败：" + e.getMessage());
                }
            }
        });
        }

}