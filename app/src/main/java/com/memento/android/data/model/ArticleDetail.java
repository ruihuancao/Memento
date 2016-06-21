package com.memento.android.data.model;

import java.util.List;

/**
 * 作者: caorh@dxyer.com
 * 日期: 2016-04-13
 * 时间: 09:23
 * 描述：
 * 修改历史：
 */
public class ArticleDetail {

    private String id;

    private String title;

    private String content;

    private String imageUrl;

    private List<String> css;

    public List<String> getCss() {
        return css;
    }

    public void setCss(List<String> css) {
        this.css = css;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}