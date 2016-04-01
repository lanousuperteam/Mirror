package com.lanouteam.dllo.mirror.net.cache;

import android.graphics.Bitmap;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by dllo on 16/4/1.
 */
public class DoubleCache implements ImageLoader.ImageCache {
    private MemoryCache memoryCache;
    private DiskCache diskCache;

    public DoubleCache(String diskPath) {
        memoryCache = new MemoryCache();
        diskCache = new DiskCache(diskPath);
    }

    @Override
    public Bitmap getBitmap(String url) {
        Bitmap bitmap=memoryCache.getBitmap(url);
        //先从内存中寻找,因为内存速度最快,寻找顺序  内存-->硬盘-->网络
        if (bitmap == null){
            bitmap=diskCache.getBitmap(url);
        }
        return bitmap;
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        memoryCache.putBitmap(url,bitmap);
        diskCache.putBitmap(url,bitmap);
    }
}
