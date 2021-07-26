package com.example.one.activity;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Build;
import android.os.Bundle;


import com.example.one.MainActivity;
import com.example.one.sql.myphoto;
import com.example.one.sql.notice;
import com.example.one.sql.spfunction;
import com.example.one.sql.yiyan;
import com.example.one.textcolor.*;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.one.R;
import com.githang.statusbar.StatusBarCompat;
import com.google.gson.Gson;
import com.ycbjie.notificationlib.NotificationUtils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;
import cn.bmob.v3.listener.SQLQueryListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class homeactivity extends AppCompatActivity {

    private static final int GET_IMG = 1001;
    private TextView home;
    private TextView function;
    private TextView my;
    private TextView top2;
    private Handler handler;
    private List<String> douyin_titles = new ArrayList<>();
    private List<String> douyin_hots = new ArrayList<>();
    private List<String> weibo_titles = new ArrayList<>();
    private List<String> weibo_hots = new ArrayList<>();
    private List<String> weibo_urls = new ArrayList<>();
    private List<String> zhihu_titles = new ArrayList<>();
    private List<String> zhihu_querys = new ArrayList<>();
    private List<String> zhihu_urls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeactivity);
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.top_color),false);
        init();
        query();
        getdouyin();
        getweibo();
        getzhihu();
        get_backimg();
        get_spapi();

        BmobUser.fetchUserJsonInfo(new FetchUserInfoListener<String>() {
            @Override
            public void done(String json, BmobException e) {
                if (e != null) {
                    Toast.makeText(homeactivity.this,"你已进入黑名单用户列表：" + e.getMessage(),Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(homeactivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                if(msg.what == GET_IMG)
                {
                    top2.setText(msg.obj.toString());
                }
                return true;
            }
        });

        NotificationUtils notificationUtils = new NotificationUtils(this);
        notificationUtils.sendNotification(1,"微信公众号：软件分享课堂","关注公众号获取最新资源与黑科技软件！",R.mipmap.logo);

    }


    private void init(){
        home = (TextView) findViewById(R.id.home);
        function = (TextView) findViewById(R.id.function);
        my = (TextView) findViewById(R.id.my);
        top2 = (TextView) findViewById(R.id.layout_top2);
        textcolor();
        getTopText();
        query();
    }

    private void query() { //查询数据库，获取的数据存在数组里面
        TextView notices = (TextView) findViewById(R.id.notices);
        String sql = "select * from notice";
        BmobQuery<notice> bmobQuery = new BmobQuery<>();
        bmobQuery.setSQL(sql);
        bmobQuery.doSQLQuery(new SQLQueryListener<notice>() {
            @Override
            public void done(BmobQueryResult<notice> bmobQueryResult, BmobException e) {
                if (e == null) {
                    List<notice> list = (List<notice>) bmobQueryResult.getResults();
                    notices.setText(list.get(0).getNotice());
                } else {
                    notices.setText("网络错误 or 程序错误");

                }
            }
        });
    }

    private void get_spapi()
    {
        String sql = "select * from spfunction";
        BmobQuery<spfunction> bmobQuery = new BmobQuery<>();
        bmobQuery.setSQL(sql);
        bmobQuery.doSQLQuery(new SQLQueryListener<spfunction>() {
            @Override
            public void done(BmobQueryResult<spfunction> bmobQueryResult, BmobException e) {
                if (e == null) {
                    List<spfunction> list = (List<spfunction>) bmobQueryResult.getResults();
                    SharedPreferences sp = getSharedPreferences("api", Context.MODE_PRIVATE);
                    String api = list.get(1).getUsing_api();
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putString("api", api);
                    edit.commit();
                } else {
                    SharedPreferences sp = getSharedPreferences("api", Context.MODE_PRIVATE);
                    String api = "https://api.itwei.top/163music";
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putString("api", api);
                    edit.commit();
                }
            }
        });
    }

    private void get_backimg() // 使用缓存技术
    {
        String sql = "select * from myphoto";
        BmobQuery<myphoto> bmobQuery = new BmobQuery<>();
        bmobQuery.setSQL(sql);
        bmobQuery.doSQLQuery(new SQLQueryListener<myphoto>() {
            @Override
            public void done(BmobQueryResult<myphoto> bmobQueryResult, BmobException e) {
                if (e == null) {
                    List<myphoto> list = (List<myphoto>) bmobQueryResult.getResults();
                    SharedPreferences sp = getSharedPreferences("backimg", Activity.MODE_PRIVATE);
                    String backimg = list.get(0).getUrl();
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putString("backimg", backimg);
                    edit.commit();
                    //Picasso.with(myactivity.this).load(list.get(0).getUrl()).into(myactivity_img);
                } else {
                    SharedPreferences sp = getSharedPreferences("backimg", Activity.MODE_PRIVATE);
                    String backimg = "https://tva1.sinaimg.cn/large/0072Vf1pgy1foxkjenkjaj31hc0u0dwt.jpg";
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putString("backimg", backimg);
                    edit.commit();
                    //Picasso.with(myactivity.this).load("https://tva1.sinaimg.cn/large/0072Vf1pgy1foxkjenkjaj31hc0u0dwt.jpg").into(myactivity_img);
                }
            }
        });
    }

    private void getdouyin()
    {
        String url = "https://tenapi.cn/douyinresou/";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                douyin_titles.add("网络错误！");
                douyin_hots.add("网络错误!");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String responseString = response.body().string();
                try {
                    JSONObject jsonobject = new JSONObject(responseString);
                    if(jsonobject.optString("data").equals("200"))
                    {
                        JSONArray jsonArray = jsonobject.optJSONArray("list");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonobjects = jsonArray.optJSONObject(i);
                            douyin_titles.add(jsonobjects.optString("name"));
                            douyin_hots.add(jsonobjects.optString("hot"));
                        }
                        SharedPreferences sp = getSharedPreferences("douyin", Activity.MODE_PRIVATE);
                        Gson douyin_gson = new Gson();
                        String douyin_jsontitle = douyin_gson.toJson(douyin_titles);
                        String douyin_jsonhot = douyin_gson.toJson(douyin_hots);
                        SharedPreferences.Editor edit = sp.edit();
                        edit.putString("douyin_json_title", douyin_jsontitle);
                        edit.putString("douyin_json_hot", douyin_jsonhot);
                        edit.commit();

                    }else{
                        douyin_titles.add("接口失效！");
                        douyin_hots.add("接口失效!");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } }
        });
    }

    private void getweibo()
    {
        String url = "https://tenapi.cn/resou/";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                weibo_titles.add("网络错误！");
                weibo_hots.add("网络错误!");
                weibo_urls.add("https://tenapi.cn/resou/");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String responseString = response.body().string();
                try {
                    JSONObject jsonobject = new JSONObject(responseString);
                    if(jsonobject.optString("data").equals("200"))
                    {
                        JSONArray jsonArray = jsonobject.optJSONArray("list");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonobjects = jsonArray.optJSONObject(i);
                            weibo_titles.add(jsonobjects.optString("name"));
                            weibo_hots.add(jsonobjects.optString("hot"));
                            weibo_urls.add(jsonobjects.optString("url"));
                        }
                        SharedPreferences sp = getSharedPreferences("weibo", Activity.MODE_PRIVATE);
                        Gson douyin_gson = new Gson();
                        String weibo_jsontitle = douyin_gson.toJson(weibo_titles);
                        String weibo_jsonhot = douyin_gson.toJson(weibo_hots);
                        String weibo_jsonurl = douyin_gson.toJson(weibo_urls);
                        SharedPreferences.Editor edit = sp.edit();
                        edit.putString("weibo_json_title", weibo_jsontitle);
                        edit.putString("weibo_json_hot", weibo_jsonhot);
                        edit.putString("weibo_json_url", weibo_jsonurl);
                        edit.commit();
                    }else{
                        weibo_titles.add("接口失效！");
                        weibo_hots.add("接口失效!");
                        weibo_urls.add("https://tenapi.cn/resou/");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } }
        });
    }

    private void getzhihu()
    {
        String url = "https://tenapi.cn/zhihuresou/";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                zhihu_titles.add("网络错误！");
                zhihu_querys.add("网络错误!");
                zhihu_urls.add("https://tenapi.cn/zhihuresou/");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String responseString = response.body().string();
                try {
                    JSONObject jsonobject = new JSONObject(responseString);
                    if(jsonobject.optString("data").equals("200"))
                    {
                        JSONArray jsonArray = jsonobject.optJSONArray("list");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonobjects = jsonArray.optJSONObject(i);
                            zhihu_titles.add(jsonobjects.optString("name"));
                            zhihu_querys.add(jsonobjects.optString("query"));
                            zhihu_urls.add(jsonobjects.optString("url"));
                        }
                        SharedPreferences sp = getSharedPreferences("zhihu", Activity.MODE_PRIVATE);
                        Gson douyin_gson = new Gson();
                        String zhihu_jsontitle = douyin_gson.toJson(zhihu_titles);
                        String zhihu_jsonquery = douyin_gson.toJson(zhihu_querys);
                        String zhihu_jsonurl = douyin_gson.toJson(zhihu_urls);
                        SharedPreferences.Editor edit = sp.edit();
                        edit.putString("zhihu_json_title", zhihu_jsontitle);
                        edit.putString("zhihu_json_query", zhihu_jsonquery);
                        edit.putString("zhihu_json_url", zhihu_jsonurl);
                        edit.commit();
                    }else{
                        zhihu_titles.add("接口失效！");
                        zhihu_querys.add("接口失效!");
                        zhihu_urls.add("https://tenapi.cn/zhihuresou/");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } }
        });
    }

    private void textcolor()
    {
        // 作用是修改字体的颜色
        TextView notice = (TextView) findViewById(R.id.notice);
        textcolor1.setTextViewStyles(home);
        textcolor1.setTextViewStyles(function);
        textcolor1.setTextViewStyles(my);
        textcolor1.setTextViewStyles(notice);
    }

    public void onclick_function(View view)
    {
        Intent intent = new Intent(homeactivity.this,functionactivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    public void onclick_my(View view)
    {
        Intent intent = new Intent(homeactivity.this,myactivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void getTopText() {
        String sql = "select * from yiyan";
        BmobQuery<yiyan> bmobQuery = new BmobQuery<>();
        bmobQuery.setSQL(sql);
        bmobQuery.doSQLQuery(new SQLQueryListener<yiyan>() {
            @Override
            public void done(BmobQueryResult<yiyan> bmobQueryResult, BmobException e) {
                if (e == null) {
                    List<yiyan> list = (List<yiyan>) bmobQueryResult.getResults();
                    String url = list.get(0).getUrl();
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(url).get().build();
                    Call call = client.newCall(request);
                    call.enqueue(new Callback() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            top2.setText("小编盒子，一个beta版本工具箱~");
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            if (response.isSuccessful()) {
                                JSONObject jsonobject = null;
                                try {
                                    jsonobject = new JSONObject(response.body().string());
                                    Message message = new Message();
                                    message.what = GET_IMG;
                                    message.obj = jsonobject.optString("msg");
                                    handler.sendMessage(message);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                } else {
                    top2.setText("小编盒子，一个beta版本工具箱~");
                }
            }
        });
    }
}
