/* ==================================================
 * 产品名: 卡路里消耗管理
 * 文件名: SettingRemind.java
 * --------------------------------------------------
 * 开发环境: JDK1.6
 * --------------------------------------------------
 * 修订履历  2011/06/13  REV.  备注
 *         2011/06/13  1.00  初版发行
 * --------------------------------------------------
 * (C) Copyright XIKANG CORPORATION 2011 All Rights Reserved.
 */
package com.xikang.calorie.activity;

import java.util.List;
import java.util.Map;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xikang.calorie.common.Constants;
import com.xikang.calorie.common.Util;
import com.xikang.calorie.db.DB_ConnectionPool;
import com.xikang.calorie.db.RemindService;
import com.xikang.calorie.domain.Remind;
import com.xikang.calorie.xml.XmlConstants.RemindConfGet;

/**
 * 
 * 运动时间提醒设置界面
 * 
 * 
 * <pre>
 * 
 * 2011/06/13, 曲磊
 * 
 * 修订履历：2011/08/16 闫继超  具体功能实现
 * 修订履历：2011/08/23 曲磊  重新修改程序结构，命名，开发规范。
 * </pre>
 * 
 * @author 曲磊
 * @version 1.00
 */
public class SettingRemind extends BaseActivity {

	private static final int DATA_ERROR = 7001;

	private static final int REQUEST_SAVE = 8001;

	private RemindService remindService;

	private Double targetCalories;
	private Double weight;

	private Thread thread;

	private TextView tvRemindMorning;
	private TextView tvRemindNoon;
	private TextView tvRemindNight;

	private RelativeLayout lytRemindMorning;
	private RelativeLayout lytRemindNoon;
	private RelativeLayout lytRemindNight;

	private FrameLayout lytRemindSynData;

	private TextView tvRemindIsMorning;
	private TextView tvRemindIsNoon;
	private TextView tvRemindIsNight;

	private Remind remindMorning;
	private Remind remindNoon;
	private Remind remindNight;

