package com.klisly.bookbox.ui.fragment;

import java.util.List;

import com.klisly.bookbox.Constants;
import com.klisly.bookbox.R;
import com.klisly.bookbox.adapter.ChannelFragmentAdapter;
import com.klisly.bookbox.logic.ChannelLogic;
import com.klisly.bookbox.model.Channel;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import me.yokeyword.fragmentation.anim.DefaultNoAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class HomeFragment extends BaseMainFragment implements Toolbar.OnMenuItemClickListener {

    private List<Channel> channels;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        channels = ChannelLogic.getInstance().getChannelsByType(Constants.HOME);
        initView(view);

        return view;
    }

    @Override
    protected FragmentAnimator onCreateFragmentAnimation() {
        // 默认不改变
        // return super.onCreateFragmentAnimation();
        // 在进入和离开时 设定无动画
        return new DefaultNoAnimator();
    }

    private void initView(View view) {
        Toolbar mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        TabLayout mTabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        ViewPager mViewPager = (ViewPager) view.findViewById(R.id.viewPager);

        mToolbar.setTitle(R.string.homepage);
        initToolbarNav(mToolbar);
        if(channels != null){
            for(Channel channel:channels){
                mTabLayout.addTab(mTabLayout.newTab().setText(channel.getTitle()));
            }
        }
        mViewPager.setAdapter(new ChannelFragmentAdapter(getChildFragmentManager(), channels));
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
//            case R.id.action_hierarchy:
//                _mActivity.showFragmentStackHierarchyView();
//                _mActivity.logFragmentStackHierarchy(TAG);
//                break;
//            case R.id.action_anim:
//                final PopupMenu popupMenu = new PopupMenu(_mActivity, mToolbar, GravityCompat.END);
//                popupMenu.inflate(R.menu.home_pop);
//                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem item) {
//                        switch (item.getItemId()) {
//                            case R.id.action_anim_veritical:
//                                _mActivity.setFragmentAnimator(new DefaultVerticalAnimator());
//                                Toast.makeText(_mActivity, "设置全局动画成功! 竖向", Toast.LENGTH_SHORT).show();
//                                break;
//                            case R.id.action_anim_horizontal:
//                                _mActivity.setFragmentAnimator(new DefaultHorizontalAnimator());
//                                Toast.makeText(_mActivity, "设置全局动画成功! 横向", Toast.LENGTH_SHORT).show();
//                                break;
//                            case R.id.action_anim_none:
//                                _mActivity.setFragmentAnimator(new DefaultNoAnimator());
//                                Toast.makeText(_mActivity, "设置全局动画成功! 无", Toast.LENGTH_SHORT).show();
//                                break;
//                        }
//                        popupMenu.dismiss();
//                        return true;
//                    }
//                });
//                popupMenu.show();
//                break;
        }
        return true;
    }
}
