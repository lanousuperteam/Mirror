package com.lanouteam.dllo.mirror.net;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dllo on 16/3/30.
 */
public class NetHelper {
    private RequestQueue requestQueue; //创建请求队列
    private Map<String, String> mMap;

    /**
     * NetHelper 的构造方法
     * 引入NetHelper 必须传入当前上下文对象
     * 并在构造方法中,创建请求队列
     */
    public NetHelper(Context context) {
        //在工具类中使请求队列初始化
        SingleQueue singleQueue = SingleQueue.getInstance(context);
        requestQueue = singleQueue.getQueue();
    }

    /**
     *  方法:getPhoneCode() 获取需要拉取的数据,使其拼接成可请求的 url
     *  @param requestType 请求类型
     *  @param mMap 请求参数
     *  @param netListener 网络请求成功和失败的监听对象
     * */
    public void getPhoneCode(String requestType, NetListener netListener, HashMap<String, String> mMap) {
        String idUrl = requestType;
        postDataFromNet(idUrl, netListener, mMap);
    }

    /**
     * 方法:postDataFromNet() 根据Url,HashMap 类型的请求参数来进行请求
     * @param url  拼接后的请求网址
     * @param mMap POST请求所需的参数
     * @param netListener 网络请求成功和失败的监听对象
     * */
    private void postDataFromNet(String url, final NetListener netListener, final HashMap<String, String> mMap) {
        final StringRequest request = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        netListener.getSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                netListener.getFailed("拉取失败");
            }
        }) {
            /**
             *设置请求参数
             * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return mMap;
            }
        };
        requestQueue.add(request);
    }

}