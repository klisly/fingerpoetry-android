

package com.klisly.bookbox.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.decoration.StickyHeaderDecoration;
import com.klisly.bookbox.R;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * 当前类注释：悬浮headerAdapter
 * PackageName：com.jude.dome.sticky
 * Created by Qyang on 16/11/4
 * Email: yczx27@163.com
 */
public class StickyHeaderAdapter implements StickyHeaderDecoration.IStickyHeaderAdapter<StickyHeaderAdapter.HeaderHolder> {

    private LayoutInflater mInflater;
    private List<String> headers = new ArrayList<>();
    private int groupSize = 10;
    public List<String> getHeaders() {
        return headers;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    public StickyHeaderAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public long getHeaderId(int position) {
        return position / groupSize;
    }

    @Override
    public HeaderHolder onCreateHeaderViewHolder(ViewGroup parent) {
        final View view = mInflater.inflate(R.layout.header_item, parent, false);
        return new HeaderHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(HeaderHolder viewholder, int position) {
        Timber.i("poion:"+position );
        position = (position + 1)/ groupSize;
        if(headers.size() > position){
            viewholder.header.setText(headers.get(position));
        } else {
            viewholder.header.setText("七点一刻");
        }
    }

    class HeaderHolder extends RecyclerView.ViewHolder {
        public TextView header;

        public HeaderHolder(View itemView) {
            super(itemView);
            header = (TextView) itemView;
        }
    }
}
