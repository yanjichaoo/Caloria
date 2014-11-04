/* ==================================================
 * 产品名: 卡路里消耗管理
 * 文件名: OAuthConstant.java
 * --------------------------------------------------
 * 开发环境: JDK1.6
 * --------------------------------------------------
 * 修订履历  YYYY/MM/DD  REV.  备注
 *         2011/06/23  1.00  初版发行
 * --------------------------------------------------
 * (C) Copyright XIKANG CORPORATION 2011 All Rights Reserved.
 */
package com.xikang.calorie.common;

import weibo4android.Weibo;
import weibo4android.http.AccessToken;
import weibo4android.http.RequestToken;

public class OAuthConstant {
	private static Weibo weibo = null;
	private static OAuthConstant instance = null;
	private RequestToken requestToken;
	private AccessToken accessToken;
	private String token;
	private String tokenSecret;

	private OAuthConstant() {
	};

	public static synchronized OAuthConstant getInstance() {
		if (instance == null)
			instance = new OAuthConstant();
		return instance;
	}

	public Weibo getWeibo() {
		if (weibo == null)
			weibo = new Weibo();
		return weibo;
	}

	public AccessToken getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(AccessToken accessToken) {
		this.accessToken = accessToken;
		this.token = accessToken.getToken();
		this.tokenSecret = accessToken.getTokenSecret();
	}

	public RequestToken getRequestToken() {
		return requestToken;
	}

	public void setRequestToken(RequestToken requestToken) {
		this.requestToken = requestToken;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTokenSecret() {
		return tokenSecret;
	}

	public void setTokenSecret(String tokenSecret) {
		this.tokenSecret = tokenSecret;
	}

}
