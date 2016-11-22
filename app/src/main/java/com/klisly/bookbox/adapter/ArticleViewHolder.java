package com.klisly.bookbox.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.klisly.bookbox.Constants;
import com.klisly.bookbox.R;
import com.klisly.bookbox.model.Article;
import com.klisly.common.StringUtils;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;


public class ArticleViewHolder extends BaseViewHolder<Article> {
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
    @Bind(R.id.article_item)
    RelativeLayout articlrLayout;
    @Bind(R.id.clickload)
    TextView mTvLoad;

    public ArticleViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_pager);
        ButterKnife.bind(this, itemView);
    }


    @Override
    public void setData(final Article article){
        if (article != null && !Constants.INVALID_ARTICLE_ID.equals(article.getId())) {
            articlrLayout.setVisibility(View.VISIBLE);
            mTvLoad.setVisibility(View.GONE);
            tvTitle.setText(article.getTitle());
            tvSource.setText(article.getSite());
            Date date = new Date();
            date.setTime(article.getCreateAt());

            tvDate.setText(article.getReadCount() + "阅・"
                    + article.getCollectCount() + "收藏・"
                    + article.getShareCount() + "分享");
            if(StringUtils.isNotEmpty(article.getContent())){
                tvContent.setVisibility(View.VISIBLE);
                tvContent.setText(article.getContent());
            } else {
                tvContent.setVisibility(View.GONE);
            }
        } else {
            articlrLayout.setVisibility(View.GONE);
            mTvLoad.setVisibility(View.VISIBLE);
        }
    }
}
