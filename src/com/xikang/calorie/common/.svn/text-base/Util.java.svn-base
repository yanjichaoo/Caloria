/* ==================================================
 * 产品名: 卡路里管理
 * 文件名: Util.java
 * --------------------------------------------------
 * 开发环境: JDK1.6
 * --------------------------------------------------
 * 修订履历  YYYY/MM/DD  REV.  备注
 *         2011/06/13  1.00  初版发行
 * --------------------------------------------------
 * (C) Copyright XIKANG CORPORATION 2011 All Rights Reserved.
 */
package com.xikang.calorie.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xikang.calorie.activity.R;
import com.xikang.calorie.db.CityService;
import com.xikang.calorie.db.DB_ConnectionPool;
import com.xikang.calorie.domain.City;
import com.xikang.calorie.domain.RankVO;
import com.xikang.calorie.view.BarChartForRank;

/**
 * 
 * 工具类
 * 
 * 
 * <pre>
 * 
 * 2011/06/15, 代俊义
 * 
 * 修订履历：2011/06/15 代俊义   添加排行榜图表处理
 * 
 * 修订履历：2011/06/21 曲磊   日期处理方法
 * 
 * </pre>
 * 
 * @author 代俊义
 * @version 1.00
 */
public class Util {

	// 全局排行信息
	public static List<RankVO> rankList;
	// 排行榜提示信息
	public static int[] rank_message = new int[] { R.string.ranking_message1, R.string.ranking_message2,
			R.string.ranking_message3, R.string.ranking_message4 };
	public static int[] city_rank = new int[] { R.string.city_ranking_message1, R.string.city_ranking_message2,
			R.string.city_ranking_message3, R.string.city_ranking_message4 };
	public static int[] group_rank = new int[] { R.string.group_ranking_message1, R.string.group_ranking_message2,
			R.string.group_ranking_message3, R.string.group_ranking_message4 };

	public static void initRankChartView(Activity ac, int rank_type) {

		// 控制图表样式
		// rankList = XmlOpe.getRankList(ac,
		// Constants.CURRENT_USER.getPersonId(),
		// Constants.CURRENT_USER.getPhoneNum());

		// 判断数据是否正确,不正确弹出提示信息,移除当前的chart layout
		if (rankList != null) {
			for (RankVO vo : rankList) {
				// 需要判断循环完成的状态,如果循环完成后还没有找到相同的数据,则最后需要处理
				if (vo.getRankType() == rank_type) {
					if (vo.getRank() == null) {
						break;
					} else {
						LinearLayout chart = (LinearLayout) ac.findViewById(R.id.rankchartLayout);
						chart.addView(new BarChartForRank(ac, vo.getRank()));
					}
					String rankPercent = rankList.get(rank_type).getBeatPresent();
					String rankMessage = null;
					switch (rank_type) {
					case 0:
						rankMessage = ac.getString(rank_message[Util.getRankTipMessage(rankPercent)]);
						break;
					case 1:
						rankMessage = ac.getString(city_rank[Util.getRankTipMessage(rankPercent)]);
						break;
					case 2:
						rankMessage = ac.getString(group_rank[Util.getRankTipMessage(rankPercent)]);
						break;
					}
					rankMessage = rankMessage + "\n\n"
							+ ac.getString(R.string.ranking_message).replace("@percent", rankPercent + "%");
					if (Constants.CURRENT_USER == null) {
						rankMessage = "登录以后可以查看城市排行榜和相似人群排行榜";
					}
					if (rank_type == 1) {
						CityService cityServive = new CityService(DB_ConnectionPool.getInstance(ac));
						City city = null;
						String cityName = null;
						if (vo.getCityCode() != null) {
							city = cityServive.getCity(vo.getCityCode());
							if (city == null) {
								cityName = "所属地区中";
							} else {
								cityName = city.getName();
							}
						}
						rankMessage = rankMessage.replace("@area", cityName);
					}
					if (rank_type == 2) {
						rankMessage = rankMessage.replace("age", Util.getAgeStatement(vo.getBrithday()));
					}

					TextView rank_title = (TextView) ac.findViewById(R.id.rank_text_title);
					rank_title.setText(rankMessage);
				}
			}
		}
	}

