package com.klisly.bookbox.ui.fragment.wx;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.klisly.bookbox.BusProvider;
import com.klisly.bookbox.R;
import com.klisly.bookbox.adapter.ChannelAdapter;
import com.klisly.bookbox.logic.AccountLogic;
import com.klisly.bookbox.model.ChannleEntity;
import com.klisly.bookbox.ottoevent.UpdateWxChannelEvent;
import com.klisly.bookbox.ui.base.BaseBackFragment;
import com.klisly.bookbox.utils.ItemDragHelperCallback;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ChooseChannelFragment extends BaseBackFragment {
//    @Bind(R.id.toolbar)
//    Toolbar mToolbar;
    @Bind(R.id.recy)
    RecyclerView mRecy;
    private List<ChannleEntity> my;
    private List<ChannleEntity> other;
    private ChannelAdapter mAdapter;
    public static ChooseChannelFragment newInstance() {
        ChooseChannelFragment fragment = new ChooseChannelFragment();
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_channel, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    protected void onEnterAnimationEnd() {
        super.onEnterAnimationEnd();
    }

    private void initView() {
        String title = getString(R.string.choose_topic);
//        mToolbar.setTitle(title);
//        initToolbarNav(mToolbar, false, false);

        other = ChannleEntity.loadWxDefault();
        my = AccountLogic.getInstance().getNowUser().getWxChannles();
        other.removeAll(my);
        mRecy.setVerticalScrollBarEnabled(true);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 4);
        mRecy.setLayoutManager(manager);
        ItemDragHelperCallback callback = new ItemDragHelperCallback();
        final ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(mRecy);

        mAdapter = new ChannelAdapter(getContext(), helper, my, other);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int viewType = mAdapter.getItemViewType(position);
                return viewType == ChannelAdapter.TYPE_MY || viewType == ChannelAdapter.TYPE_OTHER ? 1 : 4;
            }
        });
        mRecy.setAdapter(mAdapter);
        mAdapter.setOnMyChannelItemClickListener((v, position) -> Toast.makeText(getContext(), my.get(position).getName(), Toast.LENGTH_SHORT).show());
    }

    @Override
    public boolean onBackPressedSupport() {
        BusProvider.getInstance().post(new UpdateWxChannelEvent());
        return super.onBackPressedSupport();
    }
}
