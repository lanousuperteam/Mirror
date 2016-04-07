package com.lanouteam.dllo.mirror.fragments;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.lanouteam.dllo.mirror.R;
import com.lanouteam.dllo.mirror.adapters.AllRecyclerViewAdapter;
import com.lanouteam.dllo.mirror.base.BaseFragment;
import com.lanouteam.dllo.mirror.bean.AllFragmentBean;
import com.lanouteam.dllo.mirror.bean.RequestUrls;
import com.lanouteam.dllo.mirror.net.NetHelper;
import com.lanouteam.dllo.mirror.net.NetListener;
import com.lanouteam.dllo.mirror.utils.Popwindow;

import java.util.HashMap;

/**
 * Created by dllo on 16/4/5.
 */
public class AllFragment extends BaseFragment implements View.OnClickListener, RequestUrls {
    private LinearLayout linearLayout;
    private Popwindow popwindow;
    private AllRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private NetHelper netHelper;
    private HashMap maps;


    @Override
    protected void initView(View view) {
        linearLayout = bindView(R.id.allfragment_title_linearlayout);
        recyclerView = bindView(R.id.allfragment_recyclerview);

        popwindow = new Popwindow(getContext());
        linearLayout.setOnClickListener(this);

        maps = new HashMap();
        maps.put("device_type", "2");

        netHelper = new NetHelper(getContext());
        netHelper.getJsonData(All_LIST, new NetListener() {
            @Override
            public void getSuccess(Object object) {
                Gson gson = new Gson();
                AllFragmentBean bean = gson.fromJson(object.toString(), AllFragmentBean.class);
                adapter = new AllRecyclerViewAdapter(bean);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void getFailed(int s) {

            }
        }, maps);

        GridLayoutManager gm = new GridLayoutManager(getContext(),1);
        gm.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(gm);
    }

    @Override
    public int initLayout() {
        return R.layout.fragment_all;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.allfragment_title_linearlayout:
                popwindow.showPopUpWindow(v);
                break;
        }
    }
}
