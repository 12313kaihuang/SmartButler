package com.android.smartbutler.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.smartbutler.R;
import com.android.smartbutler.adapter.WechatAdaptor;
import com.android.smartbutler.entity.WechatData;
import com.android.smartbutler.ui.WebViewActivity;
import com.android.smartbutler.util.LogUtil;
import com.android.smartbutler.util.StaticClass;
import com.android.smartbutler.util.UtilTools;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class WechatFragment extends Fragment {

    private ListView listView;
    private List<WechatData> list = new ArrayList<>();

    //标题
    private List<String> titleList = new ArrayList<>();
    //地址
    private List<String> urlList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wechat, null);
        findView(view);
        return view;
    }

    //初始化view
    private void findView(View view) {
        listView = view.findViewById(R.id.listView);

        //解析接口
        String url = "https://v.juhe.cn/weixin/query?key=" + StaticClass.WECHAT_KEY;

        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                parsingJson(t);
            }
        });

        //点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("title", titleList.get(position));
                intent.putExtra("url", urlList.get(position));
                startActivity(intent);
            }
        });
    }

    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject result = jsonObject.getJSONObject("result");
            JSONArray jsonArray = result.getJSONArray("list");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = (JSONObject) jsonArray.get(i);
                WechatData data = new WechatData();
                data.setTitle(json.getString("title"));
                data.setSource(json.getString("source"));

                if (TextUtils.isEmpty(json.getString("firstImg"))){
                    data.setImgUrl("http://c.hiphotos.baidu.com/image/pic/item/b58f8c5494eef01f40ef23e9edfe9925bc317d26.jpg");
                }else {
                    data.setImgUrl(json.getString("firstImg"));
                }
                list.add(data);

                titleList.add(data.getTitle());
                urlList.add(json.getString("url"));
            }
            WechatAdaptor adaptor = new WechatAdaptor(getActivity(), list);
            listView.setAdapter(adaptor);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
