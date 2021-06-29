package com.example.one.Request;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.one.R;
import com.example.one.function.function1;
import com.loopj.android.image.SmartImageView;

import org.jetbrains.annotations.NotNull;
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


public class request1 {

    private static final int SUCCESS = 1;
    private static final int ERROR = 0;
    private static Handler handler;
    private static Bitmap bitmap;
    private static String bg_url="https://tva1.sinaimg.cn/large/0072Vf1pgy1fodqncwm01j31hc10xb29.jpg"; //ERROR象征

    public static void getphoto(SmartImageView img,String url,String api)
    {
        String urls;
        if(api.equals("https://tenapi.cn/bing/"))
        {
            urls = url + api;
        }else
        {
            getbg(api);
            urls = url +bg_url;
        }
        getposter(urls);
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                if(msg.what == SUCCESS)
                {
                    bitmap = (Bitmap) msg.obj;
                    img.setImageBitmap(bitmap);
                }
                else{
                    img.setImageResource(R.mipmap.b1);
                }
                return true;
            }
        });
    }

    public static Bitmap img_bitmap()
    {
        return bitmap;
    }

    private static void getbg(String api)
    {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(api).get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                bg_url="https://tva1.sinaimg.cn/large/0072Vf1pgy1fodqncwm01j31hc10xb29.jpg";
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonobject = new JSONObject(response.body().string());
                        bg_url = jsonobject.optString("imgurl");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    private static void getposter(String url)
    {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Message message = new Message();
                message.what = ERROR;
                handler.sendMessage(message);
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    InputStream inputStream = Objects.requireNonNull(response.body()).byteStream();//得到图片的流，直接通过流来获取图片而不是字节
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    Message message = new Message();
                    message.what = SUCCESS;
                    message.obj = bitmap;
                    handler.sendMessage(message);
                }
            }
        });
    }
}
