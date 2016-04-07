package com.lanouteam.dllo.mirror.adapters;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.lanouteam.dllo.mirror.R;
import com.lanouteam.dllo.mirror.base.BaseApplication;
import com.lanouteam.dllo.mirror.bean.GoodsFragmentBean;
import com.lanouteam.dllo.mirror.net.NetHelper;

/**
 * Created by dllo on 16/3/30.
 */
public class GoodsRecyclerViewAdapter extends RecyclerView.Adapter<GoodsRecyclerViewAdapter.GoodsViewHolder> {
    private GoodsFragmentBean datas;
    private ImageLoader imageLoader;
    private NetHelper netHelper;


    public GoodsRecyclerViewAdapter(GoodsFragmentBean datas) {
        this.datas = datas;
        //netHelper = new NetHelper(BaseApplication.mContext);
        //imageLoader = netHelper.getImageLoader();
    }

    @Override
    public GoodsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_goodsfragment, parent, false);
        return new GoodsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GoodsViewHolder holder, int position) {
        if (datas != null && datas.getData().getList().size() > 0) {
            holder.addressTv.setText(datas.getData().getList().get(position).getProduct_area());
            holder.modelTv.setText(datas.getData().getList().get(position).getGoods_name());
            holder.priceTv.setText(datas.getData().getList().get(position).getGoods_price());
            holder.nameTv.setText(datas.getData().getList().get(position).getBrand());
            holder.goodsIv.setImageURI(Uri.parse(datas.getData().getList().get(position).getGoods_img()));

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
            addressTv = (TextView) itemView.findViewById(R.id.goodsfragment_item_address);
            modelTv = (TextView) itemView.findViewById(R.id.goodsfragment_item_model);
            priceTv = (TextView) itemView.findViewById(R.id.goodsfragment_item_price);
            nameTv = (TextView) itemView.findViewById(R.id.goodsfragment_item_name);
            goodsIv = (ImageView) itemView.findViewById(R.id.goodsfragment_item_imageview);
        }
    }
}
