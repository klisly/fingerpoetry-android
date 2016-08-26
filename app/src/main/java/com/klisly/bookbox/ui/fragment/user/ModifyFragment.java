package com.klisly.bookbox.ui.fragment.user;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.drawee.view.SimpleDraweeView;
import com.klisly.bookbox.Constants;
import com.klisly.bookbox.R;
import com.klisly.bookbox.api.AccountApi;
import com.klisly.bookbox.api.BookRetrofit;
import com.klisly.bookbox.domain.ApiResult;
import com.klisly.bookbox.logic.AccountLogic;
import com.klisly.bookbox.model.User;
import com.klisly.bookbox.subscriber.AbsSubscriber;
import com.klisly.bookbox.subscriber.ApiException;
import com.klisly.bookbox.ui.base.BaseBackFragment;
import com.klisly.bookbox.ui.fragment.account.LoginFragment;
import com.klisly.bookbox.ui.fragment.account.ResetPassFragment;
import com.klisly.bookbox.utils.ToastHelper;
import com.klisly.common.LogUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.yokeyword.fragmentation.anim.DefaultNoAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class ModifyFragment extends BaseBackFragment {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.iv_avatar)
    SimpleDraweeView ivAvatar;
    @Bind(R.id.tv_name)
    TextView tvName;
    private User user;
    private MaterialDialog modifyNickDialog;
    private MaterialDialog modifyGenderDialog;
    private AccountApi accountApi = BookRetrofit.getInstance().getAccountApi();
    public static ModifyFragment newInstance() {
        return new ModifyFragment();
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
        View view = inflater.inflate(R.layout.fragment_modify_profile, container, false);
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
    }

    @OnClick(R.id.rl_change_passwd)
    void onModifyPass() {
        Timber.i("modify pass");
        if(Constants.PLATFORM_LOCAL.equals(user.getPlatform())){
            start(ResetPassFragment.newInstance());
        } else {
            ToastHelper.showShortTip(getString(R.string.third_unset_pass));
        }
    }
    String newAvatar = "";
    @OnClick(R.id.rl_change_avatar)
    void onModifyAvatar() {
        Timber.i("modify avatar");
//        if (CameraHelper.showAlertIfNotSupportCamera(getActivity())) return;
//
//        // 组件选项配置
//        // @see-http://tusdk.com/docs/android/api/org/lasque/tusdk/impl/components/TuAvatarComponent.html
//        // 组件选项配置
//        // @see-http://tusdk.com/docs/android/api/org/lasque/tusdk/impl/components/TuAvatarComponentOption.html
//        // component.componentOption()
//
//        // @see-http://tusdk.com/docs/android/api/org/lasque/tusdk/impl/components/album/TuAlbumListOption.html
//        // component.componentOption().albumListOption()
//
//        // @see-http://tusdk.com/docs/android/api/org/lasque/tusdk/impl/components/album/TuPhotoListOption.html
//        // component.componentOption().photoListOption()
//
//        // @see-http://tusdk.com/docs/android/api/org/lasque/tusdk/impl/components/camera/TuCameraOption.html
//        // component.componentOption().cameraOption()
//
//        // @see-http://tusdk.com/docs/android/api/org/lasque/tusdk/impl/components/edit/TuEditTurnAndCutOption.html
//        // component.componentOption().editTurnAndCutOption()
//
//        // 需要显示的滤镜名称列表 (如果为空将显示所有自定义滤镜, 可选)
//        // String[] filters = {};
//        // component.componentOption().cameraOption().setFilterGroup(Arrays.asList(filters));
//        TuAvatarComponent component = TuSdkGeeV1.avatarCommponent(getActivity(), new TuSdkComponent.TuSdkComponentDelegate() {
//            @Override
//            public void onComponentFinished(TuSdkResult result, Error error, TuFragment lastFragment) {
//                try {
//                    ivAvatar.setImageURI(Uri.fromFile(result.imageFile));
//                    // TODO 上传, 再保存
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        // 是否保存到相册
//        component.componentOption().editTurnAndCutOption().setSaveToAlbum(false);
//        // 是否保存到临时文件
//        component.componentOption().editTurnAndCutOption().setSaveToTemp(true);
//        component.componentOption().editTurnAndCutOption().setEnableOnlineFilter(true);
//        component.setAutoDismissWhenCompleted(true)
//                .showComponent();

    }

    String newNick = "";

    @OnClick(R.id.rl_change_nick)
    void onModifyNick() {
        Timber.i("modify nick");
        if (modifyNickDialog == null) {
            modifyNickDialog = new MaterialDialog.Builder(getActivity())
                    .title(R.string.change_name_tip)
                    .inputRange(2, 15, getResources().getColor(R.color.text_red))
                    .input(getString(R.string.modify_avatar_tip), user.getName(), new MaterialDialog.InputCallback() {
                        @Override
                        public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                            newNick = input.toString();
                            Timber.i(("new nick:" + newNick));
                        }
                    })
                    .positiveText(R.string.confirm)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            LogUtils.i(TAG, "onPositive");
                            updateCount = 0;
                            updateProfile(TYPE_NICK);
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

        } else {
            modifyNickDialog.getInputEditText().setText(user.getName());
        }
        modifyNickDialog.show();
    }
    private static final int TYPE_AVATAR = 1;
    private static final int TYPE_NICK = 2;
    int updateCount = 0;

    private void updateProfile(int type) {
        updateCount = (updateCount + 1 )% 2;
        Map<String, Object> infos = new HashMap<>();
        if(type - TYPE_NICK == 0){
            infos.put("name", newNick);
        }
        System.out.println("type:"+type+" info:"+infos);
        accountApi.update(infos,
                AccountLogic.getInstance().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsSubscriber<ApiResult<User>>(getActivity(), false) {
                    @Override
                    protected void onError(ApiException ex) {
                        Timber.i("onError");
                        ToastHelper.showLongTip(ex.getMessage());
                    }

                    @Override
                    protected void onPermissionError(ApiException ex) {
                        Timber.i("onPermissionError");
                        ToastHelper.showLongTip(ex.getMessage());
                    }

                    @Override
                    public void onNext(ApiResult<User> data) {
                        Timber.i("update result:" + data.getData());
                        user.setName(newNick);
                        tvName.setText(newNick);
                        AccountLogic.getInstance().updateProfile(data.getData());
                        if(updateCount == 1){
                            updateProfile(type);
                        }
                    }
                });
    }

    @Override
    protected FragmentAnimator onCreateFragmentAnimation() {
        // 默认不改变
        //         return super.onCreateFragmentAnimation();
        // 在进入和离开时 设定无动画
        return new DefaultNoAnimator();
    }

    private void initView() {
        mToolbar.setTitle(R.string.modify_profile);
        initToolbarNav(mToolbar, true, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        if (modifyNickDialog != null) {
            modifyNickDialog.dismiss();
            modifyNickDialog = null;
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
