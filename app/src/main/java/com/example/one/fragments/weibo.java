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
import com.example.one.weibo_webview;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class weibo extends Fragment {
    private View view;
    protected List<String> weibo_titles = new ArrayList<>();
    protected List<String> weibo_hots = new ArrayList<>();
    private List<String> weibo_urls = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_weibo,container,false);
        SharedPreferences sp = Objects.requireNonNull(getActivity()).getSharedPreferences("weibo", Activity.MODE_PRIVATE);
        String titles_listJson = sp.getString("weibo_json_title","");
        String hots_listJson = sp.getString("weibo_json_hot","");
        String urls_listJson = sp.getString("weibo_json_url","");
        if(!titles_listJson.equals(""))
        {
            Gson gson = new Gson();
            weibo_titles = gson.fromJson(titles_listJson,new TypeToken<List<String>>(){}.getType());
        }
        if(!hots_listJson.equals(""))
        {
            Gson gson = new Gson();
            weibo_hots = gson.fromJson(hots_listJson,new TypeToken<List<String>>(){}.getType());
        }
        if(!urls_listJson.equals(""))
        {
            Gson gson = new Gson();
            weibo_urls = gson.fromJson(urls_listJson,new TypeToken<List<String>>(){}.getType());
        }
        ListView mListView = (ListView) view.findViewById(R.id.weibo_listview); // ????????????????????????listview??????
        weibo.MyBaseAdapter mAdapter = new weibo.MyBaseAdapter(getActivity()); // ??????????????????????????????????????????????????????????????????????????????????????????
        mListView.setAdapter(mAdapter); // ???????????????????????????listview???????????????????????????????????????????????????
        return view;
    }

    public class MyBaseAdapter extends BaseAdapter {

        private LayoutInflater layoutInflater;
        private Context context;


        public MyBaseAdapter(Context context) {
            this.context = context;
            this.layoutInflater = LayoutInflater.from(context);
        }

        public final class WeiBo {
            public TextView weibo_number;
            public TextView weibo_title;
            public TextView weibo_hot;
            public LinearLayout weibo_button;
        }

        @Override
        public int getCount() {
            return weibo_titles.size();
        }

        /**
         * ???????????????????????????
         */
        @Override
        public Object getItem(int position) {
            return weibo_titles.get(position);
        }

        /**
         * ??????????????????
         */
        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            WeiBo weibos = null;
            if (convertView == null) {
                weibos = new WeiBo();
                //??????????????????????????????
                convertView = layoutInflater.inflate(R.layout.weibo_item, null);
                weibos.weibo_number = (TextView) convertView.findViewById(R.id.weibo_number);
                weibos.weibo_title = (TextView) convertView.findViewById(R.id.weibo_title);
                weibos.weibo_hot = (TextView) convertView.findViewById(R.id.weibo_hot);
                weibos.weibo_button = (LinearLayout) convertView.findViewById(R.id.weibo_button);
                convertView.setTag(weibos);
            } else {
                weibos = (WeiBo) convertView.getTag();
            }
            //????????????
            weibos.weibo_number.setText(String.valueOf(position+1)+".");
            weibos.weibo_title.setText(weibo_titles.get(position));
            weibos.weibo_hot.setText(weibo_hots.get(position));
            weibos.weibo_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), weibo_webview.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("position",position);
                    intent.putExtra("weibo",bundle);
                    startActivity(intent);
                }
            });
            return convertView;
        }
    }

}