package com.lanouteam.dllo.mirror.net.cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.android.volley.toolbox.ImageLoader;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by dllo on 16/4/1.
 */
public class DiskCache implements ImageLoader.ImageCache {
    private String diskPath;


    public DiskCache(String diskPath) {
        this.diskPath = diskPath;
    }

    @Override
    public Bitmap getBitmap(String url) {
        //截取 url 中的图片的名称
        String fileName = url.substring(url.lastIndexOf("/"), url.length());
        //用文件名拼接处实际文件存储路径
        String filePath = diskPath + "/" + fileName;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        return bitmap;
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        //截取 url 中的图片的名称
        String fileName = url.substring(url.lastIndexOf("/"), url.length());
        //用文件名拼接出实际文件储存路径
        String filePath = diskPath + "/" + fileName;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filePath);
            //将 bitmap 对象写入文件中
            //三个参数:第一个存入的格式,压缩率,输入流;
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fos != null)
                try {
                    //关流
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
}
