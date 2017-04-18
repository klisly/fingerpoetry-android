package com.klisly.bookbox;

import android.app.Application;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.multidex.MultiDex;
import android.support.v7.app.NotificationCompat;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.karumi.dexter.Dexter;
import com.klisly.bookbox.model.Notification;
import com.klisly.bookbox.ui.activity.SplashActivity;
import com.klisly.bookbox.utils.ToastHelper;
import com.klisly.common.SharedPreferenceUtils;
import com.klisly.common.StringUtils;
import com.klisly.common.dateutil.DateUtil;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.entity.UMessage;

import java.util.Date;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import timber.log.Timber;

public class BookBoxApplication extends Application {
    private static BookBoxApplication appContext = null;
    private SharedPreferenceUtils preferenceUtils;
    private ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 15, 30, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());

    public static BookBoxApplication getInstance() {
        return appContext;
    }

    private PushAgent mPushAgent;
    private Gson gson;
    private Handler handler;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        Timber.plant(new Timber.DebugTree());
        ToastHelper.init(this);
        Dexter.initialize(this);
        gson = new Gson();
        preferenceUtils = new SharedPreferenceUtils(this);
        handler = new Handler();
//        CrashHandler.getInstance().init(this.getApplicationContext());
        CrashReport.initCrashReport(getApplicationContext(), "900028744", BuildConfig.DEBUG);

        initPush();
    }

    private void initPush() {
        try {
            mPushAgent = PushAgent.getInstance(this);
            //注册推送服务，每次调用register方法都会回调该接口
            mPushAgent.register(new IUmengRegisterCallback() {

                @Override
                public void onSuccess(String deviceToken) {
    //                Timber.i("device token:" + deviceToken);
                }

                @Override
                public void onFailure(String s, String s1) {
    //                Timber.i("err device token:" + s + " " + s1);
                }
            });
            UmengMessageHandler messageHandler = new UmengMessageHandler() {
                @Override
                public void dealWithCustomMessage(final Context context, final UMessage msg) {
                    try {
                        Notification notification = gson.fromJson(msg.custom, Notification.class);
                        if (Constants.NOTIFI_TYPE_MOMENT.equals(notification.getType())) {
                            showMomentNotifi(notification.getTitle(), notification.getDesc());
                        } else if (Constants.NOTIFI_TYPE_NOVEL_UPDATE.equals(notification.getType())) {
                            showNovelUpdate(notification.getTitle(), notification.getDesc(), notification.getCid());
                        }
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                    }
                }
            };
            mPushAgent.setMessageHandler(messageHandler);
//            mPushAgent.setDebugMode(BuildConfig.DEBUG);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void showMomentNotifi(String title, String msg) {
        int hour = DateUtil.getHour(new Date());
        if(hour < 12 && !BookBoxApplication.getInstance().getPreferenceUtils().getValue(Constants.NOTIFICATION_MORNING, true)){
            return;
        } else if(hour > 12 && !BookBoxApplication.getInstance().getPreferenceUtils().getValue(Constants.NOTIFICATION_AFTERNOON, true)){
            return;
        }
        NotificationManager manager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
        //为了版本兼容  选择V7包下的NotificationCompat进行构造
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        //Ticker是状态栏显示的提示
        builder.setTicker(title);
        //第一行内容  通常作为通知栏标题
        builder.setContentTitle(title);
        //第二行内容 通常是通知正文
        builder.setContentText(msg);
        builder.setAutoCancel(true);
        //系统状态栏显示的小图标
        builder.setSmallIcon(R.drawable.ic_launcher);
        //下拉显示的大图标
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
        Intent intent = new Intent(this, SplashActivity.class);
        intent.putExtra("target", Constants.NOTIFI_ACTION_MOMENT);
        PendingIntent pIntent = PendingIntent.getActivity(this, Constants.NOTIFI_ID_MOMENT, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        //点击跳转的intent
        builder.setContentIntent(pIntent);
        //通知默认的声音 震动 呼吸灯
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        android.app.Notification notification = builder.build();
        manager.notify(Constants.NOTIFI_ID_MOMENT, notification);
    }

    /**
     * @param title
     * @param msg
     * @param cid   章节id
     */
    public void showNovelUpdate(String title, String msg, String cid) {

        if (!getPreferenceUtils().getValue(Constants.NOTIFICATION_AFTERNOON, true)) {
            return;
        }

        NotificationManager manager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
        //为了版本兼容  选择V7包下的NotificationCompat进行构造
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        //Ticker是状态栏显示的提示
        builder.setTicker(title);
        //第一行内容  通常作为通知栏标题
        builder.setContentTitle(title);
        //第二行内容 通常是通知正文
        builder.setContentText(msg);
        //可以点击通知栏的删除按钮删除
        builder.setAutoCancel(true);
        //系统状态栏显示的小图标
        builder.setSmallIcon(R.drawable.ic_launcher);
        int uniqueId = 0;
        if (StringUtils.isEmpty(cid)) {
            uniqueId = Constants.NOTIFI_ID_NOVEL_UPDATE;
        } else {
            uniqueId = cid.hashCode();
        }
        //下拉显示的大图标
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
        Intent intent = new Intent(this, SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("target", Constants.NOTIFI_ACTION_NOVEL_UPDATE);
        intent.putExtra("cid", cid);
        PendingIntent pIntent = PendingIntent.getActivity(this, uniqueId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //点击跳转的intent
        builder.setContentIntent(pIntent);
        //通知默认的声音 震动 呼吸灯
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        android.app.Notification notification = builder.build();
        manager.notify(uniqueId, notification);
    }


    public SharedPreferenceUtils getPreferenceUtils() {
        return preferenceUtils;
    }

    public Gson getGson() {
        return gson;
    }

    public Handler getHandler() {
        return handler;
    }

    public PushAgent getPushAgent() {
        return mPushAgent;
    }

    public ThreadPoolExecutor getExecutor() {
        return executor;
    }
}
