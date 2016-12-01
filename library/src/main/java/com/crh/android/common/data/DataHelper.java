package com.crh.android.common.data;

import com.crh.android.common.util.MD5Util;

/**
 * Created by android on 16-11-22.
 */

public class DataHelper {

    private static final String DEFAULT_KEY_SEPARATOR = "_";

    public static String generatorKey(String key, String ... params){
        StringBuilder builder = new StringBuilder(key);
        for (String str : params){
            builder.append(DEFAULT_KEY_SEPARATOR);
            builder.append(str);
        }
        return MD5Util.md5Hex(builder.toString());
    }
}
