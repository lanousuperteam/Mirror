package com.lanouteam.dllo.mirror.fragments;

import android.view.View;
import android.widget.LinearLayout;

import com.lanouteam.dllo.mirror.R;
import com.lanouteam.dllo.mirror.activity.MainActivity;
import com.lanouteam.dllo.mirror.base.BaseFragment;

/**
 * Created by dllo on 16/3/30.
 */
public class ShoppingCarFragment extends BaseFragment{
    private LinearLayout shoppingLinearLayout;// 标题的linearlayout 用来设置监听
    private MainActivity activity;

    @Override
    protected void initView(View view) {
        shoppingLinearLayout = bindView(R.id.shoppingcar_title_linearlayout);
        activity = (MainActivity) getContext();

        // 点击标题弹出菜单
        shoppingLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.showMenu();
            }
        });
    }

    @Override
    public int initLayout() {
        return R.layout.fragment_shoppingcar;
    }
}
