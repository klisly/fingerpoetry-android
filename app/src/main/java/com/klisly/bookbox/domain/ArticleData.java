package com.klisly.bookbox.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.klisly.bookbox.model.Article;
import com.klisly.bookbox.model.BaseModel;
import com.klisly.bookbox.model.User2Article;

public class ArticleData extends BaseModel{
    @SerializedName("user2article")
    @Expose
    private User2Article user2article;
    @SerializedName("article")
    @Expose
    private Article article;

    public User2Article getUser2article() {
        return user2article;
    }

    public void setUser2article(User2Article user2article) {
        this.user2article = user2article;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ArticleData{");
        sb.append("user2article=").append(user2article);
        sb.append(", article=").append(article);
        sb.append('}');
        return sb.toString();
    }
}
