/* ==================================================
 * 产品名: 卡路里消耗管理
 * 文件名: MainBMI.java
 * --------------------------------------------------
 * 开发环境: JDK1.6
 * --------------------------------------------------
 * 修订履历  YYYY/MM/DD  REV.  备注
 *         2011/06/13  1.00  初版发行
 * --------------------------------------------------
 * (C) Copyright XIKANG CORPORATION 2011 All Rights Reserved.
 */
package com.xikang.calorie.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.xikang.calorie.common.Constants;
import com.xikang.calorie.common.Util;
import com.xikang.calorie.domain.ProfileInfo;
import com.xikang.calorie.view.AxisView;
import com.xikang.calorie.view.AxisView.OnAxisChangeListener;

/**
 * 
 * BMI界面
 * 
 * <pre>
 * 
 * 2011/07/03, 曲磊
 * 
 * 修订履历：2011/08/09 曲磊  添加加载进度框
 * 
 * </pre>
 * 
 * @author 曲磊
 * @version 1.00
 */
public class MainBMI extends BaseActivity {
	
	protected static final int SAVE_SCUESS = 8001;
	protected static final int SAVE_FAILED = 8002;

	private TextView tvMBWeightTips;
	private TextView tvMBWeightIdealize;
	private TextView tvMBWeightCurrent;
	private TextView tvMBWeightValue;

	private TextView tvMBCurrentHeight;
	private TextView tvMBCurrentWeight;

	private AxisView mAxisView;
	private ImageView ivMBPerson;

	private int height = 178;
	private double weight = 70;

