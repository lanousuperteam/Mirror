package com.lanouteam.dllo.mirror.net;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by dllo on 16/3/12.
 * 单例模式:用来保证请求队列RequestQueue只有一个.
 */
public class SingleQueue {
    //在内部保存自己
    private static SingleQueue singleQueue;
    private RequestQueue queue;
    private Context context;

    //构造方法私有化
    private SingleQueue(Context context) {
        this.context = context;
        queue = Volley.newRequestQueue(context);
    }

    public RequestQueue getQueue() {
        return queue;
    }

    //第三部提供方法,将自己暴露出去
    public static SingleQueue getInstance(Context context) {
        //外层判断是为了提升效率考虑,当SingleQueue已经
        //有值的时候,多个线程可以同时被这个判断挡在外面
        if (singleQueue == null) {
            //synchronized 能保证大括号的内部,
            //只有一个线程
            synchronized (SingleQueue.class) {
                //这个判断是为了线程安全考虑的
                //保证singleQueue只能被初始化一次
                if (singleQueue == null) {
                    //初始化singleQueue
                    singleQueue = new SingleQueue(context);
                }
            }
        }
        //返回这个单例对象 .
        return singleQueue;
    }
}