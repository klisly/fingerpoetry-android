package com.klisly.bookbox.logic;

import com.klisly.bookbox.BookBoxApplication;
import com.klisly.bookbox.BusProvider;
import com.klisly.bookbox.api.AccountApi;
import com.klisly.bookbox.api.BookRetrofit;
import com.klisly.bookbox.domain.ApiResult;
import com.klisly.bookbox.domain.LoginData;
import com.klisly.bookbox.model.User;
import com.klisly.bookbox.model.User2Article;
import com.klisly.bookbox.ottoevent.CollectsUpdateEvent;
import com.klisly.bookbox.ottoevent.ProfileUpdateEvent;
import com.klisly.bookbox.ottoevent.ReadsUpdateEvent;
import com.klisly.bookbox.ottoevent.ToReadsUpdateEvent;
import com.klisly.bookbox.subscriber.AbsSubscriber;
import com.klisly.bookbox.subscriber.ApiException;
import com.klisly.bookbox.ui.activity.HomeActivity;
import com.klisly.common.LogUtils;
import com.klisly.common.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AccountLogic extends BaseLogic {

    /**
     * TopicLogic instance.
     */
    private static AccountLogic instance;

    private LoginData loginData;

    private static final String PRE_ACCOUNT = "PRE_ACCOUNT";

    private List<User2Article> reads = new ArrayList<>();

    private List<User2Article> toreads = new ArrayList<>();

    private List<User2Article> collects = new ArrayList<>();

    /**
     * 获取ConversationLogic单例对象
     *
     * @return single instance of ConversationLogic
     */
    public static AccountLogic getInstance() {
        if (instance == null) {
            synchronized (AccountLogic.class) {
                if (instance == null) {
                    instance = new AccountLogic();
                }
            }
        }
        return instance;
    }

    public AccountLogic() {
        super();
        initData();
    }

    private void initData() {
        String account = preferenceUtils.getValue(PRE_ACCOUNT, "");
        if (StringUtils.isNotEmpty(account)) {
            loginData = gson.fromJson(account, LoginData.class);
        }
    }

    public LoginData getLoginData() {
        return loginData;
    }

    public String getToken() {
        return loginData == null ? "" : loginData.getToken();
    }

    public User getNowUser() {
        return loginData == null ? null : loginData.getUser();
    }


    public boolean isLogin() {
        return loginData != null;
    }


    public void setLoginData(LoginData loginData) {
        if (loginData == null) {
            return;
        }
        this.loginData = loginData;

        String data = gson.toJson(loginData);
        preferenceUtils.setValue(PRE_ACCOUNT, data);
    }

    public void saveData() {
        String data = gson.toJson(loginData);
        preferenceUtils.setValue(PRE_ACCOUNT, data);
//        Map<String, Object> info = new HashMap<>();
//        info.put("wxs", loginData.getUser().getWxChannles());
//        BookRetrofit.getInstance().getAccountApi().update(info, AccountLogic.getInstance().getToken())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new AbsSubscriber<ApiResult<User>>(BookBoxApplication.getInstance(), false) {
//                    @Override
//                    protected void onError(ApiException ex) {
//                        LogUtils.e("AccountLogic Updte wx channel", ex);
//                    }
//
//                    @Override
//                    protected void onPermissionError(ApiException ex) {
//                        LogUtils.e("AccountLogic Updte wx channel", ex);
//                    }
//
//                    @Override
//                    public void onNext(ApiResult<User> userApiResult) {
//
//                    }
//                });
    }

    public void logout() {
        this.loginData = null;
        preferenceUtils.setValue(PRE_ACCOUNT, "");
    }

    public void exit() {
        AccountLogic.instance = null;
    }

    public void updateProfile(User data) {
        loginData.setUser(data);
        setLoginData(loginData);
        BusProvider.getInstance().post(new ProfileUpdateEvent());
    }

    public String getUserId() {
        String userId = "";
        if (getNowUser() != null) {
            userId = getNowUser().getId();
        }
        return userId;
    }

    public void updateReads(List<User2Article> datas) {
        this.reads.clear();
        this.reads.addAll(datas);
        notifyReadsUpdate();
    }

    public void updateCollects(List<User2Article> datas) {
        this.collects.clear();
        this.collects.addAll(datas);
        notifyCollectsUpdate();
    }

    public void updateToReads(List<User2Article> datas) {
        this.toreads.clear();
        this.toreads.addAll(datas);
        notifyToReadsUpdate();
    }

    public void updateRead(User2Article data) {
        this.reads.remove(data);
        this.reads.add(0, data);
        notifyReadsUpdate();
    }

    public void updateCollect(User2Article data) {
        this.collects.remove(data);
        this.collects.add(0, data);
        notifyCollectsUpdate();
    }

    public void updateToRead(User2Article data) {
        this.toreads.remove(data);
        this.toreads.add(0, data);
        notifyToReadsUpdate();
    }

    public List<User2Article> getReads() {
        return reads;
    }

    public void setReads(List<User2Article> reads) {
        this.reads = reads;
    }

    public List<User2Article> getToreads() {
        return toreads;
    }

    public void setToreads(List<User2Article> toreads) {
        this.toreads = toreads;
    }

    public List<User2Article> getCollects() {
        return collects;
    }

    public void setCollects(List<User2Article> collects) {
        this.collects = collects;
    }

    private void notifyReadsUpdate() {
        BusProvider.getInstance().post(new ReadsUpdateEvent());
    }

    private void notifyToReadsUpdate() {
        BusProvider.getInstance().post(new ToReadsUpdateEvent());
    }

    private void notifyCollectsUpdate() {
        BusProvider.getInstance().post(new CollectsUpdateEvent());
    }
}
