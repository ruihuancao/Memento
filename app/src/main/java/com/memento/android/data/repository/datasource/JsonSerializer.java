package com.memento.android.data.repository.datasource;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.memento.android.data.entity.SplashImage;
import com.memento.android.data.entity.ZhihuLauncherImage;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class JsonSerializer {

    private final Gson gson = new Gson();

    @Inject
    public JsonSerializer() {}

    public String serialize(ZhihuLauncherImage zhihuLauncherImage) {
        String jsonString = gson.toJson(zhihuLauncherImage, ZhihuLauncherImage.class);
        return jsonString;
    }

    public String serialize(List<SplashImage> list) {
        String jsonString = gson.toJson(list);
        return jsonString;
    }

    public List<SplashImage> deserializeListSplashImage(String jsonString) {
        List<SplashImage> list = gson.fromJson(jsonString,
                new TypeToken<List<SplashImage>>() {
                }.getType());
        return list;
    }

    public ZhihuLauncherImage deserialize(String jsonString) {
        ZhihuLauncherImage zhihuLauncherImage = gson.fromJson(jsonString, ZhihuLauncherImage.class);
        return zhihuLauncherImage;
    }
}
