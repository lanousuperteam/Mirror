package com.lanouteam.dllo.mirror.activity;

import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.lanouteam.dllo.mirror.R;
import com.lanouteam.dllo.mirror.base.BaseActivity;
import com.lanouteam.dllo.mirror.bean.RequestParams;
import com.lanouteam.dllo.mirror.bean.RequestUrls;
import com.lanouteam.dllo.mirror.net.NetHelper;
import com.lanouteam.dllo.mirror.net.NetListener;

import java.util.HashMap;

/**
 * Created by dllo on 16/4/8.
 * 购买详情界面
 */
public class BuyDetailsActivity extends BaseActivity implements View.OnClickListener, RequestParams, RequestUrls, NetListener {
    private TextView addressTv, contactPersonTv, telTv, addressDataTv;
    private ImageView returnIv;
    private Button submitOrderBtn;
    private PopupWindow popupWindow;

    @Override
    protected int getLayout() {
        return R.layout.activity_indent_details_page;
    }

    @Override
    protected void initData() {
        //TODO网络拉取默认地址
        NetHelper netHelper = new NetHelper(this);
        HashMap<String, String> mMap = new HashMap<>();
        //等之前数据传过来在进行解析.
        mMap.put(TOKEN, "a8205d6b776b7ee55f440ba0e6756c40");
        mMap.put(DEVICE_TYPE, "2");
        netHelper.getJsonData(SHOPPING_CART_LIST, this, mMap);
        addressTv.setOnClickListener(this);
        returnIv.setOnClickListener(this);
        submitOrderBtn.setOnClickListener(this);
    }

    @Override
    protected void initView() {
        addressTv = bindView(R.id.activity_indent_details_page_address);
        returnIv = bindView(R.id.activity_indent_details_page_return_iv);
        contactPersonTv = bindView(R.id.activity_indent_details_page_contact_person_tv);
        telTv = bindView(R.id.activity_indent_details_page_tel_tv);
        addressDataTv = bindView(R.id.activity_indent_details_page_address_tv);
        submitOrderBtn=bindView(R.id.activity_indent_details_page_submit_order_btn);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_indent_details_page_address:
                Intent jumpAllAddressIntent = new Intent(BuyDetailsActivity.this, AddressActivity.class);
                startActivity(jumpAllAddressIntent);
                finish();
                break;
            case R.id.activity_indent_details_page_return_iv:
                finish();
                break;
            case R.id.activity_indent_details_page_submit_order_btn:
                //TODO 弹出PopWindow
                appearPay(v);
                break;
        }
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }


    @Override
    public void getSuccess(Object object) {
        Toast.makeText(BuyDetailsActivity.this, object.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getFailed(int s) {

    }

    public void appearPay(View v){
        popupWindow = new PopupWindow(this);
        View view = LayoutInflater.from(this).inflate(R.layout.popwindow_mainactivity, null);
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

    }

    private void initPopwindow(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击popwindow界面退出popwindow
                popupWindow.dismiss();
            }
        });
    }
}
