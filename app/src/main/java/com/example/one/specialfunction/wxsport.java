package com.example.one.specialfunction;

import androidx.annotation.NonNull;

import com.example.one.R;

import com.example.one.activity.BaseActivity;
import com.example.one.activity.functionactivity;

import com.example.one.sql.myphoto;
import com.example.one.textcolor.textcolor1;
import com.example.one.util.StringUtils;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.UpdateListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class wxsport extends BaseActivity {

    private static final int SUCCESS = 200;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxsport);
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

    private void query() { //查询数据库，获取的数据存在数组里面
        ImageView spfunction1_img = (ImageView) findViewById(R.id.spfunction1_img);
        String sql = "select * from myphoto";
        BmobQuery<myphoto> bmobQuery = new BmobQuery<>();
        bmobQuery.setSQL(sql);
        bmobQuery.doSQLQuery(new SQLQueryListener<myphoto>() {
            @Override
            public void done(BmobQueryResult<myphoto> bmobQueryResult, BmobException e) {
                if (e == null) {
                    List<myphoto> list = (List<myphoto>) bmobQueryResult.getResults();
                    Picasso.with(wxsport.this).load(list.get(4).getUrl()).into(spfunction1_img);
                } else {
                    Picasso.with(wxsport.this).load("https://tva1.sinaimg.cn/large/0072Vf1pgy1foxkjenkjaj31hc0u0dwt.jpg").into(spfunction1_img);
                }
            }
        });
    }

    public void spfunction1_submit(View view)
    {
        EditText spfunction1_usernames = (EditText) findViewById(R.id.spfunction1_usernames);
        EditText spfunction1_psws = (EditText) findViewById(R.id.spfunction1_psws);
        EditText spfunction1_steps = (EditText) findViewById(R.id.spfunction1_steps);
        String username = spfunction1_usernames.getText().toString().trim();
        String psw = spfunction1_psws.getText().toString().trim();
        String step = spfunction1_steps.getText().toString().trim();
        if (StringUtils.isEmpty(username)) {
            showToast("请输入手机号！");
            return;
        }
        if (StringUtils.isEmpty(psw)) {
            showToast("请输入密码！");
            return;
        }
        if (StringUtils.isEmpty(step)) {
            showToast("请输入修改步数！");
            return;
        }

        Message message = new Message();
        message.what = SUCCESS;
        message.obj = "正在进行刷步数中，请等待成功提示！";
        handler.sendMessage(message);

        String url = "https://api.itwei.top/get_mi.php?phone="+username
                +"&password="+psw
                +"&steps="+step;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = new Message();
                message.what = SUCCESS;
                message.obj = "修改步数失败！"+e.getMessage();
                handler.sendMessage(message);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject jsonobject = new JSONObject(response.body().string());
                    if(jsonobject.optString("success").equals("1"))
                    {
                        if(jsonobject.optString("code").equals("200"))
                        {
                            Message message = new Message();
                            message.what = SUCCESS;
                            message.obj = jsonobject.optString("data");
                            handler.sendMessage(message);
                        }else
                        {
                            Message message = new Message();
                            message.what = SUCCESS;
                            message.obj = jsonobject.optString("data");
                            handler.sendMessage(message);
                        }
                    }else
                    {
                        Message message = new Message();
                        message.what =SUCCESS;
                        message.obj = jsonobject.optString("data");
                        handler.sendMessage(message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void spfunction1_back(View view)
    {
        Intent intent = new Intent(wxsport.this, functionactivity.class);
        startActivity(intent);
        finish();
    }


    private void textcolor() {
        // 作用是修改字体的颜色
        TextView spfunction1_text = (TextView) findViewById(R.id.spfunction1_text);
        TextView spfunction1_username = (TextView) findViewById(R.id.spfunction1_username);
        TextView spfunction1_psw = (TextView) findViewById(R.id.spfunction1_psw);
        TextView spfunction1_step = (TextView) findViewById(R.id.spfunction1_step);
        textcolor1.setTextViewStyles(spfunction1_username);
        textcolor1.setTextViewStyles(spfunction1_text);
        textcolor1.setTextViewStyles(spfunction1_psw);
        textcolor1.setTextViewStyles(spfunction1_step);
    }

}