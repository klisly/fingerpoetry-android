package com.klisly.bookbox.model;

import java.util.List;

/**
 * Created by wizardholy on 2016/11/17.
 */

public class Magazine extends BaseModel{
    private String _id;
    private String no;
    private long updateAt;
    private long createAt;
    private boolean isBlock;
    /**
     * publishAt : 1475241882909
     * topics : ["倚栏轩文学网","散文精选","散文随笔","美文"]
     * heartCount : 20
     * readCount : 857
     * collectCount : 11
     * shareCount : 9
     * commentCount : 0
     * createAt : 1476178202360
     * updateAt : 1476178202360
     * checked : false
     * reason : 请稍候小小君审核!
     * isBlock : false
     * srcUrl : http://m.elanp.com/a/77836.html
     * siteId : 57ab60c6f4ec39a607e5b7b8
     * site : 倚栏轩
     * title : 穿着布鞋走天下
     * _id : 57fcb11a63eaff00102e0fde
     */

    private List<Article> articles;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
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

    public boolean isIsBlock() {
        return isBlock;
    }

    public void setIsBlock(boolean isBlock) {
        this.isBlock = isBlock;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

}
