package com.example.one.function;

import androidx.appcompat.app.AppCompatActivity;

import com.example.one.activity.function5_preview;
import com.example.one.beans.*;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dingmouren.colorpicker.ColorPickerDialog;
import com.dingmouren.colorpicker.OnColorPickerListener;
import com.example.one.R;
import com.example.one.activity.functionactivity;
import com.example.one.textcolor.textcolor1;

import com.githang.statusbar.StatusBarCompat;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import biz.borealis.numberpicker.NumberPicker;
import biz.borealis.numberpicker.OnValueChangeListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class function3 extends AppCompatActivity {

    private EditText weather_id;
    private TextView weather_city;
    private TextView weather_info;
    private TextView weather_aqi;
    private TextView weather_temperature;
    private TextView weather_direct;
    private TextView weather_humidity;
    private TextView weather_power;
    private LinearLayout weather_realtime;
    protected List<String> dates = new ArrayList<>();
    protected List<String> directs = new ArrayList<>();
    protected List<String> temperatures = new ArrayList<>();
    protected List<String> weathers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function3);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.top_color), false);
        init();
        textcolor();
    }


    private void init()
    {
        weather_id = (EditText) findViewById(R.id.weather_id);
        weather_city = (TextView) findViewById(R.id.weather_city);
        weather_info = (TextView) findViewById(R.id.weather_info);
        weather_aqi = (TextView) findViewById(R.id.weather_aqi);
        weather_temperature = (TextView) findViewById(R.id.weather_temperature);
        weather_direct = (TextView) findViewById(R.id.weather_direct);
        weather_humidity = (TextView) findViewById(R.id.weather_humidity);
        weather_power = (TextView) findViewById(R.id.weather_power);
        weather_realtime = (LinearLayout) findViewById(R.id.weather_realtime);
    }

    public void weather_onclik(View view)
    {
        String url = "https://apis.juhe.cn/simpleWeather/query?key=7a579ac2ff9b401f815fbe5fd543971c&city=" + weather_id.getText().toString().trim();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Looper.prepare();
                Toast.makeText(function3.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String responseString = response.body().string();
                runOnUiThread(new Runnable() {
                                  @Override
                                  public void run() {
                                      try {
                                          JSONObject jsonobject = new JSONObject(responseString);
                                          JSONObject jsonobject0 = new JSONObject(jsonobject.optString("result"));
                                          JSONObject jsonobject1 = new JSONObject(jsonobject0.optString("realtime"));
                                          weather_realtime.setVisibility(View.VISIBLE);
                                          if(!jsonobject.optString("result").equals("null"))
                                          {
                                              weather_city.setText(jsonobject0.optString("city"));
                                              weather_temperature.setText(jsonobject1.optString("temperature")+"℃");
                                              weather_humidity.setText(jsonobject1.optString("humidity"));
                                              weather_info.setText(jsonobject1.optString("info"));
                                              weather_direct.setText(jsonobject1.optString("direct"));
                                              weather_power.setText(jsonobject1.optString("power"));
                                              weather_aqi.setText(jsonobject1.optString("aqi"));

                                              JSONArray future_json = new JSONArray(jsonobject0.optString("future"));
                                              for (int i = 0; i < future_json.length(); i++) {
                                                  JSONObject jsonobjects = future_json.optJSONObject(i);
                                                  dates.add(jsonobjects.optString("date"));
                                                  temperatures.add(jsonobjects.optString("temperature"));
                                                  weathers.add(jsonobjects.optString("weather"));
                                                  directs.add(jsonobjects.optString("direct"));
                                                  ListView mListView = (ListView) findViewById(R.id.weather_futuretime); // 这个是找到我们的listview标签
                                                  function3.MyBaseAdapter mAdapter = new function3.MyBaseAdapter(); // 这个是创建一个适配器的对象（这个适配器是我们继承父类的子类）
                                                  mListView.setAdapter(mAdapter); // 这个是将我们的这个listview的适配器设置为我们创建的这个适配器
                                              }
                                          }
                                          else {
                                              Toast.makeText(function3.this,"输入的城市有误或者城市不存在！",Toast.LENGTH_SHORT).show();
                                          }
                                      } catch (JSONException e) {
                                          e.printStackTrace();
                                      }
                                  }}
                );}
        });
    }

    class MyBaseAdapter extends BaseAdapter { //getCount决定了listview一共有多少个item（数据项），而getView返回了每个item项所显示的view。
        // 重写BaseAdapter要重写里面四个基本的方法，其中这四个方法都是有相应的返回值的
        @Override
        public int getCount() { // 这个函数是获取总共的要显示的数据数
            return weathers.size(); }
        @Override
        public Object getItem(int position) // 获得相应数据集合中特定位置的数据项
        {
            return weathers.get(position);
        }
        @Override
        public long getItemId(int position)
        {
            return position;
        } // 返回该数据项对应的id
        @Override
        public View getView(int position, View convertView, ViewGroup parnet) // 返回每个item所显示的数据(view)
        {
            View view = View.inflate(function3.this,R.layout.function3_item,null);
            // 创建一个view，通过inflate来找相应的显示规则xml布局，就是找到你在listview里面要显示的相应控件布局
            TextView date = (TextView) view.findViewById(R.id.wdate);
            TextView direct = (TextView) view.findViewById(R.id.wdirect);
            TextView temperature = (TextView) view.findViewById(R.id.wtemperature);
            TextView weather = (TextView) view.findViewById(R.id.wweather);
            date.setText(dates.get(position));
            direct.setText(directs.get(position));
            temperature.setText(temperatures.get(position));
            weather.setText(weathers.get(position));
            date.setTextColor(Color.parseColor("#0c8599"));
            return view;
        }
    }

    private void textcolor()
    {
        // 作用是修改字体的颜色
        TextView function1_text = (TextView) findViewById(R.id.function1_text);
        TextView city_color = (TextView) findViewById(R.id.city_color);
        TextView w1 = (TextView) findViewById(R.id.w1);
        TextView w2 = (TextView) findViewById(R.id.w2);
        textcolor1.setTextViewStyles(function1_text);
        textcolor1.setTextViewStyles(city_color);
        textcolor1.setTextViewStyles(w1);
        textcolor1.setTextViewStyles(w2);
    }

    public void main_function(View view)
    {
        Intent intent = new Intent(this, functionactivity.class);
        startActivity(intent);
    }
}