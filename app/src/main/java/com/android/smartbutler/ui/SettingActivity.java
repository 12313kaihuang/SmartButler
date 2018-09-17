package com.android.smartbutler.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Switch;

import com.android.smartbutler.R;
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sw_speak:
                //切换
                sw_speak.setSelected(!sw_speak.isSelected());
                //保存状态
                SharedPreferencesUtil.putBoolean(this,"isSpeak",sw_speak.isChecked());
                break;
        }
    }
}
