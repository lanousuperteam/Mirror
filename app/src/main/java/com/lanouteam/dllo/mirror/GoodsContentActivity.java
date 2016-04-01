package com.lanouteam.dllo.mirror;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.lanouteam.dllo.mirror.base.BaseActivity;

import java.util.ArrayList;

/**
 * Created by dllo on 16/3/31.
 */
public class GoodsContentActivity extends BaseActivity {
    private ArrayList<String> data;
    private RecyclerView recyclerView;
    private GoodsContentApapter goodsContentApapter;
    private int value;

    @Override
    protected int getLayout() {
        return R.layout.activity_goods_content;
    }

    @Override
    protected void initData() {
        data = new ArrayList<>();
        data.add("我是数据1");
        data.add("我是数据2");
        data.add("我是数据3");
        data.add("我是数据4");
        data.add("我是数据5");
        data.add("我是数据6");


        goodsContentApapter = new GoodsContentApapter(data);
        GridLayoutManager gm = new GridLayoutManager(this, 1);
        gm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gm);
        recyclerView.setAdapter(goodsContentApapter);


        /**
         * 该方法为对recycleview进行滑动监听 获取滑动距离
         * */

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.d("数据确认", "dx =    " + dx + "     " + "dy     " + dy + "   ");

                /**
                 * 这里的 value 是获得recyclerview 所有的滑动距离,将每一次的滑动距离叠加形成的结果.
                 * */
                value -= dy;
                Log.d("滑动效果", value + "");

                //这是Recyclerview 的方法来获得当前的 value 值.
                goodsContentApapter.setScrollValue(value);
            }
        });



    }

    @Override
    protected void initView() {
        recyclerView = bindView(R.id.activity_goods_content_recyclerview);

    }
}
