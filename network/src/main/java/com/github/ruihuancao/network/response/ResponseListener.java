package com.github.ruihuancao.network.response;

/**
 * Created by android on 16-12-20.
 */

public interface ResponseListener<T> {

    void onSuccess(T t);

    void onFail();
}
