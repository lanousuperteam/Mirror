package com.lanouteam.dllo.mirror.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.lanouteam.dllo.mirror.R;
import com.lanouteam.dllo.mirror.adapters.GoodsContentApapter;
import com.lanouteam.dllo.mirror.adapters.GoodsContentInterface;
import com.lanouteam.dllo.mirror.base.BaseActivity;
import com.lanouteam.dllo.mirror.bean.GoodsContentBean;
import com.lanouteam.dllo.mirror.bean.RequestUrls;
import com.lanouteam.dllo.mirror.net.NetHelper;
import com.lanouteam.dllo.mirror.net.NetListener;
import com.lanouteam.dllo.mirror.utils.L;

import java.util.HashMap;

/**
 * Created by dllo on 16/3/31.
 */
public class GoodsContentActivity extends BaseActivity implements View.OnClickListener {
    //定义组件
    private SimpleDraweeView backgroundIv;
    private TextView wearTv;
    private RecyclerView goodsContentRecyclerView;
    private GoodsContentApapter goodsContentApapter;
    private RelativeLayout relativeLayoutButtom;
    //异步滑动
    private int value;
    private boolean isup = true, isDown = true, isVisible = false;
    private boolean dyUp = true;
    //网络解析
    private HashMap goodsInfo;
    private NetHelper netHelper;




    @Override
    protected int getLayout() {
        return R.layout.activity_goods_content;
    }

    @Override
    protected void initData() {
        wearTv.setOnClickListener(this);

        //网络解析
        goodsInfo = new HashMap();
        goodsInfo.put("device_type", 2 + "");
        goodsInfo.put("goods_id", "96Psa1455524521");
        netHelper = new NetHelper(this);

        netHelper.getJsonData(RequestUrls.GOODS_INFO, new NetListener() {
            @Override
            public void getSuccess(Object object) {

                Gson gson = new Gson();
                GoodsContentBean data = gson.fromJson(object.toString(), GoodsContentBean.class);

                goodsContentApapter = new GoodsContentApapter(data);
                GridLayoutManager gm = new GridLayoutManager(getBaseContext(), 1);
                gm.setOrientation(LinearLayoutManager.VERTICAL);
                goodsContentRecyclerView.setLayoutManager(gm);
                goodsContentRecyclerView.setAdapter(goodsContentApapter);
                //解析图片
                backgroundIv.setImageURI(Uri.parse(data.getData().getGoods_img()));
//                ImageLoader backgroundImageLoader = netHelper.getImageLoader();
//                backgroundImageLoader.get(data.getData().getGoods_img(), backgroundImageLoader.getImageListener(
//                        backgroundIv, R.mipmap.ic_launcher, R.mipmap.background));
                /**
                 * 第二种动画出现的方法 通过接口传值(position)通过判断滑动的position位置控制动画
                 * */
                goodsContentApapter.setPosition(new GoodsContentInterface() {
                    @Override
                    public void getPosition(int position) {


                        //该数值为滑动到recycleview第三个布局by滑动的数值范围
                        if (dyUp) {//判断滑动方向  dy>0为上滑


                            //该数值为滑动到recycleview第三个布局by滑动的数值范围
                            if (position == 3 && isup) {

                                Animation animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.goods_content_layout_into);
                                relativeLayoutButtom.setAnimation(animation);
                                relativeLayoutButtom.setVisibility(View.VISIBLE);
                                isup = false;
                                isDown = true;
                                isVisible = true;


                            }

                        } else {
                            if (position == 1 && isVisible && isDown) {

                                Animation animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.goods_content_layout_out);
                                relativeLayoutButtom.setAnimation(animation);
                                relativeLayoutButtom.setVisibility(View.INVISIBLE);
                                isup = true;
                                isDown = false;
                                isVisible = false;

                            }

                        }
                        L.i("positionScroll", position + "  ");
                    }
                });

            }

            @Override
            public void getFailed(int s) {

            }
        }, goodsInfo);


        /**
         * 该方法为对recycleview进行滑动监听 获取滑动距离
         * */

        goodsContentRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                /**
                 * 这里的 value 是获得recyclerview 所有的滑动距离,将每一次的滑动距离叠加形成的结果.
                 * */
                value -= dy;
                L.d("滑动效果", value + "");
                //这是Recyclerview 的方法来获得当前的 value 值.
                goodsContentApapter.setScrollValue(value);
                dyUp = dy > 0;  //该步是第二种动画出现的步骤

                L.i("dy", "dy" + " " + dy + "");
                /**
                 * 底部动画操作 !!!该方法有待商榷,先暂时注掉,采用方法二
                 *
                 * */
                //***************************************************************************************
