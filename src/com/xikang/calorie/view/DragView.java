package com.xikang.calorie.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.xikang.calorie.activity.R;
import com.xikang.calorie.common.Util;

public class DragView extends View {

	private double xRate;
	private double yRate;
	private double zRate;

	public void setRate(double xRate, double yRate, double zRate) {
		this.xRate = xRate;
		this.yRate = yRate;
		this.zRate = zRate;
		makeLine();
		invalidate();
	}

	private int dragHeight;
	private int dragWidth;

	private Bitmap bmpSeekLeft;
	private Bitmap bmpSeekRight;

	private int leftSeekWidth;
	private int leftSeekHeight;
	private int rightSeekWidth;
	private int rightSeekHeight;

	private int topBaseline;
	private int leftBaseline;
	private int rightBaseline;

	private RectF xRect = new RectF();
	private RectF yRect = new RectF();
	private RectF zRect = new RectF();
	private RectF allRect = new RectF();
	private RectF leftSeekRect = new RectF();
	private RectF rightSeekRect = new RectF();

	private Paint drawPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private Paint dragPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

	private int zoomInGesture = 15;

	private boolean isSelectLeft = false;
	private boolean isSelectRight = false;

	private int viewWidth;
	private int viewHeight;

	private Context mContext;

	private OnDragChangeListener onDragChangeListener;

	public void setOnDragChangeListener(OnDragChangeListener onDragChangeListener) {
		this.onDragChangeListener = onDragChangeListener;
	}

	public DragView(Context context) {
		super(context, null);
	}

	public DragView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		initValue(attrs);
	}

	private void initValue(AttributeSet attrs) {

		TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.DragView);

		dragHeight = (int) array.getDimension(R.styleable.DragView_dragHeight, 440);
		dragWidth = (int) array.getDimension(R.styleable.DragView_dragWidth, 55);

		bmpSeekLeft = BitmapFactory.decodeResource(getResources(), R.drawable.drag_seek_left);
		bmpSeekRight = BitmapFactory.decodeResource(getResources(), R.drawable.drag_seek_right);

		leftSeekWidth = bmpSeekLeft.getWidth();
		leftSeekHeight = bmpSeekLeft.getHeight();

		rightSeekWidth = bmpSeekRight.getWidth();
		rightSeekHeight = bmpSeekRight.getHeight();

		viewWidth = dragWidth + leftSeekWidth + rightSeekWidth;
		viewHeight = dragHeight + leftSeekHeight / 2 + rightSeekHeight / 2;

		topBaseline = (int) rightSeekHeight / 2; // 上限

		makeLine();
	}

	private void makeLine() {
		rightBaseline = (int) (dragHeight * xRate); // 右上
		leftBaseline = (int) (dragHeight * yRate) + rightBaseline; // 左下
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		canvas.translate(leftSeekWidth, topBaseline);
		drawDrag(canvas);

		canvas.translate(-leftSeekWidth, 0);
		drawSeek(canvas);
	}

	private void drawDrag(Canvas canvas) {

		allRect.set(0, 0, dragWidth, dragHeight);
		dragPaint.setColor(0xFFFFFFFF);
		canvas.drawRect(allRect, dragPaint);
		
		xRect.set(allRect.left, allRect.top, allRect.right, allRect.top + rightBaseline);
		dragPaint.setColor(0xFFBDDDC5);
		canvas.drawRect(xRect, dragPaint);

		yRect.set(allRect.left, xRect.bottom, allRect.right, allRect.top + leftBaseline);
		dragPaint.setColor(0xFFDEFF1A);
		canvas.drawRect(yRect, dragPaint);

		zRect.set(allRect.left, yRect.bottom, allRect.right, allRect.bottom);
		dragPaint.setColor(0xFF00ADCF);
		canvas.drawRect(zRect, dragPaint);

	}

	private void drawSeek(Canvas canvas) {

		int leftTop = leftBaseline - (int) (leftSeekHeight / 2);
		int leftBottom = leftSeekHeight + leftTop;
		leftSeekRect.set(0, leftTop, leftSeekWidth, leftBottom);
		canvas.drawBitmap(bmpSeekLeft, leftSeekRect.left, leftSeekRect.top, drawPaint);

		int rightTop = rightBaseline - (int) (rightSeekHeight / 2);
		int rightBottom = rightSeekHeight + rightTop;
		rightSeekRect.set(leftSeekRect.right + dragWidth, rightTop, leftSeekRect.right + dragWidth + rightSeekWidth, rightBottom);
		canvas.drawBitmap(bmpSeekRight, rightSeekRect.left, rightSeekRect.top, drawPaint);

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

		int width, height;

		if (widthSpecMode == MeasureSpec.EXACTLY) {
			width = widthSpecSize;
		} else {
			width = viewWidth;
			if (widthSpecMode == MeasureSpec.AT_MOST) {
				width = Math.min(width, widthSpecSize);
			}
		}

		if (heightSpecMode == MeasureSpec.EXACTLY) {
			height = heightSpecSize;
		} else {
			height = viewHeight;
			if (heightSpecMode == MeasureSpec.AT_MOST) {
				height = Math.min(height, heightSpecSize);
			}
		}

		setMeasuredDimension(width, height);
	}

	private double calculatRatio(float y) {
		double dividend = y;
		double result = dividend / dragHeight;
		return Util.cutNumber(result, 2);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (x >= leftSeekRect.left - zoomInGesture && x <= leftSeekRect.right + zoomInGesture
					&& y >= leftSeekRect.top - zoomInGesture && y <= leftSeekRect.bottom + zoomInGesture) {
				isSelectLeft = true;
			}
			if (x >= rightSeekRect.left - zoomInGesture && x <= rightSeekRect.right + zoomInGesture
					&& y >= rightSeekRect.top - zoomInGesture && y <= rightSeekRect.bottom + zoomInGesture) {
				isSelectRight = true;
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (isSelectLeft) {
				if (x >= leftSeekRect.left - zoomInGesture && x <= leftSeekRect.right + zoomInGesture
						&& y >= rightBaseline - zoomInGesture && y <= dragHeight + zoomInGesture) {
					if (y < rightBaseline) {
						y = rightBaseline;
					}
					if (y > dragHeight) {
						y = dragHeight;
					}
					zRate = Util.cutNumber(Math.abs(calculatRatio(y) - 1), 2);
					yRate = Util.cutNumber(1 - xRate - zRate, 2);
					if (onDragChangeListener != null) {
						onDragChangeListener.onChangeDragLeft(xRate, yRate, zRate);
					}
					leftBaseline = (int) y;
				}
			}
			if (isSelectRight) {
				if (x >= rightSeekRect.left - zoomInGesture && x <= rightSeekRect.right + zoomInGesture && y >= 0 - zoomInGesture
						&& y <= leftBaseline + zoomInGesture) {
					if (y < 0) {
						y = 0;
					}
					if (y > leftBaseline) {
						y = leftBaseline;
					}
					xRate = calculatRatio(y);
					yRate = Util.cutNumber(1 - xRate - zRate, 2);
					if (onDragChangeListener != null) {
						onDragChangeListener.onChangeDragRight(xRate, yRate, zRate);
					}
					rightBaseline = (int) y;
				}
			}
			invalidate();
			break;
		case MotionEvent.ACTION_UP:
			if (isSelectLeft) {
				isSelectLeft = false;
			}
			if (isSelectRight) {
				isSelectRight = false;
			}
			break;
		}
		return true;
	}

	public interface OnDragChangeListener {
		void onChangeDragLeft(double xRate, double yRate, double zRate);

		void onChangeDragRight(double xRate, double yRate, double zRate);
	}
}
