package com.zzti.utils;

import java.io.IOException;
import java.util.HashMap;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

/**
 * 
 * @Title:WebServiceUtil Description： WebService
 *                       ksoap2开源包的辅助，在不用JSON传输对象时使用，默认dotNet为false，采用Ver11协议
 * @author zhuys
 * @date 2013-7-14
 * 
 */
public class WebServiceObject {

	private SoapObject soapObject;
	private HttpTransportSE ht;
	private SoapSerializationEnvelope envelope;
	private String nameSpace;

	private WebServiceObject(String url, String nameSpace, String methodName) {

		this.nameSpace = nameSpace;
		// 创建HttpTransportSE传输对象
		ht = new HttpTransportSE(url);
		ht.debug = false;
		// 创建Envelop对象
		envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = false;
		// 创建SoapObject对象
		soapObject = new SoapObject(nameSpace, methodName);
	}

	/**
	 * 调用WebService
	 * 
	 * @return
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	public SoapObject call() throws IOException, XmlPullParserException {
		envelope.bodyOut = soapObject;
		ht.call(null, envelope);
		SoapObject result = (SoapObject) envelope.bodyIn;
		return result;
	}

	/**
	 * 
	 * @Title:Builder Description：
	 *                伪builder模式，其实在创建Builder时WebServiceObject就已经创建好了
	 * @author zhuys
	 * @date 2013-7-16
	 * 
	 */
	public static class Builder {

		WebServiceObject obj;

		public Builder(String url, String nameSpace, String methodName) {
			obj = new WebServiceObject(url, nameSpace, methodName);
		}

		public Builder setInt(String paramName, int value) {
			obj.soapObject.addProperty(paramName, value);
			return this;
		}

		public Builder setInt(HashMap<String, Integer> params) {
			Object[] keys = params.keySet().toArray();
			for (int i = 0; i < keys.length; i++) {
				setInt(keys[i].toString(), params.get(keys[i]));
			}
			return this;
		}

		public Builder setLong(String paramName, long value) {
			obj.soapObject.addProperty(paramName, value);
			return this;
		}

		public Builder setLong(HashMap<String, Long> params) {
			Object[] keys = params.keySet().toArray();
			for (int i = 0; i < keys.length; i++) {
				setLong(keys[i].toString(), params.get(keys[i]));
			}
			return this;
		}

		public Builder setBool(String paramName, boolean value) {
			obj.soapObject.addProperty(paramName, value);
			return this;
		}

		public Builder setBool(HashMap<String, Boolean> params) {
			Object[] keys = params.keySet().toArray();
			for (int i = 0; i < keys.length; i++) {
				setBool(keys[i].toString(), params.get(keys[i]));
			}
			return this;
		}

		public Builder setStr(String paramName, String value) {
			obj.soapObject.addProperty(paramName, value);
			return this;
		}

		public Builder setStr(HashMap<String, String> params) {
			Object[] keys = params.keySet().toArray();
			for (int i = 0; i < keys.length; i++) {
				setStr(keys[i].toString(), params.get(keys[i]));
			}
			return this;
		}
		/**
		public Builder setIntList(String paramName, List<Integer> list) {
			obj.soapObject.addProperty(paramName, new KvmIntList(obj.nameSpace,
					list));
			return this;
		}

		public Builder setLongList(String paramName, List<Long> list) {
			obj.soapObject.addProperty(paramName, new KvmLongList(
					obj.nameSpace, list));
			return this;
		}

		public Builder setBoolList(String paramName, List<Boolean> list) {
			obj.soapObject.addProperty(paramName, new KvmBoolList(
					obj.nameSpace, list));
			return this;
		}

		public Builder setStrList(String paramName, List<String> list) {
			obj.soapObject.addProperty(paramName, new KvmStringList(
					obj.nameSpace, list));
			return this;
		}*/

		/**
		 * 还需要调用map函数添加映射,自定义对象内引用的每个自定义对象都需要调用map
		 * 
		 * @param paramName
		 * @param value
		 * @return
		 */
		public Builder setComplex(String paramName, KvmSerializable value) {
			obj.soapObject.addProperty(paramName, value);
			return this;
		}

		/**
		 * 还需要调用map函数添加映射,自定义对象内引用的每个自定义对象都需要调用map
		 * 
		 * @param paramName
		 * @param list
		 * @return
		 */
		/*public Builder setComplexList(String paramName,
				String serverComplexName, List<? extends KvmSerializable> list) {
			obj.soapObject.addProperty(paramName, new KvmComplexList(
					obj.nameSpace, serverComplexName, list));

			return this;
		}*/

		/**
		 * 添加自定义对象的映射，当server端对象的名称与本地对象名称相同时调用
		 * 
		 * @param localComplexClass
		 * @return
		 */
		public Builder map(Class<? extends KvmSerializable> localComplexClass) {

			return map(localComplexClass.getSimpleName(), localComplexClass);
		}

		/**
		 * 添加自定义对象的映射，当server端对象的名称与本地对象名称不同时调用
		 * 
		 * @param localComplexClass
		 * @return
		 */
		public Builder map(String serverComplexName,
				Class<? extends KvmSerializable> localComplexClass) {

			obj.envelope.addMapping(obj.nameSpace, serverComplexName,
					localComplexClass);
			return this;
		}

		/**
		 * 设置兼容dotnet模式，默认为true
		 * 
		 * @param dotNet
		 * @return
		 */
		public Builder setDotnet(boolean dotNet) {

			obj.envelope.dotNet = dotNet;
			return this;
		}

		public WebServiceObject get() {
			return obj;
		}
	}

}