package com.klisly.bookbox.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.klisly.bookbox.R;
import com.klisly.bookbox.model.Article;
import com.klisly.bookbox.model.BaseModel;
import com.klisly.common.StringUtils;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;


public class ArticleViewHolder extends BaseViewHolder<BaseModel> {
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.tv_source)
    TextView tvSource;
    @Bind(R.id.tv_time)
    TextView tvDate;

    public ArticleViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_pager);
        ButterKnife.bind(this, itemView);
    }


    @Override
    public void setData(final BaseModel entity) {
        Article article = (Article) entity;

        tvTitle.setText(article.getTitle());
        tvSource.setText(article.getSite());
        Date date = new Date();
        date.setTime(article.getCreateAt());

        tvDate.setText(article.getReadCount() + "阅・"
                + article.getCollectCount() + "收藏・"
                + article.getShareCount() + "分享");
        if (StringUtils.isNotEmpty(article.getContent())) {
            tvContent.setVisibility(View.VISIBLE);
            tvContent.setText(article.getContent());
        } else {
            tvContent.setVisibility(View.GONE);
        }


    }
}
