package com.example.one;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.loopj.android.image.SmartImageView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private TextView texts;
    private SmartImageView bg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, "83e23941491c7cce0cda92a4fdfe54c8");
        setContentView(R.layout.activity_main);
//        update();
          query();
//        person p2 = new person();
//        p2.setNum(1);
//        p2.setName("lucky");
//        p2.setAddress("北京海淀");
//        p2.save(new SaveListener<String>() {
//            @Override
//            public void done(String objectId, BmobException e) {
//                if(e==null){
//                    Toast.makeText(getApplication(),"添加数据成功，返回objectId为："+objectId,Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(getApplication(),"=失败："+e.getMessage(),Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        texts = (TextView) findViewById(R.id.text);
//        bg = (SmartImageView) findViewById(R.id.bgs);
//        getAsyn("https://restapi.amap.com/v3/weather/weatherInfo?key=07ce7c4969a77c908df5bc177601983d&city=河源");
//        SmartImageView images = (SmartImageView) findViewById(R.id.abc);
//        images.setImageUrl("https://tva2.sinaimg.cn/large/0072Vf1pgy1foxk744kw6j31hc0u0ni5.jpg");
//        getbg("https://api.dongmanxingkong.com/suijitupian/acg/1080p/index.php?return=json");
    }
    private void update() {
        person category = new person();
        category.setName("威威");
        category.update("1ca2e55f8e", new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Toast.makeText(getApplication(), "更新成功", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText( getApplication(),"更新失败", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void query() {
        String sql = "select * from person";
        BmobQuery<person> bmobQuery = new BmobQuery<>();
        bmobQuery.setSQL(sql);
        bmobQuery.doSQLQuery(new SQLQueryListener<person>() {
            @Override
            public void done(BmobQueryResult<person> bmobQueryResult, BmobException e) {
                if (e == null) {
                    List<person> list = (List<person>) bmobQueryResult.getResults();
                    Toast.makeText(getApplication(), "查询成功：" + list.get(0).getName(), Toast.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Toast.makeText(getApplication(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
//        bmobQuery.getObject("", new QueryListener<person>() {
//            @Override
//            public void done(person persons, BmobException e) {
//                if (e == null) {
//                    Toast.makeText(getApplication(), "查询成功：" + persons.getName(), Toast.LENGTH_LONG).show();
//                } else {
//                    Log.e("BMOB", e.toString());
//                    Toast.makeText(getApplication(), e.getMessage(), Toast.LENGTH_LONG).show();
//                }
//            }
//        });
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
                else
                {
                    texts.setText("请求失败");
                }
            }
        });
    }
    public void getbg(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).get().build();
        Call call = client.newCall(request);
        // 创建call对象，这个Call是用来执行request请求的，就是说我们创建了一个请求，但是执行使用的是call，有同步请求和异步请求
//        try {
//            Response response = call.execute();
//            try {
//                JSONObject jsonobject = new JSONObject(response.body().string());
//                bg.setImageUrl(jsonobject.optString("imgurl"));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    JSONObject jsonobject = null;
                    try {
                        jsonobject = new JSONObject(response.body().string());
                        Log.d("aa",jsonobject.optString("imgurl"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("异常", "请求失败，可能是服务器不存在");
            }
        });
    }
}