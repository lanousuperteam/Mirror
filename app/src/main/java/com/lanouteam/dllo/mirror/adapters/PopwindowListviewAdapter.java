package com.lanouteam.dllo.mirror.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanouteam.dllo.mirror.R;

import java.util.ArrayList;

/**
 * Created by dllo on 16/3/31.
 */
public class PopwindowListviewAdapter extends BaseAdapter {
    private ArrayList<String> datas;


    public PopwindowListviewAdapter(ArrayList<String> datas) {
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas != null && datas.size() > 0 ? datas.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return datas != null && datas.size() > 0 ? datas.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        PopwindowHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listview_popwindow,null);
            holder = new PopwindowHolder();
            holder.titleTv = (TextView) convertView.findViewById(R.id.item_list_popupwindow_title);
            holder.lineIv = (ImageView) convertView.findViewById(R.id.item_list_popupwindow_line);

            convertView.setTag(holder);
        } else {
            holder = (PopwindowHolder) convertView.getTag();
        }
        if (datas != null) {
            holder.titleTv.setText(datas.get(position));
//            holder.lineIv.setVisibility();
        }

        return convertView;
    }

    public class PopwindowHolder {
        private TextView titleTv;
        private ImageView lineIv;
    }
}
