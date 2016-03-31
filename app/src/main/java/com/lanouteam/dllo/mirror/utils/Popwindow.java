package com.lanouteam.dllo.mirror.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lanouteam.dllo.mirror.R;
import com.lanouteam.dllo.mirror.activity.MainActivity;
import com.lanouteam.dllo.mirror.adapters.PopwindowListviewAdapter;

import java.util.ArrayList;


/**
 * Created by dllo on 16/3/30.
 */
public class Popwindow {
    private ListView popwindowListview;
    private Context context;
    private PopupWindow popupWindow;
    private PopwindowListviewAdapter adapter;
    private ArrayList<String> datas;
    private MainActivity activity;

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
        popupWindow.setAnimationStyle(R.style.PopupAnimation);
        popupWindow.update();

        //实例化popwindow内的组件
        initPopwindow(view);
        //给popwindow 添加信息.
        initDataPopwindow();
    }

    private void initDataPopwindow() {
        datas = new ArrayList<>();
        datas.add("全部分類");
        datas.add("遊覽平光鏡");
        datas.add("遊覽太陽鏡");
        datas.add("折扣專區");
        datas.add("專題分享");
        datas.add("我的購物車");
        datas.add("返回首頁");
        datas.add("退出");


        adapter = new PopwindowListviewAdapter(datas);
        popwindowListview.setAdapter(adapter);

        // 等待从纵向viewPager传来页数信息 来决定横线的出现和选择器的状态
    }

    private void initPopwindow(View view) {
        popwindowListview = (ListView) view.findViewById(R.id.popwindow_listview);
        popwindowListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                activity = (MainActivity) context;
                activity.getPositionFromPopwindow(position);
                popupWindow.dismiss();
            }
        });


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击popwindow界面退出popwindow
                popupWindow.dismiss();
            }
        });
    }

}
