package com.memento.android.data.source.local.cache;

import android.app.ActivityManager;
import android.content.Context;
import android.text.TextUtils;
import android.util.LruCache;

import com.memento.android.data.source.local.dao.DaoSession;
import com.memento.android.data.source.local.dao.LocalDataDao;
import com.memento.android.data.source.local.entity.LocalData;
import com.orhanobut.logger.Logger;

import java.util.Date;

/**
 * Created by android on 16-11-22.
 */

public class Cache {

    private LruCache<String, String> mMemoryCache;
    private DaoSession mDaoSession;

    public Cache(Context context, DaoSession daoSession){
        this.mDaoSession = daoSession;
        int memClass = ((ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE))
                .getMemoryClass();
        // Use 1/8th of the available memory for this memory cache.
        int cacheSize =  1024 * 1024 * memClass / 8;
        mMemoryCache = new LruCache<>(cacheSize);
    }

    /**
     * clear cache
     * @param key
     */
    public void clear(String key){
        if(TextUtils.isEmpty(key)){
            return ;
        }

        mMemoryCache.remove(key);

        LocalData localData = mDaoSession.getLocalDataDao().queryBuilder()
                .where(LocalDataDao.Properties.Id.eq(key)).unique();
        if(localData != null){
            mDaoSession.getLocalDataDao().delete(localData);
        }
    }

    /**
     * exist data
     * @param key
     * @return
     */
    public boolean exist(String key){
        if(TextUtils.isEmpty(key)){
            return false;
        }

        if(!TextUtils.isEmpty(mMemoryCache.get(key))){
            return true;
        }

        long count = mDaoSession.getLocalDataDao().queryBuilder()
                .where(LocalDataDao.Properties.Id.eq(key)).count();
        return count > 0;
    }

    /**
     * get cache data
     * @param key
     * @return
     */
    public String get(String key){
        // get memory cache
        String result = mMemoryCache.get(key);
        if(!TextUtils.isEmpty(result)) {
            Logger.d("get data form memory cache");
            return result;
        }

        // get local data
        LocalData localData = mDaoSession.getLocalDataDao().queryBuilder().
                where(LocalDataDao.Properties.Id.eq(key)).unique();
        if(localData != null){
            result = localData.getData();
            Logger.d("local get data: %s", result);
        }
        return result;
    }

    /**
     * save cache data
     * @param key
     * @param data
     */
    public void put(String key, String data){
        if(TextUtils.isEmpty(key) || TextUtils.isEmpty(data)){
            return ;
        }

        Logger.d("save data: %s", data);

        // add memory cache
        mMemoryCache.put(key, data);

        // add local data
        LocalData localData = mDaoSession.getLocalDataDao().queryBuilder().where(LocalDataDao.Properties.Id.eq(key)).unique();
        if(localData == null){
            Logger.d("data insert to database");
            localData = new LocalData(key, data, new Date().getTime());
            mDaoSession.getLocalDataDao().insert(localData);
        }else{
            if(!localData.getData().equals(data)){
                Logger.d("data update to database");
                localData.setData(data);
                mDaoSession.getLocalDataDao().update(localData);
            }else{
                Logger.d("data not change");
            }
        }
    }
}
