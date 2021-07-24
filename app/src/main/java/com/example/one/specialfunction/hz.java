package com.example.one.specialfunction;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.one.R;
import com.example.one.activity.BaseActivity;
import com.example.one.activity.functionactivity;
import com.example.one.sql.myphoto;
import com.example.one.sql.spfunction;
import com.example.one.textcolor.textcolor1;
import com.example.one.util.StringUtils;
import com.githang.statusbar.StatusBarCompat;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class hz extends BaseActivity {

    private static final int SUCCESS = 200;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hz);
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.top_color),false);
        TextView hz_psws = (TextView)findViewById(R.id.hz_psws);
        Intent intent = getIntent();
        Bundle bundles = intent.getBundleExtra("hz");
        hz_psws.setText(bundles.getString("password"));
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
        ImageView hz_img = (ImageView) findViewById(R.id.hz_img);
        String sql = "select * from myphoto";
        BmobQuery<myphoto> bmobQuery = new BmobQuery<>();
        bmobQuery.setSQL(sql);
        bmobQuery.doSQLQuery(new SQLQueryListener<myphoto>() {
            @Override
            public void done(BmobQueryResult<myphoto> bmobQueryResult, BmobException e) {
                if (e == null) {
                    List<myphoto> list = (List<myphoto>) bmobQueryResult.getResults();
                    Picasso.with(hz.this).load(list.get(4).getUrl()).into(hz_img);
                } else {
                    Picasso.with(hz.this).load("https://tva1.sinaimg.cn/large/0072Vf1pgy1foxkjenkjaj31hc0u0dwt.jpg").into(hz_img);
                }
            }
        });
    }

    public void hz_submit(View view)
    {
        EditText hz_phones = (EditText) findViewById(R.id.hz_phones);
        String username = hz_phones.getText().toString().trim();
        if (StringUtils.isEmpty(username)) {
            showToast("请输入手机号！");
            return;
        }
        Message message = new Message();
        message.what = SUCCESS;
        message.obj = "轰炸时间有点长，已开始轰炸目标手机！";
        handler.sendMessage(message);

        String url = "https://api.itwei.top/get_sms.php?phone="+username;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = new Message();
                message.what = SUCCESS;
                message.obj = "轰炸失败："+e.getMessage();
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
                            message.obj = "未知错误！";
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

    public void hz_submits(View view)
    {
        String sql = "select * from spfunction";
            BmobQuery<spfunction> bmobQuery = new BmobQuery<>();
            bmobQuery.setSQL(sql);
            bmobQuery.doSQLQuery(new SQLQueryListener<spfunction>() {
                @Override
                public void done(BmobQueryResult<spfunction> bmobQueryResult, BmobException e) {
                    if (e == null) {
                        List<spfunction> list = (List<spfunction>) bmobQueryResult.getResults();
                        Intent intent = new Intent(hz.this, hz_web.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("api",list.get(2).getUsing_api());
                        intent.putExtra("hz",bundle);
                        startActivity(intent);
                        finish();
                    }
                }
            });

    }

    public void hz_back(View view)
    {
        Intent intent = new Intent(hz.this, functionactivity.class);
        startActivity(intent);
        finish();
    }


    private void textcolor() {
        // 作用是修改字体的颜色
        TextView hz_phone = (TextView) findViewById(R.id.hz_phone);
        TextView hz_psw = (TextView) findViewById(R.id.hz_psw);
        TextView hz_text = (TextView) findViewById(R.id.hz_text);
        textcolor1.setTextViewStyles(hz_phone);
        textcolor1.setTextViewStyles(hz_psw);
        textcolor1.setTextViewStyles(hz_text);
    }

}