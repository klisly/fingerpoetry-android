package com.klisly.bookbox.adapter;

import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.klisly.bookbox.R;
import com.klisly.bookbox.model.Joke;

import butterknife.Bind;
import butterknife.ButterKnife;


public class JokeViewHolder extends BaseViewHolder<Joke> {
    @Bind(R.id.iv_image)
    SimpleDraweeView ivImage;
    @Bind(R.id.tv_wechat_item_title)
    TextView tvTitle;
    @Bind(R.id.tv_wechat_item_from)
    TextView tvFrom;
    @Bind(R.id.tv_wechat_item_time)
    TextView tvTime;

    public JokeViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_joke);
        ButterKnife.bind(this, itemView);
    }


    @Override
    public void setData(final Joke entity) {
        tvTitle.setText(entity.getTitle());
        tvTime.setText(entity.getContent());
    }
}
