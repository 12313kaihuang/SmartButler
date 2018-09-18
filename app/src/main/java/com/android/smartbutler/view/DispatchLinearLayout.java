package com.android.smartbutler.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.LinearLayout;

/**
 * 项目名：SmartButler
 * 包名：  com.android.smartbutler.view
 * 文件名：DispatchLinearLayout
 * 创建者：HY
 * 创建时间：2018/9/18 20:17
 * 描述：  事件分发
 */

public class DispatchLinearLayout extends LinearLayout{

    private DisPatchKeyEventListener disPatchKeyEventListener;

    public DispatchLinearLayout(Context context) {
        super(context);
    }

    public DispatchLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DispatchLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DisPatchKeyEventListener getDisPatchKeyEventListener() {
        return disPatchKeyEventListener;
    }

    public void setDisPatchKeyEventListener(DisPatchKeyEventListener disPatchKeyEventListener) {
        this.disPatchKeyEventListener = disPatchKeyEventListener;
    }

    //接口
    public static interface DisPatchKeyEventListener{
        boolean dispatchKeyEvent(KeyEvent event);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        //如果不为空 说明调用了 去获取事件
        if (disPatchKeyEventListener != null) {
            return disPatchKeyEventListener.dispatchKeyEvent(event);
        }
        return super.dispatchKeyEvent(event);
    }
}
