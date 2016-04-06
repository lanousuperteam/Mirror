package com.lanouteam.dllo.mirror.net.cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.android.volley.toolbox.ImageLoader;
import com.lanouteam.dllo.mirror.utils.L;
import com.lanouteam.dllo.mirror.utils.MD5Util;

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
        if (url.contains("/")) {
            String fileName = url.substring(url.lastIndexOf("/"), url.length());
            String nameToMD5 = MD5Util.MD5(fileName);
            //用文件名拼接处实际文件存储路径
            String filePath = diskPath + "/" + nameToMD5;
            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
            L.d("存储路径", filePath);
            return bitmap;
        } else {
            return null;
        }
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        //截取 url 中的图片的名称
        String fileName = url.substring(url.lastIndexOf("/"), url.length());
        //将截取到的后缀名使用MD5加密成32位字符
        String nameToMD5 = MD5Util.MD5(fileName);
        //用文件名拼接出实际文件储存路径
        String filePath = diskPath + "/" + nameToMD5;
        Log.d("提取路径", filePath);
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
