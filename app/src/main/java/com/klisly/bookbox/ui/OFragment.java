package com.klisly.bookbox.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.klisly.bookbox.R;
import com.klisly.bookbox.model.Article;
import com.klisly.bookbox.model.BaseModel;
import com.klisly.bookbox.model.WxArticle;
import com.klisly.bookbox.ui.base.BaseBackFragment;
import com.klisly.bookbox.utils.ActivityUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

public class OFragment extends BaseBackFragment {
    private static final String ARG_CONTENT = "arg_article";
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.webView)
    WebView tvContent;
    @Bind(R.id.ivcollect)
    ImageView ivcollect;
    @Bind(R.id.txtcollection)
    TextView txtcollection;
    @Bind(R.id.action_collect)
    RippleView actionCollect;
    @Bind(R.id.ivshare)
    ImageView ivshare;
    @Bind(R.id.txtshare)
    TextView txtshare;
    @Bind(R.id.action_share)
    RippleView actionShare;
    private BaseModel mData;

    public static OFragment newInstance(BaseModel article) {
        OFragment fragment = new OFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CONTENT, article);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mData = (BaseModel) args.getSerializable(ARG_CONTENT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_outer_article1, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        initListener();
        return view;
    }

    private void initListener() {
        actionCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timber.i("collect");
            }
        });
        actionShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timber.i("share");
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void initView(View view) {
        initToolbarNav(mToolbar, true, false);
        String title = "";
        if (mData instanceof Article) {
            title = ((Article) mData).getTitle();
        } else if (mData instanceof WxArticle) {
            title = ((WxArticle) mData).getTitle();
        }
        mToolbar.setTitle(title);
        tvContent.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        WebSettings settings = tvContent.getSettings();
//        if (SharedPreferenceUtil.getNoImageState()) {
//            settings.setBlockNetworkImage(true);
//        }
//        if (SharedPreferenceUtil.getAutoCacheState()) {
        settings.setAppCacheEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        if (ActivityUtil.isNetworkConnected()) {
            settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            settings.setCacheMode(WebSettings.LOAD_CACHE_ONLY);
        }
//        }
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);
    }

    /**
     * 这里演示:
     * 比较复杂的Fragment页面会在第一次start时,导致动画卡顿
     * Fragmentation提供了onEnterAnimationEnd()方法,该方法会在 入栈动画 结束时回调
     * 所以在onCreateView进行一些简单的View初始化(比如 toolbar设置标题,返回按钮; 显示加载数据的进度条等),
     * 然后在onEnterAnimationEnd()方法里进行 复杂的耗时的初始化 (比如FragmentPagerAdapter的初始化 加载数据等)
     */
    @Override
    protected void onEnterAnimationEnd() {
        String url = "";
        if (mData instanceof Article) {
            url = ((Article) mData).getSrcUrl();
        } else if (mData instanceof WxArticle) {
            url = ((WxArticle) mData).getHref();
        }
        tvContent.loadUrl(url);
    }


    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
    }
}
