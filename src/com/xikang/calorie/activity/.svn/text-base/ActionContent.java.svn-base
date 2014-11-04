package com.xikang.calorie.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.xikang.calorie.common.Constants;
import com.xikang.calorie.common.Util;
import com.xikang.calorie.domain.Actions;
import com.xikang.calorie.domain.ActionsDetails;
import com.xikang.calorie.domain.ActionsDetails.Member;

public class ActionContent extends BaseActivity {

	private Actions mActions;

	private ActionsDetails actionsDetails;
	private List<Member> memberList = new ArrayList<Member>();

	private TextView tvActionDtlStartDate;
	private TextView tvActionDtlStartTime;
	private TextView tvActionDtlEndDate;
	private TextView tvActionDtlEndTime;
	private TextView tvActionDtlNumber;
	private TextView tvActionDtlAddress;

	private TextView tvActionRank;
	private Button btnActionDtlJoin;
	private LayoutInflater mInflater;

	private ListView mListView;

	private ActionsContentAdapter mAdapter;
	
	private Thread thread;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mInflater = LayoutInflater.from(mContext);
		setContentView(R.layout.layout_actions_content);

		init();
		Bundle bundle = getIntent().getExtras();
		mActions = (Actions) bundle.getSerializable("Actions");
		showProgress(getString(R.string.setting_progress_message_loading));
		thread = new Thread(runLoadData);
		thread.start();
	}

	/**
	 * 加载线程处理
	 */
	private Runnable runLoadData = new Runnable() {

		@Override
		public void run() {

			actionsDetails = mXmlope.getActionsDetailsGetData(Constants.CURRENT_USER.getPersonId(), mActions.getId());
			Message msg = handler.obtainMessage();
			msg.what = LOADING_SCUESS;
			handler.sendMessage(msg);
		}
	};

	/**
	 * 刷新UI
	 */
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case LOADING_SCUESS:
				loading();
				closeProgress();
				break;
			default:
				closeProgress();
				break;
			}
		}
	};

	private void init() {
		showLeftButton(getString(R.string.common_back), new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				ActionContent.this.finish();
			}
		});

		tvActionDtlStartDate = (TextView) findViewById(R.id.tvActionDtlStartDate);
		tvActionDtlStartTime = (TextView) findViewById(R.id.tvActionDtlStartTime);
		tvActionDtlEndDate = (TextView) findViewById(R.id.tvActionDtlEndDate);
		tvActionDtlEndTime = (TextView) findViewById(R.id.tvActionDtlEndTime);

		tvActionDtlNumber = (TextView) findViewById(R.id.tvActionDtlNumber);
		tvActionDtlAddress = (TextView) findViewById(R.id.tvActionDtlAddress);

		tvActionRank = (TextView) findViewById(R.id.tvActionRank);
		btnActionDtlJoin = (Button) findViewById(R.id.btnActionDtlJoin);
		btnActionDtlJoin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mXmlope.joinActions(Constants.CURRENT_USER.getPersonId(), mActions.getId())) {

				}
			}
		});

		mListView = (ListView) findViewById(R.id.lvActionContent);
		mAdapter = new ActionsContentAdapter();
		mListView.setAdapter(mAdapter);
	}

	private static class ListHolder {
		TextView text1;
		TextView text2;
	}

	class ActionsContentAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return memberList.size();
		}

		@Override
		public Object getItem(int arg0) {
			return memberList.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
			ListHolder holder;
			Member mMember = memberList.get(position);

			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.layout_view_actions_content_element, null);
				holder = new ListHolder();
				holder.text1 = (TextView) convertView.findViewById(R.id.tvAContent1);
				holder.text2 = (TextView) convertView.findViewById(R.id.tvAContent2);
				convertView.setTag(holder);
			} else {
				holder = (ListHolder) convertView.getTag();
			}
			holder.text1.setText(mMember.getName());
			holder.text2.setText(mMember.getValue());
			return convertView;
		}
	}

	private void loading() {

		String[] strStart = Util.makeStrDate(mActions.getStart_time());
		String[] strEnd = Util.makeStrDate(mActions.getEnd_time());
		tvActionDtlStartDate.setText(strStart[0]);
		tvActionDtlStartTime.setText(strStart[1]);
		tvActionDtlEndDate.setText(strEnd[0]);
		tvActionDtlEndTime.setText(strEnd[1]);
		tvActionDtlNumber.setText(mActions.getNumber());
		tvActionDtlAddress.setText(mActions.getAddress());
		showTitle(mActions.getName());

		String status = actionsDetails.getJoinstatus();

		if ("0".equals(status)) {
			tvActionRank.setVisibility(View.GONE);
			btnActionDtlJoin.setVisibility(View.VISIBLE);
		} else if ("1".equals(status)) {
			tvActionRank.setVisibility(View.VISIBLE);
			btnActionDtlJoin.setVisibility(View.GONE);
			if("-1".equals(actionsDetails.getRank())){
				tvActionRank.setText("无运动数据");
			}else{
				String msg1 = getString(R.string.actions_my_top).replaceAll("@num", actionsDetails.getRank());
				tvActionRank.setText(msg1);
			}
		} else if ("2".equals(status)) {
			tvActionRank.setVisibility(View.VISIBLE);
			btnActionDtlJoin.setVisibility(View.GONE);
			tvActionRank.setText("未开始");
		}

		memberList = actionsDetails.getMemberList();
		mAdapter.notifyDataSetChanged();

	}

}
