package com.lanouteam.dllo.mirror.activity;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lanouteam.dllo.mirror.R;
import com.lanouteam.dllo.mirror.base.BaseActivity;

/**
 * Created by dllo on 16/3/30.
 */
public class CreateAccountActivity extends BaseActivity implements View.OnClickListener {
    private EditText phoneNumberEt, codeEv, passwordEt;
    private Button sendCodeBtn;
    //倒计时工具类
    private CountDownTimer timer;

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
        /**
         * 分割线-----------------------------------------------------------------
         * */
        //给按钮设置监听
        findViewById(R.id.create_account_activity_create_account_btn).setOnClickListener(this);
        findViewById(R.id.create_account_activity_send_code_btn).setOnClickListener(this);
        findViewById(R.id.create_account_activity_return_iv).setOnClickListener(this);
        //创建倒计时控件
        timer = new CountDownTimer(60000, 1000) {
            /**
             * @param millisUntilFinished  当前的剩余时间
             * 每经过一次间隔时间,就会调用一次此方法
             * */
            @Override
            public void onTick(long millisUntilFinished) {
                int time = (int) (millisUntilFinished/1000);
                sendCodeBtn.setText(time+"秒后重新发送");
            }

            /**
             * 倒计时结束时调用此方法.
             * */
            @Override
            public void onFinish() {
                sendCodeBtn.setEnabled(true);
                sendCodeBtn.setText(R.string.create_acount_activity_send_code);
            }
        };

    }

    @Override
    protected void initView() {
        phoneNumberEt = bindView(R.id.create_account_activity_phone_number_et);
        codeEv = bindView(R.id.create_account_activity_code_ev);
        sendCodeBtn = bindView(R.id.create_account_activity_send_code_btn);
        passwordEt = bindView(R.id.create_account_activity_password_et);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //创建账号
            case R.id.create_account_activity_create_account_btn:
                break;
            //发送验证码
            case R.id.create_account_activity_send_code_btn:
                //点击发送验证码,然后出现60s 之后可以再次发送
                timer.start();
                sendCodeBtn.setEnabled(false);
                break;
            case R.id.create_account_activity_return_iv:
                finish();
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                break;
        }
    }

}
