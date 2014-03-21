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
 * @Title:WebServiceUtil Description�� WebService
 *                       ksoap2��Դ���ĸ������ڲ���JSON�������ʱʹ�ã�Ĭ��dotNetΪfalse������Ver11Э��
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
		// ����HttpTransportSE�������
		ht = new HttpTransportSE(url);
		ht.debug = false;
		// ����Envelop����
		envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = false;
		// ����SoapObject����
		soapObject = new SoapObject(nameSpace, methodName);
	}

	/**
	 * ����WebService
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
	 * @Title:Builder Description��
	 *                αbuilderģʽ����ʵ�ڴ���BuilderʱWebServiceObject���Ѿ���������
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
		 * ����Ҫ����map�������ӳ��,�Զ�����������õ�ÿ���Զ��������Ҫ����map
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
		 * ����Ҫ����map�������ӳ��,�Զ�����������õ�ÿ���Զ��������Ҫ����map
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
		 * ����Զ�������ӳ�䣬��server�˶���������뱾�ض���������ͬʱ����
		 * 
		 * @param localComplexClass
		 * @return
		 */
		public Builder map(Class<? extends KvmSerializable> localComplexClass) {

			return map(localComplexClass.getSimpleName(), localComplexClass);
		}

		/**
		 * ����Զ�������ӳ�䣬��server�˶���������뱾�ض������Ʋ�ͬʱ����
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
		 * ���ü���dotnetģʽ��Ĭ��Ϊtrue
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