package com.lanouteam.dllo.mirror.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by dllo on 16/3/30.
 */
public class MainViewpagerAdapter extends FragmentPagerAdapter{
    private ArrayList<Fragment> data;// 在适配器里加的fragment的集合

    // 构造方法 传进布局管理器和fragment的集合
    public MainViewpagerAdapter(FragmentManager fm, ArrayList<Fragment> data) {
        super(fm);
        this.data = data;
    }

    // 得到每个fragment
    @Override
    public Fragment getItem(int position) {
        return data.get(position);
    }

    // 得到fragment的个数
    @Override
    public int getCount() {
        return data.size();
    }
}
