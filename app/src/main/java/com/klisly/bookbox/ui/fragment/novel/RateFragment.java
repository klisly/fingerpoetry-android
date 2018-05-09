package com.klisly.bookbox.ui.fragment.novel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.klisly.bookbox.R;
import com.klisly.bookbox.ui.base.BaseFragment;

import java.util.ArrayList;

public class RateFragment extends BaseFragment {
    public static final String TAG = RateFragment.class.getSimpleName();
    public static RateFragment newInstance() {
        Bundle args = new Bundle();

        RateFragment fragment = new RateFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rate, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {

        MenuListFragment listFragment = findChildFragment(MenuListFragment.class);

        if (listFragment == null) {
            ArrayList<String> listMenus = new ArrayList<>();
            listMenus.add("销量排行");
            listMenus.add("当季特选");
            listMenus.add("炒菜");
            listMenus.add("汤面类");
            listMenus.add("煲类");
            listMenus.add("汤");
            listMenus.add("小菜");
            listMenus.add("酒水饮料");
            listMenus.add("盖浇饭类");
            listMenus.add("炒面类");
            listMenus.add("拉面类");
            listMenus.add("盖浇面类");
            listMenus.add("特色菜");
            listMenus.add("加料");
            listMenus.add("馄饨类");
            listMenus.add("其他");

            listFragment = MenuListFragment.newInstance(listMenus);
//            startChildFragment(R.id.fl_list_container, listFragment, false);
        }
    }

    @Override
    public boolean onBackPressedSupport() {
        // ContentFragment是ShopFragment的栈顶子Fragment,会先调用ContentFragment的onBackPressedSupport方法
        Toast.makeText(_mActivity, "onBackPressedSupport-->ShopFragment处理了返回!", Toast.LENGTH_SHORT).show();
        pop();
        return true;
    }

}
