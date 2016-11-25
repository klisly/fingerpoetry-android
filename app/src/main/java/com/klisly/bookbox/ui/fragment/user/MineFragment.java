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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.drawee.view.SimpleDraweeView;
import com.klisly.bookbox.BusProvider;
import com.klisly.bookbox.R;
import com.klisly.bookbox.api.AccountApi;
import com.klisly.bookbox.api.BookRetrofit;
import com.klisly.bookbox.domain.ApiResult;
import com.klisly.bookbox.logic.AccountLogic;
import com.klisly.bookbox.model.User;
import com.klisly.bookbox.ottoevent.LogoutEvent;
import com.klisly.bookbox.ottoevent.ProfileUpdateEvent;
import com.klisly.bookbox.subscriber.AbsSubscriber;
import com.klisly.bookbox.subscriber.ApiException;
import com.klisly.bookbox.ui.base.BaseMainFragment;
import com.klisly.bookbox.ui.fragment.account.LoginFragment;
import com.klisly.common.LogUtils;
import com.squareup.otto.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
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
    @Bind(R.id.rl_toread)
    RelativeLayout rlToread;
    @Bind(R.id.rl_collect)
    RelativeLayout rlCollect;
    @Bind(R.id.rl_recent_read)
    RelativeLayout rlRecentRead;
    private User user;
    private MaterialDialog exitDialog;
    private AccountApi accountApi = BookRetrofit.getInstance().getAccountApi();

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
            updateData();
        }
        updateUserData();
        return view;
    }

    private void updateUserData() {
        accountApi.profile(AccountLogic.getInstance().getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsSubscriber<ApiResult<User>>(getActivity(), false) {
                    @Override
                    protected void onError(ApiException ex) {
                        Timber.i("onError");
                    }

                    @Override
                    protected void onPermissionError(ApiException ex) {
                        Timber.i("onPermissionError");
                    }

                    @Override
                    public void onNext(ApiResult<User> data) {
                        Timber.i("update result:" + data.getData());
                        AccountLogic.getInstance().updateProfile(data.getData());
                        updateData();
                    }
                });
    }

    @Subscribe
    public void onUpdate(ProfileUpdateEvent event) {
        updateData();
    }

    private void updateData() {
        user = AccountLogic.getInstance().getNowUser();
        tvName.setText(user.getName());
        ivAvatar.setImageURI(Uri.parse(BookRetrofit.BASE_URL + user.getAvatar()));
        StringBuffer desc = new StringBuffer();
        desc.append(getString(R.string.readed)).append(" ").append(user.getReadCount())
                .append("  ").append(getString(R.string.toreaded)).append(" ").append(user.getToReadCount())
                .append("  ").append(getString(R.string.collected)).append(" ").append(user.getToReadCount());
        tvDesc.setText(desc);
    }

    @OnClick(R.id.iv_next)
    void onNext() {
        Timber.i("modify user data");
        start(ModifyFragment.newInstance());
    }

    @OnClick(R.id.rl_collect)
    void onNextCollect() {
        Timber.i("see user collect");
        start(UserRelateFragment.newInstance(UserRelateFragment.TYPE_COLLECT));
    }
    @OnClick(R.id.rl_recent_read)
    void onNextRead() {
        Timber.i("see user recent_read");
        start(UserRelateFragment.newInstance(UserRelateFragment.TYPE_READED));
    }
    @OnClick(R.id.rl_toread)
    void onNextToRead() {
        Timber.i("see user toread");
        start(UserRelateFragment.newInstance(UserRelateFragment.TYPE_TOREAD));
    }

    private void initView() {
        mToolbar.setTitle(getString(R.string.mine_center));
        initToolbarNav(mToolbar, true);
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
