package com.klisly.bookbox.ui.fragment.wx;

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

import com.klisly.bookbox.BookBoxApplication;
import com.klisly.bookbox.BusProvider;
import com.klisly.bookbox.Constants;
import com.klisly.bookbox.R;
import com.klisly.bookbox.adapter.PagerFragmentAdapter;
import com.klisly.bookbox.logic.AccountLogic;
import com.klisly.bookbox.logic.TopicLogic;
import com.klisly.bookbox.model.Topic;
import com.klisly.bookbox.ottoevent.ToLoginEvent;
import com.klisly.bookbox.ui.base.BaseMainFragment;
import com.klisly.bookbox.ui.fragment.home.ChooseTopicFragment;
import com.klisly.bookbox.utils.ToastHelper;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

public class WxFragment extends BaseMainFragment implements Toolbar.OnMenuItemClickListener {

    @Bind(R.id.tab_layout)
    TabLayout mTabLayout;
    @Bind(R.id.viewPager)
    ViewPager mViewPager;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    PagerFragmentAdapter adapter;
    public static WxFragment newInstance() {
        return new WxFragment();
    }
    private int selectIndex = 0;
    private Topic selectTopic = null;
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
        TopicLogic.getInstance().unRegisterListener(this);
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    private void initView() {
        mToolbar.setTitle(R.string.wechat);
        initToolbarNav(mToolbar);
        mToolbar.setOnMenuItemClickListener(this);
        adapter = new PagerFragmentAdapter(getChildFragmentManager(),
                TopicLogic.getInstance().getOpenFocuses());
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(adapter);
        TopicLogic.getInstance().registerListener(this, () -> {
            if (getActivity() == null || getActivity().isFinishing()) {
                return;
            }
            getActivity().runOnUiThread(() -> {
                adapter.notifyDataSetChanged();
                int newIndex = adapter.getList().indexOf(selectTopic);
                Timber.i("on topic change selectIndex:"+selectIndex+" newIndex:"+newIndex);
                if(newIndex == -1){
                    if(adapter.getList().size() > selectIndex){
                        mViewPager.setCurrentItem(selectIndex);
                    } else {
                        mViewPager.setCurrentItem(adapter.getList().size()-1);
                    }
                } else if(newIndex != selectIndex){
                    mViewPager.setCurrentItem(newIndex);
                }
            });
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectTopic = (Topic) adapter.getList().get(position);
                selectIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mTabLayout.setupWithViewPager(mViewPager);
        selectTopic = (Topic) adapter.getList().get(0);
    }
    @Override
    public void onResume() {
        super.onResume();
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
                final PopupMenu popupMenu = new PopupMenu(_mActivity,
                        mToolbar, GravityCompat.END);
                popupMenu.inflate(R.menu.menu_main_pop);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_recom_setting:
                                ToastHelper.showShortTip(R.string.recom_setting);
                                break;
                            case R.id.action_as_home:
                                BookBoxApplication.getInstance().getPreferenceUtils().setValue(Constants.HOME_FRAG, Constants.FRAG_TOPIC);
                                ToastHelper.showShortTip(R.string.success_as_home);
                                break;
                            case R.id.action_manage_topic:
                                if (!AccountLogic.getInstance().isLogin()) {
                                    BusProvider.getInstance().post(new ToLoginEvent());
                                } else {
                                    start(ChooseTopicFragment.newInstance(ChooseTopicFragment.ACTION_MANAGE));
                                }

                                break;
                            case R.id.action_reget:
                                ToastHelper.showShortTip(R.string.reget);
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
        }
        return true;
    }
}
