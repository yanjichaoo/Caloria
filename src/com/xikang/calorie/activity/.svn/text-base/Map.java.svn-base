package com.xikang.calorie.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.ItemizedOverlay;
import com.baidu.mapapi.LocationListener;
import com.baidu.mapapi.MKAddrInfo;
import com.baidu.mapapi.MKDrivingRouteResult;
import com.baidu.mapapi.MKPoiResult;
import com.baidu.mapapi.MKSearch;
import com.baidu.mapapi.MKSearchListener;
import com.baidu.mapapi.MKTransitRouteResult;
import com.baidu.mapapi.MKWalkingRouteResult;
import com.baidu.mapapi.MapActivity;
import com.baidu.mapapi.MapView;
import com.baidu.mapapi.OverlayItem;
import com.baidu.mapapi.Projection;
import com.xikang.calorie.common.Constants;
import com.xikang.calorie.domain.ActionLines;
import com.xikang.calorie.domain.ActionLines.MyPoint;
import com.xikang.calorie.domain.Line;
import com.xikang.calorie.xml.XmlOpe;

public class Map extends MapActivity {
	//经纬度转换
	private Projection projection;
	static MapView mMapView = null;
	// 位置监听器
	private LocationListener mLocationListener = null;
	// 用户ID
	private String personId = null;
	// 所在城市
	private String city = null;
	// 缓存
	private String cityCache = null;
	// popList保存路线名称
	private String popRouteName = null;
	// 下拉列表按钮
	private LinearLayout btnTBRight;
	// 下拉列表
	private ListView listView;
	// 点击路线弹出框中的文本信息
	private TextView routeName;
	private TextView routeDist;
	private TextView routeNotice;
	// 下拉列表图标
	private ImageView imageView;
	// 关注图标
	private ImageView notice;
	private XmlOpe mXmlOpe;
	// 路线图标
	private Drawable marker;
	// 下拉列表框
	private PopupWindow pop;
	// 下拉列表加载布局的视图
	private View popView;
	// 点击弹出框加载布局的视图
	static View clickView;
	// 点击路线后保存路线信息的变量
	private String routeId = null;
	private String status = null;
	// 保存下拉列表加载的路线列表
	private List<Line> list;
	// 保存城市内所有路线信息
	private List<Line> routeList;
	// 标识选中路线在城市路线list中的位置
	private int flag;
	// 查询实例
	private MKSearch mSearch = null;
	// 等待对话框
	private ProgressDialog processDialog;
	//应用对象
	MapApp app;
	//画笔
	private Paint paint = null;
	//所有路线经纬度点集合
	private List<List<GeoPoint>> GeoPointList;
	//当前位置经纬度
	private GeoPoint gp;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.layout_map);
		mXmlOpe = new XmlOpe(Map.this);

		initMap();
		getLocation();
		init();
		showProgress(this.getString(R.string.setting_progress_message_loading));
		new DataThread().start();

	}

	private void getLocation() {
		// 将地图的位置平移到手机当前位置
		mLocationListener = new LocationListener() {
			@Override
			public void onLocationChanged(Location location) {
				if (location != null) {
					GeoPoint pt = new GeoPoint((int) (location.getLatitude() * 1e6), (int) (location.getLongitude() * 1e6));
					gp=pt;
					animate(pt);

				}
			}
		};
		
	
		app.mBMapMan.getLocationManager().requestLocationUpdates(mLocationListener);
		app.mBMapMan.start();
	}

	private void initMap() {

		// 启动百度地图管理器
		app = (MapApp) this.getApplication();
		if (app.mBMapMan == null) {
			app.mBMapMan = new BMapManager(getApplication());
			app.mBMapMan.init(app.mStrKey, new MapApp.MyGeneralListener());
		}

		super.initMapActivity(app.mBMapMan);

		mMapView = (MapView) findViewById(R.id.bmapView);
		// 放大缩小
		mMapView.setBuiltInZoomControls(true);
		// 交通图
		mMapView.setTraffic(true);
		mMapView.setDrawOverlayWhenZooming(true);

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
				cityCache = arg0.addressComponents.city;
				if (city.equals(cityCache)) {

					animate(arg0.geoPt);
					for (int i = 0; i < routeList.size(); i++) {
						if (popRouteName.equals(routeList.get(i).getName())) {
							popupWindow(arg0.geoPt, i);
							break;
						}
					}
				} else {
					city = arg0.addressComponents.city;
					loadingData(cityCache);
					mMapView.getOverlays().add(new OverItemT(marker, Map.this, GeoPointList, routeList));
					animate(arg0.geoPt);

					for (int i = 0; i < routeList.size(); i++) {
						if (popRouteName.equals(routeList.get(i).getName())) {
							System.out.println(i + "$$");
							popupWindow(arg0.geoPt, i);
							break;
						}
					}

				}
			}
		});

		// 加载路线起始点图标
		marker = getResources().getDrawable(R.drawable.startpoint);
		marker.setBounds(0, 0, marker.getIntrinsicWidth(), marker.getIntrinsicHeight());

	}

	class DataThread extends Thread {
		@Override
		public void run() {
			super.run();
			loadingData(city);

			Message msg = Message.obtain();
			handler.sendMessage(msg);
		}
	}

	/**
	 * 加载路线数据
	 */
	private void loadingData(String city) {
		ActionLines mActionLines = mXmlOpe.getActionGetLines(personId, city);
		if(mActionLines!=null){
			routeList = mActionLines.getLines();
			GeoPointList = buildPointList(routeList);
		}
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if(routeList!=null){
			mMapView.getOverlays().add(new OverItemT(marker, Map.this, GeoPointList, routeList));
			animate(gp);
			closeProgress();
			}else{
				closeProgress();
				Toast.makeText(Map.this, Map.this.getString(R.string.actions_map_loading), Toast.LENGTH_LONG).show();
			}
		}
	};

	/**
	 * 实例控件
	 */
	private void init() {
		 Bundle bundle = this.getIntent().getExtras();
		 city = bundle.getString("city");
		
		paint = new Paint();
		paint.setColor(Color.BLUE);
		paint.setStrokeWidth(2);
		paint.setDither(true);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeJoin(Paint.Join.ROUND);
		paint.setStrokeCap(Paint.Cap.ROUND);
		// 加载下拉列表框
		LayoutInflater mInflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
		popView = mInflater.inflate(R.layout.layout_map_pop, null);
		pop = new PopupWindow(popView, 200, 400);
		// 为window设置隐藏监听器 改变图标
		pop.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				imageView.setImageResource(R.drawable.corner_up);
			}
		});
		// 设置框背景 必加！！
		pop.setBackgroundDrawable(new BitmapDrawable());

		// 下拉列表按钮
		btnTBRight = (LinearLayout) findViewById(R.id.layout_button);
		btnTBRight.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 窗口是否在显示
				if (!pop.isShowing()) {
					// 加载列表数据
					
					list = (List<Line>) mXmlOpe.getActionGetLinesList(personId);
					if (list != null) {
						listView.setAdapter(new lineAdapter(list));
						imageView.setImageResource(R.drawable.corner_down);
						pop.setFocusable(true);
						pop.update();
						// 显示下拉列表
						pop.showAsDropDown(v);
					} else {
						Toast.makeText(Map.this, Map.this.getString(R.string.common_failed), Toast.LENGTH_LONG).show();
					}
				} else {
					pop.dismiss();
					imageView.setImageResource(R.drawable.corner_up);
				}
			}
		});
		// 下拉列表内容
		listView = (ListView) popView.findViewById(R.id.lineslist);

		listView.setAdapter(null);
		// 为listview添加点击事件
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				pop.dismiss();
				// 确定路线
				
				Line line = mXmlOpe.getActionGetSelectRoute(personId, list.get(position).getId());
				if(line!=null){
					popRouteName = line.getName();
					List<MyPoint> arrayPoint = line.getPoints();
					int longi = arrayPoint.get(0).getLongi();
					int langt = arrayPoint.get(0).getLangt();
					// 平移到路线起点
					GeoPoint po = new GeoPoint(langt, longi);
					mSearch.reverseGeocode(po);
				}else{
					Toast.makeText(Map.this, Map.this.getString(R.string.common_failed), Toast.LENGTH_LONG).show();
				}
				
			}
		});
		// 下拉列表图标
		imageView = (ImageView) findViewById(R.id.image_corner);
		// 点击路线弹出框
		clickView = mInflater.inflate(R.layout.layout_map_clickpop, null);
		mMapView.addView(clickView, new MapView.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, null,
				MapView.LayoutParams.TOP_LEFT));
		clickView.setVisibility(View.GONE);
		// 关注按钮
		notice = (ImageView) clickView.findViewById(R.id.notice);
		// 关注按钮点击事件
		notice.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Map.clickView.setVisibility(View.GONE);
				// 判断关注按钮实际操作
				if ("0".equals(status)) {
					boolean result = mXmlOpe.setActionRouteStatus(personId, routeId, "1");
					if (result == true) {
						routeList.get(flag).setStatus("1");
					}
				} else {
					boolean result = mXmlOpe.setActionRouteStatus("personId", routeId, "0");
					if (result == true) {
						routeList.get(flag).setStatus("0");
					}
				}

				// 变量失效
				routeId = null;
				status = null;
				flag = -1;
			}
		});
		// 实例文本
		routeName = (TextView) clickView.findViewById(R.id.routename);
		routeDist = (TextView) clickView.findViewById(R.id.routedist);
		routeNotice = (TextView) clickView.findViewById(R.id.routenotice);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (!Constants.IS_LOGIN) {
			btnTBRight.setClickable(false);
		} else {
			btnTBRight.setClickable(true);
			personId=Constants.CURRENT_USER.getPersonId();
		}

	}

	private void popupWindow(GeoPoint pt, int i) {

		Line line = routeList.get(i);
		routeId = line.getId();
		Line selectRoute=mXmlOpe.getActionGetSelectRoute(personId, routeId);
		if(selectRoute!=null){
			String name = selectRoute.getName();

			if (name.length() > 8) {
				name = name.substring(0, 7).concat("...");
			}
			routeName.setText(name);
			routeDist.setText(selectRoute.getDist());

			routeNotice.setText(Map.this.getString(R.string.actions_map_notice).replace("num", selectRoute.getNotice()));

			status = line.getStatus();
			flag = i;
			// 根据路线关注情况设置显示图标
			if ("0".equals(status)) {
				if (!Constants.IS_LOGIN) {
					notice.setVisibility(View.GONE);
				} else {
					notice.setImageResource(R.drawable.btn_guanzhu);
				}
				
				
			} else {
				if (!Constants.IS_LOGIN) {
					notice.setVisibility(View.GONE);
				} else {
					notice.setImageResource(R.drawable.btn_quxiao);
				}
				
				
			}
			Map.mMapView.updateViewLayout(Map.clickView, new MapView.LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT, pt, MapView.LayoutParams.BOTTOM_CENTER));
			System.out.println(pt.getLongitudeE6()+"####"+pt.getLatitudeE6());
			Map.clickView.setVisibility(View.VISIBLE);
		}else{
			Toast.makeText(Map.this, Map.this.getString(R.string.common_failed), Toast.LENGTH_LONG).show();
		}
		
	}

	private List<List<GeoPoint>> buildPointList(List<Line> lines) {
		List<List<GeoPoint>> arrayList = new ArrayList<List<GeoPoint>>();
		List<GeoPoint> GeoList = null;
		GeoPoint gPoint;
		List<MyPoint> points;
		int lineSize = lines.size();
		for (int i = 0; i < lineSize; i++) {
			GeoList = new ArrayList<GeoPoint>();
			points = lines.get(i).getPoints();
			int pointSize = points.size();
			for (int j = 0; j < pointSize; j++) {
				int longi1 = points.get(j).getLongi();
				int langt1 = points.get(j).getLangt();
				gPoint = new GeoPoint(langt1, longi1);
				GeoList.add(gPoint);
			}
			arrayList.add(GeoList);
		}
		return arrayList;
	}

	/**
	 * 弹出进度框
	 * 
	 * @param message
	 */
	public void showProgress(final String message) {
		Map.this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				processDialog = new ProgressDialog(Map.this);
				processDialog.setMessage(message);
				processDialog.setIndeterminate(true);
				// processDialog.setCancelable(false);
				processDialog.show();

			}
		});
	}

	/**
	 * 关闭进度框
	 * 
	 * @param message
	 */
	public void closeProgress() {
		Map.this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (processDialog != null && processDialog.isShowing()) {
					processDialog.dismiss();
				}
			}
		});
	}

	private void animate(GeoPoint po) {
		mMapView.getController().animateTo(po);
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
	protected void onStop() {
		MapApp app = (MapApp) this.getApplication();
		app.mBMapMan.getLocationManager().removeUpdates(mLocationListener);
		app.mBMapMan.stop();
		super.onStop();
	}

	static class ListItem {
		TextView text;
	}

	/**
	 * 下拉列表适配器
	 * 
	 * @author yanjichao
	 * 
	 */
	class lineAdapter extends BaseAdapter {
		List<Line> linesList;
		ListItem item;

		public lineAdapter(List<Line> lines) {
			super();
			linesList = lines;
		}

		@Override
		public int getCount() {
			return linesList != null ? linesList.size() : 0;
		}

		@Override
		public Object getItem(int position) {
			return linesList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			if (convertView == null) {
				convertView = Map.this.getLayoutInflater().inflate(R.layout.layout_map_pop_item, null);
				item = new ListItem();
				item.text = (TextView) convertView.findViewById(R.id.tvRoute);
				convertView.setTag(item);
			} else {
				item = (ListItem) convertView.getTag();
			}
			item.text.setText(linesList.get(position).getName());
			return convertView;
		}
	}

	/**
	 * 在地图中画出路线
	 * 
	 * @author yanjichao
	 * 
	 */
	class OverItemT extends ItemizedOverlay<OverlayItem> {
		private List<OverlayItem> mGeoList = new ArrayList<OverlayItem>();
		private Drawable marker;
//		private Context mContext;
		List<List<GeoPoint>> mList;


		int a = 0;

		public OverItemT(Drawable marker, Context context, List<List<GeoPoint>> gList, List<Line> lines) {
			super(boundCenterBottom(marker));
			this.marker = marker;
//			this.mContext = context;
			mList = gList;
			List<GeoPoint> pointList;
			int lineSize = mList.size();
			for (int i = 0; i < lineSize; i++) {
				pointList = mList.get(i);
				int startLongi = pointList.get(0).getLongitudeE6();
				int startLangt = pointList.get(0).getLatitudeE6();
				GeoPoint startPoint = new GeoPoint(startLangt, startLongi);
				System.out.println(startPoint.getLatitudeE6()+" "+startPoint.getLongitudeE6());
				// 添加图标点
				mGeoList.add(new OverlayItem(startPoint, lines.get(i).getStatus(), lines.get(i).getId()));

			}

			populate();
		}

		@Override
		protected OverlayItem createItem(int i) {
			return mGeoList.get(i);
		}

		@Override
		public void draw(Canvas canvas, MapView mapView, boolean shadow) {

			projection = mMapView.getProjection();
//			int lineSize = mList.size();
			GeoPoint gPoint;

			Point point = new Point();
			int pointSize;
			Path path = null;
			for (List<GeoPoint> pointList:mList) {
				pointSize = pointList.size();
				path = new Path();

				// 画路线
				gPoint = pointList.get(0);
				projection.toPixels(gPoint, point);
				path.moveTo(point.x, point.y);
				for (GeoPoint gp:pointList) {	
					projection.toPixels(gp, point);
					path.lineTo(point.x, point.y);
				}
				paint.setColor(Color.RED);
				
				gPoint = new GeoPoint(pointList.get(pointSize - 1).getLatitudeE6(), pointList.get(pointSize - 1).getLongitudeE6());
				projection.toPixels(gPoint, point);
				canvas.drawCircle(point.x, point.y, 4, paint);
				paint.setColor(Color.BLUE);
				paint.setStrokeJoin(Paint.Join.ROUND);
				canvas.drawPath(path, paint);
			}

			super.draw(canvas, mapView, shadow);
			boundCenterBottom(marker);
		}

		/**
		 * 点击其他位置时隐藏弹出框
		 */
		@Override
		public boolean onTap(GeoPoint arg0, MapView arg1) {
			Map.clickView.setVisibility(View.GONE);
			routeId = null;
			status = null;
			if (flag != -1) {
				flag = -1;
			}
			return super.onTap(arg0, arg1);
		}

		/**
		 * 点击图标时显示弹出框
		 */
		@Override
		protected boolean onTap(int i) {
			setFocus(mGeoList.get(i));
			System.out.println(mGeoList.size()+"#####");
			// 平移至图标处
			GeoPoint pt = mGeoList.get(i).getPoint();
			animate(pt);
			popupWindow(pt, i);
			return super.onTap(i);
		}

		@Override
		public int size() {
			return mGeoList.size();
		}
	}
}
