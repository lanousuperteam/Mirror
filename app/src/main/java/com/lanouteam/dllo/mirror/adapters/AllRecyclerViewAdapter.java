package com.lanouteam.dllo.mirror.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by dllo on 16/4/6.
 */
public class AllRecyclerViewAdapter extends RecyclerView.Adapter{
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class GoodsViewHolder extends RecyclerView.ViewHolder{

        public GoodsViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class ShareViewHolder extends RecyclerView.ViewHolder{

        public ShareViewHolder(View itemView) {
            super(itemView);
        }
    }
}
