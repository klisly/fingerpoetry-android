package com.klisly.bookbox.ui.fragment.wx;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.klisly.bookbox.BookBoxApplication;
import com.klisly.bookbox.BusProvider;
import com.klisly.bookbox.Constants;
import com.klisly.bookbox.R;
import com.klisly.bookbox.adapter.PagerFragmentAdapter;
import com.klisly.bookbox.logic.AccountLogic;
import com.klisly.bookbox.logic.TopicLogic;
import com.klisly.bookbox.model.ChannleEntity;
import com.klisly.bookbox.ottoevent.ToLoginEvent;
import com.klisly.bookbox.ottoevent.UpdateWxChannelEvent;
import com.klisly.bookbox.ui.base.BaseMainFragment;
import com.klisly.bookbox.utils.ToastHelper;
import com.squareup.otto.Subscribe;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class WxFragment extends BaseMainFragment implements Toolbar.OnMenuItemClickListener {

    @Bind(R.id.tab_layout)
    TabLayout mTabLayout;
    @Bind(R.id.viewPager)
    ViewPager mViewPager;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    PagerFragmentAdapter adapter;
    @Bind(R.id.ivEdit)
    ImageView ivEdit;
    private List channels;
    public static WxFragment newInstance() {
        return new WxFragment();
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
        if(AccountLogic.getInstance().getNowUser() != null){
            channels = AccountLogic.getInstance().getNowUser().getWxChannles();
        } else {
            channels = ChannleEntity.loadWxDefault();
        }
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

        ivEdit.setVisibility(View.VISIBLE);

        adapter = new PagerFragmentAdapter(getChildFragmentManager(), channels);
        mViewPager.setOffscreenPageLimit(3);
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

    @Subscribe
    public void onUpdateChannel(UpdateWxChannelEvent event) {
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void initToolbarMenu(Toolbar toolbar) {
        mToolbar.inflateMenu(R.menu.menu_main_pop);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_recom_setting:
                ToastHelper.showShortTip(R.string.recom_setting);
                break;
            case R.id.action_as_home:
                BookBoxApplication.getInstance().getPreferenceUtils().setValue(Constants.HOME_FRAG, Constants.FRAG_WX);
                ToastHelper.showShortTip(R.string.success_as_home);
                break;
            case R.id.action_manage_topic:
                manageChannel();
                break;
            case R.id.action_reget:
                ToastHelper.showShortTip(R.string.reget);
                break;
            default:
                break;
        }
        return true;
    }

    private void manageChannel() {
        if (!AccountLogic.getInstance().isLogin()) {
            BusProvider.getInstance().post(new ToLoginEvent());
        } else {
            start(ChooseChannelFragment.newInstance());
        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @OnClick(R.id.ivEdit)
    void editChannel(){
        manageChannel();
    }
    /**
     * 类似于 Activity的 onNewIntent()
     */
    @Override
    protected void onNewBundle(Bundle args) {
        super.onNewBundle(args);
    }
}
