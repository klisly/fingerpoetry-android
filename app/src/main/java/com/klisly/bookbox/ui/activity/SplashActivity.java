package com.klisly.bookbox.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.klisly.bookbox.BookBoxApplication;
import com.klisly.bookbox.CommonHelper;
import com.klisly.bookbox.R;

import butterknife.ButterKnife;


public class SplashActivity extends Activity  {

  private ViewGroup container;
  public boolean canJump = false;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Fresco.initialize(this);
    setContentView(R.layout.activity_splash);
    ButterKnife.bind(this);
    container = (ViewGroup) this.findViewById(R.id.splash_container);
    CommonHelper.getTopics(this);
    CommonHelper.getUserTopics(this);
    CommonHelper.getSites(this);
    CommonHelper.getUserSites(this);
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
    }, 3000);
  }

  private void gotoMain() {
    this.startActivity(new Intent(this, HomeActivity.class));
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

  /** 开屏页最好禁止用户对返回按钮的控制 */
  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
      return true;
    }
    return super.onKeyDown(keyCode, event);
  }

}
