package com.example.one.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.one.R;
import com.example.one.sql.User;
import com.example.one.sql.myphoto;
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
import cn.bmob.v3.listener.UpdateListener;

public class register_activity extends BaseActivity {

    private EditText etAccount;
    private EditText etPwd;
    private EditText etmail;
    private Button btnResiter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registers);
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.top_color),false);
        initView();
        initData();
        query();
    }

    protected void initView() { // 类似于注册
        etAccount = findViewById(R.id.et_account);
        etPwd = findViewById(R.id.et_pwd);
        btnResiter = findViewById(R.id.btn_register);
        etmail = findViewById(R.id.et_email);
    }

    protected void initData() { // 按钮监听方法，更好用
        btnResiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = etAccount.getText().toString().trim();
                String pwd = etPwd.getText().toString().trim();
                String email = etmail.getText().toString().trim();
                register(account, pwd ,email);
            }
        });
    }

    private void query() { //查询数据库，获取的数据存在数组里面
        ImageView register_img = (ImageView) findViewById(R.id.register_img);
        String sql = "select * from myphoto";
        BmobQuery<myphoto> bmobQuery = new BmobQuery<>();
        bmobQuery.setSQL(sql);
        bmobQuery.doSQLQuery(new SQLQueryListener<myphoto>() {
            @Override
            public void done(BmobQueryResult<myphoto> bmobQueryResult, BmobException e) {
                if (e == null) {
                    List<myphoto> list = (List<myphoto>) bmobQueryResult.getResults();
                    Picasso.with(register_activity.this).load(list.get(0).getUrl()).into(register_img);
                } else {
                    Picasso.with(register_activity.this).load("https://tva1.sinaimg.cn/large/0072Vf1pgy1foxkjenkjaj31hc0u0dwt.jpg").into(register_img);
                }
            }
        });
    }


    private void register (String account, String pwd, String email){
            if (StringUtils.isEmpty(account)) {
                showToast("请输入账号");
                return;
            }
            if (StringUtils.isEmpty(pwd)) {
                showToast("请输入密码");
                return;
            }
            if (StringUtils.isEmpty(email)) {
                showToast("请输入邮箱");
                return;
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            {
                showToast("邮箱输入不正常");
                return;
            }

        BmobUser bmobUser = new BmobUser();
        bmobUser.setUsername(account);
        bmobUser.setEmail(email);
        bmobUser.setPassword(pwd);
        bmobUser.signUp(new SaveListener<BmobUser>() {
            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                if (e == null) {
                    Toast.makeText(register_activity.this,"注册成功，请到" + email + "邮箱中进行激活,如果没有激活无法登录!",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(register_activity.this,login_activity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    showToast("注册失败（提示信息）:" + e.getMessage());
                }
            }
        });

        //            HashMap<String, Object> params = new HashMap<String, Object>();
//            params.put("mobile", account);
//            params.put("password", pwd);
//            Api.config(ApiConfig.REGISTER, params).postRequest(this, new TtitCallback() {
//                @Override
//                public void onSuccess(final String res) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            showToast(res);
//                        }
//                    });
//                }
//
//                @Override
//                public void onFailure(Exception e) {
//                    Log.e("onFailure", e.toString());
//                }
//            });
        }
        private void request_email(String email) // 隐藏战神
        {
            BmobUser.requestEmailVerify(email, new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if(e==null){
                        Toast.makeText(register_activity.this,"请求验证邮件成功，请到" + email + "邮箱中进行激活,如果没有激活无法登录!",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(register_activity.this,login_activity.class);
                        startActivity(intent);
                    }else{
                        showToast("发送失败:" + e.getMessage());
                    }
                }
            });
        }
    }