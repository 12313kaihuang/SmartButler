package com.android.smartbutler.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

import com.android.smartbutler.util.LogUtil;
import com.android.smartbutler.util.StaticClass;

/**
 * 项目名：SmartButler
 * 包名：  com.android.smartbutler.service
 * 文件名：SmsService
 * 创建者：HY
 * 创建时间：2018/9/17 21:25
 * 描述：  短信监听服务
 */

public class SmsService extends Service {

    private SmsReceiver smsReceiver;
    //发件人号码
    private String smsPhone;
    //短信内容
    private String smsContent;

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

        LogUtil.d("服务启动了");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.i("stop service");

        //注销
        unregisterReceiver(smsReceiver);
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
                    LogUtil.d(smsPhone);
                }
            }
        }
    }
}
