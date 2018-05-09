package com.klisly.bookbox.ui.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.klisly.bookbox.BookBoxApplication;
import com.klisly.bookbox.BusProvider;
import com.klisly.bookbox.Constants;
import com.klisly.bookbox.R;
import com.klisly.bookbox.api.AccountApi;
import com.klisly.bookbox.api.BookRetrofit;
import com.klisly.bookbox.domain.ApiResult;
import com.klisly.bookbox.domain.LoginData;
import com.klisly.bookbox.logic.AccountLogic;
import com.klisly.bookbox.model.User;
import com.klisly.bookbox.model.Version;
import com.klisly.bookbox.ottoevent.LoginEvent;
import com.klisly.bookbox.ottoevent.LogoutEvent;
import com.klisly.bookbox.ottoevent.ToLoginEvent;
import com.klisly.bookbox.subscriber.AbsSubscriber;
import com.klisly.bookbox.subscriber.ApiException;
import com.klisly.bookbox.ui.base.BaseMainFragment;
import com.klisly.bookbox.ui.fragment.AboutFragment;
import com.klisly.bookbox.ui.fragment.SettingFragment;
import com.klisly.bookbox.ui.fragment.account.LoginFragment;
import com.klisly.bookbox.ui.fragment.topic.ChooseTopicFragment;
import com.klisly.bookbox.ui.fragment.topic.TopicFragment;
import com.klisly.bookbox.ui.fragment.magzine.MagFragment;
import com.klisly.bookbox.ui.fragment.novel.NovelFragment;
import com.klisly.bookbox.ui.fragment.site.SiteFragment;
import com.klisly.bookbox.ui.fragment.user.MineFragment;
import com.klisly.bookbox.ui.fragment.wx.WxFragment;
import com.klisly.bookbox.utils.ActivityUtil;
import com.klisly.bookbox.utils.ToastHelper;
import com.klisly.bookbox.widget.update.AppUtils;
import com.klisly.bookbox.widget.update.UpdateDialog;
import com.squareup.otto.Subscribe;
import com.umeng.message.PushAgent;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.sharesdk.framework.ShareSDK;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class HomeActivity extends SupportActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        BaseMainFragment.OnFragmentOpenDrawerListener {
    public static final String TAG = HomeActivity.class.getSimpleName();
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawer;
    @Bind(R.id.vNavigation)
    NavigationView mNavigationView;
    private TextView mTvName;   // NavigationView上的名字
    private SimpleDraweeView mImgNav;  // NavigationView上的头像

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PushAgent.getInstance(getApplicationContext()).onAppStart();
        ShareSDK.initSDK(this);
        Fresco.initialize(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        checkUpdate();
        checkPermission();
        mNavigationView.setCheckedItem(R.id.menu_weixin);
        WxFragment fragment = findFragment(WxFragment.class);
        loadRootFragment(R.id.fl_container, WxFragment.newInstance());
        if (savedInstanceState == null) {
            jumpChooseTopic();
        }
        new Handler().postDelayed(() -> checkNotify(getIntent()), 200);
    }

    private void jumpChooseTopic() {
        String home = BookBoxApplication.getInstance().getPreferenceUtils().getValue(Constants.HOME_FRAG, Constants.FRAG_WX);
        final SupportFragment topFragment = (SupportFragment) getTopFragment();
        if (home.equals(Constants.FRAG_NOVEL)) {
            mNavigationView.setCheckedItem(R.id.menu_novel);
            NovelFragment fragment = findFragment(NovelFragment.class);
            topFragment.start(fragment, SupportFragment.SINGLETASK);
        } else if (home.equals(Constants.FRAG_SITE)) {
            mNavigationView.setCheckedItem(R.id.menu_site);
            SiteFragment fragment = findFragment(SiteFragment.class);
            topFragment.start(fragment, SupportFragment.SINGLETASK);
        } else if (home.equals(Constants.FRAG_TOPIC)) {
            mNavigationView.setCheckedItem(R.id.menu_topic);
            TopicFragment fragment = findFragment(TopicFragment.class);
            topFragment.start(fragment, SupportFragment.SINGLETASK);
        } else if (home.equals(Constants.FRAG_MAGZINE)) {
            mNavigationView.setCheckedItem(R.id.menu_magzine);
            MagFragment fragment = findFragment(MagFragment.class);
            topFragment.start(fragment, SupportFragment.SINGLETASK);
        }
    }

    private void checkNotify(Intent intent) {
        getIntent().putExtra("target", intent.getIntExtra("target", 0));
        getIntent().putExtra("cid", intent.getStringExtra("cid"));
        try {
            if (intent.getIntExtra("target", 0) == Constants.NOTIFI_ACTION_MOMENT) {
                mNavigationView.setCheckedItem(R.id.menu_magzine);
                MagFragment fragment = findFragment(MagFragment.class);
                if (fragment == null) {
                    popTo(WxFragment.class, false, () -> start(new MagFragment()));
                } else {
                    fragment.onResume();
                    start(fragment, SupportFragment.SINGLETASK);
                }
            } else if (intent.getIntExtra("target", 0) == Constants.NOTIFI_ACTION_NOVEL_UPDATE) {
                mNavigationView.setCheckedItem(R.id.menu_novel);

                NovelFragment fragment = findFragment(NovelFragment.class);
                if (fragment == null) {
                    popTo(WxFragment.class, false, () -> start(new NovelFragment()));
                } else {
                    start(fragment, SupportFragment.SINGLETASK);
                    fragment.onResume();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        checkNotify(intent);
    }

    private void checkPermission() {
        String READ_EXTERNAL_STORAGE = "android.permission.READ_EXTERNAL_STORAGE";
        String WRITE_EXTERNAL_STORAGE = "android.permission.WRITE_EXTERNAL_STORAGE";
        if (checkCallingOrSelfPermission(READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && checkCallingOrSelfPermission(WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= 23) {
                requestPermissions(new String[]{
                        READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE
                }, 1);
                return;
            }
        }
    }

    private void checkUpdate() {
        BookRetrofit.getInstance().getSysApi().fetch("android").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsSubscriber<ApiResult<Version>>(HomeActivity.this, false) {
                    @Override
                    protected void onError(ApiException ex) {

                    }

                    @Override
                    protected void onPermissionError(ApiException ex) {

                    }

                    @Override
                    public void onNext(ApiResult<Version> data) {
                        Timber.i("get version info " + data + " cur version code:" + AppUtils.getVersionCode(BookBoxApplication.getInstance()));
                        if (data.getData() != null && data.getData().getVersion() > AppUtils.getVersionCode(BookBoxApplication.getInstance())) {
                            runOnUiThread(() -> UpdateDialog.show(HomeActivity.this, data.getData().getContent().replace(":", "\n").replace(":", "\n"), data.getData().getUrl()));
                        }

                    }
                });
        BookBoxApplication.getInstance()
                .getPreferenceUtils().setValue(Constants.LAST_CHECK, System.currentTimeMillis());
//        }
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return super.onCreateFragmentAnimator();
        // 设置横向(和安卓4.x动画相同)
        //        return new DefaultHorizontalAnimator();
        // 设置自定义动画
        //        return new FragmentAnimator(enter,exit,popEnter,popExit);
    }

    private void initView() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);
        mNavigationView.setCheckedItem(R.id.menu_topic);

        FrameLayout llNavHeader = (FrameLayout) mNavigationView.getHeaderView(0);
        mTvName = llNavHeader.findViewById(R.id.tvNick);
        mImgNav = llNavHeader.findViewById(R.id.ivMenuUserAvatar);
        updateNavData();
        llNavHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.closeDrawer(GravityCompat.START);

                mDrawer.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (AccountLogic.getInstance().isLogin()) {
                            gotoMine();
                        } else {
                            goToLogin();
                        }
                    }
                }, 250);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }


    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        ShareSDK.stopSDK(this);
        Fresco.shutDown();
        AccountLogic.getInstance().exit();
    }

    long firstTime = 0;

    @Override
    public void onBackPressedSupport() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            if (getTopFragment() instanceof WxFragment) {
                if (firstTime + 2000 > System.currentTimeMillis()) {
                    finish();
                    return;
                }
                ToastHelper.showShortTip(R.string.exit_tip);
                firstTime = System.currentTimeMillis();
            } else {
                if (getTopFragment() instanceof BaseMainFragment) {
                    mNavigationView.setCheckedItem(R.id.menu_weixin);
                }
                super.onBackPressedSupport();
            }
        }
    }


    /**
     * 打开抽屉
     */
    @Override
    public void onOpenDrawer() {
        if (!mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.openDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onNavigationItemSelected(final MenuItem item) {
        mDrawer.closeDrawer(GravityCompat.START);
        mDrawer.postDelayed(new Runnable() {
            @Override
            public void run() {
                int id = item.getItemId();
                SupportFragment fragment;
                switch (id) {
                    case R.id.menu_topic:
                        fragment = findFragment(TopicFragment.class);
                        if (fragment == null) {
                            popTo(WxFragment.class, false, new Runnable() {
                                @Override
                                public void run() {
                                    start(TopicFragment.newInstance());
                                }
                            });
                        } else {
                            // 如果已经在栈内,则以SingleTask模式start
                            start(fragment, SupportFragment.SINGLETASK);
                        }
                        break;
                    case R.id.menu_weixin:
                        fragment = findFragment(WxFragment.class);
                        start(fragment, SupportFragment.SINGLETASK);
                        break;
                    case R.id.menu_novel:
                        fragment = findFragment(NovelFragment.class);
                        if (fragment == null) {
                            popTo(WxFragment.class, false, new Runnable() {
                                @Override
                                public void run() {
                                    start(NovelFragment.newInstance());
                                }
                            });
                        } else {
                            start(fragment, SupportFragment.SINGLETASK);
                        }
                        break;
                    case R.id.menu_site:
                        fragment = findFragment(SiteFragment.class);
                        if (fragment == null) {
                            popTo(WxFragment.class, false, new Runnable() {
                                @Override
                                public void run() {
                                    start(SiteFragment.newInstance());
                                }
                            });
                        } else {
                            // 如果已经在栈内,则以SingleTask模式start
                            start(fragment, SupportFragment.SINGLETASK);
                        }
                        break;
                    case R.id.menu_magzine:
                        fragment = findFragment(MagFragment.class);
                        if (fragment == null) {
                            popTo(WxFragment.class, false, new Runnable() {
                                @Override
                                public void run() {
                                    start(new MagFragment());
                                }
                            });
                        } else {
                            start(fragment, SupportFragment.SINGLETASK);
                        }
                        break;
                    case R.id.menu_mine:
                        if (AccountLogic.getInstance().isLogin()) {
                            gotoMine();
                        } else {
                            goToLogin();
                        }
                        break;
                    case R.id.menu_settings:
                        fragment = findFragment(SettingFragment.class);
                        if (fragment == null) {
                            popTo(WxFragment.class, false, new Runnable() {
                                @Override
                                public void run() {
                                    start(new SettingFragment());
                                }
                            });
                        } else {
                            start(fragment, SupportFragment.SINGLETASK);
                        }
                        break;
                    case R.id.menu_about:
                        fragment = findFragment(AboutFragment.class);
                        if (fragment == null) {
                            popTo(WxFragment.class, false, new Runnable() {
                                @Override
                                public void run() {
                                    start(new AboutFragment());
                                }
                            });
                        } else {
                            start(fragment, SupportFragment.SINGLETASK);
                        }
                        break;
                }
            }
        }, 250);

        return true;
    }

    private void goToLogin() {
        start(LoginFragment.newInstance());
    }

    private void gotoMine() {
        MineFragment fragment = findFragment(MineFragment.class);
        if (fragment == null) {
            popTo(WxFragment.class, false, () -> start(MineFragment.newInstance()));
        } else {
            start(fragment, SupportFragment.SINGLETASK);
        }
        mNavigationView.setCheckedItem(R.id.menu_mine);
    }

    @Subscribe
    public void onToLogin(ToLoginEvent event) {
        goToLogin();
    }

    @Subscribe
    public void onLoginSuccess(LoginEvent event) {
        Timber.i("receive onLoginSuccess");
        updateNavData();
//        CommonHelper.getTopics(this);
//        CommonHelper.getUserTopics(this);
//        CommonHelper.getSites(this);
//        CommonHelper.getUserSites(this);
        User user = AccountLogic.getInstance().getNowUser();
        if (!user.getIsBasicSet() && Constants.isFirstLaunch()) {
            Constants.setFirstLaunch(false);
            user.setBasicSet(true);
            LoginData data = AccountLogic.getInstance().getLoginData();
            data.setUser(user);
            AccountLogic.getInstance().setLoginData(data);
            AccountApi accountApi = BookRetrofit.getInstance().getAccountApi();
            Map<String, Object> info = new HashMap<>();
            info.put("isBasicSet", user.getIsBasicSet());
            accountApi.update(info, AccountLogic.getInstance().getToken())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(initObserver(HomeActivity.this));
            start(ChooseTopicFragment.newInstance(ChooseTopicFragment.ACTION_SET));
        }
    }

    private Subscriber<ApiResult<User>> initObserver(FragmentActivity activity) {
        return new AbsSubscriber<ApiResult<User>>(activity, false) {
            @Override
            protected void onError(ApiException ex) {

            }

            @Override
            protected void onPermissionError(ApiException ex) {

            }

            @Override
            public void onNext(ApiResult<User> data) {
                Timber.i("start init site and topic");
            }
        };
    }


    @Subscribe
    public void onLogout(LogoutEvent event) {
        updateNavData();
        mNavigationView.setCheckedItem(R.id.menu_topic);
    }

    private void updateNavData() {
        User user = AccountLogic.getInstance().getNowUser();
        if (user != null) {
            Timber.d("cur login user:" + user);
            mTvName.setText(user.getName());
            mImgNav.setImageURI(Uri.parse("https://second.imdao.cn" + user.getAvatar()));
        } else {
            mTvName.setText(R.string.register_login);
            mImgNav.setImageURI(ActivityUtil.getAppResourceUri(R.drawable.menu_user, getPackageName()));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

}
