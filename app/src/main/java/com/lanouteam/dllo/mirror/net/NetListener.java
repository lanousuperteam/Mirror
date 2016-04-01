package com.lanouteam.dllo.mirror.net;

/**
 * Created by dllo on 16/3/30.
 * POST 请求成功和失败的监听
 */
public interface NetListener  {
    //请求成功
    void getSuccess(Object object);

    //请求失败
    void getFailed(int s);
}
