package com.klisly.bookbox.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.klisly.bookbox.Constants;
import com.klisly.bookbox.R;
import com.klisly.bookbox.adapter.PagerContentAdapter;
import com.klisly.bookbox.api.ArticleApi;
import com.klisly.bookbox.api.BookRetrofit;
import com.klisly.bookbox.domain.ApiResult;
import com.klisly.bookbox.domain.ArticleData;
import com.klisly.bookbox.listener.OnDataChangeListener;
import com.klisly.bookbox.listener.OnItemClickListener;
import com.klisly.bookbox.logic.AccountLogic;
import com.klisly.bookbox.logic.ArticleLogic;
import com.klisly.bookbox.model.Article;
import com.klisly.bookbox.model.BaseModel;
import com.klisly.bookbox.model.Site;
import com.klisly.bookbox.model.Topic;
import com.klisly.bookbox.subscriber.AbsSubscriber;
import com.klisly.bookbox.subscriber.ApiException;
import com.klisly.bookbox.ui.DetailFragment;
import com.klisly.bookbox.ui.base.BaseFragment;
import com.klisly.bookbox.utils.TopToastHelper;
import com.klisly.bookbox.widget.circlerefresh.CircleRefreshLayout;
import com.material.widget.CircularProgress;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class PagerChildFragment<T extends BaseModel> extends BaseFragment {
    public static final String ARG_FROM = "arg_from";
    public static final String ARG_CHANNEL = "arg_channel";
    public static final String ARG_NAME = "arg_name";

    @Bind(R.id.recy)
    RecyclerView mRecy;
    @Bind(R.id.refresh_layout)
    CircleRefreshLayout mRefreshLayout;
    @Bind(R.id.tvTip)
    TextView mTvTip;
    @Bind(R.id.cprogress)
    CircularProgress mProgress;
    private int page = 0;
    private int pageSize = 20;
    private int mFrom;
    private T mData;
    private PagerContentAdapter mAdapter;
    private ArticleApi articleApi = BookRetrofit.getInstance().getArticleApi();
    private String name;
    private boolean showToast = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mFrom = args.getInt(ARG_FROM);
            mData = (T) args.getSerializable(ARG_CHANNEL);
            name = args.getString(ARG_NAME, this.getClass().getName());
        }
    }

    @Override
    protected FragmentAnimator onCreateFragmentAnimation() {
        // 默认不改变
        return super.onCreateFragmentAnimation();
        // 在进入和离开时 设定无动画
//        return new DefaultNoAnimator();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_pager, container, false);
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
        mRecy = (RecyclerView) view.findViewById(R.id.recy);
        mTvTip = (TextView) view.findViewById(R.id.tvTip);
        mAdapter = new PagerContentAdapter(_mActivity);
        LinearLayoutManager manager = new LinearLayoutManager(_mActivity);
        mRecy.setLayoutManager(manager);
        mRecy.setAdapter(mAdapter);
        mRefreshLayout = (CircleRefreshLayout) view.findViewById(R.id.refresh_layout);
        mRefreshLayout.setOnRefreshListener(
                new CircleRefreshLayout.OnCircleRefreshListener() {
                    @Override
                    public void refreshing() {
                        showToast = true;
                        loadNew();
                    }

                    @Override
                    public void startRefresh() {
                        TopToastHelper.showTip(mTvTip, getString(R.string.start_load), TopToastHelper.DURATION_SHORT);
                    }

                    @Override
                    public void completeRefresh() {
                    }
                });

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                Timber.i("click position:" + position);
                Article article = ArticleLogic.getInstance().getArticles(name).get(position);
                if (article != null) {
                    queryData(article);
                }

            }
        });

        mAdapter.setDatas(ArticleLogic.getInstance().getArticles(name));
        ArticleLogic.getInstance().registerListener(name, new OnDataChangeListener() {
            @Override
            public void onDataChange() {
                mAdapter.setDatas(ArticleLogic.getInstance().getArticles(name));
                mAdapter.notifyDataSetChanged();
            }
        });
        if (mAdapter.getItemCount() == 0) {
            mProgress.setVisibility(View.VISIBLE);
        }
        loadNew();
    }

    private void queryData(Article article) {
        if (mData != null) {
            articleApi.fetch(article.getId(), AccountLogic.getInstance().getUserId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new AbsSubscriber<ApiResult<ArticleData>>(getActivity(), false) {
                        @Override
                        protected void onError(ApiException ex) {
                            getContentError();
                        }

                        @Override
                        protected void onPermissionError(ApiException ex) {
                            getContentError();
                        }

                        @Override
                        public void onNext(ApiResult<ArticleData> res) {
                            Timber.i("reache article:" + res);
                            if (res.getData() != null) {
                                ((BaseFragment) getParentFragment()).start(DetailFragment.newInstance(res.getData()));
                            } else {
                                getContentError();
                            }
                        }
                    });
        }
    }

    private void getContentError() {
        TopToastHelper.showTip(mTvTip,
                getString(R.string.get_detial_fail),
                TopToastHelper.DURATION_SHORT);
    }

    private void loadNew() {
        int type = getAction();
        Map<String, String> params = new HashMap<>();
        if (type == ACTION_HOT) {
            params.put("type", "hot");
        } else if (type == ACTION_RECOMMEND) {
            params.put("type", "recommend");
        } else {
            if (mData instanceof Site) {
                params.put("siteId", ((Site) mData).getId());
            } else if (mData instanceof Topic) {
                params.put("topics", ((Topic) mData).getName());
            }
        }
        page++;
        params.put("page", String.valueOf(page));
        params.put("pageSize", String.valueOf(pageSize));
        Timber.i("start load page,params:" + params.toString());
        articleApi.list(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsSubscriber<ApiResult<List<Article>>>(getActivity(), false) {
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
                    public void onNext(ApiResult<List<Article>> res) {
                        onFinish();
                        if (showToast) {
                            if (res.getData().size() > 0) {
                                TopToastHelper.showTip(mTvTip, getString(R.string.load_success), TopToastHelper.DURATION_SHORT);
                            } else {
                                TopToastHelper.showTip(mTvTip, getString(R.string.load_empty), TopToastHelper.DURATION_SHORT);
                            }
                            showToast = false;
                        }
                        Timber.i("download data size:" + res.getData().size() + " datas:" + res.getData());
                        ArticleLogic.getInstance().updateArticles(name, res.getData());
                    }
                });
    }

    private void onFinish() {
        if (mProgress != null) {
            mProgress.setVisibility(View.INVISIBLE);
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mRefreshLayout != null) {
                    mRefreshLayout.finishRefreshing();
                }
            }
        }, 1000);

    }

    private static int ACTION_HOT = 1;
    private static int ACTION_RECOMMEND = 2;
    private static int ACTION_TOPIC = 3;
    private static int ACTION_SITE = 4;

    private int getAction() {
        if (mData instanceof Topic) {
            Topic topic = (Topic) mData;
            if (Constants.RESERVE_TOPIC_HOT.equalsIgnoreCase(topic.getName())) {
                return ACTION_HOT;
            } else if (Constants.RESERVE_TOPIC_RECOMMEND.equalsIgnoreCase(topic.getName())) {
                return ACTION_RECOMMEND;
            } else {
                return ACTION_TOPIC;
            }
        } else if (mData instanceof Site) {
            return ACTION_SITE;
        }
        return ACTION_HOT;
    }

}
