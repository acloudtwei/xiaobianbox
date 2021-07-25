package com.example.one.function;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.one.R;
import com.example.one.activity.functionactivity;
import com.example.one.textcolor.textcolor1;
import com.githang.statusbar.StatusBarCompat;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class function1 extends AppCompatActivity {

//    private EditText abc;
    private Handler handler;
    private static final int SUCCESS = 1;
    private static final int ERROR = 0;
    private ImageView testimg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function1);
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.top_color),false);
        testimg = (ImageView) findViewById(R.id.testimg);
        textcolor();
        getimg();
        Glide.with(this).load(R.mipmap.dingdings).into(testimg);
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                if(msg.what == SUCCESS)
                {
                    Bitmap bitmap = (Bitmap) msg.obj;
                    testimg.setImageBitmap(bitmap);
                }
                else{
                    Toast.makeText(function1.this,"网络出问题了",Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

//        abc = (EditText) findViewById(R.id.bbbb);
    }


    private void getimg()
    {
        String url = "https://xiaojieapi.com/api/v1/get/60s?name=%E5%B0%8F%E7%BC%96%E7%9B%92%E5%AD%90";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Message message = new Message();
                message.what = ERROR;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                InputStream inputStream = Objects.requireNonNull(response.body()).byteStream();//得到图片的流，直接通过流来获取图片而不是字节
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                Message message = new Message();
                message.what = SUCCESS;
                message.obj = bitmap;
                handler.sendMessage(message);
            }
        });

    }
    private void textcolor()
    {
        // 作用是修改字体的颜色
        TextView function1_text = (TextView) findViewById(R.id.function1_text);
        textcolor1.setTextViewStyles(function1_text);
    }

    public void main_function(View view)
    {
        Intent intent = new Intent(this, functionactivity.class);
        startActivity(intent);
        finish();
    }

//    public void onclick2(View view)
//    {
//        String url = "https://xiaojieapi.com/api/v1/get/express?num=" + abc.getText().toString().trim();
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder().url(url).get().build();
//        Call call = client.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(@NotNull Call call, @NotNull IOException e) {
//                Toast.makeText(function1.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//                final String responseString = response.body().string();
//                //在主线程中修改UI
//                try {
//                    JSONObject jsonobject = new JSONObject(responseString);
//                    JSONArray jsonArray = jsonobject.optJSONArray("data");
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject jsonobjects = jsonArray.optJSONObject(i);
//                        Log.d("date：",jsonobjects.optString("date"));
//                        Log.d("direction：",jsonobjects.optString("direction"));
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//    }

}