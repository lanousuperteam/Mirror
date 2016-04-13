package com.lanouteam.dllo.mirror.utils;

import android.util.Log;

/**
 * Created by dllo on 16/4/5.
 * 自定义的工具类-->Log 统一管理类
 */
public class L {

    /**
     * 抛掉不能被实例化的异常
     * 是否需要打印 bug, 可以在 baseapplication 的onCreat 函数里面初始化
     * 也可以理解成 Log 是否打印的开关.
     */
    private L() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }


    public static boolean isDebug = true;

    private static final String TAG = "way";

    /**
     * 下面四个是默认的 tag 的函数
     */
    public static void i(String msg) {
        if (isDebug) {
            Log.i(TAG, msg);
        }
    }

    public static void d(String msg) {
        if (isDebug) {
            Log.d(TAG, msg);
        }
    }


    public static void e(String msg) {
        if (isDebug) {
            Log.e(TAG, msg);
        }
    }

    public static void v(String msg) {
        if (isDebug) {
            Log.v(TAG, msg);
        }
    }

    /**
     * 下面是传入自定义 tag 的函数
     */
    public static void i(String tag, String msg) {
        if (isDebug) {
            Log.i(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (isDebug) {
            Log.d(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (isDebug) {
            Log.e(tag, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (isDebug) {
            Log.v(tag, msg);
        }
    }
}
