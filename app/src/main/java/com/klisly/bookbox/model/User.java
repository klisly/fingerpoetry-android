package com.klisly.bookbox.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.klisly.common.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class User extends BaseModel{

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("brief")
    @Expose
    private String brief;
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
    @SerializedName("toReadCount")
    @Expose
    private long toReadCount;
    @SerializedName("shareCount")
    @Expose
    private long shareCount;
    @SerializedName("collectCount")
    @Expose
    private long collectCount;
    @SerializedName("readCount")
    @Expose
    private long readCount;
    @SerializedName("heartCount")
    @Expose
    private long heartCount;
    @SerializedName("isBasicSet")
    @Expose
    private Boolean isBasicSet;
    @SerializedName("isBlock")
    @Expose
    private Boolean isBlock;
    @SerializedName("role")
    @Expose
    private long role;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("deviceToken")
    @Expose
    private String deviceToken;

    private List<ChannleEntity> wxChannles = new ArrayList<>();
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
        if(StringUtils.isEmpty(name)){
            name = "";
        }
        if(name.length()> 15){
            name = name.substring(0, 15);
        }
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
     * The toReadCount
     */
    public long getToReadCount() {
        return toReadCount;
    }

    /**
     *
     * @param toReadCount
     * The toReadCount
     */
    public void setToReadCount(long toReadCount) {
        this.toReadCount = toReadCount;
    }

    /**
     *
     * @return
     * The shareCount
     */
    public long getShareCount() {
        return shareCount;
    }

    /**
     *
     * @param shareCount
     * The shareCount
     */
    public void setShareCount(long shareCount) {
        this.shareCount = shareCount;
    }

    /**
     *
     * @return
     * The collectCount
     */
    public long getCollectCount() {
        return collectCount;
    }

    /**
     *
     * @param collectCount
     * The collectCount
     */
    public void setCollectCount(long collectCount) {
        this.collectCount = collectCount;
    }

    /**
     *
     * @return
     * The readCount
     */
    public long getReadCount() {
        return readCount;
    }

    /**
     *
     * @param readCount
     * The readCount
     */
    public void setReadCount(long readCount) {
        this.readCount = readCount;
    }

    /**
     *
     * @return
     * The heartCount
     */
    public long getHeartCount() {
        return heartCount;
    }

    /**
     *
     * @param heartCount
     * The heartCount
     */
    public void setHeartCount(long heartCount) {
        this.heartCount = heartCount;
    }

    /**
     *
     * @return
     * The isBasicSet
     */
    public Boolean getIsBasicSet() {
        return isBasicSet;
    }

    /**
     *
     * @param isBasicSet
     * The isBasicSet
     */
    public void setIsBasicSet(Boolean isBasicSet) {
        this.isBasicSet = isBasicSet;
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

    public Boolean getBasicSet() {
        return isBasicSet;
    }

    public void setBasicSet(Boolean basicSet) {
        isBasicSet = basicSet;
    }

    public Boolean getBlock() {
        return isBlock;
    }

    public void setBlock(Boolean block) {
        isBlock = block;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    /**
     *
     * @param avatar
     * The avatar
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<ChannleEntity> getWxChannles() {
        if(wxChannles.size() == 0){
            wxChannles = ChannleEntity.loadWxDefault();
        }
        return wxChannles;
    }

    public void setWxChannles(List<ChannleEntity> wxChannles) {
        this.wxChannles = wxChannles;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("User{");
        sb.append("id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", brief='").append(brief).append('\'');
        sb.append(", platform='").append(platform).append('\'');
        sb.append(", updateAt=").append(updateAt);
        sb.append(", createAt=").append(createAt);
        sb.append(", followingPeople=").append(followingPeople);
        sb.append(", followingTopic=").append(followingTopic);
        sb.append(", followingSite=").append(followingSite);
        sb.append(", followerCount=").append(followerCount);
        sb.append(", replyCount=").append(replyCount);
        sb.append(", likeCount=").append(likeCount);
        sb.append(", toReadCount=").append(toReadCount);
        sb.append(", shareCount=").append(shareCount);
        sb.append(", collectCount=").append(collectCount);
        sb.append(", readCount=").append(readCount);
        sb.append(", heartCount=").append(heartCount);
        sb.append(", isBasicSet=").append(isBasicSet);
        sb.append(", isBlock=").append(isBlock);
        sb.append(", role=").append(role);
        sb.append(", deviceToken=").append(deviceToken);
        sb.append(", avatar='").append(avatar).append('\'');
        sb.append(", wxChannles=").append(wxChannles);
        sb.append('}');
        return sb.toString();
    }
}
