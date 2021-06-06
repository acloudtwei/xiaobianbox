package com.example.one;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private TextView texts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        texts = (TextView) findViewById(R.id.text);
        getAsyn("https://restapi.amap.com/v3/weather/weatherInfo?key=07ce7c4969a77c908df5bc177601983d&city=河源");
        SmartImageView images = (SmartImageView) findViewById(R.id.abc);
        images.setImageUrl("https://tva2.sinaimg.cn/large/0072Vf1pgy1foxk744kw6j31hc0u0ni5.jpg");
    }
    public void getAsyn(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                texts.setText("Error");

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    //                        JSONObject jsonobject = new JSONObject(response.body().string());
//                        JSONArray jsonarray =jsonobject.getJSONArray("lives");
//                        JSONObject jsonobjects = jsonarray.getJSONObject(0);
//                        String count = jsonobjects.optString("weather");
                    javabean jsonobject = jsonparse.getBean(response.body().string());
                    javabean.Lives count = jsonobject.getLives().get(0);
                    Log.d("Tag",count.getCity());
                    //处理UI需要切换到UI线程处理

                }
            }
        });
    }
}