package com.lanouteam.dllo.mirror.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lanouteam.dllo.mirror.R;


/**
 * Created by dllo on 16/3/30.
 */
public class Popwindow implements View.OnClickListener {
    private TextView allTv, shineTv, sunTv, shareTv, shopTv, returnTv, exitTv;
    private ImageView allLine, shineLine, sunLine, shareLine, shopLine, returnLine, exitLine;
    private Context context;
    private PopupWindow popupWindow;

    public Popwindow(Context context) {
        this.context = context;
    }

    public void showPopUpWindow(View v) {
        popupWindow = new PopupWindow(context);
        View view = LayoutInflater.from(context).inflate(R.layout.popwindow_mainactivity, null);

        popupWindow.setContentView(view);
        popupWindow.setBackgroundDrawable(null);
        popupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
        //使Popwindow附加动画
        popupWindow.setAnimationStyle(R.anim.into);
        popupWindow.update();
        
        //实例化popwindow内的组件
        initPopwindow(view);
        //给popwindow 添加信息.
        initDataPopwindow();
    }

    private void initDataPopwindow() {
        allTv.setOnClickListener(this);
        shineTv.setOnClickListener(this);
        sunTv.setOnClickListener(this);
        shareTv.setOnClickListener(this);
        shopTv.setOnClickListener(this);
        returnTv.setOnClickListener(this);
        exitTv.setOnClickListener(this);

        // 等待从纵向viewPager传来页数信息 来决定横线的出现和选择器的状态
    }

    private void initPopwindow(View view) {
        allTv = (TextView) view.findViewById(R.id.popwindow_all_tv);
        shineTv = (TextView) view.findViewById(R.id.popwindow_shine_tv);
        sunTv = (TextView) view.findViewById(R.id.popwindow_sun_tv);
        shareTv = (TextView) view.findViewById(R.id.popwindow_share_tv);
        shopTv = (TextView) view.findViewById(R.id.popwindow_shop_tv);
        returnTv = (TextView) view.findViewById(R.id.popwindow_return_tv);
        exitTv = (TextView) view.findViewById(R.id.popwindow_exit_tv);

        allLine = (ImageView) view.findViewById(R.id.popwindow_all_line_iv);
        shineLine = (ImageView) view.findViewById(R.id.popwindow_shine_line_iv);
        sunLine = (ImageView) view.findViewById(R.id.popwindow_sun_line_iv);
        shareLine = (ImageView) view.findViewById(R.id.popwindow_share_line_iv);
        shopLine = (ImageView) view.findViewById(R.id.popwindow_shop_line_iv);
        returnLine = (ImageView) view.findViewById(R.id.popwindow_return_line_iv);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击popwindow界面退出popwindow
                popupWindow.dismiss();
            }
        });
    }

    // 设置监听事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.popwindow_all_tv:
                // 等待实现纵向viewPager跳转功能
                allLine.setVisibility(View.VISIBLE);
                break;
            case R.id.popwindow_shine_tv:
                shineLine.setVisibility(View.VISIBLE);
                break;
            case R.id.popwindow_sun_tv:
                sunLine.setVisibility(View.VISIBLE);
                break;
            case R.id.popwindow_share_tv:
                shareLine.setVisibility(View.VISIBLE);
                break;
            case R.id.popwindow_shop_tv:
                shopLine.setVisibility(View.VISIBLE);
                break;
            case R.id.popwindow_return_tv:
                returnLine.setVisibility(View.VISIBLE);
                break;
            case R.id.popwindow_exit_tv:
                break;
        }
    }
}
