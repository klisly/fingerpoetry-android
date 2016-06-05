/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package com.klisly.bookbox.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User extends BaseModel{
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("biref")
    @Expose
    private String biref;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("updateAt")
    @Expose
    private Long updateAt;
    @SerializedName("createAt")
    @Expose
    private Long createAt;
    @SerializedName("likeNum")
    @Expose
    private Integer likeNum;
    @SerializedName("avatar")
    @Expose
    private String avatar;

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
     * The biref
     */
    public String getBiref() {
        return biref;
    }

    /**
     *
     * @param biref
     * The biref
     */
    public void setBiref(String biref) {
        this.biref = biref;
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
     * The updateAt
     */
    public Long getUpdateAt() {
        return updateAt;
    }

    /**
     *
     * @param updateAt
     * The updateAt
     */
    public void setUpdateAt(Long updateAt) {
        this.updateAt = updateAt;
    }

    /**
     *
     * @return
     * The createAt
     */
    public Long getCreateAt() {
        return createAt;
    }

    /**
     *
     * @param createAt
     * The createAt
     */
    public void setCreateAt(Long createAt) {
        this.createAt = createAt;
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
     * The avatar
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     *
     * @param avatar
     * The avatar
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("User{");
        sb.append("id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", biref='").append(biref).append('\'');
        sb.append(", v=").append(v);
        sb.append(", updateAt=").append(updateAt);
        sb.append(", createAt=").append(createAt);
        sb.append(", likeNum=").append(likeNum);
        sb.append(", avatar='").append(avatar).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
