package com.klisly.bookbox.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 频道实体类
 * Created by YoKeyword on 15/12/29.
 */
public class ChannleEntity extends BaseModel{

    private long id;
    private String name;
    private int type = 1;// 1 微信频道 2 收藏频道

    public ChannleEntity(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public ChannleEntity(long id, String name, int type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
        if (!(o instanceof ChannleEntity)) return false;

        ChannleEntity that = (ChannleEntity) o;

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
        list.add(new ChannleEntity(1, "热门"));
        list.add(new ChannleEntity(2, "推荐"));
        list.add(new ChannleEntity(3, "段子手"));
        list.add(new ChannleEntity(4, "养生堂"));
        list.add(new ChannleEntity(5, "私房话"));
        list.add(new ChannleEntity(6, "八卦精"));
        list.add(new ChannleEntity(7, "爱生活"));
        list.add(new ChannleEntity(8, "财经迷"));
        list.add(new ChannleEntity(9, "科技咖"));
        list.add(new ChannleEntity(10, "潮人帮"));
        list.add(new ChannleEntity(11, "辣妈帮"));
        list.add(new ChannleEntity(12, "点赞党"));
        list.add(new ChannleEntity(13, "旅行家"));
        list.add(new ChannleEntity(14, "职场人"));
        list.add(new ChannleEntity(15, "古今通"));
        list.add(new ChannleEntity(16, "学霸族"));
        list.add(new ChannleEntity(17, "星座控"));
        list.add(new ChannleEntity(18, "体育迷"));
        return list;
    }

    public static List loadMines() {
        List list = new ArrayList();
        list.add(new ChannleEntity(1, "微信精选", 2));
        list.add(new ChannleEntity(2, "小文学", 2));
        return list;
    }
}
