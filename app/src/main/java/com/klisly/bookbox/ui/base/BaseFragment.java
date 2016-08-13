/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package com.klisly.bookbox.ui.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.animation.Animation;

import com.klisly.bookbox.BusProvider;
import com.klisly.bookbox.R;

import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import timber.log.Timber;

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
        Timber.i("onResume");
        BusProvider.getInstance().register(this);
    }

    @Override public void onPause() {
        super.onPause();
        Timber.i("onPause");
        BusProvider.getInstance().unregister(this);
    }

    @Override
    public void onDetach() {
        Timber.i("onDetach");
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        Timber.i("onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Timber.i("onDestroy");
        super.onDestroy();
    }

    @Override
    public void onStop() {
        Timber.i("onStop");
        super.onStop();
    }

    @Override
    public void onLowMemory() {
        Timber.i("onLowMemory");
        super.onLowMemory();
    }

    @Override
    public void onAttach(Activity activity) {
        Timber.i("onAttach");
        super.onAttach(activity);
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        Timber.i("onCreateAnimation");
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    @Override
    protected FragmentAnimator onCreateFragmentAnimation() {
        Timber.i("onCreateFragmentAnimation");
        return super.onCreateFragmentAnimation();
    }

    @Override
    protected void onEnterAnimationEnd() {
        Timber.i("onEnterAnimationEnd");
        super.onEnterAnimationEnd();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Timber.i("onSaveInstanceState");
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Timber.i("onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }
}
