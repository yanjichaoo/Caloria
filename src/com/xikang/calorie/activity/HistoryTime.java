/* ==================================================
 * 产品名: 卡路里消耗管理
 * 文件名: HistoryTime.java
 * --------------------------------------------------
 * 开发环境: JDK1.6
 * --------------------------------------------------
 * 修订履历  YYYY/MM/DD  REV.  备注
 *         2011/06/13  1.00  初版发行
 * --------------------------------------------------
 * (C) Copyright XIKANG CORPORATION 2011 All Rights Reserved.
 */
package com.xikang.calorie.activity;

import java.util.Map;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xikang.calorie.common.Constants;
import com.xikang.calorie.common.Util;
import com.xikang.calorie.view.BarChartForTime;
import com.xikang.calorie.xml.XmlConstants.HistorySportTimeDataGetter;

public class HistoryTime extends BaseActivity {
	/** Called when the activity is first created. */
	private TextView txTimeData;
	private TextView txTimeTip;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_history_time);
		Util.setTitleBarTitle(this, getString(R.string.historytime_label));
		Util.returnPage(this);
		txTimeData = (TextView) findViewById(R.id.txTimeData);
		txTimeTip = (TextView) findViewById(R.id.txTimeTip);
		Map<String, String> result = mXmlope.getHistoryTime(Constants.CURRENT_USER.getPersonId());
		if (result != null) {
			txTimeData.setText(result.get(HistorySportTimeDataGetter.EXTEND_LIFEDAYS));
			String temp = getString(R.string.historyTime_tip);
			temp = temp.replace("day", result.get(HistorySportTimeDataGetter.TOTAL_SPORTDAYS));
			temp = temp.replace("percent", result.get(HistorySportTimeDataGetter.BEAT_PERCENT));
			txTimeTip.setText(temp);
		}
		LinearLayout chart = (LinearLayout) findViewById(R.id.timechartLayout);
		chart.addView(new BarChartForTime(this, result));
	}
}