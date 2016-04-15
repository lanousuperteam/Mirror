package com.lanouteam.dllo.mirror.fragments;

import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lanouteam.dllo.mirror.R;
import com.lanouteam.dllo.mirror.activity.MainActivity;

import com.lanouteam.dllo.mirror.adapters.MenuListViewAdapter;
import com.lanouteam.dllo.mirror.base.BaseFragment;
import com.lanouteam.dllo.mirror.bean.MenuListBean;
import com.lanouteam.dllo.mirror.bean.RequestUrls;
import com.lanouteam.dllo.mirror.net.NetHelper;
import com.lanouteam.dllo.mirror.net.NetListener;



/**
 * Created by dllo on 16/4/11.
 */
public class MenuFragment extends BaseFragment implements RequestUrls, NetListener {
    private ListView menuListView;
    private MainActivity activity;
    private NetHelper netHelper;
    private MenuListViewAdapter adapter;
    private LinearLayout menuLinearLayout;
    private TextView homeTv;
    private MenuListBean bean;


    @Override
    protected void initView(View view) {
        menuListView = bindView(R.id.menufragment_listview);
        menuLinearLayout = bindView(R.id.menufragment_menu);
        homeTv = bindView(R.id.menufragment_home);
        activity = (MainActivity) getContext();


        menuLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.disappearMenu();
            }
        });
        homeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.jumpViewPager(0);
            }
        });
        menuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                activity.jumpViewPager(position);
            }
        });


        netHelper = new NetHelper(getContext());
        netHelper.getJsonData(MENU_LIST, this, null);
    }

    @Override
    public int initLayout() {
        return R.layout.fragment_menu;
    }

    @Override
    public void getSuccess(Object object) {
        Gson gson = new Gson();
        bean = gson.fromJson(object.toString(), MenuListBean.class);
        adapter = new MenuListViewAdapter(bean);
        menuListView.setAdapter(adapter);

    }

    @Override
    public void getFailed(int s) {

    }

    // 设置适配器
    public void setupAdapter(int position) {
        adapter.setLine(position);
    }

}
