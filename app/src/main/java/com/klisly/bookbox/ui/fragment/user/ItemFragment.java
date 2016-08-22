package com.klisly.bookbox.ui.fragment.user;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.klisly.bookbox.R;
import com.library.StickHeaderRecyclerViewFragment;
import com.library.scroll.RecyclerWithHeaderAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ItemFragment extends StickHeaderRecyclerViewFragment {

    public static ItemFragment newInstance() {
        ItemFragment fragment = new ItemFragment();
        return fragment;
    }

    public static ItemFragment newInstance(String title) {
        ItemFragment fragment = new ItemFragment();
        fragment.setTitle(title);
        return fragment;
    }

    @Override
    public void bindData() {

        LinearLayoutManager mLayoutMgr = new LinearLayoutManager(getActivity());
        getScrollView().setLayoutManager(mLayoutMgr);

        RecyclerAdapter recyclerAdapter = new RecyclerAdapter();
        recyclerAdapter.addItems(createItemList());
        getScrollView().setAdapter(recyclerAdapter);
    }

    private List<RecyclerBean> createItemList() {
        List<RecyclerBean> list = new ArrayList<>();
        list.add(new RecyclerBean("为人民服务", R.drawable.menu_user));
        list.add(new RecyclerBean("yahoo~~",R.drawable.menu_user));
        list.add(new RecyclerBean("We will give everyone a free Mi band",R.drawable.menu_user));
        list.add(new RecyclerBean("呦西呦西",R.drawable.menu_user));
        list.add(new RecyclerBean("比我聪明没我帅",R.drawable.menu_user));
        list.add(new RecyclerBean("我只拿一个人头",R.drawable.menu_user));
        list.add(new RecyclerBean("嗯哼",R.drawable.menu_user));
        list.add(new RecyclerBean("你好",R.drawable.menu_user));
        list.add(new RecyclerBean("半边天%&￥@￥",R.drawable.menu_user));
        list.add(new RecyclerBean("不想上班是全世界的最常想的事",R.drawable.menu_user));
        list.add(new RecyclerBean("!!!",R.drawable.menu_user));
        return list;
    }

    public class RecyclerBean{
        public String title;
        public int icon;

        public RecyclerBean(String title, int icon){
            this.title = title;
            this.icon = icon;
        }
    }

    public class RecyclerAdapter extends RecyclerWithHeaderAdapter {

        private List<RecyclerBean> mItemList;

        public RecyclerAdapter() {
            super();
            mItemList = new ArrayList<>();
        }

        public void addItems(List<RecyclerBean> list) {
            mItemList.addAll(list);
        }

        @Override
        public RecyclerView.ViewHolder oncreateViewHolder(ViewGroup viewGroup, int viewType) {
            Context context = viewGroup.getContext();
            View view;
            view = LayoutInflater.from(context)
                    .inflate(R.layout.item_me, viewGroup, false);
            return new RecyclerItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
            if (position > 0) {
                RecyclerItemViewHolder holder = (RecyclerItemViewHolder) viewHolder;
                holder.tvTitle.setText(mItemList.get(position - 1).title);
                holder.ivIcon.setBackgroundResource(mItemList.get(position - 1).icon);
            }
        }

        @Override
        public int getItemCount() {
            return mItemList == null ? 1 : mItemList.size() + 1;
        }

        private  class RecyclerItemViewHolder extends RecyclerView.ViewHolder {

            private final TextView tvTitle;
            private final ImageView ivIcon;

            public RecyclerItemViewHolder(View itemView) {
                super(itemView);
                tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
                ivIcon = (ImageView) itemView.findViewById(R.id.iv_icon);
            }
        }
    }

}