	private double moringRatio = 0.3;
	private double noonRatio = 0.3;
	private double nightRatio = 0.4;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_setting_remind);
		remindService = new RemindService(DB_ConnectionPool.getInstance(mContext));
		init();

		showProgress(getString(R.string.setting_progress_message_loading));
		thread = new Thread(runLoadData);
		thread.start();
	}

	/**
	 * 加载线程处理
	 */
	private Runnable runLoadData = new Runnable() {

		@Override
		public void run() {
			Message msg = handler.obtainMessage();
			msg.what = LOADING_FAILED;
			if (Constants.CURRENT_USER != null) {
				Map<String, String> result = mXmlope.getRemindInfo(Constants.CURRENT_USER.getPersonId());
				String resultCalories = result.get(RemindConfGet.SPORTTARGET);
				String resultWeight = result.get(RemindConfGet.WEIGHT);
				if (!resultCalories.equals("") && !resultWeight.equals("0.0")) {
					targetCalories = Double.parseDouble(resultCalories);
					weight = Double.parseDouble(resultWeight);
					msg.what = LOADING_SCUESS;
				} else {
					msg.what = DATA_ERROR;
				}
			}
			handler.sendMessage(msg);
		}
	};

	/**
	 * 刷新UI
	 */
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case LOADING_SCUESS:
				loadingData();
				closeProgress();
				break;
			case LOADING_FAILED:
				closeProgress();
				showToastLong(getString(R.string.setting_progress_message_data_failed));
				SettingRemind.this.finish();
				break;
			case DATA_ERROR:
				closeProgress();
				showToastLong(getString(R.string.setting_progress_message_userinfo_empty));
				SettingRemind.this.finish();
				break;
			default:
				closeProgress();
				break;
			}
		}
	};

	private void init() {
		showTitle(this.getString(R.string.setting_layout_remind));
		showLeftButton(this.getString(R.string.common_back), new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				SettingRemind.this.finish();
			}
		});

		lytRemindMorning = (RelativeLayout) findViewById(R.id.lytRemindMorning);
		lytRemindMorning.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startEditActivity("1");
			}
		});

		lytRemindNoon = (RelativeLayout) findViewById(R.id.lytRemindNoon);
		lytRemindNoon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startEditActivity("2");

			}
		});

		lytRemindNight = (RelativeLayout) findViewById(R.id.lytRemindNight);
		lytRemindNight.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startEditActivity("3");
			}
		});

		lytRemindSynData = (FrameLayout) findViewById(R.id.lytRemindSynData);
		lytRemindSynData.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showResult(getString(R.string.common_notice), getString(R.string.setting_remind_syn_message),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								showProgress(getString(R.string.setting_progress_message_data));
								new Thread() {
									public void run() {
										List<Remind> list = mXmlope.getRemindConf(Constants.CURRENT_USER.getPersonId());
										Message msg = handler.obtainMessage();
										if (list != null) {
											for (Remind remind : list) {
												remindService.saveRemind(remind);
											}
											msg.what = LOADING_SCUESS;
										}
										handler.sendMessage(msg);
									}
								}.start();
							}
						}, null);
			}
		});

		tvRemindMorning = (TextView) findViewById(R.id.tvRemindMorning);
		tvRemindNoon = (TextView) findViewById(R.id.tvRemindNoon);
		tvRemindNight = (TextView) findViewById(R.id.tvRemindNight);

		tvRemindIsMorning = (TextView) findViewById(R.id.tvRemindIsMorning);
		tvRemindIsNoon = (TextView) findViewById(R.id.tvRemindIsNoon);
		tvRemindIsNight = (TextView) findViewById(R.id.tvRemindIsNight);

	}

	private void loadingData() {
		// 从数据库获取早中晚对应实体
		remindMorning = remindService.getCurrentRemind("1");
		remindNoon = remindService.getCurrentRemind("2");
		remindNight = remindService.getCurrentRemind("3");

		if (remindMorning == null) {
			remindMorning = new Remind();
			remindMorning.setTimeType("1"); // 提醒类型
			remindMorning.setTargetRate(String.valueOf(moringRatio)); // 卡路里比率
			remindMorning.setRemindingEnabled("1"); // 是否提醒
			remindMorning.setRemindingTime("06:30"); // 提醒时间
			remindMorning.setRingType("content://settings/system/ringtone"); // 提醒铃声
			remindMorning.setRemindingRepeat("0"); // 重复
			String calorie = String
					.valueOf(Util.cutNumber(targetCalories * Double.parseDouble(remindMorning.getTargetRate()), 2));
			remindMorning.setTargetCalorie(calorie);
			remindService.saveRemind(remindMorning);
		}

		if (remindNoon == null) {
			remindNoon = new Remind();
			remindNoon.setTimeType("2"); // 提醒类型
			remindNoon.setTargetRate(String.valueOf(noonRatio)); // 卡路里比率
			remindNoon.setRemindingEnabled("1"); // 是否提醒
			remindNoon.setRemindingTime("13:00"); // 提醒时间
			remindNoon.setRingType("content://settings/system/ringtone"); // 提醒铃声
			remindNoon.setRemindingRepeat("0"); // 重复
			String calorie = String.valueOf(Util.cutNumber(targetCalories * Double.parseDouble(remindNoon.getTargetRate()), 2));
			remindNoon.setTargetCalorie(calorie);
			remindService.saveRemind(remindNoon);
		}

		if (remindNight == null) {
			remindNight = new Remind();
			remindNight.setTimeType("3"); // 提醒类型
			remindNight.setTargetRate(String.valueOf(nightRatio)); // 卡路里比率
			remindNight.setRemindingEnabled("1"); // 是否提醒
			remindNight.setRemindingTime("19:30"); // 提醒时间
			remindNight.setRingType("content://settings/system/ringtone"); // 提醒铃声
			remindNight.setRemindingRepeat("0"); // 重复
			String calorie = String.valueOf(Util.cutNumber(targetCalories * Double.parseDouble(remindNight.getTargetRate()), 2));
			remindNight.setTargetCalorie(calorie);
			remindService.saveRemind(remindNight);
		}

		if (!"".equals(remindMorning.getTargetRate())) {
			moringRatio = Double.parseDouble(remindMorning.getTargetRate());
		}

		if (!"".equals(remindNoon.getTargetRate())) {
			noonRatio = Double.parseDouble(remindNoon.getTargetRate());
		}

		if (!"".equals(remindNight.getTargetRate())) {
			nightRatio = Double.parseDouble(remindNight.getTargetRate());
		}

		boolean moringCheck = "0".equals(remindMorning.getRemindingEnabled()) ? false : true;
		if (moringCheck) {
			tvRemindIsMorning.setText(R.string.setting_remind_set);
		} else {
			tvRemindIsMorning.setText(R.string.setting_remind_close);
		}
		boolean noonCheck = "0".equals(remindNoon.getRemindingEnabled()) ? false : true;
		if (noonCheck) {
			tvRemindIsNoon.setText(R.string.setting_remind_set);
		} else {
			tvRemindIsNoon.setText(R.string.setting_remind_close);
		}
		boolean nightCheck = "0".equals(remindNight.getRemindingEnabled()) ? false : true;
		if (nightCheck) {
			tvRemindIsNight.setText(R.string.setting_remind_set);
		} else {
			tvRemindIsNight.setText(R.string.setting_remind_close);
		}

		tvRemindMorning.setText(getMessage(getTargetCalorie(targetCalories, remindMorning.getTargetRate()),
				getString(R.string.setting_remind_morning_info)));
		tvRemindNoon.setText(getMessage(getTargetCalorie(targetCalories, remindNoon.getTargetRate()),
				getString(R.string.setting_remind_noon_info)));
		tvRemindNight.setText(getMessage(getTargetCalorie(targetCalories, remindNight.getTargetRate()),
				getString(R.string.setting_remind_night_info)));
	}

	private double getTargetCalorie(double total, String str) {
		return Util.cutNumber(total * Double.parseDouble(str), 1);
	}

	private void startEditActivity(String timeType) {
		Bundle bundle = new Bundle();
		bundle.putString("TIME_TYPE", timeType);
		bundle.putDouble("TARGET_CALORIES", targetCalories);
		bundle.putDouble("WEIGHT", weight);
		Intent intent = new Intent(mContext, SettingRemindEdit.class);
		intent.putExtras(bundle);
		startActivityForResult(intent, REQUEST_SAVE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_SAVE) {
			if (resultCode == Constants.RESULT_REMAIN_SAVE) {
				loadingData();
			}
		}
	}

	private String getMessage(double calorie, String str) {
		String msg1 = str.replaceAll("@calorie", String.valueOf(Math.round(calorie)));
		String msg2 = msg1.replaceAll("@step", String.valueOf(Util.countSteps(Math.round(calorie), weight)));
		return msg2;
	}
}
