package com.lanouteam.dllo.mirror.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lanouteam.dllo.mirror.R;
import com.lanouteam.dllo.mirror.activity.GoodsContentActivity;
import com.lanouteam.dllo.mirror.activity.MainActivity;
import com.lanouteam.dllo.mirror.adapters.GoodsRecyclerViewAdapter;
import com.lanouteam.dllo.mirror.adapters.OnRecyclerviewItemClickListener;
import com.lanouteam.dllo.mirror.base.BaseFragment;
import com.lanouteam.dllo.mirror.bean.GoodsFragmentBean;
import com.lanouteam.dllo.mirror.bean.MenuListBean;
import com.lanouteam.dllo.mirror.bean.RequestParams;
import com.lanouteam.dllo.mirror.bean.RequestUrls;
import com.lanouteam.dllo.mirror.net.NetHelper;
import com.lanouteam.dllo.mirror.net.NetListener;

import java.util.HashMap;


/**
 * Created by dllo on 16/3/30.
 */
public class GoodsFragment extends BaseFragment implements RequestUrls, View.OnClickListener,RequestParams {
    private LinearLayout linearLayout;// 标题的linearlayout 用来设置监听
    private TextView titleTv;// 标题的文字
    private GoodsRecyclerViewAdapter adapter;// 商品分类的recyclerView的适配器
    private RecyclerView recyclerView;// 商品分类的recyclerView
    private NetHelper netHelper;
    private MainActivity activity;
    private HashMap maps; // 请求参数
    private String categoryId; // 每个fragment对应的id
    private String title; // 标题上的文字


    @Override
    protected void initView(View view) {
        // 绑定布局
        linearLayout = bindView(R.id.goodsfragment_title_linearlayout);
        recyclerView = bindView(R.id.goodsfragment_recyclerview);
        titleTv = bindView(R.id.goodsfragment_title_tv);
        activity = (MainActivity) getContext();

        linearLayout.setOnClickListener(this);

        // 网络请求
        netHelper = new NetHelper(getContext());


        // 接收fragment的id 放进请求参数里
        Bundle bundle = getArguments();
        categoryId = bundle.getString(CATEGORY_ID);
        title = bundle.getString("title");
        titleTv.setText(title);

        maps = new HashMap();
        maps.put(DEVICE_TYPE, "2");
        maps.put(CATEGORY_ID, categoryId);


        // 请求fragment内容的数据
        netHelper.getJsonData(GOODS_LIST, new NetListener() {
            @Override
            public void getSuccess(Object object) {
                Gson gson = new Gson();
                GoodsFragmentBean bean = gson.fromJson(object.toString(), GoodsFragmentBean.class);
                adapter = new GoodsRecyclerViewAdapter(bean);
                recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(new OnRecyclerviewItemClickListener() {
                    @Override
                    public void onItemClick(View view, String data) {
                        Intent goodsIntent = new Intent(getActivity(), GoodsContentActivity.class);
                        String id=data;
                        goodsIntent.putExtra(RequestParams.GOODS_ID,id);
                        startActivity(goodsIntent);
                    }
                });
            }

            @Override
            public void getFailed(int s) {

            }
        }, maps);


        // 设置recyclerView
        GridLayoutManager gm = new GridLayoutManager(getContext(), 1);
        gm.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(gm);


    }


    @Override
    public int initLayout() {
        return R.layout.fragment_goods;
    }

    // 点击生成菜单
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goodsfragment_title_linearlayout:
                activity.showMenu();
                break;
        }
    }


}
