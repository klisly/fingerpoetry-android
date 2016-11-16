package com.klisly.bookbox.model;

import java.util.List;

/**
 * Created by wizardholy on 2016/11/17.
 */

public class Magazine {

    /**
     * _id : 58116325c6f61a3d6556243b
     * no : 2016-10-27早报
     * __v : 0
     * updateAt : 1477534501250
     * createAt : 1477534501250
     * isBlock : false
     * articles : [{"publishAt":1475241882909,"topics":["倚栏轩文学网","散文精选","散文随笔","美文"],"heartCount":20,"readCount":857,"collectCount":11,"shareCount":9,"commentCount":0,"createAt":1476178202360,"updateAt":1476178202360,"checked":false,"reason":"请稍候小小君审核!","isBlock":false,"srcUrl":"http://m.elanp.com/a/77836.html","siteId":"57ab60c6f4ec39a607e5b7b8","site":"倚栏轩","title":"穿着布鞋走天下","_id":"57fcb11a63eaff00102e0fde"},{"publishAt":1475241882909,"topics":["倚栏轩文学网","散文精选","亲情散文","美文"],"heartCount":20,"readCount":955,"collectCount":2,"shareCount":1,"commentCount":0,"createAt":1476178202332,"updateAt":1476178202332,"checked":false,"reason":"请稍候小小君审核!","isBlock":false,"srcUrl":"http://m.elanp.com/sanwen/135358.html","siteId":"57ab60c6f4ec39a607e5b7b8","site":"倚栏轩","title":"父亲的训语","_id":"57fcb11a63eaff00102e0fdd"},{"publishAt":1475241882909,"topics":["倚栏轩文学网","散文精选","散文随笔","美文"],"heartCount":20,"readCount":726,"collectCount":1,"shareCount":16,"commentCount":0,"createAt":1476178202122,"updateAt":1476178202122,"checked":false,"reason":"请稍候小小君审核!","isBlock":false,"srcUrl":"http://m.elanp.com/a/86925.html","siteId":"57ab60c6f4ec39a607e5b7b8","site":"倚栏轩","title":"油菜花开","_id":"57fcb11a63eaff00102e0fd6"},{"publishAt":1475241882909,"topics":["倚栏轩文学网","散文精选","短篇散文","美文"],"heartCount":20,"readCount":1307,"collectCount":1,"shareCount":12,"commentCount":0,"createAt":1476167406310,"updateAt":1476167406310,"checked":false,"reason":"请稍候小小君审核!","isBlock":false,"srcUrl":"http://m.elanp.com/sanwen/131306.html","siteId":"57ab60c6f4ec39a607e5b7b8","site":"倚栏轩","title":"苹果长叶了","_id":"57fc86ee63eaff00102dd712"},{"publishAt":1475241882909,"topics":["倚栏轩文学网","散文精选","短篇散文","美文"],"heartCount":20,"readCount":678,"collectCount":6,"shareCount":10,"commentCount":0,"createAt":1476167406266,"updateAt":1476167406266,"checked":false,"reason":"请稍候小小君审核!","isBlock":false,"srcUrl":"http://m.elanp.com/sanwen/131776.html","siteId":"57ab60c6f4ec39a607e5b7b8","site":"倚栏轩","title":"当小乌龟不在身边的时候","_id":"57fc86ee63eaff00102dd710"},{"publishAt":1475241882909,"topics":["倚栏轩文学网","散文精选","短篇散文","美文"],"heartCount":20,"readCount":1626,"collectCount":13,"shareCount":7,"commentCount":0,"createAt":1476167406201,"updateAt":1476167406201,"checked":false,"reason":"请稍候小小君审核!","isBlock":false,"srcUrl":"http://m.elanp.com/sanwen/132813.html","siteId":"57ab60c6f4ec39a607e5b7b8","site":"倚栏轩","title":"无意中影响了别人","_id":"57fc86ee63eaff00102dd70d"},{"publishAt":1475241882909,"topics":["倚栏轩文学网","经典语句","搞笑的话",""],"heartCount":20,"readCount":210,"collectCount":10,"shareCount":13,"commentCount":0,"createAt":1476167403561,"updateAt":1476167403561,"checked":false,"reason":"请稍候小小君审核!","isBlock":false,"srcUrl":"http://m.elanp.com/yuju/90515.html","siteId":"57ab60c6f4ec39a607e5b7b8","site":"倚栏轩","title":"某次看电视，讲一个人种白萝卜，到了秋天竟变成了胡萝卜","_id":"57fc86eb63eaff00102dd6ac"},{"publishAt":1475241882909,"topics":["倚栏轩文学网","散文精选","情感散文","美文"],"heartCount":20,"readCount":223,"collectCount":13,"shareCount":3,"commentCount":0,"createAt":1476167402384,"updateAt":1476167402384,"checked":false,"reason":"请稍候小小君审核!","isBlock":false,"srcUrl":"http://m.elanp.com/sanwen/135334.html","siteId":"57ab60c6f4ec39a607e5b7b8","site":"倚栏轩","title":"父母跟我\u201c捉迷藏\u201d","_id":"57fc86ea63eaff00102dd68d"},{"publishAt":1475241882909,"topics":["倚栏轩文学网","散文精选","短篇散文","美文"],"heartCount":20,"readCount":796,"collectCount":4,"shareCount":17,"commentCount":0,"createAt":1476167402508,"updateAt":1476167402508,"checked":false,"reason":"请稍候小小君审核!","isBlock":false,"srcUrl":"http://m.elanp.com/sanwen/135341.html","siteId":"57ab60c6f4ec39a607e5b7b8","site":"倚栏轩","title":"找回童心","_id":"57fc86ea63eaff00102dd690"},{"publishAt":1475241882909,"topics":["倚栏轩文学网","散文精选","情感散文","美文"],"heartCount":20,"readCount":1272,"collectCount":3,"shareCount":17,"commentCount":0,"createAt":1476167402434,"updateAt":1476167402434,"checked":false,"reason":"请稍候小小君审核!","isBlock":false,"srcUrl":"http://m.elanp.com/sanwen/135337.html","siteId":"57ab60c6f4ec39a607e5b7b8","site":"倚栏轩","title":"感动常在","_id":"57fc86ea63eaff00102dd68e"}]
     */

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
