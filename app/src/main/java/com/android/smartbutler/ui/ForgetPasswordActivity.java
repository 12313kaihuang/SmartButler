package com.android.smartbutler.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.smartbutler.R;
import com.android.smartbutler.entity.MyUser;
import com.android.smartbutler.util.LogUtil;
import com.android.smartbutler.util.UtilTools;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 项目名：SmartButler
 * 包名：  com.android.smartbutler.ui
 * 文件名：ForgetPasswordActivity
 * 创建者：HY
 * 创建时间：2018/9/10 20:13
 * 描述：  忘记/重置密码
 */

public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_forget_password;
    private EditText et_email;

    private EditText et_now;
    private EditText et_new;
    private EditText et_new_pass;
    private Button btn_update_pass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        initView();
    }

    //初始化View
    private void initView() {
        btn_forget_password = findViewById(R.id.btn_forget_password);
        btn_forget_password.setOnClickListener(this);
        et_email = findViewById(R.id.et_email);
        et_now = findViewById(R.id.et_now);
        et_new = findViewById(R.id.et_new);
        et_new_pass = findViewById(R.id.et_new_password);
        btn_update_pass = findViewById(R.id.btn_update_password);
        btn_update_pass.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_forget_password:
                //1.获取输入框的邮箱
                String email = et_email.getText().toString().trim();
                if (!TextUtils.isEmpty(email)) {
                    //3.发送邮件
                    MyUser.resetPasswordByEmail(email, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Toast.makeText(ForgetPasswordActivity.this,
                                        "邮箱发送成功！", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(ForgetPasswordActivity.this,
                                        "邮箱发送失败！", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_update_password:
                //1.获取输入框的值
                String now = et_now.getText().toString().trim();
                String new_p = et_new.getText().toString().trim();
                String new_pass = et_new_pass.getText().toString().trim();

                //2.判断是否为空
                if (!TextUtils.isEmpty(now) && !TextUtils.isEmpty(new_p)
                        && !TextUtils.isEmpty(new_pass)) {
                    MyUser.updateCurrentUserPassword(now, new_p, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                UtilTools.toast(ForgetPasswordActivity.this, "重置密码成功");
                                finish();
                            } else {
                                UtilTools.toast(ForgetPasswordActivity.this, "重置密码失败！");
                            }
                        }
                    });
                } else {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
