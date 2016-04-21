package com.lanouteam.dllo.mirror.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
        // 点击菜单的时候使Activity得ViewPager跳转
        menuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                activity.jumpViewPager(position);
            }
        });


        netHelper = new NetHelper(getContext());
        // 网络请求
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
        // 把请求下来的数据传送到适配器里
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
            // 点击空白的地方使菜单消失
            case R.id.menufragment_menu:
                activity.disappearMenu();
                break;
            // 点击返回首页跳转到首页
            case R.id.menufragment_home:
                activity.jumpViewPager(0);
                break;
            // 点击退出显示Dialog
            case R.id.menufragment_exit:
                showDialog();
                break;
        }
    }

    // 显示Dialog
    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("确定退出登录");
        // 点击确定的点击事件
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 确认退出的时候 会保存未登录的状态 并且把购物车改成登陆
                SPUtils.put(getContext(), "shoppingCar", false);
                activity.topFragment.loginTv.setText("登录");
            }
        });
        // 点击取消的点击事件
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), "取消", Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();

    }
}
