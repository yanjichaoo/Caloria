package com.xikang.calorie.xml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import android.content.Context;
import android.util.Xml;

import com.xikang.calorie.domain.Remind;

public class RemindConfSetXmlParser extends BaseXmlParser {

	public RemindConfSetXmlParser(Context context) {
		super(context);
	}

	/**
	 * 请求XML生成
	 * 
	 * @param personId
	 * @param remind
	 * @return
	 * @throws Exception
	 */
	public String buildXML(String personId, List<Remind> list) throws Exception {
		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream dataOutputStream = new DataOutputStream(arrayOutputStream);

		XmlSerializer serializer = Xml.newSerializer();
		serializer.setOutput(dataOutputStream, "utf-8");
		serializer.startDocument("utf-8", true);

		serializer.startTag(null, XmlConstants.PAGE);
		serializer.startTag(null, XmlConstants.COMMON);

		serializer.startTag(null, XmlConstants.FUNCTION);
		serializer.attribute(null, XmlConstants.RemindConfSet.ID, "SetRemindingConfigInfo_iPhone1_3");
		serializer.attribute(null, XmlConstants.RemindConfSet.DEVICETYPE, DEVICE_TYPE);
		serializer.attribute(null, XmlConstants.RemindConfSet.PERSON_ID, personId);
		serializer.attribute(null, XmlConstants.RemindConfSet.IPHONE_ID, iphone_id);
		serializer.attribute(null, XmlConstants.VERSION, version);
		serializer.endTag(null, XmlConstants.FUNCTION);
		serializer.startTag(null, XmlConstants.RemindConfSet.SPORTDISTRIBUTING);
		for (Remind remind : list) {
			if (!remind.getTargetRate().equals("0") && !remind.getTargetRate().equals("0.0")
					&& !remind.getTargetRate().equals("0.00")) {
				serializer.startTag(null, XmlConstants.RemindConfSet.SPORTPERIOD);
				serializer.attribute(null, XmlConstants.RemindConfSet.TIMETYPE, remind.getTimeType());
				serializer.attribute(null, XmlConstants.RemindConfSet.TARGETRATE, remind.getTargetRate());
				serializer.attribute(null, XmlConstants.RemindConfSet.REMINDINGENABLED, remind.getRemindingEnabled());
				serializer.attribute(null, XmlConstants.RemindConfSet.REMINDINGTIME, remind.getRemindingTime());
				serializer.endTag(null, XmlConstants.RemindConfSet.SPORTPERIOD);
			}
		}
		serializer.endTag(null, XmlConstants.RemindConfSet.SPORTDISTRIBUTING);
		serializer.endTag(null, XmlConstants.COMMON);
		serializer.endTag(null, XmlConstants.PAGE);
		serializer.endDocument();
		dataOutputStream.close();
		return arrayOutputStream.toString();

	}

	public String parseXml(String writeBack) throws Exception {

		String status = null;
		byte[] bytes = writeBack.getBytes("UTF-8");
		ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(bytes);
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(arrayInputStream, "utf-8");

		int type = parser.getEventType();
		while (type != XmlPullParser.END_DOCUMENT) {
			switch (type) {
			case XmlPullParser.START_TAG:
				if (XmlConstants.RemindConfSet.RESULT.equals(parser.getName())) {
					status = parser.getAttributeValue(null, XmlConstants.RemindConfSet.STATUS);
				}
				break;
			}
			type = parser.next();
		}
		arrayInputStream.close();
		return status;
	}

}
