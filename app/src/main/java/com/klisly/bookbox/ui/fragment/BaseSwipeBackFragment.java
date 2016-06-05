package com.klisly.bookbox.ui.fragment;

import com.klisly.bookbox.R;

import android.support.v7.widget.Toolbar;
import android.view.View;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

public class BaseSwipeBackFragment extends SwipeBackFragment {

    public void initToolbarNav(Toolbar toolbar) {
        toolbar.setTitle("SwipeBackActivity的Fragment");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mActivity.onBackPressed();
            }
        });
    }
}
