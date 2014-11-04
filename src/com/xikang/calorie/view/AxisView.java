package com.xikang.calorie.view;

import java.text.DecimalFormat;
import java.text.NumberFormat;

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

/**
 * 
 * 自定义X,Y轴控件
 * 
 * 
 * <pre>
 * 
 * 2011/07/05, 曲磊
 * 
 * 修订履历：YYYY/MM/DD 修订者  修订内容
 * 
 * </pre>
 * 
 * @author 曲磊
 * @version 1.00
 */
public class AxisView extends View {

	/**
	 * 计算参数
	 */
	private int viewWidth; // 控件宽度
	private int viewHeight; // 控件高度

	private int ySeekWidth; // y轴滑块宽度
	private int ySeekHeight; // y轴滑块高度
	private int xSeekWidth; // x轴滑块宽度
	private int xSeekHeight; // x轴滑块高度

	private int yAxisWidth; // y轴宽度
	private int yAxisHeight; // y轴高度
	private int xAxisWidth; // x轴宽度
	private int xAxisHeight; // x轴高度

	private int ySeekOver; // y轴滑块溢出部分
	private int xSeekOver; // x轴滑块溢出部分

	private int yAxisLength; // y轴长度
	private int xAxisLength; // x轴长度

	// Y轴滑块矩形坐标
	private int ySeekLeft;
	private int ySeekRight;
	private int ySeekTop;
	private int ySeekButtom;

	// x轴滑块矩形坐标
	private int xSeekLeft;
	private int xSeekRight;
	private int xSeekTop;
	private int xSeekButtom;

	/**
	 * 设置的值
	 */

	private int paddingLeftTextY = 5; // SeekY文字距图片左边距
	private int paddingTopTextX = 5; // SeekX文字距图片顶边距

	private String ySeekBarText = ""; // y轴滑块文字
	private String yCurrentText = ""; // y轴滑块数值
	private String xSeekbarText = ""; // x轴滑块文字
	private String xCurrentText = ""; // x轴滑块数值

	private int seekTextSize = 0; // 滑块字体大小
	private int currentTextSize = 0; // 滑块数值大小
	private int ponitTextSize = 0; // 轴点字体大小

