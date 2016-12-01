package com.klisly.bookbox.ui.fragment.novel;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.klisly.bookbox.R;
import com.klisly.bookbox.adapter.NovelAdapter;
import com.klisly.bookbox.api.BookRetrofit;
import com.klisly.bookbox.api.NovelApi;
import com.klisly.bookbox.domain.ApiResult;
import com.klisly.bookbox.listener.OnItemClickListener;
import com.klisly.bookbox.logic.AccountLogic;
import com.klisly.bookbox.logic.NovelLogic;
import com.klisly.bookbox.model.BaseModel;
import com.klisly.bookbox.model.Novel;
import com.klisly.bookbox.model.User2Novel;
import com.klisly.bookbox.subscriber.AbsSubscriber;
import com.klisly.bookbox.subscriber.ApiException;
import com.klisly.bookbox.ui.base.BaseBackFragment;
import com.klisly.bookbox.utils.ActivityUtil;
import com.klisly.bookbox.utils.ToastHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

import static com.klisly.bookbox.R.id.recyclerView;

public class SearchFragment<T extends BaseModel> extends BaseBackFragment {

    @Bind(recyclerView)
    RecyclerView mRecy;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.searchview)
    SearchView searchView;
    //    private PagerContentAdapter mAdapter;
    private NovelApi novelApi = BookRetrofit.getInstance().getNovelApi();
    private NovelAdapter adapter;
    private List<Novel> datas = new ArrayList<Novel>();

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_search, container, false);
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
        mToolbar.setTitle(R.string.search_novel);
        initToolbarNav(mToolbar, false);

        mRecy.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerDecoration itemDecoration = new DividerDecoration(getResources().getColor(R.color.background_black_alpha_20), ActivityUtil.dip2px(getActivity(), 0.8f), 0, 0);
        itemDecoration.setDrawLastItem(false);
        mRecy.addItemDecoration(itemDecoration);
        adapter = new NovelAdapter(getActivity().getApplicationContext(), datas);
        mRecy.setAdapter(adapter);
        loadRecommend();
        initListener();
    }

    @OnClick(R.id.btn_search)
    void onSearch(){
        hideSoftInput();
        searchView.clearFocus();
        loadSearch(input);
    }
    private String input;
    private void initListener() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Timber.i("search test:" + query);
                input = query;
                loadSearch(query);
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                input = newText;
                return false;
            }
        });

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                if (datas.size() <= position) {
                    return;
                }
                if (NovelLogic.getInstance().isFocused(datas.get(position))) {
                    String id = null;
                    if(datas.get(position).getId() == null){
                        id = NovelLogic.getInstance().getNovelId(datas.get(position).getAuthor(), datas.get(position).getTitle());
                    } else {
                        id = datas.get(position).getId();
                    }
                    novelApi.unsubscribe(id, AccountLogic.getInstance().getToken())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new AbsSubscriber<ApiResult<User2Novel>>(getActivity(), true) {
                                @Override
                                public void onNext(ApiResult<User2Novel> apiResult) {
                                    if (apiResult.getStatus() == 200 || apiResult.getStatus() == 404) {
                                        ToastHelper.showShortTip("成功取消关注");
                                        if(datas.get(position).getId() != null){
                                            NovelLogic.getInstance().unSubscribe(datas.get(position).getId());
                                        } else {
                                            NovelLogic.getInstance().unSubscribe(datas.get(position).getAuthor(), datas.get(position).getTitle());
                                        }
                                        adapter.notifyItemChanged(position);
                                    }
                                }

                                @Override
                                protected void onError(ApiException ex) {

                                }

                                @Override
                                protected void onPermissionError(ApiException ex) {

                                }
                            });

                } else {
                    if (datas.get(position).getId() != null) {
                        novelApi.subscribeById(datas.get(position).getId(), AccountLogic.getInstance().getToken())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(getSubscriber(position));
                    } else {
                        novelApi.subscribe(datas.get(position), AccountLogic.getInstance().getToken())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(getSubscriber(position));
                    }
                }
            }
        });
    }

    private Observer<? super ApiResult<User2Novel>> getSubscriber(int position) {
        return new AbsSubscriber<ApiResult<User2Novel>>(getActivity(), true) {
            @Override
            public void onNext(ApiResult<User2Novel> apiResult) {
                if (apiResult.getStatus() == 200 ) {
                    ToastHelper.showShortTip("成功关注");
                    User2Novel user2Novel = apiResult.getData();
                    if (user2Novel != null) {
                        NovelLogic.getInstance().subscribe(user2Novel);
                        datas.get(position).setId(user2Novel.getNid());
                    }
                    adapter.notifyItemChanged(position);
                }
            }

            @Override
            protected void onError(ApiException ex) {

            }

            @Override
            protected void onPermissionError(ApiException ex) {

            }
        };
    }

    private void loadSearch(String name) {
        hideSoftInput();
        datas.clear();
        adapter.notifyDataSetChanged();
        novelApi.search(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsSubscriber<ApiResult<List<Novel>>>(getActivity(), true) {
                    @Override
                    protected void onError(ApiException ex) {
                    }

                    @Override
                    protected void onPermissionError(ApiException ex) {
                    }

                    @Override
                    public void onNext(ApiResult<List<Novel>> res) {
                        Timber.i("load recommdn res:" + res.getData().size());
                        datas.clear();
                        datas.addAll(res.getData());
                        adapter.notifyDataSetChanged();
                    }
                });
    }


    private void loadRecommend() {
        novelApi.recommend()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsSubscriber<ApiResult<List<Novel>>>(getActivity(), false) {
                    @Override
                    protected void onError(ApiException ex) {
                        if (getActivity() == null || getActivity().isFinishing()) {
                            return;
                        }

                    }

                    @Override
                    protected void onPermissionError(ApiException ex) {
                        if (getActivity() == null || getActivity().isFinishing()) {
                            return;
                        }
                    }

                    @Override
                    public void onNext(ApiResult<List<Novel>> res) {
                        if (getActivity() == null || getActivity().isFinishing()) {
                            return;
                        }
                        datas.clear();
                        datas.addAll(res.getData());
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public boolean onBackPressedSupport() {
        return super.onBackPressedSupport();
    }
}

