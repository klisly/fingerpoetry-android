package com.klisly.bookbox.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
    @SerializedName("__v")
    @Expose
    private Integer v;
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
    @SerializedName("readNum")
    @Expose
    private Integer readNum;
    @SerializedName("commentNum")
    @Expose
    private Integer commentNum;
    @SerializedName("likeNum")
    @Expose
    private Integer likeNum;
    @SerializedName("topics")
    @Expose
    private List<String> topics = new ArrayList<String>();
    @SerializedName("publishAt")
    @Expose
    private long publishAt;

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
        return content == null ? "" : content;
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
     * The v
     */
    public Integer getV() {
        return v;
    }

    /**
     *
     * @param v
     * The __v
     */
    public void setV(Integer v) {
        this.v = v;
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
     * The readNum
     */
    public Integer getReadNum() {
        return readNum;
    }

    /**
     *
     * @param readNum
     * The readNum
     */
    public void setReadNum(Integer readNum) {
        this.readNum = readNum;
    }

    /**
     *
     * @return
     * The commentNum
     */
    public Integer getCommentNum() {
        return commentNum;
    }

    /**
     *
     * @param commentNum
     * The commentNum
     */
    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    /**
     *
     * @return
     * The likeNum
     */
    public Integer getLikeNum() {
        return likeNum;
    }

    /**
     *
     * @param likeNum
     * The likeNum
     */
    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
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

    public long getPublishAt() {
        return publishAt;
    }

    public void setPublishAt(long publishAt) {
        this.publishAt = publishAt;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Article{");
        sb.append("id='").append(id).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", content='").append(content).append('\'');
        sb.append(", author='").append(author).append('\'');
        sb.append(", site='").append(site).append('\'');
        sb.append(", siteId='").append(siteId).append('\'');
        sb.append(", srcUrl='").append(srcUrl).append('\'');
        sb.append(", v=").append(v);
        sb.append(", isBlock=").append(isBlock);
        sb.append(", reason='").append(reason).append('\'');
        sb.append(", checked=").append(checked);
        sb.append(", updateAt=").append(updateAt);
        sb.append(", createAt=").append(createAt);
        sb.append(", readNum=").append(readNum);
        sb.append(", commentNum=").append(commentNum);
        sb.append(", likeNum=").append(likeNum);
        sb.append(", topics=").append(topics);
        sb.append(", publishAt=").append(publishAt);
        sb.append('}');
        return sb.toString();
    }
}
