package com.klisly.bookbox.ui.fragment.magzine;

import android.app.NotificationManager;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
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
import com.jude.easyrecyclerview.decoration.StickyHeaderDecoration;
import com.klisly.bookbox.BookBoxApplication;
import com.klisly.bookbox.Constants;
import com.klisly.bookbox.R;
import com.klisly.bookbox.adapter.ArticleViewHolder;
import com.klisly.bookbox.adapter.StickyHeaderAdapter;
import com.klisly.bookbox.api.ArticleApi;
import com.klisly.bookbox.api.BookRetrofit;
import com.klisly.bookbox.domain.ApiResult;
import com.klisly.bookbox.model.Article;
import com.klisly.bookbox.model.BaseModel;
import com.klisly.bookbox.model.Magazine;
import com.klisly.bookbox.subscriber.AbsSubscriber;
import com.klisly.bookbox.subscriber.ApiException;
import com.klisly.bookbox.ui.DetailFragment;
import com.klisly.bookbox.ui.base.BaseMainFragment;
import com.klisly.bookbox.utils.ActivityUtil;
import com.klisly.bookbox.utils.ToastHelper;
import com.klisly.bookbox.utils.TopToastHelper;
import com.material.widget.CircularProgress;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.klisly.bookbox.R.id.recyclerView;

public class MagFragment<T extends BaseModel> extends BaseMainFragment implements RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, Toolbar.OnMenuItemClickListener {

    @Bind(recyclerView)
    EasyRecyclerView mRecy;
    @Bind(R.id.tvTip)
    TextView mTvTip;
    @Bind(R.id.cprogress)
    CircularProgress mProgress;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    private int page = 0;
    private int pageSize = 6;
    //    private PagerContentAdapter mAdapter;
    private ArticleApi articleApi = BookRetrofit.getInstance().getArticleApi();
    private boolean needToast = false;
    private RecyclerArrayAdapter adapter;
    private StickyHeaderAdapter headerAdapter;
    NotificationManager manager;

    public static MagFragment newInstance() {
        return new MagFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager = (NotificationManager) BookBoxApplication.getInstance().getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_magzine, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        manager.cancel(Constants.NOTIFI_ID_MOMENT);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void initView(View view) {
        mToolbar.setTitle(R.string.magzine);
        initToolbarNav(mToolbar, true);
        mToolbar.setOnMenuItemClickListener(this);

        mRecy.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerDecoration itemDecoration = new DividerDecoration(getResources().getColor(R.color.background_black_alpha_20), ActivityUtil.dip2px(getActivity(), 0.8f), 0, 0);
        itemDecoration.setDrawLastItem(false);
        mRecy.addItemDecoration(itemDecoration);

        mRecy.setAdapterWithProgress(adapter = new RecyclerArrayAdapter(getActivity()) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new ArticleViewHolder(parent);
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
                if(position < 0  || adapter.getItem(position) == null){
                    return;
                }
                queryData((Article) adapter.getItem(position));
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
        // StickyHeader
        StickyHeaderDecoration decoration = new StickyHeaderDecoration(headerAdapter = new StickyHeaderAdapter(getContext()));
        decoration.setIncludeHeader(false);
        mRecy.addItemDecoration(decoration);

        mRecy.setRefreshListener(this);
        onRefresh();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_more:
                final PopupMenu popupMenu = new PopupMenu(_mActivity, mToolbar, GravityCompat.END);
                popupMenu.inflate(R.menu.menu_novel_pop);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_as_home:
                                BookBoxApplication.getInstance().getPreferenceUtils().setValue(Constants.HOME_FRAG, Constants.FRAG_MAGZINE);
                                ToastHelper.showShortTip(R.string.success_as_home);
                                break;
                        }
                        popupMenu.dismiss();
                        return true;
                    }
                });
                popupMenu.show();
                break;
            default:
                break;
        }
        return true;
    }

    private void queryData(Article article) {
        start(DetailFragment.newInstance(article));
    }

    private void loadNew() {
        Map<String, String> params = new HashMap<>();
        page++;
        params.put("page", String.valueOf(page));
        params.put("pageSize", String.valueOf(pageSize));
        Timber.i("start load page,params:" + params.toString());
        articleApi.listMagazine(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsSubscriber<ApiResult<List<Magazine>>>(getActivity(), false) {
                    @Override
                    protected void onError(ApiException ex) {
                        page--;
                        onFinish();
                    }

                    @Override
                    protected void onPermissionError(ApiException ex) {
                        page--;
                        onFinish();
                    }

                    @Override
                    public void onNext(ApiResult<List<Magazine>> res) {
                        onFinish();
                        if (needToast) {
                            if (getActivity() == null || getActivity().isFinishing()) {
                                return;
                            }
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (mTvTip == null) {
                                        return;
                                    }
                                    if (res.getData().size() > 0) {
                                        TopToastHelper.showTip(mTvTip, getString(R.string.load_success), TopToastHelper.DURATION_SHORT);
                                    } else {
                                        TopToastHelper.showTip(mTvTip, getString(R.string.load_empty), TopToastHelper.DURATION_SHORT);
                                    }
                                }
                            });
                        }

                        if (queryType == 1) {
                            adapter.clear();
                            headerAdapter.getHeaders().clear();
                            List list = new ArrayList();
                            for (Magazine magazine : res.getData()) {
                                headerAdapter.getHeaders().add(magazine.getNo());
                                list.addAll(magazine.getArticles());
                            }
                            adapter.addAll(list);
                        } else {
                            List list = new ArrayList();
                            for (Magazine magazine : res.getData()) {
                                headerAdapter.getHeaders().add(magazine.getNo());
                                list.addAll(magazine.getArticles());
                            }
                            adapter.addAll(list);
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

