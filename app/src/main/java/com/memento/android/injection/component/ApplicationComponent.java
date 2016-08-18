package com.memento.android.injection.component;

import android.app.Application;
import android.content.Context;

import com.memento.android.MementoApplication;
import com.memento.android.data.DataManager;
import com.memento.android.data.store.http.HttpHelper;
import com.memento.android.preference.PreferenceManager;
import com.memento.android.model.mapper.DataMapper;
import com.memento.android.injection.ApplicationContext;
import com.memento.android.injection.module.ApplicationModule;
import com.memento.android.navigation.Navigator;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(MementoApplication knowApplication);

    @ApplicationContext
    Context context();

    Application application();
    DataMapper dataMapper();
    PreferenceManager preference();
    Navigator navigate();
    DataManager dataManager();
    HttpHelper httpHelper();
}
