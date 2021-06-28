package com.example.one.function;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.one.R;
import com.example.one.util.imagUtil;
import com.example.one.activity.functionactivity;
import com.example.one.textcolor.textcolor1;
import com.githang.statusbar.StatusBarCompat;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;

import android.graphics.Bitmap;

import android.widget.ImageView;
import android.widget.Toast;


public class function4 extends AppCompatActivity {

    private EditText f4_ones;
    private EditText f4_twos;
    private EditText f4_threes;
    private EditText f4_fours;
    private EditText f4_fives;
    private EditText f4_sixs;
    final String[] items = new String[]{"选项1", "选项2", "选项3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function4);
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.top_color),false);
        textcolor();
        initData();
        initButton1();


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


private void initButton1()
{
    Button f4_button1 = (Button) findViewById(R.id.f4_button1);
    f4_button1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new QMUIDialog.MenuDialogBuilder(function4.this)
                    .addItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(function4.this, "你选择了 " + items[which], Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    })
                    .show();
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