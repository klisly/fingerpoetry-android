package com.klisly.bookbox.ui.base;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.klisly.bookbox.R;

import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class BaseMainFragment extends BaseFragment {

    protected OnFragmentOpenDrawerListener mOpenDraweListener;

    protected void initToolbarNav(Toolbar toolbar) {
        initToolbarNav(toolbar, true);
    }


    protected void initToolbarNav(Toolbar toolbar, boolean isInitMenu) {
        toolbar.setNavigationIcon(R.drawable.ic_menu_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOpenDraweListener != null) {
                    mOpenDraweListener.onOpenDrawer();
                }
            }
        });

        if(isInitMenu) {
            initToolbarMenu(toolbar);
        }
    }


    @Override
    protected FragmentAnimator onCreateFragmentAnimation() {
        return new FragmentAnimator(R.anim.activity_open_enter, R.anim.activity_close_exit);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentOpenDrawerListener) {
            mOpenDraweListener = (OnFragmentOpenDrawerListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentOpenDrawerListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOpenDraweListener = null;
    }

    public interface OnFragmentOpenDrawerListener {
        void onOpenDrawer();
    }
}
