package com.lanouteam.dllo.mirror.activity;

import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;

import com.lanouteam.dllo.mirror.R;
import com.lanouteam.dllo.mirror.base.BaseActivity;

/**
 * Created by dllo on 16/3/30.
 */
public class CreatAccountActivity extends BaseActivity {

    @Override
    protected int getLayout() {
        return R.layout.activity_create_account;
    }

    @Override
    protected void initData() {
        //按钮
        /**
         * playHeartbeatAnimation()该方法为实现Mirror动画效果
         * @Param AnimationSet 动画集合
         * @Param Duration 动画持续时间
         * @Param RELATIVE_TO_SELF 相对于自身变化点在中心(0.5f,o.5f)
         * @Param Interpolator 被用来修饰动画效果，定义动画的变化率，
        可以使存在的动画效果accelerated(加速)，decelerated(减速),repeated(重复),bounced(弹跳)等
         @Param setFillAfter 动画终止时停留在最后一帧~不然会回到没有执行之前的状态
          * */

        // 按钮动画
//        private void playHeartbeatAnimation() {
//            AnimationSet animationSet = new AnimationSet(true);
//            animationSet.addAnimation(new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f,//参数为原图的X轴从1.0到1.2倍,Y轴从1.0到1.2倍
//                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
//                    0.5f));
//            animationSet.setDuration(200);
//            animationSet.setInterpolator(new AccelerateInterpolator());
//            animationSet.setFillAfter(true);
//            animationSet.setAnimationListener(new Animation.AnimationListener() {
//                @Override
//                public void onAnimationStart(Animation animation) {
//
//                }
//
//                @Override
//                public void onAnimationRepeat(Animation animation) {
//                }
//
//                @Override
//                public void onAnimationEnd(Animation animation) {
//                    AnimationSet animationSet = new AnimationSet(true);
//                    animationSet.addAnimation(new ScaleAnimation(0.7f, 1.0f, 0.7f,
//                            1.0f, Animation.RELATIVE_TO_SELF, 0.5f,
//                            Animation.RELATIVE_TO_SELF, 0.5f));
//                    animationSet.setDuration(400);
//                    animationSet.setInterpolator(new DecelerateInterpolator());
//                    animationSet.setFillAfter(true);
//                    // 实现心跳的View
//                    ivMirror.startAnimation(animationSet);
//                }
//            });
//            // 实现动画的View
//            ivMirror.startAnimation(animationSet);
//        }
    }

    @Override
    protected void initView() {

    }
}
