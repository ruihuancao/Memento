package com.memento.android.data;

import com.memento.android.util.MD5Util;

import java.io.File;

/**
 * Created by 曹瑞环 on 2016/8/10.
 */
public class KeyGenerator {

    private static final String DEFAULT_KEY_SEPARATOR = "_";

    public static String generator(String key, String ... params){
        StringBuilder builder = new StringBuilder(key);
        for (String str : params){
            builder.append(DEFAULT_KEY_SEPARATOR);
            builder.append(str);
        }
        return MD5Util.md5Hex(builder.toString());
    }
}
