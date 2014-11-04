/* ==================================================
 * 产品名: 卡路里消耗管理
 * 文件名: HistoryXmlParser.java
 * --------------------------------------------------
 * 开发环境: JDK1.6
 * --------------------------------------------------
 * 修订履历  2011/06/15  REV.  备注
 *         2011/06/15  1.00  初版发行
 * --------------------------------------------------
 * (C) Copyright XIKANG CORPORATION 2011 All Rights Reserved.
 */
package com.xikang.calorie.xml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

import com.xikang.calorie.domain.HistoryCalorieData;
import com.xikang.calorie.domain.HistoryDistanceData;
import com.xikang.calorie.xml.XmlConstants.HistoryCalorieDataGetter;
import com.xikang.calorie.xml.XmlConstants.HistoryDistanceDataGetter;
import com.xikang.calorie.xml.XmlConstants.HistorySportTimeDataGetter;
import com.xikang.calorie.xml.XmlConstants.HistorySummaryInfo;

/**
 * 
 * 历史数据接口XML解析类
 * 
 * 
 * <pre>
 * 
 * 2011/06/30, 代俊义
 * 
 * 修订履历：YYYY/MM/DD 修订者  修订内容
 * 
 * </pre>
 * 
 * @author 代俊义
 * @version 1.00
 */
public class HistoryXmlParser extends BaseXmlParser {

	public HistoryXmlParser(Context context) {
		super(context);
	}

