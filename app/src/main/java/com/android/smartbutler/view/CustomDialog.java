package com.android.smartbutler.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.android.smartbutler.R;


/**
 * 项目名：SmartButler
 * 包名：  com.android.smartbutler.view
 * 文件名：CustomDialog
 * 创建者：HY
 * 创建时间：2018/9/10 20:59
 * 描述：  自定义Dialog
 */

@SuppressWarnings("unused")
public class CustomDialog extends Dialog {

    //定义模板
    public CustomDialog(@NonNull Context context, int layout, int style) {
        this(context, WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT, layout, style, Gravity.CENTER);
    }

    //定义属性
    public CustomDialog(Context context, int width, int height, int layout, int style, int gravity, int anim) {
        super(context, style);
        //设置属性
        setContentView(layout);
        Window window = getWindow();
        @SuppressWarnings("ConstantConditions")
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = width;
        layoutParams.height = height;
        layoutParams.gravity = gravity;
        window.setAttributes(layoutParams);
        window.setWindowAnimations(anim);
    }

    //实例
    public CustomDialog(Context context, int width, int height, int layout, int style, int gravity){
        this(context, width, height, layout, style, gravity,R.style.pop_anim_style);
    }

    public static CustomDialog getDefaultDialog(Context context) {
        return new CustomDialog(context, 300,300, R.layout.dialog_loding, R.style.Theme_dialog, Gravity.CENTER,
                R.style.pop_anim_style);
    }

}
