package com.memento.android.helper;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.memento.android.helper.listener.GlideDrawableListener;
import com.memento.android.helper.listener.ImageLoadingListener;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by caoruihuan on 16/9/28.
 * Glide Helper
 */

public class GlideHelper {


    public static void loadResource(@DrawableRes int drawableId, @NonNull ImageView image) {
        Glide.with(image.getContext())
                .load(drawableId)
                .crossFade()
                .into(image);
    }

    public static void loadResource(@NonNull final String imageUrl, @NonNull ImageView image) {
        loadResource(imageUrl, image, null);
    }

    public static void loadResource(@NonNull final String imageUrl, @NonNull ImageView image, int width, int height) {
        Glide.with(image.getContext())
                .load(imageUrl)
                .dontAnimate()
                .override(width, height)
                .placeholder(image.getDrawable())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(image);
    }


    public static void loadBlurResource(@NonNull final String imageUrl, @NonNull ImageView image) {
        Glide.with(image.getContext())
                .load(imageUrl)
                .dontAnimate()
                .placeholder(image.getDrawable())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .bitmapTransform(new CropCircleTransformation(image.getContext()), new BlurTransformation(image.getContext(), 25))
                .into(image);
    }

    public static void loadCircleResource(@NonNull final String imageUrl, @NonNull ImageView image) {
        Glide.with(image.getContext())
                .load(imageUrl)
                .dontAnimate()
                .placeholder(image.getDrawable())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .bitmapTransform(new CropCircleTransformation(image.getContext()))
                .into(image);
    }


    public static void loadResource(@NonNull final String imageUrl, @NonNull ImageView image, final ImageLoadingListener listener) {
        Glide.with(image.getContext())
                .load(imageUrl)
                .dontAnimate()
                .placeholder(image.getDrawable())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(new GlideDrawableListener() {
                    @Override
                    public void onSuccess(String url) {
                        if (url.equals(imageUrl)) {
                            if (listener != null) listener.onLoaded();
                        }
                    }

                    @Override
                    public void onFail(String url) {
                        if (listener != null) listener.onFailed();
                    }
                })
                .into(image);
    }

    public static void loadBitmap(@NonNull final String imageUrl, BitmapImageViewTarget bitmapImageViewTarget) {
        Glide.with(bitmapImageViewTarget.getView().getContext())
                .load(imageUrl)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(bitmapImageViewTarget);
    }
}
