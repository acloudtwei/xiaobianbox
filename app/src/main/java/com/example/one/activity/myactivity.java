package com.example.one.activity;

import androidx.annotation.NonNull;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.one.MainActivity;
import com.example.one.My.PassWord;
import com.example.one.My.abouts;
import com.example.one.My.accounts;
import com.example.one.My.feedbacks;
import com.example.one.R;
import com.example.one.sql.User;
import com.example.one.sql.myphoto;
import com.example.one.sql.yiyan;
import com.example.one.textcolor.textcolor1;
import com.githang.statusbar.StatusBarCompat;
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
import cn.bmob.v3.listener.SQLQueryListener;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class myactivity extends BaseActivity {

    private static final int GET_IMG = 1001;
    private TextView top2;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myactivity);
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.top_color),false);
        top2 = (TextView) findViewById(R.id.layout_top2s);
        CircleImageView photoView = (CircleImageView) findViewById(R.id.head_photo);
        User user = BmobUser.getCurrentUser(User.class);
        Picasso.with(myactivity.this).load("https://q1.qlogo.cn/headimg_dl?dst_uin="+user.getQq()+"&spec=100").into(photoView);
        textcolor();
        query();
        getTopText();
        function1();
        function2();
        function3();
        function4();
        function5();

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

    private void query() { //查询数据库，获取的数据存在数组里面
        ImageView myactivity_img = (ImageView) findViewById(R.id.myactivity_img);
        String sql = "select * from myphoto";
        BmobQuery<myphoto> bmobQuery = new BmobQuery<>();
        bmobQuery.setSQL(sql);
        bmobQuery.doSQLQuery(new SQLQueryListener<myphoto>() {
            @Override
            public void done(BmobQueryResult<myphoto> bmobQueryResult, BmobException e) {
                if (e == null) {
                    List<myphoto> list = (List<myphoto>) bmobQueryResult.getResults();
                    Picasso.with(myactivity.this).load(list.get(0).getUrl()).into(myactivity_img);
                } else {
                    Picasso.with(myactivity.this).load("https://tva1.sinaimg.cn/large/0072Vf1pgy1foxkjenkjaj31hc0u0dwt.jpg").into(myactivity_img);
                }
            }
        });
    }


    public void onclick_functions(View view)
    {
        Intent intent = new Intent(myactivity.this,functionactivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    public void onclick_homes(View view)
    {
        Intent intent = new Intent(myactivity.this,homeactivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void function1()
    {
        LinearLayout accountconfig = (LinearLayout) findViewById(R.id.accountconfig);
        accountconfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(myactivity.this, accounts.class);
                startActivity(intent);
            }
        });
    }

    private void function2()
    {
        LinearLayout guanyu = (LinearLayout) findViewById(R.id.guanyu);
        guanyu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(myactivity.this, abouts.class);
                startActivity(intent);
            }
        });
    }

    private void function3()
    {
        LinearLayout fankui = (LinearLayout) findViewById(R.id.fankui);
        fankui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(myactivity.this, feedbacks.class);
                startActivity(intent);
            }
        });
    }

    private void function4()
    {
        LinearLayout quit = (LinearLayout) findViewById(R.id.quit);
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobUser.logOut();
                finish();
                Intent intent = new Intent(myactivity.this, MainActivity.class);
                startActivity(intent);
//                ActivityManager activityManager = (ActivityManager) getApplicationContext().getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
//                List<ActivityManager.AppTask> appTaskList = activityManager.getAppTasks();
//                for (ActivityManager.AppTask appTask : appTaskList) {
//                    appTask.finishAndRemoveTask();
//                }
            }
        });
    }

    private void function5()
    {
        LinearLayout psw = (LinearLayout) findViewById(R.id.psw);
        psw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(myactivity.this, PassWord.class);
                startActivity(intent);
            }
        });
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
                            top2.setText("威威工具箱，一个beta版本工具箱~");
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
                    top2.setText("威威工具箱，一个beta版本工具箱~");
                }
            }
        });
    }

    private void textcolor()
    {
        User user = BmobUser.getCurrentUser(User.class);

        // 作用是修改字体的颜色
        TextView home = (TextView) findViewById(R.id.home);
        TextView function = (TextView) findViewById(R.id.function);
        TextView my = (TextView) findViewById(R.id.my);
        TextView usernames = (TextView) findViewById(R.id.usernames);
        usernames.setText(user.getNickname());
        textcolor1.setTextViewStyles(home);
        textcolor1.setTextViewStyles(function);
        textcolor1.setTextViewStyles(my);
        textcolor1.setTextViewStyles(usernames);
    }
}