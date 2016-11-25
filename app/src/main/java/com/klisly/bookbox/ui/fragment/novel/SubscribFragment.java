package com.klisly.bookbox.ui.fragment.novel;

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
import com.klisly.bookbox.R;
import com.klisly.bookbox.adapter.NovelViewHolder;
import com.klisly.bookbox.api.BookRetrofit;
import com.klisly.bookbox.api.NovelApi;
import com.klisly.bookbox.domain.ApiResult;
import com.klisly.bookbox.listener.OnDataChangeListener;
import com.klisly.bookbox.logic.AccountLogic;
import com.klisly.bookbox.logic.NovelLogic;
import com.klisly.bookbox.model.BaseModel;
import com.klisly.bookbox.model.User2Novel;
import com.klisly.bookbox.subscriber.AbsSubscriber;
import com.klisly.bookbox.subscriber.ApiException;
import com.klisly.bookbox.ui.base.BaseFragment;
import com.klisly.bookbox.utils.ActivityUtil;
import com.klisly.bookbox.utils.TopToastHelper;
import com.material.widget.CircularProgress;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SubscribFragment<T extends BaseModel> extends BaseFragment implements RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    public static final String ARG_FROM = "arg_from";
    public static final String ARG_CHANNEL = "arg_channel";
    public static final String ARG_NAME = "arg_name";

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
        final View view = inflater.inflate(R.layout.fragment_subscribe, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void initView(View view) {

        mRecy.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerDecoration itemDecoration = new DividerDecoration(getResources().getColor(R.color.background_black_alpha_20), ActivityUtil.dip2px(getActivity(),0.8f),  0, 0);
        itemDecoration.setDrawLastItem(false);
        mRecy.addItemDecoration(itemDecoration);

        mRecy.setAdapterWithProgress(adapter = new RecyclerArrayAdapter(getActivity()) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new NovelViewHolder(parent);
            }
        });
        adapter.setNoMore(R.layout.view_nomore, new RecyclerArrayAdapter.OnNoMoreListener() {
            @Override
            public void onNoMoreShow() {
                adapter.resumeMore();
            }

            @Override
            public void onNoMoreClick() {
                adapter.resumeMore();
            }
        });
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                queryData((User2Novel) adapter.getItem(position));
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
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.clear();
                        adapter.addAll(NovelLogic.getInstance().getSubscribes());
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }


    private void queryData(User2Novel entity) {
        if (entity != null) {
            ((BaseFragment) getParentFragment()).start(ChapterListFragment.newInstance(entity));
        }
    }

    private void loadNew() {
        if(!AccountLogic.getInstance().isLogin()){
            TopToastHelper.showTip(mTvTip, "登录后为你获取订阅的小说", TopToastHelper.DURATION_SHORT);
            return;
        }
        novelApi.novels(AccountLogic.getInstance().getUserId(), AccountLogic.getInstance().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsSubscriber<ApiResult<List<User2Novel>>>(getActivity(), false) {
                    @Override
                    protected void onError(ApiException ex) {
                        adapter.clear();
                    }

                    @Override
                    protected void onPermissionError(ApiException ex) {
                        adapter.clear();
                    }

                    @Override
                    public void onNext(ApiResult<List<User2Novel>> res) {
                        NovelLogic.getInstance().updateSubscribes(res.getData());
                        adapter.clear();
                        adapter.addAll(NovelLogic.getInstance().getSubscribes());
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void onRefresh() {
        loadNew();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        NovelLogic.getInstance().unRegisterListener(this);
    }

    @Override
    public void onLoadMore() {

    }

}
