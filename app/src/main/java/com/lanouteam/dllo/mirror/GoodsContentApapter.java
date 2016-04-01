package com.lanouteam.dllo.mirror;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;

/**
 * Created by dllo on 16/3/31.
 * 商品二级页面滑动的adapter
 */
public class GoodsContentApapter extends RecyclerView.Adapter {
    private ArrayList<String> datas;
    private RecyclerView recyclerView;
    private int layoutScrollValue;
    final int TYPE_HEAD = 0;
    final int TYPE_TRANSPARENT = 1;
    final int TYPE_GOODS_TITLE = 2;
    final int TYPE_GOODS_DETAILS = 3;


    public GoodsContentApapter(ArrayList<String> datas) {
        this.datas = datas;

    }

    /**
     * 该方法为接收activity传来的监听recycleview滑动距离的value
     */
    public void setScrollValue(int scrollValue) {
        this.layoutScrollValue = scrollValue;
        //   Log.i("传递过来的value", layoutScrollValue + "我是传递的值");
        //刷新UI
        /**
         * 必须加上这句话,持续的刷新从Actvity 接收的滑动值.
         * */
        try {
            notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    /**
     * 决定元素的布局使用哪种类型
     *
     * @param
     * @return 一个int型标志，传递给onCreateViewHolder的第二个参数
     */
//
    @Override
    public int getItemViewType(int position) {
        // int p=position;
        if (position == 0)
            return TYPE_HEAD;

        else if (position == 1)
            return TYPE_TRANSPARENT;
        else if (position == 2)

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
        // View view = null;
        if (viewType == TYPE_HEAD) {
            View viewHead = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycleview_goodsfragment_content_head, parent, false);
            return new HeadViewHolder(viewHead);
        } else if (viewType == TYPE_TRANSPARENT) {
            View viewTransparent = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycleview_goodsfragments_content_transparent, parent, false);
            return new TransparentViewHolder(viewTransparent);
        } else if (viewType == TYPE_GOODS_TITLE) {
            View viewGoodsTitle = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycleview_goodsfragments_content_goods_title, parent, false);
            return new GoodsTitleViewHolder(viewGoodsTitle);
        } else {
            View viewGoodsDetails = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycleview_goodsfragment_content_goods_details, parent, false);
            return new GoodsDetailsViewHolder(viewGoodsDetails);
        }


    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        //  之后在此添加数据
        if (holder instanceof HeadViewHolder) {

        } else if (holder instanceof GoodsTitleViewHolder) {//如果holder的实例是GoodsTitleViewHolder的话
            int valueTitle = layoutScrollValue;
            //   Log.e("ValueTitle", layoutScrollValue + "啦啦");
            RelativeLayout.LayoutParams paramsTitle = (RelativeLayout.LayoutParams) ((GoodsTitleViewHolder) holder).goodsTitleRelativeLayout.getLayoutParams();

            paramsTitle.setMargins(0, (int) (valueTitle * 0.1), 0, 0);
            ((GoodsTitleViewHolder) holder).goodsTitleRelativeLayout.setLayoutParams(paramsTitle);


        } else if (holder instanceof GoodsDetailsViewHolder) {
            int valueDetails = layoutScrollValue;
            RelativeLayout.LayoutParams paramsDetails = (RelativeLayout.LayoutParams) ((GoodsDetailsViewHolder) holder).goodsDetailsRelativeLayout.getLayoutParams();

            paramsDetails.setMargins(0, (int) (valueDetails * 0.1), 0, 0);
            Log.d("ValueAdapter", layoutScrollValue + "");
            ((GoodsDetailsViewHolder) holder).goodsDetailsRelativeLayout.setLayoutParams(paramsDetails);


        }


    }


    @Override
    public int getItemCount() {
        return datas.size();
    }

    /**
     * 新建缓存类
     */
    //商品详情二级页面头布局(半透明)缓存类
    public class HeadViewHolder extends RecyclerView.ViewHolder {
        //需要网络解析的数据
        private TextView headGoodsNameTv, headBrandTv, headInfoDesTv, headGoodsPriceTv;

        public HeadViewHolder(View itemView) {
            super(itemView);
            headGoodsNameTv = (TextView) itemView.findViewById(R.id.item_goodsfragment_content_head_goods_name);
            headBrandTv = (TextView) itemView.findViewById(R.id.item_goodsfragment_content_head_brand);
            headInfoDesTv = (TextView) itemView.findViewById(R.id.item_goodsfragment_content_head_info_des);
            headGoodsPriceTv = (TextView) itemView.findViewById(R.id.item_goodsfragment_content_head_goods_price);
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
        private TextView goodsTitleBrandTv, goodsTitleCountryTv, goodsTitleLocationTv, goodsTitleEnglishTv, goodsTitleIntroContent;
        private ImageView goodsTitleImg;
        private RelativeLayout goodsTitleRelativeLayout;

        public GoodsTitleViewHolder(View itemView) {
            super(itemView);
            goodsTitleBrandTv = (TextView) itemView.findViewById(R.id.item_goodsfragment_content_goods_title_brand);
            goodsTitleCountryTv = (TextView) itemView.findViewById(R.id.item_goodsfragment_content_goods_title_country);
            goodsTitleLocationTv = (TextView) itemView.findViewById(R.id.item_goodsfragment_content_goods_title_location);
            goodsTitleEnglishTv = (TextView) itemView.findViewById(R.id.item_goodsfragment_content_goods_title_english);
            goodsTitleIntroContent = (TextView) itemView.findViewById(R.id.item_goodsfragment_content_goods_title_introContent);
            goodsTitleImg = (ImageView) itemView.findViewById(R.id.item_goodsfragment_content_goods_title_img);
            goodsTitleRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.item_goodsfragment_content_goods_title_relativelayout);


        }
    }

    //商品二级页面商品详情不带标题布局缓存类
    public class GoodsDetailsViewHolder extends RecyclerView.ViewHolder {
        //需要网络解析的数据
        private TextView goodsDetailsDetailsName, goodsDetailsIntroContent;
        private ImageView goodsDetailsImg;
        private RelativeLayout goodsDetailsRelativeLayout;

        public GoodsDetailsViewHolder(View itemView) {
            super(itemView);
            goodsDetailsDetailsName = (TextView) itemView.findViewById(R.id.item_goodsfragment_content_goods_details_name);
            goodsDetailsIntroContent = (TextView) itemView.findViewById(R.id.item_goodsfragment_content_goods_details_introContent);
            goodsDetailsImg = (ImageView) itemView.findViewById(R.id.item_goodsfragment_content_goods_details_img);
            goodsDetailsRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.item_goodsfragment_content_goods_details_relativelayout);

        }
    }


}



