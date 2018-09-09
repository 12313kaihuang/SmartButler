package com.android.smartbutler.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 项目名：SmartButler
 * 包名：  com.android.smartbutler.util
 * 文件名：ShareUtils
 * 创建者：HY
 * 创建时间：2018/9/9 15:26
 * 描述：  SharedPreference工具类
 */

@SuppressWarnings("unused")
public class SharedPreferencesUtil {

    private static final String NAME = "config";

    //键 值
    public static void putString(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putString(key, value).apply();
    }

    //键 默认值
    public static String getString(Context context, String key, String defValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(NAME,
                Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defValue);
    }

    //键 值
    public static void putInt(Context context, String key, int value) {
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putInt(key, value).apply();
    }

    //键 默认值
    public static int getInt(Context context, String key, int defValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(NAME,
                Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, defValue);
    }

    //键 值
    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).apply();
    }

    //键 默认值
    public static boolean getBoolean(Context context, String key, boolean defValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(NAME,
                Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, defValue);
    }

    //删除 单个
    public static void deleteShare(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().remove(key).apply();
    }

    //删除全部
    public static void deleteAll(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().clear().apply();
    }
}
