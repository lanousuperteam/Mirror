package com.lanouteam.dllo.mirror.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.DirectionalViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lanouteam.dllo.mirror.R;
import com.lanouteam.dllo.mirror.adapters.MainViewpagerAdapter;
import com.lanouteam.dllo.mirror.base.BaseActivity;
import com.lanouteam.dllo.mirror.fragments.GoodsFragment;
import com.lanouteam.dllo.mirror.fragments.ShoppingCarFragment;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    private ArrayList<Fragment> data;
    private MainViewpagerAdapter adapter;
    private DirectionalViewPager viewPager;


    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        data = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            data.add(new GoodsFragment());
        }
        data.add(new ShoppingCarFragment());

        adapter = new MainViewpagerAdapter(getSupportFragmentManager(),data);
        viewPager.setAdapter(adapter);
        viewPager.setOrientation(DirectionalViewPager.VERTICAL);
    }

    @Override
    protected void initView() {
        viewPager = (DirectionalViewPager) findViewById(R.id.mainactivity_viewpager);

    }
}
