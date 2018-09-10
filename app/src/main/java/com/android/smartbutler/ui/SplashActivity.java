package com.android.smartbutler.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.android.smartbutler.MainActivity;
import com.android.smartbutler.R;
import com.android.smartbutler.util.SharedPreferencesUtil;
import com.android.smartbutler.util.StaticClass;
import com.android.smartbutler.util.UtilTools;

/**
 * 项目名：SmartButler
 * 包名：  com.android.smartbutler.ui
 * 文件名：SplashActivity
 * 创建者：HY
 * 创建时间：2018/9/9 15:55
 * 描述：  闪屏页
 */

public class SplashActivity extends AppCompatActivity {

    /**
     *   1、延时2000ms
     *   2、判断程序是否第一次运行
     *   3、自定义字体
     *   4、Activity全屏主题
     */

    private TextView tv_splash;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case StaticClass.HANDLER_SPLASH:
                    //判断程序是否是第一次运行
                    if (isFirst()) {
                        startActivity(new Intent(SplashActivity.this,
                                GuideActivity.class));
                    }else {
                        startActivity(new Intent(SplashActivity.this,
                                LoginActivity.class));
                    }
                    finish();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();
    }

    private void initView() {
        //延时2000ms
        handler.sendEmptyMessageDelayed(StaticClass.HANDLER_SPLASH, 2000);

        tv_splash = findViewById(R.id.tv_splash);
        //设置字体
        UtilTools.setFont(this,tv_splash);
    }


    //判断程序是否第一次运行
    private boolean isFirst() {
        boolean isFirst = SharedPreferencesUtil.getBoolean(this, StaticClass.SHARE_IS_FIRST,
                true);
        if (isFirst) {
            //是第一次运行
            SharedPreferencesUtil.putBoolean(this, StaticClass.SHARE_IS_FIRST,false);
        }
        return isFirst;
    }


    //禁止返回键
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
