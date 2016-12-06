package com.crh.android.common.data.source.local.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.crh.android.common.data.config.LocalConfig;

import org.greenrobot.greendao.database.Database;

import static com.crh.android.common.data.source.local.dao.DaoMaster.dropAllTables;

/**
 * Created by android on 16-11-22.
 */

public class GreenDao {
    public static final String NAME = "local-data";


    private volatile static GreenDao instance;

    private DaoSession mDaoSession;

    private GreenDao(Context context){
        mDaoSession = initGreendao(context, NAME, false);
    }

    public static GreenDao getInstance(Context context){
        if(instance == null){
            synchronized (GreenDao.class){
                if(instance == null){
                    instance = new GreenDao(context);
                }
            }
        }
        return instance;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    /**
     * 初始化greendao
     * @param context
     * @param encrypted
     * @return
     */
    private DaoSession initGreendao(Context context, String name, boolean encrypted){
        GreenDaoOpenHelper helper = new GreenDaoOpenHelper(context, encrypted ? name+"-encrypted" : name);
        Database db = encrypted ? helper.getEncryptedWritableDb(LocalConfig.getDataBasePasswd()) : helper.getWritableDb();
        return new DaoMaster(db).newSession();
    }

    /**
     * 数据库升级等操作
     */
    static class GreenDaoOpenHelper extends DaoMaster.OpenHelper{
        public GreenDaoOpenHelper(Context context, String name) {
            super(context, name);
        }

        public GreenDaoOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
            super(context, name, factory);
        }

        @Override
        public void onUpgrade(Database db, int oldVersion, int newVersion) {
            Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
            dropAllTables(db, true);
            onCreate(db);
        }
    }
}
