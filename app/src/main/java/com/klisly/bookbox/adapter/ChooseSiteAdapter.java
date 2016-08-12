package com.klisly.bookbox.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.klisly.bookbox.BookBoxApplication;
import com.klisly.bookbox.R;
import com.klisly.bookbox.api.BookRetrofit;
import com.klisly.bookbox.listener.OnItemClickListener;
import com.klisly.bookbox.logic.SiteLogic;
import com.klisly.bookbox.model.Site;
import com.klisly.bookbox.widget.draglistview.DragItemAdapter;
import com.material.widget.PaperButton;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ChooseSiteAdapter extends DragItemAdapter<Site, ChooseSiteAdapter.Holder> {
    private OnItemClickListener mClickListener;

    public ChooseSiteAdapter(boolean dragOnLongPress) {
        super(dragOnLongPress);
        setHasStableIds(true);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_choose, parent, false);
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
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
    public void onBindViewHolder(Holder holder, int position) {
        super.onBindViewHolder(holder, position);
        Site item = mItemList.get(position);
        holder.tvTitle.setText(item.getName());
        StringBuffer buffer = new StringBuffer();
        holder.tvContent.setText(buffer.toString());
        holder.image.setImageURI(Uri.parse(BookRetrofit.BASE_URL + item.getImage()));
        buffer.append(item.getArticleCount())
                .append(BookBoxApplication.getInstance().getString(R.string.articlenum));
        if (!item.getName().equals(BookBoxApplication.getInstance().getString(R.string.hot))
                && !item.getName().equals(BookBoxApplication.getInstance().getString(R.string.recommend))) {
            buffer.append("・")
                    .append(item.getFollowerCount())
                    .append(BookBoxApplication.getInstance().getString(R.string.focurnum));
            initAction(holder.btnAction, SiteLogic.getInstance().isFocused(item.getId()));
        } else {
            holder.btnAction.disEnable();
            holder.btnAction.setText(BookBoxApplication.getInstance().getString(R.string.focused));
        }
        holder.tvContent.setText(buffer.toString());
    }

    private void initAction(PaperButton btnAction, boolean focus) {
        if (focus) {
            btnAction.setText(BookBoxApplication.getInstance().getString(R.string.focused));
            btnAction.setColor(BookBoxApplication.getInstance().getResources().getColor(R.color.primaryLight));
        } else {
            btnAction.setText(BookBoxApplication.getInstance().getString(R.string.unfocus));
            btnAction.setColor(BookBoxApplication.getInstance().getResources().getColor(R.color.primary));
        }

    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    @Override
    public long getItemId(int position) {
        return mItemList.get(position).getId().hashCode();
    }

    class Holder extends DragItemAdapter<Site, ChooseSiteAdapter.Holder>.ViewHolder {
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_brief)
        TextView tvContent;
        @Bind(R.id.iv_image)
        SimpleDraweeView image;
        @Bind(R.id.btn_action)
        PaperButton btnAction;

        public Holder(View itemView) {
            super(itemView, R.id.item_layout);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
}
