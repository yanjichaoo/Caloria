/* ==================================================
 * 产品名: 卡路里消耗管理
 * 文件名: Setting.java
 * --------------------------------------------------
 * 开发环境: JDK1.6
 * --------------------------------------------------
 * 修订履历  YYYY/MM/DD  REV.  备注
 *         2011/06/13  1.00  初版发行
 * --------------------------------------------------
 * (C) Copyright XIKANG CORPORATION 2011 All Rights Reserved.
 */
package com.xikang.calorie.activity;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xikang.calorie.common.ActivityJump;
import com.xikang.calorie.common.Constants;
import com.xikang.calorie.db.DB_ConnectionPool;
import com.xikang.calorie.db.RemindService;
import com.xikang.calorie.domain.Remind;

/**
 * 
 * 设置程序主窗体
 * 
 * <pre>
 * 
 * 2011/06/13, 曲磊
 * 
 * 修订履历：2011/06/18 曲磊  页面样式修改
 * 修订履历：2011/06/21 曲磊  登陆判断样式
 * 修订履历：2011/08/10 曲磊  优化程序
 * 
 * </pre>
 * 
 * @author 曲磊
 * @version 1.00
 */
public class Setting extends BaseActivity {

	private FrameLayout lyMSAppAbout; // 关于应用
	private FrameLayout lyMSAccount; // 账户管理
	private FrameLayout lyMSProfile; // 个人档案
	private FrameLayout lyMSRemind; // 运动提醒

	private TextView tvMSLeftProfile; // 个人档案
	private TextView tvMSLeftRemind; // 运动提醒

	private TextView tvMSAccount; // 账户管理
	private TextView tvMSRemind;

	private RemindService remindService;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_main_setting);
		remindService = new RemindService(DB_ConnectionPool.getInstance(mContext));
		init();
	}

	/**
	 * 初始化控件
	 */
	private void init() {
		showTitle(getString(R.string.setting_titile));

		tvMSLeftProfile = (TextView) findViewById(R.id.tvMSLeftProfile);
		tvMSLeftRemind = (TextView) findViewById(R.id.tvMSLeftRemind);

		tvMSAccount = (TextView) findViewById(R.id.tvMSAccount);

		tvMSRemind = (TextView) findViewById(R.id.tvMSRemind);

		lyMSAppAbout = (FrameLayout) findViewById(R.id.lyMSAppAbout);
		lyMSAppAbout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ActivityGroup ag = (ActivityGroup) Setting.this.getParent();
				ActivityJump.JumpSettingIntroduce(ag, (LinearLayout) ag.getWindow().findViewById(R.id.layoutAG));
			}
		});

		lyMSAccount = (FrameLayout) findViewById(R.id.lyMSAccount);
		lyMSAccount.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (Constants.IS_LOGIN) {
					startActivity(new Intent(Setting.this, SettingLogined.class));
				} else {
					startActivity(new Intent(Setting.this, SettingAccount.class));
				}

			}
		});

		lyMSProfile = (FrameLayout) findViewById(R.id.lyMSProfile);
		lyMSProfile.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(mContext, SettingProfile.class));
			}
		});

		lyMSRemind = (FrameLayout) findViewById(R.id.lyMSRemind);
		lyMSRemind.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(mContext, SettingRemind.class));

			}

		});
	}


	@Override
	protected void onStart() {
		super.onStart();
		loadingData();
	}

	/**
	 * 账户登陆判断
	 */
	private void loadingData() {
		if (!Constants.IS_LOGIN) {// 未登录
			lyMSProfile.setEnabled(false);
			lyMSRemind.setEnabled(false);
			tvMSLeftProfile.setEnabled(false);
			tvMSLeftRemind.setEnabled(false);
			tvMSAccount.setText("");
			tvMSRemind.setText("");
		} else {
			lyMSProfile.setEnabled(true);
			lyMSRemind.setEnabled(true);
			tvMSLeftProfile.setEnabled(true);
			tvMSLeftRemind.setEnabled(true);
			tvMSAccount.setText(R.string.setting_account_login);
			
			if(isRemind()){
				tvMSRemind.setText(R.string.setting_remind_set);
			}else{
				tvMSRemind.setText(R.string.setting_remind_close);
			}
		}
	}

	private boolean isRemind() {

		Remind remindMorning = remindService.getCurrentRemind("1");
		Remind remindNoon = remindService.getCurrentRemind("2");
		Remind remindNight = remindService.getCurrentRemind("3");
		
		if (remindMorning == null && remindNoon == null && remindNight == null) {
			return false;
		}
		if (remindMorning != null) {
			if (checkData(remindMorning.getRemindingEnabled())) {
				return true;
			}
		}
		if (remindNoon != null) {
			if (checkData(remindNoon.getRemindingEnabled())) {
				return true;
			}
		}
		if (remindNight != null) {
			if (checkData(remindNight.getRemindingEnabled())) {
				return true;
			}
		}
		return false;
	}

	private boolean checkData(String remind) {
		boolean result = false;
		if (remind != null) {
			if ("1".equals(remind)) {
				result = true;
			}
		}
		return result;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return false;
	}

}