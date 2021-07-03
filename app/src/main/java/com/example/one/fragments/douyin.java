package com.example.one.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.example.one.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class douyin extends Fragment {
    private View view;
    protected List<String> douyin_titles = new ArrayList<>();
    protected List<String> douyin_hots = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_douyin,container,false);
        SharedPreferences sp = Objects.requireNonNull(getActivity()).getSharedPreferences("douyin", Activity.MODE_PRIVATE);
        String titles_listJson = sp.getString("douyin_json_title","");
        String hots_listJson = sp.getString("douyin_json_hot","");
        if(!titles_listJson.equals(""))
        {
            Gson gson = new Gson();
            douyin_titles = gson.fromJson(titles_listJson,new TypeToken<List<String>>(){}.getType());
        }
        if(!hots_listJson.equals(""))
        {
            Gson gson = new Gson();
            douyin_hots = gson.fromJson(hots_listJson,new TypeToken<List<String>>(){}.getType());
        }
        Log.d("apccc", douyin_titles.get(0));
        ListView mListView = (ListView) view.findViewById(R.id.douyin_listview); // 这个是找到我们的listview标签
        MyBaseAdapter mAdapter = new MyBaseAdapter(getActivity()); // 这个是创建一个适配器的对象（这个适配器是我们继承父类的子类）
        mListView.setAdapter(mAdapter); // 这个是将我们的这个listview的适配器设置为我们创建的这个适配器
        return view;
    }


   public class MyBaseAdapter extends BaseAdapter {

        private LayoutInflater layoutInflater;
        private Context context;


       public MyBaseAdapter(Context context) {
           this.context = context;
           this.layoutInflater = LayoutInflater.from(context);
       }

        public final class DouYin {
            public TextView douyin_number;
            public TextView douyin_title;
            public TextView douyin_hot;
        }

        @Override
        public int getCount() {
            return douyin_titles.size();
        }

        /**
         * 获得某一位置的数据
         */
        @Override
        public Object getItem(int position) {
            return douyin_titles.get(position);
        }

        /**
         * 获得唯一标识
         */
        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            DouYin douyins = null;
            if (convertView == null) {
                douyins = new DouYin();
                //获得组件，实例化组件
                convertView = layoutInflater.inflate(R.layout.douyin_item, null);
                douyins.douyin_number = (TextView) convertView.findViewById(R.id.douyin_number);
                douyins.douyin_title = (TextView) convertView.findViewById(R.id.douyin_title);
                douyins.douyin_hot = (TextView) convertView.findViewById(R.id.douyin_hot);
                convertView.setTag(douyins);
            } else {
                douyins = (DouYin) convertView.getTag();
            }
            //绑定数据
            douyins.douyin_number.setText(String.valueOf(position+1)+".");
            douyins.douyin_title.setText(douyin_titles.get(position));
            douyins.douyin_hot.setText(douyin_hots.get(position));
            return convertView;
        }

    }

}