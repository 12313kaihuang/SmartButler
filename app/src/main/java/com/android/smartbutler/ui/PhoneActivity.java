package com.android.smartbutler.ui;

import android.os.Bundle;
import android.support.annotation.NavigationRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.smartbutler.R;
import com.android.smartbutler.util.StaticClass;
import com.android.smartbutler.util.UtilTools;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 项目名：SmartButler
 * 包名：  com.android.smartbutler.ui
 * 文件名：PhoneActivity
 * 创建者：HY
 * 创建时间：2018/9/15 21:25
 * 描述：  归属地查询
 */

public class PhoneActivity extends BaseActivity implements View.OnClickListener {

    //输入框
    private EditText et_number;
    private ImageView iv_company;
    private TextView tv_result;

    ;

    //标记位
    private boolean flag = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtivity_phone);

        initView();
    }

    private void initView() {
        et_number = findViewById(R.id.et_number);
        iv_company = findViewById(R.id.iv_company);
        tv_result = findViewById(R.id.tv_result);

        Button btn_0 = findViewById(R.id.btn_0);
        Button btn_1 = findViewById(R.id.btn_1);
        Button btn_2 = findViewById(R.id.btn_2);
        Button btn_3 = findViewById(R.id.btn_3);
        Button btn_4 = findViewById(R.id.btn_4);
        Button btn_5 = findViewById(R.id.btn_5);
        Button btn_6 = findViewById(R.id.btn_6);
        Button btn_7 = findViewById(R.id.btn_7);
        Button btn_8 = findViewById(R.id.btn_8);
        Button btn_9 = findViewById(R.id.btn_9);
        Button btn_del = findViewById(R.id.btn_del);
        Button btn_query = findViewById(R.id.btn_query);

        btn_0.setOnClickListener(this);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        btn_5.setOnClickListener(this);
        btn_6.setOnClickListener(this);
        btn_7.setOnClickListener(this);
        btn_8.setOnClickListener(this);
        btn_9.setOnClickListener(this);
        btn_del.setOnClickListener(this);
        btn_query.setOnClickListener(this);

        //长按事件
        btn_del.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                et_number.setText("");
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        /**
         * 逻辑
         * 1、获取输入框的内容
         * 2、判断是否为空
         * 3、网络请求
         * 4、解析Json
         * 5、结果显示
         *
         * ------
         * 键盘逻辑
         */
        //获取到输入框的内容
        String str = et_number.getText().toString();

        switch (v.getId()) {
            case R.id.btn_0:
            case R.id.btn_1:
            case R.id.btn_2:
            case R.id.btn_3:
            case R.id.btn_4:
            case R.id.btn_5:
            case R.id.btn_6:
            case R.id.btn_7:
            case R.id.btn_8:
            case R.id.btn_9:
                if (flag) {
                    flag = false;
                    str = "";
                    et_number.setText("");
                }
                et_number.setText(String.format("%s%s", str, ((Button) v).getText()));
                //移动光标
                et_number.setSelection(str.length() + 1);
                break;
            case R.id.btn_del:
                if (!TextUtils.isEmpty(str) && str.length() > 0) {
                    et_number.setText(str.substring(0, str.length() - 1));
                    et_number.setSelection(str.length() - 1);
                }
                break;
            case R.id.btn_query:
                if (!TextUtils.isEmpty(str)) {
                    getPhone(str);
                }
                break;

        }
    }

    //获取归属地
    private void getPhone(String str) {
        String url = "http://apis.juhe.cn/mobile/get?phone=" + str +
                "&key=" + StaticClass.Phone_KEY;
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                parsingJson(t);
            }
        });
    }

    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject result = jsonObject.getJSONObject("result");
            String province = result.getString("province");
            String city = result.getString("city");
            String areacode = result.getString("areacode");
            String zip = result.getString("zip");
            String company = result.getString("company");
            String card = result.getString("card");

            tv_result.setText(String.format("归属地：%s%s\n区号：%s\n邮编：%s\n运营商：%s\n类型：%s",
                    province, city, areacode, zip, company, card));

            //图片显示
            switch (company) {
                case "移动":
                    iv_company.setBackgroundResource(R.drawable.china_mobile);
                    break;
                case "联通":
                    iv_company.setBackgroundResource(R.drawable.china_unicom);
                    break;
                case "电信":
                    iv_company.setBackgroundResource(R.drawable.china_telecom);
                    break;
            }

            flag = true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
