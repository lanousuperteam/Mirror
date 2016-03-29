package com.lanouteam.dllo.mirror.activity;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lanouteam.dllo.mirror.R;
import com.lanouteam.dllo.mirror.base.BaseActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by dllo on 16/3/29.
 */
public class WelcomeActivity extends BaseActivity {
    private ImageView imageView;

    @Override
    protected int getLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initData() {
        final Intent intent=new Intent(this,MainActivity.class);
        Timer timer=new Timer();
        TimerTask task=new TimerTask() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        };
        timer.schedule(task, 1000 * 2);//2s后跳转    安排在指定的时间执行指定的任务。


        //Timer类是一种线程设施，可以用来实现某一个时间或某一段时间后安排某一个任务执行一次或定期重复执行。
        // 该功能和TimerTask配合使用。TimerTask类用于实现由Timer安排的一次或重复执行的某个任务。
        // 每一个Timer对象对应的是一个线程，因此计时器所执行的任务应该迅速完成，否则会延迟后续的任务执行。
    }

    @Override
    protected void initView() {
     imageView=bindView(R.id.welcome_activity_imageview);
    }
}
