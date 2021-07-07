package com.example.one.function;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.TextView;
import com.example.one.R;
import com.example.one.activity.functionactivity;
import com.example.one.fragments.douyin;
import com.example.one.fragments.weibo;
import com.example.one.fragments.zhihu;
import com.example.one.textcolor.textcolor1;
import com.githang.statusbar.StatusBarCompat;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import java.util.ArrayList;
import java.util.List;

// 这里一定要继承 extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener
public class function6 extends AppCompatActivity implements  ViewPager.OnPageChangeListener {

    //4个fragment
    private douyin douyintFragment;
    private weibo weiboFragment;
    private zhihu zhihuFragment;

    // 中间内容区域
    private ViewPager viewPager; // 这个viewpage就是用来监听滑动的

    private List<Fragment> fragments; // 定义一个viewpage的碎片

    private String[] tabs = {"抖音", "微博", "知乎"};

    private int[] images = new int[]{
            R.mipmap.douyin_1,
            R.mipmap.weibo_2,
            R.mipmap.touxiang_zhihu};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function6);
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.top_color),false);
        textcolor();
        initView(); //控件初始
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager,true);

//        int id = getIntent().getIntExtra("id", 0);
//        if (id == 1) {
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.zhihu_container,new zhihu())
//                    .addToBackStack(null)
//                    .commit();
//        }

//        if (id == 1) {
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.weibo_container,new weibo())
//                    .addToBackStack(null)
//                    .commit();
//        }else{
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.zhihu_container,new zhihu())
//                .addToBackStack(null)
//                .commit();
//        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // 要重写的方法
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void initView() {  // 对我们的UI界面进行初始化

        this.viewPager = findViewById(R.id.vp_content); // 重点

        //初始化4个fragment
        douyintFragment = new douyin(); // 这些碎片就是界面来的
        weiboFragment = new weibo();
        zhihuFragment = new zhihu();

        fragments = new ArrayList<>(); // 将我们定义的fragmaent对象存在list列表里面
        fragments.add(douyintFragment);
        fragments.add(weiboFragment);
        fragments.add(zhihuFragment);

        FragmentManager fragmentManager = getSupportFragmentManager(); // 配置适配器
        viewPager.setAdapter(new PageAdapters(fragmentManager,0,fragments));
        viewPager.addOnPageChangeListener(this);
        viewPager.setCurrentItem(0);
    }

    // 这个是viewpage来适配我们每一个fragment
    public class PageAdapters extends FragmentPagerAdapter {
        private List<Fragment> fragmentList;

        public PageAdapters(FragmentManager fm, int behavior,List<Fragment> fragmentList) {
            super(fm, behavior);
            this.fragmentList = fragmentList;
        }
        @Override
        public Fragment getItem(int position) {

            return fragmentList.get(position);
        }
        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Drawable image = function6.this.getResources().getDrawable(images[position]);
            image.setBounds(0, 0, image.getIntrinsicWidth()/2, image.getIntrinsicHeight()/2);
            ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
            SpannableString ss = new SpannableString(" "+tabs[position]);
            ss.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return ss;
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
        finish();
    }
}