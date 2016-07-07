package com.memento.android.model;

import android.support.annotation.DrawableRes;

/**
 * 作者: caorh@dxyer.com
 * 日期: 2016-06-27
 * 时间: 18:13
 * 描述：
 * 修改历史：
 */
public class ListItemModel {

    public ListItemModel(String title, String subTitle, String imageUrl) {
        this.title = title;
        this.subTitle = subTitle;
        this.imageUrl = imageUrl;
    }

    public ListItemModel(String title, String subTitle, int drawRes) {
        this.title = title;
        this.subTitle = subTitle;
        this.drawRes = drawRes;
    }

    private String title;

    private String subTitle;

    private String imageUrl;

    @DrawableRes
    private int drawRes;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getDrawRes() {
        return drawRes;
    }

    public void setDrawRes(int drawRes) {
        this.drawRes = drawRes;
    }
}