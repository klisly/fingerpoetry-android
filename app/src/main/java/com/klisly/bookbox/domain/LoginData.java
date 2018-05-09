package com.klisly.bookbox.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.klisly.bookbox.model.User;
public class LoginData {

    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("exp")
    @Expose
    private Long exp;
    @SerializedName("user")
    @Expose
    private User user;

    /**
     *
     * @return
     * The token
     */
    public String getToken() {
        return token;
    }

    /**
     *
     * @param token
     * The token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     *
     * @return
     * The exp
     */
    public Long getExp() {
        return exp;
    }

    /**
     *
     * @param exp
     * The exp
     */
    public void setExp(Long exp) {
        this.exp = exp;
    }

    /**
     *
     * @return
     * The user
     */
    public User getUser() {
        return user;
    }

    /**
     *
     * @param user
     * The user
     */
    public void setUser(User user) {
        if(this.user != null){
            user.setWxChannles(this.user.getWxChannles());
        }
        this.user = user;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("LoginData{");
        sb.append("token='").append(token).append('\'');
        sb.append(", exp=").append(exp);
        sb.append(", user=").append(user);
        sb.append('}');
        return sb.toString();
    }
}
