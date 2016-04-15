package com.lanouteam.dllo.mirror.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.lanouteam.dllo.mirror.R;
import com.lanouteam.dllo.mirror.adapters.GoodsContentAdapter;
import com.lanouteam.dllo.mirror.adapters.GoodsContentInterface;
import com.lanouteam.dllo.mirror.base.BaseActivity;
import com.lanouteam.dllo.mirror.bean.GoodsContentBean;
import com.lanouteam.dllo.mirror.bean.RequestParams;
import com.lanouteam.dllo.mirror.bean.RequestUrls;
import com.lanouteam.dllo.mirror.net.NetHelper;
import com.lanouteam.dllo.mirror.net.NetListener;
import com.lanouteam.dllo.mirror.utils.L;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

/**
 * Created by dllo on 16/3/31.
 */
public class GoodsContentActivity extends BaseActivity implements View.OnClickListener,RequestParams {
    //定义组件
    private ImageView backgroundIv;
    private TextView wearTv;
    private RecyclerView goodsContentRecyclerView;
    private GoodsContentAdapter goodsContentApapter;
    private RelativeLayout relativeLayoutBottom;
    //异步滑动
    private int value;
    private boolean isUp = true, isDown = true, isVisible = false;
    private boolean dyUp = true;
    //网络解析
    private HashMap goodsInfo;
    private NetHelper netHelper;
    private String id;
    @Override
    protected int getLayout() {
        return R.layout.activity_goods_content;
    }
    @Override
    protected void initData() {
        Intent goodsIntent=getIntent();
        id=goodsIntent.getStringExtra(RequestParams.GOODS_ID);
        wearTv.setOnClickListener(this);
        //网络解析
        goodsInfo = new HashMap();
        goodsInfo.put(RequestParams.DEVICE_TYPE, "2");
        goodsInfo.put(RequestParams.GOODS_ID, id);
        netHelper = new NetHelper(this);

        netHelper.getJsonData(RequestUrls.GOODS_INFO, new NetListener() {
            @Override
            public void getSuccess(Object object) {

                Gson gson = new Gson();
                GoodsContentBean data = gson.fromJson(object.toString(), GoodsContentBean.class);

                goodsContentApapter = new GoodsContentAdapter(data);
                GridLayoutManager gm = new GridLayoutManager(getBaseContext(), 1);
                gm.setOrientation(LinearLayoutManager.VERTICAL);
                goodsContentRecyclerView.setLayoutManager(gm);
                goodsContentRecyclerView.setAdapter(goodsContentApapter);
                //解析图片
                Picasso.with(GoodsContentActivity.this).load(data.getData().getGoods_img()).into(backgroundIv);
                /**
                 * 动画出现的方法 通过接口传值(position)通过判断滑动的position位置控制动画
                 * */
                goodsContentApapter.setPosition(new GoodsContentInterface() {
                    @Override
                    public void getPosition(int position) {

                        //该数值为滑动到recycleview第三个布局by滑动的数值范围
                        if (dyUp) {//判断滑动方向  dy>0为上滑


                            //该数值为滑动到recycleview第三个布局by滑动的数值范围
                            if (position == 3 && isUp) {

                                Animation animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.goods_content_layout_into);
                                relativeLayoutBottom.setAnimation(animation);
                                relativeLayoutBottom.setVisibility(View.VISIBLE);
                                isUp = false;
                                isDown = true;
                                isVisible = true;
                            }

                        } else {
                            if (position == 1 && isVisible && isDown) {

                                Animation animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.goods_content_layout_out);
                                relativeLayoutBottom.setAnimation(animation);
                                relativeLayoutBottom.setVisibility(View.INVISIBLE);
                                isUp = true;
                                isDown = false;
                                isVisible = false;
                            }
                        }
                    }
                });

            }

            @Override
            public void getFailed(int s) {

            }
        }, goodsInfo);
        /**
         * 该方法为对recycleview进行滑动监听 获取滑动距离
         * */
        goodsContentRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                value -= dy;
                L.d("滑动效果", value + "      " + dy);
                goodsContentApapter.setScrollValue(value);
                dyUp = dy > 0;  //该步是动画出现的步骤
                L.i("dy", "dy" + " " + dy + "      ");
            }
        });
    }

    @Override
    protected void initView() {
        goodsContentRecyclerView = bindView(R.id.activity_goods_content_recyclerview);
        relativeLayoutBottom = bindView(R.id.activity_goods_content_relativelayout);
        backgroundIv =bindView(R.id.activity_goods_content_background_iv);
        wearTv = bindView(R.id.activity_goods_content_tv);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_goods_content_tv:
                Intent intentWear = new Intent(this, WearActivity.class);
                intentWear.putExtra(RequestParams.GOODS_ID,id);
                startActivity(intentWear);
        }

    }

}
