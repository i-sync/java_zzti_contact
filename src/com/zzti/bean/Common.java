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
	 * ��ȡʵ��
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
	 * ��ȡ�༶�б�
	 * 
	 * @return
	 */
	public ListResult<Class> class_getlist() {
		// ������
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
	 * ��ȡ��ϵ���б�
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
	 * ����ID��ȡ��ϵ����Ϣ
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
	 * �����ϵ��
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
	 * �޸���ϵ��
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
