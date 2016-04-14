package com.lanouteam.dllo.mirror.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.google.gson.Gson;
import com.lanouteam.dllo.mirror.R;
import com.lanouteam.dllo.mirror.bean.RequestParams;
import com.lanouteam.dllo.mirror.bean.RequestUrls;
import com.lanouteam.dllo.mirror.bean.WearBean;
import com.lanouteam.dllo.mirror.net.NetHelper;
import com.lanouteam.dllo.mirror.net.NetListener;
import com.lanouteam.dllo.mirror.utils.L;
import com.lanouteam.dllo.mirror.utils.SmoothImageView;

import java.util.HashMap;

/**
 * Created by dllo on 16/4/8.
 */
public class WearDetailShowActivity extends Activity {
    //接收值
    private int mLocationX;
    private int mLocationY;
    private int mWidth;
    private int mHeight;
    //网络解析
    private NetHelper netHelper;
    private HashMap wearInfo;
    private ImageLoader wearImageLoader;
    //自定义imageview
    SmoothImageView imageView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //activity的背景
        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.color.goodsfragment_item_price_textcolor);
        this.getWindow().setBackgroundDrawable(drawable);

        //网络解析
        Intent intent=getIntent();
        String id=intent.getStringExtra(RequestParams.GOODS_ID);
        netHelper = new NetHelper(this);
        wearInfo = new HashMap();
        wearInfo.put(RequestParams.DEVICE_TYPE, 2 + "");
        wearInfo.put(RequestParams.GOODS_ID, id);
        wearInfo.put(RequestParams.APP_VERSION, "1.0.1");
        wearImageLoader = netHelper.getImageLoader();
        //接收值
        mLocationX = getIntent().getIntExtra("locationX",0);
        mLocationY = getIntent().getIntExtra("locationY", 0);
        mWidth = getIntent().getIntExtra("width", 0);
        mHeight = getIntent().getIntExtra("height", 0);

        imageView = new SmoothImageView(this);
        imageView.setOriginalInfo(mWidth, mHeight, mLocationX, mLocationY);
        imageView.transformIn();
        imageView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));//创建一个指定宽高参数  参数为-1,-1因为代表MATCH_PARENT(源码中有说明)
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setContentView(imageView);
        //解析图片
        netHelper.getJsonData(RequestUrls.GOODS_INFO, new NetListener() {
            @Override
            public void getSuccess(Object object) {
                Gson gson = new Gson();
                WearBean data = gson.fromJson(object.toString(), WearBean.class);

                for (int i = 0; i <data.getData().getWear_video().size() ; i++) {
                    if(data.getData().getWear_video().get(i).getType().equals("8")&&data.getData().getWear_video().get(i).getType().equals("9")){

                    }else{
                        wearImageLoader.get(data.getData().getWear_video().get(i).getData(), wearImageLoader.getImageListener(
                                imageView, Color.BLACK, R.mipmap.ic_launcher));
                    }



                }

            }
            @Override
            public void getFailed(int s) {

            }
        }, wearInfo);

    }

    /**
     * 点击返回键退出的方法
     */
    @Override
    public void onBackPressed() {
        imageView.setOnTransformListener(new SmoothImageView.TransformListener() {
            @Override
            public void onTransformComplete(int mode) {
                if (mode == 2) {
                    finish();
                }
            }
        });
        imageView.transformOut();
    }

    /***
     * @param event 图片触摸事件
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        imageView.setOnTransformListener(new SmoothImageView.TransformListener() {
            @Override
            public void onTransformComplete(int mode) {
                if (mode == 2) {
                    finish();
                }
            }
        });
        imageView.transformOut();
        return true;
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            overridePendingTransition(0, 0);
        }
    }

}

