package com.example.one.specialfunction;

import androidx.annotation.NonNull;

import com.example.one.R;
import com.example.one.activity.BaseActivity;

import com.example.one.textcolor.textcolor1;
import com.githang.statusbar.StatusBarCompat;
import com.squareup.picasso.Picasso;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class music163s extends BaseActivity {

    private static final int SUCCESS = 200;
    private static final int ERROR = 100;
    private Handler handler;
    private Handler handlers;
    private Map<String,String> datas = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music163s);
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.top_color),false);
        CircleImageView music163s_photo = (CircleImageView) findViewById(R.id.music163s_photo);
        ImageView music163s_img = (ImageView) findViewById(R.id.music163s_img);
        TextView music163s_names = (TextView) findViewById(R.id.music163s_names);
        TextView music163s_nicknames = (TextView) findViewById(R.id.music163s_nicknames);
        TextView music163s_ranks = (TextView) findViewById(R.id.music163s_ranks);
        TextView music163s_songs = (TextView) findViewById(R.id.music163s_songs);
        textcolor();
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("music");
        if(bundle.getInt("judge") ==1 )
        {
            music163s_photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showToast("刷新中...");
                    query(bundle.getString("id"));
                    showToast("刷新成功");
                }
            });
            Log.d("cookie",bundle.getString("cookie"));
            query(bundle.getString("id"));
            music163s_sign(bundle.getString("cookie"));
            music163s_daka(bundle.getString("cookie"));
            handler = new Handler(new Handler.Callback() {
                @Override
                public boolean handleMessage(@NonNull Message msg) {
                    if(msg.what == SUCCESS)
                    {
                        JSONObject jsonobject = (JSONObject)msg.obj;
                        music163s_ranks.setText(jsonobject.optString("level"));
                        music163s_songs.setText(jsonobject.optString("listenSongs"));
                        try {
                            JSONObject jsonobjects = new JSONObject(jsonobject.optString("profile"));
                            music163s_names.setText(jsonobjects.optString("signature"));
                            music163s_nicknames.setText(jsonobjects.optString("nickname"));
                            Picasso.with(music163s.this).load(jsonobjects.optString("backgroundUrl")).into(music163s_img);
                            Picasso.with(music163s.this).load(jsonobjects.optString("avatarUrl")).into(music163s_photo);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    return true;
                }
            });

        }else {
            showToast("登录失败，你暂未登录，无法使用！");
        }

        handlers = new Handler(new Handler.Callback() {
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

    private void query(String id) { //查询数据库，获取的数据存在数组里面

        SharedPreferences sp = music163s.this.getSharedPreferences("api", music163s.MODE_PRIVATE);
        String url = sp.getString("api","")+"/?do=detail&uid="+id;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = new Message();
                message.what = ERROR;
                message.obj = "网络错误";
                handler.sendMessage(message);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject jsonobject = new JSONObject(response.body().string());
                    Message message = new Message();
                    message.what = SUCCESS;
                    message.obj = jsonobject;
                    handler.sendMessage(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void music163s_sign(String cookie)
    {
        Button music163s_sign = (Button) findViewById(R.id.music163s_sign);
        music163s_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = music163s.this.getSharedPreferences("api", music163s.MODE_PRIVATE);
                String url = sp.getString("api","")+"/?do=sign";
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().addHeader("cookie", cookie).url(url).build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Message message = new Message();
                        message.what = SUCCESS;
                        message.obj = "网络错误";
                        handlers.sendMessage(message);
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            JSONObject jsonobject = new JSONObject(response.body().string());
                            if(jsonobject.optString("code").equals("200"))
                            {
                                Message message = new Message();
                                message.what = SUCCESS;
                                message.obj = "签到成功";
                                handlers.sendMessage(message);
                            }
                            else if (jsonobject.optString("code").equals("-2"))
                            {
                                Message message = new Message();
                                message.what = SUCCESS;
                                message.obj = "重复签到！";
                                handlers.sendMessage(message);
                            }else
                            {
                                Message message = new Message();
                                message.what = SUCCESS;
                                message.obj = "签到失败！！";
                                handlers.sendMessage(message);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private void music163s_daka(String cookie)
    {
        Button music163s_daka = (Button) findViewById(R.id.music163s_daka);
        music163s_daka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("正在打卡，请等待...");
                SharedPreferences sp = music163s.this.getSharedPreferences("api", music163s.MODE_PRIVATE);
                String url = sp.getString("api","")+"/?do=daka";
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().addHeader("cookie", cookie).url(url).build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Message message = new Message();
                        message.what = SUCCESS;
                        message.obj = "部分网络错误，请点击头像查看是否成功打卡！";
                        handlers.sendMessage(message);
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            JSONObject jsonobject = new JSONObject(response.body().string());
                            if(jsonobject.optString("code").equals("200"))
                            {
                                Message message = new Message();
                                message.what = SUCCESS;
                                message.obj = "成功打卡300首";
                                handlers.sendMessage(message);
                            }else
       {
                        Message message = new Message();
                        message.what = SUCCESS;
                        message.obj = "打卡失败，未知错误！";
                        handlers.sendMessage(message);
       }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    public void music163s_back(View view)
    {
        Intent intent = new Intent(music163s.this, music163.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
//        finish();
    }

    private void textcolor() {
        // 作用是修改字体的颜色
        TextView music163s_text = (TextView) findViewById(R.id.music163s_text);
        TextView music163s_nickname = (TextView) findViewById(R.id.music163s_nickname);
        TextView music163s_name = (TextView) findViewById(R.id.music163s_name);
        TextView music163s_rank = (TextView) findViewById(R.id.music163s_rank);
        TextView music163s_song = (TextView) findViewById(R.id.music163s_song);
        textcolor1.setTextViewStyles(music163s_text);
        textcolor1.setTextViewStyles(music163s_nickname);
        textcolor1.setTextViewStyles(music163s_name);
        textcolor1.setTextViewStyles(music163s_rank);
        textcolor1.setTextViewStyles(music163s_song);

    }
}

