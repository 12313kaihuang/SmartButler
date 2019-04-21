package com.android.smartbutler.application;

import android.Manifest;
import android.app.Application;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;

import com.android.smartbutler.util.StaticClass;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.tencent.bugly.crashreport.CrashReport;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import cn.bmob.v3.Bmob;

/**
 * Created by HY on 2018/9/9.
 * SmartButler
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        getPermission();
        //初始化Bugly
        CrashReport.initCrashReport(getApplicationContext(), StaticClass.BUGLY_APP_ID, true);
        //初始化Bmob
        Bmob.initialize(this,StaticClass.BMOB_APP_ID);
        // 初始化科大讯飞TTS
        SpeechUtility.createUtility(getApplicationContext(),
                SpeechConstant.APPID + "=" + StaticClass.VOICE_KEY);
        //初始化Zxing
//        ZXingLibrary.initDisplayOpinion(this);
        //百度地图
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
    }

//    private void getPermission() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (ContextCompat.checkSelfPermission(this,Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
//
//            }
//        }
//    }
}
