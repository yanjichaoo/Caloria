/* ==================================================
 * 产品名: 卡路里消耗管理
 * 文件名: HistoryFrame.java
 * --------------------------------------------------
 * 开发环境: JDK1.6
 * --------------------------------------------------
 * 修订履历  YYYY/MM/DD  REV.  备注
 *         2011/06/13  1.00  初版发行
 * --------------------------------------------------
 * (C) Copyright XIKANG CORPORATION 2011 All Rights Reserved.
 */package com.xikang.calorie.activity;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class HistoryFrame extends TabActivity {

	private TabHost mTabHost;
	private TabSpec mTabSpec;
	private Context mContext;

	private int page;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_history_frame);
		mTabHost = getTabHost();
		mContext = getBaseContext();
		createTabFrame();
		Bundle mBundle = getIntent().getExtras();
		page = mBundle.getInt("page");
		if (page >= 0 && page <= 2) {
			mTabHost.setCurrentTab(page);
		}
	}

	private void createTabFrame() {
		Intent mIntet;
		LinearLayout mLinearLayout;
		mIntet = new Intent(mContext, HistoryExercise.class);
		mLinearLayout = new LinearLayout(mContext);
		mLinearLayout.setBackgroundResource(R.drawable.history_tab_calorie_selector);
		addTab(mIntet, mLinearLayout, "HistoryCalories");

		mIntet = new Intent(mContext, HistoryTime.class);
		mLinearLayout = new LinearLayout(mContext);
		mLinearLayout.setBackgroundResource(R.drawable.history_tab_time_selector);
		addTab(mIntet, mLinearLayout, "HistoryTime");

		mIntet = new Intent(mContext, HistoryDistance.class);
		mLinearLayout = new LinearLayout(mContext);
		mLinearLayout.setBackgroundResource(R.drawable.history_tab_distance_selector);
		addTab(mIntet, mLinearLayout, "HistoryDiatance");

		// mIntet = new Intent(mContext, HistorySteps.class);
		// mLinearLayout = new LinearLayout(mContext);
		// mLinearLayout.setBackgroundResource(R.drawable.history_tab_stepnum_selector);
		// addTab(mIntet,mLinearLayout,"HistoryStepnnum");
	}

	private void addTab(Intent intent, View view, String str) {
		mTabSpec = mTabHost.newTabSpec(str).setIndicator(view).setContent(intent);
		mTabHost.addTab(mTabSpec);
	}
}