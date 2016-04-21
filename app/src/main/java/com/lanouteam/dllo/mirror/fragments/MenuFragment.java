package com.lanouteam.dllo.mirror.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.lanouteam.dllo.mirror.utils.SPUtils;


/**
 * Created by dllo on 16/4/11.
 */
public class MenuFragment extends BaseFragment implements RequestUrls, NetListener, View.OnClickListener {
    private ListView menuListView;
    private MainActivity activity;
    private NetHelper netHelper;
    private MenuListViewAdapter adapter;
    private LinearLayout menuLinearLayout;
    private TextView homeTv, exitTv;
    private MenuListBean bean;


    @Override
    protected void initView(View view) {
        activity = (MainActivity) mContext;
        menuListView = bindView(R.id.menufragment_listview);
        menuLinearLayout = bindView(R.id.menufragment_menu);
        homeTv = bindView(R.id.menufragment_home);
        exitTv = bindView(R.id.menufragment_exit);
        menuLinearLayout.setOnClickListener(this);
        homeTv.setOnClickListener(this);
        exitTv.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menufragment_menu:
                activity.disappearMenu();
                break;
            case R.id.menufragment_home:
                activity.jumpViewPager(0);
                break;
            case R.id.menufragment_exit:
                showDialog();
                break;
        }
    }

    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("退出登录");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                SPUtils.put(getContext(), "shoppingCar", false);
                activity.topFragment.loginTv.setText("登錄");


            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();

    }
}
