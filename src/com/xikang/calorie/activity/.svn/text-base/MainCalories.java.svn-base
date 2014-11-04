/* ==================================================
 * 产品名: 卡路里消耗管理
 * 文件名: MainCalories.java
 * --------------------------------------------------
 * 开发环境: JDK1.6 
 * --------------------------------------------------
 * 修订履历  YYYY/MM/DD  REV.  备注
 *         2011/06/13  1.00  初版发行
 *         2011/06/19  1.01  1.4接口修正
 * --------------------------------------------------
 * (C) Copyright XIKANG CORPORATION 2011 All Rights Reserved.
 */
package com.xikang.calorie.activity;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import weibo4android.Weibo;
import weibo4android.WeiboException;
import weibo4android.http.RequestToken;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xikang.calorie.common.Constants;
import com.xikang.calorie.common.OAuthConstant;
import com.xikang.calorie.domain.TodaySportInfo;
import com.xikang.calorie.view.PercentView;

/**
 * 
 * 今日卡路里主窗体
 * 
 * 
 * <pre>
 * 
 * 2011/06/14, 张荣
 * 
 * 修订履历：YYYY/MM/DD 修订者  修订内容
 * 
 * </pre>
 * 
 * @author 张荣
 * @version 1.00
 */
public class MainCalories extends BaseActivity {

	private Context mContext;

	private Gallery mGallery;
	private LayoutInflater mInflater;

	private TextView mTvTBContent;
	private Button mBtnTBRight;
	private Button mBtnTBLeft;

	private TextView mTvFirstTitle;
	private TextView mTvSecondTitle;

	private TextView mTvStepCount;
	private TextView mTvAverageHeartRate;
	private TextView mTvElapsedTime;
	private TextView mTvAverageSpeed;
	private TextView mTvDistance;

	private ImageView mLeftImage;
	private ImageView mRightImage;
	private TextView mTvSportInfoNote;
	private TextView mTvTips;
	private LinearLayout mLLTips;
	private PercentView mProgressClock;
	private TodaySportInfo mTodaySportInfo;

	private Resources mRes;
	private Handler mHandler;

	private TextView[] sportInfos = new TextView[5];
	private int[] units = new int[] { R.string.exercise_step_unit, R.string.exercise_distance_unit, R.string.exercise_time_unit,
			R.string.exercise_speed_unit, R.string.exercise_rate_unit };

