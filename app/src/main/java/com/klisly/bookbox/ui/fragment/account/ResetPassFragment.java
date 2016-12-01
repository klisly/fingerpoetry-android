/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package com.klisly.bookbox.ui.fragment.account;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.klisly.bookbox.BusProvider;
import com.klisly.bookbox.Constants;
import com.klisly.bookbox.R;
import com.klisly.bookbox.api.AccountApi;
import com.klisly.bookbox.api.BookRetrofit;
import com.klisly.bookbox.domain.ApiResult;
import com.klisly.bookbox.domain.LoginData;
import com.klisly.bookbox.logic.AccountLogic;
import com.klisly.bookbox.ottoevent.LoginEvent;
import com.klisly.bookbox.subscriber.AbsSubscriber;
import com.klisly.bookbox.subscriber.ApiException;
import com.klisly.bookbox.ui.base.BaseBackFragment;
import com.klisly.bookbox.ui.fragment.home.HomeFragment;
import com.klisly.bookbox.utils.ToastHelper;
import com.klisly.common.StringUtils;
import com.material.widget.PaperButton;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class ResetPassFragment extends BaseBackFragment {
    private static final String PRE_BRACKET = "(";
    private static final String TAIL_BRACKET = ")";
    private static final String SECOND = "s";
    private static final long INTERVAl = 1000;
    @Bind(R.id.et_account)
    EditText mEtAccount;
    @Bind(R.id.et_password)
    EditText mEtPassword;
    @Bind(R.id.et_code)
    EditText mEtCode;
    @Bind(R.id.et_password_confirm)
    EditText mEtPasswordConfirm;
    @Bind(R.id.btn_get_code)
    PaperButton mBtnGetCode;
    @Bind(R.id.btn_register)
    PaperButton mBtnRegister;
    private Handler handler = new Handler();
    private AccountApi accountApi = BookRetrofit.getInstance().getAccountApi();
    private int MAX_TIME = 60; // 6s
    private int count = 0;
    private boolean isTimeCount = false;
    private boolean isSubmit = false;
    private String preGetCode = "";
    private StringBuilder builder = new StringBuilder();
    private int showTip = Constants.INVALID;
    private Runnable timeCount = new Runnable() {
        @Override
        public void run() {
            if (count == 0) {
                isTimeCount = true;
            }
            builder = new StringBuilder();
            if (count < MAX_TIME) {
                builder.append(preGetCode)
                        .append(PRE_BRACKET)
                        .append(MAX_TIME - count)
                        .append(SECOND)
                        .append(TAIL_BRACKET);
                mBtnGetCode.setText(builder.toString());
                handler.postDelayed(timeCount, INTERVAl); // 一秒执行一次
            } else {
                isTimeCount = false;
                count = 0;
                builder.append(preGetCode);
                mBtnGetCode.setText(builder.toString());
            }
            count++;
        }
    };

    public static ResetPassFragment newInstance() {

        Bundle args = new Bundle();

        ResetPassFragment fragment = new ResetPassFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resetpasswd, container, false);
        ButterKnife.bind(this, view);
        preGetCode = getString(R.string.getcode);
        initView(view);
        initShareSDK();
        return view;
    }

    private void initShareSDK() {
        // 暂时支持中国
        SMSSDK.initSDK(getActivity(), Constants.MOB_APP_KEY, Constants.MOB_APP_SECRET);
        EventHandler eh = new EventHandler() {

            @Override
            public void afterEvent(int event, int result, Object data) {

                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        Timber.d("validate code success");
                        registerToServer();
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                        Timber.d("get code vericode success");
                        isTimeCount = true;
                        count = 0;
                        handler.postDelayed(timeCount, INTERVAl);
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表
                    }
                } else {
                    showTip = Constants.INVALID;
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码失败
                        showTip = R.string.code_invalid;
                        isSubmit = false;
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                        Timber.d("get code vericode fail");
                        isTimeCount = false;
                        showTip = R.string.getcode_fail;
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表
                    }
                    if (showTip != Constants.INVALID) {
                        if (getActivity() == null || getActivity().isFinishing()) {
                            return;
                        }
                        getActivity().runOnUiThread(() -> ToastHelper.showShortTip(showTip));
                    }
                }
            }
        };
        SMSSDK.registerEventHandler(eh); //注册短信回调
    }

    // 注册用户到服务器
    private void registerToServer() {
        if (getActivity() == null || getActivity().isFinishing()) {
            return;
        }
        getActivity().runOnUiThread(() -> {
                    String strAccount = mEtAccount.getText().toString().trim();
                    String strPassword = mEtPassword.getText().toString().trim();
                    accountApi.resetPass(strAccount, strPassword)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(initObserver(getActivity()));
                }
        );
    }

    private Subscriber<ApiResult<LoginData>> initObserver(FragmentActivity activity) {
        return new AbsSubscriber<ApiResult<LoginData>>(activity) {
            @Override
            protected void onError(ApiException ex) {
                ToastHelper.showShortTip(ex.getMessage());
                Timber.e(ex.getMessage());
                isSubmit = false;
            }

            @Override
            protected void onPermissionError(ApiException ex) {
                ToastHelper.showShortTip(ex.getMessage());
                isSubmit = false;
            }

            @Override
            public void onNext(ApiResult<LoginData> data) {
                isSubmit = false;
                ToastHelper.showShortTip(R.string.restpass_success);
                // 登录成功
                AccountLogic.getInstance().setLoginData(data.getData());
                BusProvider.getInstance().post(new LoginEvent());
                popTo(HomeFragment.class, false);
            }
        };

    }

    private void initView(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        showSoftInput(mEtAccount);

        toolbar.setTitle(R.string.reset_passwd);
        initToolbarNav(toolbar, false);

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strAccount = mEtAccount.getText().toString();
                String strCode = mEtCode.getText().toString();
                String strPassword = mEtPassword.getText().toString();
                String strPasswordConfirm = mEtPasswordConfirm.getText().toString();
                if (!checkPhoneValid(strAccount)) {
                    return;
                }
                if (!checkPassValid(strPassword, strPasswordConfirm)) {
                    return;
                }
                if (TextUtils.isEmpty(strCode.trim())) {
                    ToastHelper.showShortTip(R.string.validcode_null);
                    return;
                }
                if (isSubmit) {
                    return;
                }
                isSubmit = true;
                SMSSDK.submitVerificationCode(Constants.ZH_ZONE_CODE, strAccount, strCode);
                hideSoftInput();
            }
        });

        mBtnGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = mEtAccount.getText().toString().trim();
                if (!checkPhoneValid(phone) || isTimeCount) {
                    return;
                }
                isTimeCount = true;
                Timber.d("get code phone:" + phone);
                SMSSDK.getVerificationCode(Constants.ZH_ZONE_CODE, phone);

            }
        });
    }

    private boolean checkPassValid(String strPassword, String strPasswordConfirm) {
        if (TextUtils.isEmpty(strPassword.trim()) || TextUtils.isEmpty(strPasswordConfirm.trim())) {
            ToastHelper.showShortTip(R.string.password_null);
            return false;
        }
        if (strPassword.trim().length() < Constants.PASSWORD_MIN_LENGTH
                || strPasswordConfirm.trim().length() < Constants.PASSWORD_MIN_LENGTH) {
            ToastHelper.showShortTip(R.string.password_min_length);
            return false;
        }
        if (!strPassword.equals(strPasswordConfirm)) {
            ToastHelper.showShortTip(R.string.password_mismatch);
            return false;
        }
        return true;
    }

    private boolean checkPhoneValid(String strAccount) {
        if (TextUtils.isEmpty(strAccount.trim())) {
            ToastHelper.showShortTip(R.string.phone_null);
            return false;
        }
        if (!StringUtils.isMobile(strAccount.trim())) {
            ToastHelper.showShortTip(R.string.phone_invalid);
            return false;
        }
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        SMSSDK.unregisterAllEventHandler();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            hideSoftInput();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        handler.removeCallbacks(timeCount);
    }
}
