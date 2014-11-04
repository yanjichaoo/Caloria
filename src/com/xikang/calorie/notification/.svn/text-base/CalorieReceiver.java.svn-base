package com.xikang.calorie.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class CalorieReceiver extends BroadcastReceiver {
	private static final String ACTION = "android.intent.action.BOOT_COMPLETED";
	

	@Override
	public void onReceive(Context context, Intent intent) {
		if (ACTION.equals(intent.getAction())) {
			context.startService(new Intent(context, CalorieService.class));
		}
	}

}
