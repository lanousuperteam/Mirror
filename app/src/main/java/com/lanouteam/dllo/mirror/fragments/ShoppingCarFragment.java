package com.lanouteam.dllo.mirror.fragments;

import android.view.View;
import android.widget.LinearLayout;

import com.lanouteam.dllo.mirror.R;
import com.lanouteam.dllo.mirror.base.BaseFragment;
import com.lanouteam.dllo.mirror.utils.Popwindow;

/**
 * Created by dllo on 16/3/30.
 */
public class ShoppingCarFragment extends BaseFragment{
    private LinearLayout linearLayout;
    private Popwindow popwindow;

    @Override
    protected void initView(View view) {
        linearLayout = bindView(R.id.shoppingcar_title_linearlayout);

        popwindow = new Popwindow(getContext());
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popwindow.showPopUpWindow(v);
            }
        });
    }

    @Override
    public int initLayout() {
        return R.layout.fragment_shoppingcar;
    }
}
