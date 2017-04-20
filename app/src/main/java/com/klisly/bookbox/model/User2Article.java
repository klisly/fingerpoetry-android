package com.klisly.bookbox.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.klisly.bookbox.utils.ActivityUtil;
import com.klisly.common.StringUtils;

/**
 * Created by wizardholy on 16/8/24.
 */
public class User2Article extends BaseModel{
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("articleId")
    @Expose
    private String articleId;
    @SerializedName("articleName")
    @Expose
    private String articleName;
    @SerializedName("siteId")
    @Expose
    private String siteId;
    @SerializedName("siteName")
    @Expose
    private String siteName;
    @SerializedName("updateAt")
    @Expose
    private long updateAt;
    @SerializedName("createAt")
    @Expose
    private long createAt;
    @SerializedName("toread")
    @Expose
    private Boolean toread;
    @SerializedName("read")
    @Expose
    private Boolean read;
    @SerializedName("heart")
    @Expose
    private Boolean heart;
    @SerializedName("collect")
    @Expose
    private Boolean collect;
    @SerializedName("share")
    @Expose
    private Boolean share;
    @SerializedName("isBlock")
    @Expose
    private Boolean isBlock;
    private String img;

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The _id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     *
     * @param userId
     * The userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     *
     * @return
     * The articleId
     */
    public String getArticleId() {
        return articleId;
    }

    /**
     *
     * @param articleId
     * The articleId
     */
    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    /**
     *
     * @return
     * The articleName
     */
    public String getArticleName() {
        return articleName;
    }

    /**
     *
     * @param articleName
     * The articleName
     */
    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    /**
     *
     * @return
     * The updateAt
     */
    public long getUpdateAt() {
        return updateAt;
    }

    /**
     *
     * @param updateAt
     * The updateAt
     */
    public void setUpdateAt(long updateAt) {
        this.updateAt = updateAt;
    }

    /**
     *
     * @return
     * The createAt
     */
    public long getCreateAt() {
        return createAt;
    }

    /**
     *
     * @param createAt
     * The createAt
     */
    public void setCreateAt(long createAt) {
        this.createAt = createAt;
    }

    /**
     *
     * @return
     * The toread
     */
    public Boolean getToread() {
        return toread;
    }

    /**
     *
     * @param toread
     * The toread
     */
    public void setToread(Boolean toread) {
        this.toread = toread;
    }

    /**
     *
     * @return
     * The read
     */
    public Boolean getRead() {
        return read;
    }

    /**
     *
     * @param read
     * The read
     */
    public void setRead(Boolean read) {
        this.read = read;
    }

    /**
     *
     * @return
     * The heart
     */
    public Boolean getHeart() {
        return heart;
    }

    /**
     *
     * @param heart
     * The heart
     */
    public void setHeart(Boolean heart) {
        this.heart = heart;
    }

    /**
     *
     * @return
     * The collect
     */
    public Boolean getCollect() {
        return collect;
    }

    /**
     *
     * @param collect
     * The collect
     */
    public void setCollect(Boolean collect) {
        this.collect = collect;
    }

    /**
     *
     * @return
     * The share
     */
    public Boolean getShare() {
        return share;
    }

    /**
     *
     * @param share
     * The share
     */
    public void setShare(Boolean share) {
        this.share = share;
    }

    /**
     *
     * @return
     * The isBlock
     */
    public Boolean getIsBlock() {
        return isBlock;
    }

    /**
     *
     * @param isBlock
     * The isBlock
     */
    public void setIsBlock(Boolean isBlock) {
        this.isBlock = isBlock;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public Boolean getBlock() {
        return isBlock;
    }

    public void setBlock(Boolean block) {
        isBlock = block;
    }

    public String getImg() {
        if(StringUtils.isEmpty(img)){
            img = ActivityUtil.genRandomPic();
        }
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("User2Article{");
        sb.append("id='").append(id).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        sb.append(", articleId='").append(articleId).append('\'');
        sb.append(", articleName='").append(articleName).append('\'');
        sb.append(", updateAt=").append(updateAt);
        sb.append(", createAt=").append(createAt);
        sb.append(", toread=").append(toread);
        sb.append(", read=").append(read);
        sb.append(", heart=").append(heart);
        sb.append(", siteId=").append(siteId);
        sb.append(", siteName=").append(siteName);
        sb.append(", collect=").append(collect);
        sb.append(", share=").append(share);
        sb.append(", isBlock=").append(isBlock);
        sb.append('}');
        return sb.toString();
    }
}
