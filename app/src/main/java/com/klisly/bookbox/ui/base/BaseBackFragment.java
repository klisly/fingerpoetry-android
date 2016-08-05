package com.klisly.bookbox.ui.base;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.klisly.bookbox.R;

public class BaseBackFragment extends BaseFragment {

    protected void initToolbarNav(Toolbar toolbar) {
        initToolbarNav(toolbar, true);
    }

    protected void initToolbarNav(Toolbar toolbar, boolean initMenu) {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mActivity.onBackPressed();
            }
        });
        if(initMenu) {
            initToolbarMenu(toolbar);
        }
    }

    protected void initToolbarNav(Toolbar toolbar, boolean isBack, boolean initMenu) {
        if(isBack) {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mActivity.onBackPressed();
            }
        });
        if(initMenu) {
            initToolbarMenu(toolbar);
        }
    }
}
