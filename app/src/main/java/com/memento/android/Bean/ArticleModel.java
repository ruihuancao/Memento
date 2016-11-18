package com.memento.android.bean;

import java.util.List;

public class ArticleModel {

    private int type;

    private String title;

    private String imageUrl;

    private String date;

    private String id;

    private List<ArticleBannerModel> mArticleBannerModels;

    public List<ArticleBannerModel> getArticleBannerModels() {
        return mArticleBannerModels;
    }

    public void setArticleBannerModels(List<ArticleBannerModel> articleBannerModels) {
        mArticleBannerModels = articleBannerModels;
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