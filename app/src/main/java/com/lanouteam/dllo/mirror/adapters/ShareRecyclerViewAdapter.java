package com.lanouteam.dllo.mirror.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lanouteam.dllo.mirror.R;

import java.util.ArrayList;

/**
 * Created by dllo on 16/4/6.
 */
public class ShareRecyclerViewAdapter extends RecyclerView.Adapter<ShareRecyclerViewAdapter.ShareViewHolder>{
    private ArrayList<String> datas;// 适配器需要的数据

    // 向适配器里传入数据集合
    public ShareRecyclerViewAdapter(ArrayList<String> datas) {
        this.datas = datas;
    }

    // 绑定子布局
    @Override
    public ShareViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_sharefragment,null);
        return new ShareViewHolder(view);
    }

    // 向子布局里的组件传入数据
    @Override
    public void onBindViewHolder(ShareViewHolder holder, int position) {
        if (datas != null && datas.size() > 0) {
            holder.textView.setText(datas.get(position));
        }
    }

    // 得到数据集合的数量
    @Override
    public int getItemCount() {
        return datas.size();
    }

    // 缓存类
    public class ShareViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;

        public ShareViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.sharefragment_item_title);
        }
    }
}
