/* ==================================================
 * 产品名: 卡路里消耗管理
 * 文件名: SportRankXmlGetter.java
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
import android.util.Xml;

import com.xikang.calorie.domain.RankVO;
import com.xikang.calorie.xml.XmlConstants.RankingInfo;

/**
 * 
 * 排行榜数据接口XML解析类
 * 
 * 
 * <pre>
 * 
 * 2011/06/15, 代俊义
 * 
 * 修订履历：YYYY/MM/DD 修订者  修订内容
 * 2011/08/08  曲磊 修改参数
 *
 * 
 * </pre>
 * 
 * @author 代俊义
 * @version 1.00
 */
public class SportRankXmlParser extends BaseXmlParser{
	

	public SportRankXmlParser(Context context) {
		super(context);
	}

	public String buildXML(String persionId, String rankCount) throws Exception {

		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream dataOutputStream = new DataOutputStream(arrayOutputStream);

		XmlSerializer serializer = Xml.newSerializer();
		serializer.setOutput(dataOutputStream, "utf-8");

		serializer.startDocument("utf-8", true);

		serializer.startTag(null, XmlConstants.PAGE);
		serializer.startTag(null, XmlConstants.COMMON);
		serializer.startTag(null, XmlConstants.FUNCTION);

		serializer.attribute(null, XmlConstants.ID, "GetSportRank_iPhone");
		serializer.attribute(null, XmlConstants.DEVICE_TYPE, DEVICE_TYPE);
		serializer.attribute(null, XmlConstants.PERSON_ID, persionId);
		serializer.attribute(null, XmlConstants.IPHONE_ID, iphone_id);
		serializer.attribute(null, RankingInfo.RANK_COUNT, rankCount);
		serializer.attribute(null, XmlConstants.VERSION,  version);

		serializer.endTag(null, XmlConstants.FUNCTION);
		serializer.endTag(null, XmlConstants.COMMON);
		serializer.endTag(null, XmlConstants.PAGE);

		serializer.endDocument();
		dataOutputStream.close();

		return arrayOutputStream.toString();
	}

	public List<RankVO> parseXml(String writeBack) throws Exception {
		List<RankVO> list = new ArrayList<RankVO>();
		byte[] bytes = writeBack.getBytes("UTF-8");
		ByteArrayInputStream is = new ByteArrayInputStream(bytes);
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(is, "utf-8");
		int type = parser.getEventType();
		RankVO vo = null;
		List<Map<String, String>> rank;
		while (type != XmlPullParser.END_DOCUMENT) {
			switch (type) {
			case XmlPullParser.START_DOCUMENT:
				break;
			case XmlPullParser.START_TAG:
				if (XmlConstants.RESULT.equals(parser.getName())) {
					if ("0".equals(parser.getAttributeValue(null, XmlConstants.STATUS))) {
						return list;
					}
				}
				// 三种类型的排行榜，使用三个vo保存信息
				if (RankingInfo.SPORTRANK.equals(parser.getName()) || RankingInfo.CITYRANK.equals(parser.getName())
						|| RankingInfo.GROUPRANK.equals(parser.getName())) {
					vo = new RankVO();
					vo.setBeatPresent(parser.getAttributeValue(null, RankingInfo.BEATPERCENT));
					vo.setCurrentRank(parser.getAttributeValue(null, RankingInfo.CURRENT));
					if(parser.getAttributeValue(null, RankingInfo.CITY_CODE)!=null){
						vo.setCityCode(parser.getAttributeValue(null, RankingInfo.CITY_CODE));
					}
					if(parser.getAttributeValue(null, RankingInfo.BIRTHDAY)!=null){
						vo.setBrithday(parser.getAttributeValue(null, RankingInfo.BIRTHDAY));
					}
					
				}
				if (RankingInfo.SPORTRANK.equals(parser.getName())) {
					vo.setRankType(0);
				}
				if (RankingInfo.CITYRANK.equals(parser.getName())) {
					vo.setRankType(1);
				}
				if (RankingInfo.GROUPRANK.equals(parser.getName())) {
					vo.setRankType(2);
				}
				if (parser.getName() != null) {
					if (parser.getName().contains(RankingInfo.SPORTRANK + "_")
							|| parser.getName().contains(RankingInfo.GROUPRANK + "_")
							|| parser.getName().contains(RankingInfo.CITYRANK + "_")) {
						Map<String, String> chart = new HashMap<String, String>();

						String temp = parser.getAttributeValue(null, RankingInfo.RANK_PERSON_ID);
						chart.put(RankingInfo.RANK_PERSON_ID, temp);
						temp = parser.getAttributeValue(null, RankingInfo.RANK_USERNAME);
						chart.put(RankingInfo.RANK_USERNAME, temp);
						temp = parser.getAttributeValue(null, RankingInfo.RANK_VALUE);
						chart.put(RankingInfo.RANK_VALUE, parser.getAttributeValue(null, RankingInfo.RANK_VALUE));
						temp = parser.getAttributeValue(null, RankingInfo.RANK_CALORIE);
						chart.put(RankingInfo.RANK_CALORIE, parser.getAttributeValue(null, RankingInfo.RANK_CALORIE));
						rank = vo.getRank();
						rank.add(chart);
						vo.setRank(rank);
					}
				}
				break;
			case XmlPullParser.END_TAG:
				// 三种类型的排行榜，使用三个vo保存信息
				if (RankingInfo.SPORTRANK.equals(parser.getName()) || RankingInfo.CITYRANK.equals(parser.getName())
						|| RankingInfo.GROUPRANK.equals(parser.getName())) {
					list.add(vo);
				}
				break;
			}
			type = parser.next();
		}
		is.close();
		return list;
	}
}