	/**
	 * 构建历史信息汇总xml
	 * 
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public String buildSummaryXML(String persionId) throws Exception {
		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream dataOutputStream = new DataOutputStream(arrayOutputStream);

		XmlSerializer serializer = Xml.newSerializer();
		serializer.setOutput(dataOutputStream, "utf-8");

		serializer.startDocument("utf-8", true);

		serializer.startTag(null, XmlConstants.PAGE);
		serializer.startTag(null, XmlConstants.COMMON);
		serializer.startTag(null, XmlConstants.FUNCTION);

		serializer.attribute(null, XmlConstants.ID, "GetHistorySummaryData_iPhone");
		serializer.attribute(null, XmlConstants.DEVICE_TYPE, DEVICE_TYPE);
		serializer.attribute(null, XmlConstants.PERSON_ID, persionId);
		serializer.attribute(null, XmlConstants.IPHONE_ID, iphone_id);
		serializer.attribute(null, XmlConstants.VERSION, version);

		serializer.endTag(null, XmlConstants.FUNCTION);
		serializer.endTag(null, XmlConstants.COMMON);
		serializer.endTag(null, XmlConstants.PAGE);

		serializer.endDocument();
		dataOutputStream.close();

		return arrayOutputStream.toString();
	}

	/**
	 * 解析返回的历史信息汇总xml
	 * 
	 * @param writeBack
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> parseSummaryXml(String writeBack) throws Exception {
		Map<String, String> result = new HashMap<String, String>();

		byte[] bytes = writeBack.getBytes("UTF-8");
		ByteArrayInputStream is = new ByteArrayInputStream(bytes);
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(is, "utf-8");
		int type = parser.getEventType();
		while (type != XmlPullParser.END_DOCUMENT) {
			switch (type) {
			case XmlPullParser.START_DOCUMENT:
				break;
			case XmlPullParser.START_TAG:
				if (XmlConstants.RESULT.equals(parser.getName())) {
					if ("0".equals(parser.getAttributeValue(null, XmlConstants.STATUS))) {
						result.put(XmlConstants.STATUS, parser.getAttributeValue(null, XmlConstants.STATUS));
						return result;
					}
				}
				if (HistorySummaryInfo.HAS_DATA.equals(parser.getName())) {
					result.put(HistorySummaryInfo.HAS_DATA, parser.getAttributeValue(null, HistorySummaryInfo.HAS_DATA));
				} else if (HistorySummaryInfo.AVERAGE_SPORTDAY.equals(parser.getName())) {
					result.put(HistorySummaryInfo.AVERAGE_SPORTDAY,
							parser.getAttributeValue(null, HistorySummaryInfo.AVERAGE_SPORTDAY));
				} else if (HistorySummaryInfo.TOTALSPORTVALUE.equals(parser.getName())) {
					result.put(HistorySummaryInfo.STEP_COUNT,
							parser.getAttributeValue(null, HistorySummaryInfo.STEP_COUNT));
					result.put(HistorySummaryInfo.DISTANCE, parser.getAttributeValue(null, HistorySummaryInfo.DISTANCE));
					result.put(HistorySummaryInfo.CALORIE, parser.getAttributeValue(null, HistorySummaryInfo.CALORIE));
					result.put(HistorySummaryInfo.TOTAL_SPORTDAY,
							parser.getAttributeValue(null, HistorySummaryInfo.TOTAL_SPORTDAY));
					result.put(HistorySummaryInfo.TOTAL_NONSPORTDAY,
							parser.getAttributeValue(null, HistorySummaryInfo.TOTAL_NONSPORTDAY));
				} else if (HistorySummaryInfo.SPORT_TITLE.equals(parser.getName())) {
					result.put(HistorySummaryInfo.CURRENT_LEVEL,
							parser.getAttributeValue(null, HistorySummaryInfo.CURRENT_LEVEL));
					result.put(HistorySummaryInfo.NEXT_LEVEL,
							parser.getAttributeValue(null, HistorySummaryInfo.NEXT_LEVEL));
					result.put(HistorySummaryInfo.NEXT_LEVELDAYS,
							parser.getAttributeValue(null, HistorySummaryInfo.NEXT_LEVELDAYS));
				} else if (HistorySummaryInfo.BEAT_PERCENT.equals(parser.getName())) {
					result.put(HistorySummaryInfo.BEAT_PERCENT, parser.nextText());
				} else if (HistorySummaryInfo.EXTEND_LIFEDAYS.equals(parser.getName())) {
					result.put(HistorySummaryInfo.EXTEND_LIFEDAYS, parser.nextText());
				}
				break;
			case XmlPullParser.END_TAG:
				break;
			}
			type = parser.next();
		}
		is.close();
		return result;
	}

	/**
	 * 构建历史卡路里信息xml
	 * 
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public String buildHistoryColarieDataXML(String persionId, String beginTime, String endTime) throws Exception {
		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream dataOutputStream = new DataOutputStream(arrayOutputStream);

		XmlSerializer serializer = Xml.newSerializer();
		serializer.setOutput(dataOutputStream, "utf-8");

		serializer.startDocument("utf-8", true);

		serializer.startTag(null, XmlConstants.PAGE);
		serializer.startTag(null, XmlConstants.COMMON);
		// function
		serializer.startTag(null, XmlConstants.FUNCTION);

		serializer.attribute(null, XmlConstants.ID, "GetHistoryCalorieData_iPhone");
		serializer.attribute(null, XmlConstants.DEVICE_TYPE, DEVICE_TYPE);
		serializer.attribute(null, XmlConstants.PERSON_ID, persionId);
		serializer.attribute(null, XmlConstants.IPHONE_ID, iphone_id);
		serializer.attribute(null, XmlConstants.VERSION, version);

		serializer.endTag(null, XmlConstants.FUNCTION);
		// start parameters
		serializer.startTag(null, XmlConstants.PARAMETERS);
		serializer.attribute(null, HistoryCalorieDataGetter.BEGINTIIME, beginTime);
		serializer.attribute(null, HistoryCalorieDataGetter.ENDTIME, endTime);
		serializer.endTag(null, XmlConstants.PARAMETERS);
		// end parameters
		serializer.endTag(null, XmlConstants.COMMON);
		serializer.endTag(null, XmlConstants.PAGE);

		serializer.endDocument();
		dataOutputStream.close();

		Log.d("history calorie xml", arrayOutputStream.toString());
		return arrayOutputStream.toString();
	}

	/**
	 * 解析返回的历史卡路里信息xml
	 * 
	 * @param writeBack
	 * @return
	 * @throws Exception
	 */
	public HistoryCalorieData parseHistoryCalorieXml(String writeBack) throws Exception {
		List<Map<String, String>> result = null;
		HistoryCalorieData hisCalorie = null;
		byte[] bytes = writeBack.getBytes("UTF-8");
		ByteArrayInputStream is = new ByteArrayInputStream(bytes);
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(is, "utf-8");
		int type = parser.getEventType();
		while (type != XmlPullParser.END_DOCUMENT) {
			switch (type) {
			case XmlPullParser.START_DOCUMENT:
				break;
			case XmlPullParser.START_TAG:
				if (XmlConstants.RESULT.equals(parser.getName())) {
					if ("0".equals(parser.getAttributeValue(null, XmlConstants.STATUS))) {
						return hisCalorie;
					} else {
						hisCalorie = new HistoryCalorieData();
					}
				}
				if (HistoryCalorieDataGetter.TOTAL_NONSPORT_DAYS.equals(parser.getName())) {
					hisCalorie.setNonSportDay(Float.parseFloat(parser.nextText()));
				} else if (HistoryCalorieDataGetter.TOTAL_NONBURNED_CALORIE.equals(parser.getName())) {
					hisCalorie.setNonBurnedCalorie(Float.parseFloat(parser.nextText()));
				} else if (HistoryCalorieDataGetter.SPORT_DATA.equals(parser.getName())) {
					result = new ArrayList<Map<String, String>>();
				} else if (HistoryCalorieDataGetter.VALE.equals(parser.getName())) {
					Map<String, String> value = new HashMap<String, String>();
					value.put(HistoryCalorieDataGetter.DATE,
							parser.getAttributeValue(null, HistoryCalorieDataGetter.DATE));
					value.put(HistoryCalorieDataGetter.TARGET_CALORIE,
							parser.getAttributeValue(null, HistoryCalorieDataGetter.TARGET_CALORIE));
					value.put(HistoryCalorieDataGetter.BURNED_CALORIE,
							parser.getAttributeValue(null, HistoryCalorieDataGetter.BURNED_CALORIE));
					result.add(value);
				}
				break;
			case XmlPullParser.END_TAG:
				if (HistoryCalorieDataGetter.SPORT_DATA.equals(parser.getName())) {
					hisCalorie.setValueList(result);
				}
				break;
			}
			type = parser.next();
		}
		is.close();
		return hisCalorie;
	}