//                if (dyUp) {//判断滑动方向  dy>0为上滑 //该数值为滑动到recycleview第三个布局by滑动的数值范围
//
//
//                    if (value <= -2500 && isup) {
//
//                        Animation animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.goods_content_layout_into);
//                        relativeLayoutButtom.setAnimation(animation);
//                        relativeLayoutButtom.setVisibility(View.VISIBLE);
//                        isup = false;
//                        isDown = true;
//                        isVisible = true;
//                    }
//
//                } else {
//
//
//                    if (isVisible && value >= -2600 && isDown) {
//
//                        Animation animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.goods_content_layout_out);
//                        relativeLayoutButtom.setAnimation(animation);
//                        relativeLayoutButtom.setVisibility(View.INVISIBLE);
//                        isup = true;
//                        isDown = false;
//
//                    }
//
//                }
                //***************************************************************************************
            }
        });
    }


    @Override
    protected void initView() {
        goodsContentRecyclerView = bindView(R.id.activity_goods_content_recyclerview);
        relativeLayoutButtom = bindView(R.id.activity_goods_content_relativelayout);
        backgroundIv = bindView(R.id.activity_goods_content_background_iv);
        wearTv=bindView(R.id.activity_goods_content_tv);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_goods_content_tv:
                Intent intentWear=new Intent();
                intentWear.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intentWear.setClass(this,WearActivity.class);
                startActivity(intentWear);
                
        }

    }

