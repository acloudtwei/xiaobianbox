package com.example.one.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.RequiresApi;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class imagUtil {

//    public static final Bitmap drawableToBitmap2(Drawable drawable) {
//        BitmapDrawable bd = (BitmapDrawable) drawable;
//        return bd.getBitmap();
//    }
    @RequiresApi(api = Build.VERSION_CODES.N)

    public static void saveImageToGallery(Context context, Bitmap bmp) {

        // 首先保存图片 创建文件夹
        File appDir = new File(context.getDataDir() +  "/DCIM");
        // 这个是创建图片保存路径的文件夹，其中定义了File类对象，使用getDataDir获取的是当前包名下的文件路径
        if (!appDir.exists()) {
            appDir.mkdir();
            // 这个用来判断文件夹是否存在，如果文件夹不存在的话就可以直接创建一个名为 DCIM 的文件夹
        }

        //图片文件名称
        String fileName = "poster_"+System.currentTimeMillis() + ".jpg";
        // 这个字符串变量的作用主要是用来为照片进行命名，命名方式为acloudtwei+当前时间+图片格式，这个格式默认的png格式
        // 这个System.currentTimeMillis()作用是获取当前系统的时间，使用的是时间戳
        File file = new File(appDir, fileName); // 创建一个保存文件的对象
        try {
            FileOutputStream out = new FileOutputStream(file);  // 对当前的这个文件进行文件输出流，把文件保存起来
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
            // 这个是对bitmap图片进行压缩
            out.flush(); //清空缓冲区的数据流
            out.close(); //关闭输出流
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        String path = file.getAbsolutePath(); // 获取当前图片的绝对路径
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), path, fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // 最后通知图库更新（放着备用）
//        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//        Uri uri = Uri.fromFile(file);
//        intent.setData(uri);
//        context.sendBroadcast(intent);
        Toast.makeText(context,"图片已保存至"+appDir+"/"+fileName,Toast.LENGTH_SHORT).show();
    }
}

