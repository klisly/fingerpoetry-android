package com.klisly.bookbox.logic;

import com.klisly.bookbox.domain.LoginData;
import com.klisly.bookbox.model.User;
import com.klisly.common.StringUtils;

public class AccountLogic extends BaseLogic {

    /**
     * TopicLogic instance.
     */
    private static AccountLogic instance;

    private LoginData loginData;

    private static final String PRE_ACCOUNT = "PRE_ACCOUNT";

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
        loginData.getUser().setToken(loginData.getToken());
        this.loginData = loginData;

        String data = gson.toJson(loginData);
        this.loginData = loginData;
        preferenceUtils.setValue(PRE_ACCOUNT, data);

    }

    public void logout() {
        this.loginData = null;
        preferenceUtils.setValue(PRE_ACCOUNT, "");
    }

    public void updateProfile(LoginData data) {
        setLoginData(loginData);
    }
}
