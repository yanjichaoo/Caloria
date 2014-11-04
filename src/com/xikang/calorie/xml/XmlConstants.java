/* ==================================================
 * 产品名: 卡路里消耗管理
 * 文件名: XmlConstants.java
 * --------------------------------------------------
 * 开发环境: JDK1.6
 * --------------------------------------------------
 * 修订履历  2011/06/13  REV.  备注
 *         2011/06/13  1.00  初版发行
 *         2011/06/19  1.01  1.4接口修正
 * --------------------------------------------------
 * (C) Copyright XIKANG CORPORATION 2011 All Rights Reserved.
 */
package com.xikang.calorie.xml;

public class XmlConstants {

	public static final String PAGE = "page";
	public static final String COMMON = "common";
	public static final String FUNCTION = "function";
	public static final String PARAMETERS = "parameters";
	public static final String ID = "id";

	public static final String RESULT = "result";
	public static final String STATUS = "status";

	// 公用，每个接口中都有这个内容
	public static final String PERSON_ID = "person_id";
	public static final String DEVICE_TYPE = "deviceType";
	public static final String IPHONE_ID = "iphone_id";
	public static final String VERSION = "version";

	/**
	 * 今日运动信息获取类
	 * 
	 * @author 张荣
	 * @version 1.00
	 * 
	 */
	public static class TodaySportInfoGetter {
		public static final String FUNCTION_NAME = "GetPersonalData_iPhone";

		/** XML请求节点属性 */
		public static final String FETCH_TIP_CONTENT = "fetchTipContent";

		/** XML解析节点 */
		public static final String HAS_DATA = "hasData";
		public static final String TODAY_SPORT_TARGET = "todaySportTarget";
		public static final String USER_NAME = "userName";
		public static final String TODAY_SPORT_VALUE = "todaySportValue";
		public static final String TODAY_SPORT_TIP = "todaySportTip";

		/** XML解析属性 */
		public static final String STEPCOUNT = "stepCount";
		public static final String DISTANCE = "distance";
		public static final String CALORIE = "calorie";
		public static final String AVERAGEHEARTRATE = "averageHeartRate";
		public static final String ELAPSEDTIME = "elapsedTime";
		public static final String AVERAGESPEED = "averageSpeed";
		public static final String TIP_TITLE = "title";
		public static final String TIP_SUMMARY = "summary";
		public static final String TIP_CONTENT = "content";

	}

	/**
	 * 运动排行信息
	 * 
	 * @author 代俊义
	 * @version 1.00
	 * 
	 */
	public static class RankingInfo {

		/**
		 * XML请求节点属性
		 */
		public static final String RANK_COUNT = "rankCount";

		/**
		 * XML解析节点属性
		 */
		public static final String STATUS = "status";
		public static final String HAS_DATA = "hasData";
		public static final String BEATPERCENT = "beatPercent";
		public static final String CURRENT = "currentRank";

		public static final String SPORTRANK = "sportRank";
		public static final String CITYRANK = "cityRank";
		public static final String GROUPRANK = "groupRank";

		public static final String RANK_PERSON_ID = "person_id";
		public static final String RANK_USERNAME = "username";
		public static final String RANK_VALUE = "rankValue";
		public static final String RANK_CALORIE = "calorie";

		public static final String CITY_CODE = "cityCode";
		public static final String BIRTHDAY = "birthday";
	}

	/**
	 * 用户注册类
	 * 
	 * @author 曲磊
	 * @version 1.00
	 */
	public static class UserRegister {

		public static final String FUNCTION_NAME = "RegisterUser_iPhone1_1";

		/**
		 * XML请求节点属性
		 */
		public static final String ID = "id";
		public static final String DEVICE_TYPE = "deviceType";
		public static final String IPHONE_ID = "iphone_id";
		public static final String EMAIL = "email";
		public static final String PASSWORD = "password";
		public static final String WATCH_ID = "watch_id";
		public static final String PHONENUM = "phoneNum";
		public static final String USERNAME = "userName";
		public static final String SUMMARYTIMERENABLED = "summaryTimerEnabled";
		public static final String ARTICLETIMERENABLED = "articleTimerEnabled";
		public static final String TARGETALERTTIMERENABLED = "targetAlertTimerEnabled";
		public static final String VERSION = "version";

		/**
		 * XML解析节点属性
		 */
		public static final String SUCCESS_CODE = "successCode";
		public static final String SUCCESS_MSG = "successMsg";
		public static final String PERSON_ID = "person_id";
		public static final String ERR_CODE = "errCode";
		public static final String ERR_MSG = "errMsg";

	}

