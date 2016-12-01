package com.crh.android.common.data.source.local.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by android on 16-11-22.
 */

@Entity
public class LocalData {

    @Id
    private String id;

    @Property
    private String data;

    @Property
    private long time;

    @Generated(hash = 1547556885)
    public LocalData(String id, String data, long time) {
        this.id = id;
        this.data = data;
        this.time = time;
    }

    @Generated(hash = 643088711)
    public LocalData() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
