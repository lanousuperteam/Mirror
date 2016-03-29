package com.lanouteam.dllo.mirror.activity;

import android.view.View;
import android.widget.ImageView;

import com.lanouteam.dllo.mirror.R;
import com.lanouteam.dllo.mirror.base.BaseActivity;

/**
 * Created by dllo on 16/3/29.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private ImageView deleteIv, weChatIv, weiboIv;

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }


    /**
     * @param
     */
    @Override
    protected void initData() {
        deleteIv.setOnClickListener(this);
    }

    @Override
    protected void initView() {
        deleteIv = bindView(R.id.login_activity_delete);
        weiboIv = bindView(R.id.login_activity_weibo_iv);
        weChatIv = bindView(R.id.login_activity_weChat_iv);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_activity_delete:
                finish();
                break;
            case R.id.login_activity_weibo_iv:


                break;
            case R.id.login_activity_weChat_iv:

                break;

        }
    }
}
