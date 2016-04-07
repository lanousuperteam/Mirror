package com.lanouteam.dllo.mirror.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lanouteam.dllo.mirror.R;

import java.util.ArrayList;

/**
 * Created by dllo on 16/3/30.
 */
public class GoodsRecyclerViewAdapter extends RecyclerView.Adapter<GoodsRecyclerViewAdapter.GoodsViewHolder>{
    private ArrayList<String> datas;

    public GoodsRecyclerViewAdapter(ArrayList<String> datas) {
        this.datas = datas;
    }

    @Override
    public GoodsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_goodsfragment,parent,false);
        return new GoodsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GoodsViewHolder holder, int position) {
        if (datas != null && datas.size() > 0){
            holder.textView.setText(datas.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class GoodsViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;

        public GoodsViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.goodsfragment_item_address);
        }
    }
}