	/**
	 * 构建历史卡路里信息xml
	 * 
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public String buildHistoryTimeDataXML(String persionId) throws Exception {
		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream dataOutputStream = new DataOutputStream(arrayOutputStream);

		XmlSerializer serializer = Xml.newSerializer();
		serializer.setOutput(dataOutputStream, "utf-8");

		serializer.startDocument("utf-8", true);

		serializer.startTag(null, XmlConstants.PAGE);
		serializer.startTag(null, XmlConstants.COMMON);
		// function
		serializer.startTag(null, XmlConstants.FUNCTION);

		serializer.attribute(null, XmlConstants.ID, "GetSummarySportTimeData_iPhone");
		serializer.attribute(null, XmlConstants.DEVICE_TYPE, DEVICE_TYPE);
		serializer.attribute(null, XmlConstants.PERSON_ID, persionId);
		serializer.attribute(null, XmlConstants.IPHONE_ID, iphone_id);
		serializer.attribute(null, XmlConstants.VERSION, version);

		serializer.endTag(null, XmlConstants.FUNCTION);
		serializer.endTag(null, XmlConstants.COMMON);
		serializer.endTag(null, XmlConstants.PAGE);

		serializer.endDocument();
		dataOutputStream.close();

		Log.d("history time xml", arrayOutputStream.toString());
		return arrayOutputStream.toString();
	}

	/**
	 * 解析返回的历史卡路里信息xml
	 * 
	 * @param writeBack
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> parseHistoryTimeXml(String writeBack) throws Exception {
		Map<String, String> result = null;
		byte[] bytes = writeBack.getBytes("UTF-8");
		ByteArrayInputStream is = new ByteArrayInputStream(bytes);
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(is, "utf-8");
		int type = parser.getEventType();
		while (type != XmlPullParser.END_DOCUMENT) {
			switch (type) {
			case XmlPullParser.START_DOCUMENT:
				break;
			case XmlPullParser.START_TAG:
				if (XmlConstants.RESULT.equals(parser.getName())) {
					if ("0".equals(parser.getAttributeValue(null, XmlConstants.STATUS))) {
						return null;
					} else {
						result = new HashMap<String, String>();
					}
				}
				if (HistorySportTimeDataGetter.TOTAL_SPORTDAYS.equals(parser.getName())) {
					String totalSportDay = parser.nextText();
					result.put(HistorySportTimeDataGetter.TOTAL_SPORTDAYS, totalSportDay);
				} else if (HistorySportTimeDataGetter.ALLUSER_AVERAGESPORTDAYS.equals(parser.getName())) {
					String avgSportDay = parser.nextText();
					result.put(HistorySportTimeDataGetter.ALLUSER_AVERAGESPORTDAYS, avgSportDay);
				} else if (HistorySportTimeDataGetter.BEAT_PERCENT.equals(parser.getName())) {
					result.put(HistorySportTimeDataGetter.BEAT_PERCENT, parser.nextText());
				} else if (HistorySportTimeDataGetter.EXTEND_LIFEDAYS.equals(parser.getName())) {
					result.put(HistorySportTimeDataGetter.EXTEND_LIFEDAYS, parser.nextText());
				}
				break;
			case XmlPullParser.END_TAG:
				break;
			}
			type = parser.next();
		}
		is.close();
		return result;
	}

	/**
	 * 构建历史卡路里信息xml
	 * 
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public String buildHistoryDistanceDataXML(String persionId, String beginTime, String endTime) throws Exception {
		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream dataOutputStream = new DataOutputStream(arrayOutputStream);

		XmlSerializer serializer = Xml.newSerializer();
		serializer.setOutput(dataOutputStream, "utf-8");

		serializer.startDocument("utf-8", true);

		serializer.startTag(null, XmlConstants.PAGE);
		serializer.startTag(null, XmlConstants.COMMON);
		// function
		serializer.startTag(null, XmlConstants.FUNCTION);

		serializer.attribute(null, XmlConstants.ID, HistoryDistanceDataGetter.FUNCTION_NAME);
		serializer.attribute(null, XmlConstants.DEVICE_TYPE, DEVICE_TYPE);
		serializer.attribute(null, XmlConstants.PERSON_ID, persionId);
		serializer.attribute(null, XmlConstants.IPHONE_ID, iphone_id);
		serializer.attribute(null, XmlConstants.VERSION, "1");

		serializer.endTag(null, XmlConstants.FUNCTION);
		// start parameters
		serializer.startTag(null, XmlConstants.PARAMETERS);
		serializer.attribute(null, HistoryDistanceDataGetter.BEGINTIIME, beginTime);
		serializer.attribute(null, HistoryDistanceDataGetter.ENDTIME, endTime);
		serializer.endTag(null, XmlConstants.PARAMETERS);
		// end parameters
		serializer.endTag(null, XmlConstants.COMMON);
		serializer.endTag(null, XmlConstants.PAGE);

		serializer.endDocument();
		dataOutputStream.close();

		Log.d("history distance xml", arrayOutputStream.toString());
		return arrayOutputStream.toString();
	}

	/**
	 * 解析返回的历史距离信息xml
	 * 
	 * @param writeBack
	 * @return
	 * @throws Exception
	 */
	public HistoryDistanceData parseHistoryDistanceXml(String writeBack) throws Exception {
		List<Map<String, String>> result = null;
		HistoryDistanceData hisDistance = null;
		byte[] bytes = writeBack.getBytes("UTF-8");
		ByteArrayInputStream is = new ByteArrayInputStream(bytes);
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(is, "utf-8");
		int type = parser.getEventType();
		while (type != XmlPullParser.END_DOCUMENT) {
			switch (type) {
			case XmlPullParser.START_DOCUMENT:
				break;
			case XmlPullParser.START_TAG:
				if (XmlConstants.RESULT.equals(parser.getName())) {
					if ("0".equals(parser.getAttributeValue(null, XmlConstants.STATUS))) {
						return hisDistance;
					} else {
						hisDistance = new HistoryDistanceData();
					}
				}
				if (HistoryDistanceDataGetter.TOTAL_SPORT_DISTANCE.equals(parser.getName())) {
					hisDistance.setTotalSportDistance(Float.parseFloat(parser.nextText()));
				} else if (HistoryDistanceDataGetter.SPORT_DATA.equals(parser.getName())) {
					result = new ArrayList<Map<String, String>>();
				} else if (HistoryDistanceDataGetter.VALUE.equals(parser.getName())) {
					Map<String, String> value = new HashMap<String, String>();
					value.put(HistoryDistanceDataGetter.DATE,
							parser.getAttributeValue(null, HistoryDistanceDataGetter.DATE));
					value.put(HistoryDistanceDataGetter.DISTANCE,
							parser.getAttributeValue(null, HistoryDistanceDataGetter.DISTANCE));
					result.add(value);
				}
				break;
			case XmlPullParser.END_TAG:
				if (HistoryDistanceDataGetter.SPORT_DATA.equals(parser.getName())) {
					hisDistance.setValueList(result);
				}
				break;
			}
			type = parser.next();
		}
		is.close();
		return hisDistance;
	}
}
