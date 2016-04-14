package com.lanouteam.dllo.mirror.fragments;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.lanouteam.dllo.mirror.R;
import com.lanouteam.dllo.mirror.adapters.ShareRecyclerViewAdapter;
import com.lanouteam.dllo.mirror.base.BaseFragment;

import java.util.ArrayList;


/**
 * Created by dllo on 16/4/6.
 */
public class ShareFragment extends BaseFragment implements View.OnClickListener {
    private LinearLayout linearLayout;// 标题的linearlayout
    private RecyclerView recyclerView;// 专题分享的recyclerView
    private ArrayList<String> datas;
    private ShareRecyclerViewAdapter adapter; // 专题分享的recyclerview的适配器

    @Override
    protected void initView(View view) {
        linearLayout = bindView(R.id.sharefragment_title_linearlayout);
        recyclerView = bindView(R.id.sharefragment_recyclerview);

        linearLayout.setOnClickListener(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        gridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(gridLayoutManager);

        datas = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            // TODO: 16/4/14   假数据
            datas.add("冷美人的慵懒随性风");
        }

        adapter = new ShareRecyclerViewAdapter(datas);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public int initLayout() {
        return R.layout.fragment_share;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sharefragment_title_linearlayout:

                break;
        }
    }
}
