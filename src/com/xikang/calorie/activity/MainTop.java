/* ==================================================
 * 产品名: 卡路里管理
 * 文件名: MainTop.java
 * --------------------------------------------------
 * 开发环境: JDK1.6
 * --------------------------------------------------
 * 修订履历  YYYY/MM/DD  REV.  备注
 *         2011/06/13  1.00  初版发行
 * --------------------------------------------------
 * (C) Copyright XIKANG CORPORATION 2011 All Rights Reserved.
 */
package com.xikang.calorie.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xikang.calorie.common.Constants;
import com.xikang.calorie.common.Util;
import com.xikang.calorie.db.CityService;
import com.xikang.calorie.db.DB_ConnectionPool;
import com.xikang.calorie.db.RankService;
import com.xikang.calorie.domain.City;
import com.xikang.calorie.domain.RankInfo.Rank_Conlumns;
import com.xikang.calorie.domain.RankVO;

/**
 * 
 * 排行榜页面
 * 
 * 
 * <pre>
 * 
 * 2011/06/13, 曲磊
 * 
 * 修订履历：2011/06/15 代俊义   添加页面实现
 * 修订履历：2011/08/09 曲磊    增加加载进度框
 * </pre>
 * 
 * @author 曲磊
 * @version 1.00
 */
public class MainTop extends BaseActivity {

	private TextView rankingMessage_up;
	private TextView rankingLabel;

	private TextView cityRankingMessage_up;
	private TextView cityRankingLabel;

	private TextView groupRankingMessage_up;
	private TextView groupRankingLabel;

	private TextView rankChange;
	private TextView cityChange;
	private TextView groupChange;

	private ImageView rankChangeImg;
	private ImageView cityChangeImg;
	private ImageView groupChangeImg;

	private List<RankVO> rankList;

	private LinearLayout rank;
	private LinearLayout city_rank;
	private LinearLayout group_rank;

