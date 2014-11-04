package com.xikang.calorie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.xikang.calorie.common.Constants;
import com.xikang.calorie.common.Util;
import com.xikang.calorie.domain.Remind;
import com.xikang.calorie.view.DragView;
import com.xikang.calorie.view.DragView.OnDragChangeListener;

public class SettingRemindCalories extends BaseActivity {

	private double targetCalories;

	private TextView tvRCTitle;
	private TextView tvRCMorning;
	private TextView tvRCNoon;
	private TextView tvRCNight;

	private Remind remindMorning;
	private Remind remindNoon;
	private Remind remindNight;

	private DragView dgRCSet;

	private String msgCalorie;

	private double x;
	private double y;
	private double z;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_setting_remind_calories);
		Bundle bundle = getIntent().getExtras();
		targetCalories = bundle.getDouble("TARGET_CALORIES");

		remindMorning = (Remind) bundle.getSerializable("REMIND_MORNING");
		remindNoon = (Remind) bundle.getSerializable("REMIND_NOON");
		remindNight = (Remind) bundle.getSerializable("REMIND_NIGHT");

		init();
		loadingData();
	}

	private void init() {
		showTitle(this.getString(R.string.setting_layout_remind));
		showLeftButton(this.getString(R.string.common_back), new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				SettingRemindCalories.this.finish();
			}
		});

		showRightButton(this.getString(R.string.common_complete), new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent mIntent = SettingRemindCalories.this.getIntent();
				Bundle mBundle = new Bundle();
				mBundle.putDouble("RATE_MORNING", x);
				mBundle.putDouble("RATE_NOON", y);
				mBundle.putDouble("RATE_NIGHT", z);

				mIntent.putExtras(mBundle);
				setResult(Constants.RESULT_REMAIN_COMPLETE, mIntent);
				SettingRemindCalories.this.finish();
			}
		});

		msgCalorie = getString(R.string.setting_remind_text3);

		tvRCTitle = (TextView) findViewById(R.id.tvRCTitle);

		tvRCMorning = (TextView) findViewById(R.id.tvRCMorning);
		tvRCNoon = (TextView) findViewById(R.id.tvRCNoon);
		tvRCNight = (TextView) findViewById(R.id.tvRCNight);

		dgRCSet = (DragView) findViewById(R.id.dgRCSet);

		dgRCSet.setOnDragChangeListener(new OnDragChangeListener() {

			@Override
			public void onChangeDragRight(double xRate, double yRate, double zRate) {
				changeTextData(xRate, yRate, zRate);
				x = xRate;
				y = yRate;
				z = zRate;
			}

			@Override
			public void onChangeDragLeft(double xRate, double yRate, double zRate) {
				changeTextData(xRate, yRate, zRate);
				x = xRate;
				y = yRate;
				z = zRate;
			}
		});

	}

	private void changeTextData(double xRate, double yRate, double zRate) {
		tvRCNoon.setText(msgCalorie.replaceAll("@calorie", String.valueOf(Util.cutNumber(targetCalories * yRate, 1))));
		tvRCNight.setText(msgCalorie.replaceAll("@calorie", String.valueOf(Util.cutNumber(targetCalories * zRate, 1))));
		tvRCMorning.setText(msgCalorie.replaceAll("@calorie", String.valueOf(Util.cutNumber(targetCalories * xRate, 1))));
	}

	private void loadingData() {
		String msgTitle = getString(R.string.setting_remind_text2);
		tvRCTitle.setText(msgTitle.replaceAll("@calorie", String.valueOf(targetCalories)));

		tvRCMorning.setText(msgCalorie.replaceAll("@calorie", remindMorning.getTargetCalorie()));
		tvRCNoon.setText(msgCalorie.replaceAll("@calorie", remindNoon.getTargetCalorie()));
		tvRCNight.setText(msgCalorie.replaceAll("@calorie", remindNight.getTargetCalorie()));

		dgRCSet.setRate(Double.parseDouble(remindMorning.getTargetRate()), Double.parseDouble(remindNoon.getTargetRate()),
				Double.parseDouble(remindNight.getTargetRate()));
		
		x = Double.parseDouble(remindMorning.getTargetRate());
		y = Double.parseDouble(remindNoon.getTargetRate());
		z = Double.parseDouble(remindNight.getTargetRate());

	}
}