	private float mPercent = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return false;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_main_calories);

		System.setProperty("weibo4j.oauth.consumerKey", "501169847");
		System.setProperty("weibo4j.oauth.consumerSecret", "d486e3b7d43711a5d43c600bd8eda32f");

		mContext = this.getBaseContext();

		// 页标题设置
		mTvTBContent = (TextView) findViewById(R.id.tvTBContent);
		mTvTBContent.setText(R.string.today_kcal_title);

		// 右标题按钮设置
		mBtnTBRight = (Button) findViewById(R.id.btnTBRight);
		mBtnTBRight.setText(R.string.common_bmi);
		mBtnTBRight.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(mContext, MainBMI.class));
			}
		});
		mBtnTBRight.setVisibility(View.VISIBLE);

		mBtnTBLeft = (Button) findViewById(R.id.btnTBLeft);

		mRes = getResources();

		mTvFirstTitle = (TextView) findViewById(R.id.first_title);
		mTvSecondTitle = (TextView) findViewById(R.id.second_title);

		mProgressClock = (PercentView) findViewById(R.id.progressClock);

		mTvStepCount = new TextView(mContext);
		mTvElapsedTime = new TextView(mContext);
		mTvAverageHeartRate = new TextView(mContext);
		mTvAverageSpeed = new TextView(mContext);
		mTvDistance = new TextView(mContext);

		mLeftImage = (ImageView) findViewById(R.id.sportLeftImage);
		mGallery = (Gallery) findViewById(R.id.sport_info_gallery);
		mRightImage = (ImageView) findViewById(R.id.sportRightImage);

		mTvSportInfoNote = (TextView) findViewById(R.id.sport_info_note);
		mTvTips = (TextView) findViewById(R.id.tip_title);
		mLLTips = (LinearLayout) findViewById(R.id.tips);

		mProgressClock.setCalorie("0");
		mProgressClock.setCalorieTarget("500");
		mProgressClock.setDeltaAngle(20.0f);

		setData();
		emptyData();

		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {

				super.handleMessage(msg);
				if (msg.arg1 == 0) {
					Toast.makeText(MainCalories.this, mRes.getString(R.string.common_net_failed), Toast.LENGTH_SHORT).show();
				}

				closeProgress();

				loadingData();
				setData();
			}
		};
	}

	private void setData() {
		if (!Constants.IS_LOGIN) {
			// 左标题按钮设置
			mBtnTBLeft.setText(R.string.common_login);
			mBtnTBLeft.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {
					startActivity(new Intent(mContext, SettingAccount.class));
				}
			});
			mBtnTBLeft.setVisibility(View.VISIBLE);

			mLeftImage.setVisibility(View.INVISIBLE);
			mGallery.setVisibility(View.INVISIBLE);
			mRightImage.setVisibility(View.INVISIBLE);

			mTvSportInfoNote.setVisibility(View.VISIBLE);
		} else {
			mLeftImage.setVisibility(View.VISIBLE);
			mRightImage.setVisibility(View.VISIBLE);
			mGallery.setVisibility(View.VISIBLE);
			mTvSportInfoNote.setVisibility(View.INVISIBLE);

			// 左标题按钮设置
			if ("zh".equals(Locale.getDefault().getLanguage())) {
				mBtnTBLeft.setText(R.string.common_share);
				mBtnTBLeft.setOnClickListener(new Button.OnClickListener() {
					public void onClick(View v) {
						Weibo weibo = new Weibo();
						RequestToken requestToken;
						String token = null;
						String tokenSecret = null;

						SharedPreferences pref = getSharedPreferences("weibo_shared", Context.MODE_PRIVATE);
						token = pref.getString("Token", null);
						tokenSecret = pref.getString("TokenSecret", null);

						if ((token != null) && (token != null)) {
							OAuthConstant.getInstance().setToken(token);
							OAuthConstant.getInstance().setTokenSecret(tokenSecret);
							startActivity(new Intent(mContext, CalorieShare.class));
						} else {
							try {
								requestToken = weibo.getOAuthRequestToken("weibo4android://CalorieShare");
								Uri uri = Uri.parse(requestToken.getAuthenticationURL() + "&from=xweibo");
								OAuthConstant.getInstance().setRequestToken(requestToken);

								startActivity(new Intent(Intent.ACTION_VIEW, uri));
							} catch (WeiboException e) {
								e.printStackTrace();
							}
						}
					}
				});
				mBtnTBLeft.setVisibility(View.VISIBLE);
			}
		}

	}

	/**
	 * 加载数据
	 */
	private void loadingData() {
		String restCalories = null;
		float target, calorie;

		DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
		df.setMinimumFractionDigits(1);
		df.setGroupingUsed(false);
		DecimalFormat df1 = (DecimalFormat) NumberFormat.getInstance();
		df1.setMinimumFractionDigits(4);
		df1.setGroupingUsed(false);

		if (mTodaySportInfo != null) {
			// 今日运动信息获取成功
			target = Float.valueOf(df.format(Float.parseFloat(mTodaySportInfo.getTodaySportTarget())));
			calorie = Float.valueOf(df.format(Float.parseFloat(mTodaySportInfo.getCalorie() == null ? "0" : mTodaySportInfo
					.getCalorie())));
			restCalories = df.format((target - calorie) < 0 ? 0 : (target - calorie));
			if ("1".equals(mTodaySportInfo.getHasData())) {
				if (target != 0.0) {
					// 今日运动
					mPercent = Float.valueOf(df1.format(calorie / target));

					if (mPercent <= 0.3) {
						mTvFirstTitle.setText(R.string.second_title_4);
						mTvSecondTitle.setText(mRes.getString(R.string.second_title_1).replaceAll("rest", restCalories));
					} else if (mPercent <= 0.6) {
						mTvFirstTitle.setText(R.string.second_title_5);
						mTvSecondTitle.setText(mRes.getString(R.string.second_title_1).replaceAll("rest", restCalories));
					} else if (mPercent <= 0.9999) {
						mTvFirstTitle.setText(R.string.second_title_6);
						mTvSecondTitle.setText(mRes.getString(R.string.second_title_1).replaceAll("rest", restCalories));
					} else {
						mTvFirstTitle.setText(R.string.second_title_7);
						mTvSecondTitle.setText(R.string.second_title_9);
					}

					mTvStepCount.setText(mTodaySportInfo.getStepCount());
					mTvAverageHeartRate.setText(mTodaySportInfo.getAverageHeartRate());
					mTvElapsedTime.setText(mTodaySportInfo.getElapsedTime());
					mTvAverageSpeed.setText(mTodaySportInfo.getAverageSpeed());
					mTvDistance.setText(mTodaySportInfo.getDistance());

				} else {
					mTvFirstTitle.setText(R.string.first_title_4);
					mTvSecondTitle.setText(R.string.second_title_10);
					mGallery.setVisibility(View.INVISIBLE);
					mTvSportInfoNote.setText(R.string.sport_info_note2);
					mTvSportInfoNote.setVisibility(View.VISIBLE);
				}

			} else {
				// 今日未运动
				emptyData();
				mTvFirstTitle.setText(mTodaySportInfo.getUserName());
				mTvSecondTitle.setText(R.string.second_title_3);
			}

			if (calorie >= target) {
				mProgressClock.setCalorie(df.format(Float.parseFloat(mTodaySportInfo.getTodaySportTarget())));
			} else {
				mProgressClock.setCalorie(df.format(Float.parseFloat(mTodaySportInfo.getCalorie() == null ? "0" : mTodaySportInfo
						.getCalorie())));
			}

			mProgressClock.setCalorieTarget(df.format(Float.parseFloat(mTodaySportInfo.getTodaySportTarget())));
			mProgressClock.setSweepTempAngle(0);
			mProgressClock.invalidate();

			mTvTips.setText(mRes.getString(R.string.tip_note).concat(
					mTodaySportInfo.getTipTitle() == null ? "" : mTodaySportInfo.getTipTitle()));
		} else {
			// 今日运动信息获取失败
			emptyData();
			mTvFirstTitle.setText(R.string.first_title_2);
			mTvSecondTitle.setText(R.string.second_title_2);
		}

		SharedPreferences pref = getSharedPreferences("weibo_shared", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();

		String sharedContent = mRes.getString(R.string.share_info);
		sharedContent = sharedContent.replaceAll("step", mTvStepCount.getText().toString());
		sharedContent = sharedContent.replaceAll("distance", mTvDistance.getText().toString());
		df.setMinimumFractionDigits(0);
		sharedContent = sharedContent.replaceAll("percent", df.format((mPercent * 100) > 100 ? 100 : (mPercent * 100)));

		editor.putString("SharedContent", sharedContent);
		editor.commit();
		sportInfos[0] = mTvStepCount;
		sportInfos[1] = mTvDistance;
		sportInfos[2] = mTvElapsedTime;
		sportInfos[3] = mTvAverageSpeed;
		sportInfos[4] = mTvAverageHeartRate;
		mGallery.setAdapter(new SportInfoAdapter());

		mLLTips.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainCalories.this, SportTip.class);
				Bundle bundle = new Bundle();
				bundle.putString("tipTitle", mTodaySportInfo == null ? "" : mTodaySportInfo.getTipTitle());
				bundle.putString("tipSummary", mTodaySportInfo == null ? "" : mTodaySportInfo.getTipSummary());
				bundle.putString("tipContent", mTodaySportInfo == null ? "" : mTodaySportInfo.getTipContent());
				intent.putExtras(bundle);
				MainCalories.this.startActivity(intent);
			}
		});

	}

	@Override
	protected void onResume() {
		super.onResume();
		if (Constants.IS_LOGIN) {
			showProgress(getString(R.string.setting_progress_message_loading));
			new LoadDataThread().start();
		} else {
			mTodaySportInfo = null;
			mProgressClock.setCalorie("0");
			mProgressClock.setCalorieTarget("500");
			mProgressClock.setDeltaAngle(20.0f);
			mProgressClock.setSweepTempAngle(0);
			mProgressClock.invalidate();
			loadingData();
			setData();
		}
	}

	class LoadDataThread extends Thread {
		@Override
		public void run() {
			super.run();
			mTodaySportInfo = mXmlope.getTodaySportInfo(Constants.CURRENT_USER.getPersonId(), "0");
			int flag = 0;
			if (mTodaySportInfo != null) {
				flag = 1;
			}

			Message msg = new Message();
			msg.arg1 = flag;
			mHandler.sendMessage(msg);
		}
	}

	private void emptyData() {
		mTvStepCount.setText("0");
		mTvAverageHeartRate.setText("0");
		mTvElapsedTime.setText("0");
		mTvAverageSpeed.setText("0");
		mTvDistance.setText("0");
		mProgressClock.setCalorie("0");
	}

	static class SportInfo {
		TextView tvSportInfo;
		TextView tvUnit;
	}

	class SportInfoAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return sportInfos.length;
		}

		@Override
		public Object getItem(int position) {
			return sportInfos[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			SportInfo sportInfo;

			if (convertView == null) {
				mInflater = LayoutInflater.from(mContext);
				convertView = mInflater.inflate(R.layout.layout_sportinfo_element, null);
				sportInfo = new SportInfo();
				sportInfo.tvSportInfo = (TextView) convertView.findViewById(R.id.tvSportInfo);
				sportInfo.tvUnit = (TextView) convertView.findViewById(R.id.tvUnit);
				convertView.setTag(sportInfo);
			} else {
				sportInfo = (SportInfo) convertView.getTag();
			}

			sportInfo.tvUnit.setText(units[position]);
			sportInfo.tvSportInfo.setText(sportInfos[position].getText());
			sportInfo.tvUnit.setTextSize(18);
			sportInfo.tvSportInfo.setTextSize(18);
			return convertView;

		}
	}

}
