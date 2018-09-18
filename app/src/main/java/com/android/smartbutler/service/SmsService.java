package com.android.smartbutler.service;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.telephony.SmsMessage;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.smartbutler.R;
import com.android.smartbutler.util.LogUtil;
import com.android.smartbutler.util.StaticClass;
import com.android.smartbutler.view.DispatchLinearLayout;

/**
 * 项目名：SmartButler
 * 包名：  com.android.smartbutler.service
 * 文件名：SmsService
 * 创建者：HY
 * 创建时间：2018/9/17 21:25
 * 描述：  短信监听服务
 */

public class SmsService extends Service implements View.OnClickListener {

    private SmsReceiver smsReceiver;
    //发件人号码
    private String smsPhone;
    //短信内容
    private String smsContent;
    //窗口管理
    private WindowManager wm;
    //布局参数
    private WindowManager.LayoutParams layoutParams;

    private DispatchLinearLayout view;

    private TextView tv_phone;
    private TextView tv_content;
    private Button btn_send_sms;

    private HomeWatchReceiver homeWatchReceiver;

    public static final String SYSTEM_DIALOGS_RESON_KEY = "reason";
    public static final String SYSTEM_DIALOGS_HOME_KEY = "homekey";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        init();
    }

    //初始化
    private void init() {
        LogUtil.d("service init");

        //动态注册
        smsReceiver = new SmsReceiver();
        IntentFilter intent = new IntentFilter();
        //添加Action
        intent.addAction(StaticClass.SMS_ACTION);
        //设置权限
        intent.setPriority(Integer.MAX_VALUE);
        //注册
        registerReceiver(smsReceiver, intent);


        homeWatchReceiver = new HomeWatchReceiver();
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        registerReceiver(homeWatchReceiver, intentFilter);

        LogUtil.d("服务启动了");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.i("stop service");

        //注销
        unregisterReceiver(smsReceiver);
        unregisterReceiver(homeWatchReceiver);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send_sms:
                sendSms();
                //消失窗口
                if (view.getParent() != null) {
                    wm.removeView(view);
                }
                break;
        }
    }

    //回复短信
    private void sendSms() {
        Uri uri = Uri.parse("smsto:" + smsPhone);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        //设置启动模式
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("sms_body", "");
        startActivity(intent);
    }

    //短信广播
    public class SmsReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (StaticClass.SMS_ACTION.equals(action)) {
                LogUtil.i("来短信了");

                //获取短信内容
                Object[] objs = (Object[]) intent.getExtras().get("pdus");
                //遍历数组:
                for (Object obj :
                        objs) {
                    //吧数组元素转换成短信对象
                    SmsMessage sms = SmsMessage.createFromPdu((byte[]) obj);
                    smsPhone = sms.getOriginatingAddress();
                    smsContent = sms.getMessageBody();
                    showWindow();
                }
            }
        }
    }

    //窗口提示
    @TargetApi(Build.VERSION_CODES.O)
    private void showWindow() {
        ///获取系统服务
        wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        //获取布局参数
        layoutParams = new WindowManager.LayoutParams();
        //定义宽高
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        //定义标记
        layoutParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        //定义格式
        layoutParams.format = PixelFormat.TRANSLUCENT;
        //定义类型
        layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        //加载布局
        view = (DispatchLinearLayout) View.inflate(getApplicationContext(), R.layout.sms_item, null);

        tv_phone = view.findViewById(R.id.tv_phone);
        tv_content = view.findViewById(R.id.tv_content);
        btn_send_sms = view.findViewById(R.id.btn_send_sms);
        btn_send_sms.setOnClickListener(this);

        //设置数据
        tv_phone.setText("发件人:" + smsPhone);
        tv_content.setText(smsContent);

        //添加View到窗口
        wm.addView(view, layoutParams);


        view.setDisPatchKeyEventListener(disPatchKeyEventListener);

    }

    private DispatchLinearLayout.DisPatchKeyEventListener disPatchKeyEventListener
            = new DispatchLinearLayout.DisPatchKeyEventListener() {
        @Override
        public boolean dispatchKeyEvent(KeyEvent event) {
            //判断是否是按返回键
            if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                LogUtil.d("按下了back键");
                if (view.getParent() != null) {
                    wm.removeView(view);
                }
                return true;
            }
            return false;
        }
    };


    //监听Home键的广播
    class HomeWatchReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                String reason = intent.getStringExtra(SYSTEM_DIALOGS_RESON_KEY);
                if (SYSTEM_DIALOGS_HOME_KEY.equals(reason)) {
                    LogUtil.i("我点击了HOME键");
                    if (view.getParent() != null) {
                        wm.removeView(view);
                    }
                }
            }
        }
    }
}
