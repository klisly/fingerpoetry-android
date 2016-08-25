package com.klisly.bookbox.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.klisly.bookbox.R;
import com.klisly.bookbox.listener.OnItemClickListener;
import com.klisly.bookbox.model.User2Article;
import com.klisly.common.dateutil.DateStyle;
import com.klisly.common.dateutil.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UserRelateAdapter extends RecyclerView.Adapter<UserRelateAdapter.PagerItemViewHolder> {
    private List<User2Article> mItems = new ArrayList<>();
    private LayoutInflater mInflater;

    private OnItemClickListener mClickListener;

    public UserRelateAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    public void setDatas(List<User2Article> items) {
        mItems.clear();
        mItems.addAll(items);
    }

    @Override
    public PagerItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_me, parent, false);
        final PagerItemViewHolder holder = new PagerItemViewHolder(view);
        holder.rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (mClickListener != null) {
                    mClickListener.onItemClick(position, v);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(PagerItemViewHolder holder, int position) {
        User2Article user2Article = mItems.get(position);
        holder.tvTitle.setText(user2Article.getArticleName());
//        holder.tvSource.setText(article.getSite());
        Date date = new Date();
        date.setTime(user2Article.getCreateAt());
        holder.tvDate.setText(DateUtil.DateToString(date, DateStyle.YYYY_MM_DD_HH_MM_SS));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public List<User2Article> getmItems() {
        return mItems;
    }

    public void setmItems(List<User2Article> mItems) {
        this.mItems = mItems;
    }

    class PagerItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_source)
        TextView tvSource;
        @Bind(R.id.tv_time)
        TextView tvDate;
        @Bind(R.id.rl_item)
        RelativeLayout rlItem;
        public PagerItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
}
