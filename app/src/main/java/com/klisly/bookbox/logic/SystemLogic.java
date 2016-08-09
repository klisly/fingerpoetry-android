package com.klisly.bookbox.logic;

import com.klisly.bookbox.domain.LoginData;
import com.klisly.bookbox.model.User;
import com.klisly.common.StringUtils;

public class SystemLogic extends  BaseLogic{

    /**
     * TopicLogic instance.
     */
    private static SystemLogic instance;

    private LoginData loginData;

    private static final String PRE_ACCOUNT = "";

    /**
     * 获取ConversationLogic单例对象
     *
     * @return single instance of ConversationLogic
     */
    public static SystemLogic getInstance() {
        if (instance == null) {
            synchronized (SystemLogic.class) {
                if (instance == null) {
                    instance = new SystemLogic();
                }
            }
        }
        return instance;
    }

    public SystemLogic() {
        super();
        initData();
    }

    private void initData() {
        String account = preferenceUtils.getValue(PRE_ACCOUNT, "");
        if(StringUtils.isNotEmpty(account)){
            loginData = gson.fromJson(account, LoginData.class);
        }
    }

    public LoginData getLoginData() {
        return loginData;
    }

    public User getNowUser(){
        return loginData == null ? null : loginData.getUser();
    }


    public boolean isLogin(){
        return loginData != null ;
    }


    public void setLoginData(LoginData loginData) {
        if(loginData == null){
            return;
        }
        loginData.getUser().setToken(loginData.getToken());
        this.loginData = loginData;

        String data = gson.toJson(loginData);
        preferenceUtils.setValue(PRE_ACCOUNT, data);
    }

    public void logout() {
        this.loginData = null;
        preferenceUtils.setValue(PRE_ACCOUNT, "");
    }
}
