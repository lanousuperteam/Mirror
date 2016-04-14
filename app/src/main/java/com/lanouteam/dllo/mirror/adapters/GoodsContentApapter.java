package com.lanouteam.dllo.mirror.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.lanouteam.dllo.mirror.R;
import com.lanouteam.dllo.mirror.base.BaseApplication;
import com.lanouteam.dllo.mirror.bean.GoodsContentBean;
import com.lanouteam.dllo.mirror.bean.RequestParams;
import com.lanouteam.dllo.mirror.net.NetHelper;
import com.lanouteam.dllo.mirror.utils.LoginAndShare;

import java.util.HashMap;

/**
 * Created by dllo on 16/3/31.
 * 商品二级页面滑动的adapter
 */
public class GoodsContentApapter extends RecyclerView.Adapter implements RequestParams {
    private GoodsContentBean datas;
    private int layoutScrollValue;
    //分享
    private String id="28JeX1452078872";
    private LoginAndShare loginAndShare;
    //布局类型
    final int TYPE_HEAD = 0;
    final int TYPE_TRANSPARENT = 1;
    final int TYPE_GOODS_TITLE = 2;
    final int TYPE_GOODS_DETAILS = 3;
    //网络
    private NetHelper netHelper;
    private HashMap goodsInfo;
    private ImageLoader detailsImageLoader, titleImageLoader;
    //传值接口
    private GoodsContentInterface goodsContentInterface;


    public GoodsContentApapter(GoodsContentBean datas) {
        this.datas = datas;
        //网络解析
        goodsInfo = new HashMap();
        goodsInfo.put(DEVICE_TYPE, 2 + "");
        goodsInfo.put(GOODS_ID, "96Psa1455524521");
        netHelper = new NetHelper(BaseApplication.mContext);
        titleImageLoader = netHelper.getImageLoader();
        detailsImageLoader = netHelper.getImageLoader();
        loginAndShare=new LoginAndShare(id);

    }

