package com.android.smartbutler.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.smartbutler.R;
import com.android.smartbutler.adapter.CourierAdapter;
import com.android.smartbutler.entity.CourierData;
import com.android.smartbutler.util.StaticClass;
import com.android.smartbutler.util.UtilTools;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 项目名：SmartButler
 * 包名：  com.android.smartbutler.fragment
 * 文件名：CourierActivity
 * 创建者：HY
 * 创建时间：2018/9/12 9:46
 * 描述：  快递查询
 */

public class CourierActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_name;
    private EditText et_number;
    private Button btn_get_courier;
    private ListView listView;

    private List<CourierData> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier);

        initView();
    }

    //初始化
    private void initView() {
        et_name = findViewById(R.id.et_name);
        et_number = findViewById(R.id.et_number);
        btn_get_courier = findViewById(R.id.btn_get_courier);
        btn_get_courier.setOnClickListener(this);
        listView = findViewById(R.id.listView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get_courier:
                /**
                 * 1、获取输入框的内容
                 * 2、判断是否为空
                 * 3、拿到数据去请求数据(Json)
                 * 4、解析JSon
                 * 5、listView 适配器
                 * 6、实体类(item)
                 * 7、设置数据/显示效果
                 */
                //1、获取输入框的内容
                String name = et_name.getText().toString().trim();
                String number = et_number.getText().toString().trim();

                //拼接Url
                String url = "http://v.juhe.cn/exp/index?key=" + StaticClass.COURIER_KEY +
                        "&com=" + name +
                        "&no=" + number;

                //2、判断是否为空
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(number)) {
                    //3、拿到数据去请求数据(Json)
                    RxVolley.get(url, new HttpCallback() {
                        @Override
                        public void onSuccess(String t) {
                            //解析Json
                            parsingJson(t);
                        }
                    });
                } else {
                    UtilTools.toast(this, "输入框不能为空");
                }
                break;
        }
    }

    //解析数据
    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject result = jsonObject.getJSONObject("result");
            JSONArray jsonArray = result.getJSONArray("list");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = (JSONObject) jsonArray.get(i);

                CourierData data = new CourierData();
                data.setRemark(json.getString("remark"));
                data.setZone(json.getString("zone"));
                data.setDatetime(json.getString("datetime"));
                list.add(data);

            }

            //倒序
            Collections.reverse(list);
            CourierAdapter adapter = new CourierAdapter(this, list);
            listView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
