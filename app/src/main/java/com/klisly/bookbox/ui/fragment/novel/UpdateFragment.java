package com.klisly.bookbox.ui.fragment.novel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.klisly.bookbox.Constants;
import com.klisly.bookbox.R;
import com.klisly.bookbox.adapter.ChapterViewHolder;
import com.klisly.bookbox.api.BookRetrofit;
import com.klisly.bookbox.api.NovelApi;
import com.klisly.bookbox.domain.ApiResult;
import com.klisly.bookbox.listener.OnDataChangeListener;
import com.klisly.bookbox.logic.AccountLogic;
import com.klisly.bookbox.logic.NovelLogic;
import com.klisly.bookbox.model.BaseModel;
import com.klisly.bookbox.model.Chapter;
import com.klisly.bookbox.subscriber.AbsSubscriber;
import com.klisly.bookbox.subscriber.ApiException;
import com.klisly.bookbox.ui.base.BaseFragment;
import com.klisly.bookbox.utils.ActivityUtil;
import com.klisly.bookbox.utils.TopToastHelper;
import com.klisly.common.StringUtils;
import com.material.widget.CircularProgress;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UpdateFragment<T extends BaseModel> extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.recyclerView)
    EasyRecyclerView mRecy;
    @Bind(R.id.tvTip)
    TextView mTvTip;
    @Bind(R.id.cprogress)
    CircularProgress mProgress;
    private NovelApi novelApi = BookRetrofit.getInstance().getNovelApi();
    private RecyclerArrayAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_pager, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        checkNotify();
        return view;
    }

    private void checkNotify() {
        Intent intent = getActivity().getIntent();
        if (intent.getIntExtra("target", 0) == Constants.NOTIFI_ACTION_NOVEL_UPDATE) {
            String targetId = intent.getStringExtra("cid");
            intent.putExtra("target", 0);
            intent.putExtra("cid", "");
            if (StringUtils.isNotEmpty(targetId) && AccountLogic.getInstance().isLogin()) {
                novelApi.fetch(targetId, AccountLogic.getInstance().getUserId())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new AbsSubscriber<ApiResult<Chapter>>(getActivity(), false) {
                            @Override
                            protected void onError(ApiException ex) {
                            }

                            @Override
                            protected void onPermissionError(ApiException ex) {
                            }

                            @Override
                            public void onNext(ApiResult<Chapter> res) {
                                queryData(res.getData());
                            }
                        });
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void initView(View view) {
        mProgress.setVisibility(View.GONE);
        mRecy.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerDecoration itemDecoration = new DividerDecoration(getResources().getColor(R.color.background_black_alpha_20), ActivityUtil.dip2px(getActivity(), 0.8f), 0, 0);
        itemDecoration.setDrawLastItem(false);
        mRecy.addItemDecoration(itemDecoration);

        mRecy.setAdapterWithProgress(adapter = new RecyclerArrayAdapter(getActivity()) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new ChapterViewHolder(parent);
            }
        });
        adapter.setOnItemLongClickListener(new RecyclerArrayAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(int position) {
                return true;
            }
        });
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                queryData((Chapter) adapter.getItem(position));
            }
        });
        adapter.setError(R.layout.view_error, new RecyclerArrayAdapter.OnErrorListener() {
            @Override
            public void onErrorShow() {
                adapter.resumeMore();
            }

            @Override
            public void onErrorClick() {
                adapter.resumeMore();
            }
        });
        mRecy.setRefreshListener(this);
        onRefresh();
        NovelLogic.getInstance().registerListener(this, new OnDataChangeListener() {
            @Override
            public void onDataChange() {
                if (getActivity() != null && !getActivity().isFinishing()) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            onRefresh();
                        }
                    });
                }
            }
        });
    }

    private void queryData(Chapter article) {
        ((BaseFragment) getParentFragment()).start(ChapterFragment.newInstance(article));
    }

    private void loadNew() {
        if (!AccountLogic.getInstance().isLogin()) {
            TopToastHelper.showTip(mTvTip, "登录后为你获取小说的最新文章", TopToastHelper.DURATION_SHORT);
            adapter.clear();
        } else {
            novelApi.dailyUpdates(AccountLogic.getInstance().getUserId(), AccountLogic.getInstance().getToken())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new AbsSubscriber<ApiResult<List<Chapter>>>(getActivity(), false) {
                        @Override
                        protected void onError(ApiException ex) {
                            if (getActivity() == null || getActivity().isFinishing()) {
                                return;
                            }
                            if (mRecy != null) {
                                mRecy.showError();
                            }
                        }

                        @Override
                        protected void onPermissionError(ApiException ex) {
                            if (getActivity() == null || getActivity().isFinishing()) {
                                return;
                            }
                            if (mRecy != null) {
                                mRecy.showError();
                            }
                        }

                        @Override
                        public void onNext(ApiResult<List<Chapter>> res) {
                            if (getActivity() == null || getActivity().isFinishing()) {
                                return;
                            }
                            if (mRecy != null) {
                                if (queryType == 1) {
                                    adapter.clear();
                                    adapter.addAll(res.getData());
                                } else {
                                    adapter.addAll(res.getData());
                                }
                            }
                        }
                    });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        checkNotify();
    }

    int queryType = -1;

    @Override
    public void onRefresh() {
        queryType = 1;
        loadNew();
    }
}
