package com.klisly.bookbox.ui.activity;

import com.klisly.bookbox.BusProvider;
import com.klisly.bookbox.R;
import com.klisly.bookbox.logic.AccountLogic;
import com.klisly.bookbox.ottoevent.LoginEvent;
import com.klisly.bookbox.ottoevent.LogoutEvent;
import com.klisly.bookbox.ui.fragment.BaseMainFragment;
import com.klisly.bookbox.ui.fragment.HomeFragment;
import com.klisly.bookbox.ui.fragment.MagFragment;
import com.klisly.bookbox.ui.fragment.MineFragment;
import com.klisly.bookbox.ui.fragment.SiteFragment;
import com.klisly.bookbox.ui.fragment.TopicFragment;
import com.klisly.bookbox.ui.fragment.account.LoginFragment;
import com.squareup.otto.Subscribe;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class HomeActivity extends SupportActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        BaseMainFragment.OnFragmentOpenDrawerListener {
    public static final String TAG = MainActivity.class.getSimpleName();
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawer;
    @Bind(R.id.vNavigation)
    NavigationView mNavigationView;
    private TextView mTvName;   // NavigationView上的名字
    private ImageView mImgNav;  // NavigationView上的头像

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        mImgNav = (ImageView) llNavHeader.findViewById(R.id.ivMenuUserAvatar);
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
                            goLogin();
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

            //            Fragment topFragment = getTopFragment();
            //            // 主页的Fragment
            //            if (topFragment instanceof DiscoverFragment || topFragment instanceof ShopFragment) {
            //                mNavigationView.setCheckedItem(R.id.nav_home);
            //            }
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
                //                else if (id == R.id.nav_login) {
                //                    goLogin();
                //                } else if (id == R.id.nav_swipe_back) {
                //                    startActivity(new Intent(MainActivity.this, SwipeBackSampleActivity.class));
                //                } else if (id == R.id.nav_swipe_back_f) {
                //                    start(SwipeBackSampleFragment.newInstance());
                //                }
            }
        }, 250);

        return true;
    }

    private void goLogin() {
        start(LoginFragment.newInstance());
    }

    private void gotoMine() {
        start(MineFragment.newInstance());
        mNavigationView.setCheckedItem(R.id.menu_mine);
    }

    @Subscribe public void onLoginSuccess(LoginEvent event) {
        updateNavData();
    }

    @Subscribe
    public void onLogout(LogoutEvent event) {
        updateNavData();
    }

    private void updateNavData() {
        if (AccountLogic.getInstance().getNowUser() != null) {
            mTvName.setText(AccountLogic.getInstance().getNowUser().getName());
        } else {
            mTvName.setText(R.string.register_login);
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
