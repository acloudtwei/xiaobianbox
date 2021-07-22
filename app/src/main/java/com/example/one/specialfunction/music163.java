package com.example.one.specialfunction;


import com.example.one.R;
import com.example.one.activity.BaseActivity;
import com.example.one.activity.functionactivity;
import com.example.one.sql.myphoto;
import com.example.one.sql.spfunction;
import com.example.one.textcolor.textcolor1;
import com.example.one.util.StringUtils;
import com.example.one.weibo_webview;
import com.githang.statusbar.StatusBarCompat;
import com.squareup.picasso.Picasso;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class music163 extends BaseActivity {

    private static final int SUCCESS = 200;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music163);
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.top_color),false);
        textcolor();
        query();
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                if(msg.what == SUCCESS)
                {
                    showToast(msg.obj.toString());
                }
                return true;
            }
        });

    }

    public void spfunction2_login(View view)
    {
        EditText spfunction2_account = (EditText) findViewById(R.id.spfunction2_account);
        EditText spfunction2_pwd = (EditText) findViewById(R.id.spfunction2_pwd);
        String username = spfunction2_account.getText().toString().trim();
        String psw = spfunction2_pwd.getText().toString().trim();
        if (StringUtils.isEmpty(username)) {
            showToast("请输入手机号！");
            return;
        }
        if (StringUtils.isEmpty(psw)) {
            showToast("请输入密码！");
            return;
        }

        String sql = "select * from spfunction";
        BmobQuery<spfunction> bmobQuery = new BmobQuery<>();
        bmobQuery.setSQL(sql);
        bmobQuery.doSQLQuery(new SQLQueryListener<spfunction>() {
            @Override
            public void done(BmobQueryResult<spfunction> bmobQueryResult, BmobException e) {
                if (e == null) {
                    List<spfunction> list = (List<spfunction>) bmobQueryResult.getResults();
                    String url = list.get(1).getUsing_api()+"/?do=login&uin="+username+"&pwd="+psw;
//                    String url = "https://music.blibli.tk/?do=login&uin=18144639064&pwd=acloudtwei9999";
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(url).build();
                    Call call = client.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Message message = new Message();
                            message.what = SUCCESS;
                            message.obj = "网络错误："+e.getMessage()+ "  " +url;
                            handler.sendMessage(message);
                        }
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                JSONObject jsonobject = new JSONObject(response.body().string());
                                if(jsonobject.optString("code").equals("200"))
                                {
                                    JSONObject jsonobjects = new JSONObject(jsonobject.optString("account"));
                                    String id = jsonobjects.optString("id");
                                    Message message = new Message();
                                    message.what = SUCCESS;
                                    message.obj = "加载中...";
                                    handler.sendMessage(message);
                                    Intent intent = new Intent(music163.this, music163s.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("judge",1);
                                    bundle.putString("id",id);
                                    intent.putExtra("music",bundle);
                                    startActivity(intent);

                                }
                                else
                                {
                                    if(jsonobject.optString("code").equals("400"))
                                    {
                                        Message message = new Message();
                                        message.what = SUCCESS;
                                        message.obj = "账号不正确";
                                        handler.sendMessage(message);
                                    }else
                                    {
                                        Message message = new Message();
                                        message.what = SUCCESS;
                                        message.obj = jsonobject.optString("msg");
                                        handler.sendMessage(message);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } else {
                    showToast("网络错误！");
                }
            }
        });
    }

    private void query() { //查询数据库，获取的数据存在数组里面
        ImageView spfunction2_img = (ImageView) findViewById(R.id.spfunction2_img);
        String sql = "select * from myphoto";
        BmobQuery<myphoto> bmobQuery = new BmobQuery<>();
        bmobQuery.setSQL(sql);
        bmobQuery.doSQLQuery(new SQLQueryListener<myphoto>() {
            @Override
            public void done(BmobQueryResult<myphoto> bmobQueryResult, BmobException e) {
                if (e == null) {
                    List<myphoto> list = (List<myphoto>) bmobQueryResult.getResults();
                    Picasso.with(music163.this).load(list.get(5).getUrl()).into(spfunction2_img);
                } else {
                    Picasso.with(music163.this).load("https://tva1.sinaimg.cn/large/0072Vf1pgy1foxkjenkjaj31hc0u0dwt.jpg").into(spfunction2_img);
                }
            }
        });
    }

    public void spfunction2_back(View view)
    {
        Intent intent = new Intent(music163.this, functionactivity.class);
        startActivity(intent);
        finish();
    }

    private void textcolor() {
        // 作用是修改字体的颜色
        TextView spfunction2_text = (TextView) findViewById(R.id.spfunction2_text);
        textcolor1.setTextViewStyles(spfunction2_text);

    }

}