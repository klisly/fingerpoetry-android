package com.klisly.bookbox.ui.fragment.novel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.klisly.bookbox.R;
import com.klisly.bookbox.adapter.ChapterViewHolder;
import com.klisly.bookbox.api.BookRetrofit;
import com.klisly.bookbox.api.NovelApi;
import com.klisly.bookbox.domain.ApiResult;
import com.klisly.bookbox.logic.AccountLogic;
import com.klisly.bookbox.logic.NovelLogic;
import com.klisly.bookbox.model.BaseModel;
import com.klisly.bookbox.model.Chapter;
import com.klisly.bookbox.model.User2Novel;
import com.klisly.bookbox.subscriber.AbsSubscriber;
import com.klisly.bookbox.subscriber.ApiException;
import com.klisly.bookbox.ui.base.BaseBackFragment;
import com.klisly.bookbox.utils.ActivityUtil;
import com.klisly.bookbox.utils.ToastHelper;
import com.material.widget.CircularProgress;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

import static com.klisly.bookbox.R.id.recyclerView;

public class ChapterListFragment<T extends BaseModel> extends BaseBackFragment implements RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    private static final String ARG_CONTENT_ID = "arg_novel_id";
    private static final String ARG_CONTENT_TITLE = "arg_novel_title";

    @Bind(recyclerView)
    EasyRecyclerView mRecy;
    @Bind(R.id.tvTip)
    TextView mTvTip;
    @Bind(R.id.cprogress)
    CircularProgress mProgress;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    private int page = 0;
    private int pageSize = 50;
    //    private PagerContentAdapter mAdapter;
    private NovelApi novelApi = BookRetrofit.getInstance().getNovelApi();
    private String id;
    private String title;
    private RecyclerArrayAdapter adapter;
    public static ChapterListFragment newInstance(User2Novel user2Novel) {
        ChapterListFragment fragment = new ChapterListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CONTENT_ID, user2Novel.getNid());
        args.putString(ARG_CONTENT_TITLE, user2Novel.getTitle());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            title = args.getString(ARG_CONTENT_TITLE, "");
            id = args.getString(ARG_CONTENT_ID, "");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_magzine, container, false);
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
        mToolbar.setTitle(title);
        initToolbarNav(mToolbar, true);

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
        adapter.setMore(R.layout.view_more, this);
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
    }

    private void queryData(Chapter article) {
        start(ChapterFragment.newInstance(article));
    }

    @Override
    protected void initToolbarMenu(Toolbar toolbar) {
        toolbar.inflateMenu(R.menu.menu_chapter_list);
        if (NovelLogic.getInstance().isFocused(id)) {
            toolbar.getMenu().getItem(0).setTitle(getString(R.string.canclefocus));
        } else {
            toolbar.getMenu().getItem(0).setTitle(getString(R.string.focus));
        }
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_cancle_focus:
                        if (NovelLogic.getInstance().isFocused(id)) {
                            novelApi.unsubscribe(id,
                                    AccountLogic.getInstance().getToken())
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new AbsSubscriber<ApiResult<User2Novel>>(getActivity(), false) {
                                        @Override
                                        protected void onError(ApiException ex) {
                                            Timber.i("onError");
                                        }

                                        @Override
                                        protected void onPermissionError(ApiException ex) {
                                            Timber.i("onPermissionError");
                                        }

                                        @Override
                                        public void onNext(ApiResult<User2Novel> data) {
                                            Timber.i("unSubscribe result:" + data.getData());
                                            NovelLogic.getInstance().unSubscribe(id);
                                            ToastHelper.showShortTip("成功取消");
                                            toolbar.getMenu().getItem(0).setTitle(getString(R.string.focus));
                                        }
                                    });
                        } else {
                            novelApi.subscribeById(id,
                                    AccountLogic.getInstance().getToken())
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new AbsSubscriber<ApiResult<User2Novel>>(getActivity(), false) {
                                        @Override
                                        protected void onError(ApiException ex) {
                                            Timber.i("onError");
                                        }

                                        @Override
                                        protected void onPermissionError(ApiException ex) {
                                            Timber.i("onPermissionError");
                                        }

                                        @Override
                                        public void onNext(ApiResult<User2Novel> data) {
                                            NovelLogic.getInstance().subscribe(data.getData());
                                            ToastHelper.showShortTip("关注成功");
                                            toolbar.getMenu().getItem(0).setTitle(getString(R.string.canclefocus));
                                        }
                                    });
                        }



                        break;
                }
                return true;
            }
        });
    }
    private void loadNew() {
        Map<String, String> params = new HashMap<>();
        page++;
        params.put("page", String.valueOf(page));
        params.put("pageSize", String.valueOf(pageSize));
        novelApi.listchapters(id, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsSubscriber<ApiResult<List<Chapter>>>(getActivity(), false) {
                    @Override
                    protected void onError(ApiException ex) {
                        page--;
                        adapter.addAll(Collections.EMPTY_LIST);
                        onFinish();
                    }

                    @Override
                    protected void onPermissionError(ApiException ex) {
                        page--;
                        adapter.addAll(Collections.EMPTY_LIST);
                        onFinish();
                    }

                    @Override
                    public void onNext(ApiResult<List<Chapter>> res) {
                        onFinish();
                        if (queryType == 1) {
                            adapter.clear();
                            adapter.addAll(res.getData());
                        } else {
                            adapter.addAll(res.getData());
                        }
                    }
                });
    }

    private void onFinish() {
        if (getActivity() == null || getActivity().isFinishing()) {
            return;
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mProgress != null) {
                    mProgress.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    int queryType = -1; // 1 refresh 2 loadmore

    @Override
    public void onRefresh() {
        queryType = 1;
        page = 0;
        loadNew();
    }

    @Override
    public void onLoadMore() {
        queryType = 2;
        loadNew();
    }
}

