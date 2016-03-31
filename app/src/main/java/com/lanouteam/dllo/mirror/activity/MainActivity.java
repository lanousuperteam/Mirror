package com.lanouteam.dllo.mirror.activity;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.DirectionalViewPager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Scroller;

import com.lanouteam.dllo.mirror.R;
import com.lanouteam.dllo.mirror.adapters.MainViewpagerAdapter;
import com.lanouteam.dllo.mirror.base.BaseActivity;
import com.lanouteam.dllo.mirror.fragments.GoodsFragment;
import com.lanouteam.dllo.mirror.fragments.ShoppingCarFragment;

import java.lang.reflect.Field;
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
        viewPager = bindView(R.id.mainactivity_viewpager);

    }

    public void getPositionFromPopwindow(int position){
        //这个是设置viewPager切换过度时间的类
        ViewPagerScroller scroller = new ViewPagerScroller(this);
        scroller.setScrollDuration(50);
        scroller.initViewPagerScroll(viewPager);  //这个是设置切换过渡时间为0毫秒
        viewPager.setCurrentItem(position);
    }

    public class ViewPagerScroller extends Scroller {
        private int mScrollDuration = 2000;             // 滑动速度

        /**
         * 设置速度速度
         * @param duration
         */
        public void setScrollDuration(int duration){
            this.mScrollDuration = duration;
        }

        public ViewPagerScroller(Context context) {
            super(context);
        }


        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, mScrollDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy, mScrollDuration);
        }



        public void initViewPagerScroll(ViewPager viewPager) {
            try {
                Field mScroller = ViewPager.class.getDeclaredField("mScroller");
                mScroller.setAccessible(true);
                mScroller.set(viewPager, this);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }


}
