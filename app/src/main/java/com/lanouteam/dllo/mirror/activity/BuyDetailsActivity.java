package com.lanouteam.dllo.mirror.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.lanouteam.dllo.mirror.R;
import com.lanouteam.dllo.mirror.base.BaseActivity;
import com.lanouteam.dllo.mirror.bean.AddressBean;
import com.lanouteam.dllo.mirror.bean.GoodsContentBean;
import com.lanouteam.dllo.mirror.bean.PayBean;
import com.lanouteam.dllo.mirror.bean.RequestParams;
import com.lanouteam.dllo.mirror.bean.RequestUrls;
import com.lanouteam.dllo.mirror.net.NetHelper;
import com.lanouteam.dllo.mirror.net.NetListener;
import com.lanouteam.dllo.mirror.utils.SPUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by dllo on 16/4/8.
 * 购买详情界面
 */
public class BuyDetailsActivity extends BaseActivity implements View.OnClickListener, RequestParams, RequestUrls {
    private TextView addressTv, contactPersonTv, telTv, addressDataTv, titleTv, detailsTv, moneryTv;
    private ImageView returnIv, goodsIv;
    private Button submitOrderBtn, aliPayBtn;
    private PopupWindow popupWindow;
    private FrameLayout maskLayerLayout;
    private String id, token, goodsName, orderId, addressId, str,goodsPrice;
    private NetHelper netHelper;
    private HashMap<String, String> listMap, goodsInfoMap,payMap,serverMap;
    //支付宝调用
    private static final int SDK_PAY_FLAG = 1;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResultActvity payResultActvity = new PayResultActvity((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResultActvity.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResultActvity.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(BuyDetailsActivity.this, R.string.buy_details_activity_pay_success, Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(BuyDetailsActivity.this, R.string.buy_details_activity_pay_waiting, Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(BuyDetailsActivity.this, R.string.buy_details_activity_pay_fail, Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    @Override
    protected int getLayout() {
        return R.layout.activity_indent_details_page;
    }

    @Override
    protected void initData() {
        token = (String) SPUtils.get(this, "token",getString(R.string.add_address_activity_fail));
        Intent intentBuy = getIntent();
        id = intentBuy.getStringExtra(RequestParams.GOODS_ID);
        goodsPrice=intentBuy.getStringExtra(RequestParams.PRICE);
        Log.d("token", id + "  ");

        //TODO网络拉取默认地址
        netHelper = new NetHelper(this);
        listMap = new HashMap<>();
        goodsInfoMap = new HashMap<>();
        payMap = new HashMap<>();
        serverMap=new HashMap<>();


        //解析地址:ADDRESS_LIST
        listMap.put(TOKEN, token);
        listMap.put(DEVICE_TYPE, "2");

        //解析商品信息:GOODS_INFO
        goodsInfoMap.put(GOODS_ID, id);
        goodsInfoMap.put(DEVICE_TYPE, "2");

        //解析下订单:ORDER_SUB 为了得到订单id 地址id 和商品名
        serverMap.put(TOKEN, token);
        serverMap.put(DEVICE_TYPE, "2");
        serverMap.put(GOODS_ID, id);
        serverMap.put(PRICE,goodsPrice);
        serverMap.put(GOODS_NUM, "1");
        orderGetSiccess();

        //解析支付宝数据:ALI_PAY_SUB
        payMap.put(TOKEN, token);
        payMap.put(ORDER_NO, orderId);
        payMap.put(ADDR_ID, addressId);
        payMap.put(GOODSNAME, goodsName);
        goodsInfoGetSuccess();

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
        submitOrderBtn = bindView(R.id.activity_indent_details_page_submit_order_btn);
        maskLayerLayout = bindView(R.id.mask_layer_layout);
        titleTv = bindView(R.id.activity_indent_details_page_title_tv);
        detailsTv = bindView(R.id.activity_indent_details_page_details_tv);
        moneryTv = bindView(R.id.activity_indent_details_page_monery_tv);
        goodsIv = bindView(R.id.activity_indent_details_page_goods_iv);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_indent_details_page_address:
                Intent jumpAllAddressIntent = new Intent(BuyDetailsActivity.this, AddressActivity.class);
                startActivity(jumpAllAddressIntent);

                break;
            case R.id.activity_indent_details_page_return_iv:
                finish();
                break;
            case R.id.activity_indent_details_page_submit_order_btn:
                paySubGetSuccess();
                appearPay(v);
                maskLayerLayout.setVisibility(View.VISIBLE);

                break;
        }

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }


    public void appearPay(View v) {
        popupWindow = new PopupWindow(this);
        View view = LayoutInflater.from(this).inflate(R.layout.item_popupwindow_pay, null);
        popupWindow.setContentView(view);
        popupWindow.setBackgroundDrawable(null);
        popupWindow.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
        //使Popwindow附加动画
        popupWindow.setAnimationStyle(R.style.PayActivityAnimation);
        popupWindow.update();
        //实例化popwindow内的组件
        initPopwindow(view);
        //给popwindow 添加信息.
        initDataPopwindow();
    }

    private void initDataPopwindow() {

    }


    private void initPopwindow(View view) {
        aliPayBtn = (Button) view.findViewById(R.id.ali_pay_btn);
        aliPayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 完整的符合支付宝参数规范的订单信息
                 */
                final String payInfo = str;
                Runnable payRunnable = new Runnable() {

                    @Override
                    public void run() {
                        // 构造PayTask 对象
                        PayTask alipay = new PayTask(BuyDetailsActivity.this);
                        // 调用支付接口，获取支付结果
                        String result = alipay.pay(payInfo, true);

                        Message msg = new Message();
                        msg.what = SDK_PAY_FLAG;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    }
                };

                // 必须异步调用
                Thread payThread = new Thread(payRunnable);
                payThread.start();


            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击popwindow界面退出popwindow
                popupWindow.dismiss();
                maskLayerLayout.setVisibility(View.INVISIBLE);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        addressGetScuccess();

    }
    private void goodsInfoGetSuccess(){
        netHelper.getJsonData(GOODS_INFO, new NetListener() {
            @Override
            public void getSuccess(Object object) {
                Gson gson = new Gson();
                GoodsContentBean data = gson.fromJson(object.toString(), GoodsContentBean.class);
                titleTv.setText(data.getData().getGoods_name());
                detailsTv.setText(data.getData().getBrand());
                moneryTv.setText(data.getData().getDiscount_price());
                Picasso.with(BuyDetailsActivity.this).load(data.getData().getGoods_pic()).resize(250, 250).into(goodsIv);

            }

            @Override
            public void getFailed(int s) {

            }
        }, goodsInfoMap);
    }
    private void orderGetSiccess() {
        netHelper.getJsonData(ORDER_SUB, new NetListener() {
            @Override
            public void getSuccess(Object object) {
                Gson gson = new Gson();
                AddressBean addressBean = gson.fromJson(object.toString(), AddressBean.class);
                goodsName = addressBean.getData().getGoods().getGoods_name();
                orderId = addressBean.getData().getOrder_id();
                addressId = addressBean.getData().getAddress().getAddr_id();
            }

            @Override
            public void getFailed(int s) {

            }
        }, serverMap);
    }
    private void addressGetScuccess() {
        netHelper.getJsonData(ADDRESS_LIST, new NetListener() {
            @Override
            public void getSuccess(Object object) {
                try {

                    JSONObject addressData = new JSONObject(object.toString());
                    JSONArray addressArray = addressData.getJSONObject("data").getJSONArray("list");

                    for (int i = 0; i < addressArray.length(); i++) {
                        if (addressArray.getJSONObject(i).getString("if_moren").equals("1")) {
                            contactPersonTv.setText(getString(R.string.buy_details_activity_people) + addressArray.getJSONObject(i).getString("username"));
                            telTv.setText(getString(R.string.buy_details_activity_telephone) + addressArray.getJSONObject(i).getString("cellphone"));
                            addressDataTv.setText(getString(R.string.buy_details_activity_address) + addressArray.getJSONObject(i).getString("addr_info"));

                        }


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void getFailed(int s) {

            }
        }, listMap);
    }
    private void paySubGetSuccess(){

        netHelper.getJsonData(ALI_PAY_SUB, new NetListener() {

            @Override
            public void getSuccess(Object object) {
                Gson gson = new Gson();
                PayBean payBean = gson.fromJson(object.toString(), PayBean.class);
                str = payBean.getData().getStr();



            }

            @Override
            public void getFailed(int s) {

            }
        }, payMap);
    }

}
