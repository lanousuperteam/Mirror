package com.lanouteam.dllo.mirror.fragments;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lanouteam.dllo.mirror.R;
import com.lanouteam.dllo.mirror.activity.MainActivity;
import com.lanouteam.dllo.mirror.adapters.GoodsRecyclerViewAdapter;
import com.lanouteam.dllo.mirror.base.BaseFragment;
import com.lanouteam.dllo.mirror.bean.MenuListBean;
import com.lanouteam.dllo.mirror.bean.RequestUrls;
import com.lanouteam.dllo.mirror.net.NetHelper;
import com.lanouteam.dllo.mirror.net.NetListener;
import com.lanouteam.dllo.mirror.utils.Popwindow;

import java.util.ArrayList;


/**
 * Created by dllo on 16/3/30.
 */
public class GoodsFragment extends BaseFragment implements RequestUrls,View.OnClickListener, NetListener {
    private LinearLayout linearLayout;
    private TextView titleTv;
    private Popwindow popwindow;
    private GoodsRecyclerViewAdapter adapter;
    private ArrayList<String> datas;
    private RecyclerView recyclerView;
    private NetHelper netHelper;
    private int position;
    private MainActivity activity;


    @Override
    protected void initView(View view) {
        linearLayout = bindView(R.id.goodsfragment_title_linearlayout);
        recyclerView = bindView(R.id.goodsfragment_recyclerview);
        titleTv = bindView(R.id.goodsfragment_title_tv);

        popwindow = new Popwindow(getContext());
        linearLayout.setOnClickListener(this);

        netHelper = new NetHelper(getContext());
        netHelper.getJsonData(MENU_LIST, this, null);

        GridLayoutManager gm = new GridLayoutManager(getContext(), 1);
        gm.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(gm);

        datas = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            datas.add("法國巴黎");
        }


        adapter = new GoodsRecyclerViewAdapter(datas);
        recyclerView.setAdapter(adapter);

    }


    @Override
    public int initLayout() {
        return R.layout.fragment_goods;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goodsfragment_title_linearlayout:
                popwindow.showPopUpWindow(v);
                break;
        }
    }

    @Override
    public void getSuccess(Object object) {
        Gson gson = new Gson();
        MenuListBean bean = gson.fromJson(object.toString(), MenuListBean.class);
        activity = (MainActivity) getContext();

        position = activity.getCurrentItem();

        Log.d("GoodsFragment", position + "");

        titleTv.setText(bean.getData().getList().get(position).getTitle());
    }

    @Override
    public void getFailed(int s) {

    }
}
