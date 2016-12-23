package com.memento.android.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by caoruihuan on 16/9/29.
 */

public class LeanCloudUserBean extends APIErrorBean implements Parcelable {


    /**
     * sessionToken : ttf1v91juqm0yruzgcjfnxuz5
     * updatedAt : 2016-09-29T03:01:47.706Z
     * phone : 18612340000
     * objectId : 57ec841b67f35600581fbcbd
     * username : hjiandg
     * createdAt : 2016-09-29T03:01:47.706Z
     * emailVerified : false
     * mobilePhoneVerified : false
     */

    private String username;
    private String password;
    private String email;
    private String phone;
    private String avatar;

    private String sessionToken;
    private String updatedAt;
    private String objectId;
    private String createdAt;
    private boolean emailVerified;
    private boolean mobilePhoneVerified;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public boolean isMobilePhoneVerified() {
        return mobilePhoneVerified;
    }

    public void setMobilePhoneVerified(boolean mobilePhoneVerified) {
        this.mobilePhoneVerified = mobilePhoneVerified;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.username);
        dest.writeString(this.password);
        dest.writeString(this.email);
        dest.writeString(this.phone);
        dest.writeString(this.avatar);
        dest.writeString(this.sessionToken);
        dest.writeString(this.updatedAt);
        dest.writeString(this.objectId);
        dest.writeString(this.createdAt);
        dest.writeByte(this.emailVerified ? (byte) 1 : (byte) 0);
        dest.writeByte(this.mobilePhoneVerified ? (byte) 1 : (byte) 0);
    }

    public LeanCloudUserBean() {
    }

    protected LeanCloudUserBean(Parcel in) {
        this.username = in.readString();
        this.password = in.readString();
        this.email = in.readString();
        this.phone = in.readString();
        this.avatar = in.readString();
        this.sessionToken = in.readString();
        this.updatedAt = in.readString();
        this.objectId = in.readString();
        this.createdAt = in.readString();
        this.emailVerified = in.readByte() != 0;
        this.mobilePhoneVerified = in.readByte() != 0;
    }

    public static final Parcelable.Creator<LeanCloudUserBean> CREATOR = new Parcelable.Creator<LeanCloudUserBean>() {
        @Override
        public LeanCloudUserBean createFromParcel(Parcel source) {
            return new LeanCloudUserBean(source);
        }

        @Override
        public LeanCloudUserBean[] newArray(int size) {
            return new LeanCloudUserBean[size];
        }
    };
}
