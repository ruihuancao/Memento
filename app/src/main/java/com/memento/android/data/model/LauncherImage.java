package com.memento.android.data.model;

public class LauncherImage {

    private String imageUrl;

    private String imageText;


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageText() {
        return imageText;
    }

    public void setImageText(String imageText) {
        this.imageText = imageText;
    }

    @Override public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("***** LauncherImage Details *****\n");
        stringBuilder.append("imageUrl=" + this.getImageUrl() + "\n");
        stringBuilder.append("imageText=" + this.getImageText() + "\n");
        stringBuilder.append("*******************************");
        return stringBuilder.toString();
    }
}  