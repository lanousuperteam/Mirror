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
import com.lanouteam.dllo.mirror.utils.L;

/**
 * Created by dllo on 16/3/30.
 */
public class GoodsRecyclerViewAdapter extends RecyclerView.Adapter<GoodsRecyclerViewAdapter.GoodsViewHolder> implements View.OnClickListener {
    private GoodsFragmentBean datas;// 适配器需要的数据
    private OnRecyclerviewItemClickListener mOnItemClickListener = null;//声明一个接口的变量

    // 向适配器里传数据
    public GoodsRecyclerViewAdapter(GoodsFragmentBean datas) {
        this.datas = datas;
    }

    // 绑定子布局
    @Override
    public GoodsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_goodsfragment, parent, false);
        GoodsViewHolder goodsVh=new GoodsViewHolder(view);
        view.setOnClickListener(this);
        return goodsVh;
    }

    // 把数据传到组件里 在组件上显示
    @Override
    public void onBindViewHolder(GoodsViewHolder holder, int position) {
        if (datas != null && datas.getData().getList().size() > 0) {
            holder.addressTv.setText(datas.getData().getList().get(position).getProduct_area());
            holder.modelTv.setText(datas.getData().getList().get(position).getGoods_name());
            holder.priceTv.setText(datas.getData().getList().get(position).getGoods_price());
            holder.nameTv.setText(datas.getData().getList().get(position).getBrand());
            holder.goodsIv.setImageURI(Uri.parse(datas.getData().getList().get(position).getGoods_img()));
            holder.itemView.setTag(datas.getData().getList().get(position).getGoods_id());
        }
    }

    // 得到数据集合的数量
    @Override
    public int getItemCount() {
        return datas.getData().getList().size();
    }

    @Override
    public void onClick(View v) {
        if(mOnItemClickListener!=null){
            mOnItemClickListener.onItemClick(v, (String) v.getTag());
            L.i("tag",(String) v.getTag()+"second");
        }
    }


    // 缓存类
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
    //最后暴露给外面的调用者，定义一个设置Listener的方法（）：
    public void setOnItemClickListener(OnRecyclerviewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

}
