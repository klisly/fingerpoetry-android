package com.klisly.bookbox.ui.fragment;

import java.util.List;

import com.klisly.bookbox.Constants;
import com.klisly.bookbox.R;
import com.klisly.bookbox.adapter.ChannelFragmentAdapter;
import com.klisly.bookbox.logic.ChannelLogic;
import com.klisly.bookbox.model.Channel;
import com.klisly.bookbox.utils.ToastHelper;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import butterknife.Bind;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.anim.DefaultNoAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class TopicFragment extends BaseMainFragment implements Toolbar.OnMenuItemClickListener {
    @Bind(R.id.tab_layout)
    TabLayout mTabLayout;
    @Bind(R.id.viewPager)
    ViewPager mViewPager;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    private List<Channel> channels;

    public static TopicFragment newInstance() {
        return new TopicFragment();
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
        channels = ChannelLogic.getInstance().getChannelsByType(Constants.TOPIC);
        initView();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    protected FragmentAnimator onCreateFragmentAnimation() {
        // 默认不改变
        //         return super.onCreateFragmentAnimation();
        // 在进入和离开时 设定无动画
        return new DefaultNoAnimator();
    }

    private void initView() {
        mToolbar.setTitle(R.string.topic);
        initToolbarNav(mToolbar);
        mToolbar.setOnMenuItemClickListener(this);
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
            case R.id.action_more:
                final PopupMenu popupMenu = new PopupMenu(_mActivity, mToolbar, GravityCompat.END);
                popupMenu.inflate(R.menu.menu_site_pop);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_find_topic:
                                ToastHelper.showShortTip(R.string.find_topic);
                                break;
                            case R.id.action_manage_topic:
                                ToastHelper.showShortTip(R.string.manage_topic);
                                break;
                            case R.id.action_sort_method:
                                ToastHelper.showShortTip(R.string.sort_method);
                                break;
                            case R.id.action_notify_setting:
                                ToastHelper.showShortTip(R.string.notify_setting);
                                break;
                            default:
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
}
