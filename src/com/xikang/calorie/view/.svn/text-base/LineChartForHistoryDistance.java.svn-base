/* ==================================================
 * 产品名: 卡路里消耗管理
 * 文件名: LineChartForHistoryDistance.java
 * --------------------------------------------------
 * 开发环境: JDK1.6 
 * --------------------------------------------------
 * 修订履历  YYYY/MM/DD  REV.  备注
 *         2011/07/20  1.00  初版发行
 * --------------------------------------------------
 * (C) Copyright XIKANG CORPORATION 2011 All Rights Reserved.
 */
package com.xikang.calorie.view;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.view.View;

import com.xikang.calorie.common.Util;
import com.xikang.calorie.xml.XmlConstants.HistoryDistanceDataGetter;
/**
 * 
 * 历史距离chart
 * 
 * 
 * <pre>
 * 
 * 2011/07/20, 代俊义
 * 
 * 修订履历：YYYY/MM/DD 修订者  修订内容
 * 
 * </pre>
 * 
 * @author 代俊义
 * @version 1.00
 */
public class LineChartForHistoryDistance extends View {
	
	private float spacingX;
	private float spacingY;

	private float topSpacingY;
	private float leftSpacingX;

	private List<Map<String, String>> list;

	private int chartType;
	
	private float maxDistance = 0;
	
	public LineChartForHistoryDistance(Context context,int chartType, List<Map<String, String>> list) {
		super(context);
		this.chartType = chartType;
		this.list = list;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		spacingY = getHeight() / 6;
		spacingX = getWidth() / 8;
		topSpacingY = spacingY*1.2f;
		leftSpacingX = spacingX;
		drawBackground(canvas);
		drawLabel(canvas);
		drawChartContent(canvas);
	}
	/**
	 * 图表总体内容
	 * 
	 * @param canvas
	 */
	private void drawChartContent(Canvas canvas) {
		Paint paint = new Paint();
		int listSize = list.size();
		float lastBurnedY=0;
		float currBurnedY=0;
		
		float intervalX = 1;

		if (chartType == 1) {
			intervalX = 0.2f;
		} else if (chartType == 2) {
			intervalX = 0.2f / 3;
		}
		
		for (int i = 1; i < listSize; i++) {
			paint.setColor(Color.GREEN);
			if (i == 1) {
				lastBurnedY = Float.parseFloat(list.get(i - 1).get(HistoryDistanceDataGetter.DISTANCE));
				lastBurnedY = topSpacingY + spacingY * 4 * (1 - lastBurnedY / maxDistance);
			}
			currBurnedY = Float.parseFloat(list.get(i).get(HistoryDistanceDataGetter.DISTANCE));
			currBurnedY = topSpacingY + spacingY * 4 * (1 - currBurnedY / maxDistance);
			canvas.drawLine(leftSpacingX + spacingX * (i - 1) * intervalX, lastBurnedY, leftSpacingX + spacingX * i
					* intervalX, currBurnedY, paint);
			lastBurnedY = currBurnedY;
		}
	}
	/**
	 * 画页面背景--固定内容
	 * 
	 * @param canvas
	 */
	private void drawBackground(Canvas canvas) {
		// 定义paint
		Paint paint = new Paint();
		// 绘制背景
		float temp_y;
		for (int i = 1; i < 6; i++) {
			temp_y = (i - 1) * spacingY + topSpacingY;
			if (i < 5) {
				paint.setColor(Color.LTGRAY);
				paint.setPathEffect(new DashPathEffect(new float[] { 6, 6, 6, 6 }, 6.5f * spacingX));
				canvas.drawLine(leftSpacingX, temp_y, 7.5f * spacingX, temp_y, paint);
			} else {
				paint.setColor(Color.GREEN);
				paint.setPathEffect(null);
				canvas.drawLine(leftSpacingX, temp_y, 7.6f * spacingX, temp_y, paint);
			}
		}
		canvas.drawLine(leftSpacingX, topSpacingY- spacingY * 0.5f, leftSpacingX,topSpacingY+ spacingY * 4f, paint);
		// 标签
		canvas.drawText("公里", leftSpacingX * 0.5f, topSpacingY * 0.5f, paint);
		// 原点
		canvas.drawText("0", spacingX * 0.8f, spacingY * 5f, paint);
		// 标签 下一个目标 实际
		//canvas.drawText("━ 实际能耗", spacingX * 4.85f, spacingY * 0.5f, paint);
		// 标签 已完成目标
		//paint.setColor(Color.YELLOW);
		//canvas.drawText("━ 目标能耗", spacingX * 2.15f, spacingY * 0.5f, paint);

	}
	/**
	 * 文字
	 * 
	 * @param canvas
	 */
	private void drawLabel(Canvas canvas) {
		// 定义paint
		Paint paint = new Paint();

		float curDistance = 0;
		int interval = 1;
		if (chartType == 1) {
			interval = 5;
		} else if (chartType == 2) {
			interval = 15;
		}
		int listSize = list.size();
		paint.setColor(Color.GREEN);
		String XLabel=null;
		// 画横坐标
		for (int i = 0; i < listSize; i++) {
			if (i % interval == 0) {
				XLabel=Util.getMonthDay(list.get(i).get(HistoryDistanceDataGetter.DATE));
				canvas.drawText(Util.getMonthDay(list.get(i).get(HistoryDistanceDataGetter.DATE)), spacingX
						* (1 + i / interval)-getTextWith(paint, XLabel)/2, spacingY * 5.5f, paint);
			}
			curDistance = Float.parseFloat(list.get(i).get(HistoryDistanceDataGetter.DISTANCE));
			if (curDistance > maxDistance) {
				maxDistance = curDistance;
			}
		}
		// 画纵坐标
		String label = null;
		String format = "%1$.0f";
		for (int i = 4; i > 0; i--) {
			label = String.format(format, maxDistance * (i / 4f));
			canvas.drawText(label, leftSpacingX * 0.9f - getTextWith(paint, label), topSpacingY + spacingY * (4 - i),
					paint);
		}
	}
	
	// 文字宽度
	private int getTextWith(Paint pt, String str) {
		return (int) pt.measureText(str);
	}

}
