package com.klisly.bookbox;

import android.content.Context;

import com.klisly.bookbox.api.BookRetrofit;
import com.klisly.bookbox.domain.ApiResult;
import com.klisly.bookbox.logic.AccountLogic;
import com.klisly.bookbox.logic.SiteLogic;
import com.klisly.bookbox.logic.TopicLogic;
import com.klisly.bookbox.model.Site;
import com.klisly.bookbox.model.Topic;
import com.klisly.bookbox.model.User2Site;
import com.klisly.bookbox.model.User2Topic;
import com.klisly.bookbox.subscriber.AbsSubscriber;
import com.klisly.bookbox.subscriber.ApiException;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class CommonHelper {

    public static void getTopics(Context context) {
        BookRetrofit.getInstance().getTopicApi()
                .list()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsSubscriber<ApiResult<List<Topic>>>(context, false) {
                    @Override
                    protected void onError(ApiException ex) {
                        Timber.i("onError");

                    }

                    @Override
                    protected void onPermissionError(ApiException ex) {
                        Timber.i("onPermissionError");

                    }

                    @Override
                    public void onNext(ApiResult<List<Topic>> entities) {
                        Timber.i("onNext list topics size:" + entities.getData().size());
                        TopicLogic.getInstance().updateDefaultTopics(entities.getData());
                    }
                });
    }

    public static void getUserTopics(Context context){
        if (AccountLogic.getInstance().getNowUser() != null) {
            BookRetrofit.getInstance().getTopicApi()
                    .subscribes(AccountLogic.getInstance().getNowUser().getId(),
                    AccountLogic.getInstance().getToken())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new AbsSubscriber<ApiResult<List<User2Topic>>>(context, false) {
                        @Override
                        protected void onError(ApiException ex) {
                            Timber.i("onError");

                        }

                        @Override
                        protected void onPermissionError(ApiException ex) {
                            Timber.i("onPermissionError");

                        }

                        @Override
                        public void onNext(ApiResult<List<User2Topic>> data) {
                            Timber.i("onNext choose topics size:" + data.getData().size());
                            TopicLogic.getInstance().updateSubscribes(data.getData());
                        }
                    });
        }
    }

    public static void getSites(Context context) {
        BookRetrofit.getInstance().getSiteApi()
                .list()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsSubscriber<ApiResult<List<Site>>>(context, false) {
                    @Override
                    protected void onError(ApiException ex) {
                        Timber.i("onError");

                    }

                    @Override
                    protected void onPermissionError(ApiException ex) {
                        Timber.i("onPermissionError");

                    }

                    @Override
                    public void onNext(ApiResult<List<Site>> entities) {
                        Timber.i("onNext list topics size:" + entities.getData().size());
                        SiteLogic.getInstance().updateDefaultTopics(entities.getData());
                    }
                });
    }

    public static void getUserSites(Context context){
        if (AccountLogic.getInstance().getNowUser() != null) {
            BookRetrofit.getInstance().getSiteApi()
                    .subscribes(AccountLogic.getInstance().getNowUser().getId(),
                            AccountLogic.getInstance().getToken())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new AbsSubscriber<ApiResult<List<User2Site>>>(context, false) {
                        @Override
                        protected void onError(ApiException ex) {
                            Timber.i("onError");

                        }

                        @Override
                        protected void onPermissionError(ApiException ex) {
                            Timber.i("onPermissionError");

                        }

                        @Override
                        public void onNext(ApiResult<List<User2Site>> data) {
                            Timber.i("onNext choose topics size:" + data.getData().size());
                            SiteLogic.getInstance().updateSubscribes(data.getData());
                        }
                    });
        }
    }

    public static int getItemType(Object data){
        int itemType = Constants.ITEM_TYPE_NORMAL;
        if (data instanceof Site) {
            if(((Site) data).getType() == Constants.ITEM_TYPE_JOKE) {
                itemType = Constants.ITEM_TYPE_JOKE;
            }
        } else if (data instanceof Topic) {
            if(((Topic) data).getName().equals("段子")) {
                itemType =  Constants.ITEM_TYPE_JOKE;
            }
        }
        return itemType;
    }
}
