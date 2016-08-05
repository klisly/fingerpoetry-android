/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package com.klisly.bookbox.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.klisly.bookbox.BusProvider;
import com.klisly.bookbox.R;

import me.yokeyword.fragmentation.SupportFragment;

public class BaseFragment extends SupportFragment {
    public static String TAG = null;

    protected void initToolbarMenu(Toolbar toolbar) {
        if(toolbar != null) {
            toolbar.inflateMenu(R.menu.menu_main);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();
    }

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Override public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

}
