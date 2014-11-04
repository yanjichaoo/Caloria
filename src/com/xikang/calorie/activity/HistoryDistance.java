/* ==================================================
 * 产品名: 卡路里消耗管理
 * 文件名: HistoryDistance.java
 * --------------------------------------------------
 * 开发环境: JDK1.6
 * --------------------------------------------------
 * 修订履历  YYYY/MM/DD  REV.  备注
 *         2011/06/13  1.00  初版发行
 * --------------------------------------------------
 * (C) Copyright XIKANG CORPORATION 2011 All Rights Reserved.
 */
package com.xikang.calorie.activity;

import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xikang.calorie.common.Constants;
import com.xikang.calorie.common.Util;
import com.xikang.calorie.domain.HistoryDistanceData;
import com.xikang.calorie.view.LineChartForHistoryDistance;

public class HistoryDistance extends BaseActivity {

	private TextView txDistance;
	private TextView txDistanceTip;

	private Button[] btn = new Button[3];

	private int[] btnArray = new int[] { R.id.btn_chart_week, R.id.btn_chart_month, R.id.btn_chart_quarter };

	private Activity ac;
	private int chartType = 0;
	@SuppressWarnings("rawtypes")
	private List[] chartList = new List[3];

	private LineChartForHistoryDistance chartView;

	private LinearLayout chartlayout;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_history_distance);
		Util.setTitleBarTitle(this, getString(R.string.historydistance_label));
		Util.returnPage(this);

		txDistance = (TextView) findViewById(R.id.txDistance);

		txDistanceTip = (TextView) findViewById(R.id.txDistanceTip);

		chartlayout = (LinearLayout) findViewById(R.id.chartlayout);
		ac = this;
		btn[0] = (Button) findViewById(btnArray[0]);
		btn[1] = (Button) findViewById(btnArray[1]);
		btn[2] = (Button) findViewById(btnArray[2]);
		HistoryDistanceData distance = mXmlope.getHistoryDistance(Constants.CURRENT_USER.getPersonId(),
				-Constants.TIMESEGMENT[chartType]);
		if (distance != null) {
			if (chartList[chartType] == null) {
				chartList[chartType] = distance.getValueList();
			}
			txDistance.setText(getString(R.string.historyDistance_Pagetip).replace("num",
					distance.getTotalSportDistance() + ""));
			txDistanceTip.setText(getString(Util.getDistanceTip(this, distance.getTotalSportDistance()))
					+ getString(R.string.distance).replace("num", String.format("%1$.2f", Util.distanceCycle)));
			btn[0].setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (chartType == 0)
						return;
					changChart(0);
				}
			});
			btn[1].setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (chartType == 1)
						return;
					changChart(1);
				}
			});
			btn[2].setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (chartType == 2)
						return;
					changChart(2);
				}
			});
			changChart(0);
		} else {
			Log.e("YYYYY", "####");
			for (int i = 0; i < 3; i++) {
				btn[i].setVisibility(ImageView.INVISIBLE);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void changChart(int btnType) {
		btn[chartType].setTextColor(Color.WHITE);
		btn[chartType].setBackgroundResource(R.drawable.btn_chart);

		btn[btnType].setTextColor(Color.BLACK);
		btn[btnType].setBackgroundResource(R.drawable.btn_chart_over);
		chartType = btnType;
		if (chartView != null) {
			chartlayout.removeView(chartView);
		}
		if (chartList[chartType] == null) {
			chartList[chartType] = mXmlope.getHistoryDistance(Constants.CURRENT_USER.getPersonId(),
					-Constants.TIMESEGMENT[chartType]).getValueList();
		}
		chartView = new LineChartForHistoryDistance(ac, chartType, chartList[chartType]);
		chartlayout.addView(chartView);
	}
}