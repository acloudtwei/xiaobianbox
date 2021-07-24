package com.example.one.specialfunction;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.one.R;
import com.example.one.activity.functionactivity;
import com.example.one.function.function6;
import com.example.one.sql.myphoto;
import com.example.one.sql.spfunction;
import com.example.one.zhihu_webview;
import com.squareup.picasso.Picasso;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;

public class hz_web extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hz_web);
        view_webview();

    }

    private void view_webview(){

        Intent intent = getIntent();
        Bundle bundles = intent.getBundleExtra("hz");
        WebView webView = (WebView) findViewById(R.id.hzweb_webview);
        WebSettings webSettings = webView.getSettings();
        webView.loadUrl(bundles.getString("api"));
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
            public boolean shouldOverrideUrlLoading (WebView view, WebResourceRequest request) {
                String url =request.getUrl().toString();
                if(url.startsWith("http") || url.startsWith("https")){
                    //使用WebView加载显示url
                    view.loadUrl(url);
                }
                return true;
            }
        });

        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    //按返回键操作并且能回退网页
                    if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
                        //后退
                        webView.goBack();
                        return true;
                    }
                }
                return false;
            }
        });


        webView.setWebViewClient(new WebViewClient(){
            @Override

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                //设定加载开始的操作
                Toast.makeText(hz_web.this,"加载中...",Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void hzweb_back(View view)
    {
        Intent intent = new Intent(hz_web.this, functionactivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}