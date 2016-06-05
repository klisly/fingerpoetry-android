package com.klisly.bookbox.ui.fragment.account;

import com.klisly.bookbox.BusProvider;
import com.klisly.bookbox.R;
import com.klisly.bookbox.api.AccountApi;
import com.klisly.bookbox.api.BookRetrofit;
import com.klisly.bookbox.domain.ApiResult;
import com.klisly.bookbox.domain.LoginData;
import com.klisly.bookbox.logic.AccountLogic;
import com.klisly.bookbox.ottoevent.LoginEvent;
import com.klisly.bookbox.subscriber.AbsSubscriber;
import com.klisly.bookbox.subscriber.ApiException;
import com.klisly.bookbox.ui.fragment.BaseBackFragment;
import com.material.widget.PaperButton;

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
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class LoginFragment extends BaseBackFragment {
    private EditText mEtAccount, mEtPassword;
    private PaperButton mBtnLogin;
    private PaperButton mBtnForget;

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

        initView(view);
        return view;
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