	public static int getRankTipMessage(String percentString) {
		int return_num = 0;

		try {
			float percent = Float.parseFloat(percentString);
			if (percent < 30) {
				return_num = 0;
			} else if (percent < 60) {
				return_num = 1;
			} else if (percent < 90) {
				return_num = 2;
			} else {
				return_num = 3;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return return_num;
	}

	/**
	 * 设置title标题
	 * 
	 * @param ac
	 * @param title
	 */
	public static void setTitleBarTitle(Activity ac, String title) {
		TextView tvTBContent;
		tvTBContent = (TextView) ac.findViewById(R.id.tvTBContent);
		tvTBContent.setText(title);
	}

	public static String getAndroidDeviceId(Context base) {
		TelephonyManager tm = (TelephonyManager) base.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getDeviceId();
	}

	public static String getPackageVersion(Context context) {
		String version = "";
		PackageManager pm = context.getPackageManager();
		try {
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			version = pi.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return version;
	}

	public static String getPhoneNum(Context context) {
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getLine1Number();
	}

	public static Date addDateTime(Date time, long milliseconds) {
		long temp = time.getTime();
		long num = temp + milliseconds;
		return new Date(num);
	}

	public static Date spellDate(String time) {
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatTime = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String nowDate = formatDate.format(new Date());
		try {
			return formatTime.parse(nowDate + " " + time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String[] makeStrDate(String str) {
		try {
			Date date = parseDateS(str);
			String[] arrayStr = new String[2];
			SimpleDateFormat f = new SimpleDateFormat("MM月dd日");
			arrayStr[0] = f.format(date);
			f = new SimpleDateFormat("HH:mm");
			arrayStr[1] = f.format(date);
			return arrayStr;
		} catch (Exception e) {
			return null;
		}

	}

	public static Date parseDateS(String dateString) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.parse(dateString);
	}

	public static Date parseDate(String dateString) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date str = null;
		try {
			str = format.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return str;
	}

	public static String getDate(int year, int month, int day) {
		Date date = null;
		String dateString = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = df.parse(year + "-" + month + "-" + day);
			dateString = df.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateString;
	}

	public static String setTimeHM(int hour, int minute) {
		Date date = null;
		String dateString = null;
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		try {
			date = df.parse(hour + ":" + minute);
			dateString = df.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateString;
	}

	/**
	 * 获取CheckBox的选择值
	 * 
	 * @param checkBox
	 * @return
	 */
	public static String checkText(CheckBox checkBox) {
		if (checkBox.isChecked()) {
			return "1";
		} else {
			return "0";
		}
	}

	public static String readTextFile(InputStream inputStream) throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte buf[] = new byte[1024];
		int len;
		while ((len = inputStream.read(buf)) != -1) {
			outputStream.write(buf, 0, len);
		}
		outputStream.close();
		inputStream.close();
		return outputStream.toString();
	}

	/**
	 * 
	 * @param noDay 未运动天数
	 * @return 提示信息
	 */

	private static int[] noSportTips = new int[] { R.string.noSportTip_0, R.string.noSportTip_1, R.string.noSportTip_2,
			R.string.noSportTip_3, R.string.noSportTip_4, R.string.noSportTip_5, R.string.noSportTip_6 };

	public static int getHistoryCalorieTip(String noDay) {
		float temp = Float.parseFloat(noDay);

		if (temp == 0) {
			return noSportTips[0];
		} else if (temp <= 7) {
			return noSportTips[1];
		} else if (temp > 7 && temp <= 15) {
			return noSportTips[2];
		} else if (temp > 15 && temp <= 30) {
			return noSportTips[3];
		} else if (temp > 31 && temp <= 90) {
			return noSportTips[4];
		} else if (temp > 91 && temp <= 180) {
			return noSportTips[5];
		} else {
			return noSportTips[6];
		}
	}

	private static Button btnTBLeft;
	private static Activity ac;

	/**
	 * 
	 * @param ac
	 */

	public static void returnPage(Activity act) {
		ac = act;
		btnTBLeft = (Button) ac.findViewById(R.id.btnTBLeft);
		btnTBLeft.setVisibility(View.VISIBLE);
		btnTBLeft.setText(R.string.common_back);
		btnTBLeft.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ac.getParent().finish();
			}
		});
	}

	public static String getMonthDay(String dateString) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		String str = null;
		try {
			date = format.parse(dateString);
			format = new SimpleDateFormat("M-dd");
			str = format.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return str;
	}

	// 距离提示信息
	public static int[] distance_tip = new int[] { R.string.historyDistance_tip1, R.string.historyDistance_tip2,
			R.string.historyDistance_tip3, R.string.historyDistance_tip4, R.string.historyDistance_tip5,
			R.string.historyDistance_tip6 };

	public static double[] distance_num = { 8.8, 42.1, 306, 6000, 40000, 384000 };

	public static double distanceCycle = 0;

	public static int getDistanceTip(Activity act, double distance) {
		int num = 0;
		for (int i = 0; i < 6; i++) {
			if (distance < distance_num[i]) {
				num = i;
				distanceCycle = distance / distance_num[i];
				break;
			}
		}

		return distance_tip[num];
	}

	public static String[] ageStatement = new String[] { "0-9", "10-19", "20-29", "30-39", "40-49", "50-59", "60-69", "70-79",
			"80-89", "90-99" };

	public static String getAgeStatement(String birthday) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		Calendar c1 = Calendar.getInstance();
		try {
			c1.setTime(format.parse(birthday));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar c2 = Calendar.getInstance();
		int age = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
		int index = 0;
		if (age >= 0 && age < 9) {
			index = 0;
		} else if (age >= 10 && age < 19) {
			index = 1;
		} else if (age >= 20 && age < 29) {
			index = 2;
		} else if (age >= 30 && age < 39) {
			index = 3;
		} else if (age >= 40 && age < 49) {
			index = 4;
		} else if (age >= 50 && age < 59) {
			index = 5;
		} else if (age >= 60 && age < 69) {
			index = 6;
		} else if (age >= 70 && age < 79) {
			index = 7;
		} else if (age >= 80 && age < 89) {
			index = 8;
		} else if (age >= 90 && age < 99) {
			index = 9;
		}

		return ageStatement[index];
	}

	/**
	 * 卡路里步数换算
	 * 
	 * @param planCalorie
	 * @param weight
	 * @return
	 */
	public static int countSteps(double planCalorie, double weight) {

		double steps = 2000 * planCalorie / weight;

		return (int) steps;
	}

	public static double cutNumber(double num, int decimal) {
		BigDecimal bigDecimal = new BigDecimal(num);
		return num = bigDecimal.setScale(decimal, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 计算各时间段卡路里
	 * 
	 * @param switch1
	 * @param switch2
	 * @param switch3
	 * @param targetRate1
	 * @param targetRate2
	 * @param targetRate3
	 * @return
	 */
	public static double[] countCalorie(boolean switch1, boolean switch2, boolean switch3, Double targetRate1,
			Double targetRate2, Double targetRate3) {
		double t1 = 0;
		double t2 = 0;
		double t3 = 0;

		if (switch1 && switch2 && switch3) {
			t1 = targetRate1;
			t2 = targetRate2;
			t3 = targetRate3;
		}
		if (!switch1 && switch2 && switch3) {
			t1 = 0;
			t2 = (targetRate2 + targetRate1 / 2);
			t3 = (targetRate3 + targetRate1 / 2);
		}
		if (switch1 && !switch2 && switch3) {
			t1 = (targetRate1 + targetRate2 / 2);
			t2 = 0;
			t3 = (targetRate3 + targetRate2 / 2);
		}
		if (switch1 && switch2 && !switch3) {
			t1 = (targetRate1 + targetRate3 / 2);
			t2 = (targetRate2 + targetRate3 / 2);
			t3 = 0;
		}
		if (!switch1 && !switch2 && switch3) {
			t1 = 0;
			t2 = 0;
			t3 = 1;
		}
		if (!switch1 && switch2 && !switch3) {
			t1 = 0;
			t2 = 1;
			t3 = 0;
		}
		if (switch1 && !switch2 && !switch3) {
			t1 = 1;
			t2 = 0;
			t3 = 0;
		}
		if (!switch1 && !switch2 && !switch3) {
			t1 = 0;
			t2 = 1;
			t3 = 0;
		}
		double[] array = new double[] { cutNumber(t1, 2), cutNumber(t2, 2), cutNumber(t3, 2) };
		return array;
	}

}