	private Thread thread;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_main_bmi);
		init();
		showProgress(getString(R.string.setting_progress_message_loading));
		thread = new Thread(runLoadData);
		thread.start();
	}

	/**
	 * 初始化控件
	 */
	private void init() {
		showTitle(getString(R.string.BMI_title));

		showLeftButton(getString(R.string.common_back), new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				MainBMI.this.finish();
			}
		});

		showRightButton(getString(R.string.common_save), new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (Constants.IS_LOGIN) {
					showProgress(getString(R.string.setting_progress_message_saving));
					thread = new Thread(runSaveData);
					thread.start();
				}

			}
		});

		tvMBWeightTips = (TextView) findViewById(R.id.tvMBWeightTips);
		tvMBWeightIdealize = (TextView) findViewById(R.id.tvMBWeightIdealize);
		tvMBWeightCurrent = (TextView) findViewById(R.id.tvMBWeightCurrent);
		tvMBWeightValue = (TextView) findViewById(R.id.tvMBWeightValue);

		tvMBCurrentHeight = (TextView) findViewById(R.id.tvMBCurrentHeight);
		tvMBCurrentWeight = (TextView) findViewById(R.id.tvMBCurrentWeight);

		ivMBPerson = (ImageView) findViewById(R.id.ivMBPerson);

		mAxisView = (AxisView) findViewById(R.id.avMB);
		mAxisView.setOnAxisChangeListener(new OnAxisChangeListener() {

			@Override
			public void onStopSeekBatX(AxisView axisView) {
				mAxisView.setxSeekBitmap(BitmapFactory.decodeResource(MainBMI.this.getResources(),
						R.drawable.main_bmi_x_seek_bg));
			}

			@Override
			public void onStopSeekBarY(AxisView axisView) {
				mAxisView.setySeekBitmap(BitmapFactory.decodeResource(MainBMI.this.getResources(),
						R.drawable.main_bmi_y_seek_bg));
			}

			@Override
			public void onStartSeekBatX(AxisView axisView) {
				mAxisView.setxSeekBitmap(BitmapFactory.decodeResource(MainBMI.this.getResources(),
						R.drawable.main_bmi_x_seek_over));

			}

			@Override
			public void onStartSeekBarY(AxisView axisView) {
				mAxisView.setySeekBitmap(BitmapFactory.decodeResource(MainBMI.this.getResources(),
						R.drawable.main_bmi_y_seek_over));
			}

			@Override
			public void onChangeAxisY(AxisView axisView, double progress, boolean isSelected) {
				slideBMI(axisView.getyStartPoint(), axisView.getxStartPoint());
				changeText(axisView.getyStartPoint(), axisView.getxStartPoint());

				height = (int) axisView.getyStartPoint();
				tvMBCurrentHeight.setText(String.valueOf(Util.cutNumber(axisView.getyStartPoint() * 0.01, 2)));
			}

			@Override
			public void onChangeAxisX(AxisView axisView, double progress, boolean isSelected) {
				slideBMI(axisView.getyStartPoint(), axisView.getxStartPoint());
				changeText(axisView.getyStartPoint(), axisView.getxStartPoint());
				weight = axisView.getxStartPoint();
				tvMBCurrentWeight.setText(String.valueOf(axisView.getxStartPoint()));
			}
		});
	}
	

	
	/**
	 * 保存数据线程
	 */
	private Runnable runSaveData = new Runnable() {
		
		@Override
		public void run() {
			
			ProfileInfo mProfileInfo = Constants.CURRENT_PROFILE;
			mProfileInfo.setHeight(String.valueOf(height));
			mProfileInfo.setWeight(String.valueOf(weight));
			String status =  mXmlope.setProfileInfo(Constants.CURRENT_USER.getPersonId(), mProfileInfo);
			Constants.CURRENT_PROFILE.setHeight(String.valueOf(height));
			Constants.CURRENT_PROFILE.setWeight(String.valueOf(weight));
			
			Message msg = handler.obtainMessage();
			if(!status.equals("1")){
				msg.what = SAVE_FAILED;
			}
			handler.sendMessage(msg);
			
		}
	};
	
	/**
	 * 加载线程处理
	 */
	private Runnable runLoadData = new Runnable() {

		@Override
		public void run() {
			if (Constants.CURRENT_USER != null) {
				Constants.CURRENT_PROFILE = ProfileInfo.getInstance(mXmlope, Constants.CURRENT_USER.getPersonId());
			}

			Message msg = handler.obtainMessage();
			msg.what = LOADING_SCUESS;
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
				loadData();
				closeProgress();
				break;
			case SAVE_FAILED:
				showResult(getString(R.string.common_save),getString(R.string.common_failed));
				closeProgress();
				break;
			default:
				closeProgress();
				break;
			}
		}
	};

	/**
	 * 加载数据
	 */
	private void loadData() {
		if (Constants.CURRENT_PROFILE != null) {
			if (!Constants.CURRENT_PROFILE.getHeight().equals("0")) {
				height = (int) Double.parseDouble(Constants.CURRENT_PROFILE.getHeight());

			}
			if (!Constants.CURRENT_PROFILE.getWeight().equals("0")) {
				weight = Double.parseDouble(Constants.CURRENT_PROFILE.getWeight());
			}
		}
		mAxisView.setyStartPoint(height);
		mAxisView.setxStartPoint(weight);
		slideBMI(height, weight);
		changeText(height, weight);
		tvMBCurrentHeight.setText(String.valueOf(Util.cutNumber(height * 0.01, 2)));
		tvMBCurrentWeight.setText(String.valueOf(weight));
	}

	private double calculateBMI(double height, double weight) {
		height = height * 0.01;
		double dividend = height * height;
		double result = Util.cutNumber(weight / dividend, 1);
		return result;
	}

	private int checkPicBMI(double bmi) {
		if (bmi > 0 && bmi < 18.5) {
			return R.drawable.main_bmi_person_04;
		}
		if (bmi >= 24 && bmi < 28) {
			return R.drawable.main_bmi_person_02;
		}
		if (bmi >= 28) {
			return R.drawable.main_bmi_person_03;
		}
		if (bmi >= 18.5 && bmi < 24) {
			return R.drawable.main_bmi_person_01;
		}
		return R.drawable.main_bmi_person_04;
	}

	private void changeText(double height, double weight) {
		weight = Util.cutNumber(weight, 1);
		double bmi = calculateBMI(height, weight);
		double idealizeWeight = idealizeBMI(height);

		if (bmi > 0 && bmi < 18.5) {
			tvMBWeightTips.setText(R.string.BMI_lighter_weight);

			tvMBWeightValue.setText("(" + getString(R.string.BMI_string_4)
					+ String.valueOf(Util.cutNumber(idealizeWeight - weight, 1)) + getString(R.string.BMI_string_3) + ")");

			tvMBWeightValue.setTextColor(0xFF3399cc);
			tvMBWeightTips.setTextColor(0xFF3399cc);
			tvMBWeightCurrent.setTextColor(0xFF3399cc);
			tvMBWeightIdealize.setTextColor(0xFF3399cc);
		}
		if (bmi >= 24 && bmi < 28) {
			tvMBWeightTips.setText(R.string.BMI_heavy_weight);

			tvMBWeightValue.setText("(" + getString(R.string.BMI_string_5)
					+ String.valueOf(Util.cutNumber(weight - idealizeWeight, 1)) + getString(R.string.BMI_string_3) + ")");

			tvMBWeightValue.setTextColor(0xFFf7c80d);
			tvMBWeightTips.setTextColor(0xFFf7c80d);
			tvMBWeightCurrent.setTextColor(0xFFf7c80d);
			tvMBWeightIdealize.setTextColor(0xFFf7c80d);
		}
		if (bmi >= 28) {
			tvMBWeightTips.setText(R.string.BMI_fat_weight);

			tvMBWeightValue.setText("(" + getString(R.string.BMI_string_5)
					+ String.valueOf(Util.cutNumber(weight - idealizeWeight, 1)) + getString(R.string.BMI_string_3) + ")");

			tvMBWeightValue.setTextColor(0xFFdf7a28);
			tvMBWeightTips.setTextColor(0xFFdf7a28);
			tvMBWeightCurrent.setTextColor(0xFFdf7a28);
			tvMBWeightIdealize.setTextColor(0xFFdf7a28);
		}
		if (bmi >= 18.5 && bmi < 24) {
			tvMBWeightTips.setText(R.string.BMI_normal_weight);
			tvMBWeightValue.setText("(" + getString(R.string.BMI_string_6) + ")");

			tvMBWeightValue.setTextColor(0xFF81bd24);
			tvMBWeightTips.setTextColor(0xFF81bd24);
			tvMBWeightCurrent.setTextColor(0xFF81bd24);
			tvMBWeightIdealize.setTextColor(0xFF81bd24);
		}

		tvMBWeightCurrent.setText(String.valueOf(bmi));
		tvMBWeightIdealize.setText(String.valueOf(idealizeWeight));
	}

	private double idealizeBMI(double height) {
		height = height / 100;
		double result = Util.cutNumber((double) 21.2 * height * height,1);
		return result;
	}

	private void slideBMI(double height, double weight) {
		Bitmap bit = BitmapFactory.decodeResource(this.getResources(), checkPicBMI(calculateBMI(height, weight)));
		int makeWidth = bit.getWidth() + ((int) weight - 85);
		int makeHeight = bit.getHeight() + ((int) height - 200);
		ivMBPerson.setImageBitmap(makeImg(makeWidth, makeHeight, bit));
	}

	private Bitmap makeImg(int makeWidth, int makeHeight, Bitmap makeBit) {
		if (makeBit != null) {
			float scaleWidth = ((float) makeWidth) / makeBit.getWidth();
			float scaleHeight = ((float) makeHeight) / makeBit.getHeight();
			Matrix matrix = new Matrix();
			matrix.postScale(scaleWidth, scaleHeight);
			Bitmap resizedBitmap = Bitmap.createBitmap(makeBit, 0, 0, makeBit.getWidth(), makeBit.getHeight(), matrix,
					true);
			return resizedBitmap;
		} else {
			return null;
		}
	}
}