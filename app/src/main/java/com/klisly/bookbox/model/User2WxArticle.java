package com.klisly.bookbox.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User2WxArticle extends BaseModel{

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("articleId")
    @Expose
    private String articleId;
    @SerializedName("href")
    @Expose
    private String href;
    @SerializedName("articleName")
    @Expose
    private String articleName;
    @SerializedName("account")
    @Expose
    private String account;
    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("update")
    @Expose
    private long update;
    @SerializedName("tag")
    @Expose
    private String tag;
    @SerializedName("updateAt")
    @Expose
    private long updateAt;
    @SerializedName("createAt")
    @Expose
    private long createAt;
    @SerializedName("toread")
    @Expose
    private boolean toread;
    @SerializedName("read")
    @Expose
    private boolean read;
    @SerializedName("heart")
    @Expose
    private boolean heart;
    @SerializedName("collect")
    @Expose
    private boolean collect;
    @SerializedName("share")
    @Expose
    private boolean share;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }



    public void setCreateAt(Integer createAt) {
        this.createAt = createAt;
    }

    public boolean getToread() {
        return toread;
    }

    public void setToread(boolean toread) {
        this.toread = toread;
    }

    public boolean getRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public boolean getHeart() {
        return heart;
    }

    public void setHeart(boolean heart) {
        this.heart = heart;
    }

    public boolean getCollect() {
        return collect;
    }

    public void setCollect(boolean collect) {
        this.collect = collect;
    }

    public boolean getShare() {
        return share;
    }

    public void setShare(boolean share) {
        this.share = share;
    }

    public long getUpdate() {
        return update;
    }

    public void setUpdate(long update) {
        this.update = update;
    }

    public long getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(long updateAt) {
        this.updateAt = updateAt;
    }

    public long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(long createAt) {
        this.createAt = createAt;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User2WxArticle{");
        sb.append("id='").append(id).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        sb.append(", articleId='").append(articleId).append('\'');
        sb.append(", href='").append(href).append('\'');
        sb.append(", articleName='").append(articleName).append('\'');
        sb.append(", account='").append(account).append('\'');
        sb.append(", img='").append(img).append('\'');
        sb.append(", update=").append(update);
        sb.append(", tag='").append(tag).append('\'');
        sb.append(", updateAt=").append(updateAt);
        sb.append(", createAt=").append(createAt);
        sb.append(", toread=").append(toread);
        sb.append(", read=").append(read);
        sb.append(", heart=").append(heart);
        sb.append(", collect=").append(collect);
        sb.append(", share=").append(share);
        sb.append('}');
        return sb.toString();
    }
}
