package com.memento.android.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 作者: caorh@dxyer.com
 * 日期: 2016-07-07
 * 时间: 14:46
 * 描述：
 * 修改历史：
 */
public class MD5Util {

    public static String hex(byte[] array) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; ++i) {
            sb.append(Integer.toHexString((array[i]
                    & 0xFF) | 0x100).substring(1,3));
        }
        return sb.toString();
    }
    public static String md5Hex (String message) {
        try {
            MessageDigest md =
                    MessageDigest.getInstance("MD5");
            return hex (md.digest(message.getBytes()));
        } catch (NoSuchAlgorithmException e) {
        }
        return null;
    }
}  