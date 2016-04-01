package com.lanouteam.dllo.mirror.net;

import android.content.Context;
import android.os.Environment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.lanouteam.dllo.mirror.R;
import com.lanouteam.dllo.mirror.net.cache.DoubleCache;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dllo on 16/3/30.
 */
public class NetHelper {
    private RequestQueue requestQueue; //创建请求队列
    private Map<String, String> mMap;

    /**
     * 定义一个ImageLoder 对象,用来加载网络图片
     */
    private ImageLoader imageLoader;
    private String diskPath;

    /**
     * NetHelper 的构造方法
     * 引入NetHelper 必须传入当前上下文对象
     * 并在构造方法中,创建请求队列
     */
    public NetHelper(Context context) {
        //在工具类中使请求队列初始化
        SingleQueue singleQueue = SingleQueue.getInstance(context);
        requestQueue = singleQueue.getQueue();

        //初始化ImageLoader 需要2个参数,分别是请求队列和缓存对象
        //缓存分三级    第一级  硬盘级,缓存会存在SD卡中
        //            第二级  是在内存中,存在
        //            第三级  网络层

        //定义硬盘图片缓存的根路径
        //兼顾SD卡和根路径
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = Environment.getExternalStorageDirectory();
            diskPath = file.getAbsolutePath();
        } else {
            File file = context.getFilesDir();
            diskPath = file.getAbsolutePath();
        }

        File file = new File(diskPath + "/img");
        if (!file.exists()) {
            file.mkdir();
        }
        diskPath=file.getAbsolutePath();

        //实现三级缓存
         imageLoader=new ImageLoader(requestQueue,new DoubleCache(diskPath));
    }

    /**
     * 获取ImageLoader的方法
     * */
    public ImageLoader getImageLoader(){
        return imageLoader;
    }

    /**
     * 方法:getPhoneCode() 获取需要拉取的数据,使其拼接成可请求的 url
     *
     * @param requestType 请求类型
     * @param mMap        请求参数
     * @param netListener 网络请求成功和失败的监听对象
     */
    public void getPhoneCode(String requestType, NetListener netListener, HashMap<String, String> mMap) {
        String idUrl = requestType;
        postDataFromNet(idUrl, netListener, mMap);
    }

    /**
     * 方法:postDataFromNet() 根据Url,HashMap 类型的请求参数来进行请求
     *
     * @param url         拼接后的请求网址
     * @param mMap        POST请求所需的参数
     * @param netListener 网络请求成功和失败的监听对象
     */
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
                netListener.getFailed(R.string.nethelper_failed);
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