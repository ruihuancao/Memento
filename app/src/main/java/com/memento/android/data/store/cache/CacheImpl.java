package com.memento.android.data.store.cache;

import android.content.Context;
import android.text.TextUtils;
import android.util.LruCache;

import com.memento.android.injection.ApplicationContext;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Singleton;

import static com.google.common.base.Preconditions.checkNotNull;

@Singleton
public class CacheImpl implements Cache {

    private static final long EXPIRATION_TIME = 60 * 10 * 1000;
    private LruCache<String, String> mCache;

    private FileManager fileManager;
    private final File cacheDir;
    private Context mContext;


    @Inject
    public CacheImpl(@ApplicationContext Context context) {
        mContext = checkNotNull(context);
        mCache = new LruCache<>(10 * 1024 * 1024);
        fileManager = new FileManager();
        cacheDir = mContext.getCacheDir();
    }

    @Override
    public void clearCache(String key) {
        File file = buildFile(getFilenameForKey(key));
        boolean exist = fileManager.exists(file);
        if(exist){
            fileManager.clearFile(file);
        }
        mCache.remove(key);
    }

    @Override
    public void clearAllCache() {
        fileManager.clearDirectory(cacheDir);
        mCache.evictAll();
    }

    @Override
    public void putCache(String key, String data) {
        File file = buildFile(getFilenameForKey(key));
        fileManager.clearFile(file);
        file.setLastModified(System.currentTimeMillis());
        fileManager.writeToFile(file, data);
        mCache.put(key, data);
    }

    @Override
    public String getCache(String key) {
        String local = mCache.get(key);
        File file = buildFile(getFilenameForKey(key));
        return TextUtils.isEmpty(local) ? fileManager.readFileContent(file) : local;
    }

    @Override
    public boolean isCached(String key) {
        File file = buildFile(getFilenameForKey(key));
        return fileManager.exists(file);
    }

    @Override
    public boolean isExpired(String key) {
        File file = buildFile(getFilenameForKey(key));
        if(!fileManager.exists(file)){
           return true;
        }
        long currentTime = System.currentTimeMillis();
        long lastUpdateTime = file.lastModified();
        return (currentTime - lastUpdateTime) > EXPIRATION_TIME;
    }

    private String getFilenameForKey(String key) {
        int firstHalfLength = key.length() / 2;
        String localFilename = String.valueOf(key.substring(0, firstHalfLength).hashCode());
        localFilename += String.valueOf(key.substring(firstHalfLength).hashCode());
        return localFilename;
    }

    private File buildFile(String fileName) {
        StringBuilder fileNameBuilder = new StringBuilder();
        fileNameBuilder.append(this.cacheDir.getPath());
        fileNameBuilder.append(File.separator);
        fileNameBuilder.append(fileName);
        return new File(fileNameBuilder.toString());
    }
}
