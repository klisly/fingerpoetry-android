package com.klisly.bookbox.adapter;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.klisly.bookbox.BookBoxApplication;
import com.klisly.bookbox.R;
import com.klisly.bookbox.api.BookRetrofit;
import com.klisly.bookbox.listener.OnItemClickListener;
import com.klisly.bookbox.logic.TopicLogic;
import com.klisly.bookbox.model.Topic;
import com.material.widget.PaperButton;
import com.material.widget.RippleLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ChooseTopicAdapter extends RecyclerView.Adapter<ChooseTopicAdapter.Holder> {
    private List<Topic> mItems = new ArrayList<>();
    private LayoutInflater mInflater;

    private OnItemClickListener mClickListener;

    public ChooseTopicAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    public void setDatas(List<Topic> items) {
        mItems.clear();
        mItems.addAll(items);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_choose, parent, false);
        final Holder holder = new Holder(view);
        holder.btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (mClickListener != null) {
                    mClickListener.onItemClick(position, v);
                }
            }
        });
        RippleLayout.on(view)
                .rippleColor(Color.parseColor("#FF0000"))
                .rippleAlpha(0.2f)
                .rippleHover(true)
                .create();
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Topic item = mItems.get(position);
        holder.tvTitle.setText(item.getName());
        StringBuffer buffer = new StringBuffer();
        buffer.append(item.getArticleCount())
                .append(BookBoxApplication.getInstance().getString(R.string.articlenum))
                .append("・")
                .append(item.getFollowerCount())
                .append(BookBoxApplication.getInstance().getString(R.string.focurnum));
        holder.tvContent.setText(buffer.toString());
        holder.image.setImageURI(Uri.parse(BookRetrofit.BASE_URL +item.getId()));
        initAction(holder.btnAction, TopicLogic.getInstance().isFocus(item.getId()));

    }

    private void initAction(PaperButton btnAction, boolean focus) {
        if(focus){
            btnAction.setText(BookBoxApplication.getInstance().getString(R.string.unfocus));
            btnAction.setColor(BookBoxApplication.getInstance().getResources().getColor(R.color.primaryLight));
        } else {
            btnAction.setText(BookBoxApplication.getInstance().getString(R.string.focus));
            btnAction.setColor(BookBoxApplication.getInstance().getResources().getColor(R.color.primaryLight));
        }

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public Topic getItem(int position) {
        return mItems.get(position);
    }

    class Holder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_brief)
        TextView tvContent;
        @Bind(R.id.iv_image)
        SimpleDraweeView image;
        @Bind(R.id.btn_action)
        PaperButton btnAction;
        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
}
