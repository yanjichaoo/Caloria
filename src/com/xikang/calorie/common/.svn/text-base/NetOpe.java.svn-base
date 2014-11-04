/* ==================================================
 * 产品名: 卡路里消耗管理
 * 文件名: NetOpe.java
 * --------------------------------------------------
 * 开发环境: JDK1.6
 * --------------------------------------------------
 * 修订履历  YYYY/MM/DD  REV.  备注
 *         2011/06/13  1.00  初版发行
 * --------------------------------------------------
 * (C) Copyright XIKANG CORPORATION 2011 All Rights Reserved.
 */
package com.xikang.calorie.common;

import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;


/**
 * 
 * 网络编程类
 * 
 * <pre>
 * 
 * 2011/06/14, 曲磊
 * 
 * 修订履历：YYYY/MM/DD 修订者  修订内容
 * 
 * </pre>
 * 
 * @author 曲磊
 * @version 1.00
 */
public class NetOpe {

	/**
	 * 创建HTTP连接
	 * 
	 * @param params
	 * @return
	 */
	public static HttpClient getNewHttpClient(HttpParams params, int httpPort, int httpsProt) throws Exception {
		KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
		trustStore.load(null, null);
		SSLSocketFactory sf = new CustomSSL(trustStore);
		sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		SchemeRegistry registry = new SchemeRegistry();
		registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), httpPort));
		registry.register(new Scheme("https", sf, httpsProt));
		ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);
		DefaultHttpClient httpClient = new DefaultHttpClient(ccm, params);
//		setProxy(httpClient);
		return httpClient;
	}
	

	public static String requestGet(String linkAddress, int timeOut) throws Exception {
		
		String strResult = null;
		HttpGet httpRequest = new HttpGet(linkAddress);
		HttpParams params = new BasicHttpParams();
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
		HttpConnectionParams.setConnectionTimeout(params, timeOut * 1000);
		HttpConnectionParams.setSoTimeout(params, 60 * 1000);
		HttpConnectionParams.setSocketBufferSize(params, 8192);
		httpRequest.setParams(params);
		DefaultHttpClient httpClient = new DefaultHttpClient();
//		setProxy(httpClient);
		HttpResponse httpResponse = httpClient.execute(httpRequest);
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			strResult = EntityUtils.toString(httpResponse.getEntity(), HTTP.UTF_8);
		}
		httpClient.getConnectionManager().shutdown();
		return strResult;
	}
	

	/**
	 * 网络连接
	 * 
	 * @param linkAddress 网络地址
	 * @param map 动态参数
	 * @param timeOut 超时时间
	 * @return
	 */
	public static String requestPost(String linkAddress, Map<String, String> map, int timeOut, int httpPort, int httpsProt)
			throws Exception {
		String result = null;
		HttpParams params = new BasicHttpParams();
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
		HttpConnectionParams.setConnectionTimeout(params, timeOut * 1000);
		HttpConnectionParams.setSoTimeout(params, 60 * 1000);
		HttpConnectionParams.setSocketBufferSize(params, 8192);
		HttpClient httpClient = getNewHttpClient(params, httpPort, httpsProt);
		httpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
		HttpPost httpost = new HttpPost(linkAddress);
		if (map != null) {
			List<BasicNameValuePair> postData = new ArrayList<BasicNameValuePair>();
			for (Map.Entry<String, String> entry : map.entrySet()) {
				postData.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			httpost.setEntity(new UrlEncodedFormEntity(postData, HTTP.UTF_8));
		}

		HttpResponse response = httpClient.execute(httpost); // 执行
		String httpStatus = response.getStatusLine().toString();

		if (httpStatus.indexOf("4") > 0 || httpStatus.indexOf("5") > 0) {
			result = "error"; // 如果响应码为4开头,则代表访问不成功
		}
		HttpEntity httpEntity = response.getEntity();
		if (httpEntity != null) {
			String backStream = EntityUtils.toString(httpEntity, HTTP.UTF_8); // 回写内容
			result = backStream.toString();

			httpEntity.consumeContent();
		}
		httpClient.getConnectionManager().shutdown();
		return result;
	}

	public static String requestPost(String linkAddress, Map<String, String> map, int timeOut) throws Exception {
		return requestPost(linkAddress, map, timeOut, 80, 443);
	}

	public static void setProxy(DefaultHttpClient httpClient) {
		HttpHost proxy = new HttpHost("192.168.1.130", 8080);
		httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
		httpClient.getCredentialsProvider().setCredentials(AuthScope.ANY,
				new UsernamePasswordCredentials("qu.l", "QAZ!qaz841026"));
	}

}
