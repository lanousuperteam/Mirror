package com.lanouteam.dllo.mirror.activity;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

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
import com.lanouteam.dllo.mirror.utils.L;
import com.lanouteam.dllo.mirror.utils.jcvideoplayerlib.JCVideoPlayer;

import java.util.HashMap;


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
    private WearListViewAdapter wearListViewAdapter;
    private ListView wearListView;
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
        //video上的图片填充类型:图片充满
        JCVideoPlayer.setThumbImageViewScalType(ImageView.ScaleType.CENTER_CROP);
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

                wearListViewAdapter = new WearListViewAdapter(data);
                wearListView.setAdapter(wearListViewAdapter);

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
        wearListView = bindView(R.id.wear_activity_listview);
        imageViewBuy = bindView(R.id.activity_wear_buy);
        imageViewReturn = bindView(R.id.activity_wear_return_iv);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_wear_buy:
                Intent intentBuy = new Intent(WearActivity.this, GoodsContentActivity.class);
                startActivity(intentBuy);
            case R.id.activity_wear_return_iv:
                Intent intentReturn = new Intent(WearActivity.this, GoodsContentActivity.class);
                startActivity(intentReturn);
        }
    }

    /**
     * 该activity对应的adapter
     */
    public class WearListViewAdapter extends BaseAdapter {
        private WearBean data;
        final int TYPE_HEAD = 0;
        final int TYPE_PHOTOS = 1;

        public WearListViewAdapter(WearBean data) {
            this.data = data;

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
        public int getCount() {
            return data != null && data.getData().getWear_video().size() > 0 ? data.getData().getWear_video().size() : 0;
        }

        @Override
        public Object getItem(int position) {
            return data.getData().getWear_video().get(position) == null ? data.getData().getWear_video().get(position) : null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listview_wear_activity, null);
                holder = new ViewHolder();
                holder.imageViewPhoto = (ImageView) convertView.findViewById(R.id.item_wear_activity_iv);
                holder.jcVideoPlayer = (JCVideoPlayer) convertView.findViewById(R.id.wear_activity_video_controller);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            /**
             * getLocationOnScreen:计算图片在该屏幕上的坐标. 参数为int类型的XY坐标.
             *
             * location: 两个整数的数组来保存坐标
             */
            holder.imageViewPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //带动画跳转传值

                    Intent intent = new Intent(WearActivity.this, WearDetailShowActivity.class);

                    int[] location = new int[2];
                    holder.imageViewPhoto.getLocationOnScreen(location);
                    intent.putExtra("locationX", location[0]);
                    intent.putExtra("locationY", location[1]);
                    intent.putExtra(RequestParams.GOODS_ID, id);

                    intent.putExtra("width", holder.imageViewPhoto.getWidth());
                    intent.putExtra("height", holder.imageViewPhoto.getHeight());
                    L.i("XY值", location[0] + "  " + location[1] + "  " + holder.imageViewPhoto.getWidth() + "  " + holder.imageViewPhoto.getHeight() + "");


                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }
            });

            return convertView;

        }

        //新建一个缓存类 需要解析的数据
        public class ViewHolder {

            ImageView imageViewPhoto;
            JCVideoPlayer jcVideoPlayer;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}
