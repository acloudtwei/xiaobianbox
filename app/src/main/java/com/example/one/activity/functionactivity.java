package com.example.one.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.one.R;
import com.example.one.function.*;
import com.example.one.function.function6;
import com.example.one.specialfunction.music163;
import com.example.one.specialfunction.wxsport;
import com.example.one.sql.User;
import com.example.one.sql.myphoto;
import com.example.one.sql.spfunction;
import com.example.one.sql.yiyan;
import com.example.one.textcolor.textcolor1;
import com.githang.statusbar.StatusBarCompat;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
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
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class functionactivity extends BaseActivity {

    private static final int GET_IMG = 1001;
    private TextView top2;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_functionactivity);
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.top_color),false);
        top2 = (TextView) findViewById(R.id.layout_top2);
        textcolor();
        getTopText();
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
    }

    private void textcolor()
    {
        // 作用是修改字体的颜色
        TextView home = (TextView) findViewById(R.id.home);
        TextView function = (TextView) findViewById(R.id.function);
        TextView my = (TextView) findViewById(R.id.my);
        TextView f1 = (TextView) findViewById(R.id.f1);
        TextView f2 = (TextView) findViewById(R.id.f2);
        TextView sp = (TextView) findViewById(R.id.sp);
        textcolor1.setTextViewStyles(home);
        textcolor1.setTextViewStyles(function);
        textcolor1.setTextViewStyles(my);
        textcolor1.setTextViewStyles(f1);
        textcolor1.setTextViewStyles(f2);
        textcolor1.setTextViewStyles(sp);
    }

    public void onclick_home(View view)
    {
        Intent intent = new Intent(functionactivity.this,homeactivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    public void onclick_my(View view)
    {
        Intent intent = new Intent(functionactivity.this,myactivity.class);
       // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    public void function1(View view)
    {
        Intent intent = new Intent(functionactivity.this, function1.class);
        startActivity(intent);
    }

    public void function2(View view)
    {
        Intent intent = new Intent(functionactivity.this, function2.class);
        startActivity(intent);
    }

    public void function3(View view)
    {
        Intent intent = new Intent(functionactivity.this, function3.class);
        startActivity(intent);
    }

    public void function4(View view)
    {
        Intent intent = new Intent(functionactivity.this, function4.class);
        startActivity(intent);
    }

    public void function5(View view)
    {
        Intent intent = new Intent(functionactivity.this, function5.class);
        startActivity(intent);
    }

    public void function6(View view)
    {
        Intent intent = new Intent(functionactivity.this, function6.class);
        startActivity(intent);
    }

    public void spfunction1(View view)
    {
        new QMUIDialog.MessageDialogBuilder(functionactivity.this)
                .setTitle("刷步数使用方法")
                .setMessage("1.下载小米运动APP。\n" +
                        "2.从应用商店下载小米运动App，打开软件并选择手机号登录\n" +
                        "3.回到App首页，点击我的->第三方接入，绑定你想同步数据的项目注：同步微信运动请按照要求关注【华米科技】公众号。\n" +
                        "4.登陆，提交步数即可同步至你绑定的所有平台\n" +
                        "5.如果不会使用那就关注公众号加小编，小编教你使用呢~\n" +
                        "6.关注微信公众号：软件分享课堂，获取最新黑科技软件及资源！")
                .addAction("不关注了", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        showToast("真的不关注公众号嘛？关注一下嘛，给小编一点点支持啦！");
                    }
                })
                .addAction("已经关注啦", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        String sql = "select * from spfunction";
                        BmobQuery<spfunction> bmobQuery = new BmobQuery<>();
                        bmobQuery.setSQL(sql);
                        bmobQuery.doSQLQuery(new SQLQueryListener<spfunction>() {
                            @Override
                            public void done(BmobQueryResult<spfunction> bmobQueryResult, BmobException e) {
                                if (e == null) {
                                    List<spfunction> list = (List<spfunction>) bmobQueryResult.getResults();
                                    if(list.get(0).isJudge())
                                    {
                                        BmobUser.fetchUserJsonInfo(new FetchUserInfoListener<String>() {
                                            @Override
                                            public void done(String json, BmobException e) {
                                                if (e == null) {
                                                    try {
                                                        JSONObject jsonobject = new JSONObject(json);
                                                        if(jsonobject.optString("wx_sport").equals("true")) {
                                                            Intent intent = new Intent(functionactivity.this, wxsport.class);
                                                            startActivity(intent);
                                                            finish();
                                                        }else {
                                                            showToast("你无权使用此功能，请联系作者!");
                                                        }
                                                    } catch (JSONException jsonException) {
                                                        jsonException.printStackTrace();
                                                    }
                                                } else {
                                                    showToast("获取用户最新数据失败：" + e.getMessage());
                                                }
                                            }
                                        });
                                    }else{
                                        showToast(list.get(0).getMessage());
                                    }
                                } else {
                                    showToast("网络错误！");
                                }
                            }
                        });
                        dialog.dismiss();
                    }
                })
                .show();
    }

    public void spfunction2(View view)
    {
        String sql = "select * from spfunction";
        BmobQuery<spfunction> bmobQuery = new BmobQuery<>();
        bmobQuery.setSQL(sql);
        bmobQuery.doSQLQuery(new SQLQueryListener<spfunction>() {
            @Override
            public void done(BmobQueryResult<spfunction> bmobQueryResult, BmobException e) {
                if (e == null) {
                    List<spfunction> list = (List<spfunction>) bmobQueryResult.getResults();
                    if(list.get(1).isJudge())
                    {
                        BmobUser.fetchUserJsonInfo(new FetchUserInfoListener<String>() {
                            @Override
                            public void done(String json, BmobException e) {
                                if (e == null) {
                                    try {
                                        JSONObject jsonobject = new JSONObject(json);
                                        if(jsonobject.optString("music_163").equals("true")) {
                                            Intent intent = new Intent(functionactivity.this, music163.class);
                                            startActivity(intent);
                                            finish();
                                        }else {
                                            showToast("你无权使用此功能，请联系作者!");
                                        }
                                    } catch (JSONException jsonException) {
                                        jsonException.printStackTrace();
                                    }
                                } else {
                                    showToast("获取用户最新数据失败：" + e.getMessage());
                                }
                            }
                        });
                    }else{
                        showToast(list.get(0).getMessage());
                    }
                } else {
                    showToast("网络错误！");
                }
            }
        });
    }

    public void spfunction3(View view)
    {
        Intent intent = new Intent(functionactivity.this, function6.class);
        startActivity(intent);
    }

    private void getTopText()
    {
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
                    top2.setText("小编盒子，一个beta版本神奇盒子~");
                }
            }
        });
    }
}