package com.crh.android.common.data.download;

/**
 * Created by android on 16-12-5.
 */

public class DownLoad{

    private String url;
    private long total;
    private String filePath;
    private int progress;
    private boolean done;

    public DownLoad(String url, String filePath) {
        this.url = url;
        this.filePath = filePath;
        this.total = 0;
        this.progress = 0;
        this.done = false;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}