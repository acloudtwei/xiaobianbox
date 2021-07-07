package com.example.one.function;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.one.R;
import com.example.one.activity.functionactivity;
import com.example.one.textcolor.textcolor1;
import com.githang.statusbar.StatusBarCompat;
import com.loopj.android.image.SmartImageView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class function2 extends AppCompatActivity {

    private TextView express_ids;
    private TextView express_company;
    private EditText express_id;
    private SmartImageView express_img;
    private LinearLayout express_main;
    protected List<String> express_direction = new ArrayList<>();
    protected List<String> express_date = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function2);
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.top_color),false);
        init();
        textcolor();

}

private void init()
{
    express_id = (EditText) findViewById(R.id.express_id);
    express_ids = (TextView) findViewById(R.id.express_ids);
    express_company = (TextView) findViewById(R.id.express_company);
    express_img = (SmartImageView) findViewById(R.id.express_img);
    express_main = (LinearLayout) findViewById(R.id.express_main);

}

public void onclicks(View view)
{
                String url = "https://xiaojieapi.com/api/v1/get/express?num=" + express_id.getText().toString().trim();
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(url).get().build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Toast.makeText(function2.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        final String responseString = response.body().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONObject jsonobject = new JSONObject(responseString);
                                    JSONArray jsonArray = jsonobject.optJSONArray("data");
                                    express_main.setVisibility(View.VISIBLE);
                                    if(jsonobject.optString("code").equals("200"))
                                    {
                                        express_img.setImageUrl(jsonobject.optString("logo"));
                                        express_company.setText(jsonobject.optString("name"));
                                        express_ids.setText(jsonobject.optString("num"));
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject jsonobjects = jsonArray.optJSONObject(i);
                                            express_date.add(jsonobjects.optString("date"));
                                            express_direction.add(jsonobjects.optString("direction"));
                                            ListView mListView = (ListView) findViewById(R.id.lv); // 这个是找到我们的listview标签
                                            MyBaseAdapter mAdapter = new MyBaseAdapter(); // 这个是创建一个适配器的对象（这个适配器是我们继承父类的子类）
                                            mListView.setAdapter(mAdapter); // 这个是将我们的这个listview的适配器设置为我们创建的这个适配器
                                        }
                                    }
                                    else {
                                        Toast.makeText(function2.this,"输入错误，请输入正确的快递单号！",Toast.LENGTH_SHORT).show();
                                    }

                                } catch (JSONException e) {
                                    Toast.makeText(function2.this,"接口异常，功能暂时无法使用，修复中....",Toast.LENGTH_SHORT).show();
                                }
                    }}
                        );}
                });
}

    class MyBaseAdapter extends BaseAdapter { //getCount决定了listview一共有多少个item（数据项），而getView返回了每个item项所显示的view。
        // 重写BaseAdapter要重写里面四个基本的方法，其中这四个方法都是有相应的返回值的
        @Override
        public int getCount() { // 这个函数是获取总共的要显示的数据数
            return express_direction.size(); }
        @Override
        public Object getItem(int position) // 获得相应数据集合中特定位置的数据项
        {
            return express_direction.get(position);
        }
        @Override
        public long getItemId(int position)
        {
            return position;
        } // 返回该数据项对应的id
        @Override
        public View getView(int position, View convertView, ViewGroup parnet) // 返回每个item所显示的数据(view)
        {
            View view = View.inflate(function2.this,R.layout.function2_item,null);
            // 创建一个view，通过inflate来找相应的显示规则xml布局，就是找到你在listview里面要显示的相应控件布局
            TextView mTextView = (TextView) view.findViewById(R.id.express_message); // 文本
            TextView yTextView = (TextView) view.findViewById(R.id.express_time);
            if(position == 0) {
                mTextView.setText(express_direction.get(position)); // 美化最后一个
                yTextView.setText(express_date.get(position));
                mTextView.setTextColor(Color.parseColor("#0c8599"));
                yTextView.setTextColor(Color.parseColor("#0c8599"));
            }
            else{
                mTextView.setText(express_direction.get(position)); // 设置这个文本是我们对应的数据中某一个，通过position这个来获取
                yTextView.setText(express_date.get(position));
            }
            return view;
        }
    }

    private void textcolor()
    {
        // 作用是修改字体的颜色
        TextView function1_text = (TextView) findViewById(R.id.function1_text);
        textcolor1.setTextViewStyles(function1_text);
    }

    public void main_function(View view)
    {
        Intent intent = new Intent(function2.this, functionactivity.class);
        startActivity(intent);
        finish();
    }
}