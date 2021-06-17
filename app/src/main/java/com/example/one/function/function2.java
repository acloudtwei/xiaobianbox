package com.example.one.function;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.one.R;
import com.example.one.activity.functionactivity;
import com.example.one.textcolor.textcolor1;
import com.githang.statusbar.StatusBarCompat;

public class function2 extends AppCompatActivity {

    private ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function2);
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.top_color),false);
        textcolor();
        mListView = (ListView) findViewById(R.id.lv); // 这个是找到我们的listview标签
        MyBaseAdapter mAdapter = new MyBaseAdapter(); // 这个是创建一个适配器的对象（这个适配器是我们继承父类的子类）
        mListView.setAdapter(mAdapter); // 这个是将我们的这个listview的适配器设置为我们创建的这个适配器
    }

    public String[] fruit_names() //这个就是创建一个方法用来存储我们从Resources获取的字符串变量
    {
        String[] names={"据欸","asas","大大"};
        return names;
    }

    class MyBaseAdapter extends BaseAdapter { //getCount决定了listview一共有多少个item（数据项），而getView返回了每个item项所显示的view。
        // 重写BaseAdapter要重写里面四个基本的方法，其中这四个方法都是有相应的返回值的
        @Override
        public int getCount() { // 这个函数是获取总共的要显示的数据数
            return fruit_names().length; }
        @Override
        public Object getItem(int position) // 获得相应数据集合中特定位置的数据项
        {
            return fruit_names()[position];
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
            TextView mTextView = (TextView) view.findViewById(R.id.item_tv); // 文本
            mTextView.setText(fruit_names()[position]); // 设置这个文本是我们对应的数据中某一个，通过position这个来获取
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
        Intent intent = new Intent(this, functionactivity.class);
        startActivity(intent);
    }
}