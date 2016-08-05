package com.klisly.bookbox.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User extends BaseModel{
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("brief")
    @Expose
    private String brief;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("platform")
    @Expose
    private String platform;
    @SerializedName("updateAt")
    @Expose
    private long updateAt;
    @SerializedName("createAt")
    @Expose
    private long createAt;
    @SerializedName("followingPeople")
    @Expose
    private long followingPeople;
    @SerializedName("followingTopic")
    @Expose
    private long followingTopic;
    @SerializedName("followingSite")
    @Expose
    private long followingSite;
    @SerializedName("followerCount")
    @Expose
    private long followerCount;
    @SerializedName("replyCount")
    @Expose
    private long replyCount;
    @SerializedName("likeCount")
    @Expose
    private long likeCount;
    @SerializedName("isBasicSet")
    @Expose
    private boolean isBasicSet;
    @SerializedName("isBlock")
    @Expose
    private boolean isBlock;
    @SerializedName("role")
    @Expose
    private long role;
    @SerializedName("avatar")
    @Expose
    private String avatar;

    private String token;
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

    /**
     *
     * @return
     * The brief
     */
    public String getBrief() {
        return brief;
    }

    /**
     *
     * @param brief
     * The brief
     */
    public void setBrief(String brief) {
        this.brief = brief;
    }

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
     * The platform
     */
    public String getPlatform() {
        return platform;
    }

    /**
     *
     * @param platform
     * The platform
     */
    public void setPlatform(String platform) {
        this.platform = platform;
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
     * The followingPeople
     */
    public long getFollowingPeople() {
        return followingPeople;
    }

    /**
     *
     * @param followingPeople
     * The followingPeople
     */
    public void setFollowingPeople(long followingPeople) {
        this.followingPeople = followingPeople;
    }

    /**
     *
     * @return
     * The followingTopic
     */
    public long getFollowingTopic() {
        return followingTopic;
    }

    /**
     *
     * @param followingTopic
     * The followingTopic
     */
    public void setFollowingTopic(long followingTopic) {
        this.followingTopic = followingTopic;
    }

    /**
     *
     * @return
     * The followingSite
     */
    public long getFollowingSite() {
        return followingSite;
    }

    /**
     *
     * @param followingSite
     * The followingSite
     */
    public void setFollowingSite(long followingSite) {
        this.followingSite = followingSite;
    }

    /**
     *
     * @return
     * The followerCount
     */
    public long getFollowerCount() {
        return followerCount;
    }

    /**
     *
     * @param followerCount
     * The followerCount
     */
    public void setFollowerCount(long followerCount) {
        this.followerCount = followerCount;
    }

    /**
     *
     * @return
     * The replyCount
     */
    public long getReplyCount() {
        return replyCount;
    }

    /**
     *
     * @param replyCount
     * The replyCount
     */
    public void setReplyCount(long replyCount) {
        this.replyCount = replyCount;
    }

    /**
     *
     * @return
     * The likeCount
     */
    public long getLikeCount() {
        return likeCount;
    }

    /**
     *
     * @param likeCount
     * The likeCount
     */
    public void setLikeCount(long likeCount) {
        this.likeCount = likeCount;
    }

    /**
     *
     * @return
     * The isBlock
     */
    public boolean isIsBlock() {
        return isBlock;
    }

    /**
     *
     * @param isBlock
     * The isBlock
     */
    public void setIsBlock(boolean isBlock) {
        this.isBlock = isBlock;
    }

    /**
     *
     * @return
     * The role
     */
    public long getRole() {
        return role;
    }

    /**
     *
     * @param role
     * The role
     */
    public void setRole(long role) {
        this.role = role;
    }

    /**
     *
     * @return
     * The avatar
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     *
     * @param avatar
     * The avatar
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean isBasicSet() {
        return isBasicSet;
    }

    public void setBasicSet(boolean basicSet) {
        isBasicSet = basicSet;
    }

    public boolean isBlock() {
        return isBlock;
    }

    public void setBlock(boolean block) {
        isBlock = block;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("name='").append(name).append('\'');
        sb.append(", brief='").append(brief).append('\'');
        sb.append(", id='").append(id).append('\'');
        sb.append(", platform='").append(platform).append('\'');
        sb.append(", updateAt=").append(updateAt);
        sb.append(", createAt=").append(createAt);
        sb.append(", followingPeople=").append(followingPeople);
        sb.append(", followingTopic=").append(followingTopic);
        sb.append(", followingSite=").append(followingSite);
        sb.append(", followerCount=").append(followerCount);
        sb.append(", replyCount=").append(replyCount);
        sb.append(", likeCount=").append(likeCount);
        sb.append(", isBasicSet=").append(isBasicSet);
        sb.append(", isBlock=").append(isBlock);
        sb.append(", role=").append(role);
        sb.append(", token=").append(token);
        sb.append(", avatar='").append(avatar).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
