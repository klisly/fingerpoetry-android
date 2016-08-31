package com.klisly.bookbox.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.klisly.bookbox.Constants;
import com.klisly.bookbox.R;
import com.klisly.bookbox.listener.OnItemClickListener;
import com.klisly.bookbox.model.Article;
import com.klisly.common.dateutil.DateStyle;
import com.klisly.common.dateutil.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PagerContentAdapter extends RecyclerView.Adapter<PagerContentAdapter.PagerItemViewHolder> {
    private List<Article> mItems = new ArrayList<>();
    private LayoutInflater mInflater;

    private OnItemClickListener mClickListener;

    public PagerContentAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    public void setDatas(List<Article> items) {
        mItems.clear();
        mItems.addAll(items);
    }

    @Override
    public PagerItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_pager, parent, false);
        final PagerItemViewHolder holder = new PagerItemViewHolder(view);
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
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
        Article article = mItems.get(position);
        if(!Constants.INVALID_ARTICLE_ID.equals(article.getId())) {
            holder.articlrLayout.setVisibility(View.VISIBLE);
            holder.mTvLoad.setVisibility(View.GONE);
            holder.tvTitle.setText(article.getTitle());
            holder.tvSource.setText(article.getSite());
            Date date = new Date();
            date.setTime(article.getCreateAt());
            holder.tvDate.setText(DateUtil.DateToString(date, DateStyle.YYYY_MM_DD_HH_MM_SS));
        } else {
            holder.articlrLayout.setVisibility(View.GONE);
            holder.mTvLoad.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class PagerItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_source)
        TextView tvSource;
        @Bind(R.id.tv_time)
        TextView tvDate;
        @Bind(R.id.rl_item)
        RelativeLayout relativeLayout;
        @Bind(R.id.article_item)
        RelativeLayout articlrLayout;
        @Bind(R.id.clickload)
        TextView mTvLoad;
        public PagerItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
}
