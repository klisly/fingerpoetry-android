package com.klisly.bookbox.ui.fragment.user;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;
import com.klisly.bookbox.BusProvider;
import com.klisly.bookbox.R;
import com.klisly.bookbox.adapter.PagerFragmentAdapter;
import com.klisly.bookbox.api.AccountApi;
import com.klisly.bookbox.api.BookRetrofit;
import com.klisly.bookbox.domain.ApiResult;
import com.klisly.bookbox.logic.AccountLogic;
import com.klisly.bookbox.model.ChannleEntity;
import com.klisly.bookbox.model.User;
import com.klisly.bookbox.ottoevent.LogoutEvent;
import com.klisly.bookbox.ottoevent.ProfileUpdateEvent;
import com.klisly.bookbox.subscriber.AbsSubscriber;
import com.klisly.bookbox.subscriber.ApiException;
import com.klisly.bookbox.ui.base.BaseMainFragment;
import com.klisly.bookbox.ui.fragment.account.LoginFragment;
import com.klisly.bookbox.utils.ActivityUtil;
import com.klisly.bookbox.utils.DateUtil;
import com.klisly.common.LogUtils;
import com.squareup.otto.Subscribe;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.fresco.processors.BlurPostprocessor;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class MineFragment extends BaseMainFragment {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.name)
    TextView tvName;
    @Bind(R.id.iv_avatar)
    SimpleDraweeView ivAvatar;
    @Bind(R.id.registerat)
    TextView registerAt;
    @Bind(R.id.bg)
    SimpleDraweeView ivBg;
    @Bind(R.id.desc)
    TextView tvSig;
    @Bind(R.id.vp)
    ViewPager viewPager;
    @Bind(R.id.tab)
    TabLayout tab;

    PagerFragmentAdapter adapter;
    private List<ChannleEntity> channels;

    private User user;
    private MaterialDialog exitDialog;
    private AccountApi accountApi = BookRetrofit.getInstance().getAccountApi();
    Postprocessor postprocessor;

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
        postprocessor = new BlurPostprocessor(getContext(), 25);
        if (!AccountLogic.getInstance().isLogin()) {
            start(LoginFragment.newInstance());
        } else {
            updateData();
        }
        return view;
    }

    @Override
    protected void onEnterAnimationEnd() {
        super.onEnterAnimationEnd();
        updateUserData();
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
        if(user == null){
            return;
        }
        tvName.setText(user.getName());
        String url = ActivityUtil.genPic(user.getId().hashCode());
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
                .setPostprocessor(postprocessor)
                .build();

        PipelineDraweeController controller =
                (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                        .setImageRequest(request)
                        .setOldController(ivBg.getController())
                        .build();
        ivBg.setController(controller);
        String avartar = "https://second.imdao.cn"+user.getAvatar();
        Timber.i("avatar:"+avartar);
        ivAvatar.setImageURI(Uri.parse(avartar));
        StringBuilder desc = new StringBuilder();
        desc.append(getString(R.string.readed)).append(" ")
                .append(user.getReadCount())
                .append("  ").append(getString(R.string.collected))
                .append(" ").append(user.getCollectCount())
                .append("  ").append(getString(R.string.share))
                .append(" ").append(user.getShareCount());
        tvSig.setText(desc);
        registerAt.setText(DateUtil.getFriendlyTimeSpanByNow(user.getCreateAt()));
    }

    private void initView() {
        mToolbar.setTitle(getString(R.string.mine_center));
        initToolbarNav(mToolbar, true);
        channels = ChannleEntity.loadMines();
        adapter = new PagerFragmentAdapter(getChildFragmentManager(), channels);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

        tab.setupWithViewPager(viewPager);
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

    @Override
    protected void onNewBundle(Bundle args) {
        super.onNewBundle(args);
    }
}
