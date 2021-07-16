package com.example.one;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.one.function.function6;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class weibo_webview extends AppCompatActivity {

    private List<String> weibo_urls = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weibo_webview);
        SharedPreferences sp = Objects.requireNonNull(weibo_webview.this).getSharedPreferences("weibo", Activity.MODE_PRIVATE);
        String urls_listJson = sp.getString("weibo_json_url","");
        if(!urls_listJson.equals(""))
        {
            Gson gson = new Gson();
            weibo_urls = gson.fromJson(urls_listJson,new TypeToken<List<String>>(){}.getType());
        }
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("weibo");
        int position =  bundle.getInt("position");
        view_webview(position);

    }

    private void view_webview(int position){
        WebView webView = (WebView) findViewById(R.id.weibo_webview);
        WebSettings webSettings = webView.getSettings();
        webView.loadUrl(weibo_urls.get(position));
        //清除网页访问留下的缓存 ,由于内核缓存是全局的因此这个方法不仅仅针对webview而是针对整个应用程序.
        webView.clearCache(true);
        //进行配置-利用WebSettings子类
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true);//将图片调整到适合webview的大小
        //webSettings.setLoadWithOverviewMode(true);// 缩放至屏幕的大小
        webSettings.setSupportZoom(true);//支持缩放，默认为true。是下面那个的前提。
        webSettings.setLoadsImagesAutomatically(true);//支持自动加载图片
        //webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        //系统默认会通过手机浏览器打开网页，为了能够直接通过WebView显示网页，则必须设置
        webSettings.setJavaScriptEnabled(true);//启用js
        webSettings.setBlockNetworkImage(false);//解决图片不显示
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //使用WebView加载显示url
                view.loadUrl(url);
                //返回true
                return true;
            }
        });

        webView.setWebViewClient(new WebViewClient(){
            @Override

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                //设定加载开始的操作
                Toast.makeText(weibo_webview.this,"加载中...",Toast.LENGTH_SHORT).show();
            }

        });
    }

    public void weibo_listview(View view)
    {
        Intent intent = new Intent(this, function6.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
//        Intent intent = new Intent(this, function6.class);
//        intent.putExtra("id",1);
//        startActivity(intent);
//        overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
    }
}