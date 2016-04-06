package com.lanouteam.dllo.mirror.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.lanouteam.dllo.mirror.R;
import com.lanouteam.dllo.mirror.base.BaseActivity;

/**
 * Created by dllo on 16/3/29.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener, TextWatcher {
    private ImageView deleteIv, weChatIv, weiboIv;
    private EditText phoneNumberEt,passwordEt;
    private Button loginBtn;

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }


    @Override
    protected void initData() {
        deleteIv.setOnClickListener(this);
        weiboIv.setOnClickListener(this);
        weChatIv.setOnClickListener(this);
        findViewById(R.id.login_activity_create_account_btn).setOnClickListener(this);
        phoneNumberEt.addTextChangedListener(this);
        passwordEt.addTextChangedListener(this);
    }

    @Override
    protected void initView() {
        deleteIv = bindView(R.id.login_activity_delete);
        weiboIv = bindView(R.id.login_activity_weibo_iv);
        weChatIv = bindView(R.id.login_activity_weChat_iv);
        phoneNumberEt=bindView(R.id.login_activity_phone_number_et);
        passwordEt=bindView(R.id.login_activity_password_et);
        loginBtn=bindView(R.id.login_activity_login_btn);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_activity_delete:
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.login_activity_weibo_iv:

                break;
            case R.id.login_activity_weChat_iv:

                break;
            case R.id.login_activity_create_account_btn:
                Intent jumpCreateAccountActivityIntent =new Intent(LoginActivity.this,CreateAccountActivity.class);
                startActivity(jumpCreateAccountActivityIntent);
                break;

        }
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if ((!phoneNumberEt.getText().toString().equals(""))&&(!passwordEt.getText().toString().equals(""))){
            loginBtn.setBackgroundResource(R.mipmap.btn_register_normal);
            loginBtn.setEnabled(true);
        }else{
            loginBtn.setBackgroundResource(R.mipmap.btn_cannot_use);
            loginBtn.setEnabled(false);
        }
    }
}
