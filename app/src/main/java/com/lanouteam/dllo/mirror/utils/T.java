package com.lanouteam.dllo.mirror.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by dllo on 16/4/5.
 * Toast 统一管理类
 */
public class T {
    /**
     * 抛出不能被实例化的异常
     * */
     private T(){
          throw new UnsupportedOperationException("cannot be instantiated");
     }

    public static boolean isShow =true;

    /**
     * 短时间显示Toast
     *
     * @param context   上下文对象
     * @param message   显示信息
     * */
    public static void showShort(Context context,CharSequence message){
        if (isShow){
            Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 短时间显示Toast
     *
     * @param context   上下文对象
     * @param message   显示信息
     * */
    public static void showShort(Context context, int message){
        if (isShow){
            Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 长时间显示Toast
     *
     * @param context   上下文对象
     * @param message   显示信息
     * */
    public static void showLong(Context context,CharSequence message){
        if (isShow){
            Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 长时间显示Toast
     *
     * @param context   上下文对象
     * @param message   显示信息
     * */
    public static void showLong(Context context,int message){
        if (isShow){
            Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 自定义显示Toast 时间
     *
     * @param context   上下文对象
     * @param message   显示信息
     * @param duration  持续时间
     * */
    public static void show(Context context,CharSequence message,int duration){
        if (isShow){
            Toast.makeText(context,message,duration).show();
        }
    }


    /**
     * 自定义显示Toast 时间
     *
     * @param context   上下文对象
     * @param message   显示信息
     * @param duration  持续时间
     * */
    public static void show(Context context,int message,int duration){
        if (isShow){
            Toast.makeText(context,message,duration).show();
        }
    }








}
