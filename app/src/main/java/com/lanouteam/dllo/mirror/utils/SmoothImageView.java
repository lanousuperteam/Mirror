package com.lanouteam.dllo.mirror.utils;

import android.animation.Animator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

/**
 * 平滑变化的显示图片的ImageView
 * 仅限于用于:从一个ScaleType==CENTER_CROP的ImageView，切换到另一个ScaleType=
 * FIT_CENTER的ImageView，或者反之
 *
 */

public class SmoothImageView extends ImageView {

	private static final int STATE_NORMAL = 0;
	private static final int STATE_TRANSFORM_IN = 1;
	private static final int STATE_TRANSFORM_OUT = 2;
	private int mOriginalWidth;//原始的宽
	private int mOriginalHeight;
	private int mOriginalLocationX;//原始图片的x坐标
	private int mOriginalLocationY;
	private int mState = STATE_NORMAL;
	private Matrix mSmoothMatrix;//矩阵 ['meɪtrɪks] 在图像处理方面，主要是用于平面的缩放、平移、旋转等操作。在Android里面，Matrix由9个float值构成，是一个3*3的矩阵
	private Bitmap mBitmap;
	private boolean mTransformStart = false;
	private Transfrom mTransfrom;//变换
	private final int mBgColor = 0xFF000000;//背景颜色
	private int mBgAlpha = 0;
	private Paint mPaint;
	//不同参数的构造方法
	public SmoothImageView(Context context) {
		super(context);
		init();
	}

	//初始化矩阵 画笔
	private void init() {
		mSmoothMatrix = new Matrix();
		mPaint=new Paint();
		mPaint.setColor(mBgColor);
		mPaint.setStyle(Style.FILL);

	}

	/**
	 * 设置原始图片的信息
	 * @param width 宽
	 * @param height 高
	 * @param locationX X坐标
	 * @param locationY  Y坐标
	 */
	public void setOriginalInfo(int width, int height, int locationX, int locationY) {
		mOriginalWidth = width;
		mOriginalHeight = height;
		mOriginalLocationX = locationX;
		mOriginalLocationY = locationY;
	}


	/**
	 * 用于开始进入的方法。 调用此方前，需已经调用过setOriginalInfo
	 */
	public void transformIn() {
		mState = STATE_TRANSFORM_IN;
		mTransformStart = true;
		invalidate();//如果一个被其它窗口遮住的窗口变成了前台窗口，那么原来被遮住的部分就是无效的，需要重绘。
		//我的理解是当图片的activity跳到图片详情放大页里,那么之前的activity是无效的,这个新的activity需要重绘
	}

	/**
	 * 用于开始退出的方法。 调用此方前，需已经调用过setOriginalInfo
	 */
	public void transformOut() {
		mState = STATE_TRANSFORM_OUT;
		mTransformStart = true;
		invalidate();
	}


/**********************************************************************************************/
	private class Transfrom {
		float startScale;// 图片开始的缩放值
		float endScale;// 图片结束的缩放值
		float scale;// 属性ValueAnimator计算出来的值
		LocationSizeF startRect;// 开始的区域
		LocationSizeF endRect;// 结束的区域
		LocationSizeF rect;// 属性ValueAnimator计算出来的值

