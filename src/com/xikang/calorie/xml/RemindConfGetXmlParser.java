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
import android.util.Xml;

import com.xikang.calorie.domain.Remind;
import com.xikang.calorie.xml.XmlConstants.RemindConfGet;

public class RemindConfGetXmlParser extends BaseXmlParser {

	public RemindConfGetXmlParser(Context context) {
		super(context);
	}

	public String buildXML(String persionId) throws Exception {

		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream dataOutputStream = new DataOutputStream(arrayOutputStream);

		XmlSerializer serializer = Xml.newSerializer();
		serializer.setOutput(dataOutputStream, "utf-8");

		serializer.startDocument("utf-8", true);

		serializer.startTag(null, XmlConstants.PAGE);
		serializer.startTag(null, XmlConstants.COMMON);
		serializer.startTag(null, XmlConstants.FUNCTION);

		serializer.attribute(null, XmlConstants.ID, RemindConfGet.FUNCTION_NAME);
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

	public Map<String, String> parseInfoXml(String writeBack) throws Exception {
		byte[] bytes = writeBack.getBytes("UTF-8");
		ByteArrayInputStream is = new ByteArrayInputStream(bytes);
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(is, "utf-8");
		int type = parser.getEventType();
		int status = 0;
		Map<String, String> msgMap = null;

		while (type != XmlPullParser.END_DOCUMENT) {
			switch (type) {

			case XmlPullParser.START_DOCUMENT:
				msgMap = new HashMap<String, String>();
				break;

			case XmlPullParser.START_TAG:
				if (XmlConstants.RESULT.equals(parser.getName())) {
					status = Integer.valueOf(parser.getAttributeValue(null, XmlConstants.STATUS));
					msgMap.put(XmlConstants.STATUS, String.valueOf(status));
				}
				if (status == 1) {
					if (RemindConfGet.SPORTTARGET.equals(parser.getName())) {
						msgMap.put(RemindConfGet.SPORTTARGET, String.valueOf(parser.nextText()));
					}
					if (RemindConfGet.WEIGHT.equals(parser.getName())) {
						msgMap.put(RemindConfGet.WEIGHT, String.valueOf(parser.nextText()));
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

	public List<Remind> parseConfXml(String writeBack) throws Exception {
		byte[] bytes = writeBack.getBytes("UTF-8");
		ByteArrayInputStream is = new ByteArrayInputStream(bytes);
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(is, "utf-8");
		int type = parser.getEventType();
		int status = 0;

		List<Remind> remindList = null;

		while (type != XmlPullParser.END_DOCUMENT) {
			switch (type) {

			case XmlPullParser.START_DOCUMENT:
				Remind mRemind;
				remindList = new ArrayList<Remind>();
				break;

			case XmlPullParser.START_TAG:
				if (XmlConstants.RESULT.equals(parser.getName())) {
					status = Integer.valueOf(parser.getAttributeValue(null, XmlConstants.STATUS));
				}
				if (status == 1) {

					if (parser.getName().contains(RemindConfGet.SPORTPERIOD)) {
						mRemind = new Remind();
						mRemind.setTimeType(parser.getAttributeValue(null, RemindConfGet.TIMETYPE));
						mRemind.setTargetRate(parser.getAttributeValue(null, RemindConfGet.TARGETRATE));
						mRemind.setRemindingEnabled(parser.getAttributeValue(null, RemindConfGet.REMINDINGENABLED));
						mRemind.setRemindingTime(parser.getAttributeValue(null, RemindConfGet.REMINDINGTIME));
						mRemind.setRingType(parser.getAttributeValue(null, RemindConfGet.RINGTYPE));
						remindList.add(mRemind);
					}
				}
				break;

			case XmlPullParser.END_TAG:
				break;

			}
			type = parser.next();
		}
		is.close();

		return remindList;
	}
}
