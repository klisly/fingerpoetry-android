package com.klisly.bookbox.adapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.klisly.bookbox.model.BaseModel;
import com.klisly.bookbox.model.ChannleEntity;
import com.klisly.bookbox.model.Site;
import com.klisly.bookbox.model.Topic;
import com.klisly.bookbox.ui.fragment.PagerChildFragment;

import java.util.List;

import timber.log.Timber;

public class PagerFragmentAdapter<T extends BaseModel> extends FragmentStatePagerAdapter {
    private List<T> list;
    public PagerFragmentAdapter(@NonNull FragmentManager fm, @NonNull List<T> list) {
        super(fm);
        this.list = list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public List<T> getList() {
        return list;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Timber.i("instantiateItem "+position);
        return super.instantiateItem(container, position);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle args = new Bundle();
        args.putInt(PagerChildFragment.ARG_FROM, 0);
        args.putSerializable(PagerChildFragment.ARG_CHANNEL, list.get(position));
        String name = "";
        if(list.get(position) instanceof Topic){
            name = ((Topic)list.get(position)).getName();
        } else if(list.get(position) instanceof Site){
            name = ((Site)list.get(position)).getName();
        } else if(list.get(position) instanceof ChannleEntity){
            name = ((ChannleEntity)list.get(position)).getName();
        }
        Timber.i("get item:"+name);
        args.putString(PagerChildFragment.ARG_NAME, name);
        PagerChildFragment fragment = new PagerChildFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String name = "";
        if(list.get(position) instanceof Site){
            name = ((Site) list.get(position)).getName();
        } else  if(list.get(position) instanceof Topic){
            name = ((Topic) list.get(position)).getName();
        } else  if(list.get(position) instanceof ChannleEntity){
            name = ((ChannleEntity) list.get(position)).getName();
        }
        return name;
    }
}
