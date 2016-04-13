package com.lanouteam.dllo.mirror.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by dllo on 16/3/28.
 */
public abstract class BaseFragment extends Fragment {
    //创建一个上下文对象
    public Context mContext;

    /**
     * @param context 从依附的Activity 上获取 context 对象
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    /**
     * 初始化视图的方法
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(initLayout(), container, false);
    }

    /**
     * 加载控件的方法
     * 方法： initView 加载视图
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

    }

    /**
     * 加载数据的方法
     * 方法： initData 数据加载，变更的方法
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    /**
     * 实例化组件的方法
     */
    protected abstract void initView(View view);

    /**
     * 方法：使组件实例化不需要转型的方法
     *
     * @param T  继承View 表示组件信息
     * @param id 组件的Id
     */
    protected <T extends View> T bindView(int id) {
        return (T) getView().findViewById(id);
    }

    /**
     * 加载Fragment 对应的布局
     */
    public abstract int initLayout();

    /**
     * 加载数据的方法，非抽象方法，不需要必须复写
     */
    public void initData() {

    }

}
