package com.lanouteam.dllo.mirror.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.lanouteam.dllo.mirror.R;
import com.lanouteam.dllo.mirror.adapters.AddressSwipeMenuListViewAdapter;
import com.lanouteam.dllo.mirror.base.BaseActivity;
import com.lanouteam.dllo.mirror.bean.AddressSwipeMenuBean;
import com.lanouteam.dllo.mirror.utils.DensityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dllo on 16/4/8.
 */
public class AddressActivity extends BaseActivity implements View.OnClickListener {
    private List<AddressSwipeMenuBean> addressData;
    private AddressSwipeMenuListViewAdapter addressSwipeMenuListViewAdapter;
    private SwipeMenuListView addressSwipeMenuListView;
    private TextView addAddressTv;

    @Override
    protected int getLayout() {
        return R.layout.activity_address;
    }

    @Override
    protected void initData() {
        addAddressTv.setOnClickListener(this);
        addressData=new ArrayList<>();
        addressData.add(new AddressSwipeMenuBean("dada","dada","1525252555"));
        addressSwipeMenuListViewAdapter =new AddressSwipeMenuListViewAdapter(addressData,this);
        addressSwipeMenuListView.setAdapter(addressSwipeMenuListViewAdapter);

        //第一步:创建一个MenuCreator
        SwipeMenuCreator creator =new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xCE,
                        0x43, 0x55)));
                // set item width
                deleteItem.setWidth(DensityUtils.dp2px(AddressActivity.this, 65));
                // set a icon
//                deleteItem.setIcon(R.mipmap.ic_launcher);
                deleteItem.setTitleSize(13);
                deleteItem.setTitle("删除");
                deleteItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        addressSwipeMenuListView.setMenuCreator(creator);


        //第二步:子布局菜单的点击事件
        addressSwipeMenuListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        //删除按钮
                        addressData.remove(position);
                        addressSwipeMenuListViewAdapter.notifyDataSetChanged();
                        Toast.makeText(AddressActivity.this, "删除地址", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });

    }

    @Override
    protected void initView() {
        addressSwipeMenuListView = bindView(R.id.activity_address_listview);
        addAddressTv=bindView(R.id.activity_address_add_tv);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.activity_address_add_tv:
                //TODO 跳转新界面

                break;
        }
    }
}
