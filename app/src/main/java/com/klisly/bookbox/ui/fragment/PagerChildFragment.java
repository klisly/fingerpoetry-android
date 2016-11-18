package com.klisly.bookbox.ui.fragment;

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
import com.klisly.bookbox.CommonHelper;
import com.klisly.bookbox.Constants;
import com.klisly.bookbox.R;
import com.klisly.bookbox.adapter.ArticleViewHolder;
import com.klisly.bookbox.api.ArticleApi;
import com.klisly.bookbox.api.BookRetrofit;
import com.klisly.bookbox.domain.ApiResult;
import com.klisly.bookbox.model.Article;
import com.klisly.bookbox.model.BaseModel;
import com.klisly.bookbox.model.Site;
import com.klisly.bookbox.model.Topic;
import com.klisly.bookbox.subscriber.AbsSubscriber;
import com.klisly.bookbox.subscriber.ApiException;
import com.klisly.bookbox.ui.DetailFragment;
import com.klisly.bookbox.ui.base.BaseFragment;
import com.klisly.bookbox.utils.ActivityUtil;
import com.klisly.bookbox.utils.TopToastHelper;
import com.material.widget.CircularProgress;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class PagerChildFragment<T extends BaseModel> extends BaseFragment implements RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    public static final String ARG_FROM = "arg_from";
    public static final String ARG_CHANNEL = "arg_channel";
    public static final String ARG_NAME = "arg_name";

    @Bind(R.id.recyclerView)
    EasyRecyclerView mRecy;
    @Bind(R.id.tvTip)
    TextView mTvTip;
    @Bind(R.id.cprogress)
    CircularProgress mProgress;
    private int page = 0;
    private int pageSize = 15;
    private int mFrom;
    private T mData;
//    private PagerContentAdapter mAdapter;
    private ArticleApi articleApi = BookRetrofit.getInstance().getArticleApi();
    private String name;
    private boolean needToast = false;
    private RecyclerArrayAdapter adapter;

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
        return new DefaultHorizontalAnimator();
    }

    public T getmData() {
        return mData;
    }

    public void setmData(T mData) {
        this.mData = mData;
        this.page = 0;
        loadNew();;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_pager, container, false);
        ButterKnife.bind(this, view);
        if(CommonHelper.getItemType(mData) == Constants.ITEM_TYPE_JOKE){
            pageSize = 30;
        }
        initView(view);
        // todo 智能推荐服务打开后,显示该消息
//        if(AccountLogic.getInstance().getNowUser() == null){
//            TopToastHelper.showTip(mTvTip, getString(R.string.recom_log), TopToastHelper.DURATION_LONG);
//        }
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
                adapter.remove(position);
                return true;
            }
        });
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
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
        mRecy.setRefreshListener(this);
        onRefresh();
    }

    private void queryData(Article article) {
        if (mData != null) {
            ((BaseFragment) getParentFragment()).start(DetailFragment.newInstance(article));
        }
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
                    }

                    @Override
                    protected void onPermissionError(ApiException ex) {
                        page--;
                    }

                    @Override
                    public void onNext(ApiResult<List<Article>> res) {
                        if (needToast) {
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

                        Timber.i("download data size:" + res.getData().size() + " datas:" + res.getData());
                        if(queryType == 1){
                            adapter.clear();
                            adapter.addAll(res.getData());
                        } else {
                           adapter.addAll(res.getData());
                        }
                    }
                });
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
    int queryType = -1; // 1 refresh 2 loadmore
    @Override
    public void onRefresh() {
        queryType = 1;
        loadNew();
    }

    @Override
    public void onLoadMore() {
        queryType = 2;
        loadNew();
    }
}
