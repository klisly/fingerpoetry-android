package com.klisly.bookbox.ui.activity;

import android.net.Uri;
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
import com.klisly.bookbox.BusProvider;
import com.klisly.bookbox.Constants;
import com.klisly.bookbox.R;
import com.klisly.bookbox.api.AccountApi;
import com.klisly.bookbox.api.BookRetrofit;
import com.klisly.bookbox.domain.ApiResult;
import com.klisly.bookbox.domain.LoginData;
import com.klisly.bookbox.logic.AccountLogic;
import com.klisly.bookbox.model.User;
import com.klisly.bookbox.ottoevent.LoginEvent;
import com.klisly.bookbox.ottoevent.LogoutEvent;
import com.klisly.bookbox.subscriber.AbsSubscriber;
import com.klisly.bookbox.subscriber.ApiException;
import com.klisly.bookbox.ui.base.BaseMainFragment;
import com.klisly.bookbox.ui.fragment.HomeFragment;
import com.klisly.bookbox.ui.fragment.account.LoginFragment;
import com.klisly.bookbox.ui.fragment.magzine.MagFragment;
import com.klisly.bookbox.ui.fragment.site.SiteFragment;
import com.klisly.bookbox.ui.fragment.topic.ChooseTopicFragment;
import com.klisly.bookbox.ui.fragment.topic.TopicFragment;
import com.klisly.bookbox.ui.fragment.user.MineFragment;
import com.klisly.bookbox.utils.ActivityUtil;
import com.klisly.bookbox.utils.ToastHelper;
import com.squareup.otto.Subscribe;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
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
        Fresco.initialize(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (savedInstanceState == null) {
            start(HomeFragment.newInstance());
        }

        initView();
    }

    @Override
    protected FragmentAnimator onCreateFragmentAnimator() {
        // 设置默认Fragment动画  默认竖向(和安卓5.0以上的动画相同)
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
        mNavigationView.setCheckedItem(R.id.menu_home);

        FrameLayout llNavHeader = (FrameLayout) mNavigationView.getHeaderView(0);
        mTvName = (TextView) llNavHeader.findViewById(R.id.tvNick);
        mImgNav = (SimpleDraweeView) llNavHeader.findViewById(R.id.ivMenuUserAvatar);
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
                            gotoLogin();
                        }
                    }
                }, 250);
            }
        });


    }

    @Override
    public int setContainerId() {
        return R.id.fl_container;
    }


    @Override
    protected void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                gotoChooseTopics();
            }
        }, 250);
    }

    private void gotoChooseTopics() {
        start(ChooseTopicFragment.newInstance());
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
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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
                if (id == R.id.menu_home) {

                    HomeFragment fragment = findFragment(HomeFragment.class);
                    //                    Bundle newBundle = new Bundle();
                    //                    newBundle.putString("from", "主页-->来自:" + topFragment.getClass()
                    // .getSimpleName());
                    //                    fragment.putNewBundle(newBundle);
                    start(fragment, SupportFragment.SINGLETASK);
                } else if (id == R.id.menu_topic) {
                    TopicFragment fragment = findFragment(TopicFragment.class);
                    if (fragment == null) {
                        popTo(HomeFragment.class, false, new Runnable() {
                            @Override
                            public void run() {
                                start(TopicFragment.newInstance());
                            }
                        });
                    } else {
                        // 如果已经在栈内,则以SingleTask模式start
                        start(fragment, SupportFragment.SINGLETASK);
                    }
                } else if (id == R.id.menu_site) {
                    SiteFragment fragment = findFragment(SiteFragment.class);
                    if (fragment == null) {
                        popTo(HomeFragment.class, false, new Runnable() {
                            @Override
                            public void run() {
                                start(SiteFragment.newInstance());
                            }
                        });
                    } else {
                        // 如果已经在栈内,则以SingleTask模式start
                        start(fragment, SupportFragment.SINGLETASK);
                    }
                } else if (id == R.id.menu_magzine) {
                    MagFragment fragment = findFragment(MagFragment.class);
                    if (fragment == null) {
                        popTo(HomeFragment.class, false, new Runnable() {
                            @Override
                            public void run() {
                                start(MagFragment.newInstance());
                            }
                        });
                    } else {
                        // 如果已经在栈内,则以SingleTask模式start
                        //                        start(fragment, SupportFragment.SINGLETASK);
                        start(fragment, SupportFragment.SINGLETASK);
                    }
                } else if (id == R.id.menu_mine) {

                    MineFragment fragment = findFragment(MineFragment.class);
                    if (fragment == null) {
                        popTo(HomeFragment.class, false, new Runnable() {
                            @Override
                            public void run() {
                                start(MineFragment.newInstance());
                            }
                        });
                    } else {
                        // 如果已经在栈内,则以SingleTask模式start
                        //                        start(fragment, SupportFragment.SINGLETASK);
                        start(fragment, SupportFragment.SINGLETASK);
                    }
                }
            }
        }, 250);

        return true;
    }

    private void gotoLogin() {
        start(LoginFragment.newInstance());
    }

    private void gotoMine() {
        start(MineFragment.newInstance());
        mNavigationView.setCheckedItem(R.id.menu_mine);
    }

    @Subscribe
    public void onLoginSuccess(LoginEvent event) {
        updateNavData();
        User user = AccountLogic.getInstance().getNowUser();
        if(!user.isBasicSet() && Constants.isFirstLaunch()){
            Constants.setFirstLaunch(false);
            user.setBasicSet(true);
            LoginData data = AccountLogic.getInstance().getLoginData();
            data.setUser(user);
            AccountLogic.getInstance().setLoginData(data);
            AccountApi accountApi = BookRetrofit.getInstance().getAccountApi();
            Map<String, Object> info = new HashMap<>();
            info.put("isBasicSet", user.isBasicSet());
            accountApi.update(info, user.getToken())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(initObserver(HomeActivity.this));
        }
    }

    private Subscriber<ApiResult<LoginData>> initObserver(FragmentActivity activity) {
        return new AbsSubscriber<ApiResult<LoginData>>(activity, false) {
            @Override
            protected void onError(ApiException ex) {

            }

            @Override
            protected void onPermissionError(ApiException ex) {

            }

            @Override
            public void onNext(ApiResult<LoginData> data) {
                ToastHelper.showLoneTip("start init site and topic");
            }
        };
    }


    @Subscribe
    public void onLogout(LogoutEvent event) {
        updateNavData();
        mNavigationView.setCheckedItem(R.id.menu_home);
    }

    private void updateNavData() {
        User user = AccountLogic.getInstance().getNowUser();
        if ( user != null) {
            Timber.d("cur login user:"+user);
            mTvName.setText(user.getName());
            mImgNav.setImageURI(Uri.parse(BookRetrofit.BASE_URL +user.getAvatar()));
        } else {
            mTvName.setText(R.string.register_login);
            mImgNav.setImageURI(ActivityUtil.getAppResourceUri(R.drawable.menu_user, getPackageName()));
        }
    }

    //    @Override
    //    public void onLockDrawLayout(boolean lock) {
    //        if (lock) {
    //            mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    //        } else {
    //            mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    //        }
    //    }
}
