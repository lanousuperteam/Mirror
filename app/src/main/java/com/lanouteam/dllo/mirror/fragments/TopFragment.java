package com.lanouteam.dllo.mirror.fragments;

import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.lanouteam.dllo.mirror.R;
import com.lanouteam.dllo.mirror.base.BaseFragment;

/**
 * Created by dllo on 16/4/11.
 */
public class TopFragment extends BaseFragment{
    private ImageView logoIv;
    @Override
    protected void initView(View view) {
        logoIv = bindView(R.id.topfragment_logo);
        logoIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playHeartbeatAnimation();
            }
        });

    }

    @Override
    public int initLayout() {
        return R.layout.fragment_top;
    }
    // 按钮动画
        private void playHeartbeatAnimation() {
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
                    logoIv.startAnimation(animationSet);
                }
            });
            // 实现动画的View
            logoIv.startAnimation(animationSet);
        }
}
