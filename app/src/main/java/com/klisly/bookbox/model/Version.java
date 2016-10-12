package com.klisly.bookbox.model;

public class Version extends BaseModel {

    /**
     * _id : 57fde0084a35818532df30f0
     * content : 优化小bug，假如每日周刊功能
     * type : android
     * url : http://localhost:3000/softs/com.klisly.bookbox_1.0.2_3.apk
     * __v : 0
     * updateAt : 1476253519475
     * createAt : 1476253519475
     * isBlock : false
     * version : 2
     */

    private String content;
    private String type;
    private String url;
    private int version;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Version{");
        sb.append("content='").append(content).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", url='").append(url).append('\'');
        sb.append(", version=").append(version);
        sb.append('}');
        return sb.toString();
    }
}
