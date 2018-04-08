package com.klisly.bookbox.ui.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.klisly.bookbox.BookBoxApplication;
import com.klisly.bookbox.BuildConfig;
import com.klisly.bookbox.CommonHelper;
import com.klisly.bookbox.Constants;
import com.klisly.bookbox.R;
import com.klisly.bookbox.alarm.AlarmManagerUtil;
import com.klisly.bookbox.api.BookRetrofit;
import com.klisly.bookbox.api.WxArticleApi;
import com.klisly.bookbox.domain.ApiResult;
import com.klisly.bookbox.logic.SystemLogic;
import com.klisly.bookbox.model.ChannleEntity;
import com.klisly.bookbox.model.WxArticle;
import com.klisly.bookbox.model.WxCate;
import com.klisly.bookbox.subscriber.AbsSubscriber;
import com.klisly.bookbox.subscriber.ApiException;
import com.klisly.bookbox.utils.TopToastHelper;
import com.klisly.common.LogUtils;
import com.qq.e.ads.splash.SplashAD;
import com.qq.e.ads.splash.SplashADListener;
import com.qq.e.comm.util.AdError;
import com.umeng.message.PushAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class SplashActivity extends Activity implements SplashADListener {
    public static final String TAG = SplashActivity.class.getName();
    private ViewGroup container;
    public boolean canJump = false;
    private SplashAD splashAD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        PushAgent.getInstance(getApplicationContext()).onAppStart();
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        container = this.findViewById(R.id.splash_container);

        CommonHelper.updateDeviceToken(getApplicationContext());

        initAlarm();

        if (BookBoxApplication.getInstance().getPreferenceUtils().getValue(Constants.FIRST_OPEN, true)) {
            Intent intent = new Intent(this, WelcomeGuideActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        BookRetrofit.getInstance().getWxArticleApi().cates(new HashMap<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsSubscriber<ApiResult<List<ChannleEntity>>>(SplashActivity.this, false) {
                    @Override
                    protected void onError(ApiException ex) {
                        jumpNext();
                    }

                    @Override
                    protected void onPermissionError(ApiException ex) {
                        jumpNext();
                    }

                    @Override
                    public void onNext(ApiResult<List<ChannleEntity>> res) {
                        jumpNext();
                        LogUtils.i(TAG, "fetch cates:" + res.getData());
                        ChannleEntity.updateDefaultWxCates(res.getData());
                    }
                });
    }

    /**
     * ----------非常重要----------
     * <p>
     * Android6.0以上的权限适配简单示例：
     * <p>
     * 如果targetSDKVersion >= 23，那么必须要申请到所需要的权限，再调用广点通SDK，否则广点通SDK不会工作。
     * <p>
     * Demo代码里是一个基本的权限申请示例，请开发者根据自己的场景合理地编写这部分代码来实现权限申请。
     * 注意：下面的`checkSelfPermission`和`requestPermissions`方法都是在Android6.0的SDK中增加的API，如果您的App还没有适配到Android6.0以上，则不需要调用这些方法，直接调用广点通SDK即可。
     */
    @TargetApi(Build.VERSION_CODES.M)
    private void checkAndRequestPermission() {
        List<String> lackedPermission = new ArrayList<String>();
        if (!(checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED)) {
            lackedPermission.add(Manifest.permission.READ_PHONE_STATE);
        }

        if (!(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            lackedPermission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (!(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
            lackedPermission.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        // 权限都已经有了，那么直接调用SDK
        if (lackedPermission.size() == 0) {
            fetchSplashAD(this, container, Constants.QQ_APP_ID, Constants.SplashPosID, this, 0);
        } else {
            // 请求所缺少的权限，在onRequestPermissionsResult中再看是否获得权限，如果获得权限就可以调用SDK，否则不要调用SDK。
            String[] requestPermissions = new String[lackedPermission.size()];
            lackedPermission.toArray(requestPermissions);
            requestPermissions(requestPermissions, 1024);
        }
    }

    private boolean hasAllPermissionsGranted(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1024 && hasAllPermissionsGranted(grantResults)) {
            fetchSplashAD(this, container, Constants.QQ_APP_ID, Constants.SplashPosID, this, 0);
        } else {
            // 如果用户没有授权，那么应该说明意图，引导用户去设置里面授权。
            Toast.makeText(this, "应用缺少必要的权限！请点击\"权限\"，打开所需要的权限。", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivity(intent);
            finish();
        }
    }

    /**
     * 拉取开屏广告，开屏广告的构造方法有3种，详细说明请参考开发者文档。
     *
     * @param activity    展示广告的activity
     * @param adContainer 展示广告的大容器
     *                    //     * @param skipContainer   自定义的跳过按钮：传入该view给SDK后，SDK会自动给它绑定点击跳过事件。SkipView的样式可以由开发者自由定制，其尺寸限制请参考activity_splash.xml或者接入文档中的说明。
     * @param appId       应用ID
     * @param posId       广告位ID
     * @param adListener  广告状态监听器
     * @param fetchDelay  拉取广告的超时时长：取值范围[3000, 5000]，设为0表示使用广点通SDK默认的超时时长。
     */
    private void fetchSplashAD(Activity activity, ViewGroup adContainer,
                               String appId, String posId, SplashADListener adListener, int fetchDelay) {
        splashAD = new SplashAD(activity, adContainer, appId, posId, adListener, fetchDelay);
    }


    private void jumpNext() {
        // 如果targetSDKVersion >= 23，就要申请好权限。如果您的App没有适配到Android6.0（即targetSDKVersion < 23），那么只需要在这里直接调用fetchSplashAD接口。
        if (Build.VERSION.SDK_INT >= 23) {
            checkAndRequestPermission();
        } else if (!BuildConfig.DEBUG) {
            // 如果是Android6.0以下的机器，默认在安装时获得了所有权限，可以直接调用SDK
            fetchSplashAD(this, container, Constants.QQ_APP_ID, Constants.SplashPosID, this, 0);
        } else {
            gotoMain();
        }
    }

    private void initAlarm() {
        AlarmManagerUtil.setAlarm(getApplicationContext(), 1, 7, 15, 0, 0, "亲，阅读小编早上为您准备的的文章吧", 0);
        AlarmManagerUtil.setAlarm(getApplicationContext(), 1, 19, 15, 1, 0, "亲，来欣赏下这几篇文章哟", 0);
    }

    @Override
    public void onADPresent() {
        Log.i("SplashActivity", "SplashADPresent");
    }

    @Override
    public void onADClicked() {
        Log.i("SplashActivity", "SplashADClicked");
    }

    @Override
    public void onADTick(long l) {

    }

    @Override
    public void onADDismissed() {
        Log.i("SplashActivity", "SplashADDismissed");
        next();
    }

    @Override
    public void onNoAD(AdError adError) {
        Log.i("SplashActivity", "LoadSplashADFail, eCode=" + adError.getErrorMsg());
        BookBoxApplication.getInstance().getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        gotoMain();

                    }
                });
            }
        }, 800);

    }

    private void gotoMain() {
        Intent intent = getIntent();
        intent.setClass(this, HomeActivity.class);
        this.startActivity(intent);
        this.finish();
    }

    /**
     * 设置一个变量来控制当前开屏页面是否可以跳转，当开屏广告为普链类广告时，点击会打开一个广告落地页，此时开发者还不能打开自己的App主页。当从广告落地页返回以后，
     * 才可以跳转到开发者自己的App主页；当开屏广告是App类广告时只会下载App。
     */
    private void next() {
        if (canJump) {
            BookBoxApplication.getInstance().getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            gotoMain();

                        }
                    });
                }
            }, 0);
        } else {
            canJump = true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        canJump = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (canJump) {
            next();
        }
        canJump = true;
    }

    /**
     * 开屏页最好禁止用户对返回按钮的控制
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
