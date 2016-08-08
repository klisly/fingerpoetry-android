package com.klisly.bookbox.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.klisly.bookbox.model.Topic;
import com.klisly.bookbox.ui.fragment.PagerChildFragment;

import java.util.List;
public class ChannelFragmentAdapter extends FragmentPagerAdapter {
    private List<Topic> topics;
    public ChannelFragmentAdapter(@NonNull FragmentManager fm, @NonNull List<Topic> topics) {
        super(fm);
        this.topics = topics;
    }

    @Override
    public Fragment getItem(int position) {
        return PagerChildFragment.newInstance(0, topics.get(position));
    }

    @Override
    public int getCount() {
        return topics.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return topics.get(position).getName();
    }
}
