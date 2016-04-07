package com.lanouteam.dllo.mirror.base;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by dllo on 16/4/5.
 */
public class BaseApplication extends Application{
    public static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext=this;
        Fresco.initialize(mContext);
    }
}
