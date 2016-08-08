package com.klisly.bookbox.ui.fragment.site;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.klisly.bookbox.R;
import com.klisly.bookbox.api.BookRetrofit;
import com.klisly.bookbox.api.SiteApi;
import com.klisly.bookbox.domain.ApiResult;
import com.klisly.bookbox.model.Site;
import com.klisly.bookbox.subscriber.AbsSubscriber;
import com.klisly.bookbox.subscriber.ApiException;
import com.klisly.bookbox.ui.base.BaseBackFragment;
import com.material.widget.PaperButton;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class ChooseSiteFragment extends BaseBackFragment {
    private static final String ARG_NUMBER = "arg_number";
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.btn_next)
    PaperButton mBtnNext;
    @Bind(R.id.btn_enter_direct)
    PaperButton mBtnEnter;
    SiteApi siteApi = BookRetrofit.getInstance().getSiteApi();
    public static ChooseSiteFragment newInstance() {
        ChooseSiteFragment fragment = new ChooseSiteFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        siteApi.list()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsSubscriber<ApiResult<List<Site>>>(getActivity(), false) {
                    @Override
                    protected void onError(ApiException ex) {

                    }

                    @Override
                    protected void onPermissionError(ApiException ex) {

                    }

                    @Override
                    public void onNext(ApiResult<List<Site>> entities) {
                        Timber.d(entities.toString());
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        String title = getString(R.string.choose_site);
        mToolbar.setTitle(title);
        initToolbarNav(mToolbar, false, false);
        mBtnEnter.setVisibility(View.INVISIBLE);
        mBtnNext.setText(getString(R.string.finish));
        mBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
    }
}
