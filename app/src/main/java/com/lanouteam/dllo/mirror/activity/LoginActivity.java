package com.lanouteam.dllo.mirror.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lanouteam.dllo.mirror.R;
import com.lanouteam.dllo.mirror.base.BaseActivity;
import com.lanouteam.dllo.mirror.bean.RequestParams;
import com.lanouteam.dllo.mirror.bean.RequestUrls;
import com.lanouteam.dllo.mirror.db.Admin;
import com.lanouteam.dllo.mirror.db.AdminHelper;
import com.lanouteam.dllo.mirror.net.NetHelper;
import com.lanouteam.dllo.mirror.net.NetListener;
import com.lanouteam.dllo.mirror.utils.L;
import com.lanouteam.dllo.mirror.utils.T;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Created by dllo on 16/3/29.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener, TextWatcher, RequestParams, RequestUrls, NetListener {
    private ImageView deleteIv, weChatIv, weiboIv;
    private EditText phoneNumberEt, passwordEt;
    private Button loginBtn;
    private TextView forgetPasswordTv;
    private String phoneNumber;

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }


    @Override
    protected void initData() {
        deleteIv.setOnClickListener(this);
        weiboIv.setOnClickListener(this);
        weChatIv.setOnClickListener(this);
        forgetPasswordTv.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        findViewById(R.id.login_activity_create_account_btn).setOnClickListener(this);
        phoneNumberEt.addTextChangedListener(this);
        passwordEt.addTextChangedListener(this);
    }

    @Override
    protected void initView() {
        deleteIv = bindView(R.id.login_activity_delete);
        weiboIv = bindView(R.id.login_activity_weibo_iv);
        weChatIv = bindView(R.id.login_activity_weChat_iv);
        phoneNumberEt = bindView(R.id.login_activity_phone_number_et);
        passwordEt = bindView(R.id.login_activity_password_et);
        loginBtn = bindView(R.id.login_activity_login_btn);
        forgetPasswordTv = bindView(R.id.login_activity_forget_password_tv);
    }

    @Override
    public void onClick(View v) {
        phoneNumber = phoneNumberEt.getText().toString();
        String passwords = passwordEt.getText().toString();
        switch (v.getId()) {
            case R.id.login_activity_delete:
                finish();
                break;
            case R.id.login_activity_weibo_iv:

                break;
            case R.id.login_activity_weChat_iv:

                break;
            case R.id.login_activity_create_account_btn:
                Intent jumpCreateAccountActivityIntent = new Intent(LoginActivity.this, CreateAccountActivity.class);
                startActivity(jumpCreateAccountActivityIntent);
                break;
            case R.id.login_activity_forget_password_tv:
                T.showShort(this, "请您到官网重置密码");
                break;
            case R.id.login_activity_login_btn:
                //点击登录按钮
                if (phoneNumber.length() != 11) {
                    Toast.makeText(LoginActivity.this, "请检查您的手机号, 并重新输入", Toast.LENGTH_SHORT).show();
                    break;
                }
                String firstNumber = phoneNumber.substring(0, 1);
                if (!firstNumber.equals("1")) {
                    Toast.makeText(LoginActivity.this, "请检查您的手机号, 并重新输入", Toast.LENGTH_SHORT).show();
                    break;
                }
                NetHelper loginNetHelper = new NetHelper(LoginActivity.this);
                HashMap<String, String> loginData = new HashMap<>();
                loginData.put(PHONE_NUMBER, phoneNumber);
                loginData.put(PASSWORD, passwords);
                loginNetHelper.getJsonData(LOGIN, this, loginData);
                break;
        }
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if ((!phoneNumberEt.getText().toString().equals("")) && (!passwordEt.getText().toString().equals(""))) {
            loginBtn.setBackgroundResource(R.mipmap.btn_register_normal);
            loginBtn.setEnabled(true);
        } else {
            loginBtn.setBackgroundResource(R.mipmap.btn_cannot_use);
            loginBtn.setEnabled(false);
        }
    }

    @Override
    public void getSuccess(Object object) {
        try {
            JSONObject accountData = new JSONObject(object.toString());
            String result = accountData.getString("result");
            String msg = accountData.getString("msg");
            if (result.equals("1")) {
                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                String token = accountData.getJSONObject("data").getString("token");
                L.d(token);
                String uid = accountData.getJSONObject("data").getString("uid");
                //利用数据库储存数据
                AdminHelper adminHelper = new AdminHelper(LoginActivity.this, "Admin");

                List<Admin> names = adminHelper.queryName(phoneNumber);
                //判断该用户名是否存在数据库,如果存在就无需添加到数据库中.
                if (names == null) {
                    adminHelper.insert(new Admin(phoneNumber, token, uid));
                }
            } else {
                Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void getFailed(int s) {

    }
}
