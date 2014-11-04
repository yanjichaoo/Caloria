/* ==================================================
 * 产品名: 卡路里消耗管理
 * 文件名: MainFrame.java
 * --------------------------------------------------
 * 开发环境: JDK1.6
 * --------------------------------------------------
 * 修订履历  2011/06/13  REV.  备注
 *         2011/06/13  1.00  初版发行
 * --------------------------------------------------
 * (C) Copyright XIKANG CORPORATION 2011 All Rights Reserved.
 */
package com.xikang.calorie.activity;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

import com.xikang.calorie.common.Constants;

/**
 * 
 * 框架Tab页
 * 
 * 
 * <pre>
 * 
 * 2011/06/13, 曲磊
 * 
 * 修订履历：YYYY/MM/DD 修订者  修订内容
 * 
 * 2011/06/15 曲磊  添加Tab按钮效果
 * 
 * </pre>
 * 
 * @author 曲磊
 * @version 1.00
 */
public class MainFrame extends TabActivity {

	private TabHost mTabHost;
	private TabSpec mTabSpec;
	private Context mContext;
	private Boolean isExit = false;

	private Handler handler = new Handler();
	Runnable runExit = new Runnable() {
		@Override
		public void run() {
			isExit = false;
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_main_frame);
		mContext = getBaseContext();
		mTabHost = getTabHost();
		createTabFrame();
	}

	private void createTabFrame() {

		Intent mIntet;
		LinearLayout mLinearLayout;

		mIntet = new Intent(mContext, MainCalories.class);
		mLinearLayout = new LinearLayout(mContext);
		mLinearLayout.setBackgroundResource(R.drawable.main_tab_home_selector);
		addTab(mIntet, mLinearLayout, "MainCalories");

		mIntet = new Intent(mContext, MainTop.class);
		mLinearLayout = new LinearLayout(mContext);
		mLinearLayout.setBackgroundResource(R.drawable.main_tab_top_selector);
		addTab(mIntet, mLinearLayout, "MainTop");

		mIntet = new Intent(mContext, MainHistory.class);
		mLinearLayout = new LinearLayout(mContext);
		mLinearLayout.setBackgroundResource(R.drawable.main_tab_history_selector);
		addTab(mIntet, mLinearLayout, "MainHistory");

		mIntet = new Intent(mContext, MainActions.class);
		mLinearLayout = new LinearLayout(mContext);
		mLinearLayout.setBackgroundResource(R.drawable.main_tab_activity_selector);
		addTab(mIntet, mLinearLayout, "MainActions");

		mIntet = new Intent(mContext, MainSetting.class);
		mLinearLayout = new LinearLayout(mContext);
		mLinearLayout.setBackgroundResource(R.drawable.main_tab_setting_selector);
		addTab(mIntet, mLinearLayout, "MainSetting");

	}

	private void addTab(Intent intent, View view, String str) {
		mTabSpec = mTabHost.newTabSpec(str).setIndicator(view).setContent(intent);
		mTabHost.addTab(mTabSpec);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (isExit == false) {
				isExit = true;
				Toast.makeText(this, getString(R.string.common_exit_message), Toast.LENGTH_SHORT).show();
				handler.postDelayed(runExit, 2000);
			} else {
				loginOut();
				this.finish();
			}
		}
		return false;
	}

	private void loginOut() {
		Constants.IS_LOGIN = false; 
		Constants.CURRENT_USER = null; 
		Constants.CURRENT_PROFILE = null; 
		Constants.sportTitleList = null;
		Constants.historyTimeResult = null;
		System.gc();
	}

}