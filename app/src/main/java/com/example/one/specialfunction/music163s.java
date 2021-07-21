package com.example.one.specialfunction;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.one.R;
import com.example.one.activity.BaseActivity;
import com.example.one.activity.functionactivity;
import com.example.one.activity.myactivity;
import com.example.one.sql.myphoto;
import com.example.one.sql.spfunction;
import com.example.one.textcolor.textcolor1;
import com.githang.statusbar.StatusBarCompat;
import com.squareup.picasso.Picasso;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class music163s extends BaseActivity {

    private static final int SUCCESS = 200;
    private final Map<String,String> datas = new HashMap<>();
    private Handler handler;
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
            String sql = "select * from spfunction";
            BmobQuery<spfunction> bmobQuery = new BmobQuery<>();
            bmobQuery.setSQL(sql);
            bmobQuery.doSQLQuery(new SQLQueryListener<spfunction>() {
                @Override
                public void done(BmobQueryResult<spfunction> bmobQueryResult, BmobException e) {
                    if (e == null) {
                        List<spfunction> list = (List<spfunction>) bmobQueryResult.getResults();
                        datas.put("api",list.get(1).getUsing_api());
                    } else {
                        showToast("网络错误！");
                    }
                }
            });
            String url = "https://music.blibli.tk/?do=detail&uid="+bundle.getString("id");
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    showToast("网络错误！");
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        JSONObject jsonobject = new JSONObject(response.body().string());
//                        datas.put("level",jsonobject.optString("level"));
//                        datas.put("listenSongs",jsonobject.optString("listenSongs"));
                        JSONObject jsonobjects = new JSONObject(jsonobject.optString("profile"));
//                        datas.put("backgroundUrl",jsonobjects.optString("backgroundUrl"));
//                        datas.put("nickname",jsonobjects.optString("nickname"));
//                        datas.put("avatarUrl",jsonobjects.optString("avatarUrl"));
//                        datas.put("signature",jsonobjects.optString("signature"));
                        Picasso.with(music163s.this).load(jsonobjects.optString("backgroundUrl")).into(music163s_img);
                        Picasso.with(music163s.this).load(jsonobjects.optString("avatarUrl"));
                        music163s_names.setText(jsonobjects.optString("signature"));
                        music163s_nicknames.setText(jsonobjects.optString("nickname"));
                        music163s_ranks.setText(jsonobject.optString("level"));
                        music163s_songs.setText(jsonobject.optString("listenSongs"));

//                                else
//                                {
//                                    Intent intent = new Intent(music163.this, music163s.class);
//                                    Bundle bundle = new Bundle();
//                                    bundle.putInt("judge",0);
//                                    intent.putExtra("music",bundle);
//                                    startActivity(intent);
//                                }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }else {
            showToast("登录失败，你暂未登录，无法使用！");
        }

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

//    private void query(String id) { //查询数据库，获取的数据存在数组里面
//
//
//    }


    public void music163s_sign(View view)
    {
        String url = datas.get("api")+"/?do=sign";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = new Message();
                message.what = SUCCESS;
                message.obj = "签到失败，网络错误！";
                handler.sendMessage(message);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject jsonobject = new JSONObject(response.body().string());
                    if(jsonobject.optString("code").equals("301"))
                    {
                        Message message = new Message();
                        message.what = SUCCESS;
                        message.obj = "签到成功";
                        handler.sendMessage(message);
                    }
//                                else
//                                {
//                    Message message = new Message();
//                    message.what = SUCCESS;
//                    message.obj = "签到成功";
//                    handler.sendMessage(message);
//                                }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void music163s_daka(View view)
    {
        String url = datas.get("api")+"/?do=daka";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = new Message();
                message.what = SUCCESS;
                message.obj = "打卡300首失败，网络错误！";
                handler.sendMessage(message);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject jsonobject = new JSONObject(response.body().string());
                    if(jsonobject.optString("code").equals("301"))
                    {
                        Message message = new Message();
                        message.what = SUCCESS;
                        message.obj = "成功打卡300首";
                        handler.sendMessage(message);
                    }
//                                else
//                                {
//                    Message message = new Message();
//                    message.what = SUCCESS;
//                    message.obj = "签到成功";
//                    handler.sendMessage(message);
//                                }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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