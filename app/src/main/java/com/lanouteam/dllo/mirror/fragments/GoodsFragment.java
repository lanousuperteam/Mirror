package com.lanouteam.dllo.mirror.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lanouteam.dllo.mirror.R;
import com.lanouteam.dllo.mirror.base.BaseFragment;
import com.lanouteam.dllo.mirror.utils.Popwindow;


/**
 * Created by dllo on 16/3/30.
 */
public class GoodsFragment extends BaseFragment implements View.OnClickListener {
    private LinearLayout linearLayout;
    private Popwindow popwindow;


    @Override
    protected void initView(View view) {
        linearLayout = (LinearLayout) view.findViewById(R.id.goodsfragment_title_linearlayout);
        popwindow = new Popwindow(getContext());
        linearLayout.setOnClickListener(this);
    }

    @Override
    public int initLayout() {
        return R.layout.fragment_goods;
    }

    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.goodsfragment_title_linearlayout:
               popwindow.showPopUpWindow(v);
               break;
       }
    }
}