    /**
     * 该方法为接收activity传来的监听recycleview滑动距离的value
     */
    public void setScrollValue(int scrollValue) {
        this.layoutScrollValue = scrollValue;
        //刷新UI,必须加上这句话,持续的刷新从Actvity 接收的滑动值.
        try {
            notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 决定元素的布局使用哪种类型
     *
     * @return 一个int型标志，传递给onCreateViewHolder的第二个参数
     */
//
    @Override
    public int getItemViewType(int position) {

        if (position == 0)
            return TYPE_HEAD;

        else if (position == 1)
            return TYPE_TRANSPARENT;
        else if (position == 2)

            return TYPE_GOODS_TITLE;
        else

            return TYPE_GOODS_DETAILS;
    }

    /**
     * 渲染具体的ViewHolder
     *
     * @param parent   ViewHolder的容器
     * @param viewType 一个标志，我们根据该标志可以实现渲染不同类型的ViewHolder
     * @return 加载哪个对应的缓存类
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_HEAD) {
            View viewHead = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_goodsfragment_content_head, parent, false);

            return new HeadViewHolder(viewHead);
        } else if (viewType == TYPE_TRANSPARENT) {
            View viewTransparent = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_goodsfragment_content_transparent, parent, false);
            return new TransparentViewHolder(viewTransparent);
        } else if (viewType == TYPE_GOODS_TITLE) {

            View viewGoodsTitle = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_goodsfragment_content_goods_title, parent, false);
            int heightItem = viewGoodsTitle.getHeight();
            Log.e("height", heightItem + "");
            return new GoodsTitleViewHolder(viewGoodsTitle);
        } else {
            View viewGoodsDetails = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_goodsfragment_content_goods_details, parent, false);
            return new GoodsDetailsViewHolder(viewGoodsDetails);
        }


    }

    /**
     * 加载数据
     *
     * @instanceof instanceof是Java的一个二元操作符。由于它是由字母组成的，所以也是Java的保留关键字。
     * 它的作用是测试它左边的对象是否是它右边的类的实例，返回boolean类型的数据
     * java 中的instanceof 运算符是用来在运行时指出对象是否是特定类的一个实例。instanceof通过返回一个布尔值来指出，这个对象是否是这个特定类或者是它的子类的一个实例。
     * @该步骤也可以用switch判断类型TYPE_HEAD之类代替if这样就可以不用instanceof.
     */

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        goodsContentInterface.getPosition(position);//为注掉的动画的第二种方法,将adapter的position传入activity

        if (holder instanceof HeadViewHolder) {
            double valueScroll = layoutScrollValue;
            //TODO 背景颜色改变
            //TODO 接着做背景透明度
//            ((HeadViewHolder) holder).relativeLayoutHead.getBackground().setAlpha((int) (255 - (-valueScroll / 10) * 1.25f));
//            Log.i("透明度", "  " + ((HeadViewHolder) holder).relativeLayoutHead.getBackground().getAlpha() + "     value值" + valueScroll + "     高度" + ((HeadViewHolder) holder).relativeLayoutHead.getHeight());

            //加载网络数据喽!!!!!!!
            ((HeadViewHolder) holder).headGoodsNameTv.setText(datas.getData().getGoods_name());
            ((HeadViewHolder) holder).headBrandTv.setText(datas.getData().getBrand());
            ((HeadViewHolder) holder).headGoodsPriceTv.setText(datas.getData().getGoods_price() + "");
            ((HeadViewHolder) holder).headInfoDesTv.setText(datas.getData().getInfo_des());

            ((HeadViewHolder) holder).imageViewShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loginAndShare.MyShare();
                }
            });


        } else if (holder instanceof GoodsTitleViewHolder) {


            int valueTitle = layoutScrollValue;//滑动总偏移量

            RelativeLayout.LayoutParams paramsTitle = (RelativeLayout.LayoutParams) ((GoodsTitleViewHolder) holder).goodsTitleRelativeLayout.getLayoutParams();

            paramsTitle.setMargins(0, (int) (valueTitle * 0.10), 0, 0);
            ((GoodsTitleViewHolder) holder).goodsTitleRelativeLayout.setLayoutParams(paramsTitle);
            ((GoodsTitleViewHolder) holder).goodsTitleRelativeLayout.getBackground().setAlpha(255);

            //加载网络数据喽!!!!!!!
            ((GoodsTitleViewHolder) holder).goodsTitleBrandTv.setText(datas.getData().getBrand());
            ((GoodsTitleViewHolder) holder).goodsTitleCountryTv.setText(datas.getData().getGoods_data().get(0).getCountry());
            ((GoodsTitleViewHolder) holder).goodsTitleEnglishTv.setText(datas.getData().getGoods_data().get(0).getEnglish());
            ((GoodsTitleViewHolder) holder).goodsTitleIntroContent.setText(datas.getData().getGoods_data().get(0).getIntroContent());
            ((GoodsTitleViewHolder) holder).goodsTitleLocationTv.setText(datas.getData().getGoods_data().get(0).getLocation());

            titleImageLoader.get(datas.getData().getGoods_pic(),titleImageLoader.getImageListener(((GoodsTitleViewHolder) holder).goodsTitleImg,R.mipmap.ic_launcher,R.mipmap.background));



        } else if (holder instanceof GoodsDetailsViewHolder) {

            if (datas.getData().getDesign_des().get(position - 3).getType().equals("1")&&position-2<(datas.getData().getGoods_data().size())) {

                int valueDetails = layoutScrollValue;

                RelativeLayout.LayoutParams paramsDetails = (RelativeLayout.LayoutParams) ((GoodsDetailsViewHolder) holder).goodsDetailsRelativeLayout.getLayoutParams();
                int detailsHeight = ((GoodsDetailsViewHolder) holder).detailsRelativeLayoutAll.getHeight();
                paramsDetails.setMargins(0, (int) -(((-valueDetails) - detailsHeight * (position - 1)) * 0.10), 0, 0);

                ((GoodsDetailsViewHolder) holder).goodsDetailsRelativeLayout.setLayoutParams(paramsDetails);
                ((GoodsDetailsViewHolder) holder).goodsDetailsRelativeLayout.setVisibility(View.VISIBLE);
                //加载网络数据喽!!!!!!!

                    ((GoodsDetailsViewHolder) holder).goodsDetailsDetailsName.setText(datas.getData().getGoods_data().get(position - 2).getName());
                    ((GoodsDetailsViewHolder) holder).goodsDetailsIntroContent.setText(datas.getData().getGoods_data().get(position - 2).getIntroContent());

                detailsImageLoader.get(datas.getData().getDesign_des().get(position - 3).getImg(),detailsImageLoader.getImageListener(((GoodsDetailsViewHolder) holder).goodsDetailsImg,R.mipmap.ic_launcher,R.mipmap.background));

            } else {


                ((GoodsDetailsViewHolder) holder).goodsDetailsRelativeLayout.setVisibility(View.INVISIBLE);
                    detailsImageLoader.get(datas.getData().getDesign_des().get(position - 3).getImg(),detailsImageLoader.getImageListener(((GoodsDetailsViewHolder) holder).goodsDetailsImg,R.mipmap.ic_launcher,R.mipmap.background));

            }
        }

    }


    @Override
    public int getItemCount() {
        return datas != null && datas.getData().getDesign_des().size() + 3 > 0 ? datas.getData().getDesign_des().size() + 3 : 0;
    }

    /**
     * 新建缓存类
     */
    //商品详情二级页面头布局(半透明)缓存类
    public class HeadViewHolder extends RecyclerView.ViewHolder {
        //需要网络解析的数据
        private TextView headGoodsNameTv, headBrandTv, headInfoDesTv, headGoodsPriceTv;
        private RelativeLayout relativeLayoutHead;//透明度
        private ImageView imageViewShare;

        public HeadViewHolder(View itemView) {
            super(itemView);
            headGoodsNameTv = (TextView) itemView.findViewById(R.id.item_goodsfragment_content_head_goods_name);
            headBrandTv = (TextView) itemView.findViewById(R.id.item_goodsfragment_content_head_brand);
            headInfoDesTv = (TextView) itemView.findViewById(R.id.item_goodsfragment_content_head_info_des);
            headGoodsPriceTv = (TextView) itemView.findViewById(R.id.item_goodsfragment_content_head_goods_price);
            relativeLayoutHead = (RelativeLayout) itemView.findViewById(R.id.item_goodsfragment_content_head_relativelayout);
            imageViewShare= (ImageView) itemView.findViewById(R.id.item_goodsfragment_content_head_share);
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
        private  ImageView  goodsTitleImg;
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
        private ImageView  goodsDetailsImg;
        private RelativeLayout goodsDetailsRelativeLayout, detailsRelativeLayoutAll;

        public GoodsDetailsViewHolder(View itemView) {
            super(itemView);
            goodsDetailsDetailsName = (TextView) itemView.findViewById(R.id.item_goodsfragment_content_goods_details_name);
            goodsDetailsIntroContent = (TextView) itemView.findViewById(R.id.item_goodsfragment_content_goods_details_introContent);
            goodsDetailsImg = (ImageView) itemView.findViewById(R.id.item_goodsfragment_content_goods_details_img);
            goodsDetailsRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.item_goodsfragment_content_goods_details_relativelayout);
            detailsRelativeLayoutAll = (RelativeLayout) itemView.findViewById(R.id.item_goodsfragment_content_goods_details_relativelayout_all);
        }
    }

    /**
     * 该步骤为接口传值,我需要将adapter的position传入activity,为动画实现第二种方法,暂时还未用到
     */
    public void setPosition(GoodsContentInterface goodsContentInterface) {
        this.goodsContentInterface = goodsContentInterface;
    }


}



