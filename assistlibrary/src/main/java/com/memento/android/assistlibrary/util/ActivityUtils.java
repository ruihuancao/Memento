package com.memento.android.assistlibrary.util;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.Random;

import static com.google.common.base.Preconditions.checkNotNull;


public class ActivityUtils {

    public static void addFragmentToActivity (@NonNull FragmentManager fragmentManager,
                                              @NonNull Fragment fragment, int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }


    public static String getQiniuImageUrl(){
        String qiniuurl = "http://7xsfes.com1.z0.glb.clouddn.com/image_";
        int random  = new Random().nextInt(50);
        return qiniuurl+random;
    }
}