//    public class GoodsContentApapter extends RecyclerView.Adapter {
//        private GoodsContentBean datas;
//        private int layoutScrollValue;
//
//        final int TYPE_HEAD = 0;
//        final int TYPE_TRANSPARENT = 1;
//        final int TYPE_GOODS_TITLE = 2;
//        final int TYPE_GOODS_DETAILS = 3;
//
//
//
//        GoodsContentInterface goodsContentInterface;
//
//
//        public GoodsContentApapter(GoodsContentBean datas) {
//            this.datas = datas;
//
//
//        }
//
//        /**
//         * 该方法为接收activity传来的监听recycleview滑动距离的value
//         */
////        public void setScrollValue(int scrollValue) {
////            this.layoutScrollValue = scrollValue;
////            //刷新UI
////            /**
////             * 必须加上这句话,持续的刷新从Actvity 接收的滑动值.
////             * */
////            try {
////                notifyDataSetChanged();
////            } catch (Exception e) {
////                e.printStackTrace();
////            }
////        }
//
//
//        /**
//         * 决定元素的布局使用哪种类型
//         *
//         * @return 一个int型标志，传递给onCreateViewHolder的第二个参数
//         */
////
//        @Override
//        public int getItemViewType(int position) {
//
//            if (position == 0)
//                return TYPE_HEAD;
//
//            else if (position == 1)
//                return TYPE_TRANSPARENT;
//            else if (position == 2)
//
//                return TYPE_GOODS_TITLE;
//            else
//
//                return TYPE_GOODS_DETAILS;
//
//        }
//
//        /**
//         * 渲染具体的ViewHolder
//         *
//         * @param parent   ViewHolder的容器
//         * @param viewType 一个标志，我们根据该标志可以实现渲染不同类型的ViewHolder
//         * @return 加载哪个对应的缓存类
//         */
//        @Override
//        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
//            if (viewType == TYPE_HEAD) {
//                View viewHead = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_goodsfragment_content_head, parent, false);
//
//                return new HeadViewHolder(viewHead);
//            } else if (viewType == TYPE_TRANSPARENT) {
//                View viewTransparent = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_goodsfragment_content_transparent, parent, false);
//                return new TransparentViewHolder(viewTransparent);
//            } else if (viewType == TYPE_GOODS_TITLE) {
//
//                View viewGoodsTitle = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_goodsfragment_content_goods_title, parent, false);
//                int heightItem = viewGoodsTitle.getHeight();
//                Log.e("height", heightItem + "");
//                return new GoodsTitleViewHolder(viewGoodsTitle);
//            } else {
//                View viewGoodsDetails = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_goodsfragment_content_goods_details, parent, false);
//                return new GoodsDetailsViewHolder(viewGoodsDetails);
//            }
//
//
//        }
//
//        /**
//         * 加载数据
//         *
//         * @instanceof instanceof是Java的一个二元操作符，和==，>，<是同一类东东。由于它是由字母组成的，所以也是Java的保留关键字。
//         * 它的作用是测试它左边的对象是否是它右边的类的实例，返回boolean类型的数据
//         * java 中的instanceof 运算符是用来在运行时指出对象是否是特定类的一个实例。instanceof通过返回一个布尔值来指出，这个对象是否是这个特定类或者是它的子类的一个实例。
//         * @该步骤也可以用switch判断类型TYPE_HEAD之类代替if这样就可以不用instanceof.
//         */
//
//        @Override
//        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
//            goodsContentInterface.getPosition(position);//为注掉的动画的第二种方法,将adapter的position传入activity
//
//            if (holder instanceof HeadViewHolder) {
//                double valueScroll = layoutScrollValue;
//                //TODO 背景颜色改变
//                //TODO 接着做背景透明度
////            ((HeadViewHolder) holder).relativeLayoutHead.getBackground().setAlpha((int) (255 - (-valueScroll / 10) * 1.25f));
////            Log.i("透明度", "  " + ((HeadViewHolder) holder).relativeLayoutHead.getBackground().getAlpha() + "     value值" + valueScroll + "     高度" + ((HeadViewHolder) holder).relativeLayoutHead.getHeight());
//
//                //加载网络数据喽!!!!!!!
//                ((HeadViewHolder) holder).headGoodsNameTv.setText(datas.getData().getGoods_name());
//                ((HeadViewHolder) holder).headBrandTv.setText(datas.getData().getBrand());
//                ((HeadViewHolder) holder).headGoodsPriceTv.setText(datas.getData().getGoods_price() + "");
//                ((HeadViewHolder) holder).headInfoDesTv.setText(datas.getData().getInfo_des());
//
//
//            } else if (holder instanceof GoodsTitleViewHolder) {
//
//
//             //   int valueTitle = layoutScrollValue;//滑动总偏移量
//
//                RelativeLayout.LayoutParams paramsTitle = (RelativeLayout.LayoutParams) ((GoodsTitleViewHolder) holder).goodsTitleRelativeLayout.getLayoutParams();
//
//                paramsTitle.setMargins(0, (int) (value * 0.10), 0, 0);
//               // paramsTitle.setMargins(0, (int) (-value*0.5),0,0);
//                ((GoodsTitleViewHolder) holder).goodsTitleRelativeLayout.setLayoutParams(paramsTitle);
//                ((GoodsTitleViewHolder) holder).goodsTitleRelativeLayout.getBackground().setAlpha(255);
//
//                //加载网络数据喽!!!!!!!
//                ((GoodsTitleViewHolder) holder).goodsTitleBrandTv.setText(datas.getData().getBrand());
//                ((GoodsTitleViewHolder) holder).goodsTitleCountryTv.setText(datas.getData().getGoods_data().get(0).getCountry());
//                ((GoodsTitleViewHolder) holder).goodsTitleEnglishTv.setText(datas.getData().getGoods_data().get(0).getEnglish());
//                ((GoodsTitleViewHolder) holder).goodsTitleIntroContent.setText(datas.getData().getGoods_data().get(position).getIntroContent());
//                ((GoodsTitleViewHolder) holder).goodsTitleLocationTv.setText(datas.getData().getGoods_data().get(0).getLocation());
//                ((GoodsTitleViewHolder) holder).goodsTitleImg.setImageURI(Uri.parse(datas.getData().getGoods_pic()));
//                Log.i("图片请求", "aaaaaa");
//
//            } else if (holder instanceof GoodsDetailsViewHolder) {
//
//                if (datas.getData().getDesign_des().get(position - 3).getType().equals("1")) {
//
//
//                  //  int valueDetails = layoutScrollValue;
//
//                    RelativeLayout.LayoutParams paramsDetails = (RelativeLayout.LayoutParams) ((GoodsDetailsViewHolder) holder).goodsDetailsRelativeLayout.getLayoutParams();
//                    int detailsHeight = ((GoodsDetailsViewHolder) holder).detailsRelativeLayoutAll.getHeight();
//                    paramsDetails.setMargins(0, (int) -(((-value) - detailsHeight * (position - 1)) * 0.10), 0, 0);
//                   // paramsDetails.setMargins(0, (int) (-value*0.5),0,0);
//
//                    ((GoodsDetailsViewHolder) holder).goodsDetailsRelativeLayout.setLayoutParams(paramsDetails);
//                    ((GoodsDetailsViewHolder) holder).goodsDetailsRelativeLayout.setVisibility(View.VISIBLE);
//                    //加载网络数据喽!!!!!!!
//                    ((GoodsDetailsViewHolder) holder).goodsDetailsImg.getHierarchy().setActualImageFocusPoint(new PointF(0.5f, 0f));
//                    ((GoodsDetailsViewHolder) holder).goodsDetailsDetailsName.setText(datas.getData().getGoods_data().get(position - 2).getName());
//                    ((GoodsDetailsViewHolder) holder).goodsDetailsIntroContent.setText(datas.getData().getGoods_data().get(position - 2).getIntroContent());
//                    ((GoodsDetailsViewHolder) holder).goodsDetailsImg.setImageURI(Uri.parse(datas.getData().getDesign_des().get(position - 3).getImg()));
//
//
//                } else {
//
//                    ((GoodsDetailsViewHolder) holder).goodsDetailsImg.getHierarchy().setActualImageFocusPoint(new PointF(0.5f, 0f));
//                    ((GoodsDetailsViewHolder) holder).goodsDetailsRelativeLayout.setVisibility(View.GONE);
//
//                    ((GoodsDetailsViewHolder) holder).goodsDetailsImg.setImageURI(Uri.parse(datas.getData().getDesign_des().get(position - 3).getImg()));
//
//
//
//
//                }
//                Log.i("图片请求","ccccccc");
//
//            }
//
//
//        }
//
//
//        @Override
//        public int getItemCount() {
//            return datas != null && datas.getData().getDesign_des().size() + 3 > 0 ? datas.getData().getDesign_des().size() + 3 : 0;
//        }
//
//
//        /**
//         * 新建缓存类
//         */
//        //商品详情二级页面头布局(半透明)缓存类
//        public class HeadViewHolder extends RecyclerView.ViewHolder {
//            //需要网络解析的数据
//            private TextView headGoodsNameTv, headBrandTv, headInfoDesTv, headGoodsPriceTv;
//            private RelativeLayout relativeLayoutHead;
//
//            public HeadViewHolder(View itemView) {
//                super(itemView);
//                headGoodsNameTv = (TextView) itemView.findViewById(R.id.item_goodsfragment_content_head_goods_name);
//                headBrandTv = (TextView) itemView.findViewById(R.id.item_goodsfragment_content_head_brand);
//                headInfoDesTv = (TextView) itemView.findViewById(R.id.item_goodsfragment_content_head_info_des);
//                headGoodsPriceTv = (TextView) itemView.findViewById(R.id.item_goodsfragment_content_head_goods_price);
//                relativeLayoutHead = (RelativeLayout) itemView.findViewById(R.id.item_goodsfragment_content_head_relativelayout);
//            }
//        }
//
//        //商品二级页面第二布局(全透明)加载布局时别忘喽!
//        public class TransparentViewHolder extends RecyclerView.ViewHolder {
//            public TransparentViewHolder(View itemView) {
//                super(itemView);
//            }
//        }
//
//        //商品二级页面商品详情带标题布局缓存类
//        public class GoodsTitleViewHolder extends RecyclerView.ViewHolder {
//            //需要网络解析的数据
//            private TextView goodsTitleBrandTv, goodsTitleCountryTv, goodsTitleLocationTv, goodsTitleEnglishTv, goodsTitleIntroContent;
//            private  SimpleDraweeView  goodsTitleImg;
//            private RelativeLayout goodsTitleRelativeLayout;
//
//            public GoodsTitleViewHolder(View itemView) {
//                super(itemView);
//                goodsTitleBrandTv = (TextView) itemView.findViewById(R.id.item_goodsfragment_content_goods_title_brand);
//                goodsTitleCountryTv = (TextView) itemView.findViewById(R.id.item_goodsfragment_content_goods_title_country);
//                goodsTitleLocationTv = (TextView) itemView.findViewById(R.id.item_goodsfragment_content_goods_title_location);
//                goodsTitleEnglishTv = (TextView) itemView.findViewById(R.id.item_goodsfragment_content_goods_title_english);
//                goodsTitleIntroContent = (TextView) itemView.findViewById(R.id.item_goodsfragment_content_goods_title_introContent);
//                goodsTitleImg = (SimpleDraweeView) itemView.findViewById(R.id.item_goodsfragment_content_goods_title_img);
//                goodsTitleRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.item_goodsfragment_content_goods_title_relativelayout);
//
//
//            }
//        }
//
//        //商品二级页面商品详情不带标题布局缓存类
//        public class GoodsDetailsViewHolder extends RecyclerView.ViewHolder {
//            //需要网络解析的数据
//            private TextView goodsDetailsDetailsName, goodsDetailsIntroContent;
//            private  SimpleDraweeView  goodsDetailsImg;
//            private RelativeLayout goodsDetailsRelativeLayout, detailsRelativeLayoutAll;
//
//            public GoodsDetailsViewHolder(View itemView) {
//                super(itemView);
//                goodsDetailsDetailsName = (TextView) itemView.findViewById(R.id.item_goodsfragment_content_goods_details_name);
//                goodsDetailsIntroContent = (TextView) itemView.findViewById(R.id.item_goodsfragment_content_goods_details_introContent);
//                goodsDetailsImg = (SimpleDraweeView) itemView.findViewById(R.id.item_goodsfragment_content_goods_details_img);
//                goodsDetailsRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.item_goodsfragment_content_goods_details_relativelayout);
//                detailsRelativeLayoutAll = (RelativeLayout) itemView.findViewById(R.id.item_goodsfragment_content_goods_details_relativelayout_all);
//            }
//        }
//
//        /**
//         * 该步骤为接口传值,我需要将adapter的position传入activity,为动画实现第二种方法,暂时还未用到
//         */
//        public void setPosition(GoodsContentInterface goodsContentInterface) {
//            this.goodsContentInterface = goodsContentInterface;
//        }
//
//
//    }




}
