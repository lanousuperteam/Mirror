package com.lanouteam.dllo.mirror.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanouteam.dllo.mirror.R;
import com.lanouteam.dllo.mirror.bean.MenuListBean;

/**
 * Created by dllo on 16/3/31.
 */
public class MenuListViewAdapter extends BaseAdapter {
    private MenuListBean datas; // 适配器需要的数据
    private int line;

    // 构造方法 传进数据
    public MenuListViewAdapter(MenuListBean datas) {
        this.datas = datas;

    }

    // 传入当前页码数
    public void setLine(int position){
        line = position;
        notifyDataSetChanged();
    }

    // 得到数据集合的数量
    @Override
    public int getCount() {
        return datas != null && datas.getData().getList().size() > 0 ? datas.getData().getList().size() : 0;
    }

    // 得到数据集合里的每一个数据
    @Override
    public Object getItem(int position) {
        return datas != null && datas.getData().getList().size() > 0 ? datas.getData().getList().get(position) : null;
    }

    // 得到数据集合的位置
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 绑定子布局 绑定子布局里面的组件
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        PopwindowHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listview_menufragment, null);
            holder = new PopwindowHolder();
            holder.titleTv = (TextView) convertView.findViewById(R.id.item_list_popupwindow_title);
            holder.lineIv = (ImageView) convertView.findViewById(R.id.item_list_popupwindow_line);
            convertView.setTag(holder);
        } else {
            holder = (PopwindowHolder) convertView.getTag();
        }
        if (datas != null) {
            // 向textview里传入数据
            holder.titleTv.setText(datas.getData().getList().get(position).getTitle());
            // 设置菜单的选中状态
            if (line == position) {
                holder.lineIv.setVisibility(View.VISIBLE);
                holder.titleTv.setAlpha(1);
            } else {
                holder.lineIv.setVisibility(View.INVISIBLE);
                holder.titleTv.setAlpha(0.25f);
            }
        }

        return convertView;
    }

    // 缓存类
    public class PopwindowHolder {
        private TextView titleTv;
        private ImageView lineIv;

    }
}
