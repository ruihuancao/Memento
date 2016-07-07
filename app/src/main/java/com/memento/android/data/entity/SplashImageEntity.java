package com.memento.android.data.entity;

import java.util.Locale;

/**
 * Created by android on 16-5-27.
 */
public class SplashImageEntity {

    private static final String PHOTO_URL_BASE = "https://unsplash.it/%d?image=%d";

    /**
     * format : jpeg
     * width : 5616
     * height : 3744
     * filename : 0000_yC-Yzbqy7PY.jpeg
     * id : 0
     * author : Alejandro Escamilla
     * author_url : https://unsplash.com/alejandroescamilla
     * post_url : https://unsplash.com/photos/yC-Yzbqy7PY
     */

    private String format;
    private int width;
    private int height;
    private String filename;
    private int id;
    private String author;
    private String author_url;
    private String post_url;

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor_url() {
        return author_url;
    }

    public void setAuthor_url(String author_url) {
        this.author_url = author_url;
    }

    public String getPost_url() {
        return post_url;
    }

    public void setPost_url(String post_url) {
        this.post_url = post_url;
    }

    public String getPhotoUrl(int requestWidth) {
        return String.format(Locale.getDefault(), PHOTO_URL_BASE, requestWidth, id);
    }
}
