package com.klisly.bookbox.adapter;

import android.net.Uri;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.klisly.bookbox.R;
import com.klisly.bookbox.model.Article;
import com.klisly.bookbox.model.BaseModel;
import com.klisly.bookbox.utils.ActivityUtil;
import com.klisly.bookbox.utils.DateUtil;

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
    @Bind(R.id.iv_image)
    SimpleDraweeView ivImage;

    public ArticleViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_pager);
        ButterKnife.bind(this, itemView);
    }


    @Override
    public void setData(final BaseModel entity) {
        Article article = (Article) entity;
        tvTitle.setText(article.getTitle());
        tvSource.setText(article.getSite());
        ivImage.setImageURI(Uri.parse(article.getImg()));
        tvDate.setText(DateUtil.getFriendlyTimeSpanByNow(new Date(article.getCreateAt())));
    }
}
