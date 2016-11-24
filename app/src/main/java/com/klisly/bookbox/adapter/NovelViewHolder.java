package com.klisly.bookbox.adapter;

import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.klisly.bookbox.R;
import com.klisly.bookbox.model.User2Novel;
import com.material.widget.PaperButton;

import butterknife.Bind;
import butterknife.ButterKnife;


public class NovelViewHolder extends BaseViewHolder<User2Novel> {
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_brief)
    TextView tvBrief;
    @Bind(R.id.tv_author)
    TextView tvAuthor;
    @Bind(R.id.tv_latest)
    TextView tvLatest;
    @Bind(R.id.iv_image)
    SimpleDraweeView simpleDraweeView;
    @Bind(R.id.btn_action)
    PaperButton paperButton;

    public NovelViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_novel);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(final User2Novel novel){
        tvTitle.setText(novel.getTitle());
        tvAuthor.setText("作者： "+novel.getAuthor() == null?"未知":novel.getAuthor());
        tvLatest.setText("最近更新： "+novel.getLatest());
        simpleDraweeView.setImageURI(Uri.parse(novel.getImage()));
        paperButton.setVisibility(View.INVISIBLE);
    }
}