	/**
	 * 用户登陆类
	 * 
	 * @author 曲磊
	 * @version 1.00
	 * 
	 */
	public static class UserLogin {
		public static final String FUNCTION_NAME = "Login_iPhone1_1";

		/** XML请求节点属性 */
		public static final String DEVICE_TYPE = "deviceType";
		public static final String IPHONE_ID = "iphone_id";
		public static final String DEVICE_TOKEN = "deviceToken";
		public static final String USERACCOUNT = "userAccount";
		public static final String PASSWORD = "password";
		public static final String WATCH_ID = "watch_id";
		public static final String VERSION = "version";

		/** XML解析节点属性 */
		public static final String SUCCESS_CODE = "successCode";
		public static final String SUCCESS_MSG = "successMsg";
		public static final String ERR_CODE = "errCode";
		public static final String ERR_MSG = "errMsg";
		public static final String PERSON_ID = "person_id";
		public static final String BINDED_WATCH_ID = "watch_id";

	}

	/**
	 * 账户信息获取类
	 * 
	 * @author 曲磊
	 * @version 1.00
	 * 
	 */
	public static class UserProfileGetter {
		public static final String FUNCTION_NAME = "GetUserProfile_iPhone1_1";

		/** XML请求节点属性 */
		public static final String DEVICE_TYPE = "deviceType";
		public static final String IPHONE_ID = "iphone_id";
		public static final String PERSON_ID = "person_id";
		public static final String VERSION = "version";

		/** XML解析节点 */
		public static final String USERPROFILE = "userProfile";

		/** XML解析节点属性 */
		public static final String EMAIL = "email";
		public static final String WATCH_ID = "watch_id";
		public static final String PHONENUM = "phoneNum";
		public static final String USERNAME = "userName";
		public static final String SUMMARYTIMERENABLED = "summaryTimerEnabled";
		public static final String ARTICLETIMERENABLED = "articleTimerEnabled";
		public static final String TARGETALERTTIMERENABLED = "targetAlertTimerEnabled";
	}

	/**
	 * 账户信息设置类
	 * 
	 * @author 曲磊
	 * @version 1.00
	 * 
	 */
	public static class UserProfileSetter {
		public static final String FUNCTION_NAME = "AccountConfig_iPhone1_1";
		/**
		 * XML请求节点属性
		 */
		public static final String ID = "id";
		public static final String DEVICE_TYPE = "deviceType";
		public static final String IPHONE_ID = "iphone_id";
		public static final String PERSON_ID = "person_id";
		public static final String WATCH_ID = "watch_id";
		public static final String PHONENUM = "phoneNum";
		public static final String USERNAME = "userName";
		public static final String SUMMARYTIMERENABLED = "summaryTimerEnabled";
		public static final String ARTICLETIMERENABLED = "articleTimerEnabled";
		public static final String TARGETALERTTIMERENABLED = "targetAlertTimerEnabled";
		public static final String VERSION = "version";

	}

	/**
	 * 个人信息获取类
	 * 
	 * @author 曲磊
	 * @version 1.00
	 * 
	 */
	public static class UserBasicInfoGetter {
		public static final String FUNCTION_NAME = "GetUserBasicInfo_iPhone1_1";

		/** XML请求节点属性 */
		public static final String DEVICE_TYPE = "deviceType";
		public static final String PERSON_ID = "person_id";
		public static final String VERSION = "version";

		/** XML解析节点 */
		public static final String USERBASICINFO = "userBasicInfo";

		/** XML解析节点属性 */
		public static final String BIRTHDAY = "birthday";
		public static final String GENDER = "gender";
		public static final String HEIGHT = "height";
		public static final String WEIGHT = "weight";
		public static final String TARGETWEIGHT = "targetWeight";
		public static final String CITYCODE = "cityCode";
		public static final String VITALITY = "vitality";
	}

	/**
	 * 个人信息设置类
	 * 
	 * @author 曲磊
	 * @version 1.00
	 * 
	 */
	public static class UserBasicInfoSetter {
		public static final String FUNCTION_NAME = "SetUserBasicInfo_iPhone1_1";
		/**
		 * XML请求节点属性
		 */
		public static final String ID = "id";
		public static final String DEVICE_TYPE = "deviceType";
		public static final String PERSON_ID = "person_id";
		public static final String IPHONE_ID = "iphone_id";
		public static final String BIRTHDAY = "birthday";
		public static final String GENDER = "gender";
		public static final String HEIGHT = "height";
		public static final String WEIGHT = "weight";
		public static final String TARGETWEIGHT = "targetWeight";
		public static final String CITYCODE = "cityCode";
		public static final String VITALITY = "vitality";
		public static final String VERSION = "version";

	}

