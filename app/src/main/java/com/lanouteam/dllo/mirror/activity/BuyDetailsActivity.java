package com.lanouteam.dllo.mirror.activity;

import android.widget.TextView;

import com.lanouteam.dllo.mirror.R;
import com.lanouteam.dllo.mirror.base.BaseActivity;

/**
 * Created by dllo on 16/4/8.
 * 购买详情界面
 */
public class BuyDetailsActivity extends BaseActivity {
    private TextView indentDetailsPageAddress;

    @Override
    protected int getLayout() {
        return R.layout.activity_indent_details_page;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        indentDetailsPageAddress = bindView(R.id.activity_indent_details_page_address);
    }
}
