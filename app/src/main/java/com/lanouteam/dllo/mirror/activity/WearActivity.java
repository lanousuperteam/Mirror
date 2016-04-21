package com.lanouteam.dllo.mirror.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.google.gson.Gson;
import com.lanouteam.dllo.mirror.R;
import com.lanouteam.dllo.mirror.base.BaseActivity;
import com.lanouteam.dllo.mirror.base.BaseApplication;
import com.lanouteam.dllo.mirror.bean.RequestParams;
import com.lanouteam.dllo.mirror.bean.RequestUrls;
import com.lanouteam.dllo.mirror.bean.WearBean;
import com.lanouteam.dllo.mirror.net.NetHelper;
import com.lanouteam.dllo.mirror.net.NetListener;
import com.lanouteam.dllo.mirror.utils.SPUtils;
import com.lanouteam.dllo.mirror.utils.jcvideoplayerlib.JCVideoPlayer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by dllo on 16/4/8.
 */
public class WearActivity extends BaseActivity implements View.OnClickListener, RequestUrls {

    //网络解析
    private NetHelper netHelper;
    private HashMap wearInfo;
    private ImageLoader wearImageLoader;
    private String id;
    //组件
    private WearRecyclerviewViewAdapter wearRecyclerviewAdapter;
    private RecyclerView  wearRecyclerView;
    private View viewHead=LayoutInflater.from(BaseApplication.mContext).inflate(R.layout.item_recyclerview_wear_activity_head, null);

    private ImageView imageViewBuy, imageViewReturn;
    //


    @Override
    protected int getLayout() {
        return R.layout.activity_wear;
    }

    @Override
    protected void initData() {
        Intent goodsIntent = getIntent();
        id = goodsIntent.getStringExtra(RequestParams.GOODS_ID);
        //网络解析
        netHelper = new NetHelper(this);
        wearInfo = new HashMap();
        wearInfo.put(RequestParams.DEVICE_TYPE, "2");
        wearInfo.put(RequestParams.GOODS_ID, id);
        wearInfo.put(RequestParams.APP_VERSION, "1.0.1");
        wearImageLoader = netHelper.getImageLoader();

        netHelper.getJsonData(GOODS_INFO, new NetListener() {
            @Override
            public void getSuccess(Object object) {

                Gson gson = new Gson();
                WearBean data = gson.fromJson(object.toString(), WearBean.class);
                wearRecyclerviewAdapter = new WearRecyclerviewViewAdapter(data.getData().getWear_video());
                GridLayoutManager gm = new GridLayoutManager(getBaseContext(), 1);
                gm.setOrientation(LinearLayoutManager.VERTICAL);
                wearRecyclerView.setLayoutManager(gm);
                wearRecyclerView.setAdapter(wearRecyclerviewAdapter);




            }

            @Override
            public void getFailed(int s) {

            }
        }, wearInfo);

        imageViewBuy.setOnClickListener(this);
        imageViewReturn.setOnClickListener(this);

    }

    @Override
    protected void initView() {
        wearRecyclerView = bindView(R.id.wear_activity_listview);
        imageViewBuy = bindView(R.id.activity_wear_buy);
        imageViewReturn = bindView(R.id.activity_wear_return_iv);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_wear_buy:
                boolean isTrue= (boolean) SPUtils.get(WearActivity.this, "shoppingCar", true);
                if(isTrue) {
                    Intent intentBuy = new Intent(WearActivity.this, BuyDetailsActivity.class);
                    intentBuy.putExtra(RequestParams.GOODS_ID,id);
                    startActivity(intentBuy);
                }else{
                    Intent intentLogin=new Intent(this,LoginActivity.class);
                    startActivity(intentLogin);
                }

            case R.id.activity_wear_return_iv:
                finish();
        }
    }

    /**
     * 该activity对应的adapter
     */
    public class WearRecyclerviewViewAdapter extends RecyclerView.Adapter {
        List<String> imageList = new ArrayList<>();
        String videoUrl = null;
        String videoImg = null;

        private List<WearBean.DataEntity.Wear_videoEntity> list;
        final int TYPE_HEAD = 0;
        final int TYPE_PHOTOS = 1;

        public WearRecyclerviewViewAdapter(List<WearBean.DataEntity.Wear_videoEntity> list) {
            this.list = list;
        }

        @Override
        public int getItemViewType(int position) {

            if (position == 0) {
                return TYPE_HEAD;
            } else {
                return TYPE_PHOTOS;
            }
        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_HEAD) {
                View viewHead = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_wear_activity_head, parent, false);
                return new HeadViewHolder(viewHead);
            } else {
                View viewPhotos = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_wear_activity, null);
                return new PhotosViewHolder(viewPhotos);
            }
        }


        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

            List<String> videoList = new ArrayList<>();

            for (int i = 0; i < list.size(); i++) {
                String type = list.get(i).getType();
                if (type.equals("8")) {
                    videoList.add(list.get(i).getData());
                    videoUrl = list.get(i).getData();

                } else if (type.equals("9")) {
                    videoImg = list.get(i).getData();

                } else {
                    imageList.add(list.get(i).getData());
                }


            }

            if (holder instanceof HeadViewHolder) {

                JCVideoPlayer.setThumbImageViewScalType(ImageView.ScaleType.FIT_XY);
                ((HeadViewHolder) holder).jCVideoPlayer.ivStart.performClick();

                ((HeadViewHolder) holder).jCVideoPlayer.setUp(videoUrl, "", false);


                wearImageLoader.get(videoImg, wearImageLoader.getImageListener(((HeadViewHolder) holder).jCVideoPlayer.ivThumb, R.mipmap.ic_launcher, R.mipmap.ic_launcher));

            }
            if (holder instanceof PhotosViewHolder) {

                Picasso.with(WearActivity.this).load(imageList.get(position - 1)).into(((PhotosViewHolder) holder).iv);


                ((PhotosViewHolder) holder).iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent();
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        int[] location = new int[2];
                        ((PhotosViewHolder) holder).iv.getLocationOnScreen(location);//location 里 有iv 的横纵坐标
                        intent.putExtra("locationX", location[0]);
                        intent.putExtra("locationY", location[1]);
                        intent.putExtra("photoUri",imageList.get(position - 1));
                        intent.putExtra("width", ((PhotosViewHolder) holder).iv.getWidth());
                        intent.putExtra("height", ((PhotosViewHolder) holder).iv.getHeight());
                        intent.setClass(BaseApplication.mContext,WearDetailShowActivity.class);
                        BaseApplication.mContext.startActivity(intent);
                    }
                });
            }
        }


        @Override
        public int getItemCount() {
            return list != null && list.size() - 1 > 0 ? list.size() - 1 : 0;

        }

        public class HeadViewHolder extends RecyclerView.ViewHolder {

            private JCVideoPlayer jCVideoPlayer;

            public HeadViewHolder(View itemView) {
                super(itemView);
                jCVideoPlayer = (JCVideoPlayer) itemView.findViewById(R.id.wear_activity_video_controller);
            }
        }

        public class PhotosViewHolder extends RecyclerView.ViewHolder {

            private ImageView iv;

            public PhotosViewHolder(View itemView) {
                super(itemView);
                iv = (ImageView) itemView.findViewById(R.id.item_wear_activity_iv);
            }
        }
    }
}