package com.klisly.bookbox.ui;

import android.app.NotificationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.klisly.bookbox.Constants;
import com.klisly.bookbox.R;
import com.klisly.bookbox.api.ArticleApi;
import com.klisly.bookbox.api.BookRetrofit;
import com.klisly.bookbox.domain.ApiResult;
import com.klisly.bookbox.domain.ArticleData;
import com.klisly.bookbox.logic.AccountLogic;
import com.klisly.bookbox.model.Article;
import com.klisly.bookbox.model.User2Article;
import com.klisly.bookbox.subscriber.AbsSubscriber;
import com.klisly.bookbox.subscriber.ApiException;
import com.klisly.bookbox.ui.base.BaseBackFragment;
import com.klisly.bookbox.utils.ToastHelper;
import com.klisly.common.StringUtils;
import com.klisly.common.dateutil.DateStyle;
import com.klisly.common.dateutil.DateUtil;
import com.material.widget.CircularProgress;
import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.AbstractBannerADListener;
import com.qq.e.ads.banner.BannerView;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

import static android.content.Context.NOTIFICATION_SERVICE;

public class DetailFragment extends BaseBackFragment implements Toolbar.OnMenuItemClickListener {
    private static final String ARG_CONTENT = "arg_article";
    NotificationManager manager;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
//    @Bind(R.id.toolbar_layout)
//    CollapsingToolbarLayout mToolbarLayout;
//    @Bind(R.id.app_bar)
//    AppBarLayout appBar;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_source)
    TextView tvSource;
    @Bind(R.id.tv_date)
    TextView tvDate;
    @Bind(R.id.webView)
    WebView tvContent;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.cprogress)
    CircularProgress mProgress;
    @Bind(R.id.bannerContainer)
    ViewGroup bannerContainer;
    BannerView bv;
    private Article mData;
    private ArticleData mArticleData;
    private ArticleApi articleApi = BookRetrofit.getInstance().getArticleApi();
    private Menu menu;
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
        manager = (NotificationManager) getContext().getSystemService(NOTIFICATION_SERVICE);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        mProgress.setVisibility(View.VISIBLE);
        manager.cancel(mData.getId().hashCode());

        return view;
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


    private void updateData() {
        String info = mArticleData.getArticle().getSite();
        if (StringUtils.isNotEmpty(mArticleData.getArticle().getAuthor())) {
            info = info + "  " + mArticleData.getArticle().getAuthor();
        }
        tvSource.setText(info);
        Date date = new Date();
        date.setTime(mArticleData.getArticle().getCreateAt());
        tvDate.setText(DateUtil.DateToString(date, DateStyle.YYYY_MM_DD_HH_MM_SS));
        String html = Constants.ARTICLE_PREFIX + mArticleData.getArticle().getContent()+Constants.ARTICLE_SUFFIX;
        tvContent.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
//        tvContent.loadData(html, "text/html", "utf-8");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void initView(View view) {
        toolbar.setTitleTextAppearance(getContext(), R.style.TitleTextApperance);
        initToolbarNav(toolbar);
        toolbar.setOnMenuItemClickListener(this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(OuterFragment.newInstance(mData));
            }
        });
        tvContent.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return true;
            }
        });
        initBanner();
        bv.loadAD();
    }

    @Override
    protected void initToolbarMenu(Toolbar toolbar) {
        toolbar.inflateMenu(R.menu.menu_article_pop);
        menu = toolbar.getMenu();
    }

    private void updateMenu(){
        if (mArticleData != null && mArticleData.getUser2article() != null) {
            if (mArticleData.getUser2article().getToread()) {
                menu.getItem(0).setTitle(getString(R.string.notoread));
            } else {
                menu.getItem(0).setTitle(getString(R.string.toread));
            }
            if (mArticleData.getUser2article().getCollect()) {
                menu.getItem(1).setTitle(getString(R.string.nocollect));
            } else {
                menu.getItem(1).setTitle(getString(R.string.collect));
            }
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
        articleApi.fetch(mData.getId(), AccountLogic.getInstance().getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsSubscriber<ApiResult<ArticleData>>(getActivity(), false) {
                    @Override
                    protected void onError(ApiException ex) {
                        try {
                            if (getActivity() != null && mProgress != null) {
                                mProgress.setVisibility(View.INVISIBLE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        ToastHelper.showShortTip(R.string.get_detial_fail);

                    }

                    @Override
                    protected void onPermissionError(ApiException ex) {
                        if (getActivity() != null && mProgress != null) {
                            mProgress.setVisibility(View.INVISIBLE);
                        }
                        ToastHelper.showShortTip(R.string.get_detial_fail);
                    }

                    @Override
                    public void onNext(ApiResult<ArticleData> res) {
                        mProgress.setVisibility(View.INVISIBLE);
                        Timber.i("reache article:" + res);
                        if (res.getData() != null) {
                            mArticleData = res.getData();
                            updateData();
                        } else {
                            ToastHelper.showShortTip(R.string.get_detial_fail);
                        }
                    }
                });
        initLazyView();
    }

    private void initLazyView() {
        toolbar.setTitle("文章内容");
        tvTitle.setText(mData.getTitle());
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
            case R.id.action_to_read:
                toggleToRead();
                break;

            case R.id.action_collect:
                toggleCollect();
                break;

            case R.id.action_share:
                shareArticle();
                break;

            case R.id.action_original:
                if(mArticleData != null){
                    start(OuterFragment.newInstance(mArticleData.getArticle()));
                }
                break;

            case R.id.action_notify_setting:
                ToastHelper.showShortTip(R.string.report);
                break;

            default:
                break;
        }
        return true;
    }


    private void shareArticle() {
        if (mArticleData == null) {
            return;
        }
        String shareUrl ="http://second.imdao.cn/articles/"+ mArticleData.getArticle().getId();
        ShareSDK.initSDK(getActivity());
        OnekeyShare oks = new OnekeyShare();
        oks.setImageUrl("http://second.imdao.cn/images/logo.png");
        //关闭sso授权
//        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
//        oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(mArticleData.getArticle().getTitle());
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(shareUrl);
        // text是分享文本，所有平台都需要这个字段
        oks.setText("美文发现," + "\"" + mArticleData.getArticle().getTitle() + "\"" + "." + shareUrl);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(shareUrl);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我发现了这篇很走心的文章,分享给各位!");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(shareUrl);

        // 启动分享GUI
        oks.show(getContext());
    }

    private void toggleCollect() {
        if (mArticleData.getUser2article() == null || !mArticleData.getUser2article().getCollect()) {
            articleApi.collect(mArticleData.getArticle().getId(), AccountLogic.getInstance().getToken())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new AbsSubscriber<ApiResult<User2Article>>(getActivity(), false) {
                        @Override
                        protected void onError(ApiException ex) {

                        }

                        @Override
                        protected void onPermissionError(ApiException ex) {

                        }

                        @Override
                        public void onNext(ApiResult<User2Article> res) {
                            Timber.i("reache article:" + res);
                            mArticleData.setUser2article(res.getData());
                            updateMenu();
                        }
                    });
        } else {
            articleApi.uncollect(mArticleData.getArticle().getId(), AccountLogic.getInstance().getToken())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new AbsSubscriber<ApiResult<User2Article>>(getActivity(), false) {
                        @Override
                        protected void onError(ApiException ex) {

                        }

                        @Override
                        protected void onPermissionError(ApiException ex) {

                        }

                        @Override
                        public void onNext(ApiResult<User2Article> res) {
                            Timber.i("reache article:" + res);
                            mArticleData.setUser2article(res.getData());
                            updateMenu();
                        }
                    });
        }
    }

    private void toggleToRead() {
        if (mArticleData.getUser2article() == null || !mArticleData.getUser2article().getToread()) {
            articleApi.toread(mArticleData.getArticle().getId(), AccountLogic.getInstance().getToken())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new AbsSubscriber<ApiResult<User2Article>>(getActivity(), false) {
                        @Override
                        protected void onError(ApiException ex) {

                        }

                        @Override
                        protected void onPermissionError(ApiException ex) {

                        }

                        @Override
                        public void onNext(ApiResult<User2Article> res) {
                            Timber.i("reache article:" + res);
                            mArticleData.setUser2article(res.getData());
                            updateMenu();
                        }
                    });
        } else {
            articleApi.untoread(mArticleData.getArticle().getId(), AccountLogic.getInstance().getToken())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new AbsSubscriber<ApiResult<User2Article>>(getActivity(), false) {
                        @Override
                        protected void onError(ApiException ex) {

                        }

                        @Override
                        protected void onPermissionError(ApiException ex) {

                        }

                        @Override
                        public void onNext(ApiResult<User2Article> res) {
                            Timber.i("reache article:" + res);
                            mArticleData.setUser2article(res.getData());
                            updateMenu();
                        }
                    });
        }
    }
}
