package com.wallpaper.unsplash.common.basic.activity;

/**
 * Request load activity.
 * */

public abstract class RequestLoadActivity<T> extends ReadWriteActivity {

    public abstract void updateData(T t);
}
