package com.klisly.bookbox.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Joke extends BaseModel {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("bad")
    @Expose
    private Integer bad;
    @SerializedName("pub_time")
    @Expose
    private Integer pubTime;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("good")
    @Expose
    private Integer good;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getBad() {
        return bad;
    }

    public void setBad(Integer bad) {
        this.bad = bad;
    }

    public Integer getPubTime() {
        return pubTime;
    }

    public void setPubTime(Integer pubTime) {
        this.pubTime = pubTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getGood() {
        return good;
    }

    public void setGood(Integer good) {
        this.good = good;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Joke{");
        sb.append("title='").append(title).append('\'');
        sb.append(", bad=").append(bad);
        sb.append(", pubTime=").append(pubTime);
        sb.append(", type='").append(type).append('\'');
        sb.append(", id='").append(id).append('\'');
        sb.append(", content='").append(content).append('\'');
        sb.append(", good=").append(good);
        sb.append('}');
        return sb.toString();
    }
}
