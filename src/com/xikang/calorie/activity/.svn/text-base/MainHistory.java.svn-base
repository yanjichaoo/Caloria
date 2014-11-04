/* ==================================================
 * 产品名: 卡路里消耗管理
 * 文件名: MainHistory.java
 * --------------------------------------------------
 * 开发环境: JDK1.6
 * --------------------------------------------------
 * 修订履历  YYYY/MM/DD  REV.  备注
 *         2011/06/19  1.00  初版发行
 * --------------------------------------------------
 * (C) Copyright XIKANG CORPORATION 2011 All Rights Reserved.
 */
package com.xikang.calorie.activity;

/**
 * 
 * 历史首页
 * 
 * <pre>
 * 
 * 2011/06/13, 曲磊
 * 
 * 修订履历：2011/07/05 代俊义   添加实现
 * 修订履历：2011/08/09 曲磊   程序拆分（数据与UI分开），增加加载进度框
 * </pre>
 * 
 * @author 代俊义
 * @version 1.00
 */
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xikang.calorie.common.Constants;
import com.xikang.calorie.common.Util;
import com.xikang.calorie.domain.HistoryCalorieData;
import com.xikang.calorie.domain.HistoryDistanceData;
import com.xikang.calorie.xml.XmlConstants;
import com.xikang.calorie.xml.XmlConstants.HistorySummaryInfo;
import com.xikang.calorie.xml.XmlConstants.SportTitleGetter;

public class MainHistory extends BaseActivity {

	public static final int EXERCISE_SUCCESS = 1001;
	public static final int EXERCISE_FAILED = 2001;

	public static final int DISTANCE_SUCCESS = 1002;
	public static final int DISTANCE_FAILED = 2002;

	public static final int TIME_SUCCESS = 1003;
	public static final int TIME_FAILED = 2003;

	private TextView txCurrentTitle;
	private TextView txNextTitle;
	private TextView txCalorieData;
	private TextView txNoSportDay;

	private TextView txTimeData;
	private TextView txTimeTip;

	private TextView txDistanceData;
	private TextView txDistanceTip;

	// 数据
	private Map<String, String> result;

