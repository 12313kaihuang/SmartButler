package com.android.smartbutler.util;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;
import android.widget.Toast;

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

}
