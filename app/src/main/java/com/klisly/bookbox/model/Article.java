package com.klisly.bookbox.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.klisly.bookbox.utils.ActivityUtil;
import com.klisly.common.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class Article extends BaseModel{

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("site")
    @Expose
    private String site;
    @SerializedName("siteId")
    @Expose
    private String siteId;
    @SerializedName("srcUrl")
    @Expose
    private String srcUrl;
    @SerializedName("isBlock")
    @Expose
    private Boolean isBlock;
    @SerializedName("reason")
    @Expose
    private String reason;
    @SerializedName("checked")
    @Expose
    private Boolean checked;
    @SerializedName("updateAt")
    @Expose
    private long updateAt;
    @SerializedName("createAt")
    @Expose
    private long createAt;
    @SerializedName("commentCount")
    @Expose
    private long commentCount;
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
    @SerializedName("topics")
    @Expose
    private List<String> topics = new ArrayList<String>();
    @SerializedName("publishAt")
    @Expose
    private long publishAt;

    private String img;

    public String getImg() {
        if(StringUtils.isEmpty(img)){
            img = ActivityUtil.genRandomPic();
        }
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     * The content
     */
    public String getContent() {
        return content;
    }

    /**
     *
     * @param content
     * The content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     *
     * @return
     * The author
     */
    public String getAuthor() {
        return author;
    }

    /**
     *
     * @param author
     * The author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     *
     * @return
     * The site
     */
    public String getSite() {
        return site;
    }

    /**
     *
     * @param site
     * The site
     */
    public void setSite(String site) {
        this.site = site;
    }

    /**
     *
     * @return
     * The siteId
     */
    public String getSiteId() {
        return siteId;
    }

    /**
     *
     * @param siteId
     * The siteId
     */
    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    /**
     *
     * @return
     * The srcUrl
     */
    public String getSrcUrl() {
        return srcUrl;
    }

    /**
     *
     * @param srcUrl
     * The srcUrl
     */
    public void setSrcUrl(String srcUrl) {
        this.srcUrl = srcUrl;
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
     * The reason
     */
    public String getReason() {
        return reason;
    }

    /**
     *
     * @param reason
     * The reason
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     *
     * @return
     * The checked
     */
    public Boolean getChecked() {
        return checked;
    }

    /**
     *
     * @param checked
     * The checked
     */
    public void setChecked(Boolean checked) {
        this.checked = checked;
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
     * The commentCount
     */
    public long getCommentCount() {
        return commentCount;
    }

    /**
     *
     * @param commentCount
     * The commentCount
     */
    public void setCommentCount(long commentCount) {
        this.commentCount = commentCount;
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
     * The topics
     */
    public List<String> getTopics() {
        return topics;
    }

    /**
     *
     * @param topics
     * The topics
     */
    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    /**
     *
     * @return
     * The publishAt
     */
    public long getPublishAt() {
        return publishAt;
    }

    /**
     *
     * @param publishAt
     * The publishAt
     */
    public void setPublishAt(long publishAt) {
        this.publishAt = publishAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article)) return false;

        Article article = (Article) o;

        return getId().equals(article.getId());

    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Article{");
        sb.append("id='").append(id).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", content='").append(content).append('\'');
        sb.append(", author='").append(author).append('\'');
        sb.append(", site='").append(site).append('\'');
        sb.append(", siteId='").append(siteId).append('\'');
        sb.append(", srcUrl='").append(srcUrl).append('\'');
        sb.append(", isBlock=").append(isBlock);
        sb.append(", reason='").append(reason).append('\'');
        sb.append(", checked=").append(checked);
        sb.append(", updateAt=").append(updateAt);
        sb.append(", createAt=").append(createAt);
        sb.append(", commentCount=").append(commentCount);
        sb.append(", shareCount=").append(shareCount);
        sb.append(", collectCount=").append(collectCount);
        sb.append(", readCount=").append(readCount);
        sb.append(", heartCount=").append(heartCount);
        sb.append(", topics=").append(topics);
        sb.append(", publishAt=").append(publishAt);
        sb.append('}');
        return sb.toString();
    }
}
