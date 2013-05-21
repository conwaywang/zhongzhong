package com.fumuquan.util;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import com.fumuquan.exception.ServerResponseException;


public class HttpConnect {

	private static HttpConnect httpConnect = null;

	private HttpClient httpClient;
	private ClientConnectionManager mConnManager;

	private HttpConnect() {
		httpClient = new DefaultHttpClient();
		HttpParams params = httpClient.getParams();
		params.setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 1000 * 20);
		params.setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 1000 * 10);
		mConnManager = httpClient.getConnectionManager();
		httpClient = new DefaultHttpClient(new ThreadSafeClientConnManager(params,mConnManager.getSchemeRegistry()), params);
	}

	public static HttpConnect getInstance() {
		if (httpConnect == null) {
			httpConnect = new HttpConnect();
		}
		return httpConnect;
	}

	public InputStream get(String url) throws ClientProtocolException, IOException, ServerResponseException {
		InputStream in = null;

		HttpGet request = new HttpGet(url);
		HttpResponse response;
		response = httpClient.execute(request);
		if (response.getStatusLine().getStatusCode() == 200)
			in = response.getEntity().getContent();
		else{	
			in = response.getEntity().getContent();
			if(null != in){
				in.close();
			}
			
			throw new ServerResponseException("服务器响应非200");
		}
			

		return in;
	}

	public InputStream post(String uri, List<NameValuePair> content) throws ClientProtocolException, IOException, ServerResponseException {
		InputStream in = null;
		HttpPost request = new HttpPost(uri);
		request.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded");
		request.setEntity(new UrlEncodedFormEntity(content, HTTP.UTF_8));
		HttpResponse response;
		response = httpClient.execute(request);
		if (response.getStatusLine().getStatusCode() == 200)
			in = response.getEntity().getContent();
		else{
			in = response.getEntity().getContent();
			if(null != in){
				in.close();
			}
			throw new ServerResponseException("服务器响应非200");
		}
		return in;
	}

	public HttpEntity getFile(String url) throws ClientProtocolException, IOException, ServerResponseException {
		HttpEntity httpEntity = null;
		HttpGet request = new HttpGet(url);
		HttpResponse response;
		response = httpClient.execute(request);
		if (response.getStatusLine().getStatusCode() == 200)
			httpEntity = response.getEntity();
		else{
			response.getEntity();
			throw new ServerResponseException("服务器响应非200");
		}
		return httpEntity;
	}
}
