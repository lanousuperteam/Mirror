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
    private ArrayList<String> datas;

    public ShareRecyclerViewAdapter(ArrayList<String> datas) {
        this.datas = datas;
    }

    @Override
    public ShareViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_sharefragment,null);
        return new ShareViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShareViewHolder holder, int position) {
        if (datas != null && datas.size() > 0) {
            holder.textView.setText(datas.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ShareViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;

        public ShareViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.sharefragment_item_title);
        }
    }
}
