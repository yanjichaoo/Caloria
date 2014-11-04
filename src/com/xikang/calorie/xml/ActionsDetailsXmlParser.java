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

import com.xikang.calorie.domain.ActionsDetails;
import com.xikang.calorie.domain.ActionsDetails.Member;
import com.xikang.calorie.xml.XmlConstants.ActionsDetailsGetter;

public class ActionsDetailsXmlParser extends BaseXmlParser {

	public ActionsDetailsXmlParser(Context context) {
		super(context);
	}

	public String buildActionsDetailsXML(String persionId, String routeIdx) throws Exception {
		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream dataOutputStream = new DataOutputStream(arrayOutputStream);

		XmlSerializer serializer = Xml.newSerializer();
		serializer.setOutput(dataOutputStream, "utf-8");
		serializer.startDocument("utf-8", true);
		serializer.startTag(null, XmlConstants.PAGE);
		serializer.startTag(null, XmlConstants.COMMON);
		serializer.startTag(null, XmlConstants.FUNCTION);

		serializer.attribute(null, XmlConstants.ID, ActionsDetailsGetter.FUNCTION_NAME);
		serializer.attribute(null, XmlConstants.DEVICE_TYPE, DEVICE_TYPE);
		serializer.attribute(null, XmlConstants.PERSON_ID, persionId);
		serializer.attribute(null, ActionsDetailsGetter.ROUTEIDX, routeIdx);
		serializer.attribute(null, XmlConstants.IPHONE_ID, iphone_id);
		serializer.attribute(null, XmlConstants.VERSION, version);

		serializer.endTag(null, XmlConstants.FUNCTION);
		serializer.endTag(null, XmlConstants.COMMON);
		serializer.endTag(null, XmlConstants.PAGE);
		serializer.endDocument();
		dataOutputStream.close();
		return arrayOutputStream.toString();
	}

	public ActionsDetails parseXml(String writeBack) throws Exception {
		byte[] bytes = writeBack.getBytes("UTF-8");
		ByteArrayInputStream is = new ByteArrayInputStream(bytes);
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(is, "utf-8");
		int type = parser.getEventType();

		int status = 0;

		ActionsDetails mActions = null;
		List<Member> memberList = null;
		Member mMember = null;
		
		while (type != XmlPullParser.END_DOCUMENT) {
			switch (type) {
			case XmlPullParser.START_DOCUMENT:
				mActions = new ActionsDetails();
				break;

			case XmlPullParser.START_TAG:
				if (XmlConstants.RESULT.equals(parser.getName())) {
					status = Integer.valueOf(parser.getAttributeValue(null, XmlConstants.STATUS));
				}
				if (status == 1) {
					if(ActionsDetailsGetter.JOINSTATUS.equals(parser.getName())){
						mActions.setJoinstatus(parser.nextText());
					}
					if(ActionsDetailsGetter.RANK.equals(parser.getName())){
						mActions.setRank(parser.nextText());
					}
					
					if (ActionsDetailsGetter.MEMBERS.equals(parser.getName())) {
						memberList = new ArrayList<Member>();
					}

					if (ActionsDetailsGetter.MEMBERS_ITEM.equals(parser.getName())) {
						mMember = mActions.new Member();
					}
					
					if (ActionsDetailsGetter.NAME.equals(parser.getName())) {
						mMember.setName(parser.nextText());
					}

					if (ActionsDetailsGetter.VALUE.equals(parser.getName())) {
						mMember.setValue(parser.nextText());
					}

				}
				break;

			case XmlPullParser.END_TAG:
				if (ActionsDetailsGetter.MEMBERS_ITEM.equals(parser.getName())) {
					memberList.add(mMember);
				}
				
				if (ActionsDetailsGetter.MEMBERS.equals(parser.getName())) {
					mActions.setMemberList(memberList);
				}
				break;

			}
			type = parser.next();
		}
		is.close();
		return mActions;
	}

}
