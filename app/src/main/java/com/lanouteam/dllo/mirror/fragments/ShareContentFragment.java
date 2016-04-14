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
    private TextView tagTv, titleTv, contentTv;
    private String tagText, titleText, contentText;


    @Override
    protected void initView(View view) {
        tagTv = bindView(R.id.sharecontentfragment_tag_tv);
        titleTv = bindView(R.id.sharecontentfragment_title_tv);
        contentTv = bindView(R.id.sharecontentfragment_content_tv);

        Bundle bundle = getArguments();
        tagText = bundle.getString("tag");
        titleText = bundle.getString("title");
        contentText = bundle.getString("content");

        tagTv.setText(tagText);
        titleTv.setText(titleText);
        contentTv.setText(contentText);
    }

    @Override
    public int initLayout() {
        return R.layout.fragment_share_content;
    }
}
