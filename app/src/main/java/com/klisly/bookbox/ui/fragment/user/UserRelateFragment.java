package com.klisly.bookbox.ui.fragment.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.klisly.bookbox.R;
import com.klisly.bookbox.adapter.UserRelateAdapter;
import com.klisly.bookbox.api.AccountApi;
import com.klisly.bookbox.api.ArticleApi;
import com.klisly.bookbox.api.BookRetrofit;
import com.klisly.bookbox.domain.ApiResult;
import com.klisly.bookbox.domain.ArticleData;
import com.klisly.bookbox.listener.OnItemClickListener;
import com.klisly.bookbox.logic.AccountLogic;
import com.klisly.bookbox.model.User2Article;
import com.klisly.bookbox.ottoevent.CollectsUpdateEvent;
import com.klisly.bookbox.ottoevent.ReadsUpdateEvent;
import com.klisly.bookbox.ottoevent.ToReadsUpdateEvent;
import com.klisly.bookbox.subscriber.AbsSubscriber;
import com.klisly.bookbox.subscriber.ApiException;
import com.klisly.bookbox.ui.DetailFragment;
import com.klisly.bookbox.ui.base.BaseBackFragment;
import com.klisly.bookbox.utils.ToastHelper;
import com.squareup.otto.Subscribe;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class UserRelateFragment extends BaseBackFragment {
    private static final String ARG_NUMBER = "arg_number";
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.recy)
    RecyclerView mRecy;
    public static final int TYPE_READED = 1;
    public static final int TYPE_TOREAD = 2;
    public static final int TYPE_COLLECT = 3;
    private int action;
    private UserRelateAdapter mAdapter;
    private AccountApi accountApi = BookRetrofit.getInstance().getAccountApi();
    private ArticleApi articleApi = BookRetrofit.getInstance().getArticleApi();

    public static UserRelateFragment newInstance(int action) {
        UserRelateFragment fragment = new UserRelateFragment();
        fragment.setAction(action);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_relate, container, false);
        ButterKnife.bind(this, view);
        initView();
        updateData();
        return view;
    }

    @Override
    protected FragmentAnimator onCreateFragmentAnimation() {
        // 默认不改变
        return super.onCreateFragmentAnimation();
        // 在进入和离开时 设定无动画
//        return new DefaultNoAnimator();
    }

    private void updateData() {
        if (action == TYPE_READED) {
            accountApi.reads(AccountLogic.getInstance().getUserId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new AbsSubscriber<ApiResult<List<User2Article>>>(getActivity(), false) {
                        @Override
                        protected void onError(ApiException ex) {
                            Timber.i("onError");
                        }

                        @Override
                        protected void onPermissionError(ApiException ex) {
                            Timber.i("onPermissionError");
                        }

                        @Override
                        public void onNext(ApiResult<List<User2Article>> data) {
                            Timber.i("update TYPE_READED result:" + data.getData().size());
                            AccountLogic.getInstance().updateReads(data.getData());
                        }
                    });
        } else if (action == TYPE_COLLECT) {
            accountApi.collects(AccountLogic.getInstance().getUserId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new AbsSubscriber<ApiResult<List<User2Article>>>(getActivity(), false) {
                        @Override
                        protected void onError(ApiException ex) {
                            Timber.i("onError");
                        }

                        @Override
                        protected void onPermissionError(ApiException ex) {
                            Timber.i("onPermissionError");
                        }

                        @Override
                        public void onNext(ApiResult<List<User2Article>> data) {
                            Timber.i("update TYPE_COLLECT result:" + data.getData().size());
                            AccountLogic.getInstance().updateCollects(data.getData());
                        }
                    });
        } else if (action == TYPE_TOREAD) {
            accountApi.toreads(AccountLogic.getInstance().getUserId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new AbsSubscriber<ApiResult<List<User2Article>>>(getActivity(), false) {
                        @Override
                        protected void onError(ApiException ex) {
                            Timber.i("onError");
                        }

                        @Override
                        protected void onPermissionError(ApiException ex) {
                            Timber.i("onPermissionError");
                        }

                        @Override
                        public void onNext(ApiResult<List<User2Article>> data) {
                            Timber.i("update TYPE_TOREAD result:" + data.getData().size());
                            AccountLogic.getInstance().updateToReads(data.getData());
                        }
                    });
        }
    }

    private void initView() {
        String title = getTitle();
        mToolbar.setTitle(title);
        initToolbarNav(mToolbar, true, false);

        mRecy.setVerticalScrollBarEnabled(true);
        mRecy.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new UserRelateAdapter(getContext());
        mRecy.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                User2Article entity = mAdapter.getmItems().get(position);
                        Timber.i("click position:" + position
                                + " data:" + entity);
                queryData(entity);
            }
        });
        if (action == TYPE_READED) {
            mAdapter.setDatas(AccountLogic.getInstance().getReads());
        } else if (action == TYPE_COLLECT) {
            mAdapter.setDatas(AccountLogic.getInstance().getCollects());
        } else if (action == TYPE_TOREAD) {
            mAdapter.setDatas(AccountLogic.getInstance().getToreads());
        }
    }

    private void queryData(User2Article article) {

        articleApi.fetch(article.getArticleId(), AccountLogic.getInstance().getUserId())
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
                            if(action == TYPE_TOREAD){
                                unToRead(article);
                            }
                            start(DetailFragment.newInstance(res.getData()));
                        } else {
                            getContentError();
                        }
                    }
                });

    }

    private void unToRead(User2Article article) {
        articleApi.untoread(article.getArticleId(), AccountLogic.getInstance().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsSubscriber<ApiResult<User2Article>>(getActivity(), false) {
                    @Override
                    protected void onError(ApiException ex) {
                    }

                    @Override
                    protected void onPermissionError(ApiException ex) {
                    }

                    @Override
                    public void onNext(ApiResult<User2Article> res) {
                    }
                });
    }

    private void getContentError() {
        ToastHelper.showShortTip(getString(R.string.get_detial_fail));
    }

    private String getTitle() {
        if (action == TYPE_COLLECT) {
            return getString(R.string.collected);
        } else if (action == TYPE_READED) {
            return getString(R.string.recentread);
        } else if (action == TYPE_TOREAD) {
            return getString(R.string.toreaded);
        }
        return "";
    }

    @Subscribe
    public void onUpdateReads(ReadsUpdateEvent event) {
        if (action == TYPE_READED && mAdapter != null) {
            mAdapter.setDatas(AccountLogic.getInstance().getReads());
            mAdapter.notifyDataSetChanged();
        }
    }

    @Subscribe
    public void onUpdateToReads(ToReadsUpdateEvent event) {
        if (action == TYPE_TOREAD && mAdapter != null) {
            mAdapter.setDatas(AccountLogic.getInstance().getToreads());
            mAdapter.notifyDataSetChanged();
        }
    }

    @Subscribe
    public void onUpdateCollects(CollectsUpdateEvent event) {
        if (action == TYPE_COLLECT && mAdapter != null) {
            mAdapter.setDatas(AccountLogic.getInstance().getCollects());
            mAdapter.notifyDataSetChanged();
        }
    }
}
