package com.memento.android.helper;

import android.content.Context;
import android.support.annotation.NonNull;

import com.crh.android.common.data.DataRepository;
import com.crh.android.common.data.source.local.cache.Cache;
import com.crh.android.common.data.source.remote.RemoteDataSource;

import static com.google.common.base.Preconditions.checkNotNull;


public class DataHelper {

    public static DataRepository provideRepository(@NonNull Context context) {
        checkNotNull(context);
        return DataRepository.getInstance(Cache.getInstance(context), RemoteDataSource.getInstance());
    }
}
