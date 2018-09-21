package com.android.smartbutler.ui;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.smartbutler.R;
import com.android.smartbutler.util.UtilTools;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名：SmartButler
 * 包名：  com.android.smartbutler.ui
 * 文件名：AboutActivity
 * 创建者：HY
 * 创建时间：2018/9/21 20:37
 * 描述：  关于
 */

public class AboutActivity extends BaseActivity {

    private ListView mListView;
    private List<String> list = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        //去除listView的阴影
        getSupportActionBar().setElevation(0);
        initView();
    }

    private void initView() {
        mListView = findViewById(R.id.mListView);

        list.add("应用名：" + getString(R.string.app_name));
        list.add("版本号：" + UtilTools.getVersion(this));
        list.add("官网：www.imooc.com");

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                list);
        mListView.setAdapter(adapter);
    }


}
