package com.memento.android.data.model;

import java.util.List;

public class Article {

    private int type;

    private String title;

    private String imageUrl;

    private String date;

    private String id;

    private List<ArticleBanner> mArticleBanners;

    public List<ArticleBanner> getArticleBanners() {
        return mArticleBanners;
    }

    public void setArticleBanners(List<ArticleBanner> articleBanners) {
        mArticleBanners = articleBanners;
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