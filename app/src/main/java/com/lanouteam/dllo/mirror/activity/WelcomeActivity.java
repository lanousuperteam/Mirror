package com.lanouteam.dllo.mirror.activity;

import android.content.Intent;
import android.util.Log;
import android.widget.ImageView;

import com.lanouteam.dllo.mirror.R;
import com.lanouteam.dllo.mirror.base.BaseActivity;
import com.lanouteam.dllo.mirror.bean.RequestUrls;
import com.lanouteam.dllo.mirror.net.NetHelper;
import com.lanouteam.dllo.mirror.net.NetListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by dllo on 16/3/29.
 */
public class WelcomeActivity extends BaseActivity implements RequestUrls,NetListener {
    private ImageView imageView;

    @Override
    protected int getLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initData() {
        NetHelper welcomeNetHelper =new NetHelper(this);
        HashMap<String,String> mMap =new HashMap<>();
        mMap=null;
        welcomeNetHelper.getPhoneCode(STARTED_IMG,this,mMap);
        /***
         * Timer类是一种线程设施，可以用来实现某一个时间或某一段时间后安排某一个任务执行一次或定期重复执行。
         * 该功能和TimerTask配合使用。TimerTask类用于实现由Timer安排的一次或重复执行的某个任务。
         * 每一个Timer对象对应的是一个线程，因此计时器所执行的任务应该迅速完成，否则会延迟后续的任务执行。
         *
         * */
        final Intent intent=new Intent(this,MainActivity.class);
        Timer timer=new Timer();
        TimerTask task=new TimerTask() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        };
        //2s后跳转  安排在指定的时间执行指定的任务。
        timer.schedule(task, 1000 * 5);
    }

    @Override
    protected void initView() {
     imageView=bindView(R.id.welcome_activity_imageview);
    }


    @Override
    public void getSuccess(Object object) {
        try {
            JSONObject obj =new JSONObject(object.toString());
            String imageUrl =obj.getString("img");
            //TODO  未完待续等待加载图片选择好
            Log.d("imageUrl",imageUrl);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getFailed(int s) {

    }
}