	/**
	 * 运动称号获取类
	 * 
	 * @author 张荣
	 * @version 1.00
	 * 
	 */
	public static class SportTitleGetter {
		public static final String FUNCTION_NAME = "getSportTitle_iPhone";

		/** XML请求节点属性 */
		public static final String COUNTRY_CODE = "countryCode";
		public static final String LANGUAGE = "language";

		/** XML解析节点 */
		public static final String LEVEL = "level";

		/** XML解析节点属性 */
		public static final String VALUE = "value";
		public static final String NAME = "name";
	}

	/**
	 * 行政区信息获得类
	 * 
	 * @author 曲磊
	 * @version 1.00
	 * 
	 */
	public static class AreaGetter {
		public static final String FUNCTION_NAME = "GetArea_iPhone";

		/** XML请求节点属性 */
		public static final String DEVICE_TYPE = "deviceType";
		public static final String IPHONE_ID = "iphone_id";
		public static final String VERSION = "version";
		public static final String COUNTRYCODE = "countryCode";
		public static final String LANGUAGE = "language";

		/** XML解析节点 */
		public static final String NATION = "nation";
		public static final String PROVINCE = "province";
		public static final String CITY = "city";

		/** XML解析节点属性 */
		public static final String CODE = "code";
		public static final String NAME = "name";
	}

	/**
	 * 获取提醒配置
	 * 
	 * @author qulei
	 * 
	 */
	public static class RemindConfGet {
		public static final String FUNCTION_NAME = "GetRemindingConfigInfo_iPhone";
		/** info **/
		public static final String SPORTTARGET = "sportTarget";
		public static final String WEIGHT = "weight";
		/** Conf **/
		public static final String SPORTPERIOD = "sportPeriod";
		public static final String TIMETYPE = "timeType";
		public static final String TARGETRATE = "targetRate";
		public static final String REMINDINGENABLED = "remindingEnabled";
		public static final String REMINDINGTIME = "remindingTime";
		public static final String RINGTYPE = "ringType";

	}

	/**
	 * 提醒信息设置获取类
	 * 
	 * @author 闫继超
	 * @version 1.00
	 */
	public static class RemindConfSet {

		/** xml请求节点属性 */
		public static final String ID = "id";
		public static final String DEVICETYPE = "deviceType";
		public static final String PERSON_ID = "person_id";
		public static final String IPHONE_ID = "iphone_id";
		public static final String VERSION = "version";
		public static final String SPORTDISTRIBUTING = "sportDistributing";
		public static final String SPORTPERIOD = "sportperiod";
		public static final String TIMETYPE = "timeType";
		public static final String TARGETRATE = "targetRate";
		public static final String REMINDINGENABLED = "remindingEnabled";
		public static final String REMINDINGTIME = "remindingTime";
		public static final String RINGTYPE = "ringType";

		/** xml解析节点 */
		public static final String RESULT = "result";
		public static final String STATUS = "status";

	}

	/**
	 * 获取提醒信息
	 * 
	 * @author qulei
	 * 
	 */
	public static class RemindMsg {
		public static final String FUNCTION_NAME = "GetRemindingInfo_iPhone";
		/** xml请求节点属性 */
		public static final String CURRENTREMINDINGTIME = "currentRemindingTime";

		public static final String NEEDREMIND = "needRemind";
		public static final String REMINDINGINFO = "remindingInfo";

		public static final String SURPLUSCALORIE = "surplusCalorie";
		public static final String CURRENTTIMETYPE = "currentTimeType";
		public static final String ISLASTTIMEINDAY = "isLastTimeInDay";
		public static final String NEXTTIMETYPE = "nextTimeType";

		public static final String RINGTYPE = "ringType";
	}

	/**
	 * 竞赛期间运动信息获取类
	 * 
	 * @author
	 * @version 1.00
	 * 
	 */
	public static class MultiUserSportDataGetter {
		public static final String FUNCTION_NAME = "GetMultiUserSportData";
	}

	/**
	 * 历史信息汇总XML
	 * 
	 * @author
	 * @version 1.0
	 */
	public static class HistorySummaryInfo {

