package com.xikang.calorie.notification;

import java.util.Date;
import java.util.Map;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;

import com.xikang.calorie.activity.R;
import com.xikang.calorie.common.Constants;
import com.xikang.calorie.common.Util;
import com.xikang.calorie.db.DB_ConnectionPool;
import com.xikang.calorie.db.RemindService;
import com.xikang.calorie.db.UserInfoService;
import com.xikang.calorie.domain.Remind;
import com.xikang.calorie.domain.UserInfo;
import com.xikang.calorie.xml.XmlConstants;
import com.xikang.calorie.xml.XmlConstants.RemindConfGet;
import com.xikang.calorie.xml.XmlConstants.RemindMsg;
import com.xikang.calorie.xml.XmlOpe;

public class CalorieService extends Service {

	private XmlOpe mXmlope;
	private UserInfoService mUserInfoService;
	private RemindService mRemindService;

	private NotificationManager mNotificationManager;

	private boolean hasUser;

	private final static int AM_TIMETYPE = 1;

	private final static int NOON_TIMETYPE = 2;

	private final static int PM_TIMETYPE = 3;

	private final static long TIME_RANGE = 120000;

	private Handler runHandler = new Handler();

	private double sportTarget = 0;
	private double weight = 0;

	private UserInfo currentUser;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mXmlope = new XmlOpe(this);
		mUserInfoService = new UserInfoService(DB_ConnectionPool.getInstance(this));
		mRemindService = new RemindService(DB_ConnectionPool.getInstance(this));
		mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		Constants.IS_SERVICE_RUN = true;
		login();
		runHandler.post(runRemind);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Constants.IS_SERVICE_RUN = false;
		runHandler.removeCallbacks(runRemind);
	}

	Runnable runRemind = new Runnable() {
		@Override
		public void run() {
			long timeRun = 60000;
			if (hasUser) {
				if (sportTarget != 0 || weight != 0) {
					Date nowDate = new Date();
					int timeType = whichTimeType(nowDate);
					if (timeType != 0) {
						Remind currentRemind = mRemindService.getCurrentRemind(String.valueOf(timeType));
						if (currentRemind != null && currentRemind.getRemindingEnabled().equals("1")) {
							Date remindTime = Util.spellDate(currentRemind.getRemindingTime());
							if (Util.addDateTime(remindTime, -TIME_RANGE).before(nowDate)
									&& nowDate.before(Util.addDateTime(remindTime, TIME_RANGE))) {
								Map<String, String> msgMap = mXmlope.getRemindMsg(currentUser.getPersonId(),
										currentRemind.getRemindingTime());
								if (msgMap != null) {
									if ("1".equals(msgMap.get(RemindMsg.NEEDREMIND))) {
										showNotification(remindMsg(msgMap), Uri.parse(currentRemind.getRingType()));
										timeRun = timeRun + 300000;
									}
								}
							}
						}
					}
				}
			} else {
				login();
			}
			runHandler.postDelayed(runRemind, timeRun);
		}
	};

	private String remindMsg(Map<String, String> msgMap) {
		String msg = null;
		CharSequence[] arr1 = getResources().getTextArray(R.array.array_remind_msg1);
		CharSequence[] arr2 = getResources().getTextArray(R.array.array_remind_msg2);
		CharSequence[] arr3 = getResources().getTextArray(R.array.array_remind_msg3);

		Double surplusCalorie = Double.parseDouble(msgMap.get(RemindMsg.SURPLUSCALORIE));
		int currentTimeType = Integer.valueOf(msgMap.get(RemindMsg.CURRENTTIMETYPE));
		int nextTimeType = Integer.valueOf(msgMap.get(RemindMsg.NEXTTIMETYPE));
		int isLastTimeType = Integer.valueOf(msgMap.get(RemindMsg.ISLASTTIMEINDAY));

		StringBuffer remindContent = new StringBuffer();
		remindContent.append(arr1[currentTimeType - 1]);

		if (isLastTimeType == 1) {
			remindContent.append(arr2[2]);
			remindContent.append(arr3[2]);
		} else {
			remindContent.append(arr2[currentTimeType - 1]);
			remindContent.append(arr3[nextTimeType - 2]);
		}

		msg = remindContent.toString();
		if (isLastTimeType == 0) {
			msg = msg.replaceAll("@STEP", String.valueOf(Util.countSteps(surplusCalorie, weight)));
		}

		return msg;
	}

	private void login() {
		currentUser = mUserInfoService.getCurrentUserInfo();
		if (currentUser != null) {
			hasUser = true;
			Map<String, String> result = mXmlope.getRemindInfo(currentUser.getPersonId());
			if (result != null) {
				if ("1".equals(result.get(XmlConstants.STATUS))) {
					String str = result.get(RemindConfGet.SPORTTARGET);
					if (str != null && !"".equals(str)) {
						sportTarget = Double.parseDouble(str);
						weight = Double.parseDouble(result.get(RemindConfGet.WEIGHT));
					}
				}
			}
		}
	}

	private void showNotification(String msg, Uri ringUri) {
		Notification mNotification = new Notification();
		Intent mIntent = new Intent(this, CalorieDialog.class);
		Bundle mBundle = new Bundle();
		mBundle.putString("MSG", msg);
		mIntent.putExtras(mBundle);
		PendingIntent mPendingIntent = PendingIntent.getActivity(this, 0, mIntent, 0);

		mNotification.icon = R.drawable.icon;

		// 发送通知时在状态栏显示的内容
		mNotification.tickerText = "熙康走跑族运动提醒";
		// 发送通知时在状态栏铃声
		if (ringUri != null) {
			mNotification.sound = ringUri;
		}

		mNotification.flags |= Notification.FLAG_AUTO_CANCEL;

		mNotification.setLatestEventInfo(this, "熙康走跑族运动提醒", "点击查看运动目标", mPendingIntent);

		mNotificationManager.notify(0, mNotification);
	}

	public int whichTimeType(Date nowDate) {
		if (Util.spellDate("5:00").before(nowDate) && nowDate.before(Util.spellDate("9:00"))) {
			return AM_TIMETYPE;
		}
		if (Util.spellDate("10:30").before(nowDate) && nowDate.before(Util.spellDate("14:00"))) {
			return NOON_TIMETYPE;
		}
		if (Util.spellDate("17:00").before(nowDate) && nowDate.before(Util.spellDate("21:00"))) {
			return PM_TIMETYPE;
		}
		return 0;
	}

}
