package com.klisly.bookbox.ui.fragment.account;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

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
import com.klisly.bookbox.utils.ToastHelper;
import com.klisly.common.StringUtils;
import com.material.widget.PaperButton;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class LoginFragment extends BaseBackFragment {
    private EditText mEtAccount, mEtPassword;
    private PaperButton mBtnLogin;
    private PaperButton mBtnForget;
    Platform qqplatform = ShareSDK.getPlatform(QQ.NAME);
    Platform weiboplatform = ShareSDK.getPlatform(SinaWeibo.NAME);

    private AccountApi accountApi = BookRetrofit.getInstance().getAccountApi();

    public static LoginFragment newInstance() {

        Bundle args = new Bundle();

        LoginFragment fragment = new LoginFragment();
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this,view);
        initView(view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private Subscriber<ApiResult<LoginData>> initObserver(FragmentActivity activity) {
        return new AbsSubscriber<ApiResult<LoginData>>(activity) {
            @Override
            protected void onError(ApiException ex) {
                Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                Timber.e(ex.getMessage());
            }

            @Override
            protected void onPermissionError(ApiException ex) {
            }

            @Override
            public void onNext(ApiResult<LoginData> data) {
                ToastHelper.showShortTip(R.string.login_success);
                AccountLogic.getInstance().setLoginData(data.getData());
                BusProvider.getInstance().post(new LoginEvent());
                pop();
            }
        };

    }

    @Override
    protected void initToolbarMenu(Toolbar toolbar) {
        toolbar.inflateMenu(R.menu.menu_login);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_register:
                        start(RegisterFragment.newInstance());
                        break;
                }
                return true;
            }
        });
    }

    private void initView(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mEtAccount = (EditText) view.findViewById(R.id.et_account);
        mEtPassword = (EditText) view.findViewById(R.id.et_password);
        mBtnLogin = (PaperButton) view.findViewById(R.id.btn_login);
        mBtnForget = (PaperButton) view.findViewById(R.id.btn_forget);

        toolbar.setTitle(R.string.login);
        initToolbarNav(toolbar);

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strAccount = mEtAccount.getText().toString();
                String strPassword = mEtPassword.getText().toString();
                if (TextUtils.isEmpty(strAccount.trim())) {
                    Toast.makeText(_mActivity, "用户名不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(strPassword.trim())) {
                    Toast.makeText(_mActivity, "密码不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }
                accountApi.login(strAccount, strPassword)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(initObserver(getActivity()));
                hideSoftInput();
            }
        });

        mBtnForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(ResetPassFragment.newInstance());
            }
        });
    }

    @OnClick(R.id.login_button_qq_login)
    void loginByQQ(){
        qqplatform.removeAccount();
        qqplatform.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Timber.i("onComplete:"+hashMap);
                        String name = (String)hashMap.get("nickname");
                        String gender = (String)hashMap.get("gender");
                        if("男".equals(gender)){
                            gender = "male";
                        } else {
                            gender = "female";
                        }
                        String figureurl_qq_2 = (String) hashMap.get("figureurl_qq_2");
                        String figureurl_qq_1 = (String) hashMap.get("figureurl_qq_1");
                        String figureurl_2 = (String) hashMap.get("figureurl_2");
                        String figureurl_1 = (String) hashMap.get("figureurl_1");
                        String avatar = "";
                        String openId = "";
                        try {
                            if(StringUtils.isNotEmpty(figureurl_qq_2)){
                                avatar = figureurl_qq_2;
                            } else if(StringUtils.isNotEmpty(figureurl_qq_1)){
                                avatar = figureurl_qq_1;
                            } else if(StringUtils.isNotEmpty(figureurl_2)){
                                avatar = figureurl_2;
                            } else if(StringUtils.isNotEmpty(figureurl_1)){
                                avatar = figureurl_1;
                            }

                            int index = avatar.indexOf(Constants.QQ_APP_ID);
                            if(index > 0){
                                openId = avatar.substring(index+Constants.QQ_APP_ID.length()+1);
                                openId = openId.substring(0, openId.indexOf("/"));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        HashMap<String, String> infos = new HashMap<String, String>();
                        infos.put("name", name);
                        infos.put("gender", gender);
                        infos.put("loginname",  "QQ_"+openId);
                        infos.put("passwd", openId);
                        infos.put("platform","QQ");
                        accountApi.register(infos)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(initObserver(getActivity()));
                    }
                });
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                Timber.e(throwable, "onError");
            }

            @Override
            public void onCancel(Platform platform, int i) {

            }
        });
        qqplatform.authorize();//单独授权
        qqplatform.showUser(null);//授权并获取用户信息
    }

    @OnClick(R.id.login_button_sina_login)
    void loginBySina(){
        weiboplatform.removeAccount();
//        weiboplatform.SSOSetting(true);
        weiboplatform.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                Timber.i("onComplete:"+hashMap);
//                String name = (String)hashMap.get("nickname");
//                String gender = (String)hashMap.get("gender");
//                if("男".equals(gender)){
//                    gender = "male";
//                } else {
//                    gender = "female";
//                }
//                String figureurl_qq_2 = (String) hashMap.get("figureurl_qq_2");
//                String figureurl_qq_1 = (String) hashMap.get("figureurl_qq_1");
//                String figureurl_2 = (String) hashMap.get("figureurl_2");
//                String figureurl_1 = (String) hashMap.get("figureurl_1");
//                String avatar = "";
//                String openId = "";
//                try {
//                    if(StringUtils.isNotEmpty(figureurl_qq_2)){
//                        avatar = figureurl_qq_2;
//                    } else if(StringUtils.isNotEmpty(figureurl_qq_1)){
//                        avatar = figureurl_qq_1;
//                    } else if(StringUtils.isNotEmpty(figureurl_2)){
//                        avatar = figureurl_2;
//                    } else if(StringUtils.isNotEmpty(figureurl_1)){
//                        avatar = figureurl_1;
//                    }
//
//                    int index = avatar.indexOf(Constants.QQ_APP_ID);
//                    if(index > 0){
//                        openId = avatar.substring(index+Constants.QQ_APP_ID.length()+1);
//                        openId = openId.substring(0, openId.indexOf("/"));
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                HashMap<String, String> infos = new HashMap<String, String>();
//                infos.put("name", name);
//                infos.put("gender", gender);
//                infos.put("loginname",  "QQ_"+openId);
//                infos.put("passwd", openId);
//                infos.put("platform","QQ");
//                accountApi.register(infos)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(initObserver(getActivity()));
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                Timber.e(throwable, "onError");
            }

            @Override
            public void onCancel(Platform platform, int i) {

            }
        });
        weiboplatform.authorize();//单独授权
        weiboplatform.showUser(null);//授权并获取用户信息

    }
    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            hideSoftInput();
        }
    }
}
