package com.lanouteam.dllo.mirror.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lanouteam.dllo.mirror.R;
import com.lanouteam.dllo.mirror.base.BaseFragment;

/**
 * Created by dllo on 16/4/13.
 */
public class ShareContentFragment extends BaseFragment {
    private TextView tagTv, titleTv, contentTv;// 黑框里的textView
    private String tagText, titleText, contentText;// 黑框里的文字


    @Override
    protected void initView(View view) {
        tagTv = bindView(R.id.sharecontentfragment_tag_tv);
        titleTv = bindView(R.id.sharecontentfragment_title_tv);
        contentTv = bindView(R.id.sharecontentfragment_content_tv);

        // 获取到文字
        Bundle bundle = getArguments();
        tagText = bundle.getString("tag");
        titleText = bundle.getString("title");
        contentText = bundle.getString("content");

        // 设置在textView里
        tagTv.setText(tagText);
        titleTv.setText(titleText);
        contentTv.setText(contentText);
    }

    @Override
    public int initLayout() {
        return R.layout.fragment_share_content;
    }
}
