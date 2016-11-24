package com.klisly.bookbox.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.klisly.bookbox.R;
import com.klisly.bookbox.listener.OnItemClickListener;
import com.klisly.bookbox.logic.NovelLogic;
import com.klisly.bookbox.model.Novel;
import com.material.widget.PaperButton;

import java.util.List;

import static com.klisly.bookbox.R.id.item_layout;

public class NovelAdapter extends RecyclerView.Adapter<NovelAdapter.NovelViewHolder> {
    private LayoutInflater mInflater;
    private List<Novel> mItems;
    private OnItemClickListener mClickListener;

    public NovelAdapter(Context context, List<Novel> mItems) {
        this.mInflater = LayoutInflater.from(context);
        this.mItems = mItems;
    }

    @Override
    public NovelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_novel, parent, false);
        final NovelViewHolder holder = new NovelViewHolder(view);
        holder.paperButton.setOnClickListener(new View.OnClickListener() {
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
    public void onBindViewHolder(NovelViewHolder holder, int position) {
        Novel novel = mItems.get(position);
        holder.articlrLayout.setVisibility(View.VISIBLE);
        holder.tvTitle.setText(novel.getTitle());
        holder.tvBrief.setText("简介： "+novel.getDesc()==null?"暂无说明":novel.getDesc());
        holder.tvAuthor.setText("作者： "+novel.getAuthor() == null?"未知":"");
        holder.tvLatest.setText("最近更新： "+novel.getLatest());
        holder.simpleDraweeView.setImageURI(Uri.parse(novel.getImage()));
        if(NovelLogic.getInstance().isFocused(novel)){
            holder.paperButton.setText("已关注");
        } else {
            holder.paperButton.setText("关注");
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class NovelViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvBrief;
        TextView tvAuthor;
        TextView tvLatest;
        SimpleDraweeView simpleDraweeView;
        View articlrLayout;
        PaperButton paperButton;

        public NovelViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvBrief = (TextView) itemView.findViewById(R.id.tv_brief);
            tvAuthor = (TextView) itemView.findViewById(R.id.tv_author);
            tvLatest = (TextView) itemView.findViewById(R.id.tv_latest);
            paperButton = (PaperButton) itemView.findViewById(R.id.btn_action);
            simpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.iv_image);
            articlrLayout = itemView.findViewById(item_layout);
        }
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
}
