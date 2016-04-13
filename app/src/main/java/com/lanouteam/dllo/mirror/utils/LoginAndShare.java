package com.lanouteam.dllo.mirror.utils;

import com.lanouteam.dllo.mirror.R;
import com.lanouteam.dllo.mirror.base.BaseApplication;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;


/**
 * Created by dllo on 16/4/12.
 */
public class LoginAndShare {
    private String id;
    private String shareUrl = "http://api101.test.mirroreye.cn/index.php/goodweb/info?id=";

    public LoginAndShare(String id) {
        this.id = id;
    }

    public void MyShare() {
        ShareSDK.initSDK(BaseApplication.mContext);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(String.valueOf(R.string.app_name));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(shareUrl + id);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(String.valueOf(R.string.share_title));//推荐的title
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(shareUrl + id);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment(String.valueOf(R.string.share_title));
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(String.valueOf(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(shareUrl+ id);

        // 启动分享GUI
        oks.show(BaseApplication.mContext);
    }

    /**
     * 微信登录:Wechat.NAME
     * 微博登录:SinaWeibo.NAME
     * QQ空间登录:QZone.NAME
     */
    public void MyLogin() {
        ShareSDK.initSDK(BaseApplication.mContext);
        Platform platform2 = ShareSDK.getPlatform(id);
        if (platform2.isAuthValid()) {
            platform2.removeAccount();
        }
        platform2.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                platform.getDb().getUserIcon();
                platform.getDb().getUserName();

            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(Platform platform, int i) {

            }
        });
        platform2.SSOSetting(false);
        platform2.showUser(null);

    }


}
