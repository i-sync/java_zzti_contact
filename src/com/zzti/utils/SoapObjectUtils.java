package com.zzti.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.util.Base64;

public class SoapObjectUtils {

	/**
	 * 把对象转换成字符串
	 * 
	 * @param obj
	 * @return
	 */
	public static String ObjectToSting(Object obj) {
		String result = null;
		try {
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			ObjectOutputStream objOut = new ObjectOutputStream(byteOut);
			objOut.writeObject(obj);
			objOut.flush();
			byte[] array = byteOut.toByteArray();
			objOut.close();
			byteOut.close();
			result = Base64.encodeToString(array, Base64.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 按字符串转换为对象
	 * 
	 * @param str
	 * @return
	 */
	public static Object StringToObject(String str) {
		Object result = null;

		try {
			byte[] array = Base64.decode(str, Base64.DEFAULT);
			ByteArrayInputStream byteIn = new ByteArrayInputStream(array);
			ObjectInputStream objIn = new ObjectInputStream(byteIn);
			result = objIn.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
}
