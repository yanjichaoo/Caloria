/* ==================================================
 * 产品名: 卡路里消耗管理
 * 文件名: UserProfileSetXmlParser.java
 * --------------------------------------------------
 * 开发环境: JDK1.6
 * --------------------------------------------------
 * 修订履历  2011/06/13  REV.  备注
 *         2011/06/13  1.00  初版发行
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

import com.xikang.calorie.domain.UserInfo;
import com.xikang.calorie.xml.XmlConstants.UserProfileSetter;

/**
 * 
 * 设置账户信息数据接口XML解析类
 * 
 * 
 * <pre>
 * 
 * 2011/06/13, 曲磊
 * 
 * 修订履历：YYYY/MM/DD 修订者  修订内容
 * 
 * </pre>
 * 
 * @author 曲磊
 * @version 1.00
 */
public class UserProfileSetXmlParser extends BaseXmlParser{
	
	
	public UserProfileSetXmlParser(Context context) {
		super(context);
	}

	public String buildXML(UserInfo userInfo) throws Exception {

		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream dataOutputStream = new DataOutputStream(arrayOutputStream);

		XmlSerializer serializer = Xml.newSerializer();
		serializer.setOutput(dataOutputStream, "utf-8");

		serializer.startDocument("utf-8", true);

		serializer.startTag(null, XmlConstants.PAGE);
		serializer.startTag(null, XmlConstants.COMMON);

		serializer.startTag(null, XmlConstants.FUNCTION);
		serializer.attribute(XmlConstants.FUNCTION, UserProfileSetter.ID, UserProfileSetter.FUNCTION_NAME);
		serializer.attribute(XmlConstants.FUNCTION, UserProfileSetter.IPHONE_ID, iphone_id);
		serializer.attribute(XmlConstants.FUNCTION, UserProfileSetter.PERSON_ID, userInfo.getPersonId());
		serializer.attribute(XmlConstants.FUNCTION, UserProfileSetter.WATCH_ID, userInfo.getWatchId());
		serializer.attribute(XmlConstants.FUNCTION, UserProfileSetter.PHONENUM, userInfo.getPhoneNum());
		serializer.attribute(XmlConstants.FUNCTION, UserProfileSetter.USERNAME, userInfo.getUserName());
		serializer.attribute(XmlConstants.FUNCTION, UserProfileSetter.SUMMARYTIMERENABLED, userInfo.getSummaryTimerEnabled());
		serializer.attribute(XmlConstants.FUNCTION, UserProfileSetter.ARTICLETIMERENABLED, userInfo.getArticleTimerEnabled());
		serializer.attribute(XmlConstants.FUNCTION, UserProfileSetter.TARGETALERTTIMERENABLED, userInfo.getTargetAlertTimerEnabled());
		serializer.attribute(XmlConstants.FUNCTION, UserProfileSetter.VERSION, version);
		serializer.endTag(null, XmlConstants.FUNCTION);

		serializer.endTag(null, XmlConstants.COMMON);
		serializer.endTag(null, XmlConstants.PAGE);

		serializer.endDocument();
		dataOutputStream.close();

		return arrayOutputStream.toString();
	}

	public boolean parseXml(String writeBack) throws Exception {
		byte[] bytes = writeBack.getBytes("UTF-8");
		ByteArrayInputStream is = new ByteArrayInputStream(bytes);
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(is, "utf-8");
		int type = parser.getEventType();
		boolean result = false;
		int status = 0;
		while (type != XmlPullParser.END_DOCUMENT) {
			switch (type) {

			case XmlPullParser.START_DOCUMENT:

				break;

			case XmlPullParser.START_TAG:
				if (XmlConstants.RESULT.equals(parser.getName())) {
					//状态
					status = Integer.valueOf(parser.getAttributeValue(null, XmlConstants.STATUS));
				}
				if(status == 1){
					result = true;
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

}
