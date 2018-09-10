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
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.smartbutler.R;
import com.android.smartbutler.entity.MyUser;
import com.android.smartbutler.ui.LoginActivity;
import com.android.smartbutler.util.UtilTools;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;


public class UserFragment extends Fragment implements View.OnClickListener {

    private Button btn_exit_user;

    private TextView edit_user;

    private EditText et_userName;
    private EditText et_sex;
    private EditText et_age;
    private EditText et_desc;

    private Button btn_update_ok;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        btn_exit_user = view.findViewById(R.id.btn_exit_user);
        edit_user = view.findViewById(R.id.edit_user);
        et_userName = view.findViewById(R.id.et_username);
        et_sex = view.findViewById(R.id.et_sex);
        et_age = view.findViewById(R.id.et_age);
        et_desc = view.findViewById(R.id.et_desc);
        btn_update_ok = view.findViewById(R.id.btn_update_ok);

        btn_update_ok.setOnClickListener(this);

        //默认不可点击/不可输入
        setEnabled(false);

        //设置具体的值
        MyUser user = MyUser.getCurrentUser(MyUser.class);
        et_userName.setText(user.getUsername());
        et_sex.setText(user.isSex() ? "男" : "女");
        et_age.setText(String.valueOf(user.getAge()));
        et_desc.setText(user.getDesc());

        btn_exit_user.setOnClickListener(this);
        edit_user.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_exit_user:  //退出登录
                //清除缓存对象
                MyUser.logOut();
                //现在的currentUser是null了
                @SuppressWarnings("unused")
                BmobUser currentUser = MyUser.getCurrentUser();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                //noinspection ConstantConditions
                getActivity().finish();
                break;
            case R.id.edit_user:  //编辑资料
                //控制焦点
                setEnabled(true);
                btn_update_ok.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_update_ok:
                //1.拿到输入框的值
                String username = et_userName.getText().toString();
                String age = et_age.getText().toString();
                String sex = et_sex.getText().toString();
                String desc = et_desc.getText().toString();

                //2.判断是否为空
                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(age) && !TextUtils.isEmpty(sex)) {
                    //3.更新属性
                    MyUser user = new MyUser();
                    user.setUsername(username);
                    user.setAge(Integer.parseInt(age));
                    user.setDesc(desc);
                    if ("男".equals(sex)) {
                        user.setSex(true);
                    } else {
                        user.setSex(false);
                    }
                    BmobUser bmobUser = BmobUser.getCurrentUser();
                    user.update(bmobUser.getObjectId(), new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e == null) {
                                        setEnabled(false);
                                        btn_update_ok.setVisibility(View.GONE);
                                        UtilTools.toast(getActivity(),"修改成功");
                                    }else {
                                        UtilTools.toast(getActivity(),"修改失败");
                                    }
                                }
                            }
                    );
                } else {
                    UtilTools.toast(getActivity(), "输入框不能为空");
                }
                break;
        }
    }

    private void setEnabled(boolean enabled) {
        et_userName.setEnabled(enabled);
        et_sex.setEnabled(enabled);
        et_age.setEnabled(enabled);
        et_desc.setEnabled(enabled);
    }
}
