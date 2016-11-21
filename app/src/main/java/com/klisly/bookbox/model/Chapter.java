package com.klisly.bookbox.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by wizardholy on 2016/11/21.
 */

public class Chapter extends Article {
    @SerializedName("no")
    @Expose
    private String no;

    @SerializedName("nname")
    @Expose
    private String nname;
    @SerializedName("nid")
    @Expose
    private String nid;

    @SerializedName("href")
    @Expose
    private String srcUrl;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getNname() {
        return nname;
    }

    public void setNname(String nname) {
        this.nname = nname;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    @Override
    public String getSrcUrl() {
        return srcUrl;
    }

    @Override
    public void setSrcUrl(String srcUrl) {
        this.srcUrl = srcUrl;
    }
}
