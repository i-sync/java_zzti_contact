package com.zzti.bean;

import org.ksoap2.serialization.SoapObject;

import android.util.Log;

import com.zzti.utils.SoapObjectUtils;
import com.zzti.utils.WebServiceObject;

public class Common {
	private final String SERVICE_URL = "http://192.168.2.103:8080/java_zzti_clouddb/Service";
//	 private final String SERVICE_URL =
//	 "http://192.168.173.1:8080/java_zzti_clouddb/Service";
	private final String SERVICE_NS = "http://service.zzti.com/";

	private volatile static Common instance;

	private Common() {
	}

	/**
	 * 获取实例
	 * 
	 * @return
	 */
	public static Common getInstance() {
		if (instance == null) {
			synchronized (Common.class) {
				if (instance == null) {
					instance = new Common();
				}
			}
		}
		return instance;
	}

	/**
	 * 获取班级列表
	 * 
	 * @return
	 */
	public ListResult<Class> class_getlist() {
		// 方法名
		String methodName = "android_class_getlist";
		WebServiceObject.Builder builder = new WebServiceObject.Builder(
				SERVICE_URL, SERVICE_NS, methodName);

		try {
			SoapObject resultSoapObject = builder.get().call();
			String string = resultSoapObject.getProperty(0).toString();
			Log.i(methodName, string);
			return (ListResult<Class>) SoapObjectUtils.StringToObject(string);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取联系人列表
	 * 
	 * @param data
	 * @return
	 */
	public ListResult<Contact> contact_getlist(Contact data) {
		String methodName = "android_contact_getlist";
		WebServiceObject.Builder builder = new WebServiceObject.Builder(
				SERVICE_URL, SERVICE_NS, methodName);

		try {
			String string = SoapObjectUtils.ObjectToSting(data);
			SoapObject resultSoapObject = builder.setStr("string", string)
					.get().call();
			string = resultSoapObject.getProperty(0).toString();
			Log.i(methodName, string);
			return (ListResult<Contact>) SoapObjectUtils.StringToObject(string);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 根据ID获取联系人信息
	 * @param data
	 * @return
	 */
	public TResult<Contact> contact_getmodel(Contact data) {
		String methodName = "android_contact_getmodel";
		WebServiceObject.Builder builder = new WebServiceObject.Builder(
				SERVICE_URL, SERVICE_NS, methodName);

		try {
			String string = SoapObjectUtils.ObjectToSting(data);
			SoapObject resultSoapObject = builder.setStr("string", string)
					.get().call();
			string = resultSoapObject.getProperty(0).toString();
			Log.i(methodName, string);
			return (TResult<Contact>) SoapObjectUtils.StringToObject(string);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 添加联系人
	 * @param data
	 * @return
	 */
	public Result contact_add(Contact data)
	{
		String methodName="android_contact_add";
		WebServiceObject.Builder builder = new WebServiceObject.Builder(SERVICE_URL, SERVICE_NS, methodName);
		
		try
		{
			String string = SoapObjectUtils.ObjectToSting(data);
			SoapObject resultSoapObject = builder.setStr("string", string).get().call();
			
			string  = resultSoapObject.getProperty(0).toString();
			Log.i(methodName,string);
			return (Result) SoapObjectUtils.StringToObject(string);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 修改联系人
	 * @param data
	 * @return
	 */
	public Result contact_update(Contact data)
	{
		String methodName="android_contact_update";
		WebServiceObject.Builder builder = new WebServiceObject.Builder(SERVICE_URL, SERVICE_NS, methodName);
		
		try
		{
			String string = SoapObjectUtils.ObjectToSting(data);
			SoapObject resultSoapObject = builder.setStr("string", string).get().call();
			
			string  = resultSoapObject.getProperty(0).toString();
			Log.i(methodName,string);
			return (Result) SoapObjectUtils.StringToObject(string);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
