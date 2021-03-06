package com.xikang.calorie.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.LocationListener;
import com.baidu.mapapi.MKAddrInfo;
import com.baidu.mapapi.MKDrivingRouteResult;
import com.baidu.mapapi.MKPoiResult;
import com.baidu.mapapi.MKSearch;
import com.baidu.mapapi.MKSearchListener;
import com.baidu.mapapi.MKTransitRouteResult;
import com.baidu.mapapi.MKWalkingRouteResult;

import com.xikang.calorie.common.Constants;
import com.xikang.calorie.common.Util;
import com.xikang.calorie.domain.Actions;

public class MainActions extends BaseActivity {

	private RadioButton btnHotActions;
	private RadioButton btnMyActions;
	private ListView lvActions;
	private LayoutInflater mInflater;

	private List<Actions> listActions = new ArrayList<Actions>();
	private ActionsListAdapter mActionsListAdapter;

	private boolean isReload = false;

	private String city = "北京市";

	private MKSearch mSearch;
	private LocationListener mLocationListener;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_main_actions);
		mInflater = LayoutInflater.from(mContext);

		showProgress("定位中...");
		init();
		if("zh".equals(Locale.getDefault().getLanguage())){
			getLocation();
		}else{
			city="北京市";
			loadingData();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return false;
	}

	@Override
	protected void onStart() {
		if (isReload) {
			checkLogin();
		}
		super.onStart();
	}

	@Override
	protected void onStop() {
		if (mLocationListener != null) {
			MapApp app = (MapApp) MainActions.this.getApplication();
			app.mBMapMan.getLocationManager().removeUpdates(mLocationListener);
			app.mBMapMan.stop();
		}
		super.onStop();
	}

	private void getLocation() {

		MapApp app = (MapApp) this.getApplication();

		if (app.mBMapMan == null) {
			app.mBMapMan = new BMapManager(getApplication());
			app.mBMapMan.init(app.mStrKey, new MapApp.MyGeneralListener());
		}

		mLocationListener = new LocationListener() {
			@Override
			public void onLocationChanged(Location location) {
				if (location != null) {
					GeoPoint pt = new GeoPoint((int) (location.getLatitude() * 1e6), (int) (location.getLongitude() * 1e6));
					mSearch.reverseGeocode(pt);
				}
			}
		};

		app.mBMapMan.getLocationManager().requestLocationUpdates(mLocationListener);
		app.mBMapMan.start();

		mSearch = new MKSearch();
		mSearch.init(app.mBMapMan, new MKSearchListener() {
			@Override
			public void onGetWalkingRouteResult(MKWalkingRouteResult arg0, int arg1) {
			}

			@Override
			public void onGetTransitRouteResult(MKTransitRouteResult arg0, int arg1) {
			}

			@Override
			public void onGetPoiResult(MKPoiResult arg0, int arg1, int arg2) {
			}

			@Override
			public void onGetDrivingRouteResult(MKDrivingRouteResult arg0, int arg1) {
			}

			@Override
			public void onGetAddrResult(MKAddrInfo arg0, int arg1) {
				city = arg0.addressComponents.city;
				System.out.println("###");
				loadingData();
			}
		});

	}

	/**
	 * 初始化控件
	 */
	private void init() {
		showTitle(getString(R.string.actions_title));

		if("zh".equals(Locale.getDefault().getLanguage())){
			showRightButton(getString(R.string.actions_map), new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					if (mLocationListener != null) {
						MapApp app = (MapApp) MainActions.this.getApplication();
						app.mBMapMan.getLocationManager().removeUpdates(mLocationListener);
						app.mBMapMan.stop();
					}
					Intent intent = new Intent(mContext, Map.class);
					Bundle bundle = new Bundle();
					bundle.putString("city", city);
					intent.putExtras(bundle);
					startActivity(intent);
				}
			});
		}

		btnHotActions = (RadioButton) findViewById(R.id.btnHotActions);

		btnHotActions.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					String personId = (Constants.CURRENT_USER == null || Constants.CURRENT_USER.getPersonId() == null) ? ""
							: Constants.CURRENT_USER.getPersonId();
					listActions = mXmlope.getHotActionGetData(personId, city);
					mActionsListAdapter.notifyDataSetChanged();
					closeProgress();
				}
			}
		});

		btnMyActions = (RadioButton) findViewById(R.id.btnMyActions);
		btnMyActions.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					String personId = (Constants.CURRENT_USER == null || Constants.CURRENT_USER.getPersonId() == null) ? ""
							: Constants.CURRENT_USER.getPersonId();
					if (Constants.CURRENT_USER == null || Constants.CURRENT_USER.getPersonId() == null) {
						showResult(null, "确认登录", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								isReload = true;
								startActivity(new Intent(mContext, SettingAccount.class));
							}
						}, null);
					} else {
						listActions = mXmlope.getMyActionGetData(personId);
						mActionsListAdapter.notifyDataSetChanged();
					}
				}
			}
		});

		lvActions = (ListView) findViewById(R.id.lvActions);
		lvActions.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if (Constants.CURRENT_USER != null && Constants.CURRENT_USER.getPersonId() != null) {
					Intent mIntent = new Intent(mContext, ActionContent.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("Actions", listActions.get(arg2));
					mIntent.putExtras(bundle);
					startActivity(mIntent);
				}
			}
		});

		mActionsListAdapter = new ActionsListAdapter();
		lvActions.setAdapter(mActionsListAdapter);
	}

	private void loadingData() {
		checkLogin();
		btnHotActions.setChecked(true);
	}

	private void checkLogin() {
		if (!Constants.IS_LOGIN) {
			showLeftButton(getString(R.string.common_login), new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					isReload = true;
					startActivity(new Intent(mContext, SettingAccount.class));
				}
			});
		} else {
			Button btnTBLeft = (Button) findViewById(R.id.btnTBLeft);
			btnTBLeft.setVisibility(View.GONE);
		}
	}

	private static class ListHolder {
		TextView textName;
		TextView textAddress;
		TextView textStartDate;
		TextView textStartTime;
		TextView textEndDate;
		TextView textEndTime;
		TextView textNumber;
	}

	class ActionsListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return listActions==null?0:listActions.size();
		}

		@Override
		public Object getItem(int arg0) {
			return listActions.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {

			ListHolder holder;
			Actions mActions = listActions.get(position);

			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.layout_view_actions_element, null);
				holder = new ListHolder();
				holder.textName = (TextView) convertView.findViewById(R.id.tvActionsName);
				holder.textAddress = (TextView) convertView.findViewById(R.id.tvActionsAddress);
				holder.textStartDate = (TextView) convertView.findViewById(R.id.tvActionsStartDate);
				holder.textStartTime = (TextView) convertView.findViewById(R.id.tvActionsStartTime);
				holder.textEndDate = (TextView) convertView.findViewById(R.id.tvActionsEndDate);
				holder.textEndTime = (TextView) convertView.findViewById(R.id.tvActionsEndTime);
				holder.textNumber = (TextView) convertView.findViewById(R.id.tvActionsNumber);
				convertView.setTag(holder);
			} else {
				holder = (ListHolder) convertView.getTag();
			}

			holder.textName.setText(mActions.getName());
			holder.textAddress.setText(mActions.getAddress());
			String[] strStart = Util.makeStrDate(mActions.getStart_time());
			String[] strEnd = Util.makeStrDate(mActions.getEnd_time());
			holder.textStartDate.setText(strStart[0]);
			holder.textStartTime.setText(strStart[1]);
			holder.textEndDate.setText(strEnd[0]);
			holder.textEndTime.setText(strEnd[1]);
			holder.textNumber.setText(mActions.getNumber());
			return convertView;
		}
	}

}
