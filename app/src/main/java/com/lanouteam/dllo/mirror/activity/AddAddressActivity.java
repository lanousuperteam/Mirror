package com.lanouteam.dllo.mirror.activity;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.lanouteam.dllo.mirror.R;
import com.lanouteam.dllo.mirror.base.BaseActivity;
import com.lanouteam.dllo.mirror.bean.AddressSwipeMenuBean;
import com.lanouteam.dllo.mirror.bean.RequestParams;
import com.lanouteam.dllo.mirror.bean.RequestUrls;
import com.lanouteam.dllo.mirror.net.NetHelper;
import com.lanouteam.dllo.mirror.net.NetListener;
import com.lanouteam.dllo.mirror.utils.L;
import com.lanouteam.dllo.mirror.utils.SPUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by dllo on 16/4/9.
 */
public class AddAddressActivity extends BaseActivity implements View.OnClickListener, RequestUrls, RequestParams, NetListener {
    private ImageView returnIv;
    private EditText nameEt, telEt, addressEt;
    private Button addAddressBtn;
    private NetHelper netHelper;
    private String addressId,token;




    @Override
    protected int getLayout() {
        return R.layout.activity_add_address;
    }

    @Override
    protected void initData() {
        token= (String) SPUtils.get(this,"token",getString(R.string.add_address_activity_fail));
        netHelper = new NetHelper(AddAddressActivity.this);
        //接收传递过来的地址信息类
        Intent intent = this.getIntent();
        AddressSwipeMenuBean addressData = (AddressSwipeMenuBean) intent.getSerializableExtra("address");
        if (addressData != null) {
            nameEt.setText(addressData.getName());
            telEt.setText(addressData.getTel());
            addressEt.setText(addressData.getAddress());
            addressId = addressData.getAddr_id();
        }
        returnIv.setOnClickListener(this);
        addAddressBtn.setOnClickListener(this);

    }

    @Override
    protected void initView() {
        returnIv = bindView(R.id.activity_add_address_return_iv);
        nameEt = bindView(R.id.activity_add_address_name_et);
        telEt = bindView(R.id.activity_add_address_tel_et);
        addressEt = bindView(R.id.activity_add_address_et);
        addAddressBtn = bindView(R.id.activity_add_address_commit_btn);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_add_address_return_iv:
                jumpBuyDetails();
                break;
            case R.id.activity_add_address_commit_btn:
                String name = nameEt.getText().toString();
                String tel = telEt.getText().toString();
                String address = addressEt.getText().toString();
                HashMap<String, String> mMap = new HashMap<>();
                //设置判断条件
                if (name.equals("") || tel.equals("") || address.equals("")) {
                    Toast.makeText(AddAddressActivity.this, R.string.check_information_error, Toast.LENGTH_SHORT).show();
                    break;
                } else if (tel.length() != 11 || !tel.substring(0, 1).equals("1")) {
                    Toast.makeText(AddAddressActivity.this, R.string.check_phone_error, Toast.LENGTH_SHORT).show();
                    break;
                }

                if (addressId != null) {
                    //修改原来的地址信息,传到服务器上
                    mMap.put(TOKEN, token);
                    mMap.put(ADDR_ID, addressId);
                    mMap.put(USERNAME, name);
                    mMap.put(CELLPHONE, tel);
                    mMap.put(ADDR_INFO, address);
                    netHelper.getJsonData(EDIT_ADDRESS, this, mMap);
                } else {
                    //提交新的地址信息上传到服务器上
                    mMap.put(TOKEN, token);
                    mMap.put(USERNAME, name);
                    mMap.put(CELLPHONE, tel);
                    mMap.put(ADDR_INFO, address);
                    netHelper.getJsonData(ADD_ADDRESS, this, mMap);
                }
                jumpBuyDetails();
                break;
        }
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private void jumpBuyDetails() {
        Intent jumpBuyDetailsIntent = new Intent(AddAddressActivity.this, AddressActivity.class);
        startActivity(jumpBuyDetailsIntent);
        finish();
    }


    //此方法是设置返回键的同时,使返回键失效,利用返回键进行跳转.
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            jumpBuyDetails();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void getSuccess(Object object) {
        L.d(object.toString());
        try {
            JSONObject jsonObject = new JSONObject(object.toString());
            String result = jsonObject.getString("result");
            if (result.equals("1") && addressId == null) {
                Toast.makeText(AddAddressActivity.this, R.string.add_address_state, Toast.LENGTH_SHORT).show();
            } else if (result.equals("1") && addressId != null) {
                Toast.makeText(AddAddressActivity.this, R.string.edit_address, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getFailed(int s) {
        Toast.makeText(AddAddressActivity.this, R.string.operation_failure, Toast.LENGTH_SHORT).show();
    }

}
