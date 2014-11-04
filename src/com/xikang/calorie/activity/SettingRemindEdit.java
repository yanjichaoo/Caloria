package com.xikang.calorie.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.xikang.calorie.common.Constants;
import com.xikang.calorie.common.Util;
import com.xikang.calorie.db.DB_ConnectionPool;
import com.xikang.calorie.db.RemindService;
import com.xikang.calorie.domain.Remind;
import com.xikang.calorie.view.SliderBox;
import com.xikang.calorie.view.SliderBox.OnSliderBoxCheckListener;

public class SettingRemindEdit extends BaseActivity {

	private static final int RING_REQUEST = 8001;

	private static final int RATE_REQUEST = 8002;

	private RemindService remindService;

	private TextView tvRETimeType;
	private TextView tvRETime;
	private TextView tvRECalorie;
	private TextView tvRERing;
	private SliderBox boxRERepeat;
	private SliderBox boxRERemind;

	private FrameLayout lytRETime;
	private FrameLayout lytRECalorie;
	private FrameLayout lytRERing;

	private Remind remind;

	private Remind remindMorning;
	private Remind remindNoon;
	private Remind remindNight;

	private Uri ringUri;
	private double weight;
	private double targetCalories;

	private double rate;
	private String timeType;

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case LOADING_SCUESS:
				remindService.saveRemind(remindMorning);
				remindService.saveRemind(remindNoon);
				remindService.saveRemind(remindNight);
				closeProgress();
				setResult(Constants.RESULT_REMAIN_SAVE);
				SettingRemindEdit.this.finish();
				break;
			case LOADING_FAILED:
				showToastLong(getString(R.string.profile_progress_message_failed));
				closeProgress();
				break;
			default:
				closeProgress();
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_setting_remind_edit);
		remindService = new RemindService(DB_ConnectionPool.getInstance(this));

		Bundle bundle = getIntent().getExtras();
		timeType = bundle.getString("TIME_TYPE");
		targetCalories = bundle.getDouble("TARGET_CALORIES");
		weight = bundle.getDouble("WEIGHT");

		init();
		loadingData();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case RING_REQUEST:
			if (resultCode == -1) {
				ringUri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
				if (ringUri == null) {
					tvRERing.setText(R.string.setting_remind_ring_mute);
					remind.setRingType("");
				} else {
					Ringtone ringtone = RingtoneManager.getRingtone(mContext, ringUri);
					if (ringtone != null) {
						remind.setRingType(ringUri.toString());
						tvRERing.setText(ringtone.getTitle(mContext));
					} else {
						tvRERing.setText(R.string.setting_remind_ring_default);
					}
					remind.setRingType(ringUri.toString());
				}

			}
			break;

		case RATE_REQUEST:
			if (resultCode == Constants.RESULT_REMAIN_COMPLETE) {
				Bundle bundle = data.getExtras();
				double rateX = bundle.getDouble("RATE_MORNING");
				double rateY = bundle.getDouble("RATE_NOON");
				double rateZ = bundle.getDouble("RATE_NIGHT");

				if ("1".equals(timeType)) {
					rate = rateX;
					if (rateY == 0) {
						remindNoon.setRemindingEnabled("0");
					} else {
						remindNoon.setRemindingEnabled("1");
					}
					if (rateZ == 0) {
						remindNight.setRemindingEnabled("0");
					} else {
						remindNight.setRemindingEnabled("1");
					}
					remindNoon.setTargetRate(String.valueOf(rateY));
					remindNight.setTargetRate(String.valueOf(rateZ));
				} else if ("2".equals(timeType)) {
					rate = rateY;
					if (rateX == 0) {
						remindMorning.setRemindingEnabled("0");
					} else {
						remindMorning.setRemindingEnabled("1");
					}
					if (rateZ == 0) {
						remindNight.setRemindingEnabled("0");
					} else {
						remindNight.setRemindingEnabled("1");
					}
					remindMorning.setTargetRate(String.valueOf(rateX));
					remindNight.setTargetRate(String.valueOf(rateZ));
				} else if ("3".equals(timeType)) {
					rate = rateZ;
					if (rateX == 0) {
						remindMorning.setRemindingEnabled("0");
					} else {
						remindMorning.setRemindingEnabled("1");
					}

					if (rateY == 0) {
						remindNoon.setRemindingEnabled("0");
					} else {
						remindNoon.setRemindingEnabled("1");
					}
					remindMorning.setTargetRate(String.valueOf(rateX));
					remindNoon.setTargetRate(String.valueOf(rateY));
				}

				if (rate == 0) {
					remind.setRemindingEnabled("0");
				} else {
					remind.setRemindingEnabled("1");
				}
				remind.setTargetRate(String.valueOf(rate));
				tvRECalorie.setText(getCalories(Util.cutNumber(targetCalories * rate, 1)));
				break;
			}
		}
	}

	/**
	 * 初始化控件
	 */
	private void init() {
		showTitle(this.getString(R.string.setting_layout_remind));
		showLeftButton(this.getString(R.string.common_back), new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				SettingRemindEdit.this.finish();
			}
		});
		showRightButton(this.getString(R.string.common_save), new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				showProgress(getString(R.string.setting_progress_message_saving));
				new Thread() {
					public void run() {
						Message msg = handler.obtainMessage();
						initRemind();

						boolean moringCheck = "0".equals(remindMorning.getRemindingEnabled()) ? false : true;
						boolean noonCheck = "0".equals(remindNoon.getRemindingEnabled()) ? false : true;
						boolean nightCheck = "0".equals(remindNight.getRemindingEnabled()) ? false : true;

						double[] arrayRate = Util.countCalorie(moringCheck, noonCheck, nightCheck,
								Double.parseDouble(remindMorning.getTargetRate()),
								Double.parseDouble(remindNoon.getTargetRate()), Double.parseDouble(remindNight.getTargetRate()));

						remindMorning.setTargetRate(String.valueOf(arrayRate[0]));
						remindNoon.setTargetRate(String.valueOf(arrayRate[1]));
						remindNight.setTargetRate(String.valueOf(arrayRate[2]));

						String result = mXmlope.setRemindConf(Constants.CURRENT_USER.getPersonId(), makeList());
						if ("1".equals(result)) {
							msg.what = LOADING_SCUESS;
						} else {
							msg.what = LOADING_FAILED;
						}
						handler.sendMessage(msg);
					}
				}.start();
			}
		});

		tvRETimeType = (TextView) findViewById(R.id.tvRETimeType);
		tvRETime = (TextView) findViewById(R.id.tvRETime);
		tvRECalorie = (TextView) findViewById(R.id.tvRECalorie);
		tvRERing = (TextView) findViewById(R.id.tvRERing);

		boxRERepeat = (SliderBox) findViewById(R.id.boxRERepeat);
		boxRERepeat.setOnSliderBoxCheckListener(new OnSliderBoxCheckListener() {
			@Override
			public void onChecked() {
				String value = boxRERepeat.getSliderCheck() ? "1" : "0";
				remind.setRemindingRepeat(value);
			}
		});

		boxRERemind = (SliderBox) findViewById(R.id.boxRERemind);
		boxRERemind.setOnSliderBoxCheckListener(new OnSliderBoxCheckListener() {
			@Override
			public void onChecked() {

				String status = boxRERemind.getSliderCheck() ? "1" : "0";
				remind.setRemindingEnabled(status);
				lytRECalorie.setClickable(boxRERemind.getSliderCheck());

				if (boxRERemind.getSliderCheck()) { // 开启
					if (rate == 0) {
						double resumeRate = 0;
						if ("1".equals(timeType)) {
							resumeRate = 0.3;
							remind.setTargetRate(String.valueOf(0.3));
							remindNoon.setTargetRate(String.valueOf(0.3));
							remindNight.setTargetRate(String.valueOf(0.4));
						} else if ("2".equals(timeType)) {
							resumeRate = 0.3;
							remindMorning.setTargetRate(String.valueOf(0.3));
							remind.setTargetRate(String.valueOf(0.3));
							remindNight.setTargetRate(String.valueOf(0.4));
						} else if ("3".equals(timeType)) {
							resumeRate = 0.4;
							remindMorning.setTargetRate(String.valueOf(0.3));
							remindNoon.setTargetRate(String.valueOf(0.3));
							remind.setTargetRate(String.valueOf(0.4));
						}
						remindMorning.setRemindingEnabled("1");
						remindNoon.setRemindingEnabled("1");
						remind.setRemindingEnabled("1");
						tvRECalorie.setText(getCalories(Util.cutNumber(targetCalories * resumeRate, 1)));
					} else {
						double resumeRate = rate;
						tvRECalorie.setText(getCalories(Util.cutNumber(targetCalories * resumeRate, 1)));
					}

				} else { // 关闭
					double resumeRate = 0;
					tvRECalorie.setText(getCalories(Util.cutNumber(targetCalories * resumeRate, 1)));
				}

			}
		});

		lytRETime = (FrameLayout) findViewById(R.id.lytRETime);
		lytRETime.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				initTimeDialog().show();
			}
		});

		lytRECalorie = (FrameLayout) findViewById(R.id.lytRECalorie);
		lytRECalorie.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				bundle.putDouble("TARGET_CALORIES", targetCalories);

				bundle.putSerializable("REMIND_MORNING", remindMorning);
				bundle.putSerializable("REMIND_NOON", remindNoon);
				bundle.putSerializable("REMIND_NIGHT", remindNight);

				Intent intent = new Intent(mContext, SettingRemindCalories.class);
				intent.putExtras(bundle);
				startActivityForResult(intent, RATE_REQUEST);
			}
		});

		lytRERing = (FrameLayout) findViewById(R.id.lytRERing);
		lytRERing.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setAction(RingtoneManager.ACTION_RINGTONE_PICKER);
				intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true);
				intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_RINGTONE);
				Uri ringUri = Uri.parse(remind.getRingType());
				if (ringUri != null) {
					intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, ringUri);
				}
				startActivityForResult(intent, RING_REQUEST);
			}

		});
	}

	private void loadingData() {

		remind = remindService.getCurrentRemind(timeType);

		remindMorning = remindService.getCurrentRemind("1");
		remindNoon = remindService.getCurrentRemind("2");
		remindNight = remindService.getCurrentRemind("3");

		if (remind != null) {
			CharSequence[] array = getResources().getTextArray(R.array.array_remind_time_type);
			tvRETimeType.setText(array[Integer.valueOf(remind.getTimeType()) - 1]);
			tvRETime.setText(remind.getRemindingTime());

			rate = Double.parseDouble(remind.getTargetRate());
			double calorie = Util.cutNumber(targetCalories * Double.parseDouble(remind.getTargetRate()), 1);
			tvRECalorie.setText(getCalories(calorie));

			boolean isRepeat = "0".equals(remind.getRemindingRepeat()) ? false : true;
			boxRERepeat.setSliderCheck(isRepeat);

			boolean isRemind = "0".equals(remind.getRemindingEnabled()) ? false : true;
			boxRERemind.setSliderCheck(isRemind);

			lytRECalorie.setClickable(isRemind);

			ringUri = Uri.parse(remind.getRingType());
			Ringtone mRingtone = RingtoneManager.getRingtone(mContext, ringUri);
			if (mRingtone != null) {
				tvRERing.setText(mRingtone.getTitle(mContext));
			} else {
				tvRERing.setText(R.string.setting_remind_ring_default);
			}
		}
	}

	private void initRemind() {
		if ("1".equals(timeType)) {
			remindMorning = remind;
			// remindNoon = remindService.getCurrentRemind("2");
			// remindNight = remindService.getCurrentRemind("3");
		} else if ("2".equals(timeType)) {
			// remindMorning = remindService.getCurrentRemind("1");
			remindNoon = remind;
			// remindNight = remindService.getCurrentRemind("3");
		} else if ("3".equals(timeType)) {
			// remindMorning = remindService.getCurrentRemind("1");
			// remindNoon = remindService.getCurrentRemind("2");
			remindNight = remind;
		} else {
			remindMorning = remindService.getCurrentRemind("1");
			remindNoon = remindService.getCurrentRemind("2");
			remindNight = remindService.getCurrentRemind("3");
		}
	}

	private Dialog initTimeDialog() {

		int hour;
		int minute;

		if ("".equals(remind.getRemindingTime())) {
			Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"), Locale.CHINA);
			hour = calendar.get(Calendar.HOUR_OF_DAY);
			minute = calendar.get(Calendar.MINUTE);
		} else {
			Date setDate = Util.spellDate(remind.getRemindingTime());
			hour = setDate.getHours();
			minute = setDate.getMinutes();
		}

		TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				boolean isEdit = false;
				if ("1".equals(remind.getTimeType())) {
					if (hourOfDay >= 5 && hourOfDay <= 9) {
						isEdit = true;
					}
				} else if ("2".equals(remind.getTimeType())) {
					if (minute >= 30) {
						if (hourOfDay >= 10 && hourOfDay <= 13) {
							isEdit = true;
						}

					} else if (hourOfDay >= 11 && hourOfDay <= 14) {
						isEdit = true;
					}
				} else if ("3".equals(remind.getTimeType())) {
					if (hourOfDay >= 17 && hourOfDay <= 21) {
						isEdit = true;
					}
				}
				if (isEdit) {
					tvRETime.setText(Util.setTimeHM(hourOfDay, minute));
					remind.setRemindingTime(Util.setTimeHM(hourOfDay, minute));
				} else {
					CharSequence[] array = getResources().getTextArray(R.array.array_remind_sport_range);
					showToastLong("没有运动时间周期范围内," + array[Integer.valueOf(remind.getTimeType()) - 1]);
				}
			}

		};
		return new TimePickerDialog(SettingRemindEdit.this, listener, hour, minute, true);
	}

	private String getCalories(double calorie) {
		String msg = getString(R.string.setting_remind_text);
		String msg1 = msg.replaceAll("@calorie", String.valueOf(Math.round(calorie)));
		String msg2 = msg1.replaceAll("@step", String.valueOf(Util.countSteps(Math.round(calorie), weight)));
		return msg2;
	}

	private List<Remind> makeList() {
		List<Remind> list = new ArrayList<Remind>();
		list.add(remindMorning);
		list.add(remindNoon);
		list.add(remindNight);
		return list;
	}
}
