package com.example.one.function;

import androidx.appcompat.app.AppCompatActivity;

import com.example.one.beans.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dingmouren.colorpicker.ColorPickerDialog;
import com.dingmouren.colorpicker.OnColorPickerListener;
import com.example.one.R;
import com.example.one.activity.functionactivity;
import com.example.one.sql.background;
import com.example.one.sql.person;
import com.example.one.textcolor.textcolor1;

import com.githang.statusbar.StatusBarCompat;
import com.liji.imagezoom.util.ImageUrlType;
import com.liji.imagezoom.util.ImageZoom;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import biz.borealis.numberpicker.NumberPicker;
import biz.borealis.numberpicker.OnValueChangeListener;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class function3 extends AppCompatActivity {

    private EditText qcode_text;
    private TextView size2;
    private TextView margin2;
    private Button color_choose1;
    private Button color_choose2;
    private Button qcode_create;
    private NumberPicker num_choose1;
    private NumberPicker num_choose2;
    function3_bean function3_bean = new function3_bean();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function3);
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.top_color),false);
        init();
        textcolor();
        initData1();
        initData2();
        iniChoose1();
        iniChoose2();
        initData3();
//        NumberPicker numberPicker = (NumberPicker) findViewById(R.id.ppcv);
//        numberPicker.setOnValueChangeListener(new OnValueChangeListener() {
//            @Override
//            public void onValueChanged(int newValue) {
//                Toast.makeText(function3.this,"ok"+newValue,Toast.LENGTH_SHORT).show();
//            }
//        });


//取色器的监听器


