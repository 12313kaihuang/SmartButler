package com.android.smartbutler.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.smartbutler.MainActivity;
import com.android.smartbutler.R;
import com.android.smartbutler.entity.MyUser;
import com.android.smartbutler.util.SharedPreferencesUtil;
import com.android.smartbutler.view.CustomDialog;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 项目名：SmartButler
 * 包名：  com.android.smartbutler.ui
 * 文件名：LoginActivity
 * 创建者：HY
 * 创建时间：2018/9/10 9:13
 * 描述：  登录
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    //注册按钮
    private Button btn_register;
    private EditText et_name;
    private EditText et_password;
    private Button btnLogin;
    private CheckBox keep_password;
    private TextView tv_forget;

    private CustomDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        btn_register = findViewById(R.id.btn_registered);
        btn_register.setOnClickListener(this);
        et_name = findViewById(R.id.login_username);
        et_password = findViewById(R.id.login_password);
        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
        keep_password = findViewById(R.id.keep_password);
        tv_forget = findViewById(R.id.tv_forget);
        dialog =  CustomDialog.getDefaultDialog(this);
        //屏幕外点击无效
        dialog.setCancelable(false);

        tv_forget.setOnClickListener(this);

        //设置选中的状态
        boolean iskeeppass = SharedPreferencesUtil.getBoolean(this, "keeppass", false);
        keep_password.setChecked(iskeeppass);
        if (iskeeppass) {
            //设置密码
            et_name.setText(SharedPreferencesUtil.getString(this,"name",""));
            et_password.setText(SharedPreferencesUtil.getString(this,"password",""));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_registered:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.tv_forget:
                startActivity(new Intent(this,ForgetPasswordActivity.class));
                break;
            case R.id.btn_login:
                //1.获取输入框的值
                String name = et_name.getText().toString().trim();
                String password = et_password.getText().toString();
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password)) {
                    dialog.show();
                    //登录
                    final MyUser user = new MyUser();
                    user.setUsername(name);
                    user.setPassword(password);
                    user.login(new SaveListener<MyUser>() {

                        @SuppressWarnings("ConstantConditions")
                        @Override
                        public void done(MyUser myUser, BmobException e) {
                            dialog.dismiss();
                            //判断结果
                            if (e == null) {
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "登录失败"
                                        + e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    //假设现在输入用户名和密码，但是不点击登录，而是直接退出了
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //保存状态
        SharedPreferencesUtil.putBoolean(this, "keeppass", keep_password.isChecked());

        //是否记住密码
        if (keep_password.isChecked()) {
            //记住用户名和密码
            SharedPreferencesUtil.putString(this, "name", et_name.getText().toString().trim());
            SharedPreferencesUtil.putString(this, "password", et_password.getText().toString().trim());
        } else {
            SharedPreferencesUtil.deleteShare(this, "name");
            SharedPreferencesUtil.deleteShare(this, "password");

        }
    }
}
