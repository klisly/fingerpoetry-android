package com.klisly.bookbox.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.klisly.bookbox.BookBoxApplication;
import com.klisly.bookbox.Constants;
import com.klisly.bookbox.R;
import com.klisly.bookbox.model.BaseModel;
import com.klisly.bookbox.ui.base.BaseMainFragment;
import com.klisly.common.SharedPreferenceUtils;
import com.material.widget.Switch;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SettingFragment<T extends BaseModel> extends BaseMainFragment {

    @Bind(R.id.morning_switch)
    Switch mSwMorning;
    @Bind(R.id.afternoon_switch)
    Switch mSwAfternoon;
    @Bind(R.id.novel_switch)
    Switch mSwNovel;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    private SharedPreferenceUtils proferenceUtils = BookBoxApplication.getInstance().getPreferenceUtils();
    public static SettingFragment newInstance() {
        return new SettingFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        initData();
        return view;
    }

    private void initData() {
        if(proferenceUtils.getValue(Constants.NOTIFICATION_MORNING, true)){
            mSwMorning.setChecked(true);
        } else {
            mSwMorning.setChecked(false);
        }
        if(proferenceUtils.getValue(Constants.NOTIFICATION_AFTERNOON, true)){
            mSwAfternoon.setChecked(true);
        } else {
            mSwAfternoon.setChecked(false);
        }
        if(proferenceUtils.getValue(Constants.NOTIFICATION_NOVEL, true)){
            mSwNovel.setChecked(true);
        } else {
            mSwNovel.setChecked(false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void initView(View view) {
        mToolbar.setTitle(R.string.setting);
        initToolbarNav(mToolbar, false);

        mSwMorning.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                proferenceUtils.setValue(Constants.NOTIFICATION_MORNING, isChecked);
            }
        });
        mSwAfternoon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                proferenceUtils.setValue(Constants.NOTIFICATION_AFTERNOON, isChecked);
            }
        });
        mSwNovel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                proferenceUtils.setValue(Constants.NOTIFICATION_NOVEL, isChecked);
            }
        });
    }
}

