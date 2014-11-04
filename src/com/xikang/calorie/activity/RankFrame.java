/* ==================================================
 * 产品名: 卡路里管理
 * 文件名: RankFrame.java
 * --------------------------------------------------
 * 开发环境: JDK1.6
 * --------------------------------------------------
 * 修订履历  YYYY/MM/DD  REV.  备注
 *         2011/06/16  1.00  初版发行
 * --------------------------------------------------
 * (C) Copyright XIKANG CORPORATION 2011 All Rights Reserved.
 */
package com.xikang.calorie.activity;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

import com.xikang.calorie.common.Util;

public class RankFrame extends TabActivity {

	private TabHost mTabHost;
	private TabSpec mTabSpec;
	private Context mContext;

	private int page;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_rank_frame);
		mTabHost = getTabHost();
		mTabHost.setOnTabChangedListener(new OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {
				int tabNum=0;
				 if (tabId.equals("RankCtiy")) {
					 tabNum=1;
				} else if (tabId.equals("RankGroup")) {
					tabNum=2;
				}
				 
				if (Util.rankList.size() > tabNum) {
					page=tabNum;
				}
				mTabHost.setCurrentTab(page);
			}
		});
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
		mIntet = new Intent(mContext, RankCalorie.class);
		mLinearLayout = new LinearLayout(mContext);
		mLinearLayout.setBackgroundResource(R.drawable.rank_tab_all_selector);
		addTab(mIntet, mLinearLayout, "RankCalories");
		mIntet = new Intent(mContext, RankCity.class);
		mLinearLayout = new LinearLayout(mContext);
		mLinearLayout.setBackgroundResource(R.drawable.rank_tab_city_selector);
		addTab(mIntet, mLinearLayout, "RankCtiy");
		mIntet = new Intent(mContext, RankGroup.class);
		mLinearLayout = new LinearLayout(mContext);
		mLinearLayout.setBackgroundResource(R.drawable.rank_tab_group_selector);
		addTab(mIntet, mLinearLayout, "RankGroup");
	}

	private void addTab(Intent intent, View view, String str) {
		mTabSpec = mTabHost.newTabSpec(str).setIndicator(view).setContent(intent);
		mTabHost.addTab(mTabSpec);
	}
}