	private Thread thread;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_main_top);
		init();
	}

	@Override
	protected void onResume() {
		super.onResume();
		showProgress(getString(R.string.setting_progress_message_loading));
		thread = new Thread(runLoadData);
		thread.start();
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case LOADING_SCUESS:
				loadData();
				closeProgress();
				break;
			default:
				closeProgress();
				break;
			}
		}
	};

	private Runnable runLoadData = new Runnable() {
		@Override
		public void run() {
			Message msg = handler.obtainMessage();

			if (Constants.CURRENT_USER != null) {
				rankList = mXmlope.getRankList(Constants.CURRENT_USER.getPersonId(), "4");
				Util.rankList = rankList;
				msg.what = LOADING_SCUESS;
			}

			handler.sendMessage(msg);
		}
	};

	private void init() {

		showTitle(getString(R.string.rank));

		rankingMessage_up = (TextView) findViewById(R.id.ranking_message_up);
		rankingLabel = (TextView) findViewById(R.id.ranking_label);

		cityRankingMessage_up = (TextView) findViewById(R.id.city_ranking_message_up);
		cityRankingLabel = (TextView) findViewById(R.id.city_ranking_label);

		groupRankingMessage_up = (TextView) findViewById(R.id.group_ranking_message_up);
		groupRankingLabel = (TextView) findViewById(R.id.group_ranking_label);

		rankChange = (TextView) findViewById(R.id.rank_change);
		cityChange = (TextView) findViewById(R.id.city_change);
		groupChange = (TextView) findViewById(R.id.group_change);

		rankChangeImg = (ImageView) findViewById(R.id.rank_image);
		cityChangeImg = (ImageView) findViewById(R.id.city_rank_image);
		groupChangeImg = (ImageView) findViewById(R.id.group_rank_image);

		rank = (LinearLayout) findViewById(R.id.rank);
		city_rank = (LinearLayout) findViewById(R.id.city_rank);
		group_rank = (LinearLayout) findViewById(R.id.group_rank);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return false;
	}

	private void jumpRankFrame(int tab) {
		Intent intent = new Intent();
		intent.setClass(MainTop.this, RankFrame.class);
		Bundle bundle = new Bundle();
		bundle.putInt("page", tab);
		intent.putExtras(bundle);
		MainTop.this.startActivity(intent);

	}

	private void loadData() {

		if (rankList != null) {
			String rankMessage = null;
			String rankPercent;
			String currentRank;
			RankService rankService = new RankService(DB_ConnectionPool.getInstance(this));

			Map<String, Integer> lastRank = rankService.getLastRak();

			int lastAllRank = 0;
			int lastCityRank = 0;
			int lastAgeRank = 0;

			if (lastRank != null) {
				lastAllRank = lastRank.get(Rank_Conlumns.ALLRANK);
				lastCityRank = lastRank.get(Rank_Conlumns.CITYRANK);
				lastAgeRank = lastRank.get(Rank_Conlumns.AGERANK);
			}

			int currentAllRank = 0;
			int currentCityRank = 0;
			int currentAgeRank = 0;
			System.out.println(rankList.size()+"1");
			String changeString = getString(R.string.rank_change);
			int changeImgId = R.drawable.rank_left;
			for (int i = 0; i < rankList.size(); i++) {
				rankPercent = rankList.get(i).getBeatPresent();
				currentRank = rankList.get(i).getCurrentRank();
				if (currentRank == null | "".equals(currentRank)) {
					currentRank = "0";
				}
				if (rankPercent == null | "".equals(rankPercent)) {
					rankPercent = "0";
				}
				if (i == 0) {
					currentAllRank = Integer.parseInt(currentRank);
					if ("1".equals(rankPercent)) {
						rankMessage = getString(R.string.ranking_message5);
					} else {
						if (Integer.parseInt(rankPercent) > 0) {
							rankMessage = getString(Util.rank_message[Util.getRankTipMessage(rankPercent)]) + ","
									+ getString(R.string.ranking_message).replace("@percent", rankPercent + "%");
						}
					}
					if (rankMessage != null) {
						rankingMessage_up.setText(rankMessage);
					}
					rankingLabel.setText(currentRank);
					rank.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							jumpRankFrame(0);
						}
					});
					if (lastAllRank > 0) {
						if (lastAllRank > currentAllRank) {
							changeString = getString(R.string.rank_change_down).replace("num",
									lastAllRank - currentAllRank + "");
							changeImgId = R.drawable.rank_down;
						} else if (lastAllRank < currentAllRank) {
							changeString = getString(R.string.rank_change_up).replace("num",
									currentAllRank - lastAllRank + "");
							changeImgId = R.drawable.rank_up;
						}
					} else {
						if (currentAllRank > 0) {
							changeString = getString(R.string.rank_change_up).replace("num", currentAllRank + "");
							changeImgId = R.drawable.rank_up;
						}

					}
					rankChange.setText(changeString);
					rankChangeImg.setImageResource(changeImgId);
				} else if (i == 1) {
					currentCityRank = Integer.parseInt(currentRank);
					if ("1".equals(rankPercent)) {
						rankMessage = getString(R.string.ranking_message5);
					} else {
						if (rankPercent == null | "".equals(rankPercent)) {
							rankPercent = "0";
						}
						rankMessage = getString(Util.city_rank[Util.getRankTipMessage(rankPercent)]) + ","
								+ getString(R.string.city_ranking_message).replace("@percent", rankPercent + "%");
					}
					CityService cityServive = new CityService(DB_ConnectionPool.getInstance(this));
					City city = null;
					String cityName = null;
					if (rankList.get(i).getCityCode() != null) {
						city = cityServive.getCity(rankList.get(i).getCityCode());
						if (city == null) {
							cityName = "所属地区中";
						} else {
							cityName = city.getName();
						}
					}
					rankMessage = rankMessage.replace("@area", cityName);
					cityRankingMessage_up.setText(rankMessage);
					cityRankingLabel.setText(currentRank);
					city_rank.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							jumpRankFrame(1);
						}
					});
					if (lastCityRank > 0) {
						if (lastCityRank > currentCityRank) {
							changeString = getString(R.string.rank_change_down).replace("num",
									lastCityRank - currentCityRank + "");
							changeImgId = R.drawable.rank_down;
						} else if (lastCityRank < currentCityRank) {
							changeString = getString(R.string.rank_change_up).replace("num",
									currentCityRank - lastCityRank + "");
							changeImgId = R.drawable.rank_up;
						}
					} else {
						changeString = getString(R.string.rank_change_up).replace("num", currentCityRank + "");
						changeImgId = R.drawable.rank_up;
					}
					cityChange.setText(changeString);
					cityChangeImg.setImageResource(changeImgId);
				} else if (i == 2) {
					currentAgeRank = Integer.parseInt(currentRank);
					if ("1".equals(rankPercent)) {
						rankMessage = getString(R.string.ranking_message5);
					} else {
						if (rankPercent == null | "".equals(rankPercent)) {
							rankPercent = "0";
						}
						rankMessage = getString(Util.group_rank[Util.getRankTipMessage(rankPercent)]) + ","
								+ getString(R.string.group_ranking_message).replace("@percent", rankPercent + "%");
						rankMessage = rankMessage.replace("age", Util.getAgeStatement(rankList.get(i).getBrithday()));
					}
					groupRankingMessage_up.setText(rankMessage);
					groupRankingLabel.setText(currentRank);
					group_rank.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							jumpRankFrame(2);
						}
					});
					if (lastAgeRank > 0) {
						if (lastAgeRank > currentAgeRank) {
							changeString = getString(R.string.rank_change_down).replace("num",
									lastAgeRank - currentAgeRank + "");
							changeImgId = R.drawable.rank_down;
						} else if (lastAgeRank < currentAgeRank) {
							changeString = getString(R.string.rank_change_up).replace("num",
									currentAgeRank - lastAgeRank + "");
							changeImgId = R.drawable.rank_up;
						}
					} else {
						changeString = getString(R.string.rank_change_up).replace("num", currentAgeRank + "");
						changeImgId = R.drawable.rank_up;
					}
					groupChange.setText(changeString);
					groupChangeImg.setImageResource(changeImgId);
				}
			}
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put(Rank_Conlumns.ALLRANK, currentAllRank);
			map.put(Rank_Conlumns.CITYRANK, currentCityRank);
			map.put(Rank_Conlumns.AGERANK, currentAgeRank);
			rankService.insertRank(map);
		}
	}

}