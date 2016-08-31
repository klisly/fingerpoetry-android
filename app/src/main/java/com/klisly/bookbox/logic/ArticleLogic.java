package com.klisly.bookbox.logic;

import com.google.gson.reflect.TypeToken;
import com.klisly.bookbox.Constants;
import com.klisly.bookbox.listener.OnDataChangeListener;
import com.klisly.bookbox.model.Article;
import com.klisly.common.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArticleLogic extends BaseLogic {

    private static ArticleLogic instance;
    private Map<String, List<Article>> articles = new HashMap<>();
    private static final String PRE_KEY = "PRE_ARTICLES";
    private static final Article empty = new Article();
    /**
     * 获取ConversationLogic单例对象
     *
     * @return single instance of ConversationLogic
     */
    public static ArticleLogic getInstance() {
        if (instance == null) {
            synchronized (ArticleLogic.class) {
                if (instance == null) {
                    instance = new ArticleLogic();
                }
            }
        }
        return instance;
    }

    public ArticleLogic() {
        empty.setId(Constants.INVALID_ARTICLE_ID);
        initArticles();
    }

    /**
     * 初始化需要提供出去的数据
     */
    private void initArticles() {
        String data = preferenceUtils.getValue(PRE_KEY, "");
        if (StringUtils.isNotEmpty(data)) {
            articles = gson.fromJson(data, new TypeToken<Map<String, List<Article>>>() {
            }.getType());
        }
    }

    private void persistence() {
        String data = gson.toJson(articles);
        preferenceUtils.setValue(PRE_KEY, data);
    }


    public void updateArticles(String key, List<Article> data) {
        data.add(empty);
        if(articles.get(key) == null){
            articles.put(key, data);
        } else {
            articles.get(key).remove(empty);
            articles.get(key).addAll(0, data);
        }
        notifyDataChange(key);
    }

    public List<Article> getArticles(String key) {
        List<Article> list;
        list = articles.get(key);
        if (list == null) {
            list = new ArrayList<>();
        }
        articles.put(key, list);
        return articles.get(key);
    }

    protected void notifyDataChange(String key) {
        if (listeners.size() <= 0) {
            return;
        }
        OnDataChangeListener listener = listeners.get(key);
        if(listener != null){
            listener.onDataChange();
        }
    }
}
