package com.klisly.bookbox.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.klisly.bookbox.R;
import com.klisly.bookbox.model.Chapter;
import com.klisly.bookbox.utils.DateUtil;

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

    public ChapterViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_pager);
        ButterKnife.bind(this, itemView);
    }


    @Override
    public void setData(final Chapter article) {
        tvTitle.setText(article.getTitle());
        tvSource.setText(article.getNname());
        tvDate.setText(DateUtil.getFriendlyTimeSpanByNow(article.getCreateAt()));
        tvContent.setVisibility(View.GONE);

    }
}
