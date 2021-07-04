package com.example.one.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.one.R;
import com.example.one.zhihu_webview;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class zhihu extends Fragment {
    private View view;
    protected List<String> zhihu_titles = new ArrayList<>();
    protected List<String> zhihu_querys = new ArrayList<>();
    private List<String> zhihu_urls = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_zhihu,container,false);
        SharedPreferences sp = Objects.requireNonNull(getActivity()).getSharedPreferences("zhihu", Activity.MODE_PRIVATE);
        String titles_listJson = sp.getString("zhihu_json_title","");
        String querys_listJson = sp.getString("zhihu_json_query","");
        String urls_listJson = sp.getString("zhihu_json_url","");
        if(!titles_listJson.equals(""))
        {
            Gson gson = new Gson();
            zhihu_titles = gson.fromJson(titles_listJson,new TypeToken<List<String>>(){}.getType());
        }
        if(!querys_listJson.equals(""))
        {
            Gson gson = new Gson();
            zhihu_querys = gson.fromJson(querys_listJson,new TypeToken<List<String>>(){}.getType());
        }
        if(!urls_listJson.equals(""))
        {
            Gson gson = new Gson();
            zhihu_urls = gson.fromJson(urls_listJson,new TypeToken<List<String>>(){}.getType());
        }
        Log.d("apccc", zhihu_urls.get(0));
        ListView mListView = (ListView) view.findViewById(R.id.zhihu_listview); // 这个是找到我们的listview标签
        zhihu.MyBaseAdapter mAdapter = new zhihu.MyBaseAdapter(getActivity()); // 这个是创建一个适配器的对象（这个适配器是我们继承父类的子类）
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

        public final class ZhiHu {
            public TextView zhihu_number;
            public TextView zhihu_title;
            public TextView zhihu_query;
            public LinearLayout zhihu_button;
        }

        @Override
        public int getCount() {
            return zhihu_titles.size();
        }

        /**
         * 获得某一位置的数据
         */
        @Override
        public Object getItem(int position) {
            return zhihu_titles.get(position);
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
            ZhiHu zhihus = null;
            if (convertView == null) {
                zhihus = new ZhiHu();
                //获得组件，实例化组件
                convertView = layoutInflater.inflate(R.layout.zhihu_item, null);
                zhihus.zhihu_number = (TextView) convertView.findViewById(R.id.zhihu_number);
                zhihus.zhihu_title = (TextView) convertView.findViewById(R.id.zhihu_title);
                zhihus.zhihu_query = (TextView) convertView.findViewById(R.id.zhihu_query);
                zhihus.zhihu_button = (LinearLayout) convertView.findViewById(R.id.zhihu_button);
                convertView.setTag(zhihus);
            } else {
                zhihus = (ZhiHu) convertView.getTag();
            }
            //绑定数据
            zhihus.zhihu_number.setText(String.valueOf(position+1)+".");
            zhihus.zhihu_title.setText(zhihu_titles.get(position));
            zhihus.zhihu_query.setText(zhihu_querys.get(position));
            zhihus.zhihu_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), zhihu_webview.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("position",position);
                    intent.putExtra("zhihu",bundle);
                    startActivity(intent);
                }
            });
            return convertView;
        }
    }
}