package com.klisly.bookbox.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.klisly.bookbox.Constants;

public class Site extends BaseModel implements Comparable<Site>{
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
    @SerializedName("type")
    @Expose
    private int type;
    private boolean isFocused; // 外来数据, 由User2Topic 获得

    private int seq;
    private static int RESERVE_RECOMMEND = 1;
    private static int RESERVE_HOT = 2;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isFocused() {
        return isFocused;
    }

    public void setFocused(boolean focused) {
        isFocused = focused;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Site)) return false;
        Site site = (Site) o;
        return getId().equals(site.getId());

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getSrcUrl() != null ? getSrcUrl().hashCode() : 0);
        result = 31 * result + (int) (getV() ^ (getV() >>> 32));
        result = 31 * result + (int) (getUpdateAt() ^ (getUpdateAt() >>> 32));
        result = 31 * result + (int) (getCreateAt() ^ (getCreateAt() >>> 32));
        result = 31 * result + (isBlock != null ? isBlock.hashCode() : 0);
        result = 31 * result + (int) (getFollowerCount() ^ (getFollowerCount() >>> 32));
        result = 31 * result + (int) (getArticleCount() ^ (getArticleCount() >>> 32));
        result = 31 * result + (getImage() != null ? getImage().hashCode() : 0);
        result = 31 * result + (isFocused() ? 1 : 0);
        result = 31 * result + getSeq();
        return result;
    }

    public static int getNamePriority(Site site){
        if(site != null) {
            if (Constants.RESERVE_TOPIC_HOT.equals(site.getName())) {
                return RESERVE_HOT;
            }
            if (Constants.RESERVE_TOPIC_RECOMMEND.equals(site.getName())) {
                return RESERVE_RECOMMEND;
            }
        }
        return 0;
    }

    public static int getFocusPriority(Site site){
        if(site != null) {
            if(site.isFocused()){
                return 1;
            }
        }
        return 0;
    }

    public static int getSeqPriority(Site site){
        if(site != null) {
            return site.getSeq();
        }
        return 0;
    }

    @Override
    public int compareTo(Site site) {
        int degree = Site.getNamePriority(this) - Site.getNamePriority(site);
        if(degree != 0){
            return -degree;
        }

        degree = Site.getFocusPriority(this) - Site.getFocusPriority(site);
        if(degree != 0){
            return -degree;
        }

        degree = Site.getSeqPriority(this) - Site.getSeqPriority(site);
        if(degree != 0){
            return -degree;
        }
        if(this.getFollowerCount() - site.getFollowerCount() > 0){
            return -1;
        } else if(this.getFollowerCount() - site.getFollowerCount() < 0){
            return 1;
        }
        return 0;
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
        sb.append(", isBlock=").append(isBlock);
        sb.append(", followerCount=").append(followerCount);
        sb.append(", articleCount=").append(articleCount);
        sb.append(", image='").append(image).append('\'');
        sb.append(", isFocused=").append(isFocused);
        sb.append(", seq=").append(seq);
        sb.append('}');
        return sb.toString();
    }

    public void updateData(Site entity) {
        this.name = entity.getName();
        this.updateAt = entity.getUpdateAt();
        this.isBlock = entity.getBlock();
        this.articleCount = entity.getArticleCount();
        this.followerCount = entity.getFollowerCount();
        this.image = entity.getImage();
    }
}
