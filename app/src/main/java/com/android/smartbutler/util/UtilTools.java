package com.android.smartbutler.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * 项目名：SmartButler
 * 包名：  com.adnroid.smartbutler.util
 * 文件名：UtilTools
 * 创建者：HY
 * 创建时间：2018/9/9 9:56
 * 描述：  工具统一类
 */


@SuppressWarnings("unused")
public class UtilTools {

    //设置字体
    public static void setFont(Context context, TextView textView) {
        Typeface fontType = Typeface.createFromAsset(context.getAssets(), "fonts/FONT.TTF");
        textView.setTypeface(fontType);
    }

    //Toast
    public static void toast(Context context, String text) {
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
    }

    //保存图片到SharedPreferences
    public static void putImageToShare(Context context,ImageView profile_image) {
        //保存头像
        BitmapDrawable drawable = (BitmapDrawable) profile_image.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        //第一步：将Bitmap压缩成字节数组输出流
        ByteArrayOutputStream byStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byStream);
        //第二步：利用Base64将我们的字节数组输出流转换成String
        byte[] byteArray = byStream.toByteArray();
        String imgString = Base64.encodeToString(byteArray, Base64.DEFAULT);
        //第三步：将String保存成shareUtils
        SharedPreferencesUtil.putString(context, "image_title", imgString);
    }

    //从SharedPreferences获取图片
    public static void getImageFromShare(Context context,ImageView profile_image) {
        //拿到String
        String imgString = SharedPreferencesUtil.getString(context, "image_title", "");
        if (imgString != null) {
            //利用Base64将String转换
            byte[] byteArray = Base64.decode(imgString, Base64.DEFAULT);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);
            //生成Bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            profile_image.setImageBitmap(bitmap);
        }

    }

}
