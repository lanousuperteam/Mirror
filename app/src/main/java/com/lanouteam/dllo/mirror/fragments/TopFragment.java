package com.lanouteam.dllo.mirror.fragments;

import android.content.Intent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanouteam.dllo.mirror.R;
import com.lanouteam.dllo.mirror.activity.LoginActivity;
import com.lanouteam.dllo.mirror.activity.MainActivity;
import com.lanouteam.dllo.mirror.base.BaseApplication;
import com.lanouteam.dllo.mirror.base.BaseFragment;
import com.lanouteam.dllo.mirror.db.AddressDataHelperSingle;
import com.lanouteam.dllo.mirror.utils.SPUtils;

import de.greenrobot.event.EventBus;


/**
 * Created by dllo on 16/4/11.
 */
public class TopFragment extends BaseFragment implements View.OnClickListener {
    private ImageView logoIv;// 左边的logo图标
    public TextView loginTv;// 右边的文字
    private AddressDataHelperSingle addressDataHelperSingle;
    private MainActivity mainActivity;
    private boolean isTrue;


    @Override
    protected void initView(View view) {
        logoIv = bindView(R.id.topfragment_logo);
        loginTv = bindView(R.id.topfragment_login);
        // 注册成为eventBus观察者
        EventBus.getDefault().register(this);
        addressDataHelperSingle = AddressDataHelperSingle.getInstance(BaseApplication.mContext);


    }

    @Override
    public void initData() {
        // 获取是否登录状态 从而决定显示的文字
        isTrue = (boolean) SPUtils.get(getContext(), "shoppingCar", true);
        if (isTrue) {
            loginTv.setText("購物車");
        } else {
            loginTv.setText("登錄");
        }
        logoIv.setOnClickListener(this);
        loginTv.setOnClickListener(this);
        mainActivity = (MainActivity) getContext();
    }

    @Override
    public int initLayout() {
        return R.layout.fragment_top;
    }

    // 按钮动画
    private void playHeartbeatAnimation(final View view) {
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f,//参数为原图的X轴从1.0到1.2倍,Y轴从1.0到1.2倍
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f));
        animationSet.setDuration(200);
        animationSet.setInterpolator(new AccelerateInterpolator());
        animationSet.setFillAfter(true);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                AnimationSet animationSet = new AnimationSet(true);
                animationSet.addAnimation(new ScaleAnimation(0.7f, 1.0f, 0.7f,
                        1.0f, Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f));
                animationSet.setDuration(400);
                animationSet.setInterpolator(new DecelerateInterpolator());
                animationSet.setFillAfter(true);
                // 实现心跳的View
                view.startAnimation(animationSet);
            }
        });
        // 实现动画的View
        view.startAnimation(animationSet);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 点击logo会出现动画效果
            case R.id.topfragment_logo:
                playHeartbeatAnimation(logoIv);
                break;
            // 右下角如果是登录 会跳转到登录界面 如果是购物车 则会跳转到购物车界面
            case R.id.topfragment_login:
                if (loginTv.getText().equals("購物車")) {
                    mainActivity.jumpViewPager(3);
                    playHeartbeatAnimation(loginTv);
                } else {
                    Intent intentLogin = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intentLogin);
                }
                break;
        }

    }
    // 通过eventBus得到
    public void onEventMainThread(String event) {
        if (addressDataHelperSingle.queryListName(event) != null) {
            loginTv.setText("購物車");
        } else {
            loginTv.setText("登錄");
        }
    }

    // 取消注册
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