		/**
		 * 历史信息汇总XML解析节点属性
		 */
		public static final String STATUS = "status";
		public static final String HAS_DATA = "hasData";
		public static final String AVERAGE_SPORTDAY = "averageSportDays";
		// totalSportValue
		public static final String TOTALSPORTVALUE = "totalSportValue";
		// stepCount 历史总步数 整数
		public static final String STEP_COUNT = "stepCount";
		// distance 历史总距离数 千米，小数点后3位
		public static final String DISTANCE = "distance";
		// calorie 历史总卡路里数 Kcal，小数点后1位
		public static final String CALORIE = "calorie";
		// totalSportDays 已运动历史总天数 整数
		public static final String TOTAL_SPORTDAY = "totalSportDays";
		// totalNonSportDays 未运动历史总天数 整数
		public static final String TOTAL_NONSPORTDAY = "totalNonSportDays";
		// sportTitle
		public static final String SPORT_TITLE = "sportTitle";
		// currentLevel 当前等级 整数
		public static final String CURRENT_LEVEL = "currentLevel";
		// nextLevel 次级等级 整数
		public static final String NEXT_LEVEL = "nextLevel";
		// nextLevelDays 次级称号需要运动天数 整数
		public static final String NEXT_LEVELDAYS = "nextLevelDays";
		public static final String BEAT_PERCENT = "beatPercent";
		public static final String EXTEND_LIFEDAYS = "extendLifeDays";
	}

	/**
	 * 历史卡路里信息获取类
	 * 
	 * @author
	 * @version 1.00
	 * 
	 */
	public static class HistoryCalorieDataGetter {
		public static final String FUNCTION_NAME = "GetHistoryCalorieData_iPhone";

		/**
		 * 历史卡路里信息XML请求节点属性
		 */
		public static final String BEGINTIIME = "beginTime";// beginTime
		public static final String ENDTIME = "endTime";// endTime

		/** XML解析节点属性 */
		// totalNonSportDays 未运动历史总天数 1个，整数
		public static final String TOTAL_NONSPORT_DAYS = "totalNonSportDays";
		// totalNonBurnedCalorie 未消耗的历史总卡路里数 1个，kcal，小数点后1位
		public static final String TOTAL_NONBURNED_CALORIE = "totalNonBurnedCalorie";
		// sportData
		public static final String SPORT_DATA = "sportData";
		// Value 历史数据 非必须，如果没有数据，不显示
		public static final String VALE = "value";
		// date 当日日期 1个，YYYY-MM-DD
		public static final String DATE = "date";
		// targetCalorie 当日目标消耗卡路里数 Kcal，小数点后1位
		public static final String TARGET_CALORIE = "targetCalorie";
		// burnedCalorie 当日实际消耗卡路里数 Kcal，小数点后1位
		public static final String BURNED_CALORIE = "burnedCalorie";

	}

	/**
	 * 历史距离信息获取类
	 * 
	 * @author
	 * @version 1.00
	 * 
	 */
	public static class HistoryDistanceDataGetter {
		public static final String FUNCTION_NAME = "GetHistorySummaryDistanceData_iPhone";

		public static final String BEGINTIIME = "beginTime";// beginTime
		public static final String ENDTIME = "endTime";// endTime
		/** XML解析节点 */
		public static final String SPORT_DATA = "sportData";// sportData
		/** XML解析节点属性 */
		// totalSportDistance 已运动历史总距离 1个，千米，小数点后3位
		public static final String TOTAL_SPORT_DISTANCE = "totalSportDistance";
		// Value 历史数据 非必须，如果没有数据，不显示
		public static final String VALUE = "value";
		// date 当日日期 1个，YYYY-MM-DD
		public static final String DATE = "date";
		// distance 当日累计运动距离 千米，小数点后3位
		public static final String DISTANCE = "distance";

	}

	/**
	 * 历史步数信息获取类
	 * 
	 * @author
	 * @version 1.00
	 * 
	 */
	public static class HistoryStepCountDataGetter {
		public static final String FUNCTION_NAME = "GetHistoryStepCountData_iPhone";

		/** XML请求节点属性 */
		public static final String DEVICE_TYPE = "deviceType";
		public static final String PERSON_ID = "person_id";
		public static final String IPHONE_ID = "iphone_id";
		public static final String VERSION = "version";

	}

