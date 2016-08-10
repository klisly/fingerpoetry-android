package com.klisly.bookbox.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User2Topic extends BaseModel{

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("topicId")
    @Expose
    private String topicId;
    @SerializedName("topicName")
    @Expose
    private String topicName;
    @SerializedName("updateAt")
    @Expose
    private long updateAt;
    @SerializedName("createAt")
    @Expose
    private long createAt;
    @SerializedName("seq")
    @Expose
    private Integer seq;
    @SerializedName("isBlock")
    @Expose
    private Boolean isBlock; // 是否被取消关注

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
     * The topicId
     */
    public String getTopicId() {
        return topicId;
    }

    /**
     *
     * @param topicId
     * The topicId
     */
    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    /**
     *
     * @return
     * The topicName
     */
    public String getTopicName() {
        return topicName;
    }

    /**
     *
     * @param topicName
     * The topicName
     */
    public void setTopicName(String topicName) {
        this.topicName = topicName;
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

    /**
     *
     * @return
     * The seq
     */
    public Integer getSeq() {
        return seq;
    }

    /**
     *
     * @param seq
     * The seq
     */
    public void setSeq(Integer seq) {
        this.seq = seq;
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User2Topic{");
        sb.append("id='").append(id).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        sb.append(", topicId='").append(topicId).append('\'');
        sb.append(", topicName='").append(topicName).append('\'');
        sb.append(", updateAt=").append(updateAt);
        sb.append(", createAt=").append(createAt);
        sb.append(", seq=").append(seq);
        sb.append(", isBlock=").append(isBlock);
        sb.append('}');
        return sb.toString();
    }
}
