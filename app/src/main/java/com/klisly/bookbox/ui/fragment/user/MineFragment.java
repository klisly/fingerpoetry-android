package com.klisly.bookbox.ui.fragment.user;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.drawee.view.SimpleDraweeView;
import com.klisly.bookbox.BusProvider;
import com.klisly.bookbox.R;
import com.klisly.bookbox.api.BookRetrofit;
import com.klisly.bookbox.logic.AccountLogic;
import com.klisly.bookbox.model.User;
import com.klisly.bookbox.ottoevent.LogoutEvent;
import com.klisly.bookbox.ui.base.BaseMainFragment;
import com.klisly.bookbox.ui.fragment.account.LoginFragment;
import com.klisly.common.LogUtils;
import com.library.StickHeaderViewPager;
import com.library.tab.SlidingTabLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.yokeyword.fragmentation.anim.DefaultNoAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import timber.log.Timber;

public class MineFragment extends BaseMainFragment {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.iv_avatar)
    SimpleDraweeView ivAvatar;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_desc)
    TextView tvDesc;
    @Bind(R.id.iv_next)
    ImageView ivNext;
    @Bind(R.id.stl_stick)
    SlidingTabLayout stlStick;
    @Bind(R.id.shvp_content)
    StickHeaderViewPager shvpContent;
    private User user;
    private MaterialDialog exitDialog;

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this, view);
        initView();
        if (!AccountLogic.getInstance().isLogin()) {
            start(LoginFragment.newInstance());
        } else {
            user = AccountLogic.getInstance().getNowUser();
            updateData();
        }

        return view;
    }

    private void updateData() {
        tvName.setText(user.getName());
        ivAvatar.setImageURI(Uri.parse(BookRetrofit.BASE_URL + user.getAvatar()));
        StringBuffer desc = new StringBuffer();
        desc.append(getString(R.string.readed)).append(" ").append(user.getReadCount())
                .append("  ").append(getString(R.string.toreaded)).append(" ").append(user.getToReadCount())
                .append("  ").append(getString(R.string.collected)).append(" ").append(user.getToReadCount());
        tvDesc.setText(desc);
    }

    @OnClick(R.id.iv_next)
    void onNext(){
        Timber.i("modify user data");
        start(ModifyFragment.newInstance());
    }

    @Override
    protected FragmentAnimator onCreateFragmentAnimation() {
        // 默认不改变
        //         return super.onCreateFragmentAnimation();
        // 在进入和离开时 设定无动画
        return new DefaultNoAnimator();
    }

    private void initView() {
        mToolbar.setTitle(R.string.mine_center);
        initToolbarNav(mToolbar, true);
        StickHeaderViewPager.StickHeaderViewPagerBuilder.stickTo(shvpContent)
                .setFragmentManager(getChildFragmentManager())
                .addScrollFragments(
                        ItemFragment.newInstance(getString(R.string.recentread)),
                        ItemFragment.newInstance(getString(R.string.toreaded)),
                        ItemFragment.newInstance(getString(R.string.collected)))
                .notifyData();

        stlStick.setViewPager(shvpContent.getViewPager());
    }

    @Override
    protected void initToolbarMenu(Toolbar toolbar) {
        toolbar.inflateMenu(R.menu.menu_mine);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_logout) {

                    showExitDialog();
                }
                return true;
            }
        });
    }

    private void showExitDialog() {
        if (exitDialog == null) {
            exitDialog = new MaterialDialog.Builder(getActivity())
                    .title(R.string.tip)
                    .content(R.string.confirm_exit)
                    .positiveText(R.string.confirm)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            LogUtils.i(TAG, "onPositive");
                            AccountLogic.getInstance().logout();
                            BusProvider.getInstance().post(new LogoutEvent());
                            pop();
                        }
                    })
                    .negativeText(R.string.cancle)
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            LogUtils.i(TAG, "onNegtive");
                        }
                    })
                    .build();

        }
        exitDialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        if (exitDialog != null) {
            exitDialog.dismiss();
            exitDialog = null;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * 类似于 Activity的 onNewIntent()
     */
    @Override
    protected void onNewBundle(Bundle args) {
        super.onNewBundle(args);
    }
}