		void initStartIn() {
			scale = startScale;
			try {
				rect = (LocationSizeF) startRect.clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}

		void initStartOut() {
			scale = endScale;
			try {
				rect = (LocationSizeF) endRect.clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 初始化进入的变量信息
	 */
	private void initTransform() {

		if (mBitmap == null || mBitmap.isRecycled()) {
			mBitmap = ((BitmapDrawable) getDrawable()).getBitmap();
		}
		//防止mTransfrom重复的做同样的初始化
		if (mTransfrom != null) {
			return;
		}
		if (getWidth() == 0 || getHeight() == 0) {
			return;
		}
		mTransfrom = new Transfrom();

		/** 下面为缩放的计算 */
		/* 计算初始的缩放值，初始值因为是CENTR_CROP效果，所以要保证图片的宽和高至少1个能匹配原始的宽和高，另1个大于 */
		float xSScale = mOriginalWidth / ((float) mBitmap.getWidth());
		float ySScale = mOriginalHeight / ((float) mBitmap.getHeight());
		float startScale = xSScale > ySScale ? xSScale : ySScale;//取长边变化
		mTransfrom.startScale = startScale;
		/* 计算结束时候的缩放值，结束值因为要达到FIT_CENTER效果，所以要保证图片的宽和高至少1个能匹配原始的宽和高，另1个小于 */
		float xEScale = getWidth() / ((float) mBitmap.getWidth());
		float yEScale = getHeight() / ((float) mBitmap.getHeight());
		float endScale = xEScale < yEScale ? xEScale : yEScale;
		mTransfrom.endScale = endScale;

		/**
		 * 下面计算Canvas Clip的范围，也就是图片的显示的范围，因为图片是慢慢变大，并且是等比例的，所以这个效果还需要裁减图片显示的区域
		 * ，而显示区域的变化范围是在原始CENTER_CROP效果的范围区域
		 * ，到最终的FIT_CENTER的范围之间的，区域我用LocationSizeF更好计算
		 * ，他就包括左上顶点坐标，和宽高，最后转为Canvas裁减的Rect.
		 */
		/* 开始区域 */
		mTransfrom.startRect = new LocationSizeF();
		mTransfrom.startRect.left = mOriginalLocationX;
		mTransfrom.startRect.top = mOriginalLocationY;
		mTransfrom.startRect.width = mOriginalWidth;
		mTransfrom.startRect.height = mOriginalHeight;
		/* 结束区域 */
		mTransfrom.endRect = new LocationSizeF();
		float bitmapEndWidth = mBitmap.getWidth() * mTransfrom.endScale;// 图片最终的宽度
		float bitmapEndHeight = mBitmap.getHeight() * mTransfrom.endScale;// 图片最终的高度
		mTransfrom.endRect.left = (getWidth() - bitmapEndWidth) / 2;
		mTransfrom.endRect.top = (getHeight() - bitmapEndHeight) / 2;
		mTransfrom.endRect.width = bitmapEndWidth;
		mTransfrom.endRect.height = bitmapEndHeight;

		mTransfrom.rect = new LocationSizeF();
	}
    /********************************************************************************************/
	private class LocationSizeF implements Cloneable{
		float left;
		float top;
		float width;
		float height;
		@Override
		public String toString() {
			return "[left:"+left+" top:"+top+" width:"+width+" height:"+height+"]";
		}

		@Override
		public Object clone() throws CloneNotSupportedException {
			// TODO Auto-generated method stub
			return super.clone();
		}

	}
	/*******************************************************************************************/
    //对图片进行操作
	private void getBmpMatrix() {
		if (getDrawable() == null) {
			return;
		}
		if (mTransfrom == null) {
			return;
		}
		if (mBitmap == null || mBitmap.isRecycled()) {
			mBitmap = ((BitmapDrawable) getDrawable()).getBitmap();
		}
		/* 下面实现了CENTER_CROP的功能 */
		mSmoothMatrix.setScale(mTransfrom.scale, mTransfrom.scale);
		mSmoothMatrix.postTranslate(-(mTransfrom.scale * mBitmap.getWidth() / 2 - mTransfrom.rect.width / 2),
				-(mTransfrom.scale * mBitmap.getHeight() / 2 - mTransfrom.rect.height / 2));
	}

	/**
	 * 对图片动画绘制
	 * @param canvas 画布
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		if (getDrawable() == null) {
			return; // couldn't resolve the URI
		}

		if (mState == STATE_TRANSFORM_IN || mState == STATE_TRANSFORM_OUT) {
			if (mTransformStart) {
				initTransform();
			}
			if (mTransfrom == null) {
				super.onDraw(canvas);
				return;
			}

			if (mTransformStart) {
				if (mState == STATE_TRANSFORM_IN) {
					mTransfrom.initStartIn();
				} else {
					mTransfrom.initStartOut();
				}
			}
			mPaint.setAlpha(mBgAlpha);
			canvas.drawPaint(mPaint);

			int saveCount = canvas.getSaveCount();
			canvas.save();
			// 先得到图片在此刻的图像Matrix矩阵
			getBmpMatrix();
			canvas.translate(mTransfrom.rect.left, mTransfrom.rect.top);
			canvas.clipRect(0, 0, mTransfrom.rect.width, mTransfrom.rect.height);
			canvas.concat(mSmoothMatrix);
			getDrawable().draw(canvas);
			canvas.restoreToCount(saveCount);
			if (mTransformStart) {
				mTransformStart=false;
				startTransform(mState);
			}
		} else {
			//当Transform In变化完成后，把背景改为黑色，使得Activity不透明
			mPaint.setAlpha(255);
			canvas.drawPaint(mPaint);
			super.onDraw(canvas);
		}
	}

	/**
	 *
	 * @param state 图片的状态:是进入还是返回
	 */
	private void startTransform(final int state) {
		if (mTransfrom == null) {
			return;
		}
		ValueAnimator valueAnimator = new ValueAnimator();
		valueAnimator.setDuration(300);
		valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
		if (state == STATE_TRANSFORM_IN) {
			PropertyValuesHolder scaleHolder = PropertyValuesHolder.ofFloat("scale", mTransfrom.startScale, mTransfrom.endScale);
			PropertyValuesHolder leftHolder = PropertyValuesHolder.ofFloat("left", mTransfrom.startRect.left, mTransfrom.endRect.left);
			PropertyValuesHolder topHolder = PropertyValuesHolder.ofFloat("top", mTransfrom.startRect.top, mTransfrom.endRect.top);
			PropertyValuesHolder widthHolder = PropertyValuesHolder.ofFloat("width", mTransfrom.startRect.width, mTransfrom.endRect.width);
			PropertyValuesHolder heightHolder = PropertyValuesHolder.ofFloat("height", mTransfrom.startRect.height, mTransfrom.endRect.height);
			PropertyValuesHolder alphaHolder = PropertyValuesHolder.ofInt("alpha", 0, 255);
			valueAnimator.setValues(scaleHolder, leftHolder, topHolder, widthHolder, heightHolder, alphaHolder);
		} else {
			PropertyValuesHolder scaleHolder = PropertyValuesHolder.ofFloat("scale", mTransfrom.endScale, mTransfrom.startScale);
			PropertyValuesHolder leftHolder = PropertyValuesHolder.ofFloat("left", mTransfrom.endRect.left, mTransfrom.startRect.left);
			PropertyValuesHolder topHolder = PropertyValuesHolder.ofFloat("top", mTransfrom.endRect.top, mTransfrom.startRect.top);
			PropertyValuesHolder widthHolder = PropertyValuesHolder.ofFloat("width", mTransfrom.endRect.width, mTransfrom.startRect.width);
			PropertyValuesHolder heightHolder = PropertyValuesHolder.ofFloat("height", mTransfrom.endRect.height, mTransfrom.startRect.height);
			PropertyValuesHolder alphaHolder = PropertyValuesHolder.ofInt("alpha", 255, 0);
			valueAnimator.setValues(scaleHolder, leftHolder, topHolder, widthHolder, heightHolder, alphaHolder);
		}
        //ValueAnimator的AnimatorUpdateListener可以得到动画执行每一帧所返回的值,
		valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public synchronized void onAnimationUpdate(ValueAnimator animation) {
				mTransfrom.scale = (Float) animation.getAnimatedValue("scale");
				mTransfrom.rect.left = (Float) animation.getAnimatedValue("left");
				mTransfrom.rect.top = (Float) animation.getAnimatedValue("top");
				mTransfrom.rect.width = (Float) animation.getAnimatedValue("width");
				mTransfrom.rect.height = (Float) animation.getAnimatedValue("height");
				mBgAlpha = (Integer) animation.getAnimatedValue("alpha");
				invalidate();
				((Activity)getContext()).getWindow().getDecorView().invalidate();
			}
		});
		valueAnimator.addListener(new ValueAnimator.AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animation) {

			}

			@Override
			public void onAnimationRepeat(Animator animation) {

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				/*
				 * 如果是进入的话，当然是希望最后停留在center_crop的区域。但是如果是out的话，就不应该是center_crop的位置了
				 * ， 而应该是最后变化的位置，因为当out的时候结束时，不回复视图是Normal，要不然会有一个突然闪动回去的bug
				 */
				if (state == STATE_TRANSFORM_IN) {
					mState = STATE_NORMAL;
				}
				if (mTransformListener != null) {
					mTransformListener.onTransformComplete(state);
				}
			}

			@Override
			public void onAnimationCancel(Animator animation) {

			}
		});
		valueAnimator.start();
	}

	public void setOnTransformListener(TransformListener listener) {
		mTransformListener = listener;
	}

	private TransformListener mTransformListener;

	public interface TransformListener {
		/**
		 * @param mode
		 * STATE_TRANSFORM_IN 1 ,STATE_TRANSFORM_OUT 2
		 */
		void onTransformComplete(int mode);// mode 1
	}


}