	private Thread thread;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_main_history);
		init();
	}

	@Override
	protected void onResume() {
		super.onResume();
		showProgress(getString(R.string.setting_progress_message_loading));
		thread = new Thread(runLoadData);
		thread.start();
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case LOADING_SCUESS:
				loadData();
				closeProgress();
				break;
			case MainHistory.EXERCISE_SUCCESS:
				closeProgress();
				jumpHistorykFrame(0);
				break;
			case MainHistory.EXERCISE_FAILED:
				closeProgress();
				Toast.makeText(MainHistory.this, Constants.TIMESEGMENT[1] + "天内的历史卡路里数据不存在", Toast.LENGTH_SHORT).show();
				break;

			case MainHistory.DISTANCE_SUCCESS:
				closeProgress();
				jumpHistorykFrame(2);
				break;
			case MainHistory.DISTANCE_FAILED:
				closeProgress();
				Toast.makeText(MainHistory.this, Constants.TIMESEGMENT[0] + "天内的历史距离数据不存在", Toast.LENGTH_SHORT).show();
				break;

			case MainHistory.TIME_SUCCESS:
				closeProgress();
				jumpHistorykFrame(1);
				break;
			case MainHistory.TIME_FAILED:
				closeProgress();
				Toast.makeText(MainHistory.this, "历史时间数据不存在", Toast.LENGTH_SHORT).show();
				break;

			default:
				closeProgress();
				break;
			}
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return false;
	}

	/**
	 * 初始化控件
	 */
	private void init() {
		showTitle(getString(R.string.history_titile));
		txCurrentTitle = (TextView) findViewById(R.id.txCurrentTitle);
		txNextTitle = (TextView) findViewById(R.id.txNextTitle);
		txCalorieData = (TextView) findViewById(R.id.txCalorieData);
		txNoSportDay = (TextView) findViewById(R.id.txNoSportDay);
		txTimeData = (TextView) findViewById(R.id.txTimeData);
		txTimeTip = (TextView) findViewById(R.id.txTimeTip);
		txDistanceData = (TextView) findViewById(R.id.txDistanceData);
		txDistanceTip = (TextView) findViewById(R.id.txDistanceTip);
	}

	private void jumpHistorykFrame(int tab) {
		Intent intent = new Intent();
		intent.setClass(MainHistory.this, HistoryFrame.class);
		Bundle bundle = new Bundle();
		bundle.putInt("page", tab);
		intent.putExtras(bundle);
		MainHistory.this.startActivity(intent);

	}

	private Runnable runLoadData = new Runnable() {
		@Override
		public void run() {
			Message msg = handler.obtainMessage();
			if (Constants.CURRENT_USER != null && Constants.CURRENT_USER.getPersonId() != null) {
				result = mXmlope.getHistorySummary(Constants.CURRENT_USER.getPersonId());
				msg.what = LOADING_SCUESS;
			}
			handler.sendMessage(msg);
		}
	};

	private void loadData() {

		if (result == null || "0".equals(result.get(XmlConstants.STATUS))) {
			showToastShort(getString(R.string.setting_progress_message_data_failed));
			return;
		}
		// 没有数据处理
		if ("0".equals(result.get(HistorySummaryInfo.HAS_DATA))) {
			txCurrentTitle.setText(getString(R.string.no_sport_title));
			return;
		}

		String format = "%1$.0f";
		// 称号处理
		int curLevel = 0;
		int nextLevel = 0;
		if (Constants.sportTitleList == null) {
			Constants.sportTitleList = mXmlope.getSportTitleList();
		}
		if (Constants.sportTitleList != null) {
			curLevel = Integer.parseInt(result.get(HistorySummaryInfo.CURRENT_LEVEL));
			if (curLevel < Constants.sportTitleList.size() && curLevel > 0) {
				txCurrentTitle.setText(getString(R.string.sport_current_title).replace("@title",
						Constants.sportTitleList.get(curLevel - 1).get(SportTitleGetter.NAME)));
			}
			nextLevel = Integer.parseInt(result.get(HistorySummaryInfo.NEXT_LEVEL));
			if (nextLevel < Constants.sportTitleList.size() && nextLevel > 0) {
				txNextTitle.setText(getString(R.string.sport_next_title).replace("@title",
						Constants.sportTitleList.get(nextLevel - 1).get(SportTitleGetter.NAME)).replace("day",
						result.get(HistorySummaryInfo.NEXT_LEVELDAYS)));
			}
		}
		double calorieTemp = Double.parseDouble(result.get(HistorySummaryInfo.CALORIE));

		if (calorieTemp % 1 > 0) {
			format = "%1$.1f";
		}
		txCalorieData.setText(String.format(format, calorieTemp));
		txNoSportDay.setText(getString(R.string.noSportTip).replace("day", result.get(HistorySummaryInfo.TOTAL_NONSPORTDAY))
				+ "\n" + getString(Util.getHistoryCalorieTip(result.get(HistorySummaryInfo.TOTAL_NONSPORTDAY))));
		LinearLayout historyLayout = (LinearLayout) findViewById(R.id.historyLayout);
		historyLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				showProgress(MainHistory.this.getString(R.string.setting_progress_message_loading));
				new ExerciseThread().start();

			}
		});
		if (result != null) {
			txTimeData.setText(result.get(HistorySummaryInfo.EXTEND_LIFEDAYS));
			String temp = getString(R.string.historyTime_tip);

			temp = temp.replace("day", result.get(HistorySummaryInfo.EXTEND_LIFEDAYS));
			temp = temp.replace("percent", result.get(HistorySummaryInfo.BEAT_PERCENT));
			txTimeTip.setText(temp);
			LinearLayout historyTimeLayout = (LinearLayout) findViewById(R.id.historyTimeLayout);
			historyTimeLayout.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					showProgress(MainHistory.this.getString(R.string.setting_progress_message_loading));
					new TimeThread().start();
				}
			});
			Constants.historyTimeResult = result;
		}
		float totalDistance = Float.parseFloat(result.get(HistorySummaryInfo.DISTANCE)); 
		format = "%1$.0f";
		if (totalDistance % 1 > 0) {
			format = "%1$.1f";
		}
		txDistanceData.setText(String.format(format, totalDistance));
		txDistanceTip.setText(getString(R.string.historyDistance_Pagetip).replace("num", String.format(format, totalDistance)));
		txDistanceTip.setText(getString(Util.getDistanceTip(this, totalDistance)).replace(",", "\n")
				+ getString(R.string.distance).replace("num", String.format("%1$.2f", Util.distanceCycle)));
		LinearLayout historyDistanceLayout = (LinearLayout) findViewById(R.id.historyDistanceLayout);
		historyDistanceLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				showProgress(MainHistory.this.getString(R.string.setting_progress_message_loading));
				new DistanceThread().start();
			}
		});
	}

	class ExerciseThread extends Thread {

		@Override
		public void run() {
			super.run();
			HistoryCalorieData hisCalorie = mXmlope.getHistoryCalorie(Constants.CURRENT_USER.getPersonId(),
					-Constants.TIMESEGMENT[1]);
			Message msg = Message.obtain();
			if (hisCalorie != null) {
				msg.what = EXERCISE_SUCCESS;
			} else {
				msg.what = EXERCISE_FAILED;
			}
			handler.sendMessage(msg);
		}

	}

	class DistanceThread extends Thread {

		@Override
		public void run() {
			super.run();
			HistoryDistanceData distance = mXmlope.getHistoryDistance(Constants.CURRENT_USER.getPersonId(),
					-Constants.TIMESEGMENT[0]);
			Message msg = Message.obtain();

			if (distance != null) {
				msg.what = DISTANCE_SUCCESS;
			} else {
				msg.what = DISTANCE_FAILED;
			}
			handler.sendMessage(msg);
		}

	}

	class TimeThread extends Thread {

		@Override
		public void run() {
			super.run();
			Map<String, String> result = mXmlope.getHistoryTime(Constants.CURRENT_USER.getPersonId());
			Message msg = Message.obtain();
			if (result != null) {
				msg.what = TIME_SUCCESS;
			} else {
				msg.what = TIME_FAILED;
			}
			handler.sendMessage(msg);
		}

	}

}