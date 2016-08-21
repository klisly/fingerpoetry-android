package com.klisly.bookbox.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.klisly.bookbox.R;
import com.klisly.bookbox.api.ArticleApi;
import com.klisly.bookbox.api.BookRetrofit;
import com.klisly.bookbox.model.Article;
import com.klisly.bookbox.ui.base.BaseBackFragment;
import com.klisly.bookbox.utils.ToastHelper;
import com.klisly.common.StringUtils;
import com.klisly.common.dateutil.DateStyle;
import com.klisly.common.dateutil.DateUtil;
import com.material.widget.CircularProgress;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailFragment extends BaseBackFragment implements Toolbar.OnMenuItemClickListener{
    private static final String ARG_CONTENT = "arg_article";
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @Bind(R.id.app_bar)
    AppBarLayout appBar;
    @Bind(R.id.tv_source)
    TextView tvSource;
    @Bind(R.id.tv_date)
    TextView tvDate;
    @Bind(R.id.webView)
    WebView tvContent;
    @Bind(R.id.scrollView)
    NestedScrollView scrollView;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.coord)
    CoordinatorLayout coord;
    @Bind(R.id.cprogress)
    CircularProgress mProgress;
    private Toolbar mToolbar;
    private Article mData;
    private ArticleApi articleApi = BookRetrofit.getInstance().getArticleApi();
    public static DetailFragment newInstance(Article article) {
        DetailFragment fragment = new DetailFragment();
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
            mData = (Article) args.getSerializable(ARG_CONTENT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        mProgress.setVisibility(View.VISIBLE);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mProgress.setVisibility(View.INVISIBLE);
            }
        }, 600);
        return view;
    }

    private void updateData() {
        String info = mData.getSite();
        if(StringUtils.isNotEmpty(mData.getAuthor())){
            info = info +"  "+mData.getAuthor();
        }
        tvSource.setText(info);
        Date date = new Date();
        date.setTime(mData.getCreateAt());
        tvDate.setText(DateUtil.DateToString(date, DateStyle.YYYY_MM_DD_HH_MM_SS));
        String html = "<!DOCTYPE html><html><head><title>指尖书香</title><meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\">"
        + "<meta name=\"viewport\" content=\"width=device-width, maximum-scale=1.0,  maximum-scale=1.0, user-scalable=no\">"
                + " <style type=\"text/css\">"
                + " body {"
                + "margin: 0;"
                + "padding-left: 10px;padding-right:10px; "
                + " font: 16px"
                + "background: #F5FCFF;"
                + "} "
                + " p { "
                + "marginTop: 5; "
                + "}  img{display: block;\n" +
                "    width: 100%;} "
                + "</style> "
                + "</head> "
                + "<body> "
                + mData.getContent()
                + "</body> "
                + " </html>";
        tvContent.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
//        tvContent.loadData(html, "text/html", "utf-8");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void initView(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        initToolbarNav(mToolbar);
        toolbar.setOnMenuItemClickListener(this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(OuterFragment.newInstance(mData));
            }
        });
        tvContent.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return true;
            }
        });
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
        initLazyView();
    }

    private void initLazyView() {
        mToolbarLayout.setCollapsedTitleTextAppearance(R.style.Toolbar_TextAppearance_White);
        mToolbarLayout.setExpandedTitleTextAppearance(R.style.Toolbar_TextAppearance_Expanded);
        mToolbarLayout.setTitle(mData.getTitle());
        updateData();
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
    }

    /**
     * 类似于 Activity的 onNewIntent()
     */
    @Override
    protected void onNewBundle(Bundle args) {
        super.onNewBundle(args);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_more:
                openPopMenu();
                break;
            default:
                openPopMenu();
                break;
        }
        return true;
    }

    private void openPopMenu() {
        final PopupMenu popupMenu = new PopupMenu(_mActivity, mToolbar, GravityCompat.END);
        popupMenu.inflate(R.menu.menu_article_pop);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_to_read:
                        ToastHelper.showShortTip(R.string.toread);
                        break;
                    case R.id.action_collect:
                        ToastHelper.showShortTip(R.string.collect);
                        break;

                    case R.id.action_share:
                        ToastHelper.showShortTip(R.string.share);
                        break;

                    case R.id.action_original:
                        start(OuterFragment.newInstance(mData));
                        break;

                    case R.id.action_notify_setting:
                        ToastHelper.showShortTip(R.string.report);
                        break;
                    default:
                        break;
                }
                popupMenu.dismiss();
                return true;
            }
        });
        popupMenu.show();
    }
}
