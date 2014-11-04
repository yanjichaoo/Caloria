/* ==================================================
 * 产品名: 卡路里消耗管理
 * 文件名: UserXmlRegisterParser.java
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import android.content.Context;
import android.util.Xml;

import com.xikang.calorie.domain.UserInfo;
import com.xikang.calorie.xml.XmlConstants.UserRegister;

/**
 * 
 * 用户注册数据接口XML解析类
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
public class UserRegisterXmlParser extends BaseXmlParser {

	public UserRegisterXmlParser(Context context) {
		super(context);
	}

	/**
	 * 请求XML生成
	 * 
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public String buildXML(UserInfo userInfo) throws Exception {

		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream dataOutputStream = new DataOutputStream(arrayOutputStream);

		XmlSerializer serializer = Xml.newSerializer();
		serializer.setOutput(dataOutputStream, "utf-8");

		serializer.startDocument("utf-8", true);

		serializer.startTag(null, XmlConstants.PAGE);
		serializer.startTag(null, XmlConstants.COMMON);

		serializer.startTag(null, XmlConstants.FUNCTION);
		serializer.attribute(null, UserRegister.ID, UserRegister.FUNCTION_NAME);
		serializer.attribute(null, UserRegister.DEVICE_TYPE, DEVICE_TYPE);
		serializer.attribute(null, UserRegister.IPHONE_ID, iphone_id);
		serializer.attribute(null, UserRegister.EMAIL, userInfo.getEmail());
		serializer.attribute(null, UserRegister.PASSWORD, userInfo.getPwd());
		serializer.attribute(null, UserRegister.WATCH_ID, userInfo.getWatchId());
		serializer.attribute(null, UserRegister.PHONENUM, userInfo.getPhoneNum());
		serializer.attribute(null, UserRegister.USERNAME, userInfo.getUserName());
		serializer.attribute(null, UserRegister.SUMMARYTIMERENABLED, userInfo.getSummaryTimerEnabled());
		serializer.attribute(null, UserRegister.ARTICLETIMERENABLED, userInfo.getArticleTimerEnabled());
		serializer.attribute(null, UserRegister.TARGETALERTTIMERENABLED, userInfo.getTargetAlertTimerEnabled());
		serializer.attribute(null, UserRegister.VERSION, version);
		serializer.endTag(null, XmlConstants.FUNCTION);

		serializer.endTag(null, XmlConstants.COMMON);
		serializer.endTag(null, XmlConstants.PAGE);

		serializer.endDocument();
		dataOutputStream.close();

		return arrayOutputStream.toString();
	}

	/**
	 * 应答XML解析
	 * 
	 * @param writeBack
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> parseXml(String writeBack) throws Exception {
		byte[] bytes = writeBack.getBytes("UTF-8");
		ByteArrayInputStream is = new ByteArrayInputStream(bytes);
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(is, "utf-8");
		int type = parser.getEventType();
		int result = 0;
		Map<String, String> msgMap = null;
		while (type != XmlPullParser.END_DOCUMENT) {
			switch (type) {

			case XmlPullParser.START_DOCUMENT:
				msgMap = new HashMap<String, String>();
				break;

			case XmlPullParser.START_TAG:
				if (XmlConstants.RESULT.equals(parser.getName())) {
					String status = parser.getAttributeValue(null, XmlConstants.STATUS);
					msgMap.put(XmlConstants.STATUS, status);
					result = Integer.valueOf(status);
				}
				if (result == 0) {
					if (UserRegister.ERR_CODE.equals(parser.getName())) {
						msgMap.put(UserRegister.ERR_CODE, String.valueOf(parser.nextText()));
					}
					if (UserRegister.ERR_MSG.equals(parser.getName())) {
						msgMap.put(UserRegister.ERR_MSG, String.valueOf(parser.nextText()));
					}
				} else if (result == 1) {
					if (UserRegister.SUCCESS_CODE.equals(parser.getName())) {
						msgMap.put(UserRegister.SUCCESS_CODE, String.valueOf(parser.nextText()));
					}
					if (UserRegister.SUCCESS_MSG.equals(parser.getName())) {
						msgMap.put(UserRegister.SUCCESS_MSG, String.valueOf(parser.nextText()));
					}
					if (UserRegister.PERSON_ID.equals(parser.getName())) {
						msgMap.put(UserRegister.PERSON_ID, String.valueOf(parser.nextText()));
					}
				}
				break;

			case XmlPullParser.END_TAG:
				break;

			}
			type = parser.next();
		}
		is.close();
		return msgMap;
	}
}
