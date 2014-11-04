/* ==================================================
 * 产品名: 卡路里消耗管理
 * 文件名: SettingProfile.java
 * --------------------------------------------------
 * 开发环境: JDK1.6
 * --------------------------------------------------
 * 修订履历  2011/06/13  REV.  备注
 *         2011/06/13  1.00  初版发行
 * --------------------------------------------------
 * (C) Copyright XIKANG CORPORATION 2011 All Rights Reserved.
 */
package com.xikang.calorie.activity;

import java.util.Date;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.xikang.calorie.common.Constants;
import com.xikang.calorie.common.Util;
import com.xikang.calorie.db.CityService;
import com.xikang.calorie.db.DB_ConnectionPool;
import com.xikang.calorie.domain.City;
import com.xikang.calorie.domain.ProfileInfo;
import com.xikang.calorie.view.CityView;

/**
 * 
 * 个人档案设置界面
 * 
 * 
 * <pre>
 * 
 * 2011/06/13, 曲磊
 * 
 * 修订履历：YYYY/MM/DD 修订者  修订内容
 * 
 * 修订履历：2011/06/15 曲磊 个人档案设置页数据添加UI控件
 * 修订履历：2011/06/18 曲磊 数据加载功能，页面样式修改
 * 修订履历：2011/06/21 曲磊 城市数据刷新功能添加和提交功能添加
 * 修订履历：2011/08/10 曲磊 优化程序
 * 
 * </pre>
 * 
 * @author 曲磊
 * @version 1.00
 */
public class SettingProfile extends BaseActivity implements OnClickListener {

	// 按钮控件
	private FrameLayout lytSPBirthday;
	private FrameLayout lytSPSex;
	private FrameLayout lytSPHeight;
	private FrameLayout lytSPWeight;
	private FrameLayout lytSPTargetWeight;
	private FrameLayout lytSPCityCode;
	private FrameLayout lytSPVitality;

	// 文本控件
	private TextView tvSPBirthday;
	private TextView tvSPSex;
	private TextView tvSPHeight;
	private TextView tvSPWeight;
	private TextView tvSPCityCode;
	private TextView tvSPTargetWeight;
	private TextView tvSPVitality;

	// 居住地选择控件
	private CityView mCityView;

	private LayoutInflater layoutFactory;
	private CityService mCityService;

	private City city;

	private static final int BIRTHDAY_DIALOG_ID = 1;
	private static final int SEX_DIALOG_ID = 2;
	private static final int HEIGHT_DIALOG_ID = 3;
	private static final int WEIGHT_DIALOG_ID = 4;
	private static final int TARGETWEIGHT_DIALOG_ID = 5;
	private static final int CITYCODE_DIALOG_ID = 6;
	private static final int VITALITY_DIALOG_ID = 7;

	private ProfileInfo mProfileInfo;

	private int btnIndex;// 标记输入选择的位置

