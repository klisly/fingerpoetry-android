package com.klisly.bookbox.ui.fragment.topic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.klisly.bookbox.R;
import com.klisly.bookbox.adapter.ChooseTopicAdapter;
import com.klisly.bookbox.api.BookRetrofit;
import com.klisly.bookbox.api.TopicApi;
import com.klisly.bookbox.domain.ApiResult;
import com.klisly.bookbox.listener.OnDataChangeListener;
import com.klisly.bookbox.listener.OnItemClickListener;
import com.klisly.bookbox.logic.AccountLogic;
import com.klisly.bookbox.logic.TopicLogic;
import com.klisly.bookbox.model.Topic;
import com.klisly.bookbox.model.User2Topic;
import com.klisly.bookbox.subscriber.AbsSubscriber;
import com.klisly.bookbox.subscriber.ApiException;
import com.klisly.bookbox.ui.base.BaseBackFragment;
import com.klisly.bookbox.ui.fragment.site.ChooseSiteFragment;
import com.klisly.bookbox.widget.draglistview.DragListView;
import com.material.widget.PaperButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class ChooseTopicFragment extends BaseBackFragment {
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
    private ChooseTopicAdapter mAdapter;
    private TopicApi topicApi = BookRetrofit.getInstance().getTopicApi();

    public static ChooseTopicFragment newInstance(int action) {
        ChooseTopicFragment fragment = new ChooseTopicFragment();
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
        TopicLogic.getInstance().unRegisterListener(this);
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

    private void updateData() {
        topicApi.list()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsSubscriber<ApiResult<List<Topic>>>(getActivity(), false) {
                    @Override
                    protected void onError(ApiException ex) {
                        Timber.i("onError");

                    }

                    @Override
                    protected void onPermissionError(ApiException ex) {
                        Timber.i("onPermissionError");

                    }

                    @Override
                    public void onNext(ApiResult<List<Topic>> entities) {
                        Timber.i("onNext list topics size:" + entities.getData().size());
                        TopicLogic.getInstance().updateDefaultTopics(entities.getData());
                    }
                });
        if (AccountLogic.getInstance().getNowUser() != null) {
            topicApi.subscribes(AccountLogic.getInstance().getNowUser().getId(),
                    AccountLogic.getInstance().getToken())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new AbsSubscriber<ApiResult<List<User2Topic>>>(getActivity(), false) {
                        @Override
                        protected void onError(ApiException ex) {
                            Timber.i("onError");

                        }

                        @Override
                        protected void onPermissionError(ApiException ex) {
                            Timber.i("onPermissionError");

                        }

                        @Override
                        public void onNext(ApiResult<List<User2Topic>> data) {
                            Timber.i("onNext choose topics size:" + data.getData().size());
                            TopicLogic.getInstance().updateSubscribes(data.getData());
                        }
                    });
        }
    }

    private void initView() {
        String title = getString(R.string.choose_topic);
        mToolbar.setTitle(title);
        initToolbarNav(mToolbar, false, false);
        mBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mRecy.isChangePosition()) {
                    TopicLogic.getInstance().updateFocusedOrder();
                    updateFocusedOrder();
                }
                if(action == ACTION_SET) {
                    start(ChooseSiteFragment.newInstance());
                }
                pop();
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
        mAdapter = new ChooseTopicAdapter(true);
        mRecy.setAdapter(mAdapter, true);
        mRecy.setCanDragHorizontally(false);
        mRecy.setDisableReorderWhenDragging(false);
        mRecy.setHoldPosition(1);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                Topic topic = TopicLogic.getInstance().getOpenChooseTopics().get(position);
                Timber.i("click position:" + position
                        + " data:" + topic);
                if(TopicLogic.getInstance().isFocused(topic.getId())){
                    unSubscribe(topic, position);
                } else {
                    subscribe(topic, position);
                }

            }
        });
        TopicLogic.getInstance().reorderDefaultTopics();
        mAdapter.setItemList(TopicLogic.getInstance().getOpenChooseTopics());

        if (action == ACTION_MANAGE) {
            mBtnEnter.setVisibility(View.INVISIBLE);
            mBtnNext.setText(getString(R.string.finish));
        } else if (action == ACTION_SET) {

        }

        TopicLogic.getInstance().registerListener(this, new OnDataChangeListener() {
            @Override
            public void onDataChange() {
                if(mAdapter != null){
                    mAdapter.setItemList(TopicLogic.getInstance().getOpenChooseTopics());
                }
            }
        });
    }

    private void updateFocusedOrder() {

        JSONArray jsonArray = new JSONArray();
        for(User2Topic entity: TopicLogic.getInstance().getSubscribes().values()){
            try {
                JSONObject object = new JSONObject();
                object.put("id", entity.getId());
                object.put("seq", entity.getSeq());
                jsonArray.put(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        topicApi.reorder(AccountLogic.getInstance().getNowUser().getId(),
                jsonArray.toString(),
                AccountLogic.getInstance().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new AbsSubscriber<ApiResult<Void>>(getActivity(), false) {
                    @Override
                    protected void onError(ApiException ex) {
                        Timber.i("onError");
                    }

                    @Override
                    protected void onPermissionError(ApiException ex) {
                        Timber.i("onPermissionError");
                    }

                    @Override
                    public void onNext(ApiResult<Void> data) {
                        Timber.i("reorder success");
                    }
                });
    }

    private void unSubscribe(Topic topic, int position) {
        topicApi.unsubscribe(topic.getId(),
                AccountLogic.getInstance().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsSubscriber<ApiResult<User2Topic>>(getActivity(), false) {
                    @Override
                    protected void onError(ApiException ex) {
                        Timber.i("onError");
                    }

                    @Override
                    protected void onPermissionError(ApiException ex) {
                        Timber.i("onPermissionError");
                    }

                    @Override
                    public void onNext(ApiResult<User2Topic> data) {
                        Timber.i("unSubscribe result:" + data.getData());
                        TopicLogic.getInstance().unSubscribe(data.getData());
                    }
                });
    }

    private void subscribe(Topic topic, int position) {
        topicApi.subscribe(topic.getId(),
                AccountLogic.getInstance().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsSubscriber<ApiResult<User2Topic>>(getActivity(), false) {
                    @Override
                    protected void onError(ApiException ex) {
                        Timber.i("onError");
                    }

                    @Override
                    protected void onPermissionError(ApiException ex) {
                        Timber.i("onPermissionError");
                    }

                    @Override
                    public void onNext(ApiResult<User2Topic> data) {
                        Timber.i("subscribe:" + data.getData());
                        TopicLogic.getInstance().subscribe(data.getData());                    }
                });
    }
}
