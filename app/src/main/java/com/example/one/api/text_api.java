package com.example.one.api;

import android.annotation.SuppressLint;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class text_api {

    public static void getTopText(TextView text)
    {
        String url = "https://xiaojieapi.com/api/v1/get/yiyan";
        OkHttpClient client = new OkHttpClient();
        Request  request = new Request.Builder().url(url).get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                text.setText("威威工具箱，一个beta版本工具箱~");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responses = response.body().string();
                try {
                    JSONObject jsonobject = new JSONObject(responses);
                    text.setText(jsonobject.optString("msg"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
