package com.xikang.calorie.xml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import android.content.Context;
import android.util.Xml;

import com.xikang.calorie.domain.Actions;
import com.xikang.calorie.xml.XmlConstants.ActionsGetter;

public class ActionsGetDataXmlParser extends BaseXmlParser {

	public ActionsGetDataXmlParser(Context context) {
		super(context);
	}

	public String buildHotActionsXML(String persionId, String cityCode) throws Exception {
		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream dataOutputStream = new DataOutputStream(arrayOutputStream);

		XmlSerializer serializer = Xml.newSerializer();
		serializer.setOutput(dataOutputStream, "utf-8");
		serializer.startDocument("utf-8", true);
		serializer.startTag(null, XmlConstants.PAGE);
		serializer.startTag(null, XmlConstants.COMMON);
		serializer.startTag(null, XmlConstants.FUNCTION);
		serializer.attribute(null, XmlConstants.ID, ActionsGetter.FUNCTION_NAME_HOT_ACTIONS);
		serializer.attribute(null, XmlConstants.DEVICE_TYPE, DEVICE_TYPE);
		serializer.attribute(null, XmlConstants.PERSON_ID, persionId);
		serializer.attribute(null, XmlConstants.IPHONE_ID, iphone_id);
		serializer.attribute(null, ActionsGetter.CITY_CODE, cityCode);
		serializer.attribute(null, XmlConstants.VERSION, version);
		serializer.endTag(null, XmlConstants.FUNCTION);
		serializer.endTag(null, XmlConstants.COMMON);
		serializer.endTag(null, XmlConstants.PAGE);
		serializer.endDocument();
		dataOutputStream.close();
		return arrayOutputStream.toString();
	}

	public String buildMyActionsXML(String persionId) throws Exception {
		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream dataOutputStream = new DataOutputStream(arrayOutputStream);

		XmlSerializer serializer = Xml.newSerializer();
		serializer.setOutput(dataOutputStream, "utf-8");
		serializer.startDocument("utf-8", true);
		serializer.startTag(null, XmlConstants.PAGE);
		serializer.startTag(null, XmlConstants.COMMON);
		serializer.startTag(null, XmlConstants.FUNCTION);
		serializer.attribute(null, XmlConstants.ID, ActionsGetter.FUNCTION_NAME_MY_ACTIONS);
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

	public List<Actions> parseXml(String writeBack) throws Exception {
		byte[] bytes = writeBack.getBytes("UTF-8");
		ByteArrayInputStream is = new ByteArrayInputStream(bytes);
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(is, "utf-8");
		int type = parser.getEventType();

		int status = 0;
		List<Actions> actionsList = null;
		Actions mActions = null;

		while (type != XmlPullParser.END_DOCUMENT) {
			switch (type) {
			case XmlPullParser.START_DOCUMENT:

				break;

			case XmlPullParser.START_TAG:
				if (XmlConstants.RESULT.equals(parser.getName())) {
					status = Integer.valueOf(parser.getAttributeValue(null, XmlConstants.STATUS));
				}
				if (status == 1) {
					if (ActionsGetter.ACTIONS.equals(parser.getName())) {
						actionsList = new ArrayList<Actions>();
					}

					if (ActionsGetter.ACTIONS_ITEM.equals(parser.getName())) {
						mActions = new Actions();
					}

					if (ActionsGetter.ID.equals(parser.getName())) {
						mActions.setId(parser.nextText());
					}

					if (ActionsGetter.NAME.equals(parser.getName())) {
						mActions.setName(parser.nextText());
					}
					if (ActionsGetter.ADDRESS.equals(parser.getName())) {
						mActions.setAddress(parser.nextText());
					}

					if (ActionsGetter.START_TIME.equals(parser.getName())) {
						mActions.setStart_time(parser.nextText());
					}

					if (ActionsGetter.END_TIME.equals(parser.getName())) {
						mActions.setEnd_time(parser.nextText());
					}

					if (ActionsGetter.NUMBER.equals(parser.getName())) {
						mActions.setNumber(parser.nextText());
					}
				}
				break;

			case XmlPullParser.END_TAG:
				if (ActionsGetter.ACTIONS_ITEM.equals(parser.getName())) {
					actionsList.add(mActions);
				}
				break;

			}
			type = parser.next();
		}
		is.close();
		return actionsList;
	}
}
