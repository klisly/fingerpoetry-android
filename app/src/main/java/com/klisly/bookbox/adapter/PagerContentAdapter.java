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
import com.klisly.common.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PagerContentAdapter extends RecyclerView.Adapter<PagerContentAdapter.PagerItemViewHolder> {
    private List<Article> mItems = new ArrayList<>();
    private LayoutInflater mInflater;
    private int contentType = Constants.ITEM_TYPE_NORMAL;
    private OnItemClickListener mClickListener;

    public PagerContentAdapter(Context context, int contentType) {
        this.mInflater = LayoutInflater.from(context);
        this.contentType = contentType;
    }

    public void setDatas(List<Article> items) {
        mItems.clear();
        mItems.addAll(items);
        this.notifyDataSetChanged();
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
        holder.tvTitle.setText(article.getTitle());
        holder.tvSource.setText(article.getSite());
        Date date = new Date();
        date.setTime(article.getCreateAt());

        holder.tvDate.setText(article.getReadCount() + "阅・"
                + article.getCollectCount() + "收藏・"
                + article.getShareCount() + "分享");
        if (contentType == Constants.ITEM_TYPE_JOKE && StringUtils.isNotEmpty(article.getContent())) {
            holder.tvContent.setVisibility(View.VISIBLE);
            holder.tvContent.setText(article.getContent());
        } else {
            holder.tvContent.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class PagerItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_content)
        TextView tvContent;
        @Bind(R.id.tv_source)
        TextView tvSource;
        @Bind(R.id.tv_time)
        TextView tvDate;
        @Bind(R.id.rl_item)
        RelativeLayout relativeLayout;

        public PagerItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
}
