package com.android.smartbutler.ui;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.TextView;

import com.android.smartbutler.R;
import com.android.smartbutler.util.LogUtil;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.ProgressListener;
import com.kymjs.rxvolley.http.VolleyError;
import com.kymjs.rxvolley.toolbox.FileUtils;

/**
 * 项目名：SmartButler
 * 包名：  com.android.smartbutler.ui
 * 文件名：UpdateActivity
 * 创建者：HY
 * 创建时间：2018/9/18 21:28
 * 描述：  下载
 */

public class UpdateActivity extends BaseActivity {

    private TextView tv_size;
    private String path;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtivity_update);

        initView();
    }


    private void initView() {
        tv_size = findViewById(R.id.tv_size);

        path = FileUtils.getSDCardPath() + "/" + System.currentTimeMillis() + ".jpg";

        //下载
        String url = getIntent().getStringExtra("url");
        LogUtil.d("url:"+url);
        if (!TextUtils.isEmpty(url)) {
            RxVolley.download(path, "http://222.196.189.25:8080/hy/kate.jpg", new ProgressListener() {
                @Override
                public void onProgress(long transferredBytes, long totalSize) {
                    LogUtil.d("transferredBytes:" + transferredBytes
                            + ",totalSize:" + totalSize);
                }
            }, new HttpCallback() {
                @Override
                public void onSuccess(String t) {
                    LogUtil.d("成功");
                }

                @Override
                public void onFailure(VolleyError error) {
                    LogUtil.d(error.toString());
                    LogUtil.d("失败");
                }
            });
        }
    }
}
