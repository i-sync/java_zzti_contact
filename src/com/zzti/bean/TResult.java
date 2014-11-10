package com.zzti.bean;

import java.io.Serializable;

public class TResult<T> extends Result implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5087308040872567770L;

	public TResult() {
		// TODO Auto-generated constructor stub
	}
	
	private T t;

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}

}
