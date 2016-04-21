package com.lanouteam.dllo.mirror.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.lanouteam.dllo.mirror.R;
import com.lanouteam.dllo.mirror.adapters.AddressSwipeMenuListViewAdapter;
import com.lanouteam.dllo.mirror.base.BaseActivity;
import com.lanouteam.dllo.mirror.bean.AddressSwipeMenuBean;
import com.lanouteam.dllo.mirror.bean.RequestParams;
import com.lanouteam.dllo.mirror.bean.RequestUrls;
import com.lanouteam.dllo.mirror.net.NetHelper;
import com.lanouteam.dllo.mirror.net.NetListener;
import com.lanouteam.dllo.mirror.utils.DensityUtils;
import com.lanouteam.dllo.mirror.utils.SPUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by dllo on 16/4/8.
 */
public class AddressActivity extends BaseActivity implements View.OnClickListener, RequestUrls, NetListener, RequestParams {
    private List<AddressSwipeMenuBean> addressSwipeMenuBeans;
    private AddressSwipeMenuListViewAdapter addressSwipeMenuListViewAdapter;
    private SwipeMenuListView addressSwipeMenuListView;
    private Button addAddressBtn;
    private ImageView returnIv;
    private NetHelper netHelper;
    private Intent jumpAddAddressIntent;
    private String token;

    @Override
    protected int getLayout() {
        return R.layout.activity_address;
    }

    @Override
    protected void initData() {
        token = (String) SPUtils.get(this, "token",getString(R.string.add_address_activity_fail));
        addressSwipeMenuBeans = new ArrayList<>();
        //正常情况下,使用网络拉取全部地址数据
        netHelper = new NetHelper(this);
        final HashMap<String, String> mMap = new HashMap<>();
        mMap.put(TOKEN, token);
        mMap.put(DEVICE_TYPE, "2");
        netHelper.getJsonData(ADDRESS_LIST, this, mMap);
        addressSwipeMenuListViewAdapter = new AddressSwipeMenuListViewAdapter(addressSwipeMenuBeans, this, jumpAddAddressIntent);
        addressSwipeMenuListView.setAdapter(addressSwipeMenuListViewAdapter);

        //第一步:创建一个MenuCreator
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xE6,
                        0x28, 0x44)));
                // set item width
                deleteItem.setWidth(DensityUtils.dp2px(AddressActivity.this, 65));
                // set a icon
//                deleteItem.setIcon(R.mipmap.ic_launcher);
                deleteItem.setTitleSize(13);
                deleteItem.setTitle(R.string.item_delete_btn);
                deleteItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        addressSwipeMenuListView.setMenuCreator(creator);


        //第二步:子布局菜单删除按钮的点击事件
        addressSwipeMenuListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        HashMap<String, String> mMap = new HashMap<>();
                        mMap.put(TOKEN, token);
                        mMap.put(ADDR_ID, addressSwipeMenuBeans.get(position).getAddr_id());
                        addressSwipeMenuBeans.remove(position);
                        netHelper.getJsonData(DEL_ADDRESS, new NetListener() {
                            @Override
                            public void getSuccess(Object object) {
                                try {
                                    JSONObject jsonObject = new JSONObject(object.toString());
                                    String result = jsonObject.getString("result");
                                    if (result.equals("1"))
                                        Toast.makeText(AddressActivity.this, R.string.delete_address, Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void getFailed(int s) {
                                Toast.makeText(AddressActivity.this, R.string.operation_failure, Toast.LENGTH_SHORT).show();
                            }
                        }, mMap);
                        addressSwipeMenuListViewAdapter.notifyDataSetChanged();
                        break;
                }
                return false;
            }
        });

        addressSwipeMenuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> mMap = new HashMap<>();

                mMap.put(TOKEN, token);
                mMap.put(ADDR_ID, addressSwipeMenuBeans.get(position).getAddr_id());
                netHelper.getJsonData(MR_ADDRESS, new NetListener() {
                    @Override
                    public void getSuccess(Object object) {
                        try {
                            JSONObject jsonObject = new JSONObject(object.toString());

                            String result = jsonObject.getString("result");
                            if (result.equals("1")) {


                                Toast.makeText(AddressActivity.this, R.string.set_default, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void getFailed(int s) {
                        Toast.makeText(AddressActivity.this, R.string.operation_failure, Toast.LENGTH_SHORT).show();
                    }
                }, mMap);
                jumpBuyDetails();
            }
        });
        //添加地址的按钮设置点击事件
        addAddressBtn.setOnClickListener(this);
        returnIv.setOnClickListener(this);


    }

    @Override
    protected void initView() {
        addressSwipeMenuListView = bindView(R.id.activity_address_listview);
        addAddressBtn = bindView(R.id.activity_address_add_btn);
        returnIv = bindView(R.id.activity_address_return_iv);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_address_add_btn:
                jumpAddAddressIntent = new Intent(AddressActivity.this, AddAddressActivity.class);
                startActivity(jumpAddAddressIntent);
                finish();
                break;
            case R.id.activity_address_return_iv:
                jumpBuyDetails();
                break;
        }
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }


    public void jumpBuyDetails() {
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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
        try {
            JSONObject addressData = new JSONObject(object.toString());
            JSONArray addressArray = addressData.getJSONObject("data").getJSONArray("list");
            for (int i = 0; i < addressArray.length(); i++) {
                AddressSwipeMenuBean addressSwipeMenuBean = new AddressSwipeMenuBean();
                addressSwipeMenuBean.setName(addressArray.getJSONObject(i).getString("username"));
                addressSwipeMenuBean.setAddr_id(addressArray.getJSONObject(i).getString("addr_id"));
                addressSwipeMenuBean.setAddress(addressArray.getJSONObject(i).getString("addr_info"));
                addressSwipeMenuBean.setIf_moren(addressArray.getJSONObject(i).getString("if_moren"));
                addressSwipeMenuBean.setTel(addressArray.getJSONObject(i).getString("cellphone"));
                addressSwipeMenuListViewAdapter.setData(addressSwipeMenuBean);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getFailed(int s) {

    }

}
