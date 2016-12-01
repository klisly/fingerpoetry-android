package com.klisly.bookbox.ui.fragment.novel;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.klisly.bookbox.BookBoxApplication;
import com.klisly.bookbox.BusProvider;
import com.klisly.bookbox.Constants;
import com.klisly.bookbox.R;
import com.klisly.bookbox.logic.AccountLogic;
import com.klisly.bookbox.logic.SiteLogic;
import com.klisly.bookbox.model.BaseModel;
import com.klisly.bookbox.model.Chapter;
import com.klisly.bookbox.model.User2Novel;
import com.klisly.bookbox.ottoevent.ToLoginEvent;
import com.klisly.bookbox.ui.base.BaseMainFragment;
import com.klisly.bookbox.utils.ToastHelper;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NovelFragment extends BaseMainFragment implements Toolbar.OnMenuItemClickListener {
    @Bind(R.id.tab_layout)
    TabLayout mTabLayout;
    @Bind(R.id.viewPager)
    ViewPager mViewPager;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    public static NovelFragment newInstance() {
        return new NovelFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        SiteLogic.getInstance().unRegisterListener(this);
        ButterKnife.unbind(this);
    }

    private void initView() {
        mToolbar.setTitle(R.string.update);
        initToolbarNav(mToolbar, true);
        mToolbar.setOnMenuItemClickListener(this);
        mToolbar.findViewById(R.id.search).setVisibility(View.VISIBLE);
        mToolbar.findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(SearchFragment.newInstance());
            }
        });
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        PagerFragmentAdapter adapter = new PagerFragmentAdapter(getChildFragmentManager());
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mTabLayout.setupWithViewPager(mViewPager);
    }

    /**
     * 类似于 Activity的 onNewIntent()
     */
    @Override
    protected void onNewBundle(Bundle args) {
        super.onNewBundle(args);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_more:
                final PopupMenu popupMenu = new PopupMenu(_mActivity, mToolbar, GravityCompat.END);
                popupMenu.inflate(R.menu.menu_novel_pop);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_manage_site:
                                if(!AccountLogic.getInstance().isLogin()){
                                    BusProvider.getInstance().post(new ToLoginEvent());
                                } else {
                                    start(ChooseNovelFragment.newInstance(ChooseNovelFragment.ACTION_MANAGE));
                                }
                                break;
                            case R.id.action_as_home:
                                BookBoxApplication.getInstance().getPreferenceUtils().setValue(Constants.HOME_FRAG, Constants.FRAG_NOVEL);
                                ToastHelper.showShortTip(R.string.success_as_home);
                                break;
                        }
                        popupMenu.dismiss();
                        return true;
                    }
                });
                popupMenu.show();
                break;
            default:
                break;
        }
        return true;
    }


    public class PagerFragmentAdapter<T extends BaseModel> extends FragmentPagerAdapter {
        public PagerFragmentAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
           if(position == 0){
               return new UpdateFragment<Chapter>();
           } else {
               return new SubscribFragment<User2Novel>();
           }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String name = "";
            if(position == 0){
                name = "最近更新";
            } else  if(position == 1){
                name = "我的小说";
            }
            return name;
        }
    }

}
