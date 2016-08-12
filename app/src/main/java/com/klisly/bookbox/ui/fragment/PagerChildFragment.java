package com.klisly.bookbox.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.klisly.bookbox.BookBoxApplication;
import com.klisly.bookbox.R;
import com.klisly.bookbox.adapter.PagerAdapter;
import com.klisly.bookbox.listener.OnItemClickListener;
import com.klisly.bookbox.model.BaseModel;
import com.klisly.bookbox.ui.CycleFragment;
import com.klisly.bookbox.ui.base.BaseBackFragment;
import com.klisly.bookbox.ui.base.BaseFragment;
import com.klisly.bookbox.utils.ToastHelper;
import com.klisly.bookbox.utils.TopToastHelper;
import com.klisly.bookbox.widget.circlerefresh.CircleRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class PagerChildFragment<T extends BaseModel> extends BaseFragment {
    public static final String ARG_FROM = "arg_from";
    public static final String ARG_CHANNEL = "arg_channel";

    private int mFrom;
    private T mData;
    private RecyclerView mRecy;
    private PagerAdapter mAdapter;
    private TextView mTvTip;
    private CircleRefreshLayout mRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            mFrom = args.getInt(ARG_FROM);
            mData = (T) args.getSerializable(ARG_CHANNEL);
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
        initView(view);
        return view;
    }

    private void initView(View view) {
        mRecy = (RecyclerView) view.findViewById(R.id.recy);
        mTvTip = (TextView) view.findViewById(R.id.tvTip);
        mAdapter = new PagerAdapter(_mActivity);
        LinearLayoutManager manager = new LinearLayoutManager(_mActivity);
        mRecy.setLayoutManager(manager);
        mRecy.setAdapter(mAdapter);
        mRefreshLayout = (CircleRefreshLayout) view.findViewById(R.id.refresh_layout);
        mRefreshLayout.setOnRefreshListener(
                new CircleRefreshLayout.OnCircleRefreshListener() {
                    @Override
                    public void refreshing() {
                        TopToastHelper.showTip(mTvTip, "开始加载", TopToastHelper.DURATION_SHORT);
                        BookBoxApplication.getInstance()
                                .getHandler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mRefreshLayout.finishRefreshing();
                                TopToastHelper.showTip(mTvTip, "加载完成", TopToastHelper.DURATION_SHORT);
                            }
                        }, 3000);
                    }

                    @Override
                    public void completeRefresh() {
                        // do something when refresh complete
                    }
                });

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                ToastHelper.showLoneTip("click position:" + position);
                if (getParentFragment() instanceof BaseBackFragment) {
                    ((BaseFragment) getParentFragment()).start(CycleFragment.newInstance(1));
                }

            }
        });

        // Init Datas
        List<String> items = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            String item;
            if (mFrom == 0) {
                item = "推荐 " + i;
            } else if (mFrom == 1) {
                item = "热门 " + i;
            } else {
                item = "收藏 " + i;
            }
            items.add(item);
        }
        mAdapter.setDatas(items);
    }

}
