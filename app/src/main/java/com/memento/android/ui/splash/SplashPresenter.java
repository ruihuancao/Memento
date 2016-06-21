package com.memento.android.ui.splash;

import com.memento.android.data.Repository;
import com.memento.android.data.model.Photo;
import com.memento.android.data.subscriber.DefaultSubscriber;
import com.memento.android.ui.base.BasePresenter;

import java.io.File;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SplashPresenter extends BasePresenter<SplashContract.View> implements SplashContract.UserActionsListener {

    private final Repository mRepository;

    @Inject
    public SplashPresenter(Repository mRepository) {
        this.mRepository = mRepository;
    }


    @Override
    public void getImage(final int width) {
/*        String[] params = {"random","image=0", "blur",
                "gravity=east", "gravity=west", "gravity=north", "gravity=south", "gravity=center"};
        int random = new Random().nextInt(params.length);
        String url = buildImageUrl(width, height, params[random]);
        getView().showImage(buildTempImageUrl());*/
        mRepository.getImageList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<List<Photo>>(){
                    @Override
                    public void onNext(List<Photo> photos) {
                        super.onNext(photos);
                        int random  = new Random().nextInt(photos.size());
                        getView().showImage(photos.get(random).getPhotoUrl(width));
                    }
                });
    }


    private String buildImageUrl(int width, int height, String param){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://unsplash.it");
        stringBuilder.append(File.separator);
        stringBuilder.append(width);
        stringBuilder.append(File.separator);
        stringBuilder.append(height);
        stringBuilder.append(File.separator);
        stringBuilder.append("?");
        stringBuilder.append(param);
        return stringBuilder.toString();
    }

    private String buildTempImageUrl(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("http://7xsfes.com1.z0.glb.clouddn.com");
        stringBuilder.append(File.separator);
        stringBuilder.append("image_");
        stringBuilder.append(new Random().nextInt(100));
        return stringBuilder.toString();
    }
}
