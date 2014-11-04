package com.xikang.calorie.notification;

import com.xikang.calorie.activity.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class CalorieDialog extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_remind_dialog);
		Bundle bundle = getIntent().getExtras();
		String msg = bundle.getString("MSG");
		TextView tv = (TextView) findViewById(R.id.tvRD);
		tv.setText(msg);
	}

}
