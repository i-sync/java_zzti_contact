package com.zzti.bean;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.util.Log;

import com.google.gson.Gson;

public class RestRequest {
	private final static String restUrl ="http://www.contacts09.tk/java_zzti_cloudrest/rest"; 
	
	public RestRequest() {
		// TODO Auto-generated constructor stub
	}
	
		
	/*
	 * post method 
	 */
	public static <T> T postT(String path,String acceptType ,Object obj,Type classType)
	{
		T t = null;
		try
		{	
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(restUrl+path);
			post.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			post.setHeader("Accept", acceptType);

			String data = new Gson().toJson(obj);		
			//Log.i(String.format("---%s---postBefore--->",path),data);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("data",data));
			post.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));//resolve chinese 
			
			HttpResponse response = client .execute(post);
			BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"));
			StringBuilder builder = new StringBuilder();
			for (String line = null; (line = reader.readLine()) != null;) {
			    builder.append(line).append("\n");
			}
			//Log.i(String.format("---%s---postResult--->",path),builder.toString());
			t = new Gson().fromJson(builder.toString(), classType);
		}
		catch(Exception ex)
		{
			Log.e(String.format("---%s---post error--->",path), ex.getMessage());
		}
		return t;
	}
	
	/*
	 * get method
	 */
	public static <T> T getT(String path,String acceptType,Type classType)
	{
		T t = null;
		try
		{
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(restUrl+ path);
			get.setHeader("Accept", acceptType);
			
			HttpResponse response = client .execute(get);
			BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
			StringBuilder builder = new StringBuilder();
			for (String line = null; (line = reader.readLine()) != null;) {
			    builder.append(line).append("\n");
			}

			//Log.i("---result--->",builder.toString());
			t = new Gson().fromJson(builder.toString(), classType);
		}
		catch(Exception ex)
		{
			Log.e("----get error----->", ex.getMessage());
		}
		return t;
	}
}
