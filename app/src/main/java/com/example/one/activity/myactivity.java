package com.example.one.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.one.My.abouts;
import com.example.one.My.feedbacks;
import com.example.one.My.myconfigs;
import com.example.one.R;
import com.example.one.textcolor.textcolor1;
import com.githang.statusbar.StatusBarCompat;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class myactivity extends AppCompatActivity {

    private CircleImageView photoView;
    private static final int GET_IMG = 1001;
    private TextView top2;
    private Handler handler;
    private String urls = "https://tvax4.sinaimg.cn/large/0072Vf1pgy1fodqpadh6zj31kw14nnpe.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myactivity);
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.top_color),false);
        top2 = (TextView) findViewById(R.id.layout_top2s);
        photoView = (CircleImageView) findViewById(R.id.head_photo);
        Picasso.with(myactivity.this).load(urls).into(photoView);
        textcolor();
        getTopText();
        function1();
        function2();
        function3();

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

    public void onclick_functions(View view)
    {
        Intent intent = new Intent(myactivity.this,functionactivity.class);
        startActivity(intent);
    }

    public void onclick_homes(View view)
    {
        Intent intent = new Intent(myactivity.this,homeactivity.class);
        startActivity(intent);
    }

    private void function1()
    {
        LinearLayout accountconfig = (LinearLayout) findViewById(R.id.accountconfig);
        accountconfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(myactivity.this, myconfigs.class);
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


    private void getTopText()
    {
        String url = "https://xiaojieapi.com/api/v1/get/yiyan";
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
                        Message message= new Message();
                        message.what = GET_IMG;
                        message.obj = jsonobject.optString("msg");
                        handler.sendMessage(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void textcolor()
    {
        // 作用是修改字体的颜色
        TextView home = (TextView) findViewById(R.id.home);
        TextView function = (TextView) findViewById(R.id.function);
        TextView my = (TextView) findViewById(R.id.my);
        textcolor1.setTextViewStyles(home);
        textcolor1.setTextViewStyles(function);
        textcolor1.setTextViewStyles(my);
    }
}