	private static final int HANDLER_MESSAGE_SUCCESS = 8001;
	private static final int HANDLER_MESSAGE_FAILED = 8002;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_setting_profile);
		layoutFactory = LayoutInflater.from(mContext);
		mCityService = new CityService(DB_ConnectionPool.getInstance(mContext));
		init();

		showProgress(getString(R.string.setting_progress_message_loading));
		Thread thread = new Thread(runLoadData);
		thread.start();
	}

	/**
	 * 初始化控件
	 */
	private void init() {
		showTitle(getString(R.string.setting_layout_profile));

		showLeftButton(getString(R.string.common_back), new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				SettingProfile.this.finish();
			}
		});

		showRightButton(getString(R.string.common_refresh), new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				showResult(getString(R.string.common_notice), getString(R.string.profile_city_dialog_message),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								showProgress(getString(R.string.setting_progress_message_data));
								new Thread() {
									public void run() {
										try {
											if (updateCity()) {
												mCityView.setListViewData("0");
												handlerMessage.sendEmptyMessage(HANDLER_MESSAGE_SUCCESS);
											} else {
												handlerMessage.sendEmptyMessage(HANDLER_MESSAGE_FAILED);
											}
										} catch (Exception e) {
											handlerMessage.sendEmptyMessage(HANDLER_MESSAGE_FAILED);
										}
										closeProgress();
									}
								}.start();
							}
						}, null);
			}
		});

		mCityView = new CityView(mContext, mCityService);

		lytSPBirthday = (FrameLayout) findViewById(R.id.lytSPBirthday);
		lytSPBirthday.setOnClickListener(this);
		lytSPSex = (FrameLayout) findViewById(R.id.lytSPSex);
		lytSPSex.setOnClickListener(this);
		lytSPHeight = (FrameLayout) findViewById(R.id.lytSPHeight);
		lytSPHeight.setOnClickListener(this);
		lytSPWeight = (FrameLayout) findViewById(R.id.lytSPWeight);
		lytSPWeight.setOnClickListener(this);
		lytSPTargetWeight = (FrameLayout) findViewById(R.id.lytSPTargetWeight);
		lytSPTargetWeight.setOnClickListener(this);
		lytSPCityCode = (FrameLayout) findViewById(R.id.lytSPCityCode);
		lytSPCityCode.setOnClickListener(this);
		lytSPVitality = (FrameLayout) findViewById(R.id.lytSPVitality);
		lytSPVitality.setOnClickListener(this);

		tvSPBirthday = (TextView) findViewById(R.id.tvSPBirthday);
		tvSPSex = (TextView) findViewById(R.id.tvSPSex);
		tvSPHeight = (TextView) findViewById(R.id.tvSPHeight);
		tvSPWeight = (TextView) findViewById(R.id.tvSPWeight);
		tvSPCityCode = (TextView) findViewById(R.id.tvSPCityCode);
		tvSPTargetWeight = (TextView) findViewById(R.id.tvSPTargetWeight);
		tvSPVitality = (TextView) findViewById(R.id.tvSPVitality);

	}

	/**
	 * 更新城市列表
	 * 
	 * @return
	 */
	private boolean updateCity() {
		return mCityService.updateTable(mXmlope.loadCityData());
	}

	Runnable runLoadData = new Runnable() {
		@Override
		public void run() {
			if (Constants.CURRENT_USER != null) {
				Constants.CURRENT_PROFILE = ProfileInfo.getInstance(mXmlope, Constants.CURRENT_USER.getPersonId());
			}
			mProfileInfo = Constants.CURRENT_PROFILE;
			String parentId = "0";
			if (mProfileInfo != null) {
				city = mCityService.getCity(mProfileInfo.getCityCode());
				if (city != null) {
					parentId = city.getParentId();
				}
			}
			mCityView.setListViewData(parentId);
			Message msg = handlerMessage.obtainMessage();
			msg.what = LOADING_SCUESS;
			handlerMessage.sendMessage(msg);

		}
	};

	/**
	 * 加载数据
	 */
	private void loadingData() {
		if (mProfileInfo != null) {
			if (mProfileInfo.getSex().equals("")) {
				mProfileInfo.setSex("1");
			}
			if (mProfileInfo.getVitality().equals("")) {
				mProfileInfo.setVitality("1");
			}
			tvSPBirthday.setText(mProfileInfo.getBirthday());

			tvSPSex.setText(findArrayItem(getResources().getTextArray(R.array.array_profile_sex),
					Integer.valueOf(mProfileInfo.getSex()) - 1));
			tvSPHeight.setText(mProfileInfo.getHeight() + getString(R.string.profile_height_unit));
			tvSPWeight.setText(mProfileInfo.getWeight() + getString(R.string.profile_weight_unit));

			if (city != null) {
				tvSPCityCode.setText(city.getName());
			}
			tvSPTargetWeight.setText(mProfileInfo.getTargetWeight() + getString(R.string.profile_weight_unit));
			tvSPVitality.setText(findArrayItem(getResources().getTextArray(R.array.array_profile_vitality),
					Integer.valueOf(mProfileInfo.getVitality()) - 1));
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {

		switch (id) {

		// 生日设置对话框
		case BIRTHDAY_DIALOG_ID:
			Date date;
			if (mProfileInfo.getBirthday().equals("")) {
				date = new Date();
			} else {
				date = Util.parseDate(mProfileInfo.getBirthday());
			}
			DatePickerDialog dateDialog = new DatePickerDialog(this,
					new DatePickerDialog.OnDateSetListener() {
						public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
							String birthday = Util.getDate(year, monthOfYear + 1, dayOfMonth);
							tvSPBirthday.setText(birthday);
							mProfileInfo.setBirthday(birthday);
							Constants.CURRENT_PROFILE.setBirthday(birthday);
							mXmlope.setProfileInfo(Constants.CURRENT_USER.getPersonId(), mProfileInfo);
						}
					}, nowYear(date.getYear()), date.getMonth(), date.getDate()) {
				@Override
				public void onDateChanged(DatePicker view, int year, int month, int day) {
					this.setTitle(R.string.profile_birthday);
				}
			};
			dateDialog.setTitle(R.string.profile_birthday);
			return dateDialog;

			// 性别设置对话框
		case SEX_DIALOG_ID:
			btnIndex = Integer.valueOf(mProfileInfo.getSex()) - 1;
			return new AlertDialog.Builder(this).setIcon(null).setTitle(R.string.profile_sex)
					.setSingleChoiceItems(R.array.array_profile_sex, btnIndex, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
							btnIndex = whichButton;
						}
					}).setPositiveButton(R.string.common_ok, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
							tvSPSex.setText(findArrayItem(getResources().getTextArray(R.array.array_profile_sex),
									btnIndex));
							mProfileInfo.setSex(String.valueOf(btnIndex + 1));
							Constants.CURRENT_PROFILE.setSex(String.valueOf(btnIndex + 1));
							mXmlope.setProfileInfo(Constants.CURRENT_USER.getPersonId(), mProfileInfo);
							dialog.dismiss();
						}
					}).setNegativeButton(R.string.common_cancel, null).create();
			// 身高设置对话框
		case HEIGHT_DIALOG_ID:
			View viewHeight = layoutFactory.inflate(R.layout.layout_setting_dialog_input, null);
			final EditText etHeight = (EditText) viewHeight.findViewById(R.id.etSDinput);
			etHeight.setHint(R.string.profile_height_unit);
			etHeight.setText(mProfileInfo.getHeight());
			return new AlertDialog.Builder(this).setTitle(R.string.profile_height).setView(viewHeight)
					.setPositiveButton(R.string.common_ok, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
							tvSPHeight.setText(etHeight.getText() + getString(R.string.profile_height_unit));
							mProfileInfo.setHeight(etHeight.getText().toString());
							Constants.CURRENT_PROFILE.setHeight(etHeight.getText().toString());
							mXmlope.setProfileInfo(Constants.CURRENT_USER.getPersonId(), mProfileInfo);
							dialog.dismiss();

						}
					}).setNegativeButton(R.string.common_cancel, null).create();
			// 体重设置对话框
		case WEIGHT_DIALOG_ID:
			View viewWeight = layoutFactory.inflate(R.layout.layout_setting_dialog_input, null);
			final EditText etWeight = (EditText) viewWeight.findViewById(R.id.etSDinput);
			etWeight.setHint(R.string.profile_weight_unit);
			etWeight.setText(mProfileInfo.getWeight());
			return new AlertDialog.Builder(this).setTitle(R.string.profile_weight).setView(viewWeight)
					.setPositiveButton(R.string.common_ok, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
							tvSPWeight.setText(etWeight.getText() + getString(R.string.profile_weight_unit));
							mProfileInfo.setWeight(etWeight.getText().toString());
							Constants.CURRENT_PROFILE.setWeight(etWeight.getText().toString());
							mXmlope.setProfileInfo(Constants.CURRENT_USER.getPersonId(), mProfileInfo);
						}
					}).setNegativeButton(R.string.common_cancel, null).create();
			// 居住地设置对话框
		case CITYCODE_DIALOG_ID:
			return new AlertDialog.Builder(this).setTitle(R.string.profile_city).setView(mCityView)
					.setPositiveButton(R.string.common_ok, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {

							city = mCityView.getSelectedCity();
							mProfileInfo.setCityCode(city.getCode());
							Constants.CURRENT_PROFILE.setCityCode(city.getCode());
							mXmlope.setProfileInfo(Constants.CURRENT_USER.getPersonId(), mProfileInfo);
							tvSPCityCode.setText(city.getName());
						}
					}).setNegativeButton(R.string.common_cancel, null).create();
			// 目标体重设置对话框
		case TARGETWEIGHT_DIALOG_ID:
			View viewTargetWeight = layoutFactory.inflate(R.layout.layout_setting_dialog_input, null);
			final EditText etTargetWeight = (EditText) viewTargetWeight.findViewById(R.id.etSDinput);
			etTargetWeight.setHint(R.string.profile_weight_unit);
			etTargetWeight.setText(mProfileInfo.getTargetWeight());
			return new AlertDialog.Builder(this).setTitle(R.string.profile_targetweight).setView(viewTargetWeight)
					.setPositiveButton(R.string.common_ok, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
							tvSPTargetWeight.setText(etTargetWeight.getText() + getString(R.string.profile_weight_unit));
							mProfileInfo.setTargetWeight(etTargetWeight.getText().toString());
							Constants.CURRENT_PROFILE.setTargetWeight(etTargetWeight.getText().toString());
							mXmlope.setProfileInfo(Constants.CURRENT_USER.getPersonId(), mProfileInfo);
						}
					}).setNegativeButton(R.string.common_cancel, null).create();
			// 活动情况设置对话框
		case VITALITY_DIALOG_ID:
			btnIndex = Integer.valueOf(mProfileInfo.getVitality()) - 1;
			return new AlertDialog.Builder(this)
					.setTitle(R.string.profile_vitality)
					.setSingleChoiceItems(R.array.array_profile_vitality, btnIndex,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int whichButton) {
									btnIndex = whichButton;
								}
							}).setPositiveButton(R.string.common_ok, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
							tvSPVitality.setText(findArrayItem(
									getResources().getTextArray(R.array.array_profile_vitality), btnIndex));
							mProfileInfo.setVitality(String.valueOf(btnIndex + 1));
							Constants.CURRENT_PROFILE.setVitality(String.valueOf(btnIndex + 1));
							mXmlope.setProfileInfo(Constants.CURRENT_USER.getPersonId(), mProfileInfo);
							dialog.dismiss();
						}
					}).setNegativeButton(R.string.common_cancel, null).create();
		}

		return null;
	}

	@Override
	public void onClick(View v) {
		int viewId = v.getId();
		switch (viewId) {
		case R.id.lytSPBirthday:
			showDialog(BIRTHDAY_DIALOG_ID);
			break;
		case R.id.lytSPSex:
			showDialog(SEX_DIALOG_ID);
			break;
		case R.id.lytSPHeight:
			showDialog(HEIGHT_DIALOG_ID);
			break;
		case R.id.lytSPWeight:
			showDialog(WEIGHT_DIALOG_ID);
			break;
		case R.id.lytSPTargetWeight:
			showDialog(TARGETWEIGHT_DIALOG_ID);
			break;
		case R.id.lytSPCityCode:
			if (city != null && !city.getParentId().equals("")) {
				mCityView.setListViewData(city.getParentId());
				mCityView.selectCity(city);
			}
			showDialog(CITYCODE_DIALOG_ID);
			break;
		case R.id.lytSPVitality:
			showDialog(VITALITY_DIALOG_ID);
			break;
		}
	}

	/**
	 * 线程消息处理
	 */
	Handler handlerMessage = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case HANDLER_MESSAGE_SUCCESS:
				showToastLong(getString(R.string.profile_progress_message_success));
				break;
			case HANDLER_MESSAGE_FAILED:
				showToastLong(getString(R.string.profile_progress_message_failed));
				break;
			case LOADING_SCUESS:
				loadingData();
				closeProgress();
				break;
			}
		}
	};

	/**
	 * 获取资源数组的字符
	 * 
	 * @param array
	 * @param index
	 * @return
	 */
	private String findArrayItem(CharSequence[] array, int index) {
		int len = array.length;
		if (index < len || index > 0) {
			return array[index].toString();
		} else {
			return null;
		}
	}

	/**
	 * 年份换算
	 * 
	 * @param year
	 * @return
	 */
	private int nowYear(int year) {
		return year + 1900;
	}
}