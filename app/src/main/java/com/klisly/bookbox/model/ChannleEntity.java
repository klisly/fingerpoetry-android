package com.klisly.bookbox.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.klisly.bookbox.BookBoxApplication;
import com.klisly.bookbox.Constants;
import com.klisly.bookbox.domain.ApiResult;

import java.util.ArrayList;
import java.util.List;

/**
 * 频道实体类
 * Created by YoKeyword on 15/12/29.
 */
public class ChannleEntity extends BaseModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;

    private int type = 1;// 1 微信频道 2 收藏频道

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("WxCate{");
        sb.append("type='").append(type).append('\'');
        sb.append(", id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public ChannleEntity() {
    }

    public ChannleEntity(int id, String name, int type) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChannleEntity)) return false;
        ChannleEntity that = (ChannleEntity) o;
        if (getId() - that.getId() != 0) return false;
        return getName() != null ? getName().equals(that.getName()) : that.getName() == null;

    }

    @Override
    public int hashCode() {
        int result = (getId() ^ (getId() >>> 32));
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        return result;
    }

    public static List loadWxDefault() {
        Gson gson = new Gson();
        String data = BookBoxApplication.getInstance().getPreferenceUtils().getValue(Constants.DEFAULT_WX_CATES, defaultCates);
        return gson.fromJson(data, new TypeToken<List<ChannleEntity>>() {
        }.getType());
    }

    public static void updateDefaultWxCates(List<ChannleEntity> res) {
        Gson gson = new Gson();
        String wcstr = gson.toJson(res);
        BookBoxApplication.getInstance().getPreferenceUtils().setValue(Constants.DEFAULT_WX_CATES, wcstr);
    }

    public static List loadMines() {
        List list = new ArrayList();
        list.add(new ChannleEntity(1, "微信精选", 2));
        list.add(new ChannleEntity(2, "小文学", 2));
        return list;
    }

    public static String defaultCates = "[\n" +
            "        {\n" +
            "            \"_id\": \"5aca403693b43e9e303a39b9\",\n" +
            "            \"src\": \"sogou.com\",\n" +
            "            \"id\": 0,\n" +
            "            \"name\": \"热门\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5aca403693b43e9e303a39bb\",\n" +
            "            \"src\": \"sogou.com\",\n" +
            "            \"id\": 1,\n" +
            "            \"name\": \"搞笑\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5aca403693b43e9e303a39bd\",\n" +
            "            \"src\": \"sogou.com\",\n" +
            "            \"id\": 2,\n" +
            "            \"name\": \"养生堂\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5aca403693b43e9e303a39bf\",\n" +
            "            \"src\": \"sogou.com\",\n" +
            "            \"id\": 3,\n" +
            "            \"name\": \"私房话\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5aca403693b43e9e303a39c1\",\n" +
            "            \"src\": \"sogou.com\",\n" +
            "            \"id\": 4,\n" +
            "            \"name\": \"八卦精\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5aca403693b43e9e303a39c3\",\n" +
            "            \"src\": \"sogou.com\",\n" +
            "            \"id\": 5,\n" +
            "            \"name\": \"科技咖\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5aca403693b43e9e303a39c5\",\n" +
            "            \"src\": \"sogou.com\",\n" +
            "            \"id\": 6,\n" +
            "            \"name\": \"财经迷\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5aca403693b43e9e303a39c7\",\n" +
            "            \"src\": \"sogou.com\",\n" +
            "            \"id\": 7,\n" +
            "            \"name\": \"汽车控\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5aca403693b43e9e303a39c9\",\n" +
            "            \"src\": \"sogou.com\",\n" +
            "            \"id\": 8,\n" +
            "            \"name\": \"生活家\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5aca403693b43e9e303a39cb\",\n" +
            "            \"src\": \"sogou.com\",\n" +
            "            \"id\": 9,\n" +
            "            \"name\": \"时尚圈\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5aca403693b43e9e303a39cd\",\n" +
            "            \"src\": \"sogou.com\",\n" +
            "            \"id\": 10,\n" +
            "            \"name\": \"育儿\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5aca403693b43e9e303a39cf\",\n" +
            "            \"src\": \"sogou.com\",\n" +
            "            \"id\": 11,\n" +
            "            \"name\": \"旅游\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5aca403693b43e9e303a39d1\",\n" +
            "            \"src\": \"sogou.com\",\n" +
            "            \"id\": 12,\n" +
            "            \"name\": \"职场\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5aca403693b43e9e303a39d3\",\n" +
            "            \"src\": \"sogou.com\",\n" +
            "            \"id\": 13,\n" +
            "            \"name\": \"美食\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5aca403693b43e9e303a39d5\",\n" +
            "            \"src\": \"sogou.com\",\n" +
            "            \"id\": 14,\n" +
            "            \"name\": \"历史\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5aca403693b43e9e303a39d7\",\n" +
            "            \"src\": \"sogou.com\",\n" +
            "            \"id\": 15,\n" +
            "            \"name\": \"教育\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5aca403693b43e9e303a39d9\",\n" +
            "            \"src\": \"sogou.com\",\n" +
            "            \"id\": 16,\n" +
            "            \"name\": \"星座\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5aca403693b43e9e303a39db\",\n" +
            "            \"src\": \"sogou.com\",\n" +
            "            \"id\": 17,\n" +
            "            \"name\": \"体育\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5aca403693b43e9e303a39dd\",\n" +
            "            \"src\": \"sogou.com\",\n" +
            "            \"id\": 18,\n" +
            "            \"name\": \"军事\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5aca403693b43e9e303a39df\",\n" +
            "            \"src\": \"sogou.com\",\n" +
            "            \"id\": 19,\n" +
            "            \"name\": \"游戏\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5aca403693b43e9e303a39e1\",\n" +
            "            \"src\": \"sogou.com\",\n" +
            "            \"id\": 20,\n" +
            "            \"name\": \"萌宠\"\n" +
            "        }\n" +
            "    ]";
}
