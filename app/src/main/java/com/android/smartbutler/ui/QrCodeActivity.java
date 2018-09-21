package com.android.smartbutler.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.smartbutler.R;
import com.uuzuche.lib_zxing.activity.CodeUtils;

/**
 * 项目名：SmartButler
 * 包名：  com.android.smartbutler.ui
 * 文件名：QrCodeActivity
 * 创建者：HY
 * 创建时间：2018/9/21 18:24
 * 描述：  生成二维码
 */

public class QrCodeActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);

        initView();
    }

    private void initView() {
        ImageView iv_qr_code = findViewById(R.id.iv_qr_code);

        //屏幕的宽
        int width = getResources().getDisplayMetrics().widthPixels;

//        String textContent = editText.getText().toString();
        String textContent = "我是智能管家";
        if (TextUtils.isEmpty(textContent)) {
            Toast.makeText(QrCodeActivity.this, "您的输入为空!", Toast.LENGTH_SHORT).show();
            return;
        }
//        editText.setText("");
        Bitmap mBitmap = CodeUtils.createImage(textContent, width/2, width/2, BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        //noinspection ConstantConditions
        iv_qr_code.setImageBitmap(mBitmap);
    }
}
