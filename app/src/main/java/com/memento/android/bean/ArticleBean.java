package com.memento.android.bean;

import java.util.List;

public class ArticleBean {

    private int type;

    private String title;

    private String imageUrl;

    private String date;

    private String id;

    private List<ArticleBannerBean> mArticleBannerBeen;

    public List<ArticleBannerBean> getArticleBannerModels() {
        return mArticleBannerBeen;
    }

    public void setArticleBannerModels(List<ArticleBannerBean> articleBannerBeen) {
        mArticleBannerBeen = articleBannerBeen;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}  