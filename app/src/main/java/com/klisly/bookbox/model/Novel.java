package com.klisly.bookbox.model;

import com.google.gson.annotations.SerializedName;

public class Novel extends BaseModel {

    /**
     * id : 582ecb34b75d16fa6eb36616
     * title : 大主宰
     * desc : 大千世界，位面交汇，万族林立，群雄荟萃，一位位来自下位面的天之至尊，在这无尽世界，演绎着令人向往的传奇，追求着那主宰之路。无尽火域，炎帝执掌，万火焚苍穹。武境之内，武祖之威，震慑乾坤。西天之殿，百战
     * author : 天蚕土豆
     * href : http://www.biquge.cc/html/60/60821/
     * type : 玄幻奇幻
     * latest : 第一千三百六十三章 下位面
     * updateAt : 2016-11-15
     * __v : 0
     * articleCount : 0
     * followerCount : 0
     * lastCheck : 1479569485964
     * image : http://www.biquge.cc/BookFiles/BookImages/dazhuzai.jpg
     */
    @SerializedName("id")
    private String id;
    private String title;
    private String desc;
    private String author;
    private String href;
    private String type;
    private String latest;
    private String updateAt;
    private int articleCount;
    private int followerCount;
    private long lastCheck;
    private String image;
    private boolean focused;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLatest() {
        return latest;
    }

    public void setLatest(String latest) {
        this.latest = latest;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public int getArticleCount() {
        return articleCount;
    }

    public void setArticleCount(int articleCount) {
        this.articleCount = articleCount;
    }

    public int getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(int followerCount) {
        this.followerCount = followerCount;
    }

    public long getLastCheck() {
        return lastCheck;
    }

    public void setLastCheck(long lastCheck) {
        this.lastCheck = lastCheck;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isFocused() {
        return focused;
    }

    public void setFocused(boolean focused) {
        this.focused = focused;
    }
}
