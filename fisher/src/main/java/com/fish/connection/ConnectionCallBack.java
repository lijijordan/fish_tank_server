package com.fish.connection;
import org.apache.http.client.methods.CloseableHttpResponse;

public interface ConnectionCallBack {

	void callback(String str);
	
	void callback(CloseableHttpResponse response);
	
}