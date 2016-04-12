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
import com.lanouteam.dllo.mirror.bean.RequestUrls;
import com.lanouteam.dllo.mirror.bean.WearBean;
import com.lanouteam.dllo.mirror.jcvideoplayerlib.JCVideoPlayer;
import com.lanouteam.dllo.mirror.net.NetHelper;
import com.lanouteam.dllo.mirror.net.NetListener;
import com.lanouteam.dllo.mirror.utils.L;

import java.util.HashMap;



/**
 * Created by dllo on 16/4/8.
 */
public class WearActivity extends BaseActivity implements View.OnClickListener,RequestUrls {
    //视频
    private JCVideoPlayer jcVideoPlayer;
    //网络解析
    private NetHelper netHelper;
    private HashMap wearInfo;
    private ImageLoader wearImageLoader;
    //组件
    private WearListViewAdapter wearListViewAdapter;
    private ListView wearListView;
    private View viewHead = LayoutInflater.from(BaseApplication.mContext).inflate(R.layout.item_listview_wear_activity_head, null);
    private ImageView imageViewBuy,imageViewReturn;

    @Override
    protected int getLayout() {
        return R.layout.activity_wear;
    }

    @Override
    protected void initData() {

        //网络解析
        netHelper = new NetHelper(this);
        wearInfo = new HashMap();
        wearInfo.put("device_type", "2" );
        wearInfo.put("goods_id", "269");
        wearInfo.put("version", "1.0.1");
        wearImageLoader = netHelper.getImageLoader();
        //video上的图片填充类型:图片充满
        JCVideoPlayer.setThumbImageViewScalType(ImageView.ScaleType.CENTER_CROP);
        netHelper.getJsonData(GOODS_LIST, new NetListener() {
            @Override
            public void getSuccess(Object object) {
                L.d(object.toString());

                Gson gson = new Gson();
                WearBean data = gson.fromJson(object.toString(), WearBean.class);

                for (int i = 0; i < data.getData().getList().get(0).getWear_video().size(); i++) {//position待传,暂时用get(0)代替
                    if (data.getData().getList().get(0).getWear_video().get(i).getType().equals("8")) {
                        jcVideoPlayer.setUp("http://flv2.bn.netease.com/videolib3/1604/08/PacAg1401/SD/PacAg1401-mobile.mp4", null);
                    }

                    if (data.getData().getList().get(0).getWear_video().get(i).getType().equals("9")) {

                        wearImageLoader.get(data.getData().getList().get(0).getWear_video().get(i).getData(), wearImageLoader.getImageListener(jcVideoPlayer.ivThumb, R.mipmap.ic_launcher, R.mipmap.background));
                        Log.i("url请求", data.getData().getList().get(0).getWear_video().get(i).getData());
                    }
                }

                wearListViewAdapter = new WearListViewAdapter(data);
                wearListView.setAdapter(wearListViewAdapter);
                wearListView.addHeaderView(viewHead);

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
        jcVideoPlayer = (JCVideoPlayer) viewHead.findViewById(R.id.wear_activity_video_controller);
        wearListView = bindView(R.id.wear_activity_listview);
        imageViewBuy= bindView(R.id.activity_wear_buy);
        imageViewReturn=bindView(R.id.activity_wear_return_iv);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_wear_buy:
                Intent intentBuy=new Intent(WearActivity.this,GoodsContentActivity.class);
                startActivity(intentBuy);
            case R.id.activity_wear_return_iv:
                Intent intentReturn=new Intent(WearActivity.this,GoodsContentActivity.class);
                startActivity(intentReturn);
        }
    }

    // 该activity的adapter
    public class WearListViewAdapter extends BaseAdapter {
        private WearBean data;


        public WearListViewAdapter(WearBean data) {
            this.data = data;

        }
        @Override
        public int getCount() {
            return data != null && data.getData().getList().get(0).getWear_video().size() > 0 ? data.getData().getList().get(0).getWear_video().size() : 0;
        }

        @Override
        public Object getItem(int position) {
            return data != null && data.getData().getList().get(0).getWear_video().size() > 0 ? data.getData().getList().get(0).getWear_video().get(position) : null;
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
                holder.imageView_Photo = (ImageView) convertView.findViewById(R.id.item_wear_activity_iv);
                holder.jcVideoPlayer = (JCVideoPlayer) convertView.findViewById(R.id.wear_activity_video_controller);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            /**
             * getLocationOnScreen:计算图片在该屏幕上的坐标. 参数为int类型的XY坐标.
             *
             * @param location an array of two integers in which to hold the coordinates
             */
            holder.imageView_Photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //带动画跳转传值

                    Intent intent = new Intent(WearActivity.this, WearDetailShowActivity.class);
                    int[] location = new int[2];
                    holder.imageView_Photo.getLocationOnScreen(location);
                    intent.putExtra("locationX", location[0]);
                    intent.putExtra("locationY", location[1]);

                    intent.putExtra("width", holder.imageView_Photo.getWidth());
                    intent.putExtra("height", holder.imageView_Photo.getHeight());
                    L.i("XY值", location[0] + "  " + location[1] + "  " + holder.imageView_Photo.getWidth() + "  " + holder.imageView_Photo.getHeight() + "");


                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }
            });

            //imageloader进行网络解析图片
            wearImageLoader.get(data.getData().getList().get(0).getWear_video().get(1).getData(), wearImageLoader.getImageListener(
                    holder.imageView_Photo, R.mipmap.ic_launcher, R.mipmap.background));


            return convertView;

        }

        //新建一个缓存类 需要解析的数据
        public class ViewHolder {

            ImageView imageView_Photo;
            JCVideoPlayer jcVideoPlayer;
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}
