package com.klisly.bookbox.ui.fragment.novel;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.klisly.bookbox.R;
import com.klisly.bookbox.ui.base.BaseFragment;

import me.yokeyword.fragmentation.anim.DefaultNoAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
public class ContentFragment extends BaseFragment {

    public static ContentFragment newInstance(String menu) {
        ContentFragment fragment = new ContentFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ratecontent, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
//        mTvContent = (TextView) view.findViewById(R.id.tv_content);
//        mBtnNext = (Button) view.findViewById(R.id.btn_next);
//
//        mTvContent.setText("Fragment内容:\n" + mMenu);

//        mBtnNext.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // 和MsgFragment同级别的跳转 交给MsgFragment处理
//                if (getParentFragment() instanceof RateFragment) {
//                    ((RateFragment) getParentFragment()).start(ChapterListFragment.newInstance(null));
//                }
//            }
//        });
    }

    @Override
    public boolean onBackPressedSupport() {
        // ContentFragment是ShopFragment的栈顶子Fragment,可以在此处理返回按键事件
        return super.onBackPressedSupport();
    }
}