	/**
	 * 历史时间信息获取类
	 * 
	 * @author
	 * @version 1.00
	 * 
	 */
	public static class HistorySportTimeDataGetter {
		public static final String FUNCTION_NAME = "GetHistorySportTimeData_iPhone";

		/** XML解析节点属性 */
		// totalSportDays 当前用户已运动历史总天数 1个，整数
		public static final String TOTAL_SPORTDAYS = "totalSportDays";

		// allUserAverageSportDays 全国用户人均运动历史总天数 1个，小数点后一位
		public static final String ALLUSER_AVERAGESPORTDAYS = "allUserAverageSportDays";
		// beatPercent 在已运动历史总天数的指标上，击败全国用户的百分比 整数，0-100，X%
		public static final String BEAT_PERCENT = "beatPercent";
		// extendLifeDays 运动使得生命延长的天数 1个，小数点后一位
		public static final String EXTEND_LIFEDAYS = "extendLifeDays";

	}

	/**
	 * ==============================曲磊=======================
	 */
	public static class ActionsGetter {
		public static final String FUNCTION_NAME_HOT_ACTIONS = "GetHotActionData_iPhone";

		public static final String FUNCTION_NAME_MY_ACTIONS = "GetMyActionData_iPhone";

		public static final String CITY_CODE = "cityCode";

		public static final String ACTIONS = "actions";
		public static final String ACTIONS_ITEM = "actions_item";

		public static final String ID = "id";
		public static final String NAME = "name";
		public static final String ADDRESS = "address";
		public static final String START_TIME = "start_time";
		public static final String END_TIME = "end_time";
		public static final String NUMBER = "number";
	}

	public static class ActionsDetailsGetter {
		public static final String FUNCTION_NAME = "GetActionDetail_iPhone";
		public static final String ROUTEIDX = "routeIdx";

		public static final String JOINSTATUS = "joinstatus";
		public static final String RANK = "rank";

		public static final String MEMBERS = "members";
		public static final String MEMBERS_ITEM = "members_item";
		public static final String NAME = "name";
		public static final String VALUE = "value";
	}

	public static class ActionsJoin {
		public static final String FUNCTION_NAME = "JoinAction_iPhone";
		public static final String ROUTEIDX = "routeIdx";

		public static final String JOINSTATUS = "joinstatus";
		public static final String RANK = "rank";

		public static final String MEMBERS = "members";
		public static final String MEMBERS_ITEM = "members_item";
		public static final String NAME = "name";
		public static final String VALUE = "value";
	}

	/**
	 * ==============================曲磊=======================
	 */

	/**
	 * ==============================闫继超=======================
	 */

	public static class ActionsGetLinesGetter {
		public static final String FUNCTION_NAME = "GetMapRouteData_iPhone";
		public static final String CITYCODE = "cityCode";
		public static final String CENTER_LX = "center_lX";
		public static final String CENTER_LY = "center_lY";
		public static final String SPAN_LONGIDELTA = "span_longiDelta";
		public static final String SPAN_LANGDELTA = "span_langtDelta";
		public static final String ROUTES = "routes";
		public static final String ROUTES_ITEM = "routes_item";
		public static final String NAME = "name";
		public static final String DIST = "dist";
		public static final String NOTICE = "notice";
		public static final String STATUS = "status";
		public static final String POINTS = "points";
		public static final String POINTS_ITEM = "points_item";
		public static final String LONGI = "longi";
		public static final String LANGT = "langt";

	}

	public static class ActionSettingGetter {
		public static final String FUNCTION_NAME = "SetMyRouteData_iPhone";
		public static final String LINE_ID = "line_id";
	}
	public static class ActionsGetLinesListGetter {
		public static final String FUNCTION_NAME = "GetMyRouteListData_iPhone";
		public static final String ROUTE = "route";
		public static final String NAME = "name";
	}
	public static class ActionGetSelectRouteGetter{
		public static final String FUNCTION_NAME = "GetMySelectedRoute_iPhone";
		public static final String LINE_ID = "line_id";
		public static final String CENTER_LX = "center_lX";
		public static final String CENTER_LY = "center_lY";
		public static final String SPAN_LONGIDELTA = "span_longiDelta";
		public static final String SPAN_LANGDELTA = "span_langtDelta";
		public static final String ROUTE = "route";
		public static final String NAME = "name";
		public static final String DIST = "dist";
		public static final String NOTICE = "notice";
		public static final String POINTS = "points";
		public static final String LONGI = "longi";
		public static final String LANGT = "langt";
	}

	/**
	 * ==============================闫继超=======================
	 */
}
