package com.lanouteam.dllo.mirror.activity;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.lanouteam.dllo.mirror.R;
import com.lanouteam.dllo.mirror.base.BaseActivity;
import com.lanouteam.dllo.mirror.bean.RequestParams;
import com.lanouteam.dllo.mirror.bean.RequestUrls;
import com.lanouteam.dllo.mirror.db.AddressDataHelperSingle;
import com.lanouteam.dllo.mirror.db.Admin;
import com.lanouteam.dllo.mirror.net.NetHelper;
import com.lanouteam.dllo.mirror.net.NetListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by dllo on 16/3/30.
 */
public class CreateAccountActivity extends BaseActivity implements View.OnClickListener, RequestUrls, RequestParams, NetListener {
    private EditText phoneNumberEt, codeEt, passwordEt;
    private Button sendCodeBtn,createAccountBtn;
    private ImageView returnIv;
    //倒计时工具类
    private CountDownTimer timer;
    private NetHelper createAccountNetHelper;

    @Override
    protected int getLayout() {
        return R.layout.activity_create_account;
    }

    @Override
    protected void initData() {
        //定义NetHelper 进行网络请求
        createAccountNetHelper = new NetHelper(this);
        //给按钮设置监听
        createAccountBtn.setOnClickListener(this);
        sendCodeBtn.setOnClickListener(this);
        returnIv.setOnClickListener(this);
        //创建倒计时控件
        timer = new CountDownTimer(60000, 1000) {
            /**
             * @param millisUntilFinished  当前的剩余时间
             * 每经过一次间隔时间,就会调用一次此方法
             * */
            @Override
            public void onTick(long millisUntilFinished) {
                int time = (int) (millisUntilFinished / 1000);
                sendCodeBtn.setText(time + R.string.how_much_time_resend);
            }

            /**
             * 倒计时结束时调用此方法.
             * */
            @Override
            public void onFinish() {
                sendCodeBtn.setEnabled(true);
                sendCodeBtn.setText(R.string.create_acount_activity_send_code);
            }
        };

    }

    @Override
    protected void initView() {
        phoneNumberEt = bindView(R.id.create_account_activity_phone_number_et);
        codeEt = bindView(R.id.create_account_activity_code_et);
        sendCodeBtn = bindView(R.id.create_account_activity_send_code_btn);
        passwordEt = bindView(R.id.create_account_activity_password_et);
        createAccountBtn=bindView(R.id.create_account_activity_create_account_btn);
        returnIv=bindView(R.id.create_account_activity_return_iv);
    }

    @Override
    public void onClick(View v) {
        final String phoneNumber = phoneNumberEt.getText().toString();
        String codeNumber = codeEt.getText().toString();
        String passwords = passwordEt.getText().toString();
        switch (v.getId()) {
            //创建账号
            case R.id.create_account_activity_create_account_btn:
                if (phoneNumber.equals("")) {
                    Toast.makeText(CreateAccountActivity.this, R.string.phone_null, Toast.LENGTH_SHORT).show();
                    break;
                }
                if (codeNumber.equals("")) {
                    Toast.makeText(CreateAccountActivity.this, R.string.code_null, Toast.LENGTH_SHORT).show();
                    break;
                }
                if (passwords.equals("")) {
                    Toast.makeText(CreateAccountActivity.this, R.string.password_null, Toast.LENGTH_SHORT).show();
                    break;
                }
                HashMap<String, String> createAccountMap = new HashMap<>();
                createAccountMap.put(PHONE_NUMBER, phoneNumber);
                createAccountMap.put(NUMBER, codeNumber);
                createAccountMap.put(PASSWORD, passwords);
                createAccountNetHelper.getJsonData(USER_REGISTER, new NetListener() {
                    @Override
                    public void getSuccess(Object object) {
                        try {
                            JSONObject accountData = new JSONObject(object.toString());
                            String result = accountData.getString("result");
                            String msg = accountData.getString("msg");
                            if (result.equals("1")) {
                                Toast.makeText(CreateAccountActivity.this, R.string.sign_up_success, Toast.LENGTH_SHORT).show();
                                String token = accountData.getJSONObject("data").getString("token");
                                String uid = accountData.getJSONObject("data").getString("uid");
                                //利用数据库储存数据
                                AddressDataHelperSingle addressDataHelperSingle = AddressDataHelperSingle.getInstance(CreateAccountActivity.this);
                                addressDataHelperSingle.insert(new Admin(phoneNumber, token, uid));
                            } else {
                                Toast.makeText(CreateAccountActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void getFailed(int s) {

                    }
                }, createAccountMap);
                break;
            //发送验证码
            case R.id.create_account_activity_send_code_btn:
                //点击发送验证码,然后出现60s 之后可以再次发送
                //限制手机号码为11位
                if (phoneNumber.length() != 11) {
                    Toast.makeText(CreateAccountActivity.this, R.string.check_phone, Toast.LENGTH_SHORT).show();
                    break;
                }
                String firstNumber = phoneNumber.substring(0, 1);
                if (!firstNumber.equals("1")) {
                    Toast.makeText(CreateAccountActivity.this, R.string.check_phone, Toast.LENGTH_SHORT).show();
                    break;
                }
                //如果是正确手机号就开始倒计时
                timer.start();
                HashMap<String, String> sendCodeMap = new HashMap<>();
                sendCodeMap.put(PHONE_NUMBER, phoneNumber);
                createAccountNetHelper.getJsonData(SEND_CODE, this, sendCodeMap);
                sendCodeBtn.setEnabled(false);
                break;
            case R.id.create_account_activity_return_iv:
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
        }
    }

    @Override
    public void getSuccess(Object object) {
        try {
            JSONObject obj = new JSONObject(object.toString());
            String result = obj.getString("result");
            if (result.equals("1")) {
                Toast.makeText(CreateAccountActivity.this, R.string.send_code_success, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getFailed(int s) {

    }
}
