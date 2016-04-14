package com.lanouteam.dllo.mirror.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
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
    private Button submitOrderBtn, aliPayBtn;
    private PopupWindow popupWindow;
    private FrameLayout maskLayerLayout;

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
                        Toast.makeText(BuyDetailsActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(BuyDetailsActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(BuyDetailsActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
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
        submitOrderBtn = bindView(R.id.activity_indent_details_page_submit_order_btn);
        maskLayerLayout = bindView(R.id.mask_layer_layout);
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
                appearPay(v);
                maskLayerLayout.setVisibility(View.VISIBLE);

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
                //TODO 等思博穿数据组合一下,就可以
        //		final String payInfo = orderInfo + "&sign=\"" + c + "\"&" + getSignType();
                final String payInfo =
                        "service=\"mobile.securitypay.pay\"&partner=\"2088021758262531\"&_input_charset=\"utf-8\"&notify_url=\"http%3A%2F%2Fapi.mirroreye.cn%2Findex.php%2Fali_notify\"&out_trade_no=\"1460594413xsM\"&subject=\"GENTLE MONSTER\"&payment_type=\"1\"&seller_id=\"2088021758262531\"&total_fee=\"0.01\"&body=\"GENTLE MONSTER\"&it_b_pay =\"30m\"&sign=\"jgGlJg6LSEnwxYjTTNmZ5bDOtEuFGjH6g1JKBYB7UGj0ENIxbQd5YpKp6Jt6JBcxSNSYw%2FAAkZbGI%2B6RYPEBYmClpPUjSSSMLO9Isp8%2BLvzGoqTT2IE%2Bw9%2BG5BXnoY%2BCOtgXEZY6OXxL183HZ37KKI%2FbPn0FyVDxLK%2Bh%2F4ycDXg%3D\"&sign_type=\"RSA\"";
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
}