	private Paint seekTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG); // 滑块字体画笔
	private Paint ponitTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG); // 滑块数值画笔
	private Paint currentTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG); // 轴字体画笔
	private Paint linePaint = new Paint(Paint.ANTI_ALIAS_FLAG); // 线画笔
	private Paint drawPaint = new Paint(Paint.ANTI_ALIAS_FLAG); // 图画笔

	private RectF ySeekRect = new RectF(); // y轴滑块矩形
	private RectF xSeekRect = new RectF(); // x轴滑块矩形

	private Bitmap xAxisBitmap; // X轴图片
	private Bitmap yAxisBitmap; // Y轴图片

	private Bitmap xSeekBitmap; // X轴拖动图片

	public Bitmap getxSeekBitmap() {
		return xSeekBitmap;
	}

	public void setxSeekBitmap(Bitmap xSeekBitmap) {
		this.xSeekBitmap = xSeekBitmap;
		this.invalidate();
	}

	private Bitmap ySeekBitmap; // Y轴拖动图片

	public Bitmap getySeekBitmap() {
		return ySeekBitmap;
	}

	public void setySeekBitmap(Bitmap ySeekBitmap) {
		this.ySeekBitmap = ySeekBitmap;
		this.invalidate();
	}

	private int pointLineLength = 15; // 轴点长度
	private int pointLineTilt = 15; // 轴点偏移

	private boolean isSelectY = false;
	private boolean isSelectX = false;

	private int yDValue = 80;
	private int xDValue = 10;

	private int yMaxPoint = 200 - yDValue;
	private int xMaxPoint = 150 - xDValue;

	private double yStartPoint = 25;
	private double xStartPoint = 30;

	private int[] yPoints = new int[] { 100 - yDValue, 125 - yDValue, 150 - yDValue, 175 - yDValue, 200 - yDValue };
	private int[] xPoints = new int[] { 30 - xDValue, 60 - xDValue, 90 - xDValue, 120 - xDValue, 150 - xDValue };

	private int zoomInGesture = 15;

	private OnAxisChangeListener mOnAxisChangeListener;

	public void setOnAxisChangeListener(OnAxisChangeListener mOnAxisChangeListener) {
		this.mOnAxisChangeListener = mOnAxisChangeListener;
	}

	public double getyStartPoint() {
		return yStartPoint;
	}

	public void setyStartPoint(int yStartPoint) {
		this.yStartPoint = yStartPoint;
		ySeekTop = 0 - ySeekOver - getPixels(yAxisLength, yMaxPoint, yStartPoint - yDValue);
		ySeekButtom = ySeekHeight + ySeekTop;
		this.invalidate();
	}

	public double getxStartPoint() {
		return xStartPoint;
	}

	public void setxStartPoint(double xStartPoint) {
		this.xStartPoint = xStartPoint;
		xSeekLeft = 0 - xSeekOver + getPixels(xAxisLength, xMaxPoint, xStartPoint - xDValue);
		xSeekRight = xSeekWidth + xSeekLeft;
		this.invalidate();
	}

	public AxisView(Context context) {
		this(context, null);
	}

	public AxisView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initValue(context, attrs);
	}

	private void initValue(Context context, AttributeSet attrs) {

		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.AxisView);
		// 轴图片
		xAxisBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.main_bmi_x_axis);
		yAxisBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.main_bmi_y_axis);
		// 滑块图片
		xSeekBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.main_bmi_x_seek_bg);
		ySeekBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.main_bmi_y_seek_bg);
		// 轴宽高
		yAxisWidth = yAxisBitmap.getWidth();
		yAxisHeight = yAxisBitmap.getHeight();
		xAxisWidth = xAxisBitmap.getWidth();
		xAxisHeight = xAxisBitmap.getHeight();

		// 字体
		seekTextSize = (int) array.getDimension(R.styleable.AxisView_seekTextSize, 15);
		currentTextSize = (int) array.getDimension(R.styleable.AxisView_currentTextSize, 15);
		ponitTextSize = (int) array.getDimension(R.styleable.AxisView_ponitTextSize, 15);

		// 边距
		paddingLeftTextY = (int) array.getDimension(R.styleable.AxisView_paddingLeftTextY, 5);
		paddingTopTextX = (int) array.getDimension(R.styleable.AxisView_paddingTopTextX, 5);

		// yCurrentText = String.valueOf(changePoint(yStartPoint + yDValue));
		// xCurrentText = String.valueOf(xStartPoint + xDValue);

		// xSeekbarText = getResources().getString(R.string.profile_weight);
		// ySeekBarText = getResources().getString(R.string.profile_height);

		seekTextPaint.setTextSize(seekTextSize);
		seekTextPaint.setColor(0xFFcccccc);
		currentTextPaint.setTextSize(currentTextSize);
		currentTextPaint.setColor(0xFF99cc00);
		ponitTextPaint.setTextSize(ponitTextSize);
		ponitTextPaint.setColor(0xFF979797);
		linePaint.setColor(0xFF424242);

		// 滑块宽高
		ySeekWidth = ySeekBitmap.getWidth() + getTextWith(currentTextPaint, yCurrentText);
		ySeekHeight = Math.max(ySeekBitmap.getHeight(),
				Math.max(getTextHeight(currentTextPaint), getTextHeight(seekTextPaint)));
		xSeekWidth = xSeekBitmap.getWidth() + getTextWith(currentTextPaint, "000.0");
		xSeekHeight = Math.max(xSeekBitmap.getHeight(),
				Math.max(getTextHeight(currentTextPaint), getTextHeight(seekTextPaint)));

		// 显示宽高
		viewWidth = ySeekWidth + xAxisWidth;
		viewHeight = xSeekHeight + yAxisHeight + xAxisHeight + 20;

		ySeekOver = ySeekHeight / 2;
		xSeekOver = xSeekWidth - xSeekBitmap.getWidth() / 2;

		// 轴长度
		yAxisLength = (int) (yAxisHeight * 0.9);
		xAxisLength = (int) (xAxisWidth * 0.8);

		ySeekTop = 0 - ySeekOver - getPixels(yAxisLength, yMaxPoint, yStartPoint);
		ySeekLeft = 0 - ySeekWidth;
		ySeekRight = ySeekWidth + ySeekLeft;
		ySeekButtom = ySeekHeight + ySeekTop;

		xSeekTop = 0 - xAxisHeight / 2;
		xSeekLeft = 0 - xSeekOver + getPixels(xAxisLength, xMaxPoint, xStartPoint);
		xSeekRight = xSeekWidth + xSeekLeft;
		xSeekButtom = xSeekHeight + xSeekTop;

		array.recycle();
	}

	// 文字宽度
	public int getTextWith(Paint pt, String str) {
		return (int) pt.measureText(str);
	}

	// 文字高度
	public int getTextHeight(Paint pt) {
		return (int) (-pt.ascent() + pt.descent());
	}

	// 文字纵向居中
	public int getTextCenterY(float height, float bottom, Paint pt) {
		int iTextPosY = (int) bottom + (int) (-pt.ascent()) - getTextHeight(pt);
		return iTextPosY -= ((int) height >> 1) - (getTextHeight(pt) >> 1);
	}

	// 文字横向居中
	public int getTextCenterX(float width, float right, float textWidth) {
		int iTextPosX = (int) right - (int) textWidth;
		return iTextPosX -= ((int) width >> 1) - ((int) textWidth >> 1);
	}

	// 滑块图片居中
	public int getCenterY(RectF rectF, int height) {
		return (int) ((rectF.bottom - rectF.top - height) / 2 + rectF.top);
	}

	// 像素坐标换算
	public int getPixels(int length, int maxPoint, double point) {
		Double ratio = new Double((double) length / (double) maxPoint);
		int result = (int) (point * ratio);
		return result;
	}

	// 坐标像素换算
	public double getPos(int length, int maxPoint, int Pos) {
		Double ratio = new Double((double) maxPoint / (double) length);
		double result = Util.cutNumber(Pos * ratio, 1);
		return result;
	}

	// 数字小数
	public String changePoint(double number) {
		NumberFormat formatter = new DecimalFormat("0.00");
		Double result = new Double((double) number / (double) 100);
		return formatter.format(result);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

		// 定义最终确定的宽度和高度
		int width, height;

		// 如果在xml文件中是明确定义视图大小，那么就使用明确定义的值
		if (widthSpecMode == MeasureSpec.EXACTLY) {
			width = widthSpecSize;
		} else {
			// 否则测量需要显示的内容所占的宽度(就是分配视图占用的宽度)
			width = viewWidth;

			// 如果有定义一个最大宽度， 那么视图分配的宽度不得超过最大宽度
			if (widthSpecMode == MeasureSpec.AT_MOST) {
				width = Math.min(width, widthSpecSize);
			}
		}

		// 下面测量高度的方式跟宽度差不多
		if (heightSpecMode == MeasureSpec.EXACTLY) {
			height = heightSpecSize;
		} else {
			height = viewHeight;

			if (heightSpecMode == MeasureSpec.AT_MOST) {
				height = Math.min(height, heightSpecSize);
			}
		}

		// 这个一定要调用， 告诉系统测量的最终结果 需要的宽度是width 高度是height
		setMeasuredDimension(width, height);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		drawAxis(canvas);
		canvas.translate(ySeekWidth, yAxisHeight + xAxisHeight);

		drawPoints(canvas);
		drawSeek(canvas);
	}

	private void drawAxis(Canvas canvas) {
		canvas.drawBitmap(yAxisBitmap, ySeekWidth, 0, drawPaint);
		canvas.drawBitmap(xAxisBitmap, ySeekWidth, yAxisHeight, drawPaint);
	}

	private void drawPoints(Canvas canvas) {
		// 绘制Y轴点
		int yCount = yPoints.length;
		for (int i = 0; i < yCount; i++) {
			int y = getPixels(yAxisLength, yMaxPoint, yPoints[i]);
			canvas.drawLine(yAxisWidth, -y, yAxisWidth + pointLineLength, -y - pointLineTilt, linePaint);
			canvas.drawText(changePoint(yPoints[i] + yDValue), yAxisWidth + pointLineLength, -y - pointLineTilt,
					ponitTextPaint);
		}
		// 绘制X轴点
		int xCount = xPoints.length;
		for (int i = 0; i < xCount; i++) {
			int x = getPixels(xAxisLength, xMaxPoint, xPoints[i]);
			canvas.drawLine(x, -xAxisHeight, x + pointLineTilt, -xAxisHeight - pointLineLength, linePaint);
			canvas.drawText(String.valueOf(xPoints[i] + xDValue), x + pointLineTilt, -xAxisHeight - pointLineLength,
					ponitTextPaint);
		}

	}

	private void drawSeek(Canvas canvas) {
		ySeekRect.set((float) ySeekLeft, (float) ySeekTop, (float) ySeekRight, (float) ySeekButtom);
		// y轴画当前数值
		canvas.drawText(yCurrentText, ySeekRect.left,
				getTextCenterY(ySeekRect.height(), ySeekRect.bottom, currentTextPaint), currentTextPaint);
		// y轴画图片
		canvas.drawBitmap(ySeekBitmap, ySeekRect.left + getTextWith(currentTextPaint, yCurrentText),
				getCenterY(ySeekRect, ySeekBitmap.getHeight()), drawPaint);
		// y轴画图片文字
		canvas.drawText(ySeekBarText, ySeekRect.left + getTextWith(currentTextPaint, yCurrentText) + paddingLeftTextY,
				getTextCenterY(ySeekRect.height(), ySeekRect.bottom, seekTextPaint), seekTextPaint);

		xSeekRect.set((float) xSeekLeft, (float) xSeekTop, (float) xSeekRight, (float) xSeekButtom);
		// x轴画当前数值
		canvas.drawText(xCurrentText, xSeekRect.left,
				getTextCenterY(xSeekRect.height(), xSeekRect.bottom, currentTextPaint), currentTextPaint);
		// x轴画图片
		canvas.drawBitmap(xSeekBitmap, xSeekRect.left + getTextWith(currentTextPaint, "000.0"),
				getCenterY(xSeekRect, xSeekBitmap.getHeight()), drawPaint);
		// x轴画图片文字
		float xAxisSeekTextLeft = getTextCenterX(xSeekWidth - getTextWith(seekTextPaint, "000.0"), xSeekRect.right,
				getTextWith(seekTextPaint, xSeekbarText));
		canvas.drawText(xSeekbarText, xAxisSeekTextLeft, getTextHeight(currentTextPaint) + paddingTopTextX,
				seekTextPaint);

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX() - ySeekWidth;
		float y = event.getY() - yAxisHeight - xAxisHeight;
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (x >= ySeekRect.left - zoomInGesture && x <= ySeekRect.right + zoomInGesture
					&& y >= ySeekRect.top - zoomInGesture && y <= ySeekRect.bottom + zoomInGesture) {
				isSelectY = true;
				if (mOnAxisChangeListener != null) {
					mOnAxisChangeListener.onStartSeekBarY(this);
				}

			}

			if (y >= xSeekRect.top - zoomInGesture && y <= xSeekRect.bottom + zoomInGesture
					&& x >= xSeekRect.left - zoomInGesture && x <= xSeekRect.right + zoomInGesture) { // 选中X轴滑块
				if (mOnAxisChangeListener != null) {
					mOnAxisChangeListener.onStartSeekBatX(this);
				}
				isSelectX = true;
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (isSelectY) {
				if (x >= ySeekRect.left - zoomInGesture && x <= ySeekRect.right + zoomInGesture && y <= zoomInGesture
						&& y >= -yAxisLength - zoomInGesture) {// 滑动Y轴滑块

					if (y > 0) {
						y = 0;
					}
					if (y < -yAxisLength) {
						y = -yAxisLength;
					}
					ySeekTop = (int) y - ySeekOver;
					ySeekButtom = ySeekHeight + ySeekTop;
					double progress = getPos(yAxisLength, yMaxPoint, (int) -y);
					yStartPoint = progress + yDValue;
					if (mOnAxisChangeListener != null) {
						mOnAxisChangeListener.onChangeAxisY(this, progress, isSelectY);
					}
				}
			}
			if (isSelectX) {
				if (y >= xSeekRect.top - zoomInGesture && y <= xSeekRect.bottom + zoomInGesture && x >= -zoomInGesture
						&& x <= xAxisLength + zoomInGesture) {// 滑动X轴滑块

					if (x < 0) {
						x = 0;
					}
					if (x > xAxisLength) {
						x = xAxisLength;
					}

					xSeekLeft = (int) x - xSeekOver;
					xSeekRight = xSeekWidth + xSeekLeft;
					double progress = getPos(xAxisLength, xMaxPoint, (int) x);
					xStartPoint = progress + xDValue;
					if (mOnAxisChangeListener != null) {
						mOnAxisChangeListener.onChangeAxisX(this, progress, isSelectY);
					}
				}

			}
			invalidate();
			break;
		case MotionEvent.ACTION_UP:
			if (isSelectX) {
				isSelectX = false;
				if (mOnAxisChangeListener != null) {
					mOnAxisChangeListener.onStopSeekBatX(this);
				}
			}
			if (isSelectY) {
				isSelectY = false;
				if (mOnAxisChangeListener != null) {
					mOnAxisChangeListener.onStopSeekBarY(this);
				}
			}

			break;
		}
		return true;
	}

	public interface OnAxisChangeListener {
		void onStartSeekBarY(AxisView axisView);

		void onStartSeekBatX(AxisView axisView);

		void onStopSeekBarY(AxisView axisView);

		void onStopSeekBatX(AxisView axisView);

		void onChangeAxisY(AxisView axisView, double progress, boolean isSelected);

		void onChangeAxisX(AxisView axisView, double progress, boolean isSelected);
	}
}
