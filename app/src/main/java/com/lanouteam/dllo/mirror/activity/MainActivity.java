package com.lanouteam.dllo.mirror.activity;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.DirectionalViewPager;
import android.support.v4.view.ViewPager;
import android.widget.Scroller;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lanouteam.dllo.mirror.R;
import com.lanouteam.dllo.mirror.adapters.MainViewpagerAdapter;
import com.lanouteam.dllo.mirror.base.BaseActivity;
import com.lanouteam.dllo.mirror.bean.MenuListBean;
import com.lanouteam.dllo.mirror.bean.RequestUrls;
import com.lanouteam.dllo.mirror.fragments.AllFragment;
import com.lanouteam.dllo.mirror.fragments.GoodsFragment;
import com.lanouteam.dllo.mirror.fragments.ShareFragment;
import com.lanouteam.dllo.mirror.fragments.ShoppingCarFragment;
import com.lanouteam.dllo.mirror.net.NetHelper;
import com.lanouteam.dllo.mirror.net.NetListener;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class MainActivity extends BaseActivity implements RequestUrls, NetListener, ViewPager.OnPageChangeListener {
    private ArrayList<Fragment> fragments;
    private MainViewpagerAdapter adapter;
    private DirectionalViewPager viewPager;
    private int currentItem;

    private NetHelper netHelper;


    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        fragments = new ArrayList<>();
        netHelper = new NetHelper(this);
        netHelper.getPhoneCode(MENU_LIST, this, null);


//        fragments = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            fragments.add(new GoodsFragment());
//        }
//        fragments.add(new ShoppingCarFragment());

        adapter = new MainViewpagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        viewPager.setOrientation(DirectionalViewPager.VERTICAL);

        viewPager.setOnPageChangeListener(this);

    }

    public int getCurrentItem() {
        return currentItem;
    }

    @Override
    protected void initView() {
        viewPager = bindView(R.id.mainactivity_viewpager);

    }

    public void getPositionFromPopwindow(int position) {
        //这个是设置viewPager切换过度时间的类
        ViewPagerScroller scroller = new ViewPagerScroller(this);
        scroller.setScrollDuration(50);
        scroller.initViewPagerScroll(viewPager);  //这个是设置切换过渡时间为0毫秒
        viewPager.setCurrentItem(position);
    }

    @Override
    public void getSuccess(Object object) {
        Gson gson = new Gson();
        MenuListBean bean = gson.fromJson(object.toString(), MenuListBean.class);

        for (int i = 0; i < bean.getData().getList().size(); i++) {
            String type = bean.getData().getList().get(i).getType();

            switch (type) {
                case "3":
                    fragments.add(new GoodsFragment());
                    break;
                case "4":
                    fragments.add(new ShoppingCarFragment());
                    break;
                case "6":
                    fragments.add(new AllFragment());
                    break;
                case "2":
                    fragments.add(new ShareFragment());
                    break;
            }

        }


    }

    @Override
    public void getFailed(int s) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentItem = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public class ViewPagerScroller extends Scroller {
        private int mScrollDuration;             // 滑动速度

        /**
         * 设置速度速度
         *
         * @param duration
         */
        public void setScrollDuration(int duration) {
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
