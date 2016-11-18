package com.memento.android.data;

import com.memento.android.data.entity.SplashImageEntity;
import com.memento.android.data.entity.ZhihuArticleEntity;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import rx.Subscriber;

/**
 * Created by caoruihuan on 16/9/27.
 */
public class DataManagerTest {

    DataInterface dataInterface;

    @Before
    public void setUp() throws Exception {
        println("setUp");
        dataInterface = DataManager.getInstance(new File("test"));
    }

    @After
    public void tearDown() throws Exception {
        println("tearDown");
    }

    @Test
    public void getImageList() throws Exception {
        dataInterface.getImageList().subscribe(new Subscriber<List<SplashImageEntity>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Assert.fail();
            }

            @Override
            public void onNext(List<SplashImageEntity> splashImageEntities) {
                Assert.assertNotNull(splashImageEntities);
            }
        });
    }

    @Test
    public void getNewArticle() throws Exception {
        dataInterface.getNewArticle().subscribe(new Subscriber<ZhihuArticleEntity>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Assert.fail();
            }

            @Override
            public void onNext(ZhihuArticleEntity zhihuArticleEntity) {
                Assert.assertNotNull(zhihuArticleEntity);
            }
        });
    }

    @Test
    public void getNewArticle1() throws Exception {
        dataInterface.getNewArticle("20160927").subscribe(new Subscriber<ZhihuArticleEntity>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Assert.fail();
            }

            @Override
            public void onNext(ZhihuArticleEntity zhihuArticleEntity) {
                Assert.assertNotNull(zhihuArticleEntity);
            }
        });
    }

    @Test
    public void getArticleDetail() throws Exception {

    }

    @Test
    public void getTheatersMovie() throws Exception {

    }

    @Test
    public void getTop250Movie() throws Exception {

    }

    @Test
    public void getComingSoonMovie() throws Exception {

    }

    public void println(String message){
        System.out.println(message);
    }
}