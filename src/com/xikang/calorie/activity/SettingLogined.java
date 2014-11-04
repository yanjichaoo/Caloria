/* ==================================================
 * 产品名: 卡路里消耗管理
 * 文件名: SettingLogined.java
 * --------------------------------------------------
 * 开发环境: JDK1.6
 * --------------------------------------------------
 * 修订履历  2011/06/13  REV.  备注
 *         2011/06/13  1.00  初版发行
 * --------------------------------------------------
 * (C) Copyright XIKANG CORPORATION 2011 All Rights Reserved.
 */
package com.xikang.calorie.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.xikang.calorie.common.Constants;
import com.xikang.calorie.db.DB_ConnectionPool;
import com.xikang.calorie.db.UserInfoService;
import com.xikang.calorie.domain.ProfileInfo;
import com.xikang.calorie.domain.UserInfo;

/**
 * 
 * 登录成功界面
 * 
 * <pre>
 * 
 * 2011/06/19, 曲磊
 * 
 * 修订履历：2011/07/06 曲磊  调整按钮功能去掉提醒选项
 * 修订履历：2011/08/10 曲磊  优化程序
 * 
 * </pre>
 * 
 * @author 曲磊
 * @version 1.00
 */
public class SettingLogined extends BaseActivity implements OnClickListener {

	// 按钮控件
	private Button btnSLSubmit;
	private Button btnSLModify;

	// 信息输入框
	private EditText etSLEmail;
	private EditText etSLUserName;
	private EditText etSLPhone;
	private EditText etSLWatch;

	private UserInfoService mUserInfoService;

	private boolean isModify;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_setting_logined);
		mUserInfoService = new UserInfoService(DB_ConnectionPool.getInstance(mContext));
		init();
	}

	@Override
	protected void onResume() {
		super.onResume();
		loadingData();
	}

	/**
	 * 初始化控件
	 */
	private void init() {

		showTitle(getString(R.string.setting_layout_logined));

		showLeftButton(getString(R.string.common_back), new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				SettingLogined.this.finish();
			}
		});

		showRightButton(getString(R.string.setting_logined_exit), new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				logout();
			}
		});

		btnSLSubmit = (Button) findViewById(R.id.btnSLSubmit);
		btnSLSubmit.setOnClickListener(this);
		btnSLSubmit.setVisibility(View.GONE);
		btnSLModify = (Button) findViewById(R.id.btnSLModify);
		btnSLModify.setOnClickListener(this);

		etSLEmail = (EditText) findViewById(R.id.etSLEmail);
		etSLUserName = (EditText) findViewById(R.id.etSLUserName);
		etSLPhone = (EditText) findViewById(R.id.etSLPhone);
		etSLWatch = (EditText) findViewById(R.id.etSLWatch);

	}

	/**
	 * 加载数据
	 */
	private void loadingData() {
		if (Constants.CURRENT_USER != null) {
			etSLEmail.setText(Constants.CURRENT_USER.getEmail());
			etSLUserName.setText(Constants.CURRENT_USER.getUserName());
			etSLPhone.setText(Constants.CURRENT_USER.getPhoneNum());
			etSLWatch.setText(Constants.CURRENT_USER.getWatchId());
		}
	}

	@Override
	public void onClick(View v) {
		int viewId = v.getId();
		switch (viewId) {

		case R.id.btnSLSubmit:
			if (isModify) {
				updateUserInfo();
			}
			break;
		case R.id.btnSLModify:
			isModify = !isModify;
			setEditModify(isModify);
			break;
		}

	}

	/**
	 * 修改信息设置控制状态
	 * 
	 * @param isEdit
	 */
	private void setEditModify(boolean isEdit) {
		etSLUserName.setEnabled(isEdit);
		etSLUserName.setFocusableInTouchMode(isEdit);
		etSLPhone.setEnabled(isEdit);
		etSLPhone.setFocusableInTouchMode(isEdit);
		etSLWatch.setEnabled(isEdit);
		etSLWatch.setFocusableInTouchMode(isEdit);
		if (isEdit) {
			btnSLSubmit.setVisibility(View.VISIBLE);
			btnSLModify.setText(R.string.common_cancel);
		} else {
			btnSLSubmit.setVisibility(View.GONE);
			btnSLModify.setText(R.string.setting_logined_modify);
		}
	}

	/**
	 * 注销用户
	 */
	private void logout() {
		showResult(getString(R.string.common_notice), getString(R.string.setting_logined_dialog_message),
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						mUserInfoService.deleteTable();
						Constants.CURRENT_USER = null;
						Constants.IS_LOGIN = false;
						ProfileInfo.clearCache();
						startActivity(new Intent(mContext, SettingAccount.class));
						SettingLogined.this.finish();
					}
				}, null);
	}

	/**
	 * 修改个人信息
	 */
	private void updateUserInfo() {
		UserInfo userInfo = new UserInfo();
		userInfo.setPersonId(Constants.CURRENT_USER.getPersonId());
		userInfo.setEmail(etSLEmail.getText().toString());
		userInfo.setPhoneNum(etSLPhone.getText().toString());
		userInfo.setUserName(etSLUserName.getText().toString());
		userInfo.setWatchId(etSLWatch.getText().toString());
		if (mXmlope.setUserInfo(userInfo)) {
			Constants.CURRENT_USER.setEmail(etSLEmail.getText().toString());
			Constants.CURRENT_USER.setPhoneNum(etSLPhone.getText().toString());
			Constants.CURRENT_USER.setUserName(etSLUserName.getText().toString());
			Constants.CURRENT_USER.setWatchId(etSLWatch.getText().toString());
			mUserInfoService.saveUserInfo(Constants.CURRENT_USER);
			isModify = false;
			setEditModify(isModify);
		} else {
			showToastShort("修改信息不正确");
		}

	}

}
