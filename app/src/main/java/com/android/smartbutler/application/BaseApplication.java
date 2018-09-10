package com.android.smartbutler.application;

import android.app.Application;

import com.android.smartbutler.util.StaticClass;
import com.tencent.bugly.crashreport.CrashReport;

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
    }
}
