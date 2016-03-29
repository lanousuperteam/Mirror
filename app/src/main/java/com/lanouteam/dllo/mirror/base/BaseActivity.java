package com.lanouteam.dllo.mirror.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by dllo on 16/3/28.
 */
public abstract class BaseActivity extends AppCompatActivity {
    /**
     * 方法：initView 初始化组件
     * 方法：initData 初始化、变更数据
     * @param getLayout（）获得布局Id
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        initView();
        initData();
    }

    /**
     * 获得Activity 对应的布局
     * */
    protected abstract int getLayout();

    /**
     * 加载Activity信息的方法
     * */
    protected abstract void initData();

    /**
     * 加载Activity 组件的方法
     * */
    protected abstract void initView();

    /**
     * 方法：使组件实例化不需要转型的方法
     * @param T 继承View 表示组件信息
     * @param id 组件的Id
     * */
    protected <T extends View> T bindView(int id) {
        return (T) findViewById(id);
    }
}
