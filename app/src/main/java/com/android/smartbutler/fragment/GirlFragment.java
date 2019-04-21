package com.android.smartbutler.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.android.smartbutler.R;
import com.android.smartbutler.adapter.GirlAdaptor;
import com.android.smartbutler.entity.GirlData;
import com.android.smartbutler.util.StaticClass;
import com.android.smartbutler.view.CustomDialog;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class GirlFragment extends Fragment {

    private GridView mGridView;
    private List<GirlData> girlDataList = new ArrayList<>();
    private GirlAdaptor girlAdaptor;
    //提示框
    private CustomDialog dialog;
    //预览图片
    private PhotoView iv_img;
    //图片地址数据
    private List<String> urlList = new ArrayList<>();
    //PhotoView
    private PhotoViewAttacher attacher;

    /**
     * 1.监听点击事件
     * 2.提示框
     * 3.加载图片
     * 4.PhotoView
     */

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_girl, null);
        findView(view);
        return view;
    }

    //初始化
    private void findView(View view) {
        mGridView = view.findViewById(R.id.mGridView);

        //解析
        RxVolley.get(StaticClass.GIRL_URL, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                parsingJson(t);
            }
        });

        dialog = new CustomDialog(getActivity(), LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, R.layout.dialog_girl,
                R.style.Theme_dialog, Gravity.CENTER,R.style.pop_anim_style);
        iv_img = dialog.findViewById(R.id.iv_img);
        //监听点击事件
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //解析图片
                Picasso.get().load(urlList.get(position))
                        .resize(500,300)
                        .into(iv_img);
                //缩放
                attacher = new PhotoViewAttacher(iv_img);
                //刷新
                attacher.update();
                dialog.show();
            }
        });
    }

    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONArray results = jsonObject.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject json = results.getJSONObject(i);
                String url = json.getString("url");
                GirlData data = new GirlData();
                data.setUrl(url);
                urlList.add(url);
                girlDataList.add(data);
            }
            girlAdaptor = new GirlAdaptor(getActivity(), girlDataList);
            mGridView.setAdapter(girlAdaptor);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
