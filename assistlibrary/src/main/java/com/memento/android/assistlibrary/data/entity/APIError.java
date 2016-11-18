package com.memento.android.assistlibrary.data.entity;

/**
 * Created by caoruihuan on 16/9/29.
 */

public class APIError {

    private int statusCode;
    private String message;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
