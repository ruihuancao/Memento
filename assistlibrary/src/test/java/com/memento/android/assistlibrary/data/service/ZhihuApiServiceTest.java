package com.memento.android.assistlibrary.data.service;

import com.memento.android.assistlibrary.data.DataManager;
import com.memento.android.assistlibrary.data.entity.ZhihuDetailEntity;
import com.memento.android.assistlibrary.data.entity.ZhihuNewsEntity;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import rx.Subscriber;

/**
 * Created by caoruihuan on 16/9/28.
 */
public class ZhihuApiServiceTest {

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
    public void getArticleList() throws Exception {
        dataManager.getZhihuApiService().getNewArticleList().subscribe(new Subscriber<ZhihuNewsEntity>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Assert.fail(e.getMessage());
            }

            @Override
            public void onNext(ZhihuNewsEntity zhihuNewsEntity) {
                Assert.assertNotNull(zhihuNewsEntity);
                Assert.assertNotNull(zhihuNewsEntity.getTop_stories());
            }
        });
    }

    @Test
    public void getNewArticleList() throws Exception {
        String date = "20160928";
        dataManager.getZhihuApiService().getArticleList(date).subscribe(new Subscriber<ZhihuNewsEntity>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Assert.fail(e.getMessage());
            }

            @Override
            public void onNext(ZhihuNewsEntity zhihuNewsEntity) {
                Assert.assertNotNull(zhihuNewsEntity);
                Assert.assertNull(zhihuNewsEntity.getTop_stories());
            }
        });
    }

    @Test
    public void getArticleDetail() throws Exception {
        String id = "8835361";
        dataManager.getZhihuApiService().getArticleDetail(id).subscribe(new Subscriber<ZhihuDetailEntity>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Assert.fail(e.getMessage());
            }

            @Override
            public void onNext(ZhihuDetailEntity zhihuDetailEntity) {
                Assert.assertNotNull(zhihuDetailEntity);
                Assert.assertNotNull(zhihuDetailEntity.getBody());
            }
        });
    }

}