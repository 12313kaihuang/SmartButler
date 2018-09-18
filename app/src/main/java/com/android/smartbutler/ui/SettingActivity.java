package com.android.smartbutler.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Switch;

import com.android.smartbutler.MainActivity;
import com.android.smartbutler.R;
import com.android.smartbutler.service.SmsService;
import com.android.smartbutler.util.LogUtil;
import com.android.smartbutler.util.SharedPreferencesUtil;

/**
 * 项目名：SmartButler
 * 包名：  com.adnroid.smartbutler.ui
 * 文件名：SettingActivity
 * 创建者：HY
 * 创建时间：2018/9/9 10:54
 * 描述：  设置页面
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    //语音播报
    private Switch sw_speak;

    private Switch sw_sms;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();
    }

    private void initView() {
        sw_speak = findViewById(R.id.sw_speak);
        sw_speak.setOnClickListener(this);

        boolean isSpeak = SharedPreferencesUtil.getBoolean(this, "isSpeak", false);
        sw_speak.setChecked(isSpeak);

        sw_sms = findViewById(R.id.sw_sms);
        sw_sms.setOnClickListener(this);

        boolean isSms = SharedPreferencesUtil.getBoolean(this, "isSms", false);
        sw_sms.setChecked(isSms);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sw_speak:
                //切换
                sw_speak.setSelected(!sw_speak.isSelected());
                //保存状态
                SharedPreferencesUtil.putBoolean(this, "isSpeak", sw_speak.isChecked());
                break;
            case R.id.sw_sms:
                //切换
                sw_sms.setSelected(!sw_sms.isSelected());
                //保存状态
                SharedPreferencesUtil.putBoolean(this, "isSms", sw_sms.isChecked());
                if (sw_sms.isChecked()) {

                    //动态申请权限
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{
                                Manifest.permission.RECEIVE_SMS
                        }, 1);
                    }

                    if(Build.VERSION.SDK_INT>=23)
                    {
                        if(Settings.canDrawOverlays(this))
                        {
                            //有悬浮窗权限开启服务绑定 绑定权限
                            Intent intent = new Intent(this, SmsService.class);
                            startService(intent);

                        }else{
                            //没有悬浮窗权限m,去开启悬浮窗权限
                            try{
                                Intent  intent=new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                                startActivityForResult(intent, 10);
                            }catch (Exception e)
                            {
                                e.printStackTrace();
                            }

                        }
                    }



                    startService(new Intent(this, SmsService.class));
                } else {
                    stopService(new Intent(this, SmsService.class));
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    LogUtil.d("receivedSMs获取权限成功");
                } else {
                    LogUtil.d("申请获取短信权限失败");
                }
                break;
        }
    }
}
