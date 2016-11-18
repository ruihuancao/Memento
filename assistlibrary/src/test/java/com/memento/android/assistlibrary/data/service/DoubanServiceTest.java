package com.memento.android.assistlibrary.data.service;

import com.memento.android.assistlibrary.data.DataManager;
import com.memento.android.assistlibrary.data.entity.DouBanMovieEntity;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import rx.Subscriber;

/**
 * Created by caoruihuan on 16/9/28.
 */
public class DoubanServiceTest {

    DataManager dataManager;

    @Before
    public void setUp() throws Exception {
        dataManager = DataManager.getInstance();
    }

    @After
    public void tearDown() throws Exception {
        dataManager = null;
    }

    @Test
    public void getTheatersMovie() throws Exception {
        String city = "杭州";
        dataManager.getDoubanService().getTheatersMovie(city).subscribe(new Subscriber<DouBanMovieEntity>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Assert.fail();
            }

            @Override
            public void onNext(DouBanMovieEntity doubanMovieEntity) {
                Assert.assertNotNull(doubanMovieEntity);
                Assert.assertNotNull(doubanMovieEntity.getSubjects());
            }
        });
    }

    @Test
    public void getComingSoonMovie() throws Exception {
        dataManager.getDoubanService().getComingSoonMovie(0, 20).subscribe(new Subscriber<DouBanMovieEntity>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Assert.fail();
            }

            @Override
            public void onNext(DouBanMovieEntity doubanMovieEntity) {
                Assert.assertNotNull(doubanMovieEntity);
                Assert.assertNotNull(doubanMovieEntity.getSubjects());
            }
        });
    }

    @Test
    public void getTop250Movie() throws Exception {
        dataManager.getDoubanService().getTop250Movie(0, 20).subscribe(new Subscriber<DouBanMovieEntity>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Assert.fail();
            }

            @Override
            public void onNext(DouBanMovieEntity doubanMovieEntity) {
                Assert.assertNotNull(doubanMovieEntity);
                Assert.assertNotNull(doubanMovieEntity.getSubjects());
            }
        });
    }
}