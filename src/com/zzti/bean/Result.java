package com.zzti.bean;

import java.io.Serializable;

/**
 * webservice返回基类
 * 
 * @author zhenyun
 * 
 */
public class Result implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2521229921861976258L;
	/**
	 * 返回结果 1：成功 0：失败
	 */
	private int result;
	/**
	 * 操作信息
	 */
	private String message;
	
	private Object obj;

	public int getResult() {
		return this.result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
	
}
