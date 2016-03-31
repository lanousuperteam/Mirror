package com.lanouteam.dllo.mirror.net.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by dllo on 16/3/31.
 */
public class MemoryCache implements ImageLoader.ImageCache{
    /**
     *
     * */
    private LruCache<String,Bitmap> cache;

    public MemoryCache(){
        //当前运行时,的最大内存.
        int maxMemory = (int) (Runtime.getRuntime().maxMemory()/1024);
        //设定可操作的内存,为最大内存的1/4
        int cacheSize = maxMemory/4
    }









    @Override
    public Bitmap getBitmap(String url) {
        return null;
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {

    }
}
