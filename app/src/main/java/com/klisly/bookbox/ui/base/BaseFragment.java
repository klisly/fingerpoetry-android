/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package com.klisly.bookbox.ui.base;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.animation.Animation;

import com.klisly.bookbox.BookBoxApplication;
import com.klisly.bookbox.BusProvider;
import com.klisly.bookbox.R;
import com.klisly.bookbox.logic.NovelLogic;
import com.klisly.bookbox.logic.SiteLogic;
import com.klisly.bookbox.logic.TopicLogic;

import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class BaseFragment extends SupportFragment {
    public static String TAG = null;
    protected Handler handler = BookBoxApplication.getInstance().getHandler();
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

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SiteLogic.getInstance().unRegisterListener(this);
        TopicLogic.getInstance().unRegisterListener(this);
        NovelLogic.getInstance().unRegisterListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
