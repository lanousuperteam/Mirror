package com.lanouteam.dllo.mirror.net.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by dllo on 16/3/31.
 */
public class MemoryCache implements ImageLoader.ImageCache {
    /**
     * LruCache 使用机制和Map很像
     */
    private LruCache<String, Bitmap> cache;

    public MemoryCache() {
        //当前运行时,的最大内存.
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        //设定可操作的内存,为最大内存的1/4
        int cacheSize = maxMemory / 4;
        cache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //返回每一张图片所占内存大小
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };
    }


    @Override
    public Bitmap getBitmap(String url) {
        return cache.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        cache.put(url, bitmap);
    }
}
