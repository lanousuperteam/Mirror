package com.lanouteam.dllo.mirror.fragments;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.lanouteam.dllo.mirror.R;
import com.lanouteam.dllo.mirror.adapters.ShareRecyclerViewAdapter;
import com.lanouteam.dllo.mirror.base.BaseFragment;
import com.lanouteam.dllo.mirror.utils.Popwindow;

import java.util.ArrayList;


/**
 * Created by dllo on 16/4/6.
 */
public class ShareFragment extends BaseFragment implements View.OnClickListener {
    private LinearLayout linearLayout;
    private RecyclerView recyclerView;
    private Popwindow popwindow;
    private ArrayList<String> datas;
    private ShareRecyclerViewAdapter adapter;

    @Override
    protected void initView(View view) {
        linearLayout = bindView(R.id.sharefragment_title_linearlayout);
        recyclerView = bindView(R.id.sharefragment_recyclerview);

        popwindow = new Popwindow(getContext());
        linearLayout.setOnClickListener(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        gridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(gridLayoutManager);

        datas = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
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
                popwindow.showPopUpWindow(v);
                break;
        }
    }
}
