package com.klisly.bookbox.alarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.NotificationCompat;

import com.klisly.bookbox.R;
import com.klisly.bookbox.ui.activity.SplashActivity;

import timber.log.Timber;

import static android.content.Context.NOTIFICATION_SERVICE;

public class MagzineAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager manager = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);

        String msg = intent.getStringExtra("msg");
        long intervalMillis = intent.getLongExtra("intervalMillis", 0);
        if (intervalMillis != 0) {
            AlarmManagerUtil.setAlarmTime(context, System.currentTimeMillis() + intervalMillis,
                    intent);
        }
        Timber.i(MagzineAlarmReceiver.class.getSimpleName(), "MagzineAlarmReceiver");
        //为了版本兼容  选择V7包下的NotificationCompat进行构造
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        //Ticker是状态栏显示的提示
        builder.setTicker("七点一刻");
        //第一行内容  通常作为通知栏标题
        builder.setContentTitle("来自指尖书香的提醒");
        //第二行内容 通常是通知正文
        builder.setContentText(msg);
        //第三行内容 通常是内容摘要什么的 在低版本机器上不一定显示
//        builder.setSubText("这里显示的是通知第三行内容！");
        //ContentInfo 在通知的右侧 时间的下面 用来展示一些其他信息
        //builder.setContentInfo("2");
        //number设计用来显示同种通知的数量和ContentInfo的位置一样，如果设置了ContentInfo则number会被隐藏
        builder.setNumber(2);
        //可以点击通知栏的删除按钮删除
        builder.setAutoCancel(true);
        //系统状态栏显示的小图标
        builder.setSmallIcon(R.drawable.ic_launcher);
        //下拉显示的大图标
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_launcher));
        intent = new Intent(context,SplashActivity.class);
        intent.putExtra("target", 1);
        PendingIntent pIntent = PendingIntent.getActivity(context,1,intent,0);
        //点击跳转的intent
        builder.setContentIntent(pIntent);
        //通知默认的声音 震动 呼吸灯
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        Notification notification = builder.build();
        manager.notify(0,notification);
    }


}
