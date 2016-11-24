package com.klisly.bookbox.ui.fragment.novel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.klisly.bookbox.CommonHelper;
import com.klisly.bookbox.R;
import com.klisly.bookbox.adapter.ChooseSiteAdapter;
import com.klisly.bookbox.api.BookRetrofit;
import com.klisly.bookbox.api.SiteApi;
import com.klisly.bookbox.domain.ApiResult;
import com.klisly.bookbox.listener.OnDataChangeListener;
import com.klisly.bookbox.listener.OnItemClickListener;
import com.klisly.bookbox.logic.AccountLogic;
import com.klisly.bookbox.logic.SiteLogic;
import com.klisly.bookbox.model.Site;
import com.klisly.bookbox.model.User2Site;
import com.klisly.bookbox.subscriber.AbsSubscriber;
import com.klisly.bookbox.subscriber.ApiException;
import com.klisly.bookbox.ui.base.BaseBackFragment;
import com.klisly.bookbox.widget.draglistview.DragListView;
import com.material.widget.PaperButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class ChooseNovelFragment extends BaseBackFragment {
    private static final String ARG_NUMBER = "arg_number";
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.btn_next)
    PaperButton mBtnNext;
    @Bind(R.id.btn_enter_direct)
    PaperButton mBtnEnter;
    @Bind(R.id.recy)
    DragListView mRecy;
    public static int ACTION_MANAGE = 1;
    public static int ACTION_SET = 2;
    private int action;
    private ChooseSiteAdapter mAdapter;
    private SiteApi siteApi = BookRetrofit.getInstance().getSiteApi();

    public static ChooseNovelFragment newInstance(int action) {
        ChooseNovelFragment fragment = new ChooseNovelFragment();
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
        SiteLogic.getInstance().unRegisterListener(this);
        ButterKnife.unbind(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose, container, false);
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
        CommonHelper.getSites(getActivity());
        CommonHelper.getUserSites(getActivity());
    }

    private void initView() {
        String title = getString(R.string.choose_site);
        mToolbar.setTitle(title);
        initToolbarNav(mToolbar, false, false);
        mBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRecy.isChangePosition()) {
                    SiteLogic.getInstance().updateFocusedOrder();
                    updateFocusedOrder();
                } else {
                    pop();
                }

            }
        });

        mBtnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop();
            }
        });

        mRecy.getRecyclerView().setVerticalScrollBarEnabled(true);
        mRecy.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ChooseSiteAdapter(true);
        mRecy.setAdapter(mAdapter, true);
        mRecy.setCanDragHorizontally(false);
        mRecy.setDisableReorderWhenDragging(false);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                if (position < SiteLogic.getInstance().getOpenChooses().size()) {
                    Site entity = SiteLogic.getInstance().getOpenChooses().get(position);
                    Timber.i("click position:" + position
                            + " data:" + entity);
                    if (SiteLogic.getInstance().isFocused(entity.getId())) {
                        unSubscribe(entity, position);
                    } else {
                        subscribe(entity, position);
                    }
                }
            }
        });
        SiteLogic.getInstance().reorderDefaultTopics();
        mAdapter.setItemList(SiteLogic.getInstance().getOpenChooses());
        mBtnEnter.setVisibility(View.INVISIBLE);
        mBtnNext.setText(getString(R.string.finish));

        SiteLogic.getInstance().registerListener(this, new OnDataChangeListener() {
            @Override
            public void onDataChange() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (mAdapter != null) {
                            mAdapter.setItemList(SiteLogic.getInstance().getOpenChooses());
                        }
                    }
                });
            }
        });
    }

    private void updateFocusedOrder() {

        JSONArray jsonArray = new JSONArray();
        for (User2Site entity : SiteLogic.getInstance().getSubscribes().values()) {
            try {
                JSONObject object = new JSONObject();
                object.put("id", entity.getId());
                object.put("seq", entity.getSeq());
                jsonArray.put(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        siteApi.reorder(AccountLogic.getInstance().getNowUser().getId(),
                jsonArray.toString(),
                AccountLogic.getInstance().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsSubscriber<ApiResult<Void>>(getActivity(), true) {
                    @Override
                    protected void onError(ApiException ex) {
                        Timber.i("onError");
                        pop();
                    }

                    @Override
                    protected void onPermissionError(ApiException ex) {
                        Timber.i("onPermissionError");
                        pop();
                    }

                    @Override
                    public void onNext(ApiResult<Void> data) {
                        Timber.i("reorder success");
                        SiteLogic.getInstance().updateOpenData();
                        pop();
                    }
                });
        SiteLogic.getInstance().updateOpenData();
    }

    private void unSubscribe(Site entity, int position) {
        siteApi.unsubscribe(entity.getId(),
                AccountLogic.getInstance().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsSubscriber<ApiResult<User2Site>>(getActivity(), false) {
                    @Override
                    protected void onError(ApiException ex) {
                        Timber.i("onError");
                    }

                    @Override
                    protected void onPermissionError(ApiException ex) {
                        Timber.i("onPermissionError");
                    }

                    @Override
                    public void onNext(ApiResult<User2Site> data) {
                        Timber.i("unSubscribe result:" + data.getData());
                        SiteLogic.getInstance().unSubscribe(data.getData());
                    }
                });
    }

    private void subscribe(Site entity, int position) {
        siteApi.subscribe(entity.getId(),
                AccountLogic.getInstance().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsSubscriber<ApiResult<User2Site>>(getActivity(), false) {
                    @Override
                    protected void onError(ApiException ex) {
                        Timber.i("onError");
                    }

                    @Override
                    protected void onPermissionError(ApiException ex) {
                        Timber.i("onPermissionError");
                    }

                    @Override
                    public void onNext(ApiResult<User2Site> data) {
                        Timber.i("subscribe:" + data.getData());
                        SiteLogic.getInstance().subscribe(data.getData());
                    }
                });
    }
}
