package com.example.one.My;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.one.R;
import com.example.one.activity.functionactivity;
import com.example.one.activity.myactivity;
import com.example.one.function.function4;
import com.example.one.sql.authorconfig;
import com.example.one.sql.notice;
import com.example.one.sql.picture_api;
import com.example.one.textcolor.textcolor1;
import com.githang.statusbar.StatusBarCompat;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;

public class abouts extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abouts);
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.top_color),false);
        textcolor();
        querys();
        query();
    }

    private void querys() { //查询数据库，获取的数据存在数组里面
        TextView about_me = (TextView) findViewById(R.id.about_me);
        String sql = "select * from notice";
        BmobQuery<notice> bmobQuery = new BmobQuery<>();
        bmobQuery.setSQL(sql);
        bmobQuery.doSQLQuery(new SQLQueryListener<notice>() {
            @Override
            public void done(BmobQueryResult<notice> bmobQueryResult, BmobException e) {
                if (e == null) {
                    List<notice> list = (List<notice>) bmobQueryResult.getResults();
                    about_me.setText(list.get(1).getNotice());
                } else {
                    about_me.setText(R.string.words);
                }
            }
        });
    }

    private void query() { //查询数据库，获取的数据存在数组里面
        TextView author = (TextView) findViewById(R.id.author);
        TextView chat = (TextView) findViewById(R.id.chat);
        TextView web = (TextView) findViewById(R.id.web);
        String sql = "select * from author";
        BmobQuery<authorconfig> bmobQuery = new BmobQuery<>();
        bmobQuery.setSQL(sql);
        bmobQuery.doSQLQuery(new SQLQueryListener<authorconfig>() {
            @Override
            public void done(BmobQueryResult<authorconfig> bmobQueryResult, BmobException e) {
                if (e == null) {
                    List<authorconfig> list = (List<authorconfig>) bmobQueryResult.getResults();
                    author.setText(list.get(0).getAuthor());
                    chat.setText(list.get(0).getChat());
                    web.setText(list.get(0).getWeb());
                } else {
                    author.setText(R.string.authors);
                    chat.setText(R.string.wxs);
                    web.setText(R.string.webs);
                }
            }
        });
    }

    public void magic(View view)
    {
        LinearLayout mymagic = (LinearLayout) findViewById(R.id.mymagic);
        mymagic.setVisibility(View.VISIBLE);
    }

    public void main_function(View view)
    {
        Intent intent = new Intent(abouts.this, myactivity.class);
        startActivity(intent);
        finish();
    }

    private void textcolor()
    {
        // 作用是修改字体的颜色
        TextView my_about = (TextView) findViewById(R.id.my_about);
        TextView author_color = (TextView) findViewById(R.id.author_color);
        TextView chat_color = (TextView) findViewById(R.id.chat_color);
        TextView web_color = (TextView) findViewById(R.id.web_color);
        TextView function1_text = (TextView) findViewById(R.id.function1_text);
        textcolor1.setTextViewStyles(function1_text);
        textcolor1.setTextViewStyles(my_about);
        textcolor1.setTextViewStyles(author_color);
        textcolor1.setTextViewStyles(chat_color);
        textcolor1.setTextViewStyles(web_color);
    }

}