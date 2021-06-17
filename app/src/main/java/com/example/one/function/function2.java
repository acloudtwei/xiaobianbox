package com.example.one.function;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.one.R;
import com.example.one.activity.functionactivity;
import com.example.one.textcolor.textcolor1;
import com.githang.statusbar.StatusBarCompat;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class function2 extends AppCompatActivity {

    private static final int SUCCESS = 10010;
    private static final int ERROR = 0;
    private EditText express_id;
    private ImageView express_img;
    private Handler handler;
    private String[] express_direction;
    private String[] express_date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function2);
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.top_color),false);
        textcolor();
        get_express();
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                if(msg.what == SUCCESS)
                {
                    String responses = (String) msg.obj;
                    try {
                        JSONObject jsonobject = new JSONObject(responses);
                        JSONArray jsonarray = jsonobject.getJSONArray("data");
                        for(int i = 0;i<jsonarray.length();i++)
                        {
                            JSONObject new_jsonobject = jsonarray.getJSONObject(i);
                            express_date[i] = new_jsonobject.optString("date");
                            express_direction[i] = new_jsonobject.optString("direction");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return true;
            }
        });
        express_id = (EditText) findViewById(R.id.express_id);
        ListView mListView = (ListView) findViewById(R.id.lv); // 这个是找到我们的listview标签
        MyBaseAdapter mAdapter = new MyBaseAdapter(); // 这个是创建一个适配器的对象（这个适配器是我们继承父类的子类）
        mListView.setAdapter(mAdapter); // 这个是将我们的这个listview的适配器设置为我们创建的这个适配器
    }

//    public String[] fruit_names() //这个就是创建一个方法用来存储我们从Resources获取的字符串变量
//    {
//        String[] names={"据欸","asas","大大"};
//        return names;
//    }
//
//    public String[] fruit_name()
//    {
//        String[] name = {"1","2","3"};
//        return name;
//    }

    class MyBaseAdapter extends BaseAdapter { //getCount决定了listview一共有多少个item（数据项），而getView返回了每个item项所显示的view。
        // 重写BaseAdapter要重写里面四个基本的方法，其中这四个方法都是有相应的返回值的
        @Override
        public int getCount() { // 这个函数是获取总共的要显示的数据数
            return express_direction.length; }
        @Override
        public Object getItem(int position) // 获得相应数据集合中特定位置的数据项
        {
            return express_direction[position];
        }
        @Override
        public long getItemId(int position)
        {
            return position;
        } // 返回该数据项对应的id
        @Override
        public View getView(int position, View convertView, ViewGroup parnet) // 返回每个item所显示的数据(view)
        {
            View view = View.inflate(function2.this,R.layout.activity_main,null);
            // 创建一个view，通过inflate来找相应的显示规则xml布局，就是找到你在listview里面要显示的相应控件布局
            TextView mTextView = (TextView) view.findViewById(R.id.express_message); // 文本
            TextView yTextView = (TextView) view.findViewById(R.id.express_time);
            mTextView.setText(express_direction[position]); // 设置这个文本是我们对应的数据中某一个，通过position这个来获取
            yTextView.setText(express_date[position]);
            return view;
        }
    }

    private void get_express()
    {
        String url =  "https://xiaojieapi.com/api/v1/get/express?num="  + express_id.getText().toString().trim();
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
                Message message = new Message();
                message.what = SUCCESS;
                message.obj = response.body().string();
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
    }
}