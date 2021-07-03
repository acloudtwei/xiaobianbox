package com.example.one.function;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.one.R;
import com.example.one.activity.function4_preview;
import com.example.one.sql.picture_api;
import com.example.one.util.StringUtils;
import com.example.one.util.imagUtil;
import com.example.one.activity.functionactivity;
import com.example.one.textcolor.textcolor1;
import com.githang.statusbar.StatusBarCompat;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.example.one.Request.*;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
import com.example.one.database;

public class function4 extends AppCompatActivity {

    private EditText f4_ones;
    private EditText f4_twos;
    private EditText f4_threes;
    private EditText f4_fours;
    private EditText f4_fives;
    private EditText f4_sixs;
    private  String api="ERROR";
    private  String type="imgurl";
    private final ArrayList<String> item = new ArrayList<>();
    private final ArrayList<String> apis = new ArrayList<>();
    private final ArrayList<String> types = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function4);
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.top_color),false);
        textcolor();
        query();
        initData();
        initButton1();
        initButton2();
        initButton3();

//        ImageView imageView = (ImageView) findViewById(R.id.imageView);
//        Bitmap bitmap = imagUtil.drawableToBitmap2(imageView.getBackground());
//        imageView.setOnLongClickListener(new View.OnLongClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.N)
//            @Override
//            public boolean onLongClick(View v) {
//                imagUtil.saveImageToGallery(function4.this,bitmap);
//                return true;
//            }
//        });

    }

private String geturl()
{
    return "https://tenapi.cn/poster/?qrcode="+
            f4_ones.getText().toString().trim()+"&title="
            +f4_twos.getText().toString().trim()+"&content="+
            f4_threes.getText().toString().trim()+
            "&site="+f4_fours.getText().toString().trim()+
            "&info="+f4_fives.getText().toString().trim()+"&author="
            +f4_sixs.getText().toString().trim()+"&pic=";
}

private void initButton1()
{
    Button f4_button1 = (Button) findViewById(R.id.f4_button1);
    f4_button1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String[] items = new String[item.size()];
            item.toArray(items);
            new QMUIDialog.MenuDialogBuilder(function4.this)
                    .addItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            api = apis.get(which);
                            type = types.get(which);
                            Toast.makeText(function4.this, "你选择了 " + items[which], Toast.LENGTH_SHORT).show();
                            f4_button1.setText(items[which]);
                            dialog.dismiss();
                        }
                    })
                    .show();
        }
    });
}

private void query() { //查询数据库，获取的数据存在数组里面
    String sql = "select * from picture_api";
    BmobQuery<picture_api> bmobQuery = new BmobQuery<>();
    bmobQuery.setSQL(sql);
    bmobQuery.doSQLQuery(new SQLQueryListener<picture_api>() {
        @Override
        public void done(BmobQueryResult<picture_api> bmobQueryResult, BmobException e) {
            if (e == null) {
              List<picture_api> list = (List<picture_api>) bmobQueryResult.getResults();
               for(int i = 0;i<list.size();i++)
              {
                    item.add(list.get(i).getOther());
                    apis.add(list.get(i).getApi());
                    types.add(list.get(i).getChoose());

              }
                } else {
                    Toast.makeText(function4.this,"网络错误",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

private void initButton2()
{
    Button button2 = (Button) findViewById(R.id.button2);
    button2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (StringUtils.isEmpty(f4_ones.getText().toString().trim())) {
                Toast.makeText(function4.this,"请输入二维码内容",Toast.LENGTH_SHORT).show();
                return;
            }
            if (StringUtils.isEmpty(f4_twos.getText().toString().trim())) {
                Toast.makeText(function4.this,"请输入标题",Toast.LENGTH_SHORT).show();
                return;
            }
            if (StringUtils.isEmpty(f4_threes.getText().toString().trim())) {
                Toast.makeText(function4.this,"请输入内容",Toast.LENGTH_SHORT).show();
                return;
            }
            if (StringUtils.isEmpty(f4_fours.getText().toString().trim())) {
                Toast.makeText(function4.this,"请输入网站标题",Toast.LENGTH_SHORT).show();
                return;
            }
            if (StringUtils.isEmpty(f4_fives.getText().toString().trim())) {
                Toast.makeText(function4.this,"请输入网站描述",Toast.LENGTH_SHORT).show();
                return;
            }
            if (StringUtils.isEmpty(f4_sixs.getText().toString().trim())) {
                Toast.makeText(function4.this,"请输入作者名称",Toast.LENGTH_SHORT).show();
                return;
            }
            if (api.equals("ERROR")) {
                Toast.makeText(function4.this,"请选择海报封面接口",Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(function4.this, function4_preview.class);
            intent.putExtra("url", geturl());
            intent.putExtra("api", api);
            intent.putExtra("type", type);
            startActivity(intent);
        }
    });
}

private void initButton3()
{
    Button button3 = (Button) findViewById(R.id.button3);
    button3.setOnClickListener(new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onClick(View v) {
            if(request1.img_bitmap() == null) {
                Toast.makeText(function4.this,"请先预览看看，再确定是否保存！",Toast.LENGTH_SHORT).show();
            }else{
                imagUtil.saveImageToGallery(function4.this, request1.img_bitmap());
            }
        }
    });
 }

private void initData()
{
    f4_ones = (EditText) findViewById(R.id.f4_ones);
    f4_twos = (EditText) findViewById(R.id.f4_twos);
    f4_threes = (EditText) findViewById(R.id.f4_threes);
    f4_fours = (EditText) findViewById(R.id.f4_fours);
    f4_fives = (EditText) findViewById(R.id.f4_fives);
    f4_sixs = (EditText) findViewById(R.id.f4_sixs);
}

    private void textcolor()
    {
        // 作用是修改字体的颜色
        TextView function1_text = (TextView) findViewById(R.id.function1_text);
        TextView f4_one = (TextView) findViewById(R.id.f4_one);
        TextView f4_two = (TextView) findViewById(R.id.f4_two);
        TextView f4_three = (TextView) findViewById(R.id.f4_three);
        TextView f4_four = (TextView) findViewById(R.id.f4_four);
        TextView f4_five = (TextView) findViewById(R.id.f4_five);
        TextView f4_six = (TextView) findViewById(R.id.f4_six);
        textcolor1.setTextViewStyles(function1_text);
        textcolor1.setTextViewStyles(f4_one);
        textcolor1.setTextViewStyles(f4_two);
        textcolor1.setTextViewStyles(f4_three);
        textcolor1.setTextViewStyles(f4_four);
        textcolor1.setTextViewStyles(f4_five);
        textcolor1.setTextViewStyles(f4_six);
    }

    public void main_function(View view)
    {
        Intent intent = new Intent(this, functionactivity.class);
        startActivity(intent);
    }
}