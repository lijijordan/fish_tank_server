package com.fish.connection;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class MyHttpGet {

	private static final Logger log = Logger.getLogger(MyHttpGet.class);
	private ConnectionCallBack callback;
	private HttpGet httpGet;
	private HttpClientContext context;
	private CloseableHttpClient client ;
	
	public MyHttpGet(String url, ConnectionCallBack callback, Map<String, String> headMap) {
		log.info("HTTP Get URL : " + url);
		this.callback = callback;
		this.httpGet = new HttpGet(url);
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(ConnectionConstant.SOCKET_TIMEOUT).
				setConnectTimeout(ConnectionConstant.CONNECT_TIMEOUT).build();
		httpGet.setConfig(requestConfig);
		Set<String> set = headMap.keySet();
		for (String key : set) {
			httpGet.setHeader(key, headMap.get(key));
		}

		this.context = HttpClientContext.create();
		this.client = (ConnectionFactory.getHttpClientNInstance(false));
	
		// debug
		if(this.context != null && this.context.getCookieStore() != null){
			List<Cookie> list = this.context.getCookieStore().getCookies();
			for (Cookie cookie : list) {
				log.info("Request Cookie:");
				log.info("name=" + cookie.getName() + ",value=" + cookie.getValue() + ",domain=" + cookie.getDomain());
			}
		}
	}
	
	
	public int connection(){
		CloseableHttpResponse response = null;
		int statusCode = 0;
		try {
			response = this.client.execute(httpGet, context);
			if(response != null && response.getStatusLine() != null){
				statusCode = response.getStatusLine().getStatusCode();
			}
			System.out.println("states code : " + response.getStatusLine().getStatusCode());
			if(response.getEntity() != null){
				HttpEntity entity = response.getEntity();
				String entityBody = null;
				try {
					byte[] bytes = EntityUtils.toByteArray(entity);
					entityBody = new String(bytes, Consts.UTF_8); // utf-8
					// entityBody = EntityUtils.toString(entity,
					// this.charset);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Response Body : " + entityBody);
				callback.callback(entityBody);
			}
			callback.callback(response);
//				log.info("http get return : " + sb.toString());
//			System.out.println("Parse String Cost Time: " + (System.currentTimeMillis() - start1));
		} catch (ClientProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(response != null)
					response.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// recall
			/*if(isReConnection && !(statusCode == 200 || statusCode == 302)){
				this.connection();
			}*/
		}
		return statusCode;
	}
}
