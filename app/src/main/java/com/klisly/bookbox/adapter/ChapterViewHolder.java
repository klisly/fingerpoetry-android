package com.klisly.bookbox.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.klisly.bookbox.Constants;
import com.klisly.bookbox.R;
import com.klisly.bookbox.model.Chapter;
import com.klisly.common.dateutil.DateStyle;
import com.klisly.common.dateutil.DateUtil;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;


public class ChapterViewHolder extends BaseViewHolder<Chapter> {
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.tv_source)
    TextView tvSource;
    @Bind(R.id.tv_time)
    TextView tvDate;

    @Bind(R.id.article_item)
    RelativeLayout articlrLayout;
    @Bind(R.id.clickload)
    TextView mTvLoad;

    public ChapterViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_pager);
        ButterKnife.bind(this, itemView);
    }


    @Override
    public void setData(final Chapter article) {
        if (!Constants.INVALID_ARTICLE_ID.equals(article.getId())) {
            articlrLayout.setVisibility(View.VISIBLE);
            mTvLoad.setVisibility(View.GONE);
            tvTitle.setText(article.getTitle());
            tvSource.setText(article.getNname());
            Date date = new Date();
            date.setTime(article.getCreateAt());

            tvDate.setText(DateUtil.DateToString(date, DateStyle.HH_MM_SS));
            tvContent.setVisibility(View.GONE);
        } else {
            articlrLayout.setVisibility(View.GONE);
            mTvLoad.setVisibility(View.VISIBLE);
        }
    }
}
