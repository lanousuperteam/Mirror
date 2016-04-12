package com.lanouteam.dllo.mirror.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lanouteam.dllo.mirror.R;
import com.lanouteam.dllo.mirror.base.BaseActivity;
import com.lanouteam.dllo.mirror.bean.RequestParams;
import com.lanouteam.dllo.mirror.bean.RequestUrls;
import com.lanouteam.dllo.mirror.db.AddressData;
import com.lanouteam.dllo.mirror.db.AddressDataHelper;
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

    @Override
    protected int getLayout() {
        return R.layout.activity_indent_details_page;
    }

    @Override
    protected void initData() {
        //网络拉取默认地址
        NetHelper netHelper = new NetHelper(this);
        HashMap<String, String> mMap = new HashMap<>();
        //等之前数据传过来在进行解析.
        mMap.put(TOKEN, "a8205d6b776b7ee55f440ba0e6756c40");
        mMap.put(DEVICE_TYPE, "2");
        netHelper.getJsonData(SHOPPING_CART_LIST, this, mMap);


        AddressDataHelper addressDataHelper = new AddressDataHelper(this, "AddressData");
        AddressData addressData = addressDataHelper.queryFirstData();
        if (addressData == null) {
            contactPersonTv.setText("请添加收件人信息");
        } else {
            contactPersonTv.setText("收件人:" + addressData.getName());
            telTv.setText("Tel:" + addressData.getTel());
            addressDataTv.setText("地址:" + addressData.getAddress());
        }
        addressTv.setOnClickListener(this);
        returnIv.setOnClickListener(this);
    }

    @Override
    protected void initView() {
        addressTv = bindView(R.id.activity_indent_details_page_address);
        returnIv = bindView(R.id.activity_indent_details_page_return_iv);
        contactPersonTv = bindView(R.id.activity_indent_details_page_contact_person_tv);
        telTv = bindView(R.id.activity_indent_details_page_tel_tv);
        addressDataTv = bindView(R.id.activity_indent_details_page_address_tv);
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
}
