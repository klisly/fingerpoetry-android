package com.klisly.bookbox.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.klisly.bookbox.BookBoxApplication;
import com.klisly.bookbox.BuildConfig;
import com.klisly.bookbox.CommonHelper;
import com.klisly.bookbox.Constants;
import com.klisly.bookbox.R;
import com.klisly.bookbox.alarm.AlarmManagerUtil;
import com.qq.e.ads.splash.SplashAD;
import com.qq.e.ads.splash.SplashADListener;
import com.umeng.message.PushAgent;

import butterknife.ButterKnife;


public class SplashActivity extends Activity implements SplashADListener {

    private SplashAD splashAD;
    private ViewGroup container;
    public boolean canJump = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        PushAgent.getInstance(getApplicationContext()).onAppStart();
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        container = (ViewGroup) this.findViewById(R.id.splash_container);

        /**
         * 开屏广告现已增加新的接口，可以由开发者在代码中设置开屏的超时时长
         * SplashAD(Activity activity, ViewGroup container, String appId, String posId, SplashADListener adListener, int fetchDelay)
         * fetchDelay参数表示开屏的超时时间，单位为ms，取值范围[3000, 5000]。设置为0时表示使用广点通的默认开屏超时配置
         *
         * splashAD = new SplashAD(this, container, Constants.APPID, Constants.SplashPosID, this, 3000);可以设置超时时长为3000ms
         */
        CommonHelper.getTopics(getApplicationContext());
        CommonHelper.getUserTopics(getApplicationContext());
        CommonHelper.getSites(getApplicationContext());
        CommonHelper.getUserSites(getApplicationContext());
        CommonHelper.getUserNovels(getApplicationContext());
        CommonHelper.updateDeviceToken(getApplicationContext());
        initAlarm();
        if(BookBoxApplication.getInstance().getPreferenceUtils().getValue(Constants.FIRST_OPEN, true)){
            Intent intent = new Intent(this, WelcomeGuideActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        if (!BuildConfig.DEBUG) {
            splashAD = new SplashAD(this, container, Constants.QQ_APP_ID, Constants.SplashPosID, this, 3000);
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
    public void onNoAD(int errorCode) {
        Log.i("SplashActivity", "LoadSplashADFail, eCode=" + errorCode);
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
        }, 2000);

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
            gotoMain();
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
