package com.crh.android.common.data;

import com.crh.android.common.util.MD5Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by android on 16-11-22.
 */

public class LocalKey {

    private static final String DEFAULT_KEY_SEPARATOR = "_";
    public static final String DOUBAN_TOP_250_CACHE = "douban_top_250";
    public static final String DOUBAN_COMING_SOON_CACHE = "douban_coming_soon";
    public static final String DOUBAN_THEATERS_CACHE = "douban_Theaters";


    private String mKey;
    private List<String> mParams = new ArrayList<>();

    public LocalKey(String key){
        this.mKey = key;
    }

    public LocalKey addParam(String param){
        mParams.add(param);
        return this;
    }

    public String generator() {
        return generatorKey(mKey, mParams);
    }

    private String generatorKey(String key, List<String> params){
        StringBuilder builder = new StringBuilder(key);
        for (String str : params){
            builder.append(DEFAULT_KEY_SEPARATOR);
            builder.append(str);
        }
        return MD5Util.md5Hex(builder.toString());
    }
}