//        ColorPickerDialogBuilder
//                .with(this)
//                .setTitle("Choose color")
//                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
//                .density(12)
//                .setOnColorSelectedListener(new OnColorSelectedListener() {
//                    @Override
//                    public void onColorSelected(int selectedColor) {
//                            Log.d("aaa","onColorSelected: 0x" + Integer.toHexString(selectedColor));
//                    }
//                })
//                .setPositiveButton("确认", new ColorPickerClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
//                        Log.d("color", ""+Integer.toHexString(selectedColor));
//                    }
//                })
//                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                    }
//                })
//                .build()
//                .show();


    }

    private void initData1()
    {
        color_choose1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean supportAlpha = true;//是否支持透明度
                /*
                 * 创建支持透明度的取色器
                 * @param context 宿主Activity
                 * @param defauleColor 默认的颜色
                 * @param isSupportAlpha 颜色是否支持透明度
                 * @param listener 取色器的监听器
                 */
                OnColorPickerListener mOnColorPickerListener = new OnColorPickerListener() {
                    @Override
                    public void onColorCancel(ColorPickerDialog dialog) {//取消选择的颜色

                    }
                    @Override
                    public void onColorChange(ColorPickerDialog dialog, int color) {//实时监听颜色变化

                    }
                    @Override
                    public void onColorConfirm(ColorPickerDialog dialog, int color) {//确定的颜色
                        function3_bean.setBgcolor("%23"+Integer.toHexString(color).substring(2));
                        color_choose1.setBackgroundColor(color);
                        color_choose1.setText("#"+Integer.toHexString(color).substring(2));
                        //Toast.makeText(function3.this,"#"+Integer.toHexString(color),Toast.LENGTH_SHORT).show();
                    }
                };
                ColorPickerDialog mColorPickerDialog = new ColorPickerDialog(
                        function3.this,
                        getResources().getColor(R.color.top_color),
                        supportAlpha,
                        mOnColorPickerListener
                ).show();
            }
        });
    }

    private void initData2()
    {
        color_choose2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean supportAlpha = true;//是否支持透明度
                /*
                 * 创建支持透明度的取色器
                 * @param context 宿主Activity
                 * @param defauleColor 默认的颜色
                 * @param isSupportAlpha 颜色是否支持透明度
                 * @param listener 取色器的监听器
                 */
                OnColorPickerListener mOnColorPickerListener = new OnColorPickerListener() {
                    @Override
                    public void onColorCancel(ColorPickerDialog dialog) {//取消选择的颜色

                    }
                    @Override
                    public void onColorChange(ColorPickerDialog dialog, int color) {//实时监听颜色变化

                    }
                    @Override
                    public void onColorConfirm(ColorPickerDialog dialog, int color) {//确定的颜色
                        color_choose2.setBackgroundColor(color);
                        color_choose2.setText("#"+Integer.toHexString(color).substring(2));
                        function3_bean.setFgcolor("%23"+Integer.toHexString(color).substring(2));
                        //Toast.makeText(function3.this,"#"+Integer.toHexString(color),Toast.LENGTH_SHORT).show();
                    }
                };
                ColorPickerDialog mColorPickerDialog = new ColorPickerDialog(
                        function3.this,
                        getResources().getColor(R.color.top_color),
                        supportAlpha,
                        mOnColorPickerListener
                ).show();
            }
        });
    }

    private void initData3()
    {
//        final String[] urls = new String[] {
//                "https://xiaojieapi.com/api/v3/get/picture?token=c1d5c801320489e792112c461819fe3b&suffix=jpg&type=jpg",
//                "https://xiaojieapi.com/api/v3/get/picture?token=b05a7512d42fc3484e4cf3cfaf1cd11b&suffix=jpg&type=jpg",
//        };
//
//        //添加list数据
//        final List<String> list = new ArrayList<>();
//        for (int i = 0; i < urls.length; i++) {
//            list.add(urls[i]);
//        }
        qcode_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = qcode_text.getText().toString().trim();
                String url = "https://api.itwei.top/get_qcode.php?text="+text+"&bgcolor="+function3_bean.getBgcolor() +
                        "&fgcolor="+function3_bean.getFgcolor()+"&w="+function3_bean.getW()+"&m="+function3_bean.getM();

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(url).get().build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        ImageZoom.show(function3.this,"https://tva4.sinaimg.cn//large//0072Vf1pgy1foxlncsm2pj31hc0u0arz.jpg");
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        final String responseString = response.body().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                        try {
                            JSONObject jsonobject = new JSONObject(responseString);
                            if(jsonobject.optString("code").equals("1"))
                            {
//                                function3_bean.setImgurl(jsonobject.optString("url"));
                                ImageZoom.show(function3.this,jsonobject.optString("url"));
//                                Toast.makeText(function3.this,jsonobject.optString("url"),Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                            }
                        });
                    }
                });
            }
        });
    }

    private void iniChoose1()
    {
        num_choose1.setOnValueChangeListener(new OnValueChangeListener() {
            @Override
            public void onValueChanged(int newValue) {
                size2.setText(String.valueOf(newValue));
                function3_bean.setW(String.valueOf(newValue));
                //Toast.makeText(function3.this,"ok"+newValue,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void iniChoose2()
    {
        num_choose2.setOnValueChangeListener(new OnValueChangeListener() {
            @Override
            public void onValueChanged(int newValue) {
                margin2.setText(String.valueOf(newValue));
                function3_bean.setM(String.valueOf(newValue));
                //Toast.makeText(function3.this,"ok"+newValue,Toast.LENGTH_SHORT).show();
            }
        });
    }













//    private void cpdd()
//    {
//        String sql = "select * from background";
//        BmobQuery<background> bmobQuery = new BmobQuery<>();
//        bmobQuery.setSQL(sql);
//        bmobQuery.doSQLQuery(new SQLQueryListener<background>() {
//            @Override
//            public void done(BmobQueryResult<background> bmobQueryResult, BmobException e) {
//                if (e == null) {
//                    List<background> list = (List<background>) bmobQueryResult.getResults();
//                    aa.setText(list.get(0).getUrl());
////                    Message message = new Message();
////                    message.what = SUCCESS;
////                    message.obj = list;
////                    handler.sendMessage(message);
//
//                } else {
//                    aa.setText("error");
////                    Message message = new Message();
////                    message.what = ERROR;
////                    handler.sendMessage(message);
//                }
//            }
//        });
//    }

    private void init()
    {
        qcode_text = (EditText) findViewById(R.id.qcode_text);
        size2 = (TextView) findViewById(R.id.size2);
        margin2 = (TextView) findViewById(R.id.margin2);
        color_choose1 = (Button) findViewById(R.id.color_choose1);
        color_choose2 = (Button) findViewById(R.id.color_choose2);
        qcode_create = (Button) findViewById(R.id.qcode_create);
        num_choose1 = (NumberPicker) findViewById(R.id.num_choose1);
        num_choose2 = (NumberPicker) findViewById(R.id.num_choose2);
    }
    private void textcolor()
    {
        // 作用是修改字体的颜色
        TextView function1_text = (TextView) findViewById(R.id.function1_text);
        TextView size1= (TextView) findViewById(R.id.size1);
        TextView margin1= (TextView) findViewById(R.id.margin1);
        textcolor1.setTextViewStyles(function1_text);
        textcolor1.setTextViewStyles(size1);
        textcolor1.setTextViewStyles(margin1);
    }

    public void main_function(View view)
    {
        Intent intent = new Intent(this, functionactivity.class);
        startActivity(intent);
    }
}