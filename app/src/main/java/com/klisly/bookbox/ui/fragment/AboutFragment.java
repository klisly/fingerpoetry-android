package com.klisly.bookbox.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.klisly.bookbox.R;
import com.klisly.bookbox.model.BaseModel;
import com.klisly.bookbox.ui.base.BaseMainFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AboutFragment<T extends BaseModel> extends BaseMainFragment {


    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    public static SettingFragment newInstance() {
        return new SettingFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_setting, container, false);
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
        mToolbar.setTitle(R.string.about);
        initToolbarNav(mToolbar, false);
    }
}



