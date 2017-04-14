package com.klisly.bookbox.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 频道实体类
 * Created by YoKeyword on 15/12/29.
 */
public class WxChannleEntity extends BaseModel{

    private long id;
    private String name;

    public WxChannleEntity(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WxChannleEntity)) return false;

        WxChannleEntity that = (WxChannleEntity) o;

        if (getId() != that.getId()) return false;
        return getName() != null ? getName().equals(that.getName()) : that.getName() == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        return result;
    }

    public static List loadWxDefault() {
        List list = new ArrayList();
        list.add(new WxChannleEntity(1, "热门"));
        list.add(new WxChannleEntity(2, "推荐"));
        list.add(new WxChannleEntity(3, "段子手"));
        list.add(new WxChannleEntity(4, "养生堂"));
        list.add(new WxChannleEntity(5, "私房话"));
        list.add(new WxChannleEntity(6, "八卦精"));
        list.add(new WxChannleEntity(7, "爱生活"));
        list.add(new WxChannleEntity(8, "财经迷"));
        list.add(new WxChannleEntity(9, "科技咖"));
        list.add(new WxChannleEntity(10, "潮人帮"));
        list.add(new WxChannleEntity(11, "辣妈帮"));
        list.add(new WxChannleEntity(12, "点赞党"));
        list.add(new WxChannleEntity(13, "旅行家"));
        list.add(new WxChannleEntity(14, "职场人"));
        list.add(new WxChannleEntity(15, "古今通"));
        list.add(new WxChannleEntity(16, "学霸族"));
        list.add(new WxChannleEntity(17, "星座控"));
        list.add(new WxChannleEntity(18, "体育迷"));
        return list;
    }

    public static List loadWxMy() {
        List list = new ArrayList();
        list.add(new WxChannleEntity(1, "热门"));
        return list;
    }
}
