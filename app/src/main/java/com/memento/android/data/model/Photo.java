package com.memento.android.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Locale;

/**
 * Created by android on 16-6-13.
 */
public class Photo implements Parcelable {

    private static final String PHOTO_URL_BASE = "https://unsplash.it/%d?image=%d";

    private int id;
    private String author;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhotoUrl(int requestWidth) {
        return String.format(Locale.getDefault(), PHOTO_URL_BASE, requestWidth, id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.author);
    }

    public Photo() {
    }

    public Photo(int id, String author) {
        this.id = id;
        this.author = author;
    }

    protected Photo(Parcel in) {
        this.id = in.readInt();
        this.author = in.readString();
    }

    public static final Parcelable.Creator<Photo> CREATOR = new Parcelable.Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel source) {
            return new Photo(source);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };
}
