package com.klisly.bookbox.model;

import com.google.gson.annotations.SerializedName;

public class User2Novel {

    /**
     * _id : 58368261dffdde8d6950f40d
     * uid : 57908ed58b802e8b81d5ba4f
     * nid : 58368261dffdde8d6950f40c
     * title : 鹰墓
     * author : 希爷
     * href : http://www.biquge.cc/html/67/67279/
     * type : 玄幻奇幻
     * latest : 第十一章 王后的后花园
     * image : http://www.biquge.cc/images/nopic.gif
     * lastUpdate : 0
     * lastRead : 0
     * __v : 0
     */

    @SerializedName("_id")
    private String id;
    private String uid;
    private String nid;
    private String title;
    private String author;
    private String href;
    private String type;
    private String latest;
    private String image;
    private int lastUpdate;
    private int lastRead;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(int lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public int getLastRead() {
        return lastRead;
    }

    public void setLastRead(int lastRead) {
        this.lastRead = lastRead;
    }
}
