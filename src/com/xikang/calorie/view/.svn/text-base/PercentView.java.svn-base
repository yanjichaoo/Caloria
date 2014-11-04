/* ==================================================
 * 产品名: 卡路里消耗管理
 * 文件名: PercentView.java
 * --------------------------------------------------
 * 开发环境: JDK1.6 
 * --------------------------------------------------
 * 修订履历  YYYY/MM/DD  REV.  备注
 *         2011/08/15  1.00  初版发行
 * --------------------------------------------------
 * (C) Copyright XIKANG CORPORATION 2011 All Rights Reserved.
 */
package com.xikang.calorie.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

import com.xikang.calorie.activity.R;

/**
 * 
 * 自定义进度表控件
 * 
 * 
 * <pre>
 * 
 * 2011/08/15, 张荣
 * 
 * 修订履历：YYYY/MM/DD 修订者  修订内容
 * 
 * </pre>
 * 
 * @author 张荣
 * @version 1.00
 */
public class PercentView extends View {
	private Paint mPaint;
	private Paint mNeedlePaint;
	private Paint mProgPaint;
	private RectF mProgRect1;
	private RectF mProgRect2;

	private Resources mRes;
	private Bitmap mClock;
	private Bitmap mCenter;
	private BitmapDrawable mBmpDrawabe;

	private int clockWidth;
	private int clockHeight;
	private int centerWidth;
	private int centerHeight;

	private int titleTextSize;
	private int calorieTextSize;
	private int textColor;
	private int titleWidth;
	private int calorieWidth;
	private int unitWidth;

	private String title;
	private String calorie;
	private float sweepAngle;
	private float sweepTempAngle = 0;

	public float getSweepTempAngle() {
		return sweepTempAngle;
	}

	public void setSweepTempAngle(float sweepTempAngle) {
		this.sweepTempAngle = sweepTempAngle;
	}

	private float mDeltaAngle;

	public float getDeltaAngle() {
		return mDeltaAngle;
	}

	public void setDeltaAngle(float deltaAngle) {
		this.mDeltaAngle = deltaAngle;
	}

	public String getCalorie() {
		return calorie;
	}

	public void setCalorie(String calorie) {
		this.calorie = calorie;
		mPaint.setTextSize(calorieTextSize);
		calorieWidth = (int) mPaint.measureText(calorie);
	}

	private String calorieTarget;

	public String getCalorieTarget() {
		return calorieTarget;
	}

	public void setCalorieTarget(String calorieTarget) {
		this.calorieTarget = calorieTarget;
	}

	private String unit;

	private RectF rect = new RectF();

	public PercentView(Context context) {
		super(context);

	}

	public PercentView(Context context, AttributeSet attrs) {
		super(context, attrs);

		mRes = this.getResources();
		mPaint = new Paint();
		mNeedlePaint = new Paint();
		mProgPaint = new Paint();

		mProgPaint.setAntiAlias(true);
		mNeedlePaint.setStrokeWidth(4);
		mNeedlePaint.setAntiAlias(true);
		mNeedlePaint.setColor(0xFF54595F);

		mBmpDrawabe = (BitmapDrawable) mRes.getDrawable(R.drawable.clock);
		mClock = mBmpDrawabe.getBitmap();
		mBmpDrawabe = (BitmapDrawable) mRes.getDrawable(R.drawable.center);
		mCenter = mBmpDrawabe.getBitmap();
		clockWidth = mClock.getWidth();
		clockHeight = mClock.getHeight();
		centerWidth = mCenter.getWidth();
		centerHeight = mCenter.getHeight();

		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.PercentView);
		textColor = array.getColor(R.styleable.PercentView_textColor, 0XFF000000);
		titleTextSize = (int) array.getDimension(R.styleable.PercentView_titleTextSize, 20);
		calorieTextSize = (int) array.getDimension(R.styleable.PercentView_calorieTextSize, 26);

		title = array.getString(R.styleable.PercentView_title);
		calorie = array.getString(R.styleable.PercentView_calorie);
		unit = array.getString(R.styleable.PercentView_unit);
		mPaint.setColor(textColor);
		mPaint.setTextSize(titleTextSize);

		titleWidth = (int) mPaint.measureText(title);
		unitWidth = (int) mPaint.measureText(unit);

