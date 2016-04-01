package com.lanouteam.dllo.mirror;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by dllo on 16/3/31.
 * 商品二级页面滑动的adapter
 */
public class GoodsContentApapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private ArrayList<String>datas;
    private int tvScrollValue;
    final int TYPE_HEAD =0;
    final int TYPE_TRANSPARENT =1;
    final int TYPE_GOODS_TITLE =2;
    final int TYPE_GOODS_DETAILS =3;
    public GoodsContentApapter(ArrayList<String> datas) {
        this.datas = datas;


    }
    /**
     * 该方法为接收activity传来的监听recycleview滑动距离的value
     *
     * */
    public void setScrollValue(int scrollValue){
        this.tvScrollValue =scrollValue;
        //刷新UI
        /**
         * 必须加上这句话,持续的刷新从Actvity 接收的滑动值.
         * */
        notifyDataSetChanged();
    }

    /**
     * 决定元素的布局使用哪种类型
     *
     * @param position 数据源的下标
     * @return 一个int型标志，传递给onCreateViewHolder的第二个参数 */

    @Override
    public int getItemViewType(int position) {
       // int p=position;
        if(position==0)
            return TYPE_HEAD;

        else if (position==1)
            return TYPE_TRANSPARENT;
        else if(position==2)
            return TYPE_GOODS_TITLE;
        else
            return TYPE_GOODS_DETAILS;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /** * 渲染具体的ViewHolder
         *
         * @param parent   ViewHolder的容器
         * @param viewType 一个标志，我们根据该标志可以实现渲染不同类型的ViewHolder
         * @return
         */
        View view=null;
        if(viewType==TYPE_HEAD){
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycleview_goodsfragment_content_head,parent,false);
            return new HeadViewHolder(view);
        }else if(viewType==TYPE_TRANSPARENT){
            view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycleview_goodsfragments_content_transparent,parent,false);
            return new TransparentViewHolder(view);
        }else if(viewType==TYPE_GOODS_TITLE){
            view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycleview_goodsfragments_content_goods_title,parent,false);
            return new GoodsTitleViewHolder(view);
        }else{
            view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycleview_goodsfragment_content_goods_details,parent,false);
            return new GoodsDetailsViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    /**
   * 新建缓存类
   *
   * */
    //商品详情二级页面头布局(半透明)缓存类
    public class HeadViewHolder extends RecyclerView.ViewHolder {
      //需要网络解析的数据
      private TextView headGoodsNameTv,headBrandTv,headInfoDesTv,headGoodsPriceTv;

      public HeadViewHolder(View itemView) {
          super(itemView);
          headGoodsNameTv= (TextView) itemView.findViewById(R.id.item_goodsfragment_content_head_goods_name);
          headBrandTv= (TextView) itemView.findViewById(R.id.item_goodsfragment_content_head_brand);
          headInfoDesTv= (TextView) itemView.findViewById(R.id.item_goodsfragment_content_head_info_des);
          headGoodsPriceTv= (TextView) itemView.findViewById(R.id.item_goodsfragment_content_head_goods_price);
      }
  }
    //商品二级页面第二布局(全透明)加载布局时别忘喽!
    public class TransparentViewHolder extends RecyclerView.ViewHolder {
        public TransparentViewHolder(View itemView) {
            super(itemView);
        }
    }

    //商品二级页面商品详情带标题布局缓存类
    public class GoodsTitleViewHolder extends RecyclerView.ViewHolder {
        //需要网络解析的数据
        private TextView goodsTitleBrandTv,goodsTitleCountryTv,goodsTitleLocationTv,goodsTitleEnglishTv,goodsTitleIntroContent;
        private ImageView goodsTitleImg;
        public GoodsTitleViewHolder(View itemView) {
            super(itemView);
            goodsTitleBrandTv= (TextView) itemView.findViewById(R.id.item_goodsfragment_content_goods_title_brand);
            goodsTitleCountryTv= (TextView) itemView.findViewById(R.id.item_goodsfragment_content_goods_title_country);
            goodsTitleLocationTv= (TextView) itemView.findViewById(R.id.item_goodsfragment_content_goods_title_location);
            goodsTitleEnglishTv= (TextView) itemView.findViewById(R.id.item_goodsfragment_content_goods_title_english);
            goodsTitleIntroContent= (TextView) itemView.findViewById(R.id.item_goodsfragment_content_goods_title_introContent);
            goodsTitleImg= (ImageView) itemView.findViewById(R.id.item_goodsfragment_content_goods_title_img);
        }
    }
    //商品二级页面商品详情不带标题布局缓存类
    public class GoodsDetailsViewHolder extends RecyclerView.ViewHolder {
        //需要网络解析的数据
        private TextView  goodsDetailsDetailsName,goodsDetailsIntroContent;
        private ImageView goodsDetailsImg;
        public GoodsDetailsViewHolder(View itemView) {
            super(itemView);
            goodsDetailsDetailsName= (TextView) itemView.findViewById(R.id.item_goodsfragment_content_goods_details_name);
            goodsDetailsIntroContent= (TextView) itemView.findViewById(R.id.item_goodsfragment_content_goods_details_introContent);
            goodsDetailsImg= (ImageView) itemView.findViewById(R.id.item_goodsfragment_content_goods_details_img);

        }
    }


}

