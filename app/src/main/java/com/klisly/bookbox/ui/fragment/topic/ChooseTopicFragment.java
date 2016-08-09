package com.klisly.bookbox.ui.fragment.topic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.klisly.bookbox.R;
import com.klisly.bookbox.adapter.ChooseTopicAdapter;
import com.klisly.bookbox.api.BookRetrofit;
import com.klisly.bookbox.api.TopicApi;
import com.klisly.bookbox.domain.ApiResult;
import com.klisly.bookbox.listener.OnItemClickListener;
import com.klisly.bookbox.logic.AccountLogic;
import com.klisly.bookbox.logic.TopicLogic;
import com.klisly.bookbox.model.Topic;
import com.klisly.bookbox.subscriber.AbsSubscriber;
import com.klisly.bookbox.subscriber.ApiException;
import com.klisly.bookbox.ui.base.BaseBackFragment;
import com.klisly.bookbox.ui.fragment.site.ChooseSiteFragment;
import com.klisly.bookbox.widget.draglistview.DragListView;
import com.material.widget.PaperButton;

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
    private ChooseTopicAdapter mAdapter;
    private TopicApi topicApi = BookRetrofit.getInstance().getTopicApi();
    public static ChooseTopicFragment newInstance() {
        ChooseTopicFragment fragment = new ChooseTopicFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
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
        View view = inflater.inflate(R.layout.fragment_choose, container, false);
        ButterKnife.bind(this, view);
        initView();
        // todo 合并
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
                        TopicLogic.getInstance().setDefaultTopics(entities.getData());
                        mAdapter.setItemList(TopicLogic.getInstance().getChooseTopics());
                        Timber.i("onNext datas size:"+mAdapter.getItemCount());
                    }
                });
        if(AccountLogic.getInstance().getNowUser() != null) {
            topicApi.subscribes(AccountLogic.getInstance().getNowUser().getId(),
                        AccountLogic.getInstance().getToken())
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
                        public void onNext(ApiResult<List<Topic>> data) {
                            Timber.i("onNext choose topics size:"+data.getData().size());
//                            TopicLogic.getInstance().setChooseTopics(data.getData());
                        }
                    });
        }
        return view;
    }

    private void initView() {
        String title = getString(R.string.choose_topic);
        mToolbar.setTitle(title);
        initToolbarNav(mToolbar, false, false);
        mBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
                start(ChooseSiteFragment.newInstance());

            }
        });

        mBtnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop();
            }
        });

        mRecy.getRecyclerView().setVerticalScrollBarEnabled(true);
        mRecy.setDragListListener(new DragListView.DragListListenerAdapter() {
            @Override
            public void onItemDragStarted(int position) {
                Toast.makeText(mRecy.getContext(), "Start - position: " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemDragEnded(int fromPosition, int toPosition) {
                if (fromPosition != toPosition) {
                    Toast.makeText(mRecy.getContext(), "End - position: " + toPosition, Toast.LENGTH_SHORT).show();
                }
            }
        });

        mRecy.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ChooseTopicAdapter(true);
        mRecy.setAdapter(mAdapter, true);
        mRecy.setCanDragHorizontally(false);
        mRecy.setDisableReorderWhenDragging(false);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {

            }
        });

        mAdapter.setItemList(TopicLogic.getInstance().getChooseTopics());
        Timber.i("datas size:"+mAdapter.getItemCount());

    }
}
