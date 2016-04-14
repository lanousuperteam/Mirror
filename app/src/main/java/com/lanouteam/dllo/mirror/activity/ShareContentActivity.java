package com.lanouteam.dllo.mirror.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.DirectionalViewPager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.lanouteam.dllo.mirror.R;
import com.lanouteam.dllo.mirror.adapters.ShareContentViewPagerAdapter;
import com.lanouteam.dllo.mirror.base.BaseActivity;
import com.lanouteam.dllo.mirror.bean.RequestParams;
import com.lanouteam.dllo.mirror.bean.RequestUrls;
import com.lanouteam.dllo.mirror.bean.ShareContentBean;
import com.lanouteam.dllo.mirror.fragments.ShareContentFragment;
import com.lanouteam.dllo.mirror.net.NetHelper;
import com.lanouteam.dllo.mirror.net.NetListener;
import com.lanouteam.dllo.mirror.utils.LoginAndShare;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dllo on 16/4/13.
 */
public class ShareContentActivity extends BaseActivity implements RequestUrls, RequestParams, NetListener {
    private ArrayList<Fragment> fragments;
    private DirectionalViewPager viewPager;
    private ShareContentViewPagerAdapter adapter;
    private NetHelper netHelper;
    private HashMap hashMap;
    private ShareContentBean bean;
    private ImageView background, returnIv, shareIv;
    private LoginAndShare loginAndShare;

    private String shareId;

    @Override
    protected int getLayout() {
        return R.layout.activity_share_content;
    }

    @Override
    protected void initData() {
        fragments = new ArrayList<>();
        loginAndShare = new LoginAndShare();

        shareId = "id=2&from=singlemessage&isappinstalled=1";

        hashMap = new HashMap();
        hashMap.put(DEVICE_TYPE, "2");
        hashMap.put(STORY_ID, "2");

        netHelper = new NetHelper(this);
        netHelper.getJsonData(STORY_INFO, this, hashMap);

        adapter = new ShareContentViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        viewPager.setOrientation(DirectionalViewPager.VERTICAL);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                background.setImageURI(Uri.parse(bean.getData().getStory_data().getImg_array().get(position)));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        returnIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        shareIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginAndShare.MyShare("http://api.mirroreye.cn/index.php/storyweb/share_array?",shareId);
            }
        });
    }

    @Override
    protected void initView() {
        viewPager = bindView(R.id.sharecontentactivity_viewpager);
        background = bindView(R.id.sharecontentactivity_background);
        returnIv = bindView(R.id.sharecontentactivity_delete_iv);
        shareIv = bindView(R.id.sharecontentactivity_share_iv);
    }

    @Override
    public void getSuccess(Object object) {
        Gson gson = new Gson();
        bean = gson.fromJson(object.toString(), ShareContentBean.class);

        for (int i = 0; i < bean.getData().getStory_data().getText_array().size(); i++) {
            String tagText = bean.getData().getStory_data().getText_array().get(i).getSmallTitle();
            String titleText = bean.getData().getStory_data().getText_array().get(i).getTitle();
            String contentText = bean.getData().getStory_data().getText_array().get(i).getSubTitle();
            ShareContentFragment fragment = new ShareContentFragment();
            fragments.add(fragment);
            Bundle bundle = new Bundle();
            bundle.putString("tag", tagText);
            bundle.putString("title", titleText);
            bundle.putString("content", contentText);
            fragment.setArguments(bundle);
        }

        background.setImageURI(Uri.parse(bean.getData().getStory_data().getImg_array().get(0)));
    }

    @Override
    public void getFailed(int s) {

    }
}
