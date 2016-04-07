package com.lanouteam.dllo.mirror.adapters;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanouteam.dllo.mirror.R;
import com.lanouteam.dllo.mirror.bean.AllFragmentBean;

/**
 * Created by dllo on 16/4/6.
 */
public class AllRecyclerViewAdapter extends RecyclerView.Adapter {
    private AllFragmentBean datas;
    private String type;

    public AllRecyclerViewAdapter(AllFragmentBean datas) {
        this.datas = datas;

    }

    @Override
    public int getItemViewType(int position) {
        type = datas.getData().getList().get(position).getType();
        if (type.equals("1")) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1) {
            return new GoodsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_allfragment_goods,parent,false));
        } else {
            return new ShareViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_allfragment_share,parent,false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == 1){
            GoodsViewHolder goodsViewHolder = (GoodsViewHolder) holder;
            goodsViewHolder.nameTv.setText(datas.getData().getList().get(position).getData_info().getBrand());
            goodsViewHolder.priceTv.setText(datas.getData().getList().get(position).getData_info().getGoods_price());
            goodsViewHolder.modelTv.setText(datas.getData().getList().get(position).getData_info().getGoods_name());
            goodsViewHolder.addressTv.setText(datas.getData().getList().get(position).getData_info().getProduct_area());
            goodsViewHolder.goodsIv.setImageURI(Uri.parse(datas.getData().getList().get(position).getData_info().getGoods_img()));
        } else {
            // ShareViewHolder shareViewHolder = (ShareViewHolder) holder;
            // shareViewHolder.titleTv
        }
    }

    @Override
    public int getItemCount() {
        return datas.getData().getList().size();
    }

    public class GoodsViewHolder extends RecyclerView.ViewHolder {

        private TextView addressTv, modelTv, priceTv, nameTv;
        private ImageView goodsIv;

        public GoodsViewHolder(View itemView) {
            super(itemView);
            addressTv = (TextView) itemView.findViewById(R.id.allfragment_item_goods_address);
            modelTv = (TextView) itemView.findViewById(R.id.allfragment_item_goods_model);
            priceTv = (TextView) itemView.findViewById(R.id.allfragment_item_goods_price);
            nameTv = (TextView) itemView.findViewById(R.id.allfragment_item_goods_name);
            goodsIv = (ImageView) itemView.findViewById(R.id.allfragment_item_goods_imageview);
        }
    }

    public class ShareViewHolder extends RecyclerView.ViewHolder {

        private TextView titleTv;
        private ImageView shareIv;

        public ShareViewHolder(View itemView) {
            super(itemView);
            titleTv = (TextView) itemView.findViewById(R.id.allfragment_item_share_name);
            shareIv = (ImageView) itemView.findViewById(R.id.allfragment_item_share_imageview);
        }
    }
}
