package com.lanouteam.dllo.mirror.fragments;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.lanouteam.dllo.mirror.R;
import com.lanouteam.dllo.mirror.activity.GoodsContentActivity;
import com.lanouteam.dllo.mirror.activity.MainActivity;
import com.lanouteam.dllo.mirror.adapters.AllRecyclerViewAdapter;
import com.lanouteam.dllo.mirror.adapters.OnRecyclerviewItemClickListener;
import com.lanouteam.dllo.mirror.base.BaseFragment;
import com.lanouteam.dllo.mirror.bean.AllFragmentBean;
import com.lanouteam.dllo.mirror.bean.RequestParams;
import com.lanouteam.dllo.mirror.bean.RequestUrls;
import com.lanouteam.dllo.mirror.net.NetHelper;
import com.lanouteam.dllo.mirror.net.NetListener;

import java.util.HashMap;

/**
 * Created by dllo on 16/4/5.
 */
public class AllFragment extends BaseFragment implements View.OnClickListener, RequestUrls {
    private LinearLayout linearLayout; // 标题的linearlayout
    private AllRecyclerViewAdapter adapter; // 适配器
    private RecyclerView recyclerView; // 商品分类的recyclerView
    private NetHelper netHelper;
    private HashMap maps;// 请求参数
    private MainActivity activity;


    @Override
    protected void initView(View view) {
        // 绑定布局
        linearLayout = bindView(R.id.allfragment_title_linearlayout);
        recyclerView = bindView(R.id.allfragment_recyclerview);
        activity = (MainActivity) getContext();

        linearLayout.setOnClickListener(this);

        // 设置请求参数
        maps = new HashMap();
        maps.put(RequestParams.DEVICE_TYPE, "2");

        // 网络请求
        netHelper = new NetHelper(getContext());
        netHelper.getJsonData(All_LIST, new NetListener() {
            @Override
            public void getSuccess(Object object) {
                // 请求解析下来的数据
                Gson gson = new Gson();
                final AllFragmentBean bean = gson.fromJson(object.toString(), AllFragmentBean.class);
                // 把数据传给适配器
                adapter = new AllRecyclerViewAdapter(bean);
                recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(new OnRecyclerviewItemClickListener() {
                    @Override
                    public void onItemClick(View view, String data) {
                        Intent allIntent = new Intent(getActivity(), GoodsContentActivity.class);
                        String id=data;
                        allIntent.putExtra(RequestParams.GOODS_ID,id);
                        startActivity(allIntent);
                    }
                });
            }

            @Override
            public void getFailed(int s) {

            }
        }, maps);

        // 设置recyclerview
        GridLayoutManager gm = new GridLayoutManager(getContext(), 1);
        gm.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(gm);



    }

    @Override
    public int initLayout() {
        return R.layout.fragment_all;
    }

    // 点击生成菜单
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.allfragment_title_linearlayout:
                activity.showMenu();
                break;
        }
    }

}
