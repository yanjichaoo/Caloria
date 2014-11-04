/* ==================================================
 * 产品名: 卡路里消耗管理
 * 文件名: BarChartForRank.java
 * --------------------------------------------------
 * 开发环境: JDK1.6 
 * --------------------------------------------------
 * 修订履历  YYYY/MM/DD  REV.  备注
 *         2011/07/22  1.00  初版发行
 * --------------------------------------------------
 * (C) Copyright XIKANG CORPORATION 2011 All Rights Reserved.
 */
package com.xikang.calorie.view;

import java.util.List;
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
import com.xikang.calorie.common.Constants;
import com.xikang.calorie.xml.XmlConstants.RankingInfo;
/**
 * 
 * 排行榜chart
 * 
 * 
 * <pre>
 * 
 * 2011/07/22, 代俊义
 * 
 * 修订履历：YYYY/MM/DD 修订者  修订内容
 * 
 * </pre>
 * 
 * @author 代俊义
 * @version 1.00
 */
public class BarChartForRank extends View {

	private List<Map<String, String>> list;

	private float spacingX;
	private float spacingY;

	private String rankString = "第num名";

	public BarChartForRank(Context context, List<Map<String, String>> list) {
		super(context);
		this.list = list;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		spacingY = getHeight() / 4;
		spacingX = getWidth() / 10;

		Paint paint = new Paint();

		String rankText = null;
		String nameText = null;
		String calText = null;
		int textHeight = 0;
		int textWeight = 0;
		float textY = 0;
		Bitmap barBmp = null;

		Bitmap barBackground = null;
		int barId;
		float firstBarLength=1;
		float firstCalorie=1;
		
		Map<String, String> map = null;
		for (int i = 0; i < list.size(); i++) {
			map = list.get(i);

			// 画文字
			paint.setColor(Color.LTGRAY);
			paint.setTextSize(16f);
			rankText = rankString.replace("num", map.get(RankingInfo.RANK_VALUE));
			textWeight = getTextWidth(paint, rankText);
			textHeight = getTextHeight(paint);
			textY = spacingY * (i + 0.7f);
			canvas.drawText(rankText, spacingX * 2 - textWeight, textY - textHeight, paint);
			nameText = map.get(RankingInfo.RANK_USERNAME);
			textWeight = getTextWidth(paint, nameText);
			textHeight = getTextHeight(paint);
			canvas.drawText(nameText, spacingX * 2 - textWeight, textY, paint);
			// 画bar背景矩形
			Matrix bgMatrix = new Matrix();
			bgMatrix.setScale(spacingX * 7.6f, 1);

			barBackground = BitmapFactory.decodeResource(getResources(), R.drawable.rank_chart_bar_bg);
			barBackground = Bitmap.createBitmap(barBackground, 0, 0, barBackground.getWidth(),
					barBackground.getHeight(), bgMatrix, true);
			canvas.drawBitmap(barBackground, spacingX * 2.2f, textY - textHeight * 2f, null);

			// 添加第一条bar,记录第一条bar长度
			if (i == 0) {
				if (Constants.CURRENT_USER!=null&&nameText.equals(Constants.CURRENT_USER.getUserName())) {
					barId = R.drawable.rank_bar_red;
				} else {
					barId = R.drawable.rank_bar_white;
				}
				barBmp = BitmapFactory.decodeResource(getResources(), barId);
				firstCalorie=Float.parseFloat( map.get(RankingInfo.RANK_CALORIE));
				calText = String.format("%1$.0f",firstCalorie) + "千卡";	
				firstBarLength = spacingX * 7.6f - getTextWidth(paint, calText) - 10f;
				Matrix matrix = new Matrix();
				matrix.postScale(firstBarLength, 1f);
				barBmp = Bitmap.createBitmap(barBmp, 0, 0, barBmp.getWidth(), barBmp.getHeight(), matrix, true);
				canvas.drawBitmap(barBmp, spacingX * 2.2f,
						textY - textHeight * 2f + (textHeight * 2f + 5f - barBmp.getHeight()) / 2, null);
				getTextWidth(paint, calText) ;
				canvas.drawText(calText, firstBarLength + spacingX * 2.2f + 10f, textY - getTextHeight(paint) / 2,
						paint);
			} else {
				if (Constants.CURRENT_USER!=null&&nameText.equals(Constants.CURRENT_USER.getUserName())) {
					barId = R.drawable.rank_bar_green;
				} else {
					barId = R.drawable.rank_bar_white;
				}
				barBmp = BitmapFactory.decodeResource(getResources(), barId);
				Matrix matrix = new Matrix();
				float calorie=Float.parseFloat( map.get(RankingInfo.RANK_CALORIE));
				float barLength=calorie*firstBarLength/firstCalorie;
				
				if(barLength<1){
					barLength=1;
				}
				calText=String.format("%1$.0f",calorie) + "千卡";	
				matrix.postScale(barLength, 1f);
				
				barBmp = Bitmap.createBitmap(barBmp, 0, 0, barBmp.getWidth(), barBmp.getHeight(), matrix, true);
				canvas.drawBitmap(barBmp, spacingX * 2.2f,
						textY - textHeight * 2f + (textHeight * 2f + 5f - barBmp.getHeight()) / 2, null);
				getTextWidth(paint, calText) ;
				canvas.drawText(calText, barLength + spacingX * 2.2f + 10f, textY - getTextHeight(paint) / 2,
						paint);
			}

		}
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
