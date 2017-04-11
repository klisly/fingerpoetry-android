package com.klisly.bookbox.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class WxArticle {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("update")
    @Expose
    private Integer update;
    @SerializedName("href")
    @Expose
    private String href;
    @SerializedName("ahref")
    @Expose
    private String ahref;
    @SerializedName("tag")
    @Expose
    private String tag;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("account")
    @Expose
    private String account;
    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("commentCount")
    @Expose
    private Integer commentCount;
    @SerializedName("shareCount")
    @Expose
    private Integer shareCount;
    @SerializedName("collectCount")
    @Expose
    private Integer collectCount;
    @SerializedName("readCount")
    @Expose
    private Integer readCount;
    @SerializedName("heartCount")
    @Expose
    private Integer heartCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getUpdate() {
        return update;
    }

    public void setUpdate(Integer update) {
        this.update = update;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getAhref() {
        return ahref;
    }

    public void setAhref(String ahref) {
        this.ahref = ahref;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getShareCount() {
        return shareCount;
    }

    public void setShareCount(Integer shareCount) {
        this.shareCount = shareCount;
    }

    public Integer getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(Integer collectCount) {
        this.collectCount = collectCount;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    public Integer getHeartCount() {
        return heartCount;
    }

    public void setHeartCount(Integer heartCount) {
        this.heartCount = heartCount;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("WxArticle{");
        sb.append("id='").append(id).append('\'');
        sb.append(", img='").append(img).append('\'');
        sb.append(", update=").append(update);
        sb.append(", href='").append(href).append('\'');
        sb.append(", ahref='").append(ahref).append('\'');
        sb.append(", tag='").append(tag).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", account='").append(account).append('\'');
        sb.append(", desc='").append(desc).append('\'');
        sb.append(", v=").append(v);
        sb.append(", commentCount=").append(commentCount);
        sb.append(", shareCount=").append(shareCount);
        sb.append(", collectCount=").append(collectCount);
        sb.append(", readCount=").append(readCount);
        sb.append(", heartCount=").append(heartCount);
        sb.append('}');
        return sb.toString();
    }
}
