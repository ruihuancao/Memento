package com.memento.android.helper;

import android.content.Context;
import android.support.annotation.NonNull;

import com.memento.android.data.DataManager;
import com.memento.android.data.DataSource;

import static com.google.common.base.Preconditions.checkNotNull;


public class DataHelper {


    public static DataManager provideDataManager(@NonNull Context context) {
        checkNotNull(context);
        return DataManager.getInstance(context);
    }

    public static DataSource provideDataSource(@NonNull Context context) {
        checkNotNull(context);
        return provideDataManager(context).getDataSource();
    }
}
