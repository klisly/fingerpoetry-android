package com.klisly.bookbox.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Site extends BaseModel {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("srcUrl")
    @Expose
    private String srcUrl;
    @SerializedName("__v")
    @Expose
    private long v;
    @SerializedName("updateAt")
    @Expose
    private long updateAt;
    @SerializedName("createAt")
    @Expose
    private long createAt;
    @SerializedName("isBlock")
    @Expose
    private Boolean isBlock;
    @SerializedName("followerCount")
    @Expose
    private long followerCount;
    @SerializedName("articleCount")
    @Expose
    private long articleCount;
    @SerializedName("image")
    @Expose
    private String image;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSrcUrl() {
        return srcUrl;
    }

    public void setSrcUrl(String srcUrl) {
        this.srcUrl = srcUrl;
    }

    public long getV() {
        return v;
    }

    public void setV(long v) {
        this.v = v;
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

    public Boolean getBlock() {
        return isBlock;
    }

    public void setBlock(Boolean block) {
        isBlock = block;
    }

    public long getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(long followerCount) {
        this.followerCount = followerCount;
    }

    public long getArticleCount() {
        return articleCount;
    }

    public void setArticleCount(long articleCount) {
        this.articleCount = articleCount;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Site{");
        sb.append("id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", srcUrl='").append(srcUrl).append('\'');
        sb.append(", v=").append(v);
        sb.append(", updateAt=").append(updateAt);
        sb.append(", createAt=").append(createAt);
        sb.append(", articleCount=").append(articleCount);
        sb.append(", isBlock=").append(isBlock);
        sb.append(", image=").append(image);
        sb.append(", followerCount=").append(followerCount);
        sb.append('}');
        return sb.toString();
    }
}
