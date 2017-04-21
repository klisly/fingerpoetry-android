package com.klisly.bookbox.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.klisly.bookbox.BookBoxApplication;
import com.klisly.bookbox.Constants;
import com.klisly.bookbox.R;
import com.klisly.bookbox.api.ArticleApi;
import com.klisly.bookbox.api.BookRetrofit;
import com.klisly.bookbox.api.WxArticleApi;
import com.klisly.bookbox.domain.ApiResult;
import com.klisly.bookbox.logic.AccountLogic;
import com.klisly.bookbox.model.Article;
import com.klisly.bookbox.model.BaseModel;
import com.klisly.bookbox.model.User2Article;
import com.klisly.bookbox.model.User2WxArticle;
import com.klisly.bookbox.model.WxArticle;
import com.klisly.bookbox.subscriber.AbsSubscriber;
import com.klisly.bookbox.subscriber.ApiException;
import com.klisly.bookbox.ui.base.BaseBackFragment;
import com.klisly.bookbox.utils.ActivityUtil;
import com.klisly.bookbox.utils.ShareUtil;
import com.klisly.bookbox.utils.ToastHelper;
import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.AbstractBannerADListener;
import com.qq.e.ads.banner.BannerView;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class OFragment extends BaseBackFragment implements Toolbar.OnMenuItemClickListener {
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
    @Bind(R.id.bannerContainer)
    ViewGroup bannerContainer;
    BannerView bv;

    private WxArticle mData;
    private WxArticleApi articleApi = BookRetrofit.getInstance().getWxArticleApi();

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
            mData =  (WxArticle) args.getSerializable(ARG_CONTENT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_outer_article1, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        initListener();
        fetchData();

        return view;
    }

    private void fetchData() {
        articleApi.collectStatus(mData.getId(), AccountLogic.getInstance().getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsSubscriber<ApiResult<User2WxArticle>>(getActivity(), false) {
                    @Override
                    protected void onError(ApiException ex) {

                    }

                    @Override
                    protected void onPermissionError(ApiException ex) {
                        ToastHelper.showShortTip("请登录后再收藏文章");
                    }

                    @Override
                    public void onNext(ApiResult<User2WxArticle> res) {
                        if(res.getData() == null){
                            return;
                        }
                        mData.setHeart(res.getData().getHeart());
                        mData.setCollect(res.getData().getCollect());
                        mData.setToread(res.getData().getToread());
                        mData.setShare(res.getData().getShare());
                        updateInfo();
                    }
                });
    }

    private void initListener() {
        actionCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchCollect();
            }
        });
        actionShare.setOnClickListener(v -> {
            share();
        });
    }

    private void initBanner() {
        this.bv = new BannerView(getActivity(), ADSize.BANNER, Constants.QQ_APP_ID, Constants.BannerPosId);
        bv.setRefresh(30);
        bv.setADListener(new AbstractBannerADListener() {

            @Override
            public void onNoAD(int arg0) {
                Log.i("AD_DEMO", "BannerNoAD，eCode=" + arg0);
            }

            @Override
            public void onADReceiv() {
                Log.i("AD_DEMO", "ONBannerReceive");
            }
        });
        bannerContainer.addView(bv);

    }

    private void share() {
        final WxArticle article = mData;
        String shareUrl = "http://second.imdao.cn/wx?ahre=" + article.getAhref();
        String img = "http://second.imdao.cn/images/logo.png";
        String title = article.getTitle();
        String desc = "微信美文," + "\"" + article.getTitle() + "\"" + "." + shareUrl;
        String from = getString(R.string.app_name);
        String comment = "我发现了这篇很走心的文章,分享给各位!";
        ShareUtil.shareArticle(shareUrl, img, title, desc, from, comment);
    }

    private void switchCollect() {
        if(!AccountLogic.getInstance().isLogin()){
            return;
        }
        if(mData.isCollect()){
            articleApi.uncollect(mData.getId(), AccountLogic.getInstance().getToken())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new AbsSubscriber<ApiResult<User2WxArticle>>(getActivity(), false) {
                        @Override
                        protected void onError(ApiException ex) {

                        }

                        @Override
                        protected void onPermissionError(ApiException ex) {
                            ToastHelper.showShortTip("请登录后再收藏文章");
                        }

                        @Override
                        public void onNext(ApiResult<User2WxArticle> res) {
                            mData.setHeart(res.getData().getHeart());
                            mData.setCollect(res.getData().getCollect());
                            mData.setToread(res.getData().getToread());
                            mData.setShare(res.getData().getShare());
                            updateInfo();
                        }
                    });
        } else {
            articleApi.collect(mData.getId(), AccountLogic.getInstance().getToken())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new AbsSubscriber<ApiResult<User2WxArticle>>(getActivity(), false) {
                        @Override
                        protected void onError(ApiException ex) {

                        }

                        @Override
                        protected void onPermissionError(ApiException ex) {
                            ToastHelper.showShortTip("请登录后再收藏文章");
                        }

                        @Override
                        public void onNext(ApiResult<User2WxArticle> res) {
                            mData.setHeart(res.getData().getHeart());
                            mData.setCollect(res.getData().getCollect());
                            mData.setToread(res.getData().getToread());
                            mData.setShare(res.getData().getShare());
                            updateInfo();
                        }
                    });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void initView(View view) {
        initToolbarNav(mToolbar, true);
        mToolbar.setOnMenuItemClickListener(this);
        String title = mData.getTitle();
        mToolbar.setTitle(title);
        updateInfo();

        tvContent.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        WebSettings settings = tvContent.getSettings();

        settings.setAppCacheEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        if (ActivityUtil.isNetworkConnected()) {
            settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            settings.setCacheMode(WebSettings.LOAD_CACHE_ONLY);
        }
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);

        initBanner();

        bannerContainer.postDelayed(new Runnable() {
            @Override
            public void run() {
                bv.loadAD();
            }
        }, 1200);
    }

    @Override
    protected void initToolbarMenu(Toolbar toolbar) {
        toolbar.inflateMenu(R.menu.menu_wxarticle_pop);
    }



    private void updateInfo() {
        if(mData == null){
            return;
        }
        if(mData.isCollect()){
            ivcollect.setImageResource(R.drawable.collected);
        } else {
            ivcollect.setImageResource(R.drawable.uncollected);
        }
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
        url = mData.getHref();
        tvContent.loadUrl(url);
    }


    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_collect:
                switchCollect();
                break;
            case R.id.action_share:
                share();
                break;
            default:
                break;
        }
        return true;
    }
}
