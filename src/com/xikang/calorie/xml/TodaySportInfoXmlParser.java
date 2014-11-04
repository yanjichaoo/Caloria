/* ==================================================
 * 产品名: 卡路里消耗管理
 * 文件名: TodaySportInfoXmlParser.java
 * --------------------------------------------------
 * 开发环境: JDK1.6
 * --------------------------------------------------
 * 修订履历  YYYY/MM/DD  REV.  备注
 *         2011/06/13  1.00  初版发行
 *         2011/06/19  1.01  1.4接口修正
 * --------------------------------------------------
 * (C) Copyright XIKANG CORPORATION 2011 All Rights Reserved.
 */
package com.xikang.calorie.xml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import android.content.Context;
import android.util.Xml;

import com.xikang.calorie.domain.TodaySportInfo;
import com.xikang.calorie.xml.XmlConstants.TodaySportInfoGetter;

/**
 * 
 * 个人运动数据接口XML解析类
 * 
 * 
 * <pre>
 * 
 * 2011/06/13, 曲磊
 * 
 * 修订履历：YYYY/MM/DD 修订者  修订内容
 *         2011/06/19 张荣    1.4接口修正
 *         2011/08/10 张荣    1.7接口修正
 * 
 * </pre>
 * 
 * @author 曲磊
 * @version 1.00
 */
public class TodaySportInfoXmlParser extends BaseXmlParser {

	public TodaySportInfoXmlParser(Context context) {
		super(context);
	}

	public String buildXML(String personID, String fetchTipContent) throws Exception {

		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream dataOutputStream = new DataOutputStream(arrayOutputStream);

		XmlSerializer serializer = Xml.newSerializer();
		serializer.setOutput(dataOutputStream, "utf-8");

		serializer.startDocument("utf-8", true);

		serializer.startTag(null, XmlConstants.PAGE);
		serializer.startTag(null, XmlConstants.COMMON);
		serializer.startTag(null, XmlConstants.FUNCTION);

		serializer.attribute(null, XmlConstants.ID, "GetPersonalData_iPhone");
		serializer.attribute(null, XmlConstants.DEVICE_TYPE, DEVICE_TYPE);
		serializer.attribute(null, XmlConstants.PERSON_ID, personID);
		serializer.attribute(null, XmlConstants.IPHONE_ID, iphone_id);
		serializer.attribute(null, TodaySportInfoGetter.FETCH_TIP_CONTENT, fetchTipContent);
		serializer.attribute(null, XmlConstants.VERSION, version);

		serializer.endTag(null, XmlConstants.FUNCTION);
		serializer.endTag(null, XmlConstants.COMMON);
		serializer.endTag(null, XmlConstants.PAGE);

		serializer.endDocument();
		dataOutputStream.close();

		return arrayOutputStream.toString();
	}

	public TodaySportInfo parseXml(String writeBack) throws Exception {

		TodaySportInfo todaySportInfo = new TodaySportInfo();

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
					}
				}

				if (TodaySportInfoGetter.HAS_DATA.equals(parser.getName())) {
					todaySportInfo.setHasData(parser.nextText());
				}

				if (TodaySportInfoGetter.TODAY_SPORT_TARGET.equals(parser.getName())) {
					todaySportInfo.setTodaySportTarget(parser.nextText());
				}

				if (TodaySportInfoGetter.USER_NAME.equals(parser.getName())) {
					todaySportInfo.setUserName(parser.nextText());
				}

				if (TodaySportInfoGetter.TODAY_SPORT_VALUE.equals(parser.getName())) {
					todaySportInfo.setStepCount(parser.getAttributeValue(null, TodaySportInfoGetter.STEPCOUNT));
					todaySportInfo.setDistance(parser.getAttributeValue(null, TodaySportInfoGetter.DISTANCE));
					todaySportInfo.setCalorie(parser.getAttributeValue(null, TodaySportInfoGetter.CALORIE));
					todaySportInfo.setAverageHeartRate(parser.getAttributeValue(null,
							TodaySportInfoGetter.AVERAGEHEARTRATE));
					todaySportInfo.setElapsedTime(parser.getAttributeValue(null, TodaySportInfoGetter.ELAPSEDTIME));
					todaySportInfo.setAverageSpeed(parser.getAttributeValue(null, TodaySportInfoGetter.AVERAGESPEED));
				}

				if (TodaySportInfoGetter.TODAY_SPORT_TIP.equals(parser.getName())) {
					todaySportInfo.setTipTitle(parser.getAttributeValue(null, TodaySportInfoGetter.TIP_TITLE));
					todaySportInfo.setTipSummary(parser.getAttributeValue(null, TodaySportInfoGetter.TIP_SUMMARY));
					todaySportInfo.setTipContent(parser.getAttributeValue(null, TodaySportInfoGetter.TIP_CONTENT));
				}

				break;

			case XmlPullParser.END_TAG:
				break;

			}
			type = parser.next();
		}
		is.close();
		return todaySportInfo;
	}

}
