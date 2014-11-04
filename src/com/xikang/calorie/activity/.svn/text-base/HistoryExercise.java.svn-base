/* ==================================================
 * 产品名: 卡路里消耗管理
 * 文件名: HistoryExercise.java
 * --------------------------------------------------
 * 开发环境: JDK1.6
 * --------------------------------------------------
 * 修订履历  YYYY/MM/DD  REV.  备注
 *         2011/07/12  1.00  初版发行
 * --------------------------------------------------
 * (C) Copyright XIKANG CORPORATION 2011 All Rights Reserved.
 */
package com.xikang.calorie.activity;

/**
 * 
 * 历史卡路里展示页面
 * 
 * 
 * <pre>
 * 
 * 2011/07/12, 代俊义
 * 
 * 修订履历：YYYY/MM/DD 修订者  修订内容
 * 
 * </pre>
 * 
 * @author 代俊义
 * @version 1.00
 */
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xikang.calorie.common.Constants;
import com.xikang.calorie.common.Util;
import com.xikang.calorie.domain.HistoryCalorieData;
import com.xikang.calorie.view.LineChartForHistoryCalorie;

public class HistoryExercise extends BaseActivity {

	private TextView txNoSportDay;
	private TextView txFatTitle;
	private TextView fatTip;

	private Button[] btn = new Button[3];

	private LinearLayout chartlayout;
	private Activity ac;
	private int chartType = 1;

	private int[] btnArray = new int[] { R.id.btn_chart_week, R.id.btn_chart_month, R.id.btn_chart_quarter };

	@SuppressWarnings("rawtypes")
	private List[] chartList = new List[3];

	private LineChartForHistoryCalorie chartView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_history_exercise);

		txNoSportDay = (TextView) findViewById(R.id.txnosportday);
		txFatTitle = (TextView) findViewById(R.id.txfattitle);
		fatTip = (TextView) findViewById(R.id.txfattip);
		btn[0] = (Button) findViewById(btnArray[0]);
		btn[1] = (Button) findViewById(btnArray[1]);
		btn[2] = (Button) findViewById(btnArray[2]);

		btn[0].setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				changChart(0);
			}
		});

		btn[1].setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				changChart(1);
			}
		});

		btn[2].setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				changChart(2);
			}
		});

		chartlayout = (LinearLayout) findViewById(R.id.chartlayout);
		ac = this;
		Util.setTitleBarTitle(this, getString(R.string.historycalorie_label));
		Util.returnPage(this);
		changChart(0);
	}

	@SuppressWarnings("unchecked")
	private void changChart(int btnType) {
		if (chartType == btnType)
			return;
		btn[chartType].setTextColor(Color.WHITE);
		btn[chartType].setBackgroundResource(R.drawable.btn_chart);

		btn[btnType].setTextColor(Color.BLACK);
		btn[btnType].setBackgroundResource(R.drawable.btn_chart_over);
		chartType = btnType;
		if (chartList[chartType] == null) {
			HistoryCalorieData hisCalorie = mXmlope.getHistoryCalorie(Constants.CURRENT_USER.getPersonId(),
					-Constants.TIMESEGMENT[chartType]);
			if(hisCalorie==null){
				return;
			}
			txNoSportDay.setText(getString(R.string.noSportTip).replace("day",
					String.format("%1$.0f", hisCalorie.getNonSportDay()) + "")
					+ "," + getString(Util.getHistoryCalorieTip(hisCalorie.getNonSportDay() + "")));
			if (hisCalorie.getNonBurnedCalorie() > 0) {
				txFatTitle.setText(getString(R.string.fattitle));
				fatTip.setText(String.format("%1$.1f", hisCalorie.getNonBurnedCalorie() * 0.13 / 980) + "公斤");// 0.13/980
			}
			chartList[chartType] = hisCalorie.getValueList();
		}
		if (chartView != null) {
			chartlayout.removeView(chartView);
		}
		chartView = new LineChartForHistoryCalorie(ac, chartType, chartList[chartType]);
		chartlayout.addView(chartView);
	}

}