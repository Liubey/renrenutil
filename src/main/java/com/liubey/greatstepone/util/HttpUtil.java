package com.liubey.greatstepone.util;

import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;

public class HttpUtil {
	
	public static DefaultHttpClient getHttpClient() {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpClientParams.setCookiePolicy(httpClient.getParams(),CookiePolicy.BROWSER_COMPATIBILITY);
		//httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,10000);
		//httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,0);
		return httpClient;
	}
}
