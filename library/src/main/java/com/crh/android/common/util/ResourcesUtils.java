package com.crh.android.common.util;

import android.content.Context;
import android.text.TextUtils;

/**
 * User: caoruihuan(cao_ruihuan@163.com)
 * Date: 2016-03-30
 * Time: 13:26
 *
 */
public class ResourcesUtils {

    public static int getResourceId( Context context, String resourcesName, String type ){
        if( null == context || TextUtils.isEmpty(resourcesName) || TextUtils.isEmpty( type ) ){
            return -1;
        }

        return context.getResources( ).getIdentifier(resourcesName, type, context.getPackageName( ) );
    }

    public static final String TYPE_DRAWABLE = "drawable";
    public static final String TYPE_MIPMAP = "mipmap";
    public static final String TYPE_RAW = "raw";
    public static final String TYPE_COLOR = "color";

}  