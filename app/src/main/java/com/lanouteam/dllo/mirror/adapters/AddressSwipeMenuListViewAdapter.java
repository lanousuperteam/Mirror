package com.lanouteam.dllo.mirror.adapters;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lanouteam.dllo.mirror.R;
import com.lanouteam.dllo.mirror.activity.AddAddressActivity;
import com.lanouteam.dllo.mirror.bean.AddressSwipeMenuBean;

import java.util.List;

/**
 * Created by dllo on 16/4/8.
 */
public class AddressSwipeMenuListViewAdapter extends BaseAdapter {
    private List<AddressSwipeMenuBean> data;
    private Context context;
    private AddressSwipeMenuListViewInterface addressSwipeMenuListViewInterface;
    private Intent intent;

    public AddressSwipeMenuListViewAdapter(List<AddressSwipeMenuBean> data,Context context,Intent intent) {
        this.data = data;
        this.context=context;
        this.intent=intent;
    }


    public void setAddressSwipeMenuListViewInterface(AddressSwipeMenuListViewInterface addressSwipeMenuListViewInterface) {
        this.addressSwipeMenuListViewInterface = addressSwipeMenuListViewInterface;
    }

    @Override
    public int getCount() {
        return data.size() > 0 && data != null ? data.size() : 0;
    }

    public void setData(AddressSwipeMenuBean addressSwipeMenuBean) {
        data.add(addressSwipeMenuBean);
        notifyDataSetChanged();
    }

    @Override
    public Object getItem(int position) {
        return data.size() > 0 && data != null ? data.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        SwipeMenuListViewViewHolder swipeMenuListViewViewHolder =null;
        if (convertView == null) {
            convertView= LayoutInflater.from(context).inflate(R.layout.item_swipemenulistview_address,parent,false);
            swipeMenuListViewViewHolder=new SwipeMenuListViewViewHolder(convertView);
            convertView.setTag(swipeMenuListViewViewHolder);
        }else{
            swipeMenuListViewViewHolder= (SwipeMenuListViewViewHolder) convertView.getTag();
        }
        swipeMenuListViewViewHolder.nameTv.setText(context.getString(R.string.menulistview_addressee)+data.get(position).getName());
        swipeMenuListViewViewHolder.addressTv.setText(context.getString(R.string.menulistview_address)+data.get(position).getAddress());
        swipeMenuListViewViewHolder.telTv.setText(data.get(position).getTel());
        //编辑按钮的点击事件
        swipeMenuListViewViewHolder.editAddressIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //将类序列化直接传递过去.
                intent =new Intent(context, AddAddressActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("address",data.get(position));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        return convertView;
    }


    class SwipeMenuListViewViewHolder {
        TextView nameTv, addressTv, telTv;
        LinearLayout addressLl;
        ImageView editAddressIv;

        public SwipeMenuListViewViewHolder(View v) {
            nameTv = (TextView) v.findViewById(R.id.item_all_address_name);
            addressTv = (TextView) v.findViewById(R.id.item_all_address_detailed_address);
            telTv = (TextView) v.findViewById(R.id.item_all_address_tel);
            addressLl= (LinearLayout) v.findViewById(R.id.item_all_address_ll);
            editAddressIv= (ImageView) v.findViewById(R.id.edit_address_iv);
        }
    }
}
