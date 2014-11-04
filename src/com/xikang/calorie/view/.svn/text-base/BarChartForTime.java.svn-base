/* ==================================================
 * 产品名: 卡路里消耗管理
 * 文件名: BarChartForTime.java
 * --------------------------------------------------
 * 开发环境: JDK1.6 
 * --------------------------------------------------
 * 修订履历  YYYY/MM/DD  REV.  备注
 *         2011/07/29  1.00  初版发行
 * --------------------------------------------------
 * (C) Copyright XIKANG CORPORATION 2011 All Rights Reserved.
 */
package com.xikang.calorie.view;

import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.View;

import com.xikang.calorie.activity.R;
import com.xikang.calorie.xml.XmlConstants.HistorySportTimeDataGetter;

/**
 * 
 * 历史时间chart
 * 
 * 
 * <pre>
 * 
 * 2011/07/29, 代俊义
 * 
 * 修订履历：YYYY/MM/DD 修订者  修订内容
 * 
 * </pre>
 * 
 * @author 代俊义
 * @version 1.00
 */
public class BarChartForTime extends View {

	private Map<String, String> map;

	private float spacingX;
	private float spacingY;

	public BarChartForTime(Context context, Map<String, String> map) {
		super(context);
		this.map = map;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		spacingY = getHeight() / 4;
		spacingX = getWidth() / 10;

		Paint paint = new Paint();

		String rankText1 = null;
		String rankText2 = null;
		String avgBarText = null;
		String totalBarText = null;

		int textHeight = 0;
		int textWeight = 0;
		float textY = 0;
		Bitmap barBmp = null;

		Bitmap barBackground = null;
		int barId;
		float firstBarLength = 1;

		Matrix bgMatrix = new Matrix();
		bgMatrix.setScale(spacingX * 7.6f, 1);

		paint.setColor(Color.LTGRAY);
		paint.setTextSize(16f);
		// 画第一行文字
		rankText1 = "全国平均";
		rankText2 = "运动天数";
		textWeight = getTextWidth(paint, rankText1);
		textHeight = getTextHeight(paint);
		textY = spacingY * (1 + 0.7f);
		canvas.drawText(rankText1, spacingX * 2 - textWeight, textY - textHeight, paint);
		canvas.drawText(rankText2, spacingX * 2 - textWeight, textY, paint);
		// 画第一行背景
		barBackground = BitmapFactory.decodeResource(getResources(), R.drawable.rank_chart_bar_bg);
		barBackground = Bitmap.createBitmap(barBackground, 0, 0, barBackground.getWidth(), barBackground.getHeight(),
				bgMatrix, true);
		canvas.drawBitmap(barBackground, spacingX * 2.2f, textY - textHeight * 2f, null);
		// 画第二行文字
		rankText1 = "你的运动";
		rankText2 = "天数";
		textWeight = getTextWidth(paint, rankText1);
		textHeight = getTextHeight(paint);
		textY = spacingY * (2 + 0.7f);
		canvas.drawText(rankText1, spacingX * 2 - textWeight, textY - textHeight, paint);
		canvas.drawText(rankText2, spacingX * 2 - textWeight, textY, paint);
		// 画第二行背景
		barBackground = BitmapFactory.decodeResource(getResources(), R.drawable.rank_chart_bar_bg);
		barBackground = Bitmap.createBitmap(barBackground, 0, 0, barBackground.getWidth(), barBackground.getHeight(),
				bgMatrix, true);
		canvas.drawBitmap(barBackground, spacingX * 2.2f, textY - textHeight * 2f, null);

		// 求出最长的bar长度
		float avgSportDays = Float.parseFloat(map.get(HistorySportTimeDataGetter.ALLUSER_AVERAGESPORTDAYS));
		float total_SportDays = Float.parseFloat(map.get(HistorySportTimeDataGetter.TOTAL_SPORTDAYS));
		float maxDay;
		avgBarText = (avgSportDays % 1 > 0 ? avgSportDays+"" : String.format("%1$.0f", avgSportDays)) + "天";
		totalBarText = (total_SportDays % 1 > 0 ? total_SportDays+"" : String.format("%1$.0f", total_SportDays))+ "天";
		if (avgSportDays > total_SportDays) {
			firstBarLength = spacingX * 7.6f - getTextWidth(paint, avgBarText) - 10f;
			maxDay = avgSportDays;
			barId = R.drawable.rank_bar_white;
		} else {
			firstBarLength = spacingX * 7.6f - getTextWidth(paint, totalBarText) - 10f;
			maxDay = total_SportDays;
			barId = R.drawable.rank_bar_red;
		}
		// 添加第一条bar
		textY = spacingY * (1 + 0.7f);
		barBmp = BitmapFactory.decodeResource(getResources(), R.drawable.rank_bar_white);
		float barLength = avgSportDays * firstBarLength / maxDay;
		if (barLength < 1) {
			barLength = 1;
		}
		Matrix avgmatrix = new Matrix();
		avgmatrix.postScale(barLength, 1f);
		barBmp = Bitmap.createBitmap(barBmp, 0, 0, barBmp.getWidth(), barBmp.getHeight(), avgmatrix, true);
		canvas.drawBitmap(barBmp, spacingX * 2.2f, textY - textHeight * 2f
				+ (textHeight * 2f + 5f - barBmp.getHeight()) / 2, null);
		getTextWidth(paint, avgBarText);
		canvas.drawText(avgBarText, barLength + spacingX * 2.2f + 10f, textY - getTextHeight(paint) / 2, paint);
		// 添加第二条bar
		textY = spacingY * (2 + 0.7f);
		barBmp = BitmapFactory.decodeResource(getResources(), barId);
		barLength = total_SportDays * firstBarLength / maxDay;
		if (barLength < 1) {
			barLength = 1;
		}
		Matrix totalmatrix = new Matrix();
		totalmatrix.postScale(barLength, 1f);
		barBmp = Bitmap.createBitmap(barBmp, 0, 0, barBmp.getWidth(), barBmp.getHeight(), totalmatrix, true);
		canvas.drawBitmap(barBmp, spacingX * 2.2f, textY - textHeight * 2f
				+ (textHeight * 2f + 5f - barBmp.getHeight()) / 2, null);
		getTextWidth(paint, totalBarText);
		canvas.drawText(totalBarText, barLength + spacingX * 2.2f + 10f, textY - getTextHeight(paint) / 2, paint);
	}

	// 文字宽度
	private int getTextWidth(Paint pt, String str) {
		return (int) pt.measureText(str);
	}

	// 文字高度
	private int getTextHeight(Paint pt) {
		return (int) (-pt.ascent() + pt.descent());
	}

}
