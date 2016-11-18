package com.memento.android.assistlibrary.data.service;

import com.memento.android.assistlibrary.data.DataManager;
import com.memento.android.assistlibrary.data.entity.LeanCloudUser;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import rx.Subscriber;

/**
 * Created by caoruihuan on 16/9/29.
 */
public class LeanCloudApiServiceTest {

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
    public void register() throws Exception {
        LeanCloudUser user = new LeanCloudUser();
        user.setUsername("caoruidsdad");
        user.setPassword("dsdjkdssd@ew2da3");
        user.setEmail("184214324@qq.com");
        dataManager.getLeanCloudApiService().register(user).subscribe(new Subscriber<LeanCloudUser>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Assert.fail(e.getMessage());
            }

            @Override
            public void onNext(LeanCloudUser leanCloudUser) {
                Assert.assertNotNull(leanCloudUser);
                Assert.assertNotNull(leanCloudUser.getObjectId());
                Assert.assertNotNull(leanCloudUser.getSessionToken());
            }
        });

        dataManager.getLeanCloudApiService().login(user).subscribe(new Subscriber<LeanCloudUser>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Assert.fail(e.getMessage());
            }

            @Override
            public void onNext(LeanCloudUser user) {
                Assert.assertNotNull(user);
                Assert.assertNotNull(user.getObjectId());
                Assert.assertNotNull(user.getSessionToken());
            }
        });

        dataManager.getLeanCloudApiService().login(user.getUsername(), user.getPassword()).subscribe(new Subscriber<LeanCloudUser>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Assert.fail(e.getMessage());
            }

            @Override
            public void onNext(LeanCloudUser user) {
                Assert.assertNotNull(user);
                Assert.assertNotNull(user.getObjectId());
                Assert.assertNotNull(user.getSessionToken());
            }
        });
    }

}