package com.example.one.activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.one.MainActivity;
import com.example.one.R;
import com.example.one.sql.User;
import com.example.one.util.StringUtils;
import com.githang.statusbar.StatusBarCompat;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logins);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.top_color), false);
        initView();
        initData();
    }

    protected void initView() {
        accountName = findViewById(R.id.et_account);
        accountPassword = findViewById(R.id.et_pwd);
        btnLogin = findViewById(R.id.btn_login);
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

//    public void onclick2(View v)
//    {
//        String account = accountName.getText().toString().trim();
//        String pwd = accountPassword.getText().toString().trim();
//        login(account, pwd);
//    }

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
                    showToast("登录成功：" + user.getUsername());
                    Intent intent = new Intent(login_activity.this, homeactivity.class);
                    startActivity(intent);
                } else {
                    showToast("登录失败：" + e.getMessage());
                }
            }
        });

//                    @Override 老办法了！！
//                    public void done(BmobQueryResult<BmobUser> bmobQueryResult, BmobException e) {
//                        if (e == null) {
//                            List<BmobUser> list = (List<BmobUser>) bmobQueryResult.getResults();
//                            if(list.size()!=0)
//                            {
//                                if(list.get(0).getEmailVerified())
//                                {
//                                    bmobUser.setUsername(account);
//                                    bmobUser.setPassword(pwd);
//                                    bmobUser.login(new SaveListener<BmobUser>() {
//                                        @Override
//                                        public void done(BmobUser bmobUser, BmobException e) {
//                                            if (e == null) {
//                                                //登录成功后进入主界面
//                                                showToast("登陆成功");
//                                                Intent intent = new Intent(login_activity.this, homeactivity.class);
//                                                startActivity(intent);
//                                            } else {
//                                                showToast(e.getMessage());
//                                            }
//                                        }
//                                    });
//                                }
//                                else {
//                                    showToast("你还没有验证邮箱，请验证，谢谢！");
//                                }
//                            }
//                            else
//                            {
//                                showToast("此账号不存在，请注册！");
//                            }
////                    Toast.makeText(getApplication(), "查询成功：" + list.get(0).getName(), Toast.LENGTH_LONG).show();
//                        } else {
//                            showToast("错误："+e.getMessage());
//                        }
//                    }
//                });
//            }
//        }, 3000);
        }

    private void query() { //查询
        String sql = "select * from BmobUser";
        BmobQuery<BmobUser> bmobQuery = new BmobQuery<>();
        bmobQuery.setSQL(sql);
        bmobQuery.doSQLQuery(new SQLQueryListener<BmobUser>() {
            @Override
            public void done(BmobQueryResult<BmobUser> bmobQueryResult, BmobException e) {
                if (e == null) {
                    List<BmobUser> list = (List<BmobUser>) bmobQueryResult.getResults();
//                    Toast.makeText(getApplication(), "查询成功：" + list.get(0).getName(), Toast.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
//                    Toast.makeText(getApplication(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}