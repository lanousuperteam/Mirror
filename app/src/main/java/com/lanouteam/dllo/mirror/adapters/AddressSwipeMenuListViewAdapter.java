package com.lanouteam.dllo.mirror.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lanouteam.dllo.mirror.R;
import com.lanouteam.dllo.mirror.bean.AddressSwipeMenuBean;

import java.util.List;

/**
 * Created by dllo on 16/4/8.
 */
public class AddressSwipeMenuListViewAdapter extends BaseAdapter {
    private List<AddressSwipeMenuBean> data;
    private Context context;

    public AddressSwipeMenuListViewAdapter(List<AddressSwipeMenuBean> data,Context context) {
        this.data = data;
        this.context=context;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        SwipeMenuListViewViewHolder swipeMenuListViewViewHolder =null;
        if (convertView == null) {
            convertView= LayoutInflater.from(context).inflate(R.layout.item_swipemenulistview_address,parent,false);
            swipeMenuListViewViewHolder=new SwipeMenuListViewViewHolder(convertView);
            convertView.setTag(swipeMenuListViewViewHolder);
        }else{
            swipeMenuListViewViewHolder= (SwipeMenuListViewViewHolder) convertView.getTag();
        }
        swipeMenuListViewViewHolder.nameTv.setText("收件人:"+data.get(position).getName());
        swipeMenuListViewViewHolder.addressTv.setText("地址:"+data.get(position).getAddress());
        swipeMenuListViewViewHolder.telTv.setText(data.get(position).getTel());
        return convertView;
    }


    class SwipeMenuListViewViewHolder {
        TextView nameTv, addressTv, telTv;

        public SwipeMenuListViewViewHolder(View v) {
            nameTv = (TextView) v.findViewById(R.id.item_all_address_name);
            addressTv = (TextView) v.findViewById(R.id.item_all_address_detailed_address);
            telTv = (TextView) v.findViewById(R.id.item_all_address_tel);
        }
    }
}
