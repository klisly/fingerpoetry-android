package com.klisly.bookbox.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.klisly.bookbox.Constants;

public class Topic extends BaseModel implements Comparable<Topic>{
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
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

    private boolean isFocused; // 外来数据, 由User2Topic 获得

    private int seq;

    private static int RESERVE_RECOMMEND = 1;
    private static int RESERVE_HOT = 2;

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
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Topic{");
        sb.append("id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", v=").append(v);
        sb.append(", updateAt=").append(updateAt);
        sb.append(", createAt=").append(createAt);
        sb.append(", articleCount=").append(articleCount);
        sb.append(", isBlock=").append(isBlock);
        sb.append(", isFocused=").append(isFocused);
        sb.append(", seq=").append(seq);
        sb.append(", image=").append(image);
        sb.append(", followerCount=").append(followerCount);
        sb.append('}');
        return sb.toString();
    }

    public static int getNamePriority(Topic topic){
        if(topic != null) {
            if (Constants.RESERVE_TOPIC_HOT.equals(topic.getName())) {
                return RESERVE_HOT;
            }
            if (Constants.RESERVE_TOPIC_RECOMMEND.equals(topic.getName())) {
                return RESERVE_RECOMMEND;
            }
        }
        return 0;
    }

    public static int getFocusPriority(Topic topic){
        if(topic != null) {
            if(topic.isFocused()){
                return 1;
            }
        }
        return 0;
    }

    public static int getSeqPriority(Topic topic){
        if(topic != null) {
            return topic.getSeq();
        }
        return 0;
    }

    @Override
    public int compareTo(Topic topic) {
        int degree = Topic.getNamePriority(this) - Topic.getNamePriority(topic);
        if(degree != 0){
            return -degree;
        }

        degree = Topic.getFocusPriority(this) - Topic.getFocusPriority(topic);
        if(degree != 0){
            return -degree;
        }

        degree = Topic.getSeqPriority(this) - Topic.getSeqPriority(topic);
        if(degree != 0){
            return -degree;
        }

        return 0;
    }

    public void updateData(Topic topic) {
        this.name = topic.getName();
        this.updateAt = topic.getUpdateAt();
        this.isBlock = topic.getBlock();
        this.articleCount = topic.getArticleCount();
        this.followerCount = topic.getFollowerCount();
        this.image = topic.getImage();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Topic)) return false;
        Topic topic = (Topic) o;
        return getId().equals(topic.getId());

    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + (int) (getV() ^ (getV() >>> 32));
        result = 31 * result + (int) (getUpdateAt() ^ (getUpdateAt() >>> 32));
        result = 31 * result + (int) (getCreateAt() ^ (getCreateAt() >>> 32));
        result = 31 * result + isBlock.hashCode();
        result = 31 * result + (int) (getFollowerCount() ^ (getFollowerCount() >>> 32));
        result = 31 * result + (int) (getArticleCount() ^ (getArticleCount() >>> 32));
        result = 31 * result + getImage().hashCode();
        result = 31 * result + (isFocused() ? 1 : 0);
        result = 31 * result + getSeq();
        return result;
    }
}
