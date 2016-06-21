package com.memento.android.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtil {

    public static String getJsonStringValue(JSONObject jsonObject, String key) {
        return getJsonStringValue(jsonObject, key, "");
    }

    public static String getJsonStringValue(JSONObject jsonObject, String key, String defaultValue) {
        try {
            if (jsonObject != null && jsonObject.has(key)) {
                String value = jsonObject.getString(key).trim();
                if (value.equals("null")) {
                    value = "";
                }
                return value;
            }
        } catch (Exception e) {
            return defaultValue;
        }
        return defaultValue;
    }

    public static int getJsonIntegerValue(JSONObject json, String key) {
        return getJsonIntegerValue(json, key, 0);
    }

    public static int getJsonIntegerValue(JSONObject jsonObject, String key, int defaultValue) {
        try {
            if (jsonObject != null && jsonObject.has(key)) {
                return jsonObject.getInt(key);
            }
        } catch (Exception e) {
            return defaultValue;
        }
        return defaultValue;
    }

    public static Long getJsonLongValue(JSONObject json, String key) {
        return getJsonLongValue(json, key, 0L);
    }

    public static Long getJsonLongValue(JSONObject jsonObject, String key, Long defaultValue) {
        try {
            if (jsonObject != null && jsonObject.has(key)) {
                return jsonObject.getLong(key);
            }
        } catch (Exception e) {
            return defaultValue;
        }
        return defaultValue;
    }

    public static double getJsonDoubleValue(JSONObject jsonObject, String key) {
        return getJsonDoubleValue(jsonObject, key, 0);
    }

    public static double getJsonDoubleValue(JSONObject jsonObject, String key, double defaultValue) {
        try {
            if (jsonObject != null && jsonObject.has(key)) {
                return Double.valueOf(jsonObject.getString(key));
            }
        } catch (Exception e) {
            return defaultValue;
        }
        return defaultValue;
    }

    public static float getJsonFloatValue(JSONObject jsonObject, String key, float defaultValue) {
        try {
            if (jsonObject != null && jsonObject.has(key)) {
                return Float.valueOf(jsonObject.getString(key));
            }
        } catch (Exception e) {
            return defaultValue;
        }
        return defaultValue;
    }

    public static boolean getJsonBooleanValue(JSONObject jsonObject, String key, boolean defaultValue) {
        try {
            if (jsonObject != null && jsonObject.has(key)) {
                return jsonObject.getBoolean(key);
            }
        } catch (Exception e) {
            return defaultValue;
        }
        return defaultValue;
    }

    public static JSONObject getJsonObject(JSONObject jsonObject, String key) {
        try {
            if (jsonObject != null && jsonObject.has(key)) {
                return jsonObject.getJSONObject(key);
            }
        } catch (Exception e) {
            return new JSONObject();
        }
        return new JSONObject();
    }

    public static JSONArray getJsonArray(JSONObject jsonObject, String key) {
        try {
            if (jsonObject != null && jsonObject.has(key)) {
                return jsonObject.getJSONArray(key);
            }
        } catch (Exception e) {
            return new JSONArray();
        }
        return new JSONArray();
    }

    public static JSONObject getJsonObject(JSONArray jsonArray, int index) {
        try {
            if (jsonArray != null && index >= 0 && index < jsonArray.length()) {
                return jsonArray.getJSONObject(index);
            }
        } catch (JSONException e) {
            return null;
        }
        return null;
    }
}