		array.recycle();
	}

	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		rect.set(0, 0, mClock.getWidth(), mClock.getHeight());
		canvas.drawBitmap(mClock, 0, 0, mPaint);
		canvas.drawBitmap(mCenter, (clockWidth - centerWidth) / 2, (clockHeight - centerHeight) / 2, mPaint);

		mPaint.setTextSize(titleTextSize);
		canvas.drawText(title, (clockWidth - titleWidth) / 2, clockHeight / 2 + centerHeight + 30, mPaint);

		mPaint.setTextSize(calorieTextSize);
		canvas.drawText(calorie, (clockWidth - calorieWidth - unitWidth) / 2, clockHeight / 2 + centerHeight + 80,
				mPaint);
		mPaint.setTextSize(titleTextSize);
		canvas.drawText(unit, (clockWidth - calorieWidth - unitWidth) / 2 + calorieWidth, clockHeight / 2
				+ centerHeight + 80, mPaint);

		mProgPaint.setColor(0xFF59AF16);

		mProgRect1 = new RectF(20, 20, mClock.getWidth() - 20, mClock.getHeight() - 20);
		mProgRect2 = new RectF(60, 60, mClock.getWidth() - 60, mClock.getHeight() - 60);

		// 计算比例角度
		sweepAngle = (float) ((180.0 + 2 * mDeltaAngle) * Float.parseFloat(calorie)) / Float.parseFloat(calorieTarget);

		// 消耗卡路里
		Path p1 = new Path();
		p1.moveTo((float) (clockWidth / 2.0 - (clockWidth - 150.0) / 2.0 * Math.cos(Math.toRadians(mDeltaAngle))),
				(float) (clockHeight / 2.0 + (clockHeight - 150.0) / 2.0 * Math.sin(Math.toRadians(mDeltaAngle))));
		p1.lineTo((float) (clockWidth / 2.0 - (clockWidth - 20.0) / 2.0 * Math.cos(Math.toRadians(mDeltaAngle))),
				(float) (clockHeight / 2.0 + (clockHeight - 20.0) / 2.0 * Math.sin(Math.toRadians(mDeltaAngle))));
		p1.arcTo(mProgRect1, 180 - mDeltaAngle, sweepTempAngle);
		p1.lineTo(
				(float) (clockWidth / 2.0 - (clockWidth - 150.0) / 2.0
						* Math.cos(Math.toRadians(sweepTempAngle - mDeltaAngle))),
				(float) (clockHeight / 2.0 - (clockHeight - 150.0) / 2.0
						* Math.sin(Math.toRadians(sweepTempAngle - mDeltaAngle))));
		p1.arcTo(mProgRect2, 180 + sweepTempAngle - mDeltaAngle, -sweepTempAngle);
		canvas.drawPath(p1, mProgPaint);

		// 目标卡路里 - 消耗卡路里
		mProgPaint.setColor(0xFFE65F05);
		Path p2 = new Path();
		p2.moveTo(
				(float) (clockWidth / 2.0 - ((clockWidth - 150.0) / 2.0 * Math.cos(Math.toRadians(sweepTempAngle
						- mDeltaAngle)))),
				(float) (clockHeight / 2.0 - ((clockHeight - 150.0) / 2.0 * Math.sin(Math.toRadians(sweepTempAngle
						- mDeltaAngle)))));
		p2.lineTo(
				(float) (clockWidth / 2.0 - ((clockWidth - 20.0) / 2.0 * Math.cos(Math.toRadians(sweepTempAngle
						- mDeltaAngle)))),
				(float) (clockHeight / 2.0 - ((clockHeight - 20.0) / 2.0 * Math.sin(Math.toRadians(sweepTempAngle
						- mDeltaAngle)))));
		p2.arcTo(mProgRect1, 180 + sweepTempAngle - mDeltaAngle, 180 + 2 * mDeltaAngle - sweepTempAngle);
		p2.lineTo((float) (clockWidth / 2.0 + ((clockWidth - 150.0) / 2.0 * Math.cos(Math.toRadians(mDeltaAngle)))),
				(float) (clockHeight / 2.0 + ((clockHeight - 150.0) / 2.0 * Math.sin(Math.toRadians(mDeltaAngle)))));
		p2.arcTo(mProgRect2, mDeltaAngle, -(180 + 2 * mDeltaAngle - sweepTempAngle));
		canvas.drawPath(p2, mProgPaint);

		// 指针绘制
		canvas.drawLine(
				(float) (clockWidth / 2.0 - (centerHeight / 2.0 - 4.0)
						* Math.cos(Math.toRadians(sweepTempAngle - mDeltaAngle))),
				(float) (clockHeight / 2.0 - (centerHeight / 2.0 - 4.0)
						* Math.sin(Math.toRadians(sweepTempAngle - mDeltaAngle))),
				(float) (clockWidth / 2.0 - (clockWidth - 20.0) / 2.0
						* Math.cos(Math.toRadians(sweepTempAngle - mDeltaAngle))),
				(float) (clockHeight / 2.0 - (clockWidth - 20.0) / 2.0
						* Math.sin(Math.toRadians(sweepTempAngle - mDeltaAngle))), mNeedlePaint);

		if (sweepTempAngle <= sweepAngle) {
			sweepTempAngle++;
			invalidate();
		}
	}

}
