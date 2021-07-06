package com.example.one;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.one.activity.BaseActivity;
import com.example.one.activity.login_activity;
import com.example.one.activity.myactivity;
import com.example.one.sql.*;
import com.example.one.activity.homeactivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.one.activity.register_activity;
import com.githang.statusbar.StatusBarCompat;
import com.loopj.android.image.SmartImageView;
import com.squareup.picasso.Picasso;


import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.update.BmobUpdateAgent;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private TextView texts;
    private Button btn_registers;
    private SmartImageView bg;
    private SmartImageView bgs;
    private Handler handler;
    private static final int SUCCESS = 1;
    private static final int ERROR = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, "83e23941491c7cce0cda92a4fdfe54c8");
        setContentView(R.layout.activity_mains);
        BmobUpdateAgent.initAppVersion();
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.top_color),false);
        get_bgs();
        initView();
        initData();
            if (BmobUser.isLogin()) {
                User user = BmobUser.getCurrentUser(User.class);
                if(user.getEmailVerified() != null) {
                    Toast.makeText(MainActivity.this, "已经登录：" + user.getUsername(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, homeactivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else {
                    Toast.makeText(MainActivity.this, "账号："+user.getUsername()+"还没有验证邮箱！", Toast.LENGTH_LONG).show();
                }
            } else {
                    Toast.makeText(MainActivity.this, "尚未登录", Toast.LENGTH_LONG).show();
        }

//        BmobUpdateAgent.setUpdateOnlyWifi(false);
//        BmobUpdateAgent.update(this);
//        handler = new Handler(new Handler.Callback() {
//            @Override
//            public boolean handleMessage(@NonNull Message msg) {
//                if(msg.what == SUCCESS)
//                {
//                    String url = (String) msg.obj;
//                    bgs.setImageUrl(url);
//                }
//                else {
//                    bgs.setImageResource(R.mipmap.bgs);
//                }
//                return true;
//            }
//        });

//        delete();
//        create();
//        update();
//        query();

//        texts = (TextView) findViewById(R.id.text);
//        bg = (SmartImageView) findViewById(R.id.bgs);
//        getAsyn("https://restapi.amap.com/v3/weather/weatherInfo?key=07ce7c4969a77c908df5bc177601983d&city=河源");
//        SmartImageView images = (SmartImageView) findViewById(R.id.abc);
//        images.setImageUrl("https://tva2.sinaimg.cn/large/0072Vf1pgy1foxk744kw6j31hc0u0ni5.jpg");
//        getbg("https://api.dongmanxingkong.com/suijitupian/acg/1080p/index.php?return=json");
    }

    protected void initView() { // 一个用来注册的方法（查找）
        btn_registers = findViewById(R.id.btn_register);
        bgs = (SmartImageView) findViewById(R.id.main_bgs);
    }

    protected void initData() {
        btn_registers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, register_activity.class);
                startActivity(intent);
            }
        });
    }

    public void onclick1(View v)
    {
//        Intent intent = new Intent(MainActivity.this,homeactivity.class);
//        startActivity(intent);
        Intent intent = new Intent(MainActivity.this, login_activity.class);
        startActivity(intent);
//        BmobUser.requestEmailVerify("1559295172@qq.com", new UpdateListener() {
//            @Override
//            public void done(BmobException e) {
//                if(e==null){
//                    Toast.makeText(MainActivity.this,"请求验证邮件成功，请到 1559295172@qq.com 邮箱中进行激活,如果没有激活无法登录!",Toast.LENGTH_LONG).show();
////                        showToast("请求验证邮件成功，请到" + email + "邮箱中进行激活,如果没有激活无法登录!");
//                }else{
//                    Toast.makeText(MainActivity.this,"失败",Toast.LENGTH_LONG).show();
//                }
//            }
//        });
    }


//        bmobQuery.getObject("", new QueryListener<person>() {
//            @Override
//            public void done(person persons, BmobException e) {
//                if (e == null) {
//                    Toast.makeText(getApplication(), "查询成功：" + persons.getName(), Toast.LENGTH_LONG).show();
//                } else {
//                    Log.e("BMOB", e.toString());
//                    Toast.makeText(getApplication(), e.getMessage(), Toast.LENGTH_LONG).show();
//                }
//            }
//        });

    
    private void get_bgs()
    {
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder().url("https://xiaojieapi.com/api/v1/get/greet").get().build();
//        Call call = client.newCall(request);
//        call.enqueue(new Callback(){
//
//            @Override
//            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//                try {
//                    JSONObject jsonobject = new JSONObject(response.body().string());
//                    Message message = new Message();
//                    message.what = SUCCESS;
//                    message.obj = jsonobject.optString("url");
//                    handler.sendMessage(message);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(@NotNull Call call, @NotNull IOException e) {
//                Message message = new Message();
//                message.what = ERROR;
//                handler.sendMessage(message);
//            }
//        });

        String sql = "select * from background";
        BmobQuery<background> bmobQuery = new BmobQuery<>();
        bmobQuery.setSQL(sql);
        bmobQuery.doSQLQuery(new SQLQueryListener<background>() {
            @Override
            public void done(BmobQueryResult<background> bmobQueryResult, BmobException e) {
                if (e == null) {
                    List<background> list = (List<background>) bmobQueryResult.getResults();
                    bgs.setImageUrl(list.get(0).getUrl());
//                    Message message = new Message();
//                    message.what = SUCCESS;
//                    message.obj = list.get(0).getUrl();
//                    handler.sendMessage(message);

                } else {
                    bgs.setImageResource(R.mipmap.bgs);
//                    Message message = new Message();
//                    message.what = ERROR;
//                    handler.sendMessage(message);
                }
            }
        });
    }
    
    
    
    private void delete() // 删除
    {
        person p2 = new person();
        /// 大问
        p2.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Toast.makeText(getApplication(),"Success",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplication(),e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }

        });

    }

    public void getAsyn(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                texts.setText("Error");

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
//                        JSONObject jsonobject = new JSONObject(response.body().string());
//                        JSONArray jsonarray =jsonobject.getJSONArray("lives");
//                        JSONObject jsonobjects = jsonarray.getJSONObject(0);
//                        String count = jsonobjects.optString("weather");
                    javabean jsonobject = jsonparse.getBean(response.body().string());
                    javabean.Lives count = jsonobject.getLives().get(0);
                    Log.d("Tag",count.getCity());
                    //处理UI需要切换到UI线程处理

                }
                else
                {
                    texts.setText("请求失败");
                }
            }
        });
    }
}