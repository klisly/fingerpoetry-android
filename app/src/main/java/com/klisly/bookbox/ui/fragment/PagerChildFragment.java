package com.klisly.bookbox.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.klisly.bookbox.R;
import com.klisly.bookbox.adapter.PagerAdapter;
import com.klisly.bookbox.listener.OnItemClickListener;
import com.klisly.bookbox.model.Channel;
import com.klisly.bookbox.ui.CycleFragment;
import com.klisly.bookbox.utils.ToastHelper;
import com.klisly.bookbox.utils.TopToastHelper;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.anim.DefaultNoAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class PagerChildFragment extends BaseFragment {
    private static final String ARG_FROM = "arg_from";
    private static final String ARG_CHANNEL = "arg_channel";

    private int mFrom;
    private Channel mChannel;
    private RecyclerView mRecy;
    private PagerAdapter mAdapter;
    private Channel channel;
    private TextView mTvTip;

    public static PagerChildFragment newInstance(@NonNull int from, @NonNull Channel channel) {
        Bundle args = new Bundle();
        args.putInt(ARG_FROM, from);
        args.putSerializable(ARG_CHANNEL, channel);
        PagerChildFragment fragment = new PagerChildFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            mFrom = args.getInt(ARG_FROM);
            mChannel = (Channel) args.getSerializable(ARG_CHANNEL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_pager, container, false);
        initView(view);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showTip("进入子页面");
            }
        }, 3000);
        return view;
    }

    @Override
    protected FragmentAnimator onCreateFragmentAnimation() {
        return new DefaultNoAnimator();
    }

    private void initView(View view) {
        mRecy = (RecyclerView) view.findViewById(R.id.recy);
        mTvTip = (TextView) view.findViewById(R.id.tvTip);
        mAdapter = new PagerAdapter(_mActivity);
        LinearLayoutManager manager = new LinearLayoutManager(_mActivity);
        mRecy.setLayoutManager(manager);
        mRecy.setAdapter(mAdapter);

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

    private void showTip(String tip){
        TopToastHelper.showTip(mTvTip, tip, TopToastHelper.DURATION_LONG);
    }
}
