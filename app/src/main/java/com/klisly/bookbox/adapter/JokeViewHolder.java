package com.klisly.bookbox.adapter;

import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.klisly.bookbox.R;
import com.klisly.bookbox.model.Joke;
import com.klisly.common.LogUtils;

import butterknife.Bind;
import butterknife.ButterKnife;


public class JokeViewHolder extends BaseViewHolder<Joke> {
    @Bind(R.id.iv_image)
    SimpleDraweeView ivImage;
    @Bind(R.id.tv_item_title)
    TextView tvTitle;
    @Bind(R.id.tv_item_content)
    TextView tvContent;

    public JokeViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_joke);
        ButterKnife.bind(this, itemView);
    }


    @Override
    public void setData(final Joke entity) {
        tvTitle.setText(entity.getTitle());
        if (entity.getType().equals("txt")) {
            tvContent.setText(entity.getContent());
            ivImage.setVisibility(View.GONE);
        } else {
            tvContent.setVisibility(View.GONE);
            ivImage.setVisibility(View.VISIBLE);
            if (entity.getType().equals("jpg")) {
                ivImage.setImageURI(Uri.parse(entity.getContent()));
            } else {

                setSupportGif(ivImage, entity.getContent() + "5");
            }
        }
    }

    public static void setSupportGif(SimpleDraweeView draweeView, String url) {
        LogUtils.i("JokeViewHolder", "setSupportGif:" + url);
        PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                .setUri(url)
                .setOldController(draweeView.getController())
                .setAutoPlayAnimations(true) //自动播放gif动画
                .build();
        draweeView.setController(controller);
    }
}
