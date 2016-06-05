package com.klisly.bookbox.adapter;

import java.util.List;

import com.klisly.bookbox.model.Channel;
import com.klisly.bookbox.ui.fragment.PagerChildFragment;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
public class ChannelFragmentAdapter extends FragmentPagerAdapter {
    private List<Channel> channels;
    public ChannelFragmentAdapter(@NonNull FragmentManager fm, @NonNull List<Channel> channels) {
        super(fm);
        this.channels = channels;
    }

    @Override
    public Fragment getItem(int position) {
        return PagerChildFragment.newInstance(0, channels.get(position));
    }

    @Override
    public int getCount() {
        return channels.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return channels.get(position).getTitle();
    }
}
