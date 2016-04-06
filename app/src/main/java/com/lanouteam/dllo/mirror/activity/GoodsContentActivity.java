package com.lanouteam.dllo.mirror.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.volley.toolbox.ImageLoader;
import com.google.gson.Gson;
import com.lanouteam.dllo.mirror.R;
import com.lanouteam.dllo.mirror.adapters.GoodsContentApapter;
import com.lanouteam.dllo.mirror.base.BaseActivity;
import com.lanouteam.dllo.mirror.bean.GoodsContentBean;
import com.lanouteam.dllo.mirror.bean.RequestUrls;
import com.lanouteam.dllo.mirror.net.NetHelper;
import com.lanouteam.dllo.mirror.net.NetListener;

import java.util.HashMap;

/**
 * Created by dllo on 16/3/31.
 */
public class GoodsContentActivity extends BaseActivity {
    private ImageView backgroundIv, titleIv;

    private RecyclerView recyclerView;
    private GoodsContentApapter goodsContentApapter;
    private int value;
    private RelativeLayout relativeLayoutButtom;
    private boolean isup = true, isDown = true;
    private boolean dyUp = true, dyDown = true;////该步是第二种动画出现的步骤,暂未用上
    private HashMap goodsInfo;

    private NetHelper netHelper;

    private int position;

    @Override
    protected int getLayout() {
        return R.layout.activity_goods_content;
    }

    @Override
    protected void initData() {




        //网络解析
        goodsInfo=new HashMap();
        goodsInfo.put("device_type",2+"");
        goodsInfo.put("goods_id","96Psa1455524521");

        netHelper=new NetHelper(this);

        netHelper.getPhoneCode(RequestUrls.GOODS_INFO, new NetListener() {
            @Override
            public void getSuccess(Object object) {
                Gson gson = new Gson();
                GoodsContentBean data = gson.fromJson(object.toString(), GoodsContentBean.class);
                goodsContentApapter = new GoodsContentApapter(data,getBaseContext());
                GridLayoutManager gm = new GridLayoutManager(getBaseContext(), 1);
                gm.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(gm);
                recyclerView.setAdapter(goodsContentApapter);
                ImageLoader backgroundImageLoader = netHelper.getImageLoader();
                backgroundImageLoader.get(data.getData().getGoods_img(), backgroundImageLoader.getImageListener(
                        backgroundIv, R.mipmap.ic_launcher, R.mipmap.background));
                //现在需要解析图片需要用到adapter的position和承载的imageview


            }

            @Override
            public void getFailed(int s) {

            }
        },goodsInfo);







        /**
         * 该方法为对recycleview进行滑动监听 获取滑动距离
         * */

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                /**
                 * 这里的 value 是获得recyclerview 所有的滑动距离,将每一次的滑动距离叠加形成的结果.
                 * */
                value -= dy;
                Log.d("滑动效果", value + "");

                //这是Recyclerview 的方法来获得当前的 value 值.
                goodsContentApapter.setScrollValue(value);
                dyUp = dy > 0;  //该步是第二种动画出现的步骤
                dyDown = dy < 0;
                Log.i("dy","dy"+" "+dy+"");
                /**
                 * 底部动画操作
                 *
                 * */
                //***************************************************************************************
                if (dy > 0) {//判断滑动方向  dy>0为上滑

                    //该数值为滑动到recycleview第三个布局by滑动的数值范围
                    if (value <= -2100) {
                        if (isup) {
                            Animation animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.goods_content_layout_into);
                            relativeLayoutButtom.setAnimation(animation);
                            relativeLayoutButtom.setVisibility(View.VISIBLE);
                            isup = false;
                            isDown = true;
                        }

                    }

                } else {

                    if (value <= -2100 && value >= -3000) {
                        if (isDown) {
                            Animation animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.goods_content_layout_out);
                            relativeLayoutButtom.setAnimation(animation);
                            relativeLayoutButtom.setVisibility(View.INVISIBLE);
                            isup = true;
                            isDown = false;
                        }

                    }

                }
                //***************************************************************************************
            }
        });
        /**
         * 第二种动画出现的方法 通过接口传值(position)通过判断滑动的position位置控制动画 ,该方法有待商榷,先暂时注掉,采用方法一
         * */

//        goodsContentApapter.setPosition(new GoodsContentInterface() {
//            @Override
//            public void getPosition(int position) {
//
//
//                //该数值为滑动到recycleview第三个布局by滑动的数值范围
//                if (dyUp) {//判断滑动方向  dy>0为上滑
//
//
//                    //该数值为滑动到recycleview第三个布局by滑动的数值范围
//                    if (position ==2) {
//                        if (isup) {
//                            Animation animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.goods_content_layout_into);
//                            relativeLayoutButtom.setAnimation(animation);
//                            relativeLayoutButtom.setVisibility(View.VISIBLE);
//                            isup = false;
//                            isDown = true;
//                        }
//
//
//
//                    }
//
//                } else {
//                    // Toast.makeText(GoodsContentActivity.this, "动画退出方法写入", Toast.LENGTH_SHORT).show();
//                    if (position==2) {
//                        if (isDown) {
//                            Animation animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.goods_content_layout_out);
//                            relativeLayoutButtom.setAnimation(animation);
//                            relativeLayoutButtom.setVisibility(View.INVISIBLE);
//                            isup = true;
//                            isDown = false;
//                        }
//
//                    }
//
//                }
//
//                // Toast.makeText(GoodsContentActivity.this, "动画退出方法写入", Toast.LENGTH_SHORT).show();
//
//            }
//        });



    }


    @Override
    protected void initView() {
        recyclerView = bindView(R.id.activity_goods_content_recyclerview);
        relativeLayoutButtom = bindView(R.id.activity_goods_content_relativelayout);
        backgroundIv=bindView(R.id.activity_goods_content_background_iv);


    }


}
