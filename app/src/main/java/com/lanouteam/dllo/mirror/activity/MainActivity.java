package com.lanouteam.dllo.mirror.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.DirectionalViewPager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.Scroller;

import com.google.gson.Gson;
import com.lanouteam.dllo.mirror.R;
import com.lanouteam.dllo.mirror.adapters.MainViewpagerAdapter;
import com.lanouteam.dllo.mirror.base.BaseActivity;
import com.lanouteam.dllo.mirror.bean.MenuListBean;
import com.lanouteam.dllo.mirror.bean.RequestParams;
import com.lanouteam.dllo.mirror.bean.RequestUrls;
import com.lanouteam.dllo.mirror.fragments.AllFragment;
import com.lanouteam.dllo.mirror.fragments.GoodsFragment;
import com.lanouteam.dllo.mirror.fragments.MenuFragment;
import com.lanouteam.dllo.mirror.fragments.ShareFragment;
import com.lanouteam.dllo.mirror.fragments.ShoppingCarFragment;
import com.lanouteam.dllo.mirror.fragments.TopFragment;
import com.lanouteam.dllo.mirror.net.NetHelper;
import com.lanouteam.dllo.mirror.net.NetListener;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class MainActivity extends BaseActivity implements RequestUrls, NetListener, ViewPager.OnPageChangeListener,RequestParams {
    private ArrayList<Fragment> fragments; // viewpager里的fragment集合
    private MainViewpagerAdapter adapter; // viewpager的适配器
    private DirectionalViewPager viewPager; // 自定义的viewpager
    private FrameLayout frameLayout; // 菜单的占位布局
    private TopFragment topFragment; // 最上面两个小按钮的fragment
    private MenuFragment menuFragment; // 菜单的fragment
    private NetHelper netHelper;


    // 绑定布局
    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    // 完成数据的操作
    @Override
    protected void initData() {
        // 数据初始化
        fragments = new ArrayList<>();
        netHelper = new NetHelper(this);
        // 请求网络数据
        netHelper.getJsonData(MENU_LIST, this, null);

        // 向适配器里传入数据管理器 和fragment的集合
        adapter = new MainViewpagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        // 设置viewpager的滑动方向
        viewPager.setOrientation(DirectionalViewPager.VERTICAL);

        viewPager.setOnPageChangeListener(this);

        // 向两个占位布局里加入fragment
        FragmentManager fm = getSupportFragmentManager();
        topFragment = new TopFragment();
        menuFragment = new MenuFragment();
        fm.beginTransaction().replace(R.id.mainactivity_top_fragment, topFragment).commit();
        fm.beginTransaction().replace(R.id.mainactivity_menu_fragment, menuFragment).commit();

    }

    // 绑定组件
    @Override
    protected void initView() {
        viewPager = bindView(R.id.mainactivity_viewpager);
        frameLayout = bindView(R.id.mainactivity_menu_fragment);
    }

    // 设置跳转
    public void getPositionFromPopwindow(int position) {
        //TODO: 16/4/14  需要改
        disappearMenu();
        // 这个是设置viewPager切换过度时间的类
        ViewPagerScroller scroller = new ViewPagerScroller(this);
        // 设置viewpager滑动动画的时间
        scroller.setScrollDuration(50);

        scroller.initViewPagerScroll(viewPager);
        // viewpager跳转到传入的页码数
        viewPager.setCurrentItem(position);

    }

    // 展示菜单
    public void showMenu() {
        frameLayout.setVisibility(View.VISIBLE);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.into);
        frameLayout.setAnimation(animation);
    }

    // 菜单消失
    public void disappearMenu() {
        frameLayout.setVisibility(View.GONE);
    }

    @Override
    public void getSuccess(Object object) {
        // 数据解析
        Gson gson = new Gson();
        MenuListBean bean = gson.fromJson(object.toString(), MenuListBean.class);


        // 加载fragment
        for (int i = 0; i < bean.getData().getList().size(); i++) {
            String type = bean.getData().getList().get(i).getType();// 得到fragment对应的type
            String categoryId = bean.getData().getList().get(i).getInfo_data();// fragment需要的id
            String title = bean.getData().getList().get(i).getTitle();// 得到title
            // "3"是商品 "4"是购物车 "2"是专题分享 "6"是全部分类
            switch (type) {
                case "3":
                    GoodsFragment goodsFragment = new GoodsFragment();
                    fragments.add(goodsFragment);
                    // 向商品的fragment里传入"category_id"的值
                    Bundle bundle = new Bundle();
                    bundle.putString(CATEGORY_ID, categoryId);
                    bundle.putString("title", title);
                    goodsFragment.setArguments(bundle);
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

    // 当滑动viewpager 会得到当前页面的页码数
    @Override
    public void onPageSelected(int position) {
        menuFragment.setupAdapter(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    // 设置viewpager滑动速度的类
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
