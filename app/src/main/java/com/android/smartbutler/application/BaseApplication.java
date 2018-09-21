package com.android.smartbutler.application;

import android.app.Application;

import com.android.smartbutler.util.StaticClass;
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
        //初始化Bugly
        CrashReport.initCrashReport(getApplicationContext(), StaticClass.BUGLY_APP_ID, true);
        //初始化Bmob
        Bmob.initialize(this,StaticClass.BMOB_APP_ID);
        // 初始化科大讯飞TTS
        SpeechUtility.createUtility(getApplicationContext(),
                SpeechConstant.APPID + "=" + StaticClass.VOICE_KEY);
        //初始化Zxing
        ZXingLibrary.initDisplayOpinion(this);
    }